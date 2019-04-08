package cepave.geovin;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class multipartpost {
private static multipartpost mostCurrent = new multipartpost();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 
public static class _filedata1{
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
public cepave.geovin.uploadfiles _uploadfiles = null;
public cepave.geovin.frmabout _frmabout = null;
public cepave.geovin.register _register = null;
public cepave.geovin.frmreportevinchuca _frmreportevinchuca = null;
public cepave.geovin.envioarchivos2 _envioarchivos2 = null;
public cepave.geovin.frmespecies _frmespecies = null;
public cepave.geovin.frmcomofotos _frmcomofotos = null;
public cepave.geovin.frmidentificacion _frmidentificacion = null;
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest  _createpostrequest(anywheresoftware.b4a.BA _ba,String _url,anywheresoftware.b4a.objects.collections.Map _namevalues,anywheresoftware.b4a.objects.collections.List _files) throws Exception{
RDebugUtils.currentModule="multipartpost";
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
RDebugUtils.currentLine=22085632;
 //BA.debugLineNum = 22085632;BA.debugLine="Sub CreatePostRequest(URL As String, NameValues As";
RDebugUtils.currentLine=22085633;
 //BA.debugLineNum = 22085633;BA.debugLine="Dim boundary As String";
_boundary = "";
RDebugUtils.currentLine=22085634;
 //BA.debugLineNum = 22085634;BA.debugLine="boundary = \"---------------------------1461124740";
_boundary = "---------------------------1461124740692";
RDebugUtils.currentLine=22085635;
 //BA.debugLineNum = 22085635;BA.debugLine="Dim stream As OutputStream";
_stream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
RDebugUtils.currentLine=22085636;
 //BA.debugLineNum = 22085636;BA.debugLine="stream.InitializeToBytesArray(20)";
_stream.InitializeToBytesArray((int) (20));
RDebugUtils.currentLine=22085637;
 //BA.debugLineNum = 22085637;BA.debugLine="Dim EOL As String";
_eol = "";
RDebugUtils.currentLine=22085638;
 //BA.debugLineNum = 22085638;BA.debugLine="EOL = Chr(13) & Chr(10) 'CRLF constant matches An";
_eol = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (13)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
RDebugUtils.currentLine=22085639;
 //BA.debugLineNum = 22085639;BA.debugLine="Dim b() As Byte";
_b = new byte[(int) (0)];
;
RDebugUtils.currentLine=22085640;
 //BA.debugLineNum = 22085640;BA.debugLine="If NameValues <> Null And NameValues.IsInitialize";
if (_namevalues!= null && _namevalues.IsInitialized()) { 
RDebugUtils.currentLine=22085642;
 //BA.debugLineNum = 22085642;BA.debugLine="Dim key, value As String";
_key = "";
_value = "";
RDebugUtils.currentLine=22085643;
 //BA.debugLineNum = 22085643;BA.debugLine="For i = 0 To NameValues.Size - 1";
{
final int step10 = 1;
final int limit10 = (int) (_namevalues.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit10 ;_i = _i + step10 ) {
RDebugUtils.currentLine=22085644;
 //BA.debugLineNum = 22085644;BA.debugLine="key = NameValues.GetKeyAt(i)";
_key = BA.ObjectToString(_namevalues.GetKeyAt(_i));
RDebugUtils.currentLine=22085645;
 //BA.debugLineNum = 22085645;BA.debugLine="value = NameValues.GetValueAt(i)";
_value = BA.ObjectToString(_namevalues.GetValueAt(_i));
RDebugUtils.currentLine=22085646;
 //BA.debugLineNum = 22085646;BA.debugLine="b = (\"--\" & boundary & EOL & \"Content-Dispositi";
_b = ("--"+_boundary+_eol+"Content-Disposition: form-data; name="+anywheresoftware.b4a.keywords.Common.QUOTE+_key+anywheresoftware.b4a.keywords.Common.QUOTE+_eol+_eol+_value+_eol).getBytes("UTF8");
RDebugUtils.currentLine=22085648;
 //BA.debugLineNum = 22085648;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 }
};
 };
RDebugUtils.currentLine=22085651;
 //BA.debugLineNum = 22085651;BA.debugLine="If Files <> Null And Files.IsInitialized Then";
if (_files!= null && _files.IsInitialized()) { 
RDebugUtils.currentLine=22085653;
 //BA.debugLineNum = 22085653;BA.debugLine="Dim FD As FileData";
_fd = new cepave.geovin.uploadfiles._filedata();
RDebugUtils.currentLine=22085654;
 //BA.debugLineNum = 22085654;BA.debugLine="For i = 0 To Files.Size - 1";
{
final int step19 = 1;
final int limit19 = (int) (_files.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit19 ;_i = _i + step19 ) {
RDebugUtils.currentLine=22085655;
 //BA.debugLineNum = 22085655;BA.debugLine="FD = Files.Get(i)";
_fd = (cepave.geovin.uploadfiles._filedata)(_files.Get(_i));
RDebugUtils.currentLine=22085656;
 //BA.debugLineNum = 22085656;BA.debugLine="b = (\"--\" & boundary & EOL & \"Content-Dispositi";
_b = ("--"+_boundary+_eol+"Content-Disposition: form-data; name="+anywheresoftware.b4a.keywords.Common.QUOTE+_fd.KeyName+anywheresoftware.b4a.keywords.Common.QUOTE+"; filename="+anywheresoftware.b4a.keywords.Common.QUOTE+_fd.FileName+anywheresoftware.b4a.keywords.Common.QUOTE+_eol+"Content-Type: "+_fd.ContentType+_eol+_eol).getBytes("UTF8");
RDebugUtils.currentLine=22085659;
 //BA.debugLineNum = 22085659;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
RDebugUtils.currentLine=22085660;
 //BA.debugLineNum = 22085660;BA.debugLine="Dim In As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
RDebugUtils.currentLine=22085661;
 //BA.debugLineNum = 22085661;BA.debugLine="In = File.OpenInput(FD.Dir, FD.FileName)";
_in = anywheresoftware.b4a.keywords.Common.File.OpenInput(_fd.Dir,_fd.FileName);
RDebugUtils.currentLine=22085662;
 //BA.debugLineNum = 22085662;BA.debugLine="File.Copy2(In, stream) 'read the file and write";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_in.getObject()),(java.io.OutputStream)(_stream.getObject()));
RDebugUtils.currentLine=22085663;
 //BA.debugLineNum = 22085663;BA.debugLine="b = EOL.GetBytes(\"UTF8\")";
_b = _eol.getBytes("UTF8");
RDebugUtils.currentLine=22085664;
 //BA.debugLineNum = 22085664;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
RDebugUtils.currentLine=22085666;
 //BA.debugLineNum = 22085666;BA.debugLine="b = (EOL & \"--\" & boundary & \"--\" & EOL).GetByt";
_b = (_eol+"--"+_boundary+"--"+_eol).getBytes("UTF8");
RDebugUtils.currentLine=22085667;
 //BA.debugLineNum = 22085667;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
RDebugUtils.currentLine=22085668;
 //BA.debugLineNum = 22085668;BA.debugLine="b = stream.ToBytesArray";
_b = _stream.ToBytesArray();
RDebugUtils.currentLine=22085670;
 //BA.debugLineNum = 22085670;BA.debugLine="Dim request As OkHttpRequest";
_request = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
RDebugUtils.currentLine=22085671;
 //BA.debugLineNum = 22085671;BA.debugLine="request.InitializePost2(URL, b)";
_request.InitializePost2(_url,_b);
RDebugUtils.currentLine=22085672;
 //BA.debugLineNum = 22085672;BA.debugLine="request.SetContentType(\"multipart/form-data; bo";
_request.SetContentType("multipart/form-data; boundary="+_boundary);
RDebugUtils.currentLine=22085673;
 //BA.debugLineNum = 22085673;BA.debugLine="request.SetContentEncoding(\"UTF8\")";
_request.SetContentEncoding("UTF8");
RDebugUtils.currentLine=22085674;
 //BA.debugLineNum = 22085674;BA.debugLine="Return request";
if (true) return _request;
 }
};
 };
RDebugUtils.currentLine=22085678;
 //BA.debugLineNum = 22085678;BA.debugLine="b = (EOL & \"--\" & boundary & \"--\" & EOL).GetBytes";
_b = (_eol+"--"+_boundary+"--"+_eol).getBytes("UTF8");
RDebugUtils.currentLine=22085679;
 //BA.debugLineNum = 22085679;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
RDebugUtils.currentLine=22085680;
 //BA.debugLineNum = 22085680;BA.debugLine="b = stream.ToBytesArray";
_b = _stream.ToBytesArray();
RDebugUtils.currentLine=22085682;
 //BA.debugLineNum = 22085682;BA.debugLine="Dim request As OkHttpRequest";
_request = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
RDebugUtils.currentLine=22085683;
 //BA.debugLineNum = 22085683;BA.debugLine="request.InitializePost2(URL, b)";
_request.InitializePost2(_url,_b);
RDebugUtils.currentLine=22085684;
 //BA.debugLineNum = 22085684;BA.debugLine="request.SetContentType(\"multipart/form-data; boun";
_request.SetContentType("multipart/form-data; boundary="+_boundary);
RDebugUtils.currentLine=22085685;
 //BA.debugLineNum = 22085685;BA.debugLine="request.SetContentEncoding(\"UTF8\")";
_request.SetContentEncoding("UTF8");
RDebugUtils.currentLine=22085686;
 //BA.debugLineNum = 22085686;BA.debugLine="Return request";
if (true) return _request;
RDebugUtils.currentLine=22085688;
 //BA.debugLineNum = 22085688;BA.debugLine="End Sub";
return null;
}
}