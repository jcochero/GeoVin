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

public class envioarchivos2 extends Activity implements B4AActivity{
	public static envioarchivos2 mostCurrent;
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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.envioarchivos2");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (envioarchivos2).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.envioarchivos2");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.envioarchivos2", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (envioarchivos2) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (envioarchivos2) Resume **");
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
		return envioarchivos2.class;
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
        BA.LogInfo("** Activity (envioarchivos2) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            envioarchivos2 mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (envioarchivos2) Resume **");
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
public anywheresoftware.b4a.objects.ListViewWrapper _lstresultados = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public static int _numfotosenviar = 0;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public static String _currentfilesent = "";
public anywheresoftware.b4a.objects.PanelWrapper _pnlemail = null;
public static String _returnsfrom = "";
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
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=20578304;
 //BA.debugLineNum = 20578304;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=20578308;
 //BA.debugLineNum = 20578308;BA.debugLine="lstResultados.Clear";
mostCurrent._lstresultados.Clear();
RDebugUtils.currentLine=20578309;
 //BA.debugLineNum = 20578309;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.Left =";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)));
RDebugUtils.currentLine=20578310;
 //BA.debugLineNum = 20578310;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.Gravity";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=20578311;
 //BA.debugLineNum = 20578311;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.Width =";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
RDebugUtils.currentLine=20578312;
 //BA.debugLineNum = 20578312;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.Height";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
RDebugUtils.currentLine=20578313;
 //BA.debugLineNum = 20578313;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.top = 1";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
RDebugUtils.currentLine=20578314;
 //BA.debugLineNum = 20578314;BA.debugLine="lstResultados.TwoLinesAndBitmap.ItemHeight = 50di";
mostCurrent._lstresultados.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=20578315;
 //BA.debugLineNum = 20578315;BA.debugLine="ListView1.SingleLineLayout.ItemHeight = 50dip";
mostCurrent._listview1.getSingleLineLayout().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=20578316;
 //BA.debugLineNum = 20578316;BA.debugLine="ListView1.SingleLineLayout.Label.TextColor = Colo";
mostCurrent._listview1.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=20578317;
 //BA.debugLineNum = 20578317;BA.debugLine="ListView1.SingleLineLayout.Label.TextSize = 32";
mostCurrent._listview1.getSingleLineLayout().Label.setTextSize((float) (32));
RDebugUtils.currentLine=20578318;
 //BA.debugLineNum = 20578318;BA.debugLine="ListView1.SingleLineLayout.Label.left = 20dip";
mostCurrent._listview1.getSingleLineLayout().Label.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
RDebugUtils.currentLine=20578319;
 //BA.debugLineNum = 20578319;BA.debugLine="lblEstado.Text = \"Se enviará el proyecto \" & Mai";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Se enviará el proyecto "+mostCurrent._main._evaluacionpath.substring((int) (mostCurrent._main._evaluacionpath.lastIndexOf("_")+1),mostCurrent._main._evaluacionpath.indexOf("-"))));
RDebugUtils.currentLine=20578321;
 //BA.debugLineNum = 20578321;BA.debugLine="If hc.IsInitialized = False Then";
if (_hc.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=20578322;
 //BA.debugLineNum = 20578322;BA.debugLine="hc.Initialize(\"hc\")";
_hc.Initialize("hc");
 };
RDebugUtils.currentLine=20578328;
 //BA.debugLineNum = 20578328;BA.debugLine="ListFiles";
_listfiles();
RDebugUtils.currentLine=20578329;
 //BA.debugLineNum = 20578329;BA.debugLine="End Sub";
return "";
}
public static String  _listfiles() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "listfiles"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "listfiles", null));}
RDebugUtils.currentLine=20840448;
 //BA.debugLineNum = 20840448;BA.debugLine="Sub ListFiles";
RDebugUtils.currentLine=20840449;
 //BA.debugLineNum = 20840449;BA.debugLine="ListView1.Color = Colors.Transparent";
mostCurrent._listview1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=20840450;
 //BA.debugLineNum = 20840450;BA.debugLine="ListView1.SingleLineLayout.Label.TextColor = Colo";
mostCurrent._listview1.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=20840451;
 //BA.debugLineNum = 20840451;BA.debugLine="ListView1.SingleLineLayout.Label.TextSize = 14";
mostCurrent._listview1.getSingleLineLayout().Label.setTextSize((float) (14));
RDebugUtils.currentLine=20840453;
 //BA.debugLineNum = 20840453;BA.debugLine="ListView1.AddSingleLine(\"Envío de la evaluación\")";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Envío de la evaluación"));
RDebugUtils.currentLine=20840455;
 //BA.debugLineNum = 20840455;BA.debugLine="numfotosenviar = 0";
_numfotosenviar = (int) (0);
RDebugUtils.currentLine=20840456;
 //BA.debugLineNum = 20840456;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
RDebugUtils.currentLine=20840457;
 //BA.debugLineNum = 20840457;BA.debugLine="ListView1.AddSingleLine(\"Envío: Foto #1\")";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Envío: Foto #1"));
RDebugUtils.currentLine=20840458;
 //BA.debugLineNum = 20840458;BA.debugLine="numfotosenviar = 1";
_numfotosenviar = (int) (1);
 };
RDebugUtils.currentLine=20840461;
 //BA.debugLineNum = 20840461;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
RDebugUtils.currentLine=20840462;
 //BA.debugLineNum = 20840462;BA.debugLine="ListView1.AddSingleLine(\"Envío: Foto #2\")";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Envío: Foto #2"));
