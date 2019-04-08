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

public class frmdatosanteriores extends Activity implements B4AActivity{
	public static frmdatosanteriores mostCurrent;
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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmdatosanteriores");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmdatosanteriores).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmdatosanteriores");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmdatosanteriores", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmdatosanteriores) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmdatosanteriores) Resume **");
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
		return frmdatosanteriores.class;
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
        BA.LogInfo("** Activity (frmdatosanteriores) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            frmdatosanteriores mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmdatosanteriores) Resume **");
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
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper _hc = null;
public static anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public static String _nuevalatlng = "";
public static boolean _notificacion = false;
public static String _serveridnum = "";
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripdatosanteriores = null;
public static String _tabactual = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblfecha = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltipo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcalidad = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnotas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllng = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkpublico = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkenviado = null;
public anywheresoftware.b4a.objects.LabelWrapper _chkvalidadoexpertos = null;
public anywheresoftware.b4a.objects.LabelWrapper _chkonline = null;
public anywheresoftware.b4a.objects.LabelWrapper _chkenviadobar = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _scvfotos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombreenvio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbarra = null;
public static String _currentdatoid = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _foto1view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto2view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto3view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto4view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _fotogrande = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto1borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto2borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto3borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto4borrar = null;
public static String _fotoadjuntar = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviar = null;
public static String _idproyectoenviar = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btncambiarpublico = null;
public static boolean _newmarcador = false;
public static int _numbertasks = 0;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog _dd = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfullname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllocation = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblemail = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblorg = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfullname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlocation = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluser = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltipousuario = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtorg = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmaprender _frmaprender = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.frmlocalizacion _frmlocalizacion = null;
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
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=6488064;
 //BA.debugLineNum = 6488064;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=6488065;
 //BA.debugLineNum = 6488065;BA.debugLine="Activity.LoadLayout(\"layPerfilDatosAnteriores\")";
mostCurrent._activity.LoadLayout("layPerfilDatosAnteriores",mostCurrent.activityBA);
RDebugUtils.currentLine=6488068;
 //BA.debugLineNum = 6488068;BA.debugLine="tabStripDatosAnteriores.LoadLayout(\"layPerfil\", \"";
mostCurrent._tabstripdatosanteriores.LoadLayout("layPerfil",BA.ObjectToCharSequence("Perfil"));
RDebugUtils.currentLine=6488069;
 //BA.debugLineNum = 6488069;BA.debugLine="tabStripDatosAnteriores.LoadLayout(\"layPerfilDato";
mostCurrent._tabstripdatosanteriores.LoadLayout("layPerfilDatosAnteriores_Lista",BA.ObjectToCharSequence("Datos anteriores"));
RDebugUtils.currentLine=6488070;
 //BA.debugLineNum = 6488070;BA.debugLine="tabStripDatosAnteriores.LoadLayout(\"layPerfilDato";
mostCurrent._tabstripdatosanteriores.LoadLayout("layPerfilDatosAnteriores_Detalle",BA.ObjectToCharSequence("Detalle"));
RDebugUtils.currentLine=6488072;
 //BA.debugLineNum = 6488072;BA.debugLine="lblUser.Text = Main.username";
mostCurrent._lbluser.setText(BA.ObjectToCharSequence(mostCurrent._main._username));
RDebugUtils.currentLine=6488073;
 //BA.debugLineNum = 6488073;BA.debugLine="txtEmail.Text = Main.strUserEmail";
mostCurrent._txtemail.setText(BA.ObjectToCharSequence(mostCurrent._main._struseremail));
RDebugUtils.currentLine=6488074;
 //BA.debugLineNum = 6488074;BA.debugLine="txtFullName.Text = Main.strUserName";
mostCurrent._txtfullname.setText(BA.ObjectToCharSequence(mostCurrent._main._strusername));
RDebugUtils.currentLine=6488075;
 //BA.debugLineNum = 6488075;BA.debugLine="txtLocation.Text = Main.strUserLocation";
mostCurrent._txtlocation.setText(BA.ObjectToCharSequence(mostCurrent._main._struserlocation));
RDebugUtils.currentLine=6488076;
 //BA.debugLineNum = 6488076;BA.debugLine="lblTipoUsuario.Text = Main.strUserTipoUsuario";
mostCurrent._lbltipousuario.setText(BA.ObjectToCharSequence(mostCurrent._main._strusertipousuario));
RDebugUtils.currentLine=6488077;
 //BA.debugLineNum = 6488077;BA.debugLine="txtOrg.Text = Main.strUserOrg";
mostCurrent._txtorg.setText(BA.ObjectToCharSequence(mostCurrent._main._struserorg));
RDebugUtils.currentLine=6488080;
 //BA.debugLineNum = 6488080;BA.debugLine="foto1view.Initialize(\"foto1view\")";
mostCurrent._foto1view.Initialize(mostCurrent.activityBA,"foto1view");
RDebugUtils.currentLine=6488081;
 //BA.debugLineNum = 6488081;BA.debugLine="foto2view.Initialize(\"foto2view\")";
mostCurrent._foto2view.Initialize(mostCurrent.activityBA,"foto2view");
RDebugUtils.currentLine=6488082;
 //BA.debugLineNum = 6488082;BA.debugLine="foto3view.Initialize(\"foto3view\")";
mostCurrent._foto3view.Initialize(mostCurrent.activityBA,"foto3view");
RDebugUtils.currentLine=6488083;
 //BA.debugLineNum = 6488083;BA.debugLine="foto4view.Initialize(\"foto4view\")";
mostCurrent._foto4view.Initialize(mostCurrent.activityBA,"foto4view");
RDebugUtils.currentLine=6488084;
 //BA.debugLineNum = 6488084;BA.debugLine="foto1Borrar.Initialize(\"foto1Borrar\")";
mostCurrent._foto1borrar.Initialize(mostCurrent.activityBA,"foto1Borrar");
RDebugUtils.currentLine=6488085;
 //BA.debugLineNum = 6488085;BA.debugLine="foto2Borrar.Initialize(\"foto2Borrar\")";
mostCurrent._foto2borrar.Initialize(mostCurrent.activityBA,"foto2Borrar");
RDebugUtils.currentLine=6488086;
 //BA.debugLineNum = 6488086;BA.debugLine="foto3Borrar.Initialize(\"foto3Borrar\")";
mostCurrent._foto3borrar.Initialize(mostCurrent.activityBA,"foto3Borrar");
RDebugUtils.currentLine=6488087;
 //BA.debugLineNum = 6488087;BA.debugLine="foto4Borrar.Initialize(\"foto4Borrar\")";
mostCurrent._foto4borrar.Initialize(mostCurrent.activityBA,"foto4Borrar");
RDebugUtils.currentLine=6488089;
 //BA.debugLineNum = 6488089;BA.debugLine="scvFotos.Panel.AddView(foto1view, 0dip, 0dip,scvF";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto1view.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
RDebugUtils.currentLine=6488090;
 //BA.debugLineNum = 6488090;BA.debugLine="scvFotos.Panel.AddView(foto2view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto2view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*1),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
RDebugUtils.currentLine=6488091;
 //BA.debugLineNum = 6488091;BA.debugLine="scvFotos.Panel.AddView(foto3view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto3view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*2),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
RDebugUtils.currentLine=6488092;
 //BA.debugLineNum = 6488092;BA.debugLine="scvFotos.Panel.AddView(foto4view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto4view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*3),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
RDebugUtils.currentLine=6488094;
 //BA.debugLineNum = 6488094;BA.debugLine="If hc.IsInitialized = False Then";
if (_hc.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=6488095;
 //BA.debugLineNum = 6488095;BA.debugLine="hc.Initialize(\"hc\")";
_hc.Initialize("hc");
 };
RDebugUtils.currentLine=6488098;
 //BA.debugLineNum = 6488098;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode}));}
RDebugUtils.currentLine=6684672;
 //BA.debugLineNum = 6684672;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=6684674;
 //BA.debugLineNum = 6684674;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=6684675;
 //BA.debugLineNum = 6684675;BA.debugLine="If tabActual = \"Lista\" Then";
if ((mostCurrent._tabactual).equals("Lista")) { 
RDebugUtils.currentLine=6684676;
 //BA.debugLineNum = 6684676;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=6684677;
 //BA.debugLineNum = 6684677;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else 
{RDebugUtils.currentLine=6684678;
 //BA.debugLineNum = 6684678;BA.debugLine="Else If tabActual = \"Detalle\" Then";
if ((mostCurrent._tabactual).equals("Detalle")) { 
RDebugUtils.currentLine=6684679;
 //BA.debugLineNum = 6684679;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=6684680;
 //BA.debugLineNum = 6684680;BA.debugLine="tabActual = \"Lista\"";
mostCurrent._tabactual = "Lista";
RDebugUtils.currentLine=6684681;
 //BA.debugLineNum = 6684681;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
RDebugUtils.currentLine=6684683;
 //BA.debugLineNum = 6684683;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=6684684;
 //BA.debugLineNum = 6684684;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }}
;
 };
RDebugUtils.currentLine=6684687;
 //BA.debugLineNum = 6684687;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
RDebugUtils.currentLine=6619136;
 //BA.debugLineNum = 6619136;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=6619138;
 //BA.debugLineNum = 6619138;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=6553600;
 //BA.debugLineNum = 6553600;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=6553601;
 //BA.debugLineNum = 6553601;BA.debugLine="If nuevalatlng = \"\" And notificacion = False Then";
if ((_nuevalatlng).equals("") && _notificacion==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=6553602;
 //BA.debugLineNum = 6553602;BA.debugLine="ListarDatos";
_listardatos();
 };
RDebugUtils.currentLine=6553605;
 //BA.debugLineNum = 6553605;BA.debugLine="End Sub";
return "";
}
public static String  _listardatos() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "listardatos"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "listardatos", null));}
anywheresoftware.b4a.objects.collections.List _table = null;
String[] _cols = null;
int _dato = 0;
RDebugUtils.currentLine=6881280;
 //BA.debugLineNum = 6881280;BA.debugLine="Sub ListarDatos";
RDebugUtils.currentLine=6881281;
 //BA.debugLineNum = 6881281;BA.debugLine="ListView1.Clear";
mostCurrent._listview1.Clear();
RDebugUtils.currentLine=6881282;
 //BA.debugLineNum = 6881282;BA.debugLine="ListView1.Color = Colors.White";
mostCurrent._listview1.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=6881283;
 //BA.debugLineNum = 6881283;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.Width = ListVie";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setWidth((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
RDebugUtils.currentLine=6881284;
 //BA.debugLineNum = 6881284;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.Width = L";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setWidth((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
RDebugUtils.currentLine=6881285;
 //BA.debugLineNum = 6881285;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.TextColor = Col";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
RDebugUtils.currentLine=6881286;
 //BA.debugLineNum = 6881286;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.TextColor";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=6881287;
 //BA.debugLineNum = 6881287;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.TextSize = 14";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setTextSize((float) (14));
RDebugUtils.currentLine=6881288;
 //BA.debugLineNum = 6881288;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.TextSize";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setTextSize((float) (12));
RDebugUtils.currentLine=6881289;
 //BA.debugLineNum = 6881289;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.left = 20dip";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
RDebugUtils.currentLine=6881290;
 //BA.debugLineNum = 6881290;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Left = List";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setLeft((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
RDebugUtils.currentLine=6881291;
 //BA.debugLineNum = 6881291;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Gravity = G";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=6881292;
 //BA.debugLineNum = 6881292;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Width = 20d";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
RDebugUtils.currentLine=6881293;
 //BA.debugLineNum = 6881293;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Height = 20";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
RDebugUtils.currentLine=6881294;
 //BA.debugLineNum = 6881294;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.top = (List";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setTop((int) ((mostCurrent._listview1.getTwoLinesAndBitmap().getItemHeight()/(double)2)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
RDebugUtils.currentLine=6881297;
 //BA.debugLineNum = 6881297;BA.debugLine="Dim Table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=6881298;
 //BA.debugLineNum = 6881298;BA.debugLine="Table = DBUtils.ExecuteMemoryTable(Starter.sqlDB,";
_table = mostCurrent._dbutils._executememorytable(mostCurrent.activityBA,mostCurrent._starter._sqldb,"SELECT * FROM markers_local WHERE usuario=?",new String[]{mostCurrent._main._strusername},(int) (0));
RDebugUtils.currentLine=6881299;
 //BA.debugLineNum = 6881299;BA.debugLine="If Table = Null Or Table.IsInitialized = False Th";
if (_table== null || _table.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=6881300;
 //BA.debugLineNum = 6881300;BA.debugLine="Return";
if (true) return "";
RDebugUtils.currentLine=6881301;
 //BA.debugLineNum = 6881301;BA.debugLine="ToastMessageShow(\"No tienes datos anteriores...\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No tienes datos anteriores..."),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=6881302;
 //BA.debugLineNum = 6881302;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=6881303;
 //BA.debugLineNum = 6881303;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
RDebugUtils.currentLine=6881306;
 //BA.debugLineNum = 6881306;BA.debugLine="If Table.Size = 0 Then";
if (_table.getSize()==0) { 
RDebugUtils.currentLine=6881307;
 //BA.debugLineNum = 6881307;BA.debugLine="ToastMessageShow(\"No tienes datos anteriores...";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No tienes datos anteriores..."),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=6881308;
 //BA.debugLineNum = 6881308;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=6881309;
 //BA.debugLineNum = 6881309;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
RDebugUtils.currentLine=6881311;
 //BA.debugLineNum = 6881311;BA.debugLine="Dim Cols() As String";
_cols = new String[(int) (0)];
java.util.Arrays.fill(_cols,"");
RDebugUtils.currentLine=6881312;
 //BA.debugLineNum = 6881312;BA.debugLine="For dato = 0 To Table.Size -1";
{
final int step29 = 1;
final int limit29 = (int) (_table.getSize()-1);
_dato = (int) (0) ;
for (;_dato <= limit29 ;_dato = _dato + step29 ) {
RDebugUtils.currentLine=6881313;
 //BA.debugLineNum = 6881313;BA.debugLine="Cols = Table.Get(dato)";
_cols = (String[])(_table.Get(_dato));
RDebugUtils.currentLine=6881315;
 //BA.debugLineNum = 6881315;BA.debugLine="If Cols(24) <> Null Then";
if (_cols[(int) (24)]!= null) { 
RDebugUtils.currentLine=6881317;
 //BA.debugLineNum = 6881317;BA.debugLine="If Cols(34) = \"validado\" Then";
if ((_cols[(int) (34)]).equals("validado")) { 
RDebugUtils.currentLine=6881318;
 //BA.debugLineNum = 6881318;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(\"Fecha: \" & C";
mostCurrent._listview1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Fecha: "+_cols[(int) (3)]),BA.ObjectToCharSequence(_cols[(int) (4)]+"/"+_cols[(int) (5)]),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()),(Object)(_cols[(int) (0)]));
 }else {
RDebugUtils.currentLine=6881320;
 //BA.debugLineNum = 6881320;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(\"Fecha: \" & C";
mostCurrent._listview1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Fecha: "+_cols[(int) (3)]),BA.ObjectToCharSequence(_cols[(int) (4)]+"/"+_cols[(int) (5)]),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cross.png").getObject()),(Object)(_cols[(int) (0)]));
 };
 };
 }
};
 };
