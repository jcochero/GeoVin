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

public class frmidentificacion extends Activity implements B4AActivity{
	public static frmidentificacion mostCurrent;
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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmidentificacion");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmidentificacion).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmidentificacion");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmidentificacion", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmidentificacion) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmidentificacion) Resume **");
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
		return frmidentificacion.class;
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
        BA.LogInfo("** Activity (frmidentificacion) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            frmidentificacion mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmidentificacion) Resume **");
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
public static anywheresoftware.b4a.phone.Phone _p = null;
public static int _currentpregunta = 0;
public static String _foto1 = "";
public static String _foto2 = "";
public static String _foto3 = "";
public static String _foto4 = "";
public flm.b4a.scrollview2d.ScrollView2DWrapper _scroll2d = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _scrolloptions = null;
public static String _pregunta1 = "";
public static String _pregunta2 = "";
public static String _imagenusuario1 = "";
public static String _imagenusuario2 = "";
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuar = null;
public static int _opcionelegida = 0;
public anywheresoftware.b4a.objects.LabelWrapper _selopcion1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _selopcion2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _selopcion3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _selopcion4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion9 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion11 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion12 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion13 = null;
public anywheresoftware.b4a.objects.LabelWrapper _q2selopcion14 = null;
public flm.b4a.betterdialogs.BetterDialogs _bdialog = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinstrucciones = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombre = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldescripciongeneral = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhabitat = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgvinchuca1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldistribucion = null;
public de.amberhome.viewpager.AHPageContainer _container = null;
public de.amberhome.viewpager.AHViewPager _pager = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd = null;
public static float _zoom = 0f;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgusuario = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncambiarfoto = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblslide = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviar = null;
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
public cepave.geovin.frmcamara _frmcamara = null;
public cepave.geovin.uploadfiles _uploadfiles = null;
public cepave.geovin.frmabout _frmabout = null;
public cepave.geovin.register _register = null;
public cepave.geovin.frmreportevinchuca _frmreportevinchuca = null;
public cepave.geovin.envioarchivos2 _envioarchivos2 = null;
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=22675456;
 //BA.debugLineNum = 22675456;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=22675458;
 //BA.debugLineNum = 22675458;BA.debugLine="Activity.LoadLayout(\"layIdentificacion\")";
mostCurrent._activity.LoadLayout("layIdentificacion",mostCurrent.activityBA);
RDebugUtils.currentLine=22675460;
 //BA.debugLineNum = 22675460;BA.debugLine="imagenUsuario1 = Main.fotopath0";
mostCurrent._imagenusuario1 = mostCurrent._main._fotopath0;
RDebugUtils.currentLine=22675461;
 //BA.debugLineNum = 22675461;BA.debugLine="imagenUsuario2 = Main.fotopath1";
mostCurrent._imagenusuario2 = mostCurrent._main._fotopath1;
RDebugUtils.currentLine=22675462;
 //BA.debugLineNum = 22675462;BA.debugLine="If currentPregunta = 1 Then";
if (_currentpregunta==1) { 
RDebugUtils.currentLine=22675463;
 //BA.debugLineNum = 22675463;BA.debugLine="cargarOpcion1";
_cargaropcion1();
 }else 
{RDebugUtils.currentLine=22675464;
 //BA.debugLineNum = 22675464;BA.debugLine="Else If currentPregunta = 2 Then";
if (_currentpregunta==2) { 
RDebugUtils.currentLine=22675465;
 //BA.debugLineNum = 22675465;BA.debugLine="cargarOpcion2";
_cargaropcion2();
 }}
;
RDebugUtils.currentLine=22675468;
 //BA.debugLineNum = 22675468;BA.debugLine="End Sub";
return "";
}
public static String  _cargaropcion1() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargaropcion1"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cargaropcion1", null));}
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion3 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion4 = null;
anywheresoftware.b4a.objects.LabelWrapper _textopcion1 = null;
anywheresoftware.b4a.objects.LabelWrapper _textopcion2 = null;
anywheresoftware.b4a.objects.LabelWrapper _textopcion3 = null;
anywheresoftware.b4a.objects.LabelWrapper _textopcion4 = null;
RDebugUtils.currentLine=23068672;
 //BA.debugLineNum = 23068672;BA.debugLine="Sub cargarOpcion1";
RDebugUtils.currentLine=23068675;
 //BA.debugLineNum = 23068675;BA.debugLine="Dim imgOpcion1 As ImageView";
_imgopcion1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23068676;
 //BA.debugLineNum = 23068676;BA.debugLine="Dim imgOpcion2 As ImageView";
_imgopcion2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23068677;
 //BA.debugLineNum = 23068677;BA.debugLine="Dim imgOpcion3 As ImageView";
_imgopcion3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23068678;
 //BA.debugLineNum = 23068678;BA.debugLine="Dim imgOpcion4 As ImageView";
_imgopcion4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23068679;
 //BA.debugLineNum = 23068679;BA.debugLine="imgOpcion1.Initialize(\"\")";
_imgopcion1.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23068680;
 //BA.debugLineNum = 23068680;BA.debugLine="imgOpcion2.Initialize(\"\")";
_imgopcion2.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23068681;
 //BA.debugLineNum = 23068681;BA.debugLine="imgOpcion3.Initialize(\"\")";
_imgopcion3.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23068682;
 //BA.debugLineNum = 23068682;BA.debugLine="imgOpcion4.Initialize(\"\")";
_imgopcion4.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23068684;
 //BA.debugLineNum = 23068684;BA.debugLine="Dim textOpcion1 As Label";
_textopcion1 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=23068685;
 //BA.debugLineNum = 23068685;BA.debugLine="Dim textOpcion2 As Label";
_textopcion2 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=23068686;
 //BA.debugLineNum = 23068686;BA.debugLine="Dim textOpcion3 As Label";
_textopcion3 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=23068687;
 //BA.debugLineNum = 23068687;BA.debugLine="Dim textOpcion4 As Label";
_textopcion4 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=23068689;
 //BA.debugLineNum = 23068689;BA.debugLine="textOpcion1.Initialize(\"\")";
_textopcion1.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23068690;
 //BA.debugLineNum = 23068690;BA.debugLine="textOpcion2.Initialize(\"\")";
_textopcion2.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23068691;
 //BA.debugLineNum = 23068691;BA.debugLine="textOpcion3.Initialize(\"\")";
_textopcion3.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23068692;
 //BA.debugLineNum = 23068692;BA.debugLine="textOpcion4.Initialize(\"\")";
_textopcion4.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23068693;
 //BA.debugLineNum = 23068693;BA.debugLine="selOpcion1.Initialize(\"selOpcion1\")";
mostCurrent._selopcion1.Initialize(mostCurrent.activityBA,"selOpcion1");
RDebugUtils.currentLine=23068694;
 //BA.debugLineNum = 23068694;BA.debugLine="selOpcion2.Initialize(\"selOpcion2\")";
mostCurrent._selopcion2.Initialize(mostCurrent.activityBA,"selOpcion2");
RDebugUtils.currentLine=23068695;
 //BA.debugLineNum = 23068695;BA.debugLine="selOpcion3.Initialize(\"selOpcion3\")";
mostCurrent._selopcion3.Initialize(mostCurrent.activityBA,"selOpcion3");
RDebugUtils.currentLine=23068696;
 //BA.debugLineNum = 23068696;BA.debugLine="selOpcion4.Initialize(\"selOpcion4\")";
mostCurrent._selopcion4.Initialize(mostCurrent.activityBA,"selOpcion4");
RDebugUtils.currentLine=23068700;
 //BA.debugLineNum = 23068700;BA.debugLine="imgOpcion1.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"FotoPico1.jpg",mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=23068701;
 //BA.debugLineNum = 23068701;BA.debugLine="imgOpcion2.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"FotoPico2.jpg",mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=23068702;
 //BA.debugLineNum = 23068702;BA.debugLine="imgOpcion3.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"FotoPico3.jpg",mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=23068703;
 //BA.debugLineNum = 23068703;BA.debugLine="imgOpcion4.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"FotoPico4.jpg",mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=23068704;
 //BA.debugLineNum = 23068704;BA.debugLine="imgOpcion1.Gravity = Gravity.FILL";
_imgopcion1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23068705;
 //BA.debugLineNum = 23068705;BA.debugLine="imgOpcion2.Gravity = Gravity.FILL";
_imgopcion2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23068706;
 //BA.debugLineNum = 23068706;BA.debugLine="imgOpcion3.Gravity = Gravity.FILL";
_imgopcion3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23068707;
 //BA.debugLineNum = 23068707;BA.debugLine="imgOpcion4.Gravity = Gravity.FILL";
_imgopcion4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23068709;
 //BA.debugLineNum = 23068709;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion1, 10dip, 0,";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (0),(int) ((mostCurrent._scrolloptions.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)))*1.5),(int) (mostCurrent._scrolloptions.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
RDebugUtils.currentLine=23068710;
 //BA.debugLineNum = 23068710;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion2, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion2.getObject()),(int) (_imgopcion1.getLeft()+_imgopcion1.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) ((mostCurrent._scrolloptions.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)))*1.5),(int) (mostCurrent._scrolloptions.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
RDebugUtils.currentLine=23068711;
 //BA.debugLineNum = 23068711;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion3, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion3.getObject()),(int) (_imgopcion2.getLeft()+_imgopcion2.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) ((mostCurrent._scrolloptions.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)))*1.5),(int) (mostCurrent._scrolloptions.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
RDebugUtils.currentLine=23068712;
 //BA.debugLineNum = 23068712;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion4, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion4.getObject()),(int) (_imgopcion3.getLeft()+_imgopcion3.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) ((mostCurrent._scrolloptions.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)))*1.5),(int) (mostCurrent._scrolloptions.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
RDebugUtils.currentLine=23068713;
 //BA.debugLineNum = 23068713;BA.debugLine="scrollOptions.Panel.Width = imgOpcion4.Width + im";
