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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmcamara");
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
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
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
        BA.LogInfo("** Activity (frmcamara) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
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



public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public anywheresoftware.b4a.keywords.Common __c = null;
public static boolean _sacandofoto = false;
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper _hc = null;
public static anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
public static boolean _frontcamera = false;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public cepave.geovin.cameraexclass _camex = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btntakepicture = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgflash = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinstruccion = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnadjuntarfoto = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuar = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _imgmenu = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _seekfocus = null;
public static String _fotonombredestino = "";
public flm.b4a.betterimageview.BetterImageViewWrapper _imgtransparent = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgtemplate = null;
public static String _newfilename = "";
public static int _fotonumlibre = 0;
public anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomLayoutDialog _msgdialog = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmaprender _frmaprender = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.frmlocalizacion _frmlocalizacion = null;
public cepave.geovin.frmdatosanteriores _frmdatosanteriores = null;
public cepave.geovin.frmperfil _frmperfil = null;
public cepave.geovin.dbutils _dbutils = null;
public cepave.geovin.utilidades _utilidades = null;
public cepave.geovin.frmidentificacionnew _frmidentificacionnew = null;
public cepave.geovin.firebasemessaging _firebasemessaging = null;
public cepave.geovin.starter _starter = null;
public cepave.geovin.uploadfiles _uploadfiles = null;
public cepave.geovin.frmabout _frmabout = null;
public cepave.geovin.register _register = null;
public cepave.geovin.frmreportevinchuca _frmreportevinchuca = null;
public cepave.geovin.envioarchivos2 _envioarchivos2 = null;
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=16711680;
 //BA.debugLineNum = 16711680;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=16711681;
 //BA.debugLineNum = 16711681;BA.debugLine="Activity.LoadLayout(\"camera\")";
mostCurrent._activity.LoadLayout("camera",mostCurrent.activityBA);
RDebugUtils.currentLine=16711684;
 //BA.debugLineNum = 16711684;BA.debugLine="If File.ExternalWritable Then";
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()) { 
RDebugUtils.currentLine=16711685;
 //BA.debugLineNum = 16711685;BA.debugLine="If File.IsDirectory(File.DirRootEx";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"GeoVin/")==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=16711686;
 //BA.debugLineNum = 16711686;BA.debugLine="File.MakeDir(File.DirRootExter";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"GeoVin");
 };
 }else {
RDebugUtils.currentLine=16711689;
 //BA.debugLineNum = 16711689;BA.debugLine="If File.IsDirectory(File.DirIntern";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GeoVin/")==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=16711690;
 //BA.debugLineNum = 16711690;BA.debugLine="File.MakeDir(File.DirInternal,";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GeoVin");
 };
 };
RDebugUtils.currentLine=16711695;
 //BA.debugLineNum = 16711695;BA.debugLine="Activity.AddMenuItem(\"Consejos para sacar fotos\",";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Consejos para sacar fotos"),"mnuConsejos");
RDebugUtils.currentLine=16711696;
 //BA.debugLineNum = 16711696;BA.debugLine="Activity.AddMenuItem(\"Adjuntar foto\", \"mnuAdjunta";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Adjuntar foto"),"mnuAdjuntar");
RDebugUtils.currentLine=16711699;
 //BA.debugLineNum = 16711699;BA.debugLine="If hc.IsInitialized = False Then";
if (_hc.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=16711700;
 //BA.debugLineNum = 16711700;BA.debugLine="hc.Initialize(\"hc\")";
_hc.Initialize("hc");
 };
RDebugUtils.currentLine=16711703;
 //BA.debugLineNum = 16711703;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode}));}
RDebugUtils.currentLine=16842752;
 //BA.debugLineNum = 16842752;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=16842753;
 //BA.debugLineNum = 16842753;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=16842754;
 //BA.debugLineNum = 16842754;BA.debugLine="If Msgbox2(\"Volver al inicio?\", \"SALIR\", \"Si\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Volver al inicio?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
RDebugUtils.currentLine=16842755;
 //BA.debugLineNum = 16842755;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview(null);
RDebugUtils.currentLine=16842756;
 //BA.debugLineNum = 16842756;BA.debugLine="camEx.Release";
mostCurrent._camex._release(null);
RDebugUtils.currentLine=16842757;
 //BA.debugLineNum = 16842757;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=16842758;
 //BA.debugLineNum = 16842758;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
RDebugUtils.currentLine=16842760;
 //BA.debugLineNum = 16842760;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
RDebugUtils.currentLine=16842763;
 //BA.debugLineNum = 16842763;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="frmcamara";
RDebugUtils.currentLine=16908288;
 //BA.debugLineNum = 16908288;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=16908289;
 //BA.debugLineNum = 16908289;BA.debugLine="If camEx.IsInitialized = True Then";
if (mostCurrent._camex.IsInitialized()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=16908290;
 //BA.debugLineNum = 16908290;BA.debugLine="camEx.Release";
mostCurrent._camex._release(null);
 };
RDebugUtils.currentLine=16908293;
 //BA.debugLineNum = 16908293;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=16777216;
 //BA.debugLineNum = 16777216;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=16777217;
 //BA.debugLineNum = 16777217;BA.debugLine="InitializeCamera";
_initializecamera();
RDebugUtils.currentLine=16777218;
 //BA.debugLineNum = 16777218;BA.debugLine="DesignaFoto";
_designafoto();
RDebugUtils.currentLine=16777219;
 //BA.debugLineNum = 16777219;BA.debugLine="End Sub";
return "";
}
public static String  _initializecamera() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "initializecamera"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "initializecamera", null));}
RDebugUtils.currentLine=17235968;
 //BA.debugLineNum = 17235968;BA.debugLine="Private Sub InitializeCamera";
RDebugUtils.currentLine=17235970;
 //BA.debugLineNum = 17235970;BA.debugLine="camEx.Initialize(Panel1, frontCamera, Me, \"Camera";
mostCurrent._camex._initialize(null,mostCurrent.activityBA,mostCurrent._panel1,_frontcamera,frmcamara.getObject(),"Camera1");
RDebugUtils.currentLine=17235973;
 //BA.debugLineNum = 17235973;BA.debugLine="frontCamera = camEx.Front";
_frontcamera = mostCurrent._camex._front;
RDebugUtils.currentLine=17235975;
 //BA.debugLineNum = 17235975;BA.debugLine="End Sub";
return "";
}
public static void  _designafoto() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "designafoto"))
	 {Debug.delegate(mostCurrent.activityBA, "designafoto", null); return;}
