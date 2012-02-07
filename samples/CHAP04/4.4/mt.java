import java.awt.*;
public class mt extends java.applet.Applet
{
    MediaTracker m;      // まず、MediaTrackerクラスのインスタンスを作る。
    Image img;

    public void init()
    {
        img = getImage( getDocumentBase() , "kawai.jpg" ); // ここは同じ。
        m = new MediaTracker(this);
        m.addImage( img, 0 ); // MediaTrackerの仕事リストに加える。
        try{
            m.waitForID(0);      // ロードが終わるまで待つ（強制ロード）。
        }catch( InterruptedException e ){}
    }
    public void paint(Graphics g){
        g.drawImage( img , 0 , 0 , this );
    }
}