RDebugUtils.currentLine=6881328;
 //BA.debugLineNum = 6881328;BA.debugLine="End Sub";
return "";
}
public static String  _btncambiarfecha_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncambiarfecha_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncambiarfecha_click", null));}
String _msg = "";
String _fechanueva = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=7798784;
 //BA.debugLineNum = 7798784;BA.debugLine="Sub btnCambiarFecha_Click";
RDebugUtils.currentLine=7798785;
 //BA.debugLineNum = 7798785;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=7798786;
 //BA.debugLineNum = 7798786;BA.debugLine="msg = Msgbox2(\"Desea cambiar la fecha de este dat";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Desea cambiar la fecha de este dato?"),BA.ObjectToCharSequence("Cambiar fecha"),"Si","No","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=7798787;
 //BA.debugLineNum = 7798787;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=7798789;
 //BA.debugLineNum = 7798789;BA.debugLine="Dim fechanueva As String";
_fechanueva = "";
RDebugUtils.currentLine=7798790;
 //BA.debugLineNum = 7798790;BA.debugLine="dd.ShowCalendar = True";
mostCurrent._dd.ShowCalendar = anywheresoftware.b4a.keywords.Common.True;
RDebugUtils.currentLine=7798791;
 //BA.debugLineNum = 7798791;BA.debugLine="dd.Year = DateTime.GetYear(DateTime.now)     'se";
mostCurrent._dd.setYear(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
RDebugUtils.currentLine=7798792;
 //BA.debugLineNum = 7798792;BA.debugLine="dd.Month = DateTime.GetMonth(DateTime.now)";
mostCurrent._dd.setMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
RDebugUtils.currentLine=7798793;
 //BA.debugLineNum = 7798793;BA.debugLine="dd.DayOfMonth = DateTime.GetDayOfMonth(DateTime.";
mostCurrent._dd.setDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
RDebugUtils.currentLine=7798794;
 //BA.debugLineNum = 7798794;BA.debugLine="dd.Show(\"Elija la nueva fecha para este dato\", \"";
mostCurrent._dd.Show("Elija la nueva fecha para este dato","Cambio de fecha","Ok","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=7798795;
 //BA.debugLineNum = 7798795;BA.debugLine="fechanueva = dd.DayOfMonth & \"-\" & dd.Month & \"-";
_fechanueva = BA.NumberToString(mostCurrent._dd.getDayOfMonth())+"-"+BA.NumberToString(mostCurrent._dd.getMonth())+"-"+BA.NumberToString(mostCurrent._dd.getYear());
RDebugUtils.currentLine=7798796;
 //BA.debugLineNum = 7798796;BA.debugLine="ToastMessageShow(\"Nueva fecha: \" & fechanueva, F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Nueva fecha: "+_fechanueva),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=7798798;
 //BA.debugLineNum = 7798798;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7798799;
 //BA.debugLineNum = 7798799;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=7798800;
 //BA.debugLineNum = 7798800;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
RDebugUtils.currentLine=7798801;
 //BA.debugLineNum = 7798801;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","georeferencedDate",(Object)(_fechanueva),_map1);
RDebugUtils.currentLine=7798803;
 //BA.debugLineNum = 7798803;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","evalsent",(Object)("no"),_map1);
RDebugUtils.currentLine=7798804;
 //BA.debugLineNum = 7798804;BA.debugLine="lblFecha.Text = fechanueva";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence(_fechanueva));
RDebugUtils.currentLine=7798805;
 //BA.debugLineNum = 7798805;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7798806;
 //BA.debugLineNum = 7798806;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 }else {
RDebugUtils.currentLine=7798809;
 //BA.debugLineNum = 7798809;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=7798811;
 //BA.debugLineNum = 7798811;BA.debugLine="End Sub";
return "";
}
public static String  _btncambiarpublico_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncambiarpublico_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncambiarpublico_click", null));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=7995392;
 //BA.debugLineNum = 7995392;BA.debugLine="Sub btnCambiarPublico_Click";
RDebugUtils.currentLine=7995394;
 //BA.debugLineNum = 7995394;BA.debugLine="If chkPublico.Checked = True Then";
if (mostCurrent._chkpublico.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=7995395;
 //BA.debugLineNum = 7995395;BA.debugLine="chkPublico.Checked = False";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=7995396;
 //BA.debugLineNum = 7995396;BA.debugLine="chkPublico.Text = \"Dato privado\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato privado"));
RDebugUtils.currentLine=7995398;
 //BA.debugLineNum = 7995398;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7995399;
 //BA.debugLineNum = 7995399;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=7995400;
 //BA.debugLineNum = 7995400;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
RDebugUtils.currentLine=7995401;
 //BA.debugLineNum = 7995401;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","privado",(Object)("si"),_map1);
RDebugUtils.currentLine=7995402;
 //BA.debugLineNum = 7995402;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","evalsent",(Object)("no"),_map1);
RDebugUtils.currentLine=7995403;
 //BA.debugLineNum = 7995403;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7995404;
 //BA.debugLineNum = 7995404;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 }else {
RDebugUtils.currentLine=7995406;
 //BA.debugLineNum = 7995406;BA.debugLine="chkPublico.Checked = True";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7995407;
 //BA.debugLineNum = 7995407;BA.debugLine="chkPublico.Text = \"Dato público\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato público"));
RDebugUtils.currentLine=7995408;
 //BA.debugLineNum = 7995408;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7995409;
 //BA.debugLineNum = 7995409;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=7995410;
 //BA.debugLineNum = 7995410;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
RDebugUtils.currentLine=7995411;
 //BA.debugLineNum = 7995411;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","privado",(Object)("no"),_map1);
RDebugUtils.currentLine=7995412;
 //BA.debugLineNum = 7995412;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","evalsent",(Object)("no"),_map1);
RDebugUtils.currentLine=7995413;
 //BA.debugLineNum = 7995413;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7995414;
 //BA.debugLineNum = 7995414;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 };
RDebugUtils.currentLine=7995416;
 //BA.debugLineNum = 7995416;BA.debugLine="End Sub";
return "";
}
public static String  _btncambiarubicacion_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncambiarubicacion_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncambiarubicacion_click", null));}
String _msg = "";
RDebugUtils.currentLine=7864320;
 //BA.debugLineNum = 7864320;BA.debugLine="Sub btnCambiarUbicacion_Click";
RDebugUtils.currentLine=7864321;
 //BA.debugLineNum = 7864321;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=7864322;
 //BA.debugLineNum = 7864322;BA.debugLine="msg = Msgbox2(\"Desea cambiar la ubicación de este";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Desea cambiar la ubicación de este dato?"),BA.ObjectToCharSequence("Cambiar ubicación"),"Si","No","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=7864323;
 //BA.debugLineNum = 7864323;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=7864324;
 //BA.debugLineNum = 7864324;BA.debugLine="nuevalatlng = \"\"";
_nuevalatlng = "";
RDebugUtils.currentLine=7864325;
 //BA.debugLineNum = 7864325;BA.debugLine="frmLocalizacion.origen = \"cambio\"";
mostCurrent._frmlocalizacion._origen = "cambio";
RDebugUtils.currentLine=7864326;
 //BA.debugLineNum = 7864326;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 }else {
RDebugUtils.currentLine=7864329;
 //BA.debugLineNum = 7864329;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=7864331;
 //BA.debugLineNum = 7864331;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncerrar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncerrar_click", null));}
RDebugUtils.currentLine=6815744;
 //BA.debugLineNum = 6815744;BA.debugLine="Sub btnCerrar_Click";
RDebugUtils.currentLine=6815745;
 //BA.debugLineNum = 6815745;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=6815746;
 //BA.debugLineNum = 6815746;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=6815747;
 //BA.debugLineNum = 6815747;BA.debugLine="End Sub";
return "";
}
public static String  _btnenviar_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnenviar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnenviar_click", null));}
RDebugUtils.currentLine=8060928;
 //BA.debugLineNum = 8060928;BA.debugLine="Sub btnEnviar_Click";
RDebugUtils.currentLine=8060930;
 //BA.debugLineNum = 8060930;BA.debugLine="If Main.modooffline = True Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=8060931;
 //BA.debugLineNum = 8060931;BA.debugLine="Msgbox(\"Estas trabajando en modo fuera de línea.";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Estas trabajando en modo fuera de línea. Conectate a internet e inicia sesión para cambiar el estado de los datos"),BA.ObjectToCharSequence("Modo fuera de línea"),mostCurrent.activityBA);
RDebugUtils.currentLine=8060932;
 //BA.debugLineNum = 8060932;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=8060935;
 //BA.debugLineNum = 8060935;BA.debugLine="ProgressDialogShow(\"Enviando datos, espere por fa";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando datos, espere por favor..."));
RDebugUtils.currentLine=8060937;
 //BA.debugLineNum = 8060937;BA.debugLine="If NewMarcador = True Then";
if (_newmarcador==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=8060938;
 //BA.debugLineNum = 8060938;BA.debugLine="EnvioDatos(IdProyectoEnviar, True)";
_enviodatos(mostCurrent._idproyectoenviar,anywheresoftware.b4a.keywords.Common.True);
 }else {
RDebugUtils.currentLine=8060940;
 //BA.debugLineNum = 8060940;BA.debugLine="EnvioDatos(IdProyectoEnviar, False)";
_enviodatos(mostCurrent._idproyectoenviar,anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=8060943;
 //BA.debugLineNum = 8060943;BA.debugLine="End Sub";
return "";
}
public static void  _enviodatos(String _proyectonumero,boolean _newmarker) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "enviodatos"))
	 {Debug.delegate(mostCurrent.activityBA, "enviodatos", new Object[] {_proyectonumero,_newmarker}); return;}
