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

public class frmlocalizacion extends Activity implements B4AActivity{
	public static frmlocalizacion mostCurrent;
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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmlocalizacion");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmlocalizacion).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmlocalizacion");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmlocalizacion", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmlocalizacion) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmlocalizacion) Resume **");
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
		return frmlocalizacion.class;
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
        BA.LogInfo("** Activity (frmlocalizacion) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            frmlocalizacion mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmlocalizacion) Resume **");
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
public static uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper _fusedlocationprovider1 = null;
public static anywheresoftware.b4a.gps.LocationWrapper _lastlocation = null;
public static String _tipodetect = "";
public static String _origen = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btndetectar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public uk.co.martinpearman.b4a.osmdroid.views.MapView _mapview1 = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay _simplelocationoverlay1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmapa = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmanual = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuarlocalizacion = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _fondogris = null;
public anywheresoftware.b4a.objects.LabelWrapper _detectandolabel = null;
public anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmaprender _frmaprender = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
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
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=5111808;
 //BA.debugLineNum = 5111808;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=5111813;
 //BA.debugLineNum = 5111813;BA.debugLine="ProgressDialogShow(\"Buscando posición...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando posición..."));
RDebugUtils.currentLine=5111815;
 //BA.debugLineNum = 5111815;BA.debugLine="If FusedLocationProvider1.IsInitialized = False T";
if (_fusedlocationprovider1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=5111816;
 //BA.debugLineNum = 5111816;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProvider1");
 };
RDebugUtils.currentLine=5111819;
 //BA.debugLineNum = 5111819;BA.debugLine="If tipoDetect <> \"MAPAdetect\" Then";
if ((_tipodetect).equals("MAPAdetect") == false) { 
RDebugUtils.currentLine=5111820;
 //BA.debugLineNum = 5111820;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 };
RDebugUtils.currentLine=5111823;
 //BA.debugLineNum = 5111823;BA.debugLine="Activity.LoadLayout(\"frmlocation\")";
mostCurrent._activity.LoadLayout("frmlocation",mostCurrent.activityBA);
RDebugUtils.currentLine=5111824;
 //BA.debugLineNum = 5111824;BA.debugLine="Load_Reporte_Localizacion";
_load_reporte_localizacion();
RDebugUtils.currentLine=5111828;
 //BA.debugLineNum = 5111828;BA.debugLine="End Sub";
return "";
}
public static String  _load_reporte_localizacion() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "load_reporte_localizacion"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "load_reporte_localizacion", null));}
RDebugUtils.currentLine=5373952;
 //BA.debugLineNum = 5373952;BA.debugLine="Sub Load_Reporte_Localizacion";
RDebugUtils.currentLine=5373954;
 //BA.debugLineNum = 5373954;BA.debugLine="If origen <> \"cambio\" Then";
if ((_origen).equals("cambio") == false) { 
RDebugUtils.currentLine=5373955;
 //BA.debugLineNum = 5373955;BA.debugLine="origen = \"Reporte_Localizacion\"";
_origen = "Reporte_Localizacion";
RDebugUtils.currentLine=5373956;
 //BA.debugLineNum = 5373956;BA.debugLine="lblLat.Text = \"\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(""));
RDebugUtils.currentLine=5373957;
 //BA.debugLineNum = 5373957;BA.debugLine="lblLon.Text = \"\"";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(""));
 };
RDebugUtils.currentLine=5373960;
 //BA.debugLineNum = 5373960;BA.debugLine="CargarMapa";
_cargarmapa();
RDebugUtils.currentLine=5373961;
 //BA.debugLineNum = 5373961;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode}));}
RDebugUtils.currentLine=5177344;
 //BA.debugLineNum = 5177344;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=5177345;
 //BA.debugLineNum = 5177345;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=5177346;
 //BA.debugLineNum = 5177346;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=5177347;
 //BA.debugLineNum = 5177347;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
RDebugUtils.currentLine=5177349;
 //BA.debugLineNum = 5177349;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
RDebugUtils.currentLine=5308416;
 //BA.debugLineNum = 5308416;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=5308417;
 //BA.debugLineNum = 5308417;BA.debugLine="If FusedLocationProvider1.IsConnected = True Then";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=5308418;
 //BA.debugLineNum = 5308418;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
 };
