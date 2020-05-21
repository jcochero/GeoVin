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
			processBA = new BA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmidentificacionnew");
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
public cepave.geovin.frmlocalizacion _frmlocalizacion = null;
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
 //BA.debugLineNum = 82;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 84;BA.debugLine="imagenUsuario1 = Main.fotopath0";
mostCurrent._imagenusuario1 = mostCurrent._main._fotopath0 /*String*/ ;
 //BA.debugLineNum = 85;BA.debugLine="imagenUsuario2 = Main.fotopath1";
mostCurrent._imagenusuario2 = mostCurrent._main._fotopath1 /*String*/ ;
 //BA.debugLineNum = 86;BA.debugLine="If currentPregunta = 1 Then";
if (_currentpregunta==1) { 
 //BA.debugLineNum = 87;BA.debugLine="Activity.LoadLayout(\"layIdentificacionNew_1\")";
mostCurrent._activity.LoadLayout("layIdentificacionNew_1",mostCurrent.activityBA);
 //BA.debugLineNum = 88;BA.debugLine="cargarOpcion1";
_cargaropcion1();
 //BA.debugLineNum = 89;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 }else if(_currentpregunta==2) { 
 };
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 96;BA.debugLine="If currentPregunta = 2 Then";
if (_currentpregunta==2) { 
 //BA.debugLineNum = 97;BA.debugLine="If Activity.Width < Activity.Height Then";
if (mostCurrent._activity.getWidth()<mostCurrent._activity.getHeight()) { 
 //BA.debugLineNum = 98;BA.debugLine="p.SetScreenOrientation(0)";
_p.SetScreenOrientation(processBA,(int) (0));
 }else {
 //BA.debugLineNum = 100;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 101;BA.debugLine="Activity.LoadLayout(\"layIdentificacionNew_21\")";
mostCurrent._activity.LoadLayout("layIdentificacionNew_21",mostCurrent.activityBA);
 //BA.debugLineNum = 102;BA.debugLine="cargarOpcion2";
_cargaropcion2();
 };
 }else if(_currentpregunta==1) { 
 //BA.debugLineNum = 105;BA.debugLine="cargarOpcion1";
_cargaropcion1();
 //BA.debugLineNum = 106;BA.debugLine="TranslateGUI";
_translategui();
 };
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _btncambiarfoto_click() throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Sub btnCambiarFoto_Click";
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuar_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _wherefields = null;
String _resp1 = "";
String _msg = "";
String _resp2 = "";
 //BA.debugLineNum = 453;BA.debugLine="Sub btnContinuar_Click";
 //BA.debugLineNum = 456;BA.debugLine="If currentPregunta = 1 Then";
