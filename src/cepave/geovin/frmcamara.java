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

public class frmcamara extends Activity implements B4AActivity{
	public static frmcamara mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmcamara");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmcamara).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmcamara");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmcamara", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmcamara) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmcamara) Resume **");
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
		return frmcamara.class;
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
            BA.LogInfo("** Activity (frmcamara) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmcamara) Pause event (activity is not paused). **");
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
            frmcamara mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmcamara) Resume **");
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
public static boolean _adjuntandofoto = false;
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper _hc = null;
public static anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
public static boolean _frontcamera = false;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up1 = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up2 = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up3 = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up4 = null;
public static String _currentfoto = "";
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public cepave.geovin.cameraexclass _camex = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btntakepicture = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgflash = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnadjuntarfoto = null;
public anywheresoftware.b4a.objects.LabelWrapper _imgmenu = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _seekfocus = null;
public static String _foto1 = "";
public static String _foto2 = "";
public static String _foto3 = "";
public static String _foto4 = "";
public static String _fotonombredestino = "";
public anywheresoftware.b4a.objects.LabelWrapper _lbltemplate = null;
public flm.b4a.betterimageview.BetterImageViewWrapper _imgtransparent = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgtemplate = null;
public flm.b4a.betterimageview.BetterImageViewWrapper _imgcontornotransparent = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgcontorno = null;
public static String _newfilename = "";
public static int _fotonumlibre = 0;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgfotofinal = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnofoto = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnyesfoto = null;
public cepave.geovin.main _main = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 57;BA.debugLine="Activity.LoadLayout(\"layCamera\")";
mostCurrent._activity.LoadLayout("layCamera",mostCurrent.activityBA);
 //BA.debugLineNum = 60;BA.debugLine="If File.ExternalWritable Then";
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()) { 
 //BA.debugLineNum = 61;BA.debugLine="If File.IsDirectory(File.DirRootExternal,\"GeoVin";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"GeoVin/")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 62;BA.debugLine="File.MakeDir(File.DirRootExternal, \"GeoVin\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"GeoVin");
 };
 }else {
 //BA.debugLineNum = 65;BA.debugLine="If File.IsDirectory(File.DirInternal,\"GeoVin/\")";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GeoVin/")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 66;BA.debugLine="File.MakeDir(File.DirInternal, \"GeoVin\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GeoVin");
 };
 };
 //BA.debugLineNum = 71;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 72;BA.debugLine="Activity.AddMenuItem(\"Consejos para sacar fotos\"";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Consejos para sacar fotos"),"mnuConsejos");
 //BA.debugLineNum = 73;BA.debugLine="Activity.AddMenuItem(\"Adjuntar foto\", \"mnuAdjunt";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Adjuntar foto"),"mnuAdjuntar");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 75;BA.debugLine="Activity.AddMenuItem(\"How to take better photos\"";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("How to take better photos"),"mnuConsejos");
 //BA.debugLineNum = 76;BA.debugLine="Activity.AddMenuItem(\"Attach photo\", \"mnuAdjunta";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Attach photo"),"mnuAdjuntar");
 };
 //BA.debugLineNum = 81;BA.debugLine="If hc.IsInitialized = False Then";
if (_hc.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 82;BA.debugLine="hc.Initialize(\"hc\")";
_hc.Initialize("hc");
 };
 //BA.debugLineNum = 85;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 86;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 87;BA.debugLine="Up2.B4A_log=True";
_up2.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 88;BA.debugLine="Up3.B4A_log=True";
_up3.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 89;BA.debugLine="Up4.B4A_log=True";
_up4.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 90;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 91;BA.debugLine="Up2.Initialize(\"Up1\")";
_up2.Initialize(processBA,"Up1");
 //BA.debugLineNum = 92;BA.debugLine="Up3.Initialize(\"Up1\")";
_up3.Initialize(processBA,"Up1");
 //BA.debugLineNum = 93;BA.debugLine="Up4.Initialize(\"Up1\")";
_up4.Initialize(processBA,"Up1");
 };
 //BA.debugLineNum = 96;BA.debugLine="adjuntandoFoto = False";
