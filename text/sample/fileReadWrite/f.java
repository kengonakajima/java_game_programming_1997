import java.io.*;

class f {

	static public void main(String args[] ) throws IOException
	{
		FileInputStream fi = new FileInputStream(args[0]);
		FileOutputStream fo = new FileOutputStream(args[1]);
		DataInputStream din = new DataInputStream( fi );
		DataOutputStream dout = new DataOutputStream( fo);

		while(true)
		{
			String line = din.readLine();
			if(line==null)break;
			System.out.println( line );
			//dout.writeBytes( line );
			byte buf[] = new byte[line.length()];
			line.getBytes( 0 ,line.length(), buf, 0);
			fo.write( buf );
			
		}
		fi.close();
		fo.close();

	}
}

