import java.io.*;

class cgimain
{
	// メイン・ルーチンでは、自分のインスタンスを作るだけ。
	public static void main( String args[] )
	{
		cgimain c = new cgimain( args);
	}

	public final int MAXBYTE = 2000;
	byte b[] = new byte[MAXBYTE];

	String topdir;    // アクセスするファイルがあるディレクトリ。引数で指定

	cgimain( String args[] )
	{
		int querylen = 0;

		// まずGETかどうか調べ、そうでなければPOSTからだとして入力
		String getquery = System.getProperty("QUERY");
		if( getquery != null && !getquery.equals("")){
			//b = getquery.getBytes();  // JDK1.1
			getquery.getBytes( 0 , getquery.length() , b,0); //JDK1.0.2
			querylen = getquery.length();
		} else {
			try{
				querylen = System.in.read( b , 0 , MAXBYTE);
			}catch( IOException e ){System.out.println("ERROR: bad input");}
		}
		URLDecoder ud = new URLDecoder( b );


		// ブラウザに送り返す内容を書く。
		// 最初にHTTPヘッダを送信する。空行を作るための最後の'\n'が大切。
		// ここではprintlnを使うので、'\n' は一個でよい。
		System.out.println("Content-type: text/html\n");     
		System.out.println("<html>");
		System.out.println("<title>QUERY RESULT</title>");
		System.out.println("<body>");


		// どのファイルを操作するのか？
		if( args.length == 0 ){
			System.out.println("ERROR: top directory was not set.");
			return;
		} else {
			topdir = args[0];
		}

		String file_fullpath = topdir + "/" + ud.getValue("filename");



		// 実際のファイルの処理をする。実現したい内容によって、ここは
		// 変更する必要があります。
		RandomAccessFile f = null;

		if( ud.getValue("method" ).equals("append") ){
			try{
				f = new RandomAccessFile( file_fullpath , "rw");
				String val = ud.getValue("value");
				//byte[]  valbyte = val.getBytes(); // JDK1.1
				byte[] valbyte=new byte[val.length()];
				val.getBytes( 0 , val.length() , valbyte , 0 ); // JDK1.0.2

				f.seek( f.length());
				f.write( valbyte ,0,valbyte.length) ;
				f.write( '\n'); // 改行を入れる
				System.out.println("OK: append successful.");
				f.close();
			}catch( IOException e ){
				System.out.println("ERROR: Error while writing." + e);
			}
		} else if( ud.getValue("method").equals("read") ){
			try{
				f = new RandomAccessFile( file_fullpath , "r");
				byte fileb[] = new byte[ (int)f.length() ];
				System.out.println(f.read( fileb , 0 , (int)f.length() ) );
				f.close();
				System.out.println("OK: read successful.");
				System.out.write( fileb , 0 , fileb.length );
				System.out.println("");   // 最後に空行を入れる
				
			}catch( IOException e ){
				System.out.println( "ERROR: Error while reading." + e);
			}
		} else {
			System.out.println( "ERROR: method not specified." );
		}
	
	}
}

// URLDecoderがJavaコアにあればいいのですが。
// 指定された名前の項目がない場合は、空の文字列を返します。
// ascii文字以外には対応していません。
class URLDecoder
{
	int valuenum=0;
	byte newbuf[];
	int length=0;
	
	URLDecoder( byte linebuf[] )
	{
		length = linebuf.length;
		// byteToCharConverterは内部で与えられたバッファを使う
		// ので、バッファは長めにとっておく(2倍)。
		newbuf= new byte[ length*2]; 
		int counter=0;
		
		ByteArrayInputStream in = new ByteArrayInputStream( linebuf );
		for(;;){
			int c;
			c = in.read();
			if( c <= 0 ) break;
			if( c == '+' ){
				newbuf[counter++] = ' ';
				continue;
			}
			if( c == '&' || c == '\n' ){
				newbuf[counter++] = 0;
				continue;
			}
			if( c == '%' ){
				byte ib[] = new byte[2];
				ib[0] = (byte)(in.read());
				ib[1] = (byte)(in.read());
				//String is = new String( ib);   //JDK1.1
				String is = new String( ib , 0 );  //JDK1.0.2

				int i = Integer.parseInt( is, 16 );
				newbuf[counter++] = (byte)( i );
				continue;
			}
			
			newbuf[counter++] = (byte)(c);

		}

	}
	
	public String getValue( String name )
	{
		int len = name.length();
		int partlen=0;

		for(int i=0;i<length;i++){
			String sub=null;
			try{
				//sub = new String( newbuf , i , len ); //JDK1.1
				sub = new String( newbuf , 0 , i, len );  //JDK1.0.2 
			}catch( StringIndexOutOfBoundsException e){
				continue;
			}
			if( sub.equals( name )){
				for(int j=i;j<length;j++){
					if( newbuf[j]==0 ){
						
						partlen=j-i;
						break;
					}
					if( j == (length-1)){
						partlen = j-i+1;
						break;
					}
				}
				//return new String(newbuf,i+len+1,partlen-len -1);//JDK1.1
				return new String(newbuf,0,i+len+1,partlen-len-1);//JDK1.0.2
			}
		}
		return "";
	}

}






