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

public class frmprincipal extends Activity implements B4AActivity{
	public static frmprincipal mostCurrent;
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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmprincipal");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmprincipal).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmprincipal");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmprincipal", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmprincipal) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmprincipal) Resume **");
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
		return frmprincipal.class;
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
        BA.LogInfo("** Activity (frmprincipal) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            frmprincipal mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmprincipal) Resume **");
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
public static String _fullidcurrentproject = "";
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static String _currentscreen = "";
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btninformevinchuca = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnaprendermas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncomofotos = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripmain = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmenu = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblencontre = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnfondo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblencontrevinchuca = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncomofotosreportar = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelexp = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndetectar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public uk.co.martinpearman.b4a.osmdroid.views.MapView _mapview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnzoomall = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnreferenciamapa = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay _simplelocationoverlay1 = null;
public anywheresoftware.b4a.objects.collections.List _markerexport = null;
public anywheresoftware.b4a.objects.LabelWrapper _markerinfo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndatosanteriores = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmapa = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.LabelWrapper _detectandolabel = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlreferencias = null;
public anywheresoftware.b4a.objects.collections.List _markerslist = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.MarkerOverlay _markersoverlay = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlconfig = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrconfig = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokconfig = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnconfigexplorar = null;
public anywheresoftware.b4a.objects.collections.List _listaconfig = null;
public anywheresoftware.b4a.objects.collections.List _listachequeados = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkdatospropios = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkdatosusuarios = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkdatosgeovin = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncheckall = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmaprender _frmaprender = null;
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
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=1179648;
 //BA.debugLineNum = 1179648;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=1179649;
 //BA.debugLineNum = 1179649;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
RDebugUtils.currentLine=1179651;
 //BA.debugLineNum = 1179651;BA.debugLine="Activity.LoadLayout(\"frmPrincipal\")";
mostCurrent._activity.LoadLayout("frmPrincipal",mostCurrent.activityBA);
RDebugUtils.currentLine=1179652;
 //BA.debugLineNum = 1179652;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProvider1");
RDebugUtils.currentLine=1179653;
 //BA.debugLineNum = 1179653;BA.debugLine="If Main.modooffline = True Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=1179654;
 //BA.debugLineNum = 1179654;BA.debugLine="startBienvienido(False, True)";
_startbienvienido(anywheresoftware.b4a.keywords.Common.False,anywheresoftware.b4a.keywords.Common.True);
 }else {
RDebugUtils.currentLine=1179656;
 //BA.debugLineNum = 1179656;BA.debugLine="startBienvienido(True, True)";
_startbienvienido(anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.True);
 };
RDebugUtils.currentLine=1179662;
 //BA.debugLineNum = 1179662;BA.debugLine="Activity.AddMenuItem(\"Cerrar sesión\", \"mnuCerrar";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Cerrar sesión"),"mnuCerrarSesion");
RDebugUtils.currentLine=1179663;
 //BA.debugLineNum = 1179663;BA.debugLine="Activity.AddMenuItem(\"Acerca de GeoVin\", \"mnuAbo";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Acerca de GeoVin"),"mnuAbout");
RDebugUtils.currentLine=1179665;
 //BA.debugLineNum = 1179665;BA.debugLine="tabStripMain.LoadLayout(\"frmprincipal_Reportar\",";
mostCurrent._tabstripmain.LoadLayout("frmprincipal_Reportar",BA.ObjectToCharSequence("REPORTAR"));
RDebugUtils.currentLine=1179666;
 //BA.debugLineNum = 1179666;BA.debugLine="tabStripMain.LoadLayout(\"frmprincipal_Aprender\",";
mostCurrent._tabstripmain.LoadLayout("frmprincipal_Aprender",BA.ObjectToCharSequence("APRENDER"));
RDebugUtils.currentLine=1179667;
 //BA.debugLineNum = 1179667;BA.debugLine="tabStripMain.LoadLayout(\"layBlank\", \"EXPLORAR\")";
mostCurrent._tabstripmain.LoadLayout("layBlank",BA.ObjectToCharSequence("EXPLORAR"));
 };
RDebugUtils.currentLine=1179670;
 //BA.debugLineNum = 1179670;BA.debugLine="End Sub";
return "";
}
public static String  _startbienvienido(boolean _online,boolean _firsttime) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "startbienvienido"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "startbienvienido", new Object[] {_online,_firsttime}));}
String _msjpriv = "";
String[] _msjarray = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
boolean _hayevals = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
String _msg = "";
RDebugUtils.currentLine=1703936;
 //BA.debugLineNum = 1703936;BA.debugLine="Sub startBienvienido(online As Boolean, firsttime";
RDebugUtils.currentLine=1703939;
 //BA.debugLineNum = 1703939;BA.debugLine="If online = True Then";
if (_online==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=1703943;
 //BA.debugLineNum = 1703943;BA.debugLine="If Main.msjprivadoleido = True Then";
if (mostCurrent._main._msjprivadoleido==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=1703944;
 //BA.debugLineNum = 1703944;BA.debugLine="ResetMessages";
_resetmessages();
 };
RDebugUtils.currentLine=1703948;
 //BA.debugLineNum = 1703948;BA.debugLine="If Main.msjprivadouser <> \"None\" And firsttime";
if ((mostCurrent._main._msjprivadouser).equals("None") == false && _firsttime==anywheresoftware.b4a.keywords.Common.True && mostCurrent._main._msjprivadoleido==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=1703949;
 //BA.debugLineNum = 1703949;BA.debugLine="Dim msjpriv As String";
_msjpriv = "";
RDebugUtils.currentLine=1703951;
 //BA.debugLineNum = 1703951;BA.debugLine="Dim msjarray() As String = Regex.Split(\"#\", Ma";
_msjarray = anywheresoftware.b4a.keywords.Common.Regex.Split("#",mostCurrent._main._msjprivadouser);
RDebugUtils.currentLine=1703952;
 //BA.debugLineNum = 1703952;BA.debugLine="For i = 0 To msjarray.Length - 1";
{
final int step8 = 1;
final int limit8 = (int) (_msjarray.length-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
RDebugUtils.currentLine=1703954;
 //BA.debugLineNum = 1703954;BA.debugLine="If msjarray(i) <> \"\" Then";
if ((_msjarray[_i]).equals("") == false) { 
RDebugUtils.currentLine=1703955;
 //BA.debugLineNum = 1703955;BA.debugLine="msjpriv = utilidades.Mensaje(\"Notificación\",";
_msjpriv = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Notificación","MsgMensaje.png","Notificación privada",_msjarray[_i],"Ok","","",anywheresoftware.b4a.keywords.Common.True);
 };
 }
};
RDebugUtils.currentLine=1703960;
 //BA.debugLineNum = 1703960;BA.debugLine="If msjpriv = DialogResponse.POSITIVE Then";
if ((_msjpriv).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=1703961;
 //BA.debugLineNum = 1703961;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=1703962;
 //BA.debugLineNum = 1703962;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=1703963;
 //BA.debugLineNum = 1703963;BA.debugLine="Map1.Put(\"strUserID\", Main.strUserID)";
_map1.Put((Object)("strUserID"),(Object)(mostCurrent._main._struserid));
RDebugUtils.currentLine=1703964;
 //BA.debugLineNum = 1703964;BA.debugLine="Map1.Put(\"username\", Main.username)";
_map1.Put((Object)("username"),(Object)(mostCurrent._main._username));
RDebugUtils.currentLine=1703965;
 //BA.debugLineNum = 1703965;BA.debugLine="Map1.Put(\"msjprivadouser\", Main.msjprivadouse";
_map1.Put((Object)("msjprivadouser"),(Object)(mostCurrent._main._msjprivadouser));
RDebugUtils.currentLine=1703966;
 //BA.debugLineNum = 1703966;BA.debugLine="Map1.Put(\"pass\", Main.pass)";
_map1.Put((Object)("pass"),(Object)(mostCurrent._main._pass));
RDebugUtils.currentLine=1703967;
 //BA.debugLineNum = 1703967;BA.debugLine="Map1.Put(\"firstuse\", Main.firstuse)";
_map1.Put((Object)("firstuse"),(Object)(mostCurrent._main._firstuse));
RDebugUtils.currentLine=1703968;
 //BA.debugLineNum = 1703968;BA.debugLine="File.WriteMap(File.DirInternal, \"config.tx";
anywheresoftware.b4a.keywords.Common.File.WriteMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt",_map1);
 };
 };
RDebugUtils.currentLine=1703973;
 //BA.debugLineNum = 1703973;BA.debugLine="Dim hayevals As Boolean";
_hayevals = false;
RDebugUtils.currentLine=1703974;
 //BA.debugLineNum = 1703974;BA.debugLine="Dim Cursor1 As Cursor";
_cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
RDebugUtils.currentLine=1703975;
 //BA.debugLineNum = 1703975;BA.debugLine="Cursor1 = Starter.SQLdb.ExecQuery(\"SELECT * FROM";
_cursor1.setObject((android.database.Cursor)(mostCurrent._starter._sqldb.ExecQuery("SELECT * FROM markers_local WHERE usuario='"+mostCurrent._main._username+"' AND evalsent='no'")));
RDebugUtils.currentLine=1703976;
 //BA.debugLineNum = 1703976;BA.debugLine="If Cursor1.RowCount > 0 Then";
if (_cursor1.getRowCount()>0) { 
RDebugUtils.currentLine=1703977;
 //BA.debugLineNum = 1703977;BA.debugLine="hayevals = True";
_hayevals = anywheresoftware.b4a.keywords.Common.True;
 };
RDebugUtils.currentLine=1703979;
 //BA.debugLineNum = 1703979;BA.debugLine="Cursor1.Close";
_cursor1.Close();
RDebugUtils.currentLine=1703983;
 //BA.debugLineNum = 1703983;BA.debugLine="If hayevals = True And firsttime = True Then";
if (_hayevals==anywheresoftware.b4a.keywords.Common.True && _firsttime==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=1703984;
 //BA.debugLineNum = 1703984;BA.debugLine="Dim msg As String = Msgbox2(\"Tiene reportes si";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Tiene reportes sin enviar. Desea hacerlo ahora?"),BA.ObjectToCharSequence("Atención"),"Si, los enviaré ahora","","No, las enviaré luego",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=1703985;
 //BA.debugLineNum = 1703985;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=1703986;
 //BA.debugLineNum = 1703986;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=1703987;
 //BA.debugLineNum = 1703987;BA.debugLine="frmDatosAnteriores.notificacion = False";
mostCurrent._frmdatosanteriores._notificacion = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=1703988;
 //BA.debugLineNum = 1703988;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 }else {
RDebugUtils.currentLine=1703990;
 //BA.debugLineNum = 1703990;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No ha iniciado sesión aún"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 };
 }else {
RDebugUtils.currentLine=1703998;
 //BA.debugLineNum = 1703998;BA.debugLine="Main.modooffline = True";
mostCurrent._main._modooffline = anywheresoftware.b4a.keywords.Common.True;
 };
RDebugUtils.currentLine=1704001;
 //BA.debugLineNum = 1704001;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode}));}
String _msg = "";
RDebugUtils.currentLine=1376256;
 //BA.debugLineNum = 1376256;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=1376258;
 //BA.debugLineNum = 1376258;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=1376259;
 //BA.debugLineNum = 1376259;BA.debugLine="If currentscreen = \"aprendermas\" Or currentscree";
if ((mostCurrent._currentscreen).equals("aprendermas") || (mostCurrent._currentscreen).equals("fotos")) { 
RDebugUtils.currentLine=1376260;
 //BA.debugLineNum = 1376260;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=1376261;
 //BA.debugLineNum = 1376261;BA.debugLine="Activity.LoadLayout(\"frmPrincipal\")";
mostCurrent._activity.LoadLayout("frmPrincipal",mostCurrent.activityBA);
RDebugUtils.currentLine=1376262;
 //BA.debugLineNum = 1376262;BA.debugLine="currentscreen = \"\"";
mostCurrent._currentscreen = "";
 }else {
RDebugUtils.currentLine=1376264;
 //BA.debugLineNum = 1376264;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=1376265;
 //BA.debugLineNum = 1376265;BA.debugLine="msg = Msgbox2(\"Salir de la aplicación?\", \"SALIR";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Salir de la aplicación?"),BA.ObjectToCharSequence("SALIR"),"Si, deseo salir","","No, me equivoqué",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=1376266;
 //BA.debugLineNum = 1376266;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=1376267;
 //BA.debugLineNum = 1376267;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 };
RDebugUtils.currentLine=1376270;
 //BA.debugLineNum = 1376270;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
RDebugUtils.currentLine=1376272;
 //BA.debugLineNum = 1376272;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
RDebugUtils.currentLine=1376274;
 //BA.debugLineNum = 1376274;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="frmprincipal";
RDebugUtils.currentLine=1310720;
 //BA.debugLineNum = 1310720;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=1310722;
 //BA.debugLineNum = 1310722;BA.debugLine="End Sub";
return "";
}
public static String  _activity_permissionresult(String _permission,boolean _result) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_permissionresult"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_permissionresult", new Object[] {_permission,_result}));}
RDebugUtils.currentLine=1835008;
 //BA.debugLineNum = 1835008;BA.debugLine="Sub Activity_PermissionResult (Permission As Strin";
RDebugUtils.currentLine=1835009;
 //BA.debugLineNum = 1835009;BA.debugLine="If Permission = rp.PERMISSION_ACCESS_FINE_LOCATIO";
if ((_permission).equals(_rp.PERMISSION_ACCESS_FINE_LOCATION)) { 
RDebugUtils.currentLine=1835010;
 //BA.debugLineNum = 1835010;BA.debugLine="panelExp.Initialize(\"panelExp\")";
mostCurrent._panelexp.Initialize(mostCurrent.activityBA,"panelExp");
RDebugUtils.currentLine=1835011;
 //BA.debugLineNum = 1835011;BA.debugLine="panelExp.Invalidate";
mostCurrent._panelexp.Invalidate();
RDebugUtils.currentLine=1835012;
 //BA.debugLineNum = 1835012;BA.debugLine="Activity.AddView(panelExp, 0, 170dip, 100%x, Act";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelexp.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (mostCurrent._activity.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170))));
RDebugUtils.currentLine=1835013;
 //BA.debugLineNum = 1835013;BA.debugLine="panelExp.LoadLayout(\"frmprincipal_Explorar\")";
mostCurrent._panelexp.LoadLayout("frmprincipal_Explorar",mostCurrent.activityBA);
RDebugUtils.currentLine=1835014;
 //BA.debugLineNum = 1835014;BA.debugLine="CargarMapa";
_cargarmapa();
 };
RDebugUtils.currentLine=1835016;
 //BA.debugLineNum = 1835016;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmapa() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargarmapa"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cargarmapa", null));}
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
RDebugUtils.currentLine=2228224;
 //BA.debugLineNum = 2228224;BA.debugLine="Sub CargarMapa";
RDebugUtils.currentLine=2228226;
 //BA.debugLineNum = 2228226;BA.debugLine="If LastLocation.IsInitialized Then";
if (_lastlocation.IsInitialized()) { 
RDebugUtils.currentLine=2228227;
 //BA.debugLineNum = 2228227;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
RDebugUtils.currentLine=2228228;
 //BA.debugLineNum = 2228228;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
 }else {
RDebugUtils.currentLine=2228231;
 //BA.debugLineNum = 2228231;BA.debugLine="lblLat.Text = \"-34.9204950\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence("-34.9204950"));
RDebugUtils.currentLine=2228232;
 //BA.debugLineNum = 2228232;BA.debugLine="lblLon.Text = \"-57.9535660\"";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence("-57.9535660"));
 };
RDebugUtils.currentLine=2228234;
 //BA.debugLineNum = 2228234;BA.debugLine="MapView1.Initialize(\"MapView1\")";
mostCurrent._mapview1.Initialize(mostCurrent.activityBA,"MapView1");
RDebugUtils.currentLine=2228235;
 //BA.debugLineNum = 2228235;BA.debugLine="MapView1.SetBuiltInZoomControls(True)";