RDebugUtils.currentLine=5308421;
 //BA.debugLineNum = 5308421;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=5242880;
 //BA.debugLineNum = 5242880;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=5242882;
 //BA.debugLineNum = 5242882;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuarlocalizacion_click() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncontinuarlocalizacion_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncontinuarlocalizacion_click", null));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=6291456;
 //BA.debugLineNum = 6291456;BA.debugLine="Sub btnContinuarLocalizacion_Click";
RDebugUtils.currentLine=6291458;
 //BA.debugLineNum = 6291458;BA.debugLine="If lblLat.Text = \"\" Or lblLat.Text = \"0\" Then";
if ((mostCurrent._lbllat.getText()).equals("") || (mostCurrent._lbllat.getText()).equals("0")) { 
RDebugUtils.currentLine=6291460;
 //BA.debugLineNum = 6291460;BA.debugLine="ToastMessageShow(\"Debe buscar su posición para s";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe buscar su posición para seguir"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=6291461;
 //BA.debugLineNum = 6291461;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=6291466;
 //BA.debugLineNum = 6291466;BA.debugLine="If origen = \"cambio\" Then";
if ((_origen).equals("cambio")) { 
RDebugUtils.currentLine=6291467;
 //BA.debugLineNum = 6291467;BA.debugLine="frmDatosAnteriores.nuevalatlng = lblLat.Text & \"";
mostCurrent._frmdatosanteriores._nuevalatlng = mostCurrent._lbllat.getText()+"_"+mostCurrent._lbllon.getText();
RDebugUtils.currentLine=6291468;
 //BA.debugLineNum = 6291468;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=6291469;
 //BA.debugLineNum = 6291469;BA.debugLine="frmDatosAnteriores.notificacion = False";
mostCurrent._frmdatosanteriores._notificacion = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=6291470;
 //BA.debugLineNum = 6291470;BA.debugLine="CallSubDelayed(frmDatosAnteriores, \"CambiarUbica";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()),"CambiarUbicacion");
RDebugUtils.currentLine=6291472;
 //BA.debugLineNum = 6291472;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=6291473;
 //BA.debugLineNum = 6291473;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=6291478;
 //BA.debugLineNum = 6291478;BA.debugLine="If lblLat.Text = \"\" Or lblLon.Text = \"\" Then";
if ((mostCurrent._lbllat.getText()).equals("") || (mostCurrent._lbllon.getText()).equals("")) { 
RDebugUtils.currentLine=6291479;
 //BA.debugLineNum = 6291479;BA.debugLine="ToastMessageShow(\"No se han detectado tus coorde";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se han detectado tus coordenadas, intenta de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=6291480;
 //BA.debugLineNum = 6291480;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=6291482;
 //BA.debugLineNum = 6291482;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=6291483;
 //BA.debugLineNum = 6291483;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=6291484;
 //BA.debugLineNum = 6291484;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=6291486;
 //BA.debugLineNum = 6291486;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","decimalLatitude",(Object)(mostCurrent._lbllat.getText()),_map1);
RDebugUtils.currentLine=6291487;
 //BA.debugLineNum = 6291487;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","decimalLongitude",(Object)(mostCurrent._lbllon.getText()),_map1);
RDebugUtils.currentLine=6291488;
 //BA.debugLineNum = 6291488;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_map1);
RDebugUtils.currentLine=6291490;
 //BA.debugLineNum = 6291490;BA.debugLine="If tipoDetect = \"GPSdetect\" Then";
if ((_tipodetect).equals("GPSdetect")) { 
RDebugUtils.currentLine=6291491;
 //BA.debugLineNum = 6291491;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","gpsDetect",(Object)("si"),_map1);
 }else 
{RDebugUtils.currentLine=6291492;
 //BA.debugLineNum = 6291492;BA.debugLine="Else If tipoDetect = \"MAPAdetect\" Then";
if ((_tipodetect).equals("MAPAdetect")) { 
RDebugUtils.currentLine=6291493;
 //BA.debugLineNum = 6291493;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","mapaDetect",(Object)("si"),_map1);
 }}
;
RDebugUtils.currentLine=6291497;
 //BA.debugLineNum = 6291497;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=6291498;
 //BA.debugLineNum = 6291498;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=6291499;
 //BA.debugLineNum = 6291499;BA.debugLine="StartActivity(frmcamara)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmcamara.getObject()));
RDebugUtils.currentLine=6291500;
 //BA.debugLineNum = 6291500;BA.debugLine="End Sub";
return "";
}
public static String  _btndetectar_click() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btndetectar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btndetectar_click", null));}
RDebugUtils.currentLine=5505024;
 //BA.debugLineNum = 5505024;BA.debugLine="Sub btnDetectar_Click";
RDebugUtils.currentLine=5505025;
 //BA.debugLineNum = 5505025;BA.debugLine="btnDetectar.Color = Colors.Black";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=5505026;
 //BA.debugLineNum = 5505026;BA.debugLine="btnDetectar.TextColor = Colors.White";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=5505027;
 //BA.debugLineNum = 5505027;BA.debugLine="btnManual.TextColor = Colors.Black";
mostCurrent._btnmanual.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=5505028;
 //BA.debugLineNum = 5505028;BA.debugLine="btnManual.Color = Colors.Transparent";
mostCurrent._btnmanual.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=5505029;
 //BA.debugLineNum = 5505029;BA.debugLine="DetectarPosicion";
_detectarposicion();
RDebugUtils.currentLine=5505030;
 //BA.debugLineNum = 5505030;BA.debugLine="End Sub";
return "";
}
public static String  _detectarposicion() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "detectarposicion"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "detectarposicion", null));}
RDebugUtils.currentLine=5570560;
 //BA.debugLineNum = 5570560;BA.debugLine="Sub DetectarPosicion";