_adjuntandofoto = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 107;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 108;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 109;BA.debugLine="If Msgbox2(\"Salir de la cámara?\", \"SALIR\", \"Si\"";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Salir de la cámara?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 110;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview /*String*/ ();
 //BA.debugLineNum = 111;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 112;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 113;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 114;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 115;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 //BA.debugLineNum = 116;BA.debugLine="Up3.UploadKill";
_up3.UploadKill(processBA);
 //BA.debugLineNum = 117;BA.debugLine="Up4.UploadKill";
_up4.UploadKill(processBA);
 }else {
 //BA.debugLineNum = 119;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 122;BA.debugLine="If Msgbox2(\"Exit the camera?\", \"Leave\", \"Yes\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Exit the camera?"),BA.ObjectToCharSequence("Leave"),"Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 123;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview /*String*/ ();
 //BA.debugLineNum = 124;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 125;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 126;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 127;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 128;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 //BA.debugLineNum = 129;BA.debugLine="Up3.UploadKill";
_up3.UploadKill(processBA);
 //BA.debugLineNum = 130;BA.debugLine="Up4.UploadKill";
_up4.UploadKill(processBA);
 }else {
 //BA.debugLineNum = 132;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 141;BA.debugLine="If camEx.IsInitialized = True Then";
if (mostCurrent._camex.IsInitialized /*boolean*/ ()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 142;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 };
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 99;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 100;BA.debugLine="If adjuntandoFoto = False Then";
if (_adjuntandofoto==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 101;BA.debugLine="InitializeCamera";
_initializecamera();
 //BA.debugLineNum = 102;BA.debugLine="DesignaFoto";
_designafoto();
 };
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static String  _btnadjuntarfoto_click() throws Exception{
 //BA.debugLineNum = 440;BA.debugLine="Sub btnAdjuntarFoto_Click";
 //BA.debugLineNum = 442;BA.debugLine="adjuntandoFoto = True";
_adjuntandofoto = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 445;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview /*String*/ ();
 //BA.debugLineNum = 446;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 447;BA.debugLine="DesignaFoto";
_designafoto();
 //BA.debugLineNum = 448;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 449;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 450;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 452;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 //BA.debugLineNum = 454;BA.debugLine="End Sub";
return "";
}
public static String  _btnnofoto_click() throws Exception{
 //BA.debugLineNum = 525;BA.debugLine="Sub btnNoFoto_Click";
 //BA.debugLineNum = 526;BA.debugLine="imgFotoFinal.RemoveView";
mostCurrent._imgfotofinal.RemoveView();
 //BA.debugLineNum = 527;BA.debugLine="btnYesFoto.Visible = False";
mostCurrent._btnyesfoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 528;BA.debugLine="btnNoFoto.Visible = False";
mostCurrent._btnnofoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 531;BA.debugLine="InitializeCamera";
_initializecamera();
 //BA.debugLineNum = 532;BA.debugLine="DesignaFoto";
_designafoto();
 //BA.debugLineNum = 533;BA.debugLine="End Sub";
return "";
}
public static String  _btntakepicture_click() throws Exception{
 //BA.debugLineNum = 328;BA.debugLine="Sub btnTakePicture_Click";
 //BA.debugLineNum = 330;BA.debugLine="If camEx.IsInitialized Then";
if (mostCurrent._camex.IsInitialized /*boolean*/ ()) { 
 //BA.debugLineNum = 331;BA.debugLine="If imgtransparent.IsInitialized Then";
if (mostCurrent._imgtransparent.IsInitialized()) { 
 //BA.debugLineNum = 332;BA.debugLine="imgtransparent.RemoveView";
mostCurrent._imgtransparent.RemoveView();
 };
 //BA.debugLineNum = 334;BA.debugLine="btnTakePicture.Enabled = False";
mostCurrent._btntakepicture.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 335;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 336;BA.debugLine="ProgressDialogShow(\"Capturando foto\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Capturando foto"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 338;BA.debugLine="ProgressDialogShow(\"Capturing photo\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Capturing photo"));
 };
 //BA.debugLineNum = 340;BA.debugLine="Try";
