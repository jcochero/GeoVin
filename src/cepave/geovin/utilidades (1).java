package cepave.geovin;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class utilidades {
private static utilidades mostCurrent = new utilidades();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper _hc = null;
public static anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
public static int _numfotosenviar = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public cepave.geovin.main _main = null;
public cepave.geovin.frmaprender _frmaprender = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.frmlocalizacion _frmlocalizacion = null;
public cepave.geovin.frmdatosanteriores _frmdatosanteriores = null;
public cepave.geovin.frmperfil _frmperfil = null;
public cepave.geovin.dbutils _dbutils = null;
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
public static String  _getdeviceid(anywheresoftware.b4a.BA _ba) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "getdeviceid"))
	 {return ((String) Debug.delegate(null, "getdeviceid", new Object[] {_ba}));}
RDebugUtils.currentLine=10420224;
 //BA.debugLineNum = 10420224;BA.debugLine="Sub GetDeviceId As String";
RDebugUtils.currentLine=10420244;
 //BA.debugLineNum = 10420244;BA.debugLine="Return FirebaseMessaging.fm.Token";
if (true) return mostCurrent._firebasemessaging._fm.getToken();
RDebugUtils.currentLine=10420245;
 //BA.debugLineNum = 10420245;BA.debugLine="Log(FirebaseMessaging.fm.Token)";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._firebasemessaging._fm.getToken());
RDebugUtils.currentLine=10420247;
 //BA.debugLineNum = 10420247;BA.debugLine="End Sub";
return "";
}
public static String  _mensaje(anywheresoftware.b4a.BA _ba,String _titulo,String _imagen,String _text,String _textsub,String _botontextoyes,String _botontextocan,String _botontextono,boolean _textolargo) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "mensaje"))
	 {return ((String) Debug.delegate(null, "mensaje", new Object[] {_ba,_titulo,_imagen,_text,_textsub,_botontextoyes,_botontextocan,_botontextono,_textolargo}));}
flm.b4a.betterdialogs.BetterDialogs _dial = null;
anywheresoftware.b4a.objects.ImageViewWrapper _msgbottomimg = null;
anywheresoftware.b4a.objects.PanelWrapper _panelcontent = null;
anywheresoftware.b4a.objects.LabelWrapper _titulolbl = null;
anywheresoftware.b4a.objects.ImageViewWrapper _msgtopimg = null;
anywheresoftware.b4a.objects.LabelWrapper _contenido = null;
anywheresoftware.b4a.objects.LabelWrapper _textosub = null;
String _msg = "";
anywheresoftware.b4a.objects.ImageViewWrapper _imgbitmap = null;
RDebugUtils.currentLine=10354688;
 //BA.debugLineNum = 10354688;BA.debugLine="Sub Mensaje (titulo As String, imagen As String, t";
RDebugUtils.currentLine=10354689;
 //BA.debugLineNum = 10354689;BA.debugLine="Dim dial As BetterDialogs";
_dial = new flm.b4a.betterdialogs.BetterDialogs();
RDebugUtils.currentLine=10354690;
 //BA.debugLineNum = 10354690;BA.debugLine="Dim MsgBottomImg As ImageView";
_msgbottomimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=10354691;
 //BA.debugLineNum = 10354691;BA.debugLine="Dim panelcontent As Panel";
_panelcontent = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=10354692;
 //BA.debugLineNum = 10354692;BA.debugLine="Dim titulolbl As Label";
_titulolbl = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=10354693;
 //BA.debugLineNum = 10354693;BA.debugLine="Dim MsgTopImg As ImageView";
_msgtopimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=10354694;
 //BA.debugLineNum = 10354694;BA.debugLine="Dim contenido As Label";
_contenido = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=10354695;
 //BA.debugLineNum = 10354695;BA.debugLine="Dim textoSub As Label";
_textosub = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=10354696;
 //BA.debugLineNum = 10354696;BA.debugLine="Dim msg As String";
_msg = "";
RDebugUtils.currentLine=10354698;
 //BA.debugLineNum = 10354698;BA.debugLine="MsgBottomImg.Initialize(\"\")";
_msgbottomimg.Initialize(_ba,"");
RDebugUtils.currentLine=10354699;
 //BA.debugLineNum = 10354699;BA.debugLine="MsgTopImg.Initialize(\"\")";
_msgtopimg.Initialize(_ba,"");
RDebugUtils.currentLine=10354700;
 //BA.debugLineNum = 10354700;BA.debugLine="panelcontent.Initialize(\"\")";
_panelcontent.Initialize(_ba,"");
RDebugUtils.currentLine=10354701;
 //BA.debugLineNum = 10354701;BA.debugLine="titulolbl.Initialize(\"\")";
_titulolbl.Initialize(_ba,"");
RDebugUtils.currentLine=10354702;
 //BA.debugLineNum = 10354702;BA.debugLine="contenido.Initialize(\"\")";
_contenido.Initialize(_ba,"");
RDebugUtils.currentLine=10354703;
 //BA.debugLineNum = 10354703;BA.debugLine="textoSub.Initialize(\"\")";
_textosub.Initialize(_ba,"");
RDebugUtils.currentLine=10354707;
 //BA.debugLineNum = 10354707;BA.debugLine="MsgTopImg.Gravity = Gravity.FILL";
_msgtopimg.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=10354708;
 //BA.debugLineNum = 10354708;BA.debugLine="MsgBottomImg.Gravity = Gravity.FILL";
_msgbottomimg.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=10354710;
 //BA.debugLineNum = 10354710;BA.debugLine="titulolbl.Text = titulo";
_titulolbl.setText(BA.ObjectToCharSequence(_titulo));
RDebugUtils.currentLine=10354711;
 //BA.debugLineNum = 10354711;BA.debugLine="titulolbl.TextColor = Colors.Black";
_titulolbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=10354712;
 //BA.debugLineNum = 10354712;BA.debugLine="titulolbl.Gravity = Gravity.CENTER_HORIZONTAL";
_titulolbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
RDebugUtils.currentLine=10354713;
 //BA.debugLineNum = 10354713;BA.debugLine="titulolbl.TextSize = 22";
_titulolbl.setTextSize((float) (22));
RDebugUtils.currentLine=10354714;
 //BA.debugLineNum = 10354714;BA.debugLine="contenido.Text = text";
_contenido.setText(BA.ObjectToCharSequence(_text));
RDebugUtils.currentLine=10354715;
 //BA.debugLineNum = 10354715;BA.debugLine="contenido.TextColor = Colors.Black";
_contenido.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=10354716;
 //BA.debugLineNum = 10354716;BA.debugLine="contenido.TextSize = 18";
_contenido.setTextSize((float) (18));
RDebugUtils.currentLine=10354717;
 //BA.debugLineNum = 10354717;BA.debugLine="contenido.Gravity = Gravity.CENTER_HORIZONTAL";
_contenido.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
RDebugUtils.currentLine=10354718;
 //BA.debugLineNum = 10354718;BA.debugLine="contenido.Color = Colors.white";
_contenido.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=10354719;
 //BA.debugLineNum = 10354719;BA.debugLine="textoSub.Text = textsub";
_textosub.setText(BA.ObjectToCharSequence(_textsub));
RDebugUtils.currentLine=10354720;
 //BA.debugLineNum = 10354720;BA.debugLine="textoSub.TextColor = Colors.DarkGray";
_textosub.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
RDebugUtils.currentLine=10354721;
 //BA.debugLineNum = 10354721;BA.debugLine="textoSub.TextSize = 16";
_textosub.setTextSize((float) (16));
RDebugUtils.currentLine=10354722;
 //BA.debugLineNum = 10354722;BA.debugLine="textoSub.Gravity = Gravity.CENTER_HORIZONTAL";
_textosub.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
RDebugUtils.currentLine=10354723;
 //BA.debugLineNum = 10354723;BA.debugLine="textoSub.Color = Colors.white";
_textosub.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=10354724;
 //BA.debugLineNum = 10354724;BA.debugLine="panelcontent.Color = Colors.white";
_panelcontent.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=10354727;
 //BA.debugLineNum = 10354727;BA.debugLine="Dim imgbitmap As ImageView";
_imgbitmap = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=10354728;
 //BA.debugLineNum = 10354728;BA.debugLine="imgbitmap.Initialize(\"\")";
_imgbitmap.Initialize(_ba,"");
RDebugUtils.currentLine=10354729;
 //BA.debugLineNum = 10354729;BA.debugLine="If imagen <> Null Then";
if (_imagen!= null) { 
RDebugUtils.currentLine=10354730;
 //BA.debugLineNum = 10354730;BA.debugLine="imgbitmap.Bitmap =  LoadBitmapSample(File.DirAss";
_imgbitmap.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_imagen,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba)).getObject()));
 }else {
RDebugUtils.currentLine=10354732;
 //BA.debugLineNum = 10354732;BA.debugLine="imgbitmap.Bitmap =  LoadBitmapSample(File.DirAss";
_imgbitmap.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"MsgIcon.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba)).getObject()));
 };
RDebugUtils.currentLine=10354736;
 //BA.debugLineNum = 10354736;BA.debugLine="panelcontent.AddView(MsgTopImg, 0, 0,90%x, 60dip)";
_panelcontent.AddView((android.view.View)(_msgtopimg.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
RDebugUtils.currentLine=10354737;
 //BA.debugLineNum = 10354737;BA.debugLine="panelcontent.AddView(imgbitmap, 5%x, 3%y, 80%x, 3";
_panelcontent.AddView((android.view.View)(_imgbitmap.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),_ba));
RDebugUtils.currentLine=10354738;
 //BA.debugLineNum = 10354738;BA.debugLine="panelcontent.AddView(titulolbl, 0, 35%y, 90%x, 60";
_panelcontent.AddView((android.view.View)(_titulolbl.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
RDebugUtils.currentLine=10354739;
 //BA.debugLineNum = 10354739;BA.debugLine="panelcontent.AddView(contenido, 5%x, titulolbl.To";
_panelcontent.AddView((android.view.View)(_contenido.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_titulolbl.getTop()+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),_ba)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),_ba));
RDebugUtils.currentLine=10354743;
 //BA.debugLineNum = 10354743;BA.debugLine="If textolargo = True Then";
if (_textolargo==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=10354744;
 //BA.debugLineNum = 10354744;BA.debugLine="panelcontent.AddView(textoSub, 5%x, contenido.T";
_panelcontent.AddView((android.view.View)(_textosub.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_contenido.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),_ba));
 }else {
RDebugUtils.currentLine=10354746;
 //BA.debugLineNum = 10354746;BA.debugLine="panelcontent.AddView(textoSub, 5%x, contenido.T";
_panelcontent.AddView((android.view.View)(_textosub.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_contenido.getTop()+_contenido.getHeight()+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),_ba)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),_ba));
 };
RDebugUtils.currentLine=10354749;
 //BA.debugLineNum = 10354749;BA.debugLine="panelcontent.AddView(MsgBottomImg, 0, 350dip, 90%";
