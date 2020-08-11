package cepave.geovin;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static boolean _timeron = false;
public static String _valorvinchuca = "";
public static String _dateandtime = "";
public static int _idproyecto = 0;
public static String _longitud = "";
public static String _latitud = "";
public static String _fotopath0 = "";
public static String _fotopath1 = "";
public static String _fotopath2 = "";
public static String _fotopath3 = "";
public static String _struserid = "";
public static String _strusername = "";
public static String _struserlocation = "";
public static String _struseremail = "";
public static String _struserfullname = "";
public static String _struserorg = "";
public static String _strusertipousuario = "";
public static String _strusergroup = "";
public static String _username = "";
public static String _pass = "";
public static boolean _modooffline = false;
public static boolean _forceoffline = false;
public static String _savedir = "";
public static String _msjprivadouser = "";
public static boolean _msjprivadoleido = false;
public static String _currentproject = "";
public static String _datecurrentproject = "";
public static String _deviceid = "";
public static String _serverpath = "";
public static String _subfolder = "";
public static anywheresoftware.b4a.objects.collections.List _speciesmap = null;
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static String _lang = "";
public static anywheresoftware.b4a.objects.collections.List _userreportsserver = null;
public static anywheresoftware.b4a.objects.collections.List _userreportsinternal = null;
public static anywheresoftware.b4a.objects.collections.List _reportlistupload = null;
public static anywheresoftware.b4a.objects.collections.List _fotolistupload = null;
public static int _numreportuploaded = 0;
public static int _numfotosuploaded = 0;
public static com.spinter.uploadfilephp.UploadFilePhp _up1 = null;
public static String _currentinternetcheck = "";
public static boolean _buscarupdate = false;
public static String _versionactual = "";
public static String _serverversion = "";
public static String _servernewstitulo = "";
public static String _servernewstext = "";
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgbload = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoffline = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.downloadservice _downloadservice = null;
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

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (frmprincipal.mostCurrent != null);
vis = vis | (frmfotos.mostCurrent != null);
vis = vis | (frmlocalizacion.mostCurrent != null);
vis = vis | (frmabout.mostCurrent != null);
vis = vis | (frmaprender_chagas.mostCurrent != null);
vis = vis | (frmcamara.mostCurrent != null);
vis = vis | (frmcomofotos.mostCurrent != null);
vis = vis | (frmdatosanteriores.mostCurrent != null);
vis = vis | (frmeditprofile.mostCurrent != null);
vis = vis | (frmespecies.mostCurrent != null);
vis = vis | (frmidentificacionnew.mostCurrent != null);
vis = vis | (frmlogin.mostCurrent != null);
vis = vis | (frmpoliticadatos.mostCurrent != null);
vis = vis | (frmrecomendaciones.mostCurrent != null);
vis = vis | (register.mostCurrent != null);
return vis;}
public static void  _activity_create(boolean _firsttime) throws Exception{
ResumableSub_Activity_Create rsub = new ResumableSub_Activity_Create(null,_firsttime);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Create extends BA.ResumableSub {
public ResumableSub_Activity_Create(cepave.geovin.main parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
cepave.geovin.main parent;
boolean _firsttime;
anywheresoftware.b4a.phone.Phone _p = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 115;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 116;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 118;BA.debugLine="If Starter.auth.CurrentUser.IsInitialized Then St";
if (true) break;

case 1:
//if
this.state = 6;
if (parent.mostCurrent._starter._auth /*anywheresoftware.b4a.objects.FirebaseAuthWrapper*/ .getCurrentUser().IsInitialized()) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
parent.mostCurrent._starter._auth /*anywheresoftware.b4a.objects.FirebaseAuthWrapper*/ .SignOutFromGoogle();
if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 121;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 13;
return;
case 13:
//C
this.state = 7;
;
 //BA.debugLineNum = 122;BA.debugLine="deviceID = Starter.UpdateFCMToken";
parent._deviceid = parent.mostCurrent._starter._updatefcmtoken /*String*/ ();
 //BA.debugLineNum = 124;BA.debugLine="Log(\"deviceID_Main:\" & deviceID)";
anywheresoftware.b4a.keywords.Common.LogImpl("2131082","deviceID_Main:"+parent._deviceid,0);
 //BA.debugLineNum = 126;BA.debugLine="Starter.dbdir = DBUtils.CopyDBFromAssets(\"databas";
parent.mostCurrent._starter._dbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"database.db");
 //BA.debugLineNum = 127;BA.debugLine="Starter.speciesDBDir = DBUtils.CopyDBFromAssets(\"";
parent.mostCurrent._starter._speciesdbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"speciesdb.db");
 //BA.debugLineNum = 130;BA.debugLine="BuscarConfiguracion";
_buscarconfiguracion();
 //BA.debugLineNum = 132;BA.debugLine="Activity.LoadLayout(\"layload\")";
parent.mostCurrent._activity.LoadLayout("layload",mostCurrent.activityBA);
 //BA.debugLineNum = 134;BA.debugLine="If lang = \"es\" Then";
if (true) break;

case 7:
//if
this.state = 12;
if ((parent._lang).equals("es")) { 
this.state = 9;
}else if((parent._lang).equals("en")) { 
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 135;BA.debugLine="lblEstado.Text = \"Comprobando conexión a interne";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Comprobando conexión a internet"));
 //BA.debugLineNum = 136;BA.debugLine="btnOffline.Text = \"Trabajar sin conexión\"";
parent.mostCurrent._btnoffline.setText(BA.ObjectToCharSequence("Trabajar sin conexión"));
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 138;BA.debugLine="lblEstado.Text = \"Checking the connection to the";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Checking the connection to the internet"));
 //BA.debugLineNum = 139;BA.debugLine="btnOffline.Text = \"Work offline\"";
parent.mostCurrent._btnoffline.setText(BA.ObjectToCharSequence("Work offline"));
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 141;BA.debugLine="timer1.Initialize(\"Timer1\", 1000)";
parent._timer1.Initialize(processBA,"Timer1",(long) (1000));
 //BA.debugLineNum = 142;BA.debugLine="timer1.Enabled = True";
parent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 143;BA.debugLine="timerOn = True";
parent._timeron = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 144;BA.debugLine="buscarUpdate = True";
parent._buscarupdate = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 257;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 258;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 259;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 260;BA.debugLine="If Msgbox2(\"Salir de la aplicación?\", \"SALIR\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Salir de la aplicación?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 261;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 262;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 264;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 267;BA.debugLine="If Msgbox2(\"Exit the app?\", \"EXIT\", \"YES\", \"\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Exit the app?"),BA.ObjectToCharSequence("EXIT"),"YES","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 268;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 269;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 271;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 //BA.debugLineNum = 276;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 326;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 327;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 328;BA.debugLine="End Sub";
return "";
}
public static void  _activity_resume() throws Exception{
ResumableSub_Activity_Resume rsub = new ResumableSub_Activity_Resume(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Resume extends BA.ResumableSub {
public ResumableSub_Activity_Resume(cepave.geovin.main parent) {
this.parent = parent;
}
cepave.geovin.main parent;
String _permission = "";
boolean _result = false;
Object[] group1;
int index1;
int groupLen1;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 280;BA.debugLine="For Each Permission As String In Array(rp.PERMISS";
if (true) break;

case 1:
//for
this.state = 14;
group1 = new Object[]{(Object)(parent._rp.PERMISSION_ACCESS_FINE_LOCATION),(Object)(parent._rp.PERMISSION_CAMERA),(Object)(parent._rp.PERMISSION_WRITE_EXTERNAL_STORAGE)};
index1 = 0;
groupLen1 = group1.length;
this.state = 30;
if (true) break;

case 30:
//C
this.state = 14;
if (index1 < groupLen1) {
this.state = 3;
_permission = BA.ObjectToString(group1[index1]);}
if (true) break;

case 31:
//C
this.state = 30;
index1++;
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 281;BA.debugLine="Sleep(100)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (100));
this.state = 32;
return;
case 32:
//C
this.state = 4;
;
 //BA.debugLineNum = 282;BA.debugLine="rp.CheckAndRequest(Permission)";
parent._rp.CheckAndRequest(processBA,_permission);
 //BA.debugLineNum = 283;BA.debugLine="Wait For Activity_PermissionResult (Permission A";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 33;
return;
case 33:
//C
this.state = 4;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 284;BA.debugLine="If Result = False Then";
if (true) break;

case 4:
//if
this.state = 13;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 285;BA.debugLine="If lang = \"es\" Then";
if (true) break;

case 7:
//if
this.state = 12;
if ((parent._lang).equals("es")) { 
this.state = 9;
}else if((parent._lang).equals("en")) { 
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 286;BA.debugLine="ToastMessageShow(\"Para usar GeoVin, se necesit";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Para usar GeoVin, se necesitan permisos para usar la cámara, el GPS y de escritura"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 288;BA.debugLine="ToastMessageShow(\"To use GeoVin, permissions t";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("To use GeoVin, permissions to use the camera, the GPS and to write data are needed"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 12:
//C
this.state = 13;
;
 //BA.debugLineNum = 291;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 292;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 13:
//C
this.state = 31;
;
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 299;BA.debugLine="If File.ExternalWritable = True Then";

case 14:
//if
this.state = 19;
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 16;
}else {
this.state = 18;
}if (true) break;

case 16:
//C
this.state = 19;
 //BA.debugLineNum = 300;BA.debugLine="Starter.savedir = File.DirRootExternal";
parent.mostCurrent._starter._savedir /*String*/  = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal();
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 302;BA.debugLine="Starter.savedir = File.DirInternal";
parent.mostCurrent._starter._savedir /*String*/  = anywheresoftware.b4a.keywords.Common.File.getDirInternal();
 if (true) break;

case 19:
//C
this.state = 20;
;
 //BA.debugLineNum = 306;BA.debugLine="UpdateInternalDB(\"userFullName\")";
_updateinternaldb("userFullName");
 //BA.debugLineNum = 307;BA.debugLine="UpdateInternalDB(\"userGroup\")";
_updateinternaldb("userGroup");
 //BA.debugLineNum = 311;BA.debugLine="If File.Exists(savedir & \"/GeoVin/\", \"\") = False";
if (true) break;

case 20:
//if
this.state = 23;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent._savedir+"/GeoVin/","")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 22;
}if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 312;BA.debugLine="File.MakeDir(savedir, \"GeoVin\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent._savedir,"GeoVin");
 if (true) break;
;
 //BA.debugLineNum = 318;BA.debugLine="If File.Exists(savedir & \"/GeoVin/sent/\", \"\") = F";

case 23:
//if
this.state = 26;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent._savedir+"/GeoVin/sent/","")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 25;
}if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 319;BA.debugLine="File.MakeDir(savedir & \"/GeoVin/\", \"sent\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent._savedir+"/GeoVin/","sent");
 if (true) break;
;
 //BA.debugLineNum = 322;BA.debugLine="If timerOn Then";

case 26:
//if
this.state = 29;
if (parent._timeron) { 
this.state = 28;
}if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 323;BA.debugLine="timer1.Enabled = True";
parent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 29:
//C
this.state = -1;
;
 //BA.debugLineNum = 325;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _activity_permissionresult(String _permission,boolean _result) throws Exception{
}
public static String  _btnoffline_click() throws Exception{
 //BA.debugLineNum = 635;BA.debugLine="Sub btnOffline_Click";
 //BA.debugLineNum = 637;BA.debugLine="CallSubDelayed2(DownloadService, \"CancelDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"CancelDownload",(Object)(_serverpath+"/connect2/connecttest.php"));
 //BA.debugLineNum = 639;BA.debugLine="modooffline = True";
_modooffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 640;BA.debugLine="forceoffline = True";
_forceoffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 641;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 642;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 643;BA.debugLine="Try";
try { //BA.debugLineNum = 644;BA.debugLine="utilidades.Mensaje(\"Advertencia\", \"MsgIcon.png\"";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Advertencia","MsgIcon.png","Modo sin internet","Has seleccionado el modo sin conexión. GeoVin iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes.","OK","","",anywheresoftware.b4a.keywords.Common.True);
 } 
       catch (Exception e9) {
			processBA.setLastException(e9); //BA.debugLineNum = 646;BA.debugLine="Msgbox(\"Has seleccionado el modo sin conexión.";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Has seleccionado el modo sin conexión. GeoVin iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes."),BA.ObjectToCharSequence("Modo offline"),mostCurrent.activityBA);
 };
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 650;BA.debugLine="Try";
try { //BA.debugLineNum = 651;BA.debugLine="utilidades.Mensaje(\"Warning\", \"MsgIcon.png\", \"O";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Warning","MsgIcon.png","Offline mode","You have selected the offline mode. Geovin will start with the last user that logged in, but won't be able to send data.","OK","","",anywheresoftware.b4a.keywords.Common.True);
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 653;BA.debugLine="Msgbox(\"You have selected the offline mode. Geo";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You have selected the offline mode. Geovin will start with the last user that logged in, but won't be able to send data"),BA.ObjectToCharSequence("Offline mode"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 659;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 //BA.debugLineNum = 661;BA.debugLine="End Sub";
return "";
}
public static String  _buscarconfiguracion() throws Exception{
anywheresoftware.b4a.objects.collections.Map _configmap = null;
anywheresoftware.b4a.objects.collections.List _newconfig = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _deviceactual = "";
anywheresoftware.b4a.objects.collections.Map _usermap = null;
anywheresoftware.b4a.objects.collections.List _newuser = null;
 //BA.debugLineNum = 153;BA.debugLine="Sub BuscarConfiguracion";
 //BA.debugLineNum = 155;BA.debugLine="Starter.sqlDB.Initialize(Starter.dbdir, \"database";
mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ .Initialize(mostCurrent._starter._dbdir /*String*/ ,"database.db",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 156;BA.debugLine="Starter.speciesDB.Initialize(Starter.speciesDBDir";
mostCurrent._starter._speciesdb /*anywheresoftware.b4a.sql.SQL*/ .Initialize(mostCurrent._starter._speciesdbdir /*String*/ ,"speciesdb.db",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 160;BA.debugLine="Dim configmap As Map";
_configmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 161;BA.debugLine="configmap.Initialize";
_configmap.Initialize();
 //BA.debugLineNum = 162;BA.debugLine="configmap = DBUtils.ExecuteMap(Starter.sqlDB, \"SE";
_configmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM appconfig",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 163;BA.debugLine="If configmap = Null Or configmap.IsInitialized =";
if (_configmap== null || _configmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 164;BA.debugLine="Dim newconfig As List";
_newconfig = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 165;BA.debugLine="newconfig.Initialize";
_newconfig.Initialize();
 //BA.debugLineNum = 166;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 167;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 169;BA.debugLine="versionactual = Application.VersionCode";
mostCurrent._versionactual = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Application.getVersionCode());
 //BA.debugLineNum = 170;BA.debugLine="m.Put(\"configID\", \"1\")";
_m.Put((Object)("configID"),(Object)("1"));
 //BA.debugLineNum = 171;BA.debugLine="m.Put(\"appVersion\", versionactual)";
_m.Put((Object)("appVersion"),(Object)(mostCurrent._versionactual));
 //BA.debugLineNum = 172;BA.debugLine="m.Put(\"firstTime\", \"True\")";
_m.Put((Object)("firstTime"),(Object)("True"));
 //BA.debugLineNum = 173;BA.debugLine="m.Put(\"deviceID\", deviceID)";
_m.Put((Object)("deviceID"),(Object)(_deviceid));
 //BA.debugLineNum = 174;BA.debugLine="m.Put(\"applang\", \"es\")";
_m.Put((Object)("applang"),(Object)("es"));
 //BA.debugLineNum = 175;BA.debugLine="newconfig.Add(m)";
_newconfig.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 176;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"appconfig\", ne";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"appconfig",_newconfig);
 }else {
 //BA.debugLineNum = 179;BA.debugLine="Dim configmap As Map";
_configmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 180;BA.debugLine="configmap.Initialize";
_configmap.Initialize();
 //BA.debugLineNum = 181;BA.debugLine="configmap = DBUtils.ExecuteMap(Starter.sqlDB, \"S";
_configmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM appconfig WHERE configID = '1'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 182;BA.debugLine="If configmap = Null Or configmap.IsInitialized =";
if (_configmap== null || _configmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 183;BA.debugLine="deviceID = \"noID\"";
_deviceid = "noID";
 //BA.debugLineNum = 184;BA.debugLine="lang = \"es\"";
_lang = "es";
 }else {
 //BA.debugLineNum = 186;BA.debugLine="deviceID = configmap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_configmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 187;BA.debugLine="lang = configmap.Get(\"applang\")";
_lang = BA.ObjectToString(_configmap.Get((Object)("applang")));
 };
 };
 //BA.debugLineNum = 194;BA.debugLine="Dim deviceactual As String";
_deviceactual = "";
 //BA.debugLineNum = 195;BA.debugLine="deviceactual = utilidades.GetDeviceId";
_deviceactual = mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 196;BA.debugLine="If deviceactual <> deviceID Then";
if ((_deviceactual).equals(_deviceid) == false) { 
 //BA.debugLineNum = 197;BA.debugLine="deviceID = deviceactual";
_deviceid = _deviceactual;
 };
 //BA.debugLineNum = 202;BA.debugLine="Dim userMap As Map";
_usermap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 203;BA.debugLine="userMap.Initialize";
_usermap.Initialize();
 //BA.debugLineNum = 204;BA.debugLine="userMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_usermap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser = 'si'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 205;BA.debugLine="If userMap = Null Or userMap.IsInitialized = Fals";
if (_usermap== null || _usermap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 207;BA.debugLine="username = \"None\"";
_username = "None";
 //BA.debugLineNum = 208;BA.debugLine="pass = \"None\"";
_pass = "None";
 //BA.debugLineNum = 209;BA.debugLine="Dim newUser As List";
_newuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 210;BA.debugLine="newUser.Initialize";
_newuser.Initialize();
 //BA.debugLineNum = 211;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 212;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 213;BA.debugLine="m.Put(\"username\", \"guest\")";
_m.Put((Object)("username"),(Object)("guest"));
 //BA.debugLineNum = 214;BA.debugLine="m.Put(\"lang\", \"es\")";
_m.Put((Object)("lang"),(Object)("es"));
 //BA.debugLineNum = 215;BA.debugLine="m.Put(\"firstuse\", \"True\")";
_m.Put((Object)("firstuse"),(Object)("True"));
 //BA.debugLineNum = 216;BA.debugLine="m.Put(\"lastuser\", \"si\")";
_m.Put((Object)("lastuser"),(Object)("si"));
 //BA.debugLineNum = 217;BA.debugLine="newUser.Add(m)";
_newuser.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 218;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"userconfig\", n";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig",_newuser);
 }else {
 //BA.debugLineNum = 220;BA.debugLine="username = userMap.Get(\"username\")";
_username = BA.ObjectToString(_usermap.Get((Object)("username")));
 //BA.debugLineNum = 221;BA.debugLine="pass = userMap.Get(\"pass\")";
_pass = BA.ObjectToString(_usermap.Get((Object)("pass")));
 //BA.debugLineNum = 222;BA.debugLine="strUserLocation = userMap.Get(\"userlocation\")";
_struserlocation = BA.ObjectToString(_usermap.Get((Object)("userlocation")));
 //BA.debugLineNum = 223;BA.debugLine="strUserFullName = userMap.Get(\"userfullname\")";
_struserfullname = BA.ObjectToString(_usermap.Get((Object)("userfullname")));
 //BA.debugLineNum = 224;BA.debugLine="strUserOrg = userMap.Get(\"userorg\")";
_struserorg = BA.ObjectToString(_usermap.Get((Object)("userorg")));
 //BA.debugLineNum = 225;BA.debugLine="strUserTipoUsuario = userMap.Get(\"usertipousuari";
_strusertipousuario = BA.ObjectToString(_usermap.Get((Object)("usertipousuario")));
 //BA.debugLineNum = 226;BA.debugLine="strUserGroup = userMap.Get(\"usergroup\")";
_strusergroup = BA.ObjectToString(_usermap.Get((Object)("usergroup")));
 };
 //BA.debugLineNum = 232;BA.debugLine="speciesMap.Initialize";
_speciesmap.Initialize();
 //BA.debugLineNum = 233;BA.debugLine="speciesMap = DBUtils.ExecuteMemoryTable(Starter.s";
_speciesmap = mostCurrent._dbutils._executememorytable /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._starter._speciesdb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM especies",(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _cargarbienvenidos_nuevo() throws Exception{
cepave.geovin.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 482;BA.debugLine="Sub CargarBienvenidos_Nuevo";
 //BA.debugLineNum = 484;BA.debugLine="If modooffline = False Then";
if (_modooffline==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 487;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 488;BA.debugLine="dd.url = serverPath & \"/connect2/checkmessages.p";
_dd.url /*String*/  = _serverpath+"/connect2/checkmessages.php?deviceID="+_deviceid;
 //BA.debugLineNum = 489;BA.debugLine="dd.EventName = \"checkMessages\"";
_dd.EventName /*String*/  = "checkMessages";
 //BA.debugLineNum = 490;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 491;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 }else {
 //BA.debugLineNum = 493;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 494;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 495;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmprincipal.getObject()));
 };
 //BA.debugLineNum = 497;BA.debugLine="End Sub";
return "";
}
public static String  _checkmessages_complete(cepave.geovin.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
String _msg = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
anywheresoftware.b4a.objects.collections.Map _usrmap = null;
 //BA.debugLineNum = 498;BA.debugLine="Sub checkMessages_Complete(Job As HttpJob)";
 //BA.debugLineNum = 499;BA.debugLine="Log(\"Mensajes chequeados: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("228901377","Mensajes chequeados: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 500;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 502;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 503;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 504;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 505;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 506;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 507;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 508;BA.debugLine="modooffline = False";
_modooffline = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 509;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 510;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 511;BA.debugLine="Log(\"no hay mensajes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("228901389","no hay mensajes",0);
 }else if((_act).equals("Error")) { 
 }else if((_act).equals("MensajesOK")) { 
 //BA.debugLineNum = 516;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 517;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 518;BA.debugLine="Try";
try { //BA.debugLineNum = 521;BA.debugLine="For i = 0 To numresults - 1";
{
final int step18 = 1;
final int limit18 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit18 ;_i = _i + step18 ) {
 //BA.debugLineNum = 522;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 523;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 524;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 531;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 532;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 533;BA.debugLine="Map1.Put(\"serverid\", newpunto.Get(\"serverid\"))";
_map1.Put((Object)("serverid"),_newpunto.Get((Object)("serverid")));
 //BA.debugLineNum = 535;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","valorOrganismo",_newpunto.Get((Object)("mensaje_type")),_map1);
 //BA.debugLineNum = 536;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","estadoValidacion",(Object)("validado"),_map1);
 //BA.debugLineNum = 538;BA.debugLine="If newpunto.Get(\"mensaje_type\") = \"invalido\" Th";
if ((_newpunto.Get((Object)("mensaje_type"))).equals((Object)("invalido"))) { 
 //BA.debugLineNum = 540;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 541;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identificación","MsgMensaje.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"","","Ok gracias!",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 543;BA.debugLine="msg = utilidades.Mensaje(\"Identification\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identification","MsgMensaje.png","Private notification",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"","","Ok thank you!",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_newpunto.Get((Object)("mensaje_type"))).equals((Object)("ninfa"))) { 
 //BA.debugLineNum = 549;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 550;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identificación","MsgMensaje.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"","","Ok gracias!",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 552;BA.debugLine="msg = utilidades.Mensaje(\"Identification\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identification","MsgMensaje.png","Private notification",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"","","Ok thank you!",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_newpunto.Get((Object)("mensaje_type"))).equals((Object)("novinchuca"))) { 
 //BA.debugLineNum = 557;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 558;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identificación","MsgMensaje.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"","","Ok gracias!",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 560;BA.debugLine="msg = utilidades.Mensaje(\"Identification\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identification","MsgMensaje.png","Private notification",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"","","Ok thank you!",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_newpunto.Get((Object)("mensaje_type"))).equals((Object)("sospechoso"))) { 
 //BA.debugLineNum = 565;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 566;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identificación","MsgVinchuca.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"Ver detalles","","Ok, gracias!",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 568;BA.debugLine="msg = utilidades.Mensaje(\"Identification\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identification","MsgVinchuca.png","Private notification",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"View details","","Ok, thank you!",anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 572;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 573;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identificación","MsgVinchuca.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"Ver detalles","","Ok, gracias!",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 575;BA.debugLine="msg = utilidades.Mensaje(\"Identification\", \"";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Identification","MsgVinchuca.png","Private notification",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"View details","","Ok, thank you!",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 579;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 581;BA.debugLine="frmDatosAnteriores.notificacion = True";
mostCurrent._frmdatosanteriores._notificacion /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 582;BA.debugLine="frmDatosAnteriores.serverIdNum = newpunto.Get";
mostCurrent._frmdatosanteriores._serveridnum /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("serverid")));
 }else {
 };
 };
 }
};
 } 
       catch (Exception e65) {
			processBA.setLastException(e65); //BA.debugLineNum = 589;BA.debugLine="ToastMessageShow(\"Error...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 590;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("228901468",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 };
 //BA.debugLineNum = 595;BA.debugLine="Dim usrMap As Map";
_usrmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 596;BA.debugLine="usrMap.Initialize";
_usrmap.Initialize();
 //BA.debugLineNum = 597;BA.debugLine="usrMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_usrmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser = 'si'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 598;BA.debugLine="If usrMap = Null Or usrMap.IsInitialized = False";
if (_usrmap== null || _usrmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 600;BA.debugLine="username = \"guest\"";
_username = "guest";
 //BA.debugLineNum = 601;BA.debugLine="lang = \"es\"";
_lang = "es";
 }else {
 //BA.debugLineNum = 604;BA.debugLine="username = usrMap.Get(\"username\")";
_username = BA.ObjectToString(_usrmap.Get((Object)("username")));
 //BA.debugLineNum = 605;BA.debugLine="pass = usrMap.Get(\"pass\")";
_pass = BA.ObjectToString(_usrmap.Get((Object)("pass")));
 //BA.debugLineNum = 606;BA.debugLine="lang = usrMap.Get(\"lang\")";
_lang = BA.ObjectToString(_usrmap.Get((Object)("lang")));
 };
 //BA.debugLineNum = 609;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 610;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 611;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmprincipal.getObject()));
 }else {
 //BA.debugLineNum = 614;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 615;BA.debugLine="Msgbox(\"No tienes conexión a Internet, no puede";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes conexión a Internet, no puedes recibir los mensajes de los revisores"),BA.ObjectToCharSequence("Advertencia"),mostCurrent.activityBA);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 617;BA.debugLine="Msgbox(\"You are not connected to the internet,";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You are not connected to the internet, you cannot receive any messages from the experts"),BA.ObjectToCharSequence("Warning"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 620;BA.debugLine="modooffline = True";
_modooffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 621;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 622;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 623;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmprincipal.getObject()));
 };
 //BA.debugLineNum = 626;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 627;BA.debugLine="End Sub";
