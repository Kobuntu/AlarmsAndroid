package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse extends WSObject implements android.os.Parcelable
{
	
	private ArrayOfKeyValueOfintstring _ALARM_GetWorkflowActivityNamesDictResult;
	public ArrayOfKeyValueOfintstring getALARM_GetWorkflowActivityNamesDictResult(){
		return _ALARM_GetWorkflowActivityNamesDictResult;
	}
	public void setALARM_GetWorkflowActivityNamesDictResult(ArrayOfKeyValueOfintstring value){
		_ALARM_GetWorkflowActivityNamesDictResult = value;
	}
	
	public static AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse result = new AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setALARM_GetWorkflowActivityNamesDictResult(ArrayOfKeyValueOfintstring.loadFrom(WSHelper.getElement(root,"ALARM_GetWorkflowActivityNamesDictResult")));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:ALARM_GetWorkflowActivityNamesDictResponse");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_ALARM_GetWorkflowActivityNamesDictResult != null)
			ws.addChildNode(e, "ns4:ALARM_GetWorkflowActivityNamesDictResult",null,_ALARM_GetWorkflowActivityNamesDictResult);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_ALARM_GetWorkflowActivityNamesDictResult);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_ALARM_GetWorkflowActivityNamesDictResult = (ArrayOfKeyValueOfintstring)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse> CREATOR = new android.os.Parcelable.Creator<AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse>()
	{
		public AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse createFromParcel(android.os.Parcel in)
		{
			AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse tmp = new AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse[] newArray(int size)
		{
			return new AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse[size];
		}
	}
	;
	
}