_panelcontent.AddView((android.view.View)(_msgbottomimg.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
RDebugUtils.currentLine=10354751;
 //BA.debugLineNum = 10354751;BA.debugLine="msg = dial.CustomDialog(\"\", 90%x, 60dip, panelcon";
_msg = BA.NumberToString(_dial.CustomDialog((Object)(""),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)),(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_panelcontent.getObject())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),anywheresoftware.b4a.keywords.Common.Null,(Object)(_botontextoyes),(Object)(_botontextocan),(Object)(_botontextono),anywheresoftware.b4a.keywords.Common.False,"",_ba));
RDebugUtils.currentLine=10354752;
 //BA.debugLineNum = 10354752;BA.debugLine="Return msg";
if (true) return _msg;
RDebugUtils.currentLine=10354753;
 //BA.debugLineNum = 10354753;BA.debugLine="End Sub";
return "";
}
public static String  _comenzarenvio(anywheresoftware.b4a.BA _ba,String _evaluacionpath) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "comenzarenvio"))
	 {return ((String) Debug.delegate(null, "comenzarenvio", new Object[] {_ba,_evaluacionpath}));}
anywheresoftware.b4a.objects.collections.List _files = null;
cepave.geovin.uploadfiles._filedata _fd = null;
anywheresoftware.b4a.objects.collections.Map _nv = null;
anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest _req = null;
RDebugUtils.currentLine=10616832;
 //BA.debugLineNum = 10616832;BA.debugLine="Sub ComenzarEnvio(evaluacionpath As String)";
RDebugUtils.currentLine=10616833;
 //BA.debugLineNum = 10616833;BA.debugLine="If hc.IsInitialized = False Then";
if (_hc.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=10616834;
 //BA.debugLineNum = 10616834;BA.debugLine="hc.Initialize(\"hc\")";
_hc.Initialize("hc");
 };
RDebugUtils.currentLine=10616838;
 //BA.debugLineNum = 10616838;BA.debugLine="numfotosenviar = 0";
_numfotosenviar = (int) (0);
RDebugUtils.currentLine=10616839;
 //BA.debugLineNum = 10616839;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
RDebugUtils.currentLine=10616840;
 //BA.debugLineNum = 10616840;BA.debugLine="numfotosenviar = 1";
_numfotosenviar = (int) (1);
 };
RDebugUtils.currentLine=10616843;
 //BA.debugLineNum = 10616843;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
RDebugUtils.currentLine=10616844;
 //BA.debugLineNum = 10616844;BA.debugLine="numfotosenviar = 2";
_numfotosenviar = (int) (2);
 };
RDebugUtils.currentLine=10616847;
 //BA.debugLineNum = 10616847;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
RDebugUtils.currentLine=10616848;
 //BA.debugLineNum = 10616848;BA.debugLine="numfotosenviar = 3";
_numfotosenviar = (int) (3);
 };
RDebugUtils.currentLine=10616851;
 //BA.debugLineNum = 10616851;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
RDebugUtils.currentLine=10616852;
 //BA.debugLineNum = 10616852;BA.debugLine="numfotosenviar = 4";
_numfotosenviar = (int) (4);
 };
RDebugUtils.currentLine=10616870;
 //BA.debugLineNum = 10616870;BA.debugLine="Dim files As List";
_files = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=10616871;
 //BA.debugLineNum = 10616871;BA.debugLine="files.Initialize";
_files.Initialize();
RDebugUtils.currentLine=10616874;
 //BA.debugLineNum = 10616874;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=10616875;
 //BA.debugLineNum = 10616875;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=10616876;
 //BA.debugLineNum = 10616876;BA.debugLine="fd.Dir = Main.savedir & \"/GeoVin/\"";
_fd.Dir = mostCurrent._main._savedir+"/GeoVin/";
RDebugUtils.currentLine=10616877;
 //BA.debugLineNum = 10616877;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=10616878;
 //BA.debugLineNum = 10616878;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=10616883;
 //BA.debugLineNum = 10616883;BA.debugLine="Dim NV As Map";
_nv = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=10616884;
 //BA.debugLineNum = 10616884;BA.debugLine="NV.Initialize";
_nv.Initialize();
RDebugUtils.currentLine=10616885;
 //BA.debugLineNum = 10616885;BA.debugLine="NV.Put(\"usuario\", Main.username)";
_nv.Put((Object)("usuario"),(Object)(mostCurrent._main._username));
RDebugUtils.currentLine=10616886;
 //BA.debugLineNum = 10616886;BA.debugLine="NV.Put(\"eval\", Main.evaluacionpath)";
_nv.Put((Object)("eval"),(Object)(mostCurrent._main._evaluacionpath));
RDebugUtils.currentLine=10616887;
 //BA.debugLineNum = 10616887;BA.debugLine="NV.Put(\"action\", \"upload\")";
_nv.Put((Object)("action"),(Object)("upload"));
RDebugUtils.currentLine=10616888;
 //BA.debugLineNum = 10616888;BA.debugLine="NV.Put(\"usr\", \"juacochero\")";
_nv.Put((Object)("usr"),(Object)("juacochero"));
RDebugUtils.currentLine=10616889;
 //BA.debugLineNum = 10616889;BA.debugLine="NV.Put(\"pss\", \"vacagorda\")";
_nv.Put((Object)("pss"),(Object)("vacagorda"));
RDebugUtils.currentLine=10616890;
 //BA.debugLineNum = 10616890;BA.debugLine="NV.Put(\"deviceID\", Main.deviceID)";
_nv.Put((Object)("deviceID"),(Object)(mostCurrent._main._deviceid));
RDebugUtils.currentLine=10616892;
 //BA.debugLineNum = 10616892;BA.debugLine="Dim req As OkHttpRequest";
_req = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
RDebugUtils.currentLine=10616897;
 //BA.debugLineNum = 10616897;BA.debugLine="If Main.fotopath0  <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
RDebugUtils.currentLine=10616898;
 //BA.debugLineNum = 10616898;BA.debugLine="fd.FileName = Main.fotopath0 & \".jpg\"";
