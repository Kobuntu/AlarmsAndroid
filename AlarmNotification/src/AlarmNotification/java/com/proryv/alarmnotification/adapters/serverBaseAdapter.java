package com.proryv.alarmnotification.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.proryv.alarmnotification.GCMIntentService;
import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.wcf.AlarmClientService;

import java.util.ArrayList;

/**
 * Created by Ig on 27.06.13.
 */
public class serverBaseAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<SubscribedServer> objects;
    AlarmClientService myClientService;
    public static final String FILE_NAME = "serverlist";

    public serverBaseAdapter(Context context, AlarmClientService myClientService) {
        ctx = context;
        this.myClientService = myClientService;
        //ctx.deleteFile(FILE_NAME);
        LoadServerPreferences();
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        SubscribedServer item = objects.get(position);
        if (item!=null) return item.id;

        return position;
    }

    // сервер по позиции
    public SubscribedServer getServerByPosition(int position) {
        return ((SubscribedServer) getItem(position));
    }

    // сервер по номеру
    public SubscribedServer getServerById(long id) {
        if (objects!= null && objects.size() > 0)
        {
            for (SubscribedServer s : objects)
            {
                if (s.id == id)
                {
                    return s;
                }
            }
        }

        return null;
    }

    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // Сервер активен
            buttonView.setEnabled(false);
            SubscribedServer subscribedServer =(SubscribedServer) buttonView.getTag();
            if (subscribedServer!=null)
            {
                subscribedServer.IsActive = isChecked;
                if (myClientService!=null)
                {
                    myClientService.ALARM_SaveDeviceParams(subscribedServer.getEndpointAddress(), GCMIntentService.REGISTER_ID, subscribedServer.IsActive, subscribedServer.User, subscribedServer.Password);
                }

                SaveServersPreferences();
            }
            buttonView.setEnabled(true);
        }
    };

    public void SaveServersPreferences()
    {
        AlarmHelper.SaveServersPreferences(ctx, objects);
    }

    public void LoadServerPreferences()
    {
        objects = AlarmHelper.ReadSubscribedServer(ctx);
        if (objects == null)
        {
            objects = new ArrayList<SubscribedServer>();
//            objects.add(new SubscribedServer("test", "192.168.0.39", true, "admin", "adminproryv7789")); //adminproryv7789  91.214.98.11
            SaveServersPreferences();
        }
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.server_item, parent, false);
        }

        SubscribedServer s = getServerByPosition(position);

        ((TextView)view.findViewById(R.id.tvSimpleServerName)).setText(s.Name);
        ((TextView) view.findViewById(R.id.tvSimpleServerEndpointAddress)).setText(s.EndpointAddress);
        CompoundButton bt = (CompoundButton)view.findViewById(R.id.tbSimpleServerIsActive);

        bt.setTag(s);
        bt.setOnCheckedChangeListener(null);
        bt.setChecked(s.IsActive);
        bt.setOnCheckedChangeListener(myCheckChangList);

        return view;
    }

    public void deleteServer(long id)
    {
        SubscribedServer s =getServerById(id);
        if (s!=null)
        {
            AlarmHelper.deleteServerFromPreferencesById(ctx, id);
            objects.remove(s);
            notifyDataSetChanged();
        }
    }

    public void update()
    {
        LoadServerPreferences();
        notifyDataSetChanged();
    }
}
