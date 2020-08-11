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
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmprincipal");
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
        if (!dontPause)
            BA.LogInfo("** Activity (frmprincipal) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmprincipal) Pause event (activity is not paused). **");
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

public anywheresoftware.b4a.keywords.Common __c = null;
public static uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper _fusedlocationprovider1 = null;
public static anywheresoftware.b4a.gps.LocationWrapper _lastlocation = null;
public static String _fullidcurrentproject = "";
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static String _currentscreen = "";
public static boolean _resetting_msg = false;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripmain = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmenu = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblencontre = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnfondo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblencontrevinchuca = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btninformevinchuca = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnaprendermas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnelchagas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnrecomendaciones = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmasinfoonline = null;
public anywheresoftware.b4a.objects.Timer _timeraprender = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelexp = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public uk.co.martinpearman.b4a.osmdroid.views.MapView _mapview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnzoomall = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnreferenciamapa = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay _simplelocationoverlay1 = null;
public anywheresoftware.b4a.objects.collections.List _markerexport = null;
public anywheresoftware.b4a.objects.LabelWrapper _markerinfo = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmapa = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.LabelWrapper _detectandolabel = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlreferencias = null;
public static boolean _ispanelexplorar = false;
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
public anywheresoftware.b4a.objects.LabelWrapper _lblespecies = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.phone.Phone _p = null;
public cepave.geovin.b4xdrawer _drawer = null;
public anywheresoftware.b4a.objects.LabelWrapper _btncomofotosreportar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrarsesion = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnedituser = null;
public anywheresoftware.b4a.objects.LabelWrapper _btndatosanteriores = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblusername = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblregistrate = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnverperfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnabout = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnpoliticadatos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblstatussync = null;
public cepave.geovin.main _main = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 103;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 104;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProvider1");
 };
 //BA.debugLineNum = 107;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 109;BA.debugLine="Drawer.Initialize(Me, \"Drawer\", Activity, 300dip)";
mostCurrent._drawer._initialize /*String*/ (mostCurrent.activityBA,frmprincipal.getObject(),"Drawer",(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 110;BA.debugLine="Drawer.CenterPanel.LoadLayout(\"frmPrincipal\")";
mostCurrent._drawer._getcenterpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("frmPrincipal",mostCurrent.activityBA);
 //BA.debugLineNum = 111;BA.debugLine="Drawer.LeftPanel.LoadLayout(\"frmSideNav\")";
mostCurrent._drawer._getleftpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("frmSideNav",mostCurrent.activityBA);
 //BA.debugLineNum = 114;BA.debugLine="If Main.lang <> \"es\" And Main.lang <> \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es") == false && (mostCurrent._main._lang /*String*/ ).equals("en") == false) { 
 //BA.debugLineNum = 115;BA.debugLine="Main.lang = \"es\"";
mostCurrent._main._lang /*String*/  = "es";
 };
 //BA.debugLineNum = 118;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 119;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 120;BA.debugLine="btnCerrarSesion.Text = \"Iniciar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Iniciar sesión"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 122;BA.debugLine="btnCerrarSesion.Text = \"Start session\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Start session"));
 };
 //BA.debugLineNum = 125;BA.debugLine="lblUserName.Visible = False";
mostCurrent._lblusername.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 126;BA.debugLine="lblRegistrate.Visible = True";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 127;BA.debugLine="btnEditUser.Visible = False";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 129;BA.debugLine="lblUserName.Text = Main.username";
mostCurrent._lblusername.setText(BA.ObjectToCharSequence(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 130;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 131;BA.debugLine="btnCerrarSesion.Text = \"Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Cerrar sesión"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 133;BA.debugLine="btnCerrarSesion.Text = \"Close session\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Close session"));
 };
 //BA.debugLineNum = 135;BA.debugLine="lblRegistrate.Visible = False";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 136;BA.debugLine="btnEditUser.Visible = True";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 140;BA.debugLine="If frmDatosAnteriores.notificacion = True Then";
if (mostCurrent._frmdatosanteriores._notificacion /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 141;BA.debugLine="CallSubDelayed3(frmDatosAnteriores,\"VerDetalles\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed3(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()),"VerDetalles",(Object)(mostCurrent._frmdatosanteriores._serveridnum /*String*/ ),(Object)(anywheresoftware.b4a.keywords.Common.True));
 };
 //BA.debugLineNum = 145;BA.debugLine="If Main.modooffline = True Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 146;BA.debugLine="startBienvienido(False, True)";