_fd.FileName = mostCurrent._main._fotopath0+".jpg";
RDebugUtils.currentLine=10616899;
 //BA.debugLineNum = 10616899;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=10616900;
 //BA.debugLineNum = 10616900;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=10616901;
 //BA.debugLineNum = 10616901;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = mostCurrent._uploadfiles._createpostrequest(_ba,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=10616902;
 //BA.debugLineNum = 10616902;BA.debugLine="hc.Execute(req, 2)";
_hc.Execute((_ba.processBA == null ? _ba : _ba.processBA),_req,(int) (2));
 };
RDebugUtils.currentLine=10616904;
 //BA.debugLineNum = 10616904;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
RDebugUtils.currentLine=10616905;
 //BA.debugLineNum = 10616905;BA.debugLine="fd.FileName = Main.fotopath1 & \".jpg\"";
_fd.FileName = mostCurrent._main._fotopath1+".jpg";
RDebugUtils.currentLine=10616906;
 //BA.debugLineNum = 10616906;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=10616907;
 //BA.debugLineNum = 10616907;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=10616908;
 //BA.debugLineNum = 10616908;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = mostCurrent._uploadfiles._createpostrequest(_ba,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=10616909;
 //BA.debugLineNum = 10616909;BA.debugLine="hc.Execute(req, 3)";
_hc.Execute((_ba.processBA == null ? _ba : _ba.processBA),_req,(int) (3));
 };
RDebugUtils.currentLine=10616911;
 //BA.debugLineNum = 10616911;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
RDebugUtils.currentLine=10616912;
 //BA.debugLineNum = 10616912;BA.debugLine="fd.FileName = Main.fotopath2 & \".jpg\"";
_fd.FileName = mostCurrent._main._fotopath2+".jpg";
RDebugUtils.currentLine=10616913;
 //BA.debugLineNum = 10616913;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=10616914;
 //BA.debugLineNum = 10616914;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=10616915;
 //BA.debugLineNum = 10616915;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = mostCurrent._uploadfiles._createpostrequest(_ba,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=10616916;
 //BA.debugLineNum = 10616916;BA.debugLine="hc.Execute(req, 4)";
_hc.Execute((_ba.processBA == null ? _ba : _ba.processBA),_req,(int) (4));
 };
RDebugUtils.currentLine=10616918;
 //BA.debugLineNum = 10616918;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
RDebugUtils.currentLine=10616919;
 //BA.debugLineNum = 10616919;BA.debugLine="fd.FileName = Main.fotopath3 & \".jpg\"";
_fd.FileName = mostCurrent._main._fotopath3+".jpg";
RDebugUtils.currentLine=10616920;
 //BA.debugLineNum = 10616920;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=10616921;
 //BA.debugLineNum = 10616921;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=10616922;
 //BA.debugLineNum = 10616922;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.serverP";
_req = mostCurrent._uploadfiles._createpostrequest(_ba,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=10616923;
 //BA.debugLineNum = 10616923;BA.debugLine="hc.Execute(req, 5)";
_hc.Execute((_ba.processBA == null ? _ba : _ba.processBA),_req,(int) (5));
 };
RDebugUtils.currentLine=10616926;
 //BA.debugLineNum = 10616926;BA.debugLine="End Sub";
return "";
}
public static String  _comenzarenviodb(anywheresoftware.b4a.BA _ba,String _evaluacionid) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "comenzarenviodb"))
	 {return ((String) Debug.delegate(null, "comenzarenviodb", new Object[] {_ba,_evaluacionid}));}
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
int _i = 0;
String _archivonotas = "";
anywheresoftware.b4a.objects.collections.List _files = null;
anywheresoftware.b4a.objects.collections.Map _nv = null;
cepave.geovin.uploadfiles._filedata _fd = null;
anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest _req = null;
RDebugUtils.currentLine=10551296;
 //BA.debugLineNum = 10551296;BA.debugLine="Sub ComenzarEnvioDB(evaluacionID As String)";
RDebugUtils.currentLine=10551297;
 //BA.debugLineNum = 10551297;BA.debugLine="If hc.IsInitialized = False Then";
if (_hc.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=10551298;
 //BA.debugLineNum = 10551298;BA.debugLine="hc.Initialize(\"hc\")";
_hc.Initialize("hc");
 };
RDebugUtils.currentLine=10551302;
 //BA.debugLineNum = 10551302;BA.debugLine="Dim Cursor1 As Cursor";
_cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
RDebugUtils.currentLine=10551303;
 //BA.debugLineNum = 10551303;BA.debugLine="Cursor1 = Starter.SQLdb.ExecQuery(\"SELECT * FROM";