mostCurrent._mapview1.SetBuiltInZoomControls(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2228236;
 //BA.debugLineNum = 2228236;BA.debugLine="MapView1.SetMultiTouchControls(True)";
mostCurrent._mapview1.SetMultiTouchControls(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2228241;
 //BA.debugLineNum = 2228241;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
RDebugUtils.currentLine=2228242;
 //BA.debugLineNum = 2228242;BA.debugLine="geopoint1.Initialize(lblLat.Text,lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
RDebugUtils.currentLine=2228243;
 //BA.debugLineNum = 2228243;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
RDebugUtils.currentLine=2228244;
 //BA.debugLineNum = 2228244;BA.debugLine="MapView1.GetController.SetZoom(6)";
mostCurrent._mapview1.GetController().SetZoom((int) (6));
RDebugUtils.currentLine=2228246;
 //BA.debugLineNum = 2228246;BA.debugLine="pnlMapa.RemoveAllViews";
mostCurrent._pnlmapa.RemoveAllViews();
RDebugUtils.currentLine=2228247;
 //BA.debugLineNum = 2228247;BA.debugLine="pnlMapa.AddView(MapView1, 0,0, 100%x, 100%y)";
mostCurrent._pnlmapa.AddView((android.view.View)(mostCurrent._mapview1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=2228248;
 //BA.debugLineNum = 2228248;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=2228250;
 //BA.debugLineNum = 2228250;BA.debugLine="SimpleLocationOverlay1.Initialize()";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
RDebugUtils.currentLine=2228251;
 //BA.debugLineNum = 2228251;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
RDebugUtils.currentLine=2228252;
 //BA.debugLineNum = 2228252;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 }else {
RDebugUtils.currentLine=2228257;
 //BA.debugLineNum = 2228257;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
RDebugUtils.currentLine=2228258;
 //BA.debugLineNum = 2228258;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 };
RDebugUtils.currentLine=2228262;
 //BA.debugLineNum = 2228262;BA.debugLine="GetMiMapa";
_getmimapa();
RDebugUtils.currentLine=2228263;
 //BA.debugLineNum = 2228263;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=1245184;
 //BA.debugLineNum = 1245184;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=1245187;
 //BA.debugLineNum = 1245187;BA.debugLine="End Sub";
return "";
}
public static String  _btnacerca_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnacerca_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnacerca_click", null));}
RDebugUtils.currentLine=3801088;
 //BA.debugLineNum = 3801088;BA.debugLine="Sub btnAcerca_Click";
RDebugUtils.currentLine=3801089;
 //BA.debugLineNum = 3801089;BA.debugLine="StartActivity(frmabout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
RDebugUtils.currentLine=3801090;
 //BA.debugLineNum = 3801090;BA.debugLine="End Sub";
return "";
}
public static String  _btnaprendermas_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnaprendermas_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnaprendermas_click", null));}
RDebugUtils.currentLine=3473408;
 //BA.debugLineNum = 3473408;BA.debugLine="Sub btnAprenderMas_Click";
RDebugUtils.currentLine=3473409;
 //BA.debugLineNum = 3473409;BA.debugLine="currentscreen = \"aprendermas\"";
mostCurrent._currentscreen = "aprendermas";
RDebugUtils.currentLine=3473410;
 //BA.debugLineNum = 3473410;BA.debugLine="frmAprender.formorigen = \"Especies\"";
mostCurrent._frmaprender._formorigen = "Especies";
RDebugUtils.currentLine=3473411;
 //BA.debugLineNum = 3473411;BA.debugLine="ToastMessageShow(\"Cargando información...\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cargando información..."),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=3473412;
 //BA.debugLineNum = 3473412;BA.debugLine="StartActivity(frmAprender)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmaprender.getObject()));
RDebugUtils.currentLine=3473414;
 //BA.debugLineNum = 3473414;BA.debugLine="End Sub";
return "";
}
public static String  _btncheckall_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncheckall_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncheckall_click", null));}
int _i = 0;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chk = null;
RDebugUtils.currentLine=3211264;
 //BA.debugLineNum = 3211264;BA.debugLine="Sub btnCheckAll_Click";
RDebugUtils.currentLine=3211266;
 //BA.debugLineNum = 3211266;BA.debugLine="If btnCheckAll.Text = \"Seleccionar todas\" Then";
if ((mostCurrent._btncheckall.getText()).equals("Seleccionar todas")) { 
RDebugUtils.currentLine=3211267;
 //BA.debugLineNum = 3211267;BA.debugLine="For i = 0 To listaConfig.Size - 1";
{
final int step2 = 1;
final int limit2 = (int) (mostCurrent._listaconfig.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
RDebugUtils.currentLine=3211268;
 //BA.debugLineNum = 3211268;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
RDebugUtils.currentLine=3211269;
 //BA.debugLineNum = 3211269;BA.debugLine="chk = listaConfig.Get(i)";
_chk.setObject((android.widget.CheckBox)(mostCurrent._listaconfig.Get(_i)));
RDebugUtils.currentLine=3211270;
 //BA.debugLineNum = 3211270;BA.debugLine="chk.Checked = True";
_chk.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }
};
RDebugUtils.currentLine=3211272;
 //BA.debugLineNum = 3211272;BA.debugLine="btnCheckAll.Text = \"Deseleccionar todas\"";
mostCurrent._btncheckall.setText(BA.ObjectToCharSequence("Deseleccionar todas"));
 }else {
RDebugUtils.currentLine=3211274;
 //BA.debugLineNum = 3211274;BA.debugLine="For i = 0 To listaConfig.Size - 1";
{
final int step9 = 1;
final int limit9 = (int) (mostCurrent._listaconfig.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit9 ;_i = _i + step9 ) {
RDebugUtils.currentLine=3211275;
 //BA.debugLineNum = 3211275;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
RDebugUtils.currentLine=3211276;
 //BA.debugLineNum = 3211276;BA.debugLine="chk = listaConfig.Get(i)";
_chk.setObject((android.widget.CheckBox)(mostCurrent._listaconfig.Get(_i)));
RDebugUtils.currentLine=3211277;
 //BA.debugLineNum = 3211277;BA.debugLine="chk.Checked = False";
_chk.setChecked(anywheresoftware.b4a.keywords.Common.False);
 }
};
RDebugUtils.currentLine=3211279;
 //BA.debugLineNum = 3211279;BA.debugLine="btnCheckAll.Text = \"Seleccionar todas\"";
mostCurrent._btncheckall.setText(BA.ObjectToCharSequence("Seleccionar todas"));
 };
RDebugUtils.currentLine=3211282;
 //BA.debugLineNum = 3211282;BA.debugLine="End Sub";
return "";
}
public static String  _btncomofotos_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncomofotos_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncomofotos_click", null));}
RDebugUtils.currentLine=3735552;
 //BA.debugLineNum = 3735552;BA.debugLine="Sub btnComoFotos_Click";
RDebugUtils.currentLine=3735553;
 //BA.debugLineNum = 3735553;BA.debugLine="StartActivity(frmComoFotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmcomofotos.getObject()));
RDebugUtils.currentLine=3735555;
 //BA.debugLineNum = 3735555;BA.debugLine="End Sub";
return "";
}
public static String  _btncomofotosreportar_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncomofotosreportar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncomofotosreportar_click", null));}
RDebugUtils.currentLine=2097152;
 //BA.debugLineNum = 2097152;BA.debugLine="Sub btnComoFotosReportar_Click";
RDebugUtils.currentLine=2097153;
 //BA.debugLineNum = 2097153;BA.debugLine="StartActivity(frmComoFotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmcomofotos.getObject()));
RDebugUtils.currentLine=2097155;
 //BA.debugLineNum = 2097155;BA.debugLine="End Sub";
return "";
}
public static String  _btnconfigexplorar_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnconfigexplorar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnconfigexplorar_click", null));}
RDebugUtils.currentLine=3080192;
 //BA.debugLineNum = 3080192;BA.debugLine="Sub btnConfigExplorar_Click";
RDebugUtils.currentLine=3080193;
 //BA.debugLineNum = 3080193;BA.debugLine="Activity.LoadLayout(\"frmprincipal_Explorar_Config";
mostCurrent._activity.LoadLayout("frmprincipal_Explorar_Config",mostCurrent.activityBA);
RDebugUtils.currentLine=3080195;
 //BA.debugLineNum = 3080195;BA.debugLine="btnCheckAll.Visible = True";
mostCurrent._btncheckall.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=3080196;
 //BA.debugLineNum = 3080196;BA.debugLine="btnMenu.Visible = False";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=3080197;
 //BA.debugLineNum = 3080197;BA.debugLine="btnZoomAll.Visible = False";
mostCurrent._btnzoomall.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=3080198;
 //BA.debugLineNum = 3080198;BA.debugLine="btnReferenciaMapa.Visible = False";
mostCurrent._btnreferenciamapa.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=3080199;
 //BA.debugLineNum = 3080199;BA.debugLine="CargarConfigExplorar";
_cargarconfigexplorar();
RDebugUtils.currentLine=3080200;
 //BA.debugLineNum = 3080200;BA.debugLine="End Sub";
return "";
}
public static String  _cargarconfigexplorar() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargarconfigexplorar"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cargarconfigexplorar", null));}
int _i = 0;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chklista = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chknew = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chk = null;
String[] _currentsp = null;
RDebugUtils.currentLine=3145728;
 //BA.debugLineNum = 3145728;BA.debugLine="Sub CargarConfigExplorar";
RDebugUtils.currentLine=3145733;
 //BA.debugLineNum = 3145733;BA.debugLine="If listaConfig.IsInitialized = False Then";
if (mostCurrent._listaconfig.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=3145734;
 //BA.debugLineNum = 3145734;BA.debugLine="listaConfig.Initialize";
mostCurrent._listaconfig.Initialize();
 }else {
RDebugUtils.currentLine=3145737;
 //BA.debugLineNum = 3145737;BA.debugLine="listaConfig.Initialize";
mostCurrent._listaconfig.Initialize();
RDebugUtils.currentLine=3145738;
 //BA.debugLineNum = 3145738;BA.debugLine="For i=0 To listaChequeados.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._listachequeados.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
RDebugUtils.currentLine=3145739;
 //BA.debugLineNum = 3145739;BA.debugLine="Dim chklista As CheckBox";
_chklista = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
RDebugUtils.currentLine=3145740;
 //BA.debugLineNum = 3145740;BA.debugLine="Dim chkNew As CheckBox";
_chknew = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
RDebugUtils.currentLine=3145741;
 //BA.debugLineNum = 3145741;BA.debugLine="chklista = listaChequeados.Get(i)";
_chklista.setObject((android.widget.CheckBox)(mostCurrent._listachequeados.Get(_i)));
RDebugUtils.currentLine=3145742;
 //BA.debugLineNum = 3145742;BA.debugLine="chkNew.Initialize(\"\")";
_chknew.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=3145743;
 //BA.debugLineNum = 3145743;BA.debugLine="chkNew.Text = chklista.Text";
_chknew.setText(BA.ObjectToCharSequence(_chklista.getText()));
RDebugUtils.currentLine=3145744;
 //BA.debugLineNum = 3145744;BA.debugLine="chkNew.TextColor = Colors.Black";
_chknew.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=3145745;
 //BA.debugLineNum = 3145745;BA.debugLine="chkNew.Checked = chklista.Checked";
_chknew.setChecked(_chklista.getChecked());
RDebugUtils.currentLine=3145746;
 //BA.debugLineNum = 3145746;BA.debugLine="listaConfig.Add(chkNew)";
mostCurrent._listaconfig.Add((Object)(_chknew.getObject()));
RDebugUtils.currentLine=3145747;
 //BA.debugLineNum = 3145747;BA.debugLine="scrConfig.Panel.AddView(chkNew,0,50dip * (i-1),";
mostCurrent._scrconfig.getPanel().AddView((android.view.View)(_chknew.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))*(_i-1)),mostCurrent._activity.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=3145748;
 //BA.debugLineNum = 3145748;BA.debugLine="scrConfig.Color = Colors.White";
mostCurrent._scrconfig.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 }
};
RDebugUtils.currentLine=3145751;
 //BA.debugLineNum = 3145751;BA.debugLine="scrConfig.Panel.Height = Main.speciesMap.Size *";
mostCurrent._scrconfig.getPanel().setHeight((int) (mostCurrent._main._speciesmap.getSize()*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
RDebugUtils.currentLine=3145752;
 //BA.debugLineNum = 3145752;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=3145755;
 //BA.debugLineNum = 3145755;BA.debugLine="For i=0 To Main.speciesMap.Size - 1";
{
final int step20 = 1;
final int limit20 = (int) (mostCurrent._main._speciesmap.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit20 ;_i = _i + step20 ) {
RDebugUtils.currentLine=3145756;
 //BA.debugLineNum = 3145756;BA.debugLine="listaChequeados.Initialize";
mostCurrent._listachequeados.Initialize();
RDebugUtils.currentLine=3145757;
 //BA.debugLineNum = 3145757;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
RDebugUtils.currentLine=3145758;
 //BA.debugLineNum = 3145758;BA.debugLine="chk.Initialize(\"\")";
_chk.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=3145759;
 //BA.debugLineNum = 3145759;BA.debugLine="Dim currentsp() As String";
_currentsp = new String[(int) (0)];
java.util.Arrays.fill(_currentsp,"");
RDebugUtils.currentLine=3145760;
 //BA.debugLineNum = 3145760;BA.debugLine="currentsp = Main.speciesMap.Get(i)";
_currentsp = (String[])(mostCurrent._main._speciesmap.Get(_i));
RDebugUtils.currentLine=3145761;
 //BA.debugLineNum = 3145761;BA.debugLine="chk.Text = currentsp(1)";
_chk.setText(BA.ObjectToCharSequence(_currentsp[(int) (1)]));
RDebugUtils.currentLine=3145762;
 //BA.debugLineNum = 3145762;BA.debugLine="chk.TextColor = Colors.DarkGray";
_chk.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
RDebugUtils.currentLine=3145763;
 //BA.debugLineNum = 3145763;BA.debugLine="chk.Checked = True";
_chk.setChecked(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=3145764;
 //BA.debugLineNum = 3145764;BA.debugLine="listaConfig.Add(chk)";
mostCurrent._listaconfig.Add((Object)(_chk.getObject()));
RDebugUtils.currentLine=3145765;
 //BA.debugLineNum = 3145765;BA.debugLine="scrConfig.Panel.AddView(chk,0,50dip * (i-1), Act";
mostCurrent._scrconfig.getPanel().AddView((android.view.View)(_chk.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))*(_i-1)),mostCurrent._activity.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 }
};
RDebugUtils.currentLine=3145767;
 //BA.debugLineNum = 3145767;BA.debugLine="scrConfig.Panel.Height = Main.speciesMap.Size * 5";
mostCurrent._scrconfig.getPanel().setHeight((int) (mostCurrent._main._speciesmap.getSize()*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
RDebugUtils.currentLine=3145768;
 //BA.debugLineNum = 3145768;BA.debugLine="End Sub";
return "";
}
public static String  _btndatosanteriores_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btndatosanteriores_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btndatosanteriores_click", null));}
RDebugUtils.currentLine=2162688;
 //BA.debugLineNum = 2162688;BA.debugLine="Sub btnDatosAnteriores_Click";
RDebugUtils.currentLine=2162690;
 //BA.debugLineNum = 2162690;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=2162691;
 //BA.debugLineNum = 2162691;BA.debugLine="frmDatosAnteriores.notificacion = False";
mostCurrent._frmdatosanteriores._notificacion = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=2162692;
 //BA.debugLineNum = 2162692;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 }else {
RDebugUtils.currentLine=2162694;
 //BA.debugLineNum = 2162694;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No ha iniciado sesión aún"),anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=2162696;
 //BA.debugLineNum = 2162696;BA.debugLine="End Sub";
return "";
}
public static String  _btndetectar_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btndetectar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btndetectar_click", null));}
RDebugUtils.currentLine=2293760;
 //BA.debugLineNum = 2293760;BA.debugLine="Sub btnDetectar_Click";
RDebugUtils.currentLine=2293761;
 //BA.debugLineNum = 2293761;BA.debugLine="DetectarPosicion";
_detectarposicion();
RDebugUtils.currentLine=2293762;
 //BA.debugLineNum = 2293762;BA.debugLine="End Sub";
return "";
}
public static String  _detectarposicion() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "detectarposicion"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "detectarposicion", null));}
RDebugUtils.currentLine=2359296;
 //BA.debugLineNum = 2359296;BA.debugLine="Sub DetectarPosicion";
RDebugUtils.currentLine=2359299;
 //BA.debugLineNum = 2359299;BA.debugLine="fondoblanco.Initialize(\"fondoblanco\")";
mostCurrent._fondoblanco.Initialize(mostCurrent.activityBA,"fondoblanco");
RDebugUtils.currentLine=2359300;
 //BA.debugLineNum = 2359300;BA.debugLine="detectandoLabel.Initialize(\"\")";
mostCurrent._detectandolabel.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2359301;
 //BA.debugLineNum = 2359301;BA.debugLine="detectandoLabel.TextColor = Colors.White";
mostCurrent._detectandolabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2359302;
 //BA.debugLineNum = 2359302;BA.debugLine="detectandoLabel.Gravity = Gravity.CENTER_HORIZONT";
mostCurrent._detectandolabel.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
RDebugUtils.currentLine=2359303;
 //BA.debugLineNum = 2359303;BA.debugLine="detectandoLabel.Text = \"Buscando tu posición auto";
mostCurrent._detectandolabel.setText(BA.ObjectToCharSequence("Buscando tu posición automáticamente..."));
RDebugUtils.currentLine=2359305;
 //BA.debugLineNum = 2359305;BA.debugLine="fondoblanco.Color = Colors.ARGB(150, 64,64,64)";
