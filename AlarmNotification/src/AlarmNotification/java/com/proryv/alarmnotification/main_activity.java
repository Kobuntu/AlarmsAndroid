package com.proryv.alarmnotification;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.proryv.alarmnotification.adapters.AlarmHelper;
import com.proryv.alarmnotification.adapters.SubscribedServer;
import com.proryv.alarmnotification.common.Singleton;
import com.proryv.alarmnotification.tabs.TabsAdapter;
import com.proryv.alarmnotification.tabs.archive_alarms;
import com.proryv.alarmnotification.tabs.current_alarms;
import com.proryv.alarmnotification.tabs.server_list;
import com.proryv.alarmnotification.wcf.AlarmClientService;
import com.proryv.alarmnotification.wcf.neurospeech.AlarmFilterSettings;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfKeyValueOfintstring;

import java.util.ArrayList;

public class main_activity extends FragmentActivity {

    final String PARAM_STATUS = "status";
    final String PARAM_RESULT = "result";
    final Integer STATUS_ERROR = 200;
    final Integer STATUS_SUCCESS = 100;
    final Integer STATUS_ADDITIONAL = 300;
    final String PARAM_LASTSTATUS = "isLast";
    BroadcastReceiver wcfBroadcastReceiver;
    BroadcastReceiver wcfBroadcastReceiverOnServerSave;

    private ViewPager mViewPager;
    TabsAdapter mTabsAdapter;
    Intent intent;
    ServiceConnection sConn;
    public AlarmClientService myClientService;
    public boolean setSelectedCurrentTab;
    boolean bound;
    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTabbed(savedInstanceState);
        Init();
    }

    void Init()
    {
        try
        {
            GCMRegistrar.checkDevice(this);
            GCMRegistrar.checkManifest(this);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, getString(R.string.not_registered), Toast.LENGTH_LONG).show();
        }

        GCMIntentService.register(this);

        registerWcfBroadcastReceiver();
        RegisterWcfBroadcastReceiverOnServerSave();
        lastState = Singleton.getInstance().lastState;
        try
        {
            intent = new Intent(this, AlarmClientService.class);
            sConn = new ServiceConnection() {

                public void onServiceConnected(ComponentName name, IBinder binder) {
                    try
                    {
                        myClientService = ((AlarmClientService.AlarmClientBinder) binder).getService();
//                        if (setSelectedCurrentTab)
//                        {
//                            GetCurrentAlarmsAsync();
//                        }

                        requestWorkflowActivityNames();
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplication(), ex.getMessage(), Toast.LENGTH_LONG);
                    }
                }

                public void onServiceDisconnected(ComponentName name) {
                    //Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
                    bound = false;
                }
            };
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG);
        }

        //intent.putExtra(LastState.STATE_NAME, lastState);
        bound = bindService(intent, sConn, BIND_AUTO_CREATE);
        //startService(intent);

    }

    //Запрос текущих аварий
    public boolean GetCurrentAlarmsAsync() {
        if(myClientService==null) return false;

        if (lastState.getIsCurrentAlarmsStarted()) return false;

        lastState.clearCurrent();

        AlarmFilterSettings filterSettings = AlarmHelper.getFilterSettings(this);
        ArrayList<SubscribedServer> subscribedServers = AlarmHelper.ReadSubscribedServer(this);

        boolean isActiveServerFound = false;
        if (subscribedServers != null && subscribedServers.size() != 0)
        {
            for(SubscribedServer server : subscribedServers)
            {
                if (server!=null && server.IsActive)
                {
                    isActiveServerFound = true;
                    break;
                }
            }
        }

        if (!isActiveServerFound)
        {
            Intent intent = new Intent(getResources().getString(R.string.BROADCAST_CURRENT));
            intent.putExtra(getString(R.string.wcf_status), STATUS_ERROR);
            intent.putExtra(getString(R.string.wcf_result), getResources().getString(R.string.active_server_not_found));
            intent.putExtra(PARAM_LASTSTATUS, true);
            sendBroadcast(intent); //Сообщаем о том что нет активных серверов
            return false;
        }

        myClientService.ALARM_GetCurrentAlarmsAsync(filterSettings, subscribedServers, 0);
        return true;
    }

    public void requestWorkflowActivityNames()
    {
        if (lastState==null || lastState.getWorkflowActivityNamesArray() == null
                || lastState.getWorkflowActivityNamesArray().getKeyValueOfintstring() == null
                || lastState.getWorkflowActivityNamesArray().getKeyValueOfintstring().size() == 0)
        {
            ArrayList<SubscribedServer> subscribedServers = AlarmHelper.ReadSubscribedServer(getApplication());

            //Проверка наличия сервера
            if (subscribedServers == null || subscribedServers.size() == 0) return;

            //Надо запросить список процессов
            myClientService.ALARM_GetWorkflowActivityNamesDict(subscribedServers, 0);
        }
    }

    public void setBarIcon()
    {
        if (AlarmHelper.isFilterEnabled(this))
        {
            bar.setIcon(R.drawable.icon_filter_apply);
        }
        else
        {
            bar.setIcon(R.drawable.icon);
        }
    }

    private void setTabbed(Bundle bundle)
    {
        mViewPager = (ViewPager)findViewById(R.id.pager);

        bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //bar.setHomeButtonEnabled(true);
        //bar.setDisplayUseLogoEnabled(true);
        setBarIcon();

        Bundle b = null;
        Intent i = getIntent();
        if (i!=null)
        {
            b = i.getExtras();
            if (b!=null)
            {
                setSelectedCurrentTab = b!=null && b.getBoolean("isFromNotificationBar", false);
                b.putBoolean("isFromNotificationBar", false);
                i.putExtras(b);
            }
            setIntent(i);
        }

        mTabsAdapter = new TabsAdapter(this, mViewPager);
        Resources r = getResources();
        mTabsAdapter.addTab(bar.newTab().setText(r.getString(R.string.curr_tab_name)), current_alarms.class, b, setSelectedCurrentTab);
        mTabsAdapter.addTab(bar.newTab().setText(r.getString(R.string.archive_tab_name)), archive_alarms.class, bundle, !setSelectedCurrentTab);
        mTabsAdapter.addTab(bar.newTab().setText(r.getString(R.string.options_tab_name)), server_list.class, bundle, false);

        //Выбираем закладку сохраненную в предыдущем состоянии
        if (bundle != null)
        {
            bar.selectTab(bar.getTabAt(bundle.getInt("selectedTabIndex", 0)));
        }
        else
        {
            bar.selectTab(bar.getTabAt(0));
        }

    }