_cursor1.setObject((android.database.Cursor)(mostCurrent._starter._sqldb.ExecQuery("SELECT * FROM markers_local WHERE id='"+_evaluacionid+"'")));
RDebugUtils.currentLine=10551304;
 //BA.debugLineNum = 10551304;BA.debugLine="For i = 0 To Cursor1.RowCount - 1";
{
final int step6 = 1;
final int limit6 = (int) (_cursor1.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
RDebugUtils.currentLine=10551305;
 //BA.debugLineNum = 10551305;BA.debugLine="Cursor1.Position = i";
_cursor1.setPosition(_i);
RDebugUtils.currentLine=10551309;
 //BA.debugLineNum = 10551309;BA.debugLine="numfotosenviar = 0";
_numfotosenviar = (int) (0);
RDebugUtils.currentLine=10551310;
 //BA.debugLineNum = 10551310;BA.debugLine="If Cursor1.GetString(\"foto1\") <> \"\"  And Cursor1";
if ((_cursor1.GetString("foto1")).equals("") == false && _cursor1.GetString("foto1")!= null) { 
RDebugUtils.currentLine=10551311;
 //BA.debugLineNum = 10551311;BA.debugLine="numfotosenviar = 1";
_numfotosenviar = (int) (1);
 };
RDebugUtils.currentLine=10551314;
 //BA.debugLineNum = 10551314;BA.debugLine="If Cursor1.GetString(\"foto2\") <> \"\"  And Cursor1";
if ((_cursor1.GetString("foto2")).equals("") == false && _cursor1.GetString("foto2")!= null) { 
RDebugUtils.currentLine=10551315;
 //BA.debugLineNum = 10551315;BA.debugLine="numfotosenviar = 2";
_numfotosenviar = (int) (2);
RDebugUtils.currentLine=10551316;
 //BA.debugLineNum = 10551316;BA.debugLine="Msgbox(Cursor1.GetString(\"foto2\"),\"\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_cursor1.GetString("foto2")),BA.ObjectToCharSequence(""),_ba);
 };
RDebugUtils.currentLine=10551319;
 //BA.debugLineNum = 10551319;BA.debugLine="If Cursor1.GetString(\"foto3\") <> \"\" And Cursor1.";
if ((_cursor1.GetString("foto3")).equals("") == false && _cursor1.GetString("foto3")!= null) { 
RDebugUtils.currentLine=10551320;
 //BA.debugLineNum = 10551320;BA.debugLine="numfotosenviar = 3";
_numfotosenviar = (int) (3);
 };
RDebugUtils.currentLine=10551323;
 //BA.debugLineNum = 10551323;BA.debugLine="If Cursor1.GetString(\"foto4\") <> \"\" And Cursor1.";
if ((_cursor1.GetString("foto4")).equals("") == false && _cursor1.GetString("foto4")!= null) { 
RDebugUtils.currentLine=10551324;
 //BA.debugLineNum = 10551324;BA.debugLine="numfotosenviar = 4";
_numfotosenviar = (int) (4);
 };
RDebugUtils.currentLine=10551328;
 //BA.debugLineNum = 10551328;BA.debugLine="Dim archivonotas As String = Cursor1.GetString(\"";
_archivonotas = _cursor1.GetString("notas");
RDebugUtils.currentLine=10551331;
 //BA.debugLineNum = 10551331;BA.debugLine="Dim files As List";
_files = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=10551332;
 //BA.debugLineNum = 10551332;BA.debugLine="files.Initialize";
_files.Initialize();
RDebugUtils.currentLine=10551336;
 //BA.debugLineNum = 10551336;BA.debugLine="Dim NV As Map";
_nv = new anywheresoftware.b4a.objects.collections.Map();
RDebugUtils.currentLine=10551337;
 //BA.debugLineNum = 10551337;BA.debugLine="NV.Initialize";
_nv.Initialize();
RDebugUtils.currentLine=10551338;
 //BA.debugLineNum = 10551338;BA.debugLine="NV.Put(\"usuario\", Main.username)";
_nv.Put((Object)("usuario"),(Object)(mostCurrent._main._username));
RDebugUtils.currentLine=10551339;
 //BA.debugLineNum = 10551339;BA.debugLine="NV.Put(\"eval\", Cursor1.GetString(\"tipoeval\"))";
_nv.Put((Object)("eval"),(Object)(_cursor1.GetString("tipoeval")));
RDebugUtils.currentLine=10551340;
 //BA.debugLineNum = 10551340;BA.debugLine="NV.Put(\"action\", \"upload\")";
_nv.Put((Object)("action"),(Object)("upload"));
RDebugUtils.currentLine=10551341;
 //BA.debugLineNum = 10551341;BA.debugLine="NV.Put(\"usr\", \"juacochero\")";
_nv.Put((Object)("usr"),(Object)("juacochero"));
RDebugUtils.currentLine=10551342;
 //BA.debugLineNum = 10551342;BA.debugLine="NV.Put(\"pss\", \"vacagorda\")";
_nv.Put((Object)("pss"),(Object)("vacagorda"));
RDebugUtils.currentLine=10551343;
 //BA.debugLineNum = 10551343;BA.debugLine="NV.Put(\"deviceID\", Main.deviceID)";
_nv.Put((Object)("deviceID"),(Object)(mostCurrent._main._deviceid));
RDebugUtils.currentLine=10551346;
 //BA.debugLineNum = 10551346;BA.debugLine="Dim fd As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=10551347;
 //BA.debugLineNum = 10551347;BA.debugLine="Dim req As OkHttpRequest";
_req = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
RDebugUtils.currentLine=10551349;
 //BA.debugLineNum = 10551349;BA.debugLine="fd.Initialize";
_fd.Initialize();
RDebugUtils.currentLine=10551350;
 //BA.debugLineNum = 10551350;BA.debugLine="fd.Dir = Main.savedir & \"/\" & Main.subFolder & \"";
_fd.Dir = mostCurrent._main._savedir+"/"+mostCurrent._main._subfolder+"/";
RDebugUtils.currentLine=10551351;
 //BA.debugLineNum = 10551351;BA.debugLine="fd.KeyName = \"evaluacion\"";
_fd.KeyName = "evaluacion";
RDebugUtils.currentLine=10551352;
 //BA.debugLineNum = 10551352;BA.debugLine="fd.ContentType = \"application/octet-stream\"";
_fd.ContentType = "application/octet-stream";
RDebugUtils.currentLine=10551354;
 //BA.debugLineNum = 10551354;BA.debugLine="If Cursor1.GetString(\"foto1\") <> \"\"  And Cursor1";
if ((_cursor1.GetString("foto1")).equals("") == false && _cursor1.GetString("foto1")!= null) { 
RDebugUtils.currentLine=10551355;
 //BA.debugLineNum = 10551355;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=10551356;
 //BA.debugLineNum = 10551356;BA.debugLine="fd.FileName = Cursor1.GetString(\"foto1\")";
_fd.FileName = _cursor1.GetString("foto1");
RDebugUtils.currentLine=10551357;
 //BA.debugLineNum = 10551357;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=10551358;
 //BA.debugLineNum = 10551358;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.Server";
_req = mostCurrent._uploadfiles._createpostrequest(_ba,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=10551359;
 //BA.debugLineNum = 10551359;BA.debugLine="hc.Execute(req, 1)";
_hc.Execute((_ba.processBA == null ? _ba : _ba.processBA),_req,(int) (1));
 };
RDebugUtils.currentLine=10551361;
 //BA.debugLineNum = 10551361;BA.debugLine="If Cursor1.GetString(\"foto2\") <> \"\"  And Cursor1";
if ((_cursor1.GetString("foto2")).equals("") == false && _cursor1.GetString("foto2")!= null) { 
RDebugUtils.currentLine=10551362;
 //BA.debugLineNum = 10551362;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=10551363;
 //BA.debugLineNum = 10551363;BA.debugLine="fd.FileName = Cursor1.GetString(\"foto2\")";
_fd.FileName = _cursor1.GetString("foto2");
RDebugUtils.currentLine=10551364;
 //BA.debugLineNum = 10551364;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=10551365;
 //BA.debugLineNum = 10551365;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.Server";
_req = mostCurrent._uploadfiles._createpostrequest(_ba,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=10551366;
 //BA.debugLineNum = 10551366;BA.debugLine="hc.Execute(req, 1)";
_hc.Execute((_ba.processBA == null ? _ba : _ba.processBA),_req,(int) (1));
 };
RDebugUtils.currentLine=10551368;
 //BA.debugLineNum = 10551368;BA.debugLine="If Cursor1.GetString(\"foto3\") <> \"\"  And Cursor1";
if ((_cursor1.GetString("foto3")).equals("") == false && _cursor1.GetString("foto3")!= null) { 
RDebugUtils.currentLine=10551369;
 //BA.debugLineNum = 10551369;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=10551370;
 //BA.debugLineNum = 10551370;BA.debugLine="fd.FileName = Cursor1.GetString(\"foto3\")";
_fd.FileName = _cursor1.GetString("foto3");
RDebugUtils.currentLine=10551371;
 //BA.debugLineNum = 10551371;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=10551372;
 //BA.debugLineNum = 10551372;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.Server";
_req = mostCurrent._uploadfiles._createpostrequest(_ba,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=10551373;
 //BA.debugLineNum = 10551373;BA.debugLine="hc.Execute(req, 1)";
_hc.Execute((_ba.processBA == null ? _ba : _ba.processBA),_req,(int) (1));
 };
RDebugUtils.currentLine=10551375;
 //BA.debugLineNum = 10551375;BA.debugLine="If Cursor1.GetString(\"foto4\") <> \"\"  And Cursor1";
if ((_cursor1.GetString("foto4")).equals("") == false && _cursor1.GetString("foto4")!= null) { 
RDebugUtils.currentLine=10551376;
 //BA.debugLineNum = 10551376;BA.debugLine="files.Clear";
_files.Clear();
RDebugUtils.currentLine=10551377;
 //BA.debugLineNum = 10551377;BA.debugLine="fd.FileName = Cursor1.GetString(\"foto4\")";
_fd.FileName = _cursor1.GetString("foto4");
RDebugUtils.currentLine=10551378;
 //BA.debugLineNum = 10551378;BA.debugLine="files.Add(fd)";
_files.Add((Object)(_fd));
RDebugUtils.currentLine=10551379;
 //BA.debugLineNum = 10551379;BA.debugLine="req = uploadfiles.CreatePostRequest(Main.Server";
_req = mostCurrent._uploadfiles._createpostrequest(_ba,mostCurrent._main._serverpath+"/connect/multipartpost.php",_nv,_files);
RDebugUtils.currentLine=10551380;
 //BA.debugLineNum = 10551380;BA.debugLine="hc.Execute(req, 1)";
_hc.Execute((_ba.processBA == null ? _ba : _ba.processBA),_req,(int) (1));
 };
 }
};
RDebugUtils.currentLine=10551383;
 //BA.debugLineNum = 10551383;BA.debugLine="Cursor1.Close";
_cursor1.Close();
RDebugUtils.currentLine=10551384;
 //BA.debugLineNum = 10551384;BA.debugLine="End Sub";
return "";
}
public static String  _convertnulltostring(anywheresoftware.b4a.BA _ba,String _entrada) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "convertnulltostring"))
	 {return ((String) Debug.delegate(null, "convertnulltostring", new Object[] {_ba,_entrada}));}
