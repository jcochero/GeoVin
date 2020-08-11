package cepave.geovin;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class downloadservice extends  android.app.Service{
	public static class downloadservice_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (downloadservice) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, downloadservice.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, false, BA.class);
		}

	}
    static downloadservice mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return downloadservice.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "cepave.geovin", "cepave.geovin.downloadservice");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.downloadservice", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (downloadservice) Create ***");
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
                    BA.LogInfo("** Service (downloadservice) Create **");
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
    	BA.LogInfo("** Service (downloadservice) Start **");
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
            BA.LogInfo("** Service (downloadservice) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (downloadservice) Destroy **");
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
public static anywheresoftware.b4a.objects.collections.Map _jobs = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static anywheresoftware.b4a.phone.Phone.PhoneWakeState _pw = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.frmfotos _frmfotos = null;
public cepave.geovin.utilidades _utilidades = null;
public cepave.geovin.dbutils _dbutils = null;
public cepave.geovin.starter _starter = null;
public cepave.geovin.frmlocalizacion _frmlocalizacion = null;
public cepave.geovin.firebasemessaging _firebasemessaging = null;
public cepave.geovin.frmabout _frmabout = null;
public cepave.geovin.frmaprender_chagas _frmaprender_chagas = null;
public cepave.geovin.frmcamara _frmcamara = null;
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public cepave.geovin.frmdatosanteriores _frmdatosanteriores = null;
public cepave.geovin.frmeditprofile _frmeditprofile = null;
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.frmidentificacionnew _frmidentificacionnew = null;
public cepave.geovin.frmlogin _frmlogin = null;
public cepave.geovin.frmpoliticadatos _frmpoliticadatos = null;
public cepave.geovin.frmrecomendaciones _frmrecomendaciones = null;
public cepave.geovin.httputils2service _httputils2service = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.register _register = null;
public cepave.geovin.uploadfiles _uploadfiles = null;
public static class _downloaddata{
public boolean IsInitialized;
public String url;
public Object Target;
public String EventName;
public void Initialize() {
IsInitialized = true;
url = "";
Target = new Object();
EventName = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _jobtag{
public boolean IsInitialized;
public cepave.geovin.downloadservice._downloaddata Data;
public anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput CountingStream;
public long Total;
public void Initialize() {
IsInitialized = true;
Data = new cepave.geovin.downloadservice._downloaddata();
CountingStream = new anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput();
Total = 0L;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _canceldownload(String _url) throws Exception{
cepave.geovin.httpjob _job = null;
cepave.geovin.downloadservice._jobtag _jt = null;
 //BA.debugLineNum = 61;BA.debugLine="Public Sub CancelDownload(url As String)";
 //BA.debugLineNum = 62;BA.debugLine="If jobs.ContainsKey(url) = False Then";
if (_jobs.ContainsKey((Object)(_url))==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 63;BA.debugLine="Log(\"Ignoring cancel request.\")";
anywheresoftware.b4a.keywords.Common.LogImpl("24980738","Ignoring cancel request.",0);
 //BA.debugLineNum = 64;BA.debugLine="Log(\"url: \" & url)";
anywheresoftware.b4a.keywords.Common.LogImpl("24980739","url: "+_url,0);
 //BA.debugLineNum = 65;BA.debugLine="Log(jobs.Get(\"jobname\"))";
anywheresoftware.b4a.keywords.Common.LogImpl("24980740",BA.ObjectToString(_jobs.Get((Object)("jobname"))),0);
 //BA.debugLineNum = 66;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 68;BA.debugLine="Log(\"Cancelando: \" & url)";
anywheresoftware.b4a.keywords.Common.LogImpl("24980743","Cancelando: "+_url,0);
 //BA.debugLineNum = 69;BA.debugLine="Dim job As HttpJob = jobs.Get(url)";
_job = (cepave.geovin.httpjob)(_jobs.Get((Object)(_url)));
 //BA.debugLineNum = 70;BA.debugLine="Dim jt As JobTag = job.Tag";
_jt = (cepave.geovin.downloadservice._jobtag)(_job._tag /*Object*/ );
 //BA.debugLineNum = 71;BA.debugLine="If jt.CountingStream.IsInitialized Then";
if (_jt.CountingStream /*anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput*/ .IsInitialized()) { 
 //BA.debugLineNum = 72;BA.debugLine="jt.CountingStream.Close";
_jt.CountingStream /*anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput*/ .Close();
 }else {
 //BA.debugLineNum = 74;BA.debugLine="jt.Data.url = \"\"";
_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .url /*String*/  = "";
 };
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _endtimer() throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Private Sub EndTimer";
 //BA.debugLineNum = 40;BA.debugLine="Service.StopForeground(1)";
mostCurrent._service.StopForeground((int) (1));
 //BA.debugLineNum = 41;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 42;BA.debugLine="pw.ReleasePartialLock";
_pw.ReleasePartialLock();
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(cepave.geovin.httpjob _job) throws Exception{
cepave.geovin.downloadservice._jobtag _jt = null;
 //BA.debugLineNum = 88;BA.debugLine="Sub JobDone(job As HttpJob)";
 //BA.debugLineNum = 89;BA.debugLine="jobs.Remove(job.JobName)";
_jobs.Remove((Object)(_job._jobname /*String*/ ));
 //BA.debugLineNum = 90;BA.debugLine="Dim jt As JobTag = job.Tag";
_jt = (cepave.geovin.downloadservice._jobtag)(_job._tag /*Object*/ );
 //BA.debugLineNum = 91;BA.debugLine="If jobs.Size = 0 Then EndTimer";
if (_jobs.getSize()==0) { 
_endtimer();};
 //BA.debugLineNum = 92;BA.debugLine="If job.Success Then";
if (_job._success /*boolean*/ ) { 
 //BA.debugLineNum = 93;BA.debugLine="CallSubDelayed3(jt.Data.Target, jt.Data.EventNam";
anywheresoftware.b4a.keywords.Common.CallSubDelayed3(processBA,_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .Target /*Object*/ ,_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .EventName /*String*/ +"_Progress",(Object)(_jt.CountingStream /*anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput*/ .getCount()),(Object)(_jt.Total /*long*/ ));
 //BA.debugLineNum = 95;BA.debugLine="CallSubDelayed2(jt.Data.Target, jt.Data.EventNam";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .Target /*Object*/ ,_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .EventName /*String*/ +"_Complete",(Object)(_job));
 }else {
 //BA.debugLineNum = 98;BA.debugLine="Log(job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("25111818",_job._errormessage /*String*/ ,0);
 //BA.debugLineNum = 99;BA.debugLine="CallSubDelayed2(jt.Data.Target, jt.Data.EventNam";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .Target /*Object*/ ,_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .EventName /*String*/ +"_Complete",(Object)(_job));
 };
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Private jobs As Map";
_jobs = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 7;BA.debugLine="Private timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 8;BA.debugLine="Type DownloadData (url As String, Target As Objec";
;
 //BA.debugLineNum = 9;BA.debugLine="Type JobTag (Data As DownloadData,  _ 		CountingS";
;
 //BA.debugLineNum = 11;BA.debugLine="Private pw As PhoneWakeState";
_pw = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 14;BA.debugLine="jobs.Initialize";
_jobs.Initialize();
 //BA.debugLineNum = 15;BA.debugLine="timer1.Initialize(\"timer1\", 1000)";
_timer1.Initialize(processBA,"timer1",(long) (1000));
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _startdownload(cepave.geovin.downloadservice._downloaddata _data) throws Exception{
cepave.geovin.httpjob _j = null;
cepave.geovin.downloadservice._jobtag _tag = null;
 //BA.debugLineNum = 45;BA.debugLine="Public Sub StartDownload(data As DownloadData)";
 //BA.debugLineNum = 46;BA.debugLine="If jobs.ContainsKey(data.url) Then";
if (_jobs.ContainsKey((Object)(_data.url /*String*/ ))) { 
 //BA.debugLineNum = 47;BA.debugLine="Log(\"Ignoring duplicate request.\")";
anywheresoftware.b4a.keywords.Common.LogImpl("24915202","Ignoring duplicate request.",0);
 //BA.debugLineNum = 48;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 50;BA.debugLine="Dim J As HttpJob";
_j = new cepave.geovin.httpjob();
 //BA.debugLineNum = 51;BA.debugLine="J.Initialize(data.url, Me)";
_j._initialize /*String*/ (processBA,_data.url /*String*/ ,downloadservice.getObject());
 //BA.debugLineNum = 52;BA.debugLine="Dim tag As JobTag";
_tag = new cepave.geovin.downloadservice._jobtag();
 //BA.debugLineNum = 53;BA.debugLine="tag.Initialize";
_tag.Initialize();
 //BA.debugLineNum = 54;BA.debugLine="tag.data = data";
_tag.Data /*cepave.geovin.downloadservice._downloaddata*/  = _data;
 //BA.debugLineNum = 55;BA.debugLine="J.tag = tag";
_j._tag /*Object*/  = (Object)(_tag);
 //BA.debugLineNum = 56;BA.debugLine="jobs.Put(data.url, J)";
_jobs.Put((Object)(_data.url /*String*/ ),(Object)(_j));
 //BA.debugLineNum = 57;BA.debugLine="J.Download(data.url)";
_j._download /*String*/ (_data.url /*String*/ );
 //BA.debugLineNum = 58;BA.debugLine="If timer1.Enabled = False Then StartTimer(data.Ta";
if (_timer1.getEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
_starttimer(_data.Target /*Object*/ );};
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _starttimer(Object _target) throws Exception{
anywheresoftware.b4a.objects.NotificationWrapper _n = null;
 //BA.debugLineNum = 26;BA.debugLine="Private Sub StartTimer (Target As Object)";
 //BA.debugLineNum = 27;BA.debugLine="Dim n As Notification";
_n = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 28;BA.debugLine="n.Initialize";
_n.Initialize();
 //BA.debugLineNum = 29;BA.debugLine="n.Icon = \"icon\"";
_n.setIcon("icon");
 //BA.debugLineNum = 30;BA.debugLine="n.Vibrate = False";
_n.setVibrate(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 31;BA.debugLine="n.Sound = False";
_n.setSound(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 32;BA.debugLine="n.Light = False";
_n.setLight(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 33;BA.debugLine="n.SetInfo(\"Conectando con el servidor...\", \"\", Ta";
_n.SetInfoNew(processBA,BA.ObjectToCharSequence("Conectando con el servidor..."),BA.ObjectToCharSequence(""),_target);
 //BA.debugLineNum = 34;BA.debugLine="Service.StartForeground(1, n)";
mostCurrent._service.StartForeground((int) (1),(android.app.Notification)(_n.getObject()));
 //BA.debugLineNum = 35;BA.debugLine="timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 36;BA.debugLine="pw.PartialLock";
_pw.PartialLock(processBA);
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
cepave.geovin.httpjob _job = null;
cepave.geovin.downloadservice._jobtag _jt = null;
 //BA.debugLineNum = 78;BA.debugLine="Sub timer1_tick";
 //BA.debugLineNum = 79;BA.debugLine="For Each job As HttpJob In jobs.Values";
{
final anywheresoftware.b4a.BA.IterableList group1 = _jobs.Values();
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_job = (cepave.geovin.httpjob)(group1.Get(index1));
 //BA.debugLineNum = 80;BA.debugLine="Dim jt As JobTag = job.Tag";
_jt = (cepave.geovin.downloadservice._jobtag)(_job._tag /*Object*/ );
 //BA.debugLineNum = 81;BA.debugLine="If jt.CountingStream.IsInitialized Then";
if (_jt.CountingStream /*anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput*/ .IsInitialized()) { 
 //BA.debugLineNum = 82;BA.debugLine="CallSub3(jt.Data.Target, jt.Data.EventName & \"_";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .Target /*Object*/ ,_jt.Data /*cepave.geovin.downloadservice._downloaddata*/ .EventName /*String*/ +"_Progress",(Object)(_jt.CountingStream /*anywheresoftware.b4a.randomaccessfile.CountingStreams.CountingOutput*/ .getCount()),(Object)(_jt.Total /*long*/ ));
 };
 }
};
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
}