//    @Override
//    protected void onNewIntent(Intent intent)
//    {
//        setIntent(null);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Рестарт активити, сохраняем открытую закладку
        outState.putInt("selectedTabIndex", getActionBar().getSelectedNavigationIndex());
    }

    public LastState lastState;

    private boolean  doubleBackToExitPressedOnce;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.on_exit_message), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 3000);
    }


    //Обработка сообщений от сервера
    private void registerWcfBroadcastReceiver()
    {
        wcfBroadcastReceiver = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                try
                {
                    int status = intent.getIntExtra(PARAM_STATUS, 0);

                    // Успешное завершение запроса
                    if (status  == STATUS_SUCCESS) {
                        ArrayOfKeyValueOfintstring rs = intent.getParcelableExtra(PARAM_RESULT);
                        if (rs!=null)
                        {
                            //Получили список процессов
                            if (lastState!=null)
                            {
                                lastState.setWorkflowActivityNamesArray(rs);
                            }
                        }
                    }
                    // Была ошибка
                    else if (status == STATUS_ERROR) {
                        final String result = intent.getStringExtra(PARAM_RESULT);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplication(), result, Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
                catch (final Exception ex)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplication(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };

        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(getResources().getString(R.string.BROADCAST_WORKFLOW));
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(wcfBroadcastReceiver, intFilt);
    }

    ///Сохранение параметров сервера, или регистрация на нем
    private void RegisterWcfBroadcastReceiverOnServerSave()
    {
        wcfBroadcastReceiverOnServerSave = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                try
                {
                    int status = intent.getIntExtra(getResources().getString(R.string.wcf_status), 0);

                    // Успешное завершение запроса
                    if (status  == STATUS_ADDITIONAL) {

                    }
                    // Была ошибка
                    else if (status == STATUS_ERROR) {
                        final String result = intent.getStringExtra(getResources().getString(R.string.wcf_result));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplication(), result, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                catch (final Exception ex)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplication(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                finally {

                }
            }
        };

        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(getResources().getString(R.string.BROADCAST_SERVERSETTINGS));
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(wcfBroadcastReceiverOnServerSave, intFilt);
    }

    @Override
    protected void onDestroy() {
        unbindService(sConn);
        super.onDestroy();

        unregisterReceiver(wcfBroadcastReceiver);
        unregisterReceiver(wcfBroadcastReceiverOnServerSave);

        unbindDrawables(findViewById(R.id.activity_main));
        System.gc();
    }

    //Освобождение ресурсов
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    private void showMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
