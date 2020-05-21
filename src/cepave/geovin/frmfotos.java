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

public class frmfotos extends Activity implements B4AActivity{
	public static frmfotos mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmfotos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmfotos).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmfotos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmfotos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmfotos) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmfotos) Resume **");
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
		return frmfotos.class;
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
        BA.LogInfo("** Activity (frmfotos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            frmfotos mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmfotos) Resume **");
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
public static com.spinter.uploadfilephp.UploadFilePhp _up1 = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up2 = null;
public static String _currentfoto = "";
public static anywheresoftware.b4a.objects.Timer _timerenvio = null;
public static boolean _timeron = false;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto_ventral = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto_dorsal = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuar = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopcion1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopcion2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopcion3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnfoto1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnfoto2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhabitat = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar2 = null;
public static String _foto1 = "";
public static String _foto2 = "";
public static String _foto3 = "";
public static String _foto4 = "";
public static int _totalfotos = 0;
public static boolean _foto1sent = false;
public static boolean _foto2sent = false;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgdorsal = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgventral = null;
public static int _fotosenviadas = 0;
public anywheresoftware.b4a.phone.Phone.PhoneWakeState _pw = null;
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelhabitat = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spndomicilio = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnperidomicilio = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnsilvestre = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinstruccioneshabitat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldomicilio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblperidomicilio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsilvestre = null;
public static boolean _foto_dorsal_existe = false;
public static boolean _foto_ventral_existe = false;
public static boolean _habitat_existe = false;
public anywheresoftware.b4a.objects.LabelWrapper _lblinstrucciones = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblreportepublico = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkprivado = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.downloadservice _downloadservice = null;
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
public cepave.geovin.frmidentificacionnew _frmidentificacionnew = null;
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
 //BA.debugLineNum = 67;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 68;BA.debugLine="Activity.LoadLayout(\"layFotos\")";
mostCurrent._activity.LoadLayout("layFotos",mostCurrent.activityBA);
 //BA.debugLineNum = 69;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 72;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 73;BA.debugLine="TimerOn = False";