mostCurrent._scrolloptions.getPanel().setWidth((int) (_imgopcion4.getWidth()+_imgopcion4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
RDebugUtils.currentLine=23068715;
 //BA.debugLineNum = 23068715;BA.debugLine="textOpcion1.Text = \"Pico fino y largo extendiéndo";
_textopcion1.setText(BA.ObjectToCharSequence("Pico fino y largo extendiéndose por detrás de las patas"));
RDebugUtils.currentLine=23068716;
 //BA.debugLineNum = 23068716;BA.debugLine="textOpcion2.Text = \"Pico fino y largo extendiéndo";
_textopcion2.setText(BA.ObjectToCharSequence("Pico fino y largo extendiéndose hasta el primer par de patas"));
RDebugUtils.currentLine=23068717;
 //BA.debugLineNum = 23068717;BA.debugLine="textOpcion3.Text = \"Pico grueso y curvo\"";
_textopcion3.setText(BA.ObjectToCharSequence("Pico grueso y curvo"));
RDebugUtils.currentLine=23068718;
 //BA.debugLineNum = 23068718;BA.debugLine="textOpcion4.Text = \"Pico robusto del largo de la";
_textopcion4.setText(BA.ObjectToCharSequence("Pico robusto del largo de la cabeza, paralela a la misma"));
RDebugUtils.currentLine=23068719;
 //BA.debugLineNum = 23068719;BA.debugLine="textOpcion1.TextSize = 12";
_textopcion1.setTextSize((float) (12));
RDebugUtils.currentLine=23068720;
 //BA.debugLineNum = 23068720;BA.debugLine="textOpcion2.TextSize = 12";
_textopcion2.setTextSize((float) (12));
RDebugUtils.currentLine=23068721;
 //BA.debugLineNum = 23068721;BA.debugLine="textOpcion3.TextSize = 12";
_textopcion3.setTextSize((float) (12));
RDebugUtils.currentLine=23068722;
 //BA.debugLineNum = 23068722;BA.debugLine="textOpcion4.TextSize = 12";
_textopcion4.setTextSize((float) (12));
RDebugUtils.currentLine=23068723;
 //BA.debugLineNum = 23068723;BA.debugLine="textOpcion1.TextColor = Colors.Black";
_textopcion1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=23068724;
 //BA.debugLineNum = 23068724;BA.debugLine="textOpcion2.TextColor = Colors.Black";
_textopcion2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=23068725;
 //BA.debugLineNum = 23068725;BA.debugLine="textOpcion3.TextColor = Colors.Black";
_textopcion3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=23068726;
 //BA.debugLineNum = 23068726;BA.debugLine="textOpcion4.TextColor = Colors.Black";
_textopcion4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=23068727;
 //BA.debugLineNum = 23068727;BA.debugLine="scrollOptions.Panel.AddView(textOpcion1, imgOpcio";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_textopcion1.getObject()),_imgopcion1.getLeft(),(int) (_imgopcion1.getHeight()+_imgopcion1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),_imgopcion1.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=23068728;
 //BA.debugLineNum = 23068728;BA.debugLine="scrollOptions.Panel.AddView(textOpcion2, imgOpcio";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_textopcion2.getObject()),_imgopcion2.getLeft(),(int) (_imgopcion2.getHeight()+_imgopcion2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),_imgopcion2.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=23068729;
 //BA.debugLineNum = 23068729;BA.debugLine="scrollOptions.Panel.AddView(textOpcion3, imgOpcio";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_textopcion3.getObject()),_imgopcion3.getLeft(),(int) (_imgopcion3.getHeight()+_imgopcion3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),_imgopcion3.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=23068730;
 //BA.debugLineNum = 23068730;BA.debugLine="scrollOptions.Panel.AddView(textOpcion4, imgOpcio";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_textopcion4.getObject()),_imgopcion4.getLeft(),(int) (_imgopcion4.getHeight()+_imgopcion4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),_imgopcion4.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=23068733;
 //BA.debugLineNum = 23068733;BA.debugLine="scrollOptions.Panel.AddView(selOpcion1, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._selopcion1.getObject()),_imgopcion1.getLeft(),_imgopcion1.getTop(),_imgopcion1.getWidth(),_imgopcion1.getHeight());
RDebugUtils.currentLine=23068734;
 //BA.debugLineNum = 23068734;BA.debugLine="scrollOptions.Panel.AddView(selOpcion2, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._selopcion2.getObject()),_imgopcion2.getLeft(),_imgopcion2.getTop(),_imgopcion2.getWidth(),_imgopcion2.getHeight());
RDebugUtils.currentLine=23068735;
 //BA.debugLineNum = 23068735;BA.debugLine="scrollOptions.Panel.AddView(selOpcion3, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._selopcion3.getObject()),_imgopcion3.getLeft(),_imgopcion3.getTop(),_imgopcion3.getWidth(),_imgopcion3.getHeight());
RDebugUtils.currentLine=23068736;
 //BA.debugLineNum = 23068736;BA.debugLine="scrollOptions.Panel.AddView(selOpcion4, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._selopcion4.getObject()),_imgopcion4.getLeft(),_imgopcion4.getTop(),_imgopcion4.getWidth(),_imgopcion4.getHeight());
RDebugUtils.currentLine=23068740;
 //BA.debugLineNum = 23068740;BA.debugLine="scroll2d.Initialize(1000,1000,\"scroll2d\")";
mostCurrent._scroll2d.Initialize(mostCurrent.activityBA,(int) (1000),(int) (1000),"scroll2d");
RDebugUtils.currentLine=23068741;
 //BA.debugLineNum = 23068741;BA.debugLine="scroll2d.Color = Colors.Black";
mostCurrent._scroll2d.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=23068742;
 //BA.debugLineNum = 23068742;BA.debugLine="Activity.AddView(scroll2d, Label1.Left, Label1.To";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scroll2d.getObject()),mostCurrent._label1.getLeft(),mostCurrent._label1.getTop(),mostCurrent._label1.getWidth(),mostCurrent._label1.getHeight());
RDebugUtils.currentLine=23068744;
 //BA.debugLineNum = 23068744;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/Geo";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg",mostCurrent._label1.getWidth(),mostCurrent._label1.getHeight(),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=23068745;
 //BA.debugLineNum = 23068745;BA.debugLine="imgUsuario.Initialize(\"\")";
mostCurrent._imgusuario.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23068746;
 //BA.debugLineNum = 23068746;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=23068747;
 //BA.debugLineNum = 23068747;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
RDebugUtils.currentLine=23068748;
 //BA.debugLineNum = 23068748;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23068750;
 //BA.debugLineNum = 23068750;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.Widt";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23068751;
 //BA.debugLineNum = 23068751;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=23068752;
 //BA.debugLineNum = 23068752;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23068754;
 //BA.debugLineNum = 23068754;BA.debugLine="GD.SetOnGestureListener(scroll2d,\"pnl_gesture\")";
mostCurrent._gd.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._scroll2d.getObject()),"pnl_gesture");
RDebugUtils.currentLine=23068755;
 //BA.debugLineNum = 23068755;BA.debugLine="GD.EnableLongPress(False)";
mostCurrent._gd.EnableLongPress(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=23068756;
 //BA.debugLineNum = 23068756;BA.debugLine="Label2.bringtofront";
mostCurrent._label2.BringToFront();
RDebugUtils.currentLine=23068758;
 //BA.debugLineNum = 23068758;BA.debugLine="End Sub";
return "";
}
public static String  _cargaropcion2() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargaropcion2"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cargaropcion2", null));}
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion3 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion4 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion5 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion6 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion7 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion8 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion9 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion10 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion11 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion12 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion13 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion14 = null;
RDebugUtils.currentLine=23396352;
 //BA.debugLineNum = 23396352;BA.debugLine="Sub cargarOpcion2";
RDebugUtils.currentLine=23396354;
 //BA.debugLineNum = 23396354;BA.debugLine="Dim imgOpcion1 As ImageView";
_imgopcion1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396355;
 //BA.debugLineNum = 23396355;BA.debugLine="Dim imgOpcion2 As ImageView";
_imgopcion2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396356;
 //BA.debugLineNum = 23396356;BA.debugLine="Dim imgOpcion3 As ImageView";
_imgopcion3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396357;
 //BA.debugLineNum = 23396357;BA.debugLine="Dim imgOpcion4 As ImageView";
_imgopcion4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396358;
 //BA.debugLineNum = 23396358;BA.debugLine="Dim imgOpcion5 As ImageView";
_imgopcion5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396359;
 //BA.debugLineNum = 23396359;BA.debugLine="Dim imgOpcion6 As ImageView";
_imgopcion6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396360;
 //BA.debugLineNum = 23396360;BA.debugLine="Dim imgOpcion7 As ImageView";
_imgopcion7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396361;
 //BA.debugLineNum = 23396361;BA.debugLine="Dim imgOpcion8 As ImageView";
_imgopcion8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396362;
 //BA.debugLineNum = 23396362;BA.debugLine="Dim imgOpcion9 As ImageView";
_imgopcion9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396363;
 //BA.debugLineNum = 23396363;BA.debugLine="Dim imgOpcion10 As ImageView";
_imgopcion10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396364;
 //BA.debugLineNum = 23396364;BA.debugLine="Dim imgOpcion11 As ImageView";
_imgopcion11 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396365;
 //BA.debugLineNum = 23396365;BA.debugLine="Dim imgOpcion12 As ImageView";
_imgopcion12 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396366;
 //BA.debugLineNum = 23396366;BA.debugLine="Dim imgOpcion13 As ImageView";
_imgopcion13 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396367;
 //BA.debugLineNum = 23396367;BA.debugLine="Dim imgOpcion14 As ImageView";
_imgopcion14 = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=23396369;
 //BA.debugLineNum = 23396369;BA.debugLine="imgOpcion1.Initialize(\"\")";
_imgopcion1.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396370;
 //BA.debugLineNum = 23396370;BA.debugLine="imgOpcion2.Initialize(\"\")";
_imgopcion2.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396371;
 //BA.debugLineNum = 23396371;BA.debugLine="imgOpcion3.Initialize(\"\")";
_imgopcion3.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396372;
 //BA.debugLineNum = 23396372;BA.debugLine="imgOpcion4.Initialize(\"\")";
_imgopcion4.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396373;
 //BA.debugLineNum = 23396373;BA.debugLine="imgOpcion5.Initialize(\"\")";
_imgopcion5.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396374;
 //BA.debugLineNum = 23396374;BA.debugLine="imgOpcion6.Initialize(\"\")";
_imgopcion6.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396375;
 //BA.debugLineNum = 23396375;BA.debugLine="imgOpcion7.Initialize(\"\")";
_imgopcion7.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396376;
 //BA.debugLineNum = 23396376;BA.debugLine="imgOpcion8.Initialize(\"\")";
_imgopcion8.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396377;
 //BA.debugLineNum = 23396377;BA.debugLine="imgOpcion9.Initialize(\"\")";
_imgopcion9.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396378;
 //BA.debugLineNum = 23396378;BA.debugLine="imgOpcion10.Initialize(\"\")";