ResumableSub_DesignaFoto rsub = new ResumableSub_DesignaFoto(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_DesignaFoto extends BA.ResumableSub {
public ResumableSub_DesignaFoto(cepave.geovin.frmcamara parent) {
this.parent = parent;
}
cepave.geovin.frmcamara parent;
anywheresoftware.b4a.objects.collections.Map _fotomap = null;
String _currentpr = "";
String _foto1str = "";
String _foto2str = "";
String _foto3str = "";
String _foto4str = "";
String _foto5str = "";
int _fotocount = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="frmcamara";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=17170434;
 //BA.debugLineNum = 17170434;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
RDebugUtils.currentLine=17170435;
 //BA.debugLineNum = 17170435;BA.debugLine="ProgressDialogShow(\"Preparando cámara...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Preparando cámara..."));
RDebugUtils.currentLine=17170438;
 //BA.debugLineNum = 17170438;BA.debugLine="Log(\"Designando foto\")";
anywheresoftware.b4a.keywords.Common.Log("Designando foto");
RDebugUtils.currentLine=17170439;
 //BA.debugLineNum = 17170439;BA.debugLine="Dim fotoMap As Map";
_fotomap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=17170440;
 //BA.debugLineNum = 17170440;BA.debugLine="fotoMap.Initialize";
_fotomap.Initialize();
RDebugUtils.currentLine=17170441;
 //BA.debugLineNum = 17170441;BA.debugLine="Dim currentpr As String";
_currentpr = "";
RDebugUtils.currentLine=17170442;
 //BA.debugLineNum = 17170442;BA.debugLine="currentpr = Main.currentproject";
_currentpr = parent.mostCurrent._main._currentproject;
RDebugUtils.currentLine=17170443;
 //BA.debugLineNum = 17170443;BA.debugLine="Log(\"currentproject=\" & currentpr)";
anywheresoftware.b4a.keywords.Common.Log("currentproject="+_currentpr);
RDebugUtils.currentLine=17170445;
 //BA.debugLineNum = 17170445;BA.debugLine="fotoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_fotomap = parent.mostCurrent._dbutils._executemap(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"SELECT * FROM markers_local WHERE Id=?",new String[]{_currentpr});
RDebugUtils.currentLine=17170447;
 //BA.debugLineNum = 17170447;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "frmcamara", "designafoto"),(int) (200));
this.state = 96;
return;
case 96:
//C
this.state = 1;
;
RDebugUtils.currentLine=17170449;
 //BA.debugLineNum = 17170449;BA.debugLine="If fotoMap = Null Or fotoMap.IsInitialized = Fals";
if (true) break;

case 1:
//if
this.state = 58;
if (_fotomap== null || _fotomap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 58;
RDebugUtils.currentLine=17170450;
 //BA.debugLineNum = 17170450;BA.debugLine="Log(\"no hay fotomap\")";
anywheresoftware.b4a.keywords.Common.Log("no hay fotomap");
RDebugUtils.currentLine=17170451;
 //BA.debugLineNum = 17170451;BA.debugLine="fotoNombreDestino = \"\"";
parent.mostCurrent._fotonombredestino = "";
RDebugUtils.currentLine=17170452;
 //BA.debugLineNum = 17170452;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 5:
//C
this.state = 6;
RDebugUtils.currentLine=17170454;
 //BA.debugLineNum = 17170454;BA.debugLine="Log(\"hay fotomap\")";
anywheresoftware.b4a.keywords.Common.Log("hay fotomap");
RDebugUtils.currentLine=17170455;
 //BA.debugLineNum = 17170455;BA.debugLine="Dim foto1str As String = fotoMap.Get(\"foto1\")";
_foto1str = BA.ObjectToString(_fotomap.Get((Object)("foto1")));
RDebugUtils.currentLine=17170456;
 //BA.debugLineNum = 17170456;BA.debugLine="Dim foto2str As String = fotoMap.Get(\"foto2\")";
_foto2str = BA.ObjectToString(_fotomap.Get((Object)("foto2")));
RDebugUtils.currentLine=17170457;
 //BA.debugLineNum = 17170457;BA.debugLine="Dim foto3str As String = fotoMap.Get(\"foto3\")";
_foto3str = BA.ObjectToString(_fotomap.Get((Object)("foto3")));
RDebugUtils.currentLine=17170458;
 //BA.debugLineNum = 17170458;BA.debugLine="Dim foto4str As String = fotoMap.Get(\"foto4\")";
_foto4str = BA.ObjectToString(_fotomap.Get((Object)("foto4")));
RDebugUtils.currentLine=17170459;
 //BA.debugLineNum = 17170459;BA.debugLine="Dim foto5str As String = fotoMap.Get(\"foto5\")";
_foto5str = BA.ObjectToString(_fotomap.Get((Object)("foto5")));
RDebugUtils.currentLine=17170460;
 //BA.debugLineNum = 17170460;BA.debugLine="Log(\"Foto1:\" & foto1str)";
anywheresoftware.b4a.keywords.Common.Log("Foto1:"+_foto1str);
RDebugUtils.currentLine=17170461;
 //BA.debugLineNum = 17170461;BA.debugLine="Log(\"Foto2:\" & foto2str)";
anywheresoftware.b4a.keywords.Common.Log("Foto2:"+_foto2str);
RDebugUtils.currentLine=17170462;
 //BA.debugLineNum = 17170462;BA.debugLine="Log(\"Foto3:\" & foto3str)";
anywheresoftware.b4a.keywords.Common.Log("Foto3:"+_foto3str);
RDebugUtils.currentLine=17170463;
 //BA.debugLineNum = 17170463;BA.debugLine="Log(\"Foto4:\" & foto4str)";
anywheresoftware.b4a.keywords.Common.Log("Foto4:"+_foto4str);
RDebugUtils.currentLine=17170464;
 //BA.debugLineNum = 17170464;BA.debugLine="Log(\"Foto5:\" & foto5str)";
anywheresoftware.b4a.keywords.Common.Log("Foto5:"+_foto5str);
RDebugUtils.currentLine=17170470;
 //BA.debugLineNum = 17170470;BA.debugLine="If foto1str = \"\" Or foto1str = \"null\" Then";
if (true) break;

case 6:
//if
this.state = 21;
if ((_foto1str).equals("") || (_foto1str).equals("null")) { 
this.state = 8;
}else 
{RDebugUtils.currentLine=17170475;
 //BA.debugLineNum = 17170475;BA.debugLine="Else If foto2str = \"\" Or foto2str = \"null\" Then";
if ((_foto2str).equals("") || (_foto2str).equals("null")) { 
this.state = 10;
}else 
{RDebugUtils.currentLine=17170479;
 //BA.debugLineNum = 17170479;BA.debugLine="Else If foto3str = \"\" Or foto3str = \"null\" Then";
if ((_foto3str).equals("") || (_foto3str).equals("null")) { 
this.state = 12;
}else 
{RDebugUtils.currentLine=17170483;
 //BA.debugLineNum = 17170483;BA.debugLine="Else If foto4str = \"\" Or foto4str = \"null\" Then";
if ((_foto4str).equals("") || (_foto4str).equals("null")) { 
this.state = 14;
}else 
{RDebugUtils.currentLine=17170487;
 //BA.debugLineNum = 17170487;BA.debugLine="Else If foto5str = \"\" Or foto5str = \"null\" Then";
if ((_foto5str).equals("") || (_foto5str).equals("null")) { 
this.state = 16;
}}}}}
if (true) break;

case 8:
//C
this.state = 21;
RDebugUtils.currentLine=17170471;
 //BA.debugLineNum = 17170471;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentp";
parent.mostCurrent._fotonombredestino = parent.mostCurrent._frmprincipal._fullidcurrentproject+"_1";
RDebugUtils.currentLine=17170472;
 //BA.debugLineNum = 17170472;BA.debugLine="fotonumlibre = 1";
parent._fotonumlibre = (int) (1);
RDebugUtils.currentLine=17170473;
 //BA.debugLineNum = 17170473;BA.debugLine="Log(\"Va la foto 1\")";
anywheresoftware.b4a.keywords.Common.Log("Va la foto 1");
RDebugUtils.currentLine=17170474;
 //BA.debugLineNum = 17170474;BA.debugLine="lblInstruccion.Text = \"FOTO 1: Toma una foto de";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("FOTO 1: Toma una foto del insecto de perfil"));
 if (true) break;

case 10:
//C
this.state = 21;
RDebugUtils.currentLine=17170476;
 //BA.debugLineNum = 17170476;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentp";
parent.mostCurrent._fotonombredestino = parent.mostCurrent._frmprincipal._fullidcurrentproject+"_2";
RDebugUtils.currentLine=17170477;
 //BA.debugLineNum = 17170477;BA.debugLine="fotonumlibre = 2";
parent._fotonumlibre = (int) (2);
RDebugUtils.currentLine=17170478;
 //BA.debugLineNum = 17170478;BA.debugLine="lblInstruccion.Text = \"FOTO 2: Toma una foto de";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("FOTO 2: Toma una foto del insecto desde arriba"));
 if (true) break;

case 12:
//C
this.state = 21;
RDebugUtils.currentLine=17170480;
 //BA.debugLineNum = 17170480;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentp";
parent.mostCurrent._fotonombredestino = parent.mostCurrent._frmprincipal._fullidcurrentproject+"_3";
RDebugUtils.currentLine=17170481;
 //BA.debugLineNum = 17170481;BA.debugLine="fotonumlibre = 3";
parent._fotonumlibre = (int) (3);
RDebugUtils.currentLine=17170482;
 //BA.debugLineNum = 17170482;BA.debugLine="lblInstruccion.Text = \"FOTO 3: Toma una foto de";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("FOTO 3: Toma una foto del insecto desde abajo"));
 if (true) break;

case 14:
//C
this.state = 21;
RDebugUtils.currentLine=17170484;
 //BA.debugLineNum = 17170484;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentp";
parent.mostCurrent._fotonombredestino = parent.mostCurrent._frmprincipal._fullidcurrentproject+"_4";
RDebugUtils.currentLine=17170485;
 //BA.debugLineNum = 17170485;BA.debugLine="fotonumlibre = 4";
parent._fotonumlibre = (int) (4);
RDebugUtils.currentLine=17170486;
 //BA.debugLineNum = 17170486;BA.debugLine="lblInstruccion.Text = \"FOTO 4: Toma una foto de";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("FOTO 4: Toma una foto del ambiente adonde encontraste el insecto"));
 if (true) break;

case 16:
//C
this.state = 17;
RDebugUtils.currentLine=17170488;
 //BA.debugLineNum = 17170488;BA.debugLine="lblInstruccion.Text = \"Has finalizado la toma d";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Has finalizado la toma de fotos, presiona continuar!"));
RDebugUtils.currentLine=17170489;
 //BA.debugLineNum = 17170489;BA.debugLine="If imgtransparent.IsInitialized Then";
if (true) break;

case 17:
//if
this.state = 20;
if (parent.mostCurrent._imgtransparent.IsInitialized()) { 
this.state = 19;
}if (true) break;

case 19:
//C
this.state = 20;
RDebugUtils.currentLine=17170490;
 //BA.debugLineNum = 17170490;BA.debugLine="imgtransparent.removeView";
parent.mostCurrent._imgtransparent.RemoveView();
 if (true) break;

case 20:
//C
this.state = 21;
;
 if (true) break;
;
RDebugUtils.currentLine=17170496;
 //BA.debugLineNum = 17170496;BA.debugLine="If foto1str <> \"\" And foto1str <> \"null\" Then";

case 21:
//if
this.state = 30;
if ((_foto1str).equals("") == false && (_foto1str).equals("null") == false) { 
this.state = 23;
}if (true) break;

case 23:
//C
this.state = 24;
RDebugUtils.currentLine=17170498;
 //BA.debugLineNum = 17170498;BA.debugLine="Log(\"sigue foto 1\")";
anywheresoftware.b4a.keywords.Common.Log("sigue foto 1");
RDebugUtils.currentLine=17170499;
 //BA.debugLineNum = 17170499;BA.debugLine="imgSitio1.Visible = True";
parent.mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17170500;
 //BA.debugLineNum = 17170500;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin";
if (true) break;

case 24:
//if
this.state = 29;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",BA.ObjectToString(_fotomap.Get((Object)("foto1")))+".jpg")) { 
this.state = 26;
}else {
this.state = 28;
}if (true) break;

case 26:
//C
this.state = 29;
RDebugUtils.currentLine=17170501;
 //BA.debugLineNum = 17170501;BA.debugLine="imgSitio1.Bitmap = Null";
parent.mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17170502;
 //BA.debugLineNum = 17170502;BA.debugLine="imgSitio1.Bitmap = LoadBitmapSample(File.DirR";
parent.mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",BA.ObjectToString(_fotomap.Get((Object)("foto1")))+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=17170503;
 //BA.debugLineNum = 17170503;BA.debugLine="btnContinuar.Visible = False";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 28:
//C
this.state = 29;
RDebugUtils.currentLine=17170505;
 //BA.debugLineNum = 17170505;BA.debugLine="imgSitio1.Visible = False";
parent.mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17170506;
 //BA.debugLineNum = 17170506;BA.debugLine="btnContinuar.Visible = False";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 29:
//C
this.state = 30;
;
 if (true) break;
;
RDebugUtils.currentLine=17170509;
 //BA.debugLineNum = 17170509;BA.debugLine="If foto2str <> \"\" And foto2str <> \"null\" Then";

case 30:
//if
this.state = 39;
if ((_foto2str).equals("") == false && (_foto2str).equals("null") == false) { 
this.state = 32;
}if (true) break;

case 32:
//C
this.state = 33;
RDebugUtils.currentLine=17170510;
 //BA.debugLineNum = 17170510;BA.debugLine="Log(\"sigue foto 2\")";
anywheresoftware.b4a.keywords.Common.Log("sigue foto 2");
RDebugUtils.currentLine=17170511;
 //BA.debugLineNum = 17170511;BA.debugLine="imgSitio2.Visible = True";
parent.mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17170512;
 //BA.debugLineNum = 17170512;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin";
if (true) break;

case 33:
//if
this.state = 38;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",BA.ObjectToString(_fotomap.Get((Object)("foto2")))+".jpg")) { 
this.state = 35;
}else {
this.state = 37;
}if (true) break;

case 35:
//C
this.state = 38;
RDebugUtils.currentLine=17170513;
 //BA.debugLineNum = 17170513;BA.debugLine="imgSitio2.Bitmap = Null";
parent.mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17170514;
 //BA.debugLineNum = 17170514;BA.debugLine="imgSitio2.Bitmap = LoadBitmapSample(File.DirR";
parent.mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",BA.ObjectToString(_fotomap.Get((Object)("foto2")))+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=17170515;
 //BA.debugLineNum = 17170515;BA.debugLine="btnContinuar.Visible = True";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 37:
//C
this.state = 38;
RDebugUtils.currentLine=17170517;
 //BA.debugLineNum = 17170517;BA.debugLine="imgSitio2.Visible = False";
parent.mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17170518;
 //BA.debugLineNum = 17170518;BA.debugLine="btnContinuar.Visible = False";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 38:
//C
this.state = 39;
;
 if (true) break;
;
RDebugUtils.currentLine=17170521;
 //BA.debugLineNum = 17170521;BA.debugLine="If foto3str <> \"\" And foto3str <> \"null\" Then";

case 39:
//if
this.state = 48;
if ((_foto3str).equals("") == false && (_foto3str).equals("null") == false) { 
this.state = 41;
}if (true) break;

case 41:
//C
this.state = 42;
RDebugUtils.currentLine=17170522;
 //BA.debugLineNum = 17170522;BA.debugLine="Log(\"sigue foto 3\")";
anywheresoftware.b4a.keywords.Common.Log("sigue foto 3");
RDebugUtils.currentLine=17170523;
 //BA.debugLineNum = 17170523;BA.debugLine="imgSitio3.Visible = True";
parent.mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17170524;
 //BA.debugLineNum = 17170524;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin";
if (true) break;

case 42:
//if
this.state = 47;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",BA.ObjectToString(_fotomap.Get((Object)("foto3")))+".jpg")) { 
this.state = 44;
}else {
this.state = 46;
}if (true) break;

case 44:
//C
this.state = 47;
RDebugUtils.currentLine=17170525;
 //BA.debugLineNum = 17170525;BA.debugLine="imgSitio3.Bitmap = Null";
parent.mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17170526;
 //BA.debugLineNum = 17170526;BA.debugLine="imgSitio3.Bitmap = LoadBitmapSample(File.DirR";
parent.mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",BA.ObjectToString(_fotomap.Get((Object)("foto3")))+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 if (true) break;

case 46:
//C
this.state = 47;
RDebugUtils.currentLine=17170528;
 //BA.debugLineNum = 17170528;BA.debugLine="imgSitio3.Visible = False";
parent.mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 47:
//C
this.state = 48;
;
 if (true) break;
;
RDebugUtils.currentLine=17170531;
 //BA.debugLineNum = 17170531;BA.debugLine="If foto4str <> \"\" And foto4str <> \"null\" Then";

case 48:
//if
this.state = 57;
if ((_foto4str).equals("") == false && (_foto4str).equals("null") == false) { 
this.state = 50;
}if (true) break;

case 50:
//C
this.state = 51;
RDebugUtils.currentLine=17170532;
 //BA.debugLineNum = 17170532;BA.debugLine="Log(\"sigue foto 4\")";
anywheresoftware.b4a.keywords.Common.Log("sigue foto 4");
RDebugUtils.currentLine=17170533;
 //BA.debugLineNum = 17170533;BA.debugLine="imgSitio4.Visible = True";
parent.mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17170534;
 //BA.debugLineNum = 17170534;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin";
if (true) break;

case 51:
//if
this.state = 56;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",BA.ObjectToString(_fotomap.Get((Object)("foto4")))+".jpg")) { 
this.state = 53;
}else {
this.state = 55;
}if (true) break;

case 53:
//C
this.state = 56;
RDebugUtils.currentLine=17170535;
 //BA.debugLineNum = 17170535;BA.debugLine="imgSitio4.Bitmap = Null";
parent.mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17170536;
 //BA.debugLineNum = 17170536;BA.debugLine="imgSitio4.Bitmap = LoadBitmapSample(File.DirR";
parent.mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",BA.ObjectToString(_fotomap.Get((Object)("foto4")))+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 if (true) break;

case 55:
//C
this.state = 56;
RDebugUtils.currentLine=17170538;
 //BA.debugLineNum = 17170538;BA.debugLine="imgSitio4.Visible = False";
parent.mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 56:
//C
this.state = 57;
;
 if (true) break;

case 57:
//C
this.state = 58;
;
 if (true) break;

case 58:
//C
this.state = 59;
;
RDebugUtils.currentLine=17170544;
 //BA.debugLineNum = 17170544;BA.debugLine="Dim fotocount As Int = 0";
_fotocount = (int) (0);
RDebugUtils.currentLine=17170545;
 //BA.debugLineNum = 17170545;BA.debugLine="If imgSitio1.Bitmap <> Null Then";
if (true) break;

case 59:
//if
this.state = 62;
if (parent.mostCurrent._imgsitio1.getBitmap()!= null) { 
this.state = 61;
}if (true) break;

case 61:
//C
this.state = 62;
RDebugUtils.currentLine=17170546;
 //BA.debugLineNum = 17170546;BA.debugLine="fotocount = fotocount + 1";
_fotocount = (int) (_fotocount+1);
 if (true) break;
;
RDebugUtils.currentLine=17170548;
 //BA.debugLineNum = 17170548;BA.debugLine="If imgSitio2.Bitmap <> Null Then";

case 62:
//if
this.state = 65;
if (parent.mostCurrent._imgsitio2.getBitmap()!= null) { 
this.state = 64;
}if (true) break;

case 64:
//C
this.state = 65;
RDebugUtils.currentLine=17170549;
 //BA.debugLineNum = 17170549;BA.debugLine="fotocount = fotocount + 1";
_fotocount = (int) (_fotocount+1);
 if (true) break;
;
RDebugUtils.currentLine=17170551;
 //BA.debugLineNum = 17170551;BA.debugLine="If imgSitio3.Bitmap <> Null Then";

case 65:
//if
this.state = 68;
if (parent.mostCurrent._imgsitio3.getBitmap()!= null) { 
this.state = 67;
}if (true) break;

