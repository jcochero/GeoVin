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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.main");
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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



public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        anywheresoftware.b4a.samples.httputils2.httputils2service._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (frmaprender.mostCurrent != null);
vis = vis | (frmprincipal.mostCurrent != null);
vis = vis | (frmlocalizacion.mostCurrent != null);
vis = vis | (frmdatosanteriores.mostCurrent != null);
vis = vis | (frmperfil.mostCurrent != null);
vis = vis | (frmidentificacionnew.mostCurrent != null);
vis = vis | (frmcamara.mostCurrent != null);
vis = vis | (frmabout.mostCurrent != null);
vis = vis | (register.mostCurrent != null);
vis = vis | (frmreportevinchuca.mostCurrent != null);
vis = vis | (envioarchivos2.mostCurrent != null);
vis = vis | (frmespecies.mostCurrent != null);
vis = vis | (frmcomofotos.mostCurrent != null);
vis = vis | (frmidentificacion.mostCurrent != null);
return vis;}

private static BA killProgramHelper(BA ba) {
    if (ba == null)
        return null;
    anywheresoftware.b4a.BA.SharedProcessBA sharedProcessBA = ba.sharedProcessBA;
    if (sharedProcessBA == null || sharedProcessBA.activityBA == null)
        return null;
    return sharedProcessBA.activityBA.get();
}
public static void killProgram() {
     {
            Activity __a = null;
            if (main.previousOne != null) {
				__a = main.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(main.mostCurrent == null ? null : main.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmaprender.previousOne != null) {
				__a = frmaprender.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmaprender.mostCurrent == null ? null : frmaprender.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmprincipal.previousOne != null) {
				__a = frmprincipal.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmprincipal.mostCurrent == null ? null : frmprincipal.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmlocalizacion.previousOne != null) {
				__a = frmlocalizacion.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmlocalizacion.mostCurrent == null ? null : frmlocalizacion.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmdatosanteriores.previousOne != null) {
				__a = frmdatosanteriores.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmdatosanteriores.mostCurrent == null ? null : frmdatosanteriores.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmperfil.previousOne != null) {
				__a = frmperfil.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmperfil.mostCurrent == null ? null : frmperfil.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmidentificacionnew.previousOne != null) {
				__a = frmidentificacionnew.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmidentificacionnew.mostCurrent == null ? null : frmidentificacionnew.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

BA.applicationContext.stopService(new android.content.Intent(BA.applicationContext, firebasemessaging.class));
BA.applicationContext.stopService(new android.content.Intent(BA.applicationContext, starter.class));
 {
            Activity __a = null;
            if (frmcamara.previousOne != null) {
				__a = frmcamara.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmcamara.mostCurrent == null ? null : frmcamara.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmabout.previousOne != null) {
				__a = frmabout.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmabout.mostCurrent == null ? null : frmabout.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (register.previousOne != null) {
				__a = register.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(register.mostCurrent == null ? null : register.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmreportevinchuca.previousOne != null) {
				__a = frmreportevinchuca.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmreportevinchuca.mostCurrent == null ? null : frmreportevinchuca.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (envioarchivos2.previousOne != null) {
				__a = envioarchivos2.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(envioarchivos2.mostCurrent == null ? null : envioarchivos2.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmespecies.previousOne != null) {
				__a = frmespecies.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmespecies.mostCurrent == null ? null : frmespecies.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmcomofotos.previousOne != null) {
				__a = frmcomofotos.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmcomofotos.mostCurrent == null ? null : frmcomofotos.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (frmidentificacion.previousOne != null) {
				__a = frmidentificacion.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(frmidentificacion.mostCurrent == null ? null : frmidentificacion.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

}
public anywheresoftware.b4a.keywords.Common __c = null;
public static String _valorvinchuca = "";
public static String _dateandtime = "";
public static int _idproyecto = 0;
public static String _longitud = "";
public static String _latitud = "";
public static String _fotopath0 = "";
public static String _fotopath1 = "";
public static String _fotopath2 = "";
public static String _fotopath3 = "";
public static String _evaluacionpath = "";
public static String _struserid = "";
public static String _strusername = "";
public static String _struserlocation = "";
public static String _struseremail = "";
public static String _struserorg = "";
public static String _strusertipousuario = "";
public static String _username = "";
public static String _pass = "";
public static boolean _modooffline = false;
public static boolean _forceoffline = false;
public static anywheresoftware.b4a.phone.Phone _k = null;
public static String _savedir = "";
public static String _proyectoenviar = "";
public static String _msjprivadouser = "";
public static boolean _msjprivadoleido = false;
public static String _firstuse = "";
public static String _currentproject = "";
public static String _datecurrentproject = "";
public static String _par1 = "";
public static String _par2 = "";
public static String _par3 = "";
public static String _par4 = "";
public static String _par5 = "";
public static String _par6 = "";
public static String _par7 = "";
public static String _par8 = "";
public static String _par9 = "";
public static String _par10 = "";
public static String _deviceid = "";
public static String _serverpath = "";
public static String _subfolder = "";
public static anywheresoftware.b4a.objects.collections.List _speciesmap = null;
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static String _versionactual = "";
public static String _serverversion = "";
public static String _servernewstitulo = "";
public static String _servernewstext = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnlogin = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnregister = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtuserid = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmessage = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlrecoverpass = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtrecoverpass = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnforgot = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgbload = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
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
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
anywheresoftware.b4a.phone.Phone _p = null;
RDebugUtils.currentLine=131072;
 //BA.debugLineNum = 131072;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=131073;
 //BA.debugLineNum = 131073;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
RDebugUtils.currentLine=131074;
 //BA.debugLineNum = 131074;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
RDebugUtils.currentLine=131078;
 //BA.debugLineNum = 131078;BA.debugLine="If File.ExternalWritable = True Then";
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=131079;
 //BA.debugLineNum = 131079;BA.debugLine="Starter.savedir = File.DirRootExternal";
mostCurrent._starter._savedir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal();
 }else {
RDebugUtils.currentLine=131081;
 //BA.debugLineNum = 131081;BA.debugLine="Starter.savedir = File.DirInternal";
mostCurrent._starter._savedir = anywheresoftware.b4a.keywords.Common.File.getDirInternal();
 };
RDebugUtils.currentLine=131085;
 //BA.debugLineNum = 131085;BA.debugLine="File.Delete(File.DirDefaultExternal, \"speciesdb.d";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"speciesdb.db");
RDebugUtils.currentLine=131089;
 //BA.debugLineNum = 131089;BA.debugLine="Starter.dbdir = DBUtils.CopyDBFromAssets(\"databas";
mostCurrent._starter._dbdir = mostCurrent._dbutils._copydbfromassets(mostCurrent.activityBA,"database.db");
RDebugUtils.currentLine=131090;
 //BA.debugLineNum = 131090;BA.debugLine="Starter.speciesDBDir = DBUtils.CopyDBFromAssets(\"";
mostCurrent._starter._speciesdbdir = mostCurrent._dbutils._copydbfromassets(mostCurrent.activityBA,"speciesdb.db");
RDebugUtils.currentLine=131094;
 //BA.debugLineNum = 131094;BA.debugLine="If File.Exists(savedir & \"/GeoVin/\", \"\") = False";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_savedir+"/GeoVin/","")==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=131095;
 //BA.debugLineNum = 131095;BA.debugLine="File.MakeDir(savedir, \"GeoVin\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(_savedir,"GeoVin");
 };
RDebugUtils.currentLine=131101;
 //BA.debugLineNum = 131101;BA.debugLine="If File.Exists(savedir & \"/GeoVin/sent/\", \"\") = F";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_savedir+"/GeoVin/sent/","")==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=131102;
 //BA.debugLineNum = 131102;BA.debugLine="File.MakeDir(savedir & \"/GeoVin/\", \"sent\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(_savedir+"/GeoVin/","sent");
 };
RDebugUtils.currentLine=131118;
 //BA.debugLineNum = 131118;BA.debugLine="BuscarConfiguracion";
_buscarconfiguracion();
RDebugUtils.currentLine=131121;
 //BA.debugLineNum = 131121;BA.debugLine="Activity.LoadLayout(\"layload\")";
mostCurrent._activity.LoadLayout("layload",mostCurrent.activityBA);
RDebugUtils.currentLine=131123;
 //BA.debugLineNum = 131123;BA.debugLine="deviceID = utilidades.GetDeviceId";
_deviceid = mostCurrent._utilidades._getdeviceid(mostCurrent.activityBA);
RDebugUtils.currentLine=131125;
 //BA.debugLineNum = 131125;BA.debugLine="timer1.Initialize(\"Timer1\", 2000)";
mostCurrent._timer1.Initialize(processBA,"Timer1",(long) (2000));
RDebugUtils.currentLine=131126;
 //BA.debugLineNum = 131126;BA.debugLine="timer1.Enabled = True";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=131128;
 //BA.debugLineNum = 131128;BA.debugLine="End Sub";
return "";
}
public static String  _buscarconfiguracion() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "buscarconfiguracion"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "buscarconfiguracion", null));}
anywheresoftware.b4a.objects.collections.Map _deviceidmap = null;
String _deviceactual = "";
anywheresoftware.b4a.objects.collections.Map _usermap = null;
RDebugUtils.currentLine=4063232;
 //BA.debugLineNum = 4063232;BA.debugLine="Sub BuscarConfiguracion";
RDebugUtils.currentLine=4063234;
 //BA.debugLineNum = 4063234;BA.debugLine="Starter.sqlDB.Initialize(Starter.dbdir, \"database";
mostCurrent._starter._sqldb.Initialize(mostCurrent._starter._dbdir,"database.db",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=4063235;
 //BA.debugLineNum = 4063235;BA.debugLine="Starter.speciesDB.Initialize(Starter.speciesDBDir";
mostCurrent._starter._speciesdb.Initialize(mostCurrent._starter._speciesdbdir,"speciesdb.db",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=4063253;
 //BA.debugLineNum = 4063253;BA.debugLine="Dim deviceIdMap As Map";
_deviceidmap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4063254;
 //BA.debugLineNum = 4063254;BA.debugLine="deviceIdMap.Initialize";
_deviceidmap.Initialize();
RDebugUtils.currentLine=4063255;
 //BA.debugLineNum = 4063255;BA.debugLine="deviceIdMap = DBUtils.ExecuteMap(Starter.sqlDB, \"";
_deviceidmap = mostCurrent._dbutils._executemap(mostCurrent.activityBA,mostCurrent._starter._sqldb,"SELECT deviceID FROM appconfig WHERE configID = '1'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=4063256;
 //BA.debugLineNum = 4063256;BA.debugLine="If deviceIdMap = Null Or deviceIdMap.IsInitialize";
if (_deviceidmap== null || _deviceidmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=4063257;
 //BA.debugLineNum = 4063257;BA.debugLine="deviceID = \"noID\"";
_deviceid = "noID";
 }else {
RDebugUtils.currentLine=4063259;
 //BA.debugLineNum = 4063259;BA.debugLine="deviceID = deviceIdMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_deviceidmap.Get((Object)("deviceID")));
 };
RDebugUtils.currentLine=4063263;
 //BA.debugLineNum = 4063263;BA.debugLine="Dim deviceactual As String";
_deviceactual = "";
RDebugUtils.currentLine=4063264;
 //BA.debugLineNum = 4063264;BA.debugLine="deviceactual = utilidades.GetDeviceId";
_deviceactual = mostCurrent._utilidades._getdeviceid(mostCurrent.activityBA);
RDebugUtils.currentLine=4063265;
 //BA.debugLineNum = 4063265;BA.debugLine="If deviceactual <> deviceID Then";
if ((_deviceactual).equals(_deviceid) == false) { 
RDebugUtils.currentLine=4063266;
 //BA.debugLineNum = 4063266;BA.debugLine="deviceID = deviceactual";
_deviceid = _deviceactual;
 };
RDebugUtils.currentLine=4063271;
 //BA.debugLineNum = 4063271;BA.debugLine="Dim userMap As Map";
_usermap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4063272;
 //BA.debugLineNum = 4063272;BA.debugLine="userMap.Initialize";
_usermap.Initialize();
RDebugUtils.currentLine=4063273;
 //BA.debugLineNum = 4063273;BA.debugLine="userMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_usermap = mostCurrent._dbutils._executemap(mostCurrent.activityBA,mostCurrent._starter._sqldb,"SELECT * FROM userconfig WHERE lastUser = 'si'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=4063274;
 //BA.debugLineNum = 4063274;BA.debugLine="If userMap = Null Or userMap.IsInitialized = Fals";
if (_usermap== null || _usermap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=4063275;
 //BA.debugLineNum = 4063275;BA.debugLine="username = \"None\"";
_username = "None";
RDebugUtils.currentLine=4063276;
 //BA.debugLineNum = 4063276;BA.debugLine="pass = \"None\"";
_pass = "None";
 }else {
RDebugUtils.currentLine=4063278;
 //BA.debugLineNum = 4063278;BA.debugLine="username = userMap.Get(\"username\")";
_username = BA.ObjectToString(_usermap.Get((Object)("username")));
RDebugUtils.currentLine=4063279;
 //BA.debugLineNum = 4063279;BA.debugLine="pass = userMap.Get(\"pass\")";
_pass = BA.ObjectToString(_usermap.Get((Object)("pass")));
 };
RDebugUtils.currentLine=4063286;
 //BA.debugLineNum = 4063286;BA.debugLine="speciesMap.Initialize";
_speciesmap.Initialize();
RDebugUtils.currentLine=4063287;
 //BA.debugLineNum = 4063287;BA.debugLine="speciesMap = DBUtils.ExecuteMemoryTable(Starter.s";
_speciesmap = mostCurrent._dbutils._executememorytable(mostCurrent.activityBA,mostCurrent._starter._speciesdb,"SELECT * FROM especies",(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
RDebugUtils.currentLine=4063289;
 //BA.debugLineNum = 4063289;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode}));}
RDebugUtils.currentLine=4194304;
 //BA.debugLineNum = 4194304;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=4194305;
 //BA.debugLineNum = 4194305;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=4194306;
 //BA.debugLineNum = 4194306;BA.debugLine="If Msgbox2(\"Salir de la aplicación?\", \"SAL";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Salir de la aplicación?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
RDebugUtils.currentLine=4194307;
 //BA.debugLineNum = 4194307;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=4194308;
 //BA.debugLineNum = 4194308;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
RDebugUtils.currentLine=4194310;
 //BA.debugLineNum = 4194310;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
RDebugUtils.currentLine=4194313;
 //BA.debugLineNum = 4194313;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="main";
RDebugUtils.currentLine=262144;
 //BA.debugLineNum = 262144;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=262146;
 //BA.debugLineNum = 262146;BA.debugLine="End Sub";
return "";
}
public static void  _activity_resume() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {Debug.delegate(mostCurrent.activityBA, "activity_resume", null); return;}
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

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="main";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=196609;
 //BA.debugLineNum = 196609;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_ACCESS_FINE_LOCA";
parent._rp.CheckAndRequest(processBA,parent._rp.PERMISSION_ACCESS_FINE_LOCATION);
RDebugUtils.currentLine=196610;
 //BA.debugLineNum = 196610;BA.debugLine="Wait For Activity_PermissionResult (Permission As";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "main", "activity_resume"), null);
this.state = 19;
return;
case 19:
//C
this.state = 1;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
RDebugUtils.currentLine=196611;
 //BA.debugLineNum = 196611;BA.debugLine="If Result = False Then";
if (true) break;

case 1:
//if
this.state = 18;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 18;
RDebugUtils.currentLine=196612;
 //BA.debugLineNum = 196612;BA.debugLine="Msgbox(\"Debe activar los permisos de localizació";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Debe activar los permisos de localización para proseguir"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 if (true) break;

case 5:
//C
this.state = 6;
RDebugUtils.currentLine=196614;
 //BA.debugLineNum = 196614;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_CAMERA)";
parent._rp.CheckAndRequest(processBA,parent._rp.PERMISSION_CAMERA);
RDebugUtils.currentLine=196615;
 //BA.debugLineNum = 196615;BA.debugLine="Wait For Activity_PermissionResult (Permission A";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "main", "activity_resume"), null);
this.state = 20;
return;
case 20:
//C
this.state = 6;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
RDebugUtils.currentLine=196616;
 //BA.debugLineNum = 196616;BA.debugLine="If Result = False Then";
if (true) break;

case 6:
//if
this.state = 17;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 8;
}else {
this.state = 10;
}if (true) break;

case 8:
//C
this.state = 17;
RDebugUtils.currentLine=196617;
 //BA.debugLineNum = 196617;BA.debugLine="Msgbox(\"Debe activar los permisos de cámara par";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Debe activar los permisos de cámara para proseguir"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 if (true) break;

case 10:
//C
this.state = 11;
RDebugUtils.currentLine=196619;
 //BA.debugLineNum = 196619;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_WRITE_EXTERNAL";
parent._rp.CheckAndRequest(processBA,parent._rp.PERMISSION_WRITE_EXTERNAL_STORAGE);
RDebugUtils.currentLine=196620;
 //BA.debugLineNum = 196620;BA.debugLine="Wait For Activity_PermissionResult (Permission";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "main", "activity_resume"), null);
this.state = 21;
return;
case 21:
//C
this.state = 11;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
RDebugUtils.currentLine=196621;
 //BA.debugLineNum = 196621;BA.debugLine="If Result = False Then";
if (true) break;

case 11:
//if
this.state = 16;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 13;
}else {
this.state = 15;
}if (true) break;

case 13:
//C
this.state = 16;
RDebugUtils.currentLine=196622;
 //BA.debugLineNum = 196622;BA.debugLine="Msgbox(\"Debe activar los permisos de escritura";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Debe activar los permisos de escritura para proseguir"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 if (true) break;

case 15:
//C
this.state = 16;
 if (true) break;

case 16:
//C
this.state = 17;
;
 if (true) break;

case 17:
//C
this.state = 18;
;
 if (true) break;

case 18:
//C
this.state = -1;
;
RDebugUtils.currentLine=196629;
 //BA.debugLineNum = 196629;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btnforgot_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnforgot_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnforgot_click", null));}
RDebugUtils.currentLine=4587520;
 //BA.debugLineNum = 4587520;BA.debugLine="Sub btnForgot_Click";
RDebugUtils.currentLine=4587521;
 //BA.debugLineNum = 4587521;BA.debugLine="pnlRecoverPass.Visible = True";
mostCurrent._pnlrecoverpass.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=4587522;
 //BA.debugLineNum = 4587522;BA.debugLine="pnlRecoverPass.BringToFront";
mostCurrent._pnlrecoverpass.BringToFront();
RDebugUtils.currentLine=4587523;
 //BA.debugLineNum = 4587523;BA.debugLine="End Sub";
return "";
}
public static String  _btnguest_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnguest_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnguest_click", null));}
String _msg = "";
RDebugUtils.currentLine=4849664;
 //BA.debugLineNum = 4849664;BA.debugLine="Sub btnGuest_Click";
RDebugUtils.currentLine=4849665;
 //BA.debugLineNum = 4849665;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=4849666;
 //BA.debugLineNum = 4849666;BA.debugLine="msg = utilidades.Mensaje(\"Invitado\", \"MsgIcon.png";
_msg = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Invitado","MsgIcon.png","Ingresar como invitado","Los revisores no podrán ayudarte a confirmar si has encontrado una vinchuca. Deseas continuar igual?","Si, quiero continuar","","Me registraré",anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=4849667;
 //BA.debugLineNum = 4849667;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=4849668;
 //BA.debugLineNum = 4849668;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
RDebugUtils.currentLine=4849669;
 //BA.debugLineNum = 4849669;BA.debugLine="username = \"guest\"";
_username = "guest";
RDebugUtils.currentLine=4849670;
 //BA.debugLineNum = 4849670;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmprincipal.getObject()));
 }else 
{RDebugUtils.currentLine=4849671;
 //BA.debugLineNum = 4849671;BA.debugLine="Else If msg = DialogResponse.NEGATIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE))) { 
RDebugUtils.currentLine=4849672;
 //BA.debugLineNum = 4849672;BA.debugLine="StartActivity(register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._register.getObject()));
 }}
;
RDebugUtils.currentLine=4849674;
 //BA.debugLineNum = 4849674;BA.debugLine="End Sub";
return "";
}
public static void  _btnlogin_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnlogin_click"))
	 {Debug.delegate(mostCurrent.activityBA, "btnlogin_click", null); return;}
ResumableSub_btnLogin_Click rsub = new ResumableSub_btnLogin_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnLogin_Click extends BA.ResumableSub {
public ResumableSub_btnLogin_Click(cepave.geovin.main parent) {
this.parent = parent;
}
cepave.geovin.main parent;
String _strpassword = "";
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _msj = "";
anywheresoftware.b4a.objects.collections.Map _userexists = null;
anywheresoftware.b4a.objects.collections.List _newuser = null;
anywheresoftware.b4a.objects.collections.Map _m = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="main";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=4456449;
 //BA.debugLineNum = 4456449;BA.debugLine="k.HideKeyboard(Activity)";
parent._k.HideKeyboard(parent.mostCurrent._activity);
RDebugUtils.currentLine=4456451;
 //BA.debugLineNum = 4456451;BA.debugLine="strUserID = txtUserID.Text.Trim";
parent._struserid = parent.mostCurrent._txtuserid.getText().trim();
RDebugUtils.currentLine=4456452;
 //BA.debugLineNum = 4456452;BA.debugLine="If strUserID = \"\" Then";
if (true) break;

case 1:
//if
this.state = 4;
if ((parent._struserid).equals("")) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
RDebugUtils.currentLine=4456453;
 //BA.debugLineNum = 4456453;BA.debugLine="Msgbox(\"Ingrese su usuario\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese su usuario"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=4456454;
 //BA.debugLineNum = 4456454;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = 5;
;
RDebugUtils.currentLine=4456456;
 //BA.debugLineNum = 4456456;BA.debugLine="Dim strPassword As String = txtPassword.Text.Trim";
_strpassword = parent.mostCurrent._txtpassword.getText().trim();
RDebugUtils.currentLine=4456457;
 //BA.debugLineNum = 4456457;BA.debugLine="If strPassword = \"\" Then";
if (true) break;

case 5:
//if
this.state = 8;
if ((_strpassword).equals("")) { 
this.state = 7;
}if (true) break;

case 7:
//C
this.state = 8;
RDebugUtils.currentLine=4456458;
 //BA.debugLineNum = 4456458;BA.debugLine="Msgbox(\"Ingrese su clave\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese su clave"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=4456459;
 //BA.debugLineNum = 4456459;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 8:
//C
this.state = 9;
;
RDebugUtils.currentLine=4456462;
 //BA.debugLineNum = 4456462;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=4456463;
 //BA.debugLineNum = 4456463;BA.debugLine="j.Initialize(\"Login\", Me)";
_j._initialize(processBA,"Login",main.getObject());
RDebugUtils.currentLine=4456464;
 //BA.debugLineNum = 4456464;BA.debugLine="j.Download2(serverPath & \"/connect/signin2.php\",";
_j._download2(parent._serverpath+"/connect/signin2.php",new String[]{"user_id",parent._struserid,"password",_strpassword});
RDebugUtils.currentLine=4456465;
 //BA.debugLineNum = 4456465;BA.debugLine="ProgressDialogShow(\"Conectando al servidor...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Conectando al servidor..."));
RDebugUtils.currentLine=4456466;
 //BA.debugLineNum = 4456466;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "main", "btnlogin_click"), (Object)(_j));
this.state = 42;
return;
case 42:
//C
this.state = 9;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=4456468;
 //BA.debugLineNum = 4456468;BA.debugLine="If j.Success Then";
if (true) break;

case 9:
//if
this.state = 41;
if (_j._success) { 
this.state = 11;
}else {
this.state = 40;
}if (true) break;

case 11:
//C
this.state = 12;
RDebugUtils.currentLine=4456469;
 //BA.debugLineNum = 4456469;BA.debugLine="Dim ret As String";
_ret = "";
RDebugUtils.currentLine=4456470;
 //BA.debugLineNum = 4456470;BA.debugLine="Dim act As String";
_act = "";
RDebugUtils.currentLine=4456471;
 //BA.debugLineNum = 4456471;BA.debugLine="ret = j.GetString";
_ret = _j._getstring();
RDebugUtils.currentLine=4456473;
 //BA.debugLineNum = 4456473;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=4456474;
 //BA.debugLineNum = 4456474;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
RDebugUtils.currentLine=4456475;
 //BA.debugLineNum = 4456475;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=4456477;
 //BA.debugLineNum = 4456477;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 12:
//if
this.state = 38;
if ((_act).equals("Not Found")) { 
this.state = 14;
}else 
{RDebugUtils.currentLine=4456479;
 //BA.debugLineNum = 4456479;BA.debugLine="Else If act = \"Error\" Then";
if ((_act).equals("Error")) { 
this.state = 16;
}else 
{RDebugUtils.currentLine=4456481;
 //BA.debugLineNum = 4456481;BA.debugLine="Else If act = \"Login OK\" Then";
if ((_act).equals("Login OK")) { 
this.state = 18;
}else {
this.state = 37;
}}}
if (true) break;

case 14:
//C
this.state = 38;
RDebugUtils.currentLine=4456478;
 //BA.debugLineNum = 4456478;BA.debugLine="ToastMessageShow(\"Error\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 16:
//C
this.state = 38;
RDebugUtils.currentLine=4456480;
 //BA.debugLineNum = 4456480;BA.debugLine="ToastMessageShow(\"Usuario o clave incorrecta\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Usuario o clave incorrecta"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 18:
//C
this.state = 19;
RDebugUtils.currentLine=4456483;
 //BA.debugLineNum = 4456483;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4456484;
 //BA.debugLineNum = 4456484;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
RDebugUtils.currentLine=4456486;
 //BA.debugLineNum = 4456486;BA.debugLine="strUserID = newpunto.Get(\"id\")";
parent._struserid = BA.ObjectToString(_newpunto.Get((Object)("id")));
RDebugUtils.currentLine=4456487;
 //BA.debugLineNum = 4456487;BA.debugLine="strUserName = newpunto.Get(\"username\")";
parent._strusername = BA.ObjectToString(_newpunto.Get((Object)("username")));
RDebugUtils.currentLine=4456488;
 //BA.debugLineNum = 4456488;BA.debugLine="strUserLocation = newpunto.Get(\"location\")";
parent._struserlocation = BA.ObjectToString(_newpunto.Get((Object)("location")));
RDebugUtils.currentLine=4456489;
 //BA.debugLineNum = 4456489;BA.debugLine="strUserEmail = newpunto.Get(\"email\")";
parent._struseremail = BA.ObjectToString(_newpunto.Get((Object)("email")));
RDebugUtils.currentLine=4456490;
 //BA.debugLineNum = 4456490;BA.debugLine="strUserOrg = newpunto.Get(\"org\")";
parent._struserorg = BA.ObjectToString(_newpunto.Get((Object)("org")));
RDebugUtils.currentLine=4456491;
 //BA.debugLineNum = 4456491;BA.debugLine="strUserTipoUsuario = newpunto.Get(\"tipousuario\"";
parent._strusertipousuario = BA.ObjectToString(_newpunto.Get((Object)("tipousuario")));
RDebugUtils.currentLine=4456492;
 //BA.debugLineNum = 4456492;BA.debugLine="pass = newpunto.Get(\"password\")";
parent._pass = BA.ObjectToString(_newpunto.Get((Object)("password")));
RDebugUtils.currentLine=4456493;
 //BA.debugLineNum = 4456493;BA.debugLine="username = strUserName";
parent._username = parent._strusername;
RDebugUtils.currentLine=4456494;
 //BA.debugLineNum = 4456494;BA.debugLine="btnForgot.Visible = False";
parent.mostCurrent._btnforgot.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=4456495;
 //BA.debugLineNum = 4456495;BA.debugLine="modooffline = False";
parent._modooffline = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=4456497;
 //BA.debugLineNum = 4456497;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4456498;
 //BA.debugLineNum = 4456498;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=4456499;
 //BA.debugLineNum = 4456499;BA.debugLine="Map1.Put(\"strUserID\", strUserID)";
_map1.Put((Object)("strUserID"),(Object)(parent._struserid));
RDebugUtils.currentLine=4456500;
 //BA.debugLineNum = 4456500;BA.debugLine="Map1.Put(\"username\", username)";
_map1.Put((Object)("username"),(Object)(parent._username));
RDebugUtils.currentLine=4456501;
 //BA.debugLineNum = 4456501;BA.debugLine="Map1.Put(\"msjprivadouser\", msjprivadouser)";
_map1.Put((Object)("msjprivadouser"),(Object)(parent._msjprivadouser));
RDebugUtils.currentLine=4456502;
 //BA.debugLineNum = 4456502;BA.debugLine="Map1.Put(\"pass\", pass)";
_map1.Put((Object)("pass"),(Object)(parent._pass));
RDebugUtils.currentLine=4456503;
 //BA.debugLineNum = 4456503;BA.debugLine="Map1.Put(\"firstuse\", firstuse)";
_map1.Put((Object)("firstuse"),(Object)(parent._firstuse));
RDebugUtils.currentLine=4456504;
 //BA.debugLineNum = 4456504;BA.debugLine="File.WriteMap(File.DirInternal, \"config.txt\", M";
anywheresoftware.b4a.keywords.Common.File.WriteMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt",_map1);
RDebugUtils.currentLine=4456508;
 //BA.debugLineNum = 4456508;BA.debugLine="Dim msj As String = newpunto.Get(\"msjprivado\")";
_msj = BA.ObjectToString(_newpunto.Get((Object)("msjprivado")));
RDebugUtils.currentLine=4456509;
 //BA.debugLineNum = 4456509;BA.debugLine="If msj = msjprivadouser Then";
if (true) break;

case 19:
//if
this.state = 24;
if ((_msj).equals(parent._msjprivadouser)) { 
this.state = 21;
}else {
this.state = 23;
}if (true) break;

case 21:
//C
this.state = 24;
RDebugUtils.currentLine=4456510;
 //BA.debugLineNum = 4456510;BA.debugLine="msjprivadoleido = True";
parent._msjprivadoleido = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 23:
//C
this.state = 24;
RDebugUtils.currentLine=4456512;
 //BA.debugLineNum = 4456512;BA.debugLine="msjprivadoleido = False";
parent._msjprivadoleido = anywheresoftware.b4a.keywords.Common.False;
 if (true) break;
;
RDebugUtils.currentLine=4456514;
 //BA.debugLineNum = 4456514;BA.debugLine="If msj <> Null And msj <> \"\" Then";

case 24:
//if
this.state = 29;
if (_msj!= null && (_msj).equals("") == false) { 
this.state = 26;
}else {
this.state = 28;
}if (true) break;

case 26:
//C
this.state = 29;
RDebugUtils.currentLine=4456515;
 //BA.debugLineNum = 4456515;BA.debugLine="msjprivadouser = msj";
parent._msjprivadouser = _msj;
 if (true) break;

case 28:
//C
this.state = 29;
RDebugUtils.currentLine=4456517;
 //BA.debugLineNum = 4456517;BA.debugLine="msjprivadouser = \"None\"";
parent._msjprivadouser = "None";
 if (true) break;

case 29:
//C
this.state = 30;
;
RDebugUtils.currentLine=4456522;
 //BA.debugLineNum = 4456522;BA.debugLine="Dim userExists As Map";
_userexists = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4456523;
 //BA.debugLineNum = 4456523;BA.debugLine="userExists.Initialize";
_userexists.Initialize();
RDebugUtils.currentLine=4456524;
 //BA.debugLineNum = 4456524;BA.debugLine="userExists = DBUtils.ExecuteMap(Starter.sqlDB,";
_userexists = parent.mostCurrent._dbutils._executemap(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"SELECT * FROM userconfig WHERE username=?",new String[]{parent._username});
RDebugUtils.currentLine=4456525;
 //BA.debugLineNum = 4456525;BA.debugLine="If userExists = Null Or userExists.IsInitialize";
if (true) break;

case 30:
//if
this.state = 35;
if (_userexists== null || _userexists.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 32;
}else {
this.state = 34;
}if (true) break;

case 32:
//C
this.state = 35;
RDebugUtils.currentLine=4456527;
 //BA.debugLineNum = 4456527;BA.debugLine="Dim newUser As List";
_newuser = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=4456528;
 //BA.debugLineNum = 4456528;BA.debugLine="newUser.Initialize";
_newuser.Initialize();
RDebugUtils.currentLine=4456529;
 //BA.debugLineNum = 4456529;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4456530;
 //BA.debugLineNum = 4456530;BA.debugLine="m.Initialize";
_m.Initialize();
RDebugUtils.currentLine=4456531;
 //BA.debugLineNum = 4456531;BA.debugLine="m.Put(\"username\", username)";
_m.Put((Object)("username"),(Object)(parent._username));
RDebugUtils.currentLine=4456532;
 //BA.debugLineNum = 4456532;BA.debugLine="m.Put(\"userID\", strUserID)";
_m.Put((Object)("userID"),(Object)(parent._struserid));
RDebugUtils.currentLine=4456533;
 //BA.debugLineNum = 4456533;BA.debugLine="m.Put(\"userLocation\", strUserLocation )";
_m.Put((Object)("userLocation"),(Object)(parent._struserlocation));
RDebugUtils.currentLine=4456534;
 //BA.debugLineNum = 4456534;BA.debugLine="m.Put(\"userEmail\", strUserEmail)";
_m.Put((Object)("userEmail"),(Object)(parent._struseremail));
RDebugUtils.currentLine=4456535;
 //BA.debugLineNum = 4456535;BA.debugLine="m.Put(\"userOrg\", strUserOrg)";
_m.Put((Object)("userOrg"),(Object)(parent._struserorg));
RDebugUtils.currentLine=4456536;
 //BA.debugLineNum = 4456536;BA.debugLine="m.Put(\"userTipoUsuario\", strUserTipoUsuario)";
_m.Put((Object)("userTipoUsuario"),(Object)(parent._strusertipousuario));
RDebugUtils.currentLine=4456537;
 //BA.debugLineNum = 4456537;BA.debugLine="m.Put(\"pass\", pass)";
_m.Put((Object)("pass"),(Object)(parent._pass));
RDebugUtils.currentLine=4456538;
 //BA.debugLineNum = 4456538;BA.debugLine="m.Put(\"firstuse\", \"True\")";
_m.Put((Object)("firstuse"),(Object)("True"));
RDebugUtils.currentLine=4456539;
 //BA.debugLineNum = 4456539;BA.debugLine="m.Put(\"lastuser\", \"si\")";
_m.Put((Object)("lastuser"),(Object)("si"));
RDebugUtils.currentLine=4456540;
 //BA.debugLineNum = 4456540;BA.debugLine="newUser.Add(m)";
_newuser.Add((Object)(_m.getObject()));
RDebugUtils.currentLine=4456541;
 //BA.debugLineNum = 4456541;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"userconfig\",";
parent.mostCurrent._dbutils._insertmaps(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"userconfig",_newuser,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 34:
//C
this.state = 35;
RDebugUtils.currentLine=4456544;
 //BA.debugLineNum = 4456544;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4456545;
 //BA.debugLineNum = 4456545;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=4456546;
 //BA.debugLineNum = 4456546;BA.debugLine="Map1.Put(\"username\", username)";
_map1.Put((Object)("username"),(Object)(parent._username));
RDebugUtils.currentLine=4456547;
 //BA.debugLineNum = 4456547;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"userconfig","lastuser",(Object)("si"),_map1);
RDebugUtils.currentLine=4456549;
 //BA.debugLineNum = 4456549;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"userconfig","userLocation",(Object)(parent._struserlocation),_map1);
RDebugUtils.currentLine=4456550;
 //BA.debugLineNum = 4456550;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"userconfig","userEmail",(Object)(parent._struseremail),_map1);
RDebugUtils.currentLine=4456551;
 //BA.debugLineNum = 4456551;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"userconfig","userOrg",(Object)(parent._struserorg),_map1);
RDebugUtils.currentLine=4456552;
 //BA.debugLineNum = 4456552;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"userconfig","UserTipoUsuario",(Object)(parent._strusertipousuario),_map1);
 if (true) break;

case 35:
//C
this.state = 38;
;
RDebugUtils.currentLine=4456555;
 //BA.debugLineNum = 4456555;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=4456556;
 //BA.debugLineNum = 4456556;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
RDebugUtils.currentLine=4456557;
 //BA.debugLineNum = 4456557;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmprincipal.getObject()));
 if (true) break;

case 37:
//C
this.state = 38;
RDebugUtils.currentLine=4456560;
 //BA.debugLineNum = 4456560;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
RDebugUtils.currentLine=4456561;
 //BA.debugLineNum = 4456561;BA.debugLine="Msgbox(act,\"act\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_act),BA.ObjectToCharSequence("act"),mostCurrent.activityBA);
 if (true) break;

case 38:
//C
this.state = 41;
;
 if (true) break;

case 40:
//C
this.state = 41;
RDebugUtils.currentLine=4456564;
 //BA.debugLineNum = 4456564;BA.debugLine="ToastMessageShow(\"Usuario o contraseña incorrect";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Usuario o contraseña incorrectos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 41:
//C
this.state = -1;
;
RDebugUtils.currentLine=4456566;
 //BA.debugLineNum = 4456566;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=4456567;
 //BA.debugLineNum = 4456567;BA.debugLine="j.Release";
_j._release();
RDebugUtils.currentLine=4456569;
 //BA.debugLineNum = 4456569;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btnoffline_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnoffline_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnoffline_click", null));}
RDebugUtils.currentLine=4784128;
 //BA.debugLineNum = 4784128;BA.debugLine="Sub btnOffline_Click";
RDebugUtils.currentLine=4784130;
 //BA.debugLineNum = 4784130;BA.debugLine="modooffline = True";
_modooffline = anywheresoftware.b4a.keywords.Common.True;
RDebugUtils.currentLine=4784131;
 //BA.debugLineNum = 4784131;BA.debugLine="forceoffline = True";
_forceoffline = anywheresoftware.b4a.keywords.Common.True;
RDebugUtils.currentLine=4784132;
 //BA.debugLineNum = 4784132;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=4784133;
 //BA.debugLineNum = 4784133;BA.debugLine="utilidades.Mensaje(\"Advertencia\", \"MsgIcon.png\",";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Advertencia","MsgIcon.png","Modo sin internet","Has seleccionado el modo sin conexión. GeoVin iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes.","OK","","",anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=4784135;
 //BA.debugLineNum = 4784135;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
RDebugUtils.currentLine=4784137;
 //BA.debugLineNum = 4784137;BA.debugLine="End Sub";
return "";
}
public static void  _cargarbienvenidos() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargarbienvenidos"))
	 {Debug.delegate(mostCurrent.activityBA, "cargarbienvenidos", null); return;}
ResumableSub_CargarBienvenidos rsub = new ResumableSub_CargarBienvenidos(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_CargarBienvenidos extends BA.ResumableSub {
public ResumableSub_CargarBienvenidos(cepave.geovin.main parent) {
this.parent = parent;
}
cepave.geovin.main parent;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
String _msg = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
anywheresoftware.b4a.objects.collections.Map _usrmap = null;
int step22;
int limit22;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="main";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=4325380;
 //BA.debugLineNum = 4325380;BA.debugLine="Activity.AddMenuItem(\"Cerrar sesión\", \"mnuCerrarS";
parent.mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Cerrar sesión"),"mnuCerrarSesion");
RDebugUtils.currentLine=4325381;
 //BA.debugLineNum = 4325381;BA.debugLine="Activity.AddMenuItem(\"Acerca de GeoVin\", \"mnuAbou";
parent.mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Acerca de GeoVin"),"mnuAbout");
RDebugUtils.currentLine=4325385;
 //BA.debugLineNum = 4325385;BA.debugLine="If modooffline = False Then";
if (true) break;

case 1:
//if
this.state = 38;
if (parent._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 37;
}if (true) break;

case 3:
//C
this.state = 4;
RDebugUtils.currentLine=4325387;
 //BA.debugLineNum = 4325387;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=4325388;
 //BA.debugLineNum = 4325388;BA.debugLine="j.Initialize(\"CheckMessages\", Me)";
_j._initialize(processBA,"CheckMessages",main.getObject());
RDebugUtils.currentLine=4325389;
 //BA.debugLineNum = 4325389;BA.debugLine="j.Download2(serverPath & \"/connect/checkmessages";
_j._download2(parent._serverpath+"/connect/checkmessages.php",new String[]{"deviceID",parent._deviceid});
RDebugUtils.currentLine=4325390;
 //BA.debugLineNum = 4325390;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "main", "cargarbienvenidos"), (Object)(_j));
this.state = 53;
return;
case 53:
//C
this.state = 4;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=4325391;
 //BA.debugLineNum = 4325391;BA.debugLine="If j.Success Then";
if (true) break;

case 4:
//if
this.state = 35;
if (_j._success) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
RDebugUtils.currentLine=4325392;
 //BA.debugLineNum = 4325392;BA.debugLine="Dim ret As String";
_ret = "";
RDebugUtils.currentLine=4325393;
 //BA.debugLineNum = 4325393;BA.debugLine="Dim act As String";
_act = "";
RDebugUtils.currentLine=4325394;
 //BA.debugLineNum = 4325394;BA.debugLine="ret = j.GetString";
_ret = _j._getstring();
RDebugUtils.currentLine=4325395;
 //BA.debugLineNum = 4325395;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=4325396;
 //BA.debugLineNum = 4325396;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
RDebugUtils.currentLine=4325397;
 //BA.debugLineNum = 4325397;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=4325398;
 //BA.debugLineNum = 4325398;BA.debugLine="modooffline = False";
parent._modooffline = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=4325399;
 //BA.debugLineNum = 4325399;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=4325400;
 //BA.debugLineNum = 4325400;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 7:
//if
this.state = 34;
if ((_act).equals("Not Found")) { 
this.state = 9;
}else 
{RDebugUtils.currentLine=4325401;
 //BA.debugLineNum = 4325401;BA.debugLine="Else If act = \"Error\" Then";
if ((_act).equals("Error")) { 
this.state = 11;
}else 
{RDebugUtils.currentLine=4325402;
 //BA.debugLineNum = 4325402;BA.debugLine="Else If act = \"MensajesOK\" Then";
if ((_act).equals("MensajesOK")) { 
this.state = 13;
}}}
if (true) break;

case 9:
//C
this.state = 34;
 if (true) break;

case 11:
//C
this.state = 34;
 if (true) break;

case 13:
//C
this.state = 14;
RDebugUtils.currentLine=4325404;
 //BA.debugLineNum = 4325404;BA.debugLine="Dim numresults As String";
_numresults = "";
RDebugUtils.currentLine=4325405;
 //BA.debugLineNum = 4325405;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=4325406;
 //BA.debugLineNum = 4325406;BA.debugLine="For i = 0 To numresults - 1";
if (true) break;

case 14:
//for
this.state = 33;
step22 = 1;
limit22 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
this.state = 54;
if (true) break;

case 54:
//C
this.state = 33;
if ((step22 > 0 && _i <= limit22) || (step22 < 0 && _i >= limit22)) this.state = 16;
if (true) break;

case 55:
//C
this.state = 54;
_i = ((int)(0 + _i + step22)) ;
if (true) break;

case 16:
//C
this.state = 17;
RDebugUtils.currentLine=4325407;
 //BA.debugLineNum = 4325407;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4325408;
 //BA.debugLineNum = 4325408;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
RDebugUtils.currentLine=4325409;
 //BA.debugLineNum = 4325409;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=4325415;
 //BA.debugLineNum = 4325415;BA.debugLine="If newpunto.Get(\"mensaje_type\") = \"invalido\"";
if (true) break;

case 17:
//if
this.state = 32;
if ((_newpunto.Get((Object)("mensaje_type"))).equals((Object)("invalido"))) { 
this.state = 19;
}else 
{RDebugUtils.currentLine=4325419;
 //BA.debugLineNum = 4325419;BA.debugLine="Else if newpunto.Get(\"mensaje_type\") = \"ninfa";
if ((_newpunto.Get((Object)("mensaje_type"))).equals((Object)("ninfa"))) { 
this.state = 21;
}else 
{RDebugUtils.currentLine=4325422;
 //BA.debugLineNum = 4325422;BA.debugLine="Else if newpunto.Get(\"mensaje_type\") = \"novin";
if ((_newpunto.Get((Object)("mensaje_type"))).equals((Object)("novinchuca"))) { 
this.state = 23;
}else 
{RDebugUtils.currentLine=4325425;
 //BA.debugLineNum = 4325425;BA.debugLine="Else if newpunto.Get(\"mensaje_type\") = \"sospe";
if ((_newpunto.Get((Object)("mensaje_type"))).equals((Object)("sospechoso"))) { 
this.state = 25;
}else {
this.state = 27;
}}}}
if (true) break;

case 19:
//C
this.state = 32;
RDebugUtils.currentLine=4325417;
 //BA.debugLineNum = 4325417;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = parent.mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Identificación","MsgMensaje.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"","","Ok gracias!",anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 21:
//C
this.state = 32;
RDebugUtils.currentLine=4325421;
 //BA.debugLineNum = 4325421;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = parent.mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Identificación","MsgMensaje.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"","","Ok gracias!",anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 23:
//C
this.state = 32;
RDebugUtils.currentLine=4325424;
 //BA.debugLineNum = 4325424;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = parent.mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Identificación","MsgMensaje.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"","","Ok gracias!",anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 25:
//C
this.state = 32;
RDebugUtils.currentLine=4325427;
 //BA.debugLineNum = 4325427;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = parent.mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Identificación","MsgVinchuca.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"Ver detalles","","Ok, gracias!",anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 27:
//C
this.state = 28;
RDebugUtils.currentLine=4325429;
 //BA.debugLineNum = 4325429;BA.debugLine="msg = utilidades.Mensaje(\"Identificación\", \"";
_msg = parent.mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Identificación","MsgVinchuca.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"Ver detalles","","Ok, gracias!",anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=4325431;
 //BA.debugLineNum = 4325431;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if (true) break;

case 28:
//if
this.state = 31;
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
this.state = 30;
}if (true) break;

case 30:
//C
this.state = 31;
RDebugUtils.currentLine=4325433;
 //BA.debugLineNum = 4325433;BA.debugLine="frmDatosAnteriores.notificacion = True";
parent.mostCurrent._frmdatosanteriores._notificacion = anywheresoftware.b4a.keywords.Common.True;
RDebugUtils.currentLine=4325434;
 //BA.debugLineNum = 4325434;BA.debugLine="frmDatosAnteriores.serverIdNum = newpunto.G";
parent.mostCurrent._frmdatosanteriores._serveridnum = BA.ObjectToString(_newpunto.Get((Object)("serverid")));
 if (true) break;

case 31:
//C
this.state = 32;
;
 if (true) break;

case 32:
//C
this.state = 55;
;
RDebugUtils.currentLine=4325438;
 //BA.debugLineNum = 4325438;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4325439;
 //BA.debugLineNum = 4325439;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=4325440;
 //BA.debugLineNum = 4325440;BA.debugLine="Map1.Put(\"serverid\", newpunto.Get(\"serverid\")";
_map1.Put((Object)("serverid"),_newpunto.Get((Object)("serverid")));
RDebugUtils.currentLine=4325441;
 //BA.debugLineNum = 4325441;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"markers_local","valorOrganismo",_newpunto.Get((Object)("mensaje_type")),_map1);
RDebugUtils.currentLine=4325442;
 //BA.debugLineNum = 4325442;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"markers_local","estadoValidacion",(Object)("validado"),_map1);
RDebugUtils.currentLine=4325444;
 //BA.debugLineNum = 4325444;BA.debugLine="ResetMessages";
_resetmessages();
 if (true) break;
if (true) break;

case 33:
//C
this.state = 34;
;
 if (true) break;

case 34:
//C
this.state = 35;
;
 if (true) break;

case 35:
//C
this.state = 38;
;
 if (true) break;

case 37:
//C
this.state = 38;
RDebugUtils.currentLine=4325450;
 //BA.debugLineNum = 4325450;BA.debugLine="Msgbox(\"No tienes conexión a Internet, no puedes";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes conexión a Internet, no puedes recibir los mensajes de los revisores"),BA.ObjectToCharSequence("Advertencia"),mostCurrent.activityBA);
RDebugUtils.currentLine=4325451;
 //BA.debugLineNum = 4325451;BA.debugLine="modooffline = True";
parent._modooffline = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;
;
RDebugUtils.currentLine=4325457;
 //BA.debugLineNum = 4325457;BA.debugLine="If modooffline = False Then";

case 38:
//if
this.state = 52;
if (parent._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 40;
}else {
this.state = 51;
}if (true) break;

case 40:
//C
this.state = 41;
RDebugUtils.currentLine=4325458;
 //BA.debugLineNum = 4325458;BA.debugLine="Activity.LoadLayout(\"frmBienvenidos\")";
parent.mostCurrent._activity.LoadLayout("frmBienvenidos",mostCurrent.activityBA);
RDebugUtils.currentLine=4325460;
 //BA.debugLineNum = 4325460;BA.debugLine="Dim usrMap As Map";
_usrmap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4325461;
 //BA.debugLineNum = 4325461;BA.debugLine="usrMap.Initialize";
_usrmap.Initialize();
RDebugUtils.currentLine=4325462;
 //BA.debugLineNum = 4325462;BA.debugLine="usrMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_usrmap = parent.mostCurrent._dbutils._executemap(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"SELECT * FROM userconfig WHERE lastUser = 'si'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=4325463;
 //BA.debugLineNum = 4325463;BA.debugLine="If usrMap = Null Or usrMap.IsInitialized = Fals";
if (true) break;

case 41:
//if
this.state = 46;
if (_usrmap== null || _usrmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 43;
}else {
this.state = 45;
}if (true) break;

case 43:
//C
this.state = 46;
 if (true) break;

case 45:
//C
this.state = 46;
RDebugUtils.currentLine=4325466;
 //BA.debugLineNum = 4325466;BA.debugLine="txtUserID.Text = usrMap.Get(\"username\")";
parent.mostCurrent._txtuserid.setText(BA.ObjectToCharSequence(_usrmap.Get((Object)("username"))));
RDebugUtils.currentLine=4325467;
 //BA.debugLineNum = 4325467;BA.debugLine="txtPassword.Text = usrMap.Get(\"pass\")";
parent.mostCurrent._txtpassword.setText(BA.ObjectToCharSequence(_usrmap.Get((Object)("pass"))));
 if (true) break;
;
RDebugUtils.currentLine=4325470;
 //BA.debugLineNum = 4325470;BA.debugLine="If frmDatosAnteriores.notificacion = True Then";

case 46:
//if
this.state = 49;
if (parent.mostCurrent._frmdatosanteriores._notificacion==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 48;
}if (true) break;

case 48:
//C
this.state = 49;
RDebugUtils.currentLine=4325471;
 //BA.debugLineNum = 4325471;BA.debugLine="CallSubDelayed3(frmDatosAnteriores,\"VerDetalle";
anywheresoftware.b4a.keywords.Common.CallSubDelayed3(processBA,(Object)(parent.mostCurrent._frmdatosanteriores.getObject()),"VerDetalles",(Object)(parent.mostCurrent._frmdatosanteriores._serveridnum),(Object)(anywheresoftware.b4a.keywords.Common.True));
 if (true) break;

case 49:
//C
this.state = 52;
;
 if (true) break;

case 51:
//C
this.state = 52;
RDebugUtils.currentLine=4325474;
 //BA.debugLineNum = 4325474;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=4325475;
 //BA.debugLineNum = 4325475;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
RDebugUtils.currentLine=4325476;
 //BA.debugLineNum = 4325476;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmprincipal.getObject()));
 if (true) break;

case 52:
//C
this.state = -1;
;
RDebugUtils.currentLine=4325480;
 //BA.debugLineNum = 4325480;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btnregister_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnregister_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnregister_click", null));}
RDebugUtils.currentLine=4521984;
 //BA.debugLineNum = 4521984;BA.debugLine="Sub btnRegister_Click";
RDebugUtils.currentLine=4521985;
 //BA.debugLineNum = 4521985;BA.debugLine="StartActivity(\"Register\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("Register"));
RDebugUtils.currentLine=4521986;
 //BA.debugLineNum = 4521986;BA.debugLine="End Sub";
return "";
}
public static void  _btnsendforgot_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnsendforgot_click"))
	 {Debug.delegate(mostCurrent.activityBA, "btnsendforgot_click", null); return;}
ResumableSub_btnSendForgot_Click rsub = new ResumableSub_btnSendForgot_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnSendForgot_Click extends BA.ResumableSub {
public ResumableSub_btnSendForgot_Click(cepave.geovin.main parent) {
this.parent = parent;
}
cepave.geovin.main parent;
anywheresoftware.b4a.agraham.dialogs.InputDialog _forgotdialog = null;
String _forgotresponse = "";
String _recoveremail = "";
anywheresoftware.b4a.samples.httputils2.httpjob _recoverpass = null;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="main";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=4653057;
 //BA.debugLineNum = 4653057;BA.debugLine="Dim forgotDialog As InputDialog";
_forgotdialog = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
RDebugUtils.currentLine=4653058;
 //BA.debugLineNum = 4653058;BA.debugLine="Dim forgotResponse As String";
_forgotresponse = "";
RDebugUtils.currentLine=4653059;
 //BA.debugLineNum = 4653059;BA.debugLine="forgotResponse = forgotDialog.Show(\"Ingrese su em";
_forgotresponse = BA.NumberToString(_forgotdialog.Show("Ingrese su email y le reenviaremos su clave","Recuperar clave","Ok","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)));
RDebugUtils.currentLine=4653060;
 //BA.debugLineNum = 4653060;BA.debugLine="If forgotDialog.Response = DialogResponse.CANCEL";
if (true) break;

case 1:
//if
this.state = 22;
if (_forgotdialog.getResponse()==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 22;
RDebugUtils.currentLine=4653061;
 //BA.debugLineNum = 4653061;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 5:
//C
this.state = 6;
RDebugUtils.currentLine=4653063;
 //BA.debugLineNum = 4653063;BA.debugLine="Dim recoveremail As String";
_recoveremail = "";
RDebugUtils.currentLine=4653065;
 //BA.debugLineNum = 4653065;BA.debugLine="recoveremail = forgotDialog.Input.Trim";
_recoveremail = _forgotdialog.getInput().trim();
RDebugUtils.currentLine=4653066;
 //BA.debugLineNum = 4653066;BA.debugLine="If recoveremail = \"\" Then";
if (true) break;

case 6:
//if
this.state = 9;
if ((_recoveremail).equals("")) { 
this.state = 8;
}if (true) break;

case 8:
//C
this.state = 9;
RDebugUtils.currentLine=4653067;
 //BA.debugLineNum = 4653067;BA.debugLine="ToastMessageShow(\"Ingrese su email\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingrese su email"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=4653068;
 //BA.debugLineNum = 4653068;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 9:
//C
this.state = 10;
;
RDebugUtils.currentLine=4653072;
 //BA.debugLineNum = 4653072;BA.debugLine="Dim RecoverPass As HttpJob";
_recoverpass = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=4653073;
 //BA.debugLineNum = 4653073;BA.debugLine="RecoverPass.Initialize(\"Recover\", Me)";
_recoverpass._initialize(processBA,"Recover",main.getObject());
RDebugUtils.currentLine=4653077;
 //BA.debugLineNum = 4653077;BA.debugLine="RecoverPass.Download2(serverPath & \"/connect/rec";
_recoverpass._download2(parent._serverpath+"/connect/recover.php",new String[]{"email",_recoveremail});
RDebugUtils.currentLine=4653079;
 //BA.debugLineNum = 4653079;BA.debugLine="ProgressDialogShow(\"Solicitando recuperación...";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Solicitando recuperación..."));
RDebugUtils.currentLine=4653081;
 //BA.debugLineNum = 4653081;BA.debugLine="Wait For (RecoverPass) JobDone(RecoverPass As Ht";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "main", "btnsendforgot_click"), (Object)(_recoverpass));
this.state = 23;
return;
case 23:
//C
this.state = 10;
_recoverpass = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=4653082;
 //BA.debugLineNum = 4653082;BA.debugLine="If RecoverPass.Success Then";
if (true) break;

case 10:
//if
this.state = 21;
if (_recoverpass._success) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
RDebugUtils.currentLine=4653084;
 //BA.debugLineNum = 4653084;BA.debugLine="Dim ret As String";
_ret = "";
RDebugUtils.currentLine=4653085;
 //BA.debugLineNum = 4653085;BA.debugLine="Dim act As String";
_act = "";
RDebugUtils.currentLine=4653086;
 //BA.debugLineNum = 4653086;BA.debugLine="ret = RecoverPass.GetString";
_ret = _recoverpass._getstring();
RDebugUtils.currentLine=4653087;
 //BA.debugLineNum = 4653087;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=4653088;
 //BA.debugLineNum = 4653088;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
RDebugUtils.currentLine=4653089;
 //BA.debugLineNum = 4653089;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=4653090;
 //BA.debugLineNum = 4653090;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 13:
//if
this.state = 20;
if ((_act).equals("Not Found")) { 
this.state = 15;
}else 
{RDebugUtils.currentLine=4653092;
 //BA.debugLineNum = 4653092;BA.debugLine="Else If act = \"Error\" Then";
if ((_act).equals("Error")) { 
this.state = 17;
}else 
{RDebugUtils.currentLine=4653094;
 //BA.debugLineNum = 4653094;BA.debugLine="Else If act = \"RecoverOK\" Then";
if ((_act).equals("RecoverOK")) { 
this.state = 19;
}}}
if (true) break;

case 15:
//C
this.state = 20;
RDebugUtils.currentLine=4653091;
 //BA.debugLineNum = 4653091;BA.debugLine="ToastMessageShow(\"Error: El email no existe\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: El email no existe"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 17:
//C
this.state = 20;
RDebugUtils.currentLine=4653093;
 //BA.debugLineNum = 4653093;BA.debugLine="ToastMessageShow(\"Error: Ha ocurrido un error!";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: Ha ocurrido un error!"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 19:
//C
this.state = 20;
RDebugUtils.currentLine=4653096;
 //BA.debugLineNum = 4653096;BA.debugLine="ToastMessageShow(\"Se ha enviado su clave a su";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se ha enviado su clave a su email registrado"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 20:
//C
this.state = 21;
;
 if (true) break;

case 21:
//C
this.state = 22;
;
RDebugUtils.currentLine=4653099;
 //BA.debugLineNum = 4653099;BA.debugLine="RecoverPass.Release";
_recoverpass._release();
RDebugUtils.currentLine=4653100;
 //BA.debugLineNum = 4653100;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=4653101;
 //BA.debugLineNum = 4653101;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 22:
//C
this.state = -1;
;
RDebugUtils.currentLine=4653104;
 //BA.debugLineNum = 4653104;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _butcancel_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "butcancel_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "butcancel_click", null));}
RDebugUtils.currentLine=4718592;
 //BA.debugLineNum = 4718592;BA.debugLine="Sub butCancel_Click";
RDebugUtils.currentLine=4718593;
 //BA.debugLineNum = 4718593;BA.debugLine="pnlRecoverPass.Visible = False";
mostCurrent._pnlrecoverpass.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=4718594;
 //BA.debugLineNum = 4718594;BA.debugLine="pnlRecoverPass.SendToBack";
mostCurrent._pnlrecoverpass.SendToBack();
RDebugUtils.currentLine=4718595;
 //BA.debugLineNum = 4718595;BA.debugLine="End Sub";
return "";
}
public static void  _resetmessages() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "resetmessages"))
	 {Debug.delegate(mostCurrent.activityBA, "resetmessages", null); return;}
ResumableSub_ResetMessages rsub = new ResumableSub_ResetMessages(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_ResetMessages extends BA.ResumableSub {
public ResumableSub_ResetMessages(cepave.geovin.main parent) {
this.parent = parent;
}
cepave.geovin.main parent;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="main";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=4915201;
 //BA.debugLineNum = 4915201;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=4915202;
 //BA.debugLineNum = 4915202;BA.debugLine="j.Initialize(\"ResetMessages\", Me)";
_j._initialize(processBA,"ResetMessages",main.getObject());
RDebugUtils.currentLine=4915203;
 //BA.debugLineNum = 4915203;BA.debugLine="j.Download2(serverPath & \"/connect/resetmessages_";
_j._download2(parent._serverpath+"/connect/resetmessages_new.php",new String[]{"deviceID",parent._deviceid});
RDebugUtils.currentLine=4915204;
 //BA.debugLineNum = 4915204;BA.debugLine="msjprivadouser = \"None\"";
parent._msjprivadouser = "None";
RDebugUtils.currentLine=4915205;
 //BA.debugLineNum = 4915205;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "main", "resetmessages"), (Object)(_j));
this.state = 17;
return;
case 17:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=4915206;
 //BA.debugLineNum = 4915206;BA.debugLine="If j.Success Then";
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
RDebugUtils.currentLine=4915207;
 //BA.debugLineNum = 4915207;BA.debugLine="Dim ret As String";
_ret = "";
RDebugUtils.currentLine=4915208;
 //BA.debugLineNum = 4915208;BA.debugLine="Dim act As String";
_act = "";
RDebugUtils.currentLine=4915209;
 //BA.debugLineNum = 4915209;BA.debugLine="ret = j.GetString";
_ret = _j._getstring();
RDebugUtils.currentLine=4915210;
 //BA.debugLineNum = 4915210;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=4915211;
 //BA.debugLineNum = 4915211;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
RDebugUtils.currentLine=4915212;
 //BA.debugLineNum = 4915212;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=4915215;
 //BA.debugLineNum = 4915215;BA.debugLine="If j.JobName = \"ResetMessages\" Then";
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
RDebugUtils.currentLine=4915216;
 //BA.debugLineNum = 4915216;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 7:
//if
this.state = 14;
if ((_act).equals("Not Found")) { 
this.state = 9;
}else 
{RDebugUtils.currentLine=4915218;
 //BA.debugLineNum = 4915218;BA.debugLine="Else If act = \"Error reseteando los mensajes, \"";
if ((_act).equals("Error reseteando los mensajes, ")) { 
this.state = 11;
}else 
{RDebugUtils.currentLine=4915220;
 //BA.debugLineNum = 4915220;BA.debugLine="Else If act = \"ResetMessages\" Then";
if ((_act).equals("ResetMessages")) { 
this.state = 13;
}}}
if (true) break;

case 9:
//C
this.state = 14;
RDebugUtils.currentLine=4915217;
 //BA.debugLineNum = 4915217;BA.debugLine="ToastMessageShow(\"Error\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 14;
RDebugUtils.currentLine=4915219;
 //BA.debugLineNum = 4915219;BA.debugLine="ToastMessageShow(\"Login failed\", True)";
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
RDebugUtils.currentLine=4915225;
 //BA.debugLineNum = 4915225;BA.debugLine="j.Release";
_j._release();
RDebugUtils.currentLine=4915227;
 //BA.debugLineNum = 4915227;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _mnuabout_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "mnuabout_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "mnuabout_click", null));}
RDebugUtils.currentLine=4390912;
 //BA.debugLineNum = 4390912;BA.debugLine="Sub mnuAbout_Click";
RDebugUtils.currentLine=4390913;
 //BA.debugLineNum = 4390913;BA.debugLine="StartActivity(frmabout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
RDebugUtils.currentLine=4390914;
 //BA.debugLineNum = 4390914;BA.debugLine="End Sub";
return "";
}
public static void  _testconnection() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "testconnection"))
	 {Debug.delegate(mostCurrent.activityBA, "testconnection", null); return;}
ResumableSub_TestConnection rsub = new ResumableSub_TestConnection(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_TestConnection extends BA.ResumableSub {
public ResumableSub_TestConnection(cepave.geovin.main parent) {
this.parent = parent;
}
cepave.geovin.main parent;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
int _upd = 0;
anywheresoftware.b4a.objects.IntentWrapper _market = null;
String _uri = "";

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="main";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=4259842;
 //BA.debugLineNum = 4259842;BA.debugLine="versionactual = Application.VersionCode";
parent.mostCurrent._versionactual = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Application.getVersionCode());
RDebugUtils.currentLine=4259843;
 //BA.debugLineNum = 4259843;BA.debugLine="lblEstado.Text = \"Comprobando conexión a internet";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Comprobando conexión a internet"));
RDebugUtils.currentLine=4259844;
 //BA.debugLineNum = 4259844;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=4259845;
 //BA.debugLineNum = 4259845;BA.debugLine="j.Initialize(\"Connect\", Me)";
_j._initialize(processBA,"Connect",main.getObject());
RDebugUtils.currentLine=4259846;
 //BA.debugLineNum = 4259846;BA.debugLine="j.Download(serverPath & \"/connect/connecttest.php";
_j._download(parent._serverpath+"/connect/connecttest.php");
RDebugUtils.currentLine=4259847;
 //BA.debugLineNum = 4259847;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "main", "testconnection"), (Object)(_j));
this.state = 27;
return;
case 27:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=4259848;
 //BA.debugLineNum = 4259848;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 26;
if (_j._success) { 
this.state = 3;
}else {
this.state = 25;
}if (true) break;

case 3:
//C
this.state = 4;
RDebugUtils.currentLine=4259849;
 //BA.debugLineNum = 4259849;BA.debugLine="Dim ret As String";
_ret = "";
RDebugUtils.currentLine=4259850;
 //BA.debugLineNum = 4259850;BA.debugLine="Dim act As String";
_act = "";
RDebugUtils.currentLine=4259851;
 //BA.debugLineNum = 4259851;BA.debugLine="ret = j.GetString";
_ret = _j._getstring();
RDebugUtils.currentLine=4259852;
 //BA.debugLineNum = 4259852;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=4259853;
 //BA.debugLineNum = 4259853;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
RDebugUtils.currentLine=4259854;
 //BA.debugLineNum = 4259854;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=4259855;
 //BA.debugLineNum = 4259855;BA.debugLine="If act = \"Connected\" Then";
if (true) break;

case 4:
//if
this.state = 23;
if ((_act).equals("Connected")) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
RDebugUtils.currentLine=4259857;
 //BA.debugLineNum = 4259857;BA.debugLine="lblEstado.Text = \"Conectado. Comprobando versió";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Conectado. Comprobando versión de la aplicación"));
RDebugUtils.currentLine=4259858;
 //BA.debugLineNum = 4259858;BA.debugLine="modooffline = False";
parent._modooffline = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=4259860;
 //BA.debugLineNum = 4259860;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=4259861;
 //BA.debugLineNum = 4259861;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
RDebugUtils.currentLine=4259862;
 //BA.debugLineNum = 4259862;BA.debugLine="serverversion = nd.Get(\"currentversion\")";
parent.mostCurrent._serverversion = BA.ObjectToString(_nd.Get((Object)("currentversion")));
RDebugUtils.currentLine=4259863;
 //BA.debugLineNum = 4259863;BA.debugLine="servernewstitulo = nd.Get(\"newstitulo\")";
parent.mostCurrent._servernewstitulo = BA.ObjectToString(_nd.Get((Object)("newstitulo")));
RDebugUtils.currentLine=4259864;
 //BA.debugLineNum = 4259864;BA.debugLine="servernewstext = nd.Get(\"newstext\")";
parent.mostCurrent._servernewstext = BA.ObjectToString(_nd.Get((Object)("newstext")));
RDebugUtils.currentLine=4259873;
 //BA.debugLineNum = 4259873;BA.debugLine="If serverversion <> versionactual Then";
if (true) break;

case 7:
//if
this.state = 22;
if ((parent.mostCurrent._serverversion).equals(parent.mostCurrent._versionactual) == false) { 
this.state = 9;
}else {
this.state = 17;
}if (true) break;

case 9:
//C
this.state = 10;
RDebugUtils.currentLine=4259874;
 //BA.debugLineNum = 4259874;BA.debugLine="Dim upd As Int";
_upd = 0;
RDebugUtils.currentLine=4259875;
 //BA.debugLineNum = 4259875;BA.debugLine="upd = Msgbox2(\"Para continuar, debe descargar";
_upd = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Para continuar, debe descargar una actualización importante"),BA.ObjectToCharSequence("Actualización"),"Ir a GooglePlay","Lo haré después","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
RDebugUtils.currentLine=4259876;
 //BA.debugLineNum = 4259876;BA.debugLine="If upd = DialogResponse.POSITIVE Then";
if (true) break;

case 10:
//if
this.state = 15;
if (_upd==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 12;
}else {
this.state = 14;
}if (true) break;

case 12:
//C
this.state = 15;
RDebugUtils.currentLine=4259878;
 //BA.debugLineNum = 4259878;BA.debugLine="Dim market As Intent, uri As String";
_market = new anywheresoftware.b4a.objects.IntentWrapper();
_uri = "";
RDebugUtils.currentLine=4259879;
 //BA.debugLineNum = 4259879;BA.debugLine="uri=\"market://details?id=cepave.geovin\"";
_uri = "market://details?id=cepave.geovin";
RDebugUtils.currentLine=4259880;
 //BA.debugLineNum = 4259880;BA.debugLine="market.Initialize(market.ACTION_VIEW,uri)";
_market.Initialize(_market.ACTION_VIEW,_uri);
RDebugUtils.currentLine=4259881;
 //BA.debugLineNum = 4259881;BA.debugLine="StartActivity(market)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_market.getObject()));
RDebugUtils.currentLine=4259883;
 //BA.debugLineNum = 4259883;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=4259884;
 //BA.debugLineNum = 4259884;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 if (true) break;

case 14:
//C
this.state = 15;
RDebugUtils.currentLine=4259887;
 //BA.debugLineNum = 4259887;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=4259888;
 //BA.debugLineNum = 4259888;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 if (true) break;

case 15:
//C
this.state = 22;
;
 if (true) break;

case 17:
//C
this.state = 18;
RDebugUtils.currentLine=4259892;
 //BA.debugLineNum = 4259892;BA.debugLine="If servernewstitulo <> \"\" And servernewstitulo";
if (true) break;

case 18:
//if
this.state = 21;
if ((parent.mostCurrent._servernewstitulo).equals("") == false && (parent.mostCurrent._servernewstitulo).equals("Nada") == false) { 
this.state = 20;
}if (true) break;

case 20:
//C
this.state = 21;
RDebugUtils.currentLine=4259893;
 //BA.debugLineNum = 4259893;BA.debugLine="Msgbox(servernewstext, servernewstitulo)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(parent.mostCurrent._servernewstext),BA.ObjectToCharSequence(parent.mostCurrent._servernewstitulo),mostCurrent.activityBA);
 if (true) break;

case 21:
//C
this.state = 22;
;
RDebugUtils.currentLine=4259897;
 //BA.debugLineNum = 4259897;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=4259898;
 //BA.debugLineNum = 4259898;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 if (true) break;

case 22:
//C
this.state = 23;
;
 if (true) break;

case 23:
//C
this.state = 26;
;
 if (true) break;

case 25:
//C
this.state = 26;
RDebugUtils.currentLine=4259904;
 //BA.debugLineNum = 4259904;BA.debugLine="Msgbox(\"No tienes conexión a Internet. GeoVin in";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes conexión a Internet. GeoVin iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes!"),BA.ObjectToCharSequence("Advertencia"),mostCurrent.activityBA);
RDebugUtils.currentLine=4259905;
 //BA.debugLineNum = 4259905;BA.debugLine="modooffline = True";
parent._modooffline = anywheresoftware.b4a.keywords.Common.True;
RDebugUtils.currentLine=4259906;
 //BA.debugLineNum = 4259906;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=4259908;
 //BA.debugLineNum = 4259908;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=4259909;
 //BA.debugLineNum = 4259909;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 if (true) break;

case 26:
//C
this.state = -1;
;
RDebugUtils.currentLine=4259911;
 //BA.debugLineNum = 4259911;BA.debugLine="j.Release";
_j._release();
RDebugUtils.currentLine=4259912;
 //BA.debugLineNum = 4259912;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _timer1_tick() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "timer1_tick"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "timer1_tick", null));}
RDebugUtils.currentLine=4128768;
 //BA.debugLineNum = 4128768;BA.debugLine="Sub timer1_Tick";
RDebugUtils.currentLine=4128776;
 //BA.debugLineNum = 4128776;BA.debugLine="TestConnection";
_testconnection();
RDebugUtils.currentLine=4128777;
 //BA.debugLineNum = 4128777;BA.debugLine="timer1.Enabled = False";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=4128778;
 //BA.debugLineNum = 4128778;BA.debugLine="End Sub";
return "";
}
}