import java.awt.*;
public class mt extends java.applet.Applet
{
    MediaTracker m;      // �܂��AMediaTracker�N���X�̃C���X�^���X�����B
    Image img;

    public void init()
    {
        img = getImage( getDocumentBase() , "kawai.jpg" ); // �����͓����B
        m = new MediaTracker(this);
        m.addImage( img, 0 ); // MediaTracker�̎d�����X�g�ɉ�����B
        try{
            m.waitForID(0);      // ���[�h���I���܂ő҂i�������[�h�j�B
        }catch( InterruptedException e ){}
    }
    public void paint(Graphics g){
        g.drawImage( img , 0 , 0 , this );
    }
}