mostCurrent._fondoblanco.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
RDebugUtils.currentLine=2359306;
 //BA.debugLineNum = 2359306;BA.debugLine="Activity.AddView(fondoblanco, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondoblanco.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=2359307;
 //BA.debugLineNum = 2359307;BA.debugLine="Activity.AddView(detectandoLabel, 5%x, 55%y, 80%x";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._detectandolabel.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (55),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
RDebugUtils.currentLine=2359308;
 //BA.debugLineNum = 2359308;BA.debugLine="btnDetectar.Enabled = False";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=2359309;
 //BA.debugLineNum = 2359309;BA.debugLine="btnDetectar.Color = Colors.Black";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=2359310;
 //BA.debugLineNum = 2359310;BA.debugLine="btnDetectar.TextColor = Colors.White";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2359312;
 //BA.debugLineNum = 2359312;BA.debugLine="If FusedLocationProvider1.IsInitialized = False T";
if (_fusedlocationprovider1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=2359314;
 //BA.debugLineNum = 2359314;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProviderExplorar");
RDebugUtils.currentLine=2359315;
 //BA.debugLineNum = 2359315;BA.debugLine="Log(\"init fusedlocationproviderExplorar\")";
anywheresoftware.b4a.keywords.Common.Log("init fusedlocationproviderExplorar");
 };
RDebugUtils.currentLine=2359317;
 //BA.debugLineNum = 2359317;BA.debugLine="If FusedLocationProvider1.IsConnected = False The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=2359318;
 //BA.debugLineNum = 2359318;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
RDebugUtils.currentLine=2359319;
 //BA.debugLineNum = 2359319;BA.debugLine="Log(\"connecting fusedlocationproviderExplorar\")";
anywheresoftware.b4a.keywords.Common.Log("connecting fusedlocationproviderExplorar");
 };
RDebugUtils.currentLine=2359321;
 //BA.debugLineNum = 2359321;BA.debugLine="End Sub";
return "";
}
public static String  _btnelchagas_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnelchagas_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnelchagas_click", null));}
RDebugUtils.currentLine=3670016;
 //BA.debugLineNum = 3670016;BA.debugLine="Sub btnElChagas_Click";
RDebugUtils.currentLine=3670017;
 //BA.debugLineNum = 3670017;BA.debugLine="frmAprender.formorigen = \"Chagas\"";
mostCurrent._frmaprender._formorigen = "Chagas";
RDebugUtils.currentLine=3670018;
 //BA.debugLineNum = 3670018;BA.debugLine="StartActivity(frmAprender)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmaprender.getObject()));
RDebugUtils.currentLine=3670019;
 //BA.debugLineNum = 3670019;BA.debugLine="End Sub";
return "";
}
public static String  _btnelproyecto_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnelproyecto_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnelproyecto_click", null));}
RDebugUtils.currentLine=3932160;
 //BA.debugLineNum = 3932160;BA.debugLine="Sub btnElproyecto_Click";
RDebugUtils.currentLine=3932161;
 //BA.debugLineNum = 3932161;BA.debugLine="StartActivity(frmabout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
RDebugUtils.currentLine=3932162;
 //BA.debugLineNum = 3932162;BA.debugLine="End Sub";
return "";
}
public static String  _btninformevinchuca_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btninformevinchuca_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btninformevinchuca_click", null));}
RDebugUtils.currentLine=1900544;
 //BA.debugLineNum = 1900544;BA.debugLine="Sub btnInformeVinchuca_Click";
RDebugUtils.currentLine=1900545;
 //BA.debugLineNum = 1900545;BA.debugLine="ComenzarReporte";
_comenzarreporte();
RDebugUtils.currentLine=1900546;
 //BA.debugLineNum = 1900546;BA.debugLine="End Sub";
return "";
}
public static String  _comenzarreporte() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "comenzarreporte"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "comenzarreporte", null));}
anywheresoftware.b4a.objects.collections.List _neweval = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _currentprojectmap = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=2031616;
 //BA.debugLineNum = 2031616;BA.debugLine="Sub ComenzarReporte";
RDebugUtils.currentLine=2031617;
 //BA.debugLineNum = 2031617;BA.debugLine="Main.fotopath0 = \"\"";
mostCurrent._main._fotopath0 = "";
RDebugUtils.currentLine=2031618;
 //BA.debugLineNum = 2031618;BA.debugLine="Main.fotopath1 = \"\"";
mostCurrent._main._fotopath1 = "";
RDebugUtils.currentLine=2031619;
 //BA.debugLineNum = 2031619;BA.debugLine="Main.fotopath2 = \"\"";
mostCurrent._main._fotopath2 = "";
RDebugUtils.currentLine=2031620;
 //BA.debugLineNum = 2031620;BA.debugLine="Main.fotopath3 = \"\"";
mostCurrent._main._fotopath3 = "";
RDebugUtils.currentLine=2031621;
 //BA.debugLineNum = 2031621;BA.debugLine="Main.latitud = \"\"";
mostCurrent._main._latitud = "";
RDebugUtils.currentLine=2031622;
 //BA.debugLineNum = 2031622;BA.debugLine="Main.longitud = \"\"";
mostCurrent._main._longitud = "";
RDebugUtils.currentLine=2031627;
 //BA.debugLineNum = 2031627;BA.debugLine="Dim newEval As List";
_neweval = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=2031628;
 //BA.debugLineNum = 2031628;BA.debugLine="newEval.Initialize";
_neweval.Initialize();
RDebugUtils.currentLine=2031629;
 //BA.debugLineNum = 2031629;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=2031630;
 //BA.debugLineNum = 2031630;BA.debugLine="m.Initialize";
_m.Initialize();
RDebugUtils.currentLine=2031631;
 //BA.debugLineNum = 2031631;BA.debugLine="m.Put(\"usuario\", Main.username)";
_m.Put((Object)("usuario"),(Object)(mostCurrent._main._username));
RDebugUtils.currentLine=2031632;
 //BA.debugLineNum = 2031632;BA.debugLine="m.Put(\"tipoEval\", \"organismo\")";
_m.Put((Object)("tipoEval"),(Object)("organismo"));
RDebugUtils.currentLine=2031633;
 //BA.debugLineNum = 2031633;BA.debugLine="newEval.Add(m)";
_neweval.Add((Object)(_m.getObject()));
RDebugUtils.currentLine=2031634;
 //BA.debugLineNum = 2031634;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"markers_local\",";
mostCurrent._dbutils._insertmaps(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local",_neweval,anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2031638;
 //BA.debugLineNum = 2031638;BA.debugLine="Dim currentprojectMap As Map";
_currentprojectmap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=2031639;
 //BA.debugLineNum = 2031639;BA.debugLine="currentprojectMap.Initialize";
_currentprojectmap.Initialize();
RDebugUtils.currentLine=2031640;
 //BA.debugLineNum = 2031640;BA.debugLine="currentprojectMap = DBUtils.ExecuteMap(Starter.sq";
_currentprojectmap = mostCurrent._dbutils._executemap(mostCurrent.activityBA,mostCurrent._starter._sqldb,"SELECT * FROM markers_local ORDER BY id DESC LIMIT 1",(String[])(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=2031641;
 //BA.debugLineNum = 2031641;BA.debugLine="Main.currentproject = currentprojectMap.Get(\"id\")";
mostCurrent._main._currentproject = BA.ObjectToString(_currentprojectmap.Get((Object)("id")));
RDebugUtils.currentLine=2031642;
 //BA.debugLineNum = 2031642;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=2031643;
 //BA.debugLineNum = 2031643;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=2031644;
 //BA.debugLineNum = 2031644;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=2031645;
 //BA.debugLineNum = 2031645;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
RDebugUtils.currentLine=2031646;
 //BA.debugLineNum = 2031646;BA.debugLine="Main.datecurrentproject = DateTime.Date(DateTime.";
mostCurrent._main._datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
RDebugUtils.currentLine=2031647;
 //BA.debugLineNum = 2031647;BA.debugLine="fullidcurrentproject = Main.username & \"_\" & Main";
_fullidcurrentproject = mostCurrent._main._username+"_"+mostCurrent._main._currentproject+"_"+mostCurrent._main._datecurrentproject;
RDebugUtils.currentLine=2031648;
 //BA.debugLineNum = 2031648;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","fullID",(Object)(_fullidcurrentproject),_map1);
RDebugUtils.currentLine=2031651;
 //BA.debugLineNum = 2031651;BA.debugLine="Main.Idproyecto = Main.currentproject";
mostCurrent._main._idproyecto = (int)(Double.parseDouble(mostCurrent._main._currentproject));
RDebugUtils.currentLine=2031652;
 //BA.debugLineNum = 2031652;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
RDebugUtils.currentLine=2031654;
 //BA.debugLineNum = 2031654;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasinfoonline_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnmasinfoonline_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnmasinfoonline_click", null));}
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
RDebugUtils.currentLine=3538944;
 //BA.debugLineNum = 3538944;BA.debugLine="Sub btnMasInfoOnline_Click";
RDebugUtils.currentLine=3538945;
 //BA.debugLineNum = 3538945;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
RDebugUtils.currentLine=3538946;
 //BA.debugLineNum = 3538946;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.geovin.co";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser("http://www.geovin.com.ar/")));
RDebugUtils.currentLine=3538947;
 //BA.debugLineNum = 3538947;BA.debugLine="End Sub";
return "";
}
public static String  _btnmenu_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnmenu_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnmenu_click", null));}
RDebugUtils.currentLine=1441792;
 //BA.debugLineNum = 1441792;BA.debugLine="Sub btnMenu_Click";
RDebugUtils.currentLine=1441793;
 //BA.debugLineNum = 1441793;BA.debugLine="Activity.OpenMenu";
mostCurrent._activity.OpenMenu();
RDebugUtils.currentLine=1441794;
 //BA.debugLineNum = 1441794;BA.debugLine="End Sub";
return "";
}
public static String  _btnmiperfil_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnmiperfil_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnmiperfil_click", null));}
RDebugUtils.currentLine=3866624;
 //BA.debugLineNum = 3866624;BA.debugLine="Sub btnMiPerfil_Click";
RDebugUtils.currentLine=3866625;
 //BA.debugLineNum = 3866625;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=3866626;
 //BA.debugLineNum = 3866626;BA.debugLine="frmDatosAnteriores.notificacion = False";
mostCurrent._frmdatosanteriores._notificacion = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=3866627;
 //BA.debugLineNum = 3866627;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 }else {
RDebugUtils.currentLine=3866629;
 //BA.debugLineNum = 3866629;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No ha iniciado sesión aún"),anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=3866631;
 //BA.debugLineNum = 3866631;BA.debugLine="End Sub";
return "";
}
public static String  _btnokconfig_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnokconfig_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnokconfig_click", null));}
anywheresoftware.b4a.objects.collections.List _listmostrar = null;
int _i = 0;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chk = null;
String _spname = "";
anywheresoftware.b4a.objects.collections.List _listmostrarconfig = null;
String _itemamostrar = "";
int _j = 0;
uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker _markeramostrar = null;
RDebugUtils.currentLine=3276800;
 //BA.debugLineNum = 3276800;BA.debugLine="Sub btnOkConfig_Click";
RDebugUtils.currentLine=3276803;
 //BA.debugLineNum = 3276803;BA.debugLine="ProgressDialogShow(\"Actualizando mapa...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Actualizando mapa..."));
RDebugUtils.currentLine=3276804;
 //BA.debugLineNum = 3276804;BA.debugLine="ToastMessageShow(\"Actualizando mapa...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Actualizando mapa..."),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=3276805;
 //BA.debugLineNum = 3276805;BA.debugLine="Dim listMostrar As List";
_listmostrar = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=3276806;
 //BA.debugLineNum = 3276806;BA.debugLine="listMostrar.Initialize";
_listmostrar.Initialize();
RDebugUtils.currentLine=3276807;
 //BA.debugLineNum = 3276807;BA.debugLine="listaChequeados.Initialize";
mostCurrent._listachequeados.Initialize();
RDebugUtils.currentLine=3276811;
 //BA.debugLineNum = 3276811;BA.debugLine="For i = 0 To listaConfig.Size - 1";
{
final int step6 = 1;
final int limit6 = (int) (mostCurrent._listaconfig.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
RDebugUtils.currentLine=3276812;
 //BA.debugLineNum = 3276812;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
RDebugUtils.currentLine=3276813;
 //BA.debugLineNum = 3276813;BA.debugLine="chk = listaConfig.Get(i)";
_chk.setObject((android.widget.CheckBox)(mostCurrent._listaconfig.Get(_i)));
RDebugUtils.currentLine=3276814;
 //BA.debugLineNum = 3276814;BA.debugLine="listaChequeados.Add(chk)";
mostCurrent._listachequeados.Add((Object)(_chk.getObject()));
RDebugUtils.currentLine=3276815;
 //BA.debugLineNum = 3276815;BA.debugLine="If chk.Checked = True Then";
if (_chk.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=3276817;
 //BA.debugLineNum = 3276817;BA.debugLine="Dim spname As String";
_spname = "";
RDebugUtils.currentLine=3276818;
 //BA.debugLineNum = 3276818;BA.debugLine="spname = chk.Text.SubString2(chk.Text.LastIndex";
_spname = _chk.getText().substring(_chk.getText().lastIndexOf(" "),_chk.getText().length());
RDebugUtils.currentLine=3276819;
 //BA.debugLineNum = 3276819;BA.debugLine="If spname.EndsWith(\"*\") Then";
if (_spname.endsWith("*")) { 
RDebugUtils.currentLine=3276820;
 //BA.debugLineNum = 3276820;BA.debugLine="spname = spname.SubString2(0, spname.Length -";
_spname = _spname.substring((int) (0),(int) (_spname.length()-1));
 };
RDebugUtils.currentLine=3276822;
 //BA.debugLineNum = 3276822;BA.debugLine="If spname.Contains(\"patagónica\") Then";
if (_spname.contains("patagónica")) { 
RDebugUtils.currentLine=3276823;
 //BA.debugLineNum = 3276823;BA.debugLine="spname = \"patagonica\"";
_spname = "patagonica";
 };
RDebugUtils.currentLine=3276825;
 //BA.debugLineNum = 3276825;BA.debugLine="listMostrar.Add(spname)";
_listmostrar.Add((Object)(_spname));
 };
 }
};
RDebugUtils.currentLine=3276830;
 //BA.debugLineNum = 3276830;BA.debugLine="Dim listMostrarConfig As List";
_listmostrarconfig = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=3276831;
 //BA.debugLineNum = 3276831;BA.debugLine="listMostrarConfig.Initialize";
_listmostrarconfig.Initialize();
RDebugUtils.currentLine=3276833;
 //BA.debugLineNum = 3276833;BA.debugLine="If chkDatosGeoVin.Checked = True Then";
if (mostCurrent._chkdatosgeovin.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=3276834;
 //BA.debugLineNum = 3276834;BA.debugLine="listMostrarConfig.Add(\"geovin\")";
_listmostrarconfig.Add((Object)("geovin"));
 }else {
RDebugUtils.currentLine=3276836;
 //BA.debugLineNum = 3276836;BA.debugLine="listMostrarConfig.Add(\"no\")";
_listmostrarconfig.Add((Object)("no"));
 };
RDebugUtils.currentLine=3276838;
 //BA.debugLineNum = 3276838;BA.debugLine="If chkDatosPropios.Checked = True Then";
if (mostCurrent._chkdatospropios.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=3276839;
 //BA.debugLineNum = 3276839;BA.debugLine="listMostrarConfig.Add(\"usuario\")";
_listmostrarconfig.Add((Object)("usuario"));
 }else {
RDebugUtils.currentLine=3276841;
 //BA.debugLineNum = 3276841;BA.debugLine="listMostrarConfig.Add(\"no\")";
_listmostrarconfig.Add((Object)("no"));
 };
RDebugUtils.currentLine=3276843;
 //BA.debugLineNum = 3276843;BA.debugLine="If chkDatosUsuarios.Checked = True Then";
if (mostCurrent._chkdatosusuarios.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=3276844;
 //BA.debugLineNum = 3276844;BA.debugLine="listMostrarConfig.Add(\"otrosusuarios\")";
_listmostrarconfig.Add((Object)("otrosusuarios"));
 }else {
RDebugUtils.currentLine=3276846;
 //BA.debugLineNum = 3276846;BA.debugLine="listMostrarConfig.Add(\"no\")";
_listmostrarconfig.Add((Object)("no"));
 };
RDebugUtils.currentLine=3276849;
 //BA.debugLineNum = 3276849;BA.debugLine="pnlConfig.RemoveView";
mostCurrent._pnlconfig.RemoveView();
RDebugUtils.currentLine=3276851;
 //BA.debugLineNum = 3276851;BA.debugLine="btnCheckAll.Visible = False";
