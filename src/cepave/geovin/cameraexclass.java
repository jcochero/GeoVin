package cepave.geovin;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class cameraexclass extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new anywheresoftware.b4a.ShellBA(_ba, this, htSubs, "cepave.geovin.cameraexclass");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", cepave.geovin.cameraexclass.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 
    public void  innerInitializeHelper(anywheresoftware.b4a.BA _ba) throws Exception{
        innerInitialize(_ba);
    }
    public Object callSub(String sub, Object sender, Object[] args) throws Exception {
        return BA.SubDelegator.SubNotFound;
    }
public static class _camerainfoandid{
public boolean IsInitialized;
public Object CameraInfo;
public int Id;
public void Initialize() {
IsInitialized = true;
CameraInfo = new Object();
Id = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _camerasize{
public boolean IsInitialized;
public int Width;
public int Height;
public void Initialize() {
IsInitialized = true;
Width = 0;
Height = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public anywheresoftware.b4a.keywords.Common __c = null;
public Object _nativecam = null;
public anywheresoftware.b4a.objects.CameraW _cam = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
public Object _target = null;
public String _event = "";
public boolean _front = false;
public Object _parameters = null;
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
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public String  _camera_focusdone(cepave.geovin.cameraexclass __ref,boolean _success) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "camera_focusdone"))
	 {return ((String) Debug.delegate(ba, "camera_focusdone", new Object[] {_success}));}
RDebugUtils.currentLine=15728640;
 //BA.debugLineNum = 15728640;BA.debugLine="Private Sub Camera_FocusDone (Success As Boolean)";
RDebugUtils.currentLine=15728641;
 //BA.debugLineNum = 15728641;BA.debugLine="If Success Then";
if (_success) { 
RDebugUtils.currentLine=15728642;
 //BA.debugLineNum = 15728642;BA.debugLine="TakePicture";
__ref._takepicture(null);
 }else {
RDebugUtils.currentLine=15728644;
 //BA.debugLineNum = 15728644;BA.debugLine="Log(\"AutoFocus error.\")";
__c.Log("AutoFocus error.");
 };
RDebugUtils.currentLine=15728646;
 //BA.debugLineNum = 15728646;BA.debugLine="End Sub";
return "";
}
public String  _takepicture(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "takepicture"))
	 {return ((String) Debug.delegate(ba, "takepicture", null));}
RDebugUtils.currentLine=13565952;
 //BA.debugLineNum = 13565952;BA.debugLine="Public Sub TakePicture";
RDebugUtils.currentLine=13565954;
 //BA.debugLineNum = 13565954;BA.debugLine="cam.TakePicture";
__ref._cam.TakePicture();
RDebugUtils.currentLine=13565955;
 //BA.debugLineNum = 13565955;BA.debugLine="End Sub";
return "";
}
public String  _camera_picturetaken(cepave.geovin.cameraexclass __ref,byte[] _data) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "camera_picturetaken"))
	 {return ((String) Debug.delegate(ba, "camera_picturetaken", new Object[] {_data}));}
RDebugUtils.currentLine=13631488;
 //BA.debugLineNum = 13631488;BA.debugLine="Private Sub Camera_PictureTaken (Data() As Byte)";
RDebugUtils.currentLine=13631489;
 //BA.debugLineNum = 13631489;BA.debugLine="CallSub2(target, event & \"_PictureTaken\", Data)";
__c.CallSubNew2(ba,__ref._target,__ref._event+"_PictureTaken",(Object)(_data));
RDebugUtils.currentLine=13631490;
 //BA.debugLineNum = 13631490;BA.debugLine="End Sub";
return "";
}
public String  _camera_ready(cepave.geovin.cameraexclass __ref,boolean _success) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "camera_ready"))
	 {return ((String) Debug.delegate(ba, "camera_ready", new Object[] {_success}));}
RDebugUtils.currentLine=13500416;
 //BA.debugLineNum = 13500416;BA.debugLine="Private Sub Camera_Ready (Success As Boolean)";
RDebugUtils.currentLine=13500417;
 //BA.debugLineNum = 13500417;BA.debugLine="If Success Then";
if (_success) { 
RDebugUtils.currentLine=13500418;
 //BA.debugLineNum = 13500418;BA.debugLine="r.target = cam";
__ref._r.Target = (Object)(__ref._cam);
RDebugUtils.currentLine=13500419;
 //BA.debugLineNum = 13500419;BA.debugLine="nativeCam = r.GetField(\"camera\")";
__ref._nativecam = __ref._r.GetField("camera");
RDebugUtils.currentLine=13500420;
 //BA.debugLineNum = 13500420;BA.debugLine="r.target = nativeCam";
__ref._r.Target = __ref._nativecam;
RDebugUtils.currentLine=13500421;
 //BA.debugLineNum = 13500421;BA.debugLine="parameters = r.RunMethod(\"getParameters\")";
__ref._parameters = __ref._r.RunMethod("getParameters");
RDebugUtils.currentLine=13500422;
 //BA.debugLineNum = 13500422;BA.debugLine="SetDisplayOrientation";
__ref._setdisplayorientation(null);
 }else {
RDebugUtils.currentLine=13500424;
 //BA.debugLineNum = 13500424;BA.debugLine="Log(\"success = false, \" & LastException)";
__c.Log("success = false, "+BA.ObjectToString(__c.LastException(ba)));
 };
RDebugUtils.currentLine=13500426;
 //BA.debugLineNum = 13500426;BA.debugLine="CallSub2(target, event & \"_ready\", Success)";
__c.CallSubNew2(ba,__ref._target,__ref._event+"_ready",(Object)(_success));
RDebugUtils.currentLine=13500427;
 //BA.debugLineNum = 13500427;BA.debugLine="End Sub";