try { //BA.debugLineNum = 341;BA.debugLine="camEx.TakePicture";
mostCurrent._camex._takepicture /*String*/ ();
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 343;BA.debugLine="ToastMessageShow(\"Error tomando la foto\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error tomando la foto"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 344;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("217629200",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 };
 //BA.debugLineNum = 349;BA.debugLine="End Sub";
return "";
}
public static String  _btnyesfoto_click() throws Exception{
 //BA.debugLineNum = 519;BA.debugLine="Sub btnYesFoto_Click";
 //BA.debugLineNum = 520;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 521;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 523;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_picturetaken(byte[] _data) throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 350;BA.debugLine="Sub Camera1_PictureTaken (Data() As Byte)";
 //BA.debugLineNum = 353;BA.debugLine="Try";
try { //BA.debugLineNum = 354;BA.debugLine="camEx.SavePictureToFile(Data, File.DirRootExtern";
mostCurrent._camex._savepicturetofile /*String*/ (_data,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg");
 } 
       catch (Exception e4) {
			processBA.setLastException(e4); //BA.debugLineNum = 356;BA.debugLine="ToastMessageShow(\"Error guardando la foto\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error guardando la foto"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 357;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("217694727",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 358;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 363;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 364;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 365;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 366;BA.debugLine="If fotoNombreDestino.EndsWith(\"_1\") Then";
if (mostCurrent._fotonombredestino.endsWith("_1")) { 
 //BA.debugLineNum = 367;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto1",(Object)(mostCurrent._fotonombredestino),_map1);
 //BA.debugLineNum = 368;BA.debugLine="Main.fotopath0 = fotoNombreDestino";
mostCurrent._main._fotopath0 /*String*/  = mostCurrent._fotonombredestino;
 }else if(mostCurrent._fotonombredestino.endsWith("_2")) { 
 //BA.debugLineNum = 370;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto2",(Object)(mostCurrent._fotonombredestino),_map1);
 //BA.debugLineNum = 371;BA.debugLine="Main.fotopath1 = fotoNombreDestino";
mostCurrent._main._fotopath1 /*String*/  = mostCurrent._fotonombredestino;
 };
 //BA.debugLineNum = 375;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview /*String*/ ();
 //BA.debugLineNum = 376;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 380;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 383;BA.debugLine="imgFotoFinal.Initialize(\"imgFotoFinal\")";
mostCurrent._imgfotofinal.Initialize(mostCurrent.activityBA,"imgFotoFinal");
 //BA.debugLineNum = 384;BA.debugLine="imgFotoFinal.Bitmap = LoadBitmapResize(File.DirRo";
mostCurrent._imgfotofinal.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg",mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight(),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 385;BA.debugLine="Activity.AddView(imgFotoFinal, 0,0,Activity.Width";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._imgfotofinal.getObject()),(int) (0),(int) (0),mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight());
 //BA.debugLineNum = 387;BA.debugLine="btnYesFoto.Visible = True";
mostCurrent._btnyesfoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 388;BA.debugLine="btnNoFoto.Visible= True";
mostCurrent._btnnofoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 389;BA.debugLine="btnNoFoto.BringToFront";
mostCurrent._btnnofoto.BringToFront();
 //BA.debugLineNum = 390;BA.debugLine="btnYesFoto.BringToFront";
mostCurrent._btnyesfoto.BringToFront();
 //BA.debugLineNum = 393;BA.debugLine="btnAdjuntarFoto.visible = False";
mostCurrent._btnadjuntarfoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 395;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_ready(boolean _success) throws Exception{
 //BA.debugLineNum = 285;BA.debugLine="Sub Camera1_Ready (Success As Boolean)";
 //BA.debugLineNum = 286;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 287;BA.debugLine="Log(camEx.GetSupportedPicturesSizes)";
anywheresoftware.b4a.keywords.Common.LogImpl("217498114",BA.ObjectToString(mostCurrent._camex._getsupportedpicturessizes /*cepave.geovin.cameraexclass._camerasize[]*/ ()),0);
 //BA.debugLineNum = 288;BA.debugLine="Log(camEx.GetPictureSize)";
anywheresoftware.b4a.keywords.Common.LogImpl("217498115",BA.ObjectToString(mostCurrent._camex._getpicturesize /*cepave.geovin.cameraexclass._camerasize*/ ()),0);
 //BA.debugLineNum = 289;BA.debugLine="SetMaxSize";
_setmaxsize();
 //BA.debugLineNum = 290;BA.debugLine="camEx.SetContinuousAutoFocus";
mostCurrent._camex._setcontinuousautofocus /*String*/ ();
 //BA.debugLineNum = 291;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 //BA.debugLineNum = 292;BA.debugLine="camEx.StartPreview";
mostCurrent._camex._startpreview /*String*/ ();
 //BA.debugLineNum = 293;BA.debugLine="Log(camEx.GetPreviewSize)";
anywheresoftware.b4a.keywords.Common.LogImpl("217498120",BA.ObjectToString(mostCurrent._camex._getpreviewsize /*cepave.geovin.cameraexclass._camerasize*/ ()),0);
 }else {
 //BA.debugLineNum = 295;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 296;BA.debugLine="ToastMessageShow(\"No se puede encender la cámar";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se puede encender la cámara. Revise los permisos de la aplicación"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 298;BA.debugLine="ToastMessageShow(\"Can't start the camera, check";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Can't start the camera, check the permissions"),anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 304;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 455;BA.debugLine="Sub CC_Result (Success As Boolean, Dir As String,";
 //BA.debugLineNum = 457;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 458;BA.debugLine="Try";
try { //BA.debugLineNum = 461;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 462;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 463;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 464;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 465;BA.debugLine="If fotoNombreDestino.EndsWith(\"_1\") Then";
if (mostCurrent._fotonombredestino.endsWith("_1")) { 
 //BA.debugLineNum = 466;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 467;BA.debugLine="Main.fotopath0 = fotoNombreDestino";
mostCurrent._main._fotopath0 /*String*/  = mostCurrent._fotonombredestino;
 //BA.debugLineNum = 468;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto1",(Object)(mostCurrent._fotonombredestino),_map1);
 }else if(mostCurrent._fotonombredestino.endsWith("_2")) { 
 //BA.debugLineNum = 470;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 471;BA.debugLine="Main.fotopath1 = fotoNombreDestino";
mostCurrent._main._fotopath1 /*String*/  = mostCurrent._fotonombredestino;
 //BA.debugLineNum = 472;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto2",(Object)(mostCurrent._fotonombredestino),_map1);
 };
 } 
       catch (Exception e17) {
			processBA.setLastException(e17); //BA.debugLineNum = 475;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("217956884",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 476;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 477;BA.debugLine="ToastMessageShow(\"Hubo un problema adjuntando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un problema adjuntando la foto"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 479;BA.debugLine="ToastMessageShow(\"There was a problem attachin";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There was a problem attaching the photo"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 481;BA.debugLine="imgFotoFinal.RemoveView";
mostCurrent._imgfotofinal.RemoveView();
 //BA.debugLineNum = 482;BA.debugLine="btnYesFoto.Visible = False";
mostCurrent._btnyesfoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 483;BA.debugLine="btnNoFoto.Visible = False";
mostCurrent._btnnofoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 484;BA.debugLine="adjuntandoFoto = False";
_adjuntandofoto = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 486;BA.debugLine="InitializeCamera";
_initializecamera();
 //BA.debugLineNum = 487;BA.debugLine="DesignaFoto";
_designafoto();
 };
 //BA.debugLineNum = 490;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 491;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 494;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 495;BA.debugLine="ToastMessageShow(\"Hubo un problema adjuntando l";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un problema adjuntando la foto"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 497;BA.debugLine="ToastMessageShow(\"There was a problem attaching";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There was a problem attaching the photo"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 499;BA.debugLine="imgFotoFinal.RemoveView";
mostCurrent._imgfotofinal.RemoveView();
 //BA.debugLineNum = 500;BA.debugLine="btnYesFoto.Visible = False";
mostCurrent._btnyesfoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 501;BA.debugLine="btnNoFoto.Visible = False";
mostCurrent._btnnofoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 502;BA.debugLine="adjuntandoFoto = False";
_adjuntandofoto = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 504;BA.debugLine="InitializeCamera";
_initializecamera();
 //BA.debugLineNum = 505;BA.debugLine="DesignaFoto";
_designafoto();
 };
 //BA.debugLineNum = 509;BA.debugLine="End Sub";
return "";
}
public static String  _designafoto() throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Sub DesignaFoto";
 //BA.debugLineNum = 193;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 194;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 195;BA.debugLine="ProgressDialogShow(\"Preparando cámara...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Preparando cámara..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 197;BA.debugLine="ProgressDialogShow(\"Preparing camera...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Preparing camera..."));
 };
 //BA.debugLineNum = 204;BA.debugLine="If currentFoto = \"dorsal\" Then";
if ((_currentfoto).equals("dorsal")) { 
 //BA.debugLineNum = 205;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentpr";
mostCurrent._fotonombredestino = mostCurrent._frmprincipal._fullidcurrentproject /*String*/ +"_1";
 //BA.debugLineNum = 206;BA.debugLine="fotonumlibre = 1";
_fotonumlibre = (int) (1);
 }else if((_currentfoto).equals("ventral")) { 
 //BA.debugLineNum = 208;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentpr";
mostCurrent._fotonombredestino = mostCurrent._frmprincipal._fullidcurrentproject /*String*/ +"_2";
 //BA.debugLineNum = 209;BA.debugLine="fotonumlibre = 2";
_fotonumlibre = (int) (2);
 };
 //BA.debugLineNum = 214;BA.debugLine="Panel1.Visible = True";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 216;BA.debugLine="imgTemplate.Visible = False";
mostCurrent._imgtemplate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 217;BA.debugLine="imgContorno.Visible = False";
mostCurrent._imgcontorno.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 218;BA.debugLine="If imgtransparent.IsInitialized Then";
if (mostCurrent._imgtransparent.IsInitialized()) { 
 //BA.debugLineNum = 219;BA.debugLine="imgtransparent.removeView";
mostCurrent._imgtransparent.RemoveView();
 };
 //BA.debugLineNum = 221;BA.debugLine="imgtransparent.Initialize(\"\")";
mostCurrent._imgtransparent.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 222;BA.debugLine="imgContornoTransparent.Initialize(\"\")";
mostCurrent._imgcontornotransparent.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 223;BA.debugLine="imgtransparent.Bitmap = Null";
mostCurrent._imgtransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 224;BA.debugLine="imgContornoTransparent.Bitmap = Null";
mostCurrent._imgcontornotransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 225;BA.debugLine="If currentFoto = \"dorsal\" Then";
if ((_currentfoto).equals("dorsal")) { 
 //BA.debugLineNum = 226;BA.debugLine="imgtransparent.Bitmap = LoadBitmapSample(File.Di";
mostCurrent._imgtransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"template_dorsal.png",mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
 //BA.debugLineNum = 227;BA.debugLine="lblTemplate.Text = \"Vista dorsal\"";
mostCurrent._lbltemplate.setText(BA.ObjectToCharSequence("Vista dorsal"));
 //BA.debugLineNum = 228;BA.debugLine="lblTemplate.TextColor = Colors.white";
mostCurrent._lbltemplate.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 }else if((_currentfoto).equals("ventral")) { 
 //BA.debugLineNum = 230;BA.debugLine="imgtransparent.Bitmap = LoadBitmapSample(File.Di";
mostCurrent._imgtransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"template_ventral.png",mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
 //BA.debugLineNum = 231;BA.debugLine="lblTemplate.Text = \"Vista ventral\"";
mostCurrent._lbltemplate.setText(BA.ObjectToCharSequence("Vista ventral"));
 //BA.debugLineNum = 232;BA.debugLine="lblTemplate.TextColor = Colors.white";
mostCurrent._lbltemplate.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 };
 //BA.debugLineNum = 235;BA.debugLine="imgContornoTransparent.Bitmap = LoadBitmapSample(";
mostCurrent._imgcontornotransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"dorsal.png",mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
 //BA.debugLineNum = 236;BA.debugLine="imgContornoTransparent.Gravity = Gravity.FILL";
mostCurrent._imgcontornotransparent.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 237;BA.debugLine="imgContornoTransparent.ChangeAlpha(100)";
mostCurrent._imgcontornotransparent.ChangeAlpha((int) (100));
 //BA.debugLineNum = 238;BA.debugLine="imgContorno.Height = Activity.height";
mostCurrent._imgcontorno.setHeight(mostCurrent._activity.getHeight());
 //BA.debugLineNum = 239;BA.debugLine="imgContorno.Top = Panel1.top - 100dip";
mostCurrent._imgcontorno.setTop((int) (mostCurrent._panel1.getTop()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 243;BA.debugLine="imgtransparent.Gravity = Gravity.FILL";
mostCurrent._imgtransparent.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 244;BA.debugLine="imgtransparent.ChangeAlpha(255)";
mostCurrent._imgtransparent.ChangeAlpha((int) (255));
 //BA.debugLineNum = 245;BA.debugLine="imgTemplate.Height = Activity.height / 6";
mostCurrent._imgtemplate.setHeight((int) (mostCurrent._activity.getHeight()/(double)6));
 //BA.debugLineNum = 246;BA.debugLine="imgTemplate.Left = Activity.Width - 140dip";
mostCurrent._imgtemplate.setLeft((int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))));
 //BA.debugLineNum = 247;BA.debugLine="imgTemplate.Top = btnAdjuntarFoto.Top - imgTempla";
mostCurrent._imgtemplate.setTop((int) (mostCurrent._btnadjuntarfoto.getTop()-mostCurrent._imgtemplate.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 248;BA.debugLine="lblTemplate.Top = imgTemplate.Top - lblTemplate.H";
mostCurrent._lbltemplate.setTop((int) (mostCurrent._imgtemplate.getTop()-mostCurrent._lbltemplate.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 249;BA.debugLine="imgTemplate.Width = (Activity.Width / 2)";
mostCurrent._imgtemplate.setWidth((int) ((mostCurrent._activity.getWidth()/(double)2)));
 //BA.debugLineNum = 250;BA.debugLine="lblTemplate.left = imgTemplate.Left + (Activity.W";
mostCurrent._lbltemplate.setLeft((int) (mostCurrent._imgtemplate.getLeft()+(mostCurrent._activity.getWidth()/(double)8)));
 //BA.debugLineNum = 253;BA.debugLine="Activity.AddView(imgContornoTransparent, imgConto";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._imgcontornotransparent.getObject()),mostCurrent._imgcontorno.getLeft(),mostCurrent._imgcontorno.getTop(),mostCurrent._imgcontorno.getWidth(),mostCurrent._imgcontorno.getHeight());
 //BA.debugLineNum = 254;BA.debugLine="Activity.AddView(imgtransparent, imgTemplate.Left";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._imgtransparent.getObject()),mostCurrent._imgtemplate.getLeft(),mostCurrent._imgtemplate.getTop(),mostCurrent._imgtemplate.getWidth(),mostCurrent._imgtemplate.getHeight());
 //BA.debugLineNum = 257;BA.debugLine="btnTakePicture.Visible = True";
mostCurrent._btntakepicture.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 258;BA.debugLine="btnTakePicture.Enabled = True";
mostCurrent._btntakepicture.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 261;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 263;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 29;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private camEx As CameraExClass";
mostCurrent._camex = new cepave.geovin.cameraexclass();
 //BA.debugLineNum = 31;BA.debugLine="Private btnTakePicture As Button";
mostCurrent._btntakepicture = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private imgFlash As ImageView";
mostCurrent._imgflash = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnAdjuntarFoto As Button";
mostCurrent._btnadjuntarfoto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private imgMenu As Label";
mostCurrent._imgmenu = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private seekFocus As SeekBar";
mostCurrent._seekfocus = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim foto1,foto2,foto3,foto4 As String";
mostCurrent._foto1 = "";
mostCurrent._foto2 = "";
mostCurrent._foto3 = "";
mostCurrent._foto4 = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim fotoNombreDestino As String";
mostCurrent._fotonombredestino = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim lblTemplate As Label";
mostCurrent._lbltemplate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim imgtransparent As BetterImageView";
mostCurrent._imgtransparent = new flm.b4a.betterimageview.BetterImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim imgTemplate As ImageView";
mostCurrent._imgtemplate = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim imgContornoTransparent As BetterImageView";
mostCurrent._imgcontornotransparent = new flm.b4a.betterimageview.BetterImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim imgContorno As ImageView";
mostCurrent._imgcontorno = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim newfilename As String";
mostCurrent._newfilename = "";
 //BA.debugLineNum = 48;BA.debugLine="Dim fotonumlibre As Int";
_fotonumlibre = 0;
 //BA.debugLineNum = 51;BA.debugLine="Dim imgFotoFinal As ImageView";
mostCurrent._imgfotofinal = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private btnNoFoto As Button";
mostCurrent._btnnofoto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private btnYesFoto As Button";
mostCurrent._btnyesfoto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _imgflash_click() throws Exception{
float[] _f = null;
anywheresoftware.b4a.objects.collections.List _flashmodes = null;
String _flash = "";
 //BA.debugLineNum = 405;BA.debugLine="Sub imgFlash_Click";
 //BA.debugLineNum = 406;BA.debugLine="Dim f() As Float = camEx.GetFocusDistances";
_f = mostCurrent._camex._getfocusdistances /*float[]*/ ();
 //BA.debugLineNum = 407;BA.debugLine="Log(f(0) & \", \" & f(1) & \", \" & f(2))";
anywheresoftware.b4a.keywords.Common.LogImpl("217760258",BA.NumberToString(_f[(int) (0)])+", "+BA.NumberToString(_f[(int) (1)])+", "+BA.NumberToString(_f[(int) (2)]),0);
 //BA.debugLineNum = 408;BA.debugLine="Dim flashModes As List = camEx.GetSupportedFlashM";
_flashmodes = new anywheresoftware.b4a.objects.collections.List();
_flashmodes = mostCurrent._camex._getsupportedflashmodes /*anywheresoftware.b4a.objects.collections.List*/ ();
 //BA.debugLineNum = 409;BA.debugLine="If flashModes.IsInitialized = False Then";
if (_flashmodes.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 410;BA.debugLine="ToastMessageShow(\"Flash not supported.\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Flash not supported."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 411;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 413;BA.debugLine="Dim flash As String = flashModes.Get((flashModes.";
_flash = BA.ObjectToString(_flashmodes.Get((int) ((_flashmodes.IndexOf((Object)(mostCurrent._camex._getflashmode /*String*/ ()))+1)%_flashmodes.getSize())));
 //BA.debugLineNum = 414;BA.debugLine="camEx.SetFlashMode(flash)";
mostCurrent._camex._setflashmode /*String*/ (_flash);
 //BA.debugLineNum = 415;BA.debugLine="ToastMessageShow(flash, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_flash),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 416;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 //BA.debugLineNum = 417;BA.debugLine="End Sub";
return "";
}
public static String  _imgmenu_click() throws Exception{
 //BA.debugLineNum = 155;BA.debugLine="Sub imgMenu_Click";
 //BA.debugLineNum = 156;BA.debugLine="Activity.OpenMenu";
mostCurrent._activity.OpenMenu();
 //BA.debugLineNum = 157;BA.debugLine="End Sub";
return "";
}
public static String  _initializecamera() throws Exception{
 //BA.debugLineNum = 273;BA.debugLine="Private Sub InitializeCamera";
 //BA.debugLineNum = 274;BA.debugLine="Try";
try { //BA.debugLineNum = 275;BA.debugLine="camEx.Initialize(Panel1, frontCamera, Me, \"Camer";
mostCurrent._camex._initialize /*String*/ (mostCurrent.activityBA,mostCurrent._panel1,_frontcamera,frmcamara.getObject(),"Camera1");
 //BA.debugLineNum = 277;BA.debugLine="frontCamera = camEx.Front";
_frontcamera = mostCurrent._camex._front /*boolean*/ ;
 //BA.debugLineNum = 279;BA.debugLine="btnAdjuntarFoto.visible = True";
mostCurrent._btnadjuntarfoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
 } 
       catch (Exception e6) {
			processBA.setLastException(e6); //BA.debugLineNum = 281;BA.debugLine="ToastMessageShow(\"Error en la cámara\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la cámara"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 282;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("217432585",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 284;BA.debugLine="End Sub";
return "";
}
public static String  _mnuadjuntar_click() throws Exception{
 //BA.debugLineNum = 161;BA.debugLine="Sub mnuAdjuntar_Click";
 //BA.debugLineNum = 163;BA.debugLine="adjuntandoFoto = True";
_adjuntandofoto = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 164;BA.debugLine="Try";
try { //BA.debugLineNum = 166;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview /*String*/ ();
 //BA.debugLineNum = 167;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 168;BA.debugLine="DesignaFoto";
_designafoto();
 //BA.debugLineNum = 169;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 171;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 172;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 174;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 177;BA.debugLine="ToastMessageShow(\"Error en la galería, pruebe de";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la galería, pruebe de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 178;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("217301521",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 181;BA.debugLine="End Sub";
return "";
}
public static String  _mnuconsejos_click() throws Exception{
 //BA.debugLineNum = 158;BA.debugLine="Sub mnuConsejos_Click";
 //BA.debugLineNum = 159;BA.debugLine="StartActivity(frmComoFotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmcomofotos.getObject()));
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim adjuntandoFoto As Boolean";
_adjuntandofoto = false;
 //BA.debugLineNum = 10;BA.debugLine="Dim hc As OkHttpClient";
_hc = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private frontCamera As Boolean = False";
_frontcamera = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 14;BA.debugLine="Dim CC As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 17;BA.debugLine="Dim Up1 As UploadFilePhp";
_up1 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 18;BA.debugLine="Dim Up2 As UploadFilePhp";
_up2 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 19;BA.debugLine="Dim Up3 As UploadFilePhp";
_up3 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 20;BA.debugLine="Dim Up4 As UploadFilePhp";
_up4 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 23;BA.debugLine="Dim currentFoto As String";
_currentfoto = "";
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _seekfocus_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 426;BA.debugLine="Sub seekFocus_ValueChanged (Value As Int, UserChan";
 //BA.debugLineNum = 427;BA.debugLine="If UserChanged = False Or camEx.IsZoomSupported =";
if (_userchanged==anywheresoftware.b4a.keywords.Common.False || mostCurrent._camex._iszoomsupported /*boolean*/ ()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 428;BA.debugLine="camEx.Zoom = Value / 100 * camEx.GetMaxZoom";
mostCurrent._camex._setzoom /*int*/ ((int) (_value/(double)100*mostCurrent._camex._getmaxzoom /*int*/ ()));
 //BA.debugLineNum = 429;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static String  _setmaxsize() throws Exception{
cepave.geovin.cameraexclass._camerasize _mincs = null;
cepave.geovin.cameraexclass._camerasize _cs = null;
 //BA.debugLineNum = 305;BA.debugLine="Private Sub SetMaxSize";
 //BA.debugLineNum = 306;BA.debugLine="Dim minCS As CameraSize";
_mincs = new cepave.geovin.cameraexclass._camerasize();
 //BA.debugLineNum = 307;BA.debugLine="For Each cs As CameraSize In camEx.GetSupportedPi";
{
final cepave.geovin.cameraexclass._camerasize[] group2 = mostCurrent._camex._getsupportedpicturessizes /*cepave.geovin.cameraexclass._camerasize[]*/ ();
final int groupLen2 = group2.length
;int index2 = 0;
;
for (; index2 < groupLen2;index2++){
_cs = group2[index2];
 //BA.debugLineNum = 308;BA.debugLine="If minCS.Width = 0 Then";
if (_mincs.Width /*int*/ ==0) { 
 //BA.debugLineNum = 309;BA.debugLine="minCS = cs";
_mincs = _cs;
 }else {
 //BA.debugLineNum = 312;BA.debugLine="If Power(minCS.Width, 2) + Power(minCS.Height,";
if (anywheresoftware.b4a.keywords.Common.Power(_mincs.Width /*int*/ ,2)+anywheresoftware.b4a.keywords.Common.Power(_mincs.Height /*int*/ ,2)<anywheresoftware.b4a.keywords.Common.Power(_cs.Width /*int*/ ,2)+anywheresoftware.b4a.keywords.Common.Power(_cs.Height /*int*/ ,2)) { 
 //BA.debugLineNum = 313;BA.debugLine="minCS = cs";
_mincs = _cs;
 };
 };
 }
};
 //BA.debugLineNum = 317;BA.debugLine="camEx.SetPictureSize(minCS.Width, minCS.Height)";
mostCurrent._camex._setpicturesize /*String*/ (_mincs.Width /*int*/ ,_mincs.Height /*int*/ );
 //BA.debugLineNum = 318;BA.debugLine="Log(\"Selected size: \" & minCS)";
anywheresoftware.b4a.keywords.Common.LogImpl("217563661","Selected size: "+BA.ObjectToString(_mincs),0);
 //BA.debugLineNum = 319;BA.debugLine="End Sub";
return "";
}
}