_imgopcion10.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396379;
 //BA.debugLineNum = 23396379;BA.debugLine="imgOpcion11.Initialize(\"\")";
_imgopcion11.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396380;
 //BA.debugLineNum = 23396380;BA.debugLine="imgOpcion12.Initialize(\"\")";
_imgopcion12.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396381;
 //BA.debugLineNum = 23396381;BA.debugLine="imgOpcion13.Initialize(\"\")";
_imgopcion13.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396382;
 //BA.debugLineNum = 23396382;BA.debugLine="imgOpcion14.Initialize(\"\")";
_imgopcion14.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396384;
 //BA.debugLineNum = 23396384;BA.debugLine="Q2selOpcion1.Initialize(\"Q2selOpcion1\")";
mostCurrent._q2selopcion1.Initialize(mostCurrent.activityBA,"Q2selOpcion1");
RDebugUtils.currentLine=23396385;
 //BA.debugLineNum = 23396385;BA.debugLine="Q2selOpcion2.Initialize(\"Q2selOpcion2\")";
mostCurrent._q2selopcion2.Initialize(mostCurrent.activityBA,"Q2selOpcion2");
RDebugUtils.currentLine=23396386;
 //BA.debugLineNum = 23396386;BA.debugLine="Q2selOpcion3.Initialize(\"Q2selOpcion3\")";
mostCurrent._q2selopcion3.Initialize(mostCurrent.activityBA,"Q2selOpcion3");
RDebugUtils.currentLine=23396387;
 //BA.debugLineNum = 23396387;BA.debugLine="Q2selOpcion4.Initialize(\"Q2selOpcion4\")";
mostCurrent._q2selopcion4.Initialize(mostCurrent.activityBA,"Q2selOpcion4");
RDebugUtils.currentLine=23396388;
 //BA.debugLineNum = 23396388;BA.debugLine="Q2selOpcion5.Initialize(\"Q2selOpcion5\")";
mostCurrent._q2selopcion5.Initialize(mostCurrent.activityBA,"Q2selOpcion5");
RDebugUtils.currentLine=23396389;
 //BA.debugLineNum = 23396389;BA.debugLine="Q2selOpcion6.Initialize(\"Q2selOpcion6\")";
mostCurrent._q2selopcion6.Initialize(mostCurrent.activityBA,"Q2selOpcion6");
RDebugUtils.currentLine=23396390;
 //BA.debugLineNum = 23396390;BA.debugLine="Q2selOpcion7.Initialize(\"Q2selOpcion7\")";
mostCurrent._q2selopcion7.Initialize(mostCurrent.activityBA,"Q2selOpcion7");
RDebugUtils.currentLine=23396391;
 //BA.debugLineNum = 23396391;BA.debugLine="Q2selOpcion8.Initialize(\"Q2selOpcion8\")";
mostCurrent._q2selopcion8.Initialize(mostCurrent.activityBA,"Q2selOpcion8");
RDebugUtils.currentLine=23396392;
 //BA.debugLineNum = 23396392;BA.debugLine="Q2selOpcion9.Initialize(\"Q2selOpcion9\")";
mostCurrent._q2selopcion9.Initialize(mostCurrent.activityBA,"Q2selOpcion9");
RDebugUtils.currentLine=23396393;
 //BA.debugLineNum = 23396393;BA.debugLine="Q2selOpcion10.Initialize(\"Q2selOpcion10\")";
mostCurrent._q2selopcion10.Initialize(mostCurrent.activityBA,"Q2selOpcion10");
RDebugUtils.currentLine=23396394;
 //BA.debugLineNum = 23396394;BA.debugLine="Q2selOpcion11.Initialize(\"Q2selOpcion11\")";
mostCurrent._q2selopcion11.Initialize(mostCurrent.activityBA,"Q2selOpcion11");
RDebugUtils.currentLine=23396395;
 //BA.debugLineNum = 23396395;BA.debugLine="Q2selOpcion12.Initialize(\"Q2selOpcion12\")";
mostCurrent._q2selopcion12.Initialize(mostCurrent.activityBA,"Q2selOpcion12");
RDebugUtils.currentLine=23396396;
 //BA.debugLineNum = 23396396;BA.debugLine="Q2selOpcion13.Initialize(\"Q2selOpcion13\")";
mostCurrent._q2selopcion13.Initialize(mostCurrent.activityBA,"Q2selOpcion13");
RDebugUtils.currentLine=23396397;
 //BA.debugLineNum = 23396397;BA.debugLine="Q2selOpcion14.Initialize(\"Q2selOpcion14\")";
mostCurrent._q2selopcion14.Initialize(mostCurrent.activityBA,"Q2selOpcion14");
RDebugUtils.currentLine=23396400;
 //BA.debugLineNum = 23396400;BA.debugLine="imgOpcion1.Gravity = Gravity.FILL";
_imgopcion1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396401;
 //BA.debugLineNum = 23396401;BA.debugLine="imgOpcion2.Gravity = Gravity.FILL";
_imgopcion2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396402;
 //BA.debugLineNum = 23396402;BA.debugLine="imgOpcion3.Gravity = Gravity.FILL";
_imgopcion3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396403;
 //BA.debugLineNum = 23396403;BA.debugLine="imgOpcion4.Gravity = Gravity.FILL";
_imgopcion4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396404;
 //BA.debugLineNum = 23396404;BA.debugLine="imgOpcion5.Gravity = Gravity.FILL";
_imgopcion5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396405;
 //BA.debugLineNum = 23396405;BA.debugLine="imgOpcion6.Gravity = Gravity.FILL";
_imgopcion6.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396406;
 //BA.debugLineNum = 23396406;BA.debugLine="imgOpcion7.Gravity = Gravity.FILL";
_imgopcion7.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396407;
 //BA.debugLineNum = 23396407;BA.debugLine="imgOpcion8.Gravity = Gravity.FILL";
_imgopcion8.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396408;
 //BA.debugLineNum = 23396408;BA.debugLine="imgOpcion9.Gravity = Gravity.FILL";
_imgopcion9.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396409;
 //BA.debugLineNum = 23396409;BA.debugLine="imgOpcion10.Gravity = Gravity.FILL";
_imgopcion10.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396410;
 //BA.debugLineNum = 23396410;BA.debugLine="imgOpcion11.Gravity = Gravity.FILL";
_imgopcion11.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396411;
 //BA.debugLineNum = 23396411;BA.debugLine="imgOpcion12.Gravity = Gravity.FILL";
_imgopcion12.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396412;
 //BA.debugLineNum = 23396412;BA.debugLine="imgOpcion13.Gravity = Gravity.FILL";
_imgopcion13.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396413;
 //BA.debugLineNum = 23396413;BA.debugLine="imgOpcion14.Gravity = Gravity.FILL";
_imgopcion14.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396415;
 //BA.debugLineNum = 23396415;BA.debugLine="imgOpcion1.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 14a.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396416;
 //BA.debugLineNum = 23396416;BA.debugLine="imgOpcion2.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 15a.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396417;
 //BA.debugLineNum = 23396417;BA.debugLine="imgOpcion3.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 16a.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396418;
 //BA.debugLineNum = 23396418;BA.debugLine="imgOpcion4.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 18a.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396419;
 //BA.debugLineNum = 23396419;BA.debugLine="imgOpcion5.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 19a.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396420;
 //BA.debugLineNum = 23396420;BA.debugLine="imgOpcion6.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 21a.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396421;
 //BA.debugLineNum = 23396421;BA.debugLine="imgOpcion7.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 22a.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396422;
 //BA.debugLineNum = 23396422;BA.debugLine="imgOpcion8.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion8.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 23a.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396423;
 //BA.debugLineNum = 23396423;BA.debugLine="imgOpcion9.Bitmap = LoadBitmapSample(File.DirAsse";
_imgopcion9.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 24.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396424;
 //BA.debugLineNum = 23396424;BA.debugLine="imgOpcion10.Bitmap = LoadBitmapSample(File.DirAss";
_imgopcion10.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 25.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396425;
 //BA.debugLineNum = 23396425;BA.debugLine="imgOpcion11.Bitmap = LoadBitmapSample(File.DirAss";
_imgopcion11.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 26.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396426;
 //BA.debugLineNum = 23396426;BA.debugLine="imgOpcion12.Bitmap = LoadBitmapSample(File.DirAss";
_imgopcion12.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 27.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396427;
 //BA.debugLineNum = 23396427;BA.debugLine="imgOpcion13.Bitmap = LoadBitmapSample(File.DirAss";
_imgopcion13.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 28.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396428;
 //BA.debugLineNum = 23396428;BA.debugLine="imgOpcion14.Bitmap = LoadBitmapSample(File.DirAss";
_imgopcion14.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto 29a.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=23396430;
 //BA.debugLineNum = 23396430;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion1, 0, 0, scr";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion1.getObject()),(int) (0),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396431;
 //BA.debugLineNum = 23396431;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion2, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion2.getObject()),(int) (_imgopcion1.getWidth()+_imgopcion1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396432;
 //BA.debugLineNum = 23396432;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion3, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion3.getObject()),(int) (_imgopcion2.getWidth()+_imgopcion2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396433;
 //BA.debugLineNum = 23396433;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion4, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion4.getObject()),(int) (_imgopcion3.getWidth()+_imgopcion3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396434;
 //BA.debugLineNum = 23396434;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion5, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion5.getObject()),(int) (_imgopcion4.getWidth()+_imgopcion4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396435;
 //BA.debugLineNum = 23396435;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion6, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion6.getObject()),(int) (_imgopcion5.getWidth()+_imgopcion5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396436;
 //BA.debugLineNum = 23396436;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion7, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion7.getObject()),(int) (_imgopcion6.getWidth()+_imgopcion6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396437;
 //BA.debugLineNum = 23396437;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion8, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion8.getObject()),(int) (_imgopcion7.getWidth()+_imgopcion7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396438;
 //BA.debugLineNum = 23396438;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion9, imgOpcion";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion9.getObject()),(int) (_imgopcion8.getWidth()+_imgopcion8.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396439;
 //BA.debugLineNum = 23396439;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion10, imgOpcio";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion10.getObject()),(int) (_imgopcion9.getWidth()+_imgopcion9.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396440;
 //BA.debugLineNum = 23396440;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion11, imgOpcio";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion11.getObject()),(int) (_imgopcion10.getWidth()+_imgopcion10.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396441;
 //BA.debugLineNum = 23396441;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion12, imgOpcio";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion12.getObject()),(int) (_imgopcion11.getWidth()+_imgopcion11.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396442;
 //BA.debugLineNum = 23396442;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion13, imgOpcio";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion13.getObject()),(int) (_imgopcion12.getWidth()+_imgopcion12.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396443;
 //BA.debugLineNum = 23396443;BA.debugLine="scrollOptions.Panel.AddView(imgOpcion14, imgOpcio";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(_imgopcion14.getObject()),(int) (_imgopcion13.getWidth()+_imgopcion13.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (0),(int) (mostCurrent._scrolloptions.getHeight()/(double)1.5),mostCurrent._scrolloptions.getHeight());
