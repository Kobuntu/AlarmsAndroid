package com.proryv.alarmnotification.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.common.ObjectSerializer;
import com.proryv.alarmnotification.main_activity;
import com.proryv.alarmnotification.wcf.neurospeech.AlarmFilterSettings;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfint;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by IG on 01.07.13.
 */
public class AlarmHelper {

    public static void setAlarmImage(ImageView image, Integer alarmSeverity)
    {
        switch (alarmSeverity)
        {
            case 0: //нет
                image.setVisibility(View.INVISIBLE);
                break;
            case 1://Нормальные
                image.setImageResource(R.drawable.flag_green);
                break;
            case 2://Предупреждение
                image.setImageResource(R.drawable.flag_yellow);
                break;
            case 3://Критический
                image.setImageResource(R.drawable.flag_red);
                break;
            default://Непонятно что
                image.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public static AlarmFilterSettings getFilterSettings(Context ctx)
    {
        AlarmFilterSettings filterSettings = new AlarmFilterSettings();
        filterSettings.setConfirmed(false);
        try{
            filterSettings.setFilterChanged(false);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            Long defaultDateTime = Calendar.getInstance().getTimeInMillis();
            if (prefs.contains("AlarmDateTimeStart"))
            {
                filterSettings.setAlarmDateTimeStart(new Date(prefs.getLong("AlarmDateTimeStart", defaultDateTime)));
            }

            if (prefs.contains("AlarmDateTimeFinish"))
            {
                filterSettings.setAlarmDateTimeFinish(new Date(prefs.getLong("AlarmDateTimeFinish", defaultDateTime)));
            }

            if (prefs.contains("EventDateTimeStart"))
            {
                filterSettings.setEventDateTimeStart(new Date(prefs.getLong("EventDateTimeStart", defaultDateTime)));
            }
            else
            {
                filterSettings.setEventDateTimeStart(null);
            }

            if (prefs.contains("EventDateTimeFinish"))
            {
                filterSettings.setEventDateTimeFinish(new Date(prefs.getLong("EventDateTimeFinish", defaultDateTime)));
            }
            else
            {
                filterSettings.setEventDateTimeFinish(null);
            }

            if (prefs.contains("AlarmSeverityList"))
            {
                try
                {
                    String[] severityList = prefs.getString("AlarmSeverityList", "0").split(",");
                    if (severityList!=null && severityList.length > 0)
                    {
                        Vector<Integer> v = new Vector<Integer>();
                        for(String s : severityList)
                        {
                          v.add(Integer.parseInt(s));
                        }
                        if (v.size() > 0 && v.size() < 3) filterSettings.setFilterChanged(true);
                        ArrayOfint a = new ArrayOfint();
                        a.setint(v);
                        filterSettings.setAlarmSeverityList(a);
                    }
                }
                catch (Exception ex){}
            }

            if (prefs.contains("Confirmed"))
            {
                filterSettings.setConfirmed(prefs.getBoolean("Confirmed", true));
            }

            if (prefs.contains("WorkflowActivityList"))
            {
                try
                {
                    String[] severityList = prefs.getString("WorkflowActivityList", "0").split(",");
                    if (severityList!=null && severityList.length > 0)
                    {
                        Vector<Integer> v = new Vector<Integer>();
                        for(String s : severityList)
                        {
                            v.add(Integer.parseInt(s));
                        }
                        if (v.size() > 0) filterSettings.setFilterChanged(true);
                        ArrayOfint a = new ArrayOfint();
                        a.setint(v);
                        filterSettings.setWorkflowActivityList(a);
                    }
                }
                catch (Exception ex){}
            }
        }
        catch (Exception ex)
        {


        }

        return filterSettings;
    }


    public static void setFilterSettings(Context ctx, AlarmFilterSettings filterSettings)
    {
        try
        {
            SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
            Long defaultDateTime = Calendar.getInstance().getTimeInMillis();

            if (filterSettings.getAlarmDateTimeStart()!=null)
            {
                prefsEditor.putLong("AlarmDateTimeStart", filterSettings.getAlarmDateTimeStart().getTime());
            }
            else
            {
                prefsEditor.remove("AlarmDateTimeStart");
            }

            if (filterSettings.getAlarmDateTimeFinish()!=null)
            {
                prefsEditor.putLong("AlarmDateTimeFinish", filterSettings.getAlarmDateTimeFinish().getTime());
            }
            else
            {
                prefsEditor.remove("AlarmDateTimeFinish");
            }

            if (filterSettings.getEventDateTimeStart()!=null)
            {
                prefsEditor.putLong("EventDateTimeStart", filterSettings.getEventDateTimeStart().getTime());
            }
            else
            {
                prefsEditor.remove("EventDateTimeStart");
            }

            if (filterSettings.getEventDateTimeFinish()!=null)
            {
                prefsEditor.putLong("EventDateTimeFinish", filterSettings.getEventDateTimeFinish().getTime());
            }
            else
            {
                prefsEditor.remove("EventDateTimeFinish");
            }

            if (filterSettings.getAlarmSeverityList()!=null && filterSettings.getAlarmSeverityList().getint()!=null && filterSettings.getAlarmSeverityList().getint().size() > 0)
            {
                StringBuilder sb = new StringBuilder();
                for(Integer a : filterSettings.getAlarmSeverityList().getint())
                {
                    sb.append(a.toString() + ",");
                }
                prefsEditor.putString("AlarmSeverityList", sb.toString());
            }
            else
            {
                prefsEditor.remove("AlarmSeverityList");
            }

            if (filterSettings.getConfirmed()!=null)
            {
                prefsEditor.putBoolean("Confirmed", filterSettings.getConfirmed());
            }
            else
            {
                prefsEditor.remove("Confirmed");
            }

            if (filterSettings.getWorkflowActivityList()!=null && filterSettings.getWorkflowActivityList().getint()!=null
                    && filterSettings.getWorkflowActivityList().getint().size() > 0)
            {
                StringBuilder sb = new StringBuilder();
                for(Integer a : filterSettings.getWorkflowActivityList().getint())
                {
                    sb.append(a.toString() + ",");
                }
                prefsEditor.putString("WorkflowActivityList", sb.toString());
            }
            else
            {
                prefsEditor.remove("WorkflowActivityList");
            }

            prefsEditor.commit();
        }
        catch (Exception ex)
        {
            Toast.makeText(ctx, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public static void floorCalendar(Calendar calendar)
    {
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
    }

    public static void ceilingCalendar(Calendar calendar)
    {
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
    }


    public static boolean validAddress(final String address){

        InetAddressValidator validator = new InetAddressValidator();
        if (validator.isValidInet4Address(address)) return true;

        DomainValidator validator1 = DomainValidator.getInstance(true);
        return validator1.isValid(address);
    }


    public static final String FILE_NAME = "serverlist";
    public static ArrayList<SubscribedServer> ReadSubscribedServer(Context ctx)
    {
        File file = ctx.getFileStreamPath(FILE_NAME);
        if(!file.exists())
        {
            return null;
        }

        ArrayList<SubscribedServer> result = null;
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(ctx.openFileInput(FILE_NAME)));
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            Object o = ObjectSerializer.deserialize(buffer.toString());
            if (o!=null)
            {
                result = (ArrayList<SubscribedServer>)o;
            }

        } catch (Exception e) {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }

        return result;
    }

    //Проверка на наличие активного сервера
    public static boolean existsActiveServer(main_activity activity)
    {
        ArrayList<SubscribedServer> subscribedServers = AlarmHelper.ReadSubscribedServer(activity);

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

        return isActiveServerFound;
    }

    public static void SaveServersPreferences(Context ctx, ArrayList<SubscribedServer> objects)
    {
        try {
            String s = ObjectSerializer.serialize(objects);

            BufferedWriter writer = null;
            try {
                ctx.deleteFile(FILE_NAME);
                writer = new BufferedWriter(new OutputStreamWriter(ctx.openFileOutput(FILE_NAME, ctx.MODE_APPEND)));
                writer.write(s);
            } catch (Exception e) {
                Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

        } catch (Exception e) {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private static Object syncLock = new Object();

    public static boolean addServerToPreferences(Context ctx, SubscribedServer object)
    {
        if (object == null || object.EndpointAddress == null || object.EndpointAddress.isEmpty()) return false;

        synchronized (syncLock)
        {
            try
            {
                ArrayList<SubscribedServer> objects = ReadSubscribedServer(ctx);
                if (objects == null)
                {
                    objects = new ArrayList<SubscribedServer>();
                }
                else
                {
                    for (SubscribedServer s : objects)
                    {
                        if (s.id == object.id)
                        {
                            Toast.makeText(ctx, ctx.getResources().getString(R.string.server_exists) + ": " + object.EndpointAddress
                                    + "\n" + ctx.getResources().getString(R.string.set_other_param), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }

                objects.add(object);

                SaveServersPreferences(ctx, objects);
            }
            catch (Exception ex)
            {
                Toast.makeText(ctx, ex.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    public static boolean updateServerToPreferences(Context ctx, SubscribedServer object, Long oldId)
    {
        if (object == null) return false;

        synchronized (syncLock)
        {
            try
            {
                ArrayList<SubscribedServer> objects = ReadSubscribedServer(ctx);
                if (objects == null)
                {
                    objects = new ArrayList<SubscribedServer>();
                    objects.add(object);
                }
                else
                {
                    for (SubscribedServer s : objects)
                    {
                        if (s.id == oldId)
                        {
                            s.Password = object.Password;
                            s.IsActive = object.IsActive;
                            s.User = object.User;
                            s.Name = object.Name;
                            s.EndpointAddress = object.EndpointAddress;
                            s.id = object.id;
                            s.Port = object.Port;

                            break;
                        }
                    }
                }

                SaveServersPreferences(ctx, objects);
            }
            catch (Exception ex)
            {
                Toast.makeText(ctx, ex.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    public static boolean deleteServerFromPreferencesById(Context ctx, Long id)
    {
        ArrayList<SubscribedServer> objects = ReadSubscribedServer(ctx);
        if (objects == null || objects.size() == 0) return false;

        synchronized (syncLock)
        {
            try
            {
                for (SubscribedServer s : objects)
                {
                    if (s.id == id)
                    {
                        objects.remove(s);
                        break;
                    }
                }

                SaveServersPreferences(ctx, objects);
            }
            catch (Exception ex)
            {
                Toast.makeText(ctx, ex.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    public static void iterateMap(EnumMultiChoiceType multiChoiceType, Map mp, ArrayList<HierarchyElement> listSource, ArrayList<MultiChoiceElement> filterObjects)
    {
        if (mp == null || mp.size() == 0) return;

        //Сортировщик объектов, сначала сортировка по выбранным, за
        Comparator<MultiChoiceElement> comparator = new Comparator<MultiChoiceElement>() {
            @Override
            public int compare(MultiChoiceElement multiChoiceElement, MultiChoiceElement multiChoiceElement2) {
                if (multiChoiceElement.IsChecked && !multiChoiceElement2.IsChecked) return -1;
                if (!multiChoiceElement.IsChecked && multiChoiceElement2.IsChecked) return 1;

                return multiChoiceElement.Text.compareTo(multiChoiceElement2.Text);
            }
        };

        ArrayList<MultiChoiceElement> al = new ArrayList<MultiChoiceElement>();
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            String val = pairs.getValue().toString();
            String id = pairs.getKey().toString();

            boolean isChecked = false;

            if (filterObjects!=null && filterObjects.size() > 0)
            {
                for (MultiChoiceElement me : filterObjects)
                {
                    if (me.MultiChoiceType == multiChoiceType && me.Id2!=null && me.Id2.equalsIgnoreCase(id))
                    {
                        isChecked = true;
                        break;
                    }
                }
            }
            al.add(new MultiChoiceElement(isChecked, id, val, multiChoiceType));
        }

        Collections.sort(al, comparator);
        listSource.add(new HierarchyElement(multiChoiceType, al));
    }

    public static void disableOrientationChange(Activity activity)
    {
        if (activity == null) return;

        switch (activity.getResources().getConfiguration().orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO){
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                    if(rotation == android.view.Surface.ROTATION_90|| rotation == android.view.Surface.ROTATION_180){
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    } else {
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
                break;

            case Configuration.ORIENTATION_LANDSCAPE:
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO){
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                    if(rotation == android.view.Surface.ROTATION_0 || rotation == android.view.Surface.ROTATION_90){
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    }
                }
                break;
        }
    }

    //Фильтр есть и есть включенные настройки
    public static boolean isFilterEnabled(Context ctx)
    {
        AlarmFilterSettings filterSettings = getFilterSettings(ctx);
        if (filterSettings!=null && filterSettings.getFilterChanged())
        {
            return true;
        }

        return false;
    }
}


