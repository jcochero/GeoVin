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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmabout");
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



public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblversion = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbloms = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblwebsite = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldesarrollo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintext = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _fcbicon = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgcc = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllicencia = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcolaboran = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogo5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfinancian = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcereve = null;
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
public cepave.geovin.register _register = null;
public cepave.geovin.frmreportevinchuca _frmreportevinchuca = null;
public cepave.geovin.envioarchivos2 _envioarchivos2 = null;
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="frmabout";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=18808832;
 //BA.debugLineNum = 18808832;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=18808834;
 //BA.debugLineNum = 18808834;BA.debugLine="Activity.LoadLayout(\"frmAbout\")";
mostCurrent._activity.LoadLayout("frmAbout",mostCurrent.activityBA);
RDebugUtils.currentLine=18808835;
 //BA.debugLineNum = 18808835;BA.debugLine="lblOMS.RemoveView";
mostCurrent._lbloms.RemoveView();
RDebugUtils.currentLine=18808836;
 //BA.debugLineNum = 18808836;BA.debugLine="lblWebSite.RemoveView";
mostCurrent._lblwebsite.RemoveView();
RDebugUtils.currentLine=18808837;
 //BA.debugLineNum = 18808837;BA.debugLine="lblDesarrollo.RemoveView";
mostCurrent._lbldesarrollo.RemoveView();
RDebugUtils.currentLine=18808838;
 //BA.debugLineNum = 18808838;BA.debugLine="lblMainText.RemoveView";
mostCurrent._lblmaintext.RemoveView();
RDebugUtils.currentLine=18808839;
 //BA.debugLineNum = 18808839;BA.debugLine="fcbIcon.RemoveView";
mostCurrent._fcbicon.RemoveView();
RDebugUtils.currentLine=18808840;
 //BA.debugLineNum = 18808840;BA.debugLine="imgCC.RemoveView";
mostCurrent._imgcc.RemoveView();
RDebugUtils.currentLine=18808841;
 //BA.debugLineNum = 18808841;BA.debugLine="lblLicencia.RemoveView";
mostCurrent._lbllicencia.RemoveView();
RDebugUtils.currentLine=18808842;
 //BA.debugLineNum = 18808842;BA.debugLine="imgLogo3.RemoveView";
mostCurrent._imglogo3.RemoveView();
RDebugUtils.currentLine=18808843;
 //BA.debugLineNum = 18808843;BA.debugLine="imgLogo1.RemoveView";
mostCurrent._imglogo1.RemoveView();
RDebugUtils.currentLine=18808844;
 //BA.debugLineNum = 18808844;BA.debugLine="imgLogo4.RemoveView";
mostCurrent._imglogo4.RemoveView();
RDebugUtils.currentLine=18808845;
 //BA.debugLineNum = 18808845;BA.debugLine="imgLogo2.RemoveView";
mostCurrent._imglogo2.RemoveView();
RDebugUtils.currentLine=18808846;
 //BA.debugLineNum = 18808846;BA.debugLine="lblColaboran.RemoveView";
mostCurrent._lblcolaboran.RemoveView();
RDebugUtils.currentLine=18808847;
 //BA.debugLineNum = 18808847;BA.debugLine="imgLogo5.RemoveView";
mostCurrent._imglogo5.RemoveView();
RDebugUtils.currentLine=18808848;
 //BA.debugLineNum = 18808848;BA.debugLine="imgLogo6.RemoveView";
mostCurrent._imglogo6.RemoveView();
RDebugUtils.currentLine=18808849;
 //BA.debugLineNum = 18808849;BA.debugLine="lblFinancian.RemoveView";
mostCurrent._lblfinancian.RemoveView();
RDebugUtils.currentLine=18808850;
 //BA.debugLineNum = 18808850;BA.debugLine="lblCeReVe.RemoveView";
mostCurrent._lblcereve.RemoveView();
RDebugUtils.currentLine=18808851;
 //BA.debugLineNum = 18808851;BA.debugLine="lblVersion.Text = \"b: \" & Application.VersionName";
mostCurrent._lblversion.setText(BA.ObjectToCharSequence("b: "+anywheresoftware.b4a.keywords.Common.Application.getVersionName()));
RDebugUtils.currentLine=18808852;
 //BA.debugLineNum = 18808852;BA.debugLine="ScrollView1.Panel.AddView(lblMainText, 5dip, 5dip";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._lblmaintext.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
