import java.applet.Applet;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class client extends Applet implements Runnable
{
    Socket clisock;        // クライアント用
    Thread thread;
    Socket so;
    InputStream in;
    OutputStream out;
    DataInputStream din;

    Image offscr;
    Graphics offscrg;
    int width , height;

    int num;
    int x[] = new int[100];        // たくさん用意しておく。
    int y[] = new int[100];
    Color colortable[] = new Color[100];

    public void init()
    {
        

        offscr = createImage( width = size().width ,
                             height = size().height );
        offscrg = offscr.getGraphics();

        // 色を初期化しておく。
        
        for(int i=0;i<100;i++){
            colortable[i] =
            new Color( (i*105)&255,(i*54)&255,(i*202)&255 );
        }
        
        try{
            so = new Socket( getParameter("host") ,
                Integer.parseInt(getParameter("port")));
            in = so.getInputStream();
            din = new DataInputStream( in );
            out = so.getOutputStream();
        }catch( IOException e ){
            System.out.println(
            "Network error. Server down? or bad hostname/port?");
        }
        thread = new Thread( this );
        thread.start();
    }

    public void paint( Graphics g )
    {
        g.drawImage( offscr , 0 , 0 ,this );
    }
    public void update( Graphics g )
    {
        paint(g);
    }
    void dispAll()
    {
        offscrg.setColor( Color.white );
        offscrg.fillRect( 0 , 0 , width , height );
        
        for(int i=0;i < num ; i++){
            offscrg.setColor( colortable[i] );    
            offscrg.fillRect( x[i] , y[i] , 8, 8 );
        }
    }
    public boolean keyDown(Event e , int c)
    {
        try{
            out.write( c );
        }catch( IOException ex ){}
        return true;
    }

    public void run()
    {
        
        while(true)
        {
            int counter = 0;
            while(true)
            {
                int tmpx=0,tmpy=0;
                try{
                    tmpx = din.readShort();
                    tmpy = din.readShort();
                }catch( IOException e ){
                    break;
                }

                if( tmpx == -1 && tmpy == -1 ){
                    dispAll();
                    repaint();
                    break;
                } else {
                    x[counter] = tmpx;
                    y[counter] = tmpy;
                    counter++;
                }
            }
            num = counter;
            try{
                Thread.sleep(100);
            }catch( InterruptedException e){}
            
        }
        
    }

}    