mostCurrent._btncheckall.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=3276852;
 //BA.debugLineNum = 3276852;BA.debugLine="btnZoomAll.Visible = True";
mostCurrent._btnzoomall.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=3276853;
 //BA.debugLineNum = 3276853;BA.debugLine="btnReferenciaMapa.Visible = True";
mostCurrent._btnreferenciamapa.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=3276857;
 //BA.debugLineNum = 3276857;BA.debugLine="MapView1.GetOverlays.Remove(markersOverlay)";
mostCurrent._mapview1.GetOverlays().Remove((org.osmdroid.views.overlay.Overlay)(mostCurrent._markersoverlay.getObject()));
RDebugUtils.currentLine=3276858;
 //BA.debugLineNum = 3276858;BA.debugLine="markersOverlay.RemoveAllItems";
mostCurrent._markersoverlay.RemoveAllItems();
RDebugUtils.currentLine=3276864;
 //BA.debugLineNum = 3276864;BA.debugLine="For i = 0 To 250";
{
final int step45 = 1;
final int limit45 = (int) (250);
_i = (int) (0) ;
for (;_i <= limit45 ;_i = _i + step45 ) {
RDebugUtils.currentLine=3276870;
 //BA.debugLineNum = 3276870;BA.debugLine="Dim itemAMostrar As String";
_itemamostrar = "";
RDebugUtils.currentLine=3276871;
 //BA.debugLineNum = 3276871;BA.debugLine="itemAMostrar = listMostrar.Get(i)";
_itemamostrar = BA.ObjectToString(_listmostrar.Get(_i));
RDebugUtils.currentLine=3276872;
 //BA.debugLineNum = 3276872;BA.debugLine="itemAMostrar = itemAMostrar.Trim";
_itemamostrar = _itemamostrar.trim();
RDebugUtils.currentLine=3276873;
 //BA.debugLineNum = 3276873;BA.debugLine="For j = 0 To markersList.Size - 1";
{
final int step49 = 1;
final int limit49 = (int) (mostCurrent._markerslist.getSize()-1);
_j = (int) (0) ;
for (;_j <= limit49 ;_j = _j + step49 ) {
RDebugUtils.currentLine=3276875;
 //BA.debugLineNum = 3276875;BA.debugLine="Dim markerAMostrar As OSMDroid_Marker";
_markeramostrar = new uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker();
RDebugUtils.currentLine=3276876;
 //BA.debugLineNum = 3276876;BA.debugLine="markerAMostrar = markersList.Get(j)";
_markeramostrar.setObject((uk.co.martinpearman.osmdroid.views.overlay.Marker)(mostCurrent._markerslist.Get(_j)));
RDebugUtils.currentLine=3276877;
 //BA.debugLineNum = 3276877;BA.debugLine="If markerAMostrar.GetTitle = itemAMostrar Then";
if ((_markeramostrar.GetTitle()).equals(_itemamostrar)) { 
RDebugUtils.currentLine=3276879;
 //BA.debugLineNum = 3276879;BA.debugLine="If markerAMostrar.GetSnippet = \"geovin\" And li";
if ((_markeramostrar.GetSnippet()).equals("geovin") && (_listmostrarconfig.Get((int) (0))).equals((Object)("geovin"))) { 
RDebugUtils.currentLine=3276880;
 //BA.debugLineNum = 3276880;BA.debugLine="markersOverlay.AddItem(markerAMostrar)";
mostCurrent._markersoverlay.AddItem((uk.co.martinpearman.osmdroid.views.overlay.Marker)(_markeramostrar.getObject()));
 }else 
{RDebugUtils.currentLine=3276881;
 //BA.debugLineNum = 3276881;BA.debugLine="Else If markerAMostrar.GetSnippet = Main.usern";
if ((_markeramostrar.GetSnippet()).equals(mostCurrent._main._username) && (_listmostrarconfig.Get((int) (1))).equals((Object)("usuario"))) { 
RDebugUtils.currentLine=3276882;
 //BA.debugLineNum = 3276882;BA.debugLine="markersOverlay.AddItem(markerAMostrar)";
mostCurrent._markersoverlay.AddItem((uk.co.martinpearman.osmdroid.views.overlay.Marker)(_markeramostrar.getObject()));
 }else 
{RDebugUtils.currentLine=3276883;
 //BA.debugLineNum = 3276883;BA.debugLine="Else if markerAMostrar.GetSnippet <> \"geovin\"";
if ((_markeramostrar.GetSnippet()).equals("geovin") == false && (_markeramostrar.GetSnippet()).equals(mostCurrent._main._username) == false && (_listmostrarconfig.Get((int) (2))).equals((Object)("otrosusuarios"))) { 
RDebugUtils.currentLine=3276884;
 //BA.debugLineNum = 3276884;BA.debugLine="markersOverlay.AddItem(markerAMostrar)";
mostCurrent._markersoverlay.AddItem((uk.co.martinpearman.osmdroid.views.overlay.Marker)(_markeramostrar.getObject()));
 }}}
;
 };
 }
};
 }
};
RDebugUtils.currentLine=3276891;
 //BA.debugLineNum = 3276891;BA.debugLine="ToastMessageShow(\"Se encontraron \" & (markersOver";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se encontraron "+BA.NumberToString((mostCurrent._markersoverlay.Size()))+" observaciones"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=3276892;
 //BA.debugLineNum = 3276892;BA.debugLine="MapView1.GetOverlays.Add(markersOverlay)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._markersoverlay.getObject()));
RDebugUtils.currentLine=3276893;
 //BA.debugLineNum = 3276893;BA.debugLine="MapView1.Invalidate";
mostCurrent._mapview1.Invalidate();
RDebugUtils.currentLine=3276894;
 //BA.debugLineNum = 3276894;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=3276895;
 //BA.debugLineNum = 3276895;BA.debugLine="End Sub";
return "";
}
public static String  _btnrecomendaciones_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnrecomendaciones_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnrecomendaciones_click", null));}
RDebugUtils.currentLine=3604480;
 //BA.debugLineNum = 3604480;BA.debugLine="Sub btnRecomendaciones_Click";
RDebugUtils.currentLine=3604481;
 //BA.debugLineNum = 3604481;BA.debugLine="frmAprender.formorigen = \"Recomendaciones\"";
mostCurrent._frmaprender._formorigen = "Recomendaciones";
RDebugUtils.currentLine=3604482;
 //BA.debugLineNum = 3604482;BA.debugLine="StartActivity(frmAprender)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmaprender.getObject()));
RDebugUtils.currentLine=3604483;
 //BA.debugLineNum = 3604483;BA.debugLine="End Sub";
return "";
}
public static String  _btnreferenciamapa_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnreferenciamapa_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnreferenciamapa_click", null));}
RDebugUtils.currentLine=3342336;
 //BA.debugLineNum = 3342336;BA.debugLine="Sub btnReferenciaMapa_Click";
RDebugUtils.currentLine=3342337;
 //BA.debugLineNum = 3342337;BA.debugLine="Activity.LoadLayout(\"frmPrincipal_Explorar_Refere";
mostCurrent._activity.LoadLayout("frmPrincipal_Explorar_Referencia",mostCurrent.activityBA);
RDebugUtils.currentLine=3342338;
 //BA.debugLineNum = 3342338;BA.debugLine="btnMenu.Visible = False";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=3342358;
 //BA.debugLineNum = 3342358;BA.debugLine="End Sub";
return "";
}
public static String  _btnzoomall_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnzoomall_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnzoomall_click", null));}
RDebugUtils.currentLine=3014656;
 //BA.debugLineNum = 3014656;BA.debugLine="Sub btnZoomAll_Click";
RDebugUtils.currentLine=3014657;
 //BA.debugLineNum = 3014657;BA.debugLine="MapView1.GetController.SetZoom(5)";
mostCurrent._mapview1.GetController().SetZoom((int) (5));
RDebugUtils.currentLine=3014658;
 //BA.debugLineNum = 3014658;BA.debugLine="End Sub";
return "";
}
public static String  _butcerrarreferencia_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "butcerrarreferencia_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "butcerrarreferencia_click", null));}
RDebugUtils.currentLine=3407872;
 //BA.debugLineNum = 3407872;BA.debugLine="Sub butCerrarReferencia_Click";
RDebugUtils.currentLine=3407873;
 //BA.debugLineNum = 3407873;BA.debugLine="pnlReferencias.RemoveView";
mostCurrent._pnlreferencias.RemoveView();
RDebugUtils.currentLine=3407874;
 //BA.debugLineNum = 3407874;BA.debugLine="btnMenu.Visible = True";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=3407875;
 //BA.debugLineNum = 3407875;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "button1_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "button1_click", null));}
RDebugUtils.currentLine=1966080;
 //BA.debugLineNum = 1966080;BA.debugLine="Sub Button1_Click";
RDebugUtils.currentLine=1966081;
 //BA.debugLineNum = 1966081;BA.debugLine="ComenzarReporte";
_comenzarreporte();
RDebugUtils.currentLine=1966082;
 //BA.debugLineNum = 1966082;BA.debugLine="End Sub";
return "";
}
public static void  _getmimapa() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "getmimapa"))
	 {Debug.delegate(mostCurrent.activityBA, "getmimapa", null); return;}