ResumableSub_EnvioDatos rsub = new ResumableSub_EnvioDatos(null,_proyectonumero,_newmarker);
rsub.resume(processBA, null);
}
public static class ResumableSub_EnvioDatos extends BA.ResumableSub {
public ResumableSub_EnvioDatos(cepave.geovin.frmdatosanteriores parent,String _proyectonumero,boolean _newmarker) {
this.parent = parent;
this._proyectonumero = _proyectonumero;
this._newmarker = _newmarker;
}
cepave.geovin.frmdatosanteriores parent;
String _proyectonumero;
boolean _newmarker;
String _username = "";
String _dateandtime = "";
String _tipoeval = "";
String _lat = "";
String _lng = "";
String _gpsdetect = "";
String _wifidetect = "";
String _mapadetect = "";
String _valorind1 = "";
String _valorind2 = "";
String _foto1 = "";
String _foto2 = "";
String _foto3 = "";
String _foto4 = "";
String _terminado = "";
String _privado = "";
String _estadovalidacion = "";
String _serveridupdate = "";
anywheresoftware.b4a.objects.collections.Map _datosmap = null;
int _conf = 0;
anywheresoftware.b4a.samples.httputils2.httpjob _enviomarcadores = null;
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
String _serverid = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
anywheresoftware.b4a.objects.collections.Map _nv = null;
anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest _req = null;
anywheresoftware.b4a.objects.collections.List _files = null;
cepave.geovin.uploadfiles._filedata _fd = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=8126466;
 //BA.debugLineNum = 8126466;BA.debugLine="Dim username,dateandtime,tipoeval,lat,lng,gpsdete";
_username = "";
_dateandtime = "";
_tipoeval = "";
_lat = "";
_lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
RDebugUtils.currentLine=8126467;
 //BA.debugLineNum = 8126467;BA.debugLine="Dim valorind1, valorind2, foto1, foto2, foto3, fo";
_valorind1 = "";
_valorind2 = "";
_foto1 = "";
_foto2 = "";
_foto3 = "";
_foto4 = "";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_serveridupdate = "";
RDebugUtils.currentLine=8126471;
 //BA.debugLineNum = 8126471;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=8126472;
 //BA.debugLineNum = 8126472;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
RDebugUtils.currentLine=8126473;
 //BA.debugLineNum = 8126473;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datosmap = parent.mostCurrent._dbutils._executemap(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"SELECT * FROM markers_local WHERE Id=?",new String[]{_proyectonumero});
RDebugUtils.currentLine=8126475;
 //BA.debugLineNum = 8126475;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
if (true) break;

case 1:
//if
this.state = 18;
if (_datosmap== null || _datosmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 18;
RDebugUtils.currentLine=8126476;
 //BA.debugLineNum = 8126476;BA.debugLine="ToastMessageShow(\"Error cargando el análisis\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisis"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=8126477;
 //BA.debugLineNum = 8126477;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=8126478;
 //BA.debugLineNum = 8126478;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 5:
//C
this.state = 6;
RDebugUtils.currentLine=8126480;
 //BA.debugLineNum = 8126480;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
RDebugUtils.currentLine=8126481;
 //BA.debugLineNum = 8126481;BA.debugLine="dateandtime = datosMap.Get(\"georeferencedDate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferencedDate")));
RDebugUtils.currentLine=8126482;
 //BA.debugLineNum = 8126482;BA.debugLine="tipoeval = datosMap.Get(\"tipoeval\")";
_tipoeval = BA.ObjectToString(_datosmap.Get((Object)("tipoeval")));
RDebugUtils.currentLine=8126483;
 //BA.debugLineNum = 8126483;BA.debugLine="lat = datosMap.Get(\"decimalLatitude\")";
_lat = BA.ObjectToString(_datosmap.Get((Object)("decimalLatitude")));
RDebugUtils.currentLine=8126484;
 //BA.debugLineNum = 8126484;BA.debugLine="lng = datosMap.Get(\"decimalLongitude\")";
_lng = BA.ObjectToString(_datosmap.Get((Object)("decimalLongitude")));
RDebugUtils.currentLine=8126485;
 //BA.debugLineNum = 8126485;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
RDebugUtils.currentLine=8126486;
 //BA.debugLineNum = 8126486;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
RDebugUtils.currentLine=8126487;
 //BA.debugLineNum = 8126487;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
RDebugUtils.currentLine=8126488;
 //BA.debugLineNum = 8126488;BA.debugLine="valorind1 = datosMap.Get(\"valorOrganismo\")";
_valorind1 = BA.ObjectToString(_datosmap.Get((Object)("valorOrganismo")));
RDebugUtils.currentLine=8126489;
 //BA.debugLineNum = 8126489;BA.debugLine="valorind2 = datosMap.Get(\"valorAmbiente\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("valorAmbiente")));
RDebugUtils.currentLine=8126490;
 //BA.debugLineNum = 8126490;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
_foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
RDebugUtils.currentLine=8126491;
 //BA.debugLineNum = 8126491;BA.debugLine="foto2 = datosMap.Get(\"foto2\")";
_foto2 = BA.ObjectToString(_datosmap.Get((Object)("foto2")));
RDebugUtils.currentLine=8126492;
 //BA.debugLineNum = 8126492;BA.debugLine="foto3 = datosMap.Get(\"foto3\")";
_foto3 = BA.ObjectToString(_datosmap.Get((Object)("foto3")));
RDebugUtils.currentLine=8126493;
 //BA.debugLineNum = 8126493;BA.debugLine="foto4 = datosMap.Get(\"foto4\")";
_foto4 = BA.ObjectToString(_datosmap.Get((Object)("foto4")));
RDebugUtils.currentLine=8126494;
 //BA.debugLineNum = 8126494;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
RDebugUtils.currentLine=8126495;
 //BA.debugLineNum = 8126495;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
RDebugUtils.currentLine=8126496;
 //BA.debugLineNum = 8126496;BA.debugLine="estadovalidacion = datosMap.Get(\"estadoValidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadoValidacion")));
RDebugUtils.currentLine=8126497;
 //BA.debugLineNum = 8126497;BA.debugLine="If privado = Null Or privado = \"null\" Or privado";
if (true) break;

case 6:
//if
this.state = 9;
if (_privado== null || (_privado).equals("null") || (_privado).equals("")) { 
this.state = 8;
}if (true) break;

case 8:
//C
this.state = 9;
RDebugUtils.currentLine=8126498;
 //BA.debugLineNum = 8126498;BA.debugLine="privado = \"no\"";
_privado = "no";
 if (true) break;

case 9:
//C
this.state = 10;
;
RDebugUtils.currentLine=8126500;
 //BA.debugLineNum = 8126500;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
RDebugUtils.currentLine=8126501;
 //BA.debugLineNum = 8126501;BA.debugLine="If estadovalidacion = \"null\" Then";
if (true) break;

case 10:
//if
this.state = 13;
if ((_estadovalidacion).equals("null")) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
RDebugUtils.currentLine=8126502;
 //BA.debugLineNum = 8126502;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 if (true) break;

case 13:
//C
this.state = 14;
;
RDebugUtils.currentLine=8126504;
 //BA.debugLineNum = 8126504;BA.debugLine="serverIdupdate = datosMap.Get(\"serverid\")";
_serveridupdate = BA.ObjectToString(_datosmap.Get((Object)("serverid")));
RDebugUtils.currentLine=8126505;
 //BA.debugLineNum = 8126505;BA.debugLine="If NewMarker = False And serverIdupdate = Null T";
if (true) break;

case 14:
//if
this.state = 17;
if (_newmarker==anywheresoftware.b4a.keywords.Common.False && _serveridupdate== null) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
RDebugUtils.currentLine=8126506;
 //BA.debugLineNum = 8126506;BA.debugLine="ToastMessageShow(\"No se puede actualizar el dat";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se puede actualizar el dato"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=8126507;
 //BA.debugLineNum = 8126507;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 17:
//C
this.state = 18;
;
 if (true) break;
;
RDebugUtils.currentLine=8126512;
 //BA.debugLineNum = 8126512;BA.debugLine="If terminado <> \"si\" Then";

case 18:
//if
this.state = 27;
if ((_terminado).equals("si") == false) { 
this.state = 20;
}if (true) break;

case 20:
//C
this.state = 21;
RDebugUtils.currentLine=8126513;
 //BA.debugLineNum = 8126513;BA.debugLine="Dim conf As Int";
_conf = 0;
RDebugUtils.currentLine=8126515;
 //BA.debugLineNum = 8126515;BA.debugLine="Select conf";
if (true) break;

case 21:
//select
this.state = 26;
switch (BA.switchObjectToInt(_conf,anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE,anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
this.state = 23;
if (true) break;
}
case 1: {
this.state = 25;
if (true) break;
}
}
if (true) break;

case 23:
//C
this.state = 26;
RDebugUtils.currentLine=8126517;
 //BA.debugLineNum = 8126517;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 25:
//C
this.state = 26;
RDebugUtils.currentLine=8126519;
 //BA.debugLineNum = 8126519;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 26:
//C
this.state = 27;
;
 if (true) break;

case 27:
//C
this.state = 28;
;
RDebugUtils.currentLine=8126525;
 //BA.debugLineNum = 8126525;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.Log("Comienza envio de datos");
RDebugUtils.currentLine=8126526;
 //BA.debugLineNum = 8126526;BA.debugLine="Dim enviomarcadores As HttpJob";
_enviomarcadores = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=8126527;
 //BA.debugLineNum = 8126527;BA.debugLine="enviomarcadores.Initialize(\"EnvioMarcador\", Me)";
_enviomarcadores._initialize(processBA,"EnvioMarcador",frmdatosanteriores.getObject());
RDebugUtils.currentLine=8126528;
 //BA.debugLineNum = 8126528;BA.debugLine="enviomarcadores.Download2(Main.serverPath & \"/con";
_enviomarcadores._download2(parent.mostCurrent._main._serverpath+"/connect/addpuntomapa.php",new String[]{"username",_username,"deviceID",parent.mostCurrent._main._deviceid,"dateandtime",_dateandtime,"lat",_lat,"lng",_lng,"valorVinchuca",_valorind1,"foto1path",_foto1,"foto2path",_foto2,"foto3path",_foto3,"foto4path",_foto4,"privado",_privado,"gpsdetect",_gpsdetect,"wifidetect",_wifidetect,"mapadetect",_mapadetect,"terminado",_terminado,"verificado","No Verificado"});
RDebugUtils.currentLine=8126534;
 //BA.debugLineNum = 8126534;BA.debugLine="Wait For (enviomarcadores) JobDone(enviomarcadore";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "frmdatosanteriores", "enviodatos"), (Object)(_enviomarcadores));
this.state = 84;
return;
case 84:
//C
this.state = 28;
_enviomarcadores = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=8126535;
 //BA.debugLineNum = 8126535;BA.debugLine="If enviomarcadores.Success Then";
if (true) break;

case 28:
//if
this.state = 39;
if (_enviomarcadores._success) { 
this.state = 30;
}else {
this.state = 38;
}if (true) break;

case 30:
//C
this.state = 31;
RDebugUtils.currentLine=8126536;
 //BA.debugLineNum = 8126536;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
RDebugUtils.currentLine=8126537;
 //BA.debugLineNum = 8126537;BA.debugLine="res = enviomarcadores.GetString";
_res = _enviomarcadores._getstring();
RDebugUtils.currentLine=8126538;
 //BA.debugLineNum = 8126538;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=8126539;
 //BA.debugLineNum = 8126539;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
RDebugUtils.currentLine=8126540;
 //BA.debugLineNum = 8126540;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=8126541;
 //BA.debugLineNum = 8126541;BA.debugLine="If action = \"Not Found\" Then";
if (true) break;

case 31:
//if
this.state = 36;
if ((_action).equals("Not Found")) { 
this.state = 33;
}else 
{RDebugUtils.currentLine=8126545;
 //BA.debugLineNum = 8126545;BA.debugLine="Else If action = \"Marcadores\" Or action = \"Marca";
if ((_action).equals("Marcadores") || (_action).equals("MarcadorActualizado")) { 
this.state = 35;
}}
if (true) break;

case 33:
//C
this.state = 36;
RDebugUtils.currentLine=8126544;
 //BA.debugLineNum = 8126544;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 35:
//C
this.state = 36;
RDebugUtils.currentLine=8126546;
 //BA.debugLineNum = 8126546;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=8126547;
 //BA.debugLineNum = 8126547;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
RDebugUtils.currentLine=8126548;
 //BA.debugLineNum = 8126548;BA.debugLine="Dim serverID As String";
_serverid = "";
RDebugUtils.currentLine=8126549;
 //BA.debugLineNum = 8126549;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
RDebugUtils.currentLine=8126552;
 //BA.debugLineNum = 8126552;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=8126553;
 //BA.debugLineNum = 8126553;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=8126554;
 //BA.debugLineNum = 8126554;BA.debugLine="Map1.Put(\"Id\", proyectonumero)";
_map1.Put((Object)("Id"),(Object)(_proyectonumero));
RDebugUtils.currentLine=8126555;
 //BA.debugLineNum = 8126555;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"markers_local","evalsent",(Object)("si"),_map1);
RDebugUtils.currentLine=8126556;
 //BA.debugLineNum = 8126556;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
parent.mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,parent.mostCurrent._starter._sqldb,"markers_local","serverId",(Object)(_serverid),_map1);
 if (true) break;

case 36:
//C
this.state = 39;
;
 if (true) break;

case 38:
//C
this.state = 39;
RDebugUtils.currentLine=8126559;
 //BA.debugLineNum = 8126559;BA.debugLine="ToastMessageShow(\"Error: \" & enviomarcadores.Err";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: "+_enviomarcadores._errormessage),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 39:
//C
this.state = 40;
;
RDebugUtils.currentLine=8126561;
 //BA.debugLineNum = 8126561;BA.debugLine="enviomarcadores.Release";
_enviomarcadores._release();
RDebugUtils.currentLine=8126565;
 //BA.debugLineNum = 8126565;BA.debugLine="Dim NV As Map";
_nv = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=8126566;
 //BA.debugLineNum = 8126566;BA.debugLine="NV.Initialize";
_nv.Initialize();
RDebugUtils.currentLine=8126567;
 //BA.debugLineNum = 8126567;BA.debugLine="NV.Put(\"usuario\", Main.username)";
_nv.Put((Object)("usuario"),(Object)(parent.mostCurrent._main._username));
RDebugUtils.currentLine=8126568;
 //BA.debugLineNum = 8126568;BA.debugLine="NV.Put(\"eval\", proyectonumero)";
_nv.Put((Object)("eval"),(Object)(_proyectonumero));
RDebugUtils.currentLine=8126569;
 //BA.debugLineNum = 8126569;BA.debugLine="NV.Put(\"action\", \"upload\")";
_nv.Put((Object)("action"),(Object)("upload"));
RDebugUtils.currentLine=8126570;
 //BA.debugLineNum = 8126570;BA.debugLine="NV.Put(\"usr\", \"juacochero\")";
_nv.Put((Object)("usr"),(Object)("juacochero"));
RDebugUtils.currentLine=8126571;
 //BA.debugLineNum = 8126571;BA.debugLine="NV.Put(\"pss\", \"vacagorda\")";
_nv.Put((Object)("pss"),(Object)("vacagorda"));
RDebugUtils.currentLine=8126574;
 //BA.debugLineNum = 8126574;BA.debugLine="Dim req As OkHttpRequest";
_req = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
RDebugUtils.currentLine=8126576;
 //BA.debugLineNum = 8126576;BA.debugLine="Dim files As List";
_files = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=8126577;
 //BA.debugLineNum = 8126577;BA.debugLine="files.Initialize";
_files.Initialize();
RDebugUtils.currentLine=8126578;
 //BA.debugLineNum = 8126578;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=8126579;
 //BA.debugLineNum = 8126579;BA.debugLine="If foto1 <> \"\" And foto1 <> Null Then";
if (true) break;

case 40:
//if
this.state = 47;
if ((_foto1).equals("") == false && _foto1!= null) { 
this.state = 42;
}if (true) break;

case 42:
//C
this.state = 43;
RDebugUtils.currentLine=8126580;
 //BA.debugLineNum = 8126580;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (true) break;

case 43:
//if
this.state = 46;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1+".jpg")) { 
this.state = 45;
}if (true) break;

case 45:
//C
this.state = 46;
RDebugUtils.currentLine=8126581;
 //BA.debugLineNum = 8126581;BA.debugLine="Log(\"Agrego foto 1 a la lista\")";
anywheresoftware.b4a.keywords.Common.Log("Agrego foto 1 a la lista");
RDebugUtils.currentLine=8126582;
 //BA.debugLineNum = 8126582;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=8126583;
 //BA.debugLineNum = 8126583;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=8126584;
 //BA.debugLineNum = 8126584;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=8126585;
 //BA.debugLineNum = 8126585;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=8126586;
 //BA.debugLineNum = 8126586;BA.debugLine="fd.Dir = File.DirRootExternal & \"/GeoVin/\"";
_fd.Dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/";
RDebugUtils.currentLine=8126587;
 //BA.debugLineNum = 8126587;BA.debugLine="fd.FileName = foto1 & \".jpg\"";
_fd.FileName = _foto1+".jpg";
RDebugUtils.currentLine=8126588;
 //BA.debugLineNum = 8126588;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=8126589;
 //BA.debugLineNum = 8126589;BA.debugLine="numberTasks = numberTasks + 1";
parent._numbertasks = (int) (parent._numbertasks+1);
 if (true) break;

case 46:
//C
this.state = 47;
;
 if (true) break;
;
RDebugUtils.currentLine=8126592;
 //BA.debugLineNum = 8126592;BA.debugLine="If files.Size >= 1 Then";

case 47:
//if
this.state = 50;
if (_files.getSize()>=1) { 
this.state = 49;
}if (true) break;

case 49:
//C
this.state = 50;
RDebugUtils.currentLine=8126593;
 //BA.debugLineNum = 8126593;BA.debugLine="ProgressDialogShow(\"Enviando foto #1... aguarde";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando foto #1... aguarde por favor"));
RDebugUtils.currentLine=8126595;
 //BA.debugLineNum = 8126595;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = parent.mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,parent.mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=8126596;
 //BA.debugLineNum = 8126596;BA.debugLine="Log(\"Hc execute foto 1\")";
anywheresoftware.b4a.keywords.Common.Log("Hc execute foto 1");
RDebugUtils.currentLine=8126597;
 //BA.debugLineNum = 8126597;BA.debugLine="hc.Execute(req, 1)";
parent._hc.Execute(processBA,_req,(int) (1));
 if (true) break;

case 50:
//C
this.state = 51;
;
RDebugUtils.currentLine=8126600;
 //BA.debugLineNum = 8126600;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=8126601;
 //BA.debugLineNum = 8126601;BA.debugLine="If foto2 <> \"\" And foto2 <> Null Then";
if (true) break;

case 51:
//if
this.state = 58;
if ((_foto2).equals("") == false && _foto2!= null) { 
this.state = 53;
}if (true) break;

case 53:
//C
this.state = 54;
RDebugUtils.currentLine=8126602;
 //BA.debugLineNum = 8126602;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (true) break;

case 54:
//if
this.state = 57;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2+".jpg")) { 
this.state = 56;
}if (true) break;

case 56:
//C
this.state = 57;
RDebugUtils.currentLine=8126603;
 //BA.debugLineNum = 8126603;BA.debugLine="Log(\"Agrego foto 2 a la lista\")";
anywheresoftware.b4a.keywords.Common.Log("Agrego foto 2 a la lista");
RDebugUtils.currentLine=8126604;
 //BA.debugLineNum = 8126604;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=8126605;
 //BA.debugLineNum = 8126605;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=8126606;
 //BA.debugLineNum = 8126606;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=8126607;
 //BA.debugLineNum = 8126607;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=8126608;
 //BA.debugLineNum = 8126608;BA.debugLine="fd.Dir = File.DirRootExternal & \"/GeoVin/\"";
_fd.Dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/";
RDebugUtils.currentLine=8126609;
 //BA.debugLineNum = 8126609;BA.debugLine="fd.FileName = foto2 & \".jpg\"";
_fd.FileName = _foto2+".jpg";
RDebugUtils.currentLine=8126610;
 //BA.debugLineNum = 8126610;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=8126611;
 //BA.debugLineNum = 8126611;BA.debugLine="numberTasks = numberTasks + 1";
parent._numbertasks = (int) (parent._numbertasks+1);
 if (true) break;

case 57:
//C
this.state = 58;
;
 if (true) break;
;
RDebugUtils.currentLine=8126615;
 //BA.debugLineNum = 8126615;BA.debugLine="If files.Size >= 1 Then";

case 58:
//if
this.state = 61;
if (_files.getSize()>=1) { 
this.state = 60;
}if (true) break;

