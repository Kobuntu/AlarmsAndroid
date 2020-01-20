package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_ALARM_GetArchiveAlarms extends WSObject implements android.os.Parcelable
{
	
	private String _userName;
	public String getuserName(){
		return _userName;
	}
	public void setuserName(String value){
		_userName = value;
	}
	private String _password;
	public String getpassword(){
		return _password;
	}
	public void setpassword(String value){
		_password = value;
	}
	private Integer _topNumbers;
	public Integer gettopNumbers(){
		return _topNumbers;
	}
	public void settopNumbers(Integer value){
		_topNumbers = value;
	}
	private java.util.Date _dtStart;
	public java.util.Date getdtStart(){
		return _dtStart;
	}
	public void setdtStart(java.util.Date value){
		_dtStart = value;
	}
	private java.util.Date _dtEnd;
	public java.util.Date getdtEnd(){
		return _dtEnd;
	}
	public void setdtEnd(java.util.Date value){
		_dtEnd = value;
	}
	private AlarmFilterSettings _filterSettings;
	public AlarmFilterSettings getfilterSettings(){
		return _filterSettings;
	}
	public void setfilterSettings(AlarmFilterSettings value){
		_filterSettings = value;
	}
	
	public static AlarmsService_ALARM_GetArchiveAlarms loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_ALARM_GetArchiveAlarms result = new AlarmsService_ALARM_GetArchiveAlarms();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setuserName(WSHelper.getString(root,"userName",false));
		this.setpassword(WSHelper.getString(root,"password",false));
		this.settopNumbers(WSHelper.getInteger(root,"topNumbers",false));
		this.setdtStart(WSHelper.getDate(root,"dtStart",false));
		this.setdtEnd(WSHelper.getDate(root,"dtEnd",false));
		this.setfilterSettings(AlarmFilterSettings.loadFrom(WSHelper.getElement(root,"filterSettings")));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:ALARM_GetArchiveAlarms");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		ws.addChild(e,"ns4:userName",String.valueOf(_userName),false);
		ws.addChild(e,"ns4:password",String.valueOf(_password),false);
		ws.addChild(e,"ns4:topNumbers",String.valueOf(_topNumbers),false);
		ws.addChild(e,"ns4:dtStart",ws.stringValueOf(_dtStart),false);
		ws.addChild(e,"ns4:dtEnd",ws.stringValueOf(_dtEnd),false);
		if(_filterSettings != null)
			ws.addChildNode(e, "ns4:filterSettings",null,_filterSettings);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_userName);
		out.writeValue(_password);
		out.writeValue(_topNumbers);
		out.writeValue(_dtStart);
		out.writeValue(_dtEnd);
		out.writeValue(_filterSettings);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_userName = (String)in.readValue(null);
		_password = (String)in.readValue(null);
		_topNumbers = (Integer)in.readValue(null);
		_dtStart = (java.util.Date)in.readValue(null);
		_dtEnd = (java.util.Date)in.readValue(null);
		_filterSettings = (AlarmFilterSettings)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_ALARM_GetArchiveAlarms> CREATOR = new android.os.Parcelable.Creator<AlarmsService_ALARM_GetArchiveAlarms>()
	{
		public AlarmsService_ALARM_GetArchiveAlarms createFromParcel(android.os.Parcel in)
		{
			AlarmsService_ALARM_GetArchiveAlarms tmp = new AlarmsService_ALARM_GetArchiveAlarms();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_ALARM_GetArchiveAlarms[] newArray(int size)
		{
			return new AlarmsService_ALARM_GetArchiveAlarms[size];
		}
	}
	;
	
}
