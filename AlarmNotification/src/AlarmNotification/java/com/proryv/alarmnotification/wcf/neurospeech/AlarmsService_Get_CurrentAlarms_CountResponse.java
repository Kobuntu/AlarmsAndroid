package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService_Get_CurrentAlarms_CountResponse extends WSObject implements android.os.Parcelable
{
	
	private Integer _Get_CurrentAlarms_CountResult;
	public Integer getGet_CurrentAlarms_CountResult(){
		return _Get_CurrentAlarms_CountResult;
	}
	public void setGet_CurrentAlarms_CountResult(Integer value){
		_Get_CurrentAlarms_CountResult = value;
	}
	
	public static AlarmsService_Get_CurrentAlarms_CountResponse loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		AlarmsService_Get_CurrentAlarms_CountResponse result = new AlarmsService_Get_CurrentAlarms_CountResponse();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setGet_CurrentAlarms_CountResult(WSHelper.getInteger(root,"Get_CurrentAlarms_CountResult",false));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:Get_CurrentAlarms_CountResponse");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		ws.addChild(e,"ns4:Get_CurrentAlarms_CountResult",String.valueOf(_Get_CurrentAlarms_CountResult),false);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_Get_CurrentAlarms_CountResult);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_Get_CurrentAlarms_CountResult = (Integer)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<AlarmsService_Get_CurrentAlarms_CountResponse> CREATOR = new android.os.Parcelable.Creator<AlarmsService_Get_CurrentAlarms_CountResponse>()
	{
		public AlarmsService_Get_CurrentAlarms_CountResponse createFromParcel(android.os.Parcel in)
		{
			AlarmsService_Get_CurrentAlarms_CountResponse tmp = new AlarmsService_Get_CurrentAlarms_CountResponse();
			tmp.readFromParcel(in);
			return tmp;
		}
		public AlarmsService_Get_CurrentAlarms_CountResponse[] newArray(int size)
		{
			return new AlarmsService_Get_CurrentAlarms_CountResponse[size];
		}
	}
	;
	
}