return "";
}
public String  _setdisplayorientation(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setdisplayorientation"))
	 {return ((String) Debug.delegate(ba, "setdisplayorientation", null));}
int _previewresult = 0;
int _result = 0;
int _degrees = 0;
cepave.geovin.cameraexclass._camerainfoandid _ci = null;
int _orientation = 0;
RDebugUtils.currentLine=13434880;
 //BA.debugLineNum = 13434880;BA.debugLine="Private Sub SetDisplayOrientation";
RDebugUtils.currentLine=13434881;
 //BA.debugLineNum = 13434881;BA.debugLine="r.target = r.GetActivity";
__ref._r.Target = (Object)(__ref._r.GetActivity(ba));
RDebugUtils.currentLine=13434882;
 //BA.debugLineNum = 13434882;BA.debugLine="r.target = r.RunMethod(\"getWindowManager\")";
__ref._r.Target = __ref._r.RunMethod("getWindowManager");
RDebugUtils.currentLine=13434883;
 //BA.debugLineNum = 13434883;BA.debugLine="r.target = r.RunMethod(\"getDefaultDisplay\")";
__ref._r.Target = __ref._r.RunMethod("getDefaultDisplay");
RDebugUtils.currentLine=13434884;
 //BA.debugLineNum = 13434884;BA.debugLine="r.target = r.RunMethod(\"getRotation\")";
__ref._r.Target = __ref._r.RunMethod("getRotation");
RDebugUtils.currentLine=13434885;
 //BA.debugLineNum = 13434885;BA.debugLine="Dim previewResult, result, degrees As Int = r.tar";
_previewresult = 0;
_result = 0;
_degrees = (int) ((double)(BA.ObjectToNumber(__ref._r.Target))*90);
RDebugUtils.currentLine=13434886;
 //BA.debugLineNum = 13434886;BA.debugLine="Dim ci As CameraInfoAndId = FindCamera(Front)";
_ci = __ref._findcamera(null,__ref._front);
RDebugUtils.currentLine=13434887;
 //BA.debugLineNum = 13434887;BA.debugLine="r.target = ci.CameraInfo";
__ref._r.Target = _ci.CameraInfo;
RDebugUtils.currentLine=13434888;
 //BA.debugLineNum = 13434888;BA.debugLine="Dim orientation As Int = r.GetField(\"orientation\"";
_orientation = (int)(BA.ObjectToNumber(__ref._r.GetField("orientation")));
RDebugUtils.currentLine=13434889;
 //BA.debugLineNum = 13434889;BA.debugLine="If Front Then";
if (__ref._front) { 
RDebugUtils.currentLine=13434890;
 //BA.debugLineNum = 13434890;BA.debugLine="previewResult = (orientation + degrees) Mod 360";
_previewresult = (int) ((_orientation+_degrees)%360);
RDebugUtils.currentLine=13434891;
 //BA.debugLineNum = 13434891;BA.debugLine="result = previewResult";
_result = _previewresult;
RDebugUtils.currentLine=13434892;
 //BA.debugLineNum = 13434892;BA.debugLine="previewResult = (360 - previewResult) Mod 360";
_previewresult = (int) ((360-_previewresult)%360);
 }else {
RDebugUtils.currentLine=13434894;
 //BA.debugLineNum = 13434894;BA.debugLine="previewResult = (orientation - degrees + 360) Mo";
_previewresult = (int) ((_orientation-_degrees+360)%360);
RDebugUtils.currentLine=13434895;
 //BA.debugLineNum = 13434895;BA.debugLine="result = previewResult";
_result = _previewresult;
RDebugUtils.currentLine=13434896;
 //BA.debugLineNum = 13434896;BA.debugLine="Log(previewResult)";
__c.Log(BA.NumberToString(_previewresult));
 };
RDebugUtils.currentLine=13434898;
 //BA.debugLineNum = 13434898;BA.debugLine="r.target = nativeCam";
__ref._r.Target = __ref._nativecam;
RDebugUtils.currentLine=13434899;
 //BA.debugLineNum = 13434899;BA.debugLine="r.RunMethod2(\"setDisplayOrientation\", previewResu";
__ref._r.RunMethod2("setDisplayOrientation",BA.NumberToString(_previewresult),"java.lang.int");
RDebugUtils.currentLine=13434900;
 //BA.debugLineNum = 13434900;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=13434901;
 //BA.debugLineNum = 13434901;BA.debugLine="r.RunMethod2(\"setRotation\", result, \"java.lang.in";
__ref._r.RunMethod2("setRotation",BA.NumberToString(_result),"java.lang.int");
RDebugUtils.currentLine=13434902;
 //BA.debugLineNum = 13434902;BA.debugLine="CommitParameters";
__ref._commitparameters(null);
RDebugUtils.currentLine=13434903;
 //BA.debugLineNum = 13434903;BA.debugLine="End Sub";
return "";
}
public String  _class_globals(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
RDebugUtils.currentLine=13238272;
 //BA.debugLineNum = 13238272;BA.debugLine="Sub Class_Globals";
RDebugUtils.currentLine=13238273;
 //BA.debugLineNum = 13238273;BA.debugLine="Private nativeCam As Object";
_nativecam = new Object();
RDebugUtils.currentLine=13238274;
 //BA.debugLineNum = 13238274;BA.debugLine="Private cam As Camera";
_cam = new anywheresoftware.b4a.objects.CameraW();
RDebugUtils.currentLine=13238275;
 //BA.debugLineNum = 13238275;BA.debugLine="Private r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
RDebugUtils.currentLine=13238276;
 //BA.debugLineNum = 13238276;BA.debugLine="Private target As Object";
_target = new Object();
RDebugUtils.currentLine=13238277;
 //BA.debugLineNum = 13238277;BA.debugLine="Private event As String";
_event = "";
RDebugUtils.currentLine=13238278;
 //BA.debugLineNum = 13238278;BA.debugLine="Public Front As Boolean";
_front = false;
RDebugUtils.currentLine=13238279;
 //BA.debugLineNum = 13238279;BA.debugLine="Type CameraInfoAndId (CameraInfo As Object, Id As";
;
RDebugUtils.currentLine=13238280;
 //BA.debugLineNum = 13238280;BA.debugLine="Type CameraSize (Width As Int, Height As Int)";
;
RDebugUtils.currentLine=13238281;
 //BA.debugLineNum = 13238281;BA.debugLine="Private parameters As Object";
_parameters = new Object();
RDebugUtils.currentLine=13238285;
 //BA.debugLineNum = 13238285;BA.debugLine="End Sub";
return "";
}
public String  _closenow(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "closenow"))
	 {return ((String) Debug.delegate(ba, "closenow", null));}
RDebugUtils.currentLine=15597568;
 //BA.debugLineNum = 15597568;BA.debugLine="Public Sub CloseNow";
RDebugUtils.currentLine=15597569;
 //BA.debugLineNum = 15597569;BA.debugLine="cam.Release";
__ref._cam.Release();
RDebugUtils.currentLine=15597570;
 //BA.debugLineNum = 15597570;BA.debugLine="r.target = cam";
__ref._r.Target = (Object)(__ref._cam);
RDebugUtils.currentLine=15597571;
 //BA.debugLineNum = 15597571;BA.debugLine="r.RunMethod2(\"releaseCameras\", True, \"java.lang.b";
__ref._r.RunMethod2("releaseCameras",BA.ObjectToString(__c.True),"java.lang.boolean");
RDebugUtils.currentLine=15597572;
 //BA.debugLineNum = 15597572;BA.debugLine="End Sub";
return "";
}
public String  _commitparameters(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "commitparameters"))
	 {return ((String) Debug.delegate(ba, "commitparameters", null));}
RDebugUtils.currentLine=14090240;
 //BA.debugLineNum = 14090240;BA.debugLine="Public Sub CommitParameters";
RDebugUtils.currentLine=14090242;
 //BA.debugLineNum = 14090242;BA.debugLine="r.target = nativeCam";
__ref._r.Target = __ref._nativecam;
RDebugUtils.currentLine=14090243;
 //BA.debugLineNum = 14090243;BA.debugLine="r.RunMethod4(\"setParameters\", Array As Object(pa";
__ref._r.RunMethod4("setParameters",new Object[]{__ref._parameters},new String[]{"android.hardware.Camera$Parameters"});
RDebugUtils.currentLine=14090248;
 //BA.debugLineNum = 14090248;BA.debugLine="End Sub";
return "";
}
public Object  _facedetection_event(cepave.geovin.cameraexclass __ref,String _methodname,Object[] _args) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "facedetection_event"))
	 {return ((Object) Debug.delegate(ba, "facedetection_event", new Object[] {_methodname,_args}));}
Object[] _faces = null;
Object _f = null;
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _facerect = null;
RDebugUtils.currentLine=16384000;
 //BA.debugLineNum = 16384000;BA.debugLine="Private Sub FaceDetection_Event (MethodName As Str";
RDebugUtils.currentLine=16384001;
 //BA.debugLineNum = 16384001;BA.debugLine="Dim faces() As Object = Args(0)";
_faces = (Object[])(_args[(int) (0)]);
RDebugUtils.currentLine=16384002;
 //BA.debugLineNum = 16384002;BA.debugLine="For Each f As Object In faces";
{
final Object[] group2 = _faces;
final int groupLen2 = group2.length
;int index2 = 0;
;
for (; index2 < groupLen2;index2++){
_f = group2[index2];
RDebugUtils.currentLine=16384003;
 //BA.debugLineNum = 16384003;BA.debugLine="Dim jo As JavaObject = f";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo.setObject((java.lang.Object)(_f));
RDebugUtils.currentLine=16384004;
 //BA.debugLineNum = 16384004;BA.debugLine="Dim faceRect As Rect = jo.GetField(\"rect\")";
_facerect = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
_facerect.setObject((android.graphics.Rect)(_jo.GetField("rect")));
RDebugUtils.currentLine=16384005;
 //BA.debugLineNum = 16384005;BA.debugLine="Log(faceRect)";
__c.Log(BA.ObjectToString(_facerect));
 }
};
RDebugUtils.currentLine=16384007;
 //BA.debugLineNum = 16384007;BA.debugLine="Return Null";
if (true) return __c.Null;
RDebugUtils.currentLine=16384008;
 //BA.debugLineNum = 16384008;BA.debugLine="End Sub";
return null;
}
public cepave.geovin.cameraexclass._camerainfoandid  _findcamera(cepave.geovin.cameraexclass __ref,boolean _frontcamera) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "findcamera"))
	 {return ((cepave.geovin.cameraexclass._camerainfoandid) Debug.delegate(ba, "findcamera", new Object[] {_frontcamera}));}
cepave.geovin.cameraexclass._camerainfoandid _ci = null;
Object _camerainfo = null;
int _cameravalue = 0;
int _numberofcameras = 0;
int _i = 0;
RDebugUtils.currentLine=13369344;
 //BA.debugLineNum = 13369344;BA.debugLine="Private Sub FindCamera (frontCamera As Boolean) As";
