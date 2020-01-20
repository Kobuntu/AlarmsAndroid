package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_ALARM_GetCurrentAlarmsResponse extends WSObject implements android.os.Parcelable
{
	
	private TAlarmsRequest _ALARM_GetCurrentAlarmsResult;
	public TAlarmsRequest getALARM_GetCurrentAlarmsResult(){
		return _ALARM_GetCurrentAlarmsResult;
	}
	public void setALARM_GetCurrentAlarmsResult(TAlarmsRequest value){
		_ALARM_GetCurrentAlarmsResult = value;
	}
	
	public static AlarmsService_ALARM_GetCurrentAlarmsResponse loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_ALARM_GetCurrentAlarmsResponse result = new AlarmsService_ALARM_GetCurrentAlarmsResponse();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setALARM_GetCurrentAlarmsResult(TAlarmsRequest.loadFrom(WSHelper.getElement(root,"ALARM_GetCurrentAlarmsResult")));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:ALARM_GetCurrentAlarmsResponse");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_ALARM_GetCurrentAlarmsResult != null)
			ws.addChildNode(e, "ns4:ALARM_GetCurrentAlarmsResult",null,_ALARM_GetCurrentAlarmsResult);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_ALARM_GetCurrentAlarmsResult);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_ALARM_GetCurrentAlarmsResult = (TAlarmsRequest)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_ALARM_GetCurrentAlarmsResponse> CREATOR = new android.os.Parcelable.Creator<AlarmsService_ALARM_GetCurrentAlarmsResponse>()
	{
		public AlarmsService_ALARM_GetCurrentAlarmsResponse createFromParcel(android.os.Parcel in)
		{
			AlarmsService_ALARM_GetCurrentAlarmsResponse tmp = new AlarmsService_ALARM_GetCurrentAlarmsResponse();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_ALARM_GetCurrentAlarmsResponse[] newArray(int size)
		{
			return new AlarmsService_ALARM_GetCurrentAlarmsResponse[size];
		}
	}
	;
	
}