RDebugUtils.currentLine=5570563;
 //BA.debugLineNum = 5570563;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
RDebugUtils.currentLine=5570564;
 //BA.debugLineNum = 5570564;BA.debugLine="detectandoLabel.Initialize(\"\")";
mostCurrent._detectandolabel.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=5570565;
 //BA.debugLineNum = 5570565;BA.debugLine="detectandoLabel.TextColor = Colors.White";
mostCurrent._detectandolabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=5570566;
 //BA.debugLineNum = 5570566;BA.debugLine="detectandoLabel.Gravity = Gravity.CENTER_HORIZONT";
mostCurrent._detectandolabel.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
RDebugUtils.currentLine=5570567;
 //BA.debugLineNum = 5570567;BA.debugLine="detectandoLabel.Text = \"Buscando tu posición auto";
mostCurrent._detectandolabel.setText(BA.ObjectToCharSequence("Buscando tu posición automáticamente..."));
RDebugUtils.currentLine=5570568;
 //BA.debugLineNum = 5570568;BA.debugLine="btnContinuarLocalizacion.Visible = False";
mostCurrent._btncontinuarlocalizacion.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=5570570;
 //BA.debugLineNum = 5570570;BA.debugLine="fondogris.Color = Colors.ARGB(150, 64,64,64)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
RDebugUtils.currentLine=5570571;
 //BA.debugLineNum = 5570571;BA.debugLine="Activity.AddView(fondogris, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=5570572;
 //BA.debugLineNum = 5570572;BA.debugLine="Activity.AddView(detectandoLabel, 5%x, 45%y, 80%x";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._detectandolabel.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (45),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
RDebugUtils.currentLine=5570573;
 //BA.debugLineNum = 5570573;BA.debugLine="btnDetectar.Enabled = False";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=5570575;
 //BA.debugLineNum = 5570575;BA.debugLine="If FusedLocationProvider1.IsInitialized = False T";
if (_fusedlocationprovider1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=5570576;
 //BA.debugLineNum = 5570576;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProvider1");
 };
RDebugUtils.currentLine=5570579;
 //BA.debugLineNum = 5570579;BA.debugLine="If FusedLocationProvider1.IsConnected = False The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=5570580;
 //BA.debugLineNum = 5570580;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 };
RDebugUtils.currentLine=5570582;
 //BA.debugLineNum = 5570582;BA.debugLine="End Sub";
return "";
}
public static String  _btnmanual_click() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnmanual_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnmanual_click", null));}
RDebugUtils.currentLine=6160384;
 //BA.debugLineNum = 6160384;BA.debugLine="Sub btnManual_Click";
RDebugUtils.currentLine=6160385;
 //BA.debugLineNum = 6160385;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=6160386;
 //BA.debugLineNum = 6160386;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=6160387;
 //BA.debugLineNum = 6160387;BA.debugLine="btnManual.TextColor = Colors.white";
mostCurrent._btnmanual.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=6160388;
 //BA.debugLineNum = 6160388;BA.debugLine="btnManual.Color = Colors.Black";
mostCurrent._btnmanual.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=6160392;
 //BA.debugLineNum = 6160392;BA.debugLine="If FusedLocationProvider1.IsConnected = True Then";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=6160393;
 //BA.debugLineNum = 6160393;BA.debugLine="FusedLocationProvider1.DisConnect";
