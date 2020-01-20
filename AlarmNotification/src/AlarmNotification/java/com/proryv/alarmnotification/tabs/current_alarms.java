package com.proryv.alarmnotification.tabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.adapters.AlarmExpandableAdapter;
import com.proryv.alarmnotification.adapters.AlarmHelper;
import com.proryv.alarmnotification.adapters.EnumMultiChoiceType;
import com.proryv.alarmnotification.adapters.HierarchyElement;
import com.proryv.alarmnotification.adapters.MultiChoiceElement;
import com.proryv.alarmnotification.adapters.MultiChoiceFindableAdapter;
import com.proryv.alarmnotification.adapters.SubscribedServer;
import com.proryv.alarmnotification.common.Singleton;
import com.proryv.alarmnotification.main_activity;
import com.proryv.alarmnotification.wcf.neurospeech.AlarmFilterSettings;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfguid;
import com.proryv.alarmnotification.wcf.neurospeech.DbAlarm;
import com.proryv.alarmnotification.wcf.neurospeech.TAlarmsRequest;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Ig on 26.06.13.
 */
public class current_alarms extends Fragment {

    MenuItem _menuItemSearch;
    BroadcastReceiver wcfBroadcastReceiver;
    AlarmExpandableAdapter alarmExpandableAdapter;
    LinearLayout linlaHeaderProgress;
    MenuItem menuSearch;
    MenuItem menuRefresh;
    PullToRefreshExpandableListView lvCurrAlarm;

    final String PARAM_LASTSTATUS = "isLast";
    final Integer STATUS_ADDITIONAL = 300;
    final Integer STATUS_ERROR = 200;
    final Integer STATUS_SUCCESS = 100;

    private static final String TAG = "MyActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        RegisterWcfBroadcastReceiver();
        alarmExpandableAdapter = new AlarmExpandableAdapter((main_activity)this.getActivity(), false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.current_alarms, container, false);
        lvCurrAlarm = (PullToRefreshExpandableListView)v.findViewById(R.id.lvCurrAlarm);

