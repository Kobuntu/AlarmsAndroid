package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class ArrayOfint extends WSObject implements android.os.Parcelable
{
	
	private java.util.Vector<Integer> _int = new java.util.Vector<Integer>();
	public java.util.Vector<Integer> getint(){
		return _int;
	}
	public void setint(java.util.Vector<Integer> value){
		_int = value;
	}
	
	public static ArrayOfint loadFrom(Element root) throws Exception
	{
		if(root==null){
			return null;
		}
		ArrayOfint result = new ArrayOfint();
		result.load(root);
		return result;
	}
	
	
	protected void load(Element root) throws Exception
	{
		NodeList list;
		int i;
		list = WSHelper.getChildren(root, "int");
		if(list != null)
		{
			for(i=0;i<list.getLength();i++)
			{
				Element nc = (Element)list.item(i);
				_int.addElement(WSHelper.getInteger(nc,null,false));
			}
		}
	}
	
	
	
	public Element toXMLElement(WSHelper ws, Element root)
	{
		Element e = ws.createElement("ns5:ArrayOfint");
		fillXML(ws,e);
		return e;
	}
	
	public void fillXML(WSHelper ws,Element e)
	{
		if(_int != null)
			ws.addChildArrayInline(e,"ns5:int","null:int",_int);
	}
	public int describeContents(){ return 0; }
	public void writeToParcel(android.os.Parcel out, int flags)
	{
		out.writeList(_int);
	}
	void readFromParcel(android.os.Parcel in)
	{
		in.readList(_int,null);
	}
	public static final android.os.Parcelable.Creator<ArrayOfint> CREATOR = new android.os.Parcelable.Creator<ArrayOfint>()
	{
		public ArrayOfint createFromParcel(android.os.Parcel in)
		{
			ArrayOfint tmp = new ArrayOfint();
			tmp.readFromParcel(in);
			return tmp;
		}
		public ArrayOfint[] newArray(int size)
		{
			return new ArrayOfint[size];
		}
	}
	;
	
}
