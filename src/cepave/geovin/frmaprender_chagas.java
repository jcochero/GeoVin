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

public class frmaprender_chagas extends Activity implements B4AActivity{
	public static frmaprender_chagas mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmaprender_chagas");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmaprender_chagas).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmaprender_chagas");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmaprender_chagas", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmaprender_chagas) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmaprender_chagas) Resume **");
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
		return frmaprender_chagas.class;
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
            BA.LogInfo("** Activity (frmaprender_chagas) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmaprender_chagas) Pause event (activity is not paused). **");
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
            frmaprender_chagas mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmaprender_chagas) Resume **");
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
public anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerraraprender = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripaprender = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmasinfo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloparte1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintextparte1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo1_desc = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo2_desc = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo3_desc = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo4_desc = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintextparte2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloparte2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloparte3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintextparte3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo1_3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo1_desc_3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgparte3_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo2_3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo2_desc_3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgparte3_2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo3_3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo3_desc_3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo4_desc_3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloparte4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintextparte4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo1_4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo1_desc_4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloparte5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintextparte5 = null;
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
 //BA.debugLineNum = 70;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 72;BA.debugLine="Activity.LoadLayout(\"layAprender_Chagas\")";
mostCurrent._activity.LoadLayout("layAprender_Chagas",mostCurrent.activityBA);
 //BA.debugLineNum = 76;BA.debugLine="tabStripAprender.LoadLayout(\"layEspecies_Especie\"";
mostCurrent._tabstripaprender.LoadLayout("layEspecies_Especie",BA.ObjectToCharSequence(" "));
 //BA.debugLineNum = 77;BA.debugLine="ScrollView1.panel.LoadLayout(\"layAprender_Chagas_";
mostCurrent._scrollview1.getPanel().LoadLayout("layAprender_Chagas_Parte1",mostCurrent.activityBA);
 //BA.debugLineNum = 78;BA.debugLine="ScrollView1.Panel.Height = 1200dip";
mostCurrent._scrollview1.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1200)));
 //BA.debugLineNum = 80;BA.debugLine="tabStripAprender.LoadLayout(\"layEspecies_Especie\"";
mostCurrent._tabstripaprender.LoadLayout("layEspecies_Especie",BA.ObjectToCharSequence(" "));
 //BA.debugLineNum = 81;BA.debugLine="ScrollView1.panel.LoadLayout(\"layAprender_Chagas_";
mostCurrent._scrollview1.getPanel().LoadLayout("layAprender_Chagas_Parte2",mostCurrent.activityBA);
 //BA.debugLineNum = 83;BA.debugLine="tabStripAprender.LoadLayout(\"layEspecies_Especie\"";
mostCurrent._tabstripaprender.LoadLayout("layEspecies_Especie",BA.ObjectToCharSequence(" "));
 //BA.debugLineNum = 84;BA.debugLine="ScrollView1.panel.LoadLayout(\"layAprender_Chagas_";
mostCurrent._scrollview1.getPanel().LoadLayout("layAprender_Chagas_Parte3",mostCurrent.activityBA);
 //BA.debugLineNum = 85;BA.debugLine="ScrollView1.Panel.Height = 1000dip";
mostCurrent._scrollview1.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1000)));
 //BA.debugLineNum = 87;BA.debugLine="tabStripAprender.LoadLayout(\"layEspecies_Especie\"";
mostCurrent._tabstripaprender.LoadLayout("layEspecies_Especie",BA.ObjectToCharSequence(" "));
 //BA.debugLineNum = 88;BA.debugLine="ScrollView1.panel.LoadLayout(\"layAprender_Chagas_";
mostCurrent._scrollview1.getPanel().LoadLayout("layAprender_Chagas_Parte4",mostCurrent.activityBA);
 //BA.debugLineNum = 90;BA.debugLine="tabStripAprender.LoadLayout(\"layEspecies_Especie\"";
mostCurrent._tabstripaprender.LoadLayout("layEspecies_Especie",BA.ObjectToCharSequence(" "));
 //BA.debugLineNum = 91;BA.debugLine="ScrollView1.panel.LoadLayout(\"layAprender_Chagas_";
