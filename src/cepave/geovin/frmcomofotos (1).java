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

public class frmcomofotos extends Activity implements B4AActivity{
	public static frmcomofotos mostCurrent;
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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "cepave.geovin", "cepave.geovin.frmcomofotos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmcomofotos).");
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
		activityBA = new BA(this, layout, processBA, "cepave.geovin", "cepave.geovin.frmcomofotos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cepave.geovin.frmcomofotos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmcomofotos) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmcomofotos) Resume **");
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
		return frmcomofotos.class;
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
        BA.LogInfo("** Activity (frmcomofotos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            frmcomofotos mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmcomofotos) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltexto1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgtexto1 = null;
public de.amberhome.viewpager.AHPageContainer _container = null;
public de.amberhome.viewpager.AHViewPager _ahviewpager1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblslide = null;
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
public cepave.geovin.envioarchivos2 _envioarchivos2 = null;
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="frmcomofotos";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime}));}
anywheresoftware.b4a.objects.PanelWrapper[] _pan = null;
RDebugUtils.currentLine=22282240;
 //BA.debugLineNum = 22282240;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=22282244;
 //BA.debugLineNum = 22282244;BA.debugLine="Dim Pan(4) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[(int) (4)];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
RDebugUtils.currentLine=22282246;
 //BA.debugLineNum = 22282246;BA.debugLine="Pan(0).Initialize(\"\")";
_pan[(int) (0)].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=22282247;
 //BA.debugLineNum = 22282247;BA.debugLine="Pan(1).Initialize(\"\")";
_pan[(int) (1)].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=22282248;
 //BA.debugLineNum = 22282248;BA.debugLine="Pan(2).Initialize(\"\")";
_pan[(int) (2)].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=22282249;
 //BA.debugLineNum = 22282249;BA.debugLine="Pan(3).Initialize(\"\")";
_pan[(int) (3)].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=22282252;
 //BA.debugLineNum = 22282252;BA.debugLine="Container.Initialize";
mostCurrent._container.Initialize(mostCurrent.activityBA);
RDebugUtils.currentLine=22282255;
 //BA.debugLineNum = 22282255;BA.debugLine="Pan(0).LoadLayout(\"layComoFotos_Panels\")";
_pan[(int) (0)].LoadLayout("layComoFotos_Panels",mostCurrent.activityBA);
RDebugUtils.currentLine=22282256;
 //BA.debugLineNum = 22282256;BA.debugLine="lblTitulo.Text  = \"Cómo capturar insectos\"";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("Cómo capturar insectos"));
RDebugUtils.currentLine=22282258;
 //BA.debugLineNum = 22282258;BA.debugLine="lblTexto1.Text  = \"Siempre que captures un insect";
mostCurrent._lbltexto1.setText(BA.ObjectToCharSequence("Siempre que captures un insecto lo debes hacer con un guante de latex o con una pinza, sin tener contacto directo con el mismo."));
RDebugUtils.currentLine=22282259;
 //BA.debugLineNum = 22282259;BA.debugLine="lblTitulo.Width = 100%x";
mostCurrent._lbltitulo.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=22282260;
 //BA.debugLineNum = 22282260;BA.debugLine="lblTexto1.Width = 90%x";
mostCurrent._lbltexto1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA));
RDebugUtils.currentLine=22282261;
 //BA.debugLineNum = 22282261;BA.debugLine="imgTexto1.RemoveView";
mostCurrent._imgtexto1.RemoveView();
RDebugUtils.currentLine=22282262;
 //BA.debugLineNum = 22282262;BA.debugLine="imgTexto1.Initialize(\"\")";
mostCurrent._imgtexto1.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=22282263;
 //BA.debugLineNum = 22282263;BA.debugLine="imgTexto1.Bitmap = LoadBitmapSample(File.DirAsset";
mostCurrent._imgtexto1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto captura1.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=22282264;
 //BA.debugLineNum = 22282264;BA.debugLine="imgTexto1.Gravity = Gravity.FILL";
mostCurrent._imgtexto1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=22282265;
 //BA.debugLineNum = 22282265;BA.debugLine="Pan(0).AddView(imgTexto1,0,220dip,100%x,170dip)";
_pan[(int) (0)].AddView((android.view.View)(mostCurrent._imgtexto1.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)));
RDebugUtils.currentLine=22282266;
 //BA.debugLineNum = 22282266;BA.debugLine="Container.AddPageAt(Pan(0), \"panel0\", 0)";
mostCurrent._container.AddPageAt((android.view.View)(_pan[(int) (0)].getObject()),"panel0",(int) (0));
RDebugUtils.currentLine=22282268;
 //BA.debugLineNum = 22282268;BA.debugLine="Pan(1).LoadLayout(\"layComoFotos_Panels\")";
