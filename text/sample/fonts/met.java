import java.awt.*;

public class font extends java.applet.Applet
{
	String font_names[];
	public void init(){
		// まず、使用可能なフォントを探します。
		font_names = Toolkit.getDefaultToolkit().getFontList();
		for(int i=0;i<font_names.length;i++){
			System.out.println( font_names[i] );
		}
	}