return "";
}
public static String  _comparedb(String _tipo) throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _serverreportmap = null;
String _sent = "";
String _lat = "";
String _lng = "";
String _foto1path = "";
String _foto2path = "";
String _foto3path = "";
String _foto4path = "";
String _verificado = "";
String _serverid = "";
String _privado = "";
String _gpsdetect = "";
String _wifidetect = "";
String _mapadetect = "";
anywheresoftware.b4a.objects.collections.List _newinternalreport = null;
anywheresoftware.b4a.objects.collections.Map _mapupdateinternal = null;
boolean _reportfound = false;
anywheresoftware.b4a.objects.collections.Map _internalreportmap = null;
String _reportid = "";
int _j = 0;
anywheresoftware.b4a.objects.collections.Map _mapupdate = null;
 //BA.debugLineNum = 762;BA.debugLine="Sub CompareDB(tipo As String)";
 //BA.debugLineNum = 764;BA.debugLine="reportListUpload.Initialize";
_reportlistupload.Initialize();
 //BA.debugLineNum = 765;BA.debugLine="fotoListUpload.Initialize";
_fotolistupload.Initialize();
 //BA.debugLineNum = 766;BA.debugLine="userReportsInternal.Initialize";
_userreportsinternal.Initialize();
 //BA.debugLineNum = 767;BA.debugLine="numReportUploaded = 0";