case 60:
//C
this.state = 61;
RDebugUtils.currentLine=8126616;
 //BA.debugLineNum = 8126616;BA.debugLine="ProgressDialogShow(\"Enviando foto #2... aguarde";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando foto #2... aguarde por favor"));
RDebugUtils.currentLine=8126617;
 //BA.debugLineNum = 8126617;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = parent.mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,parent.mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=8126618;
 //BA.debugLineNum = 8126618;BA.debugLine="Log(\"Hc execute foto 2\")";
anywheresoftware.b4a.keywords.Common.Log("Hc execute foto 2");
RDebugUtils.currentLine=8126619;
 //BA.debugLineNum = 8126619;BA.debugLine="hc.Execute(req, 2)";
parent._hc.Execute(processBA,_req,(int) (2));
 if (true) break;

case 61:
//C
this.state = 62;
;
RDebugUtils.currentLine=8126623;
 //BA.debugLineNum = 8126623;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=8126624;
 //BA.debugLineNum = 8126624;BA.debugLine="If foto3 <> \"\" And foto3 <> Null Then";
if (true) break;

case 62:
//if
this.state = 69;
if ((_foto3).equals("") == false && _foto3!= null) { 
this.state = 64;
}if (true) break;

case 64:
//C
this.state = 65;
RDebugUtils.currentLine=8126625;
 //BA.debugLineNum = 8126625;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (true) break;

case 65:
//if
this.state = 68;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3+".jpg")) { 
this.state = 67;
}if (true) break;

case 67:
//C
this.state = 68;
RDebugUtils.currentLine=8126626;
 //BA.debugLineNum = 8126626;BA.debugLine="Log(\"Agrego foto 3 a la lista\")";
anywheresoftware.b4a.keywords.Common.Log("Agrego foto 3 a la lista");
RDebugUtils.currentLine=8126627;
 //BA.debugLineNum = 8126627;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=8126628;
 //BA.debugLineNum = 8126628;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=8126629;
 //BA.debugLineNum = 8126629;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=8126630;
 //BA.debugLineNum = 8126630;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=8126631;
 //BA.debugLineNum = 8126631;BA.debugLine="fd.Dir = File.DirRootExternal & \"/GeoVin/\"";
_fd.Dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/";
RDebugUtils.currentLine=8126632;
 //BA.debugLineNum = 8126632;BA.debugLine="fd.FileName = foto3 & \".jpg\"";
_fd.FileName = _foto3+".jpg";
RDebugUtils.currentLine=8126633;
 //BA.debugLineNum = 8126633;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=8126634;
 //BA.debugLineNum = 8126634;BA.debugLine="numberTasks = numberTasks + 1";
parent._numbertasks = (int) (parent._numbertasks+1);
 if (true) break;

case 68:
//C
this.state = 69;
;
 if (true) break;
;
RDebugUtils.currentLine=8126638;
 //BA.debugLineNum = 8126638;BA.debugLine="If files.Size >= 1 Then";

case 69:
//if
this.state = 72;
if (_files.getSize()>=1) { 
this.state = 71;
}if (true) break;

case 71:
//C
this.state = 72;
RDebugUtils.currentLine=8126639;
 //BA.debugLineNum = 8126639;BA.debugLine="ProgressDialogShow(\"Enviando foto #3... aguarde";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando foto #3... aguarde por favor"));
RDebugUtils.currentLine=8126640;
 //BA.debugLineNum = 8126640;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = parent.mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,parent.mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=8126641;
 //BA.debugLineNum = 8126641;BA.debugLine="Log(\"Hc execute  foto 3\")";
anywheresoftware.b4a.keywords.Common.Log("Hc execute  foto 3");
RDebugUtils.currentLine=8126642;
 //BA.debugLineNum = 8126642;BA.debugLine="hc.Execute(req, 3)";
parent._hc.Execute(processBA,_req,(int) (3));
 if (true) break;

case 72:
//C
this.state = 73;
;
RDebugUtils.currentLine=8126645;
 //BA.debugLineNum = 8126645;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=8126646;
 //BA.debugLineNum = 8126646;BA.debugLine="If foto4 <> \"\" And foto4 <> Null Then";
if (true) break;

case 73:
//if
this.state = 80;
if ((_foto4).equals("") == false && _foto4!= null) { 
this.state = 75;
}if (true) break;

case 75:
//C
this.state = 76;
RDebugUtils.currentLine=8126647;
 //BA.debugLineNum = 8126647;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\"";
if (true) break;

case 76:
//if
this.state = 79;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4+".jpg")) { 
this.state = 78;
}if (true) break;

case 78:
//C
this.state = 79;
RDebugUtils.currentLine=8126648;
 //BA.debugLineNum = 8126648;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=8126649;
 //BA.debugLineNum = 8126649;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=8126650;
 //BA.debugLineNum = 8126650;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=8126651;
 //BA.debugLineNum = 8126651;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=8126652;
 //BA.debugLineNum = 8126652;BA.debugLine="fd.Dir = File.DirRootExternal & \"/GeoVin/\"";
_fd.Dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/";
RDebugUtils.currentLine=8126653;
 //BA.debugLineNum = 8126653;BA.debugLine="Log(\"Agrego foto 4 a la lista\")";
anywheresoftware.b4a.keywords.Common.Log("Agrego foto 4 a la lista");
RDebugUtils.currentLine=8126654;
 //BA.debugLineNum = 8126654;BA.debugLine="fd.FileName = foto4 & \".jpg\"";
_fd.FileName = _foto4+".jpg";
RDebugUtils.currentLine=8126655;
 //BA.debugLineNum = 8126655;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=8126656;
 //BA.debugLineNum = 8126656;BA.debugLine="numberTasks = numberTasks + 1";
parent._numbertasks = (int) (parent._numbertasks+1);
 if (true) break;

case 79:
//C
this.state = 80;
;
 if (true) break;
;
RDebugUtils.currentLine=8126659;
 //BA.debugLineNum = 8126659;BA.debugLine="If files.Size >= 1 Then";

case 80:
//if
this.state = 83;
if (_files.getSize()>=1) { 
this.state = 82;
}if (true) break;

case 82:
//C
this.state = 83;
RDebugUtils.currentLine=8126660;
 //BA.debugLineNum = 8126660;BA.debugLine="ProgressDialogShow(\"Enviando foto #4... aguarde";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando foto #4... aguarde por favor"));
RDebugUtils.currentLine=8126661;
 //BA.debugLineNum = 8126661;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = parent.mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,parent.mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=8126662;
 //BA.debugLineNum = 8126662;BA.debugLine="Log(\"Hc execute foto 4\")";
anywheresoftware.b4a.keywords.Common.Log("Hc execute foto 4");
RDebugUtils.currentLine=8126663;
 //BA.debugLineNum = 8126663;BA.debugLine="hc.Execute(req, 4)";
parent._hc.Execute(processBA,_req,(int) (4));
 if (true) break;

case 83:
//C
this.state = -1;
;
RDebugUtils.currentLine=8126667;
 //BA.debugLineNum = 8126667;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _cambiarubicacion() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cambiarubicacion"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cambiarubicacion", null));}
String _newlat = "";
String _newlng = "";
String[] _arr = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=7929856;
 //BA.debugLineNum = 7929856;BA.debugLine="Sub CambiarUbicacion";
RDebugUtils.currentLine=7929857;
 //BA.debugLineNum = 7929857;BA.debugLine="Dim newlat As String";
_newlat = "";
RDebugUtils.currentLine=7929858;
 //BA.debugLineNum = 7929858;BA.debugLine="Dim newlng As String";
_newlng = "";
RDebugUtils.currentLine=7929860;
 //BA.debugLineNum = 7929860;BA.debugLine="If nuevalatlng = \"\" Then";
if ((_nuevalatlng).equals("")) { 
RDebugUtils.currentLine=7929861;
 //BA.debugLineNum = 7929861;BA.debugLine="Return";
if (true) return "";
 }else {
RDebugUtils.currentLine=7929863;
 //BA.debugLineNum = 7929863;BA.debugLine="Dim arr() As String = Regex.Split(\"_\", nuevalatl";
_arr = anywheresoftware.b4a.keywords.Common.Regex.Split("_",_nuevalatlng);
RDebugUtils.currentLine=7929864;
 //BA.debugLineNum = 7929864;BA.debugLine="newlat = arr(0)";
_newlat = _arr[(int) (0)];
RDebugUtils.currentLine=7929865;
 //BA.debugLineNum = 7929865;BA.debugLine="newlng = arr(1)";
_newlng = _arr[(int) (1)];
 };
RDebugUtils.currentLine=7929869;
 //BA.debugLineNum = 7929869;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7929870;
 //BA.debugLineNum = 7929870;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=7929871;
 //BA.debugLineNum = 7929871;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
RDebugUtils.currentLine=7929872;
 //BA.debugLineNum = 7929872;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","decimalLatitude",(Object)(_newlat),_map1);
RDebugUtils.currentLine=7929873;
 //BA.debugLineNum = 7929873;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","decimalLongitude",(Object)(_newlng),_map1);
RDebugUtils.currentLine=7929874;
 //BA.debugLineNum = 7929874;BA.debugLine="lblLat.text = newlat";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_newlat));
RDebugUtils.currentLine=7929875;
 //BA.debugLineNum = 7929875;BA.debugLine="lblLng.text = newlng";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence(_newlng));
RDebugUtils.currentLine=7929876;
 //BA.debugLineNum = 7929876;BA.debugLine="nuevalatlng = \"\"";
_nuevalatlng = "";
RDebugUtils.currentLine=7929878;
 //BA.debugLineNum = 7929878;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","evalsent",(Object)("no"),_map1);
RDebugUtils.currentLine=7929879;
 //BA.debugLineNum = 7929879;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7929880;
 //BA.debugLineNum = 7929880;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=7929881;
 //BA.debugLineNum = 7929881;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cc_result"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cc_result", new Object[] {_success,_dir,_filename}));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _fotonombredestino = "";
RDebugUtils.currentLine=7733248;
 //BA.debugLineNum = 7733248;BA.debugLine="Sub CC_Result (Success As Boolean, Dir As String,";
RDebugUtils.currentLine=7733249;
 //BA.debugLineNum = 7733249;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=7733252;
 //BA.debugLineNum = 7733252;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7733253;
 //BA.debugLineNum = 7733253;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=7733254;
 //BA.debugLineNum = 7733254;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=7733255;
 //BA.debugLineNum = 7733255;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
RDebugUtils.currentLine=7733256;
 //BA.debugLineNum = 7733256;BA.debugLine="Dim fotoNombreDestino As String";
_fotonombredestino = "";
RDebugUtils.currentLine=7733258;
 //BA.debugLineNum = 7733258;BA.debugLine="If fotoAdjuntar = \"foto1\" Then";
