import java.awt.*;

public class font extends java.applet.Applet
{
	String font_names[];
	public void init(){
		// $B$^$:!";HMQ2DG=$J%U%)%s%H$rC5$7$^$9!#(B
		font_names = Toolkit.getDefaultToolkit().getFontList();
		for(int i=0;i<font_names.length;i++){
			System.out.println( font_names[i] );
		}
	}