_numreportuploaded = (int) (0);
 //BA.debugLineNum = 768;BA.debugLine="numFotosUploaded = 0";
_numfotosuploaded = (int) (0);
 //BA.debugLineNum = 770;BA.debugLine="If tipo = \"user\" Then";
if ((_tipo).equals("user")) { 
 //BA.debugLineNum = 771;BA.debugLine="userReportsInternal = DBUtils.ExecuteListOfMaps(";
_userreportsinternal = mostCurrent._dbutils._executelistofmaps /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM markers_local WHERE usuario=?",new String[]{_username},(int) (0));
 }else if((_tipo).equals("device")) { 
 //BA.debugLineNum = 773;BA.debugLine="userReportsInternal = DBUtils.ExecuteListOfMaps(";
_userreportsinternal = mostCurrent._dbutils._executelistofmaps /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM markers_local WHERE deviceID=?",new String[]{_deviceid},(int) (0));
 };
 //BA.debugLineNum = 775;BA.debugLine="If userReportsInternal = Null Or userReportsInter";
if (_userreportsinternal== null || _userreportsinternal.IsInitialized()==anywheresoftware.b4a.keywords.Common.False || _userreportsinternal.getSize()==0) { 
 //BA.debugLineNum = 777;BA.debugLine="Log(\"No internal data\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229163535","No internal data",0);
 //BA.debugLineNum = 778;BA.debugLine="For i = 0 To userReportsServer.Size - 1";
{
final int step13 = 1;
final int limit13 = (int) (_userreportsserver.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit13 ;_i = _i + step13 ) {
 //BA.debugLineNum = 779;BA.debugLine="Try";
try { //BA.debugLineNum = 780;BA.debugLine="Dim serverReportMap As Map";
_serverreportmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 781;BA.debugLine="serverReportMap = userReportsServer.Get(i)";
_serverreportmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_userreportsserver.Get(_i)));
 //BA.debugLineNum = 782;BA.debugLine="Dim sent As String = \"si\"";
_sent = "si";
 //BA.debugLineNum = 783;BA.debugLine="Dim valorVinchuca As String = serverReportMap.";
_valorvinchuca = BA.ObjectToString(_serverreportmap.Get((Object)("valorVinchuca")));
 //BA.debugLineNum = 784;BA.debugLine="Dim lat As String = serverReportMap.Get(\"lat\")";
_lat = BA.ObjectToString(_serverreportmap.Get((Object)("lat")));
 //BA.debugLineNum = 785;BA.debugLine="Dim lng As String = serverReportMap.Get(\"lng\")";
_lng = BA.ObjectToString(_serverreportmap.Get((Object)("lng")));
 //BA.debugLineNum = 786;BA.debugLine="Dim dateandtime As String = serverReportMap.Ge";
_dateandtime = BA.ObjectToString(_serverreportmap.Get((Object)("dateandtime")));
 //BA.debugLineNum = 787;BA.debugLine="Dim foto1path As String = serverReportMap.Get(";
_foto1path = BA.ObjectToString(_serverreportmap.Get((Object)("foto1path")));
 //BA.debugLineNum = 788;BA.debugLine="Dim foto2path As String = serverReportMap.Get(";
_foto2path = BA.ObjectToString(_serverreportmap.Get((Object)("foto2path")));
 //BA.debugLineNum = 789;BA.debugLine="Dim foto3path As String = serverReportMap.Get(";
_foto3path = BA.ObjectToString(_serverreportmap.Get((Object)("foto3path")));
 //BA.debugLineNum = 790;BA.debugLine="Dim foto4path As String = serverReportMap.Get(";
_foto4path = BA.ObjectToString(_serverreportmap.Get((Object)("foto4path")));
 //BA.debugLineNum = 791;BA.debugLine="Dim verificado As String = serverReportMap.Get";
_verificado = BA.ObjectToString(_serverreportmap.Get((Object)("verificado")));
 //BA.debugLineNum = 792;BA.debugLine="Dim serverid As String = serverReportMap.Get(\"";
_serverid = BA.ObjectToString(_serverreportmap.Get((Object)("serverid")));
 //BA.debugLineNum = 793;BA.debugLine="Dim privado As String = serverReportMap.Get(\"p";
_privado = BA.ObjectToString(_serverreportmap.Get((Object)("privado")));
 //BA.debugLineNum = 794;BA.debugLine="Dim gpsdetect As String = serverReportMap.Get(";
_gpsdetect = BA.ObjectToString(_serverreportmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 795;BA.debugLine="Dim wifidetect As String = serverReportMap.Get";
_wifidetect = BA.ObjectToString(_serverreportmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 796;BA.debugLine="Dim mapadetect As String = serverReportMap.Get";
_mapadetect = BA.ObjectToString(_serverreportmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 799;BA.debugLine="Dim newInternalReport As List";
_newinternalreport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 800;BA.debugLine="newInternalReport.Initialize";
_newinternalreport.Initialize();
 //BA.debugLineNum = 801;BA.debugLine="Dim mapUpdateInternal As Map";
_mapupdateinternal = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 802;BA.debugLine="mapUpdateInternal.Initialize";
_mapupdateinternal.Initialize();
 //BA.debugLineNum = 803;BA.debugLine="mapUpdateInternal.Put(\"usuario\", username)";
_mapupdateinternal.Put((Object)("usuario"),(Object)(_username));
 //BA.debugLineNum = 804;BA.debugLine="mapUpdateInternal.Put(\"evalsent\", sent)";
_mapupdateinternal.Put((Object)("evalsent"),(Object)(_sent));
 //BA.debugLineNum = 805;BA.debugLine="mapUpdateInternal.Put(\"valororganismo\", valorV";
_mapupdateinternal.Put((Object)("valororganismo"),(Object)(_valorvinchuca));
 //BA.debugLineNum = 806;BA.debugLine="mapUpdateInternal.Put(\"decimalLatitude\", lat)";
_mapupdateinternal.Put((Object)("decimalLatitude"),(Object)(_lat));
 //BA.debugLineNum = 807;BA.debugLine="mapUpdateInternal.Put(\"decimalLongitude\", lng)";
_mapupdateinternal.Put((Object)("decimalLongitude"),(Object)(_lng));
 //BA.debugLineNum = 808;BA.debugLine="mapUpdateInternal.Put(\"georeferencedDate\", dat";
_mapupdateinternal.Put((Object)("georeferencedDate"),(Object)(_dateandtime));
 //BA.debugLineNum = 809;BA.debugLine="mapUpdateInternal.Put(\"foto1\", foto1path)";
_mapupdateinternal.Put((Object)("foto1"),(Object)(_foto1path));
 //BA.debugLineNum = 810;BA.debugLine="mapUpdateInternal.Put(\"foto2\", foto2path)";
_mapupdateinternal.Put((Object)("foto2"),(Object)(_foto2path));
 //BA.debugLineNum = 811;BA.debugLine="mapUpdateInternal.Put(\"foto3\", foto3path)";
_mapupdateinternal.Put((Object)("foto3"),(Object)(_foto3path));
 //BA.debugLineNum = 812;BA.debugLine="mapUpdateInternal.Put(\"foto4\", foto4path)";
_mapupdateinternal.Put((Object)("foto4"),(Object)(_foto4path));
 //BA.debugLineNum = 813;BA.debugLine="mapUpdateInternal.Put(\"validacion\", verificado";
_mapupdateinternal.Put((Object)("validacion"),(Object)(_verificado));
 //BA.debugLineNum = 814;BA.debugLine="mapUpdateInternal.Put(\"serverid\", serverid)";
_mapupdateinternal.Put((Object)("serverid"),(Object)(_serverid));
 //BA.debugLineNum = 815;BA.debugLine="mapUpdateInternal.Put(\"privado\", privado)";
_mapupdateinternal.Put((Object)("privado"),(Object)(_privado));
 //BA.debugLineNum = 816;BA.debugLine="mapUpdateInternal.Put(\"gpsdetect\", gpsdetect)";
_mapupdateinternal.Put((Object)("gpsdetect"),(Object)(_gpsdetect));
 //BA.debugLineNum = 817;BA.debugLine="mapUpdateInternal.Put(\"wifidetect\", wifidetect";
_mapupdateinternal.Put((Object)("wifidetect"),(Object)(_wifidetect));
 //BA.debugLineNum = 818;BA.debugLine="mapUpdateInternal.Put(\"mapadetect\", mapadetect";
_mapupdateinternal.Put((Object)("mapadetect"),(Object)(_mapadetect));
 //BA.debugLineNum = 820;BA.debugLine="newInternalReport.Add(mapUpdateInternal)";
_newinternalreport.Add((Object)(_mapupdateinternal.getObject()));
 //BA.debugLineNum = 821;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"markers_loca";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local",_newinternalreport);
 //BA.debugLineNum = 824;BA.debugLine="If serverReportMap.Get(\"foto1sent\") = \"no\" The";
if ((_serverreportmap.Get((Object)("foto1sent"))).equals((Object)("no"))) { 
 //BA.debugLineNum = 825;BA.debugLine="fotoListUpload.Add(serverReportMap.Get(\"foto1";
_fotolistupload.Add(_serverreportmap.Get((Object)("foto1path")));
 };
 //BA.debugLineNum = 827;BA.debugLine="If serverReportMap.Get(\"foto2sent\") = \"no\" The";
if ((_serverreportmap.Get((Object)("foto2sent"))).equals((Object)("no"))) { 
 //BA.debugLineNum = 828;BA.debugLine="fotoListUpload.Add(serverReportMap.Get(\"foto2";
_fotolistupload.Add(_serverreportmap.Get((Object)("foto2path")));
 };
 } 
       catch (Exception e61) {
			processBA.setLastException(e61); //BA.debugLineNum = 831;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("229163589",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 }
};
 }else {
 //BA.debugLineNum = 838;BA.debugLine="Log(\"Comparando INTERNA -> SERVER\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229163596","Comparando INTERNA -> SERVER",0);
 //BA.debugLineNum = 840;BA.debugLine="Dim reportfound As Boolean";
_reportfound = false;
 //BA.debugLineNum = 841;BA.debugLine="Dim internalReportMap As Map";
_internalreportmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 842;BA.debugLine="Dim serverReportMap As Map";
_serverreportmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 843;BA.debugLine="For i = 0 To userReportsInternal.Size - 1";
{
final int step69 = 1;
final int limit69 = (int) (_userreportsinternal.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit69 ;_i = _i + step69 ) {
 //BA.debugLineNum = 844;BA.debugLine="internalReportMap = userReportsInternal.Get(i)";
_internalreportmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_userreportsinternal.Get(_i)));
 //BA.debugLineNum = 845;BA.debugLine="Dim reportid As String = internalReportMap.Get(";
_reportid = BA.ObjectToString(_internalreportmap.Get((Object)("id")));
 //BA.debugLineNum = 847;BA.debugLine="reportfound = False";
_reportfound = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 850;BA.debugLine="For j = 0 To userReportsServer.Size - 1";
{
final int step73 = 1;
final int limit73 = (int) (_userreportsserver.getSize()-1);
_j = (int) (0) ;
for (;_j <= limit73 ;_j = _j + step73 ) {
 //BA.debugLineNum = 851;BA.debugLine="Try";
try { //BA.debugLineNum = 852;BA.debugLine="serverReportMap = userReportsServer.Get(j)";
_serverreportmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_userreportsserver.Get(_j)));
 //BA.debugLineNum = 855;BA.debugLine="If internalReportMap.Get(\"serverid\") = server";
if ((_internalreportmap.Get((Object)("serverid"))).equals(_serverreportmap.Get((Object)("serverid"))) || (_internalreportmap.Get((Object)("georeferenceddate"))).equals(_serverreportmap.Get((Object)("dateandtime"))) && (_internalreportmap.Get((Object)("decimallatitude"))).equals(_serverreportmap.Get((Object)("lat"))) && (_internalreportmap.Get((Object)("decimallongitude"))).equals(_serverreportmap.Get((Object)("lng")))) { 
 //BA.debugLineNum = 861;BA.debugLine="reportfound = True";
_reportfound = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 864;BA.debugLine="Dim MapUpdate As Map";
_mapupdate = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 865;BA.debugLine="MapUpdate.Initialize";
_mapupdate.Initialize();
 //BA.debugLineNum = 866;BA.debugLine="MapUpdate.Put(\"id\", reportid)";
_mapupdate.Put((Object)("id"),(Object)(_reportid));
 //BA.debugLineNum = 867;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","serverid",_serverreportmap.Get((Object)("serverid")),_mapupdate);
 //BA.debugLineNum = 868;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","valorOrganismo",_serverreportmap.Get((Object)("valorVinchuca")),_mapupdate);
 //BA.debugLineNum = 869;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","decimalLatitude",_serverreportmap.Get((Object)("lat")),_mapupdate);
 //BA.debugLineNum = 870;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","decimalLongitude",_serverreportmap.Get((Object)("lng")),_mapupdate);
 //BA.debugLineNum = 871;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","georeferencedDate",_serverreportmap.Get((Object)("dateandtime")),_mapupdate);
 //BA.debugLineNum = 872;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto1",_serverreportmap.Get((Object)("foto1path")),_mapupdate);
 //BA.debugLineNum = 873;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto2",_serverreportmap.Get((Object)("foto2path")),_mapupdate);
 //BA.debugLineNum = 874;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto3",_serverreportmap.Get((Object)("foto3path")),_mapupdate);
 //BA.debugLineNum = 875;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto4",_serverreportmap.Get((Object)("foto4path")),_mapupdate);
 //BA.debugLineNum = 876;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","estadovalidacion",_serverreportmap.Get((Object)("verificado")),_mapupdate);
 //BA.debugLineNum = 877;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","privado",_serverreportmap.Get((Object)("privado")),_mapupdate);
 //BA.debugLineNum = 878;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","gpsdetect",_serverreportmap.Get((Object)("gpsdetect")),_mapupdate);
 //BA.debugLineNum = 879;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","wifidetect",_serverreportmap.Get((Object)("wifidetect")),_mapupdate);
 //BA.debugLineNum = 880;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","mapadetect",_serverreportmap.Get((Object)("mapadetect")),_mapupdate);
 //BA.debugLineNum = 881;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","evalsent",(Object)("si"),_mapupdate);
 //BA.debugLineNum = 884;BA.debugLine="If serverReportMap.Get(\"foto1sent\") = \"no\" T";
if ((_serverreportmap.Get((Object)("foto1sent"))).equals((Object)("no"))) { 
 //BA.debugLineNum = 885;BA.debugLine="fotoListUpload.Add(serverReportMap.Get(\"fot";
_fotolistupload.Add(_serverreportmap.Get((Object)("foto1path")));
 };
 //BA.debugLineNum = 887;BA.debugLine="If serverReportMap.Get(\"foto2sent\") = \"no\" T";
if ((_serverreportmap.Get((Object)("foto2sent"))).equals((Object)("no"))) { 
 //BA.debugLineNum = 888;BA.debugLine="fotoListUpload.Add(serverReportMap.Get(\"fot";
_fotolistupload.Add(_serverreportmap.Get((Object)("foto2path")));
 };
 //BA.debugLineNum = 890;BA.debugLine="Exit";
if (true) break;
 };
 } 
       catch (Exception e105) {
			processBA.setLastException(e105); //BA.debugLineNum = 893;BA.debugLine="Log(\"error reading userReportsServer: \" & j &";
anywheresoftware.b4a.keywords.Common.LogImpl("229163651","error reading userReportsServer: "+BA.NumberToString(_j)+" and userReportInternal: "+BA.NumberToString(_i),0);
 };
 }
};
 //BA.debugLineNum = 898;BA.debugLine="Try";
try { //BA.debugLineNum = 899;BA.debugLine="If reportfound = False Then";
if (_reportfound==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 900;BA.debugLine="Log(\"report not found\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229163658","report not found",0);
 //BA.debugLineNum = 902;BA.debugLine="If internalReportMap.Get(\"decimallatitude\") <";
if (_internalreportmap.Get((Object)("decimallatitude"))!= null && (_internalreportmap.Get((Object)("decimallatitude"))).equals((Object)("0.000000")) == false) { 
 //BA.debugLineNum = 903;BA.debugLine="Log(\"adding report not found to list\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229163661","adding report not found to list",0);
 //BA.debugLineNum = 904;BA.debugLine="reportListUpload.Add(internalReportMap)";
_reportlistupload.Add((Object)(_internalreportmap.getObject()));
 };
 };
 } 
       catch (Exception e117) {
			processBA.setLastException(e117); //BA.debugLineNum = 908;BA.debugLine="Log(\"error in reportfound Boolean, skipping\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229163666","error in reportfound Boolean, skipping",0);
 };
 }
};
 //BA.debugLineNum = 914;BA.debugLine="Log(\"Comparando SERVER -> INTERNA\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229163672","Comparando SERVER -> INTERNA",0);
 //BA.debugLineNum = 918;BA.debugLine="For j = 0 To userReportsServer.Size - 1";
{
final int step121 = 1;
final int limit121 = (int) (_userreportsserver.getSize()-1);
_j = (int) (0) ;
for (;_j <= limit121 ;_j = _j + step121 ) {
 //BA.debugLineNum = 919;BA.debugLine="Try";
try { //BA.debugLineNum = 921;BA.debugLine="serverReportMap = userReportsServer.Get(j)";
_serverreportmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_userreportsserver.Get(_j)));
 //BA.debugLineNum = 922;BA.debugLine="reportfound = False";
_reportfound = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 923;BA.debugLine="For i = 0 To userReportsInternal.Size - 1";
{
final int step125 = 1;
final int limit125 = (int) (_userreportsinternal.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit125 ;_i = _i + step125 ) {
 //BA.debugLineNum = 925;BA.debugLine="internalReportMap = userReportsInternal.Get(i)";
_internalreportmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_userreportsinternal.Get(_i)));
 //BA.debugLineNum = 927;BA.debugLine="If internalReportMap.Get(\"serverid\") = serverR";
if ((_internalreportmap.Get((Object)("serverid"))).equals(_serverreportmap.Get((Object)("serverid"))) || (_internalreportmap.Get((Object)("georeferenceddate"))).equals(_serverreportmap.Get((Object)("dateandtime"))) && (_internalreportmap.Get((Object)("decimallatitude"))).equals(_serverreportmap.Get((Object)("lat"))) && (_internalreportmap.Get((Object)("decimallongitude"))).equals(_serverreportmap.Get((Object)("lng")))) { 
 //BA.debugLineNum = 933;BA.debugLine="reportfound = True";
_reportfound = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 934;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 940;BA.debugLine="If reportfound = False Then";
if (_reportfound==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 942;BA.debugLine="Dim serverReportMap As Map";
_serverreportmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 943;BA.debugLine="serverReportMap = userReportsServer.Get(j)";
_serverreportmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_userreportsserver.Get(_j)));
 //BA.debugLineNum = 944;BA.debugLine="Dim sent As String = \"si\"";
_sent = "si";
 //BA.debugLineNum = 945;BA.debugLine="Dim valorVinchuca As String = serverReportMap.";
_valorvinchuca = BA.ObjectToString(_serverreportmap.Get((Object)("valorVinchuca")));
 //BA.debugLineNum = 946;BA.debugLine="Dim lat As String = serverReportMap.Get(\"lat\")";
_lat = BA.ObjectToString(_serverreportmap.Get((Object)("lat")));
 //BA.debugLineNum = 947;BA.debugLine="Dim lng As String = serverReportMap.Get(\"lng\")";
_lng = BA.ObjectToString(_serverreportmap.Get((Object)("lng")));
 //BA.debugLineNum = 948;BA.debugLine="Dim dateandtime As String = serverReportMap.Ge";
_dateandtime = BA.ObjectToString(_serverreportmap.Get((Object)("dateandtime")));
 //BA.debugLineNum = 949;BA.debugLine="Dim foto1path As String = serverReportMap.Get(";
_foto1path = BA.ObjectToString(_serverreportmap.Get((Object)("foto1path")));
 //BA.debugLineNum = 950;BA.debugLine="Dim foto2path As String = serverReportMap.Get(";
_foto2path = BA.ObjectToString(_serverreportmap.Get((Object)("foto2path")));
 //BA.debugLineNum = 951;BA.debugLine="Dim foto3path As String = serverReportMap.Get(";
_foto3path = BA.ObjectToString(_serverreportmap.Get((Object)("foto3path")));
 //BA.debugLineNum = 952;BA.debugLine="Dim foto4path As String = serverReportMap.Get(";
_foto4path = BA.ObjectToString(_serverreportmap.Get((Object)("foto4path")));
 //BA.debugLineNum = 953;BA.debugLine="Dim verificado As String = serverReportMap.Get";
_verificado = BA.ObjectToString(_serverreportmap.Get((Object)("verificado")));
 //BA.debugLineNum = 954;BA.debugLine="Dim serverid As String = serverReportMap.Get(\"";
_serverid = BA.ObjectToString(_serverreportmap.Get((Object)("serverid")));
 //BA.debugLineNum = 955;BA.debugLine="Dim privado As String = serverReportMap.Get(\"p";
_privado = BA.ObjectToString(_serverreportmap.Get((Object)("privado")));
 //BA.debugLineNum = 956;BA.debugLine="Dim gpsdetect As String = serverReportMap.Get(";
_gpsdetect = BA.ObjectToString(_serverreportmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 957;BA.debugLine="Dim wifidetect As String = serverReportMap.Get";
_wifidetect = BA.ObjectToString(_serverreportmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 958;BA.debugLine="Dim mapadetect As String = serverReportMap.Get";
_mapadetect = BA.ObjectToString(_serverreportmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 961;BA.debugLine="Dim newInternalReport As List";
_newinternalreport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 962;BA.debugLine="newInternalReport.Initialize";
_newinternalreport.Initialize();
 //BA.debugLineNum = 963;BA.debugLine="Dim mapUpdateInternal As Map";
_mapupdateinternal = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 964;BA.debugLine="mapUpdateInternal.Initialize";
_mapupdateinternal.Initialize();
 //BA.debugLineNum = 965;BA.debugLine="mapUpdateInternal.Put(\"usuario\", username)";
_mapupdateinternal.Put((Object)("usuario"),(Object)(_username));
 //BA.debugLineNum = 966;BA.debugLine="mapUpdateInternal.Put(\"evalsent\", sent)";
_mapupdateinternal.Put((Object)("evalsent"),(Object)(_sent));
 //BA.debugLineNum = 967;BA.debugLine="mapUpdateInternal.Put(\"valororganismo\", valorV";
_mapupdateinternal.Put((Object)("valororganismo"),(Object)(_valorvinchuca));
 //BA.debugLineNum = 968;BA.debugLine="mapUpdateInternal.Put(\"decimalLatitude\", lat)";
_mapupdateinternal.Put((Object)("decimalLatitude"),(Object)(_lat));
 //BA.debugLineNum = 969;BA.debugLine="mapUpdateInternal.Put(\"decimalLongitude\", lng)";
_mapupdateinternal.Put((Object)("decimalLongitude"),(Object)(_lng));
 //BA.debugLineNum = 970;BA.debugLine="mapUpdateInternal.Put(\"georeferencedDate\", dat";
_mapupdateinternal.Put((Object)("georeferencedDate"),(Object)(_dateandtime));
 //BA.debugLineNum = 971;BA.debugLine="mapUpdateInternal.Put(\"foto1\", foto1path)";
_mapupdateinternal.Put((Object)("foto1"),(Object)(_foto1path));
 //BA.debugLineNum = 972;BA.debugLine="mapUpdateInternal.Put(\"foto2\", foto2path)";
_mapupdateinternal.Put((Object)("foto2"),(Object)(_foto2path));
 //BA.debugLineNum = 973;BA.debugLine="mapUpdateInternal.Put(\"foto3\", foto3path)";
_mapupdateinternal.Put((Object)("foto3"),(Object)(_foto3path));
 //BA.debugLineNum = 974;BA.debugLine="mapUpdateInternal.Put(\"foto4\", foto4path)";
_mapupdateinternal.Put((Object)("foto4"),(Object)(_foto4path));
 //BA.debugLineNum = 975;BA.debugLine="mapUpdateInternal.Put(\"validacion\", verificado";
_mapupdateinternal.Put((Object)("validacion"),(Object)(_verificado));
 //BA.debugLineNum = 976;BA.debugLine="mapUpdateInternal.Put(\"serverid\", serverid)";
_mapupdateinternal.Put((Object)("serverid"),(Object)(_serverid));
 //BA.debugLineNum = 977;BA.debugLine="mapUpdateInternal.Put(\"privado\", privado)";
_mapupdateinternal.Put((Object)("privado"),(Object)(_privado));
 //BA.debugLineNum = 978;BA.debugLine="mapUpdateInternal.Put(\"gpsdetect\", gpsdetect)";
_mapupdateinternal.Put((Object)("gpsdetect"),(Object)(_gpsdetect));
 //BA.debugLineNum = 979;BA.debugLine="mapUpdateInternal.Put(\"wifidetect\", wifidetect";
_mapupdateinternal.Put((Object)("wifidetect"),(Object)(_wifidetect));
 //BA.debugLineNum = 980;BA.debugLine="mapUpdateInternal.Put(\"mapadetect\", mapadetect";
_mapupdateinternal.Put((Object)("mapadetect"),(Object)(_mapadetect));
 //BA.debugLineNum = 982;BA.debugLine="newInternalReport.Add(mapUpdateInternal)";
_newinternalreport.Add((Object)(_mapupdateinternal.getObject()));
 //BA.debugLineNum = 983;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"markers_loca";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local",_newinternalreport);
 //BA.debugLineNum = 986;BA.debugLine="If serverReportMap.Get(\"foto1sent\") = \"no\" The";
if ((_serverreportmap.Get((Object)("foto1sent"))).equals((Object)("no"))) { 
 //BA.debugLineNum = 987;BA.debugLine="fotoListUpload.Add(serverReportMap.Get(\"foto1";
_fotolistupload.Add(_serverreportmap.Get((Object)("foto1path")));
 };
 //BA.debugLineNum = 989;BA.debugLine="If serverReportMap.Get(\"foto2sent\") = \"no\" The";
if ((_serverreportmap.Get((Object)("foto2sent"))).equals((Object)("no"))) { 
 //BA.debugLineNum = 990;BA.debugLine="fotoListUpload.Add(serverReportMap.Get(\"foto2";
_fotolistupload.Add(_serverreportmap.Get((Object)("foto2path")));
 };
 };
 } 
       catch (Exception e180) {
			processBA.setLastException(e180); //BA.debugLineNum = 994;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("229163752",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 }
};
 };
 //BA.debugLineNum = 1000;BA.debugLine="If reportListUpload.Size > 0 Then";
if (_reportlistupload.getSize()>0) { 
 //BA.debugLineNum = 1001;BA.debugLine="Log(\"DBs desincronizadas\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229163759","DBs desincronizadas",0);
 //BA.debugLineNum = 1002;BA.debugLine="currentInternetCheck = \"uploadDifferencesDB\"";
_currentinternetcheck = "uploadDifferencesDB";
 //BA.debugLineNum = 1003;BA.debugLine="TestInternet";
_testinternet();
 }else {
 //BA.debugLineNum = 1005;BA.debugLine="If fotoListUpload.Size > 0 Then";
if (_fotolistupload.getSize()>0) { 
 //BA.debugLineNum = 1006;BA.debugLine="Log(\"Fotos desincronizadas\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229163764","Fotos desincronizadas",0);
 //BA.debugLineNum = 1007;BA.debugLine="currentInternetCheck = \"uploadFotos\"";
_currentinternetcheck = "uploadFotos";
 //BA.debugLineNum = 1008;BA.debugLine="TestInternet";
_testinternet();
 }else {
 //BA.debugLineNum = 1010;BA.debugLine="Log(\"DB y fotos sincronizadas\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229163768","DB y fotos sincronizadas",0);
 //BA.debugLineNum = 1011;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 1012;BA.debugLine="lblEstado.Text = \"Base de datos sincronizada c";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Base de datos sincronizada con el servidor"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 1014;BA.debugLine="lblEstado.Text = \"Database synchronized correc";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Database synchronized correctly with the server"));
 };
 //BA.debugLineNum = 1016;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1017;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1018;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 };
 };
 //BA.debugLineNum = 1022;BA.debugLine="End Sub";
return "";
}
public static String  _enviardatos_complete(cepave.geovin.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
 //BA.debugLineNum = 1093;BA.debugLine="Sub EnviarDatos_Complete(Job As HttpJob)";
 //BA.debugLineNum = 1094;BA.debugLine="Log(\"Datos enviados : \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("229294593","Datos enviados : "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 1095;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1096;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 1097;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1098;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 1099;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1100;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 1101;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1102;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 1103;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 1104;BA.debugLine="lblEstado.Text = \"Error en la conexión a inter";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Error en la conexión a internet"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 1106;BA.debugLine="lblEstado.Text = \"Error connecting to the inte";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Error connecting to the internet"));
 };
 //BA.debugLineNum = 1108;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1109;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1110;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 }else if((_act).equals("Marcadores")) { 
 //BA.debugLineNum = 1113;BA.debugLine="numReportUploaded = numReportUploaded + 1";
