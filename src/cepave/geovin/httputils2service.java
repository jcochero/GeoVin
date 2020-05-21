package cepave.geovin;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class httputils2service extends  android.app.Service{
	public static class httputils2service_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (httputils2service) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, httputils2service.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, false, BA.class);
		}

	}
    static httputils2service mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return httputils2service.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "cepave.geovin", "cepave.geovin.httputils2service");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.httputils2service", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (httputils2service) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (httputils2service) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (httputils2service) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        if (false) {
            BA.LogInfo("** Service (httputils2service) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (httputils2service) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper _hc = null;
public static anywheresoftware.b4a.objects.collections.Map _taskidtojob = null;
public static String _tempfolder = "";
public static int _taskcounter = 0;
public cepave.geovin.main _main = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.downloadservice _downloadservice = null;
public cepave.geovin.frmfotos _frmfotos = null;
public cepave.geovin.utilidades _utilidades = null;
public cepave.geovin.dbutils _dbutils = null;
public cepave.geovin.starter _starter = null;
public cepave.geovin.firebasemessaging _firebasemessaging = null;
public cepave.geovin.frmabout _frmabout = null;
public cepave.geovin.frmaprender_chagas _frmaprender_chagas = null;
public cepave.geovin.frmcamara _frmcamara = null;
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public cepave.geovin.frmdatosanteriores _frmdatosanteriores = null;
public cepave.geovin.frmeditprofile _frmeditprofile = null;
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.frmidentificacionnew _frmidentificacionnew = null;
public cepave.geovin.frmlocalizacion _frmlocalizacion = null;
public cepave.geovin.frmlogin _frmlogin = null;
public cepave.geovin.frmpoliticadatos _frmpoliticadatos = null;
public cepave.geovin.frmrecomendaciones _frmrecomendaciones = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.register _register = null;
public cepave.geovin.uploadfiles _uploadfiles = null;
public static String  _completejob(int _taskid,boolean _success,String _errormessage) throws Exception{
cepave.geovin.httpjob _job = null;
 //BA.debugLineNum = 72;BA.debugLine="Sub CompleteJob(TaskId As Int, success As Boolean,";
 //BA.debugLineNum = 73;BA.debugLine="Dim job As HttpJob";
_job = new cepave.geovin.httpjob();
 //BA.debugLineNum = 74;BA.debugLine="job = TaskIdToJob.Get(TaskId)";
_job = (cepave.geovin.httpjob)(_taskidtojob.Get((Object)(_taskid)));
 //BA.debugLineNum = 75;BA.debugLine="TaskIdToJob.Remove(TaskId)";
_taskidtojob.Remove((Object)(_taskid));
 //BA.debugLineNum = 76;BA.debugLine="job.success = success";
_job._success /*boolean*/  = _success;
 //BA.debugLineNum = 77;BA.debugLine="job.errorMessage = errorMessage";
_job._errormessage /*String*/  = _errormessage;
 //BA.debugLineNum = 78;BA.debugLine="job.Complete(TaskId)";
_job._complete /*String*/ (_taskid);
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,String _reason,int _statuscode,int _taskid) throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub hc_ResponseError (Response As OkHttpResponse,";
 //BA.debugLineNum = 65;BA.debugLine="If Response <> Null Then";
if (_response!= null) { 
 //BA.debugLineNum = 66;BA.debugLine="Log(Response.ErrorResponse)";
anywheresoftware.b4a.keywords.Common.LogImpl("528377090",_response.getErrorResponse(),0);
 //BA.debugLineNum = 67;BA.debugLine="Response.Release";
_response.Release();
 };
 //BA.debugLineNum = 69;BA.debugLine="CompleteJob(TaskId, False, Reason)";
_completejob(_taskid,anywheresoftware.b4a.keywords.Common.False,_reason);
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,int _taskid) throws Exception{
anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput _cs = null;
cepave.geovin.httpjob _j = null;
cepave.geovin.downloadservice._jobtag _jt = null;
 //BA.debugLineNum = 39;BA.debugLine="Sub hc_ResponseSuccess (Response As OkHttpResponse";
 //BA.debugLineNum = 41;BA.debugLine="Dim cs As CountingOutputStream";
_cs = new anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput();
 //BA.debugLineNum = 42;BA.debugLine="cs.Initialize(File.OpenOutput(TempFolder, TaskId,";
_cs.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(_tempfolder,BA.NumberToString(_taskid),anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 43;BA.debugLine="Dim j As HttpJob = TaskIdToJob.Get(TaskId)";
_j = (cepave.geovin.httpjob)(_taskidtojob.Get((Object)(_taskid)));
 //BA.debugLineNum = 44;BA.debugLine="Dim jt As JobTag = j.Tag";
_jt = (cepave.geovin.downloadservice._jobtag)(_j._tag /*Object*/ );
 //BA.debugLineNum = 45;BA.debugLine="jt.CountingStream = cs";
_jt.CountingStream /*anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput*/  = _cs;
 //BA.debugLineNum = 46;BA.debugLine="jt.Total = Response.ContentLength";
_jt.Total /*long*/  = _response.getContentLength();
 //BA.debugLineNum = 47;BA.debugLine="If jt.Data.url = \"\" Then";
if ((_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .url /*String*/ ).equals("")) { 
 //BA.debugLineNum = 48;BA.debugLine="Log(\"Job cancelled before downloaded started\")";
anywheresoftware.b4a.keywords.Common.LogImpl("528246025","Job cancelled before downloaded started",0);
 //BA.debugLineNum = 49;BA.debugLine="cs.Close";
_cs.Close();
 };
 //BA.debugLineNum = 51;BA.debugLine="Response.GetAsynchronously(\"response\", cs , _ 		T";
_response.GetAsynchronously(processBA,"response",(java.io.OutputStream)(_cs.getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Private hc As OkHttpClient";
_hc = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Private TaskIdToJob As Map";
_taskidtojob = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 10;BA.debugLine="Public TempFolder As String";
_tempfolder = "";
 //BA.debugLineNum = 11;BA.debugLine="Private taskCounter As Int";
_taskcounter = 0;
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(boolean _success,int _taskid) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Response_StreamFinish (Success As Boolean, Tas";
 //BA.debugLineNum = 57;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 58;BA.debugLine="CompleteJob(TaskId, Success, \"\")";
_completejob(_taskid,_success,"");
 }else {
 //BA.debugLineNum = 60;BA.debugLine="CompleteJob(TaskId, Success, LastException.Messa";
_completejob(_taskid,_success,anywheresoftware.b4a.keywords.Common.LastException(processBA).getMessage());
 };
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 15;BA.debugLine="TempFolder = File.DirInternalCache";
_tempfolder = anywheresoftware.b4a.keywords.Common.File.getDirInternalCache();
 //BA.debugLineNum = 16;BA.debugLine="hc.Initialize(\"hc\")";
_hc.Initialize("hc");
 //BA.debugLineNum = 17;BA.debugLine="TaskIdToJob.Initialize";
_taskidtojob.Initialize();
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static int  _submitjob(cepave.geovin.httpjob _job) throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Public Sub SubmitJob(job As HttpJob) As Int";
 //BA.debugLineNum = 29;BA.debugLine="taskCounter = taskCounter + 1";
_taskcounter = (int) (_taskcounter+1);
 //BA.debugLineNum = 30;BA.debugLine="TaskIdToJob.Put(taskCounter, job)";
_taskidtojob.Put((Object)(_taskcounter),(Object)(_job));
 //BA.debugLineNum = 31;BA.debugLine="If job.Username <> \"\" And job.Password <> \"\" Then";
if ((_job._username /*String*/ ).equals("") == false && (_job._password /*String*/ ).equals("") == false) { 
 //BA.debugLineNum = 32;BA.debugLine="hc.ExecuteCredentials(job.GetRequest, taskCounte";
_hc.ExecuteCredentials(processBA,_job._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ (),_taskcounter,_job._username /*String*/ ,_job._password /*String*/ );
 }else {
 //BA.debugLineNum = 34;BA.debugLine="hc.Execute(job.GetRequest, taskCounter)";
_hc.Execute(processBA,_job._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ (),_taskcounter);
 };
 //BA.debugLineNum = 36;BA.debugLine="Return taskCounter";
if (true) return _taskcounter;
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return 0;
}
}