if ((mostCurrent._fotoadjuntar).equals("foto1")) { 
RDebugUtils.currentLine=7733259;
 //BA.debugLineNum = 7733259;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentp";
_fotonombredestino = mostCurrent._frmprincipal._fullidcurrentproject+"_1";
RDebugUtils.currentLine=7733260;
 //BA.debugLineNum = 7733260;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_fotonombredestino+".jpg");
RDebugUtils.currentLine=7733261;
 //BA.debugLineNum = 7733261;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto1",(Object)(_fotonombredestino),_map1);
RDebugUtils.currentLine=7733262;
 //BA.debugLineNum = 7733262;BA.debugLine="foto1view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else 
{RDebugUtils.currentLine=7733264;
 //BA.debugLineNum = 7733264;BA.debugLine="Else If fotoAdjuntar = \"foto2\" Then";
if ((mostCurrent._fotoadjuntar).equals("foto2")) { 
RDebugUtils.currentLine=7733265;
 //BA.debugLineNum = 7733265;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentp";
_fotonombredestino = mostCurrent._frmprincipal._fullidcurrentproject+"_2";
RDebugUtils.currentLine=7733266;
 //BA.debugLineNum = 7733266;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_fotonombredestino+".jpg");
RDebugUtils.currentLine=7733267;
 //BA.debugLineNum = 7733267;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto2",(Object)(_fotonombredestino),_map1);
RDebugUtils.currentLine=7733268;
 //BA.debugLineNum = 7733268;BA.debugLine="foto2view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else 
{RDebugUtils.currentLine=7733269;
 //BA.debugLineNum = 7733269;BA.debugLine="Else If fotoAdjuntar = \"foto3\" Then";
if ((mostCurrent._fotoadjuntar).equals("foto3")) { 
RDebugUtils.currentLine=7733270;
 //BA.debugLineNum = 7733270;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentp";
_fotonombredestino = mostCurrent._frmprincipal._fullidcurrentproject+"_3";
RDebugUtils.currentLine=7733271;
 //BA.debugLineNum = 7733271;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_fotonombredestino+".jpg");
RDebugUtils.currentLine=7733272;
 //BA.debugLineNum = 7733272;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto3",(Object)(_fotonombredestino),_map1);
RDebugUtils.currentLine=7733273;
 //BA.debugLineNum = 7733273;BA.debugLine="foto3view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else 
{RDebugUtils.currentLine=7733274;
 //BA.debugLineNum = 7733274;BA.debugLine="Else If fotoAdjuntar = \"foto4\"  Then";
if ((mostCurrent._fotoadjuntar).equals("foto4")) { 
RDebugUtils.currentLine=7733275;
 //BA.debugLineNum = 7733275;BA.debugLine="fotoNombreDestino = frmprincipal.fullidcurrentp";
_fotonombredestino = mostCurrent._frmprincipal._fullidcurrentproject+"_4";
RDebugUtils.currentLine=7733276;
 //BA.debugLineNum = 7733276;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_fotonombredestino+".jpg");
RDebugUtils.currentLine=7733277;
 //BA.debugLineNum = 7733277;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto4",(Object)(_fotonombredestino),_map1);
RDebugUtils.currentLine=7733278;
 //BA.debugLineNum = 7733278;BA.debugLine="foto4view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }}}}
;
 };
RDebugUtils.currentLine=7733282;
 //BA.debugLineNum = 7733282;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fondogris_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fondogris_click", null));}
RDebugUtils.currentLine=7340032;
 //BA.debugLineNum = 7340032;BA.debugLine="Sub fondogris_Click";
RDebugUtils.currentLine=7340033;
 //BA.debugLineNum = 7340033;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
RDebugUtils.currentLine=7340034;
 //BA.debugLineNum = 7340034;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
RDebugUtils.currentLine=7340035;
 //BA.debugLineNum = 7340035;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
RDebugUtils.currentLine=7340036;
 //BA.debugLineNum = 7340036;BA.debugLine="foto3Borrar.RemoveView";
mostCurrent._foto3borrar.RemoveView();
RDebugUtils.currentLine=7340037;
 //BA.debugLineNum = 7340037;BA.debugLine="foto4Borrar.RemoveView";
mostCurrent._foto4borrar.RemoveView();
RDebugUtils.currentLine=7340038;
 //BA.debugLineNum = 7340038;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
RDebugUtils.currentLine=7340039;
 //BA.debugLineNum = 7340039;BA.debugLine="End Sub";
return "";
}
public static String  _foto1borrar_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "foto1borrar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "foto1borrar_click", null));}
String _msg = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=7471104;
 //BA.debugLineNum = 7471104;BA.debugLine="Sub foto1Borrar_Click";
RDebugUtils.currentLine=7471105;
 //BA.debugLineNum = 7471105;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=7471106;
 //BA.debugLineNum = 7471106;BA.debugLine="msg = Msgbox2(\"Seguro que desea quitar esta foto";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=7471107;
 //BA.debugLineNum = 7471107;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=7471109;
 //BA.debugLineNum = 7471109;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7471110;
 //BA.debugLineNum = 7471110;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=7471111;
 //BA.debugLineNum = 7471111;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
RDebugUtils.currentLine=7471112;
 //BA.debugLineNum = 7471112;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto1",anywheresoftware.b4a.keywords.Common.Null,_map1);
RDebugUtils.currentLine=7471113;
 //BA.debugLineNum = 7471113;BA.debugLine="foto1view.Bitmap = Null";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=7471114;
 //BA.debugLineNum = 7471114;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
RDebugUtils.currentLine=7471115;
 //BA.debugLineNum = 7471115;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
 }else {
RDebugUtils.currentLine=7471117;
 //BA.debugLineNum = 7471117;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=7471120;
 //BA.debugLineNum = 7471120;BA.debugLine="End Sub";
return "";
}
public static String  _foto1view_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "foto1view_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "foto1view_click", null));}
RDebugUtils.currentLine=7077888;
 //BA.debugLineNum = 7077888;BA.debugLine="Sub foto1view_Click";
RDebugUtils.currentLine=7077891;
 //BA.debugLineNum = 7077891;BA.debugLine="If foto1view.Bitmap = Null Then";
if (mostCurrent._foto1view.getBitmap()== null) { 
RDebugUtils.currentLine=7077892;
 //BA.debugLineNum = 7077892;BA.debugLine="fotoAdjuntar = \"foto1\"";
mostCurrent._fotoadjuntar = "foto1";
RDebugUtils.currentLine=7077893;
 //BA.debugLineNum = 7077893;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
RDebugUtils.currentLine=7077894;
 //BA.debugLineNum = 7077894;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else {
RDebugUtils.currentLine=7077897;
 //BA.debugLineNum = 7077897;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
RDebugUtils.currentLine=7077898;
 //BA.debugLineNum = 7077898;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
RDebugUtils.currentLine=7077899;
 //BA.debugLineNum = 7077899;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=7077901;
 //BA.debugLineNum = 7077901;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
RDebugUtils.currentLine=7077902;
 //BA.debugLineNum = 7077902;BA.debugLine="fotogrande.Bitmap = foto1view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto1view.getBitmap());
RDebugUtils.currentLine=7077903;
 //BA.debugLineNum = 7077903;BA.debugLine="Log(foto1view.Width)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(mostCurrent._foto1view.getWidth()));
RDebugUtils.currentLine=7077904;
 //BA.debugLineNum = 7077904;BA.debugLine="Activity.AddView(fotogrande, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=7077906;
 //BA.debugLineNum = 7077906;BA.debugLine="Activity.AddView(foto1Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto1borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
RDebugUtils.currentLine=7077909;
 //BA.debugLineNum = 7077909;BA.debugLine="End Sub";
return "";
}
public static String  _foto2borrar_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "foto2borrar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "foto2borrar_click", null));}
String _msg = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=7536640;
 //BA.debugLineNum = 7536640;BA.debugLine="Sub foto2Borrar_Click";
RDebugUtils.currentLine=7536641;
 //BA.debugLineNum = 7536641;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=7536642;
 //BA.debugLineNum = 7536642;BA.debugLine="msg = Msgbox2(\"Seguro que desea quitar esta foto";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=7536643;
 //BA.debugLineNum = 7536643;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=7536645;
 //BA.debugLineNum = 7536645;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7536646;
 //BA.debugLineNum = 7536646;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=7536647;
 //BA.debugLineNum = 7536647;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
RDebugUtils.currentLine=7536648;
 //BA.debugLineNum = 7536648;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto2",anywheresoftware.b4a.keywords.Common.Null,_map1);
RDebugUtils.currentLine=7536649;
 //BA.debugLineNum = 7536649;BA.debugLine="foto2view.Bitmap = Null";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=7536650;
 //BA.debugLineNum = 7536650;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
RDebugUtils.currentLine=7536651;
 //BA.debugLineNum = 7536651;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
 }else {
RDebugUtils.currentLine=7536653;
 //BA.debugLineNum = 7536653;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=7536656;
 //BA.debugLineNum = 7536656;BA.debugLine="End Sub";
return "";
}
public static String  _foto2view_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "foto2view_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "foto2view_click", null));}
RDebugUtils.currentLine=7143424;
 //BA.debugLineNum = 7143424;BA.debugLine="Sub foto2view_Click";
RDebugUtils.currentLine=7143425;
 //BA.debugLineNum = 7143425;BA.debugLine="If foto2view.Bitmap = Null Then";
if (mostCurrent._foto2view.getBitmap()== null) { 
RDebugUtils.currentLine=7143426;
 //BA.debugLineNum = 7143426;BA.debugLine="fotoAdjuntar = \"foto2\"";
mostCurrent._fotoadjuntar = "foto2";
RDebugUtils.currentLine=7143427;
 //BA.debugLineNum = 7143427;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
RDebugUtils.currentLine=7143428;
 //BA.debugLineNum = 7143428;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else {
RDebugUtils.currentLine=7143432;
 //BA.debugLineNum = 7143432;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
RDebugUtils.currentLine=7143433;
 //BA.debugLineNum = 7143433;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
RDebugUtils.currentLine=7143434;
 //BA.debugLineNum = 7143434;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=7143436;
 //BA.debugLineNum = 7143436;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
RDebugUtils.currentLine=7143437;
 //BA.debugLineNum = 7143437;BA.debugLine="fotogrande.Bitmap = foto2view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto2view.getBitmap());
RDebugUtils.currentLine=7143438;
 //BA.debugLineNum = 7143438;BA.debugLine="Activity.AddView(fotogrande, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=7143440;
 //BA.debugLineNum = 7143440;BA.debugLine="Activity.AddView(foto2Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto2borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
RDebugUtils.currentLine=7143442;
 //BA.debugLineNum = 7143442;BA.debugLine="End Sub";
return "";
}
public static String  _foto3borrar_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "foto3borrar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "foto3borrar_click", null));}
String _msg = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=7602176;
 //BA.debugLineNum = 7602176;BA.debugLine="Sub foto3Borrar_Click";
RDebugUtils.currentLine=7602177;
 //BA.debugLineNum = 7602177;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=7602178;
 //BA.debugLineNum = 7602178;BA.debugLine="msg = Msgbox2(\"Seguro que desea quitar esta foto";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=7602179;
 //BA.debugLineNum = 7602179;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=7602181;
 //BA.debugLineNum = 7602181;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7602182;
 //BA.debugLineNum = 7602182;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=7602183;
 //BA.debugLineNum = 7602183;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
RDebugUtils.currentLine=7602184;
 //BA.debugLineNum = 7602184;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto3",anywheresoftware.b4a.keywords.Common.Null,_map1);
RDebugUtils.currentLine=7602185;
 //BA.debugLineNum = 7602185;BA.debugLine="foto3view.Bitmap = Null";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=7602186;
 //BA.debugLineNum = 7602186;BA.debugLine="foto3Borrar.RemoveView";
mostCurrent._foto3borrar.RemoveView();
RDebugUtils.currentLine=7602187;
 //BA.debugLineNum = 7602187;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
 }else {
RDebugUtils.currentLine=7602189;
 //BA.debugLineNum = 7602189;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=7602192;
 //BA.debugLineNum = 7602192;BA.debugLine="End Sub";
return "";
}
public static String  _foto3view_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "foto3view_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "foto3view_click", null));}
RDebugUtils.currentLine=7208960;
 //BA.debugLineNum = 7208960;BA.debugLine="Sub foto3view_Click";
RDebugUtils.currentLine=7208961;
 //BA.debugLineNum = 7208961;BA.debugLine="If foto3view.Bitmap = Null Then";
if (mostCurrent._foto3view.getBitmap()== null) { 
RDebugUtils.currentLine=7208962;
 //BA.debugLineNum = 7208962;BA.debugLine="fotoAdjuntar = \"foto3\"";
mostCurrent._fotoadjuntar = "foto3";
RDebugUtils.currentLine=7208963;
 //BA.debugLineNum = 7208963;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
RDebugUtils.currentLine=7208964;
 //BA.debugLineNum = 7208964;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else {
RDebugUtils.currentLine=7208968;
 //BA.debugLineNum = 7208968;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
RDebugUtils.currentLine=7208969;
 //BA.debugLineNum = 7208969;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
RDebugUtils.currentLine=7208970;
 //BA.debugLineNum = 7208970;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=7208972;
 //BA.debugLineNum = 7208972;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
RDebugUtils.currentLine=7208973;
 //BA.debugLineNum = 7208973;BA.debugLine="fotogrande.Bitmap = foto3view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto3view.getBitmap());
RDebugUtils.currentLine=7208974;
 //BA.debugLineNum = 7208974;BA.debugLine="Activity.AddView(fotogrande, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=7208976;
 //BA.debugLineNum = 7208976;BA.debugLine="Activity.AddView(foto1Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto1borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
RDebugUtils.currentLine=7208978;
 //BA.debugLineNum = 7208978;BA.debugLine="End Sub";
return "";
}
public static String  _foto4borrar_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "foto4borrar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "foto4borrar_click", null));}
String _msg = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=7667712;
 //BA.debugLineNum = 7667712;BA.debugLine="Sub foto4Borrar_Click";
RDebugUtils.currentLine=7667713;
 //BA.debugLineNum = 7667713;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=7667714;
 //BA.debugLineNum = 7667714;BA.debugLine="msg = Msgbox2(\"Seguro que desea quitar esta foto";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=7667715;
 //BA.debugLineNum = 7667715;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=7667717;
 //BA.debugLineNum = 7667717;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7667718;
 //BA.debugLineNum = 7667718;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=7667719;
 //BA.debugLineNum = 7667719;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
RDebugUtils.currentLine=7667720;
 //BA.debugLineNum = 7667720;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto4",anywheresoftware.b4a.keywords.Common.Null,_map1);
RDebugUtils.currentLine=7667721;
 //BA.debugLineNum = 7667721;BA.debugLine="foto4view.Bitmap = Null";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=7667722;
 //BA.debugLineNum = 7667722;BA.debugLine="foto4Borrar.RemoveView";
mostCurrent._foto4borrar.RemoveView();
RDebugUtils.currentLine=7667723;
 //BA.debugLineNum = 7667723;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
 }else {
RDebugUtils.currentLine=7667725;
 //BA.debugLineNum = 7667725;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=7667728;
 //BA.debugLineNum = 7667728;BA.debugLine="End Sub";
return "";
}
public static String  _foto4view_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "foto4view_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "foto4view_click", null));}
RDebugUtils.currentLine=7274496;
 //BA.debugLineNum = 7274496;BA.debugLine="Sub foto4view_Click";
RDebugUtils.currentLine=7274497;
 //BA.debugLineNum = 7274497;BA.debugLine="If foto4view.Bitmap = Null Then";
if (mostCurrent._foto4view.getBitmap()== null) { 
RDebugUtils.currentLine=7274498;
 //BA.debugLineNum = 7274498;BA.debugLine="fotoAdjuntar = \"foto4\"";
mostCurrent._fotoadjuntar = "foto4";
RDebugUtils.currentLine=7274499;
 //BA.debugLineNum = 7274499;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
RDebugUtils.currentLine=7274500;
 //BA.debugLineNum = 7274500;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else {
RDebugUtils.currentLine=7274504;
 //BA.debugLineNum = 7274504;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
RDebugUtils.currentLine=7274505;
 //BA.debugLineNum = 7274505;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
RDebugUtils.currentLine=7274506;
 //BA.debugLineNum = 7274506;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=7274508;
 //BA.debugLineNum = 7274508;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
RDebugUtils.currentLine=7274509;
 //BA.debugLineNum = 7274509;BA.debugLine="fotogrande.Bitmap = foto4view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto4view.getBitmap());
RDebugUtils.currentLine=7274510;
 //BA.debugLineNum = 7274510;BA.debugLine="Activity.AddView(fotogrande, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=7274512;
 //BA.debugLineNum = 7274512;BA.debugLine="Activity.AddView(foto1Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto1borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
RDebugUtils.currentLine=7274514;
 //BA.debugLineNum = 7274514;BA.debugLine="End Sub";
return "";
}
public static String  _fotogrande_click() throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fotogrande_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fotogrande_click", null));}
RDebugUtils.currentLine=7405568;
 //BA.debugLineNum = 7405568;BA.debugLine="Sub fotogrande_Click";
RDebugUtils.currentLine=7405569;
 //BA.debugLineNum = 7405569;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
RDebugUtils.currentLine=7405570;
 //BA.debugLineNum = 7405570;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
RDebugUtils.currentLine=7405571;
 //BA.debugLineNum = 7405571;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
RDebugUtils.currentLine=7405572;
 //BA.debugLineNum = 7405572;BA.debugLine="foto3Borrar.RemoveView";
mostCurrent._foto3borrar.RemoveView();
RDebugUtils.currentLine=7405573;
 //BA.debugLineNum = 7405573;BA.debugLine="foto4Borrar.RemoveView";
mostCurrent._foto4borrar.RemoveView();
RDebugUtils.currentLine=7405574;
 //BA.debugLineNum = 7405574;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
RDebugUtils.currentLine=7405575;
 //BA.debugLineNum = 7405575;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,String _reason,int _statuscode,int _taskid) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "hc_responseerror"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "hc_responseerror", new Object[] {_response,_reason,_statuscode,_taskid}));}
RDebugUtils.currentLine=8192000;
 //BA.debugLineNum = 8192000;BA.debugLine="Sub hc_ResponseError (Response As OkHttpResponse,";
RDebugUtils.currentLine=8192001;
 //BA.debugLineNum = 8192001;BA.debugLine="Log(\"error: \" & Response.StatusCode)";
anywheresoftware.b4a.keywords.Common.Log("error: "+BA.NumberToString(_response.getStatusCode()));
RDebugUtils.currentLine=8192002;
 //BA.debugLineNum = 8192002;BA.debugLine="If Response <> Null Then";
if (_response!= null) { 
RDebugUtils.currentLine=8192003;
 //BA.debugLineNum = 8192003;BA.debugLine="ToastMessageShow(\"Error enviando evaluación\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando evaluación"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=8192004;
 //BA.debugLineNum = 8192004;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 };
RDebugUtils.currentLine=8192006;
 //BA.debugLineNum = 8192006;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,int _taskid) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "hc_responsesuccess"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "hc_responsesuccess", new Object[] {_response,_taskid}));}
anywheresoftware.b4a.objects.collections.Map _map1 = null;
RDebugUtils.currentLine=8257536;
 //BA.debugLineNum = 8257536;BA.debugLine="Sub hc_ResponseSuccess (Response As OkHttpResponse";
RDebugUtils.currentLine=8257537;
 //BA.debugLineNum = 8257537;BA.debugLine="Log(\"envio correcto\")";
anywheresoftware.b4a.keywords.Common.Log("envio correcto");
RDebugUtils.currentLine=8257538;
 //BA.debugLineNum = 8257538;BA.debugLine="out.InitializeToBytesArray(0) ' I expect less tha";
_out.InitializeToBytesArray((int) (0));
RDebugUtils.currentLine=8257539;
 //BA.debugLineNum = 8257539;BA.debugLine="Response.GetAsynchronously(\"Response\", out, True,";
_response.GetAsynchronously(processBA,"Response",(java.io.OutputStream)(_out.getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
RDebugUtils.currentLine=8257541;
 //BA.debugLineNum = 8257541;BA.debugLine="If TaskId = 1 Then";
if (_taskid==1) { 
RDebugUtils.currentLine=8257542;
 //BA.debugLineNum = 8257542;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=8257543;
 //BA.debugLineNum = 8257543;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=8257544;
 //BA.debugLineNum = 8257544;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=8257545;
 //BA.debugLineNum = 8257545;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto1sent",(Object)("si"),_map1);
RDebugUtils.currentLine=8257546;
 //BA.debugLineNum = 8257546;BA.debugLine="If numberTasks = 1 Then";
if (_numbertasks==1) { 
RDebugUtils.currentLine=8257547;
 //BA.debugLineNum = 8257547;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=8257548;
 //BA.debugLineNum = 8257548;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=8257549;
 //BA.debugLineNum = 8257549;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 };
 }else 
{RDebugUtils.currentLine=8257551;
 //BA.debugLineNum = 8257551;BA.debugLine="Else if TaskId = 2 Then";
if (_taskid==2) { 
RDebugUtils.currentLine=8257552;
 //BA.debugLineNum = 8257552;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=8257553;
 //BA.debugLineNum = 8257553;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=8257554;
 //BA.debugLineNum = 8257554;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=8257555;
 //BA.debugLineNum = 8257555;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto2sent",(Object)("si"),_map1);
RDebugUtils.currentLine=8257556;
 //BA.debugLineNum = 8257556;BA.debugLine="If numberTasks = 2 Then";
if (_numbertasks==2) { 
RDebugUtils.currentLine=8257557;
 //BA.debugLineNum = 8257557;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=8257558;
 //BA.debugLineNum = 8257558;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=8257559;
 //BA.debugLineNum = 8257559;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 };
 }else 
{RDebugUtils.currentLine=8257561;
 //BA.debugLineNum = 8257561;BA.debugLine="Else if TaskId = 3 Then";
if (_taskid==3) { 
RDebugUtils.currentLine=8257562;
 //BA.debugLineNum = 8257562;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=8257563;
 //BA.debugLineNum = 8257563;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=8257564;
 //BA.debugLineNum = 8257564;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=8257565;
 //BA.debugLineNum = 8257565;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto3sent",(Object)("si"),_map1);
RDebugUtils.currentLine=8257566;
 //BA.debugLineNum = 8257566;BA.debugLine="If numberTasks = 3 Then";
if (_numbertasks==3) { 
RDebugUtils.currentLine=8257567;
 //BA.debugLineNum = 8257567;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=8257568;
 //BA.debugLineNum = 8257568;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=8257569;
 //BA.debugLineNum = 8257569;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 };
 }else 
{RDebugUtils.currentLine=8257571;
 //BA.debugLineNum = 8257571;BA.debugLine="Else if TaskId = 4 Then";
if (_taskid==4) { 
RDebugUtils.currentLine=8257572;
 //BA.debugLineNum = 8257572;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=8257573;
 //BA.debugLineNum = 8257573;BA.debugLine="Map1.Initialize";
_map1.Initialize();
RDebugUtils.currentLine=8257574;
 //BA.debugLineNum = 8257574;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject));
RDebugUtils.currentLine=8257575;
 //BA.debugLineNum = 8257575;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqldb,"markers_local","foto4sent",(Object)("si"),_map1);
RDebugUtils.currentLine=8257576;
 //BA.debugLineNum = 8257576;BA.debugLine="If numberTasks = 4 Then";
if (_numbertasks==4) { 
RDebugUtils.currentLine=8257577;
 //BA.debugLineNum = 8257577;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=8257578;
 //BA.debugLineNum = 8257578;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
RDebugUtils.currentLine=8257579;
 //BA.debugLineNum = 8257579;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 };
 }}}}
;
RDebugUtils.currentLine=8257583;
 //BA.debugLineNum = 8257583;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "listview1_itemclick"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "listview1_itemclick", new Object[] {_position,_value}));}
RDebugUtils.currentLine=6946816;
 //BA.debugLineNum = 6946816;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As";
RDebugUtils.currentLine=6946818;
 //BA.debugLineNum = 6946818;BA.debugLine="currentDatoId = Value";
mostCurrent._currentdatoid = BA.ObjectToString(_value);
RDebugUtils.currentLine=6946819;
 //BA.debugLineNum = 6946819;BA.debugLine="VerDetalles(currentDatoId, False)";
_verdetalles(mostCurrent._currentdatoid,anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=6946820;
 //BA.debugLineNum = 6946820;BA.debugLine="End Sub";
return "";
}
public static String  _verdetalles(String _datoid,boolean _serverid) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "verdetalles"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "verdetalles", new Object[] {_datoid,_serverid}));}
anywheresoftware.b4a.objects.collections.Map _datomap = null;
String _spname = "";
String _datoenviado = "";
String _datorevisado = "";
String _datoprivado = "";
String _foto1path = "";
String _foto2path = "";
String _foto3path = "";
String _foto4path = "";
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
anywheresoftware.b4a.objects.LabelWrapper _cdplus1 = null;
anywheresoftware.b4a.objects.LabelWrapper _cdplus2 = null;
anywheresoftware.b4a.objects.LabelWrapper _cdplus3 = null;
anywheresoftware.b4a.objects.LabelWrapper _cdplus4 = null;
RDebugUtils.currentLine=7012352;
 //BA.debugLineNum = 7012352;BA.debugLine="Sub VerDetalles(datoID As String, serverId As Bool";
RDebugUtils.currentLine=7012354;
 //BA.debugLineNum = 7012354;BA.debugLine="Dim datoMap As Map";
_datomap = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=7012355;
 //BA.debugLineNum = 7012355;BA.debugLine="datoMap.Initialize";
_datomap.Initialize();
RDebugUtils.currentLine=7012356;
 //BA.debugLineNum = 7012356;BA.debugLine="If serverId = False Then";
if (_serverid==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=7012357;
 //BA.debugLineNum = 7012357;BA.debugLine="datoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datomap = mostCurrent._dbutils._executemap(mostCurrent.activityBA,mostCurrent._starter._sqldb,"SELECT * FROM markers_local WHERE Id=?",new String[]{_datoid});
 }else {
RDebugUtils.currentLine=7012359;
 //BA.debugLineNum = 7012359;BA.debugLine="datoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datomap = mostCurrent._dbutils._executemap(mostCurrent.activityBA,mostCurrent._starter._sqldb,"SELECT * FROM markers_local WHERE serverid=?",new String[]{_datoid});
 };
RDebugUtils.currentLine=7012363;
 //BA.debugLineNum = 7012363;BA.debugLine="If datoMap = Null Or datoMap.IsInitialized = Fals";
if (_datomap== null || _datomap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=7012364;
 //BA.debugLineNum = 7012364;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012365;
 //BA.debugLineNum = 7012365;BA.debugLine="ToastMessageShow(\"Dato no encontrado...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Dato no encontrado..."),anywheresoftware.b4a.keywords.Common.False);
 }else {
RDebugUtils.currentLine=7012368;
 //BA.debugLineNum = 7012368;BA.debugLine="IdProyectoEnviar = datoID";
mostCurrent._idproyectoenviar = _datoid;
RDebugUtils.currentLine=7012370;
 //BA.debugLineNum = 7012370;BA.debugLine="lblNombreEnvio.Text = datoMap.Get(\"georeferenced";
mostCurrent._lblnombreenvio.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("georeferenceddate"))));
RDebugUtils.currentLine=7012371;
 //BA.debugLineNum = 7012371;BA.debugLine="lblFecha.Text = datoMap.Get(\"georeferenceddate\")";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("georeferenceddate"))));
RDebugUtils.currentLine=7012372;
 //BA.debugLineNum = 7012372;BA.debugLine="If datoMap.Get(\"par2\") = Null Then";
if (_datomap.Get((Object)("par2"))== null) { 
RDebugUtils.currentLine=7012373;
 //BA.debugLineNum = 7012373;BA.debugLine="lblTipo.Text = \"Indeterminado\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Indeterminado"));
 }else {
RDebugUtils.currentLine=7012376;
 //BA.debugLineNum = 7012376;BA.debugLine="Dim spname As String";
_spname = "";
RDebugUtils.currentLine=7012377;
 //BA.debugLineNum = 7012377;BA.debugLine="If datoMap.Get(\"par2\") = \"1\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("1"))) { 
RDebugUtils.currentLine=7012378;
 //BA.debugLineNum = 7012378;BA.debugLine="spname = \"Triatoma infestans\"";
_spname = "Triatoma infestans";
 }else 
{RDebugUtils.currentLine=7012379;
 //BA.debugLineNum = 7012379;BA.debugLine="Else If datoMap.Get(\"par2\") = \"2\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("2"))) { 
RDebugUtils.currentLine=7012380;
 //BA.debugLineNum = 7012380;BA.debugLine="spname = \"Triatoma guasayana\"";
_spname = "Triatoma guasayana";
 }else 
{RDebugUtils.currentLine=7012381;
 //BA.debugLineNum = 7012381;BA.debugLine="Else If datoMap.Get(\"par2\") = \"3\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("3"))) { 
RDebugUtils.currentLine=7012382;
 //BA.debugLineNum = 7012382;BA.debugLine="spname = \"Triatoma sordida\"";
_spname = "Triatoma sordida";
 }else 
{RDebugUtils.currentLine=7012383;
 //BA.debugLineNum = 7012383;BA.debugLine="Else If datoMap.Get(\"par2\") = \"4\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("4"))) { 
RDebugUtils.currentLine=7012384;
 //BA.debugLineNum = 7012384;BA.debugLine="spname = \"Triatoma garciabesi\"";
_spname = "Triatoma garciabesi";
 }else 
{RDebugUtils.currentLine=7012385;
 //BA.debugLineNum = 7012385;BA.debugLine="Else If datoMap.Get(\"par2\") = \"5\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("5"))) { 
RDebugUtils.currentLine=7012386;
 //BA.debugLineNum = 7012386;BA.debugLine="spname = \"Triatoma patagónica\"";
_spname = "Triatoma patagónica";
 }else 
{RDebugUtils.currentLine=7012387;
 //BA.debugLineNum = 7012387;BA.debugLine="Else If datoMap.Get(\"par2\") = \"6\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("6"))) { 
RDebugUtils.currentLine=7012388;
 //BA.debugLineNum = 7012388;BA.debugLine="spname = \"Triatoma delpontei\"";
_spname = "Triatoma delpontei";
 }else 
{RDebugUtils.currentLine=7012389;
 //BA.debugLineNum = 7012389;BA.debugLine="Else If datoMap.Get(\"par2\") = \"7\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("7"))) { 
RDebugUtils.currentLine=7012390;
 //BA.debugLineNum = 7012390;BA.debugLine="spname = \"Triatoma platensis\"";
_spname = "Triatoma platensis";
 }else 
{RDebugUtils.currentLine=7012391;
 //BA.debugLineNum = 7012391;BA.debugLine="Else If datoMap.Get(\"par2\") = \"8\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("8"))) { 
RDebugUtils.currentLine=7012392;
 //BA.debugLineNum = 7012392;BA.debugLine="spname = \"Triatoma rubrovaria\"";
_spname = "Triatoma rubrovaria";
 }else 
{RDebugUtils.currentLine=7012393;
 //BA.debugLineNum = 7012393;BA.debugLine="Else If datoMap.Get(\"par2\") = \"9\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("9"))) { 
RDebugUtils.currentLine=7012394;
 //BA.debugLineNum = 7012394;BA.debugLine="spname = \"Triatoma eratyrusiformis\"";
_spname = "Triatoma eratyrusiformis";
 }else 
{RDebugUtils.currentLine=7012395;
 //BA.debugLineNum = 7012395;BA.debugLine="Else If datoMap.Get(\"par2\") = \"10\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("10"))) { 
RDebugUtils.currentLine=7012396;
 //BA.debugLineNum = 7012396;BA.debugLine="spname = \"Triatoma breyeri\"";
_spname = "Triatoma breyeri";
 }else 
{RDebugUtils.currentLine=7012397;
 //BA.debugLineNum = 7012397;BA.debugLine="Else If datoMap.Get(\"par2\") = \"11\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("11"))) { 
RDebugUtils.currentLine=7012398;
 //BA.debugLineNum = 7012398;BA.debugLine="spname = \"Panstrongylus rufotuberculatus\"";
_spname = "Panstrongylus rufotuberculatus";
 }else 
{RDebugUtils.currentLine=7012399;
 //BA.debugLineNum = 7012399;BA.debugLine="Else If datoMap.Get(\"par2\") = \"12\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("12"))) { 
RDebugUtils.currentLine=7012400;
 //BA.debugLineNum = 7012400;BA.debugLine="spname = \"Triatoma limai\"";
_spname = "Triatoma limai";
 }else 
{RDebugUtils.currentLine=7012401;
 //BA.debugLineNum = 7012401;BA.debugLine="Else If datoMap.Get(\"par2\") = \"13\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("13"))) { 
RDebugUtils.currentLine=7012402;
 //BA.debugLineNum = 7012402;BA.debugLine="spname = \"Psammolestes coreodes\"";
_spname = "Psammolestes coreodes";
 }else 
{RDebugUtils.currentLine=7012403;
 //BA.debugLineNum = 7012403;BA.debugLine="Else If datoMap.Get(\"par2\") = \"14\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("14"))) { 
RDebugUtils.currentLine=7012404;
 //BA.debugLineNum = 7012404;BA.debugLine="spname = \"Panstrongylus geniculatus\"";
_spname = "Panstrongylus geniculatus";
 }else 
{RDebugUtils.currentLine=7012405;
 //BA.debugLineNum = 7012405;BA.debugLine="Else If datoMap.Get(\"par2\") = \"15\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("15"))) { 
RDebugUtils.currentLine=7012406;
 //BA.debugLineNum = 7012406;BA.debugLine="spname = \"Panstrongylus megistus\"";
_spname = "Panstrongylus megistus";
 }else 
{RDebugUtils.currentLine=7012407;
 //BA.debugLineNum = 7012407;BA.debugLine="Else If datoMap.Get(\"par2\") = \"16\" Then";
if ((_datomap.Get((Object)("par2"))).equals((Object)("16"))) { 
RDebugUtils.currentLine=7012408;
 //BA.debugLineNum = 7012408;BA.debugLine="spname = \"Panstrongylus guentheri\"";
_spname = "Panstrongylus guentheri";
 }}}}}}}}}}}}}}}}