RDebugUtils.currentLine=23396444;
 //BA.debugLineNum = 23396444;BA.debugLine="scrollOptions.Panel.Width  = imgOpcion14.left + i";
mostCurrent._scrolloptions.getPanel().setWidth((int) (_imgopcion14.getLeft()+_imgopcion14.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
RDebugUtils.currentLine=23396447;
 //BA.debugLineNum = 23396447;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion1, imgOpci";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion1.getObject()),_imgopcion1.getLeft(),_imgopcion1.getTop(),_imgopcion1.getWidth(),_imgopcion1.getHeight());
RDebugUtils.currentLine=23396448;
 //BA.debugLineNum = 23396448;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion2, imgOpci";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion2.getObject()),_imgopcion2.getLeft(),_imgopcion2.getTop(),_imgopcion2.getWidth(),_imgopcion2.getHeight());
RDebugUtils.currentLine=23396449;
 //BA.debugLineNum = 23396449;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion3, imgOpci";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion3.getObject()),_imgopcion3.getLeft(),_imgopcion3.getTop(),_imgopcion3.getWidth(),_imgopcion3.getHeight());
RDebugUtils.currentLine=23396450;
 //BA.debugLineNum = 23396450;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion4, imgOpci";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion4.getObject()),_imgopcion4.getLeft(),_imgopcion4.getTop(),_imgopcion4.getWidth(),_imgopcion4.getHeight());
RDebugUtils.currentLine=23396451;
 //BA.debugLineNum = 23396451;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion5, imgOpci";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion5.getObject()),_imgopcion5.getLeft(),_imgopcion5.getTop(),_imgopcion5.getWidth(),_imgopcion5.getHeight());
RDebugUtils.currentLine=23396452;
 //BA.debugLineNum = 23396452;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion6, imgOpci";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion6.getObject()),_imgopcion6.getLeft(),_imgopcion6.getTop(),_imgopcion6.getWidth(),_imgopcion6.getHeight());
RDebugUtils.currentLine=23396453;
 //BA.debugLineNum = 23396453;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion7, imgOpci";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion7.getObject()),_imgopcion7.getLeft(),_imgopcion7.getTop(),_imgopcion7.getWidth(),_imgopcion7.getHeight());
RDebugUtils.currentLine=23396454;
 //BA.debugLineNum = 23396454;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion8, imgOpci";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion8.getObject()),_imgopcion8.getLeft(),_imgopcion8.getTop(),_imgopcion8.getWidth(),_imgopcion8.getHeight());
RDebugUtils.currentLine=23396455;
 //BA.debugLineNum = 23396455;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion9, imgOpci";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion9.getObject()),_imgopcion9.getLeft(),_imgopcion9.getTop(),_imgopcion9.getWidth(),_imgopcion9.getHeight());
RDebugUtils.currentLine=23396456;
 //BA.debugLineNum = 23396456;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion10, imgOpc";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion10.getObject()),_imgopcion10.getLeft(),_imgopcion10.getTop(),_imgopcion10.getWidth(),_imgopcion10.getHeight());
RDebugUtils.currentLine=23396457;
 //BA.debugLineNum = 23396457;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion11, imgOpc";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion11.getObject()),_imgopcion11.getLeft(),_imgopcion11.getTop(),_imgopcion11.getWidth(),_imgopcion11.getHeight());
RDebugUtils.currentLine=23396458;
 //BA.debugLineNum = 23396458;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion12, imgOpc";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion12.getObject()),_imgopcion12.getLeft(),_imgopcion12.getTop(),_imgopcion12.getWidth(),_imgopcion12.getHeight());
RDebugUtils.currentLine=23396459;
 //BA.debugLineNum = 23396459;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion13, imgOpc";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion13.getObject()),_imgopcion13.getLeft(),_imgopcion13.getTop(),_imgopcion13.getWidth(),_imgopcion13.getHeight());
RDebugUtils.currentLine=23396460;
 //BA.debugLineNum = 23396460;BA.debugLine="scrollOptions.Panel.AddView(Q2selOpcion14, imgOpc";
mostCurrent._scrolloptions.getPanel().AddView((android.view.View)(mostCurrent._q2selopcion14.getObject()),_imgopcion14.getLeft(),_imgopcion14.getTop(),_imgopcion14.getWidth(),_imgopcion14.getHeight());
RDebugUtils.currentLine=23396463;
 //BA.debugLineNum = 23396463;BA.debugLine="scroll2d.Initialize(1000,1000,\"scroll2d\")";
mostCurrent._scroll2d.Initialize(mostCurrent.activityBA,(int) (1000),(int) (1000),"scroll2d");
RDebugUtils.currentLine=23396464;
 //BA.debugLineNum = 23396464;BA.debugLine="scroll2d.Color = Colors.Black";
mostCurrent._scroll2d.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=23396465;
 //BA.debugLineNum = 23396465;BA.debugLine="Activity.AddView(scroll2d, Label1.Left, Label1.To";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scroll2d.getObject()),mostCurrent._label1.getLeft(),mostCurrent._label1.getTop(),mostCurrent._label1.getWidth(),mostCurrent._label1.getHeight());
RDebugUtils.currentLine=23396466;
 //BA.debugLineNum = 23396466;BA.debugLine="If foto2 <> \"null\" Then";
if ((_foto2).equals("null") == false) { 
RDebugUtils.currentLine=23396467;
 //BA.debugLineNum = 23396467;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/Ge";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2+".jpg",mostCurrent._label1.getWidth(),mostCurrent._label1.getHeight(),anywheresoftware.b4a.keywords.Common.True);
 }else {
RDebugUtils.currentLine=23396469;
 //BA.debugLineNum = 23396469;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/Ge";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg",mostCurrent._label1.getWidth(),mostCurrent._label1.getHeight(),anywheresoftware.b4a.keywords.Common.True);
 };
RDebugUtils.currentLine=23396473;
 //BA.debugLineNum = 23396473;BA.debugLine="imgUsuario.Initialize(\"\")";
mostCurrent._imgusuario.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=23396474;
 //BA.debugLineNum = 23396474;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23396475;
 //BA.debugLineNum = 23396475;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=23396476;
 //BA.debugLineNum = 23396476;BA.debugLine="scroll2d.panel.AddView(imgUsuario, 0,0, bmp.Width";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) (0),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23396477;
 //BA.debugLineNum = 23396477;BA.debugLine="scroll2d.Panel.Width = bmp.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._bmp.getWidth());
RDebugUtils.currentLine=23396478;
 //BA.debugLineNum = 23396478;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23396480;
 //BA.debugLineNum = 23396480;BA.debugLine="GD.SetOnGestureListener(scroll2d,\"pnl_gesture\")";
mostCurrent._gd.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._scroll2d.getObject()),"pnl_gesture");
RDebugUtils.currentLine=23396481;
 //BA.debugLineNum = 23396481;BA.debugLine="GD.EnableLongPress(False)";
mostCurrent._gd.EnableLongPress(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=23396485;
 //BA.debugLineNum = 23396485;BA.debugLine="Label2.bringtofront";
mostCurrent._label2.BringToFront();
RDebugUtils.currentLine=23396486;
 //BA.debugLineNum = 23396486;BA.debugLine="lblInstrucciones.Text = \"¿A cuál de estas vinchuc";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("¿A cuál de estas vinchucas se parece más la que encontraste?"));
RDebugUtils.currentLine=23396487;
 //BA.debugLineNum = 23396487;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="frmidentificacion";
RDebugUtils.currentLine=22806528;
 //BA.debugLineNum = 22806528;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=22806530;
 //BA.debugLineNum = 22806530;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=22740992;
 //BA.debugLineNum = 22740992;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=22740993;
 //BA.debugLineNum = 22740993;BA.debugLine="If currentPregunta = 1 Then";
if (_currentpregunta==1) { 
RDebugUtils.currentLine=22740994;
 //BA.debugLineNum = 22740994;BA.debugLine="cargarOpcion1";
_cargaropcion1();
 }else 
{RDebugUtils.currentLine=22740995;
 //BA.debugLineNum = 22740995;BA.debugLine="Else If currentPregunta = 2 Then";
if (_currentpregunta==2) { 
RDebugUtils.currentLine=22740996;
 //BA.debugLineNum = 22740996;BA.debugLine="cargarOpcion2";
_cargaropcion2();
 }}
;
RDebugUtils.currentLine=22740998;
 //BA.debugLineNum = 22740998;BA.debugLine="End Sub";
return "";
}
public static String  _btncambiarfoto_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncambiarfoto_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncambiarfoto_click", null));}
RDebugUtils.currentLine=23003136;
 //BA.debugLineNum = 23003136;BA.debugLine="Sub btnCambiarFoto_Click";
RDebugUtils.currentLine=23003138;
 //BA.debugLineNum = 23003138;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuar_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncontinuar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncontinuar_click", null));}
anywheresoftware.b4a.objects.collections.Map _wherefields = null;
String _resp1 = "";
String _msg = "";
String _resp2 = "";
anywheresoftware.b4a.objects.collections.Map _speciesmap = null;
RDebugUtils.currentLine=24379392;
 //BA.debugLineNum = 24379392;BA.debugLine="Sub btnContinuar_Click";
RDebugUtils.currentLine=24379395;
 //BA.debugLineNum = 24379395;BA.debugLine="If currentPregunta = 1 Then";
