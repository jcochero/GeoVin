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

public class frmespecies extends Activity implements B4AActivity{
	public static frmespecies mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmespecies");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmespecies).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmespecies");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmespecies", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmespecies) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmespecies) Resume **");
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
		return frmespecies.class;
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
            BA.LogInfo("** Activity (frmespecies) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmespecies) Pause event (activity is not paused). **");
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
            frmespecies mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmespecies) Resume **");
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
public static String _formorigen = "";
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripespecies = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintextparte1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo1_desc = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo2_desc = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloparte1 = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollpanel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombre = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldescripciongeneral = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgvinchuca1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghabitat = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgdistribucion = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintextparte2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloparte2 = null;
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
public cepave.geovin.frmeditprofile _frmeditprofile = null;
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
public static void  _activity_create(boolean _firsttime) throws Exception{
ResumableSub_Activity_Create rsub = new ResumableSub_Activity_Create(null,_firsttime);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Create extends BA.ResumableSub {
public ResumableSub_Activity_Create(cepave.geovin.frmespecies parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
cepave.geovin.frmespecies parent;
boolean _firsttime;
anywheresoftware.b4a.objects.collections.List _mapaespecies = null;
int _i = 0;
String[] _currentsp = null;
int step42;
int limit42;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 45;BA.debugLine="p.SetScreenOrientation(1)";
parent.mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 51;BA.debugLine="Activity.LoadLayout(\"layEspecies_Main\")";
parent.mostCurrent._activity.LoadLayout("layEspecies_Main",mostCurrent.activityBA);
 //BA.debugLineNum = 54;BA.debugLine="tabStripEspecies.LoadLayout(\"layEspecies_General1";
parent.mostCurrent._tabstripespecies.LoadLayout("layEspecies_General1",BA.ObjectToCharSequence(" "));
 //BA.debugLineNum = 55;BA.debugLine="lblTituloParte1.RemoveView";
parent.mostCurrent._lbltituloparte1.RemoveView();
 //BA.debugLineNum = 56;BA.debugLine="lblMainTextParte1.RemoveView";
parent.mostCurrent._lblmaintextparte1.RemoveView();
 //BA.debugLineNum = 57;BA.debugLine="lblSubtitulo1_desc.RemoveView";
parent.mostCurrent._lblsubtitulo1_desc.RemoveView();
 //BA.debugLineNum = 58;BA.debugLine="lblSubtitulo2_desc.RemoveView";
parent.mostCurrent._lblsubtitulo2_desc.RemoveView();
 //BA.debugLineNum = 59;BA.debugLine="ImageView1.RemoveView";
parent.mostCurrent._imageview1.RemoveView();
 //BA.debugLineNum = 60;BA.debugLine="ImageView2.RemoveView";
parent.mostCurrent._imageview2.RemoveView();
 //BA.debugLineNum = 61;BA.debugLine="scrollPanel1.Left = 0";
parent.mostCurrent._scrollpanel1.setLeft((int) (0));
 //BA.debugLineNum = 62;BA.debugLine="scrollPanel1.Top = 0";
parent.mostCurrent._scrollpanel1.setTop((int) (0));
 //BA.debugLineNum = 63;BA.debugLine="scrollPanel1.Width = Activity.Width";
parent.mostCurrent._scrollpanel1.setWidth(parent.mostCurrent._activity.getWidth());
 //BA.debugLineNum = 64;BA.debugLine="scrollPanel1.Height = Activity.Height";
parent.mostCurrent._scrollpanel1.setHeight(parent.mostCurrent._activity.getHeight());
 //BA.debugLineNum = 65;BA.debugLine="scrollPanel1.Panel.AddView(lblTituloParte1,0dip,1";
parent.mostCurrent._scrollpanel1.getPanel().AddView((android.view.View)(parent.mostCurrent._lbltituloparte1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 66;BA.debugLine="scrollPanel1.Panel.AddView(lblMainTextParte1,5dip";
parent.mostCurrent._scrollpanel1.getPanel().AddView((android.view.View)(parent.mostCurrent._lblmaintextparte1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 67;BA.debugLine="scrollPanel1.Panel.AddView(lblSubtitulo1_desc,5di";
parent.mostCurrent._scrollpanel1.getPanel().AddView((android.view.View)(parent.mostCurrent._lblsubtitulo1_desc.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (180)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 68;BA.debugLine="scrollPanel1.Panel.AddView(lblSubtitulo2_desc,5di";
parent.mostCurrent._scrollpanel1.getPanel().AddView((android.view.View)(parent.mostCurrent._lblsubtitulo2_desc.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (460)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)));
 //BA.debugLineNum = 69;BA.debugLine="scrollPanel1.Panel.AddView(ImageView1,30dip,280di";
parent.mostCurrent._scrollpanel1.getPanel().AddView((android.view.View)(parent.mostCurrent._imageview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (260)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (180)));
 //BA.debugLineNum = 70;BA.debugLine="scrollPanel1.Panel.AddView(ImageView2,30dip,590di";
parent.mostCurrent._scrollpanel1.getPanel().AddView((android.view.View)(parent.mostCurrent._imageview2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (590)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (260)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (110)));
 //BA.debugLineNum = 71;BA.debugLine="scrollPanel1.Panel.Height = 850dip";
parent.mostCurrent._scrollpanel1.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (850)));
 //BA.debugLineNum = 74;BA.debugLine="tabStripEspecies.LoadLayout(\"layEspecies_General2";
parent.mostCurrent._tabstripespecies.LoadLayout("layEspecies_General2",BA.ObjectToCharSequence(" "));
 //BA.debugLineNum = 77;BA.debugLine="Dim mapaEspecies As List";
_mapaespecies = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 78;BA.debugLine="mapaEspecies.Initialize()";
_mapaespecies.Initialize();
 //BA.debugLineNum = 79;BA.debugLine="mapaEspecies = Main.speciesMap";
_mapaespecies = parent.mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 81;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 1:
//if
this.state = 6;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 3;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 82;BA.debugLine="ProgressDialogShow2(\"Cargando información...\", F";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Cargando información..."),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 84;BA.debugLine="ProgressDialogShow2(\"Loading information...\", Fa";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading information..."),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 86;BA.debugLine="Sleep(700)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (700));
this.state = 29;
return;
case 29:
//C
this.state = 7;
;
 //BA.debugLineNum = 87;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 7:
//if
this.state = 12;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 9;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 88;BA.debugLine="ProgressDialogShow2(\"Despertando a los insectos.";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Despertando a los insectos..."),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 90;BA.debugLine="ProgressDialogShow2(\"Loading information...\", Fa";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading information..."),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 12:
//C
this.state = 13;
;
 //BA.debugLineNum = 92;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 30;
return;
case 30:
//C
this.state = 13;
;
 //BA.debugLineNum = 93;BA.debugLine="Try";
if (true) break;

case 13:
//try
this.state = 28;
this.catchState = 27;
this.state = 15;
if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 27;
 //BA.debugLineNum = 94;BA.debugLine="If mapaEspecies = Null Or mapaEspecies.IsInitial";
if (true) break;

case 16:
//if
this.state = 25;
if (_mapaespecies== null || _mapaespecies.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 18;
}else {
this.state = 20;
}if (true) break;

case 18:
//C
this.state = 25;
 //BA.debugLineNum = 95;BA.debugLine="ToastMessageShow(\"Error showing species...\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error showing species..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 96;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 20:
//C
this.state = 21;
 //BA.debugLineNum = 98;BA.debugLine="For i = 1 To mapaEspecies.Size -1";
if (true) break;

case 21:
//for
this.state = 24;
step42 = 1;
limit42 = (int) (_mapaespecies.getSize()-1);
_i = (int) (1) ;
this.state = 31;
if (true) break;

case 31:
//C
this.state = 24;
if ((step42 > 0 && _i <= limit42) || (step42 < 0 && _i >= limit42)) this.state = 23;
if (true) break;

case 32:
//C
this.state = 31;
_i = ((int)(0 + _i + step42)) ;
if (true) break;

case 23:
//C
this.state = 32;
 //BA.debugLineNum = 99;BA.debugLine="tabStripEspecies.LoadLayout(\"layEspecies_Espec";
parent.mostCurrent._tabstripespecies.LoadLayout("layEspecies_Especie",BA.ObjectToCharSequence(_i));
 //BA.debugLineNum = 100;BA.debugLine="ScrollView1.Panel.LoadLayout(\"layEspecie_Espec";
parent.mostCurrent._scrollview1.getPanel().LoadLayout("layEspecie_Especie_Panel",mostCurrent.activityBA);
 //BA.debugLineNum = 101;BA.debugLine="Dim currentsp() As String";
_currentsp = new String[(int) (0)];
java.util.Arrays.fill(_currentsp,"");
 //BA.debugLineNum = 102;BA.debugLine="currentsp = Main.speciesMap.Get(i)";
_currentsp = (String[])(parent.mostCurrent._main._speciesmap /*anywheresoftware.b4a.objects.collections.List*/ .Get(_i));
 //BA.debugLineNum = 103;BA.debugLine="lblNombre.Text = \"   \" & currentsp(1)";
parent.mostCurrent._lblnombre.setText(BA.ObjectToCharSequence("   "+_currentsp[(int) (1)]));
 //BA.debugLineNum = 104;BA.debugLine="lblDescripcionGeneral.Text = currentsp(2)";
parent.mostCurrent._lbldescripciongeneral.setText(BA.ObjectToCharSequence(_currentsp[(int) (2)]));
 //BA.debugLineNum = 105;BA.debugLine="imgHabitat.Bitmap = LoadBitmapSample(File.DirA";
parent.mostCurrent._imghabitat.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_currentsp[(int) (3)],parent.mostCurrent._activity.getWidth(),parent.mostCurrent._activity.getHeight()).getObject()));
 //BA.debugLineNum = 106;BA.debugLine="imgVinchuca1.Bitmap = LoadBitmapSample(File.Di";
parent.mostCurrent._imgvinchuca1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_currentsp[(int) (5)],parent.mostCurrent._activity.getWidth(),parent.mostCurrent._activity.getHeight()).getObject()));
 //BA.debugLineNum = 107;BA.debugLine="imgDistribucion.Bitmap = LoadBitmapSample(File";
parent.mostCurrent._imgdistribucion.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_currentsp[(int) (4)],anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (240)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350))).getObject()));
 //BA.debugLineNum = 108;BA.debugLine="ScrollView1.Panel.Height = 1000dip";