RDebugUtils.currentLine=13369345;
 //BA.debugLineNum = 13369345;BA.debugLine="Dim ci As CameraInfoAndId";
_ci = new cepave.geovin.cameraexclass._camerainfoandid();
RDebugUtils.currentLine=13369346;
 //BA.debugLineNum = 13369346;BA.debugLine="Dim cameraInfo As Object";
_camerainfo = new Object();
RDebugUtils.currentLine=13369347;
 //BA.debugLineNum = 13369347;BA.debugLine="Dim cameraValue As Int";
_cameravalue = 0;
RDebugUtils.currentLine=13369348;
 //BA.debugLineNum = 13369348;BA.debugLine="Log(\"findCamera\")";
__c.Log("findCamera");
RDebugUtils.currentLine=13369349;
 //BA.debugLineNum = 13369349;BA.debugLine="If frontCamera Then cameraValue = 1 Else cameraVa";
if (_frontcamera) { 
_cameravalue = (int) (1);}
else {
_cameravalue = (int) (0);};
RDebugUtils.currentLine=13369350;
 //BA.debugLineNum = 13369350;BA.debugLine="cameraInfo = r.CreateObject(\"android.hardware.Cam";
_camerainfo = __ref._r.CreateObject("android.hardware.Camera$CameraInfo");
RDebugUtils.currentLine=13369351;
 //BA.debugLineNum = 13369351;BA.debugLine="Dim numberOfCameras As Int = r.RunStaticMethod(\"a";
_numberofcameras = (int)(BA.ObjectToNumber(__ref._r.RunStaticMethod("android.hardware.Camera","getNumberOfCameras",(Object[])(__c.Null),(String[])(__c.Null))));
RDebugUtils.currentLine=13369352;
 //BA.debugLineNum = 13369352;BA.debugLine="Log(r.target)";
__c.Log(BA.ObjectToString(__ref._r.Target));
RDebugUtils.currentLine=13369353;
 //BA.debugLineNum = 13369353;BA.debugLine="Log(numberOfCameras)";
__c.Log(BA.NumberToString(_numberofcameras));
RDebugUtils.currentLine=13369354;
 //BA.debugLineNum = 13369354;BA.debugLine="For i = 0 To numberOfCameras - 1";
{
final int step10 = 1;
final int limit10 = (int) (_numberofcameras-1);
_i = (int) (0) ;
for (;_i <= limit10 ;_i = _i + step10 ) {
RDebugUtils.currentLine=13369355;
 //BA.debugLineNum = 13369355;BA.debugLine="r.RunStaticMethod(\"android.hardware.Camera\", \"ge";
__ref._r.RunStaticMethod("android.hardware.Camera","getCameraInfo",new Object[]{(Object)(_i),_camerainfo},new String[]{"java.lang.int","android.hardware.Camera$CameraInfo"});
RDebugUtils.currentLine=13369357;
 //BA.debugLineNum = 13369357;BA.debugLine="r.target = cameraInfo";
__ref._r.Target = _camerainfo;
RDebugUtils.currentLine=13369358;
 //BA.debugLineNum = 13369358;BA.debugLine="Log(\"facing: \" & r.GetField(\"facing\") & \", \" & c";
__c.Log("facing: "+BA.ObjectToString(__ref._r.GetField("facing"))+", "+BA.NumberToString(_cameravalue));
RDebugUtils.currentLine=13369359;
 //BA.debugLineNum = 13369359;BA.debugLine="If r.GetField(\"facing\") = cameraValue Then";
if ((__ref._r.GetField("facing")).equals((Object)(_cameravalue))) { 
RDebugUtils.currentLine=13369360;
 //BA.debugLineNum = 13369360;BA.debugLine="ci.cameraInfo = r.target";
_ci.CameraInfo = __ref._r.Target;
RDebugUtils.currentLine=13369361;
 //BA.debugLineNum = 13369361;BA.debugLine="ci.Id = i";
_ci.Id = _i;
RDebugUtils.currentLine=13369362;
 //BA.debugLineNum = 13369362;BA.debugLine="Return ci";
if (true) return _ci;
 };
 }
};
RDebugUtils.currentLine=13369365;
 //BA.debugLineNum = 13369365;BA.debugLine="ci.id = -1";
_ci.Id = (int) (-1);
RDebugUtils.currentLine=13369366;
 //BA.debugLineNum = 13369366;BA.debugLine="Return ci";
if (true) return _ci;
RDebugUtils.currentLine=13369367;
 //BA.debugLineNum = 13369367;BA.debugLine="End Sub";
return null;
}
public String  _focusandtakepicture(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "focusandtakepicture"))
	 {return ((String) Debug.delegate(ba, "focusandtakepicture", null));}
RDebugUtils.currentLine=15663104;
 //BA.debugLineNum = 15663104;BA.debugLine="Public Sub FocusAndTakePicture";
RDebugUtils.currentLine=15663105;
 //BA.debugLineNum = 15663105;BA.debugLine="cam.AutoFocus";
__ref._cam.AutoFocus();
RDebugUtils.currentLine=15663106;
 //BA.debugLineNum = 15663106;BA.debugLine="End Sub";
return "";
}
public String  _getcoloreffect(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getcoloreffect"))
	 {return ((String) Debug.delegate(ba, "getcoloreffect", null));}
RDebugUtils.currentLine=14155776;
 //BA.debugLineNum = 14155776;BA.debugLine="Public Sub GetColorEffect As String";
RDebugUtils.currentLine=14155777;
 //BA.debugLineNum = 14155777;BA.debugLine="Return GetParameter(\"effect\")";
if (true) return __ref._getparameter(null,"effect");
RDebugUtils.currentLine=14155778;
 //BA.debugLineNum = 14155778;BA.debugLine="End Sub";
return "";
}
public String  _getparameter(cepave.geovin.cameraexclass __ref,String _key) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getparameter"))
	 {return ((String) Debug.delegate(ba, "getparameter", new Object[] {_key}));}
RDebugUtils.currentLine=14024704;
 //BA.debugLineNum = 14024704;BA.debugLine="Public Sub GetParameter(Key As String) As String";
RDebugUtils.currentLine=14024705;
 //BA.debugLineNum = 14024705;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14024706;
 //BA.debugLineNum = 14024706;BA.debugLine="Return r.RunMethod2(\"get\", Key, \"java.lang.String";
if (true) return BA.ObjectToString(__ref._r.RunMethod2("get",_key,"java.lang.String"));
RDebugUtils.currentLine=14024707;
 //BA.debugLineNum = 14024707;BA.debugLine="End Sub";
