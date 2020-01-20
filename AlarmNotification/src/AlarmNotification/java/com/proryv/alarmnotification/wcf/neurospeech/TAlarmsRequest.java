package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import com.proryv.alarmnotification.adapters.SubscribedServer;

import org.w3c.dom.*;

public class TAlarmsRequest extends WSObject implements android.os.Parcelable
{
    public SubscribedServer subscribedServer;

	private ArrayOfDbAlarm _AlarmList;
	public ArrayOfDbAlarm getAlarmList(){
		return _AlarmList;
	}
	public void setAlarmList(ArrayOfDbAlarm value){
		_AlarmList = value;
	}
	private ArrayOfKeyValueOfstringstring _FormulaDict;
	public ArrayOfKeyValueOfstringstring getFormulaDict(){
		return _FormulaDict;
	}
	public void setFormulaDict(ArrayOfKeyValueOfstringstring value){
		_FormulaDict = value;
	}
	private ArrayOfKeyValueOfstringstring _PsBalansePathDict;
	public ArrayOfKeyValueOfstringstring getPsBalansePathDict(){
		return _PsBalansePathDict;
	}
	public void setPsBalansePathDict(ArrayOfKeyValueOfstringstring value){
		_PsBalansePathDict = value;
	}
	private ArrayOfKeyValueOfintstring _PsPathDict;
	public ArrayOfKeyValueOfintstring getPsPathDict(){
		return _PsPathDict;
	}
	public void setPsPathDict(ArrayOfKeyValueOfintstring value){
		_PsPathDict = value;
	}
	private ArrayOfKeyValueOfintstring _SlaveSystemsDict;
	public ArrayOfKeyValueOfintstring getSlaveSystemsDict(){
		return _SlaveSystemsDict;
	}
	public void setSlaveSystemsDict(ArrayOfKeyValueOfintstring value){
		_SlaveSystemsDict = value;
	}
	private ArrayOfKeyValueOfintstring _TiPathDict;
	public ArrayOfKeyValueOfintstring getTiPathDict(){
		return _TiPathDict;
	}
	public void setTiPathDict(ArrayOfKeyValueOfintstring value){
		_TiPathDict = value;
	}
	private Integer _TotalNumbers;
	public Integer getTotalNumbers(){
		return _TotalNumbers;
	}
	public void setTotalNumbers(Integer value){
		_TotalNumbers = value;
	}
	
	public static TAlarmsRequest loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		TAlarmsRequest result = new TAlarmsRequest();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setAlarmList(ArrayOfDbAlarm.loadFrom(WSHelper.getElement(root,"AlarmList")));
		this.setFormulaDict(ArrayOfKeyValueOfstringstring.loadFrom(WSHelper.getElement(root,"FormulaDict")));
		this.setPsBalansePathDict(ArrayOfKeyValueOfstringstring.loadFrom(WSHelper.getElement(root,"PsBalansePathDict")));
		this.setPsPathDict(ArrayOfKeyValueOfintstring.loadFrom(WSHelper.getElement(root,"PsPathDict")));
		this.setSlaveSystemsDict(ArrayOfKeyValueOfintstring.loadFrom(WSHelper.getElement(root,"SlaveSystemsDict")));
		this.setTiPathDict(ArrayOfKeyValueOfintstring.loadFrom(WSHelper.getElement(root,"TiPathDict")));
		this.setTotalNumbers(WSHelper.getInteger(root,"TotalNumbers",false));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:TAlarmsRequest");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_AlarmList != null)
			ws.addChildNode(e, "ns4:AlarmList",null,_AlarmList);
		if(_FormulaDict != null)
			ws.addChildNode(e, "ns4:FormulaDict",null,_FormulaDict);
		if(_PsBalansePathDict != null)
			ws.addChildNode(e, "ns4:PsBalansePathDict",null,_PsBalansePathDict);
		if(_PsPathDict != null)
			ws.addChildNode(e, "ns4:PsPathDict",null,_PsPathDict);
		if(_SlaveSystemsDict != null)
			ws.addChildNode(e, "ns4:SlaveSystemsDict",null,_SlaveSystemsDict);
		if(_TiPathDict != null)
			ws.addChildNode(e, "ns4:TiPathDict",null,_TiPathDict);
		ws.addChild(e,"ns4:TotalNumbers",String.valueOf(_TotalNumbers),false);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_AlarmList);
		out.writeValue(_FormulaDict);
		out.writeValue(_PsBalansePathDict);
		out.writeValue(_PsPathDict);
		out.writeValue(_SlaveSystemsDict);
		out.writeValue(_TiPathDict);
		out.writeValue(_TotalNumbers);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_AlarmList = (ArrayOfDbAlarm)in.readValue(null);
		_FormulaDict = (ArrayOfKeyValueOfstringstring)in.readValue(null);
		_PsBalansePathDict = (ArrayOfKeyValueOfstringstring)in.readValue(null);
		_PsPathDict = (ArrayOfKeyValueOfintstring)in.readValue(null);
		_SlaveSystemsDict = (ArrayOfKeyValueOfintstring)in.readValue(null);
		_TiPathDict = (ArrayOfKeyValueOfintstring)in.readValue(null);
		_TotalNumbers = (Integer)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<TAlarmsRequest> CREATOR = new android.os.Parcelable.Creator<TAlarmsRequest>()
	{
		public TAlarmsRequest createFromParcel(android.os.Parcel in)
		{
			TAlarmsRequest tmp = new TAlarmsRequest();
			tmp.readFromParcel(in);
			return tmp;
		}
		public TAlarmsRequest[] newArray(int size)
		{
			return new TAlarmsRequest[size];
		}
	}
	;
	
}
