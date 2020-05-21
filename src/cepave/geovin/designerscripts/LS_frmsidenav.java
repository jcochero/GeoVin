package cepave.geovin.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_frmsidenav{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("lblusericon").vw.setLeft((int)((50d / 100 * width)-((views.get("lblusericon").vw.getWidth())/2d)));
//BA.debugLineNum = 4;BA.debugLine="lblUserName.Left = 50%x - (lblUserName.Width / 2)"[frmSideNav/General script]
views.get("lblusername").vw.setLeft((int)((50d / 100 * width)-((views.get("lblusername").vw.getWidth())/2d)));

}
}