if (_currentpregunta==1) { 
RDebugUtils.currentLine=24379399;
 //BA.debugLineNum = 24379399;BA.debugLine="Dim WhereFields As Map";
_wherefields = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=24379400;
 //BA.debugLineNum = 24379400;BA.debugLine="WhereFields.Initialize";
_wherefields.Initialize();
RDebugUtils.currentLine=24379401;
 //BA.debugLineNum = 24379401;BA.debugLine="WhereFields.Put(\"id\", Main.currentproject)";
_wherefields.Put((Object)("id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=24379403;
 //BA.debugLineNum = 24379403;BA.debugLine="Dim resp1 As String = opcionElegida";
_resp1 = BA.NumberToString(_opcionelegida);
RDebugUtils.currentLine=24379404;
 //BA.debugLineNum = 24379404;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_wherefields);
RDebugUtils.currentLine=24379405;
 //BA.debugLineNum = 24379405;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","par1",(Object)(_resp1),_wherefields);
RDebugUtils.currentLine=24379406;
 //BA.debugLineNum = 24379406;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","terminado",(Object)("si"),_wherefields);
RDebugUtils.currentLine=24379409;
 //BA.debugLineNum = 24379409;BA.debugLine="If opcionElegida = 1 Then";
if (_opcionelegida==1) { 
RDebugUtils.currentLine=24379410;
 //BA.debugLineNum = 24379410;BA.debugLine="utilidades.Mensaje(\"No es una vinchuca\", \"MsgNo";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"No es una vinchuca","MsgNoVinchuca.png","Usted podría haber encontrado un insecto que se alimenta de plantas. Un profesional verificará sus fotos pero si su observación fue correcta  el insecto que encontró no es una vinchuca","Para mayor información ingrese a 'Información sobre Vinchucas'","Continuar","","",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=24379411;
 //BA.debugLineNum = 24379411;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=24379412;
 //BA.debugLineNum = 24379412;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else 
{RDebugUtils.currentLine=24379413;
 //BA.debugLineNum = 24379413;BA.debugLine="Else if opcionElegida = 2 Then";
if (_opcionelegida==2) { 
RDebugUtils.currentLine=24379414;
 //BA.debugLineNum = 24379414;BA.debugLine="utilidades.Mensaje(\"No es una vinchuca\", \"MsgNo";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"No es una vinchuca","MsgNoVinchuca.png","Usted podría haber encontrado un insecto que se alimenta de plantas. Un profesional verificará sus fotos pero si su observación fue correcta  el insecto que encontró no es una vinchuca","Para mayor información ingrese a 'Información sobre Vinchucas'","Continuar","","",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=24379415;
 //BA.debugLineNum = 24379415;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=24379416;
 //BA.debugLineNum = 24379416;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else 
{RDebugUtils.currentLine=24379417;
 //BA.debugLineNum = 24379417;BA.debugLine="Else if opcionElegida = 3 Then";
if (_opcionelegida==3) { 
RDebugUtils.currentLine=24379418;
 //BA.debugLineNum = 24379418;BA.debugLine="utilidades.Mensaje(\"No es una vinchuca\", \"MsgNo";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"No es una vinchuca","MsgNoVinchuca.png","Usted podría haber encontrado un insecto que se alimenta de otros insectos. Un profesional verificará sus fotos pero si su observación fue correcta  el insecto que encontró no es una vinchuca","Para mayor información lea 'Información sobre Vinchucas'","Continuar","","",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=24379419;
 //BA.debugLineNum = 24379419;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=24379420;
 //BA.debugLineNum = 24379420;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else 
{RDebugUtils.currentLine=24379421;
 //BA.debugLineNum = 24379421;BA.debugLine="Else if opcionElegida = 4 Then";
if (_opcionelegida==4) { 
RDebugUtils.currentLine=24379422;
 //BA.debugLineNum = 24379422;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=24379423;
 //BA.debugLineNum = 24379423;BA.debugLine="msg = utilidades.Mensaje(\"Cuidado!\", \"MsgVinchu";
_msg = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Cuidado!","MsgVinchuca.png","Usted podría haber encontrado una VINCHUCA. Un profesional verificará sus fotos y le responderá si es realmente una vinchuca","Mientras tanto, te animas a tratar determinar que especie es?","Si, quiero continuar","","No",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=24379424;
 //BA.debugLineNum = 24379424;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=24379425;
 //BA.debugLineNum = 24379425;BA.debugLine="currentPregunta = 2";
_currentpregunta = (int) (2);
RDebugUtils.currentLine=24379426;
 //BA.debugLineNum = 24379426;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=24379427;
 //BA.debugLineNum = 24379427;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=24379428;
 //BA.debugLineNum = 24379428;BA.debugLine="StartActivity(Me)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,frmidentificacion.getObject());
 };
 }}}}
;
 }else 
{RDebugUtils.currentLine=24379431;
 //BA.debugLineNum = 24379431;BA.debugLine="Else if currentPregunta = 2 Then";
if (_currentpregunta==2) { 
RDebugUtils.currentLine=24379432;
 //BA.debugLineNum = 24379432;BA.debugLine="btnContinuar.Visible = False";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=24379435;
 //BA.debugLineNum = 24379435;BA.debugLine="Dim WhereFields As Map";
_wherefields = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=24379436;
 //BA.debugLineNum = 24379436;BA.debugLine="WhereFields.Initialize";
_wherefields.Initialize();
RDebugUtils.currentLine=24379437;
 //BA.debugLineNum = 24379437;BA.debugLine="WhereFields.Put(\"id\", Main.currentproject)";
_wherefields.Put((Object)("id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=24379439;
 //BA.debugLineNum = 24379439;BA.debugLine="Dim resp2 As String = opcionElegida";
_resp2 = BA.NumberToString(_opcionelegida);
RDebugUtils.currentLine=24379440;
 //BA.debugLineNum = 24379440;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_wherefields);
RDebugUtils.currentLine=24379441;
 //BA.debugLineNum = 24379441;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","par2",(Object)(_resp2),_wherefields);
RDebugUtils.currentLine=24379442;
 //BA.debugLineNum = 24379442;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","terminado",(Object)("si"),_wherefields);
RDebugUtils.currentLine=24379449;
 //BA.debugLineNum = 24379449;BA.debugLine="Dim speciesMap As Map";
_speciesmap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=24379450;
 //BA.debugLineNum = 24379450;BA.debugLine="speciesMap.Initialize";
_speciesmap.Initialize();
RDebugUtils.currentLine=24379451;
 //BA.debugLineNum = 24379451;BA.debugLine="speciesMap = DBUtils.ExecuteMap(Starter.speciesD";
_speciesmap = mostCurrent._dbutils._executemap(mostCurrent.activityBA,mostCurrent._starter._speciesdb,"SELECT * FROM especies WHERE Id=?",new String[]{BA.NumberToString(_opcionelegida+1)});
RDebugUtils.currentLine=24379452;
 //BA.debugLineNum = 24379452;BA.debugLine="If speciesMap = Null Or speciesMap.IsInitialized";
if (_speciesmap== null || _speciesmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=24379453;
 //BA.debugLineNum = 24379453;BA.debugLine="Return";
if (true) return "";
 }else {
RDebugUtils.currentLine=24379455;
 //BA.debugLineNum = 24379455;BA.debugLine="Activity.LoadLayout(\"layCaracteristicasSp1\")";
mostCurrent._activity.LoadLayout("layCaracteristicasSp1",mostCurrent.activityBA);
RDebugUtils.currentLine=24379457;
 //BA.debugLineNum = 24379457;BA.debugLine="lblNombre.Text = speciesMap.Get(\"especie\")";
mostCurrent._lblnombre.setText(BA.ObjectToCharSequence(_speciesmap.Get((Object)("especie"))));
RDebugUtils.currentLine=24379458;
 //BA.debugLineNum = 24379458;BA.debugLine="lblDescripcionGeneral.Text = speciesMap.Get(\"ge";
mostCurrent._lbldescripciongeneral.setText(BA.ObjectToCharSequence(_speciesmap.Get((Object)("general"))));
RDebugUtils.currentLine=24379459;
 //BA.debugLineNum = 24379459;BA.debugLine="lblHabitat.Text = speciesMap.Get(\"habitat\")";
mostCurrent._lblhabitat.setText(BA.ObjectToCharSequence(_speciesmap.Get((Object)("habitat"))));
RDebugUtils.currentLine=24379460;
 //BA.debugLineNum = 24379460;BA.debugLine="lblDistribucion.Text = speciesMap.Get(\"distribu";
mostCurrent._lbldistribucion.setText(BA.ObjectToCharSequence(_speciesmap.Get((Object)("distribucion"))));
RDebugUtils.currentLine=24379461;
 //BA.debugLineNum = 24379461;BA.debugLine="btnEnviar.visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=24379462;
 //BA.debugLineNum = 24379462;BA.debugLine="lblSlide.Visible = False";
mostCurrent._lblslide.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=24379463;
 //BA.debugLineNum = 24379463;BA.debugLine="btnCambiarFoto.Visible = False";
mostCurrent._btncambiarfoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=24379465;
 //BA.debugLineNum = 24379465;BA.debugLine="If speciesMap.Get(\"foto1\") <> Null And speciesM";
if (_speciesmap.Get((Object)("foto1"))!= null && (_speciesmap.Get((Object)("foto1"))).equals((Object)("")) == false && (_speciesmap.Get((Object)("foto1"))).equals((Object)("null")) == false) { 
RDebugUtils.currentLine=24379466;
 //BA.debugLineNum = 24379466;BA.debugLine="imgVinchuca1.Bitmap = LoadBitmapSample(File.Di";
mostCurrent._imgvinchuca1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_speciesmap.Get((Object)("foto1"))),mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
 };
 };
 }}
;
RDebugUtils.currentLine=24379507;
 //BA.debugLineNum = 24379507;BA.debugLine="End Sub";
return "";
}
public static String  _btnenviar_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnenviar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnenviar_click", null));}
RDebugUtils.currentLine=24444928;
 //BA.debugLineNum = 24444928;BA.debugLine="Sub btnEnviar_Click";
RDebugUtils.currentLine=24444929;
 //BA.debugLineNum = 24444929;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=24444930;
 //BA.debugLineNum = 24444930;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=24444931;
 //BA.debugLineNum = 24444931;BA.debugLine="End Sub";
return "";
}
public static String  _btnvolver_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnvolver_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnvolver_click", null));}
RDebugUtils.currentLine=24510464;
 //BA.debugLineNum = 24510464;BA.debugLine="Sub btnVolver_Click";
RDebugUtils.currentLine=24510465;
 //BA.debugLineNum = 24510465;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=24510466;
 //BA.debugLineNum = 24510466;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=24510467;
 //BA.debugLineNum = 24510467;BA.debugLine="currentPregunta = 2";
_currentpregunta = (int) (2);
RDebugUtils.currentLine=24510468;
 //BA.debugLineNum = 24510468;BA.debugLine="StartActivity(Me)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,frmidentificacion.getObject());
RDebugUtils.currentLine=24510470;
 //BA.debugLineNum = 24510470;BA.debugLine="End Sub";
return "";
}
public static String  _pnl_gesture_onpinchclose(float _newdistance,float _previousdistance,Object _motionevent) throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "pnl_gesture_onpinchclose"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "pnl_gesture_onpinchclose", new Object[] {_newdistance,_previousdistance,_motionevent}));}
RDebugUtils.currentLine=22937600;
 //BA.debugLineNum = 22937600;BA.debugLine="Sub pnl_gesture_onPinchClose(NewDistance As Float,";
