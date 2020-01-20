package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_ALARM_GetCurrentAlarms extends WSObject implements android.os.Parcelable
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
	private AlarmFilterSettings _filterSettings;
	public AlarmFilterSettings getfilterSettings(){
		return _filterSettings;
	}
	public void setfilterSettings(AlarmFilterSettings value){
		_filterSettings = value;
	}
	
	public static AlarmsService_ALARM_GetCurrentAlarms loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_ALARM_GetCurrentAlarms result = new AlarmsService_ALARM_GetCurrentAlarms();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setuserName(WSHelper.getString(root,"userName",false));
		this.setpassword(WSHelper.getString(root,"password",false));
		this.settopNumbers(WSHelper.getInteger(root,"topNumbers",false));
		this.setfilterSettings(AlarmFilterSettings.loadFrom(WSHelper.getElement(root,"filterSettings")));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:ALARM_GetCurrentAlarms");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		ws.addChild(e,"ns4:userName",String.valueOf(_userName),false);
		ws.addChild(e,"ns4:password",String.valueOf(_password),false);
		ws.addChild(e,"ns4:topNumbers",String.valueOf(_topNumbers),false);
		if(_filterSettings != null)
			ws.addChildNode(e, "ns4:filterSettings",null,_filterSettings);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_userName);
		out.writeValue(_password);
		out.writeValue(_topNumbers);
		out.writeValue(_filterSettings);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_userName = (String)in.readValue(null);
		_password = (String)in.readValue(null);
		_topNumbers = (Integer)in.readValue(null);
		_filterSettings = (AlarmFilterSettings)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_ALARM_GetCurrentAlarms> CREATOR = new android.os.Parcelable.Creator<AlarmsService_ALARM_GetCurrentAlarms>()
	{
		public AlarmsService_ALARM_GetCurrentAlarms createFromParcel(android.os.Parcel in)
		{
			AlarmsService_ALARM_GetCurrentAlarms tmp = new AlarmsService_ALARM_GetCurrentAlarms();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_ALARM_GetCurrentAlarms[] newArray(int size)
		{
			return new AlarmsService_ALARM_GetCurrentAlarms[size];
		}
	}
	;
	
}
