package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_SaveDeviceParams extends WSObject implements android.os.Parcelable
{
	
	private String _Registration_ID;
	public String getRegistration_ID(){
		return _Registration_ID;
	}
	public void setRegistration_ID(String value){
		_Registration_ID = value;
	}
	private String _DeviceType;
	public String getDeviceType(){
		return _DeviceType;
	}
	public void setDeviceType(String value){
		_DeviceType = value;
	}
	private Boolean _IsActive;
	public Boolean getIsActive(){
		return _IsActive;
	}
	public void setIsActive(Boolean value){
		_IsActive = value;
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
	
	public static AlarmsService_SaveDeviceParams loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_SaveDeviceParams result = new AlarmsService_SaveDeviceParams();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setRegistration_ID(WSHelper.getString(root,"Registration_ID",false));
		this.setDeviceType(WSHelper.getString(root,"DeviceType",false));
		this.setIsActive(WSHelper.getBoolean(root,"IsActive",false));
		this.setuserName(WSHelper.getString(root,"userName",false));
		this.setpassword(WSHelper.getString(root,"password",false));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:SaveDeviceParams");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		ws.addChild(e,"ns4:Registration_ID",String.valueOf(_Registration_ID),false);
		ws.addChild(e,"ns4:DeviceType",String.valueOf(_DeviceType),false);
		ws.addChild(e,"ns4:IsActive",(_IsActive ? "true" : "false"),false);
		ws.addChild(e,"ns4:userName",String.valueOf(_userName),false);
		ws.addChild(e,"ns4:password",String.valueOf(_password),false);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_Registration_ID);
		out.writeValue(_DeviceType);
		out.writeValue(_IsActive);
		out.writeValue(_userName);
		out.writeValue(_password);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_Registration_ID = (String)in.readValue(null);
		_DeviceType = (String)in.readValue(null);
		_IsActive = (Boolean)in.readValue(null);
		_userName = (String)in.readValue(null);
		_password = (String)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_SaveDeviceParams> CREATOR = new android.os.Parcelable.Creator<AlarmsService_SaveDeviceParams>()
	{
		public AlarmsService_SaveDeviceParams createFromParcel(android.os.Parcel in)
		{
			AlarmsService_SaveDeviceParams tmp = new AlarmsService_SaveDeviceParams();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_SaveDeviceParams[] newArray(int size)
		{
			return new AlarmsService_SaveDeviceParams[size];
		}
	}
	;
	
}