;
RDebugUtils.currentLine=7012410;
 //BA.debugLineNum = 7012410;BA.debugLine="lblTipo.Text = spname";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence(_spname));
 };
RDebugUtils.currentLine=7012417;
 //BA.debugLineNum = 7012417;BA.debugLine="Dim spname As String";
_spname = "";
RDebugUtils.currentLine=7012418;
 //BA.debugLineNum = 7012418;BA.debugLine="If datoMap.Get(\"valororganismo\") = Null Then";
if (_datomap.Get((Object)("valororganismo"))== null) { 
RDebugUtils.currentLine=7012419;
 //BA.debugLineNum = 7012419;BA.debugLine="spname = \"Indeterminado\"";
_spname = "Indeterminado";
 }else {
RDebugUtils.currentLine=7012421;
 //BA.debugLineNum = 7012421;BA.debugLine="If datoMap.Get(\"valororganismo\") = \"infestans\"";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("infestans"))) { 
RDebugUtils.currentLine=7012422;
 //BA.debugLineNum = 7012422;BA.debugLine="spname = \"Triatoma infestans\"";
_spname = "Triatoma infestans";
 }else 
{RDebugUtils.currentLine=7012423;
 //BA.debugLineNum = 7012423;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"guasay";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("guasayana"))) { 
RDebugUtils.currentLine=7012424;
 //BA.debugLineNum = 7012424;BA.debugLine="spname = \"Triatoma guasayana\"";
_spname = "Triatoma guasayana";
 }else 
{RDebugUtils.currentLine=7012425;
 //BA.debugLineNum = 7012425;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"sordid";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("sordida"))) { 
RDebugUtils.currentLine=7012426;
 //BA.debugLineNum = 7012426;BA.debugLine="spname = \"Triatoma sordida\"";
_spname = "Triatoma sordida";
 }else 
{RDebugUtils.currentLine=7012427;
 //BA.debugLineNum = 7012427;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"garcia";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("garciabesi"))) { 
RDebugUtils.currentLine=7012428;
 //BA.debugLineNum = 7012428;BA.debugLine="spname = \"Triatoma garciabesi\"";
_spname = "Triatoma garciabesi";
 }else 
{RDebugUtils.currentLine=7012429;
 //BA.debugLineNum = 7012429;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"patagó";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("patagónica"))) { 
RDebugUtils.currentLine=7012430;
 //BA.debugLineNum = 7012430;BA.debugLine="spname = \"Triatoma patagónica\"";
_spname = "Triatoma patagónica";
 }else 
{RDebugUtils.currentLine=7012431;
 //BA.debugLineNum = 7012431;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"delpon";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("delpontei"))) { 
RDebugUtils.currentLine=7012432;
 //BA.debugLineNum = 7012432;BA.debugLine="spname = \"Triatoma delpontei\"";
_spname = "Triatoma delpontei";
 }else 
{RDebugUtils.currentLine=7012433;
 //BA.debugLineNum = 7012433;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"platen";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("platensis"))) { 
RDebugUtils.currentLine=7012434;
 //BA.debugLineNum = 7012434;BA.debugLine="spname = \"Triatoma platensis\"";
_spname = "Triatoma platensis";
 }else 
{RDebugUtils.currentLine=7012435;
 //BA.debugLineNum = 7012435;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"rubrov";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("rubrovaria"))) { 
RDebugUtils.currentLine=7012436;
 //BA.debugLineNum = 7012436;BA.debugLine="spname = \"Triatoma rubrovaria\"";
_spname = "Triatoma rubrovaria";
 }else 
{RDebugUtils.currentLine=7012437;
 //BA.debugLineNum = 7012437;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"eratyr";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("eratyrusiformis"))) { 
RDebugUtils.currentLine=7012438;
 //BA.debugLineNum = 7012438;BA.debugLine="spname = \"Triatoma eratyrusiformis\"";
_spname = "Triatoma eratyrusiformis";
 }else 
{RDebugUtils.currentLine=7012439;
 //BA.debugLineNum = 7012439;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"breyer";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("breyeri"))) { 
RDebugUtils.currentLine=7012440;
 //BA.debugLineNum = 7012440;BA.debugLine="spname = \"Triatoma breyeri\"";
_spname = "Triatoma breyeri";
 }else 
{RDebugUtils.currentLine=7012441;
 //BA.debugLineNum = 7012441;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"Panstr";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("Panstrongylus_rufotuberculatus"))) { 
RDebugUtils.currentLine=7012442;
 //BA.debugLineNum = 7012442;BA.debugLine="spname = \"Panstrongylus rufotuberculatus\"";
_spname = "Panstrongylus rufotuberculatus";
 }else 
{RDebugUtils.currentLine=7012443;
 //BA.debugLineNum = 7012443;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"limai\"";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("limai"))) { 
RDebugUtils.currentLine=7012444;
 //BA.debugLineNum = 7012444;BA.debugLine="spname = \"Triatoma limai\"";
_spname = "Triatoma limai";
 }else 
{RDebugUtils.currentLine=7012445;
 //BA.debugLineNum = 7012445;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"Psammo";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("Psammolestes_coreodes"))) { 
RDebugUtils.currentLine=7012446;
 //BA.debugLineNum = 7012446;BA.debugLine="spname = \"Psammolestes coreodes\"";
_spname = "Psammolestes coreodes";
 }else 
{RDebugUtils.currentLine=7012447;
 //BA.debugLineNum = 7012447;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"Panstr";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("Panstrongylus_geniculatus"))) { 
RDebugUtils.currentLine=7012448;
 //BA.debugLineNum = 7012448;BA.debugLine="spname = \"Panstrongylus geniculatus\"";
_spname = "Panstrongylus geniculatus";
 }else 
{RDebugUtils.currentLine=7012449;
 //BA.debugLineNum = 7012449;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"Panstr";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("Panstrongylus_megistus"))) { 
RDebugUtils.currentLine=7012450;
 //BA.debugLineNum = 7012450;BA.debugLine="spname = \"Panstrongylus megistus\"";
_spname = "Panstrongylus megistus";
 }else 
{RDebugUtils.currentLine=7012451;
 //BA.debugLineNum = 7012451;BA.debugLine="Else If datoMap.Get(\"valororganismo\") = \"Panstr";
if ((_datomap.Get((Object)("valororganismo"))).equals((Object)("Panstrongylus_guentheri"))) { 
RDebugUtils.currentLine=7012452;
 //BA.debugLineNum = 7012452;BA.debugLine="spname = \"Panstrongylus guentheri\"";
_spname = "Panstrongylus guentheri";
 }else {
RDebugUtils.currentLine=7012454;
 //BA.debugLineNum = 7012454;BA.debugLine="spname = datoMap.Get(\"valororganismo\")";
_spname = BA.ObjectToString(_datomap.Get((Object)("valororganismo")));
 }}}}}}}}}}}}}}}}
