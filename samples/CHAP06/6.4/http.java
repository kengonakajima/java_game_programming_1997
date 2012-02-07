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
        tf = new TextField( 50 );    // �������͗p�̈�
        setLayout( new BorderLayout() );
        add( "South" , tf );
        btn = new Button("load");
        add( "Center" , btn );
    }
    public boolean action( Event ev , Object o )
    {

        if( o.toString() == "load" ){
            System.out.println( tf.getText() );  // URL�𓾂�B
            try{
                u = new URL( tf.getText() );
            }catch( MalformedURLException e ){
                System.out.println("exit 1");
                return true;
            }
            try{
                uc = u.openConnection();    // URL Connecion�𓾂�B
                uc.setDoInput(true);        // setDoInput
            }catch( IOException e ){
                System.out.println("exit 2");
                return true;
            }
            try{
                in = uc.getInputStream();         // �X�g���[���𓾂Ă���A
                int c;
                while(( c = in.read()) != -1)     // ���͂��āA
                {
                    System.out.print( (char)c );  // �o�͂���B
                }
            }catch( IOException e ){
                System.out.println("exit 3");
            }
        }
        return true;
    }
}