RDebugUtils.currentLine=20840463;
 //BA.debugLineNum = 20840463;BA.debugLine="numfotosenviar = 2";
_numfotosenviar = (int) (2);
 };
RDebugUtils.currentLine=20840466;
 //BA.debugLineNum = 20840466;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
RDebugUtils.currentLine=20840467;
 //BA.debugLineNum = 20840467;BA.debugLine="ListView1.AddSingleLine(\"Envío: Foto #3\")";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Envío: Foto #3"));
RDebugUtils.currentLine=20840468;
 //BA.debugLineNum = 20840468;BA.debugLine="numfotosenviar = 3";
_numfotosenviar = (int) (3);
 };
RDebugUtils.currentLine=20840471;
 //BA.debugLineNum = 20840471;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
RDebugUtils.currentLine=20840472;
 //BA.debugLineNum = 20840472;BA.debugLine="ListView1.AddSingleLine(\"Envío: Foto #4\")";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Envío: Foto #4"));
RDebugUtils.currentLine=20840473;
 //BA.debugLineNum = 20840473;BA.debugLine="numfotosenviar = 4";
_numfotosenviar = (int) (4);
 };
RDebugUtils.currentLine=20840475;
 //BA.debugLineNum = 20840475;BA.debugLine="ListView1.AddSingleLine(\"Envío de marcadores\")";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Envío de marcadores"));
RDebugUtils.currentLine=20840476;
 //BA.debugLineNum = 20840476;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode}));}
RDebugUtils.currentLine=20774912;
 //BA.debugLineNum = 20774912;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=20774913;
 //BA.debugLineNum = 20774913;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=20774914;
 //BA.debugLineNum = 20774914;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=20774915;
 //BA.debugLineNum = 20774915;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
RDebugUtils.currentLine=20774917;
 //BA.debugLineNum = 20774917;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="envioarchivos2";
RDebugUtils.currentLine=20709376;
 //BA.debugLineNum = 20709376;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=20709378;
 //BA.debugLineNum = 20709378;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=20643840;
 //BA.debugLineNum = 20643840;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=20643841;
 //BA.debugLineNum = 20643841;BA.debugLine="If returnsfrom = \"perfil\" Then";
if ((mostCurrent._returnsfrom).equals("perfil")) { 
RDebugUtils.currentLine=20643842;
 //BA.debugLineNum = 20643842;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=20643843;
 //BA.debugLineNum = 20643843;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 };
RDebugUtils.currentLine=20643845;
 //BA.debugLineNum = 20643845;BA.debugLine="End Sub";
return "";
}
public static String  _btnemail_click() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnemail_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnemail_click", null));}
String _filename = "";
String _zipname = "";
com.AB.ABZipUnzip.ABZipUnzip _myzip = null;
anywheresoftware.b4a.phone.Phone.Email _message = null;
anywheresoftware.b4a.objects.collections.List _mylist = null;
int _i = 0;
String _myfile = "";
RDebugUtils.currentLine=21626880;
 //BA.debugLineNum = 21626880;BA.debugLine="Sub btnEmail_Click";
RDebugUtils.currentLine=21626883;
 //BA.debugLineNum = 21626883;BA.debugLine="If File.Exists(Main.savedir & \"/GeoVin/email\", \"\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/GeoVin/email","")==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=21626884;
 //BA.debugLineNum = 21626884;BA.debugLine="File.MakeDir(Main.savedir & \"/GeoVin/\", \"email\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(mostCurrent._main._savedir+"/GeoVin/","email");
 };
RDebugUtils.currentLine=21626888;
 //BA.debugLineNum = 21626888;BA.debugLine="File.MakeDir(Main.savedir & \"/GeoVin/email\",\"temp";
anywheresoftware.b4a.keywords.Common.File.MakeDir(mostCurrent._main._savedir+"/GeoVin/email","temp");
RDebugUtils.currentLine=21626891;
 //BA.debugLineNum = 21626891;BA.debugLine="Dim filename As String = Main.evaluacionpath & \".";
_filename = mostCurrent._main._evaluacionpath+".txt";
RDebugUtils.currentLine=21626892;
 //BA.debugLineNum = 21626892;BA.debugLine="File.Copy(Main.savedir & \"/GeoVin/\", filename , M";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/GeoVin/",_filename,mostCurrent._main._savedir+"/GeoVin/email/temp",_filename);
RDebugUtils.currentLine=21626893;
 //BA.debugLineNum = 21626893;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
RDebugUtils.currentLine=21626894;
 //BA.debugLineNum = 21626894;BA.debugLine="File.Copy(Main.savedir & \"/GeoVin/\", filename ,";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/GeoVin/",_filename,mostCurrent._main._savedir+"/GeoVin/email/temp",_filename);
 };
RDebugUtils.currentLine=21626896;
 //BA.debugLineNum = 21626896;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
RDebugUtils.currentLine=21626897;
 //BA.debugLineNum = 21626897;BA.debugLine="File.Copy(Main.savedir & \"/GeoVin/\", filename ,";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/GeoVin/",_filename,mostCurrent._main._savedir+"/GeoVin/email/temp",_filename);
 };
RDebugUtils.currentLine=21626899;
 //BA.debugLineNum = 21626899;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
RDebugUtils.currentLine=21626900;
 //BA.debugLineNum = 21626900;BA.debugLine="File.Copy(Main.savedir & \"/GeoVin/\", filename ,";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/GeoVin/",_filename,mostCurrent._main._savedir+"/GeoVin/email/temp",_filename);
 };
RDebugUtils.currentLine=21626902;
 //BA.debugLineNum = 21626902;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
