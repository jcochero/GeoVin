package cepave.geovin.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laynotas{

public static void LS_320x480_1(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("label2").vw).setTextSize((float)((12d * scale)));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btnok").vw).setTextSize((float)((12d * scale)));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("txtnotas").vw).setTextSize((float)((10d * scale)));

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}