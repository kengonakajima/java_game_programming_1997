import java.awt.*;

public class font extends java.applet.Applet
{
    String font_names[];
    public void init(){
        // �܂��A�g�p�\�ȃt�H���g��T���B
        font_names = Toolkit.getDefaultToolkit().getFontList();
        for(int i=0;i<font_names.length;i++){
            System.out.println( font_names[i] );
        }
    }
    public void paint( Graphics g ){
        // ���ꂼ��̃t�H���g�ŁA������`�悷��B�F���T�C�Y��
        // �ω������Ă݂悤�B
        g.setColor( Color.white );
        g.fillRect( 0 , 0 , size().width , size().height ); // ���œh��Ԃ�
        for(int i=0;i<font_names.length;i++){
            Font f = new Font( font_names[i] , Font.PLAIN , 15+i*5 );
            g.setFont( f ); 
            g.setColor( new Color((int)( 0xffffff * Math.random()) ) );
            g.drawString( "God said 'are you here?'" , 20,40*i+50 );
        }
    }
}