_numreportuploaded = (int) (_numreportuploaded+1);
 //BA.debugLineNum = 1115;BA.debugLine="If numReportUploaded = reportListUpload.Size Th";
if (_numreportuploaded==_reportlistupload.getSize()) { 
 //BA.debugLineNum = 1116;BA.debugLine="If fotoListUpload.Size > 0 Then";
if (_fotolistupload.getSize()>0) { 
 //BA.debugLineNum = 1117;BA.debugLine="Log(\"hay fotos para subir\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229294616","hay fotos para subir",0);
 //BA.debugLineNum = 1118;BA.debugLine="currentInternetCheck = \"uploadFotos\"";
_currentinternetcheck = "uploadFotos";
 //BA.debugLineNum = 1119;BA.debugLine="TestInternet";
_testinternet();
 }else {
 //BA.debugLineNum = 1121;BA.debugLine="Log(\"no hay fotos para subir\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229294620","no hay fotos para subir",0);
 //BA.debugLineNum = 1122;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1123;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 };
 };
 //BA.debugLineNum = 1126;BA.debugLine="Log(\"dato de la db subido\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229294625","dato de la db subido",0);
 };
 }else {
 //BA.debugLineNum = 1130;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229294629","envio datos not ok",0);
 //BA.debugLineNum = 1131;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 1132;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 1134;BA.debugLine="Msgbox(\"There seems to be a problem with our se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 1136;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1137;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 };
 //BA.debugLineNum = 1140;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1141;BA.debugLine="End Sub";
return "";
}
public static String  _enviarfotos() throws Exception{
int _i = 0;
 //BA.debugLineNum = 1144;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 1147;BA.debugLine="For i = 0 To fotoListUpload.Size - 1";
{
final int step1 = 1;
final int limit1 = (int) (_fotolistupload.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit1 ;_i = _i + step1 ) {
 //BA.debugLineNum = 1148;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1149;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 1151;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",BA.ObjectToString(_fotolistupload.Get(_i))+".jpg")) { 
 //BA.debugLineNum = 1152;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("229360136","Enviando foto 1 ",0);
 //BA.debugLineNum = 1153;BA.debugLine="Up1.doFileUpload(Null,Null,File.DirRootExternal";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(anywheresoftware.b4a.keywords.Common.Null),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/"+BA.ObjectToString(_fotolistupload.Get(_i))+".jpg",_serverpath+"/connect2/upload_file.php");
 }else {
 //BA.debugLineNum = 1156;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229360140","no foto 1",0);
 //BA.debugLineNum = 1157;BA.debugLine="numFotosUploaded = numFotosUploaded + 1";
_numfotosuploaded = (int) (_numfotosuploaded+1);
 //BA.debugLineNum = 1158;BA.debugLine="If numFotosUploaded = fotoListUpload.Size Then";
if (_numfotosuploaded==_fotolistupload.getSize()) { 
 //BA.debugLineNum = 1159;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1160;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 1161;BA.debugLine="lblEstado.Text = \"Algunas fotos no fueron enc";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Algunas fotos no fueron encontradas en el móvil"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 1163;BA.debugLine="lblEstado.Text = \"Some photos were not found";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Some photos were not found in the mobile device"));
 };
 //BA.debugLineNum = 1166;BA.debugLine="Log(\"Fotos no encontradas\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229360150","Fotos no encontradas",0);
 //BA.debugLineNum = 1167;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 1168;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1169;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1170;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 };
 };
 }
};
 //BA.debugLineNum = 1175;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 96;BA.debugLine="Dim versionactual As String";
