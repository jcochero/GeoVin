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

public class register extends Activity implements B4AActivity{
	public static register mostCurrent;
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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.register");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (register).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.register");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.register", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (register) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (register) Resume **");
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
		return register.class;
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
        BA.LogInfo("** Activity (register) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            register mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (register) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _txtuserid = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfullname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlocation = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtorg = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public de.amberhome.viewpager.AHPageContainer _container = null;
public de.amberhome.viewpager.AHViewPager _pager = null;
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
public cepave.geovin.frmreportevinchuca _frmreportevinchuca = null;
public cepave.geovin.envioarchivos2 _envioarchivos2 = null;
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="register";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=19464192;
 //BA.debugLineNum = 19464192;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=19464193;
 //BA.debugLineNum = 19464193;BA.debugLine="Activity.LoadLayout(\"frmRegister\")";
mostCurrent._activity.LoadLayout("frmRegister",mostCurrent.activityBA);
RDebugUtils.currentLine=19464194;
 //BA.debugLineNum = 19464194;BA.debugLine="Panel3.RemoveView";
mostCurrent._panel3.RemoveView();
RDebugUtils.currentLine=19464195;
 //BA.debugLineNum = 19464195;BA.debugLine="Panel1.RemoveView";
mostCurrent._panel1.RemoveView();
RDebugUtils.currentLine=19464198;
 //BA.debugLineNum = 19464198;BA.debugLine="Container.Initialize";
mostCurrent._container.Initialize(mostCurrent.activityBA);
RDebugUtils.currentLine=19464199;
 //BA.debugLineNum = 19464199;BA.debugLine="Container.AddPageAt(Panel3, \"Panel3\", 0)";
mostCurrent._container.AddPageAt((android.view.View)(mostCurrent._panel3.getObject()),"Panel3",(int) (0));
RDebugUtils.currentLine=19464200;
 //BA.debugLineNum = 19464200;BA.debugLine="Container.AddPageAt(Panel1, \"Panel1\", 1)";
mostCurrent._container.AddPageAt((android.view.View)(mostCurrent._panel1.getObject()),"Panel1",(int) (1));
RDebugUtils.currentLine=19464201;
 //BA.debugLineNum = 19464201;BA.debugLine="Pager.Initialize2(Container, \"Pager\")";
mostCurrent._pager.Initialize2(mostCurrent.activityBA,mostCurrent._container,"Pager");
RDebugUtils.currentLine=19464202;
 //BA.debugLineNum = 19464202;BA.debugLine="Activity.AddView(Pager, 0dip,0dip, Activity.Width";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._pager.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight());
RDebugUtils.currentLine=19464206;
 //BA.debugLineNum = 19464206;BA.debugLine="Spinner1.Add(\"Profesional científico\")";
mostCurrent._spinner1.Add("Profesional científico");
RDebugUtils.currentLine=19464207;
 //BA.debugLineNum = 19464207;BA.debugLine="Spinner1.Add(\"Docente secundario\")";
mostCurrent._spinner1.Add("Docente secundario");
RDebugUtils.currentLine=19464208;
 //BA.debugLineNum = 19464208;BA.debugLine="Spinner1.Add(\"Escuela\")";
mostCurrent._spinner1.Add("Escuela");
RDebugUtils.currentLine=19464209;
 //BA.debugLineNum = 19464209;BA.debugLine="Spinner1.Add(\"Alumno, individual\")";
mostCurrent._spinner1.Add("Alumno, individual");
RDebugUtils.currentLine=19464210;
 //BA.debugLineNum = 19464210;BA.debugLine="Spinner1.Add(\"Amateur\")";
mostCurrent._spinner1.Add("Amateur");
RDebugUtils.currentLine=19464211;
 //BA.debugLineNum = 19464211;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="register";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode}));}
RDebugUtils.currentLine=19529728;
 //BA.debugLineNum = 19529728;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=19529729;
 //BA.debugLineNum = 19529729;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=19529730;
 //BA.debugLineNum = 19529730;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=19529731;
 //BA.debugLineNum = 19529731;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
RDebugUtils.currentLine=19529733;
 //BA.debugLineNum = 19529733;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="register";
RDebugUtils.currentLine=19660800;
 //BA.debugLineNum = 19660800;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=19660802;
 //BA.debugLineNum = 19660802;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="register";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=19595264;
 //BA.debugLineNum = 19595264;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=19595266;
 //BA.debugLineNum = 19595266;BA.debugLine="End Sub";
return "";
}
public static String  _btnacepto_click() throws Exception{
RDebugUtils.currentModule="register";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnacepto_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnacepto_click", null));}
RDebugUtils.currentLine=20054016;
 //BA.debugLineNum = 20054016;BA.debugLine="Sub btnAcepto_Click";
RDebugUtils.currentLine=20054017;
 //BA.debugLineNum = 20054017;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
RDebugUtils.currentLine=20054018;
 //BA.debugLineNum = 20054018;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
RDebugUtils.currentLine=20054019;
 //BA.debugLineNum = 20054019;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
RDebugUtils.currentLine=20054020;
 //BA.debugLineNum = 20054020;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuarregister_click() throws Exception{
RDebugUtils.currentModule="register";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncontinuarregister_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncontinuarregister_click", null));}
String _struserid = "";
String _strpassword = "";
String _strpassword2 = "";
String _stremail = "";
RDebugUtils.currentLine=19791872;
 //BA.debugLineNum = 19791872;BA.debugLine="Sub btnContinuarRegister_Click";
RDebugUtils.currentLine=19791874;
 //BA.debugLineNum = 19791874;BA.debugLine="Dim strUserID As String = txtUserID.Text.Trim";
_struserid = mostCurrent._txtuserid.getText().trim();
RDebugUtils.currentLine=19791875;
 //BA.debugLineNum = 19791875;BA.debugLine="If strUserID = \"\" Then";
if ((_struserid).equals("")) { 
RDebugUtils.currentLine=19791876;
 //BA.debugLineNum = 19791876;BA.debugLine="Msgbox(\"Ingrese nombre de usuario\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese nombre de usuario"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19791877;
 //BA.debugLineNum = 19791877;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19791879;
 //BA.debugLineNum = 19791879;BA.debugLine="Dim strPassword As String = txtPassword.Text.Trim";
_strpassword = mostCurrent._txtpassword.getText().trim();
RDebugUtils.currentLine=19791880;
 //BA.debugLineNum = 19791880;BA.debugLine="If strPassword = \"\" Then";
if ((_strpassword).equals("")) { 
RDebugUtils.currentLine=19791881;
 //BA.debugLineNum = 19791881;BA.debugLine="Msgbox(\"Ingrese contraseña\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese contraseña"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19791882;
 //BA.debugLineNum = 19791882;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19791884;
 //BA.debugLineNum = 19791884;BA.debugLine="Dim strPassword2 As String = txtPassword2.Text.Tr";
_strpassword2 = mostCurrent._txtpassword2.getText().trim();
RDebugUtils.currentLine=19791885;
 //BA.debugLineNum = 19791885;BA.debugLine="If strPassword2 = \"\" Then";
if ((_strpassword2).equals("")) { 
RDebugUtils.currentLine=19791886;
 //BA.debugLineNum = 19791886;BA.debugLine="Msgbox(\"Confirme su contraseña\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Confirme su contraseña"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19791887;
 //BA.debugLineNum = 19791887;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19791889;
 //BA.debugLineNum = 19791889;BA.debugLine="If strPassword <> strPassword2 Then";
if ((_strpassword).equals(_strpassword2) == false) { 
RDebugUtils.currentLine=19791890;
 //BA.debugLineNum = 19791890;BA.debugLine="Msgbox(\"Las contraseñas no coinciden\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Las contraseñas no coinciden"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19791891;
 //BA.debugLineNum = 19791891;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19791894;
 //BA.debugLineNum = 19791894;BA.debugLine="Dim strEmail As String = txtEmail.Text.Trim";
_stremail = mostCurrent._txtemail.getText().trim();
RDebugUtils.currentLine=19791895;
 //BA.debugLineNum = 19791895;BA.debugLine="If strEmail = \"\" Then";
if ((_stremail).equals("")) { 
RDebugUtils.currentLine=19791896;
 //BA.debugLineNum = 19791896;BA.debugLine="Msgbox(\"Ingrese email\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese email"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19791897;
 //BA.debugLineNum = 19791897;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19791899;
 //BA.debugLineNum = 19791899;BA.debugLine="If Validate_Email(strEmail) = False Then";
if (_validate_email(_stremail)==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=19791900;
 //BA.debugLineNum = 19791900;BA.debugLine="Msgbox(\"Formato de email incorrecto\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Formato de email incorrecto"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19791901;
 //BA.debugLineNum = 19791901;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19791904;
 //BA.debugLineNum = 19791904;BA.debugLine="Pager.GotoPage(1,True)";
mostCurrent._pager.GotoPage((int) (1),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=19791907;
 //BA.debugLineNum = 19791907;BA.debugLine="End Sub";
return "";
}
public static boolean  _validate_email(String _emailaddress) throws Exception{
RDebugUtils.currentModule="register";
if (Debug.shouldDelegate(mostCurrent.activityBA, "validate_email"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "validate_email", new Object[] {_emailaddress}));}
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matchemail = null;
RDebugUtils.currentLine=19922944;
 //BA.debugLineNum = 19922944;BA.debugLine="Sub Validate_Email(EmailAddress As String) As Bool";
RDebugUtils.currentLine=19922945;
 //BA.debugLineNum = 19922945;BA.debugLine="Dim MatchEmail As Matcher = Regex.Matcher(\"^(?";
_matchemail = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_matchemail = anywheresoftware.b4a.keywords.Common.Regex.Matcher("^(?i)[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])$",_emailaddress);
RDebugUtils.currentLine=19922947;
 //BA.debugLineNum = 19922947;BA.debugLine="If MatchEmail.Find = True Then";
if (_matchemail.Find()==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=19922949;
 //BA.debugLineNum = 19922949;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
RDebugUtils.currentLine=19922952;
 //BA.debugLineNum = 19922952;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
RDebugUtils.currentLine=19922954;
 //BA.debugLineNum = 19922954;BA.debugLine="End Sub";
return false;
}
public static void  _btnregister_click() throws Exception{
RDebugUtils.currentModule="register";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnregister_click"))
	 {Debug.delegate(mostCurrent.activityBA, "btnregister_click", null); return;}
ResumableSub_btnRegister_Click rsub = new ResumableSub_btnRegister_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnRegister_Click extends BA.ResumableSub {
public ResumableSub_btnRegister_Click(cepave.geovin.register parent) {
this.parent = parent;
}
cepave.geovin.register parent;
String _struserid = "";
String _strpassword = "";
String _strpassword2 = "";
String _stremail = "";
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{
RDebugUtils.currentModule="register";

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
RDebugUtils.currentLine=19857410;
 //BA.debugLineNum = 19857410;BA.debugLine="Dim strUserID As String = txtUserID.Text.Trim";
_struserid = parent.mostCurrent._txtuserid.getText().trim();
RDebugUtils.currentLine=19857411;
 //BA.debugLineNum = 19857411;BA.debugLine="If strUserID = \"\" Then";
if (true) break;

case 1:
//if
this.state = 4;
if ((_struserid).equals("")) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
RDebugUtils.currentLine=19857412;
 //BA.debugLineNum = 19857412;BA.debugLine="Msgbox(\"Ingrese nombre de usuario\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese nombre de usuario"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19857413;
 //BA.debugLineNum = 19857413;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = 5;
;
RDebugUtils.currentLine=19857415;
 //BA.debugLineNum = 19857415;BA.debugLine="Dim strPassword As String = txtPassword.Text.Trim";
_strpassword = parent.mostCurrent._txtpassword.getText().trim();
RDebugUtils.currentLine=19857416;
 //BA.debugLineNum = 19857416;BA.debugLine="If strPassword = \"\" Then";
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
RDebugUtils.currentLine=19857417;
 //BA.debugLineNum = 19857417;BA.debugLine="Msgbox(\"Ingrese contraseña\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese contraseña"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19857418;
 //BA.debugLineNum = 19857418;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 8:
//C
this.state = 9;
;
RDebugUtils.currentLine=19857420;
 //BA.debugLineNum = 19857420;BA.debugLine="Dim strPassword2 As String = txtPassword2.Text.Tr";
_strpassword2 = parent.mostCurrent._txtpassword2.getText().trim();
RDebugUtils.currentLine=19857421;
 //BA.debugLineNum = 19857421;BA.debugLine="If strPassword2 = \"\" Then";
if (true) break;

case 9:
//if
this.state = 12;
if ((_strpassword2).equals("")) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
RDebugUtils.currentLine=19857422;
 //BA.debugLineNum = 19857422;BA.debugLine="Msgbox(\"Confirme su contraseña\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Confirme su contraseña"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19857423;
 //BA.debugLineNum = 19857423;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
RDebugUtils.currentLine=19857425;
 //BA.debugLineNum = 19857425;BA.debugLine="If strPassword <> strPassword2 Then";

case 12:
//if
this.state = 15;
if ((_strpassword).equals(_strpassword2) == false) { 
this.state = 14;
}if (true) break;

case 14:
//C
this.state = 15;
RDebugUtils.currentLine=19857426;
 //BA.debugLineNum = 19857426;BA.debugLine="Msgbox(\"Las contraseñas no coinciden\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Las contraseñas no coinciden"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19857427;
 //BA.debugLineNum = 19857427;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 15:
//C
this.state = 16;
;
RDebugUtils.currentLine=19857430;
 //BA.debugLineNum = 19857430;BA.debugLine="Dim strEmail As String = txtEmail.Text.Trim";
_stremail = parent.mostCurrent._txtemail.getText().trim();
RDebugUtils.currentLine=19857431;
 //BA.debugLineNum = 19857431;BA.debugLine="If strEmail = \"\" Then";
if (true) break;

case 16:
//if
this.state = 19;
if ((_stremail).equals("")) { 
this.state = 18;
}if (true) break;

case 18:
//C
this.state = 19;
RDebugUtils.currentLine=19857432;
 //BA.debugLineNum = 19857432;BA.debugLine="Msgbox(\"Ingrese email\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese email"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19857433;
 //BA.debugLineNum = 19857433;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
RDebugUtils.currentLine=19857435;
 //BA.debugLineNum = 19857435;BA.debugLine="If Validate_Email(strEmail) = False Then";

case 19:
//if
this.state = 22;
if (_validate_email(_stremail)==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 21;
}if (true) break;

case 21:
//C
this.state = 22;
RDebugUtils.currentLine=19857436;
 //BA.debugLineNum = 19857436;BA.debugLine="Msgbox(\"Formato de email incorrecto\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Formato de email incorrecto"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19857437;
 //BA.debugLineNum = 19857437;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 22:
//C
this.state = 23;
;
RDebugUtils.currentLine=19857443;
 //BA.debugLineNum = 19857443;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=19857444;
 //BA.debugLineNum = 19857444;BA.debugLine="j.Initialize(\"Register\", Me)";
_j._initialize(processBA,"Register",register.getObject());
RDebugUtils.currentLine=19857445;
 //BA.debugLineNum = 19857445;BA.debugLine="j.Download2(Main.serverPath & \"/connect/signup.ph";
_j._download2(parent.mostCurrent._main._serverpath+"/connect/signup.php",new String[]{"Action","Register","UserID",parent.mostCurrent._txtuserid.getText(),"Password",parent.mostCurrent._txtpassword.getText(),"FullName",parent.mostCurrent._txtfullname.getText(),"Location",parent.mostCurrent._txtlocation.getText(),"Org",parent.mostCurrent._txtorg.getText(),"usertipo",parent.mostCurrent._spinner1.getSelectedItem(),"Email",parent.mostCurrent._txtemail.getText(),"deviceID",parent.mostCurrent._main._deviceid});
RDebugUtils.currentLine=19857456;
 //BA.debugLineNum = 19857456;BA.debugLine="ProgressDialogShow(\"Registrando el usuario...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Registrando el usuario..."));
RDebugUtils.currentLine=19857457;
 //BA.debugLineNum = 19857457;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, new anywheresoftware.b4a.shell.DebugResumableSub.DelegatableResumableSub(this, "register", "btnregister_click"), (Object)(_j));
this.state = 37;
return;
case 37:
//C
this.state = 23;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
RDebugUtils.currentLine=19857459;
 //BA.debugLineNum = 19857459;BA.debugLine="If j.Success Then";
if (true) break;

case 23:
//if
this.state = 36;
if (_j._success) { 
this.state = 25;
}else {
this.state = 35;
}if (true) break;

case 25:
//C
this.state = 26;
RDebugUtils.currentLine=19857460;
 //BA.debugLineNum = 19857460;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
RDebugUtils.currentLine=19857461;
 //BA.debugLineNum = 19857461;BA.debugLine="res = j.GetString";
_res = _j._getstring();
RDebugUtils.currentLine=19857462;
 //BA.debugLineNum = 19857462;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=19857463;
 //BA.debugLineNum = 19857463;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
RDebugUtils.currentLine=19857464;
 //BA.debugLineNum = 19857464;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
RDebugUtils.currentLine=19857465;
 //BA.debugLineNum = 19857465;BA.debugLine="If action = \"Mail\" Then";
if (true) break;

case 26:
//if
this.state = 33;
if ((_action).equals("Mail")) { 
this.state = 28;
}else 
{RDebugUtils.currentLine=19857469;
 //BA.debugLineNum = 19857469;BA.debugLine="Else If action = \"MailInUse\" Then";
if ((_action).equals("MailInUse")) { 
this.state = 30;
}else {
this.state = 32;
}}
if (true) break;

case 28:
//C
this.state = 33;
RDebugUtils.currentLine=19857466;
 //BA.debugLineNum = 19857466;BA.debugLine="Msgbox(\"Usuario registrado!\", \"Felicitaciones\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Usuario registrado!"),BA.ObjectToCharSequence("Felicitaciones"),mostCurrent.activityBA);
RDebugUtils.currentLine=19857467;
 //BA.debugLineNum = 19857467;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=19857468;
 //BA.debugLineNum = 19857468;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 30:
//C
this.state = 33;
RDebugUtils.currentLine=19857470;
 //BA.debugLineNum = 19857470;BA.debugLine="Msgbox(\"El usuario '\" & txtUserID.Text & \"' o e";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("El usuario '"+parent.mostCurrent._txtuserid.getText()+"' o el email ("+parent.mostCurrent._txtemail.getText()+") ya están en uso"),BA.ObjectToCharSequence("Registro"),mostCurrent.activityBA);
 if (true) break;

case 32:
//C
this.state = 33;
RDebugUtils.currentLine=19857472;
 //BA.debugLineNum = 19857472;BA.debugLine="Msgbox(\"El servidor no devolvió los valores esp";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("El servidor no devolvió los valores esperados."+_j._errormessage),BA.ObjectToCharSequence("Registro"),mostCurrent.activityBA);
 if (true) break;

case 33:
//C
this.state = 36;
;
 if (true) break;

case 35:
//C
this.state = 36;
RDebugUtils.currentLine=19857475;
 //BA.debugLineNum = 19857475;BA.debugLine="ToastMessageShow(\"Error: \" & j.ErrorMessage, Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: "+_j._errormessage),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 36:
//C
this.state = -1;
;
RDebugUtils.currentLine=19857477;
 //BA.debugLineNum = 19857477;BA.debugLine="j.Release";
_j._release();
RDebugUtils.currentLine=19857479;
 //BA.debugLineNum = 19857479;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _lblleerlegales_click() throws Exception{
RDebugUtils.currentModule="register";
if (Debug.shouldDelegate(mostCurrent.activityBA, "lblleerlegales_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "lblleerlegales_click", null));}
String _txtlegales = "";
anywheresoftware.b4a.objects.LabelWrapper _lbllegales = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _scrlegales = null;
anywheresoftware.b4a.objects.ButtonWrapper _btnacepto = null;
anywheresoftware.b4a.objects.ImageViewWrapper _fondogris = null;
float _ht = 0f;
anywheresoftware.b4a.objects.StringUtils _strutil = null;
RDebugUtils.currentLine=19988480;
 //BA.debugLineNum = 19988480;BA.debugLine="Sub lblLeerLegales_Click";
RDebugUtils.currentLine=19988483;
 //BA.debugLineNum = 19988483;BA.debugLine="Dim txtLegales As String";
_txtlegales = "";
RDebugUtils.currentLine=19988484;
 //BA.debugLineNum = 19988484;BA.debugLine="Dim lbllegales As Label";
_lbllegales = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=19988485;
 //BA.debugLineNum = 19988485;BA.debugLine="Dim scrLegales As ScrollView";
_scrlegales = new anywheresoftware.b4a.objects.ScrollViewWrapper();
RDebugUtils.currentLine=19988486;
 //BA.debugLineNum = 19988486;BA.debugLine="Dim btnAcepto As Button";
_btnacepto = new anywheresoftware.b4a.objects.ButtonWrapper();
RDebugUtils.currentLine=19988487;
 //BA.debugLineNum = 19988487;BA.debugLine="Dim fondogris As ImageView";
_fondogris = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=19988489;
 //BA.debugLineNum = 19988489;BA.debugLine="fondogris.Initialize(\"fondogris\")";
_fondogris.Initialize(mostCurrent.activityBA,"fondogris");
RDebugUtils.currentLine=19988490;
 //BA.debugLineNum = 19988490;BA.debugLine="fondogris.Color = Colors.ARGB(150, 64,64,64)";
_fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
RDebugUtils.currentLine=19988492;
 //BA.debugLineNum = 19988492;BA.debugLine="lbllegales.Initialize(\"\")";
_lbllegales.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=19988493;
 //BA.debugLineNum = 19988493;BA.debugLine="scrLegales.Initialize(100%y)";
_scrlegales.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=19988494;
 //BA.debugLineNum = 19988494;BA.debugLine="btnAcepto.Initialize(\"btnAcepto\")";
_btnacepto.Initialize(mostCurrent.activityBA,"btnAcepto");
RDebugUtils.currentLine=19988495;
 //BA.debugLineNum = 19988495;BA.debugLine="scrLegales.Color = Colors.White";
_scrlegales.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=19988496;
 //BA.debugLineNum = 19988496;BA.debugLine="lbllegales.TextColor = Colors.Black";
_lbllegales.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=19988497;
 //BA.debugLineNum = 19988497;BA.debugLine="btnAcepto.Color = Colors.ARGB(255,160,221,202)";
_btnacepto.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (160),(int) (221),(int) (202)));
RDebugUtils.currentLine=19988498;
 //BA.debugLineNum = 19988498;BA.debugLine="btnAcepto.Text = \"Cerrar\"";
_btnacepto.setText(BA.ObjectToCharSequence("Cerrar"));
RDebugUtils.currentLine=19988499;
 //BA.debugLineNum = 19988499;BA.debugLine="btnAcepto.TextColor = Colors.Black";
_btnacepto.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=19988501;
 //BA.debugLineNum = 19988501;BA.debugLine="Activity.AddView(fondogris, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(_fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=19988502;
 //BA.debugLineNum = 19988502;BA.debugLine="Activity.AddView(scrLegales, 10%x, 10%y, 80%x, 60";
mostCurrent._activity.AddView((android.view.View)(_scrlegales.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (60),mostCurrent.activityBA));
RDebugUtils.currentLine=19988503;
 //BA.debugLineNum = 19988503;BA.debugLine="Activity.AddView(btnAcepto, 10%x, 70%y, 80%x, 10%";
mostCurrent._activity.AddView((android.view.View)(_btnacepto.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
RDebugUtils.currentLine=19988504;
 //BA.debugLineNum = 19988504;BA.debugLine="scrLegales.Panel.AddView(lbllegales,10,0,scrLegal";
_scrlegales.getPanel().AddView((android.view.View)(_lbllegales.getObject()),(int) (10),(int) (0),(int) (_scrlegales.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=19988507;
 //BA.debugLineNum = 19988507;BA.debugLine="txtLegales = \"LEGALES\" & CRLF & CRLF";
_txtlegales = "LEGALES"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988508;
 //BA.debugLineNum = 19988508;BA.debugLine="txtLegales = txtLegales & \"GeoVin es un proyecto";
_txtlegales = _txtlegales+"GeoVin es un proyecto de ciencia ciudadana del Centro de Parásitos y Vectores (CEPAVE), dependiente del Consejo Nacional de Investigaciones Científicas y Técnicas y de la Universidad Nacional de La Plata."+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988509;
 //BA.debugLineNum = 19988509;BA.debugLine="txtLegales = txtLegales & \"El proyecto depende de";
_txtlegales = _txtlegales+"El proyecto depende del envío de datos por parte de la ciudadanía sobre mosquitos."+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988510;
 //BA.debugLineNum = 19988510;BA.debugLine="txtLegales = txtLegales & \"Los ciudadanos partici";
_txtlegales = _txtlegales+"Los ciudadanos participan compartiendo datos a través de la aplicación para dispositivos móviles. Para ello, será necesario ser mayor de edad o participar bajo la supervisión de un adulto y estar de acuerdo con la política de privacidad de GeoVin, así como con los siguientes términos:"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988511;
 //BA.debugLineNum = 19988511;BA.debugLine="txtLegales = txtLegales & \"1. Al compartir tus da";
_txtlegales = _txtlegales+"1. Al compartir tus datos con el proyecto GeoVin, concedes a los miembros del equipo de investigación una licencia perpetua, libre de royaltys, no-exclusiva y sub-licenciable, para: usar, reproducir, modificar, adaptar, publicar, traducir, crear trabajos derivados, distribuir y ejercer todos los derechos de autor y de publicidad en todo el mundo respecto a tu contribución y/o incorporar tu contribución en otros trabajos en cualquier medio conocido actualmente o desarrollado en el futuro."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988512;
 //BA.debugLineNum = 19988512;BA.debugLine="txtLegales = txtLegales & \"2. Garantizas que tien";
_txtlegales = _txtlegales+"2. Garantizas que tienes el derecho de poner a disposición del equipo de investigación cualquier dato que compartas para los fines especificados en el punto 1 anterior y que estos datos no son difamatorios, ni infringen ninguna ley."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988513;
 //BA.debugLineNum = 19988513;BA.debugLine="txtLegales = txtLegales & \"3. La información en e";
_txtlegales = _txtlegales+"3. La información en el proyecto GeoVin, tanto en la app como en su sitio web, no será utilizada de ninguna manera como evidencia científica o técnica por los usuarios sin previa autorización de los directores del proyecto y del CEPAVE."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988515;
 //BA.debugLineNum = 19988515;BA.debugLine="txtLegales = txtLegales & \"POLITICA DE PRIVACIDAD";
_txtlegales = _txtlegales+"POLITICA DE PRIVACIDAD"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988516;
 //BA.debugLineNum = 19988516;BA.debugLine="txtLegales = txtLegales & \"El proyecto GeoVin rec";
_txtlegales = _txtlegales+"El proyecto GeoVin recoge datos compartidos por los voluntarios y los pone a disposición de terceras partes y del público general. Con el fin de proteger la privacidad de sus voluntarios, se evita la exposición de información que pueda identificar de manera individual a una persona física determinada. Así, no se muestran nombres, direcciones, contraseñas o cualquier otra información personal de los voluntarios en el sitio web de GeoVin."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988517;
 //BA.debugLineNum = 19988517;BA.debugLine="txtLegales = txtLegales & \"Se recogen las ubicaci";
_txtlegales = _txtlegales+"Se recogen las ubicaciones exactas de los voluntarios durante el envío, pero esta información se vincula solamente al informe. Los voluntarios pueden incluir notas y fotografías en sus informes, pero es su responsabilidad no incluir en estas notas y fotografías cualquier información que deseen mantener en privado o cualquier información que viole los derechos de privacidad de los demás o sea incompatible con el acuerdo del usuario de GeoVin"+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988518;
 //BA.debugLineNum = 19988518;BA.debugLine="txtLegales = txtLegales & \"Para más información d";
_txtlegales = _txtlegales+"Para más información dirigirse a: http://www.geovin.com.ar/privacy/geovin-privacy.html"+anywheresoftware.b4a.keywords.Common.CRLF;
RDebugUtils.currentLine=19988520;
 //BA.debugLineNum = 19988520;BA.debugLine="lbllegales.Text = txtLegales";
_lbllegales.setText(BA.ObjectToCharSequence(_txtlegales));
RDebugUtils.currentLine=19988522;
 //BA.debugLineNum = 19988522;BA.debugLine="Dim ht As Float";
_ht = 0f;
RDebugUtils.currentLine=19988523;
 //BA.debugLineNum = 19988523;BA.debugLine="Dim StrUtil As StringUtils";
_strutil = new anywheresoftware.b4a.objects.StringUtils();
RDebugUtils.currentLine=19988525;
 //BA.debugLineNum = 19988525;BA.debugLine="ht = StrUtil.MeasureMultilineTextHeight(lbllegale";
_ht = (float) (_strutil.MeasureMultilineTextHeight((android.widget.TextView)(_lbllegales.getObject()),BA.ObjectToCharSequence(_txtlegales)));
RDebugUtils.currentLine=19988526;
 //BA.debugLineNum = 19988526;BA.debugLine="scrLegales.Panel.Height = ht		' set the ScrollVie";
_scrlegales.getPanel().setHeight((int) (_ht));
RDebugUtils.currentLine=19988527;
 //BA.debugLineNum = 19988527;BA.debugLine="lbllegales.Height = ht					' set the Label height";
_lbllegales.setHeight((int) (_ht));
RDebugUtils.currentLine=19988529;
 //BA.debugLineNum = 19988529;BA.debugLine="scrLegales.ScrollPosition = 0";
_scrlegales.setScrollPosition((int) (0));
RDebugUtils.currentLine=19988530;
 //BA.debugLineNum = 19988530;BA.debugLine="End Sub";
return "";
}
public static String  _pager_pagechanged(int _position) throws Exception{
RDebugUtils.currentModule="register";
if (Debug.shouldDelegate(mostCurrent.activityBA, "pager_pagechanged"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "pager_pagechanged", new Object[] {_position}));}
String _struserid = "";
String _strpassword = "";
String _strpassword2 = "";
String _stremail = "";
RDebugUtils.currentLine=19726336;
 //BA.debugLineNum = 19726336;BA.debugLine="Sub Pager_PageChanged (Position As Int)";
RDebugUtils.currentLine=19726337;
 //BA.debugLineNum = 19726337;BA.debugLine="If Position = 1 Then";
if (_position==1) { 
RDebugUtils.currentLine=19726339;
 //BA.debugLineNum = 19726339;BA.debugLine="Dim strUserID As String = txtUserID.Text.Trim";
_struserid = mostCurrent._txtuserid.getText().trim();
RDebugUtils.currentLine=19726340;
 //BA.debugLineNum = 19726340;BA.debugLine="If strUserID = \"\" Then";
if ((_struserid).equals("")) { 
RDebugUtils.currentLine=19726341;
 //BA.debugLineNum = 19726341;BA.debugLine="Msgbox(\"Ingrese nombre de usuario\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese nombre de usuario"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19726342;
 //BA.debugLineNum = 19726342;BA.debugLine="Pager.GotoPage(0,True)";
mostCurrent._pager.GotoPage((int) (0),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=19726343;
 //BA.debugLineNum = 19726343;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19726345;
 //BA.debugLineNum = 19726345;BA.debugLine="Dim strPassword As String = txtPassword.Text.Tri";
_strpassword = mostCurrent._txtpassword.getText().trim();
RDebugUtils.currentLine=19726346;
 //BA.debugLineNum = 19726346;BA.debugLine="If strPassword = \"\" Then";
if ((_strpassword).equals("")) { 
RDebugUtils.currentLine=19726347;
 //BA.debugLineNum = 19726347;BA.debugLine="Msgbox(\"Ingrese contraseña\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese contraseña"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19726348;
 //BA.debugLineNum = 19726348;BA.debugLine="Pager.GotoPage(0,True)";
mostCurrent._pager.GotoPage((int) (0),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=19726349;
 //BA.debugLineNum = 19726349;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19726351;
 //BA.debugLineNum = 19726351;BA.debugLine="Dim strPassword2 As String = txtPassword2.Text.T";
_strpassword2 = mostCurrent._txtpassword2.getText().trim();
RDebugUtils.currentLine=19726352;
 //BA.debugLineNum = 19726352;BA.debugLine="If strPassword2 = \"\" Then";
if ((_strpassword2).equals("")) { 
RDebugUtils.currentLine=19726353;
 //BA.debugLineNum = 19726353;BA.debugLine="Msgbox(\"Confirme su contraseña\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Confirme su contraseña"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19726354;
 //BA.debugLineNum = 19726354;BA.debugLine="Pager.GotoPage(0,True)";
mostCurrent._pager.GotoPage((int) (0),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=19726355;
 //BA.debugLineNum = 19726355;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19726357;
 //BA.debugLineNum = 19726357;BA.debugLine="If strPassword <> strPassword2 Then";
if ((_strpassword).equals(_strpassword2) == false) { 
RDebugUtils.currentLine=19726358;
 //BA.debugLineNum = 19726358;BA.debugLine="Msgbox(\"Las contraseñas no coinciden\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Las contraseñas no coinciden"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19726359;
 //BA.debugLineNum = 19726359;BA.debugLine="Pager.GotoPage(0,True)";
mostCurrent._pager.GotoPage((int) (0),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=19726360;
 //BA.debugLineNum = 19726360;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19726363;
 //BA.debugLineNum = 19726363;BA.debugLine="Dim strEmail As String = txtEmail.Text.Trim";
_stremail = mostCurrent._txtemail.getText().trim();
RDebugUtils.currentLine=19726364;
 //BA.debugLineNum = 19726364;BA.debugLine="If strEmail = \"\" Then";
if ((_stremail).equals("")) { 
RDebugUtils.currentLine=19726365;
 //BA.debugLineNum = 19726365;BA.debugLine="Msgbox(\"Ingrese email\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese email"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19726366;
 //BA.debugLineNum = 19726366;BA.debugLine="Pager.GotoPage(0,True)";
mostCurrent._pager.GotoPage((int) (0),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=19726367;
 //BA.debugLineNum = 19726367;BA.debugLine="Return";
if (true) return "";
 };
RDebugUtils.currentLine=19726369;
 //BA.debugLineNum = 19726369;BA.debugLine="If Validate_Email(strEmail) = False Then";
if (_validate_email(_stremail)==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=19726370;
 //BA.debugLineNum = 19726370;BA.debugLine="Msgbox(\"Formato de email incorrecto\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Formato de email incorrecto"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
RDebugUtils.currentLine=19726371;
 //BA.debugLineNum = 19726371;BA.debugLine="Pager.GotoPage(0,True)";
mostCurrent._pager.GotoPage((int) (0),anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=19726372;
 //BA.debugLineNum = 19726372;BA.debugLine="Return";
if (true) return "";
 };
 };
RDebugUtils.currentLine=19726375;
 //BA.debugLineNum = 19726375;BA.debugLine="End Sub";
return "";
}
}