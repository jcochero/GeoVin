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

public class frmidentificacionnew extends Activity implements B4AActivity{
	public static frmidentificacionnew mostCurrent;
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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmidentificacionnew");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmidentificacionnew).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmidentificacionnew");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmidentificacionnew", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmidentificacionnew) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmidentificacionnew) Resume **");
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
		return frmidentificacionnew.class;
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
        BA.LogInfo("** Activity (frmidentificacionnew) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            frmidentificacionnew mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmidentificacionnew) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _lblinstrucciones = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombre = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgvinchuca11 = null;
public de.amberhome.viewpager.AHPageContainer _container = null;
public de.amberhome.viewpager.AHViewPager _pager = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfondo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvolver = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghabitat = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgdistribucion = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldescripciongeneral = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd = null;
public static float _zoom = 0f;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgusuario = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncambiarfoto = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblslide = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion1_1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion2_1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion4_1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgopcion3_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.ButtonWrapper _fotocerrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _fotocambiar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvermifoto = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopcion1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopcion2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopcion3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopcion4 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmaprender _frmaprender = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.frmlocalizacion _frmlocalizacion = null;
public cepave.geovin.frmdatosanteriores _frmdatosanteriores = null;
public cepave.geovin.frmperfil _frmperfil = null;
public cepave.geovin.dbutils _dbutils = null;
public cepave.geovin.utilidades _utilidades = null;
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
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=11141120;
 //BA.debugLineNum = 11141120;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=11141124;
 //BA.debugLineNum = 11141124;BA.debugLine="imagenUsuario1 = Main.fotopath0";
mostCurrent._imagenusuario1 = mostCurrent._main._fotopath0;
RDebugUtils.currentLine=11141125;
 //BA.debugLineNum = 11141125;BA.debugLine="imagenUsuario2 = Main.fotopath1";
mostCurrent._imagenusuario2 = mostCurrent._main._fotopath1;
RDebugUtils.currentLine=11141126;
 //BA.debugLineNum = 11141126;BA.debugLine="If currentPregunta = 1 Then";
if (_currentpregunta==1) { 
RDebugUtils.currentLine=11141127;
 //BA.debugLineNum = 11141127;BA.debugLine="Activity.LoadLayout(\"layIdentificacionNew_1\")";
mostCurrent._activity.LoadLayout("layIdentificacionNew_1",mostCurrent.activityBA);
RDebugUtils.currentLine=11141128;
 //BA.debugLineNum = 11141128;BA.debugLine="cargarOpcion1";
_cargaropcion1();
RDebugUtils.currentLine=11141129;
 //BA.debugLineNum = 11141129;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 }else 
{RDebugUtils.currentLine=11141130;
 //BA.debugLineNum = 11141130;BA.debugLine="Else If currentPregunta = 2 Then";
if (_currentpregunta==2) { 
 }}
;
RDebugUtils.currentLine=11141139;
 //BA.debugLineNum = 11141139;BA.debugLine="End Sub";
return "";
}
public static String  _cargaropcion1() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargaropcion1"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cargaropcion1", null));}
RDebugUtils.currentLine=11534336;
 //BA.debugLineNum = 11534336;BA.debugLine="Sub cargarOpcion1";
RDebugUtils.currentLine=11534337;
 //BA.debugLineNum = 11534337;BA.debugLine="imgOpcion1_1.Width = 48%x";
mostCurrent._imgopcion1_1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (48),mostCurrent.activityBA));
RDebugUtils.currentLine=11534338;
 //BA.debugLineNum = 11534338;BA.debugLine="imgOpcion2_1.Width = 48%x";
mostCurrent._imgopcion2_1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (48),mostCurrent.activityBA));
RDebugUtils.currentLine=11534339;
 //BA.debugLineNum = 11534339;BA.debugLine="imgOpcion3_1.Width = 48%x";
mostCurrent._imgopcion3_1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (48),mostCurrent.activityBA));
RDebugUtils.currentLine=11534340;
 //BA.debugLineNum = 11534340;BA.debugLine="imgOpcion4_1.Width = 48%x";
mostCurrent._imgopcion4_1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (48),mostCurrent.activityBA));
RDebugUtils.currentLine=11534341;
 //BA.debugLineNum = 11534341;BA.debugLine="imgOpcion1_1.Left = 2%x";
mostCurrent._imgopcion1_1.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA));
RDebugUtils.currentLine=11534342;
 //BA.debugLineNum = 11534342;BA.debugLine="imgOpcion2_1.Left = 52%x";
mostCurrent._imgopcion2_1.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (52),mostCurrent.activityBA));
RDebugUtils.currentLine=11534343;
 //BA.debugLineNum = 11534343;BA.debugLine="imgOpcion3_1.Left = 2%x";
mostCurrent._imgopcion3_1.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA));
RDebugUtils.currentLine=11534344;
 //BA.debugLineNum = 11534344;BA.debugLine="imgOpcion4_1.Left = 52%x";
mostCurrent._imgopcion4_1.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (52),mostCurrent.activityBA));
RDebugUtils.currentLine=11534345;
 //BA.debugLineNum = 11534345;BA.debugLine="imgOpcion3_1.Top = 50%y";
