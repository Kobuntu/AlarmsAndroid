package com.proryv.alarmnotification.wcf.neurospeech;

import com.neurospeech.wsclient.*;
import org.w3c.dom.*;

public class AlarmsServiceAsync extends SoapWebService {
	
	
	
	public AlarmsServiceAsync(){
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
	
	
	public class RegisterDeviceResultHandler extends ResultHandler
	{
		
		protected final void onServiceResult()
		{
			onResult((AlarmsService_RegisterDeviceResponse)__result);
		}
		
		protected void onResult(AlarmsService_RegisterDeviceResponse result){}
		
	}
	
	
	class RegisterDeviceRequest extends com.neurospeech.wsclient.ServiceRequest
	{
		String Registration_ID;
		String DeviceType;
		Boolean IsActive;
		String userName;
		String password;
		
		RegisterDeviceRequest(String Registration_ID,String DeviceType,Boolean IsActive,String userName,String password, RegisterDeviceResultHandler handler)
		{
			super(handler);
			this.Registration_ID = Registration_ID;
			this.DeviceType = DeviceType;
			this.IsActive = IsActive;
			this.userName = userName;
			this.password = password;
		}
		
		@Override
		public void executeRequest() throws Exception
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
			__result = __response;
		}
		
	}
	
	public void RegisterDevice(String Registration_ID,String DeviceType,Boolean IsActive,String userName,String password, RegisterDeviceResultHandler handler)
	{
		RegisterDeviceRequest r = new RegisterDeviceRequest(Registration_ID,DeviceType,IsActive,userName,password,handler);
		r.executeAsync();
	}
	
	
	public class SaveDeviceParamsResultHandler extends ResultHandler
	{
		
		protected final void onServiceResult()
		{
			onResult((AlarmsService_SaveDeviceParamsResponse)__result);
		}
		
		protected void onResult(AlarmsService_SaveDeviceParamsResponse result){}
		
	}
	
	
	class SaveDeviceParamsRequest extends com.neurospeech.wsclient.ServiceRequest
	{
		String Registration_ID;
		String DeviceType;
		Boolean IsActive;
		String userName;
		String password;
		
		SaveDeviceParamsRequest(String Registration_ID,String DeviceType,Boolean IsActive,String userName,String password, SaveDeviceParamsResultHandler handler)
		{
			super(handler);
			this.Registration_ID = Registration_ID;
			this.DeviceType = DeviceType;
			this.IsActive = IsActive;
			this.userName = userName;
			this.password = password;
		}
		