RDebugUtils.currentLine=21626903;
 //BA.debugLineNum = 21626903;BA.debugLine="File.Copy(Main.savedir & \"/GeoVin/\", filename ,";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/GeoVin/",_filename,mostCurrent._main._savedir+"/GeoVin/email/temp",_filename);
 };
RDebugUtils.currentLine=21626907;
 //BA.debugLineNum = 21626907;BA.debugLine="Dim zipname As String = Main.evaluacionpath.Repla";
_zipname = mostCurrent._main._evaluacionpath.replace(".txt","");
RDebugUtils.currentLine=21626908;
 //BA.debugLineNum = 21626908;BA.debugLine="Dim myzip As ABZipUnzip";
_myzip = new com.AB.ABZipUnzip.ABZipUnzip();
RDebugUtils.currentLine=21626909;
 //BA.debugLineNum = 21626909;BA.debugLine="myzip.ABZipDirectory(Main.savedir & \"/GeoVin/emai";
_myzip.ABZipDirectory(mostCurrent._main._savedir+"/GeoVin/email/temp",_zipname);
RDebugUtils.currentLine=21626913;
 //BA.debugLineNum = 21626913;BA.debugLine="Dim Message As Email";
_message = new anywheresoftware.b4a.phone.Phone.Email();
RDebugUtils.currentLine=21626914;
 //BA.debugLineNum = 21626914;BA.debugLine="Message.To.Add(\"geovin@gmail.com\")";
_message.To.Add((Object)("geovin@gmail.com"));
RDebugUtils.currentLine=21626915;
 //BA.debugLineNum = 21626915;BA.debugLine="Message.Attachments.Add(Main.savedir & \"/GeoVin/";
_message.Attachments.Add((Object)(mostCurrent._main._savedir+"/GeoVin/email/"+_zipname));
RDebugUtils.currentLine=21626916;
 //BA.debugLineNum = 21626916;BA.debugLine="StartActivity(Message.GetIntent)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_message.GetIntent()));
RDebugUtils.currentLine=21626919;
 //BA.debugLineNum = 21626919;BA.debugLine="Dim mylist As List";
_mylist = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=21626920;
 //BA.debugLineNum = 21626920;BA.debugLine="mylist=File.ListFiles(Main.savedir & \"/GeoVin/ema";
_mylist = anywheresoftware.b4a.keywords.Common.File.ListFiles(mostCurrent._main._savedir+"/GeoVin/email/temp");
RDebugUtils.currentLine=21626921;
 //BA.debugLineNum = 21626921;BA.debugLine="For i= mylist.Size-1 To 0 Step -1";
{
final int step28 = -1;
final int limit28 = (int) (0);
_i = (int) (_mylist.getSize()-1) ;
for (;_i >= limit28 ;_i = _i + step28 ) {
RDebugUtils.currentLine=21626922;
 //BA.debugLineNum = 21626922;BA.debugLine="Dim MyFile As String =mylist.Get(i)";
_myfile = BA.ObjectToString(_mylist.Get(_i));
RDebugUtils.currentLine=21626923;
 //BA.debugLineNum = 21626923;BA.debugLine="File.Delete(Main.savedir & \"/GeoVin/email/temp";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/GeoVin/email/temp",_myfile);
 }
};
RDebugUtils.currentLine=21626926;
 //BA.debugLineNum = 21626926;BA.debugLine="File.Delete(Main.savedir & \"/GeoVin/email/temp\",";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/GeoVin/email/temp","");
RDebugUtils.currentLine=21626927;
 //BA.debugLineNum = 21626927;BA.debugLine="ToastMessageShow(\"El archivo es: \" & Main.savedir";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("El archivo es: "+mostCurrent._main._savedir+"/GeoVin/email/"+_zipname),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=21626929;
 //BA.debugLineNum = 21626929;BA.debugLine="End Sub";
return "";
}
public static String  _buscarpuntos() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "buscarpuntos"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "buscarpuntos", null));}
anywheresoftware.b4a.samples.httputils2.httpjob _getpuntos = null;
RDebugUtils.currentLine=21299200;
 //BA.debugLineNum = 21299200;BA.debugLine="Sub BuscarPuntos";
RDebugUtils.currentLine=21299201;
 //BA.debugLineNum = 21299201;BA.debugLine="Dim GetPuntos As HttpJob";
_getpuntos = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=21299202;
 //BA.debugLineNum = 21299202;BA.debugLine="GetPuntos.Initialize(\"GetPuntos\",Me)";
_getpuntos._initialize(processBA,"GetPuntos",envioarchivos2.getObject());
RDebugUtils.currentLine=21299203;
 //BA.debugLineNum = 21299203;BA.debugLine="GetPuntos.Download2(Main.serverPath & \"/connect/g";
_getpuntos._download2(mostCurrent._main._serverpath+"/connect/getpuntos.php",new String[]{"user_id",mostCurrent._main._struserid});
RDebugUtils.currentLine=21299204;
 //BA.debugLineNum = 21299204;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "button1_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "button1_click", null));}
String _archivonotas = "";
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _writer = null;
String _notastxt = "";
RDebugUtils.currentLine=20905984;
 //BA.debugLineNum = 20905984;BA.debugLine="Sub Button1_Click";
RDebugUtils.currentLine=20905985;
 //BA.debugLineNum = 20905985;BA.debugLine="Button1.Enabled = False";
mostCurrent._button1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=20905986;
 //BA.debugLineNum = 20905986;BA.debugLine="Button1.Text = \"Enviando... aguarde\"";
