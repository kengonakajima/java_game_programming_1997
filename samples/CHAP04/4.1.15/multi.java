import java.awt.*;

class counter implements Runnable
{
    Thread t;
    int val=0;
    long milisec;
    counter(int i)
    {
        t = new Thread( this );
        t.start();
        milisec = i;
    }
    public void run()
    {
        while(true)
        {
            val++;
            try{
                Thread.sleep( milisec );
            }catch( InterruptedException e ){}
        }
    }
    public int getVal(){ 
        return val;
    }
}
public class multi extends java.applet.Applet
{
    counter ca[] = new counter[10];

    public void init()
    {
        for(int i=0;i<10;i++){
            ca[i] = new counter(i*100+100);
        }
    }
    public void paint(Graphics g)
    {
        for( int i=0;i < 10 ;i++ ){
            g.drawString( "counter " + i + ":" + ca[i].getVal() ,
                         20 , 20 + i*20  );
        }
        try{      
            Thread.sleep(50);    // ������50�~���b�҂B
        }catch( InterruptedException e ){}

        repaint(); // �`�悪�I������������x�`�悷�邽�߂̎蔲���R�[�h�B
                   // ����ŁA�A���I�ɕ`�悳��邱�ƂɂȂ�B
            
    }
    public boolean mouseDown( Event e , int x , int y )
    {
        repaint();
        return true;
    }
}