mostCurrent._imgopcion3_1.setTop(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
RDebugUtils.currentLine=11534346;
 //BA.debugLineNum = 11534346;BA.debugLine="imgOpcion4_1.Top = 50%y";
mostCurrent._imgopcion4_1.setTop(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
RDebugUtils.currentLine=11534347;
 //BA.debugLineNum = 11534347;BA.debugLine="textOpcion3.Top = imgOpcion3_1.Top + imgOpcion3_1";
mostCurrent._textopcion3.setTop((int) (mostCurrent._imgopcion3_1.getTop()+mostCurrent._imgopcion3_1.getHeight()));
RDebugUtils.currentLine=11534348;
 //BA.debugLineNum = 11534348;BA.debugLine="textOpcion4.Top = imgOpcion4_1.Top + imgOpcion4_1";
mostCurrent._textopcion4.setTop((int) (mostCurrent._imgopcion4_1.getTop()+mostCurrent._imgopcion4_1.getHeight()));
RDebugUtils.currentLine=11534350;
 //BA.debugLineNum = 11534350;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
RDebugUtils.currentLine=11272192;
 //BA.debugLineNum = 11272192;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=11272194;
 //BA.debugLineNum = 11272194;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=11206656;
 //BA.debugLineNum = 11206656;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=11206663;
 //BA.debugLineNum = 11206663;BA.debugLine="If currentPregunta = 2 Then";
if (_currentpregunta==2) { 
RDebugUtils.currentLine=11206664;
 //BA.debugLineNum = 11206664;BA.debugLine="If Activity.Width < Activity.Height Then";
if (mostCurrent._activity.getWidth()<mostCurrent._activity.getHeight()) { 
RDebugUtils.currentLine=11206665;
 //BA.debugLineNum = 11206665;BA.debugLine="p.SetScreenOrientation(0)";
_p.SetScreenOrientation(processBA,(int) (0));
 }else {
RDebugUtils.currentLine=11206667;
 //BA.debugLineNum = 11206667;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=11206668;
 //BA.debugLineNum = 11206668;BA.debugLine="Activity.LoadLayout(\"layIdentificacionNew_21\")";
mostCurrent._activity.LoadLayout("layIdentificacionNew_21",mostCurrent.activityBA);
RDebugUtils.currentLine=11206669;
 //BA.debugLineNum = 11206669;BA.debugLine="cargarOpcion2";
_cargaropcion2();
 };
 }else 
{RDebugUtils.currentLine=11206671;
 //BA.debugLineNum = 11206671;BA.debugLine="Else If currentPregunta = 1 Then";
if (_currentpregunta==1) { 
RDebugUtils.currentLine=11206672;
 //BA.debugLineNum = 11206672;BA.debugLine="cargarOpcion1";
_cargaropcion1();
 }}
;
RDebugUtils.currentLine=11206677;
 //BA.debugLineNum = 11206677;BA.debugLine="End Sub";
return "";
}
public static String  _cargaropcion2() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargaropcion2"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cargaropcion2", null));}
anywheresoftware.b4a.objects.PanelWrapper[] _pan = null;
int _i = 0;
String[] _currentsp = null;
RDebugUtils.currentLine=12124160;
 //BA.debugLineNum = 12124160;BA.debugLine="Sub cargarOpcion2";
RDebugUtils.currentLine=12124162;
 //BA.debugLineNum = 12124162;BA.debugLine="lblInstrucciones.Text = \"¿A cuál de estas vinchuc";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("¿A cuál de estas vinchucas se parece más la que encontraste?"));
RDebugUtils.currentLine=12124163;
 //BA.debugLineNum = 12124163;BA.debugLine="If Main.speciesMap = Null Or Main.speciesMap.IsIn";
if (mostCurrent._main._speciesmap== null || mostCurrent._main._speciesmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=12124164;
 //BA.debugLineNum = 12124164;BA.debugLine="Return";
if (true) return "";
 }else {
RDebugUtils.currentLine=12124166;
 //BA.debugLineNum = 12124166;BA.debugLine="Dim Pan(Main.speciesMap.Size) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[mostCurrent._main._speciesmap.getSize()];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
RDebugUtils.currentLine=12124168;
 //BA.debugLineNum = 12124168;BA.debugLine="Container.Initialize";
mostCurrent._container.Initialize(mostCurrent.activityBA);
RDebugUtils.currentLine=12124169;
 //BA.debugLineNum = 12124169;BA.debugLine="For i = 0 To Pan.Length - 2";
{
final int step7 = 1;
final int limit7 = (int) (_pan.length-2);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
RDebugUtils.currentLine=12124170;
 //BA.debugLineNum = 12124170;BA.debugLine="Pan(i).Initialize(\"Pan\" & i)";
_pan[_i].Initialize(mostCurrent.activityBA,"Pan"+BA.NumberToString(_i));
RDebugUtils.currentLine=12124171;
 //BA.debugLineNum = 12124171;BA.debugLine="Pan(i).LoadLayout(\"layIdentificacionNew_21_Foto";
_pan[_i].LoadLayout("layIdentificacionNew_21_Fotos",mostCurrent.activityBA);
RDebugUtils.currentLine=12124173;
 //BA.debugLineNum = 12124173;BA.debugLine="Dim currentsp() As String";
_currentsp = new String[(int) (0)];
java.util.Arrays.fill(_currentsp,"");
RDebugUtils.currentLine=12124174;
 //BA.debugLineNum = 12124174;BA.debugLine="currentsp = Main.speciesMap.Get(i+1)";
_currentsp = (String[])(mostCurrent._main._speciesmap.Get((int) (_i+1)));
RDebugUtils.currentLine=12124176;
 //BA.debugLineNum = 12124176;BA.debugLine="If currentsp(5) <> Null And currentsp(5) <> \"\"";
if (_currentsp[(int) (5)]!= null && (_currentsp[(int) (5)]).equals("") == false) { 
RDebugUtils.currentLine=12124177;
 //BA.debugLineNum = 12124177;BA.debugLine="imgVinchuca11.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgvinchuca11.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_currentsp[(int) (5)],mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=12124178;
 //BA.debugLineNum = 12124178;BA.debugLine="imgVinchuca11.Height = 60%y";
mostCurrent._imgvinchuca11.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (60),mostCurrent.activityBA));
 };
RDebugUtils.currentLine=12124184;
 //BA.debugLineNum = 12124184;BA.debugLine="Container.AddPageAt(Pan(i), \"layIdentificacionN";
mostCurrent._container.AddPageAt((android.view.View)(_pan[_i].getObject()),"layIdentificacionNew_21_Fotos",_i);
 }
};
RDebugUtils.currentLine=12124187;
 //BA.debugLineNum = 12124187;BA.debugLine="Pager.Initialize2(Container, \"Pager\")";