case 67:
//C
this.state = 68;
RDebugUtils.currentLine=17170552;
 //BA.debugLineNum = 17170552;BA.debugLine="fotocount = fotocount + 1";
_fotocount = (int) (_fotocount+1);
 if (true) break;
;
RDebugUtils.currentLine=17170554;
 //BA.debugLineNum = 17170554;BA.debugLine="If imgSitio4.Bitmap <> Null Then";

case 68:
//if
this.state = 71;
if (parent.mostCurrent._imgsitio4.getBitmap()!= null) { 
this.state = 70;
}if (true) break;

case 70:
//C
this.state = 71;
RDebugUtils.currentLine=17170555;
 //BA.debugLineNum = 17170555;BA.debugLine="fotocount = fotocount + 1";
_fotocount = (int) (_fotocount+1);
 if (true) break;

case 71:
//C
this.state = 72;
;
RDebugUtils.currentLine=17170558;
 //BA.debugLineNum = 17170558;BA.debugLine="Log(\"fotocount: \" & fotocount)";
anywheresoftware.b4a.keywords.Common.Log("fotocount: "+BA.NumberToString(_fotocount));
RDebugUtils.currentLine=17170559;
 //BA.debugLineNum = 17170559;BA.debugLine="If fotocount >= 2 Then";
if (true) break;

case 72:
//if
this.state = 77;
if (_fotocount>=2) { 
this.state = 74;
}else {
this.state = 76;
}if (true) break;

case 74:
//C
this.state = 77;
RDebugUtils.currentLine=17170560;
 //BA.debugLineNum = 17170560;BA.debugLine="btnContinuar.Visible = True";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 76:
//C
this.state = 77;
RDebugUtils.currentLine=17170562;
 //BA.debugLineNum = 17170562;BA.debugLine="btnContinuar.Visible = False";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
RDebugUtils.currentLine=17170564;
 //BA.debugLineNum = 17170564;BA.debugLine="If fotocount = 4 Then";

case 77:
//if
this.state = 82;
if (_fotocount==4) { 
this.state = 79;
}else {
this.state = 81;
}if (true) break;

case 79:
//C
this.state = 82;
RDebugUtils.currentLine=17170565;
 //BA.debugLineNum = 17170565;BA.debugLine="btnTakePicture.Visible = False";
parent.mostCurrent._btntakepicture.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 81:
//C
this.state = 82;
RDebugUtils.currentLine=17170567;
 //BA.debugLineNum = 17170567;BA.debugLine="btnTakePicture.Visible = True";
parent.mostCurrent._btntakepicture.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 82:
//C
this.state = 83;
;
RDebugUtils.currentLine=17170572;
 //BA.debugLineNum = 17170572;BA.debugLine="Panel1.Visible = True";
parent.mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17170573;
 //BA.debugLineNum = 17170573;BA.debugLine="imgTemplate.Left = 0";
parent.mostCurrent._imgtemplate.setLeft((int) (0));
RDebugUtils.currentLine=17170574;
 //BA.debugLineNum = 17170574;BA.debugLine="imgTemplate.Visible = False";
parent.mostCurrent._imgtemplate.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17170575;
 //BA.debugLineNum = 17170575;BA.debugLine="If imgtransparent.IsInitialized Then";
if (true) break;

case 83:
//if
this.state = 86;
if (parent.mostCurrent._imgtransparent.IsInitialized()) { 
this.state = 85;
}if (true) break;

case 85:
//C
this.state = 86;
RDebugUtils.currentLine=17170576;
 //BA.debugLineNum = 17170576;BA.debugLine="imgtransparent.removeView";
parent.mostCurrent._imgtransparent.RemoveView();
 if (true) break;
;
RDebugUtils.currentLine=17170578;
 //BA.debugLineNum = 17170578;BA.debugLine="If fotonumlibre = 1 Then";

case 86:
//if
this.state = 95;
if (parent._fotonumlibre==1) { 
this.state = 88;
}else 
{RDebugUtils.currentLine=17170589;
 //BA.debugLineNum = 17170589;BA.debugLine="Else If fotonumlibre = 2 Then";
if (parent._fotonumlibre==2) { 
this.state = 90;
}else 
{RDebugUtils.currentLine=17170599;
 //BA.debugLineNum = 17170599;BA.debugLine="Else If fotonumlibre = 3 Then";
if (parent._fotonumlibre==3) { 
this.state = 92;
}else 
{RDebugUtils.currentLine=17170609;
 //BA.debugLineNum = 17170609;BA.debugLine="Else If fotonumlibre = 4 Then";
if (parent._fotonumlibre==4) { 
this.state = 94;
}}}}
if (true) break;

case 88:
//C
this.state = 95;
RDebugUtils.currentLine=17170579;
 //BA.debugLineNum = 17170579;BA.debugLine="imgtransparent.Initialize(\"\")";
parent.mostCurrent._imgtransparent.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=17170580;
 //BA.debugLineNum = 17170580;BA.debugLine="imgtransparent.Bitmap = Null";
parent.mostCurrent._imgtransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17170581;
 //BA.debugLineNum = 17170581;BA.debugLine="imgtransparent.Bitmap = LoadBitmapSample(File.Di";
parent.mostCurrent._imgtransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"lateral.png",parent.mostCurrent._activity.getWidth(),parent.mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=17170582;
 //BA.debugLineNum = 17170582;BA.debugLine="imgtransparent.Gravity = Gravity.FILL";
parent.mostCurrent._imgtransparent.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=17170583;
 //BA.debugLineNum = 17170583;BA.debugLine="imgtransparent.ChangeAlpha(100)";
parent.mostCurrent._imgtransparent.ChangeAlpha((int) (100));
RDebugUtils.currentLine=17170584;
 //BA.debugLineNum = 17170584;BA.debugLine="imgTemplate.Height = 25%y";
parent.mostCurrent._imgtemplate.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA));
RDebugUtils.currentLine=17170585;
 //BA.debugLineNum = 17170585;BA.debugLine="imgTemplate.Top = 50%y - imgTemplate.Height / 2";
parent.mostCurrent._imgtemplate.setTop((int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA)-parent.mostCurrent._imgtemplate.getHeight()/(double)2));
RDebugUtils.currentLine=17170586;
 //BA.debugLineNum = 17170586;BA.debugLine="Activity.AddView(imgtransparent, imgTemplate.Lef";
parent.mostCurrent._activity.AddView((android.view.View)(parent.mostCurrent._imgtransparent.getObject()),parent.mostCurrent._imgtemplate.getLeft(),parent.mostCurrent._imgtemplate.getTop(),parent.mostCurrent._imgtemplate.getWidth(),parent.mostCurrent._imgtemplate.getHeight());
 if (true) break;

case 90:
//C
this.state = 95;
RDebugUtils.currentLine=17170590;
 //BA.debugLineNum = 17170590;BA.debugLine="imgtransparent.Initialize(\"\")";
parent.mostCurrent._imgtransparent.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=17170591;
 //BA.debugLineNum = 17170591;BA.debugLine="imgtransparent.Bitmap = Null";
parent.mostCurrent._imgtransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17170592;
 //BA.debugLineNum = 17170592;BA.debugLine="imgtransparent.Bitmap = LoadBitmapSample(File.Di";
parent.mostCurrent._imgtransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"dorsal.png",parent.mostCurrent._activity.getWidth(),parent.mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=17170593;
 //BA.debugLineNum = 17170593;BA.debugLine="imgtransparent.Gravity = Gravity.FILL";
parent.mostCurrent._imgtransparent.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=17170594;
 //BA.debugLineNum = 17170594;BA.debugLine="imgtransparent.ChangeAlpha(100)";
parent.mostCurrent._imgtransparent.ChangeAlpha((int) (100));
RDebugUtils.currentLine=17170595;
 //BA.debugLineNum = 17170595;BA.debugLine="imgTemplate.Top = 0";
parent.mostCurrent._imgtemplate.setTop((int) (0));
RDebugUtils.currentLine=17170596;
 //BA.debugLineNum = 17170596;BA.debugLine="imgTemplate.Height = 100%y";
parent.mostCurrent._imgtemplate.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=17170597;
 //BA.debugLineNum = 17170597;BA.debugLine="Activity.AddView(imgtransparent, imgTemplate.Lef";
parent.mostCurrent._activity.AddView((android.view.View)(parent.mostCurrent._imgtransparent.getObject()),parent.mostCurrent._imgtemplate.getLeft(),parent.mostCurrent._imgtemplate.getTop(),parent.mostCurrent._imgtemplate.getWidth(),parent.mostCurrent._imgtemplate.getHeight());
 if (true) break;

case 92:
//C
this.state = 95;
RDebugUtils.currentLine=17170600;
 //BA.debugLineNum = 17170600;BA.debugLine="imgtransparent.Initialize(\"\")";
parent.mostCurrent._imgtransparent.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=17170601;
 //BA.debugLineNum = 17170601;BA.debugLine="imgtransparent.Bitmap = Null";
parent.mostCurrent._imgtransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17170602;
 //BA.debugLineNum = 17170602;BA.debugLine="imgtransparent.Bitmap = LoadBitmapSample(File.Di";
parent.mostCurrent._imgtransparent.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"dorsal.png",parent.mostCurrent._activity.getWidth(),parent.mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=17170603;
 //BA.debugLineNum = 17170603;BA.debugLine="imgtransparent.Gravity = Gravity.FILL";
parent.mostCurrent._imgtransparent.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=17170604;
 //BA.debugLineNum = 17170604;BA.debugLine="imgtransparent.ChangeAlpha(100)";
parent.mostCurrent._imgtransparent.ChangeAlpha((int) (100));
RDebugUtils.currentLine=17170605;
 //BA.debugLineNum = 17170605;BA.debugLine="imgTemplate.Top = 0";
parent.mostCurrent._imgtemplate.setTop((int) (0));
RDebugUtils.currentLine=17170606;
 //BA.debugLineNum = 17170606;BA.debugLine="imgTemplate.Height = 100%y";
parent.mostCurrent._imgtemplate.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=17170607;
 //BA.debugLineNum = 17170607;BA.debugLine="Activity.AddView(imgtransparent, imgTemplate.Lef";
parent.mostCurrent._activity.AddView((android.view.View)(parent.mostCurrent._imgtransparent.getObject()),parent.mostCurrent._imgtemplate.getLeft(),parent.mostCurrent._imgtemplate.getTop(),parent.mostCurrent._imgtemplate.getWidth(),parent.mostCurrent._imgtemplate.getHeight());
 if (true) break;

case 94:
//C
this.state = 95;
 if (true) break;

case 95:
//C
this.state = -1;
;
RDebugUtils.currentLine=17170616;
 //BA.debugLineNum = 17170616;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=17170618;
 //BA.debugLineNum = 17170618;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btnadjuntarfoto_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnadjuntarfoto_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnadjuntarfoto_click", null));}
RDebugUtils.currentLine=17694720;
 //BA.debugLineNum = 17694720;BA.debugLine="Sub btnAdjuntarFoto_Click";
RDebugUtils.currentLine=17694722;
 //BA.debugLineNum = 17694722;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview(null);
RDebugUtils.currentLine=17694723;
 //BA.debugLineNum = 17694723;BA.debugLine="camEx.Release";
mostCurrent._camex._release(null);
RDebugUtils.currentLine=17694724;
 //BA.debugLineNum = 17694724;BA.debugLine="DesignaFoto";
_designafoto();
RDebugUtils.currentLine=17694725;
 //BA.debugLineNum = 17694725;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
RDebugUtils.currentLine=17694726;
 //BA.debugLineNum = 17694726;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
RDebugUtils.currentLine=17694727;
 //BA.debugLineNum = 17694727;BA.debugLine="End Sub";
return "";
}
public static void  _btncontinuar_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncontinuar_click"))
	 {Debug.delegate(mostCurrent.activityBA, "btncontinuar_click", null); return;}
