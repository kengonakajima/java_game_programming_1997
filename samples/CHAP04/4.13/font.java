import java.awt.*;

public class font extends java.applet.Applet
{
    String font_names[];
    public void init(){
        // まず、使用可能なフォントを探す。
        font_names = Toolkit.getDefaultToolkit().getFontList();
        for(int i=0;i<font_names.length;i++){
            System.out.println( font_names[i] );
        }
    }
    public void paint( Graphics g ){
        // それぞれのフォントで、文字を描画する。色もサイズも
        // 変化させてみよう。
        g.setColor( Color.white );
        g.fillRect( 0 , 0 , size().width , size().height ); // 白で塗りつぶし
        for(int i=0;i<font_names.length;i++){
            Font f = new Font( font_names[i] , Font.PLAIN , 15+i*5 );
            g.setFont( f ); 
            g.setColor( new Color((int)( 0xffffff * Math.random()) ) );
            g.drawString( "God said 'are you here?'" , 20,40*i+50 );
        }
    }
}