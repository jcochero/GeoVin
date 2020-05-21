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

public class frmabout extends Activity implements B4AActivity{
	public static frmabout mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmabout");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmabout).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmabout");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmabout", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmabout) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmabout) Resume **");
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
		return frmabout.class;
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
        BA.LogInfo("** Activity (frmabout) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            frmabout mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmabout) Resume **");
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
public anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblversion = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblwebsite = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _fcbicon = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgcc = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo6 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo5 = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripmain = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblteam = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllicencia = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintext = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcolaboran = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcereve = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfinancian = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbloms = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.downloadservice _downloadservice = null;
public cepave.geovin.frmfotos _frmfotos = null;
public cepave.geovin.utilidades _utilidades = null;
public cepave.geovin.dbutils _dbutils = null;
public cepave.geovin.starter _starter = null;
public cepave.geovin.firebasemessaging _firebasemessaging = null;
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
 //BA.debugLineNum = 48;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 49;BA.debugLine="Activity.LoadLayout(\"layAbout_Panels\")";
mostCurrent._activity.LoadLayout("layAbout_Panels",mostCurrent.activityBA);
 //BA.debugLineNum = 50;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 51;BA.debugLine="tabStripMain.LoadLayout(\"layAbout_Project\", \"El";
mostCurrent._tabstripmain.LoadLayout("layAbout_Project",BA.ObjectToCharSequence("El Proyecto"));
 //BA.debugLineNum = 52;BA.debugLine="tabStripMain.LoadLayout(\"layAbout_People\", \"El e";
mostCurrent._tabstripmain.LoadLayout("layAbout_People",BA.ObjectToCharSequence("El equipo"));
 //BA.debugLineNum = 53;BA.debugLine="tabStripMain.LoadLayout(\"layAbout_Sponsors\", \"Au";