RDebugUtils.currentLine=22937601;
 //BA.debugLineNum = 22937601;BA.debugLine="zoom = zoom *(NewDistance/PreviousDistance)";
_zoom = (float) (_zoom*(_newdistance/(double)_previousdistance));
RDebugUtils.currentLine=22937602;
 //BA.debugLineNum = 22937602;BA.debugLine="imgUsuario.Width = bmp.Width*zoom";
mostCurrent._imgusuario.setWidth((int) (mostCurrent._bmp.getWidth()*_zoom));
RDebugUtils.currentLine=22937603;
 //BA.debugLineNum = 22937603;BA.debugLine="imgUsuario.Height = bmp.Height*zoom";
mostCurrent._imgusuario.setHeight((int) (mostCurrent._bmp.getHeight()*_zoom));
RDebugUtils.currentLine=22937604;
 //BA.debugLineNum = 22937604;BA.debugLine="imgUsuario.Top = (scroll2d.Panel.Height-imgUsuari";
mostCurrent._imgusuario.setTop((int) ((mostCurrent._scroll2d.getPanel().getHeight()-mostCurrent._imgusuario.getHeight())/(double)3));
RDebugUtils.currentLine=22937605;
 //BA.debugLineNum = 22937605;BA.debugLine="imgUsuario.left = (scroll2d.Panel.Width - imgUsua";
mostCurrent._imgusuario.setLeft((int) ((mostCurrent._scroll2d.getPanel().getWidth()-mostCurrent._imgusuario.getWidth())/(double)3));
RDebugUtils.currentLine=22937608;
 //BA.debugLineNum = 22937608;BA.debugLine="End Sub";
return "";
}
public static String  _pnl_gesture_onpinchopen(float _newdistance,float _previousdistance,Object _motionevent) throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "pnl_gesture_onpinchopen"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "pnl_gesture_onpinchopen", new Object[] {_newdistance,_previousdistance,_motionevent}));}
RDebugUtils.currentLine=22872064;
 //BA.debugLineNum = 22872064;BA.debugLine="Sub pnl_gesture_onPinchOpen(NewDistance As Float,";
RDebugUtils.currentLine=22872065;
 //BA.debugLineNum = 22872065;BA.debugLine="zoom = zoom * (NewDistance/PreviousDistance)";
_zoom = (float) (_zoom*(_newdistance/(double)_previousdistance));
RDebugUtils.currentLine=22872066;
 //BA.debugLineNum = 22872066;BA.debugLine="imgUsuario.Width = bmp.Width*zoom";
mostCurrent._imgusuario.setWidth((int) (mostCurrent._bmp.getWidth()*_zoom));
RDebugUtils.currentLine=22872067;
 //BA.debugLineNum = 22872067;BA.debugLine="imgUsuario.Height = bmp.Height*zoom";
mostCurrent._imgusuario.setHeight((int) (mostCurrent._bmp.getHeight()*_zoom));
RDebugUtils.currentLine=22872068;
 //BA.debugLineNum = 22872068;BA.debugLine="imgUsuario.Top = (scroll2d.Panel.Height-imgUsuari";
mostCurrent._imgusuario.setTop((int) ((mostCurrent._scroll2d.getPanel().getHeight()-mostCurrent._imgusuario.getHeight())/(double)3));
RDebugUtils.currentLine=22872069;
 //BA.debugLineNum = 22872069;BA.debugLine="imgUsuario.left = (scroll2d.Panel.Width - imgUsua";
mostCurrent._imgusuario.setLeft((int) ((mostCurrent._scroll2d.getPanel().getWidth()-mostCurrent._imgusuario.getWidth())/(double)3));
RDebugUtils.currentLine=22872072;
 //BA.debugLineNum = 22872072;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion1_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion1_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion1_click", null));}
RDebugUtils.currentLine=23461888;
 //BA.debugLineNum = 23461888;BA.debugLine="Sub Q2selOpcion1_Click";
RDebugUtils.currentLine=23461889;
 //BA.debugLineNum = 23461889;BA.debugLine="opcionElegida = 1";
_opcionelegida = (int) (1);
RDebugUtils.currentLine=23461890;
 //BA.debugLineNum = 23461890;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461891;
 //BA.debugLineNum = 23461891;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461892;
 //BA.debugLineNum = 23461892;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461893;
 //BA.debugLineNum = 23461893;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461894;
 //BA.debugLineNum = 23461894;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461895;
 //BA.debugLineNum = 23461895;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461896;
 //BA.debugLineNum = 23461896;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461897;
 //BA.debugLineNum = 23461897;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461898;
 //BA.debugLineNum = 23461898;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461899;
 //BA.debugLineNum = 23461899;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461900;
 //BA.debugLineNum = 23461900;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461901;
 //BA.debugLineNum = 23461901;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461902;
 //BA.debugLineNum = 23461902;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461903;
 //BA.debugLineNum = 23461903;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23461904;
 //BA.debugLineNum = 23461904;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion10_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion10_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion10_click", null));}
RDebugUtils.currentLine=24051712;
 //BA.debugLineNum = 24051712;BA.debugLine="Sub Q2selOpcion10_Click";
RDebugUtils.currentLine=24051713;
 //BA.debugLineNum = 24051713;BA.debugLine="opcionElegida = 10";
_opcionelegida = (int) (10);
RDebugUtils.currentLine=24051714;
 //BA.debugLineNum = 24051714;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051715;
 //BA.debugLineNum = 24051715;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051716;
 //BA.debugLineNum = 24051716;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051717;
 //BA.debugLineNum = 24051717;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051718;
 //BA.debugLineNum = 24051718;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051719;
 //BA.debugLineNum = 24051719;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051720;
 //BA.debugLineNum = 24051720;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051721;
 //BA.debugLineNum = 24051721;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051722;
 //BA.debugLineNum = 24051722;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051723;
 //BA.debugLineNum = 24051723;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051724;
 //BA.debugLineNum = 24051724;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051725;
 //BA.debugLineNum = 24051725;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051726;
 //BA.debugLineNum = 24051726;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051727;
 //BA.debugLineNum = 24051727;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24051728;
 //BA.debugLineNum = 24051728;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion11_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion11_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion11_click", null));}
RDebugUtils.currentLine=24117248;
 //BA.debugLineNum = 24117248;BA.debugLine="Sub Q2selOpcion11_Click";
RDebugUtils.currentLine=24117249;
 //BA.debugLineNum = 24117249;BA.debugLine="opcionElegida = 11";
_opcionelegida = (int) (11);
RDebugUtils.currentLine=24117250;
 //BA.debugLineNum = 24117250;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117251;
 //BA.debugLineNum = 24117251;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117252;
 //BA.debugLineNum = 24117252;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117253;
 //BA.debugLineNum = 24117253;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117254;
 //BA.debugLineNum = 24117254;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117255;
 //BA.debugLineNum = 24117255;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117256;
 //BA.debugLineNum = 24117256;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117257;
 //BA.debugLineNum = 24117257;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117258;
 //BA.debugLineNum = 24117258;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117259;
 //BA.debugLineNum = 24117259;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117260;
 //BA.debugLineNum = 24117260;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117261;
 //BA.debugLineNum = 24117261;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117262;
 //BA.debugLineNum = 24117262;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117263;
 //BA.debugLineNum = 24117263;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24117264;
 //BA.debugLineNum = 24117264;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion12_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion12_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion12_click", null));}
RDebugUtils.currentLine=24182784;
 //BA.debugLineNum = 24182784;BA.debugLine="Sub Q2selOpcion12_Click";
RDebugUtils.currentLine=24182785;
 //BA.debugLineNum = 24182785;BA.debugLine="opcionElegida = 12";
_opcionelegida = (int) (12);
RDebugUtils.currentLine=24182786;
 //BA.debugLineNum = 24182786;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182787;
 //BA.debugLineNum = 24182787;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182788;
 //BA.debugLineNum = 24182788;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182789;
 //BA.debugLineNum = 24182789;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182790;
 //BA.debugLineNum = 24182790;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182791;
 //BA.debugLineNum = 24182791;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182792;
 //BA.debugLineNum = 24182792;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182793;
 //BA.debugLineNum = 24182793;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182794;
 //BA.debugLineNum = 24182794;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182795;
 //BA.debugLineNum = 24182795;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182796;
 //BA.debugLineNum = 24182796;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182797;
 //BA.debugLineNum = 24182797;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182798;
 //BA.debugLineNum = 24182798;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182799;
 //BA.debugLineNum = 24182799;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24182800;
 //BA.debugLineNum = 24182800;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion13_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion13_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion13_click", null));}
RDebugUtils.currentLine=24248320;
 //BA.debugLineNum = 24248320;BA.debugLine="Sub Q2selOpcion13_Click";
RDebugUtils.currentLine=24248321;
 //BA.debugLineNum = 24248321;BA.debugLine="opcionElegida = 13";
_opcionelegida = (int) (13);
RDebugUtils.currentLine=24248322;
 //BA.debugLineNum = 24248322;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248323;
 //BA.debugLineNum = 24248323;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248324;
 //BA.debugLineNum = 24248324;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248325;
 //BA.debugLineNum = 24248325;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248326;
 //BA.debugLineNum = 24248326;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248327;
 //BA.debugLineNum = 24248327;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248328;
 //BA.debugLineNum = 24248328;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248329;
 //BA.debugLineNum = 24248329;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248330;
 //BA.debugLineNum = 24248330;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248331;
 //BA.debugLineNum = 24248331;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248332;
 //BA.debugLineNum = 24248332;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248333;
 //BA.debugLineNum = 24248333;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248334;
 //BA.debugLineNum = 24248334;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248335;
 //BA.debugLineNum = 24248335;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24248336;
 //BA.debugLineNum = 24248336;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion14_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion14_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion14_click", null));}
RDebugUtils.currentLine=24313856;
 //BA.debugLineNum = 24313856;BA.debugLine="Sub Q2selOpcion14_Click";
RDebugUtils.currentLine=24313857;
 //BA.debugLineNum = 24313857;BA.debugLine="opcionElegida = 14";
