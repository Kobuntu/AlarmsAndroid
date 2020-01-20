package com.proryv.alarmnotification.tabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.adapters.AlarmHelper;
import com.proryv.alarmnotification.adapters.EnumMultiChoiceType;
import com.proryv.alarmnotification.adapters.MultiChoiceAdapter;
import com.proryv.alarmnotification.adapters.MultiChoiceElement;
import com.proryv.alarmnotification.common.DateTimeRangeDialog;
import com.proryv.alarmnotification.common.DateTimeRangeEvent;
import com.proryv.alarmnotification.wcf.neurospeech.AlarmFilterSettings;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfKeyValueOfintstring;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfint;
import com.proryv.alarmnotification.wcf.neurospeech.KeyValueOfintstring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Ig on 07.07.13.
 */
public class alarm_filter extends FragmentActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, DateTimeRangeEvent {

    AlarmFilterSettings filterSettings;
    Boolean _isNeedUpdateButton;

    private HashMap<Integer, String> alarmSeverityDictionary;
    public synchronized HashMap<Integer, String> getAlarmSeverityDictionary()
    {
        if (alarmSeverityDictionary == null)
        {
            alarmSeverityDictionary = new HashMap<Integer, String>(4);
            alarmSeverityDictionary.put(1, getString(R.string.Norma));
            alarmSeverityDictionary.put(2, getString(R.string.Alarm));
            alarmSeverityDictionary.put(3, getString(R.string.Warning));
        }

        return alarmSeverityDictionary;
    }

    private Boolean isArchives;

