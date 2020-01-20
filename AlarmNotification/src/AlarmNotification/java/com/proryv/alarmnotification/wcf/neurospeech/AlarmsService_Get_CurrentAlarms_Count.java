package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_Get_CurrentAlarms_Count extends WSObject implements android.os.Parcelable
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
	
	public static AlarmsService_Get_CurrentAlarms_Count loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_Get_CurrentAlarms_Count result = new AlarmsService_Get_CurrentAlarms_Count();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setuserName(WSHelper.getString(root,"userName",false));
		this.setpassword(WSHelper.getString(root,"password",false));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:Get_CurrentAlarms_Count");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		ws.addChild(e,"ns4:userName",String.valueOf(_userName),false);
		ws.addChild(e,"ns4:password",String.valueOf(_password),false);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_userName);
		out.writeValue(_password);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_userName = (String)in.readValue(null);
		_password = (String)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_Get_CurrentAlarms_Count> CREATOR = new android.os.Parcelable.Creator<AlarmsService_Get_CurrentAlarms_Count>()
	{
		public AlarmsService_Get_CurrentAlarms_Count createFromParcel(android.os.Parcel in)
		{
			AlarmsService_Get_CurrentAlarms_Count tmp = new AlarmsService_Get_CurrentAlarms_Count();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_Get_CurrentAlarms_Count[] newArray(int size)
		{
			return new AlarmsService_Get_CurrentAlarms_Count[size];
		}
	}
	;
	
}
