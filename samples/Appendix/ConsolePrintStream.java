package ringo;

import java.io.*;
import java.awt.*;
import ringo.ConsoleFrame;

/*
   System.setErr()などに渡すことができるコンソール。
*/
public class ConsolePrintStream extends PrintStream       // JDK 1.0.2
// public class ConsolePrintStream extends PrintWriter    // JDK 1.1

{
    volatile private boolean initflg = false;
    ConsoleFrame f;

    public ConsolePrintStream(){
        super( new ByteArrayOutputStream() );
    }

    void init(){
        try{
            f = new ConsoleFrame("java console");
            f.resize(400,300);  // JDK 1.0.2
            // f.setSize(400,300);   // JDK 1.1
            f.show();//JDK1.0.2
            // f.setVisible(true);  // JDK 1.1
        }catch( Throwable t ){ }
    }
    public  void print( String s ){
        try{
            if( initflg == false ){
                initflg = true;
                init();
            }
            f.printText( s ); 
        }catch( Throwable t ){}
    }
    public  void println( String s ){ print( s + "\n" ); }
    public  void println( int x ){    println( "" + x ); }
    public  void println( byte x ){   println( "" + x ); }
    public  void println( float x ){  println( "" + x ); }
    public  void println( double x ){ println( "" + x ); }
    public  void println( boolean x ){println( "" + x ); }
    public  void println( char x ){   println( "" + x ); }
    public  void println( long x ){   println( "" + x ); }
    public  void println( Object x ){ println( "" + x ); }
    public  void println( char[] x ){ println(new String( x ) ); }
    public  void print( char[] x){    print( new String( x ) );  }
    public  void print( int x ){      print( "" + x ); }
    public  void print( byte x ){     print( "" + x ); }
    public  void print( float x ){    print( "" + x ); }
    public  void print( double x ){   print( "" + x ); }
    public  void print( boolean x ){  print( "" + x ); }
    public  void print( char x ){     print( "" + x ); }
    public  void print( long x ){     print( "" + x ); }
    public  void print( Object x ){   print( "" + x ); }

    public  void write( byte[] buf , int si , int len ) 
    {
        // print( new String( buf , si , len ) ); // JDK 1.1
        print( new String( buf , 0 , si, len ) ); // JDK 1.0.2
    }
    public void write( int x )
    {
        print( "" + (char)x );
    }

    public void close(){
        f.hide();  // JDK 1.0.2
        // f.setVisible(false);// JDK 1.1
        super.close();
    }
}
