import java.applet.Applet;
import java.applet.AudioClip;  // AudioClipを使うためのimport
import java.awt.*;
import java.awt.image.*;
import java.util.*;


public class game extends Applet implements Runnable
{


	Thread thread;
	Image double_buffer;
	Graphics dg;

	int cron=0;

	// 変更可能個所、数値のところだけ色を変える。 
	int width , height;

	final int holesize = 64;  // 穴のサイズ(正方形)

	// もぐら叩きを調整するための変更部分 
	final int yokosize = 5; // 穴の数
	final int tatesize = 4; 
	public final int interval = 100;	// ゲームの進行速度
	final int mogtime = 40;   // モグラの寿命
	final int misstime = 4;   // ミス表示の時間
	final int hittime = 4;    // ヒットした表示の時間
	final int mogblank = 8;   // 同じ穴に続けて出る時の間
	final int rate = 10;      // モグラが出現する頻度
	final int life_max = 10;  // 10回ミスしたら終わり
	final int levelunit = 10; // 10匹やっつけるごとにレベルアップ


	int mog[][] = new int[yokosize][tatesize ]; // もぐらを、必要な数だけ用意する
	int mogstate[][] = new int[yokosize][tatesize];  // モグラの状態

	final int ALIVE = 1;  // もぐらの状態は、これらの値を入れておく。
	final int MISS = 2;
	final int HIT = 3;
	final int OUT = 0;


	int score,level,life, hitno;

	MediaTracker mt; // 画像ロードのために使うMediaTracker
	Image aliveimg;  // モグラの画像(出現している時の)
	Image hitimg;    // モグラの画像(叩いた瞬間の)
	AudioClip hitsound,hellosound; // 叩いた音と出てくる音
	

	int mode;  
	final int TITLE= 1;       //modeに入れる値
	final int GAME = 2;
	final int GAMEOVER = 3;

	public void init()
	{

		width = size().width;
		height = size().height;
		thread = new Thread(this);
		double_buffer = createImage( width , height );
		dg = double_buffer.getGraphics();

		thread.start();

		/* ここからもぐら叩きのための変更部分 */
		dg.setColor( Color.black );
		dg.fillRect( 0 , 0 , width , height );


		// 使う画像をロードする。ロードが終わるまで、MediaTrackerを使って待つ
		aliveimg = getImage( getDocumentBase() , "alive.gif" );
		hitimg = getImage( getDocumentBase() , "hit.gif" );
		
		mt = new MediaTracker( this ); // 同じIDで待つ
		mt.addImage( aliveimg, 1 );
		mt.addImage( hitimg , 1 );
		try{
			mt.waitForID( 1 ); // ロード開始し、指定したIDの画像が全部ロードされるまで待つ。
		}catch( InterruptedException ie ){}

		hitsound = getAudioClip( getDocumentBase() , "yahoo.au");
		hellosound = getAudioClip( getDocumentBase() , "hi.au");
		// タイトル画面を表示
		mode = TITLE;


	}

	public void paint( Graphics g )
	{
		g.drawImage( double_buffer , 0 , 0 , null );

	}
	public void update( Graphics g )
	{
		paint( g );
	}
	public void run()
	{
		while( true )
		{
			cron++;
			try{
				Thread.sleep( interval );
			} catch( InterruptedException e ){}

			doIt();
			repaint();
		}
	}

