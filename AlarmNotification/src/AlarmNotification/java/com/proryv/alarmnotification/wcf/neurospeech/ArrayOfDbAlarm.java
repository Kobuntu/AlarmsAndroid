package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class ArrayOfDbAlarm extends WSObject implements android.os.Parcelable
{
	
	private java.util.Vector<DbAlarm> _DbAlarm = new java.util.Vector<DbAlarm>();
	public java.util.Vector<DbAlarm> getDbAlarm(){
		return _DbAlarm;
	}
	public void setDbAlarm(java.util.Vector<DbAlarm> value){
		_DbAlarm = value;
	}
	
	public static ArrayOfDbAlarm loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		ArrayOfDbAlarm result = new ArrayOfDbAlarm();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		NodeList list;
		int i;
		list = WSHelper.getChildren(root, "DbAlarm");
		if(list != null)
		{
			for(i=0;i<list.getLength();i++)
			{
				Element nc = (Element)list.item(i);
				_DbAlarm.addElement(DbAlarm.loadFrom(nc));
			}
		}
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns4:ArrayOfDbAlarm");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_DbAlarm != null)
			ws.addChildArrayInline(e,"ns4:DbAlarm",null, _DbAlarm);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeTypedList(_DbAlarm);
	}
	void readFromParcel(android.os.Parcel in)
	{
		in.readTypedList(_DbAlarm,DbAlarm.CREATOR);
	}
	public static final android.os.Parcelable.Creator<ArrayOfDbAlarm> CREATOR = new android.os.Parcelable.Creator<ArrayOfDbAlarm>()
	{
		public ArrayOfDbAlarm createFromParcel(android.os.Parcel in)
		{
			ArrayOfDbAlarm tmp = new ArrayOfDbAlarm();
			tmp.readFromParcel(in);
			return tmp;
		}
		public ArrayOfDbAlarm[] newArray(int size)
		{
			return new ArrayOfDbAlarm[size];
		}
	}
	;
	
}
