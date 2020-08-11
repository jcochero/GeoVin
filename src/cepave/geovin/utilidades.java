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
public cepave.geovin.main _main = null;
public cepave.geovin.frmprincipal _frmprincipal = null;
public cepave.geovin.downloadservice _downloadservice = null;
public cepave.geovin.frmfotos _frmfotos = null;
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
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.frmidentificacionnew _frmidentificacionnew = null;
public cepave.geovin.frmlogin _frmlogin = null;
public cepave.geovin.frmpoliticadatos _frmpoliticadatos = null;
public cepave.geovin.frmrecomendaciones _frmrecomendaciones = null;
public cepave.geovin.httputils2service _httputils2service = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.register _register = null;
public cepave.geovin.uploadfiles _uploadfiles = null;
public static String  _convertnulltostring(anywheresoftware.b4a.BA _ba,String _entrada) throws Exception{
 //BA.debugLineNum = 138;BA.debugLine="Sub convertNulltoString(entrada As String)";
 //BA.debugLineNum = 139;BA.debugLine="If entrada <> Null Then";
if (_entrada!= null) { 
 //BA.debugLineNum = 140;BA.debugLine="Return entrada";
if (true) return _entrada;
 }else {
 //BA.debugLineNum = 142;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _getdeviceid(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 93;BA.debugLine="Sub GetDeviceId As String";
 //BA.debugLineNum = 113;BA.debugLine="Return FirebaseMessaging.fm.Token";
if (true) return mostCurrent._firebasemessaging._fm /*anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper*/ .getToken();
 //BA.debugLineNum = 114;BA.debugLine="Log(FirebaseMessaging.fm.Token)";
anywheresoftware.b4a.keywords.Common.LogImpl("27208981",mostCurrent._firebasemessaging._fm /*anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper*/ .getToken(),0);
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _mensaje(anywheresoftware.b4a.BA _ba,String _titulo,String _imagen,String _text,String _textsub,String _botontextoyes,String _botontextocan,String _botontextono,boolean _textolargo) throws Exception{
flm.b4a.betterdialogs.BetterDialogs _dial = null;
anywheresoftware.b4a.objects.ImageViewWrapper _msgbottomimg = null;
anywheresoftware.b4a.objects.PanelWrapper _panelcontent = null;
anywheresoftware.b4a.objects.LabelWrapper _titulolbl = null;
anywheresoftware.b4a.objects.ImageViewWrapper _msgtopimg = null;
anywheresoftware.b4a.objects.LabelWrapper _contenido = null;
anywheresoftware.b4a.objects.LabelWrapper _textosub = null;
String _msg = "";
anywheresoftware.b4a.objects.ImageViewWrapper _imgbitmap = null;
 //BA.debugLineNum = 18;BA.debugLine="Sub Mensaje (titulo As String, imagen As String, t";
 //BA.debugLineNum = 19;BA.debugLine="Dim dial As BetterDialogs";
_dial = new flm.b4a.betterdialogs.BetterDialogs();
 //BA.debugLineNum = 20;BA.debugLine="Dim MsgBottomImg As ImageView";
_msgbottomimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim panelcontent As Panel";
_panelcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim titulolbl As Label";
_titulolbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim MsgTopImg As ImageView";
_msgtopimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim contenido As Label";
_contenido = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim textoSub As Label";
_textosub = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 28;BA.debugLine="MsgBottomImg.Initialize(\"\")";
_msgbottomimg.Initialize(_ba,"");
 //BA.debugLineNum = 29;BA.debugLine="MsgTopImg.Initialize(\"\")";
_msgtopimg.Initialize(_ba,"");
 //BA.debugLineNum = 30;BA.debugLine="panelcontent.Initialize(\"\")";
_panelcontent.Initialize(_ba,"");
 //BA.debugLineNum = 31;BA.debugLine="titulolbl.Initialize(\"\")";
_titulolbl.Initialize(_ba,"");
 //BA.debugLineNum = 32;BA.debugLine="contenido.Initialize(\"\")";
_contenido.Initialize(_ba,"");
 //BA.debugLineNum = 33;BA.debugLine="textoSub.Initialize(\"\")";
_textosub.Initialize(_ba,"");
 //BA.debugLineNum = 37;BA.debugLine="MsgTopImg.Gravity = Gravity.FILL";
_msgtopimg.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 38;BA.debugLine="MsgBottomImg.Gravity = Gravity.FILL";
_msgbottomimg.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 40;BA.debugLine="titulolbl.Text = titulo";
_titulolbl.setText(BA.ObjectToCharSequence(_titulo));
 //BA.debugLineNum = 41;BA.debugLine="titulolbl.TextColor = Colors.Black";
_titulolbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 42;BA.debugLine="titulolbl.Gravity = Gravity.CENTER_HORIZONTAL";
_titulolbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 43;BA.debugLine="titulolbl.TextSize = 22";
_titulolbl.setTextSize((float) (22));
 //BA.debugLineNum = 44;BA.debugLine="contenido.Text = text";
_contenido.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 45;BA.debugLine="contenido.TextColor = Colors.Black";
_contenido.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 46;BA.debugLine="contenido.TextSize = 18";
_contenido.setTextSize((float) (18));
 //BA.debugLineNum = 47;BA.debugLine="contenido.Gravity = Gravity.CENTER_HORIZONTAL";
_contenido.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 48;BA.debugLine="contenido.Color = Colors.white";
_contenido.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 49;BA.debugLine="textoSub.Text = textsub";
_textosub.setText(BA.ObjectToCharSequence(_textsub));
 //BA.debugLineNum = 50;BA.debugLine="textoSub.TextColor = Colors.DarkGray";
_textosub.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 51;BA.debugLine="textoSub.TextSize = 16";
_textosub.setTextSize((float) (16));
 //BA.debugLineNum = 52;BA.debugLine="textoSub.Gravity = Gravity.CENTER_HORIZONTAL";
_textosub.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 53;BA.debugLine="textoSub.Color = Colors.white";
_textosub.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 54;BA.debugLine="panelcontent.Color = Colors.white";
_panelcontent.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 57;BA.debugLine="Dim imgbitmap As ImageView";
_imgbitmap = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 58;BA.debugLine="imgbitmap.Initialize(\"\")";
_imgbitmap.Initialize(_ba,"");
 //BA.debugLineNum = 59;BA.debugLine="If imagen <> Null Then";
if (_imagen!= null) { 
 //BA.debugLineNum = 60;BA.debugLine="imgbitmap.Bitmap =  LoadBitmapSample(File.DirAss";
_imgbitmap.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_imagen,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba)).getObject()));
 }else {
 //BA.debugLineNum = 62;BA.debugLine="imgbitmap.Bitmap =  LoadBitmapSample(File.DirAss";
_imgbitmap.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"MsgIcon.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba)).getObject()));
 };
 //BA.debugLineNum = 66;BA.debugLine="panelcontent.AddView(MsgTopImg, 0, 0,90%x, 60dip)";