mostCurrent._versionactual = "";
 //BA.debugLineNum = 97;BA.debugLine="Dim serverversion As String";
mostCurrent._serverversion = "";
 //BA.debugLineNum = 98;BA.debugLine="Dim servernewstitulo As String";
mostCurrent._servernewstitulo = "";
 //BA.debugLineNum = 99;BA.debugLine="Dim servernewstext As String";
mostCurrent._servernewstext = "";
 //BA.debugLineNum = 100;BA.debugLine="Dim msjprivadouser As String";
_msjprivadouser = "";
 //BA.debugLineNum = 102;BA.debugLine="Private pgbLoad As ProgressBar";
mostCurrent._pgbload = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 103;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 107;BA.debugLine="Private btnOffline As Button";
mostCurrent._btnoffline = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 111;BA.debugLine="Dim Cursor1 As Cursor";
mostCurrent._cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
frmprincipal._process_globals();
downloadservice._process_globals();
frmfotos._process_globals();
utilidades._process_globals();
dbutils._process_globals();
starter._process_globals();
frmlocalizacion._process_globals();
firebasemessaging._process_globals();
frmabout._process_globals();
frmaprender_chagas._process_globals();
frmcamara._process_globals();
frmcomofotos._process_globals();
frmdatosanteriores._process_globals();
frmeditprofile._process_globals();
frmespecies._process_globals();
frmidentificacionnew._process_globals();
frmlogin._process_globals();
frmpoliticadatos._process_globals();
frmrecomendaciones._process_globals();
httputils2service._process_globals();
multipartpost._process_globals();
register._process_globals();
uploadfiles._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 21;BA.debugLine="Private timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 22;BA.debugLine="Private timerOn As Boolean";
_timeron = false;
 //BA.debugLineNum = 25;BA.debugLine="Dim valorVinchuca As String";