RDebugUtils.currentLine=10944512;
 //BA.debugLineNum = 10944512;BA.debugLine="Sub convertNulltoString(entrada As String)";
RDebugUtils.currentLine=10944513;
 //BA.debugLineNum = 10944513;BA.debugLine="If entrada <> Null Then";
if (_entrada!= null) { 
RDebugUtils.currentLine=10944514;
 //BA.debugLineNum = 10944514;BA.debugLine="Return entrada";
if (true) return _entrada;
 }else {
RDebugUtils.currentLine=10944516;
 //BA.debugLineNum = 10944516;BA.debugLine="Return \"\"";
if (true) return "";
 };
RDebugUtils.currentLine=10944519;
 //BA.debugLineNum = 10944519;BA.debugLine="End Sub";
return "";
}
public static String  _finalizarenvio(anywheresoftware.b4a.BA _ba) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "finalizarenvio"))
	 {return ((String) Debug.delegate(null, "finalizarenvio", new Object[] {_ba}));}
String _filename = "";
String _filenotas = "";
RDebugUtils.currentLine=10878976;
 //BA.debugLineNum = 10878976;BA.debugLine="Sub FinalizarEnvio";
RDebugUtils.currentLine=10878978;
 //BA.debugLineNum = 10878978;BA.debugLine="Dim filename As String = Main.evaluacionpath & \".";
