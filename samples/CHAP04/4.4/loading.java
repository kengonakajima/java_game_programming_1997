import java.awt.*;
public class loading extends java.applet.Applet implements Runnable
{
    MediaTracker m;
    Image img[] = new Image[10];
    Thread t;
    boolean loaded = false;
    public void init()
    {
        t = new Thread( this );
        m = new MediaTracker(this);
        for( int i = 0 ; i < 10 ; i++ ){
            String name = "image" + i + ".gif"; // ファイル名を作る。
            img[i] = getImage( getDocumentBase() , name );
            m.addImage( img[i] , i );
        }
    }
    public void paint( Graphics g )
    {
        if( loaded ){
            // もうロードが終わってるので、ここに内容を書く。
        } else {
            // まだロードが終わっていない。
            g.drawString("Now loading...",100,100);
        }

    }
    public void run()
    {
        while(true){
            try{ Thread.sleep(500); } catch( InterruptedException e ){}
            if( loaded == false ){ 
                try{
                    m.waitForAll(); 
                }catch( InterruptedException e ){}
            }
        }
    }
}