_valorvinchuca = "";
 //BA.debugLineNum = 26;BA.debugLine="Dim dateandtime As String";
_dateandtime = "";
 //BA.debugLineNum = 27;BA.debugLine="Dim Idproyecto As Int";
_idproyecto = 0;
 //BA.debugLineNum = 28;BA.debugLine="Dim longitud As String";
_longitud = "";
 //BA.debugLineNum = 29;BA.debugLine="Dim latitud As String";
_latitud = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim fotopath0 As String = \"\"";
_fotopath0 = "";
 //BA.debugLineNum = 33;BA.debugLine="Dim fotopath1 As String = \"\"";
_fotopath1 = "";
 //BA.debugLineNum = 34;BA.debugLine="Dim fotopath2 As String = \"\"";
_fotopath2 = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim fotopath3 As String = \"\"";
_fotopath3 = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim strUserID As String";
_struserid = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim strUserName As String";
_strusername = "";
 //BA.debugLineNum = 41;BA.debugLine="Dim strUserLocation As String";
_struserlocation = "";
 //BA.debugLineNum = 42;BA.debugLine="Dim strUserEmail As String";
_struseremail = "";
 //BA.debugLineNum = 43;BA.debugLine="Dim strUserFullName As String";
_struserfullname = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim strUserOrg As String";
_struserorg = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim strUserTipoUsuario As String";
_strusertipousuario = "";
 //BA.debugLineNum = 46;BA.debugLine="Dim strUserGroup As String";
_strusergroup = "";
 //BA.debugLineNum = 47;BA.debugLine="Dim username As String";
_username = "";
 //BA.debugLineNum = 48;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 51;BA.debugLine="Dim modooffline As Boolean";
_modooffline = false;
 //BA.debugLineNum = 52;BA.debugLine="Dim forceoffline As Boolean";
_forceoffline = false;
 //BA.debugLineNum = 55;BA.debugLine="Dim savedir As String";
_savedir = "";
 //BA.debugLineNum = 59;BA.debugLine="Dim msjprivadouser As String";
_msjprivadouser = "";
 //BA.debugLineNum = 60;BA.debugLine="Dim msjprivadoleido As Boolean";
_msjprivadoleido = false;
 //BA.debugLineNum = 63;BA.debugLine="Dim valorVinchuca As String";
_valorvinchuca = "";
 //BA.debugLineNum = 65;BA.debugLine="Dim currentproject As String";
_currentproject = "";
 //BA.debugLineNum = 66;BA.debugLine="Dim datecurrentproject As String";
_datecurrentproject = "";
 //BA.debugLineNum = 69;BA.debugLine="Dim deviceID As String";
_deviceid = "";
 //BA.debugLineNum = 70;BA.debugLine="Dim serverPath As String = \"http://www.geovin.com";
_serverpath = "http://www.geovin.com.ar";
 //BA.debugLineNum = 72;BA.debugLine="Dim subFolder As String = \"\"";
_subfolder = "";
 //BA.debugLineNum = 75;BA.debugLine="Dim speciesMap As List";
_speciesmap = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 78;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 79;BA.debugLine="Dim lang As String";
_lang = "";
 //BA.debugLineNum = 83;BA.debugLine="Dim userReportsServer As List";
_userreportsserver = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 84;BA.debugLine="Dim userReportsInternal As List";
_userreportsinternal = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 85;BA.debugLine="Dim reportListUpload As List";
_reportlistupload = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 86;BA.debugLine="Dim fotoListUpload As List";
_fotolistupload = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 87;BA.debugLine="Dim numReportUploaded As Int";
_numreportuploaded = 0;
 //BA.debugLineNum = 88;BA.debugLine="Dim numFotosUploaded As Int";
_numfotosuploaded = 0;
 //BA.debugLineNum = 89;BA.debugLine="Dim Up1 As UploadFilePhp";
_up1 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 90;BA.debugLine="Dim currentInternetCheck As String";
_currentinternetcheck = "";
 //BA.debugLineNum = 91;BA.debugLine="Dim buscarUpdate As Boolean";
