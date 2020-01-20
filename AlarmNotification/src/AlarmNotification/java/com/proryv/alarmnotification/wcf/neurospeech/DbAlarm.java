package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import com.proryv.alarmnotification.adapters.SubscribedServer;

import org.w3c.dom.*;

public class DbAlarm extends WSObject implements android.os.Parcelable
{
    public SubscribedServer subscribedServer;
    public boolean isSelected;

	private java.util.Date _AlarmDateTime;
	public java.util.Date getAlarmDateTime(){
		return _AlarmDateTime;
	}
	public void setAlarmDateTime(java.util.Date value){
		_AlarmDateTime = value;
	}
	private String _AlarmDescription;
	public String getAlarmDescription(){
		return _AlarmDescription;
	}
	public void setAlarmDescription(String value){
		_AlarmDescription = value;
	}
	private String _AlarmMessage;
	public String getAlarmMessage(){
		return _AlarmMessage;
	}
	public void setAlarmMessage(String value){
		_AlarmMessage = value;
	}
	private Integer _AlarmSeverity;
	public Integer getAlarmSeverity(){
		return _AlarmSeverity;
	}
	public void setAlarmSeverity(Integer value){
		_AlarmSeverity = value;
	}
	private String _Alarm_ID;
	public String getAlarm_ID(){
		return _Alarm_ID;
	}
	public void setAlarm_ID(String value){
		_Alarm_ID = value;
	}
	private String _BalancePS_UN;
	public String getBalancePS_UN(){
		return _BalancePS_UN;
	}
	public void setBalancePS_UN(String value){
		_BalancePS_UN = value;
	}
	private java.util.Date _EventDateTime;
	public java.util.Date getEventDateTime(){
		return _EventDateTime;
	}
	public void setEventDateTime(java.util.Date value){
		_EventDateTime = value;
	}
	private String _Formula_UN;
	public String getFormula_UN(){
		return _Formula_UN;
	}
	public void setFormula_UN(String value){
		_Formula_UN = value;
	}
	private Integer _PS_ID;
	public Integer getPS_ID(){
		return _PS_ID;
	}
	public void setPS_ID(Integer value){
		_PS_ID = value;
	}
	private Integer _Slave61968System_ID;
	public Integer getSlave61968System_ID(){
		return _Slave61968System_ID;
	}
	public void setSlave61968System_ID(Integer value){
		_Slave61968System_ID = value;
	}
	private Integer _TI_ID;
	public Integer getTI_ID(){
		return _TI_ID;
	}
	public void setTI_ID(Integer value){
		_TI_ID = value;
	}
	private String _WorkFlowActivityName;
	public String getWorkFlowActivityName(){
		return _WorkFlowActivityName;
	}
	public void setWorkFlowActivityName(String value){
		_WorkFlowActivityName = value;
	}
	
	public static DbAlarm loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		DbAlarm result = new DbAlarm();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setAlarmDateTime(WSHelper.getDate(root,"AlarmDateTime",false));
		this.setAlarmDescription(WSHelper.getString(root,"AlarmDescription",false));
		this.setAlarmMessage(WSHelper.getString(root,"AlarmMessage",false));
		this.setAlarmSeverity(WSHelper.getInteger(root,"AlarmSeverity",false));
		this.setAlarm_ID(WSHelper.getString(root,"Alarm_ID",false));
		this.setBalancePS_UN(WSHelper.getString(root,"BalancePS_UN",false));
		this.setEventDateTime(WSHelper.getDate(root,"EventDateTime",false));
		this.setFormula_UN(WSHelper.getString(root,"Formula_UN",false));
		this.setPS_ID(WSHelper.getInteger(root,"PS_ID",false));
		this.setSlave61968System_ID(WSHelper.getInteger(root,"Slave61968System_ID",false));
		this.setTI_ID(WSHelper.getInteger(root,"TI_ID",false));
		this.setWorkFlowActivityName(WSHelper.getString(root,"WorkFlowActivityName",false));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:DbAlarm");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		ws.addChild(e,"ns4:AlarmDateTime",ws.stringValueOf(_AlarmDateTime),false);
		ws.addChild(e,"ns4:AlarmDescription",String.valueOf(_AlarmDescription),false);
		ws.addChild(e,"ns4:AlarmMessage",String.valueOf(_AlarmMessage),false);
		ws.addChild(e,"ns4:AlarmSeverity",String.valueOf(_AlarmSeverity),false);
		ws.addChild(e,"ns4:Alarm_ID",String.valueOf(_Alarm_ID),false);
		ws.addChild(e,"ns4:BalancePS_UN",String.valueOf(_BalancePS_UN),false);
		ws.addChild(e,"ns4:EventDateTime",ws.stringValueOf(_EventDateTime),false);
		ws.addChild(e,"ns4:Formula_UN",String.valueOf(_Formula_UN),false);
		ws.addChild(e,"ns4:PS_ID",String.valueOf(_PS_ID),false);
		ws.addChild(e,"ns4:Slave61968System_ID",String.valueOf(_Slave61968System_ID),false);
		ws.addChild(e,"ns4:TI_ID",String.valueOf(_TI_ID),false);
		ws.addChild(e,"ns4:WorkFlowActivityName",String.valueOf(_WorkFlowActivityName),false);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_AlarmDateTime);
		out.writeValue(_AlarmDescription);
		out.writeValue(_AlarmMessage);
		out.writeValue(_AlarmSeverity);
		out.writeValue(_Alarm_ID);
		out.writeValue(_BalancePS_UN);
		out.writeValue(_EventDateTime);
		out.writeValue(_Formula_UN);
		out.writeValue(_PS_ID);
		out.writeValue(_Slave61968System_ID);
		out.writeValue(_TI_ID);
		out.writeValue(_WorkFlowActivityName);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_AlarmDateTime = (java.util.Date)in.readValue(null);
		_AlarmDescription = (String)in.readValue(null);
		_AlarmMessage = (String)in.readValue(null);
		_AlarmSeverity = (Integer)in.readValue(null);
		_Alarm_ID = (String)in.readValue(null);
		_BalancePS_UN = (String)in.readValue(null);
		_EventDateTime = (java.util.Date)in.readValue(null);
		_Formula_UN = (String)in.readValue(null);
		_PS_ID = (Integer)in.readValue(null);
		_Slave61968System_ID = (Integer)in.readValue(null);
		_TI_ID = (Integer)in.readValue(null);
		_WorkFlowActivityName = (String)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<DbAlarm> CREATOR = new android.os.Parcelable.Creator<DbAlarm>()
	{
		public DbAlarm createFromParcel(android.os.Parcel in)
		{
			DbAlarm tmp = new DbAlarm();
			tmp.readFromParcel(in);
			return tmp;
		}
		public DbAlarm[] newArray(int size)
		{
			return new DbAlarm[size];
		}
	}
	;
	
}