;
 };
RDebugUtils.currentLine=7012457;
 //BA.debugLineNum = 7012457;BA.debugLine="lblCalidad.Text = spname";
mostCurrent._lblcalidad.setText(BA.ObjectToCharSequence(_spname));
RDebugUtils.currentLine=7012459;
 //BA.debugLineNum = 7012459;BA.debugLine="lblLat.Text = datoMap.Get(\"decimallatitude\")";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("decimallatitude"))));
RDebugUtils.currentLine=7012460;
 //BA.debugLineNum = 7012460;BA.debugLine="lblLng.Text = datoMap.Get(\"decimallongitude\")";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("decimallongitude"))));
RDebugUtils.currentLine=7012463;
 //BA.debugLineNum = 7012463;BA.debugLine="Dim datoenviado As String";
_datoenviado = "";
RDebugUtils.currentLine=7012464;
 //BA.debugLineNum = 7012464;BA.debugLine="datoenviado = datoMap.Get(\"evalsent\")";
_datoenviado = BA.ObjectToString(_datomap.Get((Object)("evalsent")));
RDebugUtils.currentLine=7012465;
 //BA.debugLineNum = 7012465;BA.debugLine="If datoenviado = \"si\" Then";
if ((_datoenviado).equals("si")) { 
RDebugUtils.currentLine=7012466;
 //BA.debugLineNum = 7012466;BA.debugLine="chkEnviado.Visible = True";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012467;
 //BA.debugLineNum = 7012467;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=7012468;
 //BA.debugLineNum = 7012468;BA.debugLine="chkEnviado.Checked = True";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012469;
 //BA.debugLineNum = 7012469;BA.debugLine="chkEnviado.Text = \"Enviado\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence("Enviado"));
RDebugUtils.currentLine=7012470;
 //BA.debugLineNum = 7012470;BA.debugLine="lblBarra.Visible = True";
mostCurrent._lblbarra.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012471;
 //BA.debugLineNum = 7012471;BA.debugLine="lblBarra.Width = chkOnline.Left + chkOnline.Wid";
mostCurrent._lblbarra.setWidth((int) (mostCurrent._chkonline.getLeft()+mostCurrent._chkonline.getWidth()-mostCurrent._chkenviadobar.getLeft()));
 }else {
RDebugUtils.currentLine=7012473;
 //BA.debugLineNum = 7012473;BA.debugLine="lblBarra.Visible = False";
mostCurrent._lblbarra.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=7012474;
 //BA.debugLineNum = 7012474;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012475;
 //BA.debugLineNum = 7012475;BA.debugLine="chkEnviado.Visible = False";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=7012479;
 //BA.debugLineNum = 7012479;BA.debugLine="Dim datorevisado As String";
_datorevisado = "";
RDebugUtils.currentLine=7012480;
 //BA.debugLineNum = 7012480;BA.debugLine="datorevisado = datoMap.Get(\"estadovalidacion\")";
_datorevisado = BA.ObjectToString(_datomap.Get((Object)("estadovalidacion")));
RDebugUtils.currentLine=7012481;
 //BA.debugLineNum = 7012481;BA.debugLine="If datorevisado = \"validado\" Then";
if ((_datorevisado).equals("validado")) { 
RDebugUtils.currentLine=7012482;
 //BA.debugLineNum = 7012482;BA.debugLine="chkEnviado.Visible = True";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012483;
 //BA.debugLineNum = 7012483;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=7012484;
 //BA.debugLineNum = 7012484;BA.debugLine="chkEnviado.Checked = True";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012485;
 //BA.debugLineNum = 7012485;BA.debugLine="chkEnviado.Text = \"Enviado\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence("Enviado"));
RDebugUtils.currentLine=7012486;
 //BA.debugLineNum = 7012486;BA.debugLine="lblBarra.Visible = True";
mostCurrent._lblbarra.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012487;
 //BA.debugLineNum = 7012487;BA.debugLine="lblBarra.Width = chkValidadoExpertos.Left + chk";
mostCurrent._lblbarra.setWidth((int) (mostCurrent._chkvalidadoexpertos.getLeft()+mostCurrent._chkvalidadoexpertos.getWidth()-mostCurrent._chkenviadobar.getLeft()));
 };
RDebugUtils.currentLine=7012492;
 //BA.debugLineNum = 7012492;BA.debugLine="Dim datoprivado As String";
_datoprivado = "";
RDebugUtils.currentLine=7012493;
 //BA.debugLineNum = 7012493;BA.debugLine="datoprivado = datoMap.Get(\"privado\")";
_datoprivado = BA.ObjectToString(_datomap.Get((Object)("privado")));
RDebugUtils.currentLine=7012494;
 //BA.debugLineNum = 7012494;BA.debugLine="If datoprivado = \"no\" Or datoprivado = \"\" Or dat";
if ((_datoprivado).equals("no") || (_datoprivado).equals("") || (_datoprivado).equals("null")) { 
RDebugUtils.currentLine=7012495;
 //BA.debugLineNum = 7012495;BA.debugLine="chkPublico.Checked = True";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012496;
 //BA.debugLineNum = 7012496;BA.debugLine="chkPublico.Text = \"Dato público\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato público"));
RDebugUtils.currentLine=7012497;
 //BA.debugLineNum = 7012497;BA.debugLine="btnCambiarPublico.Text = \"Hacer dato privado\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Hacer dato privado"));
 }else {
RDebugUtils.currentLine=7012499;
 //BA.debugLineNum = 7012499;BA.debugLine="chkPublico.Text = \"Dato privado\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato privado"));
RDebugUtils.currentLine=7012500;
 //BA.debugLineNum = 7012500;BA.debugLine="chkPublico.Checked = False";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=7012501;
 //BA.debugLineNum = 7012501;BA.debugLine="btnCambiarPublico.Text = \"Hacer dato público\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Hacer dato público"));
 };
RDebugUtils.currentLine=7012507;
 //BA.debugLineNum = 7012507;BA.debugLine="foto1view.Bitmap = Null";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=7012508;
 //BA.debugLineNum = 7012508;BA.debugLine="foto2view.Bitmap = Null";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=7012509;
 //BA.debugLineNum = 7012509;BA.debugLine="foto3view.Bitmap = Null";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=7012510;
 //BA.debugLineNum = 7012510;BA.debugLine="foto4view.Bitmap = Null";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=7012511;
 //BA.debugLineNum = 7012511;BA.debugLine="Dim foto1path As String";
_foto1path = "";
RDebugUtils.currentLine=7012512;
 //BA.debugLineNum = 7012512;BA.debugLine="Dim foto2path As String";
_foto2path = "";
RDebugUtils.currentLine=7012513;
 //BA.debugLineNum = 7012513;BA.debugLine="Dim foto3path As String";
_foto3path = "";
RDebugUtils.currentLine=7012514;
 //BA.debugLineNum = 7012514;BA.debugLine="Dim foto4path As String";
_foto4path = "";
RDebugUtils.currentLine=7012515;
 //BA.debugLineNum = 7012515;BA.debugLine="foto1path = datoMap.Get(\"foto1\") & \".jpg\"";
_foto1path = BA.ObjectToString(_datomap.Get((Object)("foto1")))+".jpg";
RDebugUtils.currentLine=7012516;
 //BA.debugLineNum = 7012516;BA.debugLine="foto2path = datoMap.Get(\"foto2\") & \".jpg\"";
_foto2path = BA.ObjectToString(_datomap.Get((Object)("foto2")))+".jpg";
RDebugUtils.currentLine=7012517;
 //BA.debugLineNum = 7012517;BA.debugLine="foto3path = datoMap.Get(\"foto3\") & \".jpg\"";
_foto3path = BA.ObjectToString(_datomap.Get((Object)("foto3")))+".jpg";
RDebugUtils.currentLine=7012518;
 //BA.debugLineNum = 7012518;BA.debugLine="foto4path = datoMap.Get(\"foto4\") & \".jpg\"";
_foto4path = BA.ObjectToString(_datomap.Get((Object)("foto4")))+".jpg";
RDebugUtils.currentLine=7012519;
 //BA.debugLineNum = 7012519;BA.debugLine="scvFotos.Panel.Invalidate";
mostCurrent._scvfotos.getPanel().Invalidate();
RDebugUtils.currentLine=7012522;
 //BA.debugLineNum = 7012522;BA.debugLine="foto1Borrar.Color = Colors.ARGB(150,255,255,255)";
mostCurrent._foto1borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
RDebugUtils.currentLine=7012523;
 //BA.debugLineNum = 7012523;BA.debugLine="foto2Borrar.Color = Colors.ARGB(150,255,255,255)";
mostCurrent._foto2borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
RDebugUtils.currentLine=7012524;
 //BA.debugLineNum = 7012524;BA.debugLine="foto3Borrar.Color = Colors.ARGB(150,255,255,255)";
mostCurrent._foto3borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
RDebugUtils.currentLine=7012525;
 //BA.debugLineNum = 7012525;BA.debugLine="foto4Borrar.Color = Colors.ARGB(150,255,255,255)";
mostCurrent._foto4borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
RDebugUtils.currentLine=7012526;
 //BA.debugLineNum = 7012526;BA.debugLine="foto1Borrar.Text = \"X Eliminar imagen\"";
mostCurrent._foto1borrar.setText(BA.ObjectToCharSequence("X Eliminar imagen"));
RDebugUtils.currentLine=7012527;
 //BA.debugLineNum = 7012527;BA.debugLine="foto2Borrar.Text = \"X Eliminar imagen\"";
mostCurrent._foto2borrar.setText(BA.ObjectToCharSequence("X Eliminar imagen"));
RDebugUtils.currentLine=7012528;
 //BA.debugLineNum = 7012528;BA.debugLine="foto3Borrar.Text = \"X Eliminar imagen\"";
mostCurrent._foto3borrar.setText(BA.ObjectToCharSequence("X Eliminar imagen"));
RDebugUtils.currentLine=7012529;
 //BA.debugLineNum = 7012529;BA.debugLine="foto4Borrar.Text = \"X Eliminar imagen\"";
mostCurrent._foto4borrar.setText(BA.ObjectToCharSequence("X Eliminar imagen"));
RDebugUtils.currentLine=7012530;
 //BA.debugLineNum = 7012530;BA.debugLine="foto1Borrar.TextColor = Colors.Black";
mostCurrent._foto1borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=7012531;
 //BA.debugLineNum = 7012531;BA.debugLine="foto2Borrar.TextColor = Colors.Black";
mostCurrent._foto2borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=7012532;
 //BA.debugLineNum = 7012532;BA.debugLine="foto3Borrar.TextColor = Colors.Black";
mostCurrent._foto3borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=7012533;
 //BA.debugLineNum = 7012533;BA.debugLine="foto4Borrar.TextColor = Colors.Black";
mostCurrent._foto4borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=7012536;
 //BA.debugLineNum = 7012536;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
RDebugUtils.currentLine=7012537;
 //BA.debugLineNum = 7012537;BA.debugLine="cd.Initialize2(Colors.Transparent,10dip,2dip,Col";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.LightGray);
RDebugUtils.currentLine=7012538;
 //BA.debugLineNum = 7012538;BA.debugLine="scvFotos.Panel.Width = foto4view.Width + foto4vi";
mostCurrent._scvfotos.getPanel().setWidth((int) (mostCurrent._foto4view.getWidth()+mostCurrent._foto4view.getLeft()));
RDebugUtils.currentLine=7012542;
 //BA.debugLineNum = 7012542;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1path)) { 
RDebugUtils.currentLine=7012543;
 //BA.debugLineNum = 7012543;BA.debugLine="foto1view.Bitmap= LoadBitmapSample(File.DirRoo";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto1path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
RDebugUtils.currentLine=7012545;
 //BA.debugLineNum = 7012545;BA.debugLine="foto1view.Background=cd";
mostCurrent._foto1view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
RDebugUtils.currentLine=7012546;
 //BA.debugLineNum = 7012546;BA.debugLine="Dim cdplus1 As Label";
_cdplus1 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=7012547;
 //BA.debugLineNum = 7012547;BA.debugLine="cdplus1.Initialize(\"\")";
_cdplus1.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=7012548;
 //BA.debugLineNum = 7012548;BA.debugLine="cdplus1.Text = \"+\"";
_cdplus1.setText(BA.ObjectToCharSequence("+"));
RDebugUtils.currentLine=7012549;
 //BA.debugLineNum = 7012549;BA.debugLine="cdplus1.TextSize = 48";
_cdplus1.setTextSize((float) (48));
RDebugUtils.currentLine=7012550;
 //BA.debugLineNum = 7012550;BA.debugLine="cdplus1.TextColor = Colors.LightGray";
_cdplus1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
RDebugUtils.currentLine=7012551;
 //BA.debugLineNum = 7012551;BA.debugLine="cdplus1.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus1.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
RDebugUtils.currentLine=7012552;
 //BA.debugLineNum = 7012552;BA.debugLine="scvFotos.Panel.AddView(cdplus1, foto1view.Left,";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus1.getObject()),mostCurrent._foto1view.getLeft(),mostCurrent._foto1view.getTop(),mostCurrent._foto1view.getWidth(),mostCurrent._foto1view.getHeight());
 };
