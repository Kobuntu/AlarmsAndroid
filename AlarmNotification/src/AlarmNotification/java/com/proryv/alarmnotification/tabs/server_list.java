package com.proryv.alarmnotification.tabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.adapters.SubscribedServer;
import com.proryv.alarmnotification.adapters.serverBaseAdapter;
import com.proryv.alarmnotification.common.ServerSettings;
import com.proryv.alarmnotification.main_activity;
import com.proryv.alarmnotification.wcf.AlarmClientService;

/**
 * Created by Ig on 27.06.13.
 */
public class server_list extends Fragment {

    serverBaseAdapter serverAdapter;

    private final int NEW_SERVER = 0;
    private final int UPDATE_SERVER = 1;
    private final int DELETE = 1;
    private final int UPDATE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.server_list, container, false);

        AlarmClientService myClientService = null;
        main_activity activity = (main_activity) getActivity();
        if (activity!=null)
        {
            myClientService = activity.myClientService;
        }
        serverAdapter = new serverBaseAdapter(getActivity(), myClientService);

        // настраиваем список
        ListView lvMain = (ListView) v.findViewById(R.id.lvServers);
        lvMain.setAdapter(serverAdapter);

        registerForContextMenu(lvMain);

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.server_list, menu);
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAddServer:
                newServerRegister();
                return true;
            case R.id.menuAbout:
                showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newServerRegister()
    {
        Intent intent = new Intent(getActivity(), ServerSettings.class);
        startActivityForResult(intent, NEW_SERVER);
    }

    //Обработка возврата из создания (настроек) сервера
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null && data.getBooleanExtra("needUpdate", false))
        {
            serverAdapter.update();

            main_activity activity = (main_activity) getActivity();
            if (activity!=null)
            {
               //Если не загружены процессы, надо загрузить
               activity.requestWorkflowActivityNames();
            }
        }
    }

    /// Меню на списке серверов
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE, 0, getResources().getString(R.string.delete));
        menu.add(0, UPDATE, 0, getResources().getString(R.string.change));
    }

    /// Нажали меню
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        // получаем из пункта контекстного меню данные по пункту списка
        AdapterView.AdapterContextMenuInfo acmi =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        if (acmi != null)
        {
            switch (item.getItemId())
            {
                //Удаление
                case DELETE:
                    _showYesNoDialog(getActivity().getString(R.string.you_wona_delete), acmi.id);
                    break;
                case UPDATE:
                    //Форма редактирования сервера
                    SubscribedServer server = serverAdapter.getServerById(acmi.id);
                    if (server!=null)
                    {
                        Intent intent = new Intent(getActivity(), ServerSettings.class);
                        intent.putExtra("server", (Parcelable) server);
                        startActivityForResult(intent, UPDATE_SERVER);
                    }
                    break;
            }
        }

        return super.onContextItemSelected(item);
    }

    private void showAbout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout view = (LinearLayout)getLayoutInflater(null).inflate(R.layout.about, null);

        try {
            String pkg = this.getActivity().getPackageName();
            String mVersionNumber = this.getActivity().getPackageManager().getPackageInfo(pkg, 0).versionName;
            ((TextView)view.findViewById(R.id.tvVersion)).setText("( " + getResources().getString(R.string.version) + " " + mVersionNumber +" )");
        } catch (PackageManager.NameNotFoundException e) {

        }

        builder.setView(view);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        dialog =  builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);

    }

    AlertDialog dialog;
    private void _showYesNoDialog(String message, final long serverId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteServer(serverId);
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
        dialog =  builder.create();
        dialog.show();

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(lp);

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(16);
        //dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextAppearance(this, R.style.AlertDialogTextAppearance);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);
        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextAppearance(this, R.style.AlertDialogTextAppearance);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
    }

    private void deleteServer(long id)
    {
        serverAdapter.deleteServer(id);
    }
}
