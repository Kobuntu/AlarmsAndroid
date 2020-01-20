package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_ALARM_Confirm extends WSObject implements android.os.Parcelable
{
	
	private ArrayOfguid _AlarmList;
	public ArrayOfguid getAlarmList(){
		return _AlarmList;
	}
	public void setAlarmList(ArrayOfguid value){
		_AlarmList = value;
	}
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
	private Integer _CUS_ID;
	public Integer getCUS_ID(){
		return _CUS_ID;
	}
	public void setCUS_ID(Integer value){
		_CUS_ID = value;
	}
	
	public static AlarmsService_ALARM_Confirm loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_ALARM_Confirm result = new AlarmsService_ALARM_Confirm();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setAlarmList(ArrayOfguid.loadFrom(WSHelper.getElement(root,"AlarmList")));
		this.setuserName(WSHelper.getString(root,"userName",false));
		this.setpassword(WSHelper.getString(root,"password",false));
		this.setCUS_ID(WSHelper.getInteger(root,"CUS_ID",false));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:ALARM_Confirm");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_AlarmList != null)
			ws.addChildNode(e, "ns4:AlarmList",null,_AlarmList);
		ws.addChild(e,"ns4:userName",String.valueOf(_userName),false);
		ws.addChild(e,"ns4:password",String.valueOf(_password),false);
		ws.addChild(e,"ns4:CUS_ID",String.valueOf(_CUS_ID),false);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_AlarmList);
		out.writeValue(_userName);
		out.writeValue(_password);
		out.writeValue(_CUS_ID);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_AlarmList = (ArrayOfguid)in.readValue(null);
		_userName = (String)in.readValue(null);
		_password = (String)in.readValue(null);
		_CUS_ID = (Integer)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_ALARM_Confirm> CREATOR = new android.os.Parcelable.Creator<AlarmsService_ALARM_Confirm>()
	{
		public AlarmsService_ALARM_Confirm createFromParcel(android.os.Parcel in)
		{
			AlarmsService_ALARM_Confirm tmp = new AlarmsService_ALARM_Confirm();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_ALARM_Confirm[] newArray(int size)
		{
			return new AlarmsService_ALARM_Confirm[size];
		}
	}
	;
	
}