if (_currentpregunta==1) { 
 //BA.debugLineNum = 460;BA.debugLine="Dim WhereFields As Map";
_wherefields = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 461;BA.debugLine="WhereFields.Initialize";
_wherefields.Initialize();
 //BA.debugLineNum = 462;BA.debugLine="WhereFields.Put(\"id\", Main.currentproject)";
_wherefields.Put((Object)("id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 464;BA.debugLine="Dim resp1 As String = opcionElegida";
_resp1 = BA.NumberToString(_opcionelegida);
 //BA.debugLineNum = 465;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_wherefields);
 //BA.debugLineNum = 466;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","par1",(Object)(_resp1),_wherefields);
 //BA.debugLineNum = 467;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_loc";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","terminado",(Object)("si"),_wherefields);
 //BA.debugLineNum = 470;BA.debugLine="If opcionElegida = 1 Then";
if (_opcionelegida==1) { 
 //BA.debugLineNum = 471;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 472;BA.debugLine="utilidades.Mensaje(\"No es una vinchuca\", \"MsgN";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"No es una vinchuca","MsgNoVinchuca.png","Usted podría haber encontrado un insecto que se alimenta de plantas. Un profesional verificará sus fotos pero si su observación fue correcta  el insecto que encontró no es una vinchuca","Para mayor información ingrese a 'Información sobre Vinchucas'","Continuar","","",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 475;BA.debugLine="utilidades.Mensaje(\"Not a kissing bug\", \"MsgNo";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Not a kissing bug","MsgNoVinchuca.png","The insect you found feeds off plants. A specialized reviwer will check the photos but if your observation was correct, the insect is NOT a kissing bug!","For more information check the 'Explore' panel","Continue","","",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 478;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 479;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if(_opcionelegida==2) { 
 //BA.debugLineNum = 481;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 482;BA.debugLine="utilidades.Mensaje(\"No es una vinchuca\", \"MsgN";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"No es una vinchuca","MsgNoVinchuca.png","Usted podría haber encontrado un insecto que se alimenta de plantas. Un profesional verificará sus fotos pero si su observación fue correcta  el insecto que encontró no es una vinchuca","Para mayor información ingrese a 'Información sobre Vinchucas'","Continuar","","",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 484;BA.debugLine="utilidades.Mensaje(\"Not a kissing bug\", \"MsgNo";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Not a kissing bug","MsgNoVinchuca.png","The insect you found feeds off plants. A specialized reviwer will check the photos but if your observation was correct, the insect is NOT a kissing bug!","For more information check the 'Explore' panel","Continue","","",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 486;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 487;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if(_opcionelegida==3) { 
 //BA.debugLineNum = 489;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 490;BA.debugLine="utilidades.Mensaje(\"No es una vinchuca\", \"MsgN";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"No es una vinchuca","MsgNoVinchuca.png","Usted podría haber encontrado un insecto que se alimenta de otros insectos. Un profesional verificará sus fotos pero si su observación fue correcta  el insecto que encontró no es una vinchuca","Para mayor información lea 'Información sobre Vinchucas'","Continuar","","",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 492;BA.debugLine="utilidades.Mensaje(\"Not a kissing bug\", \"MsgNo";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Not a kissing bug","MsgNoVinchuca.png","The insect you found eats other insects. A specialized reviwer will check the photos but if your observation was correct, the insect is NOT a kissing bug!","For more information check the 'Explore' panel","Continue","","",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 494;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 495;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if(_opcionelegida==4) { 
 //BA.debugLineNum = 497;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 498;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 499;BA.debugLine="msg = utilidades.Mensaje(\"Cuidado!\", \"MsgVinch";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Cuidado!","MsgVinchuca.png","Usted podría haber encontrado una VINCHUCA. Un profesional verificará sus fotos y le responderá si es realmente una vinchuca","Mientras tanto, te animas a tratar determinar que especie es?","Si, quiero continuar","","No",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 501;BA.debugLine="msg = utilidades.Mensaje(\"Careful!\", \"MsgVinch";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Careful!","MsgVinchuca.png","You could have found a KISSING BUG. A specialized reviewer will check your photos and will contact you if it is really a kissing bug","Meanwhile, do you want to try to determine which species of kissing bug is it?","Yes, I want to try","","No",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 503;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 504;BA.debugLine="currentPregunta = 2";
_currentpregunta = (int) (2);
 //BA.debugLineNum = 505;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 506;BA.debugLine="Activity_Resume";
_activity_resume();
 };
 };
 }else if(_currentpregunta==2) { 
 //BA.debugLineNum = 511;BA.debugLine="btnContinuar.Visible = False";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 512;BA.debugLine="btnVerMiFoto.Visible = False";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 515;BA.debugLine="opcionElegida = Pager.CurrentPage + 1";
_opcionelegida = (int) (mostCurrent._pager.getCurrentPage()+1);
 //BA.debugLineNum = 518;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 519;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 520;BA.debugLine="msg = Msgbox2(\"¿Quieres confirmar esta identifi";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Quieres confirmar esta identificación? Si aceptas, un especialista verificará el dato y te notificará"),BA.ObjectToCharSequence("Confirmar"),"Si, creo que es esta","","No, quiero ver otras",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"MsgVinchuca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA)).getObject()),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 523;BA.debugLine="msg = Msgbox2(\"Do you think this is the one? A";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Do you think this is the one? A reviewer will check and contact you"),BA.ObjectToCharSequence("Confirm"),"Yes, I think this is the one","","No, I want to browse some more",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"MsgVinchuca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA)).getObject()),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 526;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 528;BA.debugLine="Dim WhereFields As Map";
_wherefields = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 529;BA.debugLine="WhereFields.Initialize";
_wherefields.Initialize();
 //BA.debugLineNum = 530;BA.debugLine="WhereFields.Put(\"id\", Main.currentproject)";
_wherefields.Put((Object)("id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 532;BA.debugLine="Dim resp2 As String = opcionElegida";
_resp2 = BA.NumberToString(_opcionelegida);
 //BA.debugLineNum = 533;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_wherefields);
 //BA.debugLineNum = 534;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","par2",(Object)(_resp2),_wherefields);
 //BA.debugLineNum = 535;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLdb, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","terminado",(Object)("si"),_wherefields);
 //BA.debugLineNum = 537;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 538;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 539;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmprincipal.getObject()));
 }else {
 //BA.debugLineNum = 541;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 542;BA.debugLine="btnVerMiFoto.Visible = True";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 546;BA.debugLine="End Sub";
return "";
}
public static String  _btnenviar_click() throws Exception{
 //BA.debugLineNum = 557;BA.debugLine="Sub btnEnviar_Click";
 //BA.debugLineNum = 558;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 559;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 560;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasinfo_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _speciesmap = null;
 //BA.debugLineNum = 419;BA.debugLine="Sub btnMasInfo_Click";
 //BA.debugLineNum = 420;BA.debugLine="btnContinuar.Visible = False";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 421;BA.debugLine="btnVerMiFoto.Visible = False";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 422;BA.debugLine="opcionElegida = Pager.CurrentPage +1";
_opcionelegida = (int) (mostCurrent._pager.getCurrentPage()+1);
 //BA.debugLineNum = 424;BA.debugLine="Dim speciesMap As Map";
_speciesmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 425;BA.debugLine="speciesMap.Initialize";
_speciesmap.Initialize();
 //BA.debugLineNum = 426;BA.debugLine="speciesMap = DBUtils.ExecuteMap(Starter.speciesDB";
_speciesmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._speciesdb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM especies WHERE Id=?",new String[]{BA.NumberToString(_opcionelegida+1)});
 //BA.debugLineNum = 427;BA.debugLine="If speciesMap = Null Or speciesMap.IsInitialized";
if (_speciesmap== null || _speciesmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 428;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 430;BA.debugLine="Activity.LoadLayout(\"layCaracteristicasSp1\")";
mostCurrent._activity.LoadLayout("layCaracteristicasSp1",mostCurrent.activityBA);
 //BA.debugLineNum = 431;BA.debugLine="lblNombre.Text = speciesMap.Get(\"especie\")";
mostCurrent._lblnombre.setText(BA.ObjectToCharSequence(_speciesmap.Get((Object)("especie"))));
 //BA.debugLineNum = 432;BA.debugLine="lblDescripcionGeneral.Text = speciesMap.Get(\"gen";
mostCurrent._lbldescripciongeneral.setText(BA.ObjectToCharSequence(_speciesmap.Get((Object)("general"))));
 //BA.debugLineNum = 434;BA.debugLine="imgHabitat.Bitmap = LoadBitmapSample(File.DirAss";
mostCurrent._imghabitat.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_speciesmap.Get((Object)("habitat"))),mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
 //BA.debugLineNum = 435;BA.debugLine="imgDistribucion.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgdistribucion.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_speciesmap.Get((Object)("distribucion"))),mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
 //BA.debugLineNum = 436;BA.debugLine="btnVolver.Visible = True";
mostCurrent._btnvolver.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 442;BA.debugLine="End Sub";
return "";
}
public static String  _btnvermifoto_click() throws Exception{
 //BA.debugLineNum = 228;BA.debugLine="Sub btnVerMiFoto_Click";
 //BA.debugLineNum = 230;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 231;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 232;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 235;BA.debugLine="scroll2d.Initialize(1000,1000,\"scroll2d\")";
mostCurrent._scroll2d.Initialize(mostCurrent.activityBA,(int) (1000),(int) (1000),"scroll2d");
 //BA.debugLineNum = 236;BA.debugLine="scroll2d.Color = Colors.Black";
mostCurrent._scroll2d.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 237;BA.debugLine="Activity.AddView(scroll2d, 10%x, 10%y, 80%x, 80%y";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scroll2d.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 239;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/Geo";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 240;BA.debugLine="imgUsuario.Initialize(\"\")";
mostCurrent._imgusuario.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 241;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
 //BA.debugLineNum = 242;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
 //BA.debugLineNum = 243;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 245;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.Widt";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 246;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 247;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 249;BA.debugLine="GD.SetOnGestureListener(scroll2d,\"pnl_gesture\")";
mostCurrent._gd.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._scroll2d.getObject()),"pnl_gesture");
 //BA.debugLineNum = 250;BA.debugLine="GD.EnableLongPress(False)";
mostCurrent._gd.EnableLongPress(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 253;BA.debugLine="fotoCerrar.Initialize(\"fotoCerrar\")";
mostCurrent._fotocerrar.Initialize(mostCurrent.activityBA,"fotoCerrar");
 //BA.debugLineNum = 254;BA.debugLine="fotoCerrar.Text = \"X\"";
mostCurrent._fotocerrar.setText(BA.ObjectToCharSequence("X"));
 //BA.debugLineNum = 255;BA.debugLine="fotoCerrar.Color = Colors.ARGB(100,255,255,255)";
mostCurrent._fotocerrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 256;BA.debugLine="Activity.AddView(fotoCerrar, Activity.Width - 50d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotocerrar.getObject()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 259;BA.debugLine="fotoCambiar.Initialize(\"fotoCambiar\")";
mostCurrent._fotocambiar.Initialize(mostCurrent.activityBA,"fotoCambiar");
 //BA.debugLineNum = 260;BA.debugLine="fotoCambiar.Text = \"Cambiar foto\"";
mostCurrent._fotocambiar.setText(BA.ObjectToCharSequence("Cambiar foto"));
 //BA.debugLineNum = 261;BA.debugLine="fotoCambiar.Color = Colors.ARGB(100,255,255,255)";
mostCurrent._fotocambiar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 262;BA.debugLine="Activity.AddView(fotoCambiar, 10dip, 10dip, 40%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotocambiar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 265;BA.debugLine="btnContinuar.Visible = False";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 266;BA.debugLine="btnVerMiFoto.Visible = False";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 268;BA.debugLine="End Sub";
return "";
}
public static String  _btnvolver_click() throws Exception{
 //BA.debugLineNum = 562;BA.debugLine="Sub btnVolver_Click";
 //BA.debugLineNum = 563;BA.debugLine="imgDistribucion.RemoveView";
mostCurrent._imgdistribucion.RemoveView();
 //BA.debugLineNum = 564;BA.debugLine="imgHabitat.RemoveView";
mostCurrent._imghabitat.RemoveView();
 //BA.debugLineNum = 565;BA.debugLine="lblNombre.RemoveView";
mostCurrent._lblnombre.RemoveView();
 //BA.debugLineNum = 566;BA.debugLine="lblDescripcionGeneral.RemoveView";
mostCurrent._lbldescripciongeneral.RemoveView();
 //BA.debugLineNum = 567;BA.debugLine="lblFondo.RemoveView";
mostCurrent._lblfondo.RemoveView();
 //BA.debugLineNum = 568;BA.debugLine="btnVolver.RemoveView";
mostCurrent._btnvolver.RemoveView();
 //BA.debugLineNum = 570;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 571;BA.debugLine="btnVerMiFoto.Visible = True";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 576;BA.debugLine="End Sub";
return "";
}
public static String  _cargaropcion1() throws Exception{
 //BA.debugLineNum = 184;BA.debugLine="Sub cargarOpcion1";
 //BA.debugLineNum = 185;BA.debugLine="imgOpcion1_1.Width = 48%x";
mostCurrent._imgopcion1_1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (48),mostCurrent.activityBA));
 //BA.debugLineNum = 186;BA.debugLine="imgOpcion2_1.Width = 48%x";
mostCurrent._imgopcion2_1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (48),mostCurrent.activityBA));
 //BA.debugLineNum = 187;BA.debugLine="imgOpcion3_1.Width = 48%x";
mostCurrent._imgopcion3_1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (48),mostCurrent.activityBA));
 //BA.debugLineNum = 188;BA.debugLine="imgOpcion4_1.Width = 48%x";
