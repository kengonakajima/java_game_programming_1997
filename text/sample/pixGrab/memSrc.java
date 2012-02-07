import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;

public class memSrc extends Applet
{
	Image img,nimg;
	Graphics gr;

	PixelGrabber pg;
	int pix[];
	int height , width;

	public void init()
	{
		MediaTracker mt= new MediaTracker(this);
		img = getImage( getDocumentBase() , "kawai.jpg");
		mt.addImage( img, 0 );
		try{
			mt.waitForAll();         // $BFI$_$3$_$,=*N;$9$k$^$GBT$D!#(B
		}catch( InterruptedException e ){}

		

		width = img.getWidth(null);
		height = img.getHeight(null);

		// $BI,MW$JJ,$N%a%b%j!<$r3NJ]!#MWAG?t$O(B ($B=D%I%C%H?t(B)$B!_(B($B2#%I%C%H?t(B) $B$G$9!#(B 
		pix = new int[width*height];   

		resize( width*2 , height );
		pg = new PixelGrabber( img ,0,0, width,height,pix,0,width);
		try{
			pg.grabPixels();
		} catch ( InterruptedException e ){
			System.out.println(e );
		}
		// $B$3$3$G2hA|$r9%$-$J$h$&$K=hM}!#(B
		for(int i=0;i<width*height;i++){
				pix[i]= pix[i]& 0x55ff0000;  //$BNP@.J,$rA4It;&$9=hM}(B
		}
		nimg = createImage(
				new MemoryImageSource( width,height,pix ,0,width ));
	}

	public void paint( Graphics g ){
		g.drawImage( img , 0 , 0 , this );
		g.drawImage( nimg , width,0,this );

	}

}