        lvCurrAlarm.getRefreshableView().setFastScrollEnabled(true);
        lvCurrAlarm.getRefreshableView().setAdapter(alarmExpandableAdapter);
        // Set a listener to be invoked when the list should be refreshed.
        lvCurrAlarm.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                // Задача на обновление когда тянуть вверх
                GetCurrentAlarmsAsync();
            }
        });

        linlaHeaderProgress = (LinearLayout) v.findViewById(R.id.linlaHeaderProgress);

        main_activity  mainActivity =(main_activity)getActivity();
        //Продолжение крутилки
        if (mainActivity!=null && mainActivity.lastState!=null)
        {
            if (mainActivity.setSelectedCurrentTab ||  mainActivity.lastState.getIsCurrentAlarmsStarted())
            {
                AnimateProgress(true);
            }
            else
            {
                AnimateProgress(false);
            }
        }

        return v;
    }

    ///Получение архивов
    private void RegisterWcfBroadcastReceiver()
    {
        wcfBroadcastReceiver = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                main_activity acivity = (main_activity)getActivity();
                if (acivity == null) return;
                boolean isNeedAdditionalRequest = false;

                try
                {
                    Log.d(TAG, "Current received");
                    //showMessage("Current received");
                    int status = intent.getIntExtra(getResources().getString(R.string.wcf_status), 0);
                    int additional = intent.getIntExtra(getResources().getString(R.string.wcf_additional), 0);

                    // Успешное завершение запроса текущих тревог
                    if (status  == STATUS_SUCCESS) {
                        //TAlarmsRequest rs = intent.getParcelableExtra(getResources().getString(R.string.wcf_result));
                        TAlarmsRequest rs = Singleton.getInstance().lastState.getLastCurrents(); //Обмен данными через синглтон
                        if (rs!=null)
                        {
                            Vector<DbAlarm> v = rs.getAlarmList().getDbAlarm();
                            if (v!=null)
                            {
                                final Integer size =v.size();
                                final Integer totalNumbers = rs.getTotalNumbers();
                                final String endpointAddress;
                                if (rs.subscribedServer!=null)
                                {
                                    endpointAddress = rs.subscribedServer.EndpointAddress;
                                }
                                else
                                {
                                    endpointAddress = "";
                                }
                                acivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Resources r = getResources();
                                        showMessage(endpointAddress + r.getString(R.string.received) + " "
                                                + String.valueOf(size) + " " + r.getString(R.string.from) + " " + String.valueOf(totalNumbers));
                                    }
                                });
                            }

                            if (alarmExpandableAdapter!=null)
                            {
                                alarmExpandableAdapter.UpdateItems(rs);
                                alarmExpandableAdapter.setFilterObjects(null, _menuItemSearch);
                            }

                            acivity.lastState.addCurrents(rs);
                        }

                    }
                    // Была ошибка
                    else if (status == STATUS_ERROR) {
                        final String result = intent.getStringExtra(getResources().getString(R.string.wcf_result));
                        acivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showMessage(result);
                            }
                        });

                    }
                    //Подтверждение что аварии квитированы
                    else if (status == STATUS_ADDITIONAL)
                    {
                        acivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showMessage(getResources().getString(R.string.alarm_success_ack));
                            }
                        });
                    }

                    if (additional > 0)isNeedAdditionalRequest = true;
                }
                catch (final Exception ex)
                {
                    acivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage(ex.getMessage());
                        }
                    });
                }
                finally {
                    boolean lastStatus = intent.getBooleanExtra(PARAM_LASTSTATUS, true);
                    if (lastStatus)
                    {
                        if (isNeedAdditionalRequest)
                        {
                            acivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showMessage(getResources().getString(R.string.wait_request));
                                }
                            });
                            GetCurrentAlarmsAsync();
                        }
                        else
                        {
                            AnimateProgress(false);
                        }
                    }
                }
            }
        };

        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(getResources().getString(R.string.BROADCAST_CURRENT));
        // регистрируем (включаем) BroadcastReceiver
        getActivity().registerReceiver(wcfBroadcastReceiver, intFilt);
    }

    private void showMessage(String message)
    {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        main_activity activity = (main_activity) getActivity();
        if(activity!=null && activity.lastState!=null)
        {
            ArrayList<TAlarmsRequest> al =activity.lastState.getCurrents();
            if (al!=null && al.size() > 0)
            {
                alarmExpandableAdapter.clear();
                for (TAlarmsRequest rs : al)
                {
                    alarmExpandableAdapter.UpdateItems(rs);
                }
            }

            alarmExpandableAdapter.setFilterObjects(activity.lastState.getCurrentFilterObjects(), _menuItemSearch);
        }
    }


    private void AnimateProgress(boolean isStart)
    {
        if (isStart)
        {
            linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
        else
        {
            linlaHeaderProgress.setVisibility(View.GONE);
            lvCurrAlarm.onRefreshComplete();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.current_alarms, menu);
        _menuItemSearch = menu.findItem(R.id.menuSearch);
        //Здесь настройка поиска
        SearchView mSearchView = (SearchView)_menuItemSearch.getActionView();
        //Ограничение количества символов поиска
        int etId = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText et = (EditText) mSearchView.findViewById(etId);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(15);
        et.setFilters(filterArray);

        int searchPlateId = mSearchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = mSearchView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(R.drawable.texfield_search_view);
        mSearchView.setOnQueryTextListener(alarmExpandableAdapter);

        menuSearch = menu.findItem(R.id.menuSearch);
        menuRefresh = menu.findItem(R.id.menuRefresh);
    }

    // обработка нажатий элемента меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRefresh:
                GetCurrentAlarmsAsync();
                return true;
            case R.id.menuCurrentObject:
                showCurrentObjectFilter();
                return true;
            case R.id.menuFilter:
                main_activity activity = (main_activity) getActivity();
                if(activity!=null && activity.lastState!=null)
                {
                    Intent intent = new Intent(getActivity(), alarm_filter.class);
                    intent.putExtra("WL", activity.lastState.getWorkflowActivityNamesArray());
                    intent.putExtra("isArchives", false);
                    try
                    {
                        startActivityForResult(intent, 0);
                    }
                    catch (Exception ex){
                        Toast.makeText(this.getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            case R.id.menuSelectAll:
                alarmExpandableAdapter.selectAll(true);
                return true;
            case R.id.menuUnselectAll:
                alarmExpandableAdapter.selectAll(false);
                return true;
            case R.id.menuConfirmSelected:
                _showYesNoDialog(false, getResources().getString(R.string.is_confirm_selected));
                return true;
            case R.id.menuConfirmAll:
                _showYesNoDialog(true, getResources().getString(R.string.is_confirm_all));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Обработка возврата из настроек фильтра и настроек сервера
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        main_activity activity = (main_activity)getActivity();
        if (data!=null && data.getBooleanExtra("Res", false) && activity!=null)
        {
            activity.setBarIcon();
            GetCurrentAlarmsAsync();
        }
    }


    //Запрос текущих аварий
    private void GetCurrentAlarmsAsync() {
        main_activity activity = (main_activity) getActivity();
        if(activity!=null)
        {
            if (activity.lastState.getIsCurrentAlarmsStarted()) return;

            if (alarmExpandableAdapter!=null) alarmExpandableAdapter.clear();
            activity.lastState.clearCurrent();

            AlarmFilterSettings filterSettings = AlarmHelper.getFilterSettings(getActivity());

            if (!AlarmHelper.existsActiveServer(activity))
            {
                showMessage(getResources().getString(R.string.active_server_not_found));
                AnimateProgress(false);
                return;
            }

            AnimateProgress(true);
            activity.myClientService.ALARM_GetCurrentAlarmsAsync(filterSettings, AlarmHelper.ReadSubscribedServer(activity), 0);
        }
    }


    private void ConfirmCurrentAsync(Boolean isAll)
    {
        main_activity activity = (main_activity) getActivity();
        if(activity!=null && activity.myClientService!=null)
        {
            if (activity.lastState.getIsCurrentAlarmsStarted())
            {
                Toast.makeText(activity, activity.getString(R.string.wait_prev_operation), Toast.LENGTH_LONG).show();
                return;
            }

            if (!AlarmHelper.existsActiveServer(activity))
            {
                showMessage(getResources().getString(R.string.active_server_not_found));
                AnimateProgress(false);
                return;
            }

            ArrayList<SubscribedServer> servers = AlarmHelper.ReadSubscribedServer(activity);
            ArrayOfguid alarmList = null;
            if (!isAll)
            {

                alarmList = alarmExpandableAdapter.getSelectedAlarmGuids(servers);
                if (alarmList == null || alarmList.getguid() == null || alarmList.getguid().size() == 0)
                {
                    Toast.makeText(getActivity(), activity.getString(R.string.select_alarms_for_ack), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            AnimateProgress(true);
            activity.myClientService.ALARM_Confirm(alarmList, servers, 0);
        }
    }

    private void showCurrentObjectFilter()
    {
        main_activity activity = (main_activity) getActivity();
        if (activity == null || activity.lastState == null || alarmExpandableAdapter == null) return;

        ArrayList<DbAlarm> _items = alarmExpandableAdapter.getItems();
        if (_items == null || _items.size() == 0)
        {
            showMessage(getResources().getString(R.string.alarm_list_empty));
            return;
        }

        LinearLayout view = (LinearLayout)getLayoutInflater(null).inflate(R.layout.dialog_findable, null);
        ExpandableListView lv = (ExpandableListView)view.findViewById(R.id.listView);
        ArrayList<HierarchyElement> listSource = new ArrayList<HierarchyElement>();

        //Старые объекты для фильтрации
        ArrayList<MultiChoiceElement> filterObjects = alarmExpandableAdapter.getFilterObjects();

        AlarmHelper.iterateMap(EnumMultiChoiceType.Formula, alarmExpandableAdapter.getFormulaDict(), listSource, filterObjects);
        AlarmHelper.iterateMap(EnumMultiChoiceType.BalancePS, alarmExpandableAdapter.getPsBalancePathDict(), listSource, filterObjects);
        AlarmHelper.iterateMap(EnumMultiChoiceType.TI, alarmExpandableAdapter.getTiPathDict(), listSource, filterObjects);
        AlarmHelper.iterateMap(EnumMultiChoiceType.PS, alarmExpandableAdapter.getPsPathDict(), listSource, filterObjects);
        AlarmHelper.iterateMap(EnumMultiChoiceType.Slave61968, alarmExpandableAdapter.getSlaveSystemsDict(), listSource, filterObjects);

        MultiChoiceFindableAdapter adapter = new MultiChoiceFindableAdapter(listSource, (main_activity)getActivity(), lv);
        lv.setIndicatorBounds(10,20);
        lv.setAdapter(adapter);
        if (listSource!=null && listSource.size() > 0)
        {
            lv.expandGroup(0, true);
        }
        SearchView sv = (SearchView)view.findViewById(R.id.searchView);
        //Ограничение количества символов поиска
        int etId = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText et = (EditText) sv.findViewById(etId);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(20);
        et.setFilters(filterArray);

        int searchPlateId = sv.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        sv.findViewById(searchPlateId).setBackgroundResource(R.drawable.textfield_search_default);
        if (sv!=null)
        {
            sv.setOnQueryTextListener(adapter);
        }
        _showDialog(view);
    }

    private void _showDialog(View view)
    {
        //Запрет на повор экрана
        AlarmHelper.disableOrientationChange(getActivity());

        ((TextView)view.findViewById(R.id.tvTitle)).setText(getResources().getString(R.string.objects));
        //Содержимое
        view.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onDialogClosed(dialog, id);
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
        //builder.setCancelable(false);
        AlertDialog dialog =  builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            }
        });
        dialog.show();

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(lp);

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(16);
        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextAppearance(this, R.style.AlertDialogTextAppearance);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextAppearance(this, R.style.AlertDialogTextAppearance);
    }

    private void onDialogClosed(DialogInterface d, int id)
    {
        AlertDialog dialog = (AlertDialog) d;
        if (dialog!=null && alarmExpandableAdapter!=null)
        {
            ExpandableListView lv = (ExpandableListView)dialog.findViewById(R.id.listView);
            if (lv!=null)
            {
                MultiChoiceFindableAdapter adapter = (MultiChoiceFindableAdapter)lv.getExpandableListAdapter();
                if (adapter!=null)
                {
                    ArrayList<MultiChoiceElement> selected = adapter.getSelected();
                    alarmExpandableAdapter.setFilterObjects(selected, _menuItemSearch);
                    main_activity activity = (main_activity) getActivity();
                    if (activity == null || activity.lastState == null || alarmExpandableAdapter == null) return;

                    activity.lastState.setCurrentFilterObjects(selected);
                }
            }
        }
    }

    private void _showYesNoDialog(final Boolean isAl, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ConfirmCurrentAsync(isAl);
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // устанавливаем ее, как содержимое тела диалога
        builder.setMessage(message);
        builder.setCancelable(false);
        AlertDialog dialog =  builder.create();
        dialog.show();

        //dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);
        //dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextAppearance(this, R.style.AlertDialogTextAppearance);
        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);
        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextAppearance(this, R.style.AlertDialogTextAppearance);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // дерегистрируем (выключаем) BroadcastReceiver
        getActivity().unregisterReceiver(wcfBroadcastReceiver);
    }

}