mostCurrent._pager.Initialize2(mostCurrent.activityBA,mostCurrent._container,"Pager");
RDebugUtils.currentLine=12124188;
 //BA.debugLineNum = 12124188;BA.debugLine="Activity.AddView(Pager, 10%x, 10%y, Activity.Wid";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._pager.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),mostCurrent._activity.getWidth(),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (65),mostCurrent.activityBA));
 };
RDebugUtils.currentLine=12124193;
 //BA.debugLineNum = 12124193;BA.debugLine="End Sub";
return "";
}
public static String  _btncambiarfoto_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncambiarfoto_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncambiarfoto_click", null));}
RDebugUtils.currentLine=11468800;
 //BA.debugLineNum = 11468800;BA.debugLine="Sub btnCambiarFoto_Click";
RDebugUtils.currentLine=11468802;
 //BA.debugLineNum = 11468802;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuar_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncontinuar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncontinuar_click", null));}
anywheresoftware.b4a.objects.collections.Map _wherefields = null;
String _resp1 = "";
String _msg = "";
String _resp2 = "";
RDebugUtils.currentLine=12255232;
 //BA.debugLineNum = 12255232;BA.debugLine="Sub btnContinuar_Click";
RDebugUtils.currentLine=12255235;
 //BA.debugLineNum = 12255235;BA.debugLine="If currentPregunta = 1 Then";