ResumableSub_btnContinuar_Click rsub = new ResumableSub_btnContinuar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnContinuar_Click extends BA.ResumableSub {
public ResumableSub_btnContinuar_Click(cepave.geovin.frmcamara parent) {
this.parent = parent;
}
cepave.geovin.frmcamara parent;
int _result = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _msg = "";
String _username = "";
String _dateandtime = "";
String _nombresitio = "";
String _tiporio = "";
String _lat = "";
String _lng = "";
String _gpsdetect = "";
String _wifidetect = "";
String _mapadetect = "";
String _valorind1 = "";
String _valorind2 = "";
String _valorind3 = "";
String _valorind4 = "";
String _valorind5 = "";
String _valorind6 = "";
String _valorind7 = "";
String _valorind8 = "";
String _valorind9 = "";
String _valorind10 = "";
String _valorind11 = "";
String _valorind12 = "";
String _valorind13 = "";
String _valorind14 = "";
String _valorind15 = "";
String _valorind16 = "";
String _valorind17 = "";
String _valorind18 = "";
String _valorind19 = "";
String _valorind20 = "";
String _foto1 = "";
String _foto2 = "";
String _foto3 = "";
String _foto4 = "";
String _terminado = "";
String _privado = "";
String _estadovalidacion = "";
String _deviceid = "";
anywheresoftware.b4a.objects.collections.Map _datosmap = null;
anywheresoftware.b4a.samples.httputils2.httpjob _enviomarcadores = null;
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
String _serverid = "";
anywheresoftware.b4a.objects.collections.Map _nv = null;
anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest _req = null;
anywheresoftware.b4a.objects.collections.List _files = null;
cepave.geovin.uploadfiles._filedata _fd = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="frmcamara";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=17891330;
 //BA.debugLineNum = 17891330;BA.debugLine="camEx.StopPreview";
parent.mostCurrent._camex._stoppreview(null);
RDebugUtils.currentLine=17891331;
 //BA.debugLineNum = 17891331;BA.debugLine="camEx.Release";
parent.mostCurrent._camex._release(null);
RDebugUtils.currentLine=17891334;
 //BA.debugLineNum = 17891334;BA.debugLine="Wait For(CheckInternet) Complete (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "frmcamara", "btncontinuar_click"), _checkinternet());
this.state = 86;
return;
case 86:
//C
this.state = 1;
_result = (Integer) result[0];
;
RDebugUtils.currentLine=17891335;
 //BA.debugLineNum = 17891335;BA.debugLine="If Result = 0 Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_result==0) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
RDebugUtils.currentLine=17891336;
 //BA.debugLineNum = 17891336;BA.debugLine="Msgbox(\"No hay conexión a internet, prueba cuand";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No hay conexión a internet, prueba cuando estés conectado!"),BA.ObjectToCharSequence("No hay internet"),mostCurrent.activityBA);
RDebugUtils.currentLine=17891337;
 //BA.debugLineNum = 17891337;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
RDebugUtils.currentLine=17891338;
 //BA.debugLineNum = 17891338;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;
;
RDebugUtils.currentLine=17891341;
 //BA.debugLineNum = 17891341;BA.debugLine="If Main.modooffline = True Then";

case 4:
//if
this.state = 7;
if (parent.mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
RDebugUtils.currentLine=17891342;
 //BA.debugLineNum = 17891342;BA.debugLine="Msgbox(\"Esta trabajando en modo offline. El arch";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Esta trabajando en modo offline. El archivo se guardará para que lo pueda enviar luego desde 'Mi Perfil'"),BA.ObjectToCharSequence("Modo offline"),mostCurrent.activityBA);
RDebugUtils.currentLine=17891343;
 //BA.debugLineNum = 17891343;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=17891344;
 //BA.debugLineNum = 17891344;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
RDebugUtils.currentLine=17891345;
 //BA.debugLineNum = 17891345;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmprincipal.getObject()));
RDebugUtils.currentLine=17891346;
 //BA.debugLineNum = 17891346;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 8;
;
RDebugUtils.currentLine=17891350;
 //BA.debugLineNum = 17891350;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=17891351;
 //BA.debugLineNum = 17891351;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=17891352;
 //BA.debugLineNum = 17891352;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._main._currentproject));
RDebugUtils.currentLine=17891353;
 //BA.debugLineNum = 17891353;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"markers_local","privado",(Object)("no"),_map1);
RDebugUtils.currentLine=17891355;
 //BA.debugLineNum = 17891355;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=17891356;
 //BA.debugLineNum = 17891356;BA.debugLine="msg = utilidades.Mensaje(\"Envío de datos\", \"MsgUp";
_msg = parent.mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Envío de datos","MsgUpload.png","Se enviarán los datos a un revisor especializado","Por defecto, el reporte que envías será de visibilidad pública. Si deseas hacerlo privado y que no sea exhibido en el sitio web cambia la opción desde tu perfil","Enviar datos","No enviar!","",anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17891357;
 //BA.debugLineNum = 17891357;BA.debugLine="If msg <> DialogResponse.POSITIVE Then";
if (true) break;

case 8:
//if
this.state = 11;
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) == false) { 
this.state = 10;
}if (true) break;

case 10:
//C
this.state = 11;
RDebugUtils.currentLine=17891358;
 //BA.debugLineNum = 17891358;BA.debugLine="Msgbox(\"Los datos quedarán guardados para que lo";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Los datos quedarán guardados para que los envíe luego"),BA.ObjectToCharSequence("Datos guardados"),mostCurrent.activityBA);
RDebugUtils.currentLine=17891359;
 //BA.debugLineNum = 17891359;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=17891360;
 //BA.debugLineNum = 17891360;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
RDebugUtils.currentLine=17891361;
 //BA.debugLineNum = 17891361;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 11:
//C
this.state = 12;
;
RDebugUtils.currentLine=17891365;
 //BA.debugLineNum = 17891365;BA.debugLine="ProgressDialogShow(\"Enviando datos... aguarde por";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando datos... aguarde por favor"));
RDebugUtils.currentLine=17891369;
 //BA.debugLineNum = 17891369;BA.debugLine="Dim username,dateandtime,nombresitio,tiporio,lat,";
_username = "";
_dateandtime = "";
_nombresitio = "";
_tiporio = "";
_lat = "";
_lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
RDebugUtils.currentLine=17891370;
 //BA.debugLineNum = 17891370;BA.debugLine="Dim valorind1,valorind2,valorind3,valorind4,valor";
_valorind1 = "";
_valorind2 = "";
_valorind3 = "";
_valorind4 = "";
_valorind5 = "";
_valorind6 = "";
_valorind7 = "";
_valorind8 = "";
_valorind9 = "";
_valorind10 = "";
_valorind11 = "";
_valorind12 = "";
_valorind13 = "";
_valorind14 = "";
_valorind15 = "";
_valorind16 = "";
_valorind17 = "";
_valorind18 = "";
_valorind19 = "";
_valorind20 = "";
RDebugUtils.currentLine=17891371;
 //BA.debugLineNum = 17891371;BA.debugLine="Dim foto1,foto2,foto3,foto4 As String";
_foto1 = "";
_foto2 = "";
_foto3 = "";
_foto4 = "";
RDebugUtils.currentLine=17891372;
 //BA.debugLineNum = 17891372;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceID";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
RDebugUtils.currentLine=17891374;
 //BA.debugLineNum = 17891374;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=17891375;
 //BA.debugLineNum = 17891375;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
RDebugUtils.currentLine=17891376;
 //BA.debugLineNum = 17891376;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datosmap = parent.mostCurrent._dbutils._executemap(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"SELECT * FROM markers_local WHERE Id=?",new String[]{parent.mostCurrent._main._currentproject});
RDebugUtils.currentLine=17891378;
 //BA.debugLineNum = 17891378;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
if (true) break;

case 12:
//if
this.state = 29;
if (_datosmap== null || _datosmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 14;
}else {
this.state = 16;
}if (true) break;

case 14:
//C
this.state = 29;
RDebugUtils.currentLine=17891379;
 //BA.debugLineNum = 17891379;BA.debugLine="ToastMessageShow(\"Error cargando el análisiss\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisiss"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17891380;
 //BA.debugLineNum = 17891380;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 16:
//C
this.state = 17;
RDebugUtils.currentLine=17891382;
 //BA.debugLineNum = 17891382;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
RDebugUtils.currentLine=17891383;
 //BA.debugLineNum = 17891383;BA.debugLine="dateandtime = datosMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferenceddate")));
RDebugUtils.currentLine=17891384;
 //BA.debugLineNum = 17891384;BA.debugLine="nombresitio = datosMap.Get(\"nombresitio\")";
_nombresitio = BA.ObjectToString(_datosmap.Get((Object)("nombresitio")));
RDebugUtils.currentLine=17891385;
 //BA.debugLineNum = 17891385;BA.debugLine="tiporio = datosMap.Get(\"tipoeval\")";
_tiporio = BA.ObjectToString(_datosmap.Get((Object)("tipoeval")));
RDebugUtils.currentLine=17891386;
 //BA.debugLineNum = 17891386;BA.debugLine="lat = datosMap.Get(\"decimallatitude\")";
_lat = BA.ObjectToString(_datosmap.Get((Object)("decimallatitude")));
RDebugUtils.currentLine=17891387;
 //BA.debugLineNum = 17891387;BA.debugLine="lng = datosMap.Get(\"decimallongitude\")";
_lng = BA.ObjectToString(_datosmap.Get((Object)("decimallongitude")));
RDebugUtils.currentLine=17891388;
 //BA.debugLineNum = 17891388;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
RDebugUtils.currentLine=17891389;
 //BA.debugLineNum = 17891389;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
RDebugUtils.currentLine=17891390;
 //BA.debugLineNum = 17891390;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
RDebugUtils.currentLine=17891391;
 //BA.debugLineNum = 17891391;BA.debugLine="valorind1 = datosMap.Get(\"par1\")";
_valorind1 = BA.ObjectToString(_datosmap.Get((Object)("par1")));
RDebugUtils.currentLine=17891392;
 //BA.debugLineNum = 17891392;BA.debugLine="valorind2 = datosMap.Get(\"par2\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("par2")));
RDebugUtils.currentLine=17891393;
 //BA.debugLineNum = 17891393;BA.debugLine="valorind3 = datosMap.Get(\"par3\")";
_valorind3 = BA.ObjectToString(_datosmap.Get((Object)("par3")));
RDebugUtils.currentLine=17891394;
 //BA.debugLineNum = 17891394;BA.debugLine="valorind4 = datosMap.Get(\"par4\")";
_valorind4 = BA.ObjectToString(_datosmap.Get((Object)("par4")));
RDebugUtils.currentLine=17891395;
 //BA.debugLineNum = 17891395;BA.debugLine="valorind5 = datosMap.Get(\"par5\")";
_valorind5 = BA.ObjectToString(_datosmap.Get((Object)("par5")));
RDebugUtils.currentLine=17891396;
 //BA.debugLineNum = 17891396;BA.debugLine="valorind6 = datosMap.Get(\"par6\")";
_valorind6 = BA.ObjectToString(_datosmap.Get((Object)("par6")));
RDebugUtils.currentLine=17891397;
 //BA.debugLineNum = 17891397;BA.debugLine="valorind7 = datosMap.Get(\"par7\")";
_valorind7 = BA.ObjectToString(_datosmap.Get((Object)("par7")));
RDebugUtils.currentLine=17891398;
 //BA.debugLineNum = 17891398;BA.debugLine="valorind8 = datosMap.Get(\"par8\")";
_valorind8 = BA.ObjectToString(_datosmap.Get((Object)("par8")));
RDebugUtils.currentLine=17891399;
 //BA.debugLineNum = 17891399;BA.debugLine="valorind9 = datosMap.Get(\"par9\")";
_valorind9 = BA.ObjectToString(_datosmap.Get((Object)("par9")));
RDebugUtils.currentLine=17891400;
 //BA.debugLineNum = 17891400;BA.debugLine="valorind10 = datosMap.Get(\"par10\")";
_valorind10 = BA.ObjectToString(_datosmap.Get((Object)("par10")));
RDebugUtils.currentLine=17891401;
 //BA.debugLineNum = 17891401;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
_foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
RDebugUtils.currentLine=17891402;
 //BA.debugLineNum = 17891402;BA.debugLine="foto2 = datosMap.Get(\"foto2\")";
_foto2 = BA.ObjectToString(_datosmap.Get((Object)("foto2")));
RDebugUtils.currentLine=17891403;
 //BA.debugLineNum = 17891403;BA.debugLine="foto3 = datosMap.Get(\"foto3\")";
_foto3 = BA.ObjectToString(_datosmap.Get((Object)("foto3")));
RDebugUtils.currentLine=17891404;
 //BA.debugLineNum = 17891404;BA.debugLine="foto4 = datosMap.Get(\"foto4\")";
_foto4 = BA.ObjectToString(_datosmap.Get((Object)("foto4")));
RDebugUtils.currentLine=17891405;
 //BA.debugLineNum = 17891405;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
RDebugUtils.currentLine=17891406;
 //BA.debugLineNum = 17891406;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
RDebugUtils.currentLine=17891407;
 //BA.debugLineNum = 17891407;BA.debugLine="If privado = Null Or privado = \"null\" Then";
if (true) break;

case 17:
//if
this.state = 20;
if (_privado== null || (_privado).equals("null")) { 
this.state = 19;
}if (true) break;

case 19:
//C
this.state = 20;
RDebugUtils.currentLine=17891408;
 //BA.debugLineNum = 17891408;BA.debugLine="privado = \"no\"";
_privado = "no";
 if (true) break;

case 20:
//C
this.state = 21;
;
RDebugUtils.currentLine=17891410;
 //BA.debugLineNum = 17891410;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
RDebugUtils.currentLine=17891411;
 //BA.debugLineNum = 17891411;BA.debugLine="If estadovalidacion = \"null\" Then";
if (true) break;

case 21:
//if
this.state = 24;
if ((_estadovalidacion).equals("null")) { 
this.state = 23;
}if (true) break;

case 23:
//C
this.state = 24;
RDebugUtils.currentLine=17891412;
 //BA.debugLineNum = 17891412;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 if (true) break;

case 24:
//C
this.state = 25;
;
RDebugUtils.currentLine=17891414;
 //BA.debugLineNum = 17891414;BA.debugLine="deviceID = datosMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_datosmap.Get((Object)("deviceID")));
RDebugUtils.currentLine=17891415;
 //BA.debugLineNum = 17891415;BA.debugLine="If deviceID = Null Or deviceID = \"\" Or deviceID";
if (true) break;

case 25:
//if
this.state = 28;
if (_deviceid== null || (_deviceid).equals("") || (_deviceid).equals("null")) { 
this.state = 27;
}if (true) break;

case 27:
//C
this.state = 28;
RDebugUtils.currentLine=17891416;
 //BA.debugLineNum = 17891416;BA.debugLine="deviceID = utilidades.GetDeviceId";
_deviceid = parent.mostCurrent._utilidades._getdeviceid(mostCurrent.activityBA);
 if (true) break;

