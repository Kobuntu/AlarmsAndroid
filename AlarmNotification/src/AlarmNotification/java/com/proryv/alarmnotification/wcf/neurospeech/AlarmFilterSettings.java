package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmFilterSettings extends WSObject implements android.os.Parcelable
{
    private boolean _isFilterChanged;
    public synchronized boolean getFilterChanged()
    {
       return _isFilterChanged;
    }
    public synchronized void setFilterChanged(boolean isFilterChanged)
    {
        _isFilterChanged = isFilterChanged;
    }
    private java.util.Date _AlarmDateTimeFinish;
    public java.util.Date getAlarmDateTimeFinish(){
        return _AlarmDateTimeFinish;
    }
    public void setAlarmDateTimeFinish(java.util.Date value){
        _AlarmDateTimeFinish = value;
    }
    private java.util.Date _AlarmDateTimeStart;
    public java.util.Date getAlarmDateTimeStart(){
        return _AlarmDateTimeStart;
    }
    public void setAlarmDateTimeStart(java.util.Date value){
        if (value!=null) _isFilterChanged = true;
        _AlarmDateTimeStart = value;
    }
    private ArrayOfint _AlarmSeverityList;
    public ArrayOfint getAlarmSeverityList(){
        return _AlarmSeverityList;
    }
    public void setAlarmSeverityList(ArrayOfint value){
        _AlarmSeverityList = value;
    }
    private Boolean _Confirmed;
    public Boolean getConfirmed(){
        return _Confirmed;
    }
    public void setConfirmed(Boolean value){
        _Confirmed = value;
    }
    private java.util.Date _EventDateTimeFinish;
    public java.util.Date getEventDateTimeFinish(){
        return _EventDateTimeFinish;
    }
    public void setEventDateTimeFinish(java.util.Date value){
        if (value!=null) _isFilterChanged = true;
        _EventDateTimeFinish = value;
    }
    private java.util.Date _EventDateTimeStart;
    public java.util.Date getEventDateTimeStart(){
        return _EventDateTimeStart;
    }
    public void setEventDateTimeStart(java.util.Date value){
        if (value!=null) _isFilterChanged = true;
        _EventDateTimeStart = value;
    }
    private ArrayOfint _WorkflowActivityList;
    public ArrayOfint getWorkflowActivityList(){
        return _WorkflowActivityList;
    }
    public void setWorkflowActivityList(ArrayOfint value){
        _WorkflowActivityList = value;
    }

    public static AlarmFilterSettings loadFrom(Element root) throws Exception
    {
        if(root==null){
            return null;
        }
        AlarmFilterSettings result = new AlarmFilterSettings();
        result.load(root);
        return result;
    }


    protected void load(Element root) throws Exception
    {
        this.setAlarmDateTimeFinish(WSHelper.getDate(root,"AlarmDateTimeFinish",false));
        this.setAlarmDateTimeStart(WSHelper.getDate(root,"AlarmDateTimeStart",false));
        this.setAlarmSeverityList(ArrayOfint.loadFrom(WSHelper.getElement(root,"AlarmSeverityList")));
        this.setConfirmed(WSHelper.getBoolean(root,"Confirmed",false));
        this.setEventDateTimeFinish(WSHelper.getDate(root,"EventDateTimeFinish",false));
        this.setEventDateTimeStart(WSHelper.getDate(root,"EventDateTimeStart",false));
        this.setWorkflowActivityList(ArrayOfint.loadFrom(WSHelper.getElement(root,"WorkflowActivityList")));
    }



    public Element toXMLElement(WSHelper ws, Element root)
    {
        Element e = ws.createElement("ns4:AlarmFilterSettings");
        fillXML(ws,e);
        return e;
    }

    public void fillXML(WSHelper ws,Element e)
    {
        ws.addChild(e,"ns4:AlarmDateTimeFinish",ws.stringValueOf(_AlarmDateTimeFinish),false);
        ws.addChild(e,"ns4:AlarmDateTimeStart",ws.stringValueOf(_AlarmDateTimeStart),false);
        if(_AlarmSeverityList != null)
            ws.addChildNode(e, "ns4:AlarmSeverityList",null,_AlarmSeverityList);
        ws.addChild(e,"ns4:Confirmed",(_Confirmed ? "true" : "false"),false);
        ws.addChild(e,"ns4:EventDateTimeFinish",ws.stringValueOf(_EventDateTimeFinish),false);
        ws.addChild(e,"ns4:EventDateTimeStart",ws.stringValueOf(_EventDateTimeStart),false);
        if(_WorkflowActivityList != null)
            ws.addChildNode(e, "ns4:WorkflowActivityList",null,_WorkflowActivityList);
    }
    public int describeContents(){ return 0; }
    public void writeToParcel(android.os.Parcel out, int flags)
    {
        out.writeValue(_AlarmDateTimeFinish);
        out.writeValue(_AlarmDateTimeStart);
        out.writeValue(_AlarmSeverityList);
        out.writeValue(_Confirmed);
        out.writeValue(_EventDateTimeFinish);
        out.writeValue(_EventDateTimeStart);
        out.writeValue(_WorkflowActivityList);
    }
    void readFromParcel(android.os.Parcel in)
    {
        _AlarmDateTimeFinish = (java.util.Date)in.readValue(null);
        _AlarmDateTimeStart = (java.util.Date)in.readValue(null);
        _AlarmSeverityList = (ArrayOfint)in.readValue(null);
        _Confirmed = (Boolean)in.readValue(null);
        _EventDateTimeFinish = (java.util.Date)in.readValue(null);
        _EventDateTimeStart = (java.util.Date)in.readValue(null);
        _WorkflowActivityList = (ArrayOfint)in.readValue(null);
    }
    public static final android.os.Parcelable.Creator<AlarmFilterSettings> CREATOR = new android.os.Parcelable.Creator<AlarmFilterSettings>()
    {
        public AlarmFilterSettings createFromParcel(android.os.Parcel in)
        {
            AlarmFilterSettings tmp = new AlarmFilterSettings();
            tmp.readFromParcel(in);
            return tmp;
        }
        public AlarmFilterSettings[] newArray(int size)
        {
            return new AlarmFilterSettings[size];
        }
    }
            ;

}
