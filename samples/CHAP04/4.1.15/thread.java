import java.awt.*;

public class thread extends java.applet.Applet implements Runnable   
{
    Thread th;                // Thread�^�̕ϐ���錾����B

    public void init()
    {
        th = new Thread( this );    // �X���b�h���X�^�[�g����B
        th.start();
    }
    public void paint(Graphics g)
    {
        // �����𕶎��ɕϊ�����ɂ́AInteger�N���X���g���B
        g.drawString( Integer.toString( i ) , 100 , 100 );
    }

    public boolean mouseDown( Event e , int x , int y )
    {
        // ....... �}�E�X���������Ƃ��̏��������ʂɏ����Ă�����B
        return true;
    }

    int i=0;
    public void run() // ���̃��\�b�h���K�v�B
    {
        while(true){    // �����Ƒ�����ꍇ�́A���[�v������K�v������B
            i++;
            try{        // ����3�s���d�v�B
                Thread.sleep( 100 );
            }catch( InterruptedException e ) {}
            repaint();
        }
    }
}