case 28:
//C
this.state = 29;
;
 if (true) break;

case 29:
//C
this.state = 30;
;
RDebugUtils.currentLine=17891423;
 //BA.debugLineNum = 17891423;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.Log("Comienza envio de datos");
RDebugUtils.currentLine=17891424;
 //BA.debugLineNum = 17891424;BA.debugLine="Dim enviomarcadores As HttpJob";
_enviomarcadores = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=17891425;
 //BA.debugLineNum = 17891425;BA.debugLine="enviomarcadores.Initialize(\"EnvioMarcador\", Me)";
_enviomarcadores._initialize(processBA,"EnvioMarcador",frmcamara.getObject());
RDebugUtils.currentLine=17891426;
 //BA.debugLineNum = 17891426;BA.debugLine="enviomarcadores.Download2(Main.serverPath & \"/con";
_enviomarcadores._download2(parent.mostCurrent._main._serverpath+"/connect/addpuntomapa.php",new String[]{"username",_username,"deviceID",parent.mostCurrent._main._deviceid,"dateandtime",_dateandtime,"lat",_lat,"lng",_lng,"valorVinchuca",_valorind1,"foto1path",_foto1,"foto2path",_foto2,"foto3path",_foto3,"foto4path",_foto4,"privado",_privado,"gpsdetect",_gpsdetect,"wifidetect",_wifidetect,"mapadetect",_mapadetect,"terminado",_terminado,"verificado","No Verificado"});
RDebugUtils.currentLine=17891431;
 //BA.debugLineNum = 17891431;BA.debugLine="Wait For (enviomarcadores) JobDone(enviomarcadore";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "frmcamara", "btncontinuar_click"), (Object)(_enviomarcadores));
this.state = 87;
return;
case 87:
//C
this.state = 30;
_enviomarcadores = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=17891433;
 //BA.debugLineNum = 17891433;BA.debugLine="If enviomarcadores.Success Then";
if (true) break;

case 30:
//if
this.state = 41;
if (_enviomarcadores._success) { 
this.state = 32;
}else {
this.state = 40;
}if (true) break;

case 32:
//C
this.state = 33;
RDebugUtils.currentLine=17891434;
 //BA.debugLineNum = 17891434;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
RDebugUtils.currentLine=17891435;
 //BA.debugLineNum = 17891435;BA.debugLine="res = enviomarcadores.GetString";
_res = _enviomarcadores._getstring();
RDebugUtils.currentLine=17891436;
 //BA.debugLineNum = 17891436;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=17891437;
 //BA.debugLineNum = 17891437;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
RDebugUtils.currentLine=17891438;
 //BA.debugLineNum = 17891438;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=17891439;
 //BA.debugLineNum = 17891439;BA.debugLine="If action = \"Not Found\" Then";
if (true) break;

case 33:
//if
this.state = 38;
if ((_action).equals("Not Found")) { 
this.state = 35;
}else 
{RDebugUtils.currentLine=17891442;
 //BA.debugLineNum = 17891442;BA.debugLine="Else If action = \"Marcadores\" Then";
if ((_action).equals("Marcadores")) { 
this.state = 37;
}}
if (true) break;

case 35:
//C
this.state = 38;
RDebugUtils.currentLine=17891440;
 //BA.debugLineNum = 17891440;BA.debugLine="ToastMessageShow(\"Error en la carga de marcador";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de marcadores"),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17891441;
 //BA.debugLineNum = 17891441;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 37:
//C
this.state = 38;
RDebugUtils.currentLine=17891443;
 //BA.debugLineNum = 17891443;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=17891444;
 //BA.debugLineNum = 17891444;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
RDebugUtils.currentLine=17891445;
 //BA.debugLineNum = 17891445;BA.debugLine="Dim serverID As String";
_serverid = "";
RDebugUtils.currentLine=17891446;
 //BA.debugLineNum = 17891446;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
RDebugUtils.currentLine=17891449;
 //BA.debugLineNum = 17891449;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=17891450;
 //BA.debugLineNum = 17891450;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=17891451;
 //BA.debugLineNum = 17891451;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._main._currentproject));
RDebugUtils.currentLine=17891452;
 //BA.debugLineNum = 17891452;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"markers_local","evalsent",(Object)("si"),_map1);
RDebugUtils.currentLine=17891453;
 //BA.debugLineNum = 17891453;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"markers_local","serverId",(Object)(_serverid),_map1);
RDebugUtils.currentLine=17891454;
 //BA.debugLineNum = 17891454;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando foto";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17891457;
 //BA.debugLineNum = 17891457;BA.debugLine="frmIdentificacionNew.currentPregunta = 1";
parent.mostCurrent._frmidentificacionnew._currentpregunta = (int) (1);
RDebugUtils.currentLine=17891458;
 //BA.debugLineNum = 17891458;BA.debugLine="frmIdentificacionNew.foto1 = foto1";
parent.mostCurrent._frmidentificacionnew._foto1 = _foto1;
RDebugUtils.currentLine=17891459;
 //BA.debugLineNum = 17891459;BA.debugLine="frmIdentificacionNew.foto2 = foto2";
parent.mostCurrent._frmidentificacionnew._foto2 = _foto2;
RDebugUtils.currentLine=17891460;
 //BA.debugLineNum = 17891460;BA.debugLine="frmIdentificacionNew.foto3 = foto3";
parent.mostCurrent._frmidentificacionnew._foto3 = _foto3;
RDebugUtils.currentLine=17891461;
 //BA.debugLineNum = 17891461;BA.debugLine="frmIdentificacionNew.foto4 = foto4";
parent.mostCurrent._frmidentificacionnew._foto4 = _foto4;
 if (true) break;

case 38:
//C
this.state = 41;
;
 if (true) break;

case 40:
//C
this.state = 41;
RDebugUtils.currentLine=17891466;
 //BA.debugLineNum = 17891466;BA.debugLine="ToastMessageShow(\"Error: \" & enviomarcadores.Err";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: "+_enviomarcadores._errormessage),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 41:
//C
this.state = 42;
;
RDebugUtils.currentLine=17891468;
 //BA.debugLineNum = 17891468;BA.debugLine="enviomarcadores.Release";
_enviomarcadores._release();
RDebugUtils.currentLine=17891472;
 //BA.debugLineNum = 17891472;BA.debugLine="Dim NV As Map";
_nv = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=17891473;
 //BA.debugLineNum = 17891473;BA.debugLine="NV.Initialize";
_nv.Initialize();
RDebugUtils.currentLine=17891474;
 //BA.debugLineNum = 17891474;BA.debugLine="NV.Put(\"usuario\", Main.username)";
_nv.Put((Object)("usuario"),(Object)(parent.mostCurrent._main._username));
RDebugUtils.currentLine=17891475;
 //BA.debugLineNum = 17891475;BA.debugLine="NV.Put(\"eval\", Main.currentproject)";
_nv.Put((Object)("eval"),(Object)(parent.mostCurrent._main._currentproject));
RDebugUtils.currentLine=17891476;
 //BA.debugLineNum = 17891476;BA.debugLine="NV.Put(\"action\", \"upload\")";
_nv.Put((Object)("action"),(Object)("upload"));
RDebugUtils.currentLine=17891477;
 //BA.debugLineNum = 17891477;BA.debugLine="NV.Put(\"usr\", \"juacochero\")";
_nv.Put((Object)("usr"),(Object)("juacochero"));
RDebugUtils.currentLine=17891478;
 //BA.debugLineNum = 17891478;BA.debugLine="NV.Put(\"pss\", \"vacagorda\")";
_nv.Put((Object)("pss"),(Object)("vacagorda"));
RDebugUtils.currentLine=17891481;
 //BA.debugLineNum = 17891481;BA.debugLine="Dim req As OkHttpRequest";
_req = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
RDebugUtils.currentLine=17891483;
 //BA.debugLineNum = 17891483;BA.debugLine="Dim files As List";
_files = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=17891484;
 //BA.debugLineNum = 17891484;BA.debugLine="files.Initialize";
_files.Initialize();
RDebugUtils.currentLine=17891485;
 //BA.debugLineNum = 17891485;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=17891486;
 //BA.debugLineNum = 17891486;BA.debugLine="If foto1 <> \"\" And foto1 <> Null Then";
if (true) break;

case 42:
//if
this.state = 49;
if ((_foto1).equals("") == false && _foto1!= null) { 
this.state = 44;
}if (true) break;

case 44:
//C
this.state = 45;
RDebugUtils.currentLine=17891487;
 //BA.debugLineNum = 17891487;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (true) break;

case 45:
//if
this.state = 48;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg")) { 
this.state = 47;
}if (true) break;

case 47:
//C
this.state = 48;
RDebugUtils.currentLine=17891488;
 //BA.debugLineNum = 17891488;BA.debugLine="Log(\"Agrego foto 1 a la lista\")";
anywheresoftware.b4a.keywords.Common.Log("Agrego foto 1 a la lista");
RDebugUtils.currentLine=17891489;
 //BA.debugLineNum = 17891489;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=17891490;
 //BA.debugLineNum = 17891490;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=17891491;
 //BA.debugLineNum = 17891491;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=17891492;
 //BA.debugLineNum = 17891492;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=17891493;
 //BA.debugLineNum = 17891493;BA.debugLine="fd.Dir = File.DirRootExternal & \"/GeoVin/\"";
_fd.Dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/";
RDebugUtils.currentLine=17891494;
 //BA.debugLineNum = 17891494;BA.debugLine="fd.FileName = foto1 & \".jpg\"";
_fd.FileName = _foto1+".jpg";
RDebugUtils.currentLine=17891495;
 //BA.debugLineNum = 17891495;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
 if (true) break;

case 48:
//C
this.state = 49;
;
 if (true) break;
;
RDebugUtils.currentLine=17891498;
 //BA.debugLineNum = 17891498;BA.debugLine="If files.Size >= 1 Then";

case 49:
//if
this.state = 52;
if (_files.getSize()>=1) { 
this.state = 51;
}if (true) break;

case 51:
//C
this.state = 52;
RDebugUtils.currentLine=17891499;
 //BA.debugLineNum = 17891499;BA.debugLine="ProgressDialogShow(\"Enviando foto #1... aguarde";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando foto #1... aguarde por favor"));
RDebugUtils.currentLine=17891501;
 //BA.debugLineNum = 17891501;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = parent.mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,parent.mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=17891502;
 //BA.debugLineNum = 17891502;BA.debugLine="Log(\"Hc execute foto 1\")";
anywheresoftware.b4a.keywords.Common.Log("Hc execute foto 1");
RDebugUtils.currentLine=17891503;
 //BA.debugLineNum = 17891503;BA.debugLine="hc.Execute(req, 1)";
parent._hc.Execute(processBA,_req,(int) (1));
 if (true) break;

case 52:
//C
this.state = 53;
;
RDebugUtils.currentLine=17891506;
 //BA.debugLineNum = 17891506;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=17891507;
 //BA.debugLineNum = 17891507;BA.debugLine="If foto2 <> \"\" And foto2 <> Null Then";
if (true) break;

case 53:
//if
this.state = 60;
if ((_foto2).equals("") == false && _foto2!= null) { 
this.state = 55;
}if (true) break;

case 55:
//C
this.state = 56;
RDebugUtils.currentLine=17891508;
 //BA.debugLineNum = 17891508;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (true) break;

case 56:
//if
this.state = 59;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2+".jpg")) { 
this.state = 58;
}if (true) break;

case 58:
//C
this.state = 59;
RDebugUtils.currentLine=17891509;
 //BA.debugLineNum = 17891509;BA.debugLine="Log(\"Agrego foto 2 a la lista\")";
anywheresoftware.b4a.keywords.Common.Log("Agrego foto 2 a la lista");
RDebugUtils.currentLine=17891510;
 //BA.debugLineNum = 17891510;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=17891511;
 //BA.debugLineNum = 17891511;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=17891512;
 //BA.debugLineNum = 17891512;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=17891513;
 //BA.debugLineNum = 17891513;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=17891514;
 //BA.debugLineNum = 17891514;BA.debugLine="fd.Dir = File.DirRootExternal & \"/GeoVin/\"";
_fd.Dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/";
RDebugUtils.currentLine=17891515;
 //BA.debugLineNum = 17891515;BA.debugLine="fd.FileName = foto2 & \".jpg\"";
_fd.FileName = _foto2+".jpg";
RDebugUtils.currentLine=17891516;
 //BA.debugLineNum = 17891516;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
 if (true) break;

case 59:
//C
this.state = 60;
;
 if (true) break;
;
RDebugUtils.currentLine=17891520;
 //BA.debugLineNum = 17891520;BA.debugLine="If files.Size >= 1 Then";

case 60:
//if
this.state = 63;
if (_files.getSize()>=1) { 
this.state = 62;
}if (true) break;

case 62:
//C
this.state = 63;
RDebugUtils.currentLine=17891521;
 //BA.debugLineNum = 17891521;BA.debugLine="ProgressDialogShow(\"Enviando foto #2... aguarde";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando foto #2... aguarde por favor"));
RDebugUtils.currentLine=17891522;
 //BA.debugLineNum = 17891522;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = parent.mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,parent.mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=17891523;
 //BA.debugLineNum = 17891523;BA.debugLine="Log(\"Hc execute foto 2\")";
anywheresoftware.b4a.keywords.Common.Log("Hc execute foto 2");
RDebugUtils.currentLine=17891524;
 //BA.debugLineNum = 17891524;BA.debugLine="hc.Execute(req, 2)";