_filename = mostCurrent._main._evaluacionpath+".txt";
RDebugUtils.currentLine=10878979;
 //BA.debugLineNum = 10878979;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\", file";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=10878982;
 //BA.debugLineNum = 10878982;BA.debugLine="Dim filenotas As String";
_filenotas = "";
RDebugUtils.currentLine=10878983;
 //BA.debugLineNum = 10878983;BA.debugLine="filenotas = Main.evaluacionpath.SubString2(0,Main";
_filenotas = mostCurrent._main._evaluacionpath.substring((int) (0),mostCurrent._main._evaluacionpath.indexOf("_",(int) (mostCurrent._main._evaluacionpath.indexOf("_")+1)))+"_-Notas.txt";
RDebugUtils.currentLine=10878985;
 //BA.debugLineNum = 10878985;BA.debugLine="If File.Exists(File.DirRootExternal & \"/GeoVin/\",";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filenotas)) { 
RDebugUtils.currentLine=10878986;
 //BA.debugLineNum = 10878986;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\", fil";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filenotas,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filenotas);
RDebugUtils.currentLine=10878987;
 //BA.debugLineNum = 10878987;BA.debugLine="File.delete(File.DirRootExternal & \"/GeoVin/\", f";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filenotas);
 };
RDebugUtils.currentLine=10878990;
 //BA.debugLineNum = 10878990;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/\", fi";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
RDebugUtils.currentLine=10878993;
 //BA.debugLineNum = 10878993;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
RDebugUtils.currentLine=10878994;
 //BA.debugLineNum = 10878994;BA.debugLine="filename = Main.fotopath0 & \".jpg\"";
_filename = mostCurrent._main._fotopath0+".jpg";
RDebugUtils.currentLine=10878995;
 //BA.debugLineNum = 10878995;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\", fil";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=10878996;
 //BA.debugLineNum = 10878996;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/\", f";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
 };
RDebugUtils.currentLine=10878998;
 //BA.debugLineNum = 10878998;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
RDebugUtils.currentLine=10878999;
 //BA.debugLineNum = 10878999;BA.debugLine="filename = Main.fotopath1 & \".jpg\"";
_filename = mostCurrent._main._fotopath1+".jpg";
RDebugUtils.currentLine=10879000;
 //BA.debugLineNum = 10879000;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\", fil";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=10879001;
 //BA.debugLineNum = 10879001;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/\", f";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
 };
RDebugUtils.currentLine=10879003;
 //BA.debugLineNum = 10879003;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
RDebugUtils.currentLine=10879004;
 //BA.debugLineNum = 10879004;BA.debugLine="filename = Main.fotopath2 & \".jpg\"";
_filename = mostCurrent._main._fotopath2+".jpg";
RDebugUtils.currentLine=10879005;
 //BA.debugLineNum = 10879005;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\", fil";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=10879006;
 //BA.debugLineNum = 10879006;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/\", f";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
 };
RDebugUtils.currentLine=10879008;
 //BA.debugLineNum = 10879008;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
RDebugUtils.currentLine=10879009;
 //BA.debugLineNum = 10879009;BA.debugLine="filename = Main.fotopath3 & \".jpg\"";
_filename = mostCurrent._main._fotopath3+".jpg";
RDebugUtils.currentLine=10879010;
 //BA.debugLineNum = 10879010;BA.debugLine="File.Copy(File.DirRootExternal & \"/GeoVin/\", fil";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/sent/",_filename);
RDebugUtils.currentLine=10879011;
 //BA.debugLineNum = 10879011;BA.debugLine="File.Delete(File.DirRootExternal & \"/GeoVin/\", f";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/GeoVin/",_filename);
 };
RDebugUtils.currentLine=10879013;
 //BA.debugLineNum = 10879013;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4a.BA _ba,anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,String _reason,int _statuscode,int _taskid) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "hc_responseerror"))
	 {return ((String) Debug.delegate(null, "hc_responseerror", new Object[] {_ba,_response,_reason,_statuscode,_taskid}));}
RDebugUtils.currentLine=10682368;
 //BA.debugLineNum = 10682368;BA.debugLine="Sub hc_ResponseError (Response As OkHttpResponse,";
RDebugUtils.currentLine=10682370;
 //BA.debugLineNum = 10682370;BA.debugLine="If Response <> Null Then";
if (_response!= null) { 
RDebugUtils.currentLine=10682371;
 //BA.debugLineNum = 10682371;BA.debugLine="If TaskId = 1 Then";
if (_taskid==1) { 
RDebugUtils.currentLine=10682372;
 //BA.debugLineNum = 10682372;BA.debugLine="ToastMessageShow(\"Error enviando evaluación\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando evaluación"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=10682373;
 //BA.debugLineNum = 10682373;BA.debugLine="Else If TaskId = 2 Then";
if (_taskid==2) { 
RDebugUtils.currentLine=10682374;
 //BA.debugLineNum = 10682374;BA.debugLine="ToastMessageShow(\"Error enviando Foto #1\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando Foto #1"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=10682375;
 //BA.debugLineNum = 10682375;BA.debugLine="Else If TaskId = 3 Then";
if (_taskid==3) { 
RDebugUtils.currentLine=10682376;
 //BA.debugLineNum = 10682376;BA.debugLine="ToastMessageShow(\"Error enviando Foto #2\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando Foto #2"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=10682377;
 //BA.debugLineNum = 10682377;BA.debugLine="Else If TaskId = 4 Then";
if (_taskid==4) { 
RDebugUtils.currentLine=10682378;
 //BA.debugLineNum = 10682378;BA.debugLine="ToastMessageShow(\"Error enviando Foto #3\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando Foto #3"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=10682379;
 //BA.debugLineNum = 10682379;BA.debugLine="Else If TaskId = 5 Then";
if (_taskid==5) { 
RDebugUtils.currentLine=10682380;
 //BA.debugLineNum = 10682380;BA.debugLine="ToastMessageShow(\"Error enviando Foto #4\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enviando Foto #4"),anywheresoftware.b4a.keywords.Common.False);
 }}}}}
;
 };