mostCurrent._imgopcion4_1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (48),mostCurrent.activityBA));
 //BA.debugLineNum = 189;BA.debugLine="imgOpcion1_1.Left = 2%x";
mostCurrent._imgopcion1_1.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA));
 //BA.debugLineNum = 190;BA.debugLine="imgOpcion2_1.Left = 52%x";
mostCurrent._imgopcion2_1.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (52),mostCurrent.activityBA));
 //BA.debugLineNum = 191;BA.debugLine="imgOpcion3_1.Left = 2%x";
mostCurrent._imgopcion3_1.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA));
 //BA.debugLineNum = 192;BA.debugLine="imgOpcion4_1.Left = 52%x";
mostCurrent._imgopcion4_1.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (52),mostCurrent.activityBA));
 //BA.debugLineNum = 193;BA.debugLine="imgOpcion3_1.Top = 50%y";
mostCurrent._imgopcion3_1.setTop(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 194;BA.debugLine="imgOpcion4_1.Top = 50%y";
mostCurrent._imgopcion4_1.setTop(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 195;BA.debugLine="textOpcion3.Top = imgOpcion3_1.Top + imgOpcion3_1";
mostCurrent._textopcion3.setTop((int) (mostCurrent._imgopcion3_1.getTop()+mostCurrent._imgopcion3_1.getHeight()));
 //BA.debugLineNum = 196;BA.debugLine="textOpcion4.Top = imgOpcion4_1.Top + imgOpcion4_1";
mostCurrent._textopcion4.setTop((int) (mostCurrent._imgopcion4_1.getTop()+mostCurrent._imgopcion4_1.getHeight()));
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _cargaropcion2() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper[] _pan = null;
int _i = 0;
String[] _currentsp = null;
 //BA.debugLineNum = 373;BA.debugLine="Sub cargarOpcion2";
 //BA.debugLineNum = 375;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 376;BA.debugLine="lblInstrucciones.Text = \"¿A cuál de estas vinchu";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("¿A cuál de estas vinchucas se parece más la que encontraste?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 378;BA.debugLine="lblInstrucciones.Text = \"Which of these resemble";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("Which of these resembles more the kissing bug you found?"));
 };
 //BA.debugLineNum = 381;BA.debugLine="If Main.speciesMap = Null Or Main.speciesMap.IsIn";
if (mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ == null || mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ .IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 382;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 384;BA.debugLine="Dim Pan(Main.speciesMap.Size) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ .getSize()];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 386;BA.debugLine="Container.Initialize";
mostCurrent._container.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 387;BA.debugLine="For i = 0 To Pan.Length - 2";
{
final int step11 = 1;
final int limit11 = (int) (_pan.length-2);
_i = (int) (0) ;
for (;_i <= limit11 ;_i = _i + step11 ) {
 //BA.debugLineNum = 388;BA.debugLine="Pan(i).Initialize(\"Pan\" & i)";
_pan[_i].Initialize(mostCurrent.activityBA,"Pan"+BA.NumberToString(_i));
 //BA.debugLineNum = 389;BA.debugLine="Pan(i).LoadLayout(\"layIdentificacionNew_21_Foto";
_pan[_i].LoadLayout("layIdentificacionNew_21_Fotos",mostCurrent.activityBA);
 //BA.debugLineNum = 390;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 391;BA.debugLine="Label1.Text = \"<< Desliza para ver otras espec";
mostCurrent._label1.setText(BA.ObjectToCharSequence("<< Desliza para ver otras especies >>"));
 }else {
 //BA.debugLineNum = 393;BA.debugLine="Label1.Text = \"<< Slide to see other species >";
mostCurrent._label1.setText(BA.ObjectToCharSequence("<< Slide to see other species >>"));
 };
 //BA.debugLineNum = 396;BA.debugLine="Dim currentsp() As String";
_currentsp = new String[(int) (0)];
java.util.Arrays.fill(_currentsp,"");
 //BA.debugLineNum = 397;BA.debugLine="currentsp = Main.speciesMap.Get(i+1)";
_currentsp = (String[])(mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ .Get((int) (_i+1)));
 //BA.debugLineNum = 399;BA.debugLine="If currentsp(5) <> Null And currentsp(5) <> \"\"";
if (_currentsp[(int) (5)]!= null && (_currentsp[(int) (5)]).equals("") == false) { 
 //BA.debugLineNum = 400;BA.debugLine="imgVinchuca11.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgvinchuca11.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_currentsp[(int) (5)],mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
 //BA.debugLineNum = 401;BA.debugLine="imgVinchuca11.Height = 60%y";
mostCurrent._imgvinchuca11.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (60),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 407;BA.debugLine="Container.AddPageAt(Pan(i), \"layIdentificacionN";
mostCurrent._container.AddPageAt((android.view.View)(_pan[_i].getObject()),"layIdentificacionNew_21_Fotos",_i);
 }
};
 //BA.debugLineNum = 410;BA.debugLine="Pager.Initialize2(Container, \"Pager\")";
mostCurrent._pager.Initialize2(mostCurrent.activityBA,mostCurrent._container,"Pager");
 //BA.debugLineNum = 411;BA.debugLine="Activity.AddView(Pager, 10%x, 10%y, Activity.Wid";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._pager.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),mostCurrent._activity.getWidth(),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (65),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 416;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 355;BA.debugLine="Sub fondogris_Click";
 //BA.debugLineNum = 356;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 357;BA.debugLine="fotoCerrar.RemoveView";
mostCurrent._fotocerrar.RemoveView();
 //BA.debugLineNum = 358;BA.debugLine="scroll2d.RemoveView";
mostCurrent._scroll2d.RemoveView();
 //BA.debugLineNum = 359;BA.debugLine="fotoCambiar.RemoveView";
mostCurrent._fotocambiar.RemoveView();
 //BA.debugLineNum = 360;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 361;BA.debugLine="btnVerMiFoto.Visible = True";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 362;BA.debugLine="End Sub";
return "";
}
public static String  _fotocambiar_click() throws Exception{
 //BA.debugLineNum = 270;BA.debugLine="Sub fotoCambiar_Click";
 //BA.debugLineNum = 271;BA.debugLine="If imgUsuario.Tag = \"1\" Then";
if ((mostCurrent._imgusuario.getTag()).equals((Object)("1"))) { 
 //BA.debugLineNum = 272;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg")) { 
 //BA.debugLineNum = 273;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 274;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
 //BA.debugLineNum = 275;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 276;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 277;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
 //BA.debugLineNum = 278;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
 //BA.debugLineNum = 279;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
 //BA.debugLineNum = 280;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 281;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 282;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 283;BA.debugLine="imgUsuario.Tag = \"2\"";
mostCurrent._imgusuario.setTag((Object)("2"));
 };
 };
 }else if((mostCurrent._imgusuario.getTag()).equals((Object)("2"))) { 
 //BA.debugLineNum = 287;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2+".jpg")) { 
 //BA.debugLineNum = 288;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 289;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
 //BA.debugLineNum = 290;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 291;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 292;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
 //BA.debugLineNum = 293;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
 //BA.debugLineNum = 294;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
 //BA.debugLineNum = 295;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 296;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 297;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 298;BA.debugLine="imgUsuario.Tag = \"3\"";
mostCurrent._imgusuario.setTag((Object)("3"));
 };
 }else {
 //BA.debugLineNum = 301;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
 //BA.debugLineNum = 302;BA.debugLine="fotoCambiar_Click";
_fotocambiar_click();
 };
 }else if((mostCurrent._imgusuario.getTag()).equals((Object)("3"))) { 
 //BA.debugLineNum = 305;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3+".jpg")) { 
 //BA.debugLineNum = 306;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 307;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
 //BA.debugLineNum = 308;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 309;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 310;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
 //BA.debugLineNum = 311;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
 //BA.debugLineNum = 312;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
 //BA.debugLineNum = 313;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 314;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 315;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 316;BA.debugLine="imgUsuario.Tag = \"4\"";
mostCurrent._imgusuario.setTag((Object)("4"));
 };
 }else {
 //BA.debugLineNum = 319;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
 //BA.debugLineNum = 320;BA.debugLine="fotoCambiar_Click";
_fotocambiar_click();
 };
 }else if((mostCurrent._imgusuario.getTag()).equals((Object)("4"))) { 
 //BA.debugLineNum = 323;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4+".jpg")) { 
 //BA.debugLineNum = 324;BA.debugLine="bmp.InitializeResize(File.DirRootExternal & \"/G";
mostCurrent._bmp.InitializeResize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 325;BA.debugLine="If imgUsuario.IsInitialized Then";
if (mostCurrent._imgusuario.IsInitialized()) { 
 //BA.debugLineNum = 326;BA.debugLine="imgUsuario.Bitmap = Null";
mostCurrent._imgusuario.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 327;BA.debugLine="imgUsuario.Gravity = Gravity.FILL";
mostCurrent._imgusuario.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 328;BA.debugLine="imgUsuario.SetBackgroundImage(bmp)";
mostCurrent._imgusuario.SetBackgroundImageNew((android.graphics.Bitmap)(mostCurrent._bmp.getObject()));
 //BA.debugLineNum = 329;BA.debugLine="scroll2d.Panel.RemoveAllViews";
mostCurrent._scroll2d.getPanel().RemoveAllViews();
 //BA.debugLineNum = 330;BA.debugLine="scroll2d.panel.Invalidate";
mostCurrent._scroll2d.getPanel().Invalidate();
 //BA.debugLineNum = 331;BA.debugLine="scroll2d.panel.AddView(imgUsuario, (Activity.W";
mostCurrent._scroll2d.getPanel().AddView((android.view.View)(mostCurrent._imgusuario.getObject()),(int) ((mostCurrent._activity.getWidth()/(double)2-mostCurrent._bmp.getWidth()/(double)2)),(int) (0),mostCurrent._bmp.getWidth(),mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 332;BA.debugLine="scroll2d.Panel.Width = Activity.Width";
mostCurrent._scroll2d.getPanel().setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 333;BA.debugLine="scroll2d.Panel.Height = bmp.Height";
mostCurrent._scroll2d.getPanel().setHeight(mostCurrent._bmp.getHeight());
 //BA.debugLineNum = 334;BA.debugLine="imgUsuario.Tag = \"4\"";
mostCurrent._imgusuario.setTag((Object)("4"));
 };
 }else {
 //BA.debugLineNum = 337;BA.debugLine="imgUsuario.Tag = \"1\"";
mostCurrent._imgusuario.setTag((Object)("1"));
 //BA.debugLineNum = 338;BA.debugLine="fotoCambiar_Click";
_fotocambiar_click();
 };
 };
 //BA.debugLineNum = 343;BA.debugLine="End Sub";