_opcionelegida = (int) (14);
RDebugUtils.currentLine=24313858;
 //BA.debugLineNum = 24313858;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313859;
 //BA.debugLineNum = 24313859;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313860;
 //BA.debugLineNum = 24313860;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313861;
 //BA.debugLineNum = 24313861;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313862;
 //BA.debugLineNum = 24313862;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313863;
 //BA.debugLineNum = 24313863;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313864;
 //BA.debugLineNum = 24313864;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313865;
 //BA.debugLineNum = 24313865;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313866;
 //BA.debugLineNum = 24313866;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313867;
 //BA.debugLineNum = 24313867;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313868;
 //BA.debugLineNum = 24313868;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313869;
 //BA.debugLineNum = 24313869;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313870;
 //BA.debugLineNum = 24313870;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313871;
 //BA.debugLineNum = 24313871;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=24313872;
 //BA.debugLineNum = 24313872;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion2_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion2_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion2_click", null));}
RDebugUtils.currentLine=23527424;
 //BA.debugLineNum = 23527424;BA.debugLine="Sub Q2selOpcion2_Click";
RDebugUtils.currentLine=23527425;
 //BA.debugLineNum = 23527425;BA.debugLine="opcionElegida = 2";
_opcionelegida = (int) (2);
RDebugUtils.currentLine=23527426;
 //BA.debugLineNum = 23527426;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527427;
 //BA.debugLineNum = 23527427;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527428;
 //BA.debugLineNum = 23527428;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527429;
 //BA.debugLineNum = 23527429;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527430;
 //BA.debugLineNum = 23527430;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527431;
 //BA.debugLineNum = 23527431;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527432;
 //BA.debugLineNum = 23527432;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527433;
 //BA.debugLineNum = 23527433;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527434;
 //BA.debugLineNum = 23527434;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527435;
 //BA.debugLineNum = 23527435;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527436;
 //BA.debugLineNum = 23527436;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527437;
 //BA.debugLineNum = 23527437;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527438;
 //BA.debugLineNum = 23527438;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527439;
 //BA.debugLineNum = 23527439;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23527440;
 //BA.debugLineNum = 23527440;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion3_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion3_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion3_click", null));}
RDebugUtils.currentLine=23592960;
 //BA.debugLineNum = 23592960;BA.debugLine="Sub Q2selOpcion3_Click";
RDebugUtils.currentLine=23592961;
 //BA.debugLineNum = 23592961;BA.debugLine="opcionElegida = 3";
_opcionelegida = (int) (3);
RDebugUtils.currentLine=23592962;
 //BA.debugLineNum = 23592962;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592963;
 //BA.debugLineNum = 23592963;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592964;
 //BA.debugLineNum = 23592964;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592965;
 //BA.debugLineNum = 23592965;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592966;
 //BA.debugLineNum = 23592966;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592967;
 //BA.debugLineNum = 23592967;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592968;
 //BA.debugLineNum = 23592968;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592969;
 //BA.debugLineNum = 23592969;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592970;
 //BA.debugLineNum = 23592970;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592971;
 //BA.debugLineNum = 23592971;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592972;
 //BA.debugLineNum = 23592972;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592973;
 //BA.debugLineNum = 23592973;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592974;
 //BA.debugLineNum = 23592974;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592975;
 //BA.debugLineNum = 23592975;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23592977;
 //BA.debugLineNum = 23592977;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion4_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion4_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion4_click", null));}
RDebugUtils.currentLine=23658496;
 //BA.debugLineNum = 23658496;BA.debugLine="Sub Q2selOpcion4_Click";
RDebugUtils.currentLine=23658497;
 //BA.debugLineNum = 23658497;BA.debugLine="opcionElegida = 4";
_opcionelegida = (int) (4);
RDebugUtils.currentLine=23658498;
 //BA.debugLineNum = 23658498;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658499;
 //BA.debugLineNum = 23658499;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658500;
 //BA.debugLineNum = 23658500;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658501;
 //BA.debugLineNum = 23658501;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658502;
 //BA.debugLineNum = 23658502;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658503;
 //BA.debugLineNum = 23658503;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658504;
 //BA.debugLineNum = 23658504;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658505;
 //BA.debugLineNum = 23658505;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658506;
 //BA.debugLineNum = 23658506;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658507;
 //BA.debugLineNum = 23658507;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658508;
 //BA.debugLineNum = 23658508;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658509;
 //BA.debugLineNum = 23658509;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658510;
 //BA.debugLineNum = 23658510;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658511;
 //BA.debugLineNum = 23658511;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23658512;
 //BA.debugLineNum = 23658512;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion5_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion5_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion5_click", null));}
RDebugUtils.currentLine=23724032;
 //BA.debugLineNum = 23724032;BA.debugLine="Sub Q2selOpcion5_Click";
RDebugUtils.currentLine=23724033;
 //BA.debugLineNum = 23724033;BA.debugLine="opcionElegida = 5";
_opcionelegida = (int) (5);
RDebugUtils.currentLine=23724034;
 //BA.debugLineNum = 23724034;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724035;
 //BA.debugLineNum = 23724035;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724036;
 //BA.debugLineNum = 23724036;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724037;
 //BA.debugLineNum = 23724037;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724038;
 //BA.debugLineNum = 23724038;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724039;
 //BA.debugLineNum = 23724039;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724040;
 //BA.debugLineNum = 23724040;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724041;
 //BA.debugLineNum = 23724041;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724042;
 //BA.debugLineNum = 23724042;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724043;
 //BA.debugLineNum = 23724043;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724044;
 //BA.debugLineNum = 23724044;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724045;
 //BA.debugLineNum = 23724045;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724046;
 //BA.debugLineNum = 23724046;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724047;
 //BA.debugLineNum = 23724047;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23724048;
 //BA.debugLineNum = 23724048;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion6_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion6_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion6_click", null));}
RDebugUtils.currentLine=23789568;
 //BA.debugLineNum = 23789568;BA.debugLine="Sub Q2selOpcion6_Click";
RDebugUtils.currentLine=23789569;
 //BA.debugLineNum = 23789569;BA.debugLine="opcionElegida = 6";
_opcionelegida = (int) (6);
RDebugUtils.currentLine=23789570;
 //BA.debugLineNum = 23789570;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789571;
 //BA.debugLineNum = 23789571;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789572;
 //BA.debugLineNum = 23789572;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789573;
 //BA.debugLineNum = 23789573;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789574;
 //BA.debugLineNum = 23789574;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789575;
 //BA.debugLineNum = 23789575;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789576;
 //BA.debugLineNum = 23789576;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789577;
 //BA.debugLineNum = 23789577;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789578;
 //BA.debugLineNum = 23789578;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789579;
 //BA.debugLineNum = 23789579;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789580;
 //BA.debugLineNum = 23789580;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789581;
 //BA.debugLineNum = 23789581;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789582;
 //BA.debugLineNum = 23789582;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789583;
 //BA.debugLineNum = 23789583;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23789584;
 //BA.debugLineNum = 23789584;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion7_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion7_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion7_click", null));}
RDebugUtils.currentLine=23855104;
 //BA.debugLineNum = 23855104;BA.debugLine="Sub Q2selOpcion7_Click";
RDebugUtils.currentLine=23855105;
 //BA.debugLineNum = 23855105;BA.debugLine="opcionElegida = 7";
_opcionelegida = (int) (7);
RDebugUtils.currentLine=23855106;
 //BA.debugLineNum = 23855106;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855107;
 //BA.debugLineNum = 23855107;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855108;
 //BA.debugLineNum = 23855108;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855109;
 //BA.debugLineNum = 23855109;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855110;
 //BA.debugLineNum = 23855110;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855111;
 //BA.debugLineNum = 23855111;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855112;
 //BA.debugLineNum = 23855112;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855113;
 //BA.debugLineNum = 23855113;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855114;
 //BA.debugLineNum = 23855114;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855115;
 //BA.debugLineNum = 23855115;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855116;
 //BA.debugLineNum = 23855116;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855117;
 //BA.debugLineNum = 23855117;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855118;
 //BA.debugLineNum = 23855118;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855119;
 //BA.debugLineNum = 23855119;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23855120;
 //BA.debugLineNum = 23855120;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion8_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion8_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion8_click", null));}
RDebugUtils.currentLine=23920640;
 //BA.debugLineNum = 23920640;BA.debugLine="Sub Q2selOpcion8_Click";
RDebugUtils.currentLine=23920641;
 //BA.debugLineNum = 23920641;BA.debugLine="opcionElegida = 8";
_opcionelegida = (int) (8);
RDebugUtils.currentLine=23920642;
 //BA.debugLineNum = 23920642;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920643;
 //BA.debugLineNum = 23920643;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920644;
 //BA.debugLineNum = 23920644;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920645;
 //BA.debugLineNum = 23920645;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920646;
 //BA.debugLineNum = 23920646;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920647;
 //BA.debugLineNum = 23920647;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920648;
 //BA.debugLineNum = 23920648;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920649;
 //BA.debugLineNum = 23920649;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920650;
 //BA.debugLineNum = 23920650;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920651;
 //BA.debugLineNum = 23920651;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920652;
 //BA.debugLineNum = 23920652;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920653;
 //BA.debugLineNum = 23920653;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920654;
 //BA.debugLineNum = 23920654;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920655;
 //BA.debugLineNum = 23920655;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23920656;
 //BA.debugLineNum = 23920656;BA.debugLine="End Sub";
return "";
}
public static String  _q2selopcion9_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "q2selopcion9_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "q2selopcion9_click", null));}
RDebugUtils.currentLine=23986176;
 //BA.debugLineNum = 23986176;BA.debugLine="Sub Q2selOpcion9_Click";
RDebugUtils.currentLine=23986177;
 //BA.debugLineNum = 23986177;BA.debugLine="opcionElegida = 9";