    private HashMap<Integer, String> _workflowActivityNamesDictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_filter);

        Intent intent = getIntent();

        if (intent!=null)
        {
            _workflowActivityNamesDictionary = new HashMap<Integer, String>();
            ArrayOfKeyValueOfintstring _workflowActivityNamesArray = intent.getParcelableExtra("WL");
            if (_workflowActivityNamesArray!=null && _workflowActivityNamesArray.getKeyValueOfintstring()!=null
                    && _workflowActivityNamesArray.getKeyValueOfintstring().size() > 0)
            {
                for (KeyValueOfintstring i : _workflowActivityNamesArray.getKeyValueOfintstring())
                {
                    _workflowActivityNamesDictionary.put(i.getKey(), i.getValue());
                }
            }

            isArchives = intent.getBooleanExtra("isArchives", false);
        }

        configureView(savedInstanceState);
    }

    private void configureView(Bundle savedInstanceState)
    {
        if (savedInstanceState!=null && savedInstanceState.containsKey("filter"))
        {
            filterSettings = savedInstanceState.getParcelable("filter");
        }
        else
        {
            filterSettings = AlarmHelper.getFilterSettings(this);
        }

        {
        //Диапазон дат срабатывания аварий
            DateTimeRangeDialog dateTimeRangeDialog = (DateTimeRangeDialog)findViewById(R.id.lAlarmLayout);
            if (dateTimeRangeDialog!=null)
            {
//                Calendar dtStart = Calendar.getInstance();
//                Calendar dtEnd = Calendar.getInstance();
//
//                Date dt = filterSettings.getAlarmDateTimeStart();
//                if (dt!=null)
//                    dtStart.setTime(dt);
//                else
//                    dtStart = null;
//
//                dt = filterSettings.getAlarmDateTimeFinish();
//                if (dt!=null)
//                    dtEnd.setTime(dt);
//                else
//                    dtEnd = null;
//
//                dateTimeRangeDialog.Init(dtStart, dtEnd, this, true, true, getSupportFragmentManager());

                findViewById(R.id.tvAlarmDateTimeFilterName).setVisibility(View.GONE);
                dateTimeRangeDialog.setVisibility(View.GONE);
            }
        }


        {
            //Диапазон дат получения аварий
            DateTimeRangeDialog  dateTimeRangeDialog = (DateTimeRangeDialog)findViewById(R.id.lEventLayout);
            if (dateTimeRangeDialog!=null)
            {
                if (isArchives)
                {
                    dateTimeRangeDialog.setVisibility(View.GONE);
                    findViewById(R.id.tvEventDateTimeFilterName).setVisibility(View.GONE);
                }
                else
                {
                    Calendar dtStart = Calendar.getInstance();
                    Calendar dtEnd = Calendar.getInstance();

                    Date dt = filterSettings.getEventDateTimeStart();
                    if (dt!=null)
                        dtStart.setTime(dt);
                    else
                        dtStart = null;

                    dt = filterSettings.getEventDateTimeFinish();
                    if (dt!=null)
                        dtEnd.setTime(dt);
                    else
                        dtEnd = null;

                    dateTimeRangeDialog.Init(dtStart, dtEnd, this, false, true, getSupportFragmentManager());
                }
            }
        }

        //Настройка  выбора уровня тревог
        EditText et = (EditText)findViewById(R.id.etAlarmSeverity);
        et.setTag(R.id.etAlarmSeverity);
        setAlarmSeverityText();
        et.setOnClickListener(this);

        //Настройка  выбора процессов
        et = (EditText)findViewById(R.id.etWorkflowActivity);
        et.setTag(R.id.etWorkflowActivity);
        setWorkflowText();
        et.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    // обработка нажатий меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (filterSettings == null) return super.onOptionsItemSelected(item);

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        switch (item.getItemId()) {
            case R.id.menuSave:
                AlarmHelper.setFilterSettings(this, filterSettings);
                intent.putExtra("Res", true);
                finish();
                return true;
            case R.id.menuCancel:
                intent.putExtra("Res", false);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void setAlarmSeverityText()
    {
        EditText et = ((EditText)findViewById(R.id.etAlarmSeverity));
        if (filterSettings.getAlarmSeverityList() != null && filterSettings.getAlarmSeverityList().getint() != null  && filterSettings.getAlarmSeverityList().getint().size() > 0)
        {
            StringBuilder sb = new StringBuilder();
            Iterator<Integer> iter =filterSettings.getAlarmSeverityList().getint().iterator();

            while (iter.hasNext()) {
                sb.append(getAlarmSeverityDictionary().get(iter.next()));
                if (!iter.hasNext()) {
                    break;
                }
                sb.append(", ");
            }

            et.setText(sb);
        }
        else
        {
            et.setText(getString(R.string.all_alarm_selected));
        }
    }

    void setWorkflowText()
    {
        if (_workflowActivityNamesDictionary == null || _workflowActivityNamesDictionary.size() == 0) return;

        EditText et = ((EditText)findViewById(R.id.etWorkflowActivity));
        if (filterSettings.getWorkflowActivityList() != null && filterSettings.getWorkflowActivityList().getint() != null  && filterSettings.getWorkflowActivityList().getint().size() > 0)
        {
            StringBuilder sb = new StringBuilder();
            Iterator<Integer> iter =filterSettings.getWorkflowActivityList().getint().iterator();

            while (iter.hasNext()) {
                sb.append(_workflowActivityNamesDictionary.get(iter.next()));
                if (!iter.hasNext()) {
                    break;
                }
                sb.append(", ");
            }

            et.setText(sb);
        }
        else
        {
            et.setText(getString(R.string.all_process_selected));
        }
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag==null) return;

        if (tag instanceof Integer)
        {
            Integer id = Integer.valueOf(tag.toString());

            LinearLayout view = (LinearLayout)getLayoutInflater().inflate(R.layout.dialog_multichoice, null);
            String title;
            switch (id)
            {
                case R.id.etAlarmSeverity:
                    if (!ConfigureDialogAdapter(EnumMultiChoiceType.Alarm, view))
                    {
                        Toast.makeText(this, getString(R.string.list_empty), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    title = getResources().getString(R.string.alarm_level);
                    break;
                case R.id.etWorkflowActivity:
                    if (!ConfigureDialogAdapter(EnumMultiChoiceType.Workflow, view))
                    {
                        Toast.makeText(this, getString(R.string.list_empty), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    title = getResources().getString(R.string.workflow_list);
                    break;
                default: title ="";
            }

            _showDialog(title, view);
        }
        else if (tag instanceof BaseAdapter)
        {
            Integer id = v.getId();
            if (id == R.id.ibSelectAll || id == R.id.ibUnSelectAll)
            {
                Boolean isCheck = id == R.id.ibSelectAll;
                SelectDeselect(v, isCheck);
                return;
            }
        }
    }

    private void SelectDeselect(View v, Boolean isCheck) {

        MultiChoiceAdapter adapter = (MultiChoiceAdapter)v.getTag();
        if (adapter!=null)
        {
            adapter.selectDeselectAll(isCheck);
        }
    }

    private boolean ConfigureDialogAdapter(EnumMultiChoiceType multiChoiceType, View view)
    {
        HashMap<Integer,String> filterDict = null;

        if (multiChoiceType == EnumMultiChoiceType.Alarm)
        {
            filterDict = getAlarmSeverityDictionary();
        }
        else
        {
            filterDict = _workflowActivityNamesDictionary;
        }

        if (filterDict == null || filterDict.size() == 0) return false;

        Vector<Integer> filterArray = null;
        if (multiChoiceType == EnumMultiChoiceType.Alarm)
        {
            if (filterSettings.getAlarmSeverityList()!=null) filterArray = filterSettings.getAlarmSeverityList().getint();
        }
        else
        {
            if (filterSettings.getWorkflowActivityList() != null) filterArray = filterSettings.getWorkflowActivityList().getint();
        }

        boolean alarmSeverityExists = filterArray != null && filterArray.size() > 0;

        ListView lv = (ListView)view.findViewById(R.id.listView);

        ArrayList<MultiChoiceElement> listSource = new ArrayList<MultiChoiceElement>();
        int size = filterDict.size();
        for(Map.Entry<Integer, String>  g : filterDict.entrySet())
        {
            Integer k =  g.getKey();
            Boolean isChecked = false;

            if (alarmSeverityExists)
            {
                for(Integer i :filterArray)
                {
                    if (i == k)
                    {
                        isChecked = true;
                        break;
                    }
                }
            }

            listSource.add(new MultiChoiceElement(isChecked, k, g.getValue(), multiChoiceType)); //&& filterArray.contains(g.Key)
        }

        MultiChoiceAdapter adapter = new MultiChoiceAdapter(listSource, this, multiChoiceType);
        lv.setAdapter(adapter);

        Button btn = (Button)view.findViewById(R.id.ibSelectAll);
        btn.setTag(adapter);
        btn.setOnClickListener(this);

        btn = (Button)view.findViewById(R.id.ibUnSelectAll);
        btn.setTag(adapter);
        btn.setOnClickListener(this);

        //Поиск
        SearchView sv = (SearchView)view.findViewById(R.id.searchView);
        //Ограничение количества символов поиска
        int etId = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText et = (EditText) sv.findViewById(etId);
        InputFilter[] fa = new InputFilter[1];
        fa[0] = new InputFilter.LengthFilter(20);
        et.setFilters(fa);

        int searchPlateId = sv.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        sv.findViewById(searchPlateId).setBackgroundResource(R.drawable.textfield_search_default);
        if (sv!=null)
        {
            sv.setOnQueryTextListener(adapter);
        }

        return true;
    }

    private void _showDialog(String title, View view)
    {
        //Запрет на повор экрана
        AlarmHelper.disableOrientationChange(this);

        ((TextView)view.findViewById(R.id.tvTitle)).setText(title);
        //Содержимое
        view.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);



        builder.setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onDialogClosed(dialog);
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // устанавливаем ее, как содержимое тела диалога
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            }
        });

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextAppearance(this, R.style.AlertDialogTextAppearance);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextAppearance(this, R.style.AlertDialogTextAppearance);

}

    //Окончательный результат
    private void onDialogClosed(DialogInterface dialog)
    {
        AlertDialog ad = (AlertDialog)dialog;
        if (ad!=null)
        {
            ListView lv = (ListView)ad.findViewById(R.id.listView);
            if (lv!=null)
            {
                MultiChoiceAdapter adapter = (MultiChoiceAdapter)lv.getAdapter();
                if (adapter!=null)
                {
                    Vector<Integer> items = adapter.getSelectedItems();
                    //if (items == null || items.size() == 0) return;

                    ArrayOfint ai = new ArrayOfint();
                    ai.setint(items);

                    if (adapter.multiChoiceType == EnumMultiChoiceType.Alarm)
                    {
                        filterSettings.setAlarmSeverityList(ai);
                        setAlarmSeverityText();
                    }
                    else
                    {
                        filterSettings.setWorkflowActivityList(ai);
                        setWorkflowText();
                    }
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.clear();
        if (filterSettings!=null) outState.putParcelable("filter", filterSettings);
    }

    //После изменений дат фильтра
    @Override
    public void OnRangeSelected(Calendar dtStart, Calendar dtEnd, Boolean isAlarmDateTimeChanged) {

        Date dt1, dt2;

        if (dtStart!=null) dt1 = dtStart.getTime();
        else dt1 = null;

        if (dtEnd!=null) dt2 = dtEnd.getTime();
        else dt2 = null;

        if (isAlarmDateTimeChanged)
        {
            filterSettings.setAlarmDateTimeStart(dt1);
            filterSettings.setAlarmDateTimeFinish(dt2);
        }
        else
        {
            filterSettings.setEventDateTimeStart(dt1);
            filterSettings.setEventDateTimeFinish(dt2);
        }
    }
}