_startbienvienido(anywheresoftware.b4a.keywords.Common.False,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 147;BA.debugLine="resetting_msg = False";
_resetting_msg = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 149;BA.debugLine="startBienvienido(True, True)";
_startbienvienido(anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 152;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 153;BA.debugLine="tabStripMain.LoadLayout(\"frmprincipal_Reportar\",";
mostCurrent._tabstripmain.LoadLayout("frmprincipal_Reportar",BA.ObjectToCharSequence("REPORTAR"));
 //BA.debugLineNum = 154;BA.debugLine="tabStripMain.LoadLayout(\"frmprincipal_Aprender\",";
mostCurrent._tabstripmain.LoadLayout("frmprincipal_Aprender",BA.ObjectToCharSequence("APRENDER"));
 //BA.debugLineNum = 155;BA.debugLine="tabStripMain.LoadLayout(\"layBlank\", \"EXPLORAR\")";
mostCurrent._tabstripmain.LoadLayout("layBlank",BA.ObjectToCharSequence("EXPLORAR"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 157;BA.debugLine="tabStripMain.LoadLayout(\"frmprincipal_Reportar\",";
mostCurrent._tabstripmain.LoadLayout("frmprincipal_Reportar",BA.ObjectToCharSequence("REPORT"));
 //BA.debugLineNum = 158;BA.debugLine="tabStripMain.LoadLayout(\"frmprincipal_Aprender\",";
mostCurrent._tabstripmain.LoadLayout("frmprincipal_Aprender",BA.ObjectToCharSequence("LEARN"));
 //BA.debugLineNum = 159;BA.debugLine="tabStripMain.LoadLayout(\"layBlank\", \"EXPLORE\")";
mostCurrent._tabstripmain.LoadLayout("layBlank",BA.ObjectToCharSequence("EXPLORE"));
 };
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
String _msg = "";
 //BA.debugLineNum = 171;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 173;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 174;BA.debugLine="If Drawer.LeftOpen Then";
if (mostCurrent._drawer._getleftopen /*boolean*/ ()) { 
 //BA.debugLineNum = 175;BA.debugLine="Drawer.LeftOpen = False";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 176;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 178;BA.debugLine="If currentscreen = \"aprendermas\" Or currentscre";
if ((mostCurrent._currentscreen).equals("aprendermas") || (mostCurrent._currentscreen).equals("fotos")) { 
 //BA.debugLineNum = 179;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 180;BA.debugLine="Activity.LoadLayout(\"frmPrincipal\")";
mostCurrent._activity.LoadLayout("frmPrincipal",mostCurrent.activityBA);
 //BA.debugLineNum = 181;BA.debugLine="currentscreen = \"\"";
mostCurrent._currentscreen = "";
 }else {
 //BA.debugLineNum = 183;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 184;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 185;BA.debugLine="msg = Msgbox2(\"Salir de la aplicación?\", \"SAL";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Salir de la aplicación?"),BA.ObjectToCharSequence("SALIR"),"Si, deseo salir","","No, me equivoqué",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 187;BA.debugLine="msg = Msgbox2(\"Exit the app?\", \"EXIT\", \"Yes,";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Exit the app?"),BA.ObjectToCharSequence("EXIT"),"Yes, exit","","Nop, my bad",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 189;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 190;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 };
 };
 //BA.debugLineNum = 194;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 196;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _activity_permissionresult(String _permission,boolean _result) throws Exception{
 //BA.debugLineNum = 452;BA.debugLine="Sub Activity_PermissionResult (Permission As Strin";
 //BA.debugLineNum = 453;BA.debugLine="If Permission = rp.PERMISSION_ACCESS_FINE_LOCATIO";
if ((_permission).equals(_rp.PERMISSION_ACCESS_FINE_LOCATION)) { 
 //BA.debugLineNum = 454;BA.debugLine="panelExp.Initialize(\"panelExp\")";
mostCurrent._panelexp.Initialize(mostCurrent.activityBA,"panelExp");
 //BA.debugLineNum = 455;BA.debugLine="panelExp.Invalidate";
mostCurrent._panelexp.Invalidate();
 //BA.debugLineNum = 456;BA.debugLine="Activity.AddView(panelExp, 0, 170dip, 100%x, Act";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelexp.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (mostCurrent._activity.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170))));
 //BA.debugLineNum = 457;BA.debugLine="panelExp.LoadLayout(\"frmprincipal_Explorar\")";
mostCurrent._panelexp.LoadLayout("frmprincipal_Explorar",mostCurrent.activityBA);
 //BA.debugLineNum = 458;BA.debugLine="CargarMapa";
_cargarmapa();
 };
 //BA.debugLineNum = 460;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 164;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 166;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static String  _btnabout_click() throws Exception{
 //BA.debugLineNum = 299;BA.debugLine="Sub btnAbout_Click";
 //BA.debugLineNum = 300;BA.debugLine="StartActivity(frmabout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return "";
}
public static String  _btnacerca_click() throws Exception{
 //BA.debugLineNum = 1483;BA.debugLine="Sub btnAcerca_Click";
 //BA.debugLineNum = 1484;BA.debugLine="StartActivity(frmabout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 1485;BA.debugLine="End Sub";
return "";
}
public static String  _btnaprendermas_click() throws Exception{
 //BA.debugLineNum = 1462;BA.debugLine="Sub btnAprenderMas_Click";
 //BA.debugLineNum = 1466;BA.debugLine="StartActivity(frmEspecies)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmespecies.getObject()));
 //BA.debugLineNum = 1468;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarsesion_click() throws Exception{
String _msg = "";
 //BA.debugLineNum = 278;BA.debugLine="Sub btnCerrarSesion_Click";
 //BA.debugLineNum = 279;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 280;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 281;BA.debugLine="msg = Msgbox2(\"Desea cerrar la sesión? Ingresar";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!"),BA.ObjectToCharSequence("Seguro?"),"Si, tengo internet","","No, no tengo internet ahora",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 283;BA.debugLine="msg = Msgbox2(\"Do you want to close the session?";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Do you want to close the session? To log in with another user you need an internet connection!"),BA.ObjectToCharSequence("Sure?"),"Yes, I have internet","","No, i'm offline now",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 285;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 286;BA.debugLine="Main.strUserID = \"\"";
mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 287;BA.debugLine="Main.strUserName = \"\"";
mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 288;BA.debugLine="Main.strUserLocation = \"\"";
mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 289;BA.debugLine="Main.strUserEmail = \"\"";
mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 290;BA.debugLine="Main.strUserOrg = \"\"";
mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 291;BA.debugLine="Main.username = \"\"";
mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 292;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 293;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 295;BA.debugLine="CallSubDelayed(frmLogin, \"SignOutGoogle\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._frmlogin.getObject()),"SignOutGoogle");
 //BA.debugLineNum = 296;BA.debugLine="StartActivity(frmLogin)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlogin.getObject()));
 };
 //BA.debugLineNum = 298;BA.debugLine="End Sub";
return "";
}
public static String  _btncheckall_click() throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chk = null;
 //BA.debugLineNum = 1300;BA.debugLine="Sub btnCheckAll_Click";
 //BA.debugLineNum = 1302;BA.debugLine="If btnCheckAll.Text = \"Seleccionar todas\" Or btnC";
if ((mostCurrent._btncheckall.getText()).equals("Seleccionar todas") || (mostCurrent._btncheckall.getText()).equals("Select all")) { 
 //BA.debugLineNum = 1303;BA.debugLine="For i = 0 To listaConfig.Size - 1";
{
final int step2 = 1;
final int limit2 = (int) (mostCurrent._listaconfig.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 1304;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 1305;BA.debugLine="chk = listaConfig.Get(i)";
_chk = (anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper(), (android.widget.CheckBox)(mostCurrent._listaconfig.Get(_i)));
 //BA.debugLineNum = 1306;BA.debugLine="chk.Checked = True";
_chk.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }
};
 //BA.debugLineNum = 1308;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1309;BA.debugLine="btnCheckAll.Text = \"Deseleccionar todas\"";
mostCurrent._btncheckall.setText(BA.ObjectToCharSequence("Deseleccionar todas"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1311;BA.debugLine="btnCheckAll.Text = \"Deselect all\"";
mostCurrent._btncheckall.setText(BA.ObjectToCharSequence("Deselect all"));
 };
 }else {
 //BA.debugLineNum = 1317;BA.debugLine="For i = 0 To listaConfig.Size - 1";
{
final int step13 = 1;
final int limit13 = (int) (mostCurrent._listaconfig.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit13 ;_i = _i + step13 ) {
 //BA.debugLineNum = 1318;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 1319;BA.debugLine="chk = listaConfig.Get(i)";
_chk = (anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper(), (android.widget.CheckBox)(mostCurrent._listaconfig.Get(_i)));
 //BA.debugLineNum = 1320;BA.debugLine="chk.Checked = False";
_chk.setChecked(anywheresoftware.b4a.keywords.Common.False);
 }
};
 //BA.debugLineNum = 1322;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1323;BA.debugLine="btnCheckAll.Text = \"Seleccionar todas\"";
mostCurrent._btncheckall.setText(BA.ObjectToCharSequence("Seleccionar todas"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1325;BA.debugLine="btnCheckAll.Text = \"Select all\"";
mostCurrent._btncheckall.setText(BA.ObjectToCharSequence("Select all"));
 };
 };
 //BA.debugLineNum = 1329;BA.debugLine="End Sub";
return "";
}
public static String  _btncomofotos_click() throws Exception{
 //BA.debugLineNum = 1479;BA.debugLine="Sub btnComoFotos_Click";
 //BA.debugLineNum = 1480;BA.debugLine="StartActivity(frmComoFotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmcomofotos.getObject()));
 //BA.debugLineNum = 1482;BA.debugLine="End Sub";
return "";
}
public static String  _btncomofotosreportar_click() throws Exception{
 //BA.debugLineNum = 581;BA.debugLine="Sub btnComoFotosReportar_Click";
 //BA.debugLineNum = 582;BA.debugLine="StartActivity(frmComoFotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmcomofotos.getObject()));
 //BA.debugLineNum = 584;BA.debugLine="End Sub";
return "";
}
public static String  _btnconfigexplorar_click() throws Exception{
 //BA.debugLineNum = 1110;BA.debugLine="Sub btnConfigExplorar_Click";
 //BA.debugLineNum = 1111;BA.debugLine="Activity.LoadLayout(\"frmprincipal_Explorar_Config";
mostCurrent._activity.LoadLayout("frmprincipal_Explorar_Config",mostCurrent.activityBA);
 //BA.debugLineNum = 1113;BA.debugLine="btnCheckAll.Visible = True";
mostCurrent._btncheckall.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1114;BA.debugLine="btnMenu.Visible = False";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1115;BA.debugLine="btnZoomAll.Visible = False";
mostCurrent._btnzoomall.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1116;BA.debugLine="Label1.Visible = True";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1117;BA.debugLine="btnReferenciaMapa.Visible = False";
mostCurrent._btnreferenciamapa.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1118;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1119;BA.debugLine="btnConfigExplorar.Text = \" Filtrar\"";
mostCurrent._btnconfigexplorar.setText(BA.ObjectToCharSequence(" Filtrar"));
 //BA.debugLineNum = 1120;BA.debugLine="chkDatosGeoVin.Text = \"Datos GeoVin\"";
mostCurrent._chkdatosgeovin.setText(BA.ObjectToCharSequence("Datos GeoVin"));
 //BA.debugLineNum = 1121;BA.debugLine="chkDatosPropios.Text = \"Datos propios\"";
mostCurrent._chkdatospropios.setText(BA.ObjectToCharSequence("Datos propios"));
 //BA.debugLineNum = 1122;BA.debugLine="chkDatosUsuarios.Text = \"Datos de otros usuarios";
mostCurrent._chkdatosusuarios.setText(BA.ObjectToCharSequence("Datos de otros usuarios"));
 //BA.debugLineNum = 1123;BA.debugLine="btnCheckAll.Text = \"Deseleccionar todas\"";
mostCurrent._btncheckall.setText(BA.ObjectToCharSequence("Deseleccionar todas"));
 //BA.debugLineNum = 1124;BA.debugLine="lblEspecies.Text = \"Especies:\"";
mostCurrent._lblespecies.setText(BA.ObjectToCharSequence("Especies:"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1126;BA.debugLine="btnConfigExplorar.Text = \" Filter\"";
mostCurrent._btnconfigexplorar.setText(BA.ObjectToCharSequence(" Filter"));
 //BA.debugLineNum = 1127;BA.debugLine="chkDatosGeoVin.Text = \"GeoVin data\"";
mostCurrent._chkdatosgeovin.setText(BA.ObjectToCharSequence("GeoVin data"));
 //BA.debugLineNum = 1128;BA.debugLine="chkDatosPropios.Text = \"Own data\"";
mostCurrent._chkdatospropios.setText(BA.ObjectToCharSequence("Own data"));
 //BA.debugLineNum = 1129;BA.debugLine="chkDatosUsuarios.Text = \"Other user data\"";
mostCurrent._chkdatosusuarios.setText(BA.ObjectToCharSequence("Other user data"));
 //BA.debugLineNum = 1130;BA.debugLine="lblEspecies.Text = \"Species:\"";
mostCurrent._lblespecies.setText(BA.ObjectToCharSequence("Species:"));
 //BA.debugLineNum = 1131;BA.debugLine="btnCheckAll.Text = \"Deselect all\"";
mostCurrent._btncheckall.setText(BA.ObjectToCharSequence("Deselect all"));
 };
 //BA.debugLineNum = 1133;BA.debugLine="CargarConfigExplorar";
_cargarconfigexplorar();
 //BA.debugLineNum = 1134;BA.debugLine="End Sub";
return "";
}
public static String  _btndatosanteriores_click() throws Exception{
 //BA.debugLineNum = 585;BA.debugLine="Sub btnDatosAnteriores_Click";
 //BA.debugLineNum = 587;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 588;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 }else {
 //BA.debugLineNum = 590;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 592;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No ha iniciado sesión aún"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 595;BA.debugLine="ToastMessageShow(\"You haven't logged in yet\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You haven't logged in yet"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 600;BA.debugLine="End Sub";
return "";
}
public static String  _btnedituser_click() throws Exception{
 //BA.debugLineNum = 302;BA.debugLine="Sub btnEditUser_Click";
 //BA.debugLineNum = 303;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 304;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 }else {
 //BA.debugLineNum = 306;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 307;BA.debugLine="ToastMessageShow(\"Necesita tener internet para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita tener internet para editar su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 309;BA.debugLine="ToastMessageShow(\"You need to be online to chec";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You need to be online to check your profile"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 313;BA.debugLine="End Sub";
return "";
}
public static String  _btnelchagas_click() throws Exception{
 //BA.debugLineNum = 1476;BA.debugLine="Sub btnElChagas_Click";
 //BA.debugLineNum = 1477;BA.debugLine="StartActivity(frmAprender_Chagas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmaprender_chagas.getObject()));
 //BA.debugLineNum = 1478;BA.debugLine="End Sub";
return "";
}
public static String  _btninformevinchuca_click() throws Exception{
 //BA.debugLineNum = 471;BA.debugLine="Sub btnInformeVinchuca_Click";
 //BA.debugLineNum = 473;BA.debugLine="If resetting_msg = True Then";
if (_resetting_msg==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 474;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 475;BA.debugLine="ToastMessageShow(\"Buscando señal... aguarde uno";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Buscando señal... aguarde unos segundos y vuelva a intentarlo"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 477;BA.debugLine="ToastMessageShow(\"Looking for signal... please";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Looking for signal... please wait a few seconds and try again"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 479;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 481;BA.debugLine="ComenzarReporte";
_comenzarreporte();
 //BA.debugLineNum = 482;BA.debugLine="End Sub";
return "";
}
public static String  _btnlangen_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 332;BA.debugLine="Sub btnLangEn_Click";
 //BA.debugLineNum = 333;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 334;BA.debugLine="Main.lang = \"en\"";
mostCurrent._main._lang /*String*/  = "en";
 //BA.debugLineNum = 335;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 336;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 337;BA.debugLine="Map1.Put(\"configID\", \"1\")";
_map1.Put((Object)("configID"),(Object)("1"));
 //BA.debugLineNum = 338;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"appconfig\",";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"appconfig","applang",(Object)(mostCurrent._main._lang /*String*/ ),_map1);
 //BA.debugLineNum = 340;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 341;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 342;BA.debugLine="StartActivity(Me)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,frmprincipal.getObject());
 };
 //BA.debugLineNum = 345;BA.debugLine="End Sub";
return "";
}
public static String  _btnlanges_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 318;BA.debugLine="Sub btnLangEs_Click";
 //BA.debugLineNum = 319;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 320;BA.debugLine="Main.lang = \"es\"";
mostCurrent._main._lang /*String*/  = "es";
 //BA.debugLineNum = 321;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 322;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 323;BA.debugLine="Map1.Put(\"configID\", \"1\")";
_map1.Put((Object)("configID"),(Object)("1"));
 //BA.debugLineNum = 324;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"appconfig\",";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"appconfig","applang",(Object)(mostCurrent._main._lang /*String*/ ),_map1);
 //BA.debugLineNum = 326;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 327;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 328;BA.debugLine="StartActivity(Me)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,frmprincipal.getObject());
 };
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasinfoonline_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;
 //BA.debugLineNum = 1469;BA.debugLine="Sub btnMasInfoOnline_Click";
 //BA.debugLineNum = 1470;BA.debugLine="Dim pi As PhoneIntents";
_pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 1471;BA.debugLine="StartActivity(pi.OpenBrowser(\"http://www.geovin.c";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_pi.OpenBrowser("http://www.geovin.com.ar/")));
 //BA.debugLineNum = 1472;BA.debugLine="End Sub";
return "";
}
public static String  _btnmenu_click() throws Exception{
 //BA.debugLineNum = 246;BA.debugLine="Sub btnMenu_Click";
 //BA.debugLineNum = 247;BA.debugLine="Drawer.LeftOpen = True";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 248;BA.debugLine="If Main.username = \"guest\" Then";
if ((mostCurrent._main._username /*String*/ ).equals("guest")) { 
 //BA.debugLineNum = 249;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 250;BA.debugLine="btnCerrarSesion.Text = \"Registrarse!\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Registrarse!"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 252;BA.debugLine="btnCerrarSesion.Text = \"Sign up!\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Sign up!"));
 };
 };
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _btnokconfig_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _listmostrar = null;
int _i = 0;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chk = null;
String _spname = "";
anywheresoftware.b4a.objects.collections.List _listmostrarconfig = null;
uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker _markeramostrar = null;
int _j = 0;
 //BA.debugLineNum = 1330;BA.debugLine="Sub btnOkConfig_Click";
 //BA.debugLineNum = 1334;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1335;BA.debugLine="ProgressDialogShow(\"Actualizando mapa...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Actualizando mapa..."));
 //BA.debugLineNum = 1336;BA.debugLine="ToastMessageShow(\"Actualizando mapa...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Actualizando mapa..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1338;BA.debugLine="ProgressDialogShow(\"Updating map...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Updating map..."));
 //BA.debugLineNum = 1339;BA.debugLine="ToastMessageShow(\"Updating mapa...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Updating mapa..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1342;BA.debugLine="Dim listMostrar As List";
_listmostrar = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1343;BA.debugLine="listMostrar.Initialize";
_listmostrar.Initialize();
 //BA.debugLineNum = 1344;BA.debugLine="listaChequeados.Initialize";
mostCurrent._listachequeados.Initialize();
 //BA.debugLineNum = 1346;BA.debugLine="For i = 0 To listaConfig.Size - 1";
{
final int step11 = 1;
final int limit11 = (int) (mostCurrent._listaconfig.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit11 ;_i = _i + step11 ) {
 //BA.debugLineNum = 1347;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 1348;BA.debugLine="chk = listaConfig.Get(i)";
_chk = (anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper(), (android.widget.CheckBox)(mostCurrent._listaconfig.Get(_i)));
 //BA.debugLineNum = 1349;BA.debugLine="listaChequeados.Add(chk)";
mostCurrent._listachequeados.Add((Object)(_chk.getObject()));
 //BA.debugLineNum = 1350;BA.debugLine="If chk.Checked = True Then";
if (_chk.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1352;BA.debugLine="Dim spname As String";
_spname = "";
 //BA.debugLineNum = 1353;BA.debugLine="spname = chk.Text.SubString2(chk.Text.LastIndex";
_spname = _chk.getText().substring(_chk.getText().lastIndexOf(" "),_chk.getText().length());
 //BA.debugLineNum = 1354;BA.debugLine="If spname.EndsWith(\"*\") Then";
if (_spname.endsWith("*")) { 
 //BA.debugLineNum = 1355;BA.debugLine="spname = spname.SubString2(0, spname.Length -";
_spname = _spname.substring((int) (0),(int) (_spname.length()-1));
 };
 //BA.debugLineNum = 1357;BA.debugLine="If spname.Contains(\"patagónica\") Then";
if (_spname.contains("patagónica")) { 
 //BA.debugLineNum = 1358;BA.debugLine="spname = \"patagonica\"";
_spname = "patagonica";
 };
 //BA.debugLineNum = 1361;BA.debugLine="listMostrar.Add(spname.Trim)";
_listmostrar.Add((Object)(_spname.trim()));
 };
 }
};
 //BA.debugLineNum = 1366;BA.debugLine="Dim listMostrarConfig As List";
_listmostrarconfig = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1367;BA.debugLine="listMostrarConfig.Initialize";
_listmostrarconfig.Initialize();
 //BA.debugLineNum = 1369;BA.debugLine="If chkDatosGeoVin.Checked = True Then";
if (mostCurrent._chkdatosgeovin.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1370;BA.debugLine="listMostrarConfig.Add(\"geovin\")";
_listmostrarconfig.Add((Object)("geovin"));
 }else {
 //BA.debugLineNum = 1372;BA.debugLine="listMostrarConfig.Add(\"no\")";
_listmostrarconfig.Add((Object)("no"));
 };
 //BA.debugLineNum = 1374;BA.debugLine="If chkDatosPropios.Checked = True Then";
if (mostCurrent._chkdatospropios.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1375;BA.debugLine="listMostrarConfig.Add(\"usuario\")";
_listmostrarconfig.Add((Object)("usuario"));
 }else {
 //BA.debugLineNum = 1377;BA.debugLine="listMostrarConfig.Add(\"no\")";
_listmostrarconfig.Add((Object)("no"));
 };
 //BA.debugLineNum = 1379;BA.debugLine="If chkDatosUsuarios.Checked = True Then";
if (mostCurrent._chkdatosusuarios.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1380;BA.debugLine="listMostrarConfig.Add(\"otrosusuarios\")";
_listmostrarconfig.Add((Object)("otrosusuarios"));
 }else {
 //BA.debugLineNum = 1382;BA.debugLine="listMostrarConfig.Add(\"no\")";
_listmostrarconfig.Add((Object)("no"));
 };
 //BA.debugLineNum = 1385;BA.debugLine="pnlConfig.RemoveView";
mostCurrent._pnlconfig.RemoveView();
 //BA.debugLineNum = 1387;BA.debugLine="btnCheckAll.Visible = False";
mostCurrent._btncheckall.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1388;BA.debugLine="btnZoomAll.Visible = True";
mostCurrent._btnzoomall.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1389;BA.debugLine="btnReferenciaMapa.Visible = True";
mostCurrent._btnreferenciamapa.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1390;BA.debugLine="Label1.Visible = False";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1393;BA.debugLine="markersOverlay.RemoveAllItems";
mostCurrent._markersoverlay.RemoveAllItems();
 //BA.debugLineNum = 1394;BA.debugLine="MapView1.GetOverlays.Remove(markersOverlay)";
