import java.awt.*;

public class thread extends java.applet.Applet implements Runnable   
{
    Thread th;                // Thread型の変数を宣言する。

    public void init()
    {
        th = new Thread( this );    // スレッドをスタートする。
        th.start();
    }
    public void paint(Graphics g)
    {
        // 数字を文字に変換するには、Integerクラスを使う。
        g.drawString( Integer.toString( i ) , 100 , 100 );
    }

    public boolean mouseDown( Event e , int x , int y )
    {
        // ....... マウスを押したときの処理も普通に書いておける。
        return true;
    }

    int i=0;
    public void run() // このメソッドが必要。
    {
        while(true){    // ずっと続ける場合は、ループさせる必要がある。
            i++;
            try{        // この3行が重要。
                Thread.sleep( 100 );
            }catch( InterruptedException e ) {}
            repaint();
        }
    }
}