if (_currentpregunta==1) { 
RDebugUtils.currentLine=12255239;
 //BA.debugLineNum = 12255239;BA.debugLine="Dim WhereFields As Map";
_wherefields = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=12255240;
 //BA.debugLineNum = 12255240;BA.debugLine="WhereFields.Initialize";
_wherefields.Initialize();
RDebugUtils.currentLine=12255241;
 //BA.debugLineNum = 12255241;BA.debugLine="WhereFields.Put(\"id\", Main.currentproject)";
_wherefields.Put((Object)("id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=12255243;
 //BA.debugLineNum = 12255243;BA.debugLine="Dim resp1 As String = opcionElegida";
_resp1 = BA.NumberToString(_opcionelegida);
RDebugUtils.currentLine=12255244;
 //BA.debugLineNum = 12255244;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_wherefields);
RDebugUtils.currentLine=12255245;
 //BA.debugLineNum = 12255245;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","par1",(Object)(_resp1),_wherefields);
RDebugUtils.currentLine=12255246;
 //BA.debugLineNum = 12255246;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","terminado",(Object)("si"),_wherefields);
RDebugUtils.currentLine=12255249;
 //BA.debugLineNum = 12255249;BA.debugLine="If opcionElegida = 1 Then";
if (_opcionelegida==1) { 
RDebugUtils.currentLine=12255250;
 //BA.debugLineNum = 12255250;BA.debugLine="utilidades.Mensaje(\"No es una vinchuca\", \"MsgNo";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"No es una vinchuca","MsgNoVinchuca.png","Usted podría haber encontrado un insecto que se alimenta de plantas. Un profesional verificará sus fotos pero si su observación fue correcta  el insecto que encontró no es una vinchuca","Para mayor información ingrese a 'Información sobre Vinchucas'","Continuar","","",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=12255251;
 //BA.debugLineNum = 12255251;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=12255252;
 //BA.debugLineNum = 12255252;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else 
{RDebugUtils.currentLine=12255253;
 //BA.debugLineNum = 12255253;BA.debugLine="Else if opcionElegida = 2 Then";
if (_opcionelegida==2) { 
RDebugUtils.currentLine=12255254;
 //BA.debugLineNum = 12255254;BA.debugLine="utilidades.Mensaje(\"No es una vinchuca\", \"MsgNo";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"No es una vinchuca","MsgNoVinchuca.png","Usted podría haber encontrado un insecto que se alimenta de plantas. Un profesional verificará sus fotos pero si su observación fue correcta  el insecto que encontró no es una vinchuca","Para mayor información ingrese a 'Información sobre Vinchucas'","Continuar","","",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=12255255;
 //BA.debugLineNum = 12255255;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=12255256;
 //BA.debugLineNum = 12255256;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else 
{RDebugUtils.currentLine=12255257;
 //BA.debugLineNum = 12255257;BA.debugLine="Else if opcionElegida = 3 Then";
if (_opcionelegida==3) { 
RDebugUtils.currentLine=12255258;
 //BA.debugLineNum = 12255258;BA.debugLine="utilidades.Mensaje(\"No es una vinchuca\", \"MsgNo";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"No es una vinchuca","MsgNoVinchuca.png","Usted podría haber encontrado un insecto que se alimenta de otros insectos. Un profesional verificará sus fotos pero si su observación fue correcta  el insecto que encontró no es una vinchuca","Para mayor información lea 'Información sobre Vinchucas'","Continuar","","",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=12255259;
 //BA.debugLineNum = 12255259;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=12255260;
 //BA.debugLineNum = 12255260;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else 
{RDebugUtils.currentLine=12255261;
 //BA.debugLineNum = 12255261;BA.debugLine="Else if opcionElegida = 4 Then";
if (_opcionelegida==4) { 
RDebugUtils.currentLine=12255262;
 //BA.debugLineNum = 12255262;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=12255263;
 //BA.debugLineNum = 12255263;BA.debugLine="msg = utilidades.Mensaje(\"Cuidado!\", \"MsgVinchu";
_msg = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Cuidado!","MsgVinchuca.png","Usted podría haber encontrado una VINCHUCA. Un profesional verificará sus fotos y le responderá si es realmente una vinchuca","Mientras tanto, te animas a tratar determinar que especie es?","Si, quiero continuar","","No",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=12255264;
 //BA.debugLineNum = 12255264;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=12255265;
 //BA.debugLineNum = 12255265;BA.debugLine="currentPregunta = 2";
_currentpregunta = (int) (2);
RDebugUtils.currentLine=12255266;
 //BA.debugLineNum = 12255266;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=12255267;
 //BA.debugLineNum = 12255267;BA.debugLine="Activity_Resume";
_activity_resume();
 };
 }}}}
;
 }else 
{RDebugUtils.currentLine=12255271;
 //BA.debugLineNum = 12255271;BA.debugLine="Else if currentPregunta = 2 Then";
if (_currentpregunta==2) { 
RDebugUtils.currentLine=12255272;
 //BA.debugLineNum = 12255272;BA.debugLine="btnContinuar.Visible = False";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=12255273;
 //BA.debugLineNum = 12255273;BA.debugLine="btnVerMiFoto.Visible = False";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=12255276;
 //BA.debugLineNum = 12255276;BA.debugLine="opcionElegida = Pager.CurrentPage + 1";
_opcionelegida = (int) (mostCurrent._pager.getCurrentPage()+1);
RDebugUtils.currentLine=12255279;
 //BA.debugLineNum = 12255279;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=12255280;
 //BA.debugLineNum = 12255280;BA.debugLine="msg = Msgbox2(\"¿Quieres confirmar esta identific";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Quieres confirmar esta identificación? Si aceptas, un especialista verificará el dato y te notificará"),BA.ObjectToCharSequence("Confirmar"),"Si, creo que es esta","","No, quiero ver otras",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"MsgVinchuca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA)).getObject()),mostCurrent.activityBA));
RDebugUtils.currentLine=12255281;
 //BA.debugLineNum = 12255281;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=12255283;
 //BA.debugLineNum = 12255283;BA.debugLine="Dim WhereFields As Map";
_wherefields = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=12255284;
 //BA.debugLineNum = 12255284;BA.debugLine="WhereFields.Initialize";
_wherefields.Initialize();
RDebugUtils.currentLine=12255285;
 //BA.debugLineNum = 12255285;BA.debugLine="WhereFields.Put(\"id\", Main.currentproject)";
_wherefields.Put((Object)("id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=12255287;
 //BA.debugLineNum = 12255287;BA.debugLine="Dim resp2 As String = opcionElegida";
_resp2 = BA.NumberToString(_opcionelegida);
RDebugUtils.currentLine=12255288;
 //BA.debugLineNum = 12255288;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_wherefields);
RDebugUtils.currentLine=12255289;
 //BA.debugLineNum = 12255289;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","par2",(Object)(_resp2),_wherefields);
RDebugUtils.currentLine=12255290;
 //BA.debugLineNum = 12255290;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","terminado",(Object)("si"),_wherefields);
RDebugUtils.currentLine=12255292;
 //BA.debugLineNum = 12255292;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=12255293;
 //BA.debugLineNum = 12255293;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
RDebugUtils.currentLine=12255295;
 //BA.debugLineNum = 12255295;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=12255296;
 //BA.debugLineNum = 12255296;BA.debugLine="btnVerMiFoto.Visible = True";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }}
;
RDebugUtils.currentLine=12255300;
 //BA.debugLineNum = 12255300;BA.debugLine="End Sub";
return "";
}
public static String  _btnenviar_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnenviar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnenviar_click", null));}
RDebugUtils.currentLine=12320768;
 //BA.debugLineNum = 12320768;BA.debugLine="Sub btnEnviar_Click";
RDebugUtils.currentLine=12320769;
 //BA.debugLineNum = 12320769;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=12320770;
 //BA.debugLineNum = 12320770;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=12320771;
 //BA.debugLineNum = 12320771;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasinfo_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnmasinfo_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnmasinfo_click", null));}
anywheresoftware.b4a.objects.collections.Map _speciesmap = null;
RDebugUtils.currentLine=12189696;
 //BA.debugLineNum = 12189696;BA.debugLine="Sub btnMasInfo_Click";
RDebugUtils.currentLine=12189697;
 //BA.debugLineNum = 12189697;BA.debugLine="btnContinuar.Visible = False";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=12189698;
 //BA.debugLineNum = 12189698;BA.debugLine="btnVerMiFoto.Visible = False";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=12189699;
 //BA.debugLineNum = 12189699;BA.debugLine="opcionElegida = Pager.CurrentPage +1";
_opcionelegida = (int) (mostCurrent._pager.getCurrentPage()+1);
RDebugUtils.currentLine=12189701;
 //BA.debugLineNum = 12189701;BA.debugLine="Dim speciesMap As Map";
_speciesmap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=12189702;
 //BA.debugLineNum = 12189702;BA.debugLine="speciesMap.Initialize";
_speciesmap.Initialize();
RDebugUtils.currentLine=12189703;
 //BA.debugLineNum = 12189703;BA.debugLine="speciesMap = DBUtils.ExecuteMap(Starter.speciesDB";
_speciesmap = mostCurrent._dbutils._executemap(mostCurrent.activityBA,mostCurrent._starter._speciesdb,"SELECT * FROM especies WHERE Id=?",new String[]{BA.NumberToString(_opcionelegida+1)});
RDebugUtils.currentLine=12189704;
 //BA.debugLineNum = 12189704;BA.debugLine="If speciesMap = Null Or speciesMap.IsInitialized";
if (_speciesmap== null || _speciesmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=12189705;
 //BA.debugLineNum = 12189705;BA.debugLine="Return";
if (true) return "";
 }else {
RDebugUtils.currentLine=12189707;
 //BA.debugLineNum = 12189707;BA.debugLine="Activity.LoadLayout(\"layCaracteristicasSp1\")";
mostCurrent._activity.LoadLayout("layCaracteristicasSp1",mostCurrent.activityBA);
RDebugUtils.currentLine=12189708;
 //BA.debugLineNum = 12189708;BA.debugLine="lblNombre.Text = speciesMap.Get(\"especie\")";
mostCurrent._lblnombre.setText(BA.ObjectToCharSequence(_speciesmap.Get((Object)("especie"))));
RDebugUtils.currentLine=12189709;
 //BA.debugLineNum = 12189709;BA.debugLine="lblDescripcionGeneral.Text = speciesMap.Get(\"gen";
mostCurrent._lbldescripciongeneral.setText(BA.ObjectToCharSequence(_speciesmap.Get((Object)("general"))));
RDebugUtils.currentLine=12189711;
 //BA.debugLineNum = 12189711;BA.debugLine="imgHabitat.Bitmap = LoadBitmapSample(File.DirAss";
mostCurrent._imghabitat.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_speciesmap.Get((Object)("habitat"))),mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=12189712;
 //BA.debugLineNum = 12189712;BA.debugLine="imgDistribucion.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgdistribucion.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_speciesmap.Get((Object)("distribucion"))),mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=12189713;
 //BA.debugLineNum = 12189713;BA.debugLine="btnVolver.Visible = True";
mostCurrent._btnvolver.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
RDebugUtils.currentLine=12189719;
 //BA.debugLineNum = 12189719;BA.debugLine="End Sub";
return "";
}
public static String  _btnvermifoto_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnvermifoto_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnvermifoto_click", null));}
RDebugUtils.currentLine=11862016;
 //BA.debugLineNum = 11862016;BA.debugLine="Sub btnVerMiFoto_Click";
RDebugUtils.currentLine=11862018;
 //BA.debugLineNum = 11862018;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
RDebugUtils.currentLine=11862019;
 //BA.debugLineNum = 11862019;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
RDebugUtils.currentLine=11862020;
 //BA.debugLineNum = 11862020;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=11862023;
 //BA.debugLineNum = 11862023;BA.debugLine="scroll2d.Initialize(1000,1000,\"scroll2d\")";
mostCurrent._scroll2d.Initialize(mostCurrent.activityBA,(int) (1000),(int) (1000),"scroll2d");
RDebugUtils.currentLine=11862024;
 //BA.debugLineNum = 11862024;BA.debugLine="scroll2d.Color = Colors.Black";
mostCurrent._scroll2d.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=11862025;
 //BA.debugLineNum = 11862025;BA.debugLine="Activity.AddView(scroll2d, 10%x, 10%y, 80%x, 80%y";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scroll2d.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
RDebugUtils.currentLine=11862027;
 //BA.debugLineNum = 11862027;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/Geo";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11862028;
 //BA.debugLineNum = 11862028;BA.debugLine="imgUsuario.Initialize(\"\")";
mostCurrent._imgusuario.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=11862029;
 //BA.debugLineNum = 11862029;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=11862030;
 //BA.debugLineNum = 11862030;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
RDebugUtils.currentLine=11862031;
 //BA.debugLineNum = 11862031;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=11862033;
 //BA.debugLineNum = 11862033;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.Widt";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11862034;
 //BA.debugLineNum = 11862034;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=11862035;
 //BA.debugLineNum = 11862035;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11862037;
 //BA.debugLineNum = 11862037;BA.debugLine="GD.SetOnGestureListener(scroll2d,\"pnl_gesture\")";
mostCurrent._gd.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._scroll2d.getObject()),"pnl_gesture");
RDebugUtils.currentLine=11862038;
 //BA.debugLineNum = 11862038;BA.debugLine="GD.EnableLongPress(False)";
mostCurrent._gd.EnableLongPress(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=11862041;
 //BA.debugLineNum = 11862041;BA.debugLine="fotoCerrar.Initialize(\"fotoCerrar\")";
mostCurrent._fotocerrar.Initialize(mostCurrent.activityBA,"fotoCerrar");
RDebugUtils.currentLine=11862042;
 //BA.debugLineNum = 11862042;BA.debugLine="fotoCerrar.Text = \"X\"";
mostCurrent._fotocerrar.setText(BA.ObjectToCharSequence("X"));
RDebugUtils.currentLine=11862043;
 //BA.debugLineNum = 11862043;BA.debugLine="fotoCerrar.Color = Colors.ARGB(100,255,255,255)";
mostCurrent._fotocerrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)));
RDebugUtils.currentLine=11862044;
 //BA.debugLineNum = 11862044;BA.debugLine="Activity.AddView(fotoCerrar, Activity.Width - 50d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotocerrar.getObject()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
RDebugUtils.currentLine=11862047;
 //BA.debugLineNum = 11862047;BA.debugLine="fotoCambiar.Initialize(\"fotoCambiar\")";
mostCurrent._fotocambiar.Initialize(mostCurrent.activityBA,"fotoCambiar");
RDebugUtils.currentLine=11862048;
 //BA.debugLineNum = 11862048;BA.debugLine="fotoCambiar.Text = \"Cambiar foto\"";
mostCurrent._fotocambiar.setText(BA.ObjectToCharSequence("Cambiar foto"));
RDebugUtils.currentLine=11862049;
 //BA.debugLineNum = 11862049;BA.debugLine="fotoCambiar.Color = Colors.ARGB(100,255,255,255)";
mostCurrent._fotocambiar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)));
RDebugUtils.currentLine=11862050;
 //BA.debugLineNum = 11862050;BA.debugLine="Activity.AddView(fotoCambiar, 10dip, 10dip, 40%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotocambiar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