mostCurrent._button1.setText(BA.ObjectToCharSequence("Enviando... aguarde"));
RDebugUtils.currentLine=20905987;
 //BA.debugLineNum = 20905987;BA.debugLine="ProgressDialogShow2(\"Enviando proyecto, esto tard";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando proyecto, esto tardará unos minutos"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=20905990;
 //BA.debugLineNum = 20905990;BA.debugLine="Dim archivonotas As String";
_archivonotas = "";
RDebugUtils.currentLine=20905991;
 //BA.debugLineNum = 20905991;BA.debugLine="archivonotas = Main.username & \"_\" & Main.Idproye";
_archivonotas = mostCurrent._main._username+"_"+BA.NumberToString(mostCurrent._main._idproyecto)+"_"+"-Notas.txt";
RDebugUtils.currentLine=20905992;
 //BA.debugLineNum = 20905992;BA.debugLine="Dim Writer As TextWriter";
_writer = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
RDebugUtils.currentLine=20905993;
 //BA.debugLineNum = 20905993;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\",";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_archivonotas)) { 
RDebugUtils.currentLine=20905994;
 //BA.debugLineNum = 20905994;BA.debugLine="Dim notastxt As String";
_notastxt = "";
RDebugUtils.currentLine=20905995;
 //BA.debugLineNum = 20905995;BA.debugLine="notastxt = File.ReadString(File.DirRootExternal";
_notastxt = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_archivonotas);
RDebugUtils.currentLine=20905996;
 //BA.debugLineNum = 20905996;BA.debugLine="Writer.Initialize(File.OpenOutput(File.DirRootEx";
_writer.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._evaluacionpath+".txt",anywheresoftware.b4a.keywords.Common.True).getObject()));
RDebugUtils.currentLine=20905997;
 //BA.debugLineNum = 20905997;BA.debugLine="Writer.WriteLine(notastxt)";
_writer.WriteLine(_notastxt);
RDebugUtils.currentLine=20905998;
 //BA.debugLineNum = 20905998;BA.debugLine="Writer.Close";
_writer.Close();
RDebugUtils.currentLine=20906000;
 //BA.debugLineNum = 20906000;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/\", a";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_archivonotas);
 };
RDebugUtils.currentLine=20906002;
 //BA.debugLineNum = 20906002;BA.debugLine="ComenzarEnvio";
_comenzarenvio();
RDebugUtils.currentLine=20906003;
 //BA.debugLineNum = 20906003;BA.debugLine="End Sub";
return "";
}
public static String  _comenzarenvio() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "comenzarenvio"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "comenzarenvio", null));}
anywheresoftware.b4a.objects.collections.List _files = null;
cepave.geovin.uploadfiles._filedata _fd = null;
anywheresoftware.b4a.objects.collections.Map _nv = null;
anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest _req = null;
RDebugUtils.currentLine=20971520;
 //BA.debugLineNum = 20971520;BA.debugLine="Sub ComenzarEnvio()";
RDebugUtils.currentLine=20971522;
 //BA.debugLineNum = 20971522;BA.debugLine="Dim files As List";
_files = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=20971523;
 //BA.debugLineNum = 20971523;BA.debugLine="files.Initialize";
_files.Initialize();
RDebugUtils.currentLine=20971526;
 //BA.debugLineNum = 20971526;BA.debugLine="Main.deviceID = utilidades.getdeviceID";
mostCurrent._main._deviceid = mostCurrent._utilidades._getdeviceid(mostCurrent.activityBA);
RDebugUtils.currentLine=20971530;
 //BA.debugLineNum = 20971530;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=20971531;
 //BA.debugLineNum = 20971531;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=20971532;
 //BA.debugLineNum = 20971532;BA.debugLine="fd.Dir = Main.savedir & \"/GeoVin/\"";
_fd.Dir = mostCurrent._main._savedir+"/GeoVin/";
RDebugUtils.currentLine=20971533;
 //BA.debugLineNum = 20971533;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=20971534;
 //BA.debugLineNum = 20971534;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=20971535;
 //BA.debugLineNum = 20971535;BA.debugLine="fd.FileName =  Main.evaluacionpath & \".txt\"";
_fd.FileName = mostCurrent._main._evaluacionpath+".txt";
RDebugUtils.currentLine=20971536;
 //BA.debugLineNum = 20971536;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=20971539;
 //BA.debugLineNum = 20971539;BA.debugLine="Dim NV As Map";
_nv = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=20971540;
 //BA.debugLineNum = 20971540;BA.debugLine="NV.Initialize";
_nv.Initialize();
RDebugUtils.currentLine=20971541;
 //BA.debugLineNum = 20971541;BA.debugLine="NV.Put(\"usuario\", Main.username)";
_nv.Put((Object)("usuario"),(Object)(mostCurrent._main._username));
RDebugUtils.currentLine=20971542;
 //BA.debugLineNum = 20971542;BA.debugLine="NV.Put(\"eval\", Main.evaluacionpath)";
_nv.Put((Object)("eval"),(Object)(mostCurrent._main._evaluacionpath));
RDebugUtils.currentLine=20971543;
 //BA.debugLineNum = 20971543;BA.debugLine="NV.Put(\"action\", \"upload\")";
_nv.Put((Object)("action"),(Object)("upload"));
RDebugUtils.currentLine=20971544;
 //BA.debugLineNum = 20971544;BA.debugLine="NV.Put(\"usr\", \"juacochero\")";
_nv.Put((Object)("usr"),(Object)("juacochero"));
RDebugUtils.currentLine=20971545;
 //BA.debugLineNum = 20971545;BA.debugLine="NV.Put(\"pss\", \"vacagorda\")";