parent._hc.Execute(processBA,_req,(int) (2));
 if (true) break;

case 63:
//C
this.state = 64;
;
RDebugUtils.currentLine=17891528;
 //BA.debugLineNum = 17891528;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=17891529;
 //BA.debugLineNum = 17891529;BA.debugLine="If foto3 <> \"\" And foto3 <> Null Then";
if (true) break;

case 64:
//if
this.state = 71;
if ((_foto3).equals("") == false && _foto3!= null) { 
this.state = 66;
}if (true) break;

case 66:
//C
this.state = 67;
RDebugUtils.currentLine=17891530;
 //BA.debugLineNum = 17891530;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (true) break;

case 67:
//if
this.state = 70;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3+".jpg")) { 
this.state = 69;
}if (true) break;

case 69:
//C
this.state = 70;
RDebugUtils.currentLine=17891531;
 //BA.debugLineNum = 17891531;BA.debugLine="Log(\"Agrego foto 3 a la lista\")";
anywheresoftware.b4a.keywords.Common.Log("Agrego foto 3 a la lista");
RDebugUtils.currentLine=17891532;
 //BA.debugLineNum = 17891532;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=17891533;
 //BA.debugLineNum = 17891533;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=17891534;
 //BA.debugLineNum = 17891534;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=17891535;
 //BA.debugLineNum = 17891535;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=17891536;
 //BA.debugLineNum = 17891536;BA.debugLine="fd.Dir = File.DirRootExternal & \"/GeoVin/\"";
_fd.Dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/";
RDebugUtils.currentLine=17891537;
 //BA.debugLineNum = 17891537;BA.debugLine="fd.FileName = foto3 & \".jpg\"";
_fd.FileName = _foto3+".jpg";
RDebugUtils.currentLine=17891538;
 //BA.debugLineNum = 17891538;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
 if (true) break;

case 70:
//C
this.state = 71;
;
 if (true) break;
;
RDebugUtils.currentLine=17891542;
 //BA.debugLineNum = 17891542;BA.debugLine="If files.Size >= 1 Then";

case 71:
//if
this.state = 74;
if (_files.getSize()>=1) { 
this.state = 73;
}if (true) break;

case 73:
//C
this.state = 74;
RDebugUtils.currentLine=17891543;
 //BA.debugLineNum = 17891543;BA.debugLine="ProgressDialogShow(\"Enviando foto #3... aguarde";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando foto #3... aguarde por favor"));
RDebugUtils.currentLine=17891544;
 //BA.debugLineNum = 17891544;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = parent.mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,parent.mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=17891545;
 //BA.debugLineNum = 17891545;BA.debugLine="Log(\"Hc execute  foto 3\")";
anywheresoftware.b4a.keywords.Common.Log("Hc execute  foto 3");
RDebugUtils.currentLine=17891546;
 //BA.debugLineNum = 17891546;BA.debugLine="hc.Execute(req, 3)";
parent._hc.Execute(processBA,_req,(int) (3));
 if (true) break;

case 74:
//C
this.state = 75;
;
RDebugUtils.currentLine=17891549;
 //BA.debugLineNum = 17891549;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=17891550;
 //BA.debugLineNum = 17891550;BA.debugLine="If foto4 <> \"\" And foto4 <> Null Then";
if (true) break;

case 75:
//if
this.state = 82;
if ((_foto4).equals("") == false && _foto4!= null) { 
this.state = 77;
}if (true) break;

case 77:
//C
this.state = 78;
RDebugUtils.currentLine=17891551;
 //BA.debugLineNum = 17891551;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (true) break;

case 78:
//if
this.state = 81;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4+".jpg")) { 
this.state = 80;
}if (true) break;

case 80:
//C
this.state = 81;
RDebugUtils.currentLine=17891552;
 //BA.debugLineNum = 17891552;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=17891553;
 //BA.debugLineNum = 17891553;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=17891554;
 //BA.debugLineNum = 17891554;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=17891555;
 //BA.debugLineNum = 17891555;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=17891556;
 //BA.debugLineNum = 17891556;BA.debugLine="fd.Dir = File.DirRootExternal & \"/GeoVin/\"";
_fd.Dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/";
RDebugUtils.currentLine=17891557;
 //BA.debugLineNum = 17891557;BA.debugLine="Log(\"Agrego foto 4 a la lista\")";
anywheresoftware.b4a.keywords.Common.Log("Agrego foto 4 a la lista");
RDebugUtils.currentLine=17891558;
 //BA.debugLineNum = 17891558;BA.debugLine="fd.FileName = foto4 & \".jpg\"";
_fd.FileName = _foto4+".jpg";
RDebugUtils.currentLine=17891559;
 //BA.debugLineNum = 17891559;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
 if (true) break;

case 81:
//C
this.state = 82;
;
 if (true) break;
;
RDebugUtils.currentLine=17891562;
 //BA.debugLineNum = 17891562;BA.debugLine="If files.Size >= 1 Then";

case 82:
//if
this.state = 85;
if (_files.getSize()>=1) { 
this.state = 84;
}if (true) break;

case 84:
//C
this.state = 85;
RDebugUtils.currentLine=17891563;
 //BA.debugLineNum = 17891563;BA.debugLine="ProgressDialogShow(\"Enviando foto #4... aguarde";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando foto #4... aguarde por favor"));
RDebugUtils.currentLine=17891564;
 //BA.debugLineNum = 17891564;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = parent.mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,parent.mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=17891565;
 //BA.debugLineNum = 17891565;BA.debugLine="Log(\"Hc execute foto 4\")";
anywheresoftware.b4a.keywords.Common.Log("Hc execute foto 4");
RDebugUtils.currentLine=17891566;
 //BA.debugLineNum = 17891566;BA.debugLine="hc.Execute(req, 4)";
parent._hc.Execute(processBA,_req,(int) (4));
 if (true) break;

case 85:
//C
this.state = -1;
;
RDebugUtils.currentLine=17891569;
 //BA.debugLineNum = 17891569;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _checkinternet() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "checkinternet"))
	 {return ((anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) Debug.delegate(mostCurrent.activityBA, "checkinternet", null));}
ResumableSub_CheckInternet rsub = new ResumableSub_CheckInternet(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_CheckInternet extends BA.ResumableSub {
public ResumableSub_CheckInternet(cepave.geovin.frmcamara parent) {
this.parent = parent;
}
cepave.geovin.frmcamara parent;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="frmcamara";

    while (true) {
        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
RDebugUtils.currentLine=18153473;
 //BA.debugLineNum = 18153473;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=18153474;
 //BA.debugLineNum = 18153474;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize(processBA,"",frmcamara.getObject());
RDebugUtils.currentLine=18153476;
 //BA.debugLineNum = 18153476;BA.debugLine="j.Download(\"http://www.app-ear.com.ar/connect20/c";
_j._download("http://www.app-ear.com.ar/connect20/connecttest.php");
RDebugUtils.currentLine=18153477;
 //BA.debugLineNum = 18153477;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "frmcamara", "checkinternet"), (Object)(_j));
this.state = 7;
return;
case 7:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=18153478;
 //BA.debugLineNum = 18153478;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_j._success) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
RDebugUtils.currentLine=18153480;
 //BA.debugLineNum = 18153480;BA.debugLine="Main.modooffline = False";
parent.mostCurrent._main._modooffline = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=18153481;
 //BA.debugLineNum = 18153481;BA.debugLine="j.Release";
_j._release();
RDebugUtils.currentLine=18153482;
 //BA.debugLineNum = 18153482;BA.debugLine="Return 1";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(1));return;};
 if (true) break;

case 5:
//C
this.state = 6;
RDebugUtils.currentLine=18153485;
 //BA.debugLineNum = 18153485;BA.debugLine="Main.modooffline = True";
parent.mostCurrent._main._modooffline = anywheresoftware.b4a.keywords.Common.True;
RDebugUtils.currentLine=18153486;
 //BA.debugLineNum = 18153486;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=18153487;
 //BA.debugLineNum = 18153487;BA.debugLine="j.Release";
_j._release();
RDebugUtils.currentLine=18153488;
 //BA.debugLineNum = 18153488;BA.debugLine="Return 0";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(0));return;};
 if (true) break;

case 6:
//C
this.state = -1;
;
RDebugUtils.currentLine=18153490;
 //BA.debugLineNum = 18153490;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btntakepicture_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btntakepicture_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btntakepicture_click", null));}
RDebugUtils.currentLine=17432576;
 //BA.debugLineNum = 17432576;BA.debugLine="Sub btnTakePicture_Click";
RDebugUtils.currentLine=17432578;
 //BA.debugLineNum = 17432578;BA.debugLine="If camEx.IsInitialized Then";
if (mostCurrent._camex.IsInitialized()) { 
RDebugUtils.currentLine=17432579;
 //BA.debugLineNum = 17432579;BA.debugLine="If imgtransparent.IsInitialized Then";
if (mostCurrent._imgtransparent.IsInitialized()) { 
RDebugUtils.currentLine=17432580;
 //BA.debugLineNum = 17432580;BA.debugLine="imgtransparent.RemoveView";
mostCurrent._imgtransparent.RemoveView();
 };
RDebugUtils.currentLine=17432582;
 //BA.debugLineNum = 17432582;BA.debugLine="btnTakePicture.Enabled = False";
mostCurrent._btntakepicture.setEnabled(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17432583;
 //BA.debugLineNum = 17432583;BA.debugLine="ProgressDialogShow(\"Capturando foto\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Capturando foto"));
RDebugUtils.currentLine=17432584;
 //BA.debugLineNum = 17432584;BA.debugLine="camEx.TakePicture";
mostCurrent._camex._takepicture(null);
 };
RDebugUtils.currentLine=17432587;
 //BA.debugLineNum = 17432587;BA.debugLine="End Sub";
return "";
}
public static String  _btnvolver_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnvolver_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnvolver_click", null));}
RDebugUtils.currentLine=18219008;
 //BA.debugLineNum = 18219008;BA.debugLine="Sub btnVolver_Click";
RDebugUtils.currentLine=18219009;
 //BA.debugLineNum = 18219009;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=18219010;
 //BA.debugLineNum = 18219010;BA.debugLine="Activity.LoadLayout(\"camera\")";
mostCurrent._activity.LoadLayout("camera",mostCurrent.activityBA);
RDebugUtils.currentLine=18219011;
 //BA.debugLineNum = 18219011;BA.debugLine="PreviewFotos";
_previewfotos();
RDebugUtils.currentLine=18219012;
 //BA.debugLineNum = 18219012;BA.debugLine="End Sub";
return "";
}
public static String  _previewfotos() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "previewfotos"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "previewfotos", null));}
RDebugUtils.currentLine=17825792;
 //BA.debugLineNum = 17825792;BA.debugLine="Sub PreviewFotos";
RDebugUtils.currentLine=17825794;
 //BA.debugLineNum = 17825794;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
RDebugUtils.currentLine=17825795;
 //BA.debugLineNum = 17825795;BA.debugLine="imgSitio1.Visible = True";
mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17825796;
 //BA.debugLineNum = 17825796;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath0+".jpg")) { 
RDebugUtils.currentLine=17825797;
 //BA.debugLineNum = 17825797;BA.debugLine="imgSitio1.Bitmap = Null";
mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17825798;
 //BA.debugLineNum = 17825798;BA.debugLine="imgSitio1.Bitmap = LoadBitmapSample(File.DirRoo";
mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath0+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=17825800;
 //BA.debugLineNum = 17825800;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
RDebugUtils.currentLine=17825802;
 //BA.debugLineNum = 17825802;BA.debugLine="imgSitio1.Visible = False";
mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17825803;
 //BA.debugLineNum = 17825803;BA.debugLine="btnContinuar.Visible = False";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
RDebugUtils.currentLine=17825806;
 //BA.debugLineNum = 17825806;BA.debugLine="imgSitio1.Visible = False";
mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=17825808;
 //BA.debugLineNum = 17825808;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
RDebugUtils.currentLine=17825809;
 //BA.debugLineNum = 17825809;BA.debugLine="imgSitio2.Visible = True";
mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17825810;
 //BA.debugLineNum = 17825810;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath1+".jpg")) { 
RDebugUtils.currentLine=17825811;
 //BA.debugLineNum = 17825811;BA.debugLine="imgSitio2.Bitmap = Null";
mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17825812;
 //BA.debugLineNum = 17825812;BA.debugLine="imgSitio2.Bitmap = LoadBitmapSample(File.DirRoo";
mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath1+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
RDebugUtils.currentLine=17825815;
 //BA.debugLineNum = 17825815;BA.debugLine="imgSitio2.Visible = False";
mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
RDebugUtils.currentLine=17825818;
 //BA.debugLineNum = 17825818;BA.debugLine="imgSitio2.Visible = False";
mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=17825820;
 //BA.debugLineNum = 17825820;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
RDebugUtils.currentLine=17825821;
 //BA.debugLineNum = 17825821;BA.debugLine="imgSitio3.Visible = True";
mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17825822;
 //BA.debugLineNum = 17825822;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath2+".jpg")) { 
RDebugUtils.currentLine=17825823;
 //BA.debugLineNum = 17825823;BA.debugLine="imgSitio3.Bitmap = Null";
mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17825824;
 //BA.debugLineNum = 17825824;BA.debugLine="imgSitio3.Bitmap = LoadBitmapSample(File.DirRoo";
mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath2+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
RDebugUtils.currentLine=17825827;
 //BA.debugLineNum = 17825827;BA.debugLine="imgSitio3.Visible = False";
mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
RDebugUtils.currentLine=17825830;
 //BA.debugLineNum = 17825830;BA.debugLine="imgSitio3.Visible = False";
mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=17825832;
 //BA.debugLineNum = 17825832;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
RDebugUtils.currentLine=17825833;
 //BA.debugLineNum = 17825833;BA.debugLine="imgSitio4.Visible = True";
mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17825834;
 //BA.debugLineNum = 17825834;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath3+".jpg")) { 
RDebugUtils.currentLine=17825835;
 //BA.debugLineNum = 17825835;BA.debugLine="imgSitio4.Bitmap = Null";
mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=17825836;
 //BA.debugLineNum = 17825836;BA.debugLine="imgSitio4.Bitmap = LoadBitmapSample(File.DirRoo";
mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath3+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
RDebugUtils.currentLine=17825839;
 //BA.debugLineNum = 17825839;BA.debugLine="imgSitio4.Visible = False";
mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
RDebugUtils.currentLine=17825842;
 //BA.debugLineNum = 17825842;BA.debugLine="imgSitio4.Visible = False";
mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=17825846;
 //BA.debugLineNum = 17825846;BA.debugLine="DesignaFoto";
_designafoto();
RDebugUtils.currentLine=17825848;
 //BA.debugLineNum = 17825848;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_picturetaken(byte[] _data) throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "camera1_picturetaken"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "camera1_picturetaken", new Object[] {_data}));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=17498112;
 //BA.debugLineNum = 17498112;BA.debugLine="Sub Camera1_PictureTaken (Data() As Byte)";
RDebugUtils.currentLine=17498114;
 //BA.debugLineNum = 17498114;BA.debugLine="camEx.SavePictureToFile(Data, File.DirRootExterna";
mostCurrent._camex._savepicturetofile(null,_data,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg");
RDebugUtils.currentLine=17498117;
 //BA.debugLineNum = 17498117;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=17498118;
 //BA.debugLineNum = 17498118;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=17498119;
 //BA.debugLineNum = 17498119;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=17498120;
 //BA.debugLineNum = 17498120;BA.debugLine="If fotoNombreDestino.EndsWith(\"_1\") Then";
if (mostCurrent._fotonombredestino.endsWith("_1")) { 
RDebugUtils.currentLine=17498121;
 //BA.debugLineNum = 17498121;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto1",(Object)(mostCurrent._fotonombredestino),_map1);
 }else 
{RDebugUtils.currentLine=17498122;
 //BA.debugLineNum = 17498122;BA.debugLine="Else If fotoNombreDestino.EndsWith(\"_2\") Then";
if (mostCurrent._fotonombredestino.endsWith("_2")) { 
RDebugUtils.currentLine=17498123;
 //BA.debugLineNum = 17498123;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto2",(Object)(mostCurrent._fotonombredestino),_map1);
 }else 
{RDebugUtils.currentLine=17498124;
 //BA.debugLineNum = 17498124;BA.debugLine="Else If fotoNombreDestino.EndsWith(\"_3\") Then";
if (mostCurrent._fotonombredestino.endsWith("_3")) { 
RDebugUtils.currentLine=17498125;
 //BA.debugLineNum = 17498125;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto3",(Object)(mostCurrent._fotonombredestino),_map1);
 }else 
{RDebugUtils.currentLine=17498126;
 //BA.debugLineNum = 17498126;BA.debugLine="Else If fotoNombreDestino.EndsWith(\"_4\") Then";
if (mostCurrent._fotonombredestino.endsWith("_4")) { 
RDebugUtils.currentLine=17498127;
 //BA.debugLineNum = 17498127;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto4",(Object)(mostCurrent._fotonombredestino),_map1);
 }}}}
;
RDebugUtils.currentLine=17498131;
 //BA.debugLineNum = 17498131;BA.debugLine="ToastMessageShow(\"Imagen guardada: \" & File.Combi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Imagen guardada: "+anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg")),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17498132;
 //BA.debugLineNum = 17498132;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=17498133;
 //BA.debugLineNum = 17498133;BA.debugLine="camEx.StartPreview 'restart preview";
mostCurrent._camex._startpreview(null);
RDebugUtils.currentLine=17498134;
 //BA.debugLineNum = 17498134;BA.debugLine="btnTakePicture.Enabled = True";
mostCurrent._btntakepicture.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=17498136;
 //BA.debugLineNum = 17498136;BA.debugLine="DesignaFoto";
_designafoto();
RDebugUtils.currentLine=17498137;
 //BA.debugLineNum = 17498137;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_ready(boolean _success) throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "camera1_ready"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "camera1_ready", new Object[] {_success}));}
RDebugUtils.currentLine=17301504;
 //BA.debugLineNum = 17301504;BA.debugLine="Sub Camera1_Ready (Success As Boolean)";
RDebugUtils.currentLine=17301505;
 //BA.debugLineNum = 17301505;BA.debugLine="If Success Then";
if (_success) { 
RDebugUtils.currentLine=17301506;
 //BA.debugLineNum = 17301506;BA.debugLine="Log(camEx.GetSupportedPicturesSizes)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._camex._getsupportedpicturessizes(null)));
RDebugUtils.currentLine=17301507;
 //BA.debugLineNum = 17301507;BA.debugLine="Log(camEx.GetPictureSize)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._camex._getpicturesize(null)));
RDebugUtils.currentLine=17301508;
 //BA.debugLineNum = 17301508;BA.debugLine="SetMaxSize";
_setmaxsize();
RDebugUtils.currentLine=17301509;
 //BA.debugLineNum = 17301509;BA.debugLine="camEx.SetContinuousAutoFocus";
mostCurrent._camex._setcontinuousautofocus(null);
RDebugUtils.currentLine=17301510;
 //BA.debugLineNum = 17301510;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters(null);
RDebugUtils.currentLine=17301511;
 //BA.debugLineNum = 17301511;BA.debugLine="camEx.StartPreview";
mostCurrent._camex._startpreview(null);
RDebugUtils.currentLine=17301512;
 //BA.debugLineNum = 17301512;BA.debugLine="Log(camEx.GetPreviewSize)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._camex._getpreviewsize(null)));
 }else {
RDebugUtils.currentLine=17301514;
 //BA.debugLineNum = 17301514;BA.debugLine="ToastMessageShow(\"No se puede encender la cámara";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se puede encender la cámara. Revise los permisos de la aplicación"),anywheresoftware.b4a.keywords.Common.True);
 };
RDebugUtils.currentLine=17301516;
 //BA.debugLineNum = 17301516;BA.debugLine="End Sub";
return "";
}
public static String  _setmaxsize() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "setmaxsize"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "setmaxsize", null));}
cepave.geovin.cameraexclass._camerasize _mincs = null;
cepave.geovin.cameraexclass._camerasize _cs = null;
RDebugUtils.currentLine=17367040;
 //BA.debugLineNum = 17367040;BA.debugLine="Private Sub SetMaxSize";
RDebugUtils.currentLine=17367041;
 //BA.debugLineNum = 17367041;BA.debugLine="Dim minCS As CameraSize";
_mincs = new cepave.geovin.cameraexclass._camerasize();
RDebugUtils.currentLine=17367042;
 //BA.debugLineNum = 17367042;BA.debugLine="For Each cs As CameraSize In camEx.GetSupportedPi";
{
final cepave.geovin.cameraexclass._camerasize[] group2 = mostCurrent._camex._getsupportedpicturessizes(null);
final int groupLen2 = group2.length
;int index2 = 0;
;
for (; index2 < groupLen2;index2++){
_cs = group2[index2];
RDebugUtils.currentLine=17367043;
 //BA.debugLineNum = 17367043;BA.debugLine="If minCS.Width = 0 Then";
if (_mincs.Width==0) { 
RDebugUtils.currentLine=17367044;
 //BA.debugLineNum = 17367044;BA.debugLine="minCS = cs";
_mincs = _cs;
 }else {
RDebugUtils.currentLine=17367047;
 //BA.debugLineNum = 17367047;BA.debugLine="If Power(minCS.Width, 2) + Power(minCS.Height,";
if (anywheresoftware.b4a.keywords.Common.Power(_mincs.Width,2)+anywheresoftware.b4a.keywords.Common.Power(_mincs.Height,2)<anywheresoftware.b4a.keywords.Common.Power(_cs.Width,2)+anywheresoftware.b4a.keywords.Common.Power(_cs.Height,2)) { 
RDebugUtils.currentLine=17367048;
 //BA.debugLineNum = 17367048;BA.debugLine="minCS = cs";
_mincs = _cs;
 };
 };
 }
};
RDebugUtils.currentLine=17367052;
 //BA.debugLineNum = 17367052;BA.debugLine="camEx.SetPictureSize(minCS.Width, minCS.Height)";
mostCurrent._camex._setpicturesize(null,_mincs.Width,_mincs.Height);
RDebugUtils.currentLine=17367053;
 //BA.debugLineNum = 17367053;BA.debugLine="Log(\"Selected size: \" & minCS)";
anywheresoftware.b4a.keywords.Common.Log("Selected size: "+BA.ObjectToString(_mincs));
RDebugUtils.currentLine=17367054;
 //BA.debugLineNum = 17367054;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cc_result"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cc_result", new Object[] {_success,_dir,_filename}));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=17760256;
 //BA.debugLineNum = 17760256;BA.debugLine="Sub CC_Result (Success As Boolean, Dir As String,";
RDebugUtils.currentLine=17760257;
 //BA.debugLineNum = 17760257;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=17760260;
 //BA.debugLineNum = 17760260;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=17760261;
 //BA.debugLineNum = 17760261;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=17760262;
 //BA.debugLineNum = 17760262;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=17760263;
 //BA.debugLineNum = 17760263;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
RDebugUtils.currentLine=17760264;
 //BA.debugLineNum = 17760264;BA.debugLine="If fotoNombreDestino.EndsWith(\"_1\") Then";
if (mostCurrent._fotonombredestino.endsWith("_1")) { 
RDebugUtils.currentLine=17760265;
 //BA.debugLineNum = 17760265;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg");
RDebugUtils.currentLine=17760266;
 //BA.debugLineNum = 17760266;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto1",(Object)(mostCurrent._fotonombredestino),_map1);
 }else 
{RDebugUtils.currentLine=17760267;
 //BA.debugLineNum = 17760267;BA.debugLine="Else If fotoNombreDestino.EndsWith(\"_2\") Then";
if (mostCurrent._fotonombredestino.endsWith("_2")) { 
RDebugUtils.currentLine=17760268;
 //BA.debugLineNum = 17760268;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg");
RDebugUtils.currentLine=17760269;
 //BA.debugLineNum = 17760269;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto2",(Object)(mostCurrent._fotonombredestino),_map1);
 }else 
{RDebugUtils.currentLine=17760270;
 //BA.debugLineNum = 17760270;BA.debugLine="Else If fotoNombreDestino.EndsWith(\"_3\") Then";
if (mostCurrent._fotonombredestino.endsWith("_3")) { 
RDebugUtils.currentLine=17760271;
 //BA.debugLineNum = 17760271;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg");
RDebugUtils.currentLine=17760272;
 //BA.debugLineNum = 17760272;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto3",(Object)(mostCurrent._fotonombredestino),_map1);
 }else 
{RDebugUtils.currentLine=17760273;
 //BA.debugLineNum = 17760273;BA.debugLine="Else If fotoNombreDestino.EndsWith(\"_4\") Then";
if (mostCurrent._fotonombredestino.endsWith("_4")) { 
RDebugUtils.currentLine=17760274;
 //BA.debugLineNum = 17760274;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._fotonombredestino+".jpg");
RDebugUtils.currentLine=17760275;
 //BA.debugLineNum = 17760275;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto4",(Object)(mostCurrent._fotonombredestino),_map1);
 }}}}
;
RDebugUtils.currentLine=17760279;
 //BA.debugLineNum = 17760279;BA.debugLine="DesignaFoto";
_designafoto();
 };
RDebugUtils.currentLine=17760281;
 //BA.debugLineNum = 17760281;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,String _reason,int _statuscode,int _taskid) throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "hc_responseerror"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "hc_responseerror", new Object[] {_response,_reason,_statuscode,_taskid}));}
RDebugUtils.currentLine=17956864;
 //BA.debugLineNum = 17956864;BA.debugLine="Sub hc_ResponseError (Response As OkHttpResponse,";
RDebugUtils.currentLine=17956866;
 //BA.debugLineNum = 17956866;BA.debugLine="Log(\"envio incorrecto\")";
anywheresoftware.b4a.keywords.Common.Log("envio incorrecto");
RDebugUtils.currentLine=17956867;
 //BA.debugLineNum = 17956867;BA.debugLine="If Response <> Null Then";