RDebugUtils.currentLine=11862053;
 //BA.debugLineNum = 11862053;BA.debugLine="btnContinuar.Visible = False";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=11862054;
 //BA.debugLineNum = 11862054;BA.debugLine="btnVerMiFoto.Visible = False";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=11862056;
 //BA.debugLineNum = 11862056;BA.debugLine="End Sub";
return "";
}
public static String  _btnvolver_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnvolver_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnvolver_click", null));}
RDebugUtils.currentLine=12386304;
 //BA.debugLineNum = 12386304;BA.debugLine="Sub btnVolver_Click";
RDebugUtils.currentLine=12386305;
 //BA.debugLineNum = 12386305;BA.debugLine="imgDistribucion.RemoveView";
mostCurrent._imgdistribucion.RemoveView();
RDebugUtils.currentLine=12386306;
 //BA.debugLineNum = 12386306;BA.debugLine="imgHabitat.RemoveView";
mostCurrent._imghabitat.RemoveView();
RDebugUtils.currentLine=12386307;
 //BA.debugLineNum = 12386307;BA.debugLine="lblNombre.RemoveView";
mostCurrent._lblnombre.RemoveView();
RDebugUtils.currentLine=12386308;
 //BA.debugLineNum = 12386308;BA.debugLine="lblDescripcionGeneral.RemoveView";
