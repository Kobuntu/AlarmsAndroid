package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class ArrayOfKeyValueOfintstring extends WSObject implements android.os.Parcelable
{
	
	private java.util.Vector<KeyValueOfintstring> _KeyValueOfintstring = new java.util.Vector<KeyValueOfintstring>();
	public java.util.Vector<KeyValueOfintstring> getKeyValueOfintstring(){
		return _KeyValueOfintstring;
	}
	public void setKeyValueOfintstring(java.util.Vector<KeyValueOfintstring> value){
		_KeyValueOfintstring = value;
	}
	
	public static ArrayOfKeyValueOfintstring loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		ArrayOfKeyValueOfintstring result = new ArrayOfKeyValueOfintstring();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		NodeList list;
		int i;
		list = WSHelper.getChildren(root, "KeyValueOfintstring");
		if(list != null)
		{
			for(i=0;i<list.getLength();i++)
			{
				Element nc = (Element)list.item(i);
				_KeyValueOfintstring.addElement(KeyValueOfintstring.loadFrom(nc));
			}
		}
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns5:ArrayOfKeyValueOfintstring");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_KeyValueOfintstring != null)
			ws.addChildArrayInline(e,"ns5:KeyValueOfintstring",null, _KeyValueOfintstring);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeTypedList(_KeyValueOfintstring);
	}
	void readFromParcel(android.os.Parcel in)
	{
		in.readTypedList(_KeyValueOfintstring,KeyValueOfintstring.CREATOR);
	}
	public static final android.os.Parcelable.Creator<ArrayOfKeyValueOfintstring> CREATOR = new android.os.Parcelable.Creator<ArrayOfKeyValueOfintstring>()
	{
		public ArrayOfKeyValueOfintstring createFromParcel(android.os.Parcel in)
		{
			ArrayOfKeyValueOfintstring tmp = new ArrayOfKeyValueOfintstring();
			tmp.readFromParcel(in);
			return tmp;
		}
		public ArrayOfKeyValueOfintstring[] newArray(int size)
		{
			return new ArrayOfKeyValueOfintstring[size];
		}
	}
	;
	
}
