import java.awt.*;
public class OffScr extends java.applet.Applet implements Runnable
{
    Thread t;
    int x = 0;
    Image ofscr; // ���z��ʁi�I�t�X�N���[���A�_�u���o�b�t�@�Ƃ������j
    Graphics ofscr_g;       // ���z��ʂ𑀍삷�邽�߂�Graphics

    public void init()
    {
        t = new Thread( this );
        t.start();
        // ���̍s�ɂ��A���z��ʂ́A�^�����܂��͐^�����ɏ����������B
        // �g���O�ɁA�K�v�ȐF�œh��Ԃ��Ă��K�v������B
        ofscr = createImage( 300,300 );  
        ofscr_g = ofscr.getGraphics();
    }
    public void paint( Graphics g )
    {
        ofscr_g.setColor( Color.white);
        ofscr_g.fillRect( 0 , 0 , 300, 300 );   // �S��ʂ������B
        ofscr_g.setColor( Color.black );
        ofscr_g.fillRect( x,50,200,200 );       // �ړI�̎l�p��`�悷��B
        g.drawImage( ofscr , 0 , 0 , null );
    }
    public void update( Graphics g )
    {
        paint( g );
    }
    public void run()
    {
        while(true){
            x++;
            try{
                Thread.sleep( 100 );
            }catch( InterruptedException e ) {}
            repaint();
        }
    }
}
