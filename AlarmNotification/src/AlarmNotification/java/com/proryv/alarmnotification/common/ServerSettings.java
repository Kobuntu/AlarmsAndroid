package com.proryv.alarmnotification.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.proryv.alarmnotification.GCMIntentService;
import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.adapters.AlarmHelper;
import com.proryv.alarmnotification.adapters.SubscribedServer;
import com.proryv.alarmnotification.wcf.AlarmClientService;

/**
 * Created by Ig on 14.07.13.
 */
public class ServerSettings  extends Activity implements CompoundButton.OnCheckedChangeListener, TextWatcher {

    SubscribedServer server;
    EditText editAdressArray;
    boolean isNew;
    Long oldId;
    BroadcastReceiver wcfBroadcastReceiverOnServerSave;
    public AlarmClientService myClientService;
    ServiceConnection sConn;

    final Integer STATUS_ERROR = 200;
    final Integer STATUS_SUCCESS = 100;

    boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_settings);

        configureView();
        initWCFService();

        if (savedInstanceState!=null)
        {
            isChanged = savedInstanceState.getBoolean("isChanged", false);
        }
    }

    private void configureView()
    {
        Intent intent = getIntent();
        if (intent!=null)
        {
            server = intent.getParcelableExtra("server");
        }

        EditText et =(EditText)findViewById( R.id.editServerAdress);

        //InputFilter[] filters = new InputFilter[]{ createInputFilteIp()};
        //et.setFilters(filters);

        ((CompoundButton)findViewById(R.id.checkBox1)).setOnCheckedChangeListener(this);

        //Редактирование существующего сервера
        if (server!=null)
        {
            isNew = false;
            oldId = server.id;

            ((EditText)findViewById(R.id.editServerName)).setText(server.Name);
            if (server.EndpointAddress!=null && !server.EndpointAddress.isEmpty())
            {
                et.setText(server.EndpointAddress);
                et.addTextChangedListener(this);
            }

            CompoundButton cb = (CompoundButton)findViewById(R.id.toggleIsActive);
            cb.setChecked(server.IsActive);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isChanged = true;
                }
            });

            ((EditText)findViewById(R.id.editUserName)).setText(server.User);
            ((EditText)findViewById(R.id.editPassword)).setText(server.Password);
        }
        else
        {
            isNew = true;
        }

        ((EditText)findViewById(R.id.editServerName)).addTextChangedListener(this);
        ((EditText)findViewById(R.id.editUserName)).addTextChangedListener(this);
        ((EditText)findViewById(R.id.editPassword)).addTextChangedListener(this);
    }

    private void initWCFService()
    {
        Intent intent = null;
        try
        {
            intent = new Intent(this, AlarmClientService.class);
            sConn = new ServiceConnection() {

                public void onServiceConnected(ComponentName name, IBinder binder) {
                    try
                    {
                        myClientService = ((AlarmClientService.AlarmClientBinder) binder).getService();
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplication(), ex.getMessage(), Toast.LENGTH_LONG);
                    }
                }

                public void onServiceDisconnected(ComponentName name) {

                }
            };
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG);
        }

        if (intent!=null)
        {
            bindService(intent, sConn, BIND_AUTO_CREATE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    private InputFilter createInputFilteIp()
    {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                    if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i=0; i<splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }
        };
    }

    // обработка нажатий меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSave:
                if (saveServer())
                {
                    finish();
                }
                return true;
            case R.id.menuCancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean saveServer()
    {
        String name = ((EditText)findViewById(R.id.editServerName)).getText().toString();
        if (name == null || name.isEmpty())
        {
            showMessage(getString(R.string.server_name_not_valid));
            return false;
        }

        boolean isActive = ((CompoundButton)findViewById(R.id.toggleIsActive)).isChecked();
        String user = ((EditText)findViewById(R.id.editUserName)).getText().toString();
        String password = ((EditText)findViewById(R.id.editPassword)).getText().toString();

        EditText et =(EditText)findViewById( R.id.editServerAdress);

        String endpointAddress = et.getText().toString();

        if (endpointAddress == null || endpointAddress.isEmpty())
        {
            showMessage(getString(R.string.server_address_not_valid));
            return false;
        }

        //Выделяем порт
        int port = 8017;
        int lastD = endpointAddress.lastIndexOf(':');
        if (lastD > 0)
        {
            String p = endpointAddress.substring(lastD + 1);

            if (!p.isEmpty())
            {
                try
                {
                    port = Integer.parseInt(p);
                    endpointAddress = endpointAddress.substring(0, lastD);
                }
                catch (Exception ex)
                {
                    showMessage(getString(R.string.port_not_correct));
                    return false;
                }
            }
        }

        if (port < 1 | port > 65535)
        {
            showMessage(getString(R.string.port_not_correct));
            return false;
        }

        server = new SubscribedServer(name, endpointAddress, isActive, user, password, port);

        Intent intent = new Intent();
        intent.putExtra("needUpdate", true);
        setResult(RESULT_OK, intent);

        boolean prefResult;
        try
        {
            if (isNew)
            {
                prefResult = AlarmHelper.addServerToPreferences(this, server);
                myClientService.ALARM_RegisterDevice(server.getEndpointAddress(), GCMIntentService.REGISTER_ID, server.IsActive, server.User, server.Password);
            }
            else
            {
                prefResult = AlarmHelper.updateServerToPreferences(this, server, oldId);
                myClientService.ALARM_SaveDeviceParams(server.getEndpointAddress(), GCMIntentService.REGISTER_ID, server.IsActive, server.User, server.Password);
            }
        }
        catch (Exception ex)
        {
            showMessage(ex.getMessage());
            return false;
        }

        return prefResult;
    }

    @Override
    public void onBackPressed()
    {
        if (!isChanged)
        {
            finish();
            return;
        }

        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.save_server_settings))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (saveServer())
                        {
                            finish();
                        }
                    }
                }).
                setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        EditText editPassword = (EditText)findViewById(R.id.editPassword);

        if (isChecked)
        {
            editPassword.setTransformationMethod(null);
        }
        else
        {
            editPassword.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    private void showMessage(String message)
    {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 150);
        toast.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isChanged", isChanged);
    }

    @Override
    protected void onDestroy() {
        unbindService(sConn);
        super.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        isChanged = true;
    }
}