return "";
}
public int  _getexposurecompensation(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getexposurecompensation"))
	 {return ((Integer) Debug.delegate(ba, "getexposurecompensation", null));}
RDebugUtils.currentLine=16056320;
 //BA.debugLineNum = 16056320;BA.debugLine="Public Sub getExposureCompensation As Int";
RDebugUtils.currentLine=16056321;
 //BA.debugLineNum = 16056321;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=16056322;
 //BA.debugLineNum = 16056322;BA.debugLine="Return r.RunMethod(\"getExposureCompensation\")";
if (true) return (int)(BA.ObjectToNumber(__ref._r.RunMethod("getExposureCompensation")));
RDebugUtils.currentLine=16056323;
 //BA.debugLineNum = 16056323;BA.debugLine="End Sub";
return 0;
}
public String  _getflashmode(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getflashmode"))
	 {return ((String) Debug.delegate(ba, "getflashmode", null));}
RDebugUtils.currentLine=14680064;
 //BA.debugLineNum = 14680064;BA.debugLine="Public Sub GetFlashMode As String";
RDebugUtils.currentLine=14680065;
 //BA.debugLineNum = 14680065;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14680066;
 //BA.debugLineNum = 14680066;BA.debugLine="Return r.RunMethod(\"getFlashMode\")";
if (true) return BA.ObjectToString(__ref._r.RunMethod("getFlashMode"));
RDebugUtils.currentLine=14680067;
 //BA.debugLineNum = 14680067;BA.debugLine="End Sub";
return "";
}
public float[]  _getfocusdistances(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getfocusdistances"))
	 {return ((float[]) Debug.delegate(ba, "getfocusdistances", null));}
float[] _f = null;
RDebugUtils.currentLine=15466496;
 //BA.debugLineNum = 15466496;BA.debugLine="Public Sub GetFocusDistances As Float()";
RDebugUtils.currentLine=15466497;
 //BA.debugLineNum = 15466497;BA.debugLine="Dim F(3) As Float";
_f = new float[(int) (3)];
;
RDebugUtils.currentLine=15466498;
 //BA.debugLineNum = 15466498;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15466499;
 //BA.debugLineNum = 15466499;BA.debugLine="r.RunMethod4(\"getFocusDistances\", Array As Object";
__ref._r.RunMethod4("getFocusDistances",new Object[]{(Object)(_f)},new String[]{"[F"});
RDebugUtils.currentLine=15466500;
 //BA.debugLineNum = 15466500;BA.debugLine="Return F";
if (true) return _f;
RDebugUtils.currentLine=15466501;
 //BA.debugLineNum = 15466501;BA.debugLine="End Sub";
return null;
}
public int  _getmaxexposurecompensation(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getmaxexposurecompensation"))
	 {return ((Integer) Debug.delegate(ba, "getmaxexposurecompensation", null));}
RDebugUtils.currentLine=16252928;
 //BA.debugLineNum = 16252928;BA.debugLine="Public Sub getMaxExposureCompensation As Int";
RDebugUtils.currentLine=16252929;
 //BA.debugLineNum = 16252929;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=16252930;
 //BA.debugLineNum = 16252930;BA.debugLine="Return r.RunMethod(\"getMaxExposureCompensation\")";
if (true) return (int)(BA.ObjectToNumber(__ref._r.RunMethod("getMaxExposureCompensation")));
RDebugUtils.currentLine=16252931;
 //BA.debugLineNum = 16252931;BA.debugLine="End Sub";
return 0;
}
public int  _getmaxzoom(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getmaxzoom"))
	 {return ((Integer) Debug.delegate(ba, "getmaxzoom", null));}
RDebugUtils.currentLine=15859712;
 //BA.debugLineNum = 15859712;BA.debugLine="Public Sub GetMaxZoom As Int";
RDebugUtils.currentLine=15859713;
 //BA.debugLineNum = 15859713;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15859714;
 //BA.debugLineNum = 15859714;BA.debugLine="Return r.RunMethod(\"getMaxZoom\")";
if (true) return (int)(BA.ObjectToNumber(__ref._r.RunMethod("getMaxZoom")));
RDebugUtils.currentLine=15859715;
 //BA.debugLineNum = 15859715;BA.debugLine="End Sub";
return 0;
}
public int  _getminexposurecompensation(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getminexposurecompensation"))
	 {return ((Integer) Debug.delegate(ba, "getminexposurecompensation", null));}
RDebugUtils.currentLine=16187392;
 //BA.debugLineNum = 16187392;BA.debugLine="Public Sub getMinExposureCompensation As Int";
RDebugUtils.currentLine=16187393;
 //BA.debugLineNum = 16187393;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=16187394;
 //BA.debugLineNum = 16187394;BA.debugLine="Return r.RunMethod(\"getMinExposureCompensation\")";
if (true) return (int)(BA.ObjectToNumber(__ref._r.RunMethod("getMinExposureCompensation")));
RDebugUtils.currentLine=16187395;
 //BA.debugLineNum = 16187395;BA.debugLine="End Sub";
return 0;
}
public cepave.geovin.cameraexclass._camerasize  _getpicturesize(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getpicturesize"))
	 {return ((cepave.geovin.cameraexclass._camerasize) Debug.delegate(ba, "getpicturesize", null));}
cepave.geovin.cameraexclass._camerasize _cs = null;
RDebugUtils.currentLine=15138816;
 //BA.debugLineNum = 15138816;BA.debugLine="Public Sub GetPictureSize As CameraSize";
RDebugUtils.currentLine=15138817;
 //BA.debugLineNum = 15138817;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15138818;
 //BA.debugLineNum = 15138818;BA.debugLine="r.target = r.RunMethod(\"getPictureSize\")";
__ref._r.Target = __ref._r.RunMethod("getPictureSize");
RDebugUtils.currentLine=15138819;
 //BA.debugLineNum = 15138819;BA.debugLine="Dim cs As CameraSize";
_cs = new cepave.geovin.cameraexclass._camerasize();
RDebugUtils.currentLine=15138820;
 //BA.debugLineNum = 15138820;BA.debugLine="cs.Width = r.GetField(\"width\")";
_cs.Width = (int)(BA.ObjectToNumber(__ref._r.GetField("width")));
RDebugUtils.currentLine=15138821;
 //BA.debugLineNum = 15138821;BA.debugLine="cs.Height = r.GetField(\"height\")";
_cs.Height = (int)(BA.ObjectToNumber(__ref._r.GetField("height")));
RDebugUtils.currentLine=15138822;
 //BA.debugLineNum = 15138822;BA.debugLine="Return cs";
if (true) return _cs;
RDebugUtils.currentLine=15138823;
 //BA.debugLineNum = 15138823;BA.debugLine="End Sub";
return null;
}
public String  _getpreviewfpsrange(cepave.geovin.cameraexclass __ref,int[] _range) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getpreviewfpsrange"))
	 {return ((String) Debug.delegate(ba, "getpreviewfpsrange", new Object[] {_range}));}
RDebugUtils.currentLine=14942208;
 //BA.debugLineNum = 14942208;BA.debugLine="Public Sub GetPreviewFpsRange(Range() As Int)";
RDebugUtils.currentLine=14942209;
 //BA.debugLineNum = 14942209;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14942210;
 //BA.debugLineNum = 14942210;BA.debugLine="r.RunMethod4(\"getPreviewFpsRange\", Array As Objec";
__ref._r.RunMethod4("getPreviewFpsRange",new Object[]{(Object)(_range)},new String[]{"[I"});
RDebugUtils.currentLine=14942211;
 //BA.debugLineNum = 14942211;BA.debugLine="End Sub";
