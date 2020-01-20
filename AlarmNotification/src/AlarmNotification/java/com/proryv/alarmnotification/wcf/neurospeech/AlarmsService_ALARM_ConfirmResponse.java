package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_ALARM_ConfirmResponse extends WSObject implements android.os.Parcelable
{
	
	private String _ALARM_ConfirmResult;
	public String getALARM_ConfirmResult(){
		return _ALARM_ConfirmResult;
	}
	public void setALARM_ConfirmResult(String value){
		_ALARM_ConfirmResult = value;
	}
	
	public static AlarmsService_ALARM_ConfirmResponse loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_ALARM_ConfirmResponse result = new AlarmsService_ALARM_ConfirmResponse();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setALARM_ConfirmResult(WSHelper.getString(root,"ALARM_ConfirmResult",false));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:ALARM_ConfirmResponse");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		ws.addChild(e,"ns4:ALARM_ConfirmResult",String.valueOf(_ALARM_ConfirmResult),false);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_ALARM_ConfirmResult);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_ALARM_ConfirmResult = (String)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_ALARM_ConfirmResponse> CREATOR = new android.os.Parcelable.Creator<AlarmsService_ALARM_ConfirmResponse>()
	{
		public AlarmsService_ALARM_ConfirmResponse createFromParcel(android.os.Parcel in)
		{
			AlarmsService_ALARM_ConfirmResponse tmp = new AlarmsService_ALARM_ConfirmResponse();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_ALARM_ConfirmResponse[] newArray(int size)
		{
			return new AlarmsService_ALARM_ConfirmResponse[size];
		}
	}
	;
	
}
