package com.proryv.alarmnotification;

import android.os.Parcel;
import android.os.Parcelable;

import com.proryv.alarmnotification.adapters.MultiChoiceElement;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfDbAlarm;
import com.proryv.alarmnotification.wcf.neurospeech.ArrayOfKeyValueOfintstring;
import com.proryv.alarmnotification.wcf.neurospeech.TAlarmsRequest;

import java.util.ArrayList;

/**
 * Created by IG on 03.07.13.
 */
public class LastState implements Parcelable {

    public final static String STATE_NAME = "com.proryv.alarmnotification.last_state";

    private boolean _IsArchiveAlarmsStarted;
    public synchronized boolean getIsArchiveAlarmsStarted()
    {
        return _IsArchiveAlarmsStarted;
    }
    public synchronized  void setIsArchiveAlarmsStarted(Boolean state)
    {
        _IsArchiveAlarmsStarted = state;
    }
    private ArrayList<TAlarmsRequest> _Archives;
    public synchronized ArrayList<TAlarmsRequest> getArchives()
    {
        return _Archives;
    }
    public synchronized void clearArchives()
    {
        _Archives.clear();
        clearArchiveFilterObjects();
        setLastArchives(null);
    }
    public synchronized void setArchives(ArrayList<TAlarmsRequest> archives)
    {
        _Archives = archives;
    }
    public void addArchives(TAlarmsRequest rs)
    {
        synchronized (_Archives)
        {
            _Archives.add(rs);
        }
    }

    private boolean _IsCurrentAlarmsStarted;
    public synchronized boolean getIsCurrentAlarmsStarted()
    {
        return _IsCurrentAlarmsStarted;
    }
    public synchronized  void setIsCurrentAlarmsStarted(Boolean state)
    {
        _IsCurrentAlarmsStarted = state;
    }

    private ArrayList<TAlarmsRequest> _Currents;
    public synchronized ArrayList<TAlarmsRequest> getCurrents()
    {
        return _Currents;
    }
    public synchronized void setCurrents(ArrayList<TAlarmsRequest> currents)
    {
        _Currents = currents;
    }
    public synchronized void clearCurrent()
    {
        _Currents.clear();
        clearCurrentFilterObjects();
        setLastCurrents(null);
    }
    public void addCurrents(TAlarmsRequest rs)
    {
        synchronized (_Currents)
        {
            _Currents.add(rs);
        }
    }

    ArrayList<MultiChoiceElement> _archiveFilterObjects;
    public synchronized void setArchiveFilterObjects(ArrayList<MultiChoiceElement> archiveFilterObjects)
    {
        _archiveFilterObjects = archiveFilterObjects;
    }
    public synchronized void clearArchiveFilterObjects()
    {
        _archiveFilterObjects = null;
    }
    public synchronized ArrayList<MultiChoiceElement> getArchiveFilterObjects()
    {
        return _archiveFilterObjects;
    }
    public void addArchiveFilterObjects(MultiChoiceElement rs)
    {
        synchronized (_archiveFilterObjects)
        {
            _archiveFilterObjects.add(rs);
        }
    }

    ArrayList<MultiChoiceElement> _currentFilterObjects;
    public synchronized void setCurrentFilterObjects(ArrayList<MultiChoiceElement> currentFilterObjects)
    {
        _currentFilterObjects = currentFilterObjects;
    }
    public synchronized void clearCurrentFilterObjects()
    {
        _currentFilterObjects = null;
    }
    public synchronized ArrayList<MultiChoiceElement> getCurrentFilterObjects()
    {
        return _currentFilterObjects;
    }
    public void addCurrentFilterObjects(MultiChoiceElement rs)
    {
        synchronized (_currentFilterObjects)
        {
            _currentFilterObjects.add(rs);
        }
    }

    private ArrayOfKeyValueOfintstring _workflowActivityNamesArray;
    public synchronized ArrayOfKeyValueOfintstring getWorkflowActivityNamesArray()
    {
        return _workflowActivityNamesArray;
    }
    public synchronized void setWorkflowActivityNamesArray(ArrayOfKeyValueOfintstring workflowActivityNamesArray)
    {
            _workflowActivityNamesArray = workflowActivityNamesArray;
    }

    private TAlarmsRequest _lastArchives;
    public synchronized TAlarmsRequest getLastArchives()
    {
        return _lastArchives;
    }
    public synchronized void setLastArchives(TAlarmsRequest lastArchives)
    {
        _lastArchives = lastArchives;
    }