return "";
}
public cepave.geovin.cameraexclass._camerasize  _getpreviewsize(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getpreviewsize"))
	 {return ((cepave.geovin.cameraexclass._camerasize) Debug.delegate(ba, "getpreviewsize", null));}
cepave.geovin.cameraexclass._camerasize _cs = null;
RDebugUtils.currentLine=15073280;
 //BA.debugLineNum = 15073280;BA.debugLine="Public Sub GetPreviewSize As CameraSize";
RDebugUtils.currentLine=15073281;
 //BA.debugLineNum = 15073281;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15073282;
 //BA.debugLineNum = 15073282;BA.debugLine="r.target = r.RunMethod(\"getPreviewSize\")";
__ref._r.Target = __ref._r.RunMethod("getPreviewSize");
RDebugUtils.currentLine=15073283;
 //BA.debugLineNum = 15073283;BA.debugLine="Dim cs As CameraSize";
_cs = new cepave.geovin.cameraexclass._camerasize();
RDebugUtils.currentLine=15073284;
 //BA.debugLineNum = 15073284;BA.debugLine="cs.Width = r.GetField(\"width\")";
_cs.Width = (int)(BA.ObjectToNumber(__ref._r.GetField("width")));
RDebugUtils.currentLine=15073285;
 //BA.debugLineNum = 15073285;BA.debugLine="cs.Height = r.GetField(\"height\")";
_cs.Height = (int)(BA.ObjectToNumber(__ref._r.GetField("height")));
RDebugUtils.currentLine=15073286;
 //BA.debugLineNum = 15073286;BA.debugLine="Return cs";
if (true) return _cs;
RDebugUtils.currentLine=15073287;
 //BA.debugLineNum = 15073287;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedcoloreffects(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getsupportedcoloreffects"))
	 {return ((anywheresoftware.b4a.objects.collections.List) Debug.delegate(ba, "getsupportedcoloreffects", null));}
RDebugUtils.currentLine=14811136;
 //BA.debugLineNum = 14811136;BA.debugLine="Public Sub GetSupportedColorEffects As List";
RDebugUtils.currentLine=14811137;
 //BA.debugLineNum = 14811137;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14811138;
 //BA.debugLineNum = 14811138;BA.debugLine="Return r.RunMethod(\"getSupportedColorEffects\")";
if (true) return (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(__ref._r.RunMethod("getSupportedColorEffects")));
RDebugUtils.currentLine=14811139;
 //BA.debugLineNum = 14811139;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedflashmodes(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getsupportedflashmodes"))
	 {return ((anywheresoftware.b4a.objects.collections.List) Debug.delegate(ba, "getsupportedflashmodes", null));}
RDebugUtils.currentLine=14745600;
 //BA.debugLineNum = 14745600;BA.debugLine="Public Sub GetSupportedFlashModes As List";
RDebugUtils.currentLine=14745601;
 //BA.debugLineNum = 14745601;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14745602;
 //BA.debugLineNum = 14745602;BA.debugLine="Return r.RunMethod(\"getSupportedFlashModes\")";
if (true) return (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(__ref._r.RunMethod("getSupportedFlashModes")));
RDebugUtils.currentLine=14745603;
 //BA.debugLineNum = 14745603;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedfocusmodes(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getsupportedfocusmodes"))
	 {return ((anywheresoftware.b4a.objects.collections.List) Debug.delegate(ba, "getsupportedfocusmodes", null));}
RDebugUtils.currentLine=15269888;
 //BA.debugLineNum = 15269888;BA.debugLine="Public Sub GetSupportedFocusModes As List";
RDebugUtils.currentLine=15269889;
 //BA.debugLineNum = 15269889;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15269890;
 //BA.debugLineNum = 15269890;BA.debugLine="Return r.RunMethod(\"getSupportedFocusModes\")";
if (true) return (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(__ref._r.RunMethod("getSupportedFocusModes")));
RDebugUtils.currentLine=15269891;
 //BA.debugLineNum = 15269891;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedpictureformats(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getsupportedpictureformats"))
	 {return ((anywheresoftware.b4a.objects.collections.List) Debug.delegate(ba, "getsupportedpictureformats", null));}
RDebugUtils.currentLine=15532032;
 //BA.debugLineNum = 15532032;BA.debugLine="Public Sub GetSupportedPictureFormats As List";
RDebugUtils.currentLine=15532033;
 //BA.debugLineNum = 15532033;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15532034;
 //BA.debugLineNum = 15532034;BA.debugLine="Return r.RunMethod(\"getSupportedPictureFormats";
if (true) return (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(__ref._r.RunMethod("getSupportedPictureFormats")));
RDebugUtils.currentLine=15532035;
 //BA.debugLineNum = 15532035;BA.debugLine="End Sub";
return null;
}
public cepave.geovin.cameraexclass._camerasize[]  _getsupportedpicturessizes(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getsupportedpicturessizes"))
	 {return ((cepave.geovin.cameraexclass._camerasize[]) Debug.delegate(ba, "getsupportedpicturessizes", null));}
anywheresoftware.b4a.objects.collections.List _list1 = null;
cepave.geovin.cameraexclass._camerasize[] _cs = null;
int _i = 0;
RDebugUtils.currentLine=14417920;
 //BA.debugLineNum = 14417920;BA.debugLine="Public Sub GetSupportedPicturesSizes As CameraSize";
RDebugUtils.currentLine=14417921;
 //BA.debugLineNum = 14417921;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14417922;
 //BA.debugLineNum = 14417922;BA.debugLine="Dim list1 As List = r.RunMethod(\"getSupportedPict";
_list1 = new anywheresoftware.b4a.objects.collections.List();
_list1.setObject((java.util.List)(__ref._r.RunMethod("getSupportedPictureSizes")));
RDebugUtils.currentLine=14417923;
 //BA.debugLineNum = 14417923;BA.debugLine="Dim cs(list1.Size) As CameraSize";
_cs = new cepave.geovin.cameraexclass._camerasize[_list1.getSize()];
{
int d0 = _cs.length;
for (int i0 = 0;i0 < d0;i0++) {
_cs[i0] = new cepave.geovin.cameraexclass._camerasize();
}
}
;
RDebugUtils.currentLine=14417924;
 //BA.debugLineNum = 14417924;BA.debugLine="For i = 0 To list1.Size - 1";
{
final int step4 = 1;
final int limit4 = (int) (_list1.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
RDebugUtils.currentLine=14417925;
 //BA.debugLineNum = 14417925;BA.debugLine="r.target = list1.get(i)";
__ref._r.Target = _list1.Get(_i);
RDebugUtils.currentLine=14417926;
 //BA.debugLineNum = 14417926;BA.debugLine="cs(i).Width = r.GetField(\"width\")";
_cs[_i].Width = (int)(BA.ObjectToNumber(__ref._r.GetField("width")));
RDebugUtils.currentLine=14417927;
 //BA.debugLineNum = 14417927;BA.debugLine="cs(i).Height = r.GetField(\"height\")";
_cs[_i].Height = (int)(BA.ObjectToNumber(__ref._r.GetField("height")));
 }
};
RDebugUtils.currentLine=14417929;
 //BA.debugLineNum = 14417929;BA.debugLine="Return cs";
if (true) return _cs;
RDebugUtils.currentLine=14417930;
 //BA.debugLineNum = 14417930;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedpreviewfpsrange(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getsupportedpreviewfpsrange"))
	 {return ((anywheresoftware.b4a.objects.collections.List) Debug.delegate(ba, "getsupportedpreviewfpsrange", null));}
RDebugUtils.currentLine=14876672;
 //BA.debugLineNum = 14876672;BA.debugLine="Public Sub GetSupportedPreviewFpsRange As List";
RDebugUtils.currentLine=14876673;
 //BA.debugLineNum = 14876673;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14876674;
 //BA.debugLineNum = 14876674;BA.debugLine="Return r.RunMethod(\"getSupportedPreviewFpsRange\")";
if (true) return (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(__ref._r.RunMethod("getSupportedPreviewFpsRange")));
RDebugUtils.currentLine=14876675;
 //BA.debugLineNum = 14876675;BA.debugLine="End Sub";
return null;
}
public cepave.geovin.cameraexclass._camerasize[]  _getsupportedpreviewsizes(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getsupportedpreviewsizes"))
	 {return ((cepave.geovin.cameraexclass._camerasize[]) Debug.delegate(ba, "getsupportedpreviewsizes", null));}
anywheresoftware.b4a.objects.collections.List _list1 = null;
cepave.geovin.cameraexclass._camerasize[] _cs = null;
int _i = 0;
RDebugUtils.currentLine=14286848;
 //BA.debugLineNum = 14286848;BA.debugLine="Public Sub GetSupportedPreviewSizes As CameraSize(";
RDebugUtils.currentLine=14286849;
 //BA.debugLineNum = 14286849;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14286850;
 //BA.debugLineNum = 14286850;BA.debugLine="Dim list1 As List = r.RunMethod(\"getSupportedPrev";
_list1 = new anywheresoftware.b4a.objects.collections.List();
_list1.setObject((java.util.List)(__ref._r.RunMethod("getSupportedPreviewSizes")));
RDebugUtils.currentLine=14286851;
 //BA.debugLineNum = 14286851;BA.debugLine="Dim cs(list1.Size) As CameraSize";
_cs = new cepave.geovin.cameraexclass._camerasize[_list1.getSize()];
{
int d0 = _cs.length;
for (int i0 = 0;i0 < d0;i0++) {
_cs[i0] = new cepave.geovin.cameraexclass._camerasize();
}
}
;
RDebugUtils.currentLine=14286852;
 //BA.debugLineNum = 14286852;BA.debugLine="For i = 0 To list1.Size - 1";
{
final int step4 = 1;
final int limit4 = (int) (_list1.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
RDebugUtils.currentLine=14286853;
 //BA.debugLineNum = 14286853;BA.debugLine="r.target = list1.get(i)";
__ref._r.Target = _list1.Get(_i);
RDebugUtils.currentLine=14286854;
 //BA.debugLineNum = 14286854;BA.debugLine="cs(i).Width = r.GetField(\"width\")";
_cs[_i].Width = (int)(BA.ObjectToNumber(__ref._r.GetField("width")));
RDebugUtils.currentLine=14286855;
 //BA.debugLineNum = 14286855;BA.debugLine="cs(i).Height = r.GetField(\"height\")";
_cs[_i].Height = (int)(BA.ObjectToNumber(__ref._r.GetField("height")));
 }
};
RDebugUtils.currentLine=14286857;
 //BA.debugLineNum = 14286857;BA.debugLine="Return cs";
if (true) return _cs;
RDebugUtils.currentLine=14286858;
 //BA.debugLineNum = 14286858;BA.debugLine="End Sub";
return null;
}
public int  _getzoom(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "getzoom"))
	 {return ((Integer) Debug.delegate(ba, "getzoom", null));}
RDebugUtils.currentLine=15925248;
 //BA.debugLineNum = 15925248;BA.debugLine="Public Sub getZoom() As Int";
