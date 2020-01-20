package com.proryv.alarmnotification.adapters;

import android.support.v4.widget.SearchViewCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.main_activity;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfguid;
import com.proryv.alarmnotification.wcf.neurospeech.DbAlarm;
import com.proryv.alarmnotification.wcf.neurospeech.KeyValueOfintstring;
import com.proryv.alarmnotification.wcf.neurospeech.KeyValueOfstringstring;
import com.proryv.alarmnotification.wcf.neurospeech.TAlarmsRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static java.util.Calendar.getInstance;

/**
 * Created by IG on 01.07.13.
 */
public class AlarmExpandableAdapter extends BaseExpandableListAdapter
        implements SearchView.OnQueryTextListener, CompoundButton.OnCheckedChangeListener, Filterable, Runnable {

    //отфильтрованные по условию поиска

    //FilteredItems _items;
    private ArrayList<DbAlarm> _items;
    private ArrayList<DbAlarm> _filteredItems;


    ArrayList<MultiChoiceElement> _filterObjects;
    public synchronized ArrayList<MultiChoiceElement> getFilterObjects()
    {
        return _filterObjects;
    }
    synchronized void setFilterObjects(ArrayList<MultiChoiceElement> filterObjects)
    {
        _filterObjects = filterObjects;
    }

    final private SimpleDateFormat dateFormat;
    final private SimpleDateFormat dateFormatms;
    private main_activity _activity;
    private int _serverCount;
    private boolean _isArchive;

    public AlarmExpandableAdapter(main_activity activity, boolean isArchive)
    {
        _isArchive = isArchive;
        _activity = activity;
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        dateFormatms = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SS");
        //_items = new FilteredItems(activity);
        _items = new ArrayList<DbAlarm>();
        _filteredItems = new ArrayList<DbAlarm>();
    }

    public Calendar getLastEventDate()
    {
        if (_items!=null && _items.size() > 0)
        {
            //Сортировка идет только по Дате
            DbAlarm lastElement = _items.get(_items.size() - 1);
            Calendar dtStart = getInstance();
            dtStart.setTime(lastElement.getEventDateTime());
            return dtStart;
        }
        return null;
    }

    public void UpdateItems(TAlarmsRequest rs)
    {
        addAll(rs);
        setF("");
        //notifyDataSetChanged();
    }

    public void clear()
    {
        synchronized (_items)
        {
            _filteredItems.clear();
            _items.clear();
            _formulaDict.clear();
            _psBalancePathDict.clear();
            _psPathDict.clear();
            _slaveSystemsDict.clear();
            _tiPathDict.clear();
        }
        notifyDataSetChanged();
    }

    public synchronized ArrayList<DbAlarm> getItems()
    {
        return _items;
    }

    @Override
    public int getGroupCount() {
        return _filteredItems.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _filteredItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _filteredItems.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        boolean isFirstBinding;
        if (view == null && _activity != null)
        {
            isFirstBinding = true;
            view = _activity.getLayoutInflater().inflate(R.layout.alarm_item_group, null);
        }
        else
        {
            isFirstBinding = false;
        }

        try
        {
            DbAlarm currAlarm = _filteredItems.get(groupPosition);
            if (currAlarm != null && view != null)
            {
                TextView tvCurrAlarmEndpointAddress = (TextView)view.findViewById(R.id.tvCurrAlarmEndpointAddress);

                if (_serverCount <= 1)
                {
                    tvCurrAlarmEndpointAddress.setVisibility(View.GONE);
                }
                else if (currAlarm.subscribedServer!=null)
                {
                    tvCurrAlarmEndpointAddress.setText(currAlarm.subscribedServer.EndpointAddress);
                }


                ((TextView)view.findViewById(R.id.tvCurrAlarmMessageShort)).setText(currAlarm.getAlarmDescription());
                ((TextView)view.findViewById(R.id.tvCurrAlarmDateTime)).setText(dateFormatms.format(currAlarm.getEventDateTime()));

                //Уровень тревоги
                ImageView ivCurrAlarmLevel = (ImageView)view.findViewById(R.id.ivCurrAlarmLevel);

                AlarmHelper.setAlarmImage(ivCurrAlarmLevel, currAlarm.getAlarmSeverity());

                CheckBox cbIsConfirmCurrAlarm = ((CheckBox)view.findViewById(R.id.cbIsConfirmCurrAlarm));
                if (_isArchive)
                {
                    cbIsConfirmCurrAlarm.setVisibility(View.GONE);
                }
                else
                {
                    cbIsConfirmCurrAlarm.setTag(currAlarm);
                    cbIsConfirmCurrAlarm.setChecked(currAlarm.isSelected);
                }

                if (isFirstBinding && _filteredItems!=null)
                {
                    cbIsConfirmCurrAlarm.setOnCheckedChangeListener(this);
                }
            }
        }
        catch (Exception ex)
        {

        }

        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null && _activity != null)
        {
            view = _activity.getLayoutInflater().inflate(R.layout.alarm_item_child, null);
        }

        DbAlarm currAlarm = _filteredItems.get(groupPosition);
        if (currAlarm != null && view != null)
        {
            Date ad = currAlarm.getAlarmDateTime();
            if (ad!=null)
            {
                ((TextView)view.findViewById(R.id.tvDetailEventDateTime)).setText(dateFormat.format(ad));
            }
            else
            {
                ((TextView)view.findViewById(R.id.tvDetailEventDateTime)).setText("");
            }
            ((TextView)view.findViewById(R.id.tvDetailAlarmDescription)).setText(currAlarm.getAlarmMessage());
            ((TextView)view.findViewById(R.id.tvDetailWorkFlowActivityName)).setText(currAlarm.getWorkFlowActivityName());
            StringBuilder objectName = new StringBuilder();
            ((TextView)view.findViewById(R.id.tvDetailFullHierarchyName)).setText(GetHierarchyName(currAlarm, objectName));

            ((TextView)view.findViewById(R.id.tvObjectType)).setText(objectName.toString());
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
     return startFilter(s);
    }

    private boolean startFilter(String s)
    {
        setF(s);
        if (!getIsUpdated())
        {
            if ((s == null || s.isEmpty()) && (getFilterObjects() == null || getFilterObjects().size() == 0))
            {
                if (_filteredItems!=_items)
                {
                    _filteredItems = _items;
                    notifyDataSetChanged();
                    setIsUpdated(false);
                }
            }
            else
            {
                new Thread(this).start();
                //new GetFindTask().execute();
            }
        }
        return true;
    }

    String _f;
    synchronized String getF()
    {
        return _f;
    }

    synchronized void setF(String f)
    {
        _f = f;
    }

    boolean _isUpdated;
    synchronized boolean getIsUpdated()
    {
        return _isUpdated;
    }
    synchronized void  setIsUpdated(boolean isUpdated)
    {
        _isUpdated = isUpdated;
    }

    @Override
    public void run() {

        //Фоновая работа
        setIsUpdated(true);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {

        }

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getFilter().filter(getF());
                //Log.d("MyApp", "Обновление фильтра -> " + dateFormatms.format(Calendar.getInstance().getTime()));
                setIsUpdated(false);
            }
        });
    }

    public void selectAll(Boolean b)
    {
        if (_filteredItems!=null && _filteredItems.size() > 0)
        {
            synchronized (_filteredItems)
            {
                for(DbAlarm a : _filteredItems)
                {
                    a.isSelected = b;
                }
            }

            notifyDataSetChanged();
        }
    }

    public ArrayOfguid getSelectedAlarmGuids(ArrayList<SubscribedServer> servers)
    {
        Vector<String> guids = new Vector<String>();
        if (_filteredItems!=null && _filteredItems.size() > 0 && servers!=null && servers.size() > 0)
        {
            synchronized (_filteredItems)
            {
                for(DbAlarm a : _filteredItems)
                {
                    if (a.isSelected && a.subscribedServer!=null)
                    {
                        for (SubscribedServer s : servers)
                        {
                            if (s.id == a.subscribedServer.id)
                            {
                                if (s.IsActive)  guids.add(a.getAlarm_ID());
                                break;
                            }
                        }
                    }
                }
            }
        }

        ArrayOfguid result = new ArrayOfguid();
        result.setguid(guids);

        return result;
    }

    @Override
    public Filter getFilter()
    {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, android.widget.Filter.FilterResults results) {

                ArrayList<DbAlarm> fi =(ArrayList<DbAlarm>) results.values;
                if (fi!=null)
                {
                    _filteredItems = fi;
                    notifyDataSetChanged();
                }

                setIsUpdated(false);
            }

            @Override
            protected android.widget.Filter.FilterResults performFiltering(CharSequence constraint) {

                android.widget.Filter.FilterResults results = new android.widget.Filter.FilterResults();
                String filter;
                ArrayList<DbAlarm> filteredItems = new ArrayList<DbAlarm>();
                filter = constraint.toString().toLowerCase();

                if (_items != null && _items.size() > 0)
                {
                    synchronized (_items)
                    {
                        for(DbAlarm item : _items)
                        {
                            if (isItemContainText(filter, item) && isItemInObjects(item))
                            {
                                filteredItems.add(item);
                            }
                        }
                    }
                }

                results.count = filteredItems.size();
                results.values = filteredItems;

                return results;
            }
        };
        return filter;
    }

    private boolean isItemContainText(String filter, DbAlarm item)
    {

        String ad = item.getAlarmMessage();
        boolean result = (ad!=null && ad.toLowerCase().indexOf(filter) > -1);
        if (result) return result;

        Date d = item.getEventDateTime();
        if (d!=null)
        {
            String ed = dateFormat.format(d);
            result = (ed!=null && ed.toLowerCase().indexOf(filter) > -1);
            if (result) return result;
        }

        d = item.getAlarmDateTime();
        if (d!=null)
        {
            String ed = dateFormat.format(d);
            result = (ed!=null && ed.toLowerCase().indexOf(filter) > -1);
            if (result) return result;
        }

        StringBuilder objectName = new StringBuilder();
        String hN = GetHierarchyName(item, objectName);
        return  (hN!=null && hN.toLowerCase().indexOf(filter) > -1);
    }


    private boolean isItemInObjects(DbAlarm item)
    {
        ArrayList<MultiChoiceElement> fo =getFilterObjects();
        if (fo == null || fo.size() == 0) return true;

        Boolean isExists = false;
        for(MultiChoiceElement me : fo)
        {
            if (me==null) break;
            switch (me.MultiChoiceType)
            {
                case Formula:
                    String s = item.getFormula_UN();
                    if (s!=null && s.equalsIgnoreCase(me.Id2))
                    {
                        isExists = true; break;
                    }
                    break;

                case BalancePS:
                    s = item.getBalancePS_UN();
                    if (s!=null && s.equalsIgnoreCase(me.Id2))
                    {
                        isExists = true;break;
                    }
                    break;
                case PS:
                    int id;
                    try {
                        id = Integer.parseInt(me.Id2);
                    }
                    catch(NumberFormatException nfe) {
                        id = -1;
                    }

                    if (item.getPS_ID() == id)
                    {
                        isExists = true;break;
                    }
                    break;
                case TI:
                    try {
                        id = Integer.parseInt(me.Id2);
                    }
                    catch(NumberFormatException nfe) {
                        id = -1;
                    }

                    if (item.getTI_ID() == id)
                    {
                        isExists = true;break;
                    }
                    break;
                case Slave61968:
                    try {
                        id = Integer.parseInt(me.Id2);
                    }
                    catch(NumberFormatException nfe) {
                        id = -1;
                    }

                    if (item.getSlave61968System_ID() == id)
                    {
                        isExists = true;break;
                    }
                    break;
            }
        }
        return isExists;
    }

    public void setFilterObjects(ArrayList<MultiChoiceElement> selectedObjects, MenuItem _menuItemSearch)
    {
        ClearFinder(_menuItemSearch);
        setFilterObjects(selectedObjects);
        startFilter("");
//

//        else
//        {
//            if (_items!=null && _items.size() > 0)
//            {
//                _filteredItems = new ArrayList<DbAlarm>();
//                synchronized (_items)
//                {
//                    for(DbAlarm item : _items)
//                    {
//                        if (isItemInObjects(item))
//                        {
//                            _filteredItems.add(item);
//                        }
//                    }
//                }
//            }
//        }
//        notifyDataSetChanged();
    }

    private void ClearFinder(MenuItem _menuItemSearch)
    {
        if (_menuItemSearch!=null)
        {
            SearchView mSearchView = (SearchView)_menuItemSearch.getActionView();
            mSearchView.setQuery("", false);
            _menuItemSearch.collapseActionView();
        }
    }

    public String GetHierarchyName(DbAlarm item, StringBuilder objectName)
    {
        try
        {
            if (item.getTI_ID() > 0 && _tiPathDict != null)
            {
                objectName.append(_activity.getString(R.string.TI));
                return  _tiPathDict.get(item.getTI_ID());
            }

            if (item.getPS_ID() > 0 && _psPathDict != null)
            {
                objectName.append(_activity.getString(R.string.PS));
                return  _psPathDict.get(item.getPS_ID());
            }

            String un = item.getFormula_UN();
            if (un!=null && !un.isEmpty() && _formulaDict != null)
            {
                objectName.append(_activity.getString(R.string.formulaDict));
                return _formulaDict.get(un);
            }

            un =item.getBalancePS_UN();
            if (un!=null && !un.isEmpty() && _psBalancePathDict != null)
            {
                objectName.append(_activity.getString(R.string.sBalancePath));
                return _psBalancePathDict.get(item.getBalancePS_UN());
            }

            if (item.getSlave61968System_ID()>0 && _slaveSystemsDict != null)
            {
                objectName.append(_activity.getString(R.string.slaveSystems));
                return _slaveSystemsDict.get(item.getSlave61968System_ID());
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(_activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return "";
    }


    Map<String, String> _formulaDict = new HashMap<String, String>();
    Map<String, String> _psBalancePathDict = new HashMap<String, String>();
    Map<Integer, String> _tiPathDict = new HashMap<Integer, String>();
    Map<Integer, String> _psPathDict = new HashMap<Integer, String>();
    Map<Integer, String> _slaveSystemsDict = new HashMap<Integer, String>();
    public synchronized Map<String, String> getFormulaDict()
    {
        return _formulaDict;
    }

    public synchronized Map<String, String> getPsBalancePathDict()
    {
        return _psBalancePathDict;
    }

    public synchronized Map<Integer, String> getTiPathDict()
    {
        return _tiPathDict;
    }

    public synchronized Map<Integer, String> getPsPathDict()
    {
        return _psPathDict;
    }

    public synchronized Map<Integer, String> getSlaveSystemsDict()
    {
        return _slaveSystemsDict;
    }

    public void addAll(TAlarmsRequest rs)
    {
        SubscribedServer subscribedServer = rs.subscribedServer;
        if (rs!=null)
        {
            try
            {
                Vector<DbAlarm> v = rs.getAlarmList().getDbAlarm();

                synchronized (_items)
                {
                    for (DbAlarm a : v)
                    {
                        a.subscribedServer = subscribedServer;
                        _items.add(a);
                    }

                    if (_items.size() > 0)
                    {
                        DbAlarm a =_items.get(0);
                        DbAlarm b =_items.get(_items.size() - 1);
                        if (a!=null && b!=null && a.subscribedServer!=null && b.subscribedServer!=null && a.subscribedServer.id != b.subscribedServer.id)
                        {
                            _serverCount = 2;
                        }
                        else
                        {
                            _serverCount =1;
                        }
                    }

                }

                KeyValueOfstringstringToDictionary(_formulaDict, rs.getFormulaDict().getKeyValueOfstringstring());
                KeyValueOfstringstringToDictionary(_psBalancePathDict, rs.getPsBalansePathDict().getKeyValueOfstringstring());
                KeyValueOfintstringToDictionary(_psPathDict, rs.getPsPathDict().getKeyValueOfintstring());
                KeyValueOfintstringToDictionary(_slaveSystemsDict, rs.getSlaveSystemsDict().getKeyValueOfintstring());
                KeyValueOfintstringToDictionary(_tiPathDict, rs.getTiPathDict().getKeyValueOfintstring());
            }
            catch (Exception ex)
            {
                Toast.makeText(_activity, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void KeyValueOfstringstringToDictionary (Map<String, String> result, Vector<KeyValueOfstringstring> valueOfstringstrings)
    {
        if (valueOfstringstrings == null || valueOfstringstrings.size() == 0) return;

        synchronized (result)
        {
            for(KeyValueOfstringstring valueOfstringstring : valueOfstringstrings)
            {
                result.put(valueOfstringstring.getKey(), valueOfstringstring.getValue());
            }
        }
    }

    private void KeyValueOfintstringToDictionary (Map<Integer, String> result, Vector<KeyValueOfintstring> valueOfintstrings)
    {
        if (valueOfintstrings == null || valueOfintstrings.size() == 0) return;

        synchronized (result)
        {
            for(KeyValueOfintstring valueOfintstring : valueOfintstrings)
            {
                result.put(valueOfintstring.getKey(), valueOfintstring.getValue());
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        DbAlarm alarm = (DbAlarm)buttonView.getTag();
        if (alarm!=null)
        {
            alarm.isSelected = isChecked;
        }
    }
}