_fusedlocationprovider1.Disconnect();
 };
RDebugUtils.currentLine=6160397;
 //BA.debugLineNum = 6160397;BA.debugLine="tipoDetect = \"MAPAdetect\"";
_tipodetect = "MAPAdetect";
RDebugUtils.currentLine=6160398;
 //BA.debugLineNum = 6160398;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmapa() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargarmapa"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cargarmapa", null));}
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
RDebugUtils.currentLine=5439488;
 //BA.debugLineNum = 5439488;BA.debugLine="Sub CargarMapa";
RDebugUtils.currentLine=5439489;
 //BA.debugLineNum = 5439489;BA.debugLine="If LastLocation.IsInitialized Then";
if (_lastlocation.IsInitialized()) { 
RDebugUtils.currentLine=5439490;
 //BA.debugLineNum = 5439490;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
RDebugUtils.currentLine=5439491;
 //BA.debugLineNum = 5439491;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
 }else {
RDebugUtils.currentLine=5439493;
 //BA.debugLineNum = 5439493;BA.debugLine="lblLat.Text = \"-34.9204950\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence("-34.9204950"));
RDebugUtils.currentLine=5439494;
 //BA.debugLineNum = 5439494;BA.debugLine="lblLon.Text = \"-57.9535660\"";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence("-57.9535660"));
 };
RDebugUtils.currentLine=5439496;
 //BA.debugLineNum = 5439496;BA.debugLine="MapView1.Initialize(\"MapView1\")";
mostCurrent._mapview1.Initialize(mostCurrent.activityBA,"MapView1");
RDebugUtils.currentLine=5439497;
 //BA.debugLineNum = 5439497;BA.debugLine="MapView1.SetBuiltInZoomControls(False)";