mostCurrent._mapview1.GetOverlays().Remove((org.osmdroid.views.overlay.Overlay)(mostCurrent._markersoverlay.getObject()));
 //BA.debugLineNum = 1401;BA.debugLine="For i = 0 To markersList.Size - 1";
{
final int step51 = 1;
final int limit51 = (int) (mostCurrent._markerslist.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit51 ;_i = _i + step51 ) {
 //BA.debugLineNum = 1406;BA.debugLine="Dim markerAMostrar As OSMDroid_Marker";
_markeramostrar = new uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker();
 //BA.debugLineNum = 1407;BA.debugLine="markerAMostrar = markersList.Get(i)";
_markeramostrar = (uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker(), (uk.co.martinpearman.osmdroid.views.overlay.Marker)(mostCurrent._markerslist.Get(_i)));
 //BA.debugLineNum = 1409;BA.debugLine="If markerAMostrar.GetSnippet = \"geovin\" And list";
if ((_markeramostrar.GetSnippet()).equals("geovin") && (_listmostrarconfig.Get((int) (0))).equals((Object)("geovin"))) { 
 //BA.debugLineNum = 1410;BA.debugLine="Dim j As Int = listMostrar.IndexOf(markerAMostr";
_j = _listmostrar.IndexOf((Object)(_markeramostrar.GetTitle()));
 //BA.debugLineNum = 1411;BA.debugLine="If j > -1 Then";
if (_j>-1) { 
 //BA.debugLineNum = 1412;BA.debugLine="markersOverlay.AddItem(markerAMostrar)";
mostCurrent._markersoverlay.AddItem((uk.co.martinpearman.osmdroid.views.overlay.Marker)(_markeramostrar.getObject()));
 };
 }else if((_markeramostrar.GetSnippet()).equals(mostCurrent._main._username /*String*/ ) && (_listmostrarconfig.Get((int) (1))).equals((Object)("usuario"))) { 
 //BA.debugLineNum = 1415;BA.debugLine="Dim j As Int = listMostrar.IndexOf(markerAMostr";
_j = _listmostrar.IndexOf((Object)(_markeramostrar.GetTitle()));
 //BA.debugLineNum = 1416;BA.debugLine="If j > -1 Then";
if (_j>-1) { 
 //BA.debugLineNum = 1417;BA.debugLine="markersOverlay.AddItem(markerAMostrar)";
mostCurrent._markersoverlay.AddItem((uk.co.martinpearman.osmdroid.views.overlay.Marker)(_markeramostrar.getObject()));
 };
 }else if((_markeramostrar.GetSnippet()).equals("geovin") == false && (_markeramostrar.GetSnippet()).equals(mostCurrent._main._username /*String*/ ) == false && (_listmostrarconfig.Get((int) (2))).equals((Object)("otrosusuarios"))) { 
 //BA.debugLineNum = 1420;BA.debugLine="Dim j As Int = listMostrar.IndexOf(markerAMostr";
_j = _listmostrar.IndexOf((Object)(_markeramostrar.GetTitle()));
 //BA.debugLineNum = 1421;BA.debugLine="If j > -1 Then";
if (_j>-1) { 
 //BA.debugLineNum = 1422;BA.debugLine="markersOverlay.AddItem(markerAMostrar)";
mostCurrent._markersoverlay.AddItem((uk.co.martinpearman.osmdroid.views.overlay.Marker)(_markeramostrar.getObject()));
 };
 };
 }
};
 //BA.debugLineNum = 1427;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1428;BA.debugLine="ToastMessageShow(\"Se encontraron \" & (markersOve";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se encontraron "+BA.NumberToString((mostCurrent._markersoverlay.Size()))+" observaciones"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1430;BA.debugLine="ToastMessageShow(\"We found \" & (markersOverlay.S";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("We found "+BA.NumberToString((mostCurrent._markersoverlay.Size()))+" reports"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1434;BA.debugLine="MapView1.GetOverlays.Add(markersOverlay)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._markersoverlay.getObject()));
 //BA.debugLineNum = 1435;BA.debugLine="MapView1.Invalidate";
mostCurrent._mapview1.Invalidate();
 //BA.debugLineNum = 1436;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1437;BA.debugLine="btnMenu.Visible = True";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1438;BA.debugLine="End Sub";