mostCurrent._tabstripmain.LoadLayout("layAbout_Sponsors",BA.ObjectToCharSequence("Auspician"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 56;BA.debugLine="tabStripMain.LoadLayout(\"layAbout_Project\", \"The";
mostCurrent._tabstripmain.LoadLayout("layAbout_Project",BA.ObjectToCharSequence("The project"));
 //BA.debugLineNum = 57;BA.debugLine="tabStripMain.LoadLayout(\"layAbout_People\", \"The";
mostCurrent._tabstripmain.LoadLayout("layAbout_People",BA.ObjectToCharSequence("The team"));
 //BA.debugLineNum = 58;BA.debugLine="tabStripMain.LoadLayout(\"layAbout_Sponsors\", \"Sp";
mostCurrent._tabstripmain.LoadLayout("layAbout_Sponsors",BA.ObjectToCharSequence("Sponsors"));
 };
 //BA.debugLineNum = 62;BA.debugLine="lblVersion.Text = Application.VersionCode";
mostCurrent._lblversion.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getVersionCode()));
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 75;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 76;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 77;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 67;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarabout_click() throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub btnCerrarAbout_Click";
 //BA.debugLineNum = 107;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 108;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static String  _fcbicon_click() throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Sub fcbIcon_Click";
 //BA.debugLineNum = 112;BA.debugLine="StartActivity(p.OpenBrowser(\"https://www.facebook";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("https://www.facebook.com/geovin/")));
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim p As PhoneIntents";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 17;BA.debugLine="Private lblVersion As Label";
mostCurrent._lblversion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblWebSite As Label";
mostCurrent._lblwebsite = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private fcbIcon As ImageView";
mostCurrent._fcbicon = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private imgCC As ImageView";
mostCurrent._imgcc = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private imgLogo3 As ImageView";
mostCurrent._imglogo3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private imgLogo1 As ImageView";
mostCurrent._imglogo1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private imgLogo4 As ImageView";
mostCurrent._imglogo4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private imgLogo2 As ImageView";
mostCurrent._imglogo2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private imgLogo6 As ImageView";
mostCurrent._imglogo6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private imgLogo5 As ImageView";
mostCurrent._imglogo5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private lblVersion As Label";
mostCurrent._lblversion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private tabStripMain As TabStrip";
mostCurrent._tabstripmain = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 36;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private lblTeam As Label";
mostCurrent._lblteam = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lblLicencia As Label";
mostCurrent._lbllicencia = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lblMainText As Label";
mostCurrent._lblmaintext = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private lblColaboran As Label";
mostCurrent._lblcolaboran = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private lblCeReVe As Label";
mostCurrent._lblcereve = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lblFinancian As Label";
mostCurrent._lblfinancian = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private lblOMS As Label";
mostCurrent._lbloms = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _imgcc_click() throws Exception{
 //BA.debugLineNum = 119;BA.debugLine="Sub imgCC_Click";
 //BA.debugLineNum = 120;BA.debugLine="StartActivity(p.OpenBrowser(\"https://creativecomm";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("https://creativecommons.org/licenses/by-nc/2.5/ar/")));
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _lblwebsite_click() throws Exception{
 //BA.debugLineNum = 115;BA.debugLine="Sub lblWebSite_Click";
 //BA.debugLineNum = 116;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.geovin.co";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("http://www.geovin.com.ar")));
 //BA.debugLineNum = 117;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 81;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 82;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 83;BA.debugLine="lblTeam.Text = \"El equipo GeoVin\"";
mostCurrent._lblteam.setText(BA.ObjectToCharSequence("El equipo GeoVin"));
 //BA.debugLineNum = 84;BA.debugLine="Label2.Text = \"Colaboradores\"";
mostCurrent._label2.setText(BA.ObjectToCharSequence("Colaboradores"));
 //BA.debugLineNum = 85;BA.debugLine="lblLicencia.Text = \"Todos los datos enviados por";
mostCurrent._lbllicencia.setText(BA.ObjectToCharSequence("Todos los datos enviados por los usuarios, a excepción de sus datos personales, serán compartidos bajo la licencia CC-BY-NC"));
 //BA.debugLineNum = 86;BA.debugLine="lblMainText.Text = \"GeoVin es una aplicación púb";
mostCurrent._lblmaintext.setText(BA.ObjectToCharSequence("GeoVin es una aplicación pública y gratuita, y su fin es orientar a todo tipo de usuarios en la identificación de posibles vinchucas que encuentren y que puedan implicar un riesgo epidemiológico. En base a los datos que se registren de localizaciones de las diferentes especies de vinchucas, la aplicación elabora mapas geográficos donde el usuario podrá visibilizar sus hallazgos junto con los de otros usuarios. Toda la información utilizada para la elaboración de la App “GeoVin” fue obtenida a partir de publicaciones científicas, datos cedidos por colegas y datos propios del Laboratorio de Triatominos del Centro de Estudios Parasitológicos y de Vectores (CEPAVE)."));
 //BA.debugLineNum = 87;BA.debugLine="lblColaboran.Text = \"Colaboraron en su elaboraci";
mostCurrent._lblcolaboran.setText(BA.ObjectToCharSequence("Colaboraron en su elaboración"));
 //BA.debugLineNum = 88;BA.debugLine="lblCeReVe.Text = \"Y el Centro de Referencia de V";
mostCurrent._lblcereve.setText(BA.ObjectToCharSequence("Y el Centro de Referencia de Vectores (CeReVe), Coordinación Nacional de Vectores, Ministerio de Salud de la Nación."));
 //BA.debugLineNum = 89;BA.debugLine="lblFinancian.Text = \"Con el financiamiento de\"";
mostCurrent._lblfinancian.setText(BA.ObjectToCharSequence("Con el financiamiento de"));
 //BA.debugLineNum = 90;BA.debugLine="lblOMS.Text = \"Con el apoyo de la Organización M";
mostCurrent._lbloms.setText(BA.ObjectToCharSequence("Con el apoyo de la Organización Mundial de la Salud (OMS)"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 92;BA.debugLine="lblTeam.Text = \"GeoVin Team\"";
mostCurrent._lblteam.setText(BA.ObjectToCharSequence("GeoVin Team"));
 //BA.debugLineNum = 93;BA.debugLine="Label2.Text = \"Collaborators\"";
mostCurrent._label2.setText(BA.ObjectToCharSequence("Collaborators"));
 //BA.debugLineNum = 94;BA.debugLine="lblLicencia.Text = \"All data sent by users, exce";
mostCurrent._lbllicencia.setText(BA.ObjectToCharSequence("All data sent by users, except any personal information, can be shared under a CC-BY-NC license"));
 //BA.debugLineNum = 95;BA.debugLine="lblMainText.Text = \"GeoVin is a free app, to gui";
mostCurrent._lblmaintext.setText(BA.ObjectToCharSequence("GeoVin is a free app, to guide users in the identification of kissing bugs that might represent an epidemiological issue. With the help of the reports, the project generates open and free geographical maps of the distribution of kissing bugs. All information collected by the “GeoVin” team, also shown in the maps was collected from museums, articles and data collected by the Laboratorio de Triatominos of the Centro de Estudios Parasitológicos y de Vectores (CEPAVE, Argentina)."));
 //BA.debugLineNum = 96;BA.debugLine="lblColaboran.Text = \"Collaborated in its creatio";
mostCurrent._lblcolaboran.setText(BA.ObjectToCharSequence("Collaborated in its creation"));
 //BA.debugLineNum = 97;BA.debugLine="lblCeReVe.Text = \"And the Centro de Referencia d";
mostCurrent._lblcereve.setText(BA.ObjectToCharSequence("And the Centro de Referencia de Vectores (CeReVe), Coordinación Nacional de Vectores, Ministerio de Salud de la Nación."));
 //BA.debugLineNum = 98;BA.debugLine="lblFinancian.Text = \"Financed by\"";
mostCurrent._lblfinancian.setText(BA.ObjectToCharSequence("Financed by"));
 //BA.debugLineNum = 99;BA.debugLine="lblOMS.Text = \"With the support of the World Hea";
mostCurrent._lbloms.setText(BA.ObjectToCharSequence("With the support of the World Healt Organization (WHO)"));
 };
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
}
