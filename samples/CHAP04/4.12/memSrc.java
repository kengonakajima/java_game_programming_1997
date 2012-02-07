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
            mt.waitForAll();         // �ǂݍ����I������܂ő҂B
        }catch( InterruptedException e ){}

        width = img.getWidth(null);
        height = img.getHeight(null);

        // �K�v�ȕ��̃��������m�ہB�v�f����(�c�h�b�g��)�~(���h�b�g��)�B
        pix = new int[width*height];   

        resize( width*2 , height );
        pg = new PixelGrabber( img ,0,0, width,height,pix,0,width );
        try{
            pg.grabPixels();
        } catch ( InterruptedException e ){
            System.out.println( e );
        }
        // �����ŉ摜���D���Ȃ悤�ɏ�������B
        for(int i=0;i<width*height;i++){
                pix[i]= pix[i]& 0xffff00ff;  // �ΐ�����S���E�������B
        }
        nimg = createImage(
                new MemoryImageSource( width, height, pix , 0, width ) );
    }

    public void paint( Graphics g ){
        g.drawImage( img , 0 , 0 , this );
        g.drawImage( nimg , width,0,this );

    }
}