return "";
}
public static String  _fotocerrar_click() throws Exception{
 //BA.debugLineNum = 345;BA.debugLine="Sub fotoCerrar_Click";
 //BA.debugLineNum = 346;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 347;BA.debugLine="fotoCerrar.RemoveView";
mostCurrent._fotocerrar.RemoveView();
 //BA.debugLineNum = 348;BA.debugLine="scroll2d.RemoveView";
mostCurrent._scroll2d.RemoveView();
 //BA.debugLineNum = 349;BA.debugLine="fotoCambiar.RemoveView";
mostCurrent._fotocambiar.RemoveView();
 //BA.debugLineNum = 350;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 351;BA.debugLine="btnVerMiFoto.Visible = True";
mostCurrent._btnvermifoto.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 353;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 21;BA.debugLine="Dim scroll2d As ScrollView2D";
mostCurrent._scroll2d = new flm.b4a.scrollview2d.ScrollView2DWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private scrollOptions As HorizontalScrollView";
mostCurrent._scrolloptions = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim pregunta1 As String";
mostCurrent._pregunta1 = "";
 //BA.debugLineNum = 26;BA.debugLine="Dim pregunta2 As String";
mostCurrent._pregunta2 = "";
 //BA.debugLineNum = 27;BA.debugLine="Dim imagenUsuario1 As String";
mostCurrent._imagenusuario1 = "";
 //BA.debugLineNum = 28;BA.debugLine="Dim imagenUsuario2 As String";
mostCurrent._imagenusuario2 = "";
 //BA.debugLineNum = 30;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private btnContinuar As Button";
mostCurrent._btncontinuar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim opcionElegida As Int";
_opcionelegida = 0;
 //BA.debugLineNum = 38;BA.debugLine="Private lblInstrucciones As Label";
mostCurrent._lblinstrucciones = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private lblNombre As Label";
mostCurrent._lblnombre = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private imgVinchuca11 As ImageView";
mostCurrent._imgvinchuca11 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim Container As AHPageContainer";
mostCurrent._container = new de.amberhome.viewpager.AHPageContainer();
 //BA.debugLineNum = 44;BA.debugLine="Dim Pager As AHViewPager";
mostCurrent._pager = new de.amberhome.viewpager.AHViewPager();
 //BA.debugLineNum = 45;BA.debugLine="Private lblFondo As Label";
mostCurrent._lblfondo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private btnVolver As Button";
mostCurrent._btnvolver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private imgHabitat As ImageView";
mostCurrent._imghabitat = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private imgDistribucion As ImageView";
mostCurrent._imgdistribucion = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblDescripcionGeneral As Label";
mostCurrent._lbldescripciongeneral = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim GD As GestureDetector";
mostCurrent._gd = new flm.b4a.gesturedetector.GestureDetectorForB4A();
 //BA.debugLineNum = 53;BA.debugLine="Dim zoom As Float = 1.0";
_zoom = (float) (1.0);
 //BA.debugLineNum = 54;BA.debugLine="Dim imgUsuario As ImageView";
mostCurrent._imgusuario = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private bmp As Bitmap";
mostCurrent._bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private btnCambiarFoto As Button";
mostCurrent._btncambiarfoto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private lblSlide As Label";
mostCurrent._lblslide = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private btnEnviar As Button";
mostCurrent._btnenviar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private lblRojo As Label";
mostCurrent._lblrojo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private imgOpcion1_1 As ImageView";
mostCurrent._imgopcion1_1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private imgOpcion2_1 As ImageView";
mostCurrent._imgopcion2_1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private imgOpcion4_1 As ImageView";
mostCurrent._imgopcion4_1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private imgOpcion3_1 As ImageView";
mostCurrent._imgopcion3_1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Dim fondogris As Label";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Dim fotoCerrar As Button";
mostCurrent._fotocerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Dim fotoCambiar As Button";
mostCurrent._fotocambiar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private btnVerMiFoto As Button";
mostCurrent._btnvermifoto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private textOpcion1 As Label";
mostCurrent._textopcion1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private textOpcion2 As Label";
mostCurrent._textopcion2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Private textOpcion3 As Label";
mostCurrent._textopcion3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private textOpcion4 As Label";
mostCurrent._textopcion4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static String  _imgopcion1_1_click() throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub imgOpcion1_1_Click";
 //BA.debugLineNum = 201;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 202;BA.debugLine="lblRojo.Left = imgOpcion1_1.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgopcion1_1.getLeft());
 //BA.debugLineNum = 203;BA.debugLine="lblRojo.Top = imgOpcion1_1.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgopcion1_1.getTop());
 //BA.debugLineNum = 204;BA.debugLine="opcionElegida = 1";
_opcionelegida = (int) (1);
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return "";
}
public static String  _imgopcion2_1_click() throws Exception{
 //BA.debugLineNum = 207;BA.debugLine="Sub imgOpcion2_1_Click";
 //BA.debugLineNum = 208;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 209;BA.debugLine="lblRojo.Left = imgOpcion2_1.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgopcion2_1.getLeft());
 //BA.debugLineNum = 210;BA.debugLine="lblRojo.Top = imgOpcion2_1.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgopcion2_1.getTop());
 //BA.debugLineNum = 211;BA.debugLine="opcionElegida = 2";
_opcionelegida = (int) (2);
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _imgopcion3_1_click() throws Exception{
 //BA.debugLineNum = 214;BA.debugLine="Sub imgOpcion3_1_Click";
 //BA.debugLineNum = 215;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 216;BA.debugLine="lblRojo.Left = imgOpcion3_1.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgopcion3_1.getLeft());
 //BA.debugLineNum = 217;BA.debugLine="lblRojo.Top = imgOpcion3_1.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgopcion3_1.getTop());
 //BA.debugLineNum = 218;BA.debugLine="opcionElegida = 3";
_opcionelegida = (int) (3);
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return "";
}
public static String  _imgopcion4_1_click() throws Exception{
 //BA.debugLineNum = 221;BA.debugLine="Sub imgOpcion4_1_Click";
 //BA.debugLineNum = 222;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 223;BA.debugLine="lblRojo.Left = imgOpcion4_1.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgopcion4_1.getLeft());
 //BA.debugLineNum = 224;BA.debugLine="lblRojo.Top = imgOpcion4_1.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgopcion4_1.getTop());
 //BA.debugLineNum = 225;BA.debugLine="opcionElegida = 4";
_opcionelegida = (int) (4);
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
public static String  _pnl_gesture_onpinchclose(float _newdistance,float _previousdistance,Object _motionevent) throws Exception{
 //BA.debugLineNum = 160;BA.debugLine="Sub pnl_gesture_onPinchClose(NewDistance As Float,";
 //BA.debugLineNum = 161;BA.debugLine="zoom = zoom *(NewDistance/PreviousDistance)";
_zoom = (float) (_zoom*(_newdistance/(double)_previousdistance));
 //BA.debugLineNum = 162;BA.debugLine="imgUsuario.Width = bmp.Width*zoom";
mostCurrent._imgusuario.setWidth((int) (mostCurrent._bmp.getWidth()*_zoom));
 //BA.debugLineNum = 163;BA.debugLine="imgUsuario.Height = bmp.Height*zoom";
mostCurrent._imgusuario.setHeight((int) (mostCurrent._bmp.getHeight()*_zoom));
 //BA.debugLineNum = 164;BA.debugLine="imgUsuario.Top = (scroll2d.Panel.Height-imgUsuari";
mostCurrent._imgusuario.setTop((int) ((mostCurrent._scroll2d.getPanel().getHeight()-mostCurrent._imgusuario.getHeight())/(double)3));
 //BA.debugLineNum = 165;BA.debugLine="imgUsuario.left = (scroll2d.Panel.Width - imgUsua";
mostCurrent._imgusuario.setLeft((int) ((mostCurrent._scroll2d.getPanel().getWidth()-mostCurrent._imgusuario.getWidth())/(double)3));
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
public static String  _pnl_gesture_onpinchopen(float _newdistance,float _previousdistance,Object _motionevent) throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub pnl_gesture_onPinchOpen(NewDistance As Float,";
 //BA.debugLineNum = 154;BA.debugLine="zoom = zoom * (NewDistance/PreviousDistance)";
_zoom = (float) (_zoom*(_newdistance/(double)_previousdistance));
 //BA.debugLineNum = 155;BA.debugLine="imgUsuario.Width = bmp.Width*zoom";
mostCurrent._imgusuario.setWidth((int) (mostCurrent._bmp.getWidth()*_zoom));
 //BA.debugLineNum = 156;BA.debugLine="imgUsuario.Height = bmp.Height*zoom";
mostCurrent._imgusuario.setHeight((int) (mostCurrent._bmp.getHeight()*_zoom));
 //BA.debugLineNum = 157;BA.debugLine="imgUsuario.Top = (scroll2d.Panel.Height-imgUsuari";
mostCurrent._imgusuario.setTop((int) ((mostCurrent._scroll2d.getPanel().getHeight()-mostCurrent._imgusuario.getHeight())/(double)3));
 //BA.debugLineNum = 158;BA.debugLine="imgUsuario.left = (scroll2d.Panel.Width - imgUsua";
mostCurrent._imgusuario.setLeft((int) ((mostCurrent._scroll2d.getPanel().getWidth()-mostCurrent._imgusuario.getWidth())/(double)3));
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 10;BA.debugLine="Dim currentPregunta As Int";
_currentpregunta = 0;
 //BA.debugLineNum = 12;BA.debugLine="Dim foto1 As String";
_foto1 = "";
 //BA.debugLineNum = 13;BA.debugLine="Dim foto2 As String";
_foto2 = "";
 //BA.debugLineNum = 14;BA.debugLine="Dim foto3 As String";
_foto3 = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim foto4 As String";
_foto4 = "";
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 119;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 120;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 121;BA.debugLine="If textOpcion1.IsInitialized Then";
if (mostCurrent._textopcion1.IsInitialized()) { 
 //BA.debugLineNum = 122;BA.debugLine="lblInstrucciones.Text = \"Selecciona el pico del";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("Selecciona el pico del insecto que encontraste"));
 //BA.debugLineNum = 123;BA.debugLine="textOpcion1.Text = \"Pico fino y largo extendién";
mostCurrent._textopcion1.setText(BA.ObjectToCharSequence("Pico fino y largo extendiéndose por detrás de las patas"));
 //BA.debugLineNum = 124;BA.debugLine="textOpcion2.Text = \"Pico fino y largo extendién";
mostCurrent._textopcion2.setText(BA.ObjectToCharSequence("Pico fino y largo extendiéndose hasta el primer par de patas"));
 //BA.debugLineNum = 125;BA.debugLine="textOpcion4.Text = \"Pico grueso y curvo\"";
mostCurrent._textopcion4.setText(BA.ObjectToCharSequence("Pico grueso y curvo"));
 //BA.debugLineNum = 126;BA.debugLine="textOpcion4.Text = \"Pico robusto del largo de l";
mostCurrent._textopcion4.setText(BA.ObjectToCharSequence("Pico robusto del largo de la cabeza, paralela a la misma"));
 //BA.debugLineNum = 127;BA.debugLine="btnVerMiFoto.Text  = \"     Mis fotos\"";
mostCurrent._btnvermifoto.setText(BA.ObjectToCharSequence("     Mis fotos"));
 //BA.debugLineNum = 128;BA.debugLine="btnContinuar.Text  = \"Continuar\"";
mostCurrent._btncontinuar.setText(BA.ObjectToCharSequence("Continuar"));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 132;BA.debugLine="If textOpcion1.IsInitialized Then";
if (mostCurrent._textopcion1.IsInitialized()) { 
 //BA.debugLineNum = 133;BA.debugLine="lblInstrucciones.Text = \"Selecciona el pico del";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("Selecciona el pico del insecto que encontraste"));
 //BA.debugLineNum = 134;BA.debugLine="textOpcion1.Text = \"Pico fino y largo extendién";
mostCurrent._textopcion1.setText(BA.ObjectToCharSequence("Pico fino y largo extendiéndose por detrás de las patas"));
 //BA.debugLineNum = 135;BA.debugLine="textOpcion2.Text = \"Pico fino y largo extendién";
mostCurrent._textopcion2.setText(BA.ObjectToCharSequence("Pico fino y largo extendiéndose hasta el primer par de patas"));
 //BA.debugLineNum = 136;BA.debugLine="textOpcion4.Text = \"Pico grueso y curvo\"";
mostCurrent._textopcion4.setText(BA.ObjectToCharSequence("Pico grueso y curvo"));
 //BA.debugLineNum = 137;BA.debugLine="textOpcion4.Text = \"Pico robusto del largo de l";
mostCurrent._textopcion4.setText(BA.ObjectToCharSequence("Pico robusto del largo de la cabeza, paralela a la misma"));
 //BA.debugLineNum = 138;BA.debugLine="btnVerMiFoto.Text  = \"     My photos\"";
mostCurrent._btnvermifoto.setText(BA.ObjectToCharSequence("     My photos"));
 //BA.debugLineNum = 139;BA.debugLine="btnContinuar.Text  = \"Continue\"";
mostCurrent._btncontinuar.setText(BA.ObjectToCharSequence("Continue"));
 };
 };
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
}
