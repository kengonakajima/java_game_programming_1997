// serverの決定バージョン。これに変更を加えたら、文章の方にも変更を加える必要がある。
// 注意せよ。


// 何でもよいから、プロトコルを決める必要がある。
// 入力は、キー入力だから、1バイト送信すればよい。これは図にして説明すること。
// 出力は、shortの値2個セットの座標を人数分送信して、最後に(-1,-1)を送って終りとする。
// クライアントの側では、-1がくるまで待って、来たら全体を一気に更新する。
// サーバは、誰かが何かを入力する度に全体を送信する。だから人数が多いと大変だ。
// 入力だけをやりとりするようなデザインもあるということを説明する。しかし、遅延の大きい
// システムでは、それは辛いということも説明する。サーバーは、世界の同期を取るために
// 使うことができるということだ。

// 動作実験は、Netscapeはもちろんダメで、appletviewerだと、
/*

 server     client
 UNIX       UNIX	 .... totally ok
 UNIX       windows95    .... Totally ok
 windows95  UNIX	 .... TOtally ok
 windows95  windows95    .... totally ok

環境は、EtherNetでIP接続されているマシン同士。インターネット上でもproxyがない限り
同じ結果になると思われる。

*/


import java.net.*;
import java.io.*;

class serverMain
{
	
	public static void main( String args[] )
	{
		server serv;
		serv = new server(9000);
	}
}
class acceptor implements Runnable
{
	ServerSocket servsock;
	server parent;
	Thread t;
	
	acceptor(ServerSocket ss, server parent)
	{
		servsock = ss;
		this.parent = parent;
		t = new Thread( this );
		t.start();
	}
	public void run()
	{
		System.out.println("wait for new connection.");
		while(true)
		{
			Socket tmpsock;
			try{
				tmpsock = servsock.accept();
			} catch( IOException e ){
				System.out.println("accept error.");
				tmpsock = null;
			}
			System.out.println("New socket. " + tmpsock);
			parent.addSocket( tmpsock );
		}
	}
}

class server implements Runnable
{

	int playerno = 5;
	Thread thread;
	ServerSocket servsock;
	Socket sock[] = new Socket[playerno];		// 人数分だけ用意する。
	boolean using[] = new boolean[playerno];        // ソケットが使用中かどうか 
	InputStream in[] = new InputStream[playerno];
	OutputStream out[] = new OutputStream[playerno];
	int timeout[] = new int[playerno];		// 一定時間操作がない時はタイムアウトする。
	int timeout_max = 300;

	acceptor ac;

	// プレイヤーの情報
	int x[] = new int[playerno];
	int y[] = new int[playerno];
	
	server(int port)
	{
		try{
			ServerSocket servsock = new ServerSocket( port );
			ac = new acceptor( servsock , this );
		}catch( IOException e ){
			System.out.println("Can't make ServerSocket.");
		}
		
		for(int i = 0 ; i < playerno ; i++){
			using[i] = false;
			x[i] = y[i] = 50;  // 初期位置は適当
			timeout[i] = 0;
		}
		thread = new Thread(this);
		thread.start();
	}
	
	// acceptorから呼びだされる。ソケットの空きを探して登録する
	public void addSocket( Socket soc )
	{
		if( soc == null ) return;
		for(int i = 0 ; i < playerno ; i++){
			if( using[i] == false ){
				sock[i] = soc;
				try{
					in[i] = soc.getInputStream();
					out[i] = soc.getOutputStream();
				} catch( IOException e ){
					System.out.println( "getting IN/OUT stream error.");
					return;
				}
				using[i] = true;
				System.out.println( "player " + i + " is added.");
				return;
			}
		}
		System.out.println("player full.");
		try{
			soc.close();
		}catch( IOException e ){}

	}
	void deletePlayer(int index )
	{
		timeout[index] = 0;
		try{
			sock[index].close();
			in[index].close();
			out[index].close();
		}catch( IOException exc ){}
		using[index] = false;
		System.out.println("Closed socket. player="+index);
	}
	
	// 入力に応じて移動させる
	void playerMove( int index , int key)
	{
		int dx , dy;

		switch( key)
		{
			case 'h': dx = -5; dy = 0; break;
			case 'j': dx = 0; dy = 5; break;
			case 'k': dx = 0; dy = -5; break;
			case 'l': dx = 5; dy = 0; break;
			case 'q': dx=dy=0;deletePlayer( index );return;
			default: dx = dy = 0; break;
		}
		x[index] += dx;
		if( x[index] < 0 ) x[index] = 0;
		if( x[index] > 200) x[index] = 200;
		y[index] += dy;
		if( y[index] < 0 ) y[index] = 0;
		if( y[index] > 200) y[index] = 200;
		timeout[index] = 0;			// タイムアウトまでの時間を元に戻す
	}
	// 全員に対して位置の情報を送信する
	void sendForAll()
	{
		// まず、送る情報を作る。バッファのサイズはプロトコルを参照
		
		short sendbuf[] = new short[(playerno+1)*2*2];
		int counter = 0;
		for(int i = 0 ;i < playerno ; i++){
			if( using[i] == true){
				sendbuf[counter++] = (short)x[i];
				sendbuf[counter++] = (short)y[i];
			}
		}
		sendbuf[counter++] = -1; // データの最後という意味
		sendbuf[counter++] = -1;
		
		
		for(int i = 0 ;i < playerno ; i++){
			if( using[i] == true )
			{
				try{
					DataOutputStream dout;
					dout = new DataOutputStream( out[i] );
					for(int j=0; j < counter ; j++){
						dout.writeShort( sendbuf[j] );
					}
					dout.flush();
					//dout.close();
				}catch( IOException e ){
					// 書きこみがうまくいかなかったら、そのプレイヤーを抹消
					System.out.println("dout ex.");
					deletePlayer( i );
				}
			}
		}
	}
	public void run()
	{
		while(true)
		{
			try{
				Thread.sleep(200);
			}catch( InterruptedException e){}

			// サーバーの状態を表示
			for(int i=0;i < playerno ;i++){
				
				if(using[i])System.out.print("ON"); else System.out.print("OFF");
				System.out.print(":"+x[i]);
				System.out.print(":"+y[i]);
				System.out.print(":"+timeout[i]);
				System.out.print(" ");
			}
			System.out.println("");
			
			// それぞれの接続の処理

			for(int i = 0  ; i < playerno ; i++){
				if( using[i] == true ){
					if( (++timeout[i]) > timeout_max){
						deletePlayer( i );
						continue;
					}
					
					try{
						if( in[i].available() > 0 ){
							// 何か情報を送ってきたぞ
							int r;
							
							r = in[i].read();
							playerMove( i, r );
							sendForAll();
							
						}
					}catch( IOException e)
					{
						// ソケットがもう使えない
						deletePlayer( i );
					}
				}
			}
		}
	}
}