_nv.Put((Object)("pss"),(Object)("vacagorda"));
RDebugUtils.currentLine=20971546;
 //BA.debugLineNum = 20971546;BA.debugLine="NV.Put(\"deviceID\", Main.deviceID)";
_nv.Put((Object)("deviceID"),(Object)(mostCurrent._main._deviceid));
RDebugUtils.currentLine=20971548;
 //BA.debugLineNum = 20971548;BA.debugLine="Dim req As OkHttpRequest";
_req = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
RDebugUtils.currentLine=20971549;
 //BA.debugLineNum = 20971549;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverPa";
_req = mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=20971550;
 //BA.debugLineNum = 20971550;BA.debugLine="hc.Execute(req, 1)";
_hc.Execute(processBA,_req,(int) (1));
RDebugUtils.currentLine=20971554;
 //BA.debugLineNum = 20971554;BA.debugLine="If Main.fotopath0  <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
RDebugUtils.currentLine=20971555;
 //BA.debugLineNum = 20971555;BA.debugLine="fd.FileName = Main.fotopath0 & \".jpg\"";
_fd.FileName = mostCurrent._main._fotopath0+".jpg";
RDebugUtils.currentLine=20971556;
 //BA.debugLineNum = 20971556;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=20971557;
 //BA.debugLineNum = 20971557;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=20971558;
 //BA.debugLineNum = 20971558;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=20971559;
 //BA.debugLineNum = 20971559;BA.debugLine="hc.Execute(req, 2)";
_hc.Execute(processBA,_req,(int) (2));
 };
RDebugUtils.currentLine=20971561;
 //BA.debugLineNum = 20971561;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
RDebugUtils.currentLine=20971562;
 //BA.debugLineNum = 20971562;BA.debugLine="fd.FileName = Main.fotopath1 & \".jpg\"";
_fd.FileName = mostCurrent._main._fotopath1+".jpg";
RDebugUtils.currentLine=20971563;
 //BA.debugLineNum = 20971563;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=20971564;
 //BA.debugLineNum = 20971564;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=20971565;
 //BA.debugLineNum = 20971565;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=20971566;
 //BA.debugLineNum = 20971566;BA.debugLine="hc.Execute(req, 3)";
_hc.Execute(processBA,_req,(int) (3));
 };
RDebugUtils.currentLine=20971568;
 //BA.debugLineNum = 20971568;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
RDebugUtils.currentLine=20971569;
 //BA.debugLineNum = 20971569;BA.debugLine="fd.FileName = Main.fotopath2 & \".jpg\"";
_fd.FileName = mostCurrent._main._fotopath2+".jpg";
RDebugUtils.currentLine=20971570;
 //BA.debugLineNum = 20971570;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=20971571;
 //BA.debugLineNum = 20971571;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=20971572;
 //BA.debugLineNum = 20971572;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=20971573;
 //BA.debugLineNum = 20971573;BA.debugLine="hc.Execute(req, 4)";
_hc.Execute(processBA,_req,(int) (4));
 };
RDebugUtils.currentLine=20971575;
 //BA.debugLineNum = 20971575;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
RDebugUtils.currentLine=20971576;
 //BA.debugLineNum = 20971576;BA.debugLine="fd.FileName = Main.fotopath3 & \".jpg\"";
_fd.FileName = mostCurrent._main._fotopath3+".jpg";
RDebugUtils.currentLine=20971577;
 //BA.debugLineNum = 20971577;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=20971578;
 //BA.debugLineNum = 20971578;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=20971579;
 //BA.debugLineNum = 20971579;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = mostCurrent._uploadfiles._createpostrequest(mostCurrent.activityBA,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=20971580;
 //BA.debugLineNum = 20971580;BA.debugLine="hc.Execute(req, 5)";
_hc.Execute(processBA,_req,(int) (5));
 };
RDebugUtils.currentLine=20971584;
 //BA.debugLineNum = 20971584;BA.debugLine="End Sub";
return "";
}
public static String  _cargafelicitaciones() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargafelicitaciones"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cargafelicitaciones", null));}
String _msj = "";
RDebugUtils.currentLine=21495808;
 //BA.debugLineNum = 21495808;BA.debugLine="Sub CargaFelicitaciones";
RDebugUtils.currentLine=21495809;
 //BA.debugLineNum = 21495809;BA.debugLine="Dim msj As String";
_msj = "";
RDebugUtils.currentLine=21495810;
 //BA.debugLineNum = 21495810;BA.debugLine="msj = Msgbox2(\"Has enviado exitosamente los datos";
_msj = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Has enviado exitosamente los datos, y has sumado un punto! Cuando el dato sea validado por un revisor especializado, sumarás puntos extra!"),BA.ObjectToCharSequence("Gracias!"),"Ok","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
RDebugUtils.currentLine=21495811;
 //BA.debugLineNum = 21495811;BA.debugLine="If msj = DialogResponse.POSITIVE Then";
if ((_msj).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
RDebugUtils.currentLine=21495813;
 //BA.debugLineNum = 21495813;BA.debugLine="CheckNivel";
_checknivel();
RDebugUtils.currentLine=21495814;
 //BA.debugLineNum = 21495814;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=21495816;
 //BA.debugLineNum = 21495816;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmprincipal.getObject()));
 };
RDebugUtils.currentLine=21495818;
 //BA.debugLineNum = 21495818;BA.debugLine="End Sub";
return "";
}
public static String  _checknivel() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "checknivel"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "checknivel", null));}
RDebugUtils.currentLine=21561344;
 //BA.debugLineNum = 21561344;BA.debugLine="Sub CheckNivel";
