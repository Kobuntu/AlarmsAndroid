package com.proryv.alarmnotification.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.main_activity;

import java.util.ArrayList;

/**
 * Created by IG on 08.08.13.
 */
public class MultiChoiceFindableAdapter extends BaseExpandableListAdapter implements CompoundButton.OnCheckedChangeListener, SearchView.OnQueryTextListener, Filterable {

    private ArrayList<HierarchyElement> _originalItems;
    private ArrayList<HierarchyElement> _items;
    private main_activity _activity;
    private ExpandableListView _lv;

    public MultiChoiceFindableAdapter(ArrayList<HierarchyElement> items, main_activity activity, ExpandableListView lv)
    {
        _originalItems = items;
        _items = new ArrayList<HierarchyElement>(_originalItems);
        _activity = activity;
        _lv = lv;
    }

    public ArrayList<MultiChoiceElement> getSelected()
    {
        if (_items == null) return null;

        ArrayList<MultiChoiceElement> result = new ArrayList<MultiChoiceElement>();

        for (HierarchyElement hi : _items)
        {
            if (hi.items!=null && hi.items.size() > 0)
            {
                for (MultiChoiceElement me : hi.items)
                {
                    if (me.IsChecked)
                    {
                        result.add(me);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        MultiChoiceElement me = (MultiChoiceElement)compoundButton.getTag();
        if (me!=null)
        {
            me.IsChecked = b;
        }
    }

    boolean isPrevEmpty = false;

    @Override
    public Filter getFilter()
    {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, android.widget.Filter.FilterResults results) {

                _items.clear();
                ArrayList<HierarchyElement> listSource = (ArrayList<HierarchyElement>) results.values;
                _items.addAll(listSource);
                notifyDataSetChanged();

                if (listSource!=null && listSource.size() > 0)
                {
                    if (isPrevEmpty) //Предыдущий поиск вернул пустой результат
                    {
                        _lv.expandGroup(0, true);
                        isPrevEmpty = false;
                    }
                }
                else
                {
                    isPrevEmpty = true;
                }
            }

            @Override
            protected android.widget.Filter.FilterResults performFiltering(CharSequence constraint) {
                android.widget.Filter.FilterResults results = new android.widget.Filter.FilterResults();

                if (_originalItems != null && _originalItems.size() > 0)
                {
                    String filter;
                    ArrayList<HierarchyElement> filteredItems = new ArrayList<HierarchyElement>();
                    if (constraint != null && !(filter = constraint.toString().toLowerCase()).isEmpty())
                    {
                        for(HierarchyElement item : _originalItems)
                        {
                            if (item.items!=null && item.items.size() > 0)
                            {
                                HierarchyElement newItem = new HierarchyElement(item.multiChoiceType, new ArrayList<MultiChoiceElement>());
                                for(MultiChoiceElement me : item.items)
                                {
                                    if (isItemContainText(filter, me))
                                    {
                                        newItem.items.add(me);
                                    }
                                }

                                if (newItem.items.size() > 0)
                                {
                                    filteredItems.add(newItem);
                                }
                            }
                        }
                    }
                    else
                    {
                        filteredItems.addAll(_originalItems);
                    }

                    results.values = filteredItems;
                    results.count = filteredItems.size();
                }

                return results;
            }
        };
        return filter;
    }

    private boolean isItemContainText(String filter, MultiChoiceElement item)
    {
        String ad = item.Text;
       return  ((ad!=null && !ad.isEmpty() && ad.toLowerCase().indexOf(filter) > -1));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        setF(s);
        if (!getIsUpdated())
        {
            upDateFilter();
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

    private void upDateFilter()
    {
        setIsUpdated(true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {

                }
                if (_activity!=null)
                {
                    _activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getFilter().filter(getF());
                            setIsUpdated(false);
                        }
                    });
                }
            }
        }).start();


    }

    @Override
    public int getGroupCount() {
        return _items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        HierarchyElement he = _items.get(groupPosition);
        if (he!=null && he.items!=null)
        {
            return he.items.size();
        }

        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _items.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        HierarchyElement he = _items.get(groupPosition);
        if (he!=null && he.items!=null)
        {
            return he.items.get(childPosition);
        }

        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        HierarchyElement he = _items.get(groupPosition);
        if (he!=null)
        {
            return he.multiChoiceType.getValue();
        }

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        HierarchyElement he = _items.get(groupPosition);
        long r = childPosition;
        if (he!=null && he.items!=null)
        {
            r = ((long)he.multiChoiceType.getValue()) << 32;
            MultiChoiceElement mce = he.items.get(childPosition);
            if (mce!=null && mce.Id2!=null)
            {
                return r | (mce.Id2.hashCode() & 0xffffffffL);
            }
        }

        return r;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null && _activity != null)
        {
            view = _activity.getLayoutInflater().inflate(R.layout.item_hierarchy, null);
        }

        HierarchyElement he = _items.get(groupPosition);
        if (he!=null)
        {
            ((TextView)view.findViewById(R.id.textView)).setText(he.multiChoiceType.getName(_activity));
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(_activity);
            view = inflater.inflate(R.layout.item_multichoice, null);
        }

        HierarchyElement he = _items.get(groupPosition);
        if (he!=null && he.items!=null)
        {
            MultiChoiceElement currItem = he.items.get(childPosition);
            if (currItem != null)
            {
                CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);
                cb.setText(currItem.Text);
                cb.setTag(currItem);
                cb.setChecked(currItem.IsChecked);
                cb.setOnCheckedChangeListener(this);
                ImageView image = (ImageView)view.findViewById(R.id.imageView);
                if (currItem.MultiChoiceType == EnumMultiChoiceType.Alarm)
                {
                    image.setVisibility(View.VISIBLE);
                    AlarmHelper.setAlarmImage(image, currItem.Id);
                }
                else
                {
                    image.setVisibility(View.GONE);
                }
            }
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }
}