RDebugUtils.currentLine=15925249;
 //BA.debugLineNum = 15925249;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15925250;
 //BA.debugLineNum = 15925250;BA.debugLine="Return r.RunMethod(\"getZoom\")";
if (true) return (int)(BA.ObjectToNumber(__ref._r.RunMethod("getZoom")));
RDebugUtils.currentLine=15925251;
 //BA.debugLineNum = 15925251;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(cepave.geovin.cameraexclass __ref,anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.PanelWrapper _panel1,boolean _frontcamera,Object _targetmodule,String _eventname) throws Exception{
__ref = this;
innerInitialize(_ba);
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "initialize"))
	 {return ((String) Debug.delegate(ba, "initialize", new Object[] {_ba,_panel1,_frontcamera,_targetmodule,_eventname}));}
int _id = 0;
RDebugUtils.currentLine=13303808;
 //BA.debugLineNum = 13303808;BA.debugLine="Public Sub Initialize (Panel1 As Panel, FrontCamer";
RDebugUtils.currentLine=13303809;
 //BA.debugLineNum = 13303809;BA.debugLine="target = TargetModule";
__ref._target = _targetmodule;
RDebugUtils.currentLine=13303810;
 //BA.debugLineNum = 13303810;BA.debugLine="event = EventName";
__ref._event = _eventname;
RDebugUtils.currentLine=13303811;
 //BA.debugLineNum = 13303811;BA.debugLine="Front = FrontCamera";
__ref._front = _frontcamera;
RDebugUtils.currentLine=13303812;
 //BA.debugLineNum = 13303812;BA.debugLine="Dim id As Int";
_id = 0;
RDebugUtils.currentLine=13303813;
 //BA.debugLineNum = 13303813;BA.debugLine="id = FindCamera(Front).id";
_id = __ref._findcamera(null,__ref._front).Id;
RDebugUtils.currentLine=13303814;
 //BA.debugLineNum = 13303814;BA.debugLine="If id = -1 Then";
if (_id==-1) { 
RDebugUtils.currentLine=13303815;
 //BA.debugLineNum = 13303815;BA.debugLine="Front = Not(Front) 'try different camera";
__ref._front = __c.Not(__ref._front);
RDebugUtils.currentLine=13303816;
 //BA.debugLineNum = 13303816;BA.debugLine="id = FindCamera(Front).id";
_id = __ref._findcamera(null,__ref._front).Id;
RDebugUtils.currentLine=13303817;
 //BA.debugLineNum = 13303817;BA.debugLine="If id = -1 Then";
if (_id==-1) { 
RDebugUtils.currentLine=13303818;
 //BA.debugLineNum = 13303818;BA.debugLine="ToastMessageShow(\"No camera found.\", True)";
__c.ToastMessageShow(BA.ObjectToCharSequence("No camera found."),__c.True);
RDebugUtils.currentLine=13303819;
 //BA.debugLineNum = 13303819;BA.debugLine="Return";
if (true) return "";
 };
 };
RDebugUtils.currentLine=13303822;
 //BA.debugLineNum = 13303822;BA.debugLine="cam.Initialize2(Panel1, \"camera\", id)";
__ref._cam.Initialize2(ba,(android.view.ViewGroup)(_panel1.getObject()),"camera",_id);
RDebugUtils.currentLine=13303823;
 //BA.debugLineNum = 13303823;BA.debugLine="End Sub";
return "";
}
public boolean  _iszoomsupported(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "iszoomsupported"))
	 {return ((Boolean) Debug.delegate(ba, "iszoomsupported", null));}
RDebugUtils.currentLine=15794176;
 //BA.debugLineNum = 15794176;BA.debugLine="Public Sub IsZoomSupported As Boolean";
RDebugUtils.currentLine=15794177;
 //BA.debugLineNum = 15794177;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15794178;
 //BA.debugLineNum = 15794178;BA.debugLine="Return r.RunMethod(\"isZoomSupported\")";
if (true) return BA.ObjectToBoolean(__ref._r.RunMethod("isZoomSupported"));
RDebugUtils.currentLine=15794179;
 //BA.debugLineNum = 15794179;BA.debugLine="End Sub";
return false;
}
public byte[]  _previewimagetojpeg(cepave.geovin.cameraexclass __ref,byte[] _data,int _quality) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "previewimagetojpeg"))
	 {return ((byte[]) Debug.delegate(ba, "previewimagetojpeg", new Object[] {_data,_quality}));}
Object _size = null;
Object _previewformat = null;
int _width = 0;
int _height = 0;
Object _yuvimage = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rect1 = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
RDebugUtils.currentLine=15204352;
 //BA.debugLineNum = 15204352;BA.debugLine="Public Sub PreviewImageToJpeg(data() As Byte, qual";
RDebugUtils.currentLine=15204353;
 //BA.debugLineNum = 15204353;BA.debugLine="Dim size, previewFormat As Object";
_size = new Object();
_previewformat = new Object();
RDebugUtils.currentLine=15204354;
 //BA.debugLineNum = 15204354;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15204355;
 //BA.debugLineNum = 15204355;BA.debugLine="size = r.RunMethod(\"getPreviewSize\")";
_size = __ref._r.RunMethod("getPreviewSize");
RDebugUtils.currentLine=15204356;
 //BA.debugLineNum = 15204356;BA.debugLine="previewFormat = r.RunMethod(\"getPreviewFormat\")";
_previewformat = __ref._r.RunMethod("getPreviewFormat");
RDebugUtils.currentLine=15204357;
 //BA.debugLineNum = 15204357;BA.debugLine="r.target = size";
__ref._r.Target = _size;
RDebugUtils.currentLine=15204358;
 //BA.debugLineNum = 15204358;BA.debugLine="Dim width = r.GetField(\"width\"), height = r.GetFi";
_width = (int)(BA.ObjectToNumber(__ref._r.GetField("width")));
_height = (int)(BA.ObjectToNumber(__ref._r.GetField("height")));
RDebugUtils.currentLine=15204359;
 //BA.debugLineNum = 15204359;BA.debugLine="Dim yuvImage As Object = r.CreateObject2(\"android";
_yuvimage = __ref._r.CreateObject2("android.graphics.YuvImage",new Object[]{(Object)(_data),_previewformat,(Object)(_width),(Object)(_height),__c.Null},new String[]{"[B","java.lang.int","java.lang.int","java.lang.int","[I"});
RDebugUtils.currentLine=15204362;
 //BA.debugLineNum = 15204362;BA.debugLine="r.target = yuvImage";
__ref._r.Target = _yuvimage;
RDebugUtils.currentLine=15204363;
 //BA.debugLineNum = 15204363;BA.debugLine="Dim rect1 As Rect";
_rect1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
RDebugUtils.currentLine=15204364;
 //BA.debugLineNum = 15204364;BA.debugLine="rect1.Initialize(0, 0, r.RunMethod(\"getWidth\"), r";
_rect1.Initialize((int) (0),(int) (0),(int)(BA.ObjectToNumber(__ref._r.RunMethod("getWidth"))),(int)(BA.ObjectToNumber(__ref._r.RunMethod("getHeight"))));
RDebugUtils.currentLine=15204365;
 //BA.debugLineNum = 15204365;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
RDebugUtils.currentLine=15204366;
 //BA.debugLineNum = 15204366;BA.debugLine="out.InitializeToBytesArray(100)";
_out.InitializeToBytesArray((int) (100));
RDebugUtils.currentLine=15204367;
 //BA.debugLineNum = 15204367;BA.debugLine="r.RunMethod4(\"compressToJpeg\", Array As Object(re";
__ref._r.RunMethod4("compressToJpeg",new Object[]{(Object)(_rect1.getObject()),(Object)(_quality),(Object)(_out.getObject())},new String[]{"android.graphics.Rect","java.lang.int","java.io.OutputStream"});
RDebugUtils.currentLine=15204369;
 //BA.debugLineNum = 15204369;BA.debugLine="Return out.ToBytesArray";
if (true) return _out.ToBytesArray();
RDebugUtils.currentLine=15204370;
 //BA.debugLineNum = 15204370;BA.debugLine="End Sub";
return null;
}
public String  _release(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "release"))
	 {return ((String) Debug.delegate(ba, "release", null));}
RDebugUtils.currentLine=13828096;
 //BA.debugLineNum = 13828096;BA.debugLine="Public Sub Release";