RDebugUtils.currentLine=21561361;
 //BA.debugLineNum = 21561361;BA.debugLine="End Sub";
return "";
}
public static String  _enviarmarcadores() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "enviarmarcadores"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "enviarmarcadores", null));}
RDebugUtils.currentLine=21233664;
 //BA.debugLineNum = 21233664;BA.debugLine="Sub EnviarMarcadores";
RDebugUtils.currentLine=21233670;
 //BA.debugLineNum = 21233670;BA.debugLine="End Sub";
return "";
}
public static String  _enviarpuntos() throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "enviarpuntos"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "enviarpuntos", null));}
RDebugUtils.currentLine=21364736;
 //BA.debugLineNum = 21364736;BA.debugLine="Sub EnviarPuntos";
RDebugUtils.currentLine=21364744;
 //BA.debugLineNum = 21364744;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,String _reason,int _statuscode,int _taskid) throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "hc_responseerror"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "hc_responseerror", new Object[] {_response,_reason,_statuscode,_taskid}));}
RDebugUtils.currentLine=21037056;
 //BA.debugLineNum = 21037056;BA.debugLine="Sub hc_ResponseError (Response As OkHttpResponse,";
RDebugUtils.currentLine=21037058;
 //BA.debugLineNum = 21037058;BA.debugLine="If Response <> Null Then";
if (_response!= null) { 
RDebugUtils.currentLine=21037059;
 //BA.debugLineNum = 21037059;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",LoadBi";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(""),BA.ObjectToCharSequence(""),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"crosssmall.png").getObject()),(Object)(""));
RDebugUtils.currentLine=21037060;
 //BA.debugLineNum = 21037060;BA.debugLine="pnlEmail.Visible = True";
mostCurrent._pnlemail.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=21037061;
 //BA.debugLineNum = 21037061;BA.debugLine="pnlEmail.BringToFront";
mostCurrent._pnlemail.BringToFront();
RDebugUtils.currentLine=21037062;
 //BA.debugLineNum = 21037062;BA.debugLine="Button1.Visible = False";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=21037063;
 //BA.debugLineNum = 21037063;BA.debugLine="If TaskId = 1 Then";
if (_taskid==1) { 
RDebugUtils.currentLine=21037064;
 //BA.debugLineNum = 21037064;BA.debugLine="ToastMessageShow(\"Error enviando evaluación\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando evaluación"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=21037065;
 //BA.debugLineNum = 21037065;BA.debugLine="Else If TaskId = 2 Then";
if (_taskid==2) { 
RDebugUtils.currentLine=21037066;
 //BA.debugLineNum = 21037066;BA.debugLine="ToastMessageShow(\"Error enviando Foto #1\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando Foto #1"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=21037067;
 //BA.debugLineNum = 21037067;BA.debugLine="Else If TaskId = 3 Then";
if (_taskid==3) { 
RDebugUtils.currentLine=21037068;
 //BA.debugLineNum = 21037068;BA.debugLine="ToastMessageShow(\"Error enviando Foto #2\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando Foto #2"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=21037069;
 //BA.debugLineNum = 21037069;BA.debugLine="Else If TaskId = 4 Then";
if (_taskid==4) { 
RDebugUtils.currentLine=21037070;
 //BA.debugLineNum = 21037070;BA.debugLine="ToastMessageShow(\"Error enviando Foto #3\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando Foto #3"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=21037071;
 //BA.debugLineNum = 21037071;BA.debugLine="Else If TaskId = 5 Then";
if (_taskid==5) { 
RDebugUtils.currentLine=21037072;
 //BA.debugLineNum = 21037072;BA.debugLine="ToastMessageShow(\"Error enviando Foto #4\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando Foto #4"),anywheresoftware.b4a.keywords.Common.False);
 }}}}}
;
 };
RDebugUtils.currentLine=21037075;
 //BA.debugLineNum = 21037075;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,int _taskid) throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "hc_responsesuccess"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "hc_responsesuccess", new Object[] {_response,_taskid}));}
RDebugUtils.currentLine=21102592;
 //BA.debugLineNum = 21102592;BA.debugLine="Sub hc_ResponseSuccess (Response As OkHttpResponse";
RDebugUtils.currentLine=21102593;
 //BA.debugLineNum = 21102593;BA.debugLine="out.InitializeToBytesArray(0) ' I expect less tha";
_out.InitializeToBytesArray((int) (0));
RDebugUtils.currentLine=21102594;
 //BA.debugLineNum = 21102594;BA.debugLine="Response.GetAsynchronously(\"Response\", out, Tr";
