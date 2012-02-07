package ringo;

import java.awt.*;
import java.io.*;


public class ConsoleFrame extends Frame
{
    private TextArea ta;
    private Button clear_b;
    private Button save_b;
    private Button close_b;
    private Panel p ;

    ConsoleFrame( String name ){
        super( name );
        setLayout( new BorderLayout() );
        ta = new TextArea(    "" ,20,10);
        clear_b = new Button("Clear");
        save_b = new Button("Save to java.log");
        close_b = new Button("Close");
        p = new Panel();
        p.setLayout( new BorderLayout() );
        p.add( "East" , close_b );
        p.add( "West" , save_b );
        p.add( "Center" , clear_b );
        add( "Center" , ta );
        add( "South" ,  p );

    }
    public boolean action( Event e , Object o ){
        if( e.target== clear_b ){
            ta.replaceText( "" , 0 , ta.getText().length()  );  // JDK 1.0.2
            // ta.replaceRange( "" , 0 , ta.getText().length()  ); // JDK 1.1
        }
        if( e.target == save_b ){
            try{
                FileOutputStream fout =
                new FileOutputStream( "java.log" );
                String contents = ta.getText();
                byte b[] = new byte[ contents.length() ];
                // b = contents.getBytes(); // JDK 1.1
                contents.getBytes( 0 , contents.length() , b , 0 ); //JDK 1.0.2
                fout.write( b , 0 , b.length );
                fout.close();
                ta.appendText( "\nsaved to java.log\n" ); // JDK 1.0.2
                // ta.append( "\nsaved to java.log\n" );  // JDK 1.1 
            }catch( Throwable t ){
                ta.appendText( "\njava console: file save error:\n" + t ); 
                                                               // JDK 1.0.2
                // ta.append( "\njava console: file save error:\n" + t ); 
                                                               //JDK1.1
            }
        }
        if( e.target == close_b ){
            hide(); // JDK 1.0.2
        }
        return true;
    }
    public void printText( String s ){
        // ta.append( s );    // JDK 1.1
        ta.appendText( s );   // JDK 1.0.2
    }
}