RDebugUtils.currentLine=13828097;
 //BA.debugLineNum = 13828097;BA.debugLine="cam.Release";
__ref._cam.Release();
RDebugUtils.currentLine=13828098;
 //BA.debugLineNum = 13828098;BA.debugLine="End Sub";
return "";
}
public String  _savepicturetofile(cepave.geovin.cameraexclass __ref,byte[] _data,String _dir,String _filename) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "savepicturetofile"))
	 {return ((String) Debug.delegate(ba, "savepicturetofile", new Object[] {_data,_dir,_filename}));}
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
RDebugUtils.currentLine=13893632;
 //BA.debugLineNum = 13893632;BA.debugLine="Public Sub SavePictureToFile(Data() As Byte, Dir A";
RDebugUtils.currentLine=13893633;
 //BA.debugLineNum = 13893633;BA.debugLine="Dim out As OutputStream = File.OpenOutput(Dir, Fi";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
_out = __c.File.OpenOutput(_dir,_filename,__c.False);
RDebugUtils.currentLine=13893634;
 //BA.debugLineNum = 13893634;BA.debugLine="out.WriteBytes(Data, 0, Data.Length)";
_out.WriteBytes(_data,(int) (0),_data.length);
RDebugUtils.currentLine=13893635;
 //BA.debugLineNum = 13893635;BA.debugLine="out.Close";
_out.Close();
RDebugUtils.currentLine=13893636;
 //BA.debugLineNum = 13893636;BA.debugLine="End Sub";
return "";
}
public String  _setcoloreffect(cepave.geovin.cameraexclass __ref,String _effect) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setcoloreffect"))
	 {return ((String) Debug.delegate(ba, "setcoloreffect", new Object[] {_effect}));}
RDebugUtils.currentLine=14221312;
 //BA.debugLineNum = 14221312;BA.debugLine="Public Sub SetColorEffect(Effect As String)";
RDebugUtils.currentLine=14221313;
 //BA.debugLineNum = 14221313;BA.debugLine="SetParameter(\"effect\", Effect)";
__ref._setparameter(null,"effect",_effect);
RDebugUtils.currentLine=14221314;
 //BA.debugLineNum = 14221314;BA.debugLine="End Sub";
return "";
}
public String  _setparameter(cepave.geovin.cameraexclass __ref,String _key,String _value) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setparameter"))
	 {return ((String) Debug.delegate(ba, "setparameter", new Object[] {_key,_value}));}
RDebugUtils.currentLine=13959168;
 //BA.debugLineNum = 13959168;BA.debugLine="Public Sub SetParameter(Key As String, Value As St";
RDebugUtils.currentLine=13959169;
 //BA.debugLineNum = 13959169;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=13959170;
 //BA.debugLineNum = 13959170;BA.debugLine="r.RunMethod3(\"set\", Key, \"java.lang.String\", Valu";
__ref._r.RunMethod3("set",_key,"java.lang.String",_value,"java.lang.String");
RDebugUtils.currentLine=13959171;
 //BA.debugLineNum = 13959171;BA.debugLine="End Sub";
return "";
}
public String  _setcontinuousautofocus(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setcontinuousautofocus"))
	 {return ((String) Debug.delegate(ba, "setcontinuousautofocus", null));}
anywheresoftware.b4a.objects.collections.List _modes = null;
RDebugUtils.currentLine=15335424;
 //BA.debugLineNum = 15335424;BA.debugLine="Public Sub SetContinuousAutoFocus";
RDebugUtils.currentLine=15335425;
 //BA.debugLineNum = 15335425;BA.debugLine="Dim modes As List = GetSupportedFocusModes";
_modes = new anywheresoftware.b4a.objects.collections.List();
_modes = __ref._getsupportedfocusmodes(null);
RDebugUtils.currentLine=15335426;
 //BA.debugLineNum = 15335426;BA.debugLine="If modes.IndexOf(\"continuous-picture\") > -1 Th";
if (_modes.IndexOf((Object)("continuous-picture"))>-1) { 
RDebugUtils.currentLine=15335427;
 //BA.debugLineNum = 15335427;BA.debugLine="SetFocusMode(\"continuous-picture\")";
__ref._setfocusmode(null,"continuous-picture");
 }else 
{RDebugUtils.currentLine=15335428;
 //BA.debugLineNum = 15335428;BA.debugLine="Else If modes.IndexOf(\"continuous-video\") > -1";
if (_modes.IndexOf((Object)("continuous-video"))>-1) { 
RDebugUtils.currentLine=15335429;
 //BA.debugLineNum = 15335429;BA.debugLine="SetFocusMode(\"continuous-video\")";
__ref._setfocusmode(null,"continuous-video");
 }else {
RDebugUtils.currentLine=15335431;
 //BA.debugLineNum = 15335431;BA.debugLine="Log(\"Continuous focus mode is not availabl";
__c.Log("Continuous focus mode is not available");
 }}
;
RDebugUtils.currentLine=15335433;
 //BA.debugLineNum = 15335433;BA.debugLine="End Sub";
return "";
}
public String  _setfocusmode(cepave.geovin.cameraexclass __ref,String _mode) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setfocusmode"))
	 {return ((String) Debug.delegate(ba, "setfocusmode", new Object[] {_mode}));}
RDebugUtils.currentLine=15400960;
 //BA.debugLineNum = 15400960;BA.debugLine="Public Sub SetFocusMode(Mode As String)";
RDebugUtils.currentLine=15400961;
 //BA.debugLineNum = 15400961;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15400962;
 //BA.debugLineNum = 15400962;BA.debugLine="r.RunMethod2(\"setFocusMode\", Mode, \"java.lang.";
__ref._r.RunMethod2("setFocusMode",_mode,"java.lang.String");
RDebugUtils.currentLine=15400963;
 //BA.debugLineNum = 15400963;BA.debugLine="End Sub";
return "";
}
public String  _setexposurecompensation(cepave.geovin.cameraexclass __ref,int _v) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setexposurecompensation"))
	 {return ((String) Debug.delegate(ba, "setexposurecompensation", new Object[] {_v}));}
RDebugUtils.currentLine=16121856;
 //BA.debugLineNum = 16121856;BA.debugLine="Public Sub setExposureCompensation(v As Int)";
RDebugUtils.currentLine=16121857;
 //BA.debugLineNum = 16121857;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=16121858;
 //BA.debugLineNum = 16121858;BA.debugLine="r.RunMethod2(\"setExposureCompensation\", v, \"java.";
__ref._r.RunMethod2("setExposureCompensation",BA.NumberToString(_v),"java.lang.int");
RDebugUtils.currentLine=16121859;
 //BA.debugLineNum = 16121859;BA.debugLine="End Sub";
return "";
}
public String  _setfacedetectionlistener(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setfacedetectionlistener"))
	 {return ((String) Debug.delegate(ba, "setfacedetectionlistener", null));}
anywheresoftware.b4j.object.JavaObject _jo = null;
Object _e = null;
RDebugUtils.currentLine=16318464;
 //BA.debugLineNum = 16318464;BA.debugLine="Public Sub SetFaceDetectionListener";
RDebugUtils.currentLine=16318465;
 //BA.debugLineNum = 16318465;BA.debugLine="Dim jo As JavaObject = nativeCam";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo.setObject((java.lang.Object)(__ref._nativecam));
RDebugUtils.currentLine=16318466;
 //BA.debugLineNum = 16318466;BA.debugLine="Dim e As Object = jo.CreateEvent(\"android.hardwar";
_e = _jo.CreateEvent(ba,"android.hardware.Camera.FaceDetectionListener","FaceDetection",__c.Null);
RDebugUtils.currentLine=16318467;
 //BA.debugLineNum = 16318467;BA.debugLine="jo.RunMethod(\"setFaceDetectionListener\", Array(e)";
_jo.RunMethod("setFaceDetectionListener",new Object[]{_e});
RDebugUtils.currentLine=16318468;
 //BA.debugLineNum = 16318468;BA.debugLine="End Sub";
return "";
}
public String  _setflashmode(cepave.geovin.cameraexclass __ref,String _mode) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setflashmode"))
	 {return ((String) Debug.delegate(ba, "setflashmode", new Object[] {_mode}));}
RDebugUtils.currentLine=14614528;
 //BA.debugLineNum = 14614528;BA.debugLine="Public Sub SetFlashMode(Mode As String)";
