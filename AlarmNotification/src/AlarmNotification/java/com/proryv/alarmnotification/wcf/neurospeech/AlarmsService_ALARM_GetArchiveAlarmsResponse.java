package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_ALARM_GetArchiveAlarmsResponse extends WSObject implements android.os.Parcelable
{
	
	private TAlarmsRequest _ALARM_GetArchiveAlarmsResult;
	public TAlarmsRequest getALARM_GetArchiveAlarmsResult(){
		return _ALARM_GetArchiveAlarmsResult;
	}
	public void setALARM_GetArchiveAlarmsResult(TAlarmsRequest value){
		_ALARM_GetArchiveAlarmsResult = value;
	}
	
	public static AlarmsService_ALARM_GetArchiveAlarmsResponse loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_ALARM_GetArchiveAlarmsResponse result = new AlarmsService_ALARM_GetArchiveAlarmsResponse();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setALARM_GetArchiveAlarmsResult(TAlarmsRequest.loadFrom(WSHelper.getElement(root,"ALARM_GetArchiveAlarmsResult")));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:ALARM_GetArchiveAlarmsResponse");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_ALARM_GetArchiveAlarmsResult != null)
			ws.addChildNode(e, "ns4:ALARM_GetArchiveAlarmsResult",null,_ALARM_GetArchiveAlarmsResult);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_ALARM_GetArchiveAlarmsResult);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_ALARM_GetArchiveAlarmsResult = (TAlarmsRequest)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_ALARM_GetArchiveAlarmsResponse> CREATOR = new android.os.Parcelable.Creator<AlarmsService_ALARM_GetArchiveAlarmsResponse>()
	{
		public AlarmsService_ALARM_GetArchiveAlarmsResponse createFromParcel(android.os.Parcel in)
		{
			AlarmsService_ALARM_GetArchiveAlarmsResponse tmp = new AlarmsService_ALARM_GetArchiveAlarmsResponse();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_ALARM_GetArchiveAlarmsResponse[] newArray(int size)
		{
			return new AlarmsService_ALARM_GetArchiveAlarmsResponse[size];
		}
	}
	;
	
}
