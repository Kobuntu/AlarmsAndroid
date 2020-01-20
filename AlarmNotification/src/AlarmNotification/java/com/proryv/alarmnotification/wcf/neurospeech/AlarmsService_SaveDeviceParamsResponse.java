package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_SaveDeviceParamsResponse extends WSObject implements android.os.Parcelable
{
	
	
	public static AlarmsService_SaveDeviceParamsResponse loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_SaveDeviceParamsResponse result = new AlarmsService_SaveDeviceParamsResponse();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:SaveDeviceParamsResponse");
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
	public static final android.os.Parcelable.Creator<AlarmsService_SaveDeviceParamsResponse> CREATOR = new android.os.Parcelable.Creator<AlarmsService_SaveDeviceParamsResponse>()
	{
		public AlarmsService_SaveDeviceParamsResponse createFromParcel(android.os.Parcel in)
		{
			AlarmsService_SaveDeviceParamsResponse tmp = new AlarmsService_SaveDeviceParamsResponse();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_SaveDeviceParamsResponse[] newArray(int size)
		{
			return new AlarmsService_SaveDeviceParamsResponse[size];
		}
	}
	;
	
}