return "";
}
public static String  _btnpoliticadatos_click() throws Exception{
 //BA.debugLineNum = 314;BA.debugLine="Sub btnPoliticaDatos_Click";
 //BA.debugLineNum = 315;BA.debugLine="StartActivity(frmPoliticaDatos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmpoliticadatos.getObject()));
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _btnrecomendaciones_click() throws Exception{
 //BA.debugLineNum = 1473;BA.debugLine="Sub btnRecomendaciones_Click";
 //BA.debugLineNum = 1474;BA.debugLine="StartActivity(frmRecomendaciones)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmrecomendaciones.getObject()));
 //BA.debugLineNum = 1475;BA.debugLine="End Sub";
return "";
}
public static String  _btnreferenciamapa_click() throws Exception{
 //BA.debugLineNum = 1443;BA.debugLine="Sub btnReferenciaMapa_Click";
 //BA.debugLineNum = 1444;BA.debugLine="Activity.LoadLayout(\"frmPrincipal_Explorar_Refere";
mostCurrent._activity.LoadLayout("frmPrincipal_Explorar_Referencia",mostCurrent.activityBA);
 //BA.debugLineNum = 1445;BA.debugLine="btnMenu.Visible = False";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1448;BA.debugLine="End Sub";
return "";
}
public static String  _btnverperfil_click() throws Exception{
 //BA.debugLineNum = 257;BA.debugLine="Sub btnVerPerfil_Click";
 //BA.debugLineNum = 258;BA.debugLine="If Main.username = \"guest\" Then";
if ((mostCurrent._main._username /*String*/ ).equals("guest")) { 
 //BA.debugLineNum = 259;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 260;BA.debugLine="ToastMessageShow(\"Necesita estar registrado par";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 262;BA.debugLine="ToastMessageShow(\"You need to be registered to";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You need to be registered to view your profile"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 265;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 266;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 }else {
 //BA.debugLineNum = 268;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 269;BA.debugLine="ToastMessageShow(\"Necesita tener internet para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita tener internet para editar su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 271;BA.debugLine="ToastMessageShow(\"You need to be online to che";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You need to be online to check your profile"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 };
 //BA.debugLineNum = 277;BA.debugLine="End Sub";
return "";
}
public static String  _btnzoomall_click() throws Exception{
 //BA.debugLineNum = 1104;BA.debugLine="Sub btnZoomAll_Click";
 //BA.debugLineNum = 1105;BA.debugLine="MapView1.GetController.SetZoom(5)";
mostCurrent._mapview1.GetController().SetZoom((int) (5));
 //BA.debugLineNum = 1106;BA.debugLine="End Sub";
return "";
}
public static String  _butcerrarreferencia_click() throws Exception{
 //BA.debugLineNum = 1449;BA.debugLine="Sub butCerrarReferencia_Click";
 //BA.debugLineNum = 1450;BA.debugLine="pnlReferencias.RemoveView";
mostCurrent._pnlreferencias.RemoveView();
 //BA.debugLineNum = 1451;BA.debugLine="btnMenu.Visible = True";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1452;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 483;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 485;BA.debugLine="If resetting_msg = True Then";
if (_resetting_msg==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 486;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 487;BA.debugLine="ToastMessageShow(\"Buscando señal... aguarde uno";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Buscando señal... aguarde unos segundos y vuelva a intentarlo"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 489;BA.debugLine="ToastMessageShow(\"Looking for signal... please";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Looking for signal... please wait a few seconds and try again"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 491;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 494;BA.debugLine="ComenzarReporte";
_comenzarreporte();
 //BA.debugLineNum = 495;BA.debugLine="End Sub";
return "";
}
public static String  _cargarconfigexplorar() throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chklista = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chknew = null;
anywheresoftware.b4a.objects.ImageViewWrapper _chkimg = null;
anywheresoftware.b4a.objects.ImageViewWrapper _chkimggeovin = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chk = null;
String[] _currentsp = null;
 //BA.debugLineNum = 1135;BA.debugLine="Sub CargarConfigExplorar";
 //BA.debugLineNum = 1140;BA.debugLine="If listaConfig.IsInitialized = False Then";
if (mostCurrent._listaconfig.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1141;BA.debugLine="listaConfig.Initialize";
mostCurrent._listaconfig.Initialize();
 }else {
 //BA.debugLineNum = 1144;BA.debugLine="listaConfig.Initialize";
mostCurrent._listaconfig.Initialize();
 //BA.debugLineNum = 1145;BA.debugLine="For i=0 To listaChequeados.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._listachequeados.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 1146;BA.debugLine="Dim chklista As CheckBox";
_chklista = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 1147;BA.debugLine="Dim chkNew As CheckBox";
_chknew = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 1148;BA.debugLine="chklista = listaChequeados.Get(i)";
_chklista = (anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper(), (android.widget.CheckBox)(mostCurrent._listachequeados.Get(_i)));
 //BA.debugLineNum = 1149;BA.debugLine="chkNew.Initialize(\"\")";
_chknew.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1150;BA.debugLine="chkNew.Text = chklista.Text";
_chknew.setText(BA.ObjectToCharSequence(_chklista.getText()));
 //BA.debugLineNum = 1151;BA.debugLine="chkNew.TextColor = Colors.Black";
_chknew.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1152;BA.debugLine="chkNew.Checked = chklista.Checked";
_chknew.setChecked(_chklista.getChecked());
 //BA.debugLineNum = 1153;BA.debugLine="listaConfig.Add(chkNew)";
mostCurrent._listaconfig.Add((Object)(_chknew.getObject()));
 //BA.debugLineNum = 1154;BA.debugLine="scrConfig.Panel.AddView(chkNew,0,50dip * (i-1),";
mostCurrent._scrconfig.getPanel().AddView((android.view.View)(_chknew.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))*(_i-1)),mostCurrent._activity.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 1155;BA.debugLine="scrConfig.Color = Colors.White";
mostCurrent._scrconfig.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1159;BA.debugLine="Dim chkImg As ImageView";
_chkimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1160;BA.debugLine="chkImg.Initialize(\"\")";
_chkimg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1161;BA.debugLine="Dim chkImgGeoVin As ImageView";
_chkimggeovin = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1162;BA.debugLine="chkImgGeoVin.Initialize(\"\")";
_chkimggeovin.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1163;BA.debugLine="If chkNew.Text = \"Triatoma infestans\" Then";
if ((_chknew.getText()).equals("Triatoma infestans")) { 
 //BA.debugLineNum = 1164;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker1.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1165;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_1.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma guasayana")) { 
 //BA.debugLineNum = 1167;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1168;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma sordida")) { 
 //BA.debugLineNum = 1171;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1172;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_5.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma garciabesi")) { 
 //BA.debugLineNum = 1175;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1176;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_3.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma delpontei")) { 
 //BA.debugLineNum = 1178;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1179;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma platensis")) { 
 //BA.debugLineNum = 1181;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1182;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_7.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma rubrovaria")) { 
 //BA.debugLineNum = 1184;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1185;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_8.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma eratyrusiformis")) { 
 //BA.debugLineNum = 1187;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1188;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_9.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma breyeri")) { 
 //BA.debugLineNum = 1190;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1191;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_10.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Panstrongylus rufotuberculatus")) { 
 //BA.debugLineNum = 1193;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1194;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_11.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma limai")) { 
 //BA.debugLineNum = 1196;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1197;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_12.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Psammolestes coreodes")) { 
 //BA.debugLineNum = 1199;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1200;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_13.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Panstrongylus geniculatus")) { 
 //BA.debugLineNum = 1202;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1203;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_14.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Panstrongylus guentheri")) { 
 //BA.debugLineNum = 1205;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1206;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_15.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Panstrongylus megistus")) { 
 //BA.debugLineNum = 1208;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1209;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_16.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chknew.getText()).equals("Triatoma patagónica")) { 
 //BA.debugLineNum = 1211;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAsset";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1212;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Di";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_4.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 1214;BA.debugLine="scrConfig.Panel.AddView(chkImg, Label1.Left + L";
mostCurrent._scrconfig.getPanel().AddView((android.view.View)(_chkimg.getObject()),(int) (mostCurrent._label1.getLeft()+mostCurrent._label1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (38))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))*(_i-1)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 1215;BA.debugLine="scrConfig.Panel.AddView(chkImgGeoVin, chkImg.Le";
mostCurrent._scrconfig.getPanel().AddView((android.view.View)(_chkimggeovin.getObject()),(int) (_chkimg.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))*(_i-1)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 }
};
 //BA.debugLineNum = 1218;BA.debugLine="scrConfig.Panel.Height = Main.speciesMap.Size *";
mostCurrent._scrconfig.getPanel().setHeight((int) (mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ .getSize()*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 1219;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1222;BA.debugLine="For i=0 To Main.speciesMap.Size - 1";
{
final int step75 = 1;
final int limit75 = (int) (mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ .getSize()-1);
_i = (int) (0) ;
for (;_i <= limit75 ;_i = _i + step75 ) {
 //BA.debugLineNum = 1223;BA.debugLine="listaChequeados.Initialize";
mostCurrent._listachequeados.Initialize();
 //BA.debugLineNum = 1224;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 1225;BA.debugLine="chk.Initialize(\"\")";
_chk.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1226;BA.debugLine="Dim currentsp() As String";
_currentsp = new String[(int) (0)];
java.util.Arrays.fill(_currentsp,"");
 //BA.debugLineNum = 1227;BA.debugLine="currentsp = Main.speciesMap.Get(i)";
_currentsp = (String[])(mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ .Get(_i));
 //BA.debugLineNum = 1228;BA.debugLine="chk.Text = currentsp(1)";
_chk.setText(BA.ObjectToCharSequence(_currentsp[(int) (1)]));
 //BA.debugLineNum = 1229;BA.debugLine="chk.TextColor = Colors.DarkGray";
_chk.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 1230;BA.debugLine="chk.Checked = True";
_chk.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1231;BA.debugLine="listaConfig.Add(chk)";
mostCurrent._listaconfig.Add((Object)(_chk.getObject()));
 //BA.debugLineNum = 1232;BA.debugLine="scrConfig.Panel.AddView(chk,0,50dip * (i-1), Act";
mostCurrent._scrconfig.getPanel().AddView((android.view.View)(_chk.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))*(_i-1)),mostCurrent._activity.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 1236;BA.debugLine="Dim chkImg As ImageView";
_chkimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1237;BA.debugLine="chkImg.Initialize(\"\")";
_chkimg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1238;BA.debugLine="Dim chkImgGeoVin As ImageView";
_chkimggeovin = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1239;BA.debugLine="chkImgGeoVin.Initialize(\"\")";
_chkimggeovin.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1240;BA.debugLine="If chk.Text = \"Triatoma infestans\" Then";
if ((_chk.getText()).equals("Triatoma infestans")) { 
 //BA.debugLineNum = 1241;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker1.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1242;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_1.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma guasayana")) { 
 //BA.debugLineNum = 1244;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1245;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma sordida")) { 
 //BA.debugLineNum = 1248;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1249;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_5.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma garciabesi")) { 
 //BA.debugLineNum = 1252;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1253;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_3.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma delpontei")) { 
 //BA.debugLineNum = 1255;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1256;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma platensis")) { 
 //BA.debugLineNum = 1258;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1259;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_7.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma rubrovaria")) { 
 //BA.debugLineNum = 1261;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1262;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_8.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma eratyrusiformis")) { 
 //BA.debugLineNum = 1264;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1265;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_9.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma breyeri")) { 
 //BA.debugLineNum = 1267;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1268;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_10.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Panstrongylus rufotuberculatus")) { 
 //BA.debugLineNum = 1270;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1271;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_11.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma limai")) { 
 //BA.debugLineNum = 1273;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1274;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_12.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Psammolestes coreodes")) { 
 //BA.debugLineNum = 1276;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1277;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_13.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Panstrongylus geniculatus")) { 
 //BA.debugLineNum = 1279;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1280;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_14.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Panstrongylus guentheri")) { 
 //BA.debugLineNum = 1282;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1283;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_15.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Panstrongylus megistus")) { 
 //BA.debugLineNum = 1285;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1286;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_16.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_chk.getText()).equals("Triatoma patagónica")) { 
 //BA.debugLineNum = 1288;BA.debugLine="chkImg.Bitmap = LoadBitmapSample(File.DirAssets";
_chkimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1289;BA.debugLine="chkImgGeoVin.Bitmap = LoadBitmapSample(File.Dir";
_chkimggeovin.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_4.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 1291;BA.debugLine="scrConfig.Panel.AddView(chkImg, Label1.Left + La";
mostCurrent._scrconfig.getPanel().AddView((android.view.View)(_chkimg.getObject()),(int) (mostCurrent._label1.getLeft()+mostCurrent._label1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (38))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))*(_i-1)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 1292;BA.debugLine="scrConfig.Panel.AddView(chkImgGeoVin, chkImg.Lef";
mostCurrent._scrconfig.getPanel().AddView((android.view.View)(_chkimggeovin.getObject()),(int) (_chkimg.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))*(_i-1)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 }
};
 //BA.debugLineNum = 1298;BA.debugLine="scrConfig.Panel.Height = Main.speciesMap.Size * 5";
mostCurrent._scrconfig.getPanel().setHeight((int) (mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ .getSize()*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 1299;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmapa() throws Exception{
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
 //BA.debugLineNum = 612;BA.debugLine="Sub CargarMapa";
 //BA.debugLineNum = 614;BA.debugLine="If LastLocation.IsInitialized Then";
if (_lastlocation.IsInitialized()) { 
 //BA.debugLineNum = 615;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
 //BA.debugLineNum = 616;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
 }else {
 //BA.debugLineNum = 619;BA.debugLine="lblLat.Text = \"-34.9204950\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence("-34.9204950"));
 //BA.debugLineNum = 620;BA.debugLine="lblLon.Text = \"-57.9535660\"";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence("-57.9535660"));
 };
 //BA.debugLineNum = 622;BA.debugLine="MapView1.Initialize(\"MapView1\")";
mostCurrent._mapview1.Initialize(mostCurrent.activityBA,"MapView1");
 //BA.debugLineNum = 623;BA.debugLine="MapView1.SetBuiltInZoomControls(True)";
mostCurrent._mapview1.SetBuiltInZoomControls(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 624;BA.debugLine="MapView1.SetMultiTouchControls(True)";
mostCurrent._mapview1.SetMultiTouchControls(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 626;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
 //BA.debugLineNum = 627;BA.debugLine="geopoint1.Initialize(lblLat.Text,lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
 //BA.debugLineNum = 628;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 //BA.debugLineNum = 629;BA.debugLine="MapView1.GetController.SetZoom(6)";
mostCurrent._mapview1.GetController().SetZoom((int) (6));
 //BA.debugLineNum = 631;BA.debugLine="pnlMapa.RemoveAllViews";
mostCurrent._pnlmapa.RemoveAllViews();
 //BA.debugLineNum = 632;BA.debugLine="pnlMapa.AddView(MapView1, 0,0, 100%x, 100%y)";
mostCurrent._pnlmapa.AddView((android.view.View)(mostCurrent._mapview1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 633;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 634;BA.debugLine="SimpleLocationOverlay1.Initialize()";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 //BA.debugLineNum = 635;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
 //BA.debugLineNum = 636;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 }else {
 //BA.debugLineNum = 638;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
 //BA.debugLineNum = 639;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 };
 //BA.debugLineNum = 641;BA.debugLine="GetMiMapa";
_getmimapa();
 //BA.debugLineNum = 642;BA.debugLine="End Sub";
return "";
}
public static String  _comenzarreporte() throws Exception{
anywheresoftware.b4a.objects.collections.List _neweval = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _currentprojectmap = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _usernametemp = "";
 //BA.debugLineNum = 497;BA.debugLine="Sub ComenzarReporte";
 //BA.debugLineNum = 498;BA.debugLine="Main.fotopath0 = \"\"";
mostCurrent._main._fotopath0 /*String*/  = "";
 //BA.debugLineNum = 499;BA.debugLine="Main.fotopath1 = \"\"";
mostCurrent._main._fotopath1 /*String*/  = "";
 //BA.debugLineNum = 500;BA.debugLine="Main.fotopath2 = \"\"";
mostCurrent._main._fotopath2 /*String*/  = "";
 //BA.debugLineNum = 501;BA.debugLine="Main.fotopath3 = \"\"";
mostCurrent._main._fotopath3 /*String*/  = "";
 //BA.debugLineNum = 502;BA.debugLine="Main.latitud = \"\"";
mostCurrent._main._latitud /*String*/  = "";
 //BA.debugLineNum = 503;BA.debugLine="Main.longitud = \"\"";
mostCurrent._main._longitud /*String*/  = "";
 //BA.debugLineNum = 508;BA.debugLine="Dim newEval As List";
_neweval = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 509;BA.debugLine="newEval.Initialize";
_neweval.Initialize();
 //BA.debugLineNum = 510;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 511;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 512;BA.debugLine="m.Put(\"usuario\", Main.username)";
_m.Put((Object)("usuario"),(Object)(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 513;BA.debugLine="m.Put(\"tipoEval\", \"organismo\")";
_m.Put((Object)("tipoEval"),(Object)("organismo"));
 //BA.debugLineNum = 514;BA.debugLine="newEval.Add(m)";
_neweval.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 515;BA.debugLine="Try";
try { //BA.debugLineNum = 516;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"markers_local\"";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local",_neweval);
 } 
       catch (Exception e17) {
			processBA.setLastException(e17); //BA.debugLineNum = 518;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("21638421",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 519;BA.debugLine="Log(\"dbdir=\" & Starter.dbdir)";
anywheresoftware.b4a.keywords.Common.LogImpl("21638422","dbdir="+mostCurrent._starter._dbdir /*String*/ ,0);
 //BA.debugLineNum = 520;BA.debugLine="ToastMessageShow(\"Hubo un error con la base de d";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error con la base de datos, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 521;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 527;BA.debugLine="Log(\"deviceID: \" & Main.deviceID)";
anywheresoftware.b4a.keywords.Common.LogImpl("21638430","deviceID: "+mostCurrent._main._deviceid /*String*/ ,0);
 //BA.debugLineNum = 529;BA.debugLine="Dim currentprojectMap As Map";
_currentprojectmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 530;BA.debugLine="currentprojectMap.Initialize";
_currentprojectmap.Initialize();
 //BA.debugLineNum = 531;BA.debugLine="Try";
try { //BA.debugLineNum = 532;BA.debugLine="currentprojectMap = DBUtils.ExecuteMap(Starter.s";
_currentprojectmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM markers_local ORDER BY id DESC LIMIT 1",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 533;BA.debugLine="Log(\"currentprojectMap: \" & currentprojectMap.Ge";
anywheresoftware.b4a.keywords.Common.LogImpl("21638436","currentprojectMap: "+BA.ObjectToString(_currentprojectmap.Get((Object)("id"))),0);
 //BA.debugLineNum = 534;BA.debugLine="Log(\"deviceID: \" & Main.deviceID.SubString2(0,6)";
anywheresoftware.b4a.keywords.Common.LogImpl("21638437","deviceID: "+mostCurrent._main._deviceid /*String*/ .substring((int) (0),(int) (6)),0);
 //BA.debugLineNum = 535;BA.debugLine="If currentprojectMap = Null Or currentprojectMap";
if (_currentprojectmap== null || _currentprojectmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 536;BA.debugLine="Main.currentproject = 1";
mostCurrent._main._currentproject /*String*/  = BA.NumberToString(1);
 //BA.debugLineNum = 537;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 538;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 539;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 540;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 541;BA.debugLine="Main.datecurrentproject = DateTime.Date(DateTim";
mostCurrent._main._datecurrentproject /*String*/  = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 542;BA.debugLine="Dim usernameTemp As String";
_usernametemp = "";
 //BA.debugLineNum = 543;BA.debugLine="If Main.username = \"guest\" Then";
if ((mostCurrent._main._username /*String*/ ).equals("guest")) { 
 //BA.debugLineNum = 544;BA.debugLine="usernameTemp = \"guest\" & Main.deviceID.SubStri";
_usernametemp = "guest"+mostCurrent._main._deviceid /*String*/ .substring((int) (0),(int) (6));
 }else {
 //BA.debugLineNum = 546;BA.debugLine="usernameTemp = Main.username";
_usernametemp = mostCurrent._main._username /*String*/ ;
 };
 //BA.debugLineNum = 548;BA.debugLine="fullidcurrentproject = usernameTemp & \"_\" & Mai";
_fullidcurrentproject = _usernametemp+"_"+mostCurrent._main._currentproject /*String*/ +"_"+mostCurrent._main._datecurrentproject /*String*/ ;
 //BA.debugLineNum = 549;BA.debugLine="Log(\"fullidcurrentproject: \" & fullidcurrentpro";
anywheresoftware.b4a.keywords.Common.LogImpl("21638452","fullidcurrentproject: "+_fullidcurrentproject,0);
 //BA.debugLineNum = 550;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","fullID",(Object)(_fullidcurrentproject),_map1);
 }else {
 //BA.debugLineNum = 552;BA.debugLine="Main.currentproject = currentprojectMap.Get(\"id";
mostCurrent._main._currentproject /*String*/  = BA.ObjectToString(_currentprojectmap.Get((Object)("id")));
 //BA.debugLineNum = 553;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 554;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 555;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 556;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 557;BA.debugLine="Main.datecurrentproject = DateTime.Date(DateTim";
mostCurrent._main._datecurrentproject /*String*/  = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 558;BA.debugLine="Dim usernameTemp As String";
_usernametemp = "";
 //BA.debugLineNum = 559;BA.debugLine="If Main.username = \"guest\" Then";
if ((mostCurrent._main._username /*String*/ ).equals("guest")) { 
 //BA.debugLineNum = 560;BA.debugLine="usernameTemp = \"guest\" & Main.deviceID.SubStri";
_usernametemp = "guest"+mostCurrent._main._deviceid /*String*/ .substring((int) (0),(int) (6));
 }else {
 //BA.debugLineNum = 562;BA.debugLine="usernameTemp = Main.username";
_usernametemp = mostCurrent._main._username /*String*/ ;
 };
 //BA.debugLineNum = 564;BA.debugLine="Log(\"check1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("21638467","check1",0);
 //BA.debugLineNum = 565;BA.debugLine="fullidcurrentproject = usernameTemp & \"_\" & Mai";
_fullidcurrentproject = _usernametemp+"_"+mostCurrent._main._currentproject /*String*/ +"_"+mostCurrent._main._datecurrentproject /*String*/ ;
 //BA.debugLineNum = 566;BA.debugLine="Log(\"fullidcurrentproject: \" & fullidcurrentpro";
anywheresoftware.b4a.keywords.Common.LogImpl("21638469","fullidcurrentproject: "+_fullidcurrentproject,0);
 //BA.debugLineNum = 567;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","fullID",(Object)(_fullidcurrentproject),_map1);
 };
 } 
       catch (Exception e64) {
			processBA.setLastException(e64); //BA.debugLineNum = 571;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("21638474",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 572;BA.debugLine="ToastMessageShow(\"Hubo un error, intente de nuev";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 573;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 577;BA.debugLine="Main.Idproyecto = Main.currentproject";
mostCurrent._main._idproyecto /*int*/  = (int)(Double.parseDouble(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 578;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 580;BA.debugLine="End Sub";
return "";
}
public static String  _detectarposicion() throws Exception{
 //BA.debugLineNum = 646;BA.debugLine="Sub DetectarPosicion";
 //BA.debugLineNum = 649;BA.debugLine="fondoblanco.Initialize(\"fondoblanco\")";
mostCurrent._fondoblanco.Initialize(mostCurrent.activityBA,"fondoblanco");
 //BA.debugLineNum = 650;BA.debugLine="detectandoLabel.Initialize(\"\")";
mostCurrent._detectandolabel.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 651;BA.debugLine="detectandoLabel.TextColor = Colors.White";
mostCurrent._detectandolabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 652;BA.debugLine="detectandoLabel.Gravity = Gravity.CENTER_HORIZONT";
mostCurrent._detectandolabel.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 654;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 655;BA.debugLine="detectandoLabel.Text = \"Buscando tu posición aut";
mostCurrent._detectandolabel.setText(BA.ObjectToCharSequence("Buscando tu posición automáticamente..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 657;BA.debugLine="detectandoLabel.Text = \"Looking for your positio";
mostCurrent._detectandolabel.setText(BA.ObjectToCharSequence("Looking for your position automatically..."));
 };
 //BA.debugLineNum = 661;BA.debugLine="fondoblanco.Color = Colors.ARGB(150, 64,64,64)";
mostCurrent._fondoblanco.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
 //BA.debugLineNum = 662;BA.debugLine="Activity.AddView(fondoblanco, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondoblanco.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 663;BA.debugLine="Activity.AddView(detectandoLabel, 5%x, 55%y, 80%x";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._detectandolabel.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (55),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 664;BA.debugLine="If FusedLocationProvider1.IsInitialized = False T";
if (_fusedlocationprovider1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 666;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProviderExplorar");
 //BA.debugLineNum = 667;BA.debugLine="Log(\"init fusedlocationproviderExplorar\")";
anywheresoftware.b4a.keywords.Common.LogImpl("21900565","init fusedlocationproviderExplorar",0);
 };
 //BA.debugLineNum = 669;BA.debugLine="If FusedLocationProvider1.IsConnected = False The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 670;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 //BA.debugLineNum = 671;BA.debugLine="Log(\"connecting fusedlocationproviderExplorar\")";
anywheresoftware.b4a.keywords.Common.LogImpl("21900569","connecting fusedlocationproviderExplorar",0);
 };
 //BA.debugLineNum = 673;BA.debugLine="End Sub";
return "";
}
public static String  _fondoblanco_click() throws Exception{
 //BA.debugLineNum = 677;BA.debugLine="Sub fondoblanco_Click";
 //BA.debugLineNum = 681;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
 //BA.debugLineNum = 682;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 //BA.debugLineNum = 683;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionfailed(int _connectionresult1) throws Exception{
 //BA.debugLineNum = 686;BA.debugLine="Sub FusedLocationProvider1_ConnectionFailed(Connec";
 //BA.debugLineNum = 687;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionFailed\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22031617","FusedLocationProvider1_ConnectionFailed",0);
 //BA.debugLineNum = 691;BA.debugLine="Select ConnectionResult1";
switch (BA.switchObjectToInt(_connectionresult1,_fusedlocationprovider1.ConnectionResult.NETWORK_ERROR)) {
case 0: {
 //BA.debugLineNum = 695;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 break; }
default: {
 break; }
}
;
 //BA.debugLineNum = 699;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuccess() throws Exception{
uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest _locationrequest1 = null;
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder _locationsettingsrequestbuilder1 = null;
 //BA.debugLineNum = 700;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuccess";
 //BA.debugLineNum = 701;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuccess\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22097153","FusedLocationProvider1_ConnectionSuccess",0);
 //BA.debugLineNum = 702;BA.debugLine="Dim LocationRequest1 As LocationRequest";
_locationrequest1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest();
 //BA.debugLineNum = 703;BA.debugLine="LocationRequest1.Initialize";
_locationrequest1.Initialize();
 //BA.debugLineNum = 704;BA.debugLine="LocationRequest1.SetInterval(1000)	'	1000 millise";
_locationrequest1.SetInterval((long) (1000));
 //BA.debugLineNum = 706;BA.debugLine="LocationRequest1.SetPriority(LocationRequest1.Pri";
_locationrequest1.SetPriority(_locationrequest1.Priority.PRIORITY_HIGH_ACCURACY);
 //BA.debugLineNum = 707;BA.debugLine="LocationRequest1.SetSmallestDisplacement(1)	'	1 m";
_locationrequest1.SetSmallestDisplacement((float) (1));
 //BA.debugLineNum = 709;BA.debugLine="Dim LocationSettingsRequestBuilder1 As LocationSe";
_locationsettingsrequestbuilder1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder();
 //BA.debugLineNum = 710;BA.debugLine="LocationSettingsRequestBuilder1.Initialize";
_locationsettingsrequestbuilder1.Initialize();
 //BA.debugLineNum = 711;BA.debugLine="LocationSettingsRequestBuilder1.AddLocationReques";
_locationsettingsrequestbuilder1.AddLocationRequest((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
 //BA.debugLineNum = 712;BA.debugLine="FusedLocationProvider1.CheckLocationSettings(Loca";
_fusedlocationprovider1.CheckLocationSettings(_locationsettingsrequestbuilder1.Build());
 //BA.debugLineNum = 714;BA.debugLine="FusedLocationProvider1.RequestLocationUpdates(Loc";
_fusedlocationprovider1.RequestLocationUpdates((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
 //BA.debugLineNum = 717;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuspended(int _suspendedcause1) throws Exception{
 //BA.debugLineNum = 718;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuspended(Sus";
 //BA.debugLineNum = 719;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuspended\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22162689","FusedLocationProvider1_ConnectionSuspended",0);
 //BA.debugLineNum = 723;BA.debugLine="Select SuspendedCause1";
switch (BA.switchObjectToInt(_suspendedcause1,_fusedlocationprovider1.SuspendedCause.CAUSE_NETWORK_LOST,_fusedlocationprovider1.SuspendedCause.CAUSE_SERVICE_DISCONNECTED)) {
case 0: {
 break; }
case 1: {
 break; }
}
;
 //BA.debugLineNum = 729;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
 //BA.debugLineNum = 730;BA.debugLine="Sub FusedLocationProvider1_LocationChanged(Locatio";
 //BA.debugLineNum = 731;BA.debugLine="Log(\"FusedLocationProvider1_LocationChanged\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22228225","FusedLocationProvider1_LocationChanged",0);
 //BA.debugLineNum = 732;BA.debugLine="LastLocation=Location1";
_lastlocation = _location1;
 //BA.debugLineNum = 733;BA.debugLine="Log(LastLocation.Latitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("22228227",BA.NumberToString(_lastlocation.getLatitude()),0);
 //BA.debugLineNum = 734;BA.debugLine="Log(LastLocation.Longitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("22228228",BA.NumberToString(_lastlocation.getLongitude()),0);
 //BA.debugLineNum = 735;BA.debugLine="UpdateUI";
_updateui();
 //BA.debugLineNum = 736;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationsettingschecked(uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsResult _locationsettingsresult1) throws Exception{
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus _locationsettingsstatus1 = null;
 //BA.debugLineNum = 737;BA.debugLine="Sub FusedLocationProvider1_LocationSettingsChecked";
 //BA.debugLineNum = 738;BA.debugLine="Log(\"FusedLocationProvider1_LocationSettingsCheck";
anywheresoftware.b4a.keywords.Common.LogImpl("22293761","FusedLocationProvider1_LocationSettingsChecked",0);
 //BA.debugLineNum = 739;BA.debugLine="Dim LocationSettingsStatus1 As LocationSettingsSt";
_locationsettingsstatus1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus();
_locationsettingsstatus1 = _locationsettingsresult1.GetLocationSettingsStatus();
 //BA.debugLineNum = 740;BA.debugLine="Select LocationSettingsStatus1.GetStatusCode";
switch (BA.switchObjectToInt(_locationsettingsstatus1.GetStatusCode(),_locationsettingsstatus1.StatusCodes.RESOLUTION_REQUIRED,_locationsettingsstatus1.StatusCodes.SETTINGS_CHANGE_UNAVAILABLE,_locationsettingsstatus1.StatusCodes.SUCCESS)) {
case 0: {
 //BA.debugLineNum = 742;BA.debugLine="Log(\"RESOLUTION_REQUIRED\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22293765","RESOLUTION_REQUIRED",0);
 //BA.debugLineNum = 745;BA.debugLine="LocationSettingsStatus1.StartResolutionDialog(\"";
_locationsettingsstatus1.StartResolutionDialog(mostCurrent.activityBA,"LocationSettingsResult1");
 break; }
case 1: {
 //BA.debugLineNum = 747;BA.debugLine="Log(\"SETTINGS_CHANGE_UNAVAILABLE\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22293770","SETTINGS_CHANGE_UNAVAILABLE",0);
 //BA.debugLineNum = 750;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 751;BA.debugLine="Msgbox(\"Error, tu dispositivo no tiene localiz";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error, tu dispositivo no tiene localización. Busca tu posición en el mapa!"),BA.ObjectToCharSequence("Problem"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 753;BA.debugLine="Msgbox(\"Error, your device cannot be located.";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error, your device cannot be located. Find your location in the map!"),BA.ObjectToCharSequence("Problem"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 755;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 2: {
 //BA.debugLineNum = 757;BA.debugLine="Log(\"SUCCESS\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22293780","SUCCESS",0);
 break; }
}
;
 //BA.debugLineNum = 761;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa() throws Exception{
cepave.geovin.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 821;BA.debugLine="Sub GetMiMapa";
 //BA.debugLineNum = 823;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 824;BA.debugLine="ProgressDialogShow(\"Buscando puntos cercanos...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando puntos cercanos..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 826;BA.debugLine="ProgressDialogShow(\"Getting nearby reports...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Getting nearby reports..."));
 };
 //BA.debugLineNum = 831;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 832;BA.debugLine="dd.url = Main.serverPath & \"/connect2/getallmapa.";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect2/getallmapa.php";
 //BA.debugLineNum = 833;BA.debugLine="dd.EventName = \"GetMiMapa\"";
_dd.EventName /*String*/  = "GetMiMapa";
 //BA.debugLineNum = 834;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmprincipal.getObject();
 //BA.debugLineNum = 835;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 837;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa_complete(cepave.geovin.httpjob _job) throws Exception{
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
 //BA.debugLineNum = 839;BA.debugLine="Sub GetMiMapa_Complete(Job As HttpJob)";
 //BA.debugLineNum = 840;BA.debugLine="Log(\"GetMapa messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("22555905","GetMapa messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 841;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 842;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 843;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 844;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 845;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 846;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 847;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 848;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 849;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 850;BA.debugLine="ToastMessageShow(\"No encuentro sitios anterior";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 852;BA.debugLine="ToastMessageShow(\"Cannot find previous reports";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cannot find previous reports, try again later"),anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 855;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 856;BA.debugLine="ToastMessageShow(\"No encuentro sitios anterior";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 858;BA.debugLine="ToastMessageShow(\"Cannot find previous reports";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cannot find previous reports, try again later"),anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("GetMapaOk")) { 
 //BA.debugLineNum = 863;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 864;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 870;BA.debugLine="markersOverlay.Initialize(\"markersOverlay\", Map";
mostCurrent._markersoverlay.Initialize(processBA,"markersOverlay",(org.osmdroid.views.MapView)(mostCurrent._mapview1.getObject()));
 //BA.debugLineNum = 871;BA.debugLine="MapView1.GetOverlays.Add(markersOverlay)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._markersoverlay.getObject()));
 //BA.debugLineNum = 874;BA.debugLine="markersList.Initialize()";
mostCurrent._markerslist.Initialize();
 //BA.debugLineNum = 877;BA.debugLine="Dim markerGV_1 As Bitmap";
_markergv_1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 878;BA.debugLine="Dim markerGV_2 As Bitmap";
_markergv_2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 879;BA.debugLine="Dim markerGV_3 As Bitmap";
_markergv_3 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 880;BA.debugLine="Dim markerGV_4 As Bitmap";
_markergv_4 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 881;BA.debugLine="Dim markerGV_5 As Bitmap";
_markergv_5 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 882;BA.debugLine="Dim markerGV_6 As Bitmap";
_markergv_6 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 883;BA.debugLine="Dim markerGV_7 As Bitmap";
_markergv_7 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 884;BA.debugLine="Dim markerGV_8 As Bitmap";
_markergv_8 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 885;BA.debugLine="Dim markerGV_9 As Bitmap";
_markergv_9 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 886;BA.debugLine="Dim markerGV_10 As Bitmap";
_markergv_10 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 887;BA.debugLine="Dim markerGV_11 As Bitmap";
_markergv_11 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 888;BA.debugLine="Dim markerGV_12 As Bitmap";
_markergv_12 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 889;BA.debugLine="Dim markerGV_13 As Bitmap";
_markergv_13 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 890;BA.debugLine="Dim markerGV_14 As Bitmap";
_markergv_14 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 891;BA.debugLine="Dim markerGV_15 As Bitmap";
_markergv_15 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 892;BA.debugLine="Dim markerGV_16 As Bitmap";
_markergv_16 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 894;BA.debugLine="Dim marker1 As Bitmap";
_marker1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 895;BA.debugLine="Dim marker2 As Bitmap";
_marker2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 896;BA.debugLine="Dim marker3 As Bitmap";
_marker3 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 897;BA.debugLine="Dim marker4 As Bitmap";
_marker4 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 898;BA.debugLine="Dim marker5 As Bitmap";
_marker5 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 899;BA.debugLine="Dim marker6 As Bitmap";
_marker6 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 900;BA.debugLine="Dim marker7 As Bitmap";
_marker7 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 901;BA.debugLine="Dim marker8 As Bitmap";
_marker8 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 902;BA.debugLine="Dim marker9 As Bitmap";
_marker9 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 903;BA.debugLine="Dim marker10 As Bitmap";
_marker10 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 904;BA.debugLine="Dim marker11 As Bitmap";
_marker11 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 905;BA.debugLine="Dim marker12 As Bitmap";
_marker12 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 906;BA.debugLine="Dim marker13 As Bitmap";
_marker13 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 907;BA.debugLine="Dim marker14 As Bitmap";
_marker14 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 908;BA.debugLine="Dim marker15 As Bitmap";
_marker15 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 909;BA.debugLine="Dim marker16 As Bitmap";
_marker16 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 912;BA.debugLine="markerGV_1.Initialize(File.DirAssets, \"markerGV";
_markergv_1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_1.png");
 //BA.debugLineNum = 913;BA.debugLine="markerGV_2.Initialize(File.DirAssets, \"markerGV";
_markergv_2.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_2.png");
 //BA.debugLineNum = 914;BA.debugLine="markerGV_3.Initialize(File.DirAssets, \"markerGV";
_markergv_3.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_3.png");
 //BA.debugLineNum = 915;BA.debugLine="markerGV_4.Initialize(File.DirAssets, \"markerGV";
_markergv_4.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_4.png");
 //BA.debugLineNum = 916;BA.debugLine="markerGV_5.Initialize(File.DirAssets, \"markerGV";
_markergv_5.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_5.png");
 //BA.debugLineNum = 917;BA.debugLine="markerGV_6.Initialize(File.DirAssets, \"markerGV";
_markergv_6.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_6.png");
 //BA.debugLineNum = 918;BA.debugLine="markerGV_7.Initialize(File.DirAssets, \"markerGV";
_markergv_7.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_7.png");
 //BA.debugLineNum = 919;BA.debugLine="markerGV_8.Initialize(File.DirAssets, \"markerGV";
_markergv_8.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_8.png");
 //BA.debugLineNum = 920;BA.debugLine="markerGV_9.Initialize(File.DirAssets, \"markerGV";
_markergv_9.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_9.png");
 //BA.debugLineNum = 921;BA.debugLine="markerGV_10.Initialize(File.DirAssets, \"markerG";
_markergv_10.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_10.png");
 //BA.debugLineNum = 922;BA.debugLine="markerGV_11.Initialize(File.DirAssets, \"markerG";
_markergv_11.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_11.png");
 //BA.debugLineNum = 923;BA.debugLine="markerGV_12.Initialize(File.DirAssets, \"markerG";
_markergv_12.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_12.png");
 //BA.debugLineNum = 924;BA.debugLine="markerGV_13.Initialize(File.DirAssets, \"markerG";
_markergv_13.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_13.png");
 //BA.debugLineNum = 925;BA.debugLine="markerGV_14.Initialize(File.DirAssets, \"markerG";
_markergv_14.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_14.png");
 //BA.debugLineNum = 926;BA.debugLine="markerGV_15.Initialize(File.DirAssets, \"markerG";
_markergv_15.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_15.png");
 //BA.debugLineNum = 927;BA.debugLine="markerGV_16.Initialize(File.DirAssets, \"markerG";
_markergv_16.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"markerGV_16.png");
 //BA.debugLineNum = 929;BA.debugLine="marker1.Initialize(File.DirAssets, \"marker1.png";
_marker1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker1.png");
 //BA.debugLineNum = 930;BA.debugLine="marker2.Initialize(File.DirAssets, \"marker2.png";
_marker2.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker2.png");
 //BA.debugLineNum = 931;BA.debugLine="marker3.Initialize(File.DirAssets, \"marker3.png";
_marker3.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker3.png");
 //BA.debugLineNum = 932;BA.debugLine="marker4.Initialize(File.DirAssets, \"marker4.png";
_marker4.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker4.png");
 //BA.debugLineNum = 933;BA.debugLine="marker5.Initialize(File.DirAssets, \"marker5.png";
_marker5.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker5.png");
 //BA.debugLineNum = 934;BA.debugLine="marker6.Initialize(File.DirAssets, \"marker6.png";
_marker6.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker6.png");
 //BA.debugLineNum = 935;BA.debugLine="marker7.Initialize(File.DirAssets, \"marker7.png";
_marker7.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker7.png");
 //BA.debugLineNum = 936;BA.debugLine="marker8.Initialize(File.DirAssets, \"marker8.png";
_marker8.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker8.png");
 //BA.debugLineNum = 937;BA.debugLine="marker9.Initialize(File.DirAssets, \"marker9.png";
_marker9.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker9.png");
 //BA.debugLineNum = 938;BA.debugLine="marker10.Initialize(File.DirAssets, \"marker10.p";
_marker10.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker10.png");
 //BA.debugLineNum = 939;BA.debugLine="marker11.Initialize(File.DirAssets, \"marker11.p";
_marker11.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker11.png");
 //BA.debugLineNum = 940;BA.debugLine="marker12.Initialize(File.DirAssets, \"marker12.p";
_marker12.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker12.png");
 //BA.debugLineNum = 941;BA.debugLine="marker13.Initialize(File.DirAssets, \"marker13.p";
_marker13.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker13.png");
 //BA.debugLineNum = 942;BA.debugLine="marker14.Initialize(File.DirAssets, \"marker14.p";
_marker14.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker14.png");
 //BA.debugLineNum = 943;BA.debugLine="marker15.Initialize(File.DirAssets, \"marker15.p";
_marker15.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker15.png");
 //BA.debugLineNum = 944;BA.debugLine="marker16.Initialize(File.DirAssets, \"marker16.p";
_marker16.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker16.png");
 //BA.debugLineNum = 946;BA.debugLine="Dim markerGV1dr As BitmapDrawable";
_markergv1dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 947;BA.debugLine="Dim markerGV2dr As BitmapDrawable";
_markergv2dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 948;BA.debugLine="Dim markerGV3dr As BitmapDrawable";
_markergv3dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 949;BA.debugLine="Dim markerGV4dr As BitmapDrawable";
_markergv4dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 950;BA.debugLine="Dim markerGV5dr As BitmapDrawable";
_markergv5dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 951;BA.debugLine="Dim markerGV6dr As BitmapDrawable";
_markergv6dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 952;BA.debugLine="Dim markerGV7dr As BitmapDrawable";
_markergv7dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 953;BA.debugLine="Dim markerGV8dr As BitmapDrawable";
_markergv8dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 954;BA.debugLine="Dim markerGV9dr As BitmapDrawable";
_markergv9dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 955;BA.debugLine="Dim markerGV10dr As BitmapDrawable";
_markergv10dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 956;BA.debugLine="Dim markerGV11dr As BitmapDrawable";
_markergv11dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 957;BA.debugLine="Dim markerGV12dr As BitmapDrawable";
_markergv12dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 958;BA.debugLine="Dim markerGV13dr As BitmapDrawable";
_markergv13dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 959;BA.debugLine="Dim markerGV14dr As BitmapDrawable";
_markergv14dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 960;BA.debugLine="Dim markerGV15dr As BitmapDrawable";
_markergv15dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 961;BA.debugLine="Dim markerGV16dr As BitmapDrawable";
_markergv16dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 963;BA.debugLine="markerGV1dr.Initialize(markerGV_1)";
_markergv1dr.Initialize((android.graphics.Bitmap)(_markergv_1.getObject()));
 //BA.debugLineNum = 964;BA.debugLine="markerGV2dr.Initialize(markerGV_2)";
_markergv2dr.Initialize((android.graphics.Bitmap)(_markergv_2.getObject()));
 //BA.debugLineNum = 965;BA.debugLine="markerGV3dr.Initialize(markerGV_3)";
_markergv3dr.Initialize((android.graphics.Bitmap)(_markergv_3.getObject()));
 //BA.debugLineNum = 966;BA.debugLine="markerGV4dr.Initialize(markerGV_4)";
_markergv4dr.Initialize((android.graphics.Bitmap)(_markergv_4.getObject()));
 //BA.debugLineNum = 967;BA.debugLine="markerGV5dr.Initialize(markerGV_5)";
_markergv5dr.Initialize((android.graphics.Bitmap)(_markergv_5.getObject()));
 //BA.debugLineNum = 968;BA.debugLine="markerGV6dr.Initialize(markerGV_6)";
_markergv6dr.Initialize((android.graphics.Bitmap)(_markergv_6.getObject()));
 //BA.debugLineNum = 969;BA.debugLine="markerGV7dr.Initialize(markerGV_7)";
_markergv7dr.Initialize((android.graphics.Bitmap)(_markergv_7.getObject()));
 //BA.debugLineNum = 970;BA.debugLine="markerGV8dr.Initialize(markerGV_8)";
_markergv8dr.Initialize((android.graphics.Bitmap)(_markergv_8.getObject()));
 //BA.debugLineNum = 971;BA.debugLine="markerGV9dr.Initialize(markerGV_9)";
_markergv9dr.Initialize((android.graphics.Bitmap)(_markergv_9.getObject()));
 //BA.debugLineNum = 972;BA.debugLine="markerGV10dr.Initialize(markerGV_10)";
_markergv10dr.Initialize((android.graphics.Bitmap)(_markergv_10.getObject()));
 //BA.debugLineNum = 973;BA.debugLine="markerGV11dr.Initialize(markerGV_11)";
_markergv11dr.Initialize((android.graphics.Bitmap)(_markergv_11.getObject()));
 //BA.debugLineNum = 974;BA.debugLine="markerGV12dr.Initialize(markerGV_12)";
_markergv12dr.Initialize((android.graphics.Bitmap)(_markergv_12.getObject()));
 //BA.debugLineNum = 975;BA.debugLine="markerGV13dr.Initialize(markerGV_13)";
_markergv13dr.Initialize((android.graphics.Bitmap)(_markergv_13.getObject()));
 //BA.debugLineNum = 976;BA.debugLine="markerGV14dr.Initialize(markerGV_14)";
_markergv14dr.Initialize((android.graphics.Bitmap)(_markergv_14.getObject()));
 //BA.debugLineNum = 977;BA.debugLine="markerGV15dr.Initialize(markerGV_15)";
_markergv15dr.Initialize((android.graphics.Bitmap)(_markergv_15.getObject()));
 //BA.debugLineNum = 978;BA.debugLine="markerGV16dr.Initialize(markerGV_16)";
_markergv16dr.Initialize((android.graphics.Bitmap)(_markergv_16.getObject()));
 //BA.debugLineNum = 980;BA.debugLine="Dim marker1dr As BitmapDrawable";
_marker1dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 981;BA.debugLine="Dim marker2dr As BitmapDrawable";
_marker2dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 982;BA.debugLine="Dim marker3dr As BitmapDrawable";
_marker3dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 983;BA.debugLine="Dim marker4dr As BitmapDrawable";
_marker4dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 984;BA.debugLine="Dim marker5dr As BitmapDrawable";
_marker5dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 985;BA.debugLine="Dim marker6dr As BitmapDrawable";
_marker6dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 986;BA.debugLine="Dim marker7dr As BitmapDrawable";
_marker7dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 987;BA.debugLine="Dim marker8dr As BitmapDrawable";
_marker8dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 988;BA.debugLine="Dim marker9dr As BitmapDrawable";
_marker9dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 989;BA.debugLine="Dim marker10dr As BitmapDrawable";
_marker10dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 990;BA.debugLine="Dim marker11dr As BitmapDrawable";
_marker11dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 991;BA.debugLine="Dim marker12dr As BitmapDrawable";
_marker12dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 992;BA.debugLine="Dim marker13dr As BitmapDrawable";
_marker13dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 993;BA.debugLine="Dim marker14dr As BitmapDrawable";
_marker14dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 994;BA.debugLine="Dim marker15dr As BitmapDrawable";
_marker15dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 995;BA.debugLine="Dim marker16dr As BitmapDrawable";
_marker16dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 997;BA.debugLine="marker1dr.Initialize(marker1)";
_marker1dr.Initialize((android.graphics.Bitmap)(_marker1.getObject()));
 //BA.debugLineNum = 998;BA.debugLine="marker2dr.Initialize(marker2)";
_marker2dr.Initialize((android.graphics.Bitmap)(_marker2.getObject()));
 //BA.debugLineNum = 999;BA.debugLine="marker3dr.Initialize(marker3)";
_marker3dr.Initialize((android.graphics.Bitmap)(_marker3.getObject()));
 //BA.debugLineNum = 1000;BA.debugLine="marker4dr.Initialize(marker4)";
_marker4dr.Initialize((android.graphics.Bitmap)(_marker4.getObject()));
 //BA.debugLineNum = 1001;BA.debugLine="marker5dr.Initialize(marker5)";
_marker5dr.Initialize((android.graphics.Bitmap)(_marker5.getObject()));
 //BA.debugLineNum = 1002;BA.debugLine="marker6dr.Initialize(marker6)";
_marker6dr.Initialize((android.graphics.Bitmap)(_marker6.getObject()));
 //BA.debugLineNum = 1003;BA.debugLine="marker7dr.Initialize(marker7)";
_marker7dr.Initialize((android.graphics.Bitmap)(_marker7.getObject()));
 //BA.debugLineNum = 1004;BA.debugLine="marker8dr.Initialize(marker8)";
_marker8dr.Initialize((android.graphics.Bitmap)(_marker8.getObject()));
 //BA.debugLineNum = 1005;BA.debugLine="marker9dr.Initialize(marker9)";
_marker9dr.Initialize((android.graphics.Bitmap)(_marker9.getObject()));
 //BA.debugLineNum = 1006;BA.debugLine="marker10dr.Initialize(marker10)";
_marker10dr.Initialize((android.graphics.Bitmap)(_marker10.getObject()));
 //BA.debugLineNum = 1007;BA.debugLine="marker11dr.Initialize(marker11)";
_marker11dr.Initialize((android.graphics.Bitmap)(_marker11.getObject()));
 //BA.debugLineNum = 1008;BA.debugLine="marker12dr.Initialize(marker12)";
_marker12dr.Initialize((android.graphics.Bitmap)(_marker12.getObject()));
 //BA.debugLineNum = 1009;BA.debugLine="marker13dr.Initialize(marker13)";
_marker13dr.Initialize((android.graphics.Bitmap)(_marker13.getObject()));
 //BA.debugLineNum = 1010;BA.debugLine="marker14dr.Initialize(marker14)";
_marker14dr.Initialize((android.graphics.Bitmap)(_marker14.getObject()));
 //BA.debugLineNum = 1011;BA.debugLine="marker15dr.Initialize(marker15)";
_marker15dr.Initialize((android.graphics.Bitmap)(_marker15.getObject()));
 //BA.debugLineNum = 1012;BA.debugLine="marker16dr.Initialize(marker16)";
_marker16dr.Initialize((android.graphics.Bitmap)(_marker16.getObject()));
 //BA.debugLineNum = 1019;BA.debugLine="For i = 0 To numresults - 1";
{
final int step155 = 1;
final int limit155 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit155 ;_i = _i + step155 ) {
 //BA.debugLineNum = 1023;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1024;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 1025;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 1026;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 1027;BA.debugLine="Dim valorvinchuca As String = newpunto.Get(\"va";
_valorvinchuca = BA.ObjectToString(_newpunto.Get((Object)("valorVinchuca")));
 //BA.debugLineNum = 1028;BA.debugLine="Dim nombreUsuario As String = newpunto.Get(\"us";
_nombreusuario = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 1029;BA.debugLine="Dim marker As OSMDroid_Marker";
_marker = new uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker();
 //BA.debugLineNum = 1030;BA.debugLine="marker.Initialize(valorvinchuca,nombreUsuario,";
_marker.Initialize(_valorvinchuca,_nombreusuario,_sitiolat,_sitiolong);
 //BA.debugLineNum = 1032;BA.debugLine="If nombreUsuario = \"geovin\" Then";
if ((_nombreusuario).equals("geovin")) { 
 //BA.debugLineNum = 1033;BA.debugLine="If valorvinchuca = \"infestans\" Then";
if ((_valorvinchuca).equals("infestans")) { 
 //BA.debugLineNum = 1034;BA.debugLine="marker.SetMarkerIcon(markerGV1dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv1dr.getObject()));
 }else if((_valorvinchuca).equals("guasayana")) { 
 //BA.debugLineNum = 1037;BA.debugLine="marker.SetMarkerIcon(markerGV2dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv2dr.getObject()));
 }else if((_valorvinchuca).equals("sordida")) { 
 //BA.debugLineNum = 1039;BA.debugLine="marker.SetMarkerIcon(markerGV5dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv5dr.getObject()));
 }else if((_valorvinchuca).equals("garciabesi")) { 
 //BA.debugLineNum = 1041;BA.debugLine="marker.SetMarkerIcon(markerGV3dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv3dr.getObject()));
 }else if((_valorvinchuca).equals("delpontei")) { 
 //BA.debugLineNum = 1043;BA.debugLine="marker.SetMarkerIcon(markerGV6dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv6dr.getObject()));
 }else if((_valorvinchuca).equals("platensis")) { 
 //BA.debugLineNum = 1045;BA.debugLine="marker.SetMarkerIcon(markerGV7dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv7dr.getObject()));
 }else if((_valorvinchuca).equals("rubrovaria")) { 
 //BA.debugLineNum = 1047;BA.debugLine="marker.SetMarkerIcon(markerGV8dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv8dr.getObject()));
 }else if((_valorvinchuca).equals("eratyrusiformis")) { 
 //BA.debugLineNum = 1049;BA.debugLine="marker.SetMarkerIcon(markerGV9dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv9dr.getObject()));
 }else if((_valorvinchuca).equals("breyeri")) { 
 //BA.debugLineNum = 1051;BA.debugLine="marker.SetMarkerIcon(markerGV10dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv10dr.getObject()));
 }else if((_valorvinchuca).equals("rufotuberculatus")) { 
 //BA.debugLineNum = 1053;BA.debugLine="marker.SetMarkerIcon(markerGV11dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv11dr.getObject()));
 }else if((_valorvinchuca).equals("limai")) { 
 //BA.debugLineNum = 1055;BA.debugLine="marker.SetMarkerIcon(markerGV12dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv12dr.getObject()));
 }else if((_valorvinchuca).equals("coreodes") || (_valorvinchuca).equals("Psammolestes_coreodes")) { 
 //BA.debugLineNum = 1057;BA.debugLine="marker.SetMarkerIcon(markerGV13dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv13dr.getObject()));
 }else if((_valorvinchuca).equals("geniculatus") || (_valorvinchuca).equals("Panstrongylus_geniculatus")) { 
 //BA.debugLineNum = 1059;BA.debugLine="marker.SetMarkerIcon(markerGV14dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv14dr.getObject()));
 }else if((_valorvinchuca).equals("guentheri") || (_valorvinchuca).equals("Panstrongylus_guentheri")) { 
 //BA.debugLineNum = 1061;BA.debugLine="marker.SetMarkerIcon(markerGV15dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv15dr.getObject()));
 }else if((_valorvinchuca).equals("megistus") || (_valorvinchuca).equals("Panstrongylus_megistus")) { 
 //BA.debugLineNum = 1063;BA.debugLine="marker.SetMarkerIcon(markerGV16dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv16dr.getObject()));
 }else if((_valorvinchuca).equals("patagonica")) { 
 //BA.debugLineNum = 1065;BA.debugLine="marker.SetMarkerIcon(markerGV4dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv4dr.getObject()));
 }else {
 //BA.debugLineNum = 1067;BA.debugLine="marker.SetMarkerIcon(markerGV4dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_markergv4dr.getObject()));
 };
 }else {
 //BA.debugLineNum = 1071;BA.debugLine="If valorvinchuca = \"infestans\" Then";
if ((_valorvinchuca).equals("infestans")) { 
 //BA.debugLineNum = 1072;BA.debugLine="marker.SetMarkerIcon(marker1dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker1dr.getObject()));
 }else if((_valorvinchuca).equals("guasayana") || (_valorvinchuca).equals("sordida") || (_valorvinchuca).equals("garciabesi") || (_valorvinchuca).equals("patagonica")) { 
 //BA.debugLineNum = 1074;BA.debugLine="marker.SetMarkerIcon(marker2dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker2dr.getObject()));
 }else {
 //BA.debugLineNum = 1076;BA.debugLine="marker.SetMarkerIcon(marker6dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker6dr.getObject()));
 };
 };
 //BA.debugLineNum = 1079;BA.debugLine="markersList.Add(marker)";
mostCurrent._markerslist.Add((Object)(_marker.getObject()));
 }
};
 //BA.debugLineNum = 1083;BA.debugLine="markersOverlay.AddItems(markersList)";
mostCurrent._markersoverlay.AddItems(mostCurrent._markerslist);
 //BA.debugLineNum = 1084;BA.debugLine="MapView1.GetController.SetZoom(7)";
mostCurrent._mapview1.GetController().SetZoom((int) (7));
 };
 }else {
 //BA.debugLineNum = 1087;BA.debugLine="Log(\"GetMapa not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22556152","GetMapa not ok",0);
 //BA.debugLineNum = 1088;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1089;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1091;BA.debugLine="Msgbox(\"There seems to be a problem with the se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There seems to be a problem with the server, we will fix it soon!"),BA.ObjectToCharSequence("My bad!"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 1096;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1098;BA.debugLine="If FusedLocationProvider1.IsConnected = False The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1099;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 };
 //BA.debugLineNum = 1101;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 20;BA.debugLine="Dim currentscreen As String";
mostCurrent._currentscreen = "";
 //BA.debugLineNum = 23;BA.debugLine="Dim resetting_msg As Boolean";
_resetting_msg = false;
 //BA.debugLineNum = 26;BA.debugLine="Private tabStripMain As TabStrip";
mostCurrent._tabstripmain = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 27;BA.debugLine="Private btnMenu As Button";
mostCurrent._btnmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lblEncontre As Label";
mostCurrent._lblencontre = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private btnFondo As Button";
mostCurrent._btnfondo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lblEncontreVinchuca As Label";
mostCurrent._lblencontrevinchuca = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnInformeVinchuca As Button";
mostCurrent._btninformevinchuca = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btnAprenderMas As Button";
mostCurrent._btnaprendermas = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private btnElChagas As Button";
mostCurrent._btnelchagas = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnRecomendaciones As Button";
mostCurrent._btnrecomendaciones = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private btnMasInfoOnline As Button";
mostCurrent._btnmasinfoonline = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private TimerAprender As Timer";
mostCurrent._timeraprender = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 43;BA.debugLine="Dim panelExp As Panel";
mostCurrent._panelexp = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim MapView1 As OSMDroid_MapView";
mostCurrent._mapview1 = new uk.co.martinpearman.b4a.osmdroid.views.MapView();
 //BA.debugLineNum = 47;BA.debugLine="Private btnZoomAll As Button";
mostCurrent._btnzoomall = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private btnReferenciaMapa As Button";
mostCurrent._btnreferenciamapa = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim SimpleLocationOverlay1 As OSMDroid_SimpleLoca";
mostCurrent._simplelocationoverlay1 = new uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay();
 //BA.debugLineNum = 50;BA.debugLine="Dim MarkerExport As List";
mostCurrent._markerexport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 52;BA.debugLine="Dim MarkerInfo As Label";
mostCurrent._markerinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Dim detectandoLabel As Label";
mostCurrent._detectandolabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private pnlReferencias As Panel";
mostCurrent._pnlreferencias = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim isPanelExplorar As Boolean";
_ispanelexplorar = false;
 //BA.debugLineNum = 62;BA.debugLine="Dim markersList As List";
mostCurrent._markerslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 63;BA.debugLine="Dim markersOverlay As OSMDroid_MarkerOverlay";
mostCurrent._markersoverlay = new uk.co.martinpearman.b4a.osmdroid.views.overlay.MarkerOverlay();
 //BA.debugLineNum = 64;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private pnlConfig As Panel";
mostCurrent._pnlconfig = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private scrConfig As ScrollView";
mostCurrent._scrconfig = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private btnOkConfig As Button";
mostCurrent._btnokconfig = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private btnConfigExplorar As Button";
mostCurrent._btnconfigexplorar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Dim listaConfig As List";
mostCurrent._listaconfig = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 73;BA.debugLine="Dim listaChequeados As List";
mostCurrent._listachequeados = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 74;BA.debugLine="Private chkDatosPropios As CheckBox";
mostCurrent._chkdatospropios = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private chkDatosUsuarios As CheckBox";
mostCurrent._chkdatosusuarios = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private chkDatosGeoVin As CheckBox";
mostCurrent._chkdatosgeovin = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private btnCheckAll As Button";
mostCurrent._btncheckall = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private lblEspecies As Label";
mostCurrent._lblespecies = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 85;BA.debugLine="Dim Drawer As B4XDrawer";
mostCurrent._drawer = new cepave.geovin.b4xdrawer();
 //BA.debugLineNum = 86;BA.debugLine="Private btnComoFotosReportar As Label";
mostCurrent._btncomofotosreportar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Private btnCerrarSesion As Button";
mostCurrent._btncerrarsesion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Private btnEditUser As Label";
mostCurrent._btnedituser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 89;BA.debugLine="Private btnDatosAnteriores As Label";
mostCurrent._btndatosanteriores = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private lblUserName As Label";
mostCurrent._lblusername = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private lblRegistrate As Label";
mostCurrent._lblregistrate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private btnVerPerfil As Label";
mostCurrent._btnverperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private btnAbout As Label";
mostCurrent._btnabout = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private btnPoliticaDatos As Label";
mostCurrent._btnpoliticadatos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private lblStatusSync As Label";
mostCurrent._lblstatussync = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _locationsettingsresult1_resolutiondialogdismissed(boolean _locationsettingsupdated) throws Exception{
 //BA.debugLineNum = 762;BA.debugLine="Sub LocationSettingsResult1_ResolutionDialogDismis";
 //BA.debugLineNum = 763;BA.debugLine="Log(\"LocationSettingsResult1_ResolutionDialogDism";
anywheresoftware.b4a.keywords.Common.LogImpl("22359297","LocationSettingsResult1_ResolutionDialogDismissed",0);
 //BA.debugLineNum = 764;BA.debugLine="If Not(LocationSettingsUpdated) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_locationsettingsupdated)) { 
 //BA.debugLineNum = 766;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 767;BA.debugLine="Msgbox(\"No tienes habilitada la Localización, b";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes habilitada la Localización, busca en el mapa tu posición"),BA.ObjectToCharSequence("Búsqueda manual"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 769;BA.debugLine="Msgbox(\"You don't have the location services en";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You don't have the location services enabled, use the map to locate your position"),BA.ObjectToCharSequence("Manual search"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 775;BA.debugLine="If FusedLocationProvider1.IsConnected = True The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 776;BA.debugLine="FusedLocationProvider1.DisConnect";
_fusedlocationprovider1.Disconnect();
 };
 //BA.debugLineNum = 779;BA.debugLine="If fondoblanco.IsInitialized Then";
if (mostCurrent._fondoblanco.IsInitialized()) { 
 //BA.debugLineNum = 780;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
 //BA.debugLineNum = 781;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 };
 };
 //BA.debugLineNum = 784;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private FusedLocationProvider1 As FusedLocationPr";
_fusedlocationprovider1 = new uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Private LastLocation As Location";
_lastlocation = new anywheresoftware.b4a.gps.LocationWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Dim fullidcurrentproject As String";
_fullidcurrentproject = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _resetmessages() throws Exception{
cepave.geovin.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 1496;BA.debugLine="Sub ResetMessages";
 //BA.debugLineNum = 1499;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 1500;BA.debugLine="dd.url = Main.serverPath & \"/connect2/resetmessag";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect2/resetmessages_new.php?deviceID="+mostCurrent._main._deviceid /*String*/ ;
 //BA.debugLineNum = 1501;BA.debugLine="dd.EventName = \"ResetMessages\"";
_dd.EventName /*String*/  = "ResetMessages";
 //BA.debugLineNum = 1502;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmprincipal.getObject();
 //BA.debugLineNum = 1503;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 1504;BA.debugLine="resetting_msg = True";
_resetting_msg = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1505;BA.debugLine="End Sub";
return "";
}
public static String  _resetmessages_complete(cepave.geovin.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
 //BA.debugLineNum = 1506;BA.debugLine="Sub ResetMessages_Complete(Job As HttpJob)";
 //BA.debugLineNum = 1507;BA.debugLine="Log(\"Reset messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("23538945","Reset messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 1508;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1509;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 1510;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1511;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 1512;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1513;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 1514;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1515;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 1516;BA.debugLine="ToastMessageShow(\"Error\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("Error reseteando los mensajes, ")) { 
 //BA.debugLineNum = 1518;BA.debugLine="ToastMessageShow(\"Login failed\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Login failed"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("ResetMessages")) { 
 };
 //BA.debugLineNum = 1522;BA.debugLine="resetting_msg = False";
_resetting_msg = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 1524;BA.debugLine="Log(\"reset messages not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("23538962","reset messages not ok",0);
 //BA.debugLineNum = 1525;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1526;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1528;BA.debugLine="Msgbox(\"There seems to be a problem with the se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There seems to be a problem with the server, we will fix it soon!"),BA.ObjectToCharSequence("My bad!"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 1530;BA.debugLine="resetting_msg = False";
_resetting_msg = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1533;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1534;BA.debugLine="End Sub";
return "";
}
public static String  _startbienvienido(boolean _online,boolean _firsttime) throws Exception{
String _msjpriv = "";
String[] _msjarray = null;
int _i = 0;
 //BA.debugLineNum = 355;BA.debugLine="Sub startBienvienido(online As Boolean, firsttime";
 //BA.debugLineNum = 358;BA.debugLine="If online = True Then";
if (_online==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 362;BA.debugLine="If Main.msjprivadoleido = True Then";
if (mostCurrent._main._msjprivadoleido /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 363;BA.debugLine="ResetMessages";
_resetmessages();
 };
 //BA.debugLineNum = 367;BA.debugLine="If Main.msjprivadouser <> \"None\" And firsttime";
if ((mostCurrent._main._msjprivadouser /*String*/ ).equals("None") == false && _firsttime==anywheresoftware.b4a.keywords.Common.True && mostCurrent._main._msjprivadoleido /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 368;BA.debugLine="Dim msjpriv As String";
_msjpriv = "";
 //BA.debugLineNum = 370;BA.debugLine="Dim msjarray() As String = Regex.Split(\"#\", Ma";
_msjarray = anywheresoftware.b4a.keywords.Common.Regex.Split("#",mostCurrent._main._msjprivadouser /*String*/ );
 //BA.debugLineNum = 371;BA.debugLine="For i = 0 To msjarray.Length - 1";
{
final int step8 = 1;
final int limit8 = (int) (_msjarray.length-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 373;BA.debugLine="If msjarray(i) <> \"\" Then";
if ((_msjarray[_i]).equals("") == false) { 
 //BA.debugLineNum = 375;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 376;BA.debugLine="msjpriv = utilidades.Mensaje(\"Notificación\",";
_msjpriv = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Notificación","MsgMensaje.png","Notificación privada",_msjarray[_i],"Ok","","",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 378;BA.debugLine="msjpriv = utilidades.Mensaje(\"Notification\",";
_msjpriv = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Notification","MsgMensaje.png","Private notification",_msjarray[_i],"Ok","","",anywheresoftware.b4a.keywords.Common.True);
 };
 };
 }
};
 //BA.debugLineNum = 386;BA.debugLine="If msjpriv = DialogResponse.POSITIVE Then";
if ((_msjpriv).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 };
 };
 }else {
 //BA.debugLineNum = 401;BA.debugLine="Main.modooffline = True";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 402;BA.debugLine="resetting_msg = False";
_resetting_msg = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return "";
}
public static String  _tabstripmain_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 414;BA.debugLine="Sub tabStripMain_PageSelected (Position As Int)";
 //BA.debugLineNum = 415;BA.debugLine="If Position = 1 Then";
if (_position==1) { 
 //BA.debugLineNum = 417;BA.debugLine="If isPanelExplorar = True Then";
if (_ispanelexplorar==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 418;BA.debugLine="CallSubDelayed2(DownloadService, \"CancelDownloa";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"CancelDownload",(Object)(mostCurrent._main._serverpath /*String*/ +"/connect2/getallmapa.php"));
 //BA.debugLineNum = 419;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
 //BA.debugLineNum = 420;BA.debugLine="isPanelExplorar = False";
_ispanelexplorar = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 423;BA.debugLine="If panelExp.IsInitialized Then";
if (mostCurrent._panelexp.IsInitialized()) { 
 //BA.debugLineNum = 424;BA.debugLine="panelExp.RemoveView";
mostCurrent._panelexp.RemoveView();
 };
 //BA.debugLineNum = 426;BA.debugLine="btnMenu.Visible = True";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==2) { 
 //BA.debugLineNum = 429;BA.debugLine="isPanelExplorar = True";
_ispanelexplorar = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 430;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_ACCESS_FINE_LOC";
_rp.CheckAndRequest(processBA,_rp.PERMISSION_ACCESS_FINE_LOCATION);
 //BA.debugLineNum = 431;BA.debugLine="btnMenu.Visible = True";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==0) { 
 //BA.debugLineNum = 434;BA.debugLine="If isPanelExplorar = True Then";
if (_ispanelexplorar==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 435;BA.debugLine="CallSubDelayed2(DownloadService, \"CancelDownloa";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"CancelDownload",(Object)(mostCurrent._main._serverpath /*String*/ +"/connect2/getallmapa.php"));
 //BA.debugLineNum = 436;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
 //BA.debugLineNum = 437;BA.debugLine="isPanelExplorar = False";
_ispanelexplorar = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 439;BA.debugLine="btnInformeVinchuca.Left = Activity.Width / 2 - b";
mostCurrent._btninformevinchuca.setLeft((int) (mostCurrent._activity.getWidth()/(double)2-mostCurrent._btninformevinchuca.getWidth()/(double)2));
 //BA.debugLineNum = 440;BA.debugLine="btnFondo.Left = btnInformeVinchuca.Left";
mostCurrent._btnfondo.setLeft(mostCurrent._btninformevinchuca.getLeft());
 //BA.debugLineNum = 441;BA.debugLine="btnFondo.Top = btnInformeVinchuca.Top";
mostCurrent._btnfondo.setTop(mostCurrent._btninformevinchuca.getTop());
 //BA.debugLineNum = 442;BA.debugLine="lblEncontre.Left = Activity.Width / 2 - lblEncon";
mostCurrent._lblencontre.setLeft((int) (mostCurrent._activity.getWidth()/(double)2-mostCurrent._lblencontre.getWidth()/(double)2));
 //BA.debugLineNum = 443;BA.debugLine="lblEncontreVinchuca.Left = Activity.Width / 2 -";
mostCurrent._lblencontrevinchuca.setLeft((int) (mostCurrent._activity.getWidth()/(double)2-mostCurrent._lblencontrevinchuca.getWidth()/(double)2));
 //BA.debugLineNum = 444;BA.debugLine="btnMenu.Visible = True";
mostCurrent._btnmenu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 445;BA.debugLine="If panelExp.IsInitialized Then";
if (mostCurrent._panelexp.IsInitialized()) { 
 //BA.debugLineNum = 446;BA.debugLine="panelExp.RemoveView";
mostCurrent._panelexp.RemoveView();
 };
 };
 //BA.debugLineNum = 450;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 206;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 207;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 208;BA.debugLine="btnAprenderMas.Text = \"Sobre las vinchucas\"";
mostCurrent._btnaprendermas.setText(BA.ObjectToCharSequence("Sobre las vinchucas"));
 //BA.debugLineNum = 209;BA.debugLine="btnElChagas.Text = \"Sobre Chagas\"";
mostCurrent._btnelchagas.setText(BA.ObjectToCharSequence("Sobre Chagas"));
 //BA.debugLineNum = 210;BA.debugLine="btnRecomendaciones.Text = \"Recomendaciones y con";
mostCurrent._btnrecomendaciones.setText(BA.ObjectToCharSequence("Recomendaciones y contactos"));
 //BA.debugLineNum = 211;BA.debugLine="btnMasInfoOnline.Text = \"Más información online\"";
mostCurrent._btnmasinfoonline.setText(BA.ObjectToCharSequence("Más información online"));
 //BA.debugLineNum = 212;BA.debugLine="lblEncontreVinchuca.Text = \"¡Encontré una \" & CR";
mostCurrent._lblencontrevinchuca.setText(BA.ObjectToCharSequence("¡Encontré una "+anywheresoftware.b4a.keywords.Common.CRLF+"vinchuca!"));
 //BA.debugLineNum = 213;BA.debugLine="lblEncontre.Text = \"Pulse aquí\"";
mostCurrent._lblencontre.setText(BA.ObjectToCharSequence("Pulse aquí"));
 //BA.debugLineNum = 214;BA.debugLine="btnVerPerfil.Text = \"Mi perfil\"";
mostCurrent._btnverperfil.setText(BA.ObjectToCharSequence("Mi perfil"));
 //BA.debugLineNum = 215;BA.debugLine="btnDatosAnteriores.Text = \"Mis datos anteriores\"";
mostCurrent._btndatosanteriores.setText(BA.ObjectToCharSequence("Mis datos anteriores"));
 //BA.debugLineNum = 216;BA.debugLine="btnComoFotosReportar.Text = \"Cómo sacar mejores";
mostCurrent._btncomofotosreportar.setText(BA.ObjectToCharSequence("Cómo sacar mejores fotos"));
 //BA.debugLineNum = 217;BA.debugLine="btnAbout.Text = \"Sobre el proyecto GeoVin\"";
mostCurrent._btnabout.setText(BA.ObjectToCharSequence("Sobre el proyecto GeoVin"));
 //BA.debugLineNum = 218;BA.debugLine="btnPoliticaDatos.Text = \"Política de datos\"";
mostCurrent._btnpoliticadatos.setText(BA.ObjectToCharSequence("Política de datos"));
 //BA.debugLineNum = 219;BA.debugLine="btnCerrarSesion.Text = \" Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence(" Cerrar sesión"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 223;BA.debugLine="btnAprenderMas.Text = \"About kissing bugs\"";
mostCurrent._btnaprendermas.setText(BA.ObjectToCharSequence("About kissing bugs"));
 //BA.debugLineNum = 224;BA.debugLine="btnElChagas.Text = \"About Chagas\"";
mostCurrent._btnelchagas.setText(BA.ObjectToCharSequence("About Chagas"));
 //BA.debugLineNum = 225;BA.debugLine="btnRecomendaciones.Text = \"Recommendations and c";
mostCurrent._btnrecomendaciones.setText(BA.ObjectToCharSequence("Recommendations and contacts"));
 //BA.debugLineNum = 226;BA.debugLine="btnMasInfoOnline.Text = \"More info online\"";
mostCurrent._btnmasinfoonline.setText(BA.ObjectToCharSequence("More info online"));
 //BA.debugLineNum = 227;BA.debugLine="lblEncontreVinchuca.Text = \"I found a \" & CRLF &";
mostCurrent._lblencontrevinchuca.setText(BA.ObjectToCharSequence("I found a "+anywheresoftware.b4a.keywords.Common.CRLF+"kissing bug!"));
 //BA.debugLineNum = 228;BA.debugLine="lblEncontre.Text = \"Press here\"";
mostCurrent._lblencontre.setText(BA.ObjectToCharSequence("Press here"));
 //BA.debugLineNum = 229;BA.debugLine="btnVerPerfil.Text = \"My profile\"";
mostCurrent._btnverperfil.setText(BA.ObjectToCharSequence("My profile"));
 //BA.debugLineNum = 230;BA.debugLine="btnDatosAnteriores.Text = \"My previous reports\"";
mostCurrent._btndatosanteriores.setText(BA.ObjectToCharSequence("My previous reports"));
 //BA.debugLineNum = 231;BA.debugLine="btnComoFotosReportar.Text = \"How to improve your";
mostCurrent._btncomofotosreportar.setText(BA.ObjectToCharSequence("How to improve your photos"));
 //BA.debugLineNum = 232;BA.debugLine="btnAbout.Text = \"About GeoVin\"";
mostCurrent._btnabout.setText(BA.ObjectToCharSequence("About GeoVin"));
 //BA.debugLineNum = 233;BA.debugLine="btnPoliticaDatos.Text = \"Data usage policy\"";
mostCurrent._btnpoliticadatos.setText(BA.ObjectToCharSequence("Data usage policy"));
 //BA.debugLineNum = 234;BA.debugLine="btnCerrarSesion.Text = \" Close session\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence(" Close session"));
 };
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _updateui() throws Exception{
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
 //BA.debugLineNum = 785;BA.debugLine="Sub UpdateUI";
 //BA.debugLineNum = 787;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 788;BA.debugLine="ToastMessageShow(\"Posición encontrada!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Posición encontrada!"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 790;BA.debugLine="ToastMessageShow(\"Location found!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Location found!"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 793;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
 //BA.debugLineNum = 794;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
 //BA.debugLineNum = 795;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
 //BA.debugLineNum = 796;BA.debugLine="geopoint1.Initialize(lblLat.Text,lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
 //BA.debugLineNum = 799;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 800;BA.debugLine="SimpleLocationOverlay1.Initialize";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 };
 //BA.debugLineNum = 802;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 //BA.debugLineNum = 805;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 //BA.debugLineNum = 806;BA.debugLine="MapView1.GetController.SetZoom(14)";
mostCurrent._mapview1.GetController().SetZoom((int) (14));
 //BA.debugLineNum = 807;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
 //BA.debugLineNum = 809;BA.debugLine="If fondoblanco.IsInitialized Then";
if (mostCurrent._fondoblanco.IsInitialized()) { 
 //BA.debugLineNum = 810;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
 //BA.debugLineNum = 811;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 }else {
 };
 //BA.debugLineNum = 814;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 815;BA.debugLine="End Sub";
return "";
}
}