mostCurrent._scrollview1.getPanel().LoadLayout("layAprender_Chagas_Parte5",mostCurrent.activityBA);
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 95;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}
public static String  _btncerraraprender_click() throws Exception{
 //BA.debugLineNum = 147;BA.debugLine="Sub btnCerrarAprender_Click";
 //BA.debugLineNum = 148;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 149;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 150;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim pi As PhoneIntents";
mostCurrent._pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 17;BA.debugLine="Private btnCerrarAprender As Button";
mostCurrent._btncerraraprender = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private tabStripAprender As TabStrip";
mostCurrent._tabstripaprender = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 20;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lblMasInfo As Label";
mostCurrent._lblmasinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lblTituloParte1 As Label";
mostCurrent._lbltituloparte1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lblMainTextParte1 As Label";
mostCurrent._lblmaintextparte1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lblSubtitulo1 As Label";
mostCurrent._lblsubtitulo1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private lblSubtitulo1_desc As Label";
mostCurrent._lblsubtitulo1_desc = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lblSubtitulo2 As Label";
mostCurrent._lblsubtitulo2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lblSubtitulo2_desc As Label";
mostCurrent._lblsubtitulo2_desc = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblSubtitulo3 As Label";
mostCurrent._lblsubtitulo3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblSubtitulo3_desc As Label";
mostCurrent._lblsubtitulo3_desc = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private ImageView3 As ImageView";
mostCurrent._imageview3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private lblSubtitulo4 As Label";
mostCurrent._lblsubtitulo4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private lblSubtitulo4_desc As Label";
mostCurrent._lblsubtitulo4_desc = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private ImageView4 As ImageView";
mostCurrent._imageview4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private lblMainTextParte2 As Label";
mostCurrent._lblmaintextparte2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private lblTituloParte2 As Label";
mostCurrent._lbltituloparte2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private lblTituloParte3 As Label";
mostCurrent._lbltituloparte3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lblMainTextParte3 As Label";
mostCurrent._lblmaintextparte3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lblSubtitulo1_3 As Label";
mostCurrent._lblsubtitulo1_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblSubtitulo1_desc_3 As Label";
mostCurrent._lblsubtitulo1_desc_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private imgParte3_1 As ImageView";
mostCurrent._imgparte3_1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private lblSubtitulo2_3 As Label";
mostCurrent._lblsubtitulo2_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private lblSubtitulo2_desc_3 As Label";
mostCurrent._lblsubtitulo2_desc_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private imgParte3_2 As ImageView";
mostCurrent._imgparte3_2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private lblSubtitulo3_3 As Label";
mostCurrent._lblsubtitulo3_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private lblSubtitulo3_desc_3 As Label";
mostCurrent._lblsubtitulo3_desc_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private lblSubtitulo4_desc_3 As Label";
mostCurrent._lblsubtitulo4_desc_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private lblTituloParte4 As Label";
mostCurrent._lbltituloparte4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private lblMainTextParte4 As Label";
mostCurrent._lblmaintextparte4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private lblSubtitulo1_4 As Label";
mostCurrent._lblsubtitulo1_4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private lblSubtitulo1_desc_4 As Label";
mostCurrent._lblsubtitulo1_desc_4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private lblTituloParte5 As Label";
mostCurrent._lbltituloparte5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private lblMainTextParte5 As Label";
mostCurrent._lblmaintextparte5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _lblmasinfo_click() throws Exception{
 //BA.debugLineNum = 152;BA.debugLine="Sub lblMasInfo_Click";
 //BA.debugLineNum = 153;BA.debugLine="StartActivity(pi.OpenBrowser(\"http://www.hablamos";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser("http://www.hablamosdechagas.com.ar/info-chagas/que-es-el-chagas/dimension-biomedica/")));
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 103;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 104;BA.debugLine="lblTitulo.Text = \"Chagas disease\"";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("Chagas disease"));
 //BA.debugLineNum = 105;BA.debugLine="lblMasInfo.Text = \"More info \"";
mostCurrent._lblmasinfo.setText(BA.ObjectToCharSequence("More info "));
 //BA.debugLineNum = 108;BA.debugLine="lblTituloParte1.Text = \"Who is responsible and w";
mostCurrent._lbltituloparte1.setText(BA.ObjectToCharSequence("Who is responsible and what are the routes of transmission in Chagas disease?"));
 //BA.debugLineNum = 109;BA.debugLine="lblMainTextParte1.Text = \"Chagas is produced by";
mostCurrent._lblmaintextparte1.setText(BA.ObjectToCharSequence("Chagas is produced by the parasite Trypanosoma cruzi. This parasite fulfills part of its life cycle in the digestive tract of kissing bugs and another part in the blood and / or tissues of mammals, including humans."));
 //BA.debugLineNum = 110;BA.debugLine="lblSubtitulo1.Text = \"Vectorial route:\"";
mostCurrent._lblsubtitulo1.setText(BA.ObjectToCharSequence("Vectorial route:"));
 //BA.debugLineNum = 111;BA.debugLine="lblSubtitulo1_desc.Text = \"For an infected “kiss";
mostCurrent._lblsubtitulo1_desc.setText(BA.ObjectToCharSequence("For an infected “kissing bug” or “bed bug”"));
 //BA.debugLineNum = 112;BA.debugLine="lblSubtitulo2.Text = \"Congenital or vertical rou";
mostCurrent._lblsubtitulo2.setText(BA.ObjectToCharSequence("Congenital or vertical route:"));
 //BA.debugLineNum = 113;BA.debugLine="lblSubtitulo2_desc.Text = \"From an infected moth";
mostCurrent._lblsubtitulo2_desc.setText(BA.ObjectToCharSequence("From an infected mother to her/s children during pregnancy or childbirth."));
 //BA.debugLineNum = 114;BA.debugLine="lblSubtitulo3.Text = \"Transfusional route:\"";
mostCurrent._lblsubtitulo3.setText(BA.ObjectToCharSequence("Transfusional route:"));
 //BA.debugLineNum = 115;BA.debugLine="lblSubtitulo3_desc.Text = \"Through the transfusi";
mostCurrent._lblsubtitulo3_desc.setText(BA.ObjectToCharSequence("Through the transfusion of blood or transplantation of an organ infected with the parasite."));
 //BA.debugLineNum = 116;BA.debugLine="lblSubtitulo4.Text = \"Oral route or by laborator";
mostCurrent._lblsubtitulo4.setText(BA.ObjectToCharSequence("Oral route or by laboratory accidents:"));
 //BA.debugLineNum = 117;BA.debugLine="lblSubtitulo4_desc.Text = \"Through the consumpti";
mostCurrent._lblsubtitulo4_desc.setText(BA.ObjectToCharSequence("Through the consumption of food or beverages contaminated with the parasite or by accidents during the manipulation of the parasite."));
 //BA.debugLineNum = 120;BA.debugLine="lblMainTextParte2.Text = \"How is it NOT transmit";
mostCurrent._lblmaintextparte2.setText(BA.ObjectToCharSequence("How is it NOT transmitted?"));
 //BA.debugLineNum = 121;BA.debugLine="lblTituloParte2.Text = \"By sharing 'mate' Or oth";
mostCurrent._lbltituloparte2.setText(BA.ObjectToCharSequence("By sharing 'mate' Or other drinks"+anywheresoftware.b4a.keywords.Common.CRLF+"Through breastmilk"+anywheresoftware.b4a.keywords.Common.CRLF+"Through sexual intercourse"+anywheresoftware.b4a.keywords.Common.CRLF+"Through saliva, kissing or hugs"));
 //BA.debugLineNum = 124;BA.debugLine="lblTituloParte3.Text = \"How does the disease evo";
mostCurrent._lbltituloparte3.setText(BA.ObjectToCharSequence("How does the disease evolve in a patient?"));
 //BA.debugLineNum = 125;BA.debugLine="lblMainTextParte3.Text = \"Once the parasite ente";
mostCurrent._lblmaintextparte3.setText(BA.ObjectToCharSequence("Once the parasite enters the body through the bloodstream, a process begins in which different phases or stages can be distinguished:"));
 //BA.debugLineNum = 126;BA.debugLine="lblSubtitulo1_3.Text = \"Acute phase\"";
mostCurrent._lblsubtitulo1_3.setText(BA.ObjectToCharSequence("Acute phase"));
 //BA.debugLineNum = 127;BA.debugLine="lblSubtitulo1_desc_3.Text = \"It lasts 15 to 60 d";
mostCurrent._lblsubtitulo1_desc_3.setText(BA.ObjectToCharSequence("It lasts 15 to 60 days after contracting the parasite, with no apparent or characteristic symptoms of the disease. Few people have prolonged fever, diarrhea, headache, tiredness, etc."));
 //BA.debugLineNum = 128;BA.debugLine="lblSubtitulo2_3.Text = \"Undetermined phase or ch";
mostCurrent._lblsubtitulo2_3.setText(BA.ObjectToCharSequence("Undetermined phase or chronically asymptomathic:"));
 //BA.debugLineNum = 129;BA.debugLine="lblSubtitulo2_desc_3.Text = \"It can last several";
mostCurrent._lblsubtitulo2_desc_3.setText(BA.ObjectToCharSequence("It can last several years or even a lifetime."));
 //BA.debugLineNum = 130;BA.debugLine="lblSubtitulo3_3.Text = \"Symptomatic chronic phas";
mostCurrent._lblsubtitulo3_3.setText(BA.ObjectToCharSequence("Symptomatic chronic phase"));
 //BA.debugLineNum = 131;BA.debugLine="lblSubtitulo3_desc_3.Text = \"It is important to";
mostCurrent._lblsubtitulo3_desc_3.setText(BA.ObjectToCharSequence("It is important to mention that 7 out of 10 people with Chagas can find themselves in this situation and will NOT develop the disease."));
 //BA.debugLineNum = 132;BA.debugLine="lblSubtitulo4_desc_3.Text = \"Approximately 3 out";
mostCurrent._lblsubtitulo4_desc_3.setText(BA.ObjectToCharSequence("Approximately 3 out of 10 people who have the parasite enter the chronic stage after 20 or 30 years of contracting it. In this phase, the most frequently affected organ is the heart and, to a lesser extent, damage to the digestive system and / or the nervous system can occur."));
 //BA.debugLineNum = 135;BA.debugLine="lblTituloParte4.Text = \"How can you tell for sur";
mostCurrent._lbltituloparte4.setText(BA.ObjectToCharSequence("How can you tell for sure if you have Chagas disease?"));
 //BA.debugLineNum = 136;BA.debugLine="lblMainTextParte4.Text = \"The way to detect if o";
mostCurrent._lblmaintextparte4.setText(BA.ObjectToCharSequence("The way to detect if one has the parasite is by a blood test."));
 //BA.debugLineNum = 137;BA.debugLine="lblSubtitulo1_4.Text = \"Are there treatments? Wh";
mostCurrent._lblsubtitulo1_4.setText(BA.ObjectToCharSequence("Are there treatments? Which are they?"));
 //BA.debugLineNum = 138;BA.debugLine="lblSubtitulo1_desc_4.Text = \"Currently the only";
mostCurrent._lblsubtitulo1_desc_4.setText(BA.ObjectToCharSequence("Currently the only authorized drugs are Benznidazole and Nifurtimox. They are presented in tablets with doses of two or three daily doses for 30 to 60 days. The treatment is more effective as soon as possible. In children and adolescents in the acute stage it has a healing success of 70 to 95%. However, it is essential to keep in mind that at any stage of the patient's disease or age, the treatment must be adequately supervised by a doctor."));
 //BA.debugLineNum = 140;BA.debugLine="lblTituloParte5.Text = \"What to do if you are pr";
mostCurrent._lbltituloparte5.setText(BA.ObjectToCharSequence("What to do if you are pregnant and you suspect you have Chagas disease?"));
 //BA.debugLineNum = 141;BA.debugLine="lblMainTextParte5.Text = \"Every pregnant woman s";
mostCurrent._lblmaintextparte5.setText(BA.ObjectToCharSequence("Every pregnant woman should be diagnosed for Chagas. In case of being positive, the woman should receive specific clinical controls during pregnancy and, after delivery, should be treated with the criteria of care for people in chronic phase. In Argentina, there are laws that protect both the mother and the child. Every baby of a mother who has the parasite must be included at birth in a specific monitoring protocol to determine if she has contracted the parasite through her mother."));
 };
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
}
