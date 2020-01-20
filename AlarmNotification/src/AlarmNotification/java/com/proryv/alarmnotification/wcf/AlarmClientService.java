package com.proryv.alarmnotification.wcf;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;

import com.neurospeech.wsclient.SoapFaultException;
import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.adapters.MultiChoiceElement;
import com.proryv.alarmnotification.adapters.SubscribedServer;
import com.proryv.alarmnotification.common.Singleton;
import com.proryv.alarmnotification.wcf.neurospeech.AlarmFilterSettings;
import com.proryv.alarmnotification.wcf.neurospeech.AlarmsServiceAsync;
import com.proryv.alarmnotification.wcf.neurospeech.AlarmsService_RegisterDeviceResponse;
import com.proryv.alarmnotification.wcf.neurospeech.AlarmsService_SaveDeviceParamsResponse;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfKeyValueOfintstring;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfguid;
import com.proryv.alarmnotification.wcf.neurospeech.TAlarmsRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Ig on 29.06.13.
 */
//Сервис обрабатывающий запросы WCF
public class AlarmClientService  extends Service {

    AlarmClientBinder binder = new AlarmClientBinder();
    //private final int ARCH_COUNT = 500;//Запрос аварий за один раз
    final String PARAM_LASTSTATUS = "isLast";
    final Integer STATUS_ERROR = 200;
    final Integer STATUS_SUCCESS = 100;
    final Integer STATUS_ADDITIONAL = 300;