parent.mostCurrent._scrollview1.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1000)));
 if (true) break;
if (true) break;

case 24:
//C
this.state = 25;
;
 if (true) break;

case 25:
//C
this.state = 28;
;
 if (true) break;

case 27:
//C
this.state = 28;
this.catchState = 0;
 //BA.debugLineNum = 113;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("222544453",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 28:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 115;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
processBA.setLastException(e0);}
            }
        }
    }
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 127;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 128;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 129;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 130;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 132;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 122;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 119;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarespecies_click() throws Exception{
 //BA.debugLineNum = 152;BA.debugLine="Sub btnCerrarEspecies_Click";
 //BA.debugLineNum = 153;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 154;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 24;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private tabStripEspecies As TabStrip";
mostCurrent._tabstripespecies = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 26;BA.debugLine="Private lblMainTextParte1 As Label";
mostCurrent._lblmaintextparte1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lblSubtitulo1_desc As Label";
mostCurrent._lblsubtitulo1_desc = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private lblSubtitulo2_desc As Label";
mostCurrent._lblsubtitulo2_desc = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lblTituloParte1 As Label";
mostCurrent._lbltituloparte1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private scrollPanel1 As ScrollView";
mostCurrent._scrollpanel1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblNombre As Label";
mostCurrent._lblnombre = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblDescripcionGeneral As Label";
mostCurrent._lbldescripciongeneral = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private imgVinchuca1 As ImageView";
mostCurrent._imgvinchuca1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private imgHabitat As ImageView";
mostCurrent._imghabitat = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private imgDistribucion As ImageView";
mostCurrent._imgdistribucion = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lblMainTextParte2 As Label";
mostCurrent._lblmaintextparte2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lblTituloParte2 As Label";
mostCurrent._lbltituloparte2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim formorigen As String";
_formorigen = "";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 138;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 139;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 140;BA.debugLine="lblTituloParte1.Text = \"What are 'kissing bugs'?";
mostCurrent._lbltituloparte1.setText(BA.ObjectToCharSequence("What are 'kissing bugs'?"));
 //BA.debugLineNum = 141;BA.debugLine="lblMainTextParte1.Text = \"The kissing bugs (or '";
mostCurrent._lblmaintextparte1.setText(BA.ObjectToCharSequence("The kissing bugs (or 'bedbugs') are insects that have three pairs of legs and their flattened body (similar to cockroaches) is divided into three regions: head, thorax and abdomen. This body shape allows them to take refuge in narrow places such as cracks in the wall, tree bark or under stones. In the margin of the abdomen they have spots that can vary their coloration according to the species."));
 //BA.debugLineNum = 142;BA.debugLine="lblSubtitulo1_desc.Text = \"The vinchucas can be";
mostCurrent._lblsubtitulo1_desc.setText(BA.ObjectToCharSequence("The vinchucas can be differentiated from other insects according to the type of face or beak depending on their eating habits; which is folded under the head and extends forward when feeding."));
 //BA.debugLineNum = 143;BA.debugLine="lblSubtitulo2_desc.Text = \"In the case of the mo";
mostCurrent._lblsubtitulo2_desc.setText(BA.ObjectToCharSequence("In the case of the most important kissing bug species in Argentina (Triatoma infestans) because it is the main vector of the Trypanosoma cruzi parasite; The adult has a total length of 21 to 29 mm, large and bulging eyes and his body is matt black or slightly shiny with the edge of his abdomen with characteristic black and yellow bands."));
 //BA.debugLineNum = 144;BA.debugLine="lblTituloParte2.Text = \"The life cycle of kissin";
mostCurrent._lbltituloparte2.setText(BA.ObjectToCharSequence("The life cycle of kissing bugs"));
 //BA.debugLineNum = 145;BA.debugLine="lblMainTextParte2.Text = \"The entire life cycle";
mostCurrent._lblmaintextparte2.setText(BA.ObjectToCharSequence("The entire life cycle ranges from the egg to the adult (only ones with wings) through five previous stages known as nymphs. The eggs are oval white, yellow or pink (the size of a grain of rice). All stages (the five nymphs + adults) feed on blood."));
 };
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
}