ResumableSub_GetMiMapa rsub = new ResumableSub_GetMiMapa(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_GetMiMapa extends BA.ResumableSub {
public ResumableSub_GetMiMapa(cepave.geovin.frmprincipal parent) {
this.parent = parent;
}
cepave.geovin.frmprincipal parent;
anywheresoftware.b4a.samples.httputils2.httpjob _getmapa = null;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_1 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_3 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_4 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_5 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_6 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_7 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_8 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_9 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_10 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_11 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_12 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_13 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_14 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_15 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergv_16 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker1 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker3 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker4 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker5 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker6 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker7 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker8 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker9 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker10 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker11 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker12 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker13 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker14 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker15 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker16 = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv1dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv2dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv3dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv4dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv5dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv6dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv7dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv8dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv9dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv10dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv11dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv12dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv13dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv14dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv15dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _markergv16dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker1dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker2dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker3dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker4dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker5dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker6dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker7dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker8dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker9dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker10dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker11dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker12dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker13dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker14dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker15dr = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker16dr = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
double _sitiolat = 0;
double _sitiolong = 0;
String _valorvinchuca = "";
String _nombreusuario = "";
uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker _marker = null;
int step152;
int limit152;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="frmprincipal";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=2949121;
 //BA.debugLineNum = 2949121;BA.debugLine="ProgressDialogShow(\"Buscando puntos cercanos...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando puntos cercanos..."));
RDebugUtils.currentLine=2949123;
 //BA.debugLineNum = 2949123;BA.debugLine="btnDetectar.Enabled = False";
parent.mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=2949124;
 //BA.debugLineNum = 2949124;BA.debugLine="Dim GetMapa As HttpJob";
_getmapa = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=2949125;
 //BA.debugLineNum = 2949125;BA.debugLine="GetMapa.Initialize(\"\", Me)";
_getmapa._initialize(processBA,"",frmprincipal.getObject());
RDebugUtils.currentLine=2949129;
 //BA.debugLineNum = 2949129;BA.debugLine="GetMapa.Download(\"http://appearcomar.ipage.com/Ge";
_getmapa._download("http://appearcomar.ipage.com/GeoVin/connect/getallmapa.php");
RDebugUtils.currentLine=2949130;
 //BA.debugLineNum = 2949130;BA.debugLine="Wait For (GetMapa) JobDone(GetMapa As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "frmprincipal", "getmimapa"), (Object)(_getmapa));
this.state = 97;
return;
case 97:
//C
this.state = 1;
_getmapa = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=2949132;
 //BA.debugLineNum = 2949132;BA.debugLine="If GetMapa.Success Then";
if (true) break;

case 1:
//if
this.state = 92;
if (_getmapa._success) { 
this.state = 3;
}else {
this.state = 91;
}if (true) break;

case 3:
//C
this.state = 4;
RDebugUtils.currentLine=2949134;
 //BA.debugLineNum = 2949134;BA.debugLine="Dim ret As String";
_ret = "";
RDebugUtils.currentLine=2949135;
 //BA.debugLineNum = 2949135;BA.debugLine="Dim act As String";
_act = "";
RDebugUtils.currentLine=2949136;
 //BA.debugLineNum = 2949136;BA.debugLine="ret = GetMapa.GetString";
_ret = _getmapa._getstring();
RDebugUtils.currentLine=2949137;
 //BA.debugLineNum = 2949137;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=2949138;
 //BA.debugLineNum = 2949138;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
RDebugUtils.currentLine=2949139;
 //BA.debugLineNum = 2949139;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=2949140;
 //BA.debugLineNum = 2949140;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 4:
//if
this.state = 89;
if ((_act).equals("Not Found")) { 
this.state = 6;
}else 
{RDebugUtils.currentLine=2949142;
 //BA.debugLineNum = 2949142;BA.debugLine="Else If act = \"Error\" Then";
if ((_act).equals("Error")) { 
this.state = 8;
}else 
{RDebugUtils.currentLine=2949144;
 //BA.debugLineNum = 2949144;BA.debugLine="Else If act = \"GetMapaOk\" Then";
if ((_act).equals("GetMapaOk")) { 
this.state = 10;
}}}
if (true) break;

case 6:
//C
this.state = 89;
RDebugUtils.currentLine=2949141;
 //BA.debugLineNum = 2949141;BA.debugLine="ToastMessageShow(\"No encuentro sitios anterior";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 8:
//C
this.state = 89;
RDebugUtils.currentLine=2949143;
 //BA.debugLineNum = 2949143;BA.debugLine="ToastMessageShow(\"No encuentro sitios anterior";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 10:
//C
this.state = 11;
RDebugUtils.currentLine=2949146;
 //BA.debugLineNum = 2949146;BA.debugLine="Dim numresults As String";
_numresults = "";
RDebugUtils.currentLine=2949147;
 //BA.debugLineNum = 2949147;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=2949152;
 //BA.debugLineNum = 2949152;BA.debugLine="markersOverlay.Initialize(\"markersOverlay\", Map";
parent.mostCurrent._markersoverlay.Initialize(processBA,"markersOverlay",(org.osmdroid.views.MapView)(parent.mostCurrent._mapview1.getObject()));
RDebugUtils.currentLine=2949153;
 //BA.debugLineNum = 2949153;BA.debugLine="MapView1.GetOverlays.Add(markersOverlay)";
parent.mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(parent.mostCurrent._markersoverlay.getObject()));
RDebugUtils.currentLine=2949156;
 //BA.debugLineNum = 2949156;BA.debugLine="markersList.Initialize()";
parent.mostCurrent._markerslist.Initialize();
RDebugUtils.currentLine=2949159;
 //BA.debugLineNum = 2949159;BA.debugLine="Dim markerGV_1 As Bitmap";
_markergv_1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949160;
 //BA.debugLineNum = 2949160;BA.debugLine="Dim markerGV_2 As Bitmap";
_markergv_2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949161;
 //BA.debugLineNum = 2949161;BA.debugLine="Dim markerGV_3 As Bitmap";
_markergv_3 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949162;
 //BA.debugLineNum = 2949162;BA.debugLine="Dim markerGV_4 As Bitmap";
_markergv_4 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949163;
 //BA.debugLineNum = 2949163;BA.debugLine="Dim markerGV_5 As Bitmap";
_markergv_5 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949164;
 //BA.debugLineNum = 2949164;BA.debugLine="Dim markerGV_6 As Bitmap";
_markergv_6 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949165;
 //BA.debugLineNum = 2949165;BA.debugLine="Dim markerGV_7 As Bitmap";
_markergv_7 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949166;
 //BA.debugLineNum = 2949166;BA.debugLine="Dim markerGV_8 As Bitmap";
_markergv_8 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949167;
 //BA.debugLineNum = 2949167;BA.debugLine="Dim markerGV_9 As Bitmap";
_markergv_9 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949168;
 //BA.debugLineNum = 2949168;BA.debugLine="Dim markerGV_10 As Bitmap";
_markergv_10 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949169;
 //BA.debugLineNum = 2949169;BA.debugLine="Dim markerGV_11 As Bitmap";
_markergv_11 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949170;
 //BA.debugLineNum = 2949170;BA.debugLine="Dim markerGV_12 As Bitmap";
_markergv_12 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949171;
 //BA.debugLineNum = 2949171;BA.debugLine="Dim markerGV_13 As Bitmap";
_markergv_13 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949172;
 //BA.debugLineNum = 2949172;BA.debugLine="Dim markerGV_14 As Bitmap";
_markergv_14 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949173;
 //BA.debugLineNum = 2949173;BA.debugLine="Dim markerGV_15 As Bitmap";
_markergv_15 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949174;
 //BA.debugLineNum = 2949174;BA.debugLine="Dim markerGV_16 As Bitmap";
_markergv_16 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949176;
 //BA.debugLineNum = 2949176;BA.debugLine="Dim marker1 As Bitmap";
_marker1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949177;
 //BA.debugLineNum = 2949177;BA.debugLine="Dim marker2 As Bitmap";
_marker2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949178;
 //BA.debugLineNum = 2949178;BA.debugLine="Dim marker3 As Bitmap";
_marker3 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949179;
 //BA.debugLineNum = 2949179;BA.debugLine="Dim marker4 As Bitmap";
_marker4 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949180;
 //BA.debugLineNum = 2949180;BA.debugLine="Dim marker5 As Bitmap";
_marker5 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949181;
 //BA.debugLineNum = 2949181;BA.debugLine="Dim marker6 As Bitmap";
_marker6 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949182;
 //BA.debugLineNum = 2949182;BA.debugLine="Dim marker7 As Bitmap";
_marker7 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949183;
 //BA.debugLineNum = 2949183;BA.debugLine="Dim marker8 As Bitmap";
_marker8 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949184;
 //BA.debugLineNum = 2949184;BA.debugLine="Dim marker9 As Bitmap";
_marker9 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949185;
 //BA.debugLineNum = 2949185;BA.debugLine="Dim marker10 As Bitmap";
_marker10 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949186;
 //BA.debugLineNum = 2949186;BA.debugLine="Dim marker11 As Bitmap";
_marker11 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949187;
 //BA.debugLineNum = 2949187;BA.debugLine="Dim marker12 As Bitmap";
_marker12 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949188;
 //BA.debugLineNum = 2949188;BA.debugLine="Dim marker13 As Bitmap";
_marker13 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949189;
 //BA.debugLineNum = 2949189;BA.debugLine="Dim marker14 As Bitmap";
_marker14 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949190;
 //BA.debugLineNum = 2949190;BA.debugLine="Dim marker15 As Bitmap";
_marker15 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949191;
 //BA.debugLineNum = 2949191;BA.debugLine="Dim marker16 As Bitmap";
_marker16 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=2949194;
 //BA.debugLineNum = 2949194;BA.debugLine="markerGV_1.Initialize(File.DirAssets, \"markerGV";
_markergv_1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_1.png");
RDebugUtils.currentLine=2949195;
 //BA.debugLineNum = 2949195;BA.debugLine="markerGV_2.Initialize(File.DirAssets, \"markerGV";
_markergv_2.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_2.png");
RDebugUtils.currentLine=2949196;
 //BA.debugLineNum = 2949196;BA.debugLine="markerGV_3.Initialize(File.DirAssets, \"markerGV";
_markergv_3.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_3.png");
RDebugUtils.currentLine=2949197;
 //BA.debugLineNum = 2949197;BA.debugLine="markerGV_4.Initialize(File.DirAssets, \"markerGV";
_markergv_4.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_4.png");
RDebugUtils.currentLine=2949198;
 //BA.debugLineNum = 2949198;BA.debugLine="markerGV_5.Initialize(File.DirAssets, \"markerGV";
_markergv_5.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_5.png");
RDebugUtils.currentLine=2949199;
 //BA.debugLineNum = 2949199;BA.debugLine="markerGV_6.Initialize(File.DirAssets, \"markerGV";
_markergv_6.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_6.png");
RDebugUtils.currentLine=2949200;
 //BA.debugLineNum = 2949200;BA.debugLine="markerGV_7.Initialize(File.DirAssets, \"markerGV";
_markergv_7.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_7.png");
RDebugUtils.currentLine=2949201;
 //BA.debugLineNum = 2949201;BA.debugLine="markerGV_8.Initialize(File.DirAssets, \"markerGV";
_markergv_8.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_8.png");
RDebugUtils.currentLine=2949202;
 //BA.debugLineNum = 2949202;BA.debugLine="markerGV_9.Initialize(File.DirAssets, \"markerGV";
_markergv_9.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_9.png");
RDebugUtils.currentLine=2949203;
 //BA.debugLineNum = 2949203;BA.debugLine="markerGV_10.Initialize(File.DirAssets, \"markerG";
_markergv_10.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_10.png");
RDebugUtils.currentLine=2949204;
 //BA.debugLineNum = 2949204;BA.debugLine="markerGV_11.Initialize(File.DirAssets, \"markerG";
_markergv_11.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_11.png");
RDebugUtils.currentLine=2949205;
 //BA.debugLineNum = 2949205;BA.debugLine="markerGV_12.Initialize(File.DirAssets, \"markerG";
_markergv_12.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_12.png");
RDebugUtils.currentLine=2949206;
 //BA.debugLineNum = 2949206;BA.debugLine="markerGV_13.Initialize(File.DirAssets, \"markerG";
_markergv_13.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_13.png");
RDebugUtils.currentLine=2949207;
 //BA.debugLineNum = 2949207;BA.debugLine="markerGV_14.Initialize(File.DirAssets, \"markerG";
_markergv_14.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_14.png");
RDebugUtils.currentLine=2949208;
 //BA.debugLineNum = 2949208;BA.debugLine="markerGV_15.Initialize(File.DirAssets, \"markerG";
_markergv_15.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_15.png");
RDebugUtils.currentLine=2949209;
 //BA.debugLineNum = 2949209;BA.debugLine="markerGV_16.Initialize(File.DirAssets, \"markerG";
_markergv_16.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_16.png");
RDebugUtils.currentLine=2949211;
 //BA.debugLineNum = 2949211;BA.debugLine="marker1.Initialize(File.DirAssets, \"marker1.png";
_marker1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker1.png");
RDebugUtils.currentLine=2949212;
 //BA.debugLineNum = 2949212;BA.debugLine="marker2.Initialize(File.DirAssets, \"marker2.png";
_marker2.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png");
RDebugUtils.currentLine=2949213;
 //BA.debugLineNum = 2949213;BA.debugLine="marker3.Initialize(File.DirAssets, \"marker3.png";
_marker3.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker3.png");
RDebugUtils.currentLine=2949214;
 //BA.debugLineNum = 2949214;BA.debugLine="marker4.Initialize(File.DirAssets, \"marker4.png";
_marker4.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker4.png");
RDebugUtils.currentLine=2949215;
 //BA.debugLineNum = 2949215;BA.debugLine="marker5.Initialize(File.DirAssets, \"marker5.png";
_marker5.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker5.png");
RDebugUtils.currentLine=2949216;
 //BA.debugLineNum = 2949216;BA.debugLine="marker6.Initialize(File.DirAssets, \"marker6.png";
_marker6.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png");
RDebugUtils.currentLine=2949217;
 //BA.debugLineNum = 2949217;BA.debugLine="marker7.Initialize(File.DirAssets, \"marker7.png";
_marker7.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker7.png");
RDebugUtils.currentLine=2949218;
 //BA.debugLineNum = 2949218;BA.debugLine="marker8.Initialize(File.DirAssets, \"marker8.png";
_marker8.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker8.png");
RDebugUtils.currentLine=2949219;
 //BA.debugLineNum = 2949219;BA.debugLine="marker9.Initialize(File.DirAssets, \"marker9.png";
_marker9.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker9.png");
RDebugUtils.currentLine=2949220;
 //BA.debugLineNum = 2949220;BA.debugLine="marker10.Initialize(File.DirAssets, \"marker10.p";
_marker10.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker10.png");
RDebugUtils.currentLine=2949221;
 //BA.debugLineNum = 2949221;BA.debugLine="marker11.Initialize(File.DirAssets, \"marker11.p";
_marker11.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker11.png");
RDebugUtils.currentLine=2949222;
 //BA.debugLineNum = 2949222;BA.debugLine="marker12.Initialize(File.DirAssets, \"marker12.p";
_marker12.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker12.png");
RDebugUtils.currentLine=2949223;
 //BA.debugLineNum = 2949223;BA.debugLine="marker13.Initialize(File.DirAssets, \"marker13.p";
_marker13.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker13.png");
RDebugUtils.currentLine=2949224;
 //BA.debugLineNum = 2949224;BA.debugLine="marker14.Initialize(File.DirAssets, \"marker14.p";
_marker14.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker14.png");
RDebugUtils.currentLine=2949225;
 //BA.debugLineNum = 2949225;BA.debugLine="marker15.Initialize(File.DirAssets, \"marker15.p";
_marker15.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker15.png");
RDebugUtils.currentLine=2949226;
 //BA.debugLineNum = 2949226;BA.debugLine="marker16.Initialize(File.DirAssets, \"marker16.p";
_marker16.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker16.png");
RDebugUtils.currentLine=2949228;
 //BA.debugLineNum = 2949228;BA.debugLine="Dim markerGV1dr As BitmapDrawable";
_markergv1dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949229;
 //BA.debugLineNum = 2949229;BA.debugLine="Dim markerGV2dr As BitmapDrawable";
_markergv2dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949230;
 //BA.debugLineNum = 2949230;BA.debugLine="Dim markerGV3dr As BitmapDrawable";
_markergv3dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949231;
 //BA.debugLineNum = 2949231;BA.debugLine="Dim markerGV4dr As BitmapDrawable";
_markergv4dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949232;
 //BA.debugLineNum = 2949232;BA.debugLine="Dim markerGV5dr As BitmapDrawable";
_markergv5dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949233;
 //BA.debugLineNum = 2949233;BA.debugLine="Dim markerGV6dr As BitmapDrawable";
_markergv6dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949234;
 //BA.debugLineNum = 2949234;BA.debugLine="Dim markerGV7dr As BitmapDrawable";
_markergv7dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949235;
 //BA.debugLineNum = 2949235;BA.debugLine="Dim markerGV8dr As BitmapDrawable";
_markergv8dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949236;
 //BA.debugLineNum = 2949236;BA.debugLine="Dim markerGV9dr As BitmapDrawable";
_markergv9dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949237;
 //BA.debugLineNum = 2949237;BA.debugLine="Dim markerGV10dr As BitmapDrawable";
_markergv10dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949238;
 //BA.debugLineNum = 2949238;BA.debugLine="Dim markerGV11dr As BitmapDrawable";
_markergv11dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949239;
 //BA.debugLineNum = 2949239;BA.debugLine="Dim markerGV12dr As BitmapDrawable";
_markergv12dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949240;
 //BA.debugLineNum = 2949240;BA.debugLine="Dim markerGV13dr As BitmapDrawable";
_markergv13dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949241;
 //BA.debugLineNum = 2949241;BA.debugLine="Dim markerGV14dr As BitmapDrawable";
_markergv14dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949242;
 //BA.debugLineNum = 2949242;BA.debugLine="Dim markerGV15dr As BitmapDrawable";
_markergv15dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949243;
 //BA.debugLineNum = 2949243;BA.debugLine="Dim markerGV16dr As BitmapDrawable";
_markergv16dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949245;
 //BA.debugLineNum = 2949245;BA.debugLine="markerGV1dr.Initialize(markerGV_1)";
_markergv1dr.Initialize((android.graphics.Bitmap)(_markergv_1.getObject()));
RDebugUtils.currentLine=2949246;
 //BA.debugLineNum = 2949246;BA.debugLine="markerGV2dr.Initialize(markerGV_2)";
_markergv2dr.Initialize((android.graphics.Bitmap)(_markergv_2.getObject()));
RDebugUtils.currentLine=2949247;
 //BA.debugLineNum = 2949247;BA.debugLine="markerGV3dr.Initialize(markerGV_3)";
_markergv3dr.Initialize((android.graphics.Bitmap)(_markergv_3.getObject()));
RDebugUtils.currentLine=2949248;
 //BA.debugLineNum = 2949248;BA.debugLine="markerGV4dr.Initialize(markerGV_4)";
_markergv4dr.Initialize((android.graphics.Bitmap)(_markergv_4.getObject()));
RDebugUtils.currentLine=2949249;
 //BA.debugLineNum = 2949249;BA.debugLine="markerGV5dr.Initialize(markerGV_5)";
_markergv5dr.Initialize((android.graphics.Bitmap)(_markergv_5.getObject()));
RDebugUtils.currentLine=2949250;
 //BA.debugLineNum = 2949250;BA.debugLine="markerGV6dr.Initialize(markerGV_6)";
_markergv6dr.Initialize((android.graphics.Bitmap)(_markergv_6.getObject()));
RDebugUtils.currentLine=2949251;
 //BA.debugLineNum = 2949251;BA.debugLine="markerGV7dr.Initialize(markerGV_7)";
_markergv7dr.Initialize((android.graphics.Bitmap)(_markergv_7.getObject()));
RDebugUtils.currentLine=2949252;
 //BA.debugLineNum = 2949252;BA.debugLine="markerGV8dr.Initialize(markerGV_8)";
_markergv8dr.Initialize((android.graphics.Bitmap)(_markergv_8.getObject()));
RDebugUtils.currentLine=2949253;
 //BA.debugLineNum = 2949253;BA.debugLine="markerGV9dr.Initialize(markerGV_9)";
_markergv9dr.Initialize((android.graphics.Bitmap)(_markergv_9.getObject()));
RDebugUtils.currentLine=2949254;
 //BA.debugLineNum = 2949254;BA.debugLine="markerGV10dr.Initialize(markerGV_10)";
_markergv10dr.Initialize((android.graphics.Bitmap)(_markergv_10.getObject()));
RDebugUtils.currentLine=2949255;
 //BA.debugLineNum = 2949255;BA.debugLine="markerGV11dr.Initialize(markerGV_11)";
_markergv11dr.Initialize((android.graphics.Bitmap)(_markergv_11.getObject()));
RDebugUtils.currentLine=2949256;
 //BA.debugLineNum = 2949256;BA.debugLine="markerGV12dr.Initialize(markerGV_12)";
_markergv12dr.Initialize((android.graphics.Bitmap)(_markergv_12.getObject()));
RDebugUtils.currentLine=2949257;
 //BA.debugLineNum = 2949257;BA.debugLine="markerGV13dr.Initialize(markerGV_13)";
_markergv13dr.Initialize((android.graphics.Bitmap)(_markergv_13.getObject()));
RDebugUtils.currentLine=2949258;
 //BA.debugLineNum = 2949258;BA.debugLine="markerGV14dr.Initialize(markerGV_14)";
_markergv14dr.Initialize((android.graphics.Bitmap)(_markergv_14.getObject()));
RDebugUtils.currentLine=2949259;
 //BA.debugLineNum = 2949259;BA.debugLine="markerGV15dr.Initialize(markerGV_15)";
_markergv15dr.Initialize((android.graphics.Bitmap)(_markergv_15.getObject()));
RDebugUtils.currentLine=2949260;
 //BA.debugLineNum = 2949260;BA.debugLine="markerGV16dr.Initialize(markerGV_16)";
_markergv16dr.Initialize((android.graphics.Bitmap)(_markergv_16.getObject()));
RDebugUtils.currentLine=2949262;
 //BA.debugLineNum = 2949262;BA.debugLine="Dim marker1dr As BitmapDrawable";
_marker1dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949263;
 //BA.debugLineNum = 2949263;BA.debugLine="Dim marker2dr As BitmapDrawable";
_marker2dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949264;
 //BA.debugLineNum = 2949264;BA.debugLine="Dim marker3dr As BitmapDrawable";
_marker3dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949265;
 //BA.debugLineNum = 2949265;BA.debugLine="Dim marker4dr As BitmapDrawable";
_marker4dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949266;
 //BA.debugLineNum = 2949266;BA.debugLine="Dim marker5dr As BitmapDrawable";
_marker5dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949267;
 //BA.debugLineNum = 2949267;BA.debugLine="Dim marker6dr As BitmapDrawable";
_marker6dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949268;
 //BA.debugLineNum = 2949268;BA.debugLine="Dim marker7dr As BitmapDrawable";
_marker7dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949269;
 //BA.debugLineNum = 2949269;BA.debugLine="Dim marker8dr As BitmapDrawable";
_marker8dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949270;
 //BA.debugLineNum = 2949270;BA.debugLine="Dim marker9dr As BitmapDrawable";
_marker9dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949271;
 //BA.debugLineNum = 2949271;BA.debugLine="Dim marker10dr As BitmapDrawable";
_marker10dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949272;
 //BA.debugLineNum = 2949272;BA.debugLine="Dim marker11dr As BitmapDrawable";
_marker11dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949273;
 //BA.debugLineNum = 2949273;BA.debugLine="Dim marker12dr As BitmapDrawable";
_marker12dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949274;
 //BA.debugLineNum = 2949274;BA.debugLine="Dim marker13dr As BitmapDrawable";
_marker13dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949275;
 //BA.debugLineNum = 2949275;BA.debugLine="Dim marker14dr As BitmapDrawable";
_marker14dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949276;
 //BA.debugLineNum = 2949276;BA.debugLine="Dim marker15dr As BitmapDrawable";
_marker15dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949277;
 //BA.debugLineNum = 2949277;BA.debugLine="Dim marker16dr As BitmapDrawable";
_marker16dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
RDebugUtils.currentLine=2949279;
 //BA.debugLineNum = 2949279;BA.debugLine="marker1dr.Initialize(marker1)";
_marker1dr.Initialize((android.graphics.Bitmap)(_marker1.getObject()));
RDebugUtils.currentLine=2949280;
 //BA.debugLineNum = 2949280;BA.debugLine="marker2dr.Initialize(marker2)";
_marker2dr.Initialize((android.graphics.Bitmap)(_marker2.getObject()));
RDebugUtils.currentLine=2949281;
 //BA.debugLineNum = 2949281;BA.debugLine="marker3dr.Initialize(marker3)";
_marker3dr.Initialize((android.graphics.Bitmap)(_marker3.getObject()));
RDebugUtils.currentLine=2949282;
 //BA.debugLineNum = 2949282;BA.debugLine="marker4dr.Initialize(marker4)";
_marker4dr.Initialize((android.graphics.Bitmap)(_marker4.getObject()));
RDebugUtils.currentLine=2949283;
 //BA.debugLineNum = 2949283;BA.debugLine="marker5dr.Initialize(marker5)";
_marker5dr.Initialize((android.graphics.Bitmap)(_marker5.getObject()));
RDebugUtils.currentLine=2949284;
 //BA.debugLineNum = 2949284;BA.debugLine="marker6dr.Initialize(marker6)";
_marker6dr.Initialize((android.graphics.Bitmap)(_marker6.getObject()));
RDebugUtils.currentLine=2949285;
 //BA.debugLineNum = 2949285;BA.debugLine="marker7dr.Initialize(marker7)";
_marker7dr.Initialize((android.graphics.Bitmap)(_marker7.getObject()));
RDebugUtils.currentLine=2949286;
 //BA.debugLineNum = 2949286;BA.debugLine="marker8dr.Initialize(marker8)";
_marker8dr.Initialize((android.graphics.Bitmap)(_marker8.getObject()));
RDebugUtils.currentLine=2949287;
 //BA.debugLineNum = 2949287;BA.debugLine="marker9dr.Initialize(marker9)";
_marker9dr.Initialize((android.graphics.Bitmap)(_marker9.getObject()));
RDebugUtils.currentLine=2949288;
 //BA.debugLineNum = 2949288;BA.debugLine="marker10dr.Initialize(marker10)";
_marker10dr.Initialize((android.graphics.Bitmap)(_marker10.getObject()));
RDebugUtils.currentLine=2949289;
 //BA.debugLineNum = 2949289;BA.debugLine="marker11dr.Initialize(marker11)";
_marker11dr.Initialize((android.graphics.Bitmap)(_marker11.getObject()));
RDebugUtils.currentLine=2949290;
 //BA.debugLineNum = 2949290;BA.debugLine="marker12dr.Initialize(marker12)";
_marker12dr.Initialize((android.graphics.Bitmap)(_marker12.getObject()));
RDebugUtils.currentLine=2949291;
 //BA.debugLineNum = 2949291;BA.debugLine="marker13dr.Initialize(marker13)";
_marker13dr.Initialize((android.graphics.Bitmap)(_marker13.getObject()));
RDebugUtils.currentLine=2949292;
 //BA.debugLineNum = 2949292;BA.debugLine="marker14dr.Initialize(marker14)";
_marker14dr.Initialize((android.graphics.Bitmap)(_marker14.getObject()));
RDebugUtils.currentLine=2949293;
 //BA.debugLineNum = 2949293;BA.debugLine="marker15dr.Initialize(marker15)";
_marker15dr.Initialize((android.graphics.Bitmap)(_marker15.getObject()));
RDebugUtils.currentLine=2949294;
 //BA.debugLineNum = 2949294;BA.debugLine="marker16dr.Initialize(marker16)";
_marker16dr.Initialize((android.graphics.Bitmap)(_marker16.getObject()));
RDebugUtils.currentLine=2949298;
 //BA.debugLineNum = 2949298;BA.debugLine="For i = 0 To 250";
if (true) break;

case 11:
//for
this.state = 88;
step152 = 1;
limit152 = (int) (250);
_i = (int) (0) ;
this.state = 98;
if (true) break;

case 98:
//C
this.state = 88;
if ((step152 > 0 && _i <= limit152) || (step152 < 0 && _i >= limit152)) this.state = 13;
if (true) break;

case 99:
//C
this.state = 98;
_i = ((int)(0 + _i + step152)) ;
if (true) break;

case 13:
//C
this.state = 14;
RDebugUtils.currentLine=2949305;
 //BA.debugLineNum = 2949305;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=2949306;
 //BA.debugLineNum = 2949306;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
RDebugUtils.currentLine=2949307;
 //BA.debugLineNum = 2949307;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
RDebugUtils.currentLine=2949308;
 //BA.debugLineNum = 2949308;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
RDebugUtils.currentLine=2949309;
 //BA.debugLineNum = 2949309;BA.debugLine="Dim valorvinchuca As String = newpunto.Get(\"val";
_valorvinchuca = BA.ObjectToString(_newpunto.Get((Object)("valorVinchuca")));
RDebugUtils.currentLine=2949310;
 //BA.debugLineNum = 2949310;BA.debugLine="Dim nombreUsuario As String = newpunto.Get(\"use";
_nombreusuario = BA.ObjectToString(_newpunto.Get((Object)("username")));
RDebugUtils.currentLine=2949311;
 //BA.debugLineNum = 2949311;BA.debugLine="Dim marker As OSMDroid_Marker";
_marker = new uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker();
RDebugUtils.currentLine=2949312;
 //BA.debugLineNum = 2949312;BA.debugLine="marker.Initialize(valorvinchuca,nombreUsuario,s";
_marker.Initialize(_valorvinchuca,_nombreusuario,_sitiolat,_sitiolong);
RDebugUtils.currentLine=2949314;
 //BA.debugLineNum = 2949314;BA.debugLine="If nombreUsuario = \"geovin\" Then";
if (true) break;

case 14:
//if
this.state = 87;
if ((_nombreusuario).equals("geovin")) { 
this.state = 16;
}else {
this.state = 52;
}if (true) break;

case 16:
//C
this.state = 17;
RDebugUtils.currentLine=2949315;
 //BA.debugLineNum = 2949315;BA.debugLine="If valorvinchuca = \"infestans\" Then";
if (true) break;

case 17:
//if
this.state = 50;
if ((_valorvinchuca).equals("infestans")) { 
this.state = 19;
}else 
{RDebugUtils.currentLine=2949317;
 //BA.debugLineNum = 2949317;BA.debugLine="Else If valorvinchuca = \"guasayana\" Then";
if ((_valorvinchuca).equals("guasayana")) { 
this.state = 21;
}else 
{RDebugUtils.currentLine=2949319;
 //BA.debugLineNum = 2949319;BA.debugLine="Else If valorvinchuca = \"sordida\" Then";
if ((_valorvinchuca).equals("sordida")) { 
this.state = 23;
}else 
{RDebugUtils.currentLine=2949321;
 //BA.debugLineNum = 2949321;BA.debugLine="Else If valorvinchuca = \"garciabesi\" Then";
if ((_valorvinchuca).equals("garciabesi")) { 
this.state = 25;
}else 
{RDebugUtils.currentLine=2949323;
 //BA.debugLineNum = 2949323;BA.debugLine="Else If valorvinchuca = \"delpontei\" Then";
if ((_valorvinchuca).equals("delpontei")) { 
this.state = 27;
}else 
{RDebugUtils.currentLine=2949325;
 //BA.debugLineNum = 2949325;BA.debugLine="Else If valorvinchuca = \"platensis\" Then";
if ((_valorvinchuca).equals("platensis")) { 
this.state = 29;
}else 
{RDebugUtils.currentLine=2949327;
 //BA.debugLineNum = 2949327;BA.debugLine="Else If valorvinchuca = \"rubrovaria\" Then";
if ((_valorvinchuca).equals("rubrovaria")) { 
this.state = 31;
}else 
{RDebugUtils.currentLine=2949329;
 //BA.debugLineNum = 2949329;BA.debugLine="Else If valorvinchuca = \"eratyrusiformis\" The";
if ((_valorvinchuca).equals("eratyrusiformis")) { 
this.state = 33;
}else 
{RDebugUtils.currentLine=2949331;
 //BA.debugLineNum = 2949331;BA.debugLine="Else If valorvinchuca = \"breyeri\" Then";
if ((_valorvinchuca).equals("breyeri")) { 
this.state = 35;
}else 
{RDebugUtils.currentLine=2949333;
 //BA.debugLineNum = 2949333;BA.debugLine="Else If valorvinchuca = \"rufotuberculatus\" Th";
if ((_valorvinchuca).equals("rufotuberculatus")) { 
this.state = 37;
}else 
{RDebugUtils.currentLine=2949335;
 //BA.debugLineNum = 2949335;BA.debugLine="Else If valorvinchuca = \"limai\" Then";
if ((_valorvinchuca).equals("limai")) { 
this.state = 39;
}else 
{RDebugUtils.currentLine=2949337;
 //BA.debugLineNum = 2949337;BA.debugLine="Else If valorvinchuca = \"coreodes\" Then";
if ((_valorvinchuca).equals("coreodes")) { 
this.state = 41;
}else 
{RDebugUtils.currentLine=2949339;
 //BA.debugLineNum = 2949339;BA.debugLine="Else If valorvinchuca = \"geniculatus\" Then";
if ((_valorvinchuca).equals("geniculatus")) { 
this.state = 43;
}else 
{RDebugUtils.currentLine=2949341;
 //BA.debugLineNum = 2949341;BA.debugLine="Else If valorvinchuca = \"guentheri\" Then";
if ((_valorvinchuca).equals("guentheri")) { 
this.state = 45;
}else 
{RDebugUtils.currentLine=2949343;
 //BA.debugLineNum = 2949343;BA.debugLine="Else If valorvinchuca = \"megistus\" Then";
if ((_valorvinchuca).equals("megistus")) { 
this.state = 47;
}else 
{RDebugUtils.currentLine=2949345;
 //BA.debugLineNum = 2949345;BA.debugLine="Else If valorvinchuca = \"patagonica\" Then";
if ((_valorvinchuca).equals("patagonica")) { 
this.state = 49;
}}}}}}}}}}}}}}}}
if (true) break;

case 19:
//C
this.state = 50;
RDebugUtils.currentLine=2949316;
 //BA.debugLineNum = 2949316;BA.debugLine="marker.SetMarkerIcon(markerGV1dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv1dr.getObject()));
 if (true) break;

case 21:
//C
this.state = 50;
RDebugUtils.currentLine=2949318;
 //BA.debugLineNum = 2949318;BA.debugLine="marker.SetMarkerIcon(markerGV2dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv2dr.getObject()));
 if (true) break;

case 23:
//C
this.state = 50;
RDebugUtils.currentLine=2949320;
 //BA.debugLineNum = 2949320;BA.debugLine="marker.SetMarkerIcon(markerGV5dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv5dr.getObject()));
 if (true) break;

case 25:
//C
this.state = 50;
RDebugUtils.currentLine=2949322;
 //BA.debugLineNum = 2949322;BA.debugLine="marker.SetMarkerIcon(markerGV3dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv3dr.getObject()));
 if (true) break;

case 27:
//C
this.state = 50;
RDebugUtils.currentLine=2949324;
 //BA.debugLineNum = 2949324;BA.debugLine="marker.SetMarkerIcon(markerGV6dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv6dr.getObject()));
 if (true) break;

case 29:
//C
this.state = 50;
RDebugUtils.currentLine=2949326;
 //BA.debugLineNum = 2949326;BA.debugLine="marker.SetMarkerIcon(markerGV7dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv7dr.getObject()));
 if (true) break;

case 31:
//C
this.state = 50;
RDebugUtils.currentLine=2949328;
 //BA.debugLineNum = 2949328;BA.debugLine="marker.SetMarkerIcon(markerGV8dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv8dr.getObject()));
 if (true) break;

case 33:
//C
this.state = 50;
RDebugUtils.currentLine=2949330;
 //BA.debugLineNum = 2949330;BA.debugLine="marker.SetMarkerIcon(markerGV9dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv9dr.getObject()));
 if (true) break;

case 35:
//C
this.state = 50;
RDebugUtils.currentLine=2949332;
 //BA.debugLineNum = 2949332;BA.debugLine="marker.SetMarkerIcon(markerGV10dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv10dr.getObject()));
 if (true) break;

case 37:
//C
this.state = 50;
RDebugUtils.currentLine=2949334;
 //BA.debugLineNum = 2949334;BA.debugLine="marker.SetMarkerIcon(markerGV11dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv11dr.getObject()));
 if (true) break;

case 39:
//C
this.state = 50;
RDebugUtils.currentLine=2949336;
 //BA.debugLineNum = 2949336;BA.debugLine="marker.SetMarkerIcon(markerGV12dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv12dr.getObject()));
 if (true) break;

case 41:
//C
this.state = 50;
RDebugUtils.currentLine=2949338;
 //BA.debugLineNum = 2949338;BA.debugLine="marker.SetMarkerIcon(markerGV13dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv13dr.getObject()));
 if (true) break;

case 43:
//C
this.state = 50;
RDebugUtils.currentLine=2949340;
 //BA.debugLineNum = 2949340;BA.debugLine="marker.SetMarkerIcon(markerGV14dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv14dr.getObject()));
 if (true) break;

case 45:
//C
this.state = 50;
RDebugUtils.currentLine=2949342;
 //BA.debugLineNum = 2949342;BA.debugLine="marker.SetMarkerIcon(markerGV15dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv15dr.getObject()));
 if (true) break;

case 47:
//C
this.state = 50;
RDebugUtils.currentLine=2949344;
 //BA.debugLineNum = 2949344;BA.debugLine="marker.SetMarkerIcon(markerGV16dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv16dr.getObject()));
 if (true) break;

case 49:
//C
this.state = 50;
RDebugUtils.currentLine=2949346;
 //BA.debugLineNum = 2949346;BA.debugLine="marker.SetMarkerIcon(markerGV4dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv4dr.getObject()));
 if (true) break;

case 50:
//C
this.state = 87;
;
 if (true) break;

case 52:
//C
this.state = 53;
RDebugUtils.currentLine=2949350;
 //BA.debugLineNum = 2949350;BA.debugLine="If valorvinchuca = \"infestans\" Then";
if (true) break;

case 53:
//if
this.state = 86;
if ((_valorvinchuca).equals("infestans")) { 
this.state = 55;
}else 
{RDebugUtils.currentLine=2949352;
 //BA.debugLineNum = 2949352;BA.debugLine="Else If valorvinchuca = \"guasayana\" Then";
if ((_valorvinchuca).equals("guasayana")) { 
this.state = 57;
}else 
{RDebugUtils.currentLine=2949354;
 //BA.debugLineNum = 2949354;BA.debugLine="Else If valorvinchuca = \"sordida\" Then";
if ((_valorvinchuca).equals("sordida")) { 
this.state = 59;
}else 
{RDebugUtils.currentLine=2949356;
 //BA.debugLineNum = 2949356;BA.debugLine="Else If valorvinchuca = \"garciabesi\" Then";
if ((_valorvinchuca).equals("garciabesi")) { 
this.state = 61;
}else 
{RDebugUtils.currentLine=2949358;
 //BA.debugLineNum = 2949358;BA.debugLine="Else If valorvinchuca = \"delpontei\" Then";
if ((_valorvinchuca).equals("delpontei")) { 
this.state = 63;
}else 
{RDebugUtils.currentLine=2949360;
 //BA.debugLineNum = 2949360;BA.debugLine="Else If valorvinchuca = \"platensis\" Then";
if ((_valorvinchuca).equals("platensis")) { 
this.state = 65;
}else 
{RDebugUtils.currentLine=2949362;
 //BA.debugLineNum = 2949362;BA.debugLine="Else If valorvinchuca = \"rubrovaria\" Then";
if ((_valorvinchuca).equals("rubrovaria")) { 
this.state = 67;
}else 
{RDebugUtils.currentLine=2949364;
 //BA.debugLineNum = 2949364;BA.debugLine="Else If valorvinchuca = \"eratyrusiformis\" The";
if ((_valorvinchuca).equals("eratyrusiformis")) { 
this.state = 69;
}else 
{RDebugUtils.currentLine=2949366;
 //BA.debugLineNum = 2949366;BA.debugLine="Else If valorvinchuca = \"breyeri\" Then";
if ((_valorvinchuca).equals("breyeri")) { 
this.state = 71;
}else 
{RDebugUtils.currentLine=2949368;
 //BA.debugLineNum = 2949368;BA.debugLine="Else If valorvinchuca = \"rufotuberculatus\" Th";
if ((_valorvinchuca).equals("rufotuberculatus")) { 
this.state = 73;
}else 
{RDebugUtils.currentLine=2949370;
 //BA.debugLineNum = 2949370;BA.debugLine="Else If valorvinchuca = \"limai\" Then";
if ((_valorvinchuca).equals("limai")) { 
this.state = 75;
}else 
{RDebugUtils.currentLine=2949372;
 //BA.debugLineNum = 2949372;BA.debugLine="Else If valorvinchuca = \"coreodes\" Then";
if ((_valorvinchuca).equals("coreodes")) { 
this.state = 77;
}else 
{RDebugUtils.currentLine=2949374;
 //BA.debugLineNum = 2949374;BA.debugLine="Else If valorvinchuca = \"geniculatus\" Then";
if ((_valorvinchuca).equals("geniculatus")) { 
this.state = 79;
}else 
{RDebugUtils.currentLine=2949376;
 //BA.debugLineNum = 2949376;BA.debugLine="Else If valorvinchuca = \"guentheri\" Then";
if ((_valorvinchuca).equals("guentheri")) { 
this.state = 81;
}else 
{RDebugUtils.currentLine=2949378;
 //BA.debugLineNum = 2949378;BA.debugLine="Else If valorvinchuca = \"megistus\" Then";
if ((_valorvinchuca).equals("megistus")) { 
this.state = 83;
}else 
{RDebugUtils.currentLine=2949380;
 //BA.debugLineNum = 2949380;BA.debugLine="Else If valorvinchuca = \"patagonica\" Then";
if ((_valorvinchuca).equals("patagonica")) { 
this.state = 85;
}}}}}}}}}}}}}}}}
if (true) break;

case 55:
//C
this.state = 86;
RDebugUtils.currentLine=2949351;
 //BA.debugLineNum = 2949351;BA.debugLine="marker.SetMarkerIcon(marker1dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker1dr.getObject()));
 if (true) break;

case 57:
//C
this.state = 86;
RDebugUtils.currentLine=2949353;
 //BA.debugLineNum = 2949353;BA.debugLine="marker.SetMarkerIcon(marker2dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker2dr.getObject()));
 if (true) break;

case 59:
//C
this.state = 86;
RDebugUtils.currentLine=2949355;
 //BA.debugLineNum = 2949355;BA.debugLine="marker.SetMarkerIcon(marker5dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker5dr.getObject()));
 if (true) break;

case 61:
//C
this.state = 86;
RDebugUtils.currentLine=2949357;
 //BA.debugLineNum = 2949357;BA.debugLine="marker.SetMarkerIcon(marker3dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker3dr.getObject()));
 if (true) break;

case 63:
//C
this.state = 86;
RDebugUtils.currentLine=2949359;
 //BA.debugLineNum = 2949359;BA.debugLine="marker.SetMarkerIcon(marker6dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker6dr.getObject()));
 if (true) break;

case 65:
//C
this.state = 86;
RDebugUtils.currentLine=2949361;
 //BA.debugLineNum = 2949361;BA.debugLine="marker.SetMarkerIcon(marker7dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker7dr.getObject()));
 if (true) break;

case 67:
//C
this.state = 86;
RDebugUtils.currentLine=2949363;
 //BA.debugLineNum = 2949363;BA.debugLine="marker.SetMarkerIcon(marker8dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker8dr.getObject()));
 if (true) break;

case 69:
//C
this.state = 86;
RDebugUtils.currentLine=2949365;
 //BA.debugLineNum = 2949365;BA.debugLine="marker.SetMarkerIcon(marker9dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker9dr.getObject()));
 if (true) break;

case 71:
//C
this.state = 86;
RDebugUtils.currentLine=2949367;
 //BA.debugLineNum = 2949367;BA.debugLine="marker.SetMarkerIcon(marker1dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker1dr.getObject()));
 if (true) break;

case 73:
//C
this.state = 86;
RDebugUtils.currentLine=2949369;
 //BA.debugLineNum = 2949369;BA.debugLine="marker.SetMarkerIcon(marker11dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker11dr.getObject()));
 if (true) break;

case 75:
//C
this.state = 86;
RDebugUtils.currentLine=2949371;
 //BA.debugLineNum = 2949371;BA.debugLine="marker.SetMarkerIcon(marker12dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker12dr.getObject()));
 if (true) break;

case 77:
//C
this.state = 86;
RDebugUtils.currentLine=2949373;
 //BA.debugLineNum = 2949373;BA.debugLine="marker.SetMarkerIcon(marker13dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker13dr.getObject()));
 if (true) break;

case 79:
//C
this.state = 86;
RDebugUtils.currentLine=2949375;
 //BA.debugLineNum = 2949375;BA.debugLine="marker.SetMarkerIcon(marker14dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker14dr.getObject()));
 if (true) break;

case 81:
//C
this.state = 86;
RDebugUtils.currentLine=2949377;
 //BA.debugLineNum = 2949377;BA.debugLine="marker.SetMarkerIcon(marker15dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker15dr.getObject()));
 if (true) break;

case 83:
//C
this.state = 86;
RDebugUtils.currentLine=2949379;
 //BA.debugLineNum = 2949379;BA.debugLine="marker.SetMarkerIcon(marker16dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker16dr.getObject()));
 if (true) break;

case 85:
//C
this.state = 86;
RDebugUtils.currentLine=2949381;
 //BA.debugLineNum = 2949381;BA.debugLine="marker.SetMarkerIcon(marker4dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker4dr.getObject()));
 if (true) break;

case 86:
//C
this.state = 87;
;
 if (true) break;

case 87:
//C
this.state = 99;
;
RDebugUtils.currentLine=2949384;
 //BA.debugLineNum = 2949384;BA.debugLine="markersList.Add(marker)";
parent.mostCurrent._markerslist.Add((Object)(_marker.getObject()));
 if (true) break;
if (true) break;

case 88:
//C
this.state = 89;
;
RDebugUtils.currentLine=2949387;
 //BA.debugLineNum = 2949387;BA.debugLine="markersOverlay.AddItems(markersList)";
parent.mostCurrent._markersoverlay.AddItems(parent.mostCurrent._markerslist);
RDebugUtils.currentLine=2949388;
 //BA.debugLineNum = 2949388;BA.debugLine="MapView1.GetController.SetZoom(7)";
parent.mostCurrent._mapview1.GetController().SetZoom((int) (7));
 if (true) break;

case 89:
//C
this.state = 92;
;
 if (true) break;

case 91:
//C
this.state = 92;
RDebugUtils.currentLine=2949394;
 //BA.debugLineNum = 2949394;BA.debugLine="Msgbox(\"Compruebe su conexión a Internet!\", \"Oop";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Compruebe su conexión a Internet!"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 if (true) break;

case 92:
//C
this.state = 93;
;
RDebugUtils.currentLine=2949397;
 //BA.debugLineNum = 2949397;BA.debugLine="GetMapa.Release";
_getmapa._release();
RDebugUtils.currentLine=2949398;
 //BA.debugLineNum = 2949398;BA.debugLine="btnDetectar.Enabled = True";
parent.mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2949400;
 //BA.debugLineNum = 2949400;BA.debugLine="If FusedLocationProvider1.IsConnected = False The";
if (true) break;

case 93:
//if
this.state = 96;
if (parent._fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 95;
}if (true) break;

case 95:
//C
this.state = 96;
RDebugUtils.currentLine=2949401;
 //BA.debugLineNum = 2949401;BA.debugLine="FusedLocationProvider1.Connect";
parent._fusedlocationprovider1.Connect();
 if (true) break;

case 96:
//C
this.state = -1;
;
RDebugUtils.currentLine=2949403;
 //BA.debugLineNum = 2949403;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _fondoblanco_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fondoblanco_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fondoblanco_click", null));}
RDebugUtils.currentLine=2424832;
 //BA.debugLineNum = 2424832;BA.debugLine="Sub fondoblanco_Click";
RDebugUtils.currentLine=2424834;
 //BA.debugLineNum = 2424834;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=2424835;
 //BA.debugLineNum = 2424835;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=2424836;
 //BA.debugLineNum = 2424836;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2424838;
 //BA.debugLineNum = 2424838;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
RDebugUtils.currentLine=2424839;
 //BA.debugLineNum = 2424839;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
RDebugUtils.currentLine=2424840;
 //BA.debugLineNum = 2424840;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionfailed(int _connectionresult1) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionfailed"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionfailed", new Object[] {_connectionresult1}));}
RDebugUtils.currentLine=2490368;
 //BA.debugLineNum = 2490368;BA.debugLine="Sub FusedLocationProvider1_ConnectionFailed(Connec";
RDebugUtils.currentLine=2490369;
 //BA.debugLineNum = 2490369;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionFailed\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_ConnectionFailed");
RDebugUtils.currentLine=2490373;
 //BA.debugLineNum = 2490373;BA.debugLine="Select ConnectionResult1";
switch (BA.switchObjectToInt(_connectionresult1,_fusedlocationprovider1.ConnectionResult.NETWORK_ERROR)) {
case 0: {
RDebugUtils.currentLine=2490377;
 //BA.debugLineNum = 2490377;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 break; }
default: {
 break; }
}
;
RDebugUtils.currentLine=2490381;
 //BA.debugLineNum = 2490381;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuccess() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionsuccess"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionsuccess", null));}
uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest _locationrequest1 = null;
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder _locationsettingsrequestbuilder1 = null;
RDebugUtils.currentLine=2555904;
 //BA.debugLineNum = 2555904;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuccess";
RDebugUtils.currentLine=2555905;
 //BA.debugLineNum = 2555905;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuccess\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_ConnectionSuccess");
RDebugUtils.currentLine=2555906;
 //BA.debugLineNum = 2555906;BA.debugLine="Dim LocationRequest1 As LocationRequest";
_locationrequest1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest();
RDebugUtils.currentLine=2555907;
 //BA.debugLineNum = 2555907;BA.debugLine="LocationRequest1.Initialize";
_locationrequest1.Initialize();
RDebugUtils.currentLine=2555908;
 //BA.debugLineNum = 2555908;BA.debugLine="LocationRequest1.SetInterval(1000)	'	1000 millise";
_locationrequest1.SetInterval((long) (1000));
RDebugUtils.currentLine=2555910;
 //BA.debugLineNum = 2555910;BA.debugLine="LocationRequest1.SetPriority(LocationRequest1.Pri";
_locationrequest1.SetPriority(_locationrequest1.Priority.PRIORITY_HIGH_ACCURACY);
RDebugUtils.currentLine=2555911;
 //BA.debugLineNum = 2555911;BA.debugLine="LocationRequest1.SetSmallestDisplacement(1)	'	1 m";
_locationrequest1.SetSmallestDisplacement((float) (1));
RDebugUtils.currentLine=2555913;
 //BA.debugLineNum = 2555913;BA.debugLine="Dim LocationSettingsRequestBuilder1 As LocationSe";
_locationsettingsrequestbuilder1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder();
RDebugUtils.currentLine=2555914;
 //BA.debugLineNum = 2555914;BA.debugLine="LocationSettingsRequestBuilder1.Initialize";
_locationsettingsrequestbuilder1.Initialize();
RDebugUtils.currentLine=2555915;
 //BA.debugLineNum = 2555915;BA.debugLine="LocationSettingsRequestBuilder1.AddLocationReques";
_locationsettingsrequestbuilder1.AddLocationRequest((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
RDebugUtils.currentLine=2555916;
 //BA.debugLineNum = 2555916;BA.debugLine="FusedLocationProvider1.CheckLocationSettings(Loca";
_fusedlocationprovider1.CheckLocationSettings(_locationsettingsrequestbuilder1.Build());
RDebugUtils.currentLine=2555918;
 //BA.debugLineNum = 2555918;BA.debugLine="FusedLocationProvider1.RequestLocationUpdates(Loc";
_fusedlocationprovider1.RequestLocationUpdates((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
RDebugUtils.currentLine=2555920;
 //BA.debugLineNum = 2555920;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=2555921;
 //BA.debugLineNum = 2555921;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=2555922;
 //BA.debugLineNum = 2555922;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2555923;
 //BA.debugLineNum = 2555923;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuspended(int _suspendedcause1) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionsuspended"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_connectionsuspended", new Object[] {_suspendedcause1}));}
RDebugUtils.currentLine=2621440;
 //BA.debugLineNum = 2621440;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuspended(Sus";
RDebugUtils.currentLine=2621441;
 //BA.debugLineNum = 2621441;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuspended\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_ConnectionSuspended");
RDebugUtils.currentLine=2621445;
 //BA.debugLineNum = 2621445;BA.debugLine="Select SuspendedCause1";
switch (BA.switchObjectToInt(_suspendedcause1,_fusedlocationprovider1.SuspendedCause.CAUSE_NETWORK_LOST,_fusedlocationprovider1.SuspendedCause.CAUSE_SERVICE_DISCONNECTED)) {
case 0: {
 break; }
case 1: {
 break; }
}
;
RDebugUtils.currentLine=2621451;
 //BA.debugLineNum = 2621451;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_locationchanged"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_locationchanged", new Object[] {_location1}));}
RDebugUtils.currentLine=2686976;
 //BA.debugLineNum = 2686976;BA.debugLine="Sub FusedLocationProvider1_LocationChanged(Locatio";
RDebugUtils.currentLine=2686977;
 //BA.debugLineNum = 2686977;BA.debugLine="Log(\"FusedLocationProvider1_LocationChanged\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_LocationChanged");
RDebugUtils.currentLine=2686978;
 //BA.debugLineNum = 2686978;BA.debugLine="LastLocation=Location1";
_lastlocation = _location1;
RDebugUtils.currentLine=2686979;
 //BA.debugLineNum = 2686979;BA.debugLine="Log(LastLocation.Latitude)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lastlocation.getLatitude()));
RDebugUtils.currentLine=2686980;
 //BA.debugLineNum = 2686980;BA.debugLine="Log(LastLocation.Longitude)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lastlocation.getLongitude()));
RDebugUtils.currentLine=2686981;
 //BA.debugLineNum = 2686981;BA.debugLine="UpdateUI";
_updateui();
RDebugUtils.currentLine=2686982;
 //BA.debugLineNum = 2686982;BA.debugLine="End Sub";
return "";
}
public static String  _updateui() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "updateui"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "updateui", null));}
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
RDebugUtils.currentLine=2883584;
 //BA.debugLineNum = 2883584;BA.debugLine="Sub UpdateUI";
RDebugUtils.currentLine=2883585;
 //BA.debugLineNum = 2883585;BA.debugLine="ToastMessageShow(\"Posición encontrada!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Posición encontrada!"),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2883587;
 //BA.debugLineNum = 2883587;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
RDebugUtils.currentLine=2883588;
 //BA.debugLineNum = 2883588;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
RDebugUtils.currentLine=2883589;
 //BA.debugLineNum = 2883589;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
RDebugUtils.currentLine=2883590;
 //BA.debugLineNum = 2883590;BA.debugLine="geopoint1.Initialize(lblLat.Text,lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
RDebugUtils.currentLine=2883593;
 //BA.debugLineNum = 2883593;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=2883595;
 //BA.debugLineNum = 2883595;BA.debugLine="SimpleLocationOverlay1.Initialize";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 };
RDebugUtils.currentLine=2883597;
 //BA.debugLineNum = 2883597;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
RDebugUtils.currentLine=2883603;
 //BA.debugLineNum = 2883603;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
RDebugUtils.currentLine=2883604;
 //BA.debugLineNum = 2883604;BA.debugLine="MapView1.GetController.SetZoom(14)";
mostCurrent._mapview1.GetController().SetZoom((int) (14));
RDebugUtils.currentLine=2883605;
 //BA.debugLineNum = 2883605;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=2883606;
 //BA.debugLineNum = 2883606;BA.debugLine="btnDetectar.TextColor = Colors.Black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=2883608;
 //BA.debugLineNum = 2883608;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
RDebugUtils.currentLine=2883609;
 //BA.debugLineNum = 2883609;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2883611;
 //BA.debugLineNum = 2883611;BA.debugLine="If fondoblanco.IsInitialized Then";
if (mostCurrent._fondoblanco.IsInitialized()) { 
RDebugUtils.currentLine=2883612;
 //BA.debugLineNum = 2883612;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
RDebugUtils.currentLine=2883613;
 //BA.debugLineNum = 2883613;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 }else {
RDebugUtils.currentLine=2883615;
 //BA.debugLineNum = 2883615;BA.debugLine="btnDetectar.Color = Colors.Black";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=2883616;
 //BA.debugLineNum = 2883616;BA.debugLine="btnDetectar.TextColor = Colors.White";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 };
RDebugUtils.currentLine=2883618;
 //BA.debugLineNum = 2883618;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=2883619;
 //BA.debugLineNum = 2883619;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationsettingschecked(uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsResult _locationsettingsresult1) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fusedlocationprovider1_locationsettingschecked"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fusedlocationprovider1_locationsettingschecked", new Object[] {_locationsettingsresult1}));}
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus _locationsettingsstatus1 = null;
RDebugUtils.currentLine=2752512;
 //BA.debugLineNum = 2752512;BA.debugLine="Sub FusedLocationProvider1_LocationSettingsChecked";
RDebugUtils.currentLine=2752513;
 //BA.debugLineNum = 2752513;BA.debugLine="Log(\"FusedLocationProvider1_LocationSettingsCheck";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_LocationSettingsChecked");
RDebugUtils.currentLine=2752514;
 //BA.debugLineNum = 2752514;BA.debugLine="Dim LocationSettingsStatus1 As LocationSettingsSt";
_locationsettingsstatus1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus();
_locationsettingsstatus1 = _locationsettingsresult1.GetLocationSettingsStatus();
RDebugUtils.currentLine=2752515;
 //BA.debugLineNum = 2752515;BA.debugLine="Select LocationSettingsStatus1.GetStatusCode";
switch (BA.switchObjectToInt(_locationsettingsstatus1.GetStatusCode(),_locationsettingsstatus1.StatusCodes.RESOLUTION_REQUIRED,_locationsettingsstatus1.StatusCodes.SETTINGS_CHANGE_UNAVAILABLE,_locationsettingsstatus1.StatusCodes.SUCCESS)) {
case 0: {
RDebugUtils.currentLine=2752517;
 //BA.debugLineNum = 2752517;BA.debugLine="Log(\"RESOLUTION_REQUIRED\")";
anywheresoftware.b4a.keywords.Common.Log("RESOLUTION_REQUIRED");
RDebugUtils.currentLine=2752520;
 //BA.debugLineNum = 2752520;BA.debugLine="LocationSettingsStatus1.StartResolutionDialog(\"";
_locationsettingsstatus1.StartResolutionDialog(mostCurrent.activityBA,"LocationSettingsResult1");
 break; }
case 1: {
RDebugUtils.currentLine=2752522;
 //BA.debugLineNum = 2752522;BA.debugLine="Log(\"SETTINGS_CHANGE_UNAVAILABLE\")";
anywheresoftware.b4a.keywords.Common.Log("SETTINGS_CHANGE_UNAVAILABLE");
RDebugUtils.currentLine=2752525;
 //BA.debugLineNum = 2752525;BA.debugLine="Msgbox(\"Error, tu dispositivo no tiene localiza";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error, tu dispositivo no tiene localización. Busca tu posición en el mapa!"),BA.ObjectToCharSequence("Problem"),mostCurrent.activityBA);
RDebugUtils.currentLine=2752526;
 //BA.debugLineNum = 2752526;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 2: {
RDebugUtils.currentLine=2752528;
 //BA.debugLineNum = 2752528;BA.debugLine="Log(\"SUCCESS\")";
anywheresoftware.b4a.keywords.Common.Log("SUCCESS");
 break; }
}
;
RDebugUtils.currentLine=2752532;
 //BA.debugLineNum = 2752532;BA.debugLine="End Sub";
return "";
}
public static String  _locationsettingsresult1_resolutiondialogdismissed(boolean _locationsettingsupdated) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "locationsettingsresult1_resolutiondialogdismissed"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "locationsettingsresult1_resolutiondialogdismissed", new Object[] {_locationsettingsupdated}));}
RDebugUtils.currentLine=2818048;
 //BA.debugLineNum = 2818048;BA.debugLine="Sub LocationSettingsResult1_ResolutionDialogDismis";
