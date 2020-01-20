package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsService extends SoapWebService{
	
	
	public AlarmsService(){
		this.setUrl("/Alarms/");
	}
	
	protected String getNamespaces()
	{
		return 
		" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \r\n" + 
		" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" \r\n" + 
		" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" \r\n" + 
		" xmlns:ns4=\"http://alarms.proryv.com/mobile/\" \r\n" + 
		" xmlns:ns5=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" \r\n" + 
		" xmlns:ns6=\"http://schemas.microsoft.com/2003/10/Serialization/\" \r\n" + 
		 "" ;
	}
	
	protected void appendNamespaces(Element e)
	{
		e.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		e.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		e.setAttribute("xmlns:soap", "http://schemas.xmlsoap.org/soap/envelope/");
		e.setAttribute("xmlns:ns4", "http://alarms.proryv.com/mobile/");
		e.setAttribute("xmlns:ns5", "http://schemas.microsoft.com/2003/10/Serialization/Arrays");
		e.setAttribute("xmlns:ns6", "http://schemas.microsoft.com/2003/10/Serialization/");
	}
	
	
	public AlarmsService_RegisterDeviceResponse RegisterDevice(String Registration_ID, String DeviceType, Boolean IsActive, String userName, String password) throws Exception 
	{
		SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/RegisterDevice");
		WSHelper ws = new WSHelper(req.document);
		AlarmsService_RegisterDevice ____method = new AlarmsService_RegisterDevice();
		____method.setRegistration_ID(Registration_ID);
		____method.setDeviceType(DeviceType);
		____method.setIsActive(IsActive);
		____method.setuserName(userName);
		____method.setpassword(password);
		req.method = ____method.toXMLElement(ws,req.root);
		SoapResponse sr = getSoapResponse(req);
		AlarmsService_RegisterDeviceResponse __response = AlarmsService_RegisterDeviceResponse.loadFrom((Element)sr.body.getFirstChild());
		return  __response;
	}
	
	public AlarmsService_SaveDeviceParamsResponse SaveDeviceParams(String Registration_ID, String DeviceType, Boolean IsActive, String userName, String password) throws Exception 
	{
		SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/SaveDeviceParams");
		WSHelper ws = new WSHelper(req.document);
		AlarmsService_SaveDeviceParams ____method = new AlarmsService_SaveDeviceParams();
		____method.setRegistration_ID(Registration_ID);
		____method.setDeviceType(DeviceType);
		____method.setIsActive(IsActive);
		____method.setuserName(userName);
		____method.setpassword(password);
		req.method = ____method.toXMLElement(ws,req.root);
		SoapResponse sr = getSoapResponse(req);
		AlarmsService_SaveDeviceParamsResponse __response = AlarmsService_SaveDeviceParamsResponse.loadFrom((Element)sr.body.getFirstChild());
		return  __response;
	}
	
	public AlarmsService_UnregisterDeviceResponse UnregisterDevice(String Registration_ID, String DeviceType, String userName, String password) throws Exception 
	{
		SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/UnregisterDevice");
		WSHelper ws = new WSHelper(req.document);
		AlarmsService_UnregisterDevice ____method = new AlarmsService_UnregisterDevice();
		____method.setRegistration_ID(Registration_ID);
		____method.setDeviceType(DeviceType);
		____method.setuserName(userName);
		____method.setpassword(password);
		req.method = ____method.toXMLElement(ws,req.root);
		SoapResponse sr = getSoapResponse(req);
		AlarmsService_UnregisterDeviceResponse __response = AlarmsService_UnregisterDeviceResponse.loadFrom((Element)sr.body.getFirstChild());
		return  __response;
	}
	
	public Integer Get_CurrentAlarms_Count(String userName, String password) throws Exception 
	{
		SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/Get_CurrentAlarms_Count");
		WSHelper ws = new WSHelper(req.document);
		AlarmsService_Get_CurrentAlarms_Count ____method = new AlarmsService_Get_CurrentAlarms_Count();
		____method.setuserName(userName);
		____method.setpassword(password);
		req.method = ____method.toXMLElement(ws,req.root);
		SoapResponse sr = getSoapResponse(req);
		AlarmsService_Get_CurrentAlarms_CountResponse __response = AlarmsService_Get_CurrentAlarms_CountResponse.loadFrom((Element)sr.body.getFirstChild());
		return  __response.getGet_CurrentAlarms_CountResult();
	}
	
	public TAlarmsRequest ALARM_GetCurrentAlarms(String userName, String password, Integer topNumbers, AlarmFilterSettings filterSettings) throws Exception 
	{
		SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/ALARM_GetCurrentAlarms");
		WSHelper ws = new WSHelper(req.document);
		AlarmsService_ALARM_GetCurrentAlarms ____method = new AlarmsService_ALARM_GetCurrentAlarms();
		____method.setuserName(userName);
		____method.setpassword(password);
		____method.settopNumbers(topNumbers);
		____method.setfilterSettings(filterSettings);
		req.method = ____method.toXMLElement(ws,req.root);
		SoapResponse sr = getSoapResponse(req);
		AlarmsService_ALARM_GetCurrentAlarmsResponse __response = AlarmsService_ALARM_GetCurrentAlarmsResponse.loadFrom((Element)sr.body.getFirstChild());
		return  __response.getALARM_GetCurrentAlarmsResult();
	}
	
	public TAlarmsRequest ALARM_GetArchiveAlarms(String userName, String password, Integer topNumbers, java.util.Date dtStart, java.util.Date dtEnd, AlarmFilterSettings filterSettings) throws Exception 
	{
		SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/ALARM_GetArchiveAlarms");
		WSHelper ws = new WSHelper(req.document);
		AlarmsService_ALARM_GetArchiveAlarms ____method = new AlarmsService_ALARM_GetArchiveAlarms();
		____method.setuserName(userName);
		____method.setpassword(password);
		____method.settopNumbers(topNumbers);
		____method.setdtStart(dtStart);
		____method.setdtEnd(dtEnd);
		____method.setfilterSettings(filterSettings);
		req.method = ____method.toXMLElement(ws,req.root);
		SoapResponse sr = getSoapResponse(req);
		AlarmsService_ALARM_GetArchiveAlarmsResponse __response = AlarmsService_ALARM_GetArchiveAlarmsResponse.loadFrom((Element)sr.body.getFirstChild());
		return  __response.getALARM_GetArchiveAlarmsResult();
	}
	
	public String ALARM_Confirm(ArrayOfguid AlarmList, String userName, String password, Integer CUS_ID) throws Exception 
	{
		SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/ALARM_Confirm");
		WSHelper ws = new WSHelper(req.document);
		AlarmsService_ALARM_Confirm ____method = new AlarmsService_ALARM_Confirm();
		____method.setAlarmList(AlarmList);
		____method.setuserName(userName);
		____method.setpassword(password);
		____method.setCUS_ID(CUS_ID);
		req.method = ____method.toXMLElement(ws,req.root);
		SoapResponse sr = getSoapResponse(req);
		AlarmsService_ALARM_ConfirmResponse __response = AlarmsService_ALARM_ConfirmResponse.loadFrom((Element)sr.body.getFirstChild());
		return  __response.getALARM_ConfirmResult();
	}
	
	public ArrayOfKeyValueOfintstring ALARM_GetWorkflowActivityNamesDict() throws Exception 
	{
		SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/ALARM_GetWorkflowActivityNamesDict");
		WSHelper ws = new WSHelper(req.document);
		AlarmsService_ALARM_GetWorkflowActivityNamesDict ____method = new AlarmsService_ALARM_GetWorkflowActivityNamesDict();
		req.method = ____method.toXMLElement(ws,req.root);
		SoapResponse sr = getSoapResponse(req);
		AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse __response = AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse.loadFrom((Element)sr.body.getFirstChild());
		return  __response.getALARM_GetWorkflowActivityNamesDictResult();
	}
	
}