RDebugUtils.currentLine=7012559;
 //BA.debugLineNum = 7012559;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2path)) { 
RDebugUtils.currentLine=7012560;
 //BA.debugLineNum = 7012560;BA.debugLine="foto2view.Bitmap= LoadBitmapSample(File.DirRoo";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto2path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1005),mostCurrent.activityBA)).getObject()));
 }else {
RDebugUtils.currentLine=7012562;
 //BA.debugLineNum = 7012562;BA.debugLine="foto2view.Background=cd";
mostCurrent._foto2view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
RDebugUtils.currentLine=7012563;
 //BA.debugLineNum = 7012563;BA.debugLine="Dim cdplus2 As Label";
_cdplus2 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=7012564;
 //BA.debugLineNum = 7012564;BA.debugLine="cdplus2.Initialize(\"\")";
_cdplus2.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=7012565;
 //BA.debugLineNum = 7012565;BA.debugLine="cdplus2.Text = \"+\"";
_cdplus2.setText(BA.ObjectToCharSequence("+"));
RDebugUtils.currentLine=7012566;
 //BA.debugLineNum = 7012566;BA.debugLine="cdplus2.TextSize = 48";
_cdplus2.setTextSize((float) (48));
RDebugUtils.currentLine=7012567;
 //BA.debugLineNum = 7012567;BA.debugLine="cdplus2.TextColor = Colors.LightGray";
_cdplus2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
RDebugUtils.currentLine=7012568;
 //BA.debugLineNum = 7012568;BA.debugLine="cdplus2.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
RDebugUtils.currentLine=7012569;
 //BA.debugLineNum = 7012569;BA.debugLine="scvFotos.Panel.AddView(cdplus2, foto2view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus2.getObject()),mostCurrent._foto2view.getLeft(),mostCurrent._foto2view.getTop(),mostCurrent._foto2view.getWidth(),mostCurrent._foto2view.getHeight());
 };
RDebugUtils.currentLine=7012576;
 //BA.debugLineNum = 7012576;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3path)) { 
RDebugUtils.currentLine=7012577;
 //BA.debugLineNum = 7012577;BA.debugLine="foto3view.Bitmap= LoadBitmapSample(File.DirRoo";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto3path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA)).getObject()));
 }else {
RDebugUtils.currentLine=7012579;
 //BA.debugLineNum = 7012579;BA.debugLine="foto3view.Background=cd";
mostCurrent._foto3view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
RDebugUtils.currentLine=7012580;
 //BA.debugLineNum = 7012580;BA.debugLine="Dim cdplus3 As Label";
_cdplus3 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=7012581;
 //BA.debugLineNum = 7012581;BA.debugLine="cdplus3.Initialize(\"\")";
_cdplus3.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=7012582;
 //BA.debugLineNum = 7012582;BA.debugLine="cdplus3.Text = \"+\"";
_cdplus3.setText(BA.ObjectToCharSequence("+"));
RDebugUtils.currentLine=7012583;
 //BA.debugLineNum = 7012583;BA.debugLine="cdplus3.TextSize = 48";
_cdplus3.setTextSize((float) (48));
RDebugUtils.currentLine=7012584;
 //BA.debugLineNum = 7012584;BA.debugLine="cdplus3.TextColor = Colors.LightGray";
_cdplus3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
RDebugUtils.currentLine=7012585;
 //BA.debugLineNum = 7012585;BA.debugLine="cdplus3.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus3.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
RDebugUtils.currentLine=7012586;
 //BA.debugLineNum = 7012586;BA.debugLine="scvFotos.Panel.AddView(cdplus3, foto3view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus3.getObject()),mostCurrent._foto3view.getLeft(),mostCurrent._foto3view.getTop(),mostCurrent._foto3view.getWidth(),mostCurrent._foto3view.getHeight());
 };
RDebugUtils.currentLine=7012592;
 //BA.debugLineNum = 7012592;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4path)) { 
RDebugUtils.currentLine=7012593;
 //BA.debugLineNum = 7012593;BA.debugLine="foto4view.Bitmap= LoadBitmapSample(File.DirRoo";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_foto4path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA)).getObject()));
 }else {
RDebugUtils.currentLine=7012595;
 //BA.debugLineNum = 7012595;BA.debugLine="foto4view.Background=cd";
mostCurrent._foto4view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
RDebugUtils.currentLine=7012596;
 //BA.debugLineNum = 7012596;BA.debugLine="Dim cdplus4 As Label";
_cdplus4 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=7012597;
 //BA.debugLineNum = 7012597;BA.debugLine="cdplus4.Initialize(\"\")";
_cdplus4.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=7012598;
 //BA.debugLineNum = 7012598;BA.debugLine="cdplus4.Text = \"+\"";
_cdplus4.setText(BA.ObjectToCharSequence("+"));
RDebugUtils.currentLine=7012599;
 //BA.debugLineNum = 7012599;BA.debugLine="cdplus4.TextSize = 48";
_cdplus4.setTextSize((float) (48));
RDebugUtils.currentLine=7012600;
 //BA.debugLineNum = 7012600;BA.debugLine="cdplus4.TextColor = Colors.LightGray";
_cdplus4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
RDebugUtils.currentLine=7012601;
 //BA.debugLineNum = 7012601;BA.debugLine="cdplus4.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus4.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
RDebugUtils.currentLine=7012602;
 //BA.debugLineNum = 7012602;BA.debugLine="scvFotos.Panel.AddView(cdplus4, foto4view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus4.getObject()),mostCurrent._foto4view.getLeft(),mostCurrent._foto4view.getTop(),mostCurrent._foto4view.getWidth(),mostCurrent._foto4view.getHeight());
 };
 };
RDebugUtils.currentLine=7012610;
 //BA.debugLineNum = 7012610;BA.debugLine="tabStripDatosAnteriores.ScrollTo(2,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=7012611;
 //BA.debugLineNum = 7012611;BA.debugLine="tabActual = \"Detalle\"";
mostCurrent._tabactual = "Detalle";
RDebugUtils.currentLine=7012612;
 //BA.debugLineNum = 7012612;BA.debugLine="notificacion = False";
_notificacion = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=7012613;
 //BA.debugLineNum = 7012613;BA.debugLine="serverId  = False";
_serverid = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=7012614;
 //BA.debugLineNum = 7012614;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(boolean _success,int _taskid) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "response_streamfinish"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "response_streamfinish", new Object[] {_success,_taskid}));}
byte[] _another_buffer = null;
RDebugUtils.currentLine=8323072;
 //BA.debugLineNum = 8323072;BA.debugLine="Sub Response_StreamFinish (Success As Boolean, Tas";
RDebugUtils.currentLine=8323073;
 //BA.debugLineNum = 8323073;BA.debugLine="Dim another_buffer () As Byte";
_another_buffer = new byte[(int) (0)];
;
RDebugUtils.currentLine=8323074;
 //BA.debugLineNum = 8323074;BA.debugLine="another_buffer = out.ToBytesArray";
_another_buffer = _out.ToBytesArray();
RDebugUtils.currentLine=8323075;
 //BA.debugLineNum = 8323075;BA.debugLine="Log (BytesToString(another_buffer, 0, another_buf";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.BytesToString(_another_buffer,(int) (0),_another_buffer.length,"UTF8"));
RDebugUtils.currentLine=8323076;
 //BA.debugLineNum = 8323076;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=8323077;
 //BA.debugLineNum = 8323077;BA.debugLine="Log (\"exitos!\")";
anywheresoftware.b4a.keywords.Common.Log("exitos!");
 };
RDebugUtils.currentLine=8323079;
 //BA.debugLineNum = 8323079;BA.debugLine="End Sub";
return "";
}
public static String  _tabstripdatosanteriores_pageselected(int _position) throws Exception{
RDebugUtils.currentModule="frmdatosanteriores";
if (Debug.shouldDelegate(mostCurrent.activityBA, "tabstripdatosanteriores_pageselected"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "tabstripdatosanteriores_pageselected", new Object[] {_position}));}
RDebugUtils.currentLine=6750208;
 //BA.debugLineNum = 6750208;BA.debugLine="Sub tabStripDatosAnteriores_PageSelected (Position";
RDebugUtils.currentLine=6750209;
 //BA.debugLineNum = 6750209;BA.debugLine="If Position = 1 Then";
if (_position==1) { 
RDebugUtils.currentLine=6750210;
 //BA.debugLineNum = 6750210;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
RDebugUtils.currentLine=6750211;
 //BA.debugLineNum = 6750211;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
RDebugUtils.currentLine=6750212;
 //BA.debugLineNum = 6750212;BA.debugLine="foto3Borrar.RemoveView";
mostCurrent._foto3borrar.RemoveView();
RDebugUtils.currentLine=6750213;
 //BA.debugLineNum = 6750213;BA.debugLine="foto4Borrar.RemoveView";
mostCurrent._foto4borrar.RemoveView();
RDebugUtils.currentLine=6750214;
 //BA.debugLineNum = 6750214;BA.debugLine="ListarDatos";
_listardatos();
RDebugUtils.currentLine=6750215;
 //BA.debugLineNum = 6750215;BA.debugLine="NewMarcador = True";
_newmarcador = anywheresoftware.b4a.keywords.Common.True;
 }else 
{RDebugUtils.currentLine=6750216;
 //BA.debugLineNum = 6750216;BA.debugLine="Else if Position = 0 Then";
if (_position==0) { 
RDebugUtils.currentLine=6750217;
 //BA.debugLineNum = 6750217;BA.debugLine="lblUser.Text = Main.username";
mostCurrent._lbluser.setText(BA.ObjectToCharSequence(mostCurrent._main._username));
RDebugUtils.currentLine=6750218;
 //BA.debugLineNum = 6750218;BA.debugLine="txtEmail.Text = Main.strUserEmail";
mostCurrent._txtemail.setText(BA.ObjectToCharSequence(mostCurrent._main._struseremail));
RDebugUtils.currentLine=6750219;
 //BA.debugLineNum = 6750219;BA.debugLine="txtFullName.Text = Main.strUserName";
mostCurrent._txtfullname.setText(BA.ObjectToCharSequence(mostCurrent._main._strusername));
RDebugUtils.currentLine=6750220;
 //BA.debugLineNum = 6750220;BA.debugLine="txtLocation.Text = Main.strUserLocation";
mostCurrent._txtlocation.setText(BA.ObjectToCharSequence(mostCurrent._main._struserlocation));
RDebugUtils.currentLine=6750221;
 //BA.debugLineNum = 6750221;BA.debugLine="lblTipoUsuario.Text = Main.strUserTipoUsuario";
mostCurrent._lbltipousuario.setText(BA.ObjectToCharSequence(mostCurrent._main._strusertipousuario));
RDebugUtils.currentLine=6750222;
 //BA.debugLineNum = 6750222;BA.debugLine="txtOrg.Text = Main.strUserOrg";
mostCurrent._txtorg.setText(BA.ObjectToCharSequence(mostCurrent._main._struserorg));
 }}
;
RDebugUtils.currentLine=6750224;
 //BA.debugLineNum = 6750224;BA.debugLine="End Sub";
return "";
}
}