    private TAlarmsRequest _lastCurrents;
    public synchronized TAlarmsRequest getLastCurrents()
    {
        return _lastCurrents;
    }
    public synchronized void setLastCurrents(TAlarmsRequest lastCurrents)
    {
        _lastCurrents = lastCurrents;
    }

    public LastState()
    {
        setIsArchiveAlarmsStarted(false);
        setIsCurrentAlarmsStarted(false);
        setArchives(new ArrayList<TAlarmsRequest>());
        setCurrents(new ArrayList<TAlarmsRequest>());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        Integer size = getArchives() == null ? 0 : getArchives().size();
        Parcelable[] pList = new Parcelable[size];
        if (getArchives()!=null)
        {
            for (Integer i = 0; i < size; i++)
            {
                pList[i] = getArchives().get(i);
            }
        }
        parcel.writeParcelableArray(pList, flag);
        boolean[] b = new boolean[]{getIsArchiveAlarmsStarted(), getIsCurrentAlarmsStarted()};
        parcel.writeBooleanArray(b);
        parcel.writeParcelable(getWorkflowActivityNamesArray(), flag);

        size = getCurrents() == null ? 0 : getCurrents().size();
        pList = new Parcelable[size];
        if (getCurrents()!=null)
        {
            for (Integer i = 0; i < size; i++)
            {
                pList[i] = getCurrents().get(i);
            }
        }
        parcel.writeParcelableArray(pList, flag);

        size = getCurrentFilterObjects() == null ? 0 : getCurrentFilterObjects().size();
        pList = new Parcelable[size];
        if (getCurrentFilterObjects()!=null)
        {
            for (Integer i = 0; i < size; i++)
            {
                pList[i] = getCurrentFilterObjects().get(i);
            }
        }
        parcel.writeParcelableArray(pList, flag);

        size = getArchiveFilterObjects() == null ? 0 : getArchiveFilterObjects().size();
        pList = new Parcelable[size];
        if (getArchiveFilterObjects()!=null)
        {
            for (Integer i = 0; i < size; i++)
            {
                pList[i] = getArchiveFilterObjects().get(i);
            }
        }
        parcel.writeParcelableArray(pList, flag);
    }

    public static final Parcelable.Creator<LastState> CREATOR = new Parcelable.Creator<LastState>() {
        // распаковываем объект из Parcel
        public LastState createFromParcel(Parcel in) {
            return new LastState(in);
        }

        public LastState[] newArray(int size) {
            return new LastState[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private LastState(Parcel parcel) {
        Parcelable[] pList =parcel.readParcelableArray(ArrayOfDbAlarm.class.getClassLoader());
        if (pList !=null)
        {
            setArchives(new ArrayList<TAlarmsRequest>());
            for (Integer i = 0; i < pList.length; i++)
            {
                addArchives((TAlarmsRequest)pList[i]);
            }
        }
        boolean[] b = new boolean[2];
        parcel.readBooleanArray(b);
        setIsArchiveAlarmsStarted(b[0]);
        setIsCurrentAlarmsStarted(b[1]);
        setWorkflowActivityNamesArray((ArrayOfKeyValueOfintstring)parcel.readParcelable(ArrayOfKeyValueOfintstring.class.getClassLoader()));

        pList = parcel.readParcelableArray(ArrayOfDbAlarm.class.getClassLoader());
        if (pList !=null)
        {
            setCurrents(new ArrayList<TAlarmsRequest>());
            for (Integer i = 0; i < pList.length; i++)
            {
                addCurrents((TAlarmsRequest)pList[i]);
            }
        }

        pList = parcel.readParcelableArray(MultiChoiceElement.class.getClassLoader());
        if (pList !=null)
        {
            setCurrentFilterObjects(new ArrayList<MultiChoiceElement>());
            for (Integer i = 0; i < pList.length; i++)
            {
                addCurrentFilterObjects((MultiChoiceElement)pList[i]);
            }
        }

        pList = parcel.readParcelableArray(MultiChoiceElement.class.getClassLoader());
        if (pList !=null)
        {
            setArchiveFilterObjects(new ArrayList<MultiChoiceElement>());
            for (Integer i = 0; i < pList.length; i++)
            {
                addArchiveFilterObjects((MultiChoiceElement)pList[i]);
            }
        }
    }
}
