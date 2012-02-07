import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;

public class m extends Applet
{

	int mem[] = new int[256*256];
	MemoryImageSource mm;
	Image img;

	public void init()
	{
		resize(256,256);

		for(int i = 0 ; i < 256 ; i++){
			for(int j = 0 ; j < 256 ; j++ ){
				mem[ i*256 + j ] = 0xff000000 + j*0x010101;
			}
		}
		img = createImage( new MemoryImageSource( 256, 256, mem, 0, 256 ));

	}
	public void paint( Graphics g ){
		g.drawImage( img ,0,0,null);
	}

}