_opcionelegida = (int) (9);
RDebugUtils.currentLine=23986178;
 //BA.debugLineNum = 23986178;BA.debugLine="Q2selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986179;
 //BA.debugLineNum = 23986179;BA.debugLine="Q2selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986180;
 //BA.debugLineNum = 23986180;BA.debugLine="Q2selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986181;
 //BA.debugLineNum = 23986181;BA.debugLine="Q2selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986182;
 //BA.debugLineNum = 23986182;BA.debugLine="Q2selOpcion5.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986183;
 //BA.debugLineNum = 23986183;BA.debugLine="Q2selOpcion6.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986184;
 //BA.debugLineNum = 23986184;BA.debugLine="Q2selOpcion7.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986185;
 //BA.debugLineNum = 23986185;BA.debugLine="Q2selOpcion8.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion8.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986186;
 //BA.debugLineNum = 23986186;BA.debugLine="Q2selOpcion9.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._q2selopcion9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986187;
 //BA.debugLineNum = 23986187;BA.debugLine="Q2selOpcion10.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion10.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986188;
 //BA.debugLineNum = 23986188;BA.debugLine="Q2selOpcion11.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion11.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986189;
 //BA.debugLineNum = 23986189;BA.debugLine="Q2selOpcion12.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion12.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986190;
 //BA.debugLineNum = 23986190;BA.debugLine="Q2selOpcion13.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion13.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986191;
 //BA.debugLineNum = 23986191;BA.debugLine="Q2selOpcion14.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._q2selopcion14.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23986192;
 //BA.debugLineNum = 23986192;BA.debugLine="End Sub";
return "";
}
public static String  _selopcion1_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "selopcion1_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "selopcion1_click", null));}
RDebugUtils.currentLine=23134208;
 //BA.debugLineNum = 23134208;BA.debugLine="Sub selOpcion1_Click";
RDebugUtils.currentLine=23134213;
 //BA.debugLineNum = 23134213;BA.debugLine="If imgUsuario.Tag = \"1\" Then";
if ((mostCurrent._imgusuario.getTag()).equals((Object)("1"))) { 
RDebugUtils.currentLine=23134214;
 //BA.debugLineNum = 23134214;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg")) { 
RDebugUtils.currentLine=23134215;
 //BA.debugLineNum = 23134215;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg",mostCurrent._label1.getWidth(),mostCurrent._label1.getHeight(),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=23134216;
 //BA.debugLineNum = 23134216;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
RDebugUtils.currentLine=23134217;
 //BA.debugLineNum = 23134217;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=23134218;
 //BA.debugLineNum = 23134218;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23134219;
 //BA.debugLineNum = 23134219;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=23134220;
 //BA.debugLineNum = 23134220;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
RDebugUtils.currentLine=23134221;
 //BA.debugLineNum = 23134221;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
RDebugUtils.currentLine=23134222;
 //BA.debugLineNum = 23134222;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23134223;
 //BA.debugLineNum = 23134223;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=23134224;
 //BA.debugLineNum = 23134224;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23134225;
 //BA.debugLineNum = 23134225;BA.debugLine="imgUsuario.Tag = \"2\"";
mostCurrent._imgusuario.setTag((Object)("2"));
 };
 };
 }else 
{RDebugUtils.currentLine=23134228;
 //BA.debugLineNum = 23134228;BA.debugLine="Else if imgUsuario.Tag = \"2\" Then";
if ((mostCurrent._imgusuario.getTag()).equals((Object)("2"))) { 
RDebugUtils.currentLine=23134229;
 //BA.debugLineNum = 23134229;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2+".jpg")) { 
RDebugUtils.currentLine=23134230;
 //BA.debugLineNum = 23134230;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2+".jpg",mostCurrent._label1.getWidth(),mostCurrent._label1.getHeight(),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=23134231;
 //BA.debugLineNum = 23134231;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
RDebugUtils.currentLine=23134232;
 //BA.debugLineNum = 23134232;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=23134233;
 //BA.debugLineNum = 23134233;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23134234;
 //BA.debugLineNum = 23134234;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=23134235;
 //BA.debugLineNum = 23134235;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
RDebugUtils.currentLine=23134236;
 //BA.debugLineNum = 23134236;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
RDebugUtils.currentLine=23134237;
 //BA.debugLineNum = 23134237;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23134238;
 //BA.debugLineNum = 23134238;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=23134239;
 //BA.debugLineNum = 23134239;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23134240;
 //BA.debugLineNum = 23134240;BA.debugLine="imgUsuario.Tag = \"3\"";
mostCurrent._imgusuario.setTag((Object)("3"));
 };
 }else {
RDebugUtils.currentLine=23134243;
 //BA.debugLineNum = 23134243;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
RDebugUtils.currentLine=23134244;
 //BA.debugLineNum = 23134244;BA.debugLine="selOpcion1_Click";
_selopcion1_click();
 };
 }else 
{RDebugUtils.currentLine=23134246;
 //BA.debugLineNum = 23134246;BA.debugLine="Else if imgUsuario.Tag = \"3\" Then";
if ((mostCurrent._imgusuario.getTag()).equals((Object)("3"))) { 
RDebugUtils.currentLine=23134247;
 //BA.debugLineNum = 23134247;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3+".jpg")) { 
RDebugUtils.currentLine=23134248;
 //BA.debugLineNum = 23134248;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3+".jpg",mostCurrent._label1.getWidth(),mostCurrent._label1.getHeight(),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=23134249;
 //BA.debugLineNum = 23134249;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
RDebugUtils.currentLine=23134250;
 //BA.debugLineNum = 23134250;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=23134251;
 //BA.debugLineNum = 23134251;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23134252;
 //BA.debugLineNum = 23134252;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=23134253;
 //BA.debugLineNum = 23134253;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
RDebugUtils.currentLine=23134254;
 //BA.debugLineNum = 23134254;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
RDebugUtils.currentLine=23134255;
 //BA.debugLineNum = 23134255;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23134256;
 //BA.debugLineNum = 23134256;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=23134257;
 //BA.debugLineNum = 23134257;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23134258;
 //BA.debugLineNum = 23134258;BA.debugLine="imgUsuario.Tag = \"4\"";
mostCurrent._imgusuario.setTag((Object)("4"));
 };
 }else {
RDebugUtils.currentLine=23134261;
 //BA.debugLineNum = 23134261;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
RDebugUtils.currentLine=23134262;
 //BA.debugLineNum = 23134262;BA.debugLine="selOpcion1_Click";
_selopcion1_click();
 };
 }else 
{RDebugUtils.currentLine=23134264;
 //BA.debugLineNum = 23134264;BA.debugLine="Else if imgUsuario.Tag = \"4\" Then";
if ((mostCurrent._imgusuario.getTag()).equals((Object)("4"))) { 
RDebugUtils.currentLine=23134265;
 //BA.debugLineNum = 23134265;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4+".jpg")) { 
RDebugUtils.currentLine=23134266;
 //BA.debugLineNum = 23134266;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4+".jpg",mostCurrent._label1.getWidth(),mostCurrent._label1.getHeight(),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=23134267;
 //BA.debugLineNum = 23134267;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
RDebugUtils.currentLine=23134268;
 //BA.debugLineNum = 23134268;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=23134269;
 //BA.debugLineNum = 23134269;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=23134270;
 //BA.debugLineNum = 23134270;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=23134271;
 //BA.debugLineNum = 23134271;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
RDebugUtils.currentLine=23134272;
 //BA.debugLineNum = 23134272;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
RDebugUtils.currentLine=23134273;
 //BA.debugLineNum = 23134273;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23134274;
 //BA.debugLineNum = 23134274;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=23134275;
 //BA.debugLineNum = 23134275;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=23134276;
 //BA.debugLineNum = 23134276;BA.debugLine="imgUsuario.Tag = \"4\"";
mostCurrent._imgusuario.setTag((Object)("4"));
 };
 }else {
RDebugUtils.currentLine=23134279;
 //BA.debugLineNum = 23134279;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
RDebugUtils.currentLine=23134280;
 //BA.debugLineNum = 23134280;BA.debugLine="selOpcion1_Click";
_selopcion1_click();
 };
 }}}}
;
RDebugUtils.currentLine=23134294;
 //BA.debugLineNum = 23134294;BA.debugLine="End Sub";
return "";
}
public static String  _selopcion2_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "selopcion2_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "selopcion2_click", null));}
RDebugUtils.currentLine=23199744;
 //BA.debugLineNum = 23199744;BA.debugLine="Sub selOpcion2_Click";
RDebugUtils.currentLine=23199745;
 //BA.debugLineNum = 23199745;BA.debugLine="opcionElegida = 2";
_opcionelegida = (int) (2);
RDebugUtils.currentLine=23199746;
 //BA.debugLineNum = 23199746;BA.debugLine="selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23199747;
 //BA.debugLineNum = 23199747;BA.debugLine="selOpcion2.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23199748;
 //BA.debugLineNum = 23199748;BA.debugLine="selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23199749;
 //BA.debugLineNum = 23199749;BA.debugLine="selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23199750;
 //BA.debugLineNum = 23199750;BA.debugLine="End Sub";
return "";
}
public static String  _selopcion3_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "selopcion3_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "selopcion3_click", null));}
RDebugUtils.currentLine=23265280;
 //BA.debugLineNum = 23265280;BA.debugLine="Sub selOpcion3_Click";
RDebugUtils.currentLine=23265281;
 //BA.debugLineNum = 23265281;BA.debugLine="opcionElegida = 3";
_opcionelegida = (int) (3);
RDebugUtils.currentLine=23265282;
 //BA.debugLineNum = 23265282;BA.debugLine="selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23265283;
 //BA.debugLineNum = 23265283;BA.debugLine="selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23265284;
 //BA.debugLineNum = 23265284;BA.debugLine="selOpcion3.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23265285;
 //BA.debugLineNum = 23265285;BA.debugLine="selOpcion4.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23265286;
 //BA.debugLineNum = 23265286;BA.debugLine="End Sub";
return "";
}
public static String  _selopcion4_click() throws Exception{
RDebugUtils.currentModule="frmidentificacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "selopcion4_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "selopcion4_click", null));}
RDebugUtils.currentLine=23330816;
 //BA.debugLineNum = 23330816;BA.debugLine="Sub selOpcion4_Click";
RDebugUtils.currentLine=23330817;
 //BA.debugLineNum = 23330817;BA.debugLine="opcionElegida = 4";
_opcionelegida = (int) (4);
RDebugUtils.currentLine=23330818;
 //BA.debugLineNum = 23330818;BA.debugLine="selOpcion1.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._selopcion1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23330819;
 //BA.debugLineNum = 23330819;BA.debugLine="selOpcion2.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._selopcion2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23330820;
 //BA.debugLineNum = 23330820;BA.debugLine="selOpcion3.Color = Colors.ARGB(0, 255, 0,0)";
mostCurrent._selopcion3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23330821;
 //BA.debugLineNum = 23330821;BA.debugLine="selOpcion4.Color = Colors.ARGB(60, 255, 0,0)";
mostCurrent._selopcion4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (60),(int) (255),(int) (0),(int) (0)));
RDebugUtils.currentLine=23330822;
 //BA.debugLineNum = 23330822;BA.debugLine="End Sub";
return "";
}
}