_buscarupdate = false;
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _syncdb() throws Exception{
cepave.geovin.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 672;BA.debugLine="Sub SyncDB";
 //BA.debugLineNum = 674;BA.debugLine="userReportsServer.Initialize()";
_userreportsserver.Initialize();
 //BA.debugLineNum = 675;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 678;BA.debugLine="If username <> \"guest\" And username <> \"None\" The";
if ((_username).equals("guest") == false && (_username).equals("None") == false) { 
 //BA.debugLineNum = 679;BA.debugLine="dd.url = serverPath & \"/connect2/syncdb.php?user";
_dd.url /*String*/  = _serverpath+"/connect2/syncdb.php?user_id="+_username;
 //BA.debugLineNum = 680;BA.debugLine="dd.EventName = \"SyncDB\"";
_dd.EventName /*String*/  = "SyncDB";
 //BA.debugLineNum = 681;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 682;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 683;BA.debugLine="lblEstado.Text = \"Sincronizando datos con el se";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Sincronizando datos con el servidor"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 686;BA.debugLine="lblEstado.Text = \"Synchronizing data with the s";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Synchronizing data with the server"));
 };
 //BA.debugLineNum = 689;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 }else if((_username).equals("guest")) { 
 //BA.debugLineNum = 691;BA.debugLine="If deviceID <> \"\" Then";
if ((_deviceid).equals("") == false) { 
 //BA.debugLineNum = 694;BA.debugLine="dd.url = serverPath & \"/connect2/syncdb_device.p";
_dd.url /*String*/  = _serverpath+"/connect2/syncdb_device.php?deviceid="+_deviceid;
 //BA.debugLineNum = 695;BA.debugLine="dd.EventName = \"SyncDB\"";
_dd.EventName /*String*/  = "SyncDB";
 //BA.debugLineNum = 696;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 697;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 698;BA.debugLine="lblEstado.Text = \"Sincronizando datos con el se";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Sincronizando datos con el servidor"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 701;BA.debugLine="lblEstado.Text = \"Synchronizing data with the s";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Synchronizing data with the server"));
 };
 //BA.debugLineNum = 704;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 }else {
 //BA.debugLineNum = 706;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 707;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 708;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 };
 }else if((_username).equals("None") || (_username).equals("")) { 
 //BA.debugLineNum = 711;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 712;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 713;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 };
 //BA.debugLineNum = 716;BA.debugLine="End Sub";
return "";
}
public static String  _syncdb_complete(cepave.geovin.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
 //BA.debugLineNum = 717;BA.debugLine="Sub SyncDB_Complete(Job As HttpJob)";
 //BA.debugLineNum = 718;BA.debugLine="Log(\"SyncDB messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("229097985","SyncDB messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 719;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 720;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 721;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 722;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 723;BA.debugLine="Try";
try { //BA.debugLineNum = 724;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 725;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 726;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 727;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 728;BA.debugLine="ToastMessageShow(\"No hay datos anteriores\", Tr";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay datos anteriores"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 730;BA.debugLine="ToastMessageShow(\"Sync error\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Sync error"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("GetDataOk")) { 
 //BA.debugLineNum = 733;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 734;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 735;BA.debugLine="For i = 0 To numresults - 1";
{
final int step17 = 1;
final int limit17 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit17 ;_i = _i + step17 ) {
 //BA.debugLineNum = 736;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 737;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 738;BA.debugLine="userReportsServer.Add(newpunto)";
_userreportsserver.Add((Object)(_newpunto.getObject()));
 }
};
 };
 } 
       catch (Exception e24) {
			processBA.setLastException(e24); //BA.debugLineNum = 742;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("229098009",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 }else {
 //BA.debugLineNum = 745;BA.debugLine="Log(\"reset messages not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229098012","reset messages not ok",0);
 //BA.debugLineNum = 746;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 747;BA.debugLine="lblEstado.Text = \"No hay conexión a internet\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("No hay conexión a internet"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 749;BA.debugLine="lblEstado.Text = \"No internet connection\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("No internet connection"));
 };
 //BA.debugLineNum = 751;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 };
 //BA.debugLineNum = 753;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 756;BA.debugLine="If username <> \"guest\" And username <> \"None\" And";
if ((_username).equals("guest") == false && (_username).equals("None") == false && (_username).equals("") == false) { 
 //BA.debugLineNum = 757;BA.debugLine="CompareDB(\"user\")";
_comparedb("user");
 }else {
 //BA.debugLineNum = 759;BA.debugLine="CompareDB(\"device\")";
_comparedb("device");
 };
 //BA.debugLineNum = 761;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet() throws Exception{
cepave.geovin.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 372;BA.debugLine="Sub TestInternet";
 //BA.debugLineNum = 373;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 374;BA.debugLine="dd.url = serverPath & \"/connect2/connecttest.php\"";
_dd.url /*String*/  = _serverpath+"/connect2/connecttest.php";
 //BA.debugLineNum = 376;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 377;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 378;BA.debugLine="btnOffline.Visible = True";
mostCurrent._btnoffline.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 379;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 380;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet_complete(cepave.geovin.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
int _upd = 0;
anywheresoftware.b4a.objects.IntentWrapper _market = null;
String _uri = "";
 //BA.debugLineNum = 381;BA.debugLine="Sub TestInternet_Complete(Job As HttpJob)";
 //BA.debugLineNum = 382;BA.debugLine="Log(\"Job completed: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("228770305","Job completed: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 383;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 384;BA.debugLine="versionactual = Application.VersionCode";
mostCurrent._versionactual = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Application.getVersionCode());
 //BA.debugLineNum = 385;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 386;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 387;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 388;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 389;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 390;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 391;BA.debugLine="If act = \"Connected\" Then";
if ((_act).equals("Connected")) { 
 //BA.debugLineNum = 393;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 394;BA.debugLine="lblEstado.Text = \"Conectado. Comprobando versi";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Conectado. Comprobando versión de la aplicación"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 396;BA.debugLine="lblEstado.Text = \"Connected. Checking app vers";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Connected. Checking app version"));
 };
 //BA.debugLineNum = 399;BA.debugLine="modooffline = False";
_modooffline = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 400;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 401;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 402;BA.debugLine="serverversion = nd.Get(\"currentversion\")";
mostCurrent._serverversion = BA.ObjectToString(_nd.Get((Object)("currentversion")));
 //BA.debugLineNum = 403;BA.debugLine="servernewstitulo = nd.Get(\"newstitulo\")";
mostCurrent._servernewstitulo = BA.ObjectToString(_nd.Get((Object)("newstitulo")));
 //BA.debugLineNum = 404;BA.debugLine="servernewstext = nd.Get(\"newstext\")";
mostCurrent._servernewstext = BA.ObjectToString(_nd.Get((Object)("newstext")));
 //BA.debugLineNum = 405;BA.debugLine="If serverversion <> versionactual And buscarUpd";
if ((mostCurrent._serverversion).equals(mostCurrent._versionactual) == false && _buscarupdate==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 406;BA.debugLine="Dim upd As Int";
_upd = 0;
 //BA.debugLineNum = 407;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 408;BA.debugLine="upd = Msgbox2(\"Para continuar, debe descargar";
_upd = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Para continuar, debe descargar una actualización importante"),BA.ObjectToCharSequence("Actualización"),"Ir a GooglePlay","Lo haré después","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 410;BA.debugLine="upd = Msgbox2(\"To continue, an important upda";
_upd = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("To continue, an important update has to be downloaded"),BA.ObjectToCharSequence("Update"),"Go to GooglePlay","I'll do it later","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 413;BA.debugLine="If upd = DialogResponse.POSITIVE Then";
if (_upd==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 415;BA.debugLine="Dim market As Intent, uri As String";
_market = new anywheresoftware.b4a.objects.IntentWrapper();
_uri = "";
 //BA.debugLineNum = 416;BA.debugLine="uri=\"market://details?id=cepave.geovin\"";
_uri = "market://details?id=cepave.geovin";
 //BA.debugLineNum = 417;BA.debugLine="market.Initialize(market.ACTION_VIEW,uri)";
_market.Initialize(_market.ACTION_VIEW,_uri);
 //BA.debugLineNum = 418;BA.debugLine="StartActivity(market)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_market.getObject()));
 };
 //BA.debugLineNum = 420;BA.debugLine="buscarUpdate = False";
_buscarupdate = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 423;BA.debugLine="If servernewstitulo <> \"\" And servernewstitulo";
if ((mostCurrent._servernewstitulo).equals("") == false && (mostCurrent._servernewstitulo).equals("Nada") == false) { 
 //BA.debugLineNum = 424;BA.debugLine="Msgbox(servernewstext, servernewstitulo)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(mostCurrent._servernewstext),BA.ObjectToCharSequence(mostCurrent._servernewstitulo),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 430;BA.debugLine="If currentInternetCheck = \"startsync\" Then";
if ((_currentinternetcheck).equals("startsync")) { 
 //BA.debugLineNum = 431;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 432;BA.debugLine="lblEstado.Text = \"Revisando sincronización co";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Revisando sincronización con el servidor..."));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 434;BA.debugLine="lblEstado.Text = \"Checking sync with the serv";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Checking sync with the server..."));
 };
 //BA.debugLineNum = 436;BA.debugLine="SyncDB";
_syncdb();
 };
 //BA.debugLineNum = 441;BA.debugLine="If currentInternetCheck = \"uploadDifferencesDB\"";
if ((_currentinternetcheck).equals("uploadDifferencesDB")) { 
 //BA.debugLineNum = 442;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 443;BA.debugLine="lblEstado.Text = \"Sincronizando datos anterio";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Sincronizando datos anteriores con el servidor..."));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 445;BA.debugLine="lblEstado.Text = \"Synchronizing previous data";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Synchronizing previous data with the server..."));
 };
 //BA.debugLineNum = 447;BA.debugLine="uploadDifferencesDB";
_uploaddifferencesdb();
 };
 //BA.debugLineNum = 449;BA.debugLine="If currentInternetCheck = \"uploadFotos\" Then";
if ((_currentinternetcheck).equals("uploadFotos")) { 
 //BA.debugLineNum = 450;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 451;BA.debugLine="lblEstado.Text = \"Subiendo fotos al servidor.";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Subiendo fotos al servidor..."));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 453;BA.debugLine="lblEstado.Text = \"Uploading photos to the ser";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Uploading photos to the server..."));
 };
 //BA.debugLineNum = 455;BA.debugLine="EnviarFotos";
_enviarfotos();
 };
 };
 }else {
 //BA.debugLineNum = 461;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 462;BA.debugLine="Msgbox(\"No tienes conexión a Internet. GeoVin i";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes conexión a Internet. GeoVin iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes!"),BA.ObjectToCharSequence("Advertencia"),mostCurrent.activityBA);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 464;BA.debugLine="Msgbox(\"You have no connection to the internet.";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You have no connection to the internet. GeoVin will start with the last user that logged in, but you won't be able to send data unless you have internet!"),BA.ObjectToCharSequence("Warning"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 467;BA.debugLine="modooffline = True";
_modooffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 469;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 471;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 472;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 };
 //BA.debugLineNum = 474;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 475;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 242;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 243;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 244;BA.debugLine="lblEstado.Text = \"Comprobando conexión a interne";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Comprobando conexión a internet"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 246;BA.debugLine="lblEstado.Text = \"Checking the connection to the";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Checking the connection to the internet"));
 };
 //BA.debugLineNum = 251;BA.debugLine="currentInternetCheck = \"startsync\"";
_currentinternetcheck = "startsync";
 //BA.debugLineNum = 252;BA.debugLine="TestInternet";
_testinternet();
 //BA.debugLineNum = 254;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 255;BA.debugLine="timerOn = False";
_timeron = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 1178;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 1179;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("229491201","sendfile event:"+_value,0);
 //BA.debugLineNum = 1180;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 1182;BA.debugLine="numFotosUploaded = numFotosUploaded + 1";
_numfotosuploaded = (int) (_numfotosuploaded+1);
 //BA.debugLineNum = 1183;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 1184;BA.debugLine="lblEstado.Text = \"Subiendo fotos al servidor...";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Subiendo fotos al servidor..."+BA.NumberToString(_numfotosuploaded)+"/"+BA.NumberToString(_fotolistupload.getSize())));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 1186;BA.debugLine="lblEstado.Text = \"Uploading photos to the serve";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Uploading photos to the server..."+BA.NumberToString(_numfotosuploaded)+"/"+BA.NumberToString(_fotolistupload.getSize())));
 };
 //BA.debugLineNum = 1189;BA.debugLine="If numFotosUploaded = fotoListUpload.Size Then";
