import java.io.*;

class rfile
{
    public static void main( String args[] )
    {
        try{
            RandomAccessFile rf = new RandomAccessFile("rfile.java" , "r");
            RandomAccessFile wf = new RandomAccessFile("wrotefile" , "rw");

            for(int i=0; i< rf.length(); i++){
                rf.seek( rf.length()-i-1 ); // Å‰‚Í0‚¾‚©‚ç1‚ðˆø‚­B
                int c = rf.read();
                wf.write( c );
            }
            rf.close();
            wf.close();
        }catch( IOException e ){}
    }
}