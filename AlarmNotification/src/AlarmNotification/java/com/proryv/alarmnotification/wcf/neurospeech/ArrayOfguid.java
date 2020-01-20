package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class ArrayOfguid extends WSObject implements android.os.Parcelable
{
	
	private java.util.Vector<String> _guid = new java.util.Vector<String>();
	public java.util.Vector<String> getguid(){
		return _guid;
	}
	public void setguid(java.util.Vector<String> value){
		_guid = value;
	}
	
	public static ArrayOfguid loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		ArrayOfguid result = new ArrayOfguid();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		NodeList list;
		int i;
		list = WSHelper.getChildren(root, "guid");
		if(list != null)
		{
			for(i=0;i<list.getLength();i++)
			{
				Element nc = (Element)list.item(i);
				_guid.addElement(WSHelper.getString(nc,null,false));
			}
		}
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns5:ArrayOfguid");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_guid != null)
			ws.addChildArrayInline(e,"ns5:guid","null:string",_guid);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeList(_guid);
	}
	void readFromParcel(android.os.Parcel in)
	{
		in.readList(_guid,null);
	}
	public static final android.os.Parcelable.Creator<ArrayOfguid> CREATOR = new android.os.Parcelable.Creator<ArrayOfguid>()
	{
		public ArrayOfguid createFromParcel(android.os.Parcel in)
		{
			ArrayOfguid tmp = new ArrayOfguid();
			tmp.readFromParcel(in);
			return tmp;
		}
		public ArrayOfguid[] newArray(int size)
		{
			return new ArrayOfguid[size];
		}
	}
	;
	
}
