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

public class frmaprender extends Activity implements B4AActivity{
	public static frmaprender mostCurrent;
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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmaprender");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmaprender).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmaprender");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmaprender", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmaprender) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmaprender) Resume **");
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
		return frmaprender.class;
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
        BA.LogInfo("** Activity (frmaprender) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            frmaprender mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmaprender) Resume **");
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
public static String _formorigen = "";
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombre = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldescripciongeneral = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvolver = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgvinchuca1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghabitat = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgdistribucion = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imggrande = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvolverframe = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblslide = null;
public de.amberhome.viewpager.AHPageContainer _container = null;
public de.amberhome.viewpager.AHViewPager _ahviewpager1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldistribucionarg = null;
public magnifizewrapper.magnifizeWrapper _magnifize1 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public cepave.geovin.main _main = null;
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
RDebugUtils.currentModule="frmaprender";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
RDebugUtils.currentLine=458752;
 //BA.debugLineNum = 458752;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=458754;
 //BA.debugLineNum = 458754;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="frmaprender";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	 {return ((Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode}));}
RDebugUtils.currentLine=655360;
 //BA.debugLineNum = 655360;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=655362;
 //BA.debugLineNum = 655362;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=655363;
 //BA.debugLineNum = 655363;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=655364;
 //BA.debugLineNum = 655364;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=655365;
 //BA.debugLineNum = 655365;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
RDebugUtils.currentLine=655367;
 //BA.debugLineNum = 655367;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
RDebugUtils.currentLine=655369;
 //BA.debugLineNum = 655369;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="frmaprender";
RDebugUtils.currentLine=589824;
 //BA.debugLineNum = 589824;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=589826;
 //BA.debugLineNum = 589826;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="frmaprender";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=524288;
 //BA.debugLineNum = 524288;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=524289;
 //BA.debugLineNum = 524289;BA.debugLine="CargarAprender";
_cargaraprender();
RDebugUtils.currentLine=524290;
 //BA.debugLineNum = 524290;BA.debugLine="End Sub";
return "";
}
public static String  _cargaraprender() throws Exception{
RDebugUtils.currentModule="frmaprender";
if (Debug.shouldDelegate(mostCurrent.activityBA, "cargaraprender"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "cargaraprender", null));}
String _html = "";
anywheresoftware.b4a.objects.ScrollViewWrapper _panelvert = null;
anywheresoftware.b4a.objects.PanelWrapper[] _pan = null;
anywheresoftware.b4a.objects.PanelWrapper _panelgeneral = null;
int _i = 0;
String[] _currentsp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bbmp = null;
RDebugUtils.currentLine=786432;
 //BA.debugLineNum = 786432;BA.debugLine="Sub CargarAprender";
RDebugUtils.currentLine=786436;
 //BA.debugLineNum = 786436;BA.debugLine="Dim HTML As String";
_html = "";
RDebugUtils.currentLine=786438;
 //BA.debugLineNum = 786438;BA.debugLine="If formorigen = \"Chagas\" Then";
