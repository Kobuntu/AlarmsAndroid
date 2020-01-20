package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class KeyValueOfintstring extends WSObject implements android.os.Parcelable
{
	
	private Integer _Key;
	public Integer getKey(){
		return _Key;
	}
	public void setKey(Integer value){
		_Key = value;
	}
	private String _Value;
	public String getValue(){
		return _Value;
	}
	public void setValue(String value){
		_Value = value;
	}
	
	public static KeyValueOfintstring loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		KeyValueOfintstring result = new KeyValueOfintstring();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		this.setKey(WSHelper.getInteger(root,"Key",false));
		this.setValue(WSHelper.getString(root,"Value",false));
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns5:KeyValueOfintstring");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		ws.addChild(e,"ns5:Key",String.valueOf(_Key),false);
		ws.addChild(e,"ns5:Value",String.valueOf(_Value),false);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeValue(_Key);
		out.writeValue(_Value);
	}
	void readFromParcel(android.os.Parcel in)
	{
		_Key = (Integer)in.readValue(null);
		_Value = (String)in.readValue(null);
	}
	public static final android.os.Parcelable.Creator<KeyValueOfintstring> CREATOR = new android.os.Parcelable.Creator<KeyValueOfintstring>()
	{
		public KeyValueOfintstring createFromParcel(android.os.Parcel in)
		{
			KeyValueOfintstring tmp = new KeyValueOfintstring();
			tmp.readFromParcel(in);
			return tmp;
		}
		public KeyValueOfintstring[] newArray(int size)
		{
			return new KeyValueOfintstring[size];
		}
	}
	;
	
}