RDebugUtils.currentLine=2818049;
 //BA.debugLineNum = 2818049;BA.debugLine="Log(\"LocationSettingsResult1_ResolutionDialogDism";
anywheresoftware.b4a.keywords.Common.Log("LocationSettingsResult1_ResolutionDialogDismissed");
RDebugUtils.currentLine=2818050;
 //BA.debugLineNum = 2818050;BA.debugLine="If Not(LocationSettingsUpdated) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_locationsettingsupdated)) { 
RDebugUtils.currentLine=2818052;
 //BA.debugLineNum = 2818052;BA.debugLine="Msgbox(\"No tienes habilitada la Localización, bu";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes habilitada la Localización, busca en el mapa tu posición"),BA.ObjectToCharSequence("Búsqueda manual"),mostCurrent.activityBA);
RDebugUtils.currentLine=2818055;
 //BA.debugLineNum = 2818055;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=2818056;
 //BA.debugLineNum = 2818056;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=2818057;
 //BA.debugLineNum = 2818057;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2818060;
 //BA.debugLineNum = 2818060;BA.debugLine="If FusedLocationProvider1.IsConnected = True The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=2818061;
 //BA.debugLineNum = 2818061;BA.debugLine="FusedLocationProvider1.DisConnect";
_fusedlocationprovider1.Disconnect();
 };
