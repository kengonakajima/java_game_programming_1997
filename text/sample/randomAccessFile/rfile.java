import java.io.*;


class rfile
{
	public static void main( String args[] )
	{
		
		try{
			RandomAccessFile rf = new RandomAccessFile("rfile.java" , "r");
			RandomAccessFile wf = new RandomAccessFile("wrotefile" , "rw");
			RandomAccessFile ff = new RandomAccessFile("wrotefile2" , "rw");
			ff.seek( ff.length() );
			for(int i=0;i< rf.length(); i++){
				rf.seek( rf.length()-i-1 );
				int c = rf.read();
				wf.write( c);
				ff.write( c);
			}
			rf.close();
			wf.close();
		}catch( IOException e ){}

	}



}