    final Integer TIMEOUT = 50000;

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return binder;
    }

    //Асинхронный запрос архивных аварий
    public void ALARM_GetArchiveAlarmsAsync(final Date dtStart, final Date dtEnd, final AlarmFilterSettings filter,
                                            final ArrayList<SubscribedServer> subscribedServers, final int i) {

        Singleton.getInstance().lastState.setIsArchiveAlarmsStarted(true);

        String broadcastAction = getResources().getString(R.string.BROADCAST_ARCHIVE);

        if (subscribedServers == null || subscribedServers.size() == 0)
        {
            _onError(true, new Exception(getResources().getString(R.string.servers_not_found)), broadcastAction, "");
            Singleton.getInstance().lastState.setIsArchiveAlarmsStarted(false);
            return;
        }

        final SubscribedServer subscribedServer = subscribedServers.get(i);
        if (!subscribedServer.IsActive)
        {
            if (i < subscribedServers.size() - 1)
            {
                ALARM_GetArchiveAlarmsAsync(dtStart, dtEnd, filter, subscribedServers, i + 1);
            }
            else
            {
                _onSuccess(true, null, broadcastAction); //Результат в таблицу
                Singleton.getInstance().lastState.setIsArchiveAlarmsStarted(false);
            }
            return;
        }

        try
        {
            final String ea = subscribedServer.getEndpointAddress();
            AlarmsServiceAsync alarmService = new AlarmsServiceAsync();
            alarmService.setConnectionTimeout(TIMEOUT);
            alarmService.setSocketTimeout(TIMEOUT);
            alarmService.setBaseUrl(ea);
            alarmService.ALARM_GetArchiveAlarms(subscribedServer.User, subscribedServer.Password, getResources().getInteger(R.integer.arch_count), dtStart, dtEnd, filter,
                    alarmService.new ALARM_GetArchiveAlarmsResultHandler()  {
                        public void onResult (TAlarmsRequest rs){
                            rs.subscribedServer = subscribedServer;
                            boolean isLast = i >= subscribedServers.size() - 1;
                            Singleton.getInstance().lastState.setLastArchives(rs);
                            _onSuccess(isLast, null, getResources().getString(R.string.BROADCAST_ARCHIVE)); //Результат в таблицу
                            if (!isLast)
                            {
                                ALARM_GetArchiveAlarmsAsync(dtStart, dtEnd, filter, subscribedServers,  i + 1); //Следующий сервер
                            }
                            else
                            {
                                Singleton.getInstance().lastState.setIsArchiveAlarmsStarted(false);
                            }
                        }

                        public void onError(Exception ex){
                            boolean isLast = i >= subscribedServers.size() - 1;
                            _onError(isLast, ex, getResources().getString(R.string.BROADCAST_ARCHIVE), ea);
                            if (!isLast)
                            {
                                ALARM_GetArchiveAlarmsAsync(dtStart, dtEnd, filter, subscribedServers,  i + 1); //Следующий сервер
                            }
                            else
                            {
                                Singleton.getInstance().lastState.setIsArchiveAlarmsStarted(false);
                            }
                        }
                    }
            );
        }
        catch (Exception ex)
        {
            _onError(true, ex, broadcastAction, "");
            Singleton.getInstance().lastState.setIsArchiveAlarmsStarted(false);
        }
    }


    //Асинхронный запрос списка процессов
    public void ALARM_GetWorkflowActivityNamesDict(final ArrayList<SubscribedServer> subscribedServers, final int i)
    {
        try
        {
            SubscribedServer subscribedServer = subscribedServers.get(i);

            final String ea = subscribedServer.getEndpointAddress();
            AlarmsServiceAsync alarmService = new AlarmsServiceAsync();
            alarmService.setConnectionTimeout(TIMEOUT);
            alarmService.setSocketTimeout(TIMEOUT);
            alarmService.setBaseUrl(ea);
            alarmService.ALARM_GetWorkflowActivityNamesDict(
                    alarmService.new ALARM_GetWorkflowActivityNamesDictResultHandler()  {
                        public void onResult (ArrayOfKeyValueOfintstring rs){
                            //Сортировка
                            Comparator<MultiChoiceElement> comparator = new Comparator<MultiChoiceElement>() {
                                @Override
                                public int compare(MultiChoiceElement multiChoiceElement, MultiChoiceElement multiChoiceElement2) {
                                    if (multiChoiceElement.IsChecked && !multiChoiceElement2.IsChecked) return -1;
                                    if (!multiChoiceElement.IsChecked && multiChoiceElement2.IsChecked) return 1;

                                    return multiChoiceElement.Text.compareTo(multiChoiceElement2.Text);
                                }
                            };

                            boolean isLast = i >= subscribedServers.size() - 1;
                            _onSuccess(isLast, rs, getResources().getString(R.string.BROADCAST_WORKFLOW));
                            if (!isLast)
                            {
                                ALARM_GetWorkflowActivityNamesDict(subscribedServers,  i + 1); //Следующий сервер
                            }
                        }

                        public void onError(Exception ex){
                            boolean isLast = i >= subscribedServers.size() - 1;
                            //_onError(isLast, ex, getResources().getString(R.string.BROADCAST_WORKFLOW), ea);
                            if (!isLast)
                            {
                                ALARM_GetWorkflowActivityNamesDict(subscribedServers,  i + 1); //Следующий сервер
                            }
                        }
                    }
            );
        }
        catch (Exception ex)
        {
            //_onError(true, ex, getResources().getString(R.string.BROADCAST_WORKFLOW), "");
        }
    }

    //Асинхронный запрос текущих аварий
    public void ALARM_GetCurrentAlarmsAsync(final AlarmFilterSettings filter,
                                            final ArrayList<SubscribedServer> subscribedServers, final int i) {

        Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(true);

        String broadcastAction = getResources().getString(R.string.BROADCAST_CURRENT);

        if (subscribedServers == null || subscribedServers.size() == 0)
        {
            _onError(true, new Exception(getResources().getString(R.string.servers_not_found)), broadcastAction, "");
            Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(false);
            return;
        }

        final SubscribedServer subscribedServer = subscribedServers.get(i);
        if (!subscribedServer.IsActive)
        {
            if (i < subscribedServers.size() - 1)
            {
                ALARM_GetCurrentAlarmsAsync(filter, subscribedServers, i + 1);
            }
            else
            {
                _onSuccess(true, null, broadcastAction); //Результат в таблицу
                Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(false);
            }
            return;
        }

        try
        {
            final String ea = subscribedServer.getEndpointAddress();
            AlarmsServiceAsync alarmService = new AlarmsServiceAsync();
            alarmService.setConnectionTimeout(TIMEOUT);
            alarmService.setSocketTimeout(TIMEOUT);
            alarmService.setBaseUrl(ea);
            alarmService.ALARM_GetCurrentAlarms(subscribedServer.User, subscribedServer.Password, getResources().getInteger(R.integer.arch_count), filter,
                    alarmService.new ALARM_GetCurrentAlarmsResultHandler() {
                        public void onResult(TAlarmsRequest rs) {
                            rs.subscribedServer = subscribedServer;
                            boolean isLast = i >= subscribedServers.size() - 1;
                            Singleton.getInstance().lastState.setLastCurrents(rs);
                            _onSuccess(isLast, null, getResources().getString(R.string.BROADCAST_CURRENT)); //Результат в таблицу
                            if (!isLast) {
                                ALARM_GetCurrentAlarmsAsync(filter, subscribedServers, i + 1); //Следующий сервер
                            }
                            else
                            {
                                Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(false);
                            }
                        }

                        public void onError(Exception ex) {
                            boolean isLast = i >= subscribedServers.size() - 1;
                            _onError(isLast, ex, getResources().getString(R.string.BROADCAST_CURRENT), ea);
                            if (!isLast) {
                                ALARM_GetCurrentAlarmsAsync(filter, subscribedServers, i + 1); //Следующий сервер
                            }
                            else
                            {
                                Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(false);
                            }
                        }
                    }
            );
        }
        catch (Exception ex)
        {
            _onError(true, ex, broadcastAction, "");
            Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(false);
        }
    }

    //Подтверждение списка аварий, или все аварии (alarmList == null)
    public void ALARM_Confirm(final ArrayOfguid alarmList, final ArrayList<SubscribedServer> subscribedServers, final int i)
    {
        Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(true);

        String broadcastAction = getResources().getString(R.string.BROADCAST_CURRENT);

        if (subscribedServers == null || subscribedServers.size() == 0)
        {
            _onError(true, new Exception(getResources().getString(R.string.servers_not_found)), broadcastAction, "", 1);
            Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(false);
            return;
        }

        final SubscribedServer subscribedServer = subscribedServers.get(i);
        if (!subscribedServer.IsActive)
        {
            if (i < subscribedServers.size() - 1)
            {
                ALARM_Confirm(alarmList, subscribedServers, i + 1);
            }
            else
            {
                _onSuccess(true,broadcastAction); //Результат в таблицу
                Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(false);
            }
            return;
        }

        try
        {
            final String ea = subscribedServer.getEndpointAddress();
            AlarmsServiceAsync alarmService = new AlarmsServiceAsync();
            alarmService.setConnectionTimeout(TIMEOUT);
            alarmService.setSocketTimeout(TIMEOUT);
            alarmService.setBaseUrl(ea);
            alarmService.ALARM_Confirm(alarmList, subscribedServer.User, subscribedServer.Password, 0,
                    alarmService.new ALARM_ConfirmResultHandler() {
                        public void onResult(String rs) {
                            boolean isLast = i >= subscribedServers.size() - 1;
                            if (rs != null && !rs.isEmpty())
                            {
                                _onError(isLast, new Exception(rs), getResources().getString(R.string.BROADCAST_CURRENT), ea, 1);
                            }
                            else
                            {
                                _onSuccess(isLast, getResources().getString(R.string.BROADCAST_CURRENT));
                            }
                            if (!isLast) {
                                ALARM_Confirm(alarmList, subscribedServers, i + 1); //Следующий сервер
                            }
                            else
                            {
                                Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(false);
                            }
                        }

                        public void onError(Exception ex) {
                            boolean isLast = i >= subscribedServers.size() - 1;
                            _onError(isLast, ex, getResources().getString(R.string.BROADCAST_CURRENT), ea, 1);
                            if (!isLast) {
                                ALARM_Confirm(alarmList, subscribedServers, i + 1); //Следующий сервер
                            }
                            else
                            {
                                Singleton.getInstance().lastState.setIsCurrentAlarmsStarted(false);
                            }
                        }
                    }
            );
        }
        catch (Exception ex)
        {
            _onError(true, ex, broadcastAction, "", 1);
        }
    }

    public void ALARM_RegisterDevice(String endPointAdress, String registrationId, boolean isActive, String userName, String password)
    {
        final String ea = endPointAdress;
        try
        {
            AlarmsServiceAsync alarmService = new AlarmsServiceAsync();
            alarmService.setConnectionTimeout(TIMEOUT);
            alarmService.setSocketTimeout(TIMEOUT);
            alarmService.setBaseUrl(ea);
            alarmService.RegisterDevice(registrationId, "android", isActive,userName, password,
                    alarmService.new RegisterDeviceResultHandler() {
                        public void onResult(AlarmsService_RegisterDeviceResponse response) {
                            _onSuccess(true, getResources().getString(R.string.BROADCAST_SERVERSETTINGS));
                        }

                        public void onError(Exception ex) {
                            _onError(true, new Exception(getString(R.string.reg_error)), getResources().getString(R.string.BROADCAST_SERVERSETTINGS), ea);
                        }
                    }
            );
        }
        catch (Exception ex)
        {
            _onError(true, ex, getResources().getString(R.string.BROADCAST_SERVERSETTINGS), ea);
        }
    }

    public void ALARM_SaveDeviceParams(String endPointAdress, String registrationId, boolean isActive, String userName, String password)
    {
        final String ea = endPointAdress;
        try
        {
            AlarmsServiceAsync alarmService = new AlarmsServiceAsync();
            alarmService.setConnectionTimeout(TIMEOUT);
            alarmService.setSocketTimeout(TIMEOUT);
            alarmService.setBaseUrl(ea);
            alarmService.SaveDeviceParams(registrationId, "android", isActive, userName, password,
                    alarmService.new SaveDeviceParamsResultHandler() {
                        public void onResult(AlarmsService_SaveDeviceParamsResponse response) {
                            _onSuccess(true, getResources().getString(R.string.BROADCAST_SERVERSETTINGS));
                        }

                        public void onError(Exception ex) {
                            _onError(true, new Exception(getString(R.string.save_params_error)), getResources().getString(R.string.BROADCAST_SERVERSETTINGS), ea);
                        }
                    }
            );
        }
        catch (Exception ex)
        {
            _onError(true, ex, getResources().getString(R.string.BROADCAST_SERVERSETTINGS), ea);
        }
    }

    private void _onSuccess(boolean isLast, Parcelable rs, String broadcastAction)
    {
        Intent intent = new Intent(broadcastAction);
        intent.putExtra(getString(R.string.wcf_status), STATUS_SUCCESS);
        intent.putExtra(getString(R.string.wcf_result),rs);
        intent.putExtra(PARAM_LASTSTATUS, isLast);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1000), pendingIntent);
        sendBroadcast(intent); //Возврат результата всем подписанным
    }

    private void _onError(boolean isLast, Exception ex, String broadcastAction, String endpoint)
    {
        _onError(isLast, ex, broadcastAction, endpoint, 0);
    }

    private void _onError(boolean isLast, Exception ex, String broadcastAction, String endpoint, int status)
    {
        String message;
        if (ex instanceof SoapFaultException)
        {
            SoapFaultException soapFaultException =(SoapFaultException)ex;
            if (soapFaultException.getFaultCode().compareTo("a:InternalServiceFault") == 0)
            {
                message = soapFaultException.getFaultString();
            }
            else
            {
                message = getResources().getString(R.string.servers_not_received);
            }
        }
        else
        {
            message = getRootCause(ex).getMessage();
        }

        if (message == null || message.isEmpty())
        {
            message = getResources().getString(R.string.request_fault);
        }

        Intent intent = new Intent(broadcastAction);
        intent.putExtra(getString(R.string.wcf_status), STATUS_ERROR);
        intent.putExtra(getString(R.string.wcf_additional), status);
        intent.putExtra(getString(R.string.wcf_result), endpoint + ": " + message);
        intent.putExtra(PARAM_LASTSTATUS, isLast);
        sendBroadcast(intent); //Сообщаем о результате
    }

    private void _onSuccess(boolean isLast, String broadcastAction)
    {
        Intent intent = new Intent(broadcastAction);
        intent.putExtra(getString(R.string.wcf_status), STATUS_ADDITIONAL);
        intent.putExtra(getString(R.string.wcf_additional), 1);
        intent.putExtra(PARAM_LASTSTATUS, isLast);
        sendBroadcast(intent); //Возврат результата всем подписанным
    }

    public static Throwable getRootCause(Throwable throwable) {
        if (throwable.getCause() != null)
            return getRootCause(throwable.getCause());

        return throwable;
    }

    public class AlarmClientBinder extends Binder {
        public AlarmClientService getService() {
            return AlarmClientService.this;
        }
    }

    enum enumRequestType
    {
        ArchiveAlarms
    }
}


