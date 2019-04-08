package cepave.geovin;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class uploadfiles {
private static uploadfiles mostCurrent = new uploadfiles();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 
public static class _filedata{
public boolean IsInitialized;
public String Dir;
public String FileName;
public String KeyName;
public String ContentType;
public void Initialize() {
IsInitialized = true;
Dir = "";
FileName = "";
KeyName = "";
ContentType = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public anywheresoftware.b4a.keywords.Common __c = null;
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
public cepave.geovin.frmabout _frmabout = null;
public cepave.geovin.register _register = null;
public cepave.geovin.frmreportevinchuca _frmreportevinchuca = null;
public cepave.geovin.envioarchivos2 _envioarchivos2 = null;
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.multipartpost _multipartpost = null;
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest  _createpostrequest(anywheresoftware.b4a.BA _ba,String _url,anywheresoftware.b4a.objects.collections.Map _namevalues,anywheresoftware.b4a.objects.collections.List _files) throws Exception{
RDebugUtils.currentModule="uploadfiles";
if (Debug.shouldDelegate(null, "createpostrequest"))
	 {return ((anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest) Debug.delegate(null, "createpostrequest", new Object[] {_ba,_url,_namevalues,_files}));}
String _boundary = "";
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _stream = null;
String _eol = "";
byte[] _b = null;
String _key = "";
String _value = "";
int _i = 0;
cepave.geovin.uploadfiles._filedata _fd = null;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest _request = null;
RDebugUtils.currentLine=18612224;
 //BA.debugLineNum = 18612224;BA.debugLine="Sub CreatePostRequest(URL As String, NameValues As";
RDebugUtils.currentLine=18612225;
 //BA.debugLineNum = 18612225;BA.debugLine="Dim boundary As String";
_boundary = "";
RDebugUtils.currentLine=18612226;
 //BA.debugLineNum = 18612226;BA.debugLine="boundary = \"---------------------------1461124740";
_boundary = "---------------------------1461124740692";
RDebugUtils.currentLine=18612227;
 //BA.debugLineNum = 18612227;BA.debugLine="Dim stream As OutputStream";
_stream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
RDebugUtils.currentLine=18612228;
 //BA.debugLineNum = 18612228;BA.debugLine="stream.InitializeToBytesArray(20)";
_stream.InitializeToBytesArray((int) (20));
RDebugUtils.currentLine=18612229;
 //BA.debugLineNum = 18612229;BA.debugLine="Dim EOL As String";
_eol = "";
RDebugUtils.currentLine=18612230;
 //BA.debugLineNum = 18612230;BA.debugLine="EOL = Chr(13) & Chr(10) 'CRLF constant matches An";
_eol = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (13)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
RDebugUtils.currentLine=18612231;
 //BA.debugLineNum = 18612231;BA.debugLine="Dim b() As Byte";
_b = new byte[(int) (0)];
;
RDebugUtils.currentLine=18612232;
 //BA.debugLineNum = 18612232;BA.debugLine="If NameValues <> Null And NameValues.IsInitialize";
if (_namevalues!= null && _namevalues.IsInitialized()) { 
RDebugUtils.currentLine=18612234;
 //BA.debugLineNum = 18612234;BA.debugLine="Dim key, value As String";
_key = "";
_value = "";
RDebugUtils.currentLine=18612235;
 //BA.debugLineNum = 18612235;BA.debugLine="For i = 0 To NameValues.Size - 1";
{
final int step10 = 1;
final int limit10 = (int) (_namevalues.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit10 ;_i = _i + step10 ) {
RDebugUtils.currentLine=18612236;
 //BA.debugLineNum = 18612236;BA.debugLine="key = NameValues.GetKeyAt(i)";
_key = BA.ObjectToString(_namevalues.GetKeyAt(_i));
RDebugUtils.currentLine=18612237;
 //BA.debugLineNum = 18612237;BA.debugLine="value = NameValues.GetValueAt(i)";
_value = BA.ObjectToString(_namevalues.GetValueAt(_i));
RDebugUtils.currentLine=18612238;
 //BA.debugLineNum = 18612238;BA.debugLine="b = (\"--\" & boundary & EOL & \"Content-Dispositi";
_b = ("--"+_boundary+_eol+"Content-Disposition: form-data; name="+anywheresoftware.b4a.keywords.Common.QUOTE+_key+anywheresoftware.b4a.keywords.Common.QUOTE+_eol+_eol+_value+_eol).getBytes("UTF8");
RDebugUtils.currentLine=18612240;
 //BA.debugLineNum = 18612240;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 }
};
 };
RDebugUtils.currentLine=18612243;
 //BA.debugLineNum = 18612243;BA.debugLine="If Files <> Null And Files.IsInitialized Then";
if (_files!= null && _files.IsInitialized()) { 
RDebugUtils.currentLine=18612245;
 //BA.debugLineNum = 18612245;BA.debugLine="Dim FD As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=18612246;
 //BA.debugLineNum = 18612246;BA.debugLine="For i = 0 To Files.Size";
{
final int step19 = 1;
final int limit19 = _files.getSize();
_i = (int) (0) ;
for (;_i <= limit19 ;_i = _i + step19 ) {
RDebugUtils.currentLine=18612247;
 //BA.debugLineNum = 18612247;BA.debugLine="FD = Files.Get(i)";
_fd = (cepave.geovin.uploadfiles._filedata)(_files.Get(_i));
RDebugUtils.currentLine=18612248;
 //BA.debugLineNum = 18612248;BA.debugLine="b = (\"--\" & boundary & EOL & \"Content-Dispositi";
_b = ("--"+_boundary+_eol+"Content-Disposition: form-data; name="+anywheresoftware.b4a.keywords.Common.QUOTE+_fd.KeyName+anywheresoftware.b4a.keywords.Common.QUOTE+"; filename="+anywheresoftware.b4a.keywords.Common.QUOTE+_fd.FileName+anywheresoftware.b4a.keywords.Common.QUOTE+_eol+"Content-Type: "+_fd.ContentType+_eol+_eol).getBytes("UTF8");
RDebugUtils.currentLine=18612251;
 //BA.debugLineNum = 18612251;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
RDebugUtils.currentLine=18612252;
 //BA.debugLineNum = 18612252;BA.debugLine="Dim In As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
RDebugUtils.currentLine=18612253;
 //BA.debugLineNum = 18612253;BA.debugLine="In = File.OpenInput(FD.Dir, FD.FileName)";
_in = anywheresoftware.b4a.keywords.Common.File.OpenInput(_fd.Dir,_fd.FileName);
RDebugUtils.currentLine=18612254;
 //BA.debugLineNum = 18612254;BA.debugLine="File.Copy2(In, stream) 'read the file and write";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_in.getObject()),(java.io.OutputStream)(_stream.getObject()));
RDebugUtils.currentLine=18612255;
 //BA.debugLineNum = 18612255;BA.debugLine="b = EOL.GetBytes(\"UTF8\")";
_b = _eol.getBytes("UTF8");
RDebugUtils.currentLine=18612256;
 //BA.debugLineNum = 18612256;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
RDebugUtils.currentLine=18612258;
 //BA.debugLineNum = 18612258;BA.debugLine="b = (EOL & \"--\" & boundary & \"--\" & EOL).GetByt";
_b = (_eol+"--"+_boundary+"--"+_eol).getBytes("UTF8");
RDebugUtils.currentLine=18612259;
 //BA.debugLineNum = 18612259;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
RDebugUtils.currentLine=18612260;
 //BA.debugLineNum = 18612260;BA.debugLine="b = stream.ToBytesArray";
_b = _stream.ToBytesArray();
RDebugUtils.currentLine=18612262;
 //BA.debugLineNum = 18612262;BA.debugLine="Dim request As OkHttpRequest";
_request = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
RDebugUtils.currentLine=18612263;
 //BA.debugLineNum = 18612263;BA.debugLine="request.InitializePost2(URL, b)";
_request.InitializePost2(_url,_b);
RDebugUtils.currentLine=18612264;
 //BA.debugLineNum = 18612264;BA.debugLine="request.SetContentType(\"multipart/form-data; bo";
_request.SetContentType("multipart/form-data; boundary="+_boundary);
RDebugUtils.currentLine=18612265;
 //BA.debugLineNum = 18612265;BA.debugLine="request.SetContentEncoding(\"UTF8\")";
_request.SetContentEncoding("UTF8");
RDebugUtils.currentLine=18612266;
 //BA.debugLineNum = 18612266;BA.debugLine="Return request";
if (true) return _request;
 }
};
 };
RDebugUtils.currentLine=18612270;
 //BA.debugLineNum = 18612270;BA.debugLine="b = (EOL & \"--\" & boundary & \"--\" & EOL).GetBytes";
_b = (_eol+"--"+_boundary+"--"+_eol).getBytes("UTF8");
RDebugUtils.currentLine=18612271;
 //BA.debugLineNum = 18612271;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
RDebugUtils.currentLine=18612272;
 //BA.debugLineNum = 18612272;BA.debugLine="b = stream.ToBytesArray";
_b = _stream.ToBytesArray();
RDebugUtils.currentLine=18612274;
 //BA.debugLineNum = 18612274;BA.debugLine="Dim request As OkHttpRequest";
_request = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
RDebugUtils.currentLine=18612275;
 //BA.debugLineNum = 18612275;BA.debugLine="request.InitializePost2(URL, b)";
_request.InitializePost2(_url,_b);
RDebugUtils.currentLine=18612276;
 //BA.debugLineNum = 18612276;BA.debugLine="request.SetContentType(\"multipart/form-data; boun";
_request.SetContentType("multipart/form-data; boundary="+_boundary);
RDebugUtils.currentLine=18612277;
 //BA.debugLineNum = 18612277;BA.debugLine="request.SetContentEncoding(\"UTF8\")";
_request.SetContentEncoding("UTF8");
RDebugUtils.currentLine=18612278;
 //BA.debugLineNum = 18612278;BA.debugLine="Return request";
if (true) return _request;
RDebugUtils.currentLine=18612280;
 //BA.debugLineNum = 18612280;BA.debugLine="End Sub";
return null;
}
}