_panelcontent.AddView((android.view.View)(_msgtopimg.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 67;BA.debugLine="panelcontent.AddView(imgbitmap, 5%x, 3%y, 80%x, 3";
_panelcontent.AddView((android.view.View)(_imgbitmap.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),_ba));
 //BA.debugLineNum = 68;BA.debugLine="panelcontent.AddView(titulolbl, 0, 35%y, 90%x, 60";
_panelcontent.AddView((android.view.View)(_titulolbl.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 69;BA.debugLine="panelcontent.AddView(contenido, 5%x, titulolbl.To";
_panelcontent.AddView((android.view.View)(_contenido.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_titulolbl.getTop()+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),_ba)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),_ba));
 //BA.debugLineNum = 73;BA.debugLine="If textolargo = True Then";
if (_textolargo==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 74;BA.debugLine="panelcontent.AddView(textoSub, 5%x, contenido.T";
_panelcontent.AddView((android.view.View)(_textosub.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_contenido.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),_ba));
 }else {
 //BA.debugLineNum = 76;BA.debugLine="panelcontent.AddView(textoSub, 5%x, contenido.T";
_panelcontent.AddView((android.view.View)(_textosub.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_contenido.getTop()+_contenido.getHeight()+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),_ba)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),_ba));
 };
 //BA.debugLineNum = 79;BA.debugLine="panelcontent.AddView(MsgBottomImg, 0, 350dip, 90%";
_panelcontent.AddView((android.view.View)(_msgbottomimg.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 81;BA.debugLine="msg = dial.CustomDialog(\"\", 90%x, 60dip, panelcon";
_msg = BA.NumberToString(_dial.CustomDialog((Object)(""),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)),(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_panelcontent.getObject())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),anywheresoftware.b4a.keywords.Common.Null,(Object)(_botontextoyes),(Object)(_botontextocan),(Object)(_botontextono),anywheresoftware.b4a.keywords.Common.False,"",_ba));
 //BA.debugLineNum = 82;BA.debugLine="Return msg";
if (true) return _msg;
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _mensajenotificacion(anywheresoftware.b4a.BA _ba,boolean _activo,String _titulo,String _body) throws Exception{
 //BA.debugLineNum = 124;BA.debugLine="Sub MensajeNotificacion(activo As Boolean, titulo";
 //BA.debugLineNum = 125;BA.debugLine="Msgbox(body,titulo)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_body),BA.ObjectToCharSequence(_titulo),_ba);
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 5;BA.debugLine="Dim hc As OkHttpClient";
_hc = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper();
 //BA.debugLineNum = 6;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 7;BA.debugLine="Dim numfotosenviar As Int";
_numfotosenviar = 0;
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
}