_response.GetAsynchronously(processBA,"Response",(java.io.OutputStream)(_out.getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
RDebugUtils.currentLine=21102598;
 //BA.debugLineNum = 21102598;BA.debugLine="If TaskId = 1 Then";
if (_taskid==1) { 
RDebugUtils.currentLine=21102599;
 //BA.debugLineNum = 21102599;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=21102600;
 //BA.debugLineNum = 21102600;BA.debugLine="Else If TaskId = 2 Then";
if (_taskid==2) { 
RDebugUtils.currentLine=21102601;
 //BA.debugLineNum = 21102601;BA.debugLine="ToastMessageShow(\"Foto #1 enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Foto #1 enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=21102602;
 //BA.debugLineNum = 21102602;BA.debugLine="Else If TaskId = 3 Then";
if (_taskid==3) { 
RDebugUtils.currentLine=21102603;
 //BA.debugLineNum = 21102603;BA.debugLine="ToastMessageShow(\"Foto #2 enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Foto #2 enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=21102604;
 //BA.debugLineNum = 21102604;BA.debugLine="Else If TaskId = 4 Then";
if (_taskid==4) { 
RDebugUtils.currentLine=21102605;
 //BA.debugLineNum = 21102605;BA.debugLine="ToastMessageShow(\"Foto #3 enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Foto #3 enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=21102606;
 //BA.debugLineNum = 21102606;BA.debugLine="Else If TaskId = 5 Then";
if (_taskid==5) { 
RDebugUtils.currentLine=21102607;
 //BA.debugLineNum = 21102607;BA.debugLine="ToastMessageShow(\"Foto #4 enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Foto #4 enviada"),anywheresoftware.b4a.keywords.Common.False);
 }}}}}
;
RDebugUtils.currentLine=21102610;
 //BA.debugLineNum = 21102610;BA.debugLine="If numfotosenviar = TaskId - 1 Then";
if (_numfotosenviar==_taskid-1) { 
RDebugUtils.currentLine=21102611;
 //BA.debugLineNum = 21102611;BA.debugLine="EnviarMarcadores";
_enviarmarcadores();
 };
RDebugUtils.currentLine=21102613;
 //BA.debugLineNum = 21102613;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "jobdone"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "jobdone", new Object[] {_job}));}
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
String _filename = "";
String _filenotas = "";
RDebugUtils.currentLine=21430272;
 //BA.debugLineNum = 21430272;BA.debugLine="Sub JobDone (Job As HttpJob)";
RDebugUtils.currentLine=21430273;
 //BA.debugLineNum = 21430273;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=21430274;
 //BA.debugLineNum = 21430274;BA.debugLine="Dim ret As String";
_ret = "";
RDebugUtils.currentLine=21430275;
 //BA.debugLineNum = 21430275;BA.debugLine="Dim act As String";
_act = "";
RDebugUtils.currentLine=21430276;
 //BA.debugLineNum = 21430276;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring();
RDebugUtils.currentLine=21430277;
 //BA.debugLineNum = 21430277;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=21430278;
 //BA.debugLineNum = 21430278;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
RDebugUtils.currentLine=21430279;
 //BA.debugLineNum = 21430279;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=21430282;
 //BA.debugLineNum = 21430282;BA.debugLine="If Job.JobName = \"EnvioMarcador\" Then";
if ((_job._jobname).equals("EnvioMarcador")) { 
RDebugUtils.currentLine=21430283;
 //BA.debugLineNum = 21430283;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
RDebugUtils.currentLine=21430284;
 //BA.debugLineNum = 21430284;BA.debugLine="ToastMessageShow(\"Error en la carga de marcad";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de marcadores"),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=21430285;
 //BA.debugLineNum = 21430285;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(""),BA.ObjectToCharSequence(""),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"crosssmall.png").getObject()),(Object)("FalloMarcador"));
RDebugUtils.currentLine=21430286;
 //BA.debugLineNum = 21430286;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else 
{RDebugUtils.currentLine=21430287;
 //BA.debugLineNum = 21430287;BA.debugLine="Else If act = \"Marcadores\" Then";
if ((_act).equals("Marcadores")) { 
RDebugUtils.currentLine=21430288;
 //BA.debugLineNum = 21430288;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",Loa";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(""),BA.ObjectToCharSequence(""),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"checksmall.png").getObject()),(Object)(""));
RDebugUtils.currentLine=21430289;
 //BA.debugLineNum = 21430289;BA.debugLine="BuscarPuntos";
_buscarpuntos();
 }}
;
 };
RDebugUtils.currentLine=21430294;
 //BA.debugLineNum = 21430294;BA.debugLine="If Job.JobName = \"GetPuntos\" Then";
if ((_job._jobname).equals("GetPuntos")) { 
RDebugUtils.currentLine=21430295;
 //BA.debugLineNum = 21430295;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
RDebugUtils.currentLine=21430296;
 //BA.debugLineNum = 21430296;BA.debugLine="ToastMessageShow(\"Los puntos del usuario no se";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Los puntos del usuario no se pudieron actualizar"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=21430297;
 //BA.debugLineNum = 21430297;BA.debugLine="Else If act = \"GetPuntos OK\" Then";
if ((_act).equals("GetPuntos OK")) { 
RDebugUtils.currentLine=21430298;
 //BA.debugLineNum = 21430298;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=21430299;
 //BA.debugLineNum = 21430299;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
RDebugUtils.currentLine=21430328;
 //BA.debugLineNum = 21430328;BA.debugLine="ToastMessageShow(\"Enviando puntos\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enviando puntos"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=21430329;
 //BA.debugLineNum = 21430329;BA.debugLine="EnviarPuntos";
_enviarpuntos();
 }}
;
 };
RDebugUtils.currentLine=21430332;
 //BA.debugLineNum = 21430332;BA.debugLine="If Job.JobName = \"EnvioPuntos\" Then";
if ((_job._jobname).equals("EnvioPuntos")) { 
RDebugUtils.currentLine=21430333;
 //BA.debugLineNum = 21430333;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
RDebugUtils.currentLine=21430334;
 //BA.debugLineNum = 21430334;BA.debugLine="ToastMessageShow(\"No se pudieron cargar los p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudieron cargar los puntos correctamente"),anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=21430335;
 //BA.debugLineNum = 21430335;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else 
{RDebugUtils.currentLine=21430336;
 //BA.debugLineNum = 21430336;BA.debugLine="Else If act = \"Puntos Cargados\" Then";
if ((_act).equals("Puntos Cargados")) { 
RDebugUtils.currentLine=21430340;
 //BA.debugLineNum = 21430340;BA.debugLine="Dim filename As String = Main.evaluacionpath";
_filename = mostCurrent._main._evaluacionpath+".txt";
RDebugUtils.currentLine=21430341;
 //BA.debugLineNum = 21430341;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\",";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=21430344;
 //BA.debugLineNum = 21430344;BA.debugLine="Dim filenotas As String";
_filenotas = "";
RDebugUtils.currentLine=21430345;
 //BA.debugLineNum = 21430345;BA.debugLine="filenotas = Main.evaluacionpath.SubString2(0,";
_filenotas = mostCurrent._main._evaluacionpath.substring((int) (0),mostCurrent._main._evaluacionpath.indexOf("_",(int) (mostCurrent._main._evaluacionpath.indexOf("_")+1)))+"_-Notas.txt";
RDebugUtils.currentLine=21430347;
 //BA.debugLineNum = 21430347;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVi";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filenotas)) { 
RDebugUtils.currentLine=21430348;
 //BA.debugLineNum = 21430348;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\",";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filenotas,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filenotas);
RDebugUtils.currentLine=21430349;
 //BA.debugLineNum = 21430349;BA.debugLine="File.delete(File.DirRootExternal & \"/GeoVin/";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filenotas);
 };
RDebugUtils.currentLine=21430352;
 //BA.debugLineNum = 21430352;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/\"";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
RDebugUtils.currentLine=21430355;
 //BA.debugLineNum = 21430355;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
RDebugUtils.currentLine=21430356;
 //BA.debugLineNum = 21430356;BA.debugLine="filename = Main.fotopath0 & \".jpg\"";
_filename = mostCurrent._main._fotopath0+".jpg";
RDebugUtils.currentLine=21430357;
 //BA.debugLineNum = 21430357;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\",";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=21430358;
 //BA.debugLineNum = 21430358;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
 };
RDebugUtils.currentLine=21430360;
 //BA.debugLineNum = 21430360;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
RDebugUtils.currentLine=21430361;
 //BA.debugLineNum = 21430361;BA.debugLine="filename = Main.fotopath1 & \".jpg\"";
_filename = mostCurrent._main._fotopath1+".jpg";
RDebugUtils.currentLine=21430362;
 //BA.debugLineNum = 21430362;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\",";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=21430363;
 //BA.debugLineNum = 21430363;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
 };
RDebugUtils.currentLine=21430365;
 //BA.debugLineNum = 21430365;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
RDebugUtils.currentLine=21430366;
 //BA.debugLineNum = 21430366;BA.debugLine="filename = Main.fotopath2 & \".jpg\"";
_filename = mostCurrent._main._fotopath2+".jpg";
RDebugUtils.currentLine=21430367;
 //BA.debugLineNum = 21430367;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\",";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=21430368;
 //BA.debugLineNum = 21430368;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
 };
