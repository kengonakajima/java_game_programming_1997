import java.awt.*;

public class key extends java.applet.Applet implements Runnable
{
    Image offscr;
    Graphics og;
    int x , y;      // �����l�p�̈ʒu
    boolean down[] = new boolean [4]; // down[0]����A�ȉ��E���

    int houkou;
    Thread t;        // �L�������p���I�ɓ��������߂ɃX���b�h���g���B
    final int UP = 1004; // �萔�͂��̂悤�ɍŏ��ɒ�`���Ă������B
    final int DOWN = 1005; // int�̑O��final��t����ƁA�萔�̈Ӗ��ɂȂ�B
    final int LEFT = 1006; 
    final int RIGHT = 1007;
    final int STOP = 0;

    public void init( ){
        offscr = createImage( size().width , size().height );
        og = offscr.getGraphics();
        og.setColor( Color.white );
        og.fillRect( 0 , 0 , size().width , size().height );

        houkou = STOP;  // �ŏ��͎~�܂��Ă���B
        x = size().width/2;  // �ŏ��͐^�񒆂ɂ���B
        y = size().height/2;

        for(int i = 0 ; i < 4 ; i++) down[i] = false;

        t = new Thread( this );
        t.start();
    }
    public boolean keyDown( Event e , int key){

        // �J�[�\���L�[�������ƁA�ϐ�key�ɉ������L�[�̔ԍ���������B
        System.out.println( key );      
        if( key == UP ) down[0] = true;
        if( key == RIGHT ) down[1] = true;
        if( key == DOWN ) down[2] = true;
        if( key == LEFT ) down[3] = true;

        return true;
    }
    public boolean keyUp( Event e , int key ){
        System.out.println( key );
        if( key == UP ) down[0] = false;
        if( key == RIGHT ) down[1] = false;
        if( key == DOWN ) down[2] = false;
        if( key == LEFT ) down[3] = false;

        return true;
    }
    public void run()
    {
        while(true){

            if( down[0] ) y = y - 4; // ���ꂼ��̃L�[��������Ă��邩�H
            if( down[1] ) x = x + 4;
            if( down[2] ) y = y + 4;
            if( down[3] ) x = x - 4;

            og.setColor( Color.white );
            og.fillRect( 0 , 0, size().width , size().height );
            og.setColor( Color.black );
            og.fillRect( x,y,10,10 );

            repaint();

            try{
                Thread.sleep(70);
            }catch( InterruptedException e ){}
        }
    }
    public void update( Graphics g ){
        paint(g);
    }
    public void paint( Graphics g ){
        try {
            g.drawImage( offscr , 0 , 0 , this );
        } catch( NullPointerException e ){
            System.out.println("null paint");
        }
    }
}