if ((_formorigen).equals("Chagas")) { 
RDebugUtils.currentLine=786439;
 //BA.debugLineNum = 786439;BA.debugLine="Activity.LoadLayout(\"layAprenderMas\")";
mostCurrent._activity.LoadLayout("layAprenderMas",mostCurrent.activityBA);
RDebugUtils.currentLine=786440;
 //BA.debugLineNum = 786440;BA.debugLine="Dim HTML As String";
_html = "";
RDebugUtils.currentLine=786442;
 //BA.debugLineNum = 786442;BA.debugLine="HTML = File.GetText(File.DirAssets, \"chagas-text";
_html = anywheresoftware.b4a.keywords.Common.File.GetText(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"chagas-texto.html");
RDebugUtils.currentLine=786443;
 //BA.debugLineNum = 786443;BA.debugLine="WebView1.LoadHtml(HTML)";
mostCurrent._webview1.LoadHtml(_html);
RDebugUtils.currentLine=786444;
 //BA.debugLineNum = 786444;BA.debugLine="Activity.Title = \"La enfermedad del chagas\"";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence("La enfermedad del chagas"));
RDebugUtils.currentLine=786445;
 //BA.debugLineNum = 786445;BA.debugLine="WebView1.Width = Activity.Width";
mostCurrent._webview1.setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=786446;
 //BA.debugLineNum = 786446;BA.debugLine="WebView1.Height = Activity.Height - 35dip";
mostCurrent._webview1.setHeight((int) (mostCurrent._activity.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))));
 }else 
{RDebugUtils.currentLine=786447;
 //BA.debugLineNum = 786447;BA.debugLine="Else If formorigen = \"Especies\" Then";
if ((_formorigen).equals("Especies")) { 
RDebugUtils.currentLine=786450;
 //BA.debugLineNum = 786450;BA.debugLine="If Main.speciesMap = Null Or Main.speciesMap.IsI";
if (mostCurrent._main._speciesmap== null || mostCurrent._main._speciesmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=786451;
 //BA.debugLineNum = 786451;BA.debugLine="Return";
if (true) return "";
 }else {
RDebugUtils.currentLine=786455;
 //BA.debugLineNum = 786455;BA.debugLine="Container.Initialize";
mostCurrent._container.Initialize(mostCurrent.activityBA);
RDebugUtils.currentLine=786456;
 //BA.debugLineNum = 786456;BA.debugLine="Dim panelVert As ScrollView";
_panelvert = new anywheresoftware.b4a.objects.ScrollViewWrapper();
RDebugUtils.currentLine=786457;
 //BA.debugLineNum = 786457;BA.debugLine="panelVert.Initialize(1600)";
_panelvert.Initialize(mostCurrent.activityBA,(int) (1600));
RDebugUtils.currentLine=786458;
 //BA.debugLineNum = 786458;BA.debugLine="Dim Pan(Main.speciesMap.Size) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[mostCurrent._main._speciesmap.getSize()];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
RDebugUtils.currentLine=786461;
 //BA.debugLineNum = 786461;BA.debugLine="Dim panelGeneral As Panel";
_panelgeneral = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=786462;
 //BA.debugLineNum = 786462;BA.debugLine="panelGeneral.Initialize(\"\")";
_panelgeneral.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=786463;
 //BA.debugLineNum = 786463;BA.debugLine="panelGeneral.LoadLayout(\"layAprenderMas\")";
_panelgeneral.LoadLayout("layAprenderMas",mostCurrent.activityBA);
RDebugUtils.currentLine=786465;
 //BA.debugLineNum = 786465;BA.debugLine="Dim HTML As String";
_html = "";
RDebugUtils.currentLine=786467;
 //BA.debugLineNum = 786467;BA.debugLine="HTML = File.GetText(File.DirAssets, \"infogenera";
_html = anywheresoftware.b4a.keywords.Common.File.GetText(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"infogeneral-texto.html");
RDebugUtils.currentLine=786468;
 //BA.debugLineNum = 786468;BA.debugLine="WebView1.LoadHtml(HTML)";
mostCurrent._webview1.LoadHtml(_html);
RDebugUtils.currentLine=786469;
 //BA.debugLineNum = 786469;BA.debugLine="WebView1.Width = Activity.Width";
mostCurrent._webview1.setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=786470;
 //BA.debugLineNum = 786470;BA.debugLine="WebView1.Height = Activity.Height - 35dip";
mostCurrent._webview1.setHeight((int) (mostCurrent._activity.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))));
RDebugUtils.currentLine=786472;
 //BA.debugLineNum = 786472;BA.debugLine="Container.AddPageAt(panelGeneral, 0, 0)";
mostCurrent._container.AddPageAt((android.view.View)(_panelgeneral.getObject()),BA.NumberToString(0),(int) (0));
RDebugUtils.currentLine=786475;
 //BA.debugLineNum = 786475;BA.debugLine="For i = 1 To Pan.Length - 1";
{
final int step27 = 1;
final int limit27 = (int) (_pan.length-1);
_i = (int) (1) ;
for (;_i <= limit27 ;_i = _i + step27 ) {
RDebugUtils.currentLine=786476;
 //BA.debugLineNum = 786476;BA.debugLine="Dim panelVert As ScrollView";
_panelvert = new anywheresoftware.b4a.objects.ScrollViewWrapper();
RDebugUtils.currentLine=786477;
 //BA.debugLineNum = 786477;BA.debugLine="panelVert.Initialize(1500)";
_panelvert.Initialize(mostCurrent.activityBA,(int) (1500));
RDebugUtils.currentLine=786479;
 //BA.debugLineNum = 786479;BA.debugLine="Pan(i).Initialize(\"Pan\" & i)";
_pan[_i].Initialize(mostCurrent.activityBA,"Pan"+BA.NumberToString(_i));
RDebugUtils.currentLine=786480;
 //BA.debugLineNum = 786480;BA.debugLine="Pan(i).LoadLayout(\"layCaracteristicasSp\")";
_pan[_i].LoadLayout("layCaracteristicasSp",mostCurrent.activityBA);
RDebugUtils.currentLine=786482;
 //BA.debugLineNum = 786482;BA.debugLine="Dim currentsp() As String";
_currentsp = new String[(int) (0)];
java.util.Arrays.fill(_currentsp,"");
RDebugUtils.currentLine=786483;
 //BA.debugLineNum = 786483;BA.debugLine="currentsp = Main.speciesMap.Get(i)";
_currentsp = (String[])(mostCurrent._main._speciesmap.Get(_i));
RDebugUtils.currentLine=786484;
 //BA.debugLineNum = 786484;BA.debugLine="lblNombre.Text = currentsp(1)";
mostCurrent._lblnombre.setText(BA.ObjectToCharSequence(_currentsp[(int) (1)]));
RDebugUtils.currentLine=786485;
 //BA.debugLineNum = 786485;BA.debugLine="lblNombre.Width = Activity.Width";
mostCurrent._lblnombre.setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=786487;
 //BA.debugLineNum = 786487;BA.debugLine="lblDescripcionGeneral.Text = currentsp(2)";
mostCurrent._lbldescripciongeneral.setText(BA.ObjectToCharSequence(_currentsp[(int) (2)]));
RDebugUtils.currentLine=786490;
 //BA.debugLineNum = 786490;BA.debugLine="imgHabitat.Bitmap = LoadBitmapSample(File.DirA";
mostCurrent._imghabitat.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_currentsp[(int) (3)],mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=786491;
 //BA.debugLineNum = 786491;BA.debugLine="imgHabitat.Top = imgVinchuca1.Top + imgVinchuc";
mostCurrent._imghabitat.setTop((int) (mostCurrent._imgvinchuca1.getTop()+mostCurrent._imgvinchuca1.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
RDebugUtils.currentLine=786492;
 //BA.debugLineNum = 786492;BA.debugLine="imgHabitat.Width = Activity.Width - 20dip";
mostCurrent._imghabitat.setWidth((int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
RDebugUtils.currentLine=786495;
 //BA.debugLineNum = 786495;BA.debugLine="Magnifize1.RemoveView";
mostCurrent._magnifize1.RemoveView();
RDebugUtils.currentLine=786496;
 //BA.debugLineNum = 786496;BA.debugLine="imgDistribucion.RemoveView";
mostCurrent._imgdistribucion.RemoveView();
RDebugUtils.currentLine=786497;
 //BA.debugLineNum = 786497;BA.debugLine="Pan(i).AddView(imgDistribucion, 20dip, imgHabi";
_pan[_i].AddView((android.view.View)(mostCurrent._imgdistribucion.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),(int) (mostCurrent._imghabitat.getHeight()+mostCurrent._imghabitat.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (240)));
RDebugUtils.currentLine=786498;
 //BA.debugLineNum = 786498;BA.debugLine="Pan(i).AddView(Magnifize1, 20dip, imgHabitat.H";
_pan[_i].AddView((android.view.View)(mostCurrent._magnifize1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),(int) (mostCurrent._imghabitat.getHeight()+mostCurrent._imghabitat.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (240)));
RDebugUtils.currentLine=786501;
 //BA.debugLineNum = 786501;BA.debugLine="Dim bbmp As Bitmap";
_bbmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
RDebugUtils.currentLine=786502;
 //BA.debugLineNum = 786502;BA.debugLine="bbmp.Initialize(File.DirAssets, currentsp(4))";
_bbmp.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_currentsp[(int) (4)]);
RDebugUtils.currentLine=786505;
 //BA.debugLineNum = 786505;BA.debugLine="Magnifize1.MagnifizeBitmap = bbmp";
mostCurrent._magnifize1.setMagnifizeBitmap((android.graphics.Bitmap)(_bbmp.getObject()));
RDebugUtils.currentLine=786507;
 //BA.debugLineNum = 786507;BA.debugLine="imgVinchuca1.Bitmap = LoadBitmapSample(File.Di";
mostCurrent._imgvinchuca1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_currentsp[(int) (5)],mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight()).getObject()));
RDebugUtils.currentLine=786508;
 //BA.debugLineNum = 786508;BA.debugLine="imgVinchuca1.Width = Activity.Width - 10dip";
mostCurrent._imgvinchuca1.setWidth((int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
RDebugUtils.currentLine=786514;
 //BA.debugLineNum = 786514;BA.debugLine="panelVert.Panel.AddView(Pan(i),0,0,100%x, Magn";
_panelvert.getPanel().AddView((android.view.View)(_pan[_i].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (mostCurrent._magnifize1.getHeight()+mostCurrent._magnifize1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
RDebugUtils.currentLine=786515;
 //BA.debugLineNum = 786515;BA.debugLine="panelVert.Panel.Height = Magnifize1.Height + M";
_panelvert.getPanel().setHeight((int) (mostCurrent._magnifize1.getHeight()+mostCurrent._magnifize1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
RDebugUtils.currentLine=786517;
 //BA.debugLineNum = 786517;BA.debugLine="Container.AddPageAt(panelVert, i, i)";
mostCurrent._container.AddPageAt((android.view.View)(_panelvert.getObject()),BA.NumberToString(_i),_i);
 }
};
RDebugUtils.currentLine=786521;
 //BA.debugLineNum = 786521;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=786522;
 //BA.debugLineNum = 786522;BA.debugLine="Activity.LoadLayout(\"layCaracteristicasFrame_V\"";
mostCurrent._activity.LoadLayout("layCaracteristicasFrame_V",mostCurrent.activityBA);
RDebugUtils.currentLine=786523;
 //BA.debugLineNum = 786523;BA.debugLine="AHViewPager1.RemoveView";
mostCurrent._ahviewpager1.RemoveView();
RDebugUtils.currentLine=786524;
 //BA.debugLineNum = 786524;BA.debugLine="AHViewPager1.Initialize2(Container, \"AHViewPage";
mostCurrent._ahviewpager1.Initialize2(mostCurrent.activityBA,mostCurrent._container,"AHViewPager1");
RDebugUtils.currentLine=786526;
 //BA.debugLineNum = 786526;BA.debugLine="lblSlide.Visible = True";
mostCurrent._lblslide.setVisible(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=786527;
 //BA.debugLineNum = 786527;BA.debugLine="lblSlide.Width = Activity.Width";
mostCurrent._lblslide.setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=786529;
 //BA.debugLineNum = 786529;BA.debugLine="Activity.AddView(AHViewPager1, 0, 0, 100%x, 85%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._ahviewpager1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (85),mostCurrent.activityBA));
 };
 }else 
{RDebugUtils.currentLine=786532;
 //BA.debugLineNum = 786532;BA.debugLine="Else If formorigen = \"Recomendaciones\" Then";
if ((_formorigen).equals("Recomendaciones")) { 
RDebugUtils.currentLine=786533;
 //BA.debugLineNum = 786533;BA.debugLine="Activity.LoadLayout(\"layAprenderMas\")";
mostCurrent._activity.LoadLayout("layAprenderMas",mostCurrent.activityBA);
RDebugUtils.currentLine=786534;
 //BA.debugLineNum = 786534;BA.debugLine="Dim HTML As String";
_html = "";
RDebugUtils.currentLine=786536;
 //BA.debugLineNum = 786536;BA.debugLine="HTML = File.GetText(File.DirAssets, \"recomendaci";
_html = anywheresoftware.b4a.keywords.Common.File.GetText(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"recomendaciones.html");
RDebugUtils.currentLine=786537;
 //BA.debugLineNum = 786537;BA.debugLine="WebView1.LoadHtml(HTML)";
mostCurrent._webview1.LoadHtml(_html);
RDebugUtils.currentLine=786538;
 //BA.debugLineNum = 786538;BA.debugLine="Activity.Title = \"Recomendaciones y Contactos\"";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence("Recomendaciones y Contactos"));
RDebugUtils.currentLine=786539;
 //BA.debugLineNum = 786539;BA.debugLine="WebView1.Width = Activity.Width";
mostCurrent._webview1.setWidth(mostCurrent._activity.getWidth());
RDebugUtils.currentLine=786540;
 //BA.debugLineNum = 786540;BA.debugLine="WebView1.Height = Activity.Height - 35dip";
mostCurrent._webview1.setHeight((int) (mostCurrent._activity.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))));
 }}}
;
RDebugUtils.currentLine=786543;
 //BA.debugLineNum = 786543;BA.debugLine="End Sub";
return "";
}
public static String  _ahviewpager1_pagechanged(int _position) throws Exception{
RDebugUtils.currentModule="frmaprender";
if (Debug.shouldDelegate(mostCurrent.activityBA, "ahviewpager1_pagechanged"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "ahviewpager1_pagechanged", new Object[] {_position}));}
RDebugUtils.currentLine=851968;
 //BA.debugLineNum = 851968;BA.debugLine="Sub AHViewPager1_PageChanged (Position As Int)";
RDebugUtils.currentLine=851969;
 //BA.debugLineNum = 851969;BA.debugLine="If Position = 0 Or Position = 1 Then";
if (_position==0 || _position==1) { 
RDebugUtils.currentLine=851970;
 //BA.debugLineNum = 851970;BA.debugLine="lblSlide.Text = \"       Desliza para ver las esp";
mostCurrent._lblslide.setText(BA.ObjectToCharSequence("       Desliza para ver las especies      "));
 }else {
RDebugUtils.currentLine=851972;
 //BA.debugLineNum = 851972;BA.debugLine="lblSlide.Text = \"       Desliza para ver las es";
mostCurrent._lblslide.setText(BA.ObjectToCharSequence("       Desliza para ver las especies      "));
 };
RDebugUtils.currentLine=851974;
 //BA.debugLineNum = 851974;BA.debugLine="End Sub";
return "";
}
public static String  _btncerraraprender_click() throws Exception{
RDebugUtils.currentModule="frmaprender";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncerraraprender_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncerraraprender_click", null));}
RDebugUtils.currentLine=720896;
 //BA.debugLineNum = 720896;BA.debugLine="Sub btnCerrarAprender_Click";
RDebugUtils.currentLine=720897;
 //BA.debugLineNum = 720897;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=720898;
 //BA.debugLineNum = 720898;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=720899;
 //BA.debugLineNum = 720899;BA.debugLine="End Sub";
return "";
}
public static String  _btncerraraprenderlay_click() throws Exception{
RDebugUtils.currentModule="frmaprender";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btncerraraprenderlay_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btncerraraprenderlay_click", null));}
RDebugUtils.currentLine=917504;
 //BA.debugLineNum = 917504;BA.debugLine="Sub btnCerrarAprenderLay_Click";
RDebugUtils.currentLine=917505;
 //BA.debugLineNum = 917505;BA.debugLine="If formorigen = \"Chagas\" Or formorigen = \"Recomen";
if ((_formorigen).equals("Chagas") || (_formorigen).equals("Recomendaciones") || (_formorigen).equals("Especies")) { 
RDebugUtils.currentLine=917506;
 //BA.debugLineNum = 917506;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=917507;
 //BA.debugLineNum = 917507;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
RDebugUtils.currentLine=917509;
 //BA.debugLineNum = 917509;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=917510;
 //BA.debugLineNum = 917510;BA.debugLine="CargarAprender";
_cargaraprender();
 };
RDebugUtils.currentLine=917513;
 //BA.debugLineNum = 917513;BA.debugLine="End Sub";
return "";
}
public static String  _btnvolverframe_click() throws Exception{
RDebugUtils.currentModule="frmaprender";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnvolverframe_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnvolverframe_click", null));}
RDebugUtils.currentLine=983040;
 //BA.debugLineNum = 983040;BA.debugLine="Sub btnVolverFrame_Click";
RDebugUtils.currentLine=983041;
 //BA.debugLineNum = 983041;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=983042;
 //BA.debugLineNum = 983042;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=983043;
 //BA.debugLineNum = 983043;BA.debugLine="End Sub";
return "";
}
}