RDebugUtils.currentLine=21430370;
 //BA.debugLineNum = 21430370;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
RDebugUtils.currentLine=21430371;
 //BA.debugLineNum = 21430371;BA.debugLine="filename = Main.fotopath3 & \".jpg\"";
_filename = mostCurrent._main._fotopath3+".jpg";
RDebugUtils.currentLine=21430372;
 //BA.debugLineNum = 21430372;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\",";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=21430373;
 //BA.debugLineNum = 21430373;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
 };
RDebugUtils.currentLine=21430375;
 //BA.debugLineNum = 21430375;BA.debugLine="CargaFelicitaciones";
_cargafelicitaciones();
RDebugUtils.currentLine=21430376;
 //BA.debugLineNum = 21430376;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }}
;
 };
 }else {
RDebugUtils.currentLine=21430380;
 //BA.debugLineNum = 21430380;BA.debugLine="ToastMessageShow(\"Error: \" & Job.ErrorMessage, T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: "+_job._errormessage),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=21430381;
 //BA.debugLineNum = 21430381;BA.debugLine="Msgbox(Job.ErrorMessage, \"Oops!\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_job._errormessage),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 };
RDebugUtils.currentLine=21430383;
 //BA.debugLineNum = 21430383;BA.debugLine="Job.Release";
_job._release();
RDebugUtils.currentLine=21430384;
 //BA.debugLineNum = 21430384;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(boolean _success,int _taskid) throws Exception{
RDebugUtils.currentModule="envioarchivos2";
if (Debug.shouldDelegate(mostCurrent.activityBA, "response_streamfinish"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "response_streamfinish", new Object[] {_success,_taskid}));}
byte[] _another_buffer = null;
RDebugUtils.currentLine=21168128;
 //BA.debugLineNum = 21168128;BA.debugLine="Sub Response_StreamFinish (Success As Boolean, Tas";
RDebugUtils.currentLine=21168129;
 //BA.debugLineNum = 21168129;BA.debugLine="Dim another_buffer () As Byte";
_another_buffer = new byte[(int) (0)];
;
RDebugUtils.currentLine=21168130;
 //BA.debugLineNum = 21168130;BA.debugLine="another_buffer = out.ToBytesArray";
_another_buffer = _out.ToBytesArray();
RDebugUtils.currentLine=21168131;
 //BA.debugLineNum = 21168131;BA.debugLine="Log (BytesToString(another_buffer, 0, another_";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.BytesToString(_another_buffer,(int) (0),_another_buffer.length,"UTF8"));
RDebugUtils.currentLine=21168132;
 //BA.debugLineNum = 21168132;BA.debugLine="End Sub";
return "";
}
}