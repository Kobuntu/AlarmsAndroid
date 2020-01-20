package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_UnregisterDeviceResponse extends WSObject implements android.os.Parcelable
{
	
	
	public static AlarmsService_UnregisterDeviceResponse loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_UnregisterDeviceResponse result = new AlarmsService_UnregisterDeviceResponse();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:UnregisterDeviceResponse");
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
	public static final android.os.Parcelable.Creator<AlarmsService_UnregisterDeviceResponse> CREATOR = new android.os.Parcelable.Creator<AlarmsService_UnregisterDeviceResponse>()
	{
		public AlarmsService_UnregisterDeviceResponse createFromParcel(android.os.Parcel in)
		{
			AlarmsService_UnregisterDeviceResponse tmp = new AlarmsService_UnregisterDeviceResponse();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_UnregisterDeviceResponse[] newArray(int size)
		{
			return new AlarmsService_UnregisterDeviceResponse[size];
		}
	}
	;
	
}
