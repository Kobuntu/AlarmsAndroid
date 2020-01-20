package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_RegisterDeviceResponse extends WSObject implements android.os.Parcelable
{
	
	
	public static AlarmsService_RegisterDeviceResponse loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_RegisterDeviceResponse result = new AlarmsService_RegisterDeviceResponse();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:RegisterDeviceResponse");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
	}
	void readFromParcel(android.os.Parcel in)
	{
	}
	public static final android.os.Parcelable.Creator<AlarmsService_RegisterDeviceResponse> CREATOR = new android.os.Parcelable.Creator<AlarmsService_RegisterDeviceResponse>()
	{
		public AlarmsService_RegisterDeviceResponse createFromParcel(android.os.Parcel in)
		{
			AlarmsService_RegisterDeviceResponse tmp = new AlarmsService_RegisterDeviceResponse();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_RegisterDeviceResponse[] newArray(int size)
		{
			return new AlarmsService_RegisterDeviceResponse[size];
		}
	}
	;
	
}