mostCurrent._mapview1.SetBuiltInZoomControls(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=5439498;
 //BA.debugLineNum = 5439498;BA.debugLine="MapView1.SetMultiTouchControls(True)";
mostCurrent._mapview1.SetMultiTouchControls(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=5439500;
 //BA.debugLineNum = 5439500;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
RDebugUtils.currentLine=5439501;
 //BA.debugLineNum = 5439501;BA.debugLine="geopoint1.Initialize(lblLat.Text, lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
RDebugUtils.currentLine=5439502;
 //BA.debugLineNum = 5439502;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
RDebugUtils.currentLine=5439503;
 //BA.debugLineNum = 5439503;BA.debugLine="MapView1.GetController.SetZoom(10)";
mostCurrent._mapview1.GetController().SetZoom((int) (10));
RDebugUtils.currentLine=5439504;
 //BA.debugLineNum = 5439504;BA.debugLine="pnlMapa.AddView(MapView1, 0,0, 100%x, 100%y)";
mostCurrent._pnlmapa.AddView((android.view.View)(mostCurrent._mapview1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=5439505;
 //BA.debugLineNum = 5439505;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=5439506;
 //BA.debugLineNum = 5439506;BA.debugLine="SimpleLocationOverlay1.Initialize()";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
RDebugUtils.currentLine=5439507;
 //BA.debugLineNum = 5439507;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
RDebugUtils.currentLine=5439508;
 //BA.debugLineNum = 5439508;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 }else {
RDebugUtils.currentLine=5439510;
 //BA.debugLineNum = 5439510;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
RDebugUtils.currentLine=5439511;
 //BA.debugLineNum = 5439511;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 };
RDebugUtils.currentLine=5439515;
 //BA.debugLineNum = 5439515;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fondogris_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fondogris_click", null));}
RDebugUtils.currentLine=5636096;
 //BA.debugLineNum = 5636096;BA.debugLine="Sub fondogris_Click";
RDebugUtils.currentLine=5636098;
 //BA.debugLineNum = 5636098;BA.debugLine="tipoDetect = \"MAPAdetect\"";
_tipodetect = "MAPAdetect";
RDebugUtils.currentLine=5636099;
 //BA.debugLineNum = 5636099;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=5636100;
 //BA.debugLineNum = 5636100;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=5636101;
 //BA.debugLineNum = 5636101;BA.debugLine="btnManual.TextColor = Colors.white";
mostCurrent._btnmanual.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=5636102;
 //BA.debugLineNum = 5636102;BA.debugLine="btnManual.Color = Colors.Black";
mostCurrent._btnmanual.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=5636103;
 //BA.debugLineNum = 5636103;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=5636105;
 //BA.debugLineNum = 5636105;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
RDebugUtils.currentLine=5636106;
 //BA.debugLineNum = 5636106;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
RDebugUtils.currentLine=5636107;
 //BA.debugLineNum = 5636107;BA.debugLine="btnContinuarLocalizacion.Visible = True";
mostCurrent._btncontinuarlocalizacion.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=5636108;
 //BA.debugLineNum = 5636108;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionfailed(int _connectionresult1) throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionfailed"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionfailed", new Object[] {_connectionresult1}));}
RDebugUtils.currentLine=5701632;
 //BA.debugLineNum = 5701632;BA.debugLine="Sub FusedLocationProvider1_ConnectionFailed(Connec";
RDebugUtils.currentLine=5701633;
 //BA.debugLineNum = 5701633;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionFailed\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_ConnectionFailed");
RDebugUtils.currentLine=5701637;
 //BA.debugLineNum = 5701637;BA.debugLine="Select ConnectionResult1";
switch (BA.switchObjectToInt(_connectionresult1,_fusedlocationprovider1.ConnectionResult.NETWORK_ERROR)) {
case 0: {
RDebugUtils.currentLine=5701641;
 //BA.debugLineNum = 5701641;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 break; }
default: {
 break; }
}
;
RDebugUtils.currentLine=5701645;
 //BA.debugLineNum = 5701645;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuccess() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionsuccess"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionsuccess", null));}
uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest _locationrequest1 = null;
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder _locationsettingsrequestbuilder1 = null;
RDebugUtils.currentLine=5767168;
 //BA.debugLineNum = 5767168;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuccess";
RDebugUtils.currentLine=5767169;
 //BA.debugLineNum = 5767169;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuccess\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_ConnectionSuccess");
RDebugUtils.currentLine=5767170;
 //BA.debugLineNum = 5767170;BA.debugLine="Dim LocationRequest1 As LocationRequest";
_locationrequest1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest();
RDebugUtils.currentLine=5767171;
 //BA.debugLineNum = 5767171;BA.debugLine="LocationRequest1.Initialize";
_locationrequest1.Initialize();
RDebugUtils.currentLine=5767172;
 //BA.debugLineNum = 5767172;BA.debugLine="LocationRequest1.SetInterval(1000)	'	1000 millise";
_locationrequest1.SetInterval((long) (1000));
RDebugUtils.currentLine=5767173;
 //BA.debugLineNum = 5767173;BA.debugLine="LocationRequest1.SetPriority(LocationRequest1.Pri";
_locationrequest1.SetPriority(_locationrequest1.Priority.PRIORITY_HIGH_ACCURACY);
RDebugUtils.currentLine=5767174;
 //BA.debugLineNum = 5767174;BA.debugLine="LocationRequest1.SetSmallestDisplacement(1)	'	1 m";
_locationrequest1.SetSmallestDisplacement((float) (1));
RDebugUtils.currentLine=5767176;
 //BA.debugLineNum = 5767176;BA.debugLine="Dim LocationSettingsRequestBuilder1 As LocationSe";
_locationsettingsrequestbuilder1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder();
RDebugUtils.currentLine=5767177;
 //BA.debugLineNum = 5767177;BA.debugLine="LocationSettingsRequestBuilder1.Initialize";
_locationsettingsrequestbuilder1.Initialize();
RDebugUtils.currentLine=5767178;
 //BA.debugLineNum = 5767178;BA.debugLine="LocationSettingsRequestBuilder1.AddLocationReques";
_locationsettingsrequestbuilder1.AddLocationRequest((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
RDebugUtils.currentLine=5767179;
 //BA.debugLineNum = 5767179;BA.debugLine="FusedLocationProvider1.CheckLocationSettings(Loca";
_fusedlocationprovider1.CheckLocationSettings(_locationsettingsrequestbuilder1.Build());
RDebugUtils.currentLine=5767181;
 //BA.debugLineNum = 5767181;BA.debugLine="FusedLocationProvider1.RequestLocationUpdates(Loc";
_fusedlocationprovider1.RequestLocationUpdates((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
RDebugUtils.currentLine=5767182;
 //BA.debugLineNum = 5767182;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuspended(int _suspendedcause1) throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionsuspended"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionsuspended", new Object[] {_suspendedcause1}));}
RDebugUtils.currentLine=5832704;
 //BA.debugLineNum = 5832704;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuspended(Sus";
RDebugUtils.currentLine=5832705;
 //BA.debugLineNum = 5832705;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuspended\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_ConnectionSuspended");
RDebugUtils.currentLine=5832709;
 //BA.debugLineNum = 5832709;BA.debugLine="Select SuspendedCause1";
switch (BA.switchObjectToInt(_suspendedcause1,_fusedlocationprovider1.SuspendedCause.CAUSE_NETWORK_LOST,_fusedlocationprovider1.SuspendedCause.CAUSE_SERVICE_DISCONNECTED)) {
case 0: {
 break; }
case 1: {
 break; }
}
;
RDebugUtils.currentLine=5832715;
 //BA.debugLineNum = 5832715;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_locationchanged"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_locationchanged", new Object[] {_location1}));}
RDebugUtils.currentLine=5898240;
 //BA.debugLineNum = 5898240;BA.debugLine="Sub FusedLocationProvider1_LocationChanged(Locatio";
RDebugUtils.currentLine=5898241;
 //BA.debugLineNum = 5898241;BA.debugLine="Log(\"FusedLocationProvider1_LocationChanged\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_LocationChanged");
RDebugUtils.currentLine=5898242;
 //BA.debugLineNum = 5898242;BA.debugLine="LastLocation=Location1";
_lastlocation = _location1;
RDebugUtils.currentLine=5898243;
 //BA.debugLineNum = 5898243;BA.debugLine="Log(LastLocation.Latitude)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lastlocation.getLatitude()));
RDebugUtils.currentLine=5898244;
 //BA.debugLineNum = 5898244;BA.debugLine="Log(LastLocation.Longitude)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lastlocation.getLongitude()));
RDebugUtils.currentLine=5898245;
 //BA.debugLineNum = 5898245;BA.debugLine="UpdateUI";
_updateui();
RDebugUtils.currentLine=5898246;
 //BA.debugLineNum = 5898246;BA.debugLine="End Sub";
return "";
}
public static String  _updateui() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "updateui"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "updateui", null));}
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
RDebugUtils.currentLine=6094848;
 //BA.debugLineNum = 6094848;BA.debugLine="Sub UpdateUI";
RDebugUtils.currentLine=6094849;
 //BA.debugLineNum = 6094849;BA.debugLine="tipoDetect = \"GPSdetect\"";
_tipodetect = "GPSdetect";
RDebugUtils.currentLine=6094850;
 //BA.debugLineNum = 6094850;BA.debugLine="ToastMessageShow(\"Posición encontrada!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Posición encontrada!"),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=6094852;
 //BA.debugLineNum = 6094852;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
RDebugUtils.currentLine=6094853;
 //BA.debugLineNum = 6094853;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
RDebugUtils.currentLine=6094856;
 //BA.debugLineNum = 6094856;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=6094857;
 //BA.debugLineNum = 6094857;BA.debugLine="SimpleLocationOverlay1.Initialize()";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 };
RDebugUtils.currentLine=6094859;
 //BA.debugLineNum = 6094859;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
RDebugUtils.currentLine=6094860;
 //BA.debugLineNum = 6094860;BA.debugLine="geopoint1.Initialize(lblLat.Text, lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
RDebugUtils.currentLine=6094861;
 //BA.debugLineNum = 6094861;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
RDebugUtils.currentLine=6094864;
 //BA.debugLineNum = 6094864;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
RDebugUtils.currentLine=6094865;
 //BA.debugLineNum = 6094865;BA.debugLine="MapView1.GetController.SetZoom(14)";
mostCurrent._mapview1.GetController().SetZoom((int) (14));
RDebugUtils.currentLine=6094868;
 //BA.debugLineNum = 6094868;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
RDebugUtils.currentLine=6094869;
 //BA.debugLineNum = 6094869;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=6094871;
 //BA.debugLineNum = 6094871;BA.debugLine="If fondogris.IsInitialized Then";
if (mostCurrent._fondogris.IsInitialized()) { 
RDebugUtils.currentLine=6094872;
 //BA.debugLineNum = 6094872;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
RDebugUtils.currentLine=6094873;
 //BA.debugLineNum = 6094873;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 }else {
RDebugUtils.currentLine=6094875;
 //BA.debugLineNum = 6094875;BA.debugLine="btnDetectar.Color = Colors.Black";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=6094876;
 //BA.debugLineNum = 6094876;BA.debugLine="btnDetectar.TextColor = Colors.White";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=6094877;
 //BA.debugLineNum = 6094877;BA.debugLine="btnManual.TextColor = Colors.Black";
mostCurrent._btnmanual.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=6094878;
 //BA.debugLineNum = 6094878;BA.debugLine="btnManual.Color = Colors.Transparent";
mostCurrent._btnmanual.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 };
RDebugUtils.currentLine=6094880;
 //BA.debugLineNum = 6094880;BA.debugLine="btnContinuarLocalizacion.Visible = True";
mostCurrent._btncontinuarlocalizacion.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=6094881;
 //BA.debugLineNum = 6094881;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=6094884;
 //BA.debugLineNum = 6094884;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationsettingschecked(uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsResult _locationsettingsresult1) throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_locationsettingschecked"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_locationsettingschecked", new Object[] {_locationsettingsresult1}));}
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus _locationsettingsstatus1 = null;
RDebugUtils.currentLine=5963776;
 //BA.debugLineNum = 5963776;BA.debugLine="Sub FusedLocationProvider1_LocationSettingsChecked";
RDebugUtils.currentLine=5963777;
 //BA.debugLineNum = 5963777;BA.debugLine="Log(\"FusedLocationProvider1_LocationSettingsCheck";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_LocationSettingsChecked");
RDebugUtils.currentLine=5963778;
 //BA.debugLineNum = 5963778;BA.debugLine="Dim LocationSettingsStatus1 As LocationSettingsSt";
_locationsettingsstatus1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus();
_locationsettingsstatus1 = _locationsettingsresult1.GetLocationSettingsStatus();
RDebugUtils.currentLine=5963779;
 //BA.debugLineNum = 5963779;BA.debugLine="Select LocationSettingsStatus1.GetStatusCode";
switch (BA.switchObjectToInt(_locationsettingsstatus1.GetStatusCode(),_locationsettingsstatus1.StatusCodes.RESOLUTION_REQUIRED,_locationsettingsstatus1.StatusCodes.SETTINGS_CHANGE_UNAVAILABLE,_locationsettingsstatus1.StatusCodes.SUCCESS)) {
case 0: {
RDebugUtils.currentLine=5963781;
 //BA.debugLineNum = 5963781;BA.debugLine="Log(\"RESOLUTION_REQUIRED\")";
anywheresoftware.b4a.keywords.Common.Log("RESOLUTION_REQUIRED");
RDebugUtils.currentLine=5963784;
 //BA.debugLineNum = 5963784;BA.debugLine="LocationSettingsStatus1.StartResolutionDialog(\"";
_locationsettingsstatus1.StartResolutionDialog(mostCurrent.activityBA,"LocationSettingsResult1");
 break; }
case 1: {
RDebugUtils.currentLine=5963786;
 //BA.debugLineNum = 5963786;BA.debugLine="Log(\"SETTINGS_CHANGE_UNAVAILABLE\")";
anywheresoftware.b4a.keywords.Common.Log("SETTINGS_CHANGE_UNAVAILABLE");
RDebugUtils.currentLine=5963789;
 //BA.debugLineNum = 5963789;BA.debugLine="Msgbox(\"Error, tu dispositivo no tiene localiza";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error, tu dispositivo no tiene localización. Busca tu posición en el mapa!"),BA.ObjectToCharSequence("Problem"),mostCurrent.activityBA);
RDebugUtils.currentLine=5963790;
 //BA.debugLineNum = 5963790;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 2: {
RDebugUtils.currentLine=5963792;
 //BA.debugLineNum = 5963792;BA.debugLine="Log(\"SUCCESS\")";
anywheresoftware.b4a.keywords.Common.Log("SUCCESS");
 break; }
}
;
RDebugUtils.currentLine=5963796;
 //BA.debugLineNum = 5963796;BA.debugLine="End Sub";
return "";
}
public static String  _locationsettingsresult1_resolutiondialogdismissed(boolean _locationsettingsupdated) throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "locationsettingsresult1_resolutiondialogdismissed"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "locationsettingsresult1_resolutiondialogdismissed", new Object[] {_locationsettingsupdated}));}
RDebugUtils.currentLine=6029312;
 //BA.debugLineNum = 6029312;BA.debugLine="Sub LocationSettingsResult1_ResolutionDialogDismis";
RDebugUtils.currentLine=6029313;
 //BA.debugLineNum = 6029313;BA.debugLine="Log(\"LocationSettingsResult1_ResolutionDialogDism";
anywheresoftware.b4a.keywords.Common.Log("LocationSettingsResult1_ResolutionDialogDismissed");
RDebugUtils.currentLine=6029314;
 //BA.debugLineNum = 6029314;BA.debugLine="If Not(LocationSettingsUpdated) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_locationsettingsupdated)) { 
RDebugUtils.currentLine=6029316;
 //BA.debugLineNum = 6029316;BA.debugLine="Msgbox(\"No tienes habilitada la Localización, bu";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes habilitada la Localización, busca en el mapa tu posición"),BA.ObjectToCharSequence("Búsqueda manual"),mostCurrent.activityBA);
RDebugUtils.currentLine=6029319;
 //BA.debugLineNum = 6029319;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=6029320;
 //BA.debugLineNum = 6029320;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=6029321;
 //BA.debugLineNum = 6029321;BA.debugLine="btnManual.TextColor = Colors.white";
mostCurrent._btnmanual.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=6029322;
 //BA.debugLineNum = 6029322;BA.debugLine="btnManual.Color = Colors.Black";
mostCurrent._btnmanual.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=6029323;
 //BA.debugLineNum = 6029323;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=6029326;
 //BA.debugLineNum = 6029326;BA.debugLine="If FusedLocationProvider1.IsConnected = True The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=6029327;
 //BA.debugLineNum = 6029327;BA.debugLine="FusedLocationProvider1.DisConnect";
_fusedlocationprovider1.Disconnect();
 };
