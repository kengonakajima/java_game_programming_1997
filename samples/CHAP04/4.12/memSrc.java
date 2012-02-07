import java.awt.*;
import java.awt.image.*;

public class memSrc extends java.applet.Applet
{
    Image img,nimg;
    Graphics gr;

    PixelGrabber pg;
    int pix[];
    int height , width;

    public void init()
    {
        MediaTracker mt= new MediaTracker(this);
        img = getImage( getDocumentBase() , "kawai.jpg" );
        mt.addImage( img, 0 );
        try{
            mt.waitForAll();         // 読み込が終了するまで待つ。
        }catch( InterruptedException e ){}

        width = img.getWidth(null);
        height = img.getHeight(null);

        // 必要な分のメモリを確保。要素数は(縦ドット数)×(横ドット数)。
        pix = new int[width*height];   

        resize( width*2 , height );
        pg = new PixelGrabber( img ,0,0, width,height,pix,0,width );
        try{
            pg.grabPixels();
        } catch ( InterruptedException e ){
            System.out.println( e );
        }
        // ここで画像を好きなように処理する。
        for(int i=0;i<width*height;i++){
                pix[i]= pix[i]& 0xffff00ff;  // 緑成分を全部殺す処理。
        }
        nimg = createImage(
                new MemoryImageSource( width, height, pix , 0, width ) );
    }

    public void paint( Graphics g ){
        g.drawImage( img , 0 , 0 , this );
        g.drawImage( nimg , width,0,this );

    }
}