	void doIt()
	{

		if( mode == TITLE ){
			dg.setColor( Color.black );
			dg.fillRect( 0 , 0 , width , height );
			dg.setColor( Color.white );
			dg.drawString( "DestroMogura",30, height/2 );
			dg.drawString( "press mouse button" , 30 , height/2+50);
		} else if( mode == GAME ){

		/* 変更可能個所 */
		
			// 一定時間ごとに、モグラを出現させる
			// レベルに応じて、出てくるモグラが増えていく。

			if( ( cron % rate ) == 0 ){
				int kazu;
				kazu = new Random().nextInt() & 255;
				kazu = kazu % level;
				for(int i = 0 ; i <= kazu ; i++){

					putMogura();
				}
			}
			// モグラの寿命を減らしていく。
			moveMogura();

			// 命がなくなったら、ゲームオーバー
			if( life < 0 ){
				mode = GAMEOVER;
			}
			
			// 点数などを表示、消してから書くことに注意
			dg.setColor( Color.black );
			dg.fillRect( yokosize * holesize+10,0, width - yokosize*holesize , 100);

			dg.setColor( Color.white );
			dg.drawString("Score " + score , yokosize * holesize +10 , 30);
			dg.drawString("Life " + life , yokosize * holesize+10,60);
			dg.drawString("Level " + level , yokosize * holesize+10,90);

		} else if( mode == GAMEOVER ){
			dg.setColor( Color.black );
			dg.fillRect( 0 , 0 , width , height );
			dg.setColor( Color.red );
			dg.drawString("Game Over",30, height/2 );
		}
	}
	void moveMogura( )
	{
		// 死ぬまでの時間を1減らす。死んだものは、消してしまう。
		for(int i = 0 ; i < tatesize ; i++){
			for(int j = 0 ; j < yokosize ;j++ ){
				mog[j][i]--;
				if( mog[j][i] < 0 ){
					mogstate[j][i] = OUT;
					outMogura( j , i );
				}
				if( mog[j][i] == misstime ){
					mogstate[j][i] = MISS;
					life--;
					missMogura( j , i );
				}
			}
		}
	}


	// もぐらを一匹出現させる
	void putMogura( ){
		
		int r = new Random().nextInt();
		r = r & 255; // 正の値に戻す。この結果、rには、0から255の値が入る。
		r = r % (yokosize * tatesize );
		int yoko = r % yokosize;
		int tate = r / yokosize;

		// モグラが消えてから一定時間たたないと同じ穴には登場できない
		if( mog[yoko][tate] > -mogblank ) return;
		hellosound.play(); // 出現時の音を出す
		mogstate[ yoko ][ tate ] = ALIVE;
		mog[ yoko ][ tate ] = mogtime;

		dg.setColor( Color.white );
		dg.fillRect( yoko * holesize , tate*holesize , holesize , holesize );
		dg.drawImage( aliveimg , yoko * holesize , tate*holesize ,null);
	}
	// モグラを消す。
	void outMogura( int x , int y ){
		
		dg.setColor( Color.black );
		dg.fillRect( x * holesize , y * holesize , holesize , holesize );
		dg.setColor( Color.white );
		dg.drawRect( x * holesize , y * holesize , holesize , holesize );
	}
	// 逃げ状態のモグラを表示する。
	void missMogura( int x , int y ){

		dg.setColor( Color.black );
		dg.fillRect( x * holesize+1 , y*holesize+1 , holesize-1 , holesize-1 );
		dg.setColor( Color.yellow );
		dg.drawString( "MISS" ,x*holesize , y*holesize+(holesize/2) );


	}
	//やられ状態のモグラを表示する。
	void yarareMogura( int x , int y ){
		dg.setColor( Color.red );
		dg.drawLine( x * holesize , y* holesize , x*holesize + holesize , y*holesize + holesize );
		dg.drawLine( x * holesize + holesize , y * holesize , x * holesize , y * holesize + holesize );
		dg.drawImage( hitimg , x*holesize, y*holesize ,null);
	}
	// マウスが押されたときの処理
	public boolean mouseDown( Event e , int x , int y )
	{
		if( mode == TITLE ){
		
			// もぐらを初期化
			for(int i = 0 ; i < yokosize ; i++){
				for(int j = 0 ; j < tatesize ; j++){
					mogstate[i][j] = OUT;
					mog[i][j] = 0;
				}
			}
			score = 0;
			level = 1;
			life = life_max;
			hitno = 0;
			mode = GAME;
		} else if( mode == GAME ){
			// どのモグラを叩いたのかを判定する
			int yoko ,tate; // モグラの穴の位置
			yoko = x / holesize;
			tate = y / holesize;
			
			// モグラが生きているときだけヒット。
		
			if( yoko>=0 && yoko < yokosize &&
			    tate>=0 && tate < tatesize &&
			   mogstate[yoko][tate] == ALIVE ){
				mog[yoko][tate] = hittime;
				mogstate[yoko][tate] = HIT;
				yarareMogura( yoko , tate );
				score = score + level;
				hitsound.play();  // 音をだす
				hitno++;
				// レベル
				if( (hitno % levelunit ) == 0 ){
					level++;
				}
			}
		} else if( mode == GAMEOVER ){
			mode = TITLE;
		}
		return true;
	}

}
















