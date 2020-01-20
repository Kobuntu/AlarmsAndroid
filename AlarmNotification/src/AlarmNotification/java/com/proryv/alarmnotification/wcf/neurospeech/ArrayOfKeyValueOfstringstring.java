package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class ArrayOfKeyValueOfstringstring extends WSObject implements android.os.Parcelable
{
	
	private java.util.Vector<KeyValueOfstringstring> _KeyValueOfstringstring = new java.util.Vector<KeyValueOfstringstring>();
	public java.util.Vector<KeyValueOfstringstring> getKeyValueOfstringstring(){
		return _KeyValueOfstringstring;
	}
	public void setKeyValueOfstringstring(java.util.Vector<KeyValueOfstringstring> value){
		_KeyValueOfstringstring = value;
	}
	
	public static ArrayOfKeyValueOfstringstring loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		ArrayOfKeyValueOfstringstring result = new ArrayOfKeyValueOfstringstring();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		NodeList list;
		int i;
		list = WSHelper.getChildren(root, "KeyValueOfstringstring");
		if(list != null)
		{
			for(i=0;i<list.getLength();i++)
			{
				Element nc = (Element)list.item(i);
				_KeyValueOfstringstring.addElement(KeyValueOfstringstring.loadFrom(nc));
			}
		}
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns5:ArrayOfKeyValueOfstringstring");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_KeyValueOfstringstring != null)
			ws.addChildArrayInline(e,"ns5:KeyValueOfstringstring",null, _KeyValueOfstringstring);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeTypedList(_KeyValueOfstringstring);
	}
	void readFromParcel(android.os.Parcel in)
	{
		in.readTypedList(_KeyValueOfstringstring,KeyValueOfstringstring.CREATOR);
	}
	public static final android.os.Parcelable.Creator<ArrayOfKeyValueOfstringstring> CREATOR = new android.os.Parcelable.Creator<ArrayOfKeyValueOfstringstring>()
	{
		public ArrayOfKeyValueOfstringstring createFromParcel(android.os.Parcel in)
		{
			ArrayOfKeyValueOfstringstring tmp = new ArrayOfKeyValueOfstringstring();
			tmp.readFromParcel(in);
			return tmp;
		}
		public ArrayOfKeyValueOfstringstring[] newArray(int size)
		{
			return new ArrayOfKeyValueOfstringstring[size];
		}
	}
	;
	
}