RDebugUtils.currentLine=6029330;
 //BA.debugLineNum = 6029330;BA.debugLine="tipoDetect = \"MAPAdetect\"";
_tipodetect = "MAPAdetect";
RDebugUtils.currentLine=6029332;
 //BA.debugLineNum = 6029332;BA.debugLine="If fondogris.IsInitialized Then";
if (mostCurrent._fondogris.IsInitialized()) { 
RDebugUtils.currentLine=6029333;
 //BA.debugLineNum = 6029333;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
RDebugUtils.currentLine=6029334;
 //BA.debugLineNum = 6029334;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 };
 };
RDebugUtils.currentLine=6029337;
 //BA.debugLineNum = 6029337;BA.debugLine="End Sub";
return "";
}
public static String  _mapview1_centerchanged() throws Exception{
RDebugUtils.currentModule="frmlocalizacion";
if (Debug.shouldDelegate(mostCurrent.activityBA, "mapview1_centerchanged"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "mapview1_centerchanged", null));}
RDebugUtils.currentLine=6225920;
 //BA.debugLineNum = 6225920;BA.debugLine="Sub MapView1_CenterChanged";
RDebugUtils.currentLine=6225922;
 //BA.debugLineNum = 6225922;BA.debugLine="If tipoDetect = \"GPSdetect\" Then";