if (_numfotosuploaded==_fotolistupload.getSize()) { 
 //BA.debugLineNum = 1190;BA.debugLine="Log(\"Todas las fotos subidas\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229491212","Todas las fotos subidas",0);
 //BA.debugLineNum = 1191;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 1192;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 1193;BA.debugLine="lblEstado.Text = \"Base de datos online sincron";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Base de datos online sincronizada"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 1195;BA.debugLine="lblEstado.Text = \"Online database synchronized";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Online database synchronized"));
 };
 //BA.debugLineNum = 1197;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1198;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1199;BA.debugLine="CargarBienvenidos_Nuevo";
_cargarbienvenidos_nuevo();
 };
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 1203;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229491225","FOTO error",0);
 //BA.debugLineNum = 1204;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 };
 //BA.debugLineNum = 1206;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 1176;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 1177;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 1207;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 1209;BA.debugLine="End Sub";
return "";
}
public static String  _updateinternaldb(String _columnname) throws Exception{
String _mytable = "";
String _txt = "";
int _cols = 0;
String[] _mycolname = null;
String _newcolumn = "";
boolean _flag = false;
int _i = 0;
 //BA.debugLineNum = 331;BA.debugLine="Sub UpdateInternalDB(columnname As String)";
 //BA.debugLineNum = 333;BA.debugLine="Try";
try { //BA.debugLineNum = 336;BA.debugLine="Dim MyTable As String = \"userconfig\"";
_mytable = "userconfig";
 //BA.debugLineNum = 337;BA.debugLine="Dim txt As String";
_txt = "";
 //BA.debugLineNum = 338;BA.debugLine="txt=\"SELECT * FROM \" & MyTable & \" LIMIT 1\"";
_txt = "SELECT * FROM "+_mytable+" LIMIT 1";
 //BA.debugLineNum = 339;BA.debugLine="Cursor1=Starter.sqlDB.ExecQuery(txt)";
mostCurrent._cursor1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(_txt)));
 //BA.debugLineNum = 340;BA.debugLine="Cursor1.Position=0";
mostCurrent._cursor1.setPosition((int) (0));
 //BA.debugLineNum = 341;BA.debugLine="Dim cols As Int = Cursor1.ColumnCount";
_cols = mostCurrent._cursor1.getColumnCount();
 //BA.debugLineNum = 342;BA.debugLine="Dim MyColName(cols) As String";
_mycolname = new String[_cols];
java.util.Arrays.fill(_mycolname,"");
 //BA.debugLineNum = 343;BA.debugLine="Dim NewColumn As String = columnname";
_newcolumn = _columnname;
 //BA.debugLineNum = 344;BA.debugLine="Dim Flag As Boolean =False";
_flag = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 346;BA.debugLine="For i=0 To Cursor1.ColumnCount-1";
{
final int step11 = 1;
final int limit11 = (int) (mostCurrent._cursor1.getColumnCount()-1);
_i = (int) (0) ;
for (;_i <= limit11 ;_i = _i + step11 ) {
 //BA.debugLineNum = 347;BA.debugLine="MyColName(i)=Cursor1.GetColumnName(i)";
_mycolname[_i] = mostCurrent._cursor1.GetColumnName(_i);
 //BA.debugLineNum = 348;BA.debugLine="If MyColName(i)= NewColumn Then";
if ((_mycolname[_i]).equals(_newcolumn)) { 
 //BA.debugLineNum = 350;BA.debugLine="Log(\"Columna \" &columnname &\" existe\")";
anywheresoftware.b4a.keywords.Common.LogImpl("228639251","Columna "+_columnname+" existe",0);
 //BA.debugLineNum = 351;BA.debugLine="Flag=True";
_flag = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 352;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 355;BA.debugLine="If Flag=False Then";
if (_flag==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 356;BA.debugLine="txt=\"ALTER TABLE \" & MyTable & \" ADD COLUMN \" &";
_txt = "ALTER TABLE "+_mytable+" ADD COLUMN "+_newcolumn+" VARCHAR(100)";
 //BA.debugLineNum = 357;BA.debugLine="Starter.sqlDB.ExecNonQuery(txt)";
mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(_txt);
 //BA.debugLineNum = 358;BA.debugLine="ToastMessageShow(\"Base de datos interna actualiz";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Base de datos interna actualizada / Internal DB checked"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 359;BA.debugLine="Log(\"Columna \" &columnname & \" agregada\")";
anywheresoftware.b4a.keywords.Common.LogImpl("228639260","Columna "+_columnname+" agregada",0);
 };
 } 
       catch (Exception e26) {
			processBA.setLastException(e26); //BA.debugLineNum = 362;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("228639263",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 365;BA.debugLine="End Sub";
return "";
}
public static String  _uploaddifferencesdb() throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _reportmap = null;
String _lat = "";
String _lng = "";
String _gpsdetect = "";
String _wifidetect = "";
String _mapadetect = "";
String _valorind1 = "";
String _foto1 = "";
String _foto2 = "";
String _foto3 = "";
String _foto4 = "";
String _terminado = "";
String _privado = "";
String _estadovalidacion = "";
cepave.geovin.downloadservice._downloaddata _dd = null;
String _usernametemp = "";
 //BA.debugLineNum = 1024;BA.debugLine="Sub uploadDifferencesDB";
 //BA.debugLineNum = 1026;BA.debugLine="For i = 0 To reportListUpload.Size - 1";
{
final int step1 = 1;
final int limit1 = (int) (_reportlistupload.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit1 ;_i = _i + step1 ) {
 //BA.debugLineNum = 1027;BA.debugLine="Try";
try { //BA.debugLineNum = 1029;BA.debugLine="Dim reportMap As Map";
_reportmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1030;BA.debugLine="reportMap = reportListUpload.Get(i)";
_reportmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_reportlistupload.Get(_i)));
 //BA.debugLineNum = 1034;BA.debugLine="Dim username,dateandtime,lat,lng,gpsdetect,wifid";
_username = "";
_dateandtime = "";
_lat = "";
_lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
_valorind1 = "";
 //BA.debugLineNum = 1035;BA.debugLine="Dim foto1, foto2, foto3, foto4 As String";
_foto1 = "";
_foto2 = "";
_foto3 = "";
_foto4 = "";
 //BA.debugLineNum = 1036;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceI";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
 //BA.debugLineNum = 1038;BA.debugLine="username = reportMap.Get(\"usuario\")";
_username = BA.ObjectToString(_reportmap.Get((Object)("usuario")));
 //BA.debugLineNum = 1039;BA.debugLine="dateandtime = reportMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_reportmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 1040;BA.debugLine="lat = reportMap.Get(\"decimallatitude\")";
_lat = BA.ObjectToString(_reportmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 1041;BA.debugLine="lng = reportMap.Get(\"decimallongitude\")";
_lng = BA.ObjectToString(_reportmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 1042;BA.debugLine="gpsdetect = reportMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_reportmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 1043;BA.debugLine="wifidetect = reportMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_reportmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 1044;BA.debugLine="mapadetect = reportMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_reportmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 1045;BA.debugLine="valorind1 = reportMap.Get(\"par1\")";
_valorind1 = BA.ObjectToString(_reportmap.Get((Object)("par1")));
 //BA.debugLineNum = 1046;BA.debugLine="foto1 = reportMap.Get(\"foto1\")";
_foto1 = BA.ObjectToString(_reportmap.Get((Object)("foto1")));
 //BA.debugLineNum = 1047;BA.debugLine="foto2 = reportMap.Get(\"foto2\")";
_foto2 = BA.ObjectToString(_reportmap.Get((Object)("foto2")));
 //BA.debugLineNum = 1048;BA.debugLine="foto3 = reportMap.Get(\"foto3\")";
_foto3 = BA.ObjectToString(_reportmap.Get((Object)("foto3")));
 //BA.debugLineNum = 1049;BA.debugLine="foto4 = reportMap.Get(\"foto4\")";
_foto4 = BA.ObjectToString(_reportmap.Get((Object)("foto4")));
 //BA.debugLineNum = 1050;BA.debugLine="terminado = reportMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_reportmap.Get((Object)("terminado")));
 //BA.debugLineNum = 1051;BA.debugLine="privado = reportMap.Get(\"privado\")";
_privado = BA.ObjectToString(_reportmap.Get((Object)("privado")));
 //BA.debugLineNum = 1052;BA.debugLine="If privado = Null Or privado = \"null\" Then";
if (_privado== null || (_privado).equals("null")) { 
 //BA.debugLineNum = 1053;BA.debugLine="privado = \"no\"";
_privado = "no";
 };
 //BA.debugLineNum = 1055;BA.debugLine="estadovalidacion = reportMap.Get(\"estadovalidaci";
_estadovalidacion = BA.ObjectToString(_reportmap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 1056;BA.debugLine="If estadovalidacion = \"null\" Then";
if ((_estadovalidacion).equals("null")) { 
 //BA.debugLineNum = 1057;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 };
 //BA.debugLineNum = 1059;BA.debugLine="deviceID = reportMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_reportmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 1060;BA.debugLine="If deviceID = Null Or deviceID = \"\" Or deviceID";
if (_deviceid== null || (_deviceid).equals("") || (_deviceid).equals("null")) { 
 //BA.debugLineNum = 1061;BA.debugLine="deviceID = utilidades.GetDeviceId";
_deviceid = mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 };
 //BA.debugLineNum = 1064;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.LogImpl("229229096","Comienza envio de datos",0);
 //BA.debugLineNum = 1067;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 1068;BA.debugLine="Dim usernameTemp As String";
_usernametemp = "";
 //BA.debugLineNum = 1069;BA.debugLine="If username = \"guest\" Then";
if ((_username).equals("guest")) { 
 //BA.debugLineNum = 1070;BA.debugLine="usernameTemp = \"guest\" & deviceID.SubString2(0,";
_usernametemp = "guest"+_deviceid.substring((int) (0),(int) (6));
 }else {
 //BA.debugLineNum = 1072;BA.debugLine="usernameTemp = username";
_usernametemp = _username;
 };
 //BA.debugLineNum = 1074;BA.debugLine="dd.url = serverPath & \"/connect2/addpuntomapa.ph";
_dd.url /*String*/  = _serverpath+"/connect2/addpuntomapa.php?"+"username="+_usernametemp+"&"+"deviceID="+_deviceid+"&"+"dateandtime="+_dateandtime+"&"+"lat="+_lat+"&"+"lng="+_lng+"&"+"valorVinchuca="+_valorind1+"&"+"foto1path="+_foto1+"&"+"foto2path="+_foto2+"&"+"foto3path="+_foto3+"&"+"foto4path="+_foto4+"&"+"privado="+_privado+"&"+"gpsdetect="+_gpsdetect+"&"+"wifidetect="+_wifidetect+"&"+"mapadetect="+_mapadetect+"&"+"terminado="+_terminado+"&"+"verificado=No Verificado";
 //BA.debugLineNum = 1085;BA.debugLine="dd.EventName = \"EnviarDatos\"";
_dd.EventName /*String*/  = "EnviarDatos";
 //BA.debugLineNum = 1086;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 1087;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 } 
       catch (Exception e46) {
			processBA.setLastException(e46); //BA.debugLineNum = 1089;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("229229121",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 }
};
 //BA.debugLineNum = 1092;BA.debugLine="End Sub";
return "";
}
}