RDebugUtils.currentLine=14614529;
 //BA.debugLineNum = 14614529;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14614530;
 //BA.debugLineNum = 14614530;BA.debugLine="r.RunMethod2(\"setFlashMode\", Mode, \"java.lang.Str";
__ref._r.RunMethod2("setFlashMode",_mode,"java.lang.String");
RDebugUtils.currentLine=14614531;
 //BA.debugLineNum = 14614531;BA.debugLine="End Sub";
return "";
}
public String  _setjpegquality(cepave.geovin.cameraexclass __ref,int _quality) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setjpegquality"))
	 {return ((String) Debug.delegate(ba, "setjpegquality", new Object[] {_quality}));}
RDebugUtils.currentLine=14548992;
 //BA.debugLineNum = 14548992;BA.debugLine="Public Sub SetJpegQuality(Quality As Int)";
RDebugUtils.currentLine=14548993;
 //BA.debugLineNum = 14548993;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14548994;
 //BA.debugLineNum = 14548994;BA.debugLine="r.RunMethod2(\"setJpegQuality\", Quality, \"java.lan";
__ref._r.RunMethod2("setJpegQuality",BA.NumberToString(_quality),"java.lang.int");
RDebugUtils.currentLine=14548995;
 //BA.debugLineNum = 14548995;BA.debugLine="End Sub";
return "";
}
public String  _setpicturesize(cepave.geovin.cameraexclass __ref,int _width,int _height) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setpicturesize"))
	 {return ((String) Debug.delegate(ba, "setpicturesize", new Object[] {_width,_height}));}
RDebugUtils.currentLine=14483456;
 //BA.debugLineNum = 14483456;BA.debugLine="Public Sub SetPictureSize(Width As Int, Height As";
RDebugUtils.currentLine=14483457;
 //BA.debugLineNum = 14483457;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14483458;
 //BA.debugLineNum = 14483458;BA.debugLine="r.RunMethod3(\"setPictureSize\", Width, \"java.lang.";
__ref._r.RunMethod3("setPictureSize",BA.NumberToString(_width),"java.lang.int",BA.NumberToString(_height),"java.lang.int");
RDebugUtils.currentLine=14483459;
 //BA.debugLineNum = 14483459;BA.debugLine="End Sub";
return "";
}
public String  _setpreviewfpsrange(cepave.geovin.cameraexclass __ref,int _minvalue,int _maxvalue) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setpreviewfpsrange"))
	 {return ((String) Debug.delegate(ba, "setpreviewfpsrange", new Object[] {_minvalue,_maxvalue}));}
RDebugUtils.currentLine=15007744;
 //BA.debugLineNum = 15007744;BA.debugLine="Public Sub SetPreviewFpsRange(MinValue As Int, Max";
RDebugUtils.currentLine=15007745;
 //BA.debugLineNum = 15007745;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15007746;
 //BA.debugLineNum = 15007746;BA.debugLine="r.RunMethod4(\"setPreviewFpsRange\", Array As Objec";
__ref._r.RunMethod4("setPreviewFpsRange",new Object[]{(Object)(_minvalue),(Object)(_maxvalue)},new String[]{"java.lang.int","java.lang.int"});
RDebugUtils.currentLine=15007748;
 //BA.debugLineNum = 15007748;BA.debugLine="End Sub";
return "";
}
public String  _setpreviewsize(cepave.geovin.cameraexclass __ref,int _width,int _height) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setpreviewsize"))
	 {return ((String) Debug.delegate(ba, "setpreviewsize", new Object[] {_width,_height}));}
RDebugUtils.currentLine=14352384;
 //BA.debugLineNum = 14352384;BA.debugLine="Public Sub SetPreviewSize(Width As Int, Height As";
RDebugUtils.currentLine=14352385;
 //BA.debugLineNum = 14352385;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=14352386;
 //BA.debugLineNum = 14352386;BA.debugLine="r.RunMethod3(\"setPreviewSize\", Width, \"java.lang.";
__ref._r.RunMethod3("setPreviewSize",BA.NumberToString(_width),"java.lang.int",BA.NumberToString(_height),"java.lang.int");
RDebugUtils.currentLine=14352387;
 //BA.debugLineNum = 14352387;BA.debugLine="End Sub";
return "";
}
public String  _setzoom(cepave.geovin.cameraexclass __ref,int _zoomvalue) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "setzoom"))
	 {return ((String) Debug.delegate(ba, "setzoom", new Object[] {_zoomvalue}));}
RDebugUtils.currentLine=15990784;
 //BA.debugLineNum = 15990784;BA.debugLine="Public Sub setZoom(ZoomValue As Int)";
RDebugUtils.currentLine=15990785;
 //BA.debugLineNum = 15990785;BA.debugLine="r.target = parameters";
__ref._r.Target = __ref._parameters;
RDebugUtils.currentLine=15990786;
 //BA.debugLineNum = 15990786;BA.debugLine="r.RunMethod2(\"setZoom\", ZoomValue, \"java.lang.in";
__ref._r.RunMethod2("setZoom",BA.NumberToString(_zoomvalue),"java.lang.int");
RDebugUtils.currentLine=15990787;
 //BA.debugLineNum = 15990787;BA.debugLine="End Sub";
return "";
}
public String  _startfacedetection(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "startfacedetection"))
	 {return ((String) Debug.delegate(ba, "startfacedetection", null));}
anywheresoftware.b4j.object.JavaObject _jo = null;
RDebugUtils.currentLine=16449536;
 //BA.debugLineNum = 16449536;BA.debugLine="Public Sub StartFaceDetection";
RDebugUtils.currentLine=16449537;
 //BA.debugLineNum = 16449537;BA.debugLine="Dim jo As JavaObject = nativeCam";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo.setObject((java.lang.Object)(__ref._nativecam));
RDebugUtils.currentLine=16449538;
 //BA.debugLineNum = 16449538;BA.debugLine="jo.RunMethod(\"startFaceDetection\", Null)";
_jo.RunMethod("startFaceDetection",(Object[])(__c.Null));
RDebugUtils.currentLine=16449539;
 //BA.debugLineNum = 16449539;BA.debugLine="End Sub";
return "";
}
public String  _startpreview(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "startpreview"))
	 {return ((String) Debug.delegate(ba, "startpreview", null));}
RDebugUtils.currentLine=13697024;
 //BA.debugLineNum = 13697024;BA.debugLine="Public Sub StartPreview";
RDebugUtils.currentLine=13697025;
 //BA.debugLineNum = 13697025;BA.debugLine="cam.StartPreview";
__ref._cam.StartPreview();
RDebugUtils.currentLine=13697026;
 //BA.debugLineNum = 13697026;BA.debugLine="End Sub";
return "";
}
public String  _stopfacedetection(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "stopfacedetection"))
	 {return ((String) Debug.delegate(ba, "stopfacedetection", null));}
anywheresoftware.b4j.object.JavaObject _jo = null;
RDebugUtils.currentLine=16515072;
 //BA.debugLineNum = 16515072;BA.debugLine="Public Sub StopFaceDetection";
RDebugUtils.currentLine=16515073;
 //BA.debugLineNum = 16515073;BA.debugLine="Dim jo As JavaObject = nativeCam";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo.setObject((java.lang.Object)(__ref._nativecam));
RDebugUtils.currentLine=16515074;
 //BA.debugLineNum = 16515074;BA.debugLine="jo.RunMethod(\"stopFaceDetection\", Null)";
_jo.RunMethod("stopFaceDetection",(Object[])(__c.Null));
RDebugUtils.currentLine=16515075;
 //BA.debugLineNum = 16515075;BA.debugLine="End Sub";
return "";
}
public String  _stoppreview(cepave.geovin.cameraexclass __ref) throws Exception{
__ref = this;
RDebugUtils.currentModule="cameraexclass";
if (Debug.shouldDelegate(ba, "stoppreview"))
	 {return ((String) Debug.delegate(ba, "stoppreview", null));}
RDebugUtils.currentLine=13762560;
 //BA.debugLineNum = 13762560;BA.debugLine="Public Sub StopPreview";
RDebugUtils.currentLine=13762561;
 //BA.debugLineNum = 13762561;BA.debugLine="cam.StopPreview";
__ref._cam.StopPreview();
RDebugUtils.currentLine=13762562;
 //BA.debugLineNum = 13762562;BA.debugLine="End Sub";
return "";
}
}