if ((_tipodetect).equals("GPSdetect")) { 
RDebugUtils.currentLine=6225926;
 //BA.debugLineNum = 6225926;BA.debugLine="If FusedLocationProvider1.IsConnected = True The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=6225927;
 //BA.debugLineNum = 6225927;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
 };
 }else 
{RDebugUtils.currentLine=6225929;
 //BA.debugLineNum = 6225929;BA.debugLine="Else If tipoDetect =\"MAPAdetect\" Then";
if ((_tipodetect).equals("MAPAdetect")) { 
RDebugUtils.currentLine=6225930;
 //BA.debugLineNum = 6225930;BA.debugLine="If FusedLocationProvider1.IsConnected = True The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=6225931;
 //BA.debugLineNum = 6225931;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
 };
RDebugUtils.currentLine=6225934;
 //BA.debugLineNum = 6225934;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=6225935;
 //BA.debugLineNum = 6225935;BA.debugLine="SimpleLocationOverlay1.Initialize()";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 };
RDebugUtils.currentLine=6225937;
 //BA.debugLineNum = 6225937;BA.debugLine="SimpleLocationOverlay1.SetLocation(MapView1.GetM";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(mostCurrent._mapview1.GetMapCenter().getObject()));
RDebugUtils.currentLine=6225938;
 //BA.debugLineNum = 6225938;BA.debugLine="lblLat.Text = MapView1.GetMapCenter.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(mostCurrent._mapview1.GetMapCenter().getLatitude()));
RDebugUtils.currentLine=6225939;
 //BA.debugLineNum = 6225939;BA.debugLine="lblLon.Text = MapView1.GetMapCenter.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(mostCurrent._mapview1.GetMapCenter().getLongitude()));
 }}
;
RDebugUtils.currentLine=6225942;
 //BA.debugLineNum = 6225942;BA.debugLine="End Sub";
return "";
}
}