RDebugUtils.currentLine=2818064;
 //BA.debugLineNum = 2818064;BA.debugLine="If fondoblanco.IsInitialized Then";
if (mostCurrent._fondoblanco.IsInitialized()) { 
RDebugUtils.currentLine=2818065;
 //BA.debugLineNum = 2818065;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
RDebugUtils.currentLine=2818066;
 //BA.debugLineNum = 2818066;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 };
 };
RDebugUtils.currentLine=2818069;
 //BA.debugLineNum = 2818069;BA.debugLine="End Sub";
return "";
}
public static String  _mnuabout_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "mnuabout_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "mnuabout_click", null));}
RDebugUtils.currentLine=1638400;
 //BA.debugLineNum = 1638400;BA.debugLine="Sub mnuAbout_Click";
RDebugUtils.currentLine=1638401;
 //BA.debugLineNum = 1638401;BA.debugLine="StartActivity(frmabout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
RDebugUtils.currentLine=1638402;
 //BA.debugLineNum = 1638402;BA.debugLine="End Sub";
return "";
}
public static String  _mnucerrarsesion_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "mnucerrarsesion_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "mnucerrarsesion_click", null));}
String _msg = "";
RDebugUtils.currentLine=1572864;
 //BA.debugLineNum = 1572864;BA.debugLine="Sub mnuCerrarSesion_Click";