_pan[(int) (1)].LoadLayout("layComoFotos_Panels",mostCurrent.activityBA);
RDebugUtils.currentLine=22282269;
 //BA.debugLineNum = 22282269;BA.debugLine="lblTitulo.Text  = \"Cómo capturar insectos\"";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("Cómo capturar insectos"));
RDebugUtils.currentLine=22282270;
 //BA.debugLineNum = 22282270;BA.debugLine="lblTexto1.Text  = \"Luego lo debes colocar en un r";
mostCurrent._lbltexto1.setText(BA.ObjectToCharSequence("Luego lo debes colocar en un recipiente de plástico o vidrio."));
RDebugUtils.currentLine=22282271;
 //BA.debugLineNum = 22282271;BA.debugLine="lblTitulo.Width = 100%x";
mostCurrent._lbltitulo.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=22282272;
 //BA.debugLineNum = 22282272;BA.debugLine="lblTexto1.Width = 90%x";
mostCurrent._lbltexto1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA));
RDebugUtils.currentLine=22282273;
 //BA.debugLineNum = 22282273;BA.debugLine="imgTexto1.RemoveView";
mostCurrent._imgtexto1.RemoveView();
RDebugUtils.currentLine=22282274;
 //BA.debugLineNum = 22282274;BA.debugLine="imgTexto1.Initialize(\"\")";
mostCurrent._imgtexto1.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=22282275;
 //BA.debugLineNum = 22282275;BA.debugLine="imgTexto1.Bitmap = LoadBitmapSample(File.DirAsset";
mostCurrent._imgtexto1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto captura5.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=22282276;
 //BA.debugLineNum = 22282276;BA.debugLine="imgTexto1.Gravity = Gravity.FILL";
mostCurrent._imgtexto1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=22282277;
 //BA.debugLineNum = 22282277;BA.debugLine="Pan(1).AddView(imgTexto1,0,220dip,100%x,230dip)";
_pan[(int) (1)].AddView((android.view.View)(mostCurrent._imgtexto1.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (230)));
RDebugUtils.currentLine=22282278;
 //BA.debugLineNum = 22282278;BA.debugLine="Container.AddPageAt(Pan(1), \"panel1\", 1)";
mostCurrent._container.AddPageAt((android.view.View)(_pan[(int) (1)].getObject()),"panel1",(int) (1));
RDebugUtils.currentLine=22282280;
 //BA.debugLineNum = 22282280;BA.debugLine="Pan(2).LoadLayout(\"layComoFotos_Panels\")";
_pan[(int) (2)].LoadLayout("layComoFotos_Panels",mostCurrent.activityBA);
RDebugUtils.currentLine=22282281;
 //BA.debugLineNum = 22282281;BA.debugLine="lblTitulo.Text  = \"Cómo preparar el insecto\"";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("Cómo preparar el insecto"));
RDebugUtils.currentLine=22282282;
 //BA.debugLineNum = 22282282;BA.debugLine="lblTexto1.Text  = \"Colocar el recipiente en el fr";
mostCurrent._lbltexto1.setText(BA.ObjectToCharSequence("Colocar el recipiente en el freezer durante un par de horas o (si no se cuenta con un freezer) colocar el recipiente al sol hasta comprobar que el insecto haya muerto. Una vez que el insecto este muerto, colocarlo sobre una hoja blanca o un fondo blanco para sacarle las fotos corespondientes."));
RDebugUtils.currentLine=22282283;
 //BA.debugLineNum = 22282283;BA.debugLine="lblTitulo.Width = 100%x";
mostCurrent._lbltitulo.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=22282284;
 //BA.debugLineNum = 22282284;BA.debugLine="lblTexto1.Width = 90%x";
mostCurrent._lbltexto1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA));
RDebugUtils.currentLine=22282285;
 //BA.debugLineNum = 22282285;BA.debugLine="imgTexto1.RemoveView";
mostCurrent._imgtexto1.RemoveView();
RDebugUtils.currentLine=22282286;
 //BA.debugLineNum = 22282286;BA.debugLine="imgTexto1.Initialize(\"\")";
mostCurrent._imgtexto1.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=22282287;
 //BA.debugLineNum = 22282287;BA.debugLine="imgTexto1.Bitmap = LoadBitmapSample(File.DirAsset";
mostCurrent._imgtexto1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"foto captura4.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
RDebugUtils.currentLine=22282288;
 //BA.debugLineNum = 22282288;BA.debugLine="imgTexto1.Gravity = Gravity.FILL";
mostCurrent._imgtexto1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=22282289;
 //BA.debugLineNum = 22282289;BA.debugLine="Pan(2).AddView(imgTexto1,0,260dip,100%x,100dip)";