_timeron = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 76;BA.debugLine="btnFoto1.Enabled = True";
mostCurrent._btnfoto1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 77;BA.debugLine="btnFoto1.Visible = True";
mostCurrent._btnfoto1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 78;BA.debugLine="Foto_Dorsal.Visible = True";
mostCurrent._foto_dorsal.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 79;BA.debugLine="imgDorsal.Visible = True";
mostCurrent._imgdorsal.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 80;BA.debugLine="btnFoto2.Enabled = True";
mostCurrent._btnfoto2.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 81;BA.debugLine="btnFoto2.Visible = True";
mostCurrent._btnfoto2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 82;BA.debugLine="Foto_Ventral.Visible = True";
mostCurrent._foto_ventral.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 83;BA.debugLine="imgVentral.Visible = True";
mostCurrent._imgventral.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 84;BA.debugLine="btnHabitat.Enabled = True";
mostCurrent._btnhabitat.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 85;BA.debugLine="btnHabitat.Visible = True";
mostCurrent._btnhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 86;BA.debugLine="textOpcion3.Visible = True";
mostCurrent._textopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 87;BA.debugLine="chkPrivado.Visible = True";
mostCurrent._chkprivado.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 88;BA.debugLine="lblReportePublico.Visible = True";
mostCurrent._lblreportepublico.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 107;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 108;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 109;BA.debugLine="If btnContinuar.Text = \"Enviando (cancelar envío";
if ((mostCurrent._btncontinuar.getText()).equals("Enviando (cancelar envío)") || (mostCurrent._btncontinuar.getText()).equals("Uploading (cancel upload)")) { 
 //BA.debugLineNum = 110;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 111;BA.debugLine="If Msgbox2(\"Volver al inicio? Esto cancelará e";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Volver al inicio? Esto cancelará el envío actual"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 112;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 113;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 114;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 115;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 }else {
 //BA.debugLineNum = 117;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 120;BA.debugLine="If Msgbox2(\"Back to the start? This will cance";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Back to the start? This will cancel the current upload"),BA.ObjectToCharSequence("EXIT"),"Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 121;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 122;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 123;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 124;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 }else {
 //BA.debugLineNum = 126;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 }else {
 //BA.debugLineNum = 130;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 131;BA.debugLine="If Msgbox2(\"Volver al inicio?\", \"SALIR\", \"Si\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Volver al inicio?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 132;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 133;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 134;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 135;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 }else {
 //BA.debugLineNum = 137;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 140;BA.debugLine="If Msgbox2(\"Back to the start?\", \"EXIT\", \"Yes\"";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Back to the start?"),BA.ObjectToCharSequence("EXIT"),"Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 141;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 142;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 143;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 144;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 }else {
 //BA.debugLineNum = 146;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 };
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 101;BA.debugLine="If UserClosed Then";
if (_userclosed) { 
 //BA.debugLineNum = 102;BA.debugLine="pw.ReleaseKeepAlive";
mostCurrent._pw.ReleaseKeepAlive();
 };
 //BA.debugLineNum = 104;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 92;BA.debugLine="If TimerOn = True Then";
if (_timeron==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 93;BA.debugLine="pw.KeepAlive(True)";
mostCurrent._pw.KeepAlive(processBA,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 94;BA.debugLine="TimerEnvio.Enabled = True";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 97;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 98;BA.debugLine="PreviewFotos";
_previewfotos();
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuar_click() throws Exception{
String _msg = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 510;BA.debugLine="Sub btnContinuar_Click";
 //BA.debugLineNum = 513;BA.debugLine="If btnContinuar.Text = \"Enviando (cancelar envío)";
if ((mostCurrent._btncontinuar.getText()).equals("Enviando (cancelar envío)") || (mostCurrent._btncontinuar.getText()).equals("Uploading (cancel upload)")) { 
 //BA.debugLineNum = 514;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 515;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 516;BA.debugLine="msg = Msgbox2(\"Se están enviando las fotografía";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Se están enviando las fotografías, desea cancelar?"),BA.ObjectToCharSequence("Cancelar?"),"Si, cancelar","No!","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 518;BA.debugLine="msg = Msgbox2(\"Photos are being uploaded, do yo";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Photos are being uploaded, do you want to cancel?"),BA.ObjectToCharSequence("Cancel?"),"Yes, cancel","No!","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 520;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 521;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 522;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 //BA.debugLineNum = 523;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 524;BA.debugLine="TimerOn = False";
_timeron = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 525;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 526;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 527;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmprincipal.getObject()));
 //BA.debugLineNum = 528;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 533;BA.debugLine="If foto_dorsal_existe = False Or foto_ventral_exi";
if (_foto_dorsal_existe==anywheresoftware.b4a.keywords.Common.False || _foto_ventral_existe==anywheresoftware.b4a.keywords.Common.False || _habitat_existe==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 534;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 535;BA.debugLine="ToastMessageShow(\"Recuerde sacar las dos fotos";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Recuerde sacar las dos fotos y seleccionar el hábitat"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 537;BA.debugLine="ToastMessageShow(\"Remember to take both fotos a";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Remember to take both fotos and select the habitat"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 539;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 544;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 545;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 546;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 547;BA.debugLine="If chkPrivado.Checked = True Then";
if (mostCurrent._chkprivado.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 548;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","privado",(Object)("no"),_map1);
 }else {
 //BA.debugLineNum = 550;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","privado",(Object)("si"),_map1);
 };
 //BA.debugLineNum = 552;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","usuario",(Object)(mostCurrent._main._username /*String*/ ),_map1);
 //BA.debugLineNum = 553;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto4",(Object)(mostCurrent._main._fotopath3 /*String*/ ),_map1);
 //BA.debugLineNum = 554;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","georeferencedDate",(Object)(mostCurrent._main._fotopath3 /*String*/ ),_map1);
 //BA.debugLineNum = 555;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","decimalLatitude",(Object)(mostCurrent._main._latitud /*String*/ ),_map1);
 //BA.debugLineNum = 556;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","decimalLongitude",(Object)(mostCurrent._main._longitud /*String*/ ),_map1);
 //BA.debugLineNum = 557;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loca";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","georeferencedDate",(Object)(mostCurrent._main._dateandtime /*String*/ ),_map1);
 //BA.debugLineNum = 560;BA.debugLine="If Main.modooffline = True Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 561;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 562;BA.debugLine="Msgbox(\"Esta trabajando en modo offline. El arc";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Esta trabajando en modo offline. El archivo se guardará para que lo pueda enviar luego desde 'Mi Perfil'"),BA.ObjectToCharSequence("Modo offline"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 564;BA.debugLine="Msgbox(\"You are working offline. The report wil";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You are working offline. The report will be saved so you can send it later from 'Mi profile'"),BA.ObjectToCharSequence("Offline"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 566;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 567;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 568;BA.debugLine="StartActivity(frmprincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmprincipal.getObject()));
 //BA.debugLineNum = 569;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 572;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 573;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 574;BA.debugLine="msg = utilidades.Mensaje(\"Envío de datos\", \"MsgU";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Envío de datos","MsgUpload.png","Se enviarán los datos a un revisor especializado","Por defecto, el reporte que envías será de visibilidad pública. Si deseas hacerlo privado y que no sea exhibido en el sitio web cambia la opción desde tu perfil","Enviar datos","No enviar!","",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 576;BA.debugLine="msg = utilidades.Mensaje(\"Uploading report\", \"Ms";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Uploading report","MsgUpload.png","The report will be sent to a specilized reviewer","By default, the report you are sending is public. You can make it private so it is not shown on the website by changing this option from your user profile","Send report","Do not send!","",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 578;BA.debugLine="If msg <> DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) == false) { 
 //BA.debugLineNum = 579;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 580;BA.debugLine="Msgbox(\"Los datos quedarán guardados para que l";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Los datos quedarán guardados para que los envíe luego"),BA.ObjectToCharSequence("Datos guardados"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 584;BA.debugLine="Msgbox(\"The report will be stored so you can se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("The report will be stored so you can send it later"),BA.ObjectToCharSequence("Report stored"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 587;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 588;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 589;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 592;BA.debugLine="btnFoto1.Enabled = False";
mostCurrent._btnfoto1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 593;BA.debugLine="btnFoto1.Visible = False";
mostCurrent._btnfoto1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 594;BA.debugLine="Foto_Dorsal.Visible = False";
mostCurrent._foto_dorsal.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 595;BA.debugLine="imgDorsal.Visible = False";
mostCurrent._imgdorsal.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 596;BA.debugLine="btnFoto2.Enabled = False";
mostCurrent._btnfoto2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 597;BA.debugLine="btnFoto2.Visible = False";
mostCurrent._btnfoto2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 598;BA.debugLine="Foto_Ventral.Visible = False";
mostCurrent._foto_ventral.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 599;BA.debugLine="imgVentral.Visible = False";
mostCurrent._imgventral.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 600;BA.debugLine="btnHabitat.Enabled = False";
mostCurrent._btnhabitat.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 601;BA.debugLine="btnHabitat.Visible = False";
mostCurrent._btnhabitat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 602;BA.debugLine="textOpcion3.Visible = False";
mostCurrent._textopcion3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 603;BA.debugLine="chkPrivado.Visible = False";
mostCurrent._chkprivado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 604;BA.debugLine="lblReportePublico.Visible = False";
mostCurrent._lblreportepublico.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 606;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 607;BA.debugLine="btnContinuar.Text = \"Enviando (cancelar envío)\"";
mostCurrent._btncontinuar.setText(BA.ObjectToCharSequence("Enviando (cancelar envío)"));
 //BA.debugLineNum = 608;BA.debugLine="lblInstrucciones.Text = \"Enviando... por favor a";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("Enviando... por favor aguarde"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 610;BA.debugLine="btnContinuar.Text = \"Uploading (cancel upload)\"";
mostCurrent._btncontinuar.setText(BA.ObjectToCharSequence("Uploading (cancel upload)"));
 //BA.debugLineNum = 611;BA.debugLine="lblInstrucciones.Text = \"Uploading... please wai";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("Uploading... please wait"));
 };
 //BA.debugLineNum = 615;BA.debugLine="Log(\"Chequeando internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57733353","Chequeando internet",0);
 //BA.debugLineNum = 616;BA.debugLine="CheckInternet";
_checkinternet();
 //BA.debugLineNum = 618;BA.debugLine="End Sub";
return "";
}
public static String  _btnfoto1_click() throws Exception{
 //BA.debugLineNum = 176;BA.debugLine="Sub btnFoto1_Click";
 //BA.debugLineNum = 177;BA.debugLine="currentFoto = \"dorsal\"";
_currentfoto = "dorsal";
 //BA.debugLineNum = 178;BA.debugLine="frmCamara.currentFoto = \"dorsal\"";
mostCurrent._frmcamara._currentfoto /*String*/  = "dorsal";
 //BA.debugLineNum = 179;BA.debugLine="StartActivity(frmCamara)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmcamara.getObject()));
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _btnfoto2_click() throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Sub btnFoto2_Click";
 //BA.debugLineNum = 182;BA.debugLine="currentFoto = \"ventral\"";
_currentfoto = "ventral";
 //BA.debugLineNum = 183;BA.debugLine="frmCamara.currentFoto = \"ventral\"";
mostCurrent._frmcamara._currentfoto /*String*/  = "ventral";
 //BA.debugLineNum = 184;BA.debugLine="StartActivity(frmCamara)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmcamara.getObject()));
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _btnhabitat_click() throws Exception{
 //BA.debugLineNum = 188;BA.debugLine="Sub btnHabitat_Click";
 //BA.debugLineNum = 190;BA.debugLine="textOpcion1.Visible = False";
mostCurrent._textopcion1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 191;BA.debugLine="textOpcion2.Visible = False";
mostCurrent._textopcion2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 192;BA.debugLine="textOpcion3.Visible = False";
mostCurrent._textopcion3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 193;BA.debugLine="btnFoto1.Visible = False";
mostCurrent._btnfoto1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 194;BA.debugLine="btnFoto2.Visible = False";
mostCurrent._btnfoto2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 195;BA.debugLine="btnHabitat.Visible = False";
mostCurrent._btnhabitat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 196;BA.debugLine="Foto_Dorsal.Visible = False";
mostCurrent._foto_dorsal.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 197;BA.debugLine="Foto_Ventral.Visible = False";
mostCurrent._foto_ventral.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 198;BA.debugLine="lblInstrucciones.visible = False";
mostCurrent._lblinstrucciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 199;BA.debugLine="chkPrivado.Visible = False";
mostCurrent._chkprivado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 200;BA.debugLine="lblReportePublico.Visible = False";
mostCurrent._lblreportepublico.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 202;BA.debugLine="panelHabitat.Initialize(\"panelHabitat\")";
mostCurrent._panelhabitat.Initialize(mostCurrent.activityBA,"panelHabitat");
 //BA.debugLineNum = 203;BA.debugLine="Activity.AddView(panelHabitat, 0, 0, 100%x, 100%y";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelhabitat.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 204;BA.debugLine="panelHabitat.LoadLayout(\"layHabitat\")";
mostCurrent._panelhabitat.LoadLayout("layHabitat",mostCurrent.activityBA);
 //BA.debugLineNum = 207;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 208;BA.debugLine="spnDomicilio.AddAll(Array As String(\"Selecciona.";
mostCurrent._spndomicilio.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Selecciona...","Dormitorio","Cocina","Galería","Otros"}));
 //BA.debugLineNum = 209;BA.debugLine="spnPeridomicilio.AddAll(Array As String(\"Selecci";
mostCurrent._spnperidomicilio.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Selecciona...","Corrales","Gallinero","Galpón","Otros"}));
 //BA.debugLineNum = 210;BA.debugLine="spnSilvestre.AddAll(Array As String(\"Selecciona.";
mostCurrent._spnsilvestre.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Selecciona...","Árboles","Nidos de aves","Piedras","Otros"}));
 //BA.debugLineNum = 211;BA.debugLine="lblInstruccionesHabitat.Text = \"¿En qué lugar en";
mostCurrent._lblinstruccioneshabitat.setText(BA.ObjectToCharSequence("¿En qué lugar encontraste el insecto?"));
 //BA.debugLineNum = 212;BA.debugLine="lblDomicilio.Text = \" Domicilio\"";
mostCurrent._lbldomicilio.setText(BA.ObjectToCharSequence(" Domicilio"));
 //BA.debugLineNum = 213;BA.debugLine="lblPeridomicilio.Text = \" Peridomicilio\"";
mostCurrent._lblperidomicilio.setText(BA.ObjectToCharSequence(" Peridomicilio"));
 //BA.debugLineNum = 214;BA.debugLine="lblSilvestre.Text = \" Silvestre\"";
mostCurrent._lblsilvestre.setText(BA.ObjectToCharSequence(" Silvestre"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 216;BA.debugLine="spnDomicilio.AddAll(Array As String(\"Choose...\",";
mostCurrent._spndomicilio.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Choose...","Bedroom","Kitchen","Gallery","Other"}));
 //BA.debugLineNum = 217;BA.debugLine="spnPeridomicilio.AddAll(Array As String(\"Choose.";
mostCurrent._spnperidomicilio.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Choose...","Pens","Hen house","Shed","Other"}));
 //BA.debugLineNum = 218;BA.debugLine="spnSilvestre.AddAll(Array As String(\"Choose...\",";
mostCurrent._spnsilvestre.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Choose...","Trees","Bird nest","Rocks","Other"}));
 //BA.debugLineNum = 219;BA.debugLine="lblInstruccionesHabitat.Text = \"Where did you fi";
mostCurrent._lblinstruccioneshabitat.setText(BA.ObjectToCharSequence("Where did you find the insect?"));
 //BA.debugLineNum = 220;BA.debugLine="lblDomicilio.Text = \" House\"";
mostCurrent._lbldomicilio.setText(BA.ObjectToCharSequence(" House"));
 //BA.debugLineNum = 221;BA.debugLine="lblPeridomicilio.Text = \" Near the house\"";
mostCurrent._lblperidomicilio.setText(BA.ObjectToCharSequence(" Near the house"));
 //BA.debugLineNum = 222;BA.debugLine="lblSilvestre.Text = \" Wild\"";
mostCurrent._lblsilvestre.setText(BA.ObjectToCharSequence(" Wild"));
 };
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _checkinternet() throws Exception{
cepave.geovin.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 624;BA.debugLine="Sub CheckInternet";
 //BA.debugLineNum = 625;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 626;BA.debugLine="dd.url = Main.serverPath & \"/connect2/connecttest";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect2/connecttest.php";
 //BA.debugLineNum = 627;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 628;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmfotos.getObject();
 //BA.debugLineNum = 629;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 630;BA.debugLine="End Sub";
return "";
}
public static String  _enviardatos() throws Exception{
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
String _terminado = "";
String _privado = "";
String _estadovalidacion = "";
String _deviceid = "";
anywheresoftware.b4a.objects.collections.Map _datosmap = null;
cepave.geovin.downloadservice._downloaddata _dd = null;
String _usernametemp = "";
 //BA.debugLineNum = 655;BA.debugLine="Sub EnviarDatos";
 //BA.debugLineNum = 658;BA.debugLine="Dim username,dateandtime,nombresitio,tiporio,lat,";
_username = "";
_dateandtime = "";
_nombresitio = "";
_tiporio = "";
_lat = "";
_lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
 //BA.debugLineNum = 659;BA.debugLine="Dim valorind1,valorind2,valorind3,valorind4,valor";
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
 //BA.debugLineNum = 660;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceID";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
 //BA.debugLineNum = 663;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 664;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
 //BA.debugLineNum = 665;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datosmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM markers_local WHERE Id=?",new String[]{mostCurrent._main._currentproject /*String*/ });
 //BA.debugLineNum = 667;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
if (_datosmap== null || _datosmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 668;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 669;BA.debugLine="ToastMessageShow(\"Error cargando el análisis\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisis"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 671;BA.debugLine="ToastMessageShow(\"Error loading the report\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading the report"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 673;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 675;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
 //BA.debugLineNum = 676;BA.debugLine="dateandtime = datosMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 677;BA.debugLine="nombresitio = datosMap.Get(\"nombresitio\")";
_nombresitio = BA.ObjectToString(_datosmap.Get((Object)("nombresitio")));
 //BA.debugLineNum = 678;BA.debugLine="tiporio = datosMap.Get(\"tipoeval\")";
_tiporio = BA.ObjectToString(_datosmap.Get((Object)("tipoeval")));
 //BA.debugLineNum = 679;BA.debugLine="lat = datosMap.Get(\"decimallatitude\")";
_lat = BA.ObjectToString(_datosmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 680;BA.debugLine="lng = datosMap.Get(\"decimallongitude\")";
_lng = BA.ObjectToString(_datosmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 681;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 682;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 683;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 684;BA.debugLine="valorind1 = datosMap.Get(\"par1\")";
_valorind1 = BA.ObjectToString(_datosmap.Get((Object)("par1")));
 //BA.debugLineNum = 685;BA.debugLine="valorind2 = datosMap.Get(\"par2\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("par2")));
 //BA.debugLineNum = 686;BA.debugLine="valorind3 = datosMap.Get(\"par3\")";
_valorind3 = BA.ObjectToString(_datosmap.Get((Object)("par3")));
 //BA.debugLineNum = 687;BA.debugLine="valorind4 = datosMap.Get(\"par4\")";
_valorind4 = BA.ObjectToString(_datosmap.Get((Object)("par4")));
 //BA.debugLineNum = 688;BA.debugLine="valorind5 = datosMap.Get(\"par5\")";
_valorind5 = BA.ObjectToString(_datosmap.Get((Object)("par5")));
 //BA.debugLineNum = 689;BA.debugLine="valorind6 = datosMap.Get(\"par6\")";
_valorind6 = BA.ObjectToString(_datosmap.Get((Object)("par6")));
 //BA.debugLineNum = 690;BA.debugLine="valorind7 = datosMap.Get(\"par7\")";
_valorind7 = BA.ObjectToString(_datosmap.Get((Object)("par7")));
 //BA.debugLineNum = 691;BA.debugLine="valorind8 = datosMap.Get(\"par8\")";
_valorind8 = BA.ObjectToString(_datosmap.Get((Object)("par8")));
 //BA.debugLineNum = 692;BA.debugLine="valorind9 = datosMap.Get(\"par9\")";
_valorind9 = BA.ObjectToString(_datosmap.Get((Object)("par9")));
 //BA.debugLineNum = 693;BA.debugLine="valorind10 = datosMap.Get(\"par10\")";
_valorind10 = BA.ObjectToString(_datosmap.Get((Object)("par10")));
 //BA.debugLineNum = 694;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
mostCurrent._foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
 //BA.debugLineNum = 695;BA.debugLine="foto2 = datosMap.Get(\"foto2\")";
mostCurrent._foto2 = BA.ObjectToString(_datosmap.Get((Object)("foto2")));
 //BA.debugLineNum = 696;BA.debugLine="foto3 = datosMap.Get(\"foto3\")";
mostCurrent._foto3 = BA.ObjectToString(_datosmap.Get((Object)("foto3")));
 //BA.debugLineNum = 698;BA.debugLine="foto4 = datosMap.Get(\"foto4\")";
mostCurrent._foto4 = BA.ObjectToString(_datosmap.Get((Object)("foto4")));
 //BA.debugLineNum = 699;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
 //BA.debugLineNum = 700;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
 //BA.debugLineNum = 701;BA.debugLine="If privado = Null Or privado = \"null\" Then";
if (_privado== null || (_privado).equals("null")) { 
 //BA.debugLineNum = 702;BA.debugLine="privado = \"no\"";
_privado = "no";
 };
 //BA.debugLineNum = 704;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 705;BA.debugLine="If estadovalidacion = \"null\" Then";
if ((_estadovalidacion).equals("null")) { 
 //BA.debugLineNum = 706;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 };
 //BA.debugLineNum = 708;BA.debugLine="deviceID = datosMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_datosmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 709;BA.debugLine="If deviceID = Null Or deviceID = \"\" Or deviceID";
if (_deviceid== null || (_deviceid).equals("") || (_deviceid).equals("null")) { 
 //BA.debugLineNum = 710;BA.debugLine="deviceID = utilidades.GetDeviceId";
_deviceid = mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 714;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57929915","Comienza envio de datos",0);
 //BA.debugLineNum = 717;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 718;BA.debugLine="Dim usernameTemp As String";
_usernametemp = "";
 //BA.debugLineNum = 719;BA.debugLine="If username = \"guest\" Then";
if ((_username).equals("guest")) { 
 //BA.debugLineNum = 720;BA.debugLine="usernameTemp = \"guest\" & deviceID.SubString2(0,6";
_usernametemp = "guest"+_deviceid.substring((int) (0),(int) (6));
 }else {
 //BA.debugLineNum = 722;BA.debugLine="usernameTemp = username";
_usernametemp = _username;
 };
 //BA.debugLineNum = 724;BA.debugLine="dd.url = Main.serverPath & \"/connect2/addpuntomap";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect2/addpuntomapa.php?"+"username="+_usernametemp+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ +"&"+"dateandtime="+_dateandtime+"&"+"lat="+_lat+"&"+"lng="+_lng+"&"+"valorVinchuca="+_valorind1+"&"+"foto1path="+mostCurrent._foto1+"&"+"foto2path="+mostCurrent._foto2+"&"+"foto3path="+mostCurrent._foto3+"&"+"foto4path="+mostCurrent._foto4+"&"+"privado="+_privado+"&"+"gpsdetect="+_gpsdetect+"&"+"wifidetect="+_wifidetect+"&"+"mapadetect="+_mapadetect+"&"+"terminado="+_terminado+"&"+"verificado=No Verificado";
 //BA.debugLineNum = 735;BA.debugLine="dd.EventName = \"EnviarDatos\"";
_dd.EventName /*String*/  = "EnviarDatos";
 //BA.debugLineNum = 736;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmfotos.getObject();
 //BA.debugLineNum = 737;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 739;BA.debugLine="End Sub";
return "";
}
public static String  _enviardatos_complete(cepave.geovin.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
String _serverid = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 740;BA.debugLine="Sub EnviarDatos_Complete(Job As HttpJob)";
 //BA.debugLineNum = 741;BA.debugLine="Log(\"Datos enviados : \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("57995393","Datos enviados : "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 742;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 743;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 744;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 745;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 746;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 747;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 748;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 749;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 750;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 751;BA.debugLine="ToastMessageShow(\"Error en la carga de marcado";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de marcadores"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 753;BA.debugLine="ToastMessageShow(\"Error loading markers\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading markers"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 755;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_act).equals("Marcadores")) { 
 //BA.debugLineNum = 757;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 758;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 759;BA.debugLine="Dim serverID As String";
_serverid = "";
 //BA.debugLineNum = 760;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
 //BA.debugLineNum = 763;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 764;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 765;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 766;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 767;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_lo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","serverId",(Object)(_serverid),_map1);
 //BA.debugLineNum = 768;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 769;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando fot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 771;BA.debugLine="ToastMessageShow(\"Report sent, sending photos\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 774;BA.debugLine="frmIdentificacionNew.currentPregunta = 1";
mostCurrent._frmidentificacionnew._currentpregunta /*int*/  = (int) (1);
 //BA.debugLineNum = 775;BA.debugLine="frmIdentificacionNew.foto1 = foto1";
mostCurrent._frmidentificacionnew._foto1 /*String*/  = mostCurrent._foto1;
 //BA.debugLineNum = 776;BA.debugLine="frmIdentificacionNew.foto2 = foto2";
mostCurrent._frmidentificacionnew._foto2 /*String*/  = mostCurrent._foto2;
 //BA.debugLineNum = 777;BA.debugLine="frmIdentificacionNew.foto3 = foto3";
mostCurrent._frmidentificacionnew._foto3 /*String*/  = mostCurrent._foto3;
 //BA.debugLineNum = 778;BA.debugLine="frmIdentificacionNew.foto4 = foto4";
mostCurrent._frmidentificacionnew._foto4 /*String*/  = mostCurrent._foto4;
 //BA.debugLineNum = 781;BA.debugLine="EnviarFotos";
_enviarfotos();
 };
 }else {
 //BA.debugLineNum = 784;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57995436","envio datos not ok",0);
 //BA.debugLineNum = 785;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 786;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 788;BA.debugLine="Msgbox(\"There seems to be a problem with our se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 792;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 793;BA.debugLine="End Sub";
return "";
}
public static String  _enviarfotos() throws Exception{
String _usr = "";
String _pss = "";
 //BA.debugLineNum = 799;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 802;BA.debugLine="If foto1 <> \"null\" Then";
if ((mostCurrent._foto1).equals("null") == false) { 
 //BA.debugLineNum = 803;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 805;BA.debugLine="If foto2 <>  \"null\" Then";
if ((mostCurrent._foto2).equals("null") == false) { 
 //BA.debugLineNum = 806;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 810;BA.debugLine="TimerEnvio.Initialize(\"TimerEnvio\", 1000)";
_timerenvio.Initialize(processBA,"TimerEnvio",(long) (1000));
 //BA.debugLineNum = 811;BA.debugLine="TimerEnvio.Enabled = True";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 812;BA.debugLine="TimerOn = True";
_timeron = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 814;BA.debugLine="Dim usr As String";
_usr = "";
 //BA.debugLineNum = 815;BA.debugLine="Dim pss As String";
_pss = "";
 //BA.debugLineNum = 816;BA.debugLine="usr = \"geovin_upload\"";
_usr = "geovin_upload";
 //BA.debugLineNum = 817;BA.debugLine="pss = \"geovin_pass\"";
_pss = "geovin_pass";
 //BA.debugLineNum = 819;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 820;BA.debugLine="Up2.B4A_log=True";
_up2.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 821;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 822;BA.debugLine="Up2.Initialize(\"Up1\")";
_up2.Initialize(processBA,"Up1");
 //BA.debugLineNum = 824;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\",";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._foto1+".jpg") && _foto1sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 825;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("58060954","Enviando foto 1 ",0);
 //BA.debugLineNum = 826;BA.debugLine="ProgressBar1.Visible = True";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 829;BA.debugLine="Up1.doFileUpload(ProgressBar1,Null,File.DirRootE";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/"+mostCurrent._foto1+".jpg",mostCurrent._main._serverpath /*String*/ +"/connect2/upload_file.php?usr="+_usr+"&pss="+_pss);
 //BA.debugLineNum = 830;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 831;BA.debugLine="textOpcion1.Text = \"Enviando foto...\"";
mostCurrent._textopcion1.setText(BA.ObjectToCharSequence("Enviando foto..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 833;BA.debugLine="textOpcion1.Text = \"Uploading photo...\"";
mostCurrent._textopcion1.setText(BA.ObjectToCharSequence("Uploading photo..."));
 };
 }else {
 //BA.debugLineNum = 836;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("58060965","no foto 1",0);
 };
 //BA.debugLineNum = 839;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\",";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._foto2+".jpg") && _foto2sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 840;BA.debugLine="Log(\"Enviando foto 2 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("58060969","Enviando foto 2 ",0);
 //BA.debugLineNum = 841;BA.debugLine="ProgressBar2.Visible = True";
mostCurrent._progressbar2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 842;BA.debugLine="Up2.doFileUpload(ProgressBar2,Null,File.DirRootE";
_up2.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar2.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/"+mostCurrent._foto2+".jpg",mostCurrent._main._serverpath /*String*/ +"/connect2/upload_file.php?usr="+_usr+"&pss="+_pss);
 //BA.debugLineNum = 844;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 845;BA.debugLine="textOpcion2.Text = \"Enviando foto...\"";
mostCurrent._textopcion2.setText(BA.ObjectToCharSequence("Enviando foto..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 847;BA.debugLine="textOpcion2.Text = \"Uploading photo...\"";
mostCurrent._textopcion2.setText(BA.ObjectToCharSequence("Uploading photo..."));
 };
 }else {
 //BA.debugLineNum = 850;BA.debugLine="Log(\"no foto 2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("58060979","no foto 2",0);
 };
 //BA.debugLineNum = 852;BA.debugLine="End Sub";
return "";
}
public static String  _fondoblanco_click() throws Exception{
 //BA.debugLineNum = 854;BA.debugLine="Sub fondoblanco_Click";
 //BA.debugLineNum = 856;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 21;BA.debugLine="Private Foto_Ventral As ImageView";
mostCurrent._foto_ventral = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Foto_Dorsal As ImageView";
mostCurrent._foto_dorsal = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private btnContinuar As Button";
mostCurrent._btncontinuar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private textOpcion1 As Label";
mostCurrent._textopcion1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private textOpcion2 As Label";
mostCurrent._textopcion2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private textOpcion3 As Label";
mostCurrent._textopcion3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btnFoto1 As Button";
mostCurrent._btnfoto1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btnFoto2 As Button";
mostCurrent._btnfoto2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private btnHabitat As Button";
mostCurrent._btnhabitat = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private ProgressBar2 As ProgressBar";
mostCurrent._progressbar2 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim foto1, foto2, foto3, foto4 As String";
mostCurrent._foto1 = "";
mostCurrent._foto2 = "";
mostCurrent._foto3 = "";
mostCurrent._foto4 = "";
 //BA.debugLineNum = 37;BA.debugLine="Private totalFotos As Int";
_totalfotos = 0;
 //BA.debugLineNum = 38;BA.debugLine="Private foto1Sent As Boolean";
_foto1sent = false;
 //BA.debugLineNum = 39;BA.debugLine="Private foto2Sent As Boolean";
_foto2sent = false;
 //BA.debugLineNum = 40;BA.debugLine="Private imgDorsal As ImageView";
mostCurrent._imgdorsal = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private imgVentral As ImageView";
mostCurrent._imgventral = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim fotosEnviadas As Int";
_fotosenviadas = 0;
 //BA.debugLineNum = 43;BA.debugLine="Dim pw As PhoneWakeState";
mostCurrent._pw = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 44;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 46;BA.debugLine="Private fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim panelHabitat As Panel";
mostCurrent._panelhabitat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private spnDomicilio As Spinner";
mostCurrent._spndomicilio = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private spnPeridomicilio As Spinner";
mostCurrent._spnperidomicilio = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private spnSilvestre As Spinner";
mostCurrent._spnsilvestre = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private lblInstruccionesHabitat As Label";
mostCurrent._lblinstruccioneshabitat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private lblDomicilio As Label";
mostCurrent._lbldomicilio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private lblPeridomicilio As Label";
mostCurrent._lblperidomicilio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private lblSilvestre As Label";
mostCurrent._lblsilvestre = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim foto_dorsal_existe As Boolean = False";
_foto_dorsal_existe = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 58;BA.debugLine="Dim foto_ventral_existe As Boolean = False";
_foto_ventral_existe = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 59;BA.debugLine="Dim habitat_existe As Boolean = False";
_habitat_existe = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 60;BA.debugLine="Private lblInstrucciones As Label";
mostCurrent._lblinstrucciones = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private lblReportePublico As Label";
mostCurrent._lblreportepublico = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private chkPrivado As CheckBox";
mostCurrent._chkprivado = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _lblreportepublico_click() throws Exception{
 //BA.debugLineNum = 494;BA.debugLine="Sub lblReportePublico_Click";
 //BA.debugLineNum = 495;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 496;BA.debugLine="utilidades.Mensaje(\"Privacidad\", Null, \"Los repo";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Privacidad",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),"Los reportes públicos podrán ser vistos desde el mapa de GeoVin, aunque aparecerá como 'Dato no validado' hasta que un especialista lo compruebe. Puedes elegir que el reporte sea privado, o cambiarlo luego desde 'Mis Datos Anteriores'","","Ok, entendido!",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 498;BA.debugLine="utilidades.Mensaje(\"Privacy\", Null, \"Public repo";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Privacy",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),"Public reports will be displayed in the GeoVin map, although they will show as 'Not validated' until a specialist checks it. You can choose to make it private, or later change it through the menu 'My previous reports'","","Ok, got it!",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 500;BA.debugLine="End Sub";
return "";
}
public static String  _previewfotos() throws Exception{
 //BA.debugLineNum = 451;BA.debugLine="Sub PreviewFotos";
 //BA.debugLineNum = 452;BA.debugLine="Log(Main.fotopath0)";
anywheresoftware.b4a.keywords.Common.LogImpl("57602177",mostCurrent._main._fotopath0 /*String*/ ,0);
 //BA.debugLineNum = 453;BA.debugLine="Log(Main.fotopath1)";
anywheresoftware.b4a.keywords.Common.LogImpl("57602178",mostCurrent._main._fotopath1 /*String*/ ,0);
 //BA.debugLineNum = 454;BA.debugLine="Log(Main.fotopath3)";
anywheresoftware.b4a.keywords.Common.LogImpl("57602179",mostCurrent._main._fotopath3 /*String*/ ,0);
 //BA.debugLineNum = 457;BA.debugLine="If currentFoto = \"dorsal\" Then";
if ((_currentfoto).equals("dorsal")) { 
 //BA.debugLineNum = 458;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0 /*String*/ ).equals("") == false) { 
 //BA.debugLineNum = 459;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath0 /*String*/ +".jpg")) { 
 //BA.debugLineNum = 460;BA.debugLine="Foto_Dorsal.Bitmap = Null";
mostCurrent._foto_dorsal.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 461;BA.debugLine="Foto_Dorsal.Bitmap = LoadBitmapSample(File.Dir";
mostCurrent._foto_dorsal.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath0 /*String*/ +".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 462;BA.debugLine="foto_dorsal_existe = True";
_foto_dorsal_existe = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 463;BA.debugLine="imgDorsal.Visible = False";
mostCurrent._imgdorsal.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 465;BA.debugLine="foto_dorsal_existe = False";
_foto_dorsal_existe = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 466;BA.debugLine="imgDorsal.Visible = True";
mostCurrent._imgdorsal.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 }else if((_currentfoto).equals("ventral")) { 
 //BA.debugLineNum = 470;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1 /*String*/ ).equals("") == false) { 
 //BA.debugLineNum = 471;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath1 /*String*/ +".jpg")) { 
 //BA.debugLineNum = 472;BA.debugLine="Foto_Ventral.Bitmap = Null";
mostCurrent._foto_ventral.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 473;BA.debugLine="Foto_Ventral.Bitmap = LoadBitmapSample(File.Di";
mostCurrent._foto_ventral.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",mostCurrent._main._fotopath1 /*String*/ +".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 474;BA.debugLine="foto_ventral_existe = True";
_foto_ventral_existe = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 475;BA.debugLine="imgVentral.Visible = False";
mostCurrent._imgventral.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 477;BA.debugLine="foto_ventral_existe = False";
_foto_ventral_existe = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 478;BA.debugLine="imgVentral.Visible = True";
mostCurrent._imgventral.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 };
 //BA.debugLineNum = 484;BA.debugLine="If textOpcion3.Text <> \"\" Then";
if ((mostCurrent._textopcion3.getText()).equals("") == false) { 
 //BA.debugLineNum = 485;BA.debugLine="habitat_existe = True";
_habitat_existe = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 487;BA.debugLine="habitat_existe = False";
_habitat_existe = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 490;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim Up1 As UploadFilePhp";
_up1 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 10;BA.debugLine="Dim Up2 As UploadFilePhp";
_up2 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 13;BA.debugLine="Dim currentFoto As String";
_currentfoto = "";
 //BA.debugLineNum = 15;BA.debugLine="Private TimerEnvio As Timer";
_timerenvio = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 16;BA.debugLine="Private TimerOn As Boolean";
_timeron = false;
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _spndomicilio_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _inputmsg = null;
 //BA.debugLineNum = 228;BA.debugLine="Sub spnDomicilio_ItemClick (Position As Int, Value";
 //BA.debugLineNum = 229;BA.debugLine="If Position = 1 Then";
if (_position==1) { 
 //BA.debugLineNum = 230;BA.debugLine="Log(\"domicilio1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57405570","domicilio1",0);
 //BA.debugLineNum = 231;BA.debugLine="Main.fotopath3 = \"habitat_dormitorio\"";
mostCurrent._main._fotopath3 /*String*/  = "habitat_dormitorio";
 //BA.debugLineNum = 232;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 233;BA.debugLine="textOpcion3.Text = \"Dormitorio\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Dormitorio"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 235;BA.debugLine="textOpcion3.Text = \"Bedroom\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Bedroom"));
 };
 }else if(_position==2) { 
 //BA.debugLineNum = 238;BA.debugLine="Log(\"domicilio2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57405578","domicilio2",0);
 //BA.debugLineNum = 239;BA.debugLine="Main.fotopath3 = \"habitat_cocina\"";
mostCurrent._main._fotopath3 /*String*/  = "habitat_cocina";
 //BA.debugLineNum = 240;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 241;BA.debugLine="textOpcion3.Text = \"Cocina\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Cocina"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 243;BA.debugLine="textOpcion3.Text = \"Kitchen\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Kitchen"));
 };
 }else if(_position==3) { 
 //BA.debugLineNum = 246;BA.debugLine="Log(\"domicilio3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57405586","domicilio3",0);
 //BA.debugLineNum = 247;BA.debugLine="Main.fotopath3 = \"habitat_galeria\"";
mostCurrent._main._fotopath3 /*String*/  = "habitat_galeria";
 //BA.debugLineNum = 248;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 249;BA.debugLine="textOpcion3.Text = \"Galería\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Galería"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 251;BA.debugLine="textOpcion3.Text = \"Gallery\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Gallery"));
 };
 }else if(_position==4) { 
 //BA.debugLineNum = 254;BA.debugLine="Log(\"domicilio4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57405594","domicilio4",0);
 //BA.debugLineNum = 255;BA.debugLine="Dim inputmsg As InputDialog";
_inputmsg = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 256;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 257;BA.debugLine="If inputmsg.Show(\"¿En qué otro lugar?\", \"Otro l";
if (_inputmsg.Show("¿En qué otro lugar?","Otro lugar","Ok","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null))==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 258;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 260;BA.debugLine="Main.fotopath3 = \"habitat_domicilio_\" & inputm";
mostCurrent._main._fotopath3 /*String*/  = "habitat_domicilio_"+_inputmsg.getInput();
 //BA.debugLineNum = 261;BA.debugLine="Log(Main.fotopath3)";
anywheresoftware.b4a.keywords.Common.LogImpl("57405601",mostCurrent._main._fotopath3 /*String*/ ,0);
 //BA.debugLineNum = 262;BA.debugLine="textOpcion3.Text = inputmsg.Input";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence(_inputmsg.getInput()));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 265;BA.debugLine="If inputmsg.Show(\"¿What other place?\", \"Other p";
if (_inputmsg.Show("¿What other place?","Other place","Ok","Cancel","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null))==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 266;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 268;BA.debugLine="Main.fotopath3 = \"habitat_domicilio_\" & inputm";
mostCurrent._main._fotopath3 /*String*/  = "habitat_domicilio_"+_inputmsg.getInput();
 //BA.debugLineNum = 269;BA.debugLine="Log(Main.fotopath3)";
anywheresoftware.b4a.keywords.Common.LogImpl("57405609",mostCurrent._main._fotopath3 /*String*/ ,0);
 //BA.debugLineNum = 270;BA.debugLine="textOpcion3.Text = inputmsg.Input";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence(_inputmsg.getInput()));
 };
 };
 //BA.debugLineNum = 273;BA.debugLine="Log(\"domicilio4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57405613","domicilio4",0);
 };
 //BA.debugLineNum = 276;BA.debugLine="If Position <> 0 Then";
if (_position!=0) { 
 //BA.debugLineNum = 277;BA.debugLine="habitat_existe = True";
_habitat_existe = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 278;BA.debugLine="spnPeridomicilio.SelectedIndex = 0";
mostCurrent._spnperidomicilio.setSelectedIndex((int) (0));
 //BA.debugLineNum = 279;BA.debugLine="spnSilvestre.SelectedIndex = 0";
mostCurrent._spnsilvestre.setSelectedIndex((int) (0));
 //BA.debugLineNum = 280;BA.debugLine="panelHabitat.RemoveView";
mostCurrent._panelhabitat.RemoveView();
 //BA.debugLineNum = 281;BA.debugLine="textOpcion1.Visible = True";
mostCurrent._textopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 282;BA.debugLine="textOpcion2.Visible = True";
mostCurrent._textopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 283;BA.debugLine="textOpcion3.Visible = True";
mostCurrent._textopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 284;BA.debugLine="btnFoto1.Visible = True";
mostCurrent._btnfoto1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 285;BA.debugLine="btnFoto2.Visible = True";
mostCurrent._btnfoto2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 286;BA.debugLine="btnHabitat.Visible = True";
mostCurrent._btnhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 287;BA.debugLine="Foto_Dorsal.Visible = True";
mostCurrent._foto_dorsal.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 288;BA.debugLine="Foto_Ventral.Visible = True";
mostCurrent._foto_ventral.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 289;BA.debugLine="lblInstrucciones.visible = True";
mostCurrent._lblinstrucciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 290;BA.debugLine="btnHabitat.Enabled = True";
mostCurrent._btnhabitat.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 291;BA.debugLine="chkPrivado.Visible = True";
mostCurrent._chkprivado.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 292;BA.debugLine="lblReportePublico.Visible = True";
mostCurrent._lblreportepublico.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 294;BA.debugLine="End Sub";
return "";
}
public static String  _spnperidomicilio_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _inputmsg = null;
 //BA.debugLineNum = 295;BA.debugLine="Sub spnPeridomicilio_ItemClick (Position As Int, V";
 //BA.debugLineNum = 296;BA.debugLine="If Position = 1 Then";
if (_position==1) { 
 //BA.debugLineNum = 297;BA.debugLine="Log(\"peridomicilio1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57471106","peridomicilio1",0);
 //BA.debugLineNum = 298;BA.debugLine="Main.fotopath3 = \"habitat_corrales\"";
mostCurrent._main._fotopath3 /*String*/  = "habitat_corrales";
 //BA.debugLineNum = 299;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 300;BA.debugLine="textOpcion3.Text = \"Corral\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Corral"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 302;BA.debugLine="textOpcion3.Text = \"Pens\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Pens"));
 };
 }else if(_position==2) { 
 //BA.debugLineNum = 308;BA.debugLine="Log(\"peridomicilio2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57471117","peridomicilio2",0);
 //BA.debugLineNum = 309;BA.debugLine="Main.fotopath3 = \"habitat_gallinero\"";
mostCurrent._main._fotopath3 /*String*/  = "habitat_gallinero";
 //BA.debugLineNum = 310;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 311;BA.debugLine="textOpcion3.Text = \"Gallinero\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Gallinero"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 313;BA.debugLine="textOpcion3.Text = \"Hen house\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Hen house"));
 };
 }else if(_position==3) { 
 //BA.debugLineNum = 319;BA.debugLine="Log(\"peridomicilio3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57471128","peridomicilio3",0);
 //BA.debugLineNum = 320;BA.debugLine="Main.fotopath3 = \"habitat_galpon\"";
mostCurrent._main._fotopath3 /*String*/  = "habitat_galpon";
 //BA.debugLineNum = 321;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 322;BA.debugLine="textOpcion3.Text = \"Galpón\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Galpón"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 324;BA.debugLine="textOpcion3.Text = \"Shed\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Shed"));
 };
 }else if(_position==4) { 
 //BA.debugLineNum = 330;BA.debugLine="Log(\"peridomicilio4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57471139","peridomicilio4",0);
 //BA.debugLineNum = 331;BA.debugLine="Dim inputmsg As InputDialog";
_inputmsg = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 332;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 333;BA.debugLine="If inputmsg.Show(\"¿En qué otro lugar?\", \"Otro l";
if (_inputmsg.Show("¿En qué otro lugar?","Otro lugar","Ok","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null))==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 334;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 336;BA.debugLine="Main.fotopath3 = \"habitat_peridomicilio_\" & in";
mostCurrent._main._fotopath3 /*String*/  = "habitat_peridomicilio_"+_inputmsg.getInput();
 //BA.debugLineNum = 337;BA.debugLine="Log(Main.fotopath3)";
anywheresoftware.b4a.keywords.Common.LogImpl("57471146",mostCurrent._main._fotopath3 /*String*/ ,0);
 //BA.debugLineNum = 338;BA.debugLine="textOpcion3.Text = inputmsg.Input";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence(_inputmsg.getInput()));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 341;BA.debugLine="If inputmsg.Show(\"¿What other place?\", \"Other p";
if (_inputmsg.Show("¿What other place?","Other place","Ok","Cancel","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null))==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 342;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 344;BA.debugLine="Main.fotopath3 = \"habitat_peridomicilio_\" & in";
mostCurrent._main._fotopath3 /*String*/  = "habitat_peridomicilio_"+_inputmsg.getInput();
 //BA.debugLineNum = 345;BA.debugLine="Log(Main.fotopath3)";
anywheresoftware.b4a.keywords.Common.LogImpl("57471154",mostCurrent._main._fotopath3 /*String*/ ,0);
 //BA.debugLineNum = 346;BA.debugLine="textOpcion3.Text = inputmsg.Input";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence(_inputmsg.getInput()));
 };
 };
 //BA.debugLineNum = 352;BA.debugLine="Log(\"peridomicilio4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57471161","peridomicilio4",0);
 };
 //BA.debugLineNum = 355;BA.debugLine="If Position <> 0 Then";
if (_position!=0) { 
 //BA.debugLineNum = 356;BA.debugLine="habitat_existe = True";
_habitat_existe = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 357;BA.debugLine="spnDomicilio.SelectedIndex = 0";
mostCurrent._spndomicilio.setSelectedIndex((int) (0));
 //BA.debugLineNum = 358;BA.debugLine="spnSilvestre.SelectedIndex = 0";
mostCurrent._spnsilvestre.setSelectedIndex((int) (0));
 //BA.debugLineNum = 359;BA.debugLine="panelHabitat.RemoveView";
mostCurrent._panelhabitat.RemoveView();
 //BA.debugLineNum = 360;BA.debugLine="textOpcion1.Visible = True";
mostCurrent._textopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 361;BA.debugLine="textOpcion2.Visible = True";
mostCurrent._textopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 362;BA.debugLine="textOpcion3.Visible = True";
mostCurrent._textopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 363;BA.debugLine="btnFoto1.Visible = True";
mostCurrent._btnfoto1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 364;BA.debugLine="btnFoto2.Visible = True";
mostCurrent._btnfoto2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 365;BA.debugLine="btnHabitat.Visible = True";
mostCurrent._btnhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 366;BA.debugLine="Foto_Dorsal.Visible = True";
mostCurrent._foto_dorsal.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 367;BA.debugLine="Foto_Ventral.Visible = True";
mostCurrent._foto_ventral.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 368;BA.debugLine="lblInstrucciones.visible = True";
mostCurrent._lblinstrucciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 369;BA.debugLine="chkPrivado.Visible = True";
mostCurrent._chkprivado.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 370;BA.debugLine="lblReportePublico.Visible = True";
mostCurrent._lblreportepublico.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 373;BA.debugLine="End Sub";
return "";
}
public static String  _spnsilvestre_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _inputmsg = null;
 //BA.debugLineNum = 374;BA.debugLine="Sub spnSilvestre_ItemClick (Position As Int, Value";
 //BA.debugLineNum = 375;BA.debugLine="If Position = 1 Then";
if (_position==1) { 
 //BA.debugLineNum = 376;BA.debugLine="Log(\"silvestre1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57536642","silvestre1",0);
 //BA.debugLineNum = 377;BA.debugLine="Main.fotopath3 = \"habitat_arboles\"";
mostCurrent._main._fotopath3 /*String*/  = "habitat_arboles";
 //BA.debugLineNum = 378;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 379;BA.debugLine="textOpcion3.Text = \"Árboles\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Árboles"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 381;BA.debugLine="textOpcion3.Text = \"Trees\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Trees"));
 };
 }else if(_position==2) { 
 //BA.debugLineNum = 387;BA.debugLine="Log(\"silvestre2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57536653","silvestre2",0);
 //BA.debugLineNum = 388;BA.debugLine="Main.fotopath3 = \"habitat_nidos\"";
mostCurrent._main._fotopath3 /*String*/  = "habitat_nidos";
 //BA.debugLineNum = 389;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 390;BA.debugLine="textOpcion3.Text = \"Nidos\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Nidos"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 392;BA.debugLine="textOpcion3.Text = \"Bird nest\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Bird nest"));
 };
 }else if(_position==3) { 
 //BA.debugLineNum = 398;BA.debugLine="Log(\"silvestre3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57536664","silvestre3",0);
 //BA.debugLineNum = 399;BA.debugLine="Main.fotopath3 = \"habitat_piedras\"";
mostCurrent._main._fotopath3 /*String*/  = "habitat_piedras";
 //BA.debugLineNum = 400;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 401;BA.debugLine="textOpcion3.Text = \"Piedras\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Piedras"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 403;BA.debugLine="textOpcion3.Text = \"Rocks\"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Rocks"));
 };
 }else if(_position==4) { 
 //BA.debugLineNum = 409;BA.debugLine="Log(\"silvestre4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57536675","silvestre4",0);
 //BA.debugLineNum = 410;BA.debugLine="Dim inputmsg As InputDialog";
_inputmsg = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 411;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 412;BA.debugLine="If inputmsg.Show(\"¿En qué otro lugar?\", \"Otro l";
if (_inputmsg.Show("¿En qué otro lugar?","Otro lugar","Ok","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null))==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 413;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 415;BA.debugLine="Main.fotopath3 = \"habitat_silvestre_\" & inputm";
mostCurrent._main._fotopath3 /*String*/  = "habitat_silvestre_"+_inputmsg.getInput();
 //BA.debugLineNum = 416;BA.debugLine="Log(Main.fotopath3)";
anywheresoftware.b4a.keywords.Common.LogImpl("57536682",mostCurrent._main._fotopath3 /*String*/ ,0);
 //BA.debugLineNum = 417;BA.debugLine="textOpcion3.Text = inputmsg.Input";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence(_inputmsg.getInput()));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 420;BA.debugLine="If inputmsg.Show(\"¿What other place?\", \"Other p";
if (_inputmsg.Show("¿What other place?","Other place","Ok","Cancel","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null))==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 421;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 423;BA.debugLine="Main.fotopath3 = \"habitat_silvestre_\" & inputm";
mostCurrent._main._fotopath3 /*String*/  = "habitat_silvestre_"+_inputmsg.getInput();
 //BA.debugLineNum = 424;BA.debugLine="Log(Main.fotopath3)";
anywheresoftware.b4a.keywords.Common.LogImpl("57536690",mostCurrent._main._fotopath3 /*String*/ ,0);
 //BA.debugLineNum = 425;BA.debugLine="textOpcion3.Text = inputmsg.Input";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence(_inputmsg.getInput()));
 };
 };
 };
 //BA.debugLineNum = 433;BA.debugLine="If Position <> 0 Then";
if (_position!=0) { 
 //BA.debugLineNum = 434;BA.debugLine="habitat_existe = True";
_habitat_existe = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 435;BA.debugLine="spnDomicilio.SelectedIndex = 0";
mostCurrent._spndomicilio.setSelectedIndex((int) (0));
 //BA.debugLineNum = 436;BA.debugLine="spnPeridomicilio.SelectedIndex = 0";
mostCurrent._spnperidomicilio.setSelectedIndex((int) (0));
 //BA.debugLineNum = 437;BA.debugLine="panelHabitat.RemoveView";
mostCurrent._panelhabitat.RemoveView();
 //BA.debugLineNum = 438;BA.debugLine="textOpcion1.Visible = True";
mostCurrent._textopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 439;BA.debugLine="textOpcion2.Visible = True";
mostCurrent._textopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 440;BA.debugLine="textOpcion3.Visible = True";
mostCurrent._textopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 441;BA.debugLine="btnFoto1.Visible = True";
mostCurrent._btnfoto1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 442;BA.debugLine="btnFoto2.Visible = True";
mostCurrent._btnfoto2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 443;BA.debugLine="btnHabitat.Visible = True";
mostCurrent._btnhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 444;BA.debugLine="Foto_Dorsal.Visible = True";
mostCurrent._foto_dorsal.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 445;BA.debugLine="Foto_Ventral.Visible = True";
mostCurrent._foto_ventral.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 446;BA.debugLine="lblInstrucciones.visible = True";
mostCurrent._lblinstrucciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 447;BA.debugLine="chkPrivado.Visible = True";
mostCurrent._chkprivado.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 448;BA.debugLine="lblReportePublico.Visible = True";
mostCurrent._lblreportepublico.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 450;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet_complete(cepave.geovin.httpjob _job) throws Exception{
 //BA.debugLineNum = 631;BA.debugLine="Sub TestInternet_Complete(Job As HttpJob)";
 //BA.debugLineNum = 632;BA.debugLine="Log(\"Chequeo de internet: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("57864321","Chequeo de internet: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 633;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 635;BA.debugLine="Main.modooffline = False";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 636;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 638;BA.debugLine="EnviarDatos";
_enviardatos();
 }else {
 //BA.debugLineNum = 642;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 643;BA.debugLine="Msgbox(\"No hay conexión a internet, prueba cuan";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No hay conexión a internet, prueba cuando estés conectado!"),BA.ObjectToCharSequence("No hay internet"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 645;BA.debugLine="Msgbox(\"No internet connection, try again later";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No internet connection, try again later!"),BA.ObjectToCharSequence("No internet"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 647;BA.debugLine="Main.modooffline = True";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 648;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 649;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 650;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 651;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 653;BA.debugLine="End Sub";
return "";
}
public static String  _timerenvio_tick() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 859;BA.debugLine="Sub TimerEnvio_Tick";
 //BA.debugLineNum = 860;BA.debugLine="If fotosEnviadas = totalFotos Then";
if (_fotosenviadas==_totalfotos) { 
 //BA.debugLineNum = 861;BA.debugLine="Log(\"TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMEN";
anywheresoftware.b4a.keywords.Common.LogImpl("58192002","TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMENTE",0);
 //BA.debugLineNum = 862;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 863;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 //BA.debugLineNum = 864;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 865;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 867;BA.debugLine="ToastMessageShow(\"Report sent\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 870;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 871;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 872;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 873;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto1sent",(Object)("si"),_map1);
 //BA.debugLineNum = 874;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"markers_loc";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"markers_local","foto2sent",(Object)("si"),_map1);
 //BA.debugLineNum = 875;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 876;BA.debugLine="utilidades.Mensaje(\"Felicitaciones!\", \"MsgIcon.";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Felicitaciones!","MsgIcon.png","Envío exitoso","Usted ha enviado las fotos correctamente y ya puede intentar determinar si el insecto que capturo es una vinchuca.","OK, continuar","","",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 878;BA.debugLine="utilidades.Mensaje(\"Congratulations!\", \"MsgIcon";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Congratulations!","MsgIcon.png","Upload successful","The photos have been uploaded correctly, and you can try to determine if the insect captured is a kissing but.","OK, continue","","",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 880;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 881;BA.debugLine="TimerOn = False";
_timeron = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 882;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 883;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 884;BA.debugLine="StartActivity(frmIdentificacionNew)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmidentificacionnew.getObject()));
 };
 //BA.debugLineNum = 886;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 159;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 160;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 161;BA.debugLine="lblInstrucciones.Text = \"Toma dos fotografías y";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("Toma dos fotografías y selecciona un hábitat"));
 //BA.debugLineNum = 162;BA.debugLine="textOpcion1.Text = \"Foto de arriba\"";
mostCurrent._textopcion1.setText(BA.ObjectToCharSequence("Foto de arriba"));
 //BA.debugLineNum = 163;BA.debugLine="textOpcion2.Text = \"Foto de abajo\"";
mostCurrent._textopcion2.setText(BA.ObjectToCharSequence("Foto de abajo"));
 //BA.debugLineNum = 164;BA.debugLine="btnContinuar.Text = \"Continuar\"";
mostCurrent._btncontinuar.setText(BA.ObjectToCharSequence("Continuar"));
 //BA.debugLineNum = 165;BA.debugLine="textOpcion3.Text = \"Hábitat \"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Hábitat "));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 167;BA.debugLine="lblInstrucciones.Text = \"Toma dos fotografías y";
mostCurrent._lblinstrucciones.setText(BA.ObjectToCharSequence("Toma dos fotografías y selecciona un hábitat"));
 //BA.debugLineNum = 168;BA.debugLine="textOpcion1.Text = \"Photo from above\"";
mostCurrent._textopcion1.setText(BA.ObjectToCharSequence("Photo from above"));
 //BA.debugLineNum = 169;BA.debugLine="textOpcion2.Text = \"Photo from below\"";
mostCurrent._textopcion2.setText(BA.ObjectToCharSequence("Photo from below"));
 //BA.debugLineNum = 170;BA.debugLine="textOpcion3.Text = \"Habitat \"";
mostCurrent._textopcion3.setText(BA.ObjectToCharSequence("Habitat "));
 //BA.debugLineNum = 171;BA.debugLine="btnContinuar.Text = \"Continue\"";
mostCurrent._btncontinuar.setText(BA.ObjectToCharSequence("Continue"));
 };
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 892;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 893;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("58323073","sendfile event:"+_value,0);
 //BA.debugLineNum = 894;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 896;BA.debugLine="If foto1 <> \"null\" And ProgressBar1.IsInitialize";
if ((mostCurrent._foto1).equals("null") == false && mostCurrent._progressbar1.IsInitialized() && mostCurrent._progressbar1.getProgress()==100) { 
 //BA.debugLineNum = 897;BA.debugLine="Log(\"TERMINO EL ENVIO FOTO 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("58323077","TERMINO EL ENVIO FOTO 1",0);
 //BA.debugLineNum = 898;BA.debugLine="foto1Sent = True";
_foto1sent = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 899;BA.debugLine="fotosEnviadas = fotosEnviadas+ 1";
_fotosenviadas = (int) (_fotosenviadas+1);
 };
 //BA.debugLineNum = 901;BA.debugLine="Log(\"FOTO #\" & fotosEnviadas & \"/\" & totalFotos";
anywheresoftware.b4a.keywords.Common.LogImpl("58323081","FOTO #"+BA.NumberToString(_fotosenviadas)+"/"+BA.NumberToString(_totalfotos)+" ENVIADA",0);
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 904;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("58323084","FOTO error",0);
 //BA.debugLineNum = 905;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 906;BA.debugLine="Msgbox(\"Ha habido un error en el envío. Revisa";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo desde 'Datos Anteriores'"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 908;BA.debugLine="Msgbox(\"Upload error. Check your connection and";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Upload error. Check your connection and try again from 'My profile'"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 910;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 911;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 //BA.debugLineNum = 912;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 913;BA.debugLine="TimerOn = False";
_timeron = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 914;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 915;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 916;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 918;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 889;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 890;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 919;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 921;BA.debugLine="End Sub";
return "";
}
public static String  _up2_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 927;BA.debugLine="Sub Up2_sendFile (value As String)";
 //BA.debugLineNum = 928;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("58519681","sendfile event:"+_value,0);
 //BA.debugLineNum = 929;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 931;BA.debugLine="If foto2 <> \"null\" And ProgressBar2.IsInitialize";
if ((mostCurrent._foto2).equals("null") == false && mostCurrent._progressbar2.IsInitialized() && mostCurrent._progressbar2.getProgress()==100) { 
 //BA.debugLineNum = 932;BA.debugLine="Log(\"TERMINO EL ENVIO FOTO 2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("58519685","TERMINO EL ENVIO FOTO 2",0);
 //BA.debugLineNum = 933;BA.debugLine="foto2Sent = True";
_foto2sent = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 934;BA.debugLine="fotosEnviadas = fotosEnviadas+ 1";
_fotosenviadas = (int) (_fotosenviadas+1);
 };
 //BA.debugLineNum = 936;BA.debugLine="Log(\"FOTO #\" & fotosEnviadas & \"/\" & totalFotos";
anywheresoftware.b4a.keywords.Common.LogImpl("58519689","FOTO #"+BA.NumberToString(_fotosenviadas)+"/"+BA.NumberToString(_totalfotos)+" ENVIADA",0);
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 938;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("58519691","FOTO error",0);
 //BA.debugLineNum = 939;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 940;BA.debugLine="Msgbox(\"Ha habido un error en el envío. Revisa";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo desde 'Datos Anteriores'"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 942;BA.debugLine="Msgbox(\"Upload error. Check your connection and";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Upload error. Check your connection and try again from 'My profile'"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 944;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 945;BA.debugLine="Up2.UploadKill";
_up2.UploadKill(processBA);
 //BA.debugLineNum = 946;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 947;BA.debugLine="TimerOn = False";
_timeron = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 948;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 949;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 950;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 952;BA.debugLine="End Sub";
return "";
}
public static String  _up2_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 924;BA.debugLine="Sub Up2_statusUpload (value As String)";
 //BA.debugLineNum = 925;BA.debugLine="End Sub";
return "";
}
public static String  _up2_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 953;BA.debugLine="Sub Up2_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 955;BA.debugLine="End Sub";
return "";
}
}