RDebugUtils.currentLine=18808853;
 //BA.debugLineNum = 18808853;BA.debugLine="ScrollView1.Panel.AddView(lblColaboran, 0, lblMai";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._lblcolaboran.getObject()),(int) (0),(int) (mostCurrent._lblmaintext.getHeight()+mostCurrent._lblmaintext.getTop()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=18808854;
 //BA.debugLineNum = 18808854;BA.debugLine="ScrollView1.Panel.AddView(imgLogo1, Activity.Widt";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._imglogo1.getObject()),(int) (mostCurrent._activity.getWidth()/(double)2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160))),(int) (mostCurrent._lblcolaboran.getHeight()+mostCurrent._lblcolaboran.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
RDebugUtils.currentLine=18808855;
 //BA.debugLineNum = 18808855;BA.debugLine="ScrollView1.Panel.AddView(imgLogo2, imgLogo1.Left";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._imglogo2.getObject()),(int) (mostCurrent._imglogo1.getLeft()+mostCurrent._imglogo1.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (mostCurrent._lblcolaboran.getHeight()+mostCurrent._lblcolaboran.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=18808856;
 //BA.debugLineNum = 18808856;BA.debugLine="ScrollView1.Panel.AddView(imgLogo3, imgLogo2.Left";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._imglogo3.getObject()),(int) (mostCurrent._imglogo2.getLeft()+mostCurrent._imglogo2.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (mostCurrent._lblcolaboran.getHeight()+mostCurrent._lblcolaboran.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=18808857;
 //BA.debugLineNum = 18808857;BA.debugLine="ScrollView1.Panel.AddView(lblCeReVe, 0, imgLogo1.";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._lblcereve.getObject()),(int) (0),(int) (mostCurrent._imglogo1.getHeight()+mostCurrent._imglogo1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=18808859;
 //BA.debugLineNum = 18808859;BA.debugLine="ScrollView1.Panel.AddView(lblFinancian, 0, lblCeR";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._lblfinancian.getObject()),(int) (0),(int) (mostCurrent._lblcereve.getHeight()+mostCurrent._lblcereve.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=18808860;
 //BA.debugLineNum = 18808860;BA.debugLine="ScrollView1.Panel.AddView(imgLogo4, 30dip, lblFin";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._imglogo4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),(int) (mostCurrent._lblfinancian.getHeight()+mostCurrent._lblfinancian.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=18808861;
 //BA.debugLineNum = 18808861;BA.debugLine="ScrollView1.Panel.AddView(imgLogo5, imgLogo4.Left";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._imglogo5.getObject()),(int) (mostCurrent._imglogo4.getLeft()+mostCurrent._imglogo4.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (mostCurrent._lblfinancian.getHeight()+mostCurrent._lblfinancian.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=18808862;
 //BA.debugLineNum = 18808862;BA.debugLine="ScrollView1.Panel.AddView(imgLogo6, imgLogo5.Left";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._imglogo6.getObject()),(int) (mostCurrent._imglogo5.getLeft()+mostCurrent._imglogo5.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (mostCurrent._lblfinancian.getHeight()+mostCurrent._lblfinancian.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=18808863;
 //BA.debugLineNum = 18808863;BA.debugLine="ScrollView1.Panel.AddView(lblOMS, 0, imgLogo4.Hei";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._lbloms.getObject()),(int) (0),(int) (mostCurrent._imglogo4.getHeight()+mostCurrent._imglogo4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
RDebugUtils.currentLine=18808864;
 //BA.debugLineNum = 18808864;BA.debugLine="ScrollView1.Panel.AddView(lblWebSite, 0, lblOMS.H";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._lblwebsite.getObject()),(int) (0),(int) (mostCurrent._lbloms.getHeight()+mostCurrent._lbloms.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
RDebugUtils.currentLine=18808865;
 //BA.debugLineNum = 18808865;BA.debugLine="ScrollView1.Panel.AddView(fcbIcon, Activity.Width";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._fcbicon.getObject()),(int) (mostCurrent._activity.getWidth()/(double)2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),(int) (mostCurrent._lblwebsite.getHeight()+mostCurrent._lblwebsite.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
