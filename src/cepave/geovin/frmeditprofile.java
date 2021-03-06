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

public class frmeditprofile extends Activity implements B4AActivity{
	public static frmeditprofile mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmeditprofile");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmeditprofile).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmeditprofile");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmeditprofile", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmeditprofile) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmeditprofile) Resume **");
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
		return frmeditprofile.class;
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
            BA.LogInfo("** Activity (frmeditprofile) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmeditprofile) Pause event (activity is not paused). **");
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
            frmeditprofile mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmeditprofile) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfullname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlocation = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunmaskpass = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnperfil = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnorganizaciones = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblorg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblagregarorg = null;
public static String _nombreorg = "";
public static String _idorg = "";
public static String _localidadorg = "";
public static String _provinciaorg = "";
public static String _tipoorg = "";
public static String _adminorg = "";
public static String _idorgelegida = "";
public anywheresoftware.b4a.objects.collections.Map _listaorgs = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfullname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllocation = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblperfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcambiarpass = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncancelaredit = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneditprofile = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
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
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 47;BA.debugLine="Activity.LoadLayout(\"layEditProfile\")";
mostCurrent._activity.LoadLayout("layEditProfile",mostCurrent.activityBA);
 //BA.debugLineNum = 48;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 49;BA.debugLine="spnPerfil.Add(\"Individual\")";
mostCurrent._spnperfil.Add("Individual");
 //BA.debugLineNum = 50;BA.debugLine="spnPerfil.Add(\"Grupo\")";
mostCurrent._spnperfil.Add("Grupo");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 52;BA.debugLine="spnPerfil.Add(\"Single user\")";
mostCurrent._spnperfil.Add("Single user");
 //BA.debugLineNum = 53;BA.debugLine="spnPerfil.Add(\"Group of users\")";
mostCurrent._spnperfil.Add("Group of users");
 };
 //BA.debugLineNum = 57;BA.debugLine="txtFullName.Text = Main.strUserFullName";
mostCurrent._txtfullname.setText(BA.ObjectToCharSequence(mostCurrent._main._struserfullname /*String*/ ));
 //BA.debugLineNum = 58;BA.debugLine="lblTitle.Text = Main.username";
