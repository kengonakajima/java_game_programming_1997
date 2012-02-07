// Grphics$B%/%i%9$N4pK\E*$JIA2h%a%=%C%I$r;H$C$??^7A$,$$$/$D$+(B
// $BF0$-$^$o$j!"2hLL$NC<$GD7$M$+$($k!#(B
//


import java.applet.Applet;
import java.awt.*;
import java.util.*;

public class move extends Applet implements Runnable
{
        Thread t;
        int x[] = new int[6];
        int y[] = new int[6];
        int vx[] = new int[6];
        int vy[] = new int[6];
        Random ran;

        public void init()
        {
                ran = new Random();
                t = new Thread( this );
                t.start();

                //$B%"%W%l%C%H$N??$sCf$N:BI8$r5a$a$k(B
                Dimension d = size();

                for(int i  = 0 ; i < 6 ; i++){
                        x[i] = d.width / 2;
                        y[i] = d.height / 2;
                        vx[i] = ran.nextInt() % 4;
                        vy[i] = ran.nextInt() % 4;
                }


        }
        public void paint( Graphics g )
        {
                g.drawRect( x[0] , y[0] , 40,40);
                g.fillRect( x[1] , y[1] , 20,20);
                g.drawLine( x[2], y[2] , x[3],y[3] );
                g.drawOval( x[4],y[4] , 20,30);
                g.drawString( "Moveing" , x[5],y[5] );
        }
        public void run()
        {
                while( true ){
                        try{ Thread.sleep( 200);}catch( InterruptedException e ){ }
                        for(int i = 0 ; i < 6; i++){
                                x[i] = x[i] + vx[i];
                                y[i] = y[i] + vy[i];
                                // $BC<$G$O$M$+$($k=hM}(B
                                if(x[i] <0 || x[i]>size().width) vx[i]= -vx[i];
                                if(y[i] <0 || y[i]>size().height) vy[i]= -vy[i];
                        }
                        repaint();
                }
        }

}