RDebugUtils.currentLine=1572865;
 //BA.debugLineNum = 1572865;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=1572866;
 //BA.debugLineNum = 1572866;BA.debugLine="msg = Msgbox2(\"Desea cerrar la sesión? Ingresar c";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!"),BA.ObjectToCharSequence("Seguro?"),"Si, tengo internet","","No, no tengo internet ahora",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=1572867;
 //BA.debugLineNum = 1572867;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=1572868;
 //BA.debugLineNum = 1572868;BA.debugLine="Main.strUserID = \"\"";
mostCurrent._main._struserid = "";
RDebugUtils.currentLine=1572869;
 //BA.debugLineNum = 1572869;BA.debugLine="Main.strUserName = \"\"";
mostCurrent._main._strusername = "";
RDebugUtils.currentLine=1572870;
 //BA.debugLineNum = 1572870;BA.debugLine="Main.strUserLocation = \"\"";
mostCurrent._main._struserlocation = "";
RDebugUtils.currentLine=1572871;
 //BA.debugLineNum = 1572871;BA.debugLine="Main.strUserEmail = \"\"";
mostCurrent._main._struseremail = "";
RDebugUtils.currentLine=1572872;
 //BA.debugLineNum = 1572872;BA.debugLine="Main.strUserOrg = \"\"";
mostCurrent._main._struserorg = "";
RDebugUtils.currentLine=1572873;
 //BA.debugLineNum = 1572873;BA.debugLine="Main.username = \"\"";
mostCurrent._main._username = "";
RDebugUtils.currentLine=1572875;
 //BA.debugLineNum = 1572875;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._main.getObject()));
 };
RDebugUtils.currentLine=1572877;
 //BA.debugLineNum = 1572877;BA.debugLine="End Sub";
return "";
}
public static String  _mnuvermiperfil_click() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "mnuvermiperfil_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "mnuvermiperfil_click", null));}
RDebugUtils.currentLine=1507328;
 //BA.debugLineNum = 1507328;BA.debugLine="Sub mnuVermiperfil_Click";
RDebugUtils.currentLine=1507329;
 //BA.debugLineNum = 1507329;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=1507330;
 //BA.debugLineNum = 1507330;BA.debugLine="frmDatosAnteriores.notificacion = False";
mostCurrent._frmdatosanteriores._notificacion = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=1507331;
 //BA.debugLineNum = 1507331;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 }else {
RDebugUtils.currentLine=1507333;
 //BA.debugLineNum = 1507333;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No ha iniciado sesión aún"),anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=1507335;
 //BA.debugLineNum = 1507335;BA.debugLine="End Sub";
return "";
}
public static void  _resetmessages() throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "resetmessages"))
	 {Debug.delegate(mostCurrent.activityBA, "resetmessages", null); return;}
ResumableSub_ResetMessages rsub = new ResumableSub_ResetMessages(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_ResetMessages extends BA.ResumableSub {
public ResumableSub_ResetMessages(cepave.geovin.frmprincipal parent) {
this.parent = parent;
}
cepave.geovin.frmprincipal parent;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="frmprincipal";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=3997697;
 //BA.debugLineNum = 3997697;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=3997698;
 //BA.debugLineNum = 3997698;BA.debugLine="j.Initialize(\"ResetMessages\", Me)";
_j._initialize(processBA,"ResetMessages",frmprincipal.getObject());
RDebugUtils.currentLine=3997699;
 //BA.debugLineNum = 3997699;BA.debugLine="j.Download2(Main.serverPath & \"/connect/resetmess";
_j._download2(parent.mostCurrent._main._serverpath+"/connect/resetmessages_new.php",new String[]{"deviceID",parent.mostCurrent._main._deviceid});
RDebugUtils.currentLine=3997700;
 //BA.debugLineNum = 3997700;BA.debugLine="Main.msjprivadouser = \"None\"";
parent.mostCurrent._main._msjprivadouser = "None";
RDebugUtils.currentLine=3997701;
 //BA.debugLineNum = 3997701;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "frmprincipal", "resetmessages"), (Object)(_j));
this.state = 17;
return;
case 17:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=3997702;
 //BA.debugLineNum = 3997702;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 16;
if (_j._success) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
RDebugUtils.currentLine=3997703;
 //BA.debugLineNum = 3997703;BA.debugLine="Dim ret As String";
_ret = "";
RDebugUtils.currentLine=3997704;
 //BA.debugLineNum = 3997704;BA.debugLine="Dim act As String";
_act = "";
RDebugUtils.currentLine=3997705;
 //BA.debugLineNum = 3997705;BA.debugLine="ret = j.GetString";
_ret = _j._getstring();
RDebugUtils.currentLine=3997706;
 //BA.debugLineNum = 3997706;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=3997707;
 //BA.debugLineNum = 3997707;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
RDebugUtils.currentLine=3997708;
 //BA.debugLineNum = 3997708;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=3997711;
 //BA.debugLineNum = 3997711;BA.debugLine="If j.JobName = \"ResetMessages\" Then";
if (true) break;

case 4:
//if
this.state = 15;
if ((_j._jobname).equals("ResetMessages")) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
RDebugUtils.currentLine=3997712;
 //BA.debugLineNum = 3997712;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 7:
//if
this.state = 14;
if ((_act).equals("Not Found")) { 
this.state = 9;
}else 
{RDebugUtils.currentLine=3997714;
 //BA.debugLineNum = 3997714;BA.debugLine="Else If act = \"Error reseteando los mensajes, \"";
if ((_act).equals("Error reseteando los mensajes, ")) { 
this.state = 11;
}else 
{RDebugUtils.currentLine=3997716;
 //BA.debugLineNum = 3997716;BA.debugLine="Else If act = \"ResetMessages\" Then";
if ((_act).equals("ResetMessages")) { 
this.state = 13;
}}}
if (true) break;

case 9:
//C
this.state = 14;
RDebugUtils.currentLine=3997713;
 //BA.debugLineNum = 3997713;BA.debugLine="ToastMessageShow(\"Error\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 14;
RDebugUtils.currentLine=3997715;
 //BA.debugLineNum = 3997715;BA.debugLine="ToastMessageShow(\"Login failed\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Login failed"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 13:
//C
this.state = 14;
 if (true) break;

case 14:
//C
this.state = 15;
;
 if (true) break;

case 15:
//C
this.state = 16;
;
 if (true) break;

case 16:
//C
this.state = -1;
;
RDebugUtils.currentLine=3997721;
 //BA.debugLineNum = 3997721;BA.debugLine="j.Release";
_j._release();
RDebugUtils.currentLine=3997723;
 //BA.debugLineNum = 3997723;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _tabstripmain_pageselected(int _position) throws Exception{
RDebugUtils.currentModule="frmprincipal";
if (Debug.shouldDelegate(mostCurrent.activityBA, "tabstripmain_pageselected"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "tabstripmain_pageselected", new Object[] {_position}));}
RDebugUtils.currentLine=1769472;
 //BA.debugLineNum = 1769472;BA.debugLine="Sub tabStripMain_PageSelected (Position As Int)";
RDebugUtils.currentLine=1769473;
 //BA.debugLineNum = 1769473;BA.debugLine="If Position = 1 Then";
if (_position==1) { 
RDebugUtils.currentLine=1769475;
 //BA.debugLineNum = 1769475;BA.debugLine="If panelExp.IsInitialized Then";
if (mostCurrent._panelexp.IsInitialized()) { 
RDebugUtils.currentLine=1769476;
 //BA.debugLineNum = 1769476;BA.debugLine="panelExp.RemoveView";
mostCurrent._panelexp.RemoveView();
 };
 }else 
{RDebugUtils.currentLine=1769479;
 //BA.debugLineNum = 1769479;BA.debugLine="Else If Position = 2 Then";
if (_position==2) { 
RDebugUtils.currentLine=1769481;
 //BA.debugLineNum = 1769481;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_ACCESS_FINE_LOC";
_rp.CheckAndRequest(processBA,_rp.PERMISSION_ACCESS_FINE_LOCATION);
 }else 
{RDebugUtils.currentLine=1769485;
 //BA.debugLineNum = 1769485;BA.debugLine="Else If Position = 0 Then";
if (_position==0) { 
RDebugUtils.currentLine=1769486;
 //BA.debugLineNum = 1769486;BA.debugLine="btnInformeVinchuca.Left = Activity.Width / 2 - b";
mostCurrent._btninformevinchuca.setLeft((int) (mostCurrent._activity.getWidth()/(double)2-mostCurrent._btninformevinchuca.getWidth()/(double)2));
RDebugUtils.currentLine=1769487;
 //BA.debugLineNum = 1769487;BA.debugLine="btnFondo.Left = btnInformeVinchuca.Left";
mostCurrent._btnfondo.setLeft(mostCurrent._btninformevinchuca.getLeft());
RDebugUtils.currentLine=1769488;
 //BA.debugLineNum = 1769488;BA.debugLine="btnFondo.Top = btnInformeVinchuca.Top";
mostCurrent._btnfondo.setTop(mostCurrent._btninformevinchuca.getTop());
RDebugUtils.currentLine=1769489;
 //BA.debugLineNum = 1769489;BA.debugLine="lblEncontre.Left = Activity.Width / 2 - lblEncon";
mostCurrent._lblencontre.setLeft((int) (mostCurrent._activity.getWidth()/(double)2-mostCurrent._lblencontre.getWidth()/(double)2));
RDebugUtils.currentLine=1769490;
 //BA.debugLineNum = 1769490;BA.debugLine="lblEncontreVinchuca.Left = Activity.Width / 2 -";
mostCurrent._lblencontrevinchuca.setLeft((int) (mostCurrent._activity.getWidth()/(double)2-mostCurrent._lblencontrevinchuca.getWidth()/(double)2));
RDebugUtils.currentLine=1769492;
 //BA.debugLineNum = 1769492;BA.debugLine="btnComoFotosReportar.Top = btnFondo.Top + btnFon";
mostCurrent._btncomofotosreportar.setTop((int) (mostCurrent._btnfondo.getTop()+mostCurrent._btnfondo.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
RDebugUtils.currentLine=1769493;
 //BA.debugLineNum = 1769493;BA.debugLine="btnDatosAnteriores.Top = btnComoFotosReportar.To";
mostCurrent._btndatosanteriores.setTop((int) (mostCurrent._btncomofotosreportar.getTop()+mostCurrent._btncomofotosreportar.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
RDebugUtils.currentLine=1769494;
 //BA.debugLineNum = 1769494;BA.debugLine="If panelExp.IsInitialized Then";
if (mostCurrent._panelexp.IsInitialized()) { 
RDebugUtils.currentLine=1769495;
 //BA.debugLineNum = 1769495;BA.debugLine="panelExp.RemoveView";
mostCurrent._panelexp.RemoveView();
 };
 }}}
;
RDebugUtils.currentLine=1769499;
 //BA.debugLineNum = 1769499;BA.debugLine="End Sub";
return "";
}
}