_pan[(int) (2)].AddView((android.view.View)(mostCurrent._imgtexto1.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (260)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
RDebugUtils.currentLine=22282290;
 //BA.debugLineNum = 22282290;BA.debugLine="Container.AddPageAt(Pan(2), \"panel2\", 2)";
mostCurrent._container.AddPageAt((android.view.View)(_pan[(int) (2)].getObject()),"panel2",(int) (2));
RDebugUtils.currentLine=22282292;
 //BA.debugLineNum = 22282292;BA.debugLine="Pan(3).LoadLayout(\"layComoFotos_Panels\")";
_pan[(int) (3)].LoadLayout("layComoFotos_Panels",mostCurrent.activityBA);
RDebugUtils.currentLine=22282293;
 //BA.debugLineNum = 22282293;BA.debugLine="lblTitulo.Text  = \"¡IMPORTANTE!\"";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("¡IMPORTANTE!"));
RDebugUtils.currentLine=22282294;
 //BA.debugLineNum = 22282294;BA.debugLine="lblTexto1.Text  = \"Las fotos y la carga del dato";
mostCurrent._lbltexto1.setText(BA.ObjectToCharSequence("Las fotos y la carga del dato tiene que ser en las proximidades de donde se encontró el insecto  para obtener información exacta del lugar."));
RDebugUtils.currentLine=22282295;
 //BA.debugLineNum = 22282295;BA.debugLine="lblTexto1.Top = 30%y";
mostCurrent._lbltexto1.setTop(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
RDebugUtils.currentLine=22282296;
 //BA.debugLineNum = 22282296;BA.debugLine="lblTitulo.Width = 100%x";
mostCurrent._lbltitulo.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=22282297;
 //BA.debugLineNum = 22282297;BA.debugLine="lblTexto1.Width = 90%x";
mostCurrent._lbltexto1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA));
RDebugUtils.currentLine=22282298;
 //BA.debugLineNum = 22282298;BA.debugLine="imgTexto1.Bitmap = Null";
mostCurrent._imgtexto1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
RDebugUtils.currentLine=22282299;
 //BA.debugLineNum = 22282299;BA.debugLine="Container.AddPageAt(Pan(3), \"panel3\", 3)";
mostCurrent._container.AddPageAt((android.view.View)(_pan[(int) (3)].getObject()),"panel3",(int) (3));
RDebugUtils.currentLine=22282301;
 //BA.debugLineNum = 22282301;BA.debugLine="Activity.LoadLayout(\"layComoFotos\")";
mostCurrent._activity.LoadLayout("layComoFotos",mostCurrent.activityBA);
RDebugUtils.currentLine=22282306;
 //BA.debugLineNum = 22282306;BA.debugLine="AHViewPager1.RemoveView";
mostCurrent._ahviewpager1.RemoveView();
RDebugUtils.currentLine=22282307;
 //BA.debugLineNum = 22282307;BA.debugLine="AHViewPager1.Initialize2(Container,\"Container\")";
mostCurrent._ahviewpager1.Initialize2(mostCurrent.activityBA,mostCurrent._container,"Container");
RDebugUtils.currentLine=22282308;
 //BA.debugLineNum = 22282308;BA.debugLine="Activity.AddView(AHViewPager1, 0dip, 0dip, Activi";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._ahviewpager1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()-mostCurrent._lblslide.getHeight()));
RDebugUtils.currentLine=22282309;
 //BA.debugLineNum = 22282309;BA.debugLine="lblSlide.Top = Activity.Height - lblSlide.Height";
mostCurrent._lblslide.setTop((int) (mostCurrent._activity.getHeight()-mostCurrent._lblslide.getHeight()));
RDebugUtils.currentLine=22282311;
 //BA.debugLineNum = 22282311;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="frmcomofotos";
RDebugUtils.currentLine=22413312;
 //BA.debugLineNum = 22413312;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=22413314;
 //BA.debugLineNum = 22413314;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="frmcomofotos";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null));}
RDebugUtils.currentLine=22347776;
 //BA.debugLineNum = 22347776;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=22347778;
 //BA.debugLineNum = 22347778;BA.debugLine="End Sub";
return "";
}
public static String  _btnvolver_click() throws Exception{
RDebugUtils.currentModule="frmcomofotos";
if (Debug.shouldDelegate(mostCurrent.activityBA, "btnvolver_click"))
	 {return ((String) Debug.delegate(mostCurrent.activityBA, "btnvolver_click", null));}
RDebugUtils.currentLine=22478848;
 //BA.debugLineNum = 22478848;BA.debugLine="Sub btnVolver_Click";
RDebugUtils.currentLine=22478849;
 //BA.debugLineNum = 22478849;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=22478850;
 //BA.debugLineNum = 22478850;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
RDebugUtils.currentLine=22478851;
 //BA.debugLineNum = 22478851;BA.debugLine="End Sub";
return "";
}
}