RDebugUtils.currentLine=18808866;
 //BA.debugLineNum = 18808866;BA.debugLine="ScrollView1.Panel.AddView(lblLicencia, 5dip, fcbI";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._lbllicencia.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (mostCurrent._fcbicon.getHeight()+mostCurrent._fcbicon.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=18808867;
 //BA.debugLineNum = 18808867;BA.debugLine="ScrollView1.Panel.AddView(imgCC, Activity.Width /";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._imgcc.getObject()),(int) (mostCurrent._activity.getWidth()/(double)2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (27))),(int) (mostCurrent._lbllicencia.getHeight()+mostCurrent._lbllicencia.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
RDebugUtils.currentLine=18808868;
 //BA.debugLineNum = 18808868;BA.debugLine="ScrollView1.Panel.AddView(lblDesarrollo, 5dip, im";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(mostCurrent._lbldesarrollo.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (mostCurrent._imgcc.getHeight()+mostCurrent._imgcc.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=18808869;
 //BA.debugLineNum = 18808869;BA.debugLine="ScrollView1.Panel.Height = lblDesarrollo.Top + lb";
mostCurrent._scrollview1.getPanel().setHeight((int) (mostCurrent._lbldesarrollo.getTop()+mostCurrent._lbldesarrollo.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
RDebugUtils.currentLine=18808870;
 //BA.debugLineNum = 18808870;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="frmabout";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode}));}
RDebugUtils.currentLine=19005440;
 //BA.debugLineNum = 19005440;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=19005441;
 //BA.debugLineNum = 19005441;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=19005442;
 //BA.debugLineNum = 19005442;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=19005443;
 //BA.debugLineNum = 19005443;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
RDebugUtils.currentLine=19005445;
 //BA.debugLineNum = 19005445;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="frmabout";
RDebugUtils.currentLine=18939904;
 //BA.debugLineNum = 18939904;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=18939906;
 //BA.debugLineNum = 18939906;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="frmabout";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=18874368;
 //BA.debugLineNum = 18874368;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=18874370;
 //BA.debugLineNum = 18874370;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
RDebugUtils.currentModule="frmabout";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncerrar_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncerrar_click", null));}
RDebugUtils.currentLine=19070976;
 //BA.debugLineNum = 19070976;BA.debugLine="Sub btnCerrar_Click";
RDebugUtils.currentLine=19070977;
 //BA.debugLineNum = 19070977;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=19070978;
 //BA.debugLineNum = 19070978;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=19070979;
 //BA.debugLineNum = 19070979;BA.debugLine="End Sub";
return "";
}
public static String  _fcbicon_click() throws Exception{
RDebugUtils.currentModule="frmabout";
if (Debug.shouldDelegate(mostCurrent.activityBA, "fcbicon_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "fcbicon_click", null));}
RDebugUtils.currentLine=19136512;
 //BA.debugLineNum = 19136512;BA.debugLine="Sub fcbIcon_Click";
RDebugUtils.currentLine=19136513;
 //BA.debugLineNum = 19136513;BA.debugLine="StartActivity(p.OpenBrowser(\"https://www.facebook";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("https://www.facebook.com/geovin/")));
RDebugUtils.currentLine=19136514;
 //BA.debugLineNum = 19136514;BA.debugLine="End Sub";
return "";
}
public static String  _imgcc_click() throws Exception{
RDebugUtils.currentModule="frmabout";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgcc_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "imgcc_click", null));}
RDebugUtils.currentLine=19267584;
 //BA.debugLineNum = 19267584;BA.debugLine="Sub imgCC_Click";
RDebugUtils.currentLine=19267585;
 //BA.debugLineNum = 19267585;BA.debugLine="StartActivity(p.OpenBrowser(\"https://creativecomm";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("https://creativecommons.org/licenses/by-nc/2.5/ar/")));
RDebugUtils.currentLine=19267586;
 //BA.debugLineNum = 19267586;BA.debugLine="End Sub";
return "";
}
public static String  _lblwebsite_click() throws Exception{
RDebugUtils.currentModule="frmabout";
if (Debug.shouldDelegate(mostCurrent.activityBA, "lblwebsite_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "lblwebsite_click", null));}
RDebugUtils.currentLine=19202048;
 //BA.debugLineNum = 19202048;BA.debugLine="Sub lblWebSite_Click";
RDebugUtils.currentLine=19202049;
 //BA.debugLineNum = 19202049;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.geovin.co";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("http://www.geovin.com.ar")));
RDebugUtils.currentLine=19202050;
 //BA.debugLineNum = 19202050;BA.debugLine="End Sub";
return "";
}
}