if (_response!= null) { 
RDebugUtils.currentLine=17956868;
 //BA.debugLineNum = 17956868;BA.debugLine="If TaskId = 1 Then";
if (_taskid==1) { 
RDebugUtils.currentLine=17956869;
 //BA.debugLineNum = 17956869;BA.debugLine="ToastMessageShow(\"Error enviando evaluación\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando evaluación"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
RDebugUtils.currentLine=17956872;
 //BA.debugLineNum = 17956872;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,int _taskid) throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "hc_responsesuccess"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "hc_responsesuccess", new Object[] {_response,_taskid}));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=18022400;
 //BA.debugLineNum = 18022400;BA.debugLine="Sub hc_ResponseSuccess (Response As OkHttpResponse";
RDebugUtils.currentLine=18022401;
 //BA.debugLineNum = 18022401;BA.debugLine="Log(\"envio correcto\")";
anywheresoftware.b4a.keywords.Common.Log("envio correcto");
RDebugUtils.currentLine=18022402;
 //BA.debugLineNum = 18022402;BA.debugLine="out.InitializeToBytesArray(0) ' I expect less tha";
_out.InitializeToBytesArray((int) (0));
RDebugUtils.currentLine=18022403;
 //BA.debugLineNum = 18022403;BA.debugLine="Response.GetAsynchronously(\"Response\", out, True,";
_response.GetAsynchronously(processBA,"Response",(java.io.OutputStream)(_out.getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
RDebugUtils.currentLine=18022406;
 //BA.debugLineNum = 18022406;BA.debugLine="If TaskId = 1 Then";
if (_taskid==1) { 
RDebugUtils.currentLine=18022407;
 //BA.debugLineNum = 18022407;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=18022409;
 //BA.debugLineNum = 18022409;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=18022410;
 //BA.debugLineNum = 18022410;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=18022411;
 //BA.debugLineNum = 18022411;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=18022412;
 //BA.debugLineNum = 18022412;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto1sent",(Object)("si"),_map1);
RDebugUtils.currentLine=18022413;
 //BA.debugLineNum = 18022413;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto2sent",(Object)("si"),_map1);
RDebugUtils.currentLine=18022414;
 //BA.debugLineNum = 18022414;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto3sent",(Object)("si"),_map1);
RDebugUtils.currentLine=18022415;
 //BA.debugLineNum = 18022415;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto4sent",(Object)("si"),_map1);
RDebugUtils.currentLine=18022417;
 //BA.debugLineNum = 18022417;BA.debugLine="utilidades.Mensaje(\"Felicitaciones!\", \"MsgIcon.p";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Felicitaciones!","MsgIcon.png","Envío exitoso","Usted ha enviado las fotos correctamente y ya puede intentar determinar si el insecto que capturo es una vinchuca.","OK, continuar","","",anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=18022418;
 //BA.debugLineNum = 18022418;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=18022419;
 //BA.debugLineNum = 18022419;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=18022420;
 //BA.debugLineNum = 18022420;BA.debugLine="StartActivity(frmIdentificacionNew)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmidentificacionnew.getObject()));
 };
RDebugUtils.currentLine=18022424;
 //BA.debugLineNum = 18022424;BA.debugLine="End Sub";
return "";
}
public static String  _imgflash_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgflash_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgflash_click", null));}
float[] _f = null;
anywheresoftware.b4a.objects.collections.List _flashmodes = null;
String _flash = "";
RDebugUtils.currentLine=17563648;
 //BA.debugLineNum = 17563648;BA.debugLine="Sub imgFlash_Click";
RDebugUtils.currentLine=17563649;
 //BA.debugLineNum = 17563649;BA.debugLine="Dim f() As Float = camEx.GetFocusDistances";
_f = mostCurrent._camex._getfocusdistances(null);
RDebugUtils.currentLine=17563650;
 //BA.debugLineNum = 17563650;BA.debugLine="Log(f(0) & \", \" & f(1) & \", \" & f(2))";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_f[(int) (0)])+", "+BA.NumberToString(_f[(int) (1)])+", "+BA.NumberToString(_f[(int) (2)]));
RDebugUtils.currentLine=17563651;
 //BA.debugLineNum = 17563651;BA.debugLine="Dim flashModes As List = camEx.GetSupportedFlashM";
_flashmodes = new anywheresoftware.b4a.objects.collections.List();
_flashmodes = mostCurrent._camex._getsupportedflashmodes(null);
RDebugUtils.currentLine=17563652;
 //BA.debugLineNum = 17563652;BA.debugLine="If flashModes.IsInitialized = False Then";
if (_flashmodes.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=17563653;
 //BA.debugLineNum = 17563653;BA.debugLine="ToastMessageShow(\"Flash not supported.\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Flash not supported."),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17563654;
 //BA.debugLineNum = 17563654;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=17563656;
 //BA.debugLineNum = 17563656;BA.debugLine="Dim flash As String = flashModes.Get((flashModes.";
_flash = BA.ObjectToString(_flashmodes.Get((int) ((_flashmodes.IndexOf((Object)(mostCurrent._camex._getflashmode(null)))+1)%_flashmodes.getSize())));
RDebugUtils.currentLine=17563657;
 //BA.debugLineNum = 17563657;BA.debugLine="camEx.SetFlashMode(flash)";
mostCurrent._camex._setflashmode(null,_flash);
RDebugUtils.currentLine=17563658;
 //BA.debugLineNum = 17563658;BA.debugLine="ToastMessageShow(flash, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_flash),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=17563659;
 //BA.debugLineNum = 17563659;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters(null);
RDebugUtils.currentLine=17563660;
 //BA.debugLineNum = 17563660;BA.debugLine="End Sub";
return "";
}
public static String  _imgmenu_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgmenu_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgmenu_click", null));}
RDebugUtils.currentLine=16973824;
 //BA.debugLineNum = 16973824;BA.debugLine="Sub imgMenu_Click";
RDebugUtils.currentLine=16973825;
 //BA.debugLineNum = 16973825;BA.debugLine="Activity.OpenMenu";
mostCurrent._activity.OpenMenu();
RDebugUtils.currentLine=16973826;
 //BA.debugLineNum = 16973826;BA.debugLine="End Sub";
return "";
}
public static String  _imgsitio1_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgsitio1_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgsitio1_click", null));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=18284544;
 //BA.debugLineNum = 18284544;BA.debugLine="Sub imgSitio1_Click";
RDebugUtils.currentLine=18284545;
 //BA.debugLineNum = 18284545;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=18284546;
 //BA.debugLineNum = 18284546;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=18284547;
 //BA.debugLineNum = 18284547;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=18284550;
 //BA.debugLineNum = 18284550;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto1",(Object)(""),_map1);
RDebugUtils.currentLine=18284551;
 //BA.debugLineNum = 18284551;BA.debugLine="imgSitio1.Bitmap = Null";
mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=18284552;
 //BA.debugLineNum = 18284552;BA.debugLine="DesignaFoto";
_designafoto();
RDebugUtils.currentLine=18284554;
 //BA.debugLineNum = 18284554;BA.debugLine="End Sub";
return "";
}
public static String  _imgsitio2_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgsitio2_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgsitio2_click", null));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=18350080;
 //BA.debugLineNum = 18350080;BA.debugLine="Sub imgSitio2_Click";
RDebugUtils.currentLine=18350081;
 //BA.debugLineNum = 18350081;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=18350082;
 //BA.debugLineNum = 18350082;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=18350083;
 //BA.debugLineNum = 18350083;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=18350086;
 //BA.debugLineNum = 18350086;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto2",(Object)(""),_map1);
RDebugUtils.currentLine=18350087;
 //BA.debugLineNum = 18350087;BA.debugLine="imgSitio2.Bitmap = Null";
mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=18350088;
 //BA.debugLineNum = 18350088;BA.debugLine="DesignaFoto";
_designafoto();
RDebugUtils.currentLine=18350090;
 //BA.debugLineNum = 18350090;BA.debugLine="End Sub";
return "";
}
public static String  _imgsitio3_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgsitio3_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgsitio3_click", null));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=18415616;
 //BA.debugLineNum = 18415616;BA.debugLine="Sub imgSitio3_Click";
RDebugUtils.currentLine=18415617;
 //BA.debugLineNum = 18415617;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=18415618;
 //BA.debugLineNum = 18415618;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=18415619;
 //BA.debugLineNum = 18415619;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=18415622;
 //BA.debugLineNum = 18415622;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto3",(Object)(""),_map1);
RDebugUtils.currentLine=18415623;
 //BA.debugLineNum = 18415623;BA.debugLine="imgSitio3.Bitmap = Null";
mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=18415624;
 //BA.debugLineNum = 18415624;BA.debugLine="DesignaFoto";
_designafoto();
RDebugUtils.currentLine=18415626;
 //BA.debugLineNum = 18415626;BA.debugLine="End Sub";
return "";
}
public static String  _imgsitio4_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgsitio4_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgsitio4_click", null));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=18481152;
 //BA.debugLineNum = 18481152;BA.debugLine="Sub imgSitio4_Click";
RDebugUtils.currentLine=18481153;
 //BA.debugLineNum = 18481153;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=18481154;
 //BA.debugLineNum = 18481154;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=18481155;
 //BA.debugLineNum = 18481155;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=18481158;
 //BA.debugLineNum = 18481158;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto4",(Object)(""),_map1);
RDebugUtils.currentLine=18481159;
 //BA.debugLineNum = 18481159;BA.debugLine="imgSitio4.Bitmap = Null";
mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=18481160;
 //BA.debugLineNum = 18481160;BA.debugLine="DesignaFoto";
_designafoto();
RDebugUtils.currentLine=18481162;
 //BA.debugLineNum = 18481162;BA.debugLine="End Sub";
return "";
}
public static String  _mnuadjuntar_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "mnuadjuntar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "mnuadjuntar_click", null));}
RDebugUtils.currentLine=17104896;
 //BA.debugLineNum = 17104896;BA.debugLine="Sub mnuAdjuntar_Click";
RDebugUtils.currentLine=17104898;
 //BA.debugLineNum = 17104898;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview(null);
RDebugUtils.currentLine=17104899;
 //BA.debugLineNum = 17104899;BA.debugLine="camEx.Release";
mostCurrent._camex._release(null);
RDebugUtils.currentLine=17104900;
 //BA.debugLineNum = 17104900;BA.debugLine="DesignaFoto";
_designafoto();
RDebugUtils.currentLine=17104901;
 //BA.debugLineNum = 17104901;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
RDebugUtils.currentLine=17104902;
 //BA.debugLineNum = 17104902;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
RDebugUtils.currentLine=17104903;
 //BA.debugLineNum = 17104903;BA.debugLine="End Sub";
return "";
}
public static String  _mnuconsejos_click() throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "mnuconsejos_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "mnuconsejos_click", null));}
RDebugUtils.currentLine=17039360;
 //BA.debugLineNum = 17039360;BA.debugLine="Sub mnuConsejos_Click";
RDebugUtils.currentLine=17039361;
 //BA.debugLineNum = 17039361;BA.debugLine="StartActivity(frmComoFotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmcomofotos.getObject()));
RDebugUtils.currentLine=17039362;
 //BA.debugLineNum = 17039362;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(boolean _success,int _taskid) throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "response_streamfinish"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "response_streamfinish", new Object[] {_success,_taskid}));}
byte[] _another_buffer = null;
RDebugUtils.currentLine=18087936;
 //BA.debugLineNum = 18087936;BA.debugLine="Sub Response_StreamFinish (Success As Boolean, Tas";
RDebugUtils.currentLine=18087937;
 //BA.debugLineNum = 18087937;BA.debugLine="Log(\"envio streamfinish\")";
anywheresoftware.b4a.keywords.Common.Log("envio streamfinish");
RDebugUtils.currentLine=18087938;
 //BA.debugLineNum = 18087938;BA.debugLine="Dim another_buffer () As Byte";
_another_buffer = new byte[(int) (0)];
;
RDebugUtils.currentLine=18087939;
 //BA.debugLineNum = 18087939;BA.debugLine="another_buffer = out.ToBytesArray";
_another_buffer = _out.ToBytesArray();
RDebugUtils.currentLine=18087940;
 //BA.debugLineNum = 18087940;BA.debugLine="Log (BytesToString(another_buffer, 0, another_buf";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.BytesToString(_another_buffer,(int) (0),_another_buffer.length,"UTF8"));
RDebugUtils.currentLine=18087941;
 //BA.debugLineNum = 18087941;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=18087942;
 //BA.debugLineNum = 18087942;BA.debugLine="Log (\"exitos!\")";
anywheresoftware.b4a.keywords.Common.Log("exitos!");
 };
RDebugUtils.currentLine=18087945;
 //BA.debugLineNum = 18087945;BA.debugLine="End Sub";
return "";
}
public static String  _seekfocus_valuechanged(int _value,boolean _userchanged) throws Exception{
RDebugUtils.currentModule="frmcamara";
if (Debug.shouldDelegate(mostCurrent.activityBA, "seekfocus_valuechanged"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "seekfocus_valuechanged", new Object[] {_value,_userchanged}));}
RDebugUtils.currentLine=17629184;
 //BA.debugLineNum = 17629184;BA.debugLine="Sub seekFocus_ValueChanged (Value As Int, UserChan";
RDebugUtils.currentLine=17629185;
 //BA.debugLineNum = 17629185;BA.debugLine="If UserChanged = False Or camEx.IsZoomSupported =";
if (_userchanged==anywheresoftware.b4a.keywords.Common.False || mostCurrent._camex._iszoomsupported(null)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
RDebugUtils.currentLine=17629186;
 //BA.debugLineNum = 17629186;BA.debugLine="camEx.Zoom = Value / 100 * camEx.GetMaxZoom";
mostCurrent._camex._setzoom(null,(int) (_value/(double)100*mostCurrent._camex._getmaxzoom(null)));
RDebugUtils.currentLine=17629187;
 //BA.debugLineNum = 17629187;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters(null);
RDebugUtils.currentLine=17629188;
 //BA.debugLineNum = 17629188;BA.debugLine="End Sub";
return "";
}
}