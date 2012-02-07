import java.net.*;
import java.io.*;
import java.awt.*;

public class http extends java.applet.Applet
{
    URL u;
    URLConnection uc;
    OutputStream out;
    InputStream in;
    TextField tf;
    Button btn;

    public void init()
    {
        tf = new TextField( 50 );    // 文字入力用領域
        setLayout( new BorderLayout() );
        add( "South" , tf );
        btn = new Button("load");
        add( "Center" , btn );
    }
    public boolean action( Event ev , Object o )
    {

        if( o.toString() == "load" ){
            System.out.println( tf.getText() );  // URLを得る。
            try{
                u = new URL( tf.getText() );
            }catch( MalformedURLException e ){
                System.out.println("exit 1");
                return true;
            }
            try{
                uc = u.openConnection();    // URL Connecionを得る。
                uc.setDoInput(true);        // setDoInput
            }catch( IOException e ){
                System.out.println("exit 2");
                return true;
            }
            try{
                in = uc.getInputStream();         // ストリームを得てから、
                int c;
                while(( c = in.read()) != -1)     // 入力して、
                {
                    System.out.print( (char)c );  // 出力する。
                }
            }catch( IOException e ){
                System.out.println("exit 3");
            }
        }
        return true;
    }
}