mostCurrent._lbltitle.setText(BA.ObjectToCharSequence(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 59;BA.debugLine="txtLocation.Text = Main.strUserLocation";
mostCurrent._txtlocation.setText(BA.ObjectToCharSequence(mostCurrent._main._struserlocation /*String*/ ));
 //BA.debugLineNum = 60;BA.debugLine="If Main.strUserGroup = Null Or Main.strUserGroup";
if (mostCurrent._main._strusergroup /*String*/ == null || (mostCurrent._main._strusergroup /*String*/ ).equals("null") || (mostCurrent._main._strusergroup /*String*/ ).equals("")) { 
 //BA.debugLineNum = 61;BA.debugLine="spnPerfil.SelectedIndex = 0";
mostCurrent._spnperfil.setSelectedIndex((int) (0));
 //BA.debugLineNum = 62;BA.debugLine="lblOrg.Visible = False";
mostCurrent._lblorg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 63;BA.debugLine="spnOrganizaciones.Visible = False";
mostCurrent._spnorganizaciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 64;BA.debugLine="lblAgregarOrg.Visible = False";
mostCurrent._lblagregarorg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 66;BA.debugLine="spnPerfil.SelectedIndex = 1";
mostCurrent._spnperfil.setSelectedIndex((int) (1));
 //BA.debugLineNum = 67;BA.debugLine="spnOrganizaciones.Add(Main.strUserGroup)";
mostCurrent._spnorganizaciones.Add(mostCurrent._main._strusergroup /*String*/ );
 //BA.debugLineNum = 68;BA.debugLine="spnOrganizaciones.Enabled = False";
mostCurrent._spnorganizaciones.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 69;BA.debugLine="spnOrganizaciones.Visible = True";
mostCurrent._spnorganizaciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 77;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 78;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 79;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 83;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 84;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _btncancelaredit_click() throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Sub btnCancelarEdit_Click";
 //BA.debugLineNum = 122;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 123;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _btneditprofile_click() throws Exception{
String _strpassword = "";
String _strpassword2 = "";
 //BA.debugLineNum = 144;BA.debugLine="Sub btnEditProfile_Click";
 //BA.debugLineNum = 147;BA.debugLine="If txtPassword.Text <> \"\" Then";
if ((mostCurrent._txtpassword.getText()).equals("") == false) { 
 //BA.debugLineNum = 149;BA.debugLine="Dim strPassword As String = txtPassword.Text.Tri";
_strpassword = mostCurrent._txtpassword.getText().trim();
 //BA.debugLineNum = 150;BA.debugLine="Dim strPassword2 As String = txtPassword2.Text.T";
_strpassword2 = mostCurrent._txtpassword2.getText().trim();
 //BA.debugLineNum = 151;BA.debugLine="If strPassword2 = \"\" Then";
if ((_strpassword2).equals("")) { 
 //BA.debugLineNum = 152;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 153;BA.debugLine="Msgbox(\"Confirme su nueva contraseña\", \"Error\"";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Confirme su nueva contraseña"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 155;BA.debugLine="Msgbox(\"Confirm your new password\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Confirm your new password"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 157;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 159;BA.debugLine="If strPassword <> strPassword2 Then";
if ((_strpassword).equals(_strpassword2) == false) { 
 //BA.debugLineNum = 160;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 161;BA.debugLine="Msgbox(\"Las contraseñas no coinciden\", \"Error\"";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Las contraseñas no coinciden"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 163;BA.debugLine="Msgbox(\"Passwords don't match\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Passwords don't match"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 165;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 168;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 169;BA.debugLine="ProgressDialogShow(\"Guardando cambios...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Guardando cambios..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 171;BA.debugLine="ProgressDialogShow(\"Saving changes...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Saving changes..."));
 };
 //BA.debugLineNum = 174;BA.debugLine="EditUser";
_edituser();
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static String  _cargargrupos() throws Exception{
cepave.geovin.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 292;BA.debugLine="Sub CargarGrupos";
 //BA.debugLineNum = 293;BA.debugLine="listaOrgs.Initialize";
mostCurrent._listaorgs.Initialize();
 //BA.debugLineNum = 294;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 295;BA.debugLine="dd.url = Main.serverPath & \"/connect2/retrieve_or";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect2/retrieve_orgs.php";
 //BA.debugLineNum = 296;BA.debugLine="dd.EventName = \"RetrieveOrgs\"";
_dd.EventName /*String*/  = "RetrieveOrgs";
 //BA.debugLineNum = 297;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmeditprofile.getObject();
 //BA.debugLineNum = 298;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 300;BA.debugLine="End Sub";
return "";
}
public static String  _edituser() throws Exception{
cepave.geovin.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 176;BA.debugLine="Sub EditUser";
 //BA.debugLineNum = 180;BA.debugLine="If spnPerfil.SelectedIndex = 0 Then";
if (mostCurrent._spnperfil.getSelectedIndex()==0) { 
 //BA.debugLineNum = 182;BA.debugLine="Main.strUserGroup = \"\"";
mostCurrent._main._strusergroup /*String*/  = "";
 }else {
 //BA.debugLineNum = 184;BA.debugLine="Main.strUserGroup = spnOrganizaciones.SelectedIt";
mostCurrent._main._strusergroup /*String*/  = mostCurrent._spnorganizaciones.getSelectedItem();
 };
 //BA.debugLineNum = 187;BA.debugLine="Dim dd As DownloadData";
_dd = new cepave.geovin.downloadservice._downloaddata();
 //BA.debugLineNum = 188;BA.debugLine="If txtPassword.Text <> \"\" Then";
if ((mostCurrent._txtpassword.getText()).equals("") == false) { 
 //BA.debugLineNum = 190;BA.debugLine="dd.url = Main.serverPath & \"/connect2/editUser.ph";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect2/editUser.php?Action=EditPass&"+"UserID="+mostCurrent._main._username /*String*/ +"&"+"Password="+mostCurrent._txtpassword.getText()+"&"+"FullName="+mostCurrent._txtfullname.getText()+"&"+"Location="+mostCurrent._txtlocation.getText()+"&"+"usergroup="+mostCurrent._main._strusergroup /*String*/ +"&"+"usertipo="+mostCurrent._spnperfil.getSelectedItem()+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ ;
 }else {
 //BA.debugLineNum = 200;BA.debugLine="dd.url = Main.serverPath & \"/connect2/editUser.p";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect2/editUser.php?Action=EditNoPass&"+"UserID="+mostCurrent._main._username /*String*/ +"&"+"FullName="+mostCurrent._txtfullname.getText()+"&"+"Location="+mostCurrent._txtlocation.getText()+"&"+"usergroup="+mostCurrent._main._strusergroup /*String*/ +"&"+"usertipo="+mostCurrent._spnperfil.getSelectedItem()+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ ;
 };
 //BA.debugLineNum = 209;BA.debugLine="dd.EventName = \"EditUser\"";
_dd.EventName /*String*/  = "EditUser";
 //BA.debugLineNum = 210;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmeditprofile.getObject();
 //BA.debugLineNum = 211;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return "";
}
public static String  _edituser_complete(cepave.geovin.httpjob _job) throws Exception{
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 214;BA.debugLine="Sub EditUser_Complete(Job As HttpJob)";
 //BA.debugLineNum = 215;BA.debugLine="Log(\"EditUser user: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("221954561","EditUser user: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 216;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 218;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
 //BA.debugLineNum = 219;BA.debugLine="res = Job.GetString";
_res = _job._getstring /*String*/ ();
 //BA.debugLineNum = 220;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 221;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 222;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 223;BA.debugLine="If action = \"EditOK\" Then";
if ((_action).equals("EditOK")) { 
 //BA.debugLineNum = 225;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 226;BA.debugLine="ToastMessageShow(\"Perfil editado\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Perfil editado"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 228;BA.debugLine="ToastMessageShow(\"Profile edited\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Profile edited"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 231;BA.debugLine="Main.strUserFullName = txtFullName.Text";
mostCurrent._main._struserfullname /*String*/  = mostCurrent._txtfullname.getText();
 //BA.debugLineNum = 232;BA.debugLine="Main.strUserLocation = txtLocation.Text";
mostCurrent._main._struserlocation /*String*/  = mostCurrent._txtlocation.getText();
 //BA.debugLineNum = 233;BA.debugLine="Main.strUserTipoUsuario = spnPerfil.SelectedIte";
mostCurrent._main._strusertipousuario /*String*/  = mostCurrent._spnperfil.getSelectedItem();
 //BA.debugLineNum = 236;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 237;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 238;BA.debugLine="Map1.Put(\"username\", Main.username)";
_map1.Put((Object)("username"),(Object)(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 239;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastuser",(Object)("si"),_map1);
 //BA.debugLineNum = 240;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userLocation",(Object)(mostCurrent._main._struserlocation /*String*/ ),_map1);
 //BA.debugLineNum = 241;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userFullName",(Object)(mostCurrent._main._struserfullname /*String*/ ),_map1);
 //BA.debugLineNum = 242;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userOrg",(Object)(mostCurrent._main._struserorg /*String*/ ),_map1);
 //BA.debugLineNum = 243;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userGroup",(Object)(mostCurrent._main._strusergroup /*String*/ ),_map1);
 //BA.debugLineNum = 244;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","UserTipoUsuario",(Object)(mostCurrent._main._strusertipousuario /*String*/ ),_map1);
 //BA.debugLineNum = 250;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 251;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 253;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 254;BA.debugLine="Msgbox(\"El servidor no devolvió los valores es";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("El servidor no devolvió los valores esperados."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 256;BA.debugLine="Msgbox(\"Server error.\" & Job.ErrorMessage, \"Er";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Server error."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 };
 };
 };
 //BA.debugLineNum = 260;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 261;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 262;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private txtPassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private txtPassword2 As EditText";
mostCurrent._txtpassword2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private txtFullName As EditText";
mostCurrent._txtfullname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private txtLocation As EditText";
mostCurrent._txtlocation = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private lblUnmaskPass As Label";
mostCurrent._lblunmaskpass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private lblTitle As Label";
mostCurrent._lbltitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private spnPerfil As Spinner";
mostCurrent._spnperfil = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private spnOrganizaciones As Spinner";
mostCurrent._spnorganizaciones = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lblOrg As Label";
mostCurrent._lblorg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lblAgregarOrg As Label";
mostCurrent._lblagregarorg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim nombreOrg As String";
mostCurrent._nombreorg = "";
 //BA.debugLineNum = 27;BA.debugLine="Dim idOrg As String";
mostCurrent._idorg = "";
 //BA.debugLineNum = 28;BA.debugLine="Dim localidadOrg As String";
mostCurrent._localidadorg = "";
 //BA.debugLineNum = 29;BA.debugLine="Dim provinciaOrg As String";
mostCurrent._provinciaorg = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim tipoOrg As String";
mostCurrent._tipoorg = "";
 //BA.debugLineNum = 31;BA.debugLine="Dim adminOrg As String";
mostCurrent._adminorg = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim idOrgElegida As String";
mostCurrent._idorgelegida = "";
 //BA.debugLineNum = 33;BA.debugLine="Dim listaOrgs As Map";
mostCurrent._listaorgs = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 38;BA.debugLine="Private lblFullName As Label";
mostCurrent._lblfullname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lblLocation As Label";
mostCurrent._lbllocation = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lblPerfil As Label";
mostCurrent._lblperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private lblCambiarPass As Label";
mostCurrent._lblcambiarpass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btnCancelarEdit As Button";
mostCurrent._btncancelaredit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private btnEditProfile As Button";
mostCurrent._btneditprofile = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _lblagregarorg_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 348;BA.debugLine="Sub lblAgregarOrg_Click";
 //BA.debugLineNum = 349;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 351;BA.debugLine="If Msgbox2(\"Las organizaciones (grupos, escuelas";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Las organizaciones (grupos, escuelas, empresas, ONGs) utilizan GeoVin en grupo. Para registrar una nueva, debes contactarte con el equipo GeoVin mediante el formulario en la web"),BA.ObjectToCharSequence("Grupos"),"Ir al formulario","","Volver",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 352;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 353;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.geovin.";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser("http://www.geovin.com.ar/")));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 357;BA.debugLine="If Msgbox2(\"To use GeoVin as a group of people (";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("To use GeoVin as a group of people (schools, organizations, NGOs) you have to contact GeoVin through the web form"),BA.ObjectToCharSequence("Groups"),"Go to the form","","Volver",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 358;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 359;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.geovin.";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser("http://www.geovin.com.ar/")));
 };
 };
 //BA.debugLineNum = 365;BA.debugLine="End Sub";
return "";
}
public static String  _lblleerlegales_click() throws Exception{
 //BA.debugLineNum = 376;BA.debugLine="Sub lblLeerLegales_Click";
 //BA.debugLineNum = 378;BA.debugLine="End Sub";
return "";
}
public static String  _lblunmaskpass_click() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub lblUnmaskPass_Click";
 //BA.debugLineNum = 131;BA.debugLine="If lblUnmaskPass.Text = \"\" Then";
if ((mostCurrent._lblunmaskpass.getText()).equals("")) { 
 //BA.debugLineNum = 132;BA.debugLine="txtPassword.PasswordMode = True";
mostCurrent._txtpassword.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 133;BA.debugLine="txtPassword2.PasswordMode = True";
mostCurrent._txtpassword2.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 134;BA.debugLine="lblUnmaskPass.Text = \"\"";
mostCurrent._lblunmaskpass.setText(BA.ObjectToCharSequence(""));
 }else if((mostCurrent._lblunmaskpass.getText()).equals("")) { 
 //BA.debugLineNum = 136;BA.debugLine="txtPassword.PasswordMode = False";
mostCurrent._txtpassword.setPasswordMode(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 137;BA.debugLine="txtPassword2.PasswordMode = False";
mostCurrent._txtpassword2.setPasswordMode(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 138;BA.debugLine="lblUnmaskPass.Text = \"\"";
mostCurrent._lblunmaskpass.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _retrieveorgs_complete(cepave.geovin.httpjob _job) throws Exception{
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
 //BA.debugLineNum = 302;BA.debugLine="Sub RetrieveOrgs_Complete(Job As HttpJob)";
 //BA.debugLineNum = 303;BA.debugLine="Log(\"Cargagrupos: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("222151169","Cargagrupos: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 304;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 305;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
 //BA.debugLineNum = 306;BA.debugLine="res = Job.GetString";
_res = _job._getstring /*String*/ ();
 //BA.debugLineNum = 307;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 308;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 309;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 310;BA.debugLine="If action = \"OrgsOK\" Then";
if ((_action).equals("OrgsOK")) { 
 //BA.debugLineNum = 311;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 312;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 314;BA.debugLine="For i = 0 To numresults - 1";
{
final int step11 = 1;
final int limit11 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit11 ;_i = _i + step11 ) {
 //BA.debugLineNum = 315;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 316;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 317;BA.debugLine="idOrg = newpunto.Get(\"id\")";
mostCurrent._idorg = BA.ObjectToString(_newpunto.Get((Object)("id")));
 //BA.debugLineNum = 318;BA.debugLine="nombreOrg = newpunto.Get(\"nombre\")";
mostCurrent._nombreorg = BA.ObjectToString(_newpunto.Get((Object)("nombre")));
 //BA.debugLineNum = 319;BA.debugLine="localidadOrg = newpunto.Get(\"localidad\")";
mostCurrent._localidadorg = BA.ObjectToString(_newpunto.Get((Object)("localidad")));
 //BA.debugLineNum = 320;BA.debugLine="provinciaOrg = newpunto.Get(\"provincia\")";
mostCurrent._provinciaorg = BA.ObjectToString(_newpunto.Get((Object)("provincia")));
 //BA.debugLineNum = 321;BA.debugLine="tipoOrg = newpunto.Get(\"tipo\")";
mostCurrent._tipoorg = BA.ObjectToString(_newpunto.Get((Object)("tipo")));
 //BA.debugLineNum = 322;BA.debugLine="adminOrg = newpunto.Get(\"admin\")";
mostCurrent._adminorg = BA.ObjectToString(_newpunto.Get((Object)("admin")));
 //BA.debugLineNum = 323;BA.debugLine="spnOrganizaciones.Add(nombreOrg)";
mostCurrent._spnorganizaciones.Add(mostCurrent._nombreorg);
 //BA.debugLineNum = 324;BA.debugLine="listaOrgs.Put(nombreOrg, idOrg)";
mostCurrent._listaorgs.Put((Object)(mostCurrent._nombreorg),(Object)(mostCurrent._idorg));
 //BA.debugLineNum = 325;BA.debugLine="spnOrganizaciones.Enabled = True";
mostCurrent._spnorganizaciones.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }
};
 //BA.debugLineNum = 327;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else {
 //BA.debugLineNum = 330;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 331;BA.debugLine="Msgbox(\"El servidor no devolvió los valores es";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("El servidor no devolvió los valores esperados."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Registro"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 333;BA.debugLine="Msgbox(\"Server error.\" & Job.ErrorMessage, \"Er";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Server error."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 };
 };
 };
 //BA.debugLineNum = 340;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 341;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 342;BA.debugLine="End Sub";
return "";
}
public static String  _spnorganizaciones_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 344;BA.debugLine="Sub spnOrganizaciones_ItemClick (Position As Int,";
 //BA.debugLineNum = 345;BA.debugLine="idOrgElegida = listaOrgs.Get(Value)";
mostCurrent._idorgelegida = BA.ObjectToString(mostCurrent._listaorgs.Get(_value));
 //BA.debugLineNum = 346;BA.debugLine="End Sub";
return "";
}
public static String  _spnperfil_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 273;BA.debugLine="Sub spnPerfil_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 274;BA.debugLine="If spnPerfil.SelectedItem = \"Grupo\" Or spnPerfil.";
if ((mostCurrent._spnperfil.getSelectedItem()).equals("Grupo") || (mostCurrent._spnperfil.getSelectedItem()).equals("Group")) { 
 //BA.debugLineNum = 275;BA.debugLine="lblOrg.Visible = True";
mostCurrent._lblorg.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 276;BA.debugLine="lblAgregarOrg.Visible = True";
mostCurrent._lblagregarorg.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 277;BA.debugLine="spnOrganizaciones.Visible = True";
mostCurrent._spnorganizaciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 278;BA.debugLine="spnOrganizaciones.Clear";
mostCurrent._spnorganizaciones.Clear();
 //BA.debugLineNum = 279;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 280;BA.debugLine="ProgressDialogShow(\"Buscando grupos...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando grupos..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 282;BA.debugLine="ProgressDialogShow(\"Retrieving groups...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Retrieving groups..."));
 };
 //BA.debugLineNum = 284;BA.debugLine="CargarGrupos";
_cargargrupos();
 //BA.debugLineNum = 285;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 287;BA.debugLine="lblOrg.Visible = False";
mostCurrent._lblorg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 288;BA.debugLine="lblAgregarOrg.Visible = False";
mostCurrent._lblagregarorg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 289;BA.debugLine="spnOrganizaciones.Visible = False";
mostCurrent._spnorganizaciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 97;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 98;BA.debugLine="lblFullName.Text = \"Nombre y Apellido\"";
mostCurrent._lblfullname.setText(BA.ObjectToCharSequence("Nombre y Apellido"));
 //BA.debugLineNum = 99;BA.debugLine="lblLocation.Text = \"Localidad\"";
mostCurrent._lbllocation.setText(BA.ObjectToCharSequence("Localidad"));
 //BA.debugLineNum = 100;BA.debugLine="lblPerfil.Text = \"Perfil\"";
mostCurrent._lblperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 101;BA.debugLine="lblCambiarPass.Text = \"Cambiar contraseña\"";
mostCurrent._lblcambiarpass.setText(BA.ObjectToCharSequence("Cambiar contraseña"));
 //BA.debugLineNum = 102;BA.debugLine="btnCancelarEdit.Text= \"Cancelar\"";
mostCurrent._btncancelaredit.setText(BA.ObjectToCharSequence("Cancelar"));
 //BA.debugLineNum = 103;BA.debugLine="btnEditProfile.Text= \" Aceptar\"";
mostCurrent._btneditprofile.setText(BA.ObjectToCharSequence(" Aceptar"));
 //BA.debugLineNum = 104;BA.debugLine="lblOrg.Text = \" Vincular a grupo\"";
mostCurrent._lblorg.setText(BA.ObjectToCharSequence(" Vincular a grupo"));
 //BA.debugLineNum = 105;BA.debugLine="txtPassword.Hint =\"Cambiar contraseña\"";
mostCurrent._txtpassword.setHint("Cambiar contraseña");
 //BA.debugLineNum = 106;BA.debugLine="txtPassword2.Hint =\"Repetir contraseña\"";
mostCurrent._txtpassword2.setHint("Repetir contraseña");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 109;BA.debugLine="lblFullName.Text = \"Name and Surname\"";
mostCurrent._lblfullname.setText(BA.ObjectToCharSequence("Name and Surname"));
 //BA.debugLineNum = 110;BA.debugLine="lblLocation.Text = \"Locality\"";
mostCurrent._lbllocation.setText(BA.ObjectToCharSequence("Locality"));
 //BA.debugLineNum = 111;BA.debugLine="lblPerfil.Text = \"Profile\"";
mostCurrent._lblperfil.setText(BA.ObjectToCharSequence("Profile"));
 //BA.debugLineNum = 112;BA.debugLine="lblCambiarPass.Text = \"Change password\"";
mostCurrent._lblcambiarpass.setText(BA.ObjectToCharSequence("Change password"));
 //BA.debugLineNum = 113;BA.debugLine="btnCancelarEdit.Text= \"Cancel\"";
mostCurrent._btncancelaredit.setText(BA.ObjectToCharSequence("Cancel"));
 //BA.debugLineNum = 114;BA.debugLine="btnEditProfile.Text= \" Accept\"";
mostCurrent._btneditprofile.setText(BA.ObjectToCharSequence(" Accept"));
 //BA.debugLineNum = 115;BA.debugLine="lblOrg.Text = \" Link to a group\"";
mostCurrent._lblorg.setText(BA.ObjectToCharSequence(" Link to a group"));
 //BA.debugLineNum = 116;BA.debugLine="txtPassword.Hint =\"Change password\"";
mostCurrent._txtpassword.setHint("Change password");
 //BA.debugLineNum = 117;BA.debugLine="txtPassword2.Hint =\"Repeat password\"";
mostCurrent._txtpassword2.setHint("Repeat password");
 };
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
}