RDebugUtils.currentLine=10682383;
 //BA.debugLineNum = 10682383;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4a.BA _ba,anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,int _taskid) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "hc_responsesuccess"))
	 {return ((String) Debug.delegate(null, "hc_responsesuccess", new Object[] {_ba,_response,_taskid}));}
RDebugUtils.currentLine=10747904;
 //BA.debugLineNum = 10747904;BA.debugLine="Sub hc_ResponseSuccess (Response As OkHttpResponse";
RDebugUtils.currentLine=10747905;
 //BA.debugLineNum = 10747905;BA.debugLine="out.InitializeToBytesArray(0) ' I expect less tha";
_out.InitializeToBytesArray((int) (0));
RDebugUtils.currentLine=10747906;
 //BA.debugLineNum = 10747906;BA.debugLine="Response.GetAsynchronously(\"Response\", out, True,";
_response.GetAsynchronously((_ba.processBA == null ? _ba : _ba.processBA),"Response",(java.io.OutputStream)(_out.getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
RDebugUtils.currentLine=10747909;
 //BA.debugLineNum = 10747909;BA.debugLine="If TaskId = 1 Then";
if (_taskid==1) { 
RDebugUtils.currentLine=10747910;
 //BA.debugLineNum = 10747910;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=10747911;
 //BA.debugLineNum = 10747911;BA.debugLine="Else If TaskId = 2 Then";
if (_taskid==2) { 
RDebugUtils.currentLine=10747912;
 //BA.debugLineNum = 10747912;BA.debugLine="ToastMessageShow(\"Foto #1 enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Foto #1 enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=10747913;
 //BA.debugLineNum = 10747913;BA.debugLine="Else If TaskId = 3 Then";
if (_taskid==3) { 
RDebugUtils.currentLine=10747914;
 //BA.debugLineNum = 10747914;BA.debugLine="ToastMessageShow(\"Foto #2 enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Foto #2 enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=10747915;
 //BA.debugLineNum = 10747915;BA.debugLine="Else If TaskId = 4 Then";
if (_taskid==4) { 
RDebugUtils.currentLine=10747916;
 //BA.debugLineNum = 10747916;BA.debugLine="ToastMessageShow(\"Foto #3 enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Foto #3 enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else 
{RDebugUtils.currentLine=10747917;
 //BA.debugLineNum = 10747917;BA.debugLine="Else If TaskId = 5 Then";
if (_taskid==5) { 
RDebugUtils.currentLine=10747918;
 //BA.debugLineNum = 10747918;BA.debugLine="ToastMessageShow(\"Foto #4 enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Foto #4 enviada"),anywheresoftware.b4a.keywords.Common.False);
 }}}}}
;
RDebugUtils.currentLine=10747921;
 //BA.debugLineNum = 10747921;BA.debugLine="End Sub";
return "";
}
public static String  _mensajenotificacion(anywheresoftware.b4a.BA _ba,boolean _activo,String _titulo,String _body) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "mensajenotificacion"))
	 {return ((String) Debug.delegate(null, "mensajenotificacion", new Object[] {_ba,_activo,_titulo,_body}));}
RDebugUtils.currentLine=10485760;
 //BA.debugLineNum = 10485760;BA.debugLine="Sub MensajeNotificacion(activo As Boolean, titulo";
RDebugUtils.currentLine=10485761;
 //BA.debugLineNum = 10485761;BA.debugLine="Msgbox(body,titulo)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_body),BA.ObjectToCharSequence(_titulo),_ba);
RDebugUtils.currentLine=10485763;
 //BA.debugLineNum = 10485763;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(anywheresoftware.b4a.BA _ba,boolean _success,int _taskid) throws Exception{
RDebugUtils.currentModule="utilidades";
if (Debug.shouldDelegate(null, "response_streamfinish"))
	 {return ((String) Debug.delegate(null, "response_streamfinish", new Object[] {_ba,_success,_taskid}));}
byte[] _another_buffer = null;
RDebugUtils.currentLine=10813440;
 //BA.debugLineNum = 10813440;BA.debugLine="Sub Response_StreamFinish (Success As Boolean, Tas";
RDebugUtils.currentLine=10813441;
 //BA.debugLineNum = 10813441;BA.debugLine="Dim another_buffer () As Byte";
_another_buffer = new byte[(int) (0)];
;
RDebugUtils.currentLine=10813442;
 //BA.debugLineNum = 10813442;BA.debugLine="another_buffer = out.ToBytesArray";
_another_buffer = _out.ToBytesArray();
RDebugUtils.currentLine=10813443;
 //BA.debugLineNum = 10813443;BA.debugLine="Log (BytesToString(another_buffer, 0, another_buf";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.BytesToString(_another_buffer,(int) (0),_another_buffer.length,"UTF8"));
RDebugUtils.currentLine=10813444;
 //BA.debugLineNum = 10813444;BA.debugLine="End Sub";
return "";
}
}