mostCurrent._lbldescripciongeneral.RemoveView();
RDebugUtils.currentLine=12386309;
 //BA.debugLineNum = 12386309;BA.debugLine="lblFondo.RemoveView";
mostCurrent._lblfondo.RemoveView();
RDebugUtils.currentLine=12386310;
 //BA.debugLineNum = 12386310;BA.debugLine="btnVolver.RemoveView";
mostCurrent._btnvolver.RemoveView();
RDebugUtils.currentLine=12386312;
 //BA.debugLineNum = 12386312;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=12386313;
 //BA.debugLineNum = 12386313;BA.debugLine="btnVerMiFoto.Visible = True";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=12386318;
 //BA.debugLineNum = 12386318;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fondogris_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fondogris_click", null));}
RDebugUtils.currentLine=12058624;
 //BA.debugLineNum = 12058624;BA.debugLine="Sub fondogris_Click";
RDebugUtils.currentLine=12058625;
 //BA.debugLineNum = 12058625;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
RDebugUtils.currentLine=12058626;
 //BA.debugLineNum = 12058626;BA.debugLine="fotoCerrar.RemoveView";
mostCurrent._fotocerrar.RemoveView();
RDebugUtils.currentLine=12058627;
 //BA.debugLineNum = 12058627;BA.debugLine="scroll2d.RemoveView";
mostCurrent._scroll2d.RemoveView();
RDebugUtils.currentLine=12058628;
 //BA.debugLineNum = 12058628;BA.debugLine="fotoCambiar.RemoveView";
mostCurrent._fotocambiar.RemoveView();
RDebugUtils.currentLine=12058629;
 //BA.debugLineNum = 12058629;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=12058630;
 //BA.debugLineNum = 12058630;BA.debugLine="btnVerMiFoto.Visible = True";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=12058631;
 //BA.debugLineNum = 12058631;BA.debugLine="End Sub";
return "";
}
public static String  _fotocambiar_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fotocambiar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fotocambiar_click", null));}
RDebugUtils.currentLine=11927552;
 //BA.debugLineNum = 11927552;BA.debugLine="Sub fotoCambiar_Click";
RDebugUtils.currentLine=11927553;
 //BA.debugLineNum = 11927553;BA.debugLine="If imgUsuario.Tag = \"1\" Then";
if ((mostCurrent._imgusuario.getTag()).equals((Object)("1"))) { 
RDebugUtils.currentLine=11927554;
 //BA.debugLineNum = 11927554;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg")) { 
RDebugUtils.currentLine=11927555;
 //BA.debugLineNum = 11927555;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11927556;
 //BA.debugLineNum = 11927556;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
RDebugUtils.currentLine=11927557;
 //BA.debugLineNum = 11927557;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=11927558;
 //BA.debugLineNum = 11927558;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=11927559;
 //BA.debugLineNum = 11927559;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=11927560;
 //BA.debugLineNum = 11927560;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
RDebugUtils.currentLine=11927561;
 //BA.debugLineNum = 11927561;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
RDebugUtils.currentLine=11927562;
 //BA.debugLineNum = 11927562;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11927563;
 //BA.debugLineNum = 11927563;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=11927564;
 //BA.debugLineNum = 11927564;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11927565;
 //BA.debugLineNum = 11927565;BA.debugLine="imgUsuario.Tag = \"2\"";
mostCurrent._imgusuario.setTag((Object)("2"));
 };
 };
 }else 
{RDebugUtils.currentLine=11927568;
 //BA.debugLineNum = 11927568;BA.debugLine="Else if imgUsuario.Tag = \"2\" Then";
if ((mostCurrent._imgusuario.getTag()).equals((Object)("2"))) { 
RDebugUtils.currentLine=11927569;
 //BA.debugLineNum = 11927569;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2+".jpg")) { 
RDebugUtils.currentLine=11927570;
 //BA.debugLineNum = 11927570;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11927571;
 //BA.debugLineNum = 11927571;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
RDebugUtils.currentLine=11927572;
 //BA.debugLineNum = 11927572;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=11927573;
 //BA.debugLineNum = 11927573;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=11927574;
 //BA.debugLineNum = 11927574;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=11927575;
 //BA.debugLineNum = 11927575;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
RDebugUtils.currentLine=11927576;
 //BA.debugLineNum = 11927576;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
RDebugUtils.currentLine=11927577;
 //BA.debugLineNum = 11927577;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11927578;
 //BA.debugLineNum = 11927578;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=11927579;
 //BA.debugLineNum = 11927579;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11927580;
 //BA.debugLineNum = 11927580;BA.debugLine="imgUsuario.Tag = \"3\"";
mostCurrent._imgusuario.setTag((Object)("3"));
 };
 }else {
RDebugUtils.currentLine=11927583;
 //BA.debugLineNum = 11927583;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
RDebugUtils.currentLine=11927584;
 //BA.debugLineNum = 11927584;BA.debugLine="fotoCambiar_Click";
_fotocambiar_click();
 };
 }else 
{RDebugUtils.currentLine=11927586;
 //BA.debugLineNum = 11927586;BA.debugLine="Else if imgUsuario.Tag = \"3\" Then";
if ((mostCurrent._imgusuario.getTag()).equals((Object)("3"))) { 
RDebugUtils.currentLine=11927587;
 //BA.debugLineNum = 11927587;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3+".jpg")) { 
RDebugUtils.currentLine=11927588;
 //BA.debugLineNum = 11927588;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11927589;
 //BA.debugLineNum = 11927589;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
RDebugUtils.currentLine=11927590;
 //BA.debugLineNum = 11927590;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=11927591;
 //BA.debugLineNum = 11927591;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=11927592;
 //BA.debugLineNum = 11927592;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=11927593;
 //BA.debugLineNum = 11927593;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
RDebugUtils.currentLine=11927594;
 //BA.debugLineNum = 11927594;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
RDebugUtils.currentLine=11927595;
 //BA.debugLineNum = 11927595;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11927596;
 //BA.debugLineNum = 11927596;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=11927597;
 //BA.debugLineNum = 11927597;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11927598;
 //BA.debugLineNum = 11927598;BA.debugLine="imgUsuario.Tag = \"4\"";
mostCurrent._imgusuario.setTag((Object)("4"));
 };
 }else {
RDebugUtils.currentLine=11927601;
 //BA.debugLineNum = 11927601;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
RDebugUtils.currentLine=11927602;
 //BA.debugLineNum = 11927602;BA.debugLine="fotoCambiar_Click";
_fotocambiar_click();
 };
 }else 
{RDebugUtils.currentLine=11927604;
 //BA.debugLineNum = 11927604;BA.debugLine="Else if imgUsuario.Tag = \"4\" Then";
if ((mostCurrent._imgusuario.getTag()).equals((Object)("4"))) { 
RDebugUtils.currentLine=11927605;
 //BA.debugLineNum = 11927605;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4+".jpg")) { 
RDebugUtils.currentLine=11927606;
 //BA.debugLineNum = 11927606;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11927607;
 //BA.debugLineNum = 11927607;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
RDebugUtils.currentLine=11927608;
 //BA.debugLineNum = 11927608;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=11927609;
 //BA.debugLineNum = 11927609;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=11927610;
 //BA.debugLineNum = 11927610;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
RDebugUtils.currentLine=11927611;
 //BA.debugLineNum = 11927611;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
RDebugUtils.currentLine=11927612;
 //BA.debugLineNum = 11927612;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
RDebugUtils.currentLine=11927613;
 //BA.debugLineNum = 11927613;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11927614;
 //BA.debugLineNum = 11927614;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=11927615;
 //BA.debugLineNum = 11927615;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
RDebugUtils.currentLine=11927616;
 //BA.debugLineNum = 11927616;BA.debugLine="imgUsuario.Tag = \"4\"";
mostCurrent._imgusuario.setTag((Object)("4"));
 };
 }else {
RDebugUtils.currentLine=11927619;
 //BA.debugLineNum = 11927619;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
RDebugUtils.currentLine=11927620;
 //BA.debugLineNum = 11927620;BA.debugLine="fotoCambiar_Click";
_fotocambiar_click();
 };
 }}}}
;
RDebugUtils.currentLine=11927625;
 //BA.debugLineNum = 11927625;BA.debugLine="End Sub";
return "";
}
public static String  _fotocerrar_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fotocerrar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fotocerrar_click", null));}
RDebugUtils.currentLine=11993088;
 //BA.debugLineNum = 11993088;BA.debugLine="Sub fotoCerrar_Click";
RDebugUtils.currentLine=11993089;
 //BA.debugLineNum = 11993089;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
RDebugUtils.currentLine=11993090;
 //BA.debugLineNum = 11993090;BA.debugLine="fotoCerrar.RemoveView";
mostCurrent._fotocerrar.RemoveView();
RDebugUtils.currentLine=11993091;
 //BA.debugLineNum = 11993091;BA.debugLine="scroll2d.RemoveView";
mostCurrent._scroll2d.RemoveView();
RDebugUtils.currentLine=11993092;
 //BA.debugLineNum = 11993092;BA.debugLine="fotoCambiar.RemoveView";
mostCurrent._fotocambiar.RemoveView();
RDebugUtils.currentLine=11993093;
 //BA.debugLineNum = 11993093;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11993094;
 //BA.debugLineNum = 11993094;BA.debugLine="btnVerMiFoto.Visible = True";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11993096;
 //BA.debugLineNum = 11993096;BA.debugLine="End Sub";
return "";
}
public static String  _imgopcion1_1_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgopcion1_1_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgopcion1_1_click", null));}
RDebugUtils.currentLine=11599872;
 //BA.debugLineNum = 11599872;BA.debugLine="Sub imgOpcion1_1_Click";
RDebugUtils.currentLine=11599873;
 //BA.debugLineNum = 11599873;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11599874;
 //BA.debugLineNum = 11599874;BA.debugLine="lblRojo.Left = imgOpcion1_1.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgopcion1_1.getLeft());
RDebugUtils.currentLine=11599875;
 //BA.debugLineNum = 11599875;BA.debugLine="lblRojo.Top = imgOpcion1_1.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgopcion1_1.getTop());
RDebugUtils.currentLine=11599876;
 //BA.debugLineNum = 11599876;BA.debugLine="opcionElegida = 1";
_opcionelegida = (int) (1);
RDebugUtils.currentLine=11599877;
 //BA.debugLineNum = 11599877;BA.debugLine="End Sub";
return "";
}
public static String  _imgopcion2_1_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgopcion2_1_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgopcion2_1_click", null));}
RDebugUtils.currentLine=11665408;
 //BA.debugLineNum = 11665408;BA.debugLine="Sub imgOpcion2_1_Click";
RDebugUtils.currentLine=11665409;
 //BA.debugLineNum = 11665409;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11665410;
 //BA.debugLineNum = 11665410;BA.debugLine="lblRojo.Left = imgOpcion2_1.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgopcion2_1.getLeft());
RDebugUtils.currentLine=11665411;
 //BA.debugLineNum = 11665411;BA.debugLine="lblRojo.Top = imgOpcion2_1.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgopcion2_1.getTop());
RDebugUtils.currentLine=11665412;
 //BA.debugLineNum = 11665412;BA.debugLine="opcionElegida = 2";
_opcionelegida = (int) (2);
RDebugUtils.currentLine=11665413;
 //BA.debugLineNum = 11665413;BA.debugLine="End Sub";
return "";
}
public static String  _imgopcion3_1_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgopcion3_1_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgopcion3_1_click", null));}
RDebugUtils.currentLine=11730944;
 //BA.debugLineNum = 11730944;BA.debugLine="Sub imgOpcion3_1_Click";
RDebugUtils.currentLine=11730945;
 //BA.debugLineNum = 11730945;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11730946;
 //BA.debugLineNum = 11730946;BA.debugLine="lblRojo.Left = imgOpcion3_1.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgopcion3_1.getLeft());
RDebugUtils.currentLine=11730947;
 //BA.debugLineNum = 11730947;BA.debugLine="lblRojo.Top = imgOpcion3_1.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgopcion3_1.getTop());
RDebugUtils.currentLine=11730948;
 //BA.debugLineNum = 11730948;BA.debugLine="opcionElegida = 3";
_opcionelegida = (int) (3);
RDebugUtils.currentLine=11730949;
 //BA.debugLineNum = 11730949;BA.debugLine="End Sub";
return "";
}
public static String  _imgopcion4_1_click() throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgopcion4_1_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgopcion4_1_click", null));}
RDebugUtils.currentLine=11796480;
 //BA.debugLineNum = 11796480;BA.debugLine="Sub imgOpcion4_1_Click";
RDebugUtils.currentLine=11796481;
 //BA.debugLineNum = 11796481;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=11796482;
 //BA.debugLineNum = 11796482;BA.debugLine="lblRojo.Left = imgOpcion4_1.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgopcion4_1.getLeft());
RDebugUtils.currentLine=11796483;
 //BA.debugLineNum = 11796483;BA.debugLine="lblRojo.Top = imgOpcion4_1.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgopcion4_1.getTop());
RDebugUtils.currentLine=11796484;
 //BA.debugLineNum = 11796484;BA.debugLine="opcionElegida = 4";
_opcionelegida = (int) (4);
RDebugUtils.currentLine=11796485;
 //BA.debugLineNum = 11796485;BA.debugLine="End Sub";
return "";
}
public static String  _pnl_gesture_onpinchclose(float _newdistance,float _previousdistance,Object _motionevent) throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "pnl_gesture_onpinchclose"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "pnl_gesture_onpinchclose", new Object[] {_newdistance,_previousdistance,_motionevent}));}
RDebugUtils.currentLine=11403264;
 //BA.debugLineNum = 11403264;BA.debugLine="Sub pnl_gesture_onPinchClose(NewDistance As Float,";
RDebugUtils.currentLine=11403265;
 //BA.debugLineNum = 11403265;BA.debugLine="zoom = zoom *(NewDistance/PreviousDistance)";
_zoom = (float) (_zoom*(_newdistance/(double)_previousdistance));
RDebugUtils.currentLine=11403266;
 //BA.debugLineNum = 11403266;BA.debugLine="imgUsuario.Width = bmp.Width*zoom";
mostCurrent._imgusuario.setWidth((int) (mostCurrent._bmp.getWidth()*_zoom));
RDebugUtils.currentLine=11403267;
 //BA.debugLineNum = 11403267;BA.debugLine="imgUsuario.Height = bmp.Height*zoom";
mostCurrent._imgusuario.setHeight((int) (mostCurrent._bmp.getHeight()*_zoom));
RDebugUtils.currentLine=11403268;
 //BA.debugLineNum = 11403268;BA.debugLine="imgUsuario.Top = (scroll2d.Panel.Height-imgUsuari";
mostCurrent._imgusuario.setTop((int) ((mostCurrent._scroll2d.getPanel().getHeight()-mostCurrent._imgusuario.getHeight())/(double)3));
RDebugUtils.currentLine=11403269;
 //BA.debugLineNum = 11403269;BA.debugLine="imgUsuario.left = (scroll2d.Panel.Width - imgUsua";
mostCurrent._imgusuario.setLeft((int) ((mostCurrent._scroll2d.getPanel().getWidth()-mostCurrent._imgusuario.getWidth())/(double)3));
RDebugUtils.currentLine=11403272;
 //BA.debugLineNum = 11403272;BA.debugLine="End Sub";
return "";
}
public static String  _pnl_gesture_onpinchopen(float _newdistance,float _previousdistance,Object _motionevent) throws Exception{
RDebugUtils.currentModule="frmidentificacionnew";
if (Debug.shouldDelegate(mostCurrent.activityBA, "pnl_gesture_onpinchopen"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "pnl_gesture_onpinchopen", new Object[] {_newdistance,_previousdistance,_motionevent}));}
RDebugUtils.currentLine=11337728;
 //BA.debugLineNum = 11337728;BA.debugLine="Sub pnl_gesture_onPinchOpen(NewDistance As Float,";
RDebugUtils.currentLine=11337729;
 //BA.debugLineNum = 11337729;BA.debugLine="zoom = zoom * (NewDistance/PreviousDistance)";
_zoom = (float) (_zoom*(_newdistance/(double)_previousdistance));
RDebugUtils.currentLine=11337730;
 //BA.debugLineNum = 11337730;BA.debugLine="imgUsuario.Width = bmp.Width*zoom";
mostCurrent._imgusuario.setWidth((int) (mostCurrent._bmp.getWidth()*_zoom));
RDebugUtils.currentLine=11337731;
 //BA.debugLineNum = 11337731;BA.debugLine="imgUsuario.Height = bmp.Height*zoom";
mostCurrent._imgusuario.setHeight((int) (mostCurrent._bmp.getHeight()*_zoom));
RDebugUtils.currentLine=11337732;
 //BA.debugLineNum = 11337732;BA.debugLine="imgUsuario.Top = (scroll2d.Panel.Height-imgUsuari";
mostCurrent._imgusuario.setTop((int) ((mostCurrent._scroll2d.getPanel().getHeight()-mostCurrent._imgusuario.getHeight())/(double)3));
RDebugUtils.currentLine=11337733;
 //BA.debugLineNum = 11337733;BA.debugLine="imgUsuario.left = (scroll2d.Panel.Width - imgUsua";
mostCurrent._imgusuario.setLeft((int) ((mostCurrent._scroll2d.getPanel().getWidth()-mostCurrent._imgusuario.getWidth())/(double)3));
RDebugUtils.currentLine=11337736;
 //BA.debugLineNum = 11337736;BA.debugLine="End Sub";
return "";
}
}