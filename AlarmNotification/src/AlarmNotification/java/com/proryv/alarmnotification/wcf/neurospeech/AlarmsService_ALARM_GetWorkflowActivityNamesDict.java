package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_ALARM_GetWorkflowActivityNamesDict extends WSObject implements android.os.Parcelable
{
	
	
	public static AlarmsService_ALARM_GetWorkflowActivityNamesDict loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_ALARM_GetWorkflowActivityNamesDict result = new AlarmsService_ALARM_GetWorkflowActivityNamesDict();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:ALARM_GetWorkflowActivityNamesDict");
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
	public static final android.os.Parcelable.Creator<AlarmsService_ALARM_GetWorkflowActivityNamesDict> CREATOR = new android.os.Parcelable.Creator<AlarmsService_ALARM_GetWorkflowActivityNamesDict>()
	{
		public AlarmsService_ALARM_GetWorkflowActivityNamesDict createFromParcel(android.os.Parcel in)
		{
			AlarmsService_ALARM_GetWorkflowActivityNamesDict tmp = new AlarmsService_ALARM_GetWorkflowActivityNamesDict();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_ALARM_GetWorkflowActivityNamesDict[] newArray(int size)
		{
			return new AlarmsService_ALARM_GetWorkflowActivityNamesDict[size];
		}
	}
	;
	
}