		@Override
		public void executeRequest() throws Exception
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
			__result = __response;
		}
		
	}
	
	public void SaveDeviceParams(String Registration_ID,String DeviceType,Boolean IsActive,String userName,String password, SaveDeviceParamsResultHandler handler)
	{
		SaveDeviceParamsRequest r = new SaveDeviceParamsRequest(Registration_ID,DeviceType,IsActive,userName,password,handler);
		r.executeAsync();
	}
	
	
	public class UnregisterDeviceResultHandler extends ResultHandler
	{
		
		protected final void onServiceResult()
		{
			onResult((AlarmsService_UnregisterDeviceResponse)__result);
		}
		
		protected void onResult(AlarmsService_UnregisterDeviceResponse result){}
		
	}
	
	
	class UnregisterDeviceRequest extends com.neurospeech.wsclient.ServiceRequest
	{
		String Registration_ID;
		String DeviceType;
		String userName;
		String password;
		
		UnregisterDeviceRequest(String Registration_ID,String DeviceType,String userName,String password, UnregisterDeviceResultHandler handler)
		{
			super(handler);
			this.Registration_ID = Registration_ID;
			this.DeviceType = DeviceType;
			this.userName = userName;
			this.password = password;
		}
		
		@Override
		public void executeRequest() throws Exception
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
			__result = __response;
		}
		
	}
	
	public void UnregisterDevice(String Registration_ID,String DeviceType,String userName,String password, UnregisterDeviceResultHandler handler)
	{
		UnregisterDeviceRequest r = new UnregisterDeviceRequest(Registration_ID,DeviceType,userName,password,handler);
		r.executeAsync();
	}
	
	
	public class Get_CurrentAlarms_CountResultHandler extends ResultHandler
	{
		
		protected final void onServiceResult()
		{
			onResult((Integer)__result);
		}
		
		protected void onResult(Integer result){}
		
	}
	
	
	class Get_CurrentAlarms_CountRequest extends com.neurospeech.wsclient.ServiceRequest
	{
		String userName;
		String password;
		
		Get_CurrentAlarms_CountRequest(String userName,String password, Get_CurrentAlarms_CountResultHandler handler)
		{
			super(handler);
			this.userName = userName;
			this.password = password;
		}
		
		@Override
		public void executeRequest() throws Exception
		{
			SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/Get_CurrentAlarms_Count");
			WSHelper ws = new WSHelper(req.document);
			AlarmsService_Get_CurrentAlarms_Count ____method = new AlarmsService_Get_CurrentAlarms_Count();
			____method.setuserName(userName);
			____method.setpassword(password);
			req.method = ____method.toXMLElement(ws,req.root);
			SoapResponse sr = getSoapResponse(req);
			AlarmsService_Get_CurrentAlarms_CountResponse __response = AlarmsService_Get_CurrentAlarms_CountResponse.loadFrom((Element)sr.body.getFirstChild());
			__result = __response.getGet_CurrentAlarms_CountResult();
		}
		
	}
	
	public void Get_CurrentAlarms_Count(String userName,String password, Get_CurrentAlarms_CountResultHandler handler)
	{
		Get_CurrentAlarms_CountRequest r = new Get_CurrentAlarms_CountRequest(userName,password,handler);
		r.executeAsync();
	}
	
	
	public class ALARM_GetCurrentAlarmsResultHandler extends ResultHandler
	{
		
		protected final void onServiceResult()
		{
			onResult((TAlarmsRequest)__result);
		}
		
		protected void onResult(TAlarmsRequest result){}
		
	}
	
	
	class ALARM_GetCurrentAlarmsRequest extends com.neurospeech.wsclient.ServiceRequest
	{
		String userName;
		String password;
		Integer topNumbers;
		AlarmFilterSettings filterSettings;
		
		ALARM_GetCurrentAlarmsRequest(String userName,String password,Integer topNumbers,AlarmFilterSettings filterSettings, ALARM_GetCurrentAlarmsResultHandler handler)
		{
			super(handler);
			this.userName = userName;
			this.password = password;
			this.topNumbers = topNumbers;
			this.filterSettings = filterSettings;
		}
		
		@Override
		public void executeRequest() throws Exception
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
			__result = __response.getALARM_GetCurrentAlarmsResult();
		}
		
	}
	
	public void ALARM_GetCurrentAlarms(String userName,String password,Integer topNumbers,AlarmFilterSettings filterSettings, ALARM_GetCurrentAlarmsResultHandler handler)
	{
		ALARM_GetCurrentAlarmsRequest r = new ALARM_GetCurrentAlarmsRequest(userName,password,topNumbers,filterSettings,handler);
		r.executeAsync();
	}
	
	
	public class ALARM_GetArchiveAlarmsResultHandler extends ResultHandler
	{
		
		protected final void onServiceResult()
		{
			onResult((TAlarmsRequest)__result);
		}
		
		protected void onResult(TAlarmsRequest result){}
		
	}
	
	
	class ALARM_GetArchiveAlarmsRequest extends com.neurospeech.wsclient.ServiceRequest
	{
		String userName;
		String password;
		Integer topNumbers;
		java.util.Date dtStart;
		java.util.Date dtEnd;
		AlarmFilterSettings filterSettings;
		
		ALARM_GetArchiveAlarmsRequest(String userName,String password,Integer topNumbers,java.util.Date dtStart,java.util.Date dtEnd,AlarmFilterSettings filterSettings, ALARM_GetArchiveAlarmsResultHandler handler)
		{
			super(handler);
			this.userName = userName;
			this.password = password;
			this.topNumbers = topNumbers;
			this.dtStart = dtStart;
			this.dtEnd = dtEnd;
			this.filterSettings = filterSettings;
		}
		
		@Override
		public void executeRequest() throws Exception
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
			__result = __response.getALARM_GetArchiveAlarmsResult();
		}
		
	}
	
	public void ALARM_GetArchiveAlarms(String userName,String password,Integer topNumbers,java.util.Date dtStart,java.util.Date dtEnd,AlarmFilterSettings filterSettings, ALARM_GetArchiveAlarmsResultHandler handler)
	{
		ALARM_GetArchiveAlarmsRequest r = new ALARM_GetArchiveAlarmsRequest(userName,password,topNumbers,dtStart,dtEnd,filterSettings,handler);
		r.executeAsync();
	}
	
	
	public class ALARM_ConfirmResultHandler extends ResultHandler
	{
		
		protected final void onServiceResult()
		{
			onResult((String)__result);
		}
		
		protected void onResult(String result){}
		
	}
	
	
	class ALARM_ConfirmRequest extends com.neurospeech.wsclient.ServiceRequest
	{
		ArrayOfguid AlarmList;
		String userName;
		String password;
		Integer CUS_ID;
		
		ALARM_ConfirmRequest(ArrayOfguid AlarmList,String userName,String password,Integer CUS_ID, ALARM_ConfirmResultHandler handler)
		{
			super(handler);
			this.AlarmList = AlarmList;
			this.userName = userName;
			this.password = password;
			this.CUS_ID = CUS_ID;
		}
		
		@Override
		public void executeRequest() throws Exception
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
			__result = __response.getALARM_ConfirmResult();
		}
		
	}
	
	public void ALARM_Confirm(ArrayOfguid AlarmList,String userName,String password,Integer CUS_ID, ALARM_ConfirmResultHandler handler)
	{
		ALARM_ConfirmRequest r = new ALARM_ConfirmRequest(AlarmList,userName,password,CUS_ID,handler);
		r.executeAsync();
	}
	
	
	public class ALARM_GetWorkflowActivityNamesDictResultHandler extends ResultHandler
	{
		
		protected final void onServiceResult()
		{
			onResult((ArrayOfKeyValueOfintstring)__result);
		}
		
		protected void onResult(ArrayOfKeyValueOfintstring result){}
		
	}
	
	
	class ALARM_GetWorkflowActivityNamesDictRequest extends com.neurospeech.wsclient.ServiceRequest
	{
		
		ALARM_GetWorkflowActivityNamesDictRequest( ALARM_GetWorkflowActivityNamesDictResultHandler handler)
		{
			super(handler);
		}
		
		@Override
		public void executeRequest() throws Exception
		{
			SoapRequest req = buildSoapRequest("http://alarms.proryv.com/mobile/IAlarmsService/ALARM_GetWorkflowActivityNamesDict");
			WSHelper ws = new WSHelper(req.document);
			AlarmsService_ALARM_GetWorkflowActivityNamesDict ____method = new AlarmsService_ALARM_GetWorkflowActivityNamesDict();
			req.method = ____method.toXMLElement(ws,req.root);
			SoapResponse sr = getSoapResponse(req);
			AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse __response = AlarmsService_ALARM_GetWorkflowActivityNamesDictResponse.loadFrom((Element)sr.body.getFirstChild());
			__result = __response.getALARM_GetWorkflowActivityNamesDictResult();
		}
		
	}
	
	public void ALARM_GetWorkflowActivityNamesDict( ALARM_GetWorkflowActivityNamesDictResultHandler handler)
	{
		ALARM_GetWorkflowActivityNamesDictRequest r = new ALARM_GetWorkflowActivityNamesDictRequest(handler);
		r.executeAsync();
	}
	
	
}
