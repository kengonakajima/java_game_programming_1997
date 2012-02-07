import java.awt.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.io.*;

public class robots extends java.applet.Applet
{
    Image double_buffer;
    Graphics dg;

    int cron=0;

    Image imgs[] = new Image[4];    // 画像を管理するためのImage型の配列
    int width, height;
    int interval = 50;    // このアプレットでは、スレッドは使わない。
    Image floorimg;
    Graphics floorimg_g;

    YourNameWindow win;

    public void init()
    {
        width = size().width;      // JDK 1.0.2
        height = size().height;    // JDK 1.0.2
        // width = getSize().width;    // JDK 1.1
        // height = getSize().height;  // JDK 1.1

        // 必要な画像をロードする。
        imgs[ROBOT] = getImage( getDocumentBase() , "robot.gif" );
        imgs[PLAYER] = getImage( getDocumentBase() , "man.gif" );
        imgs[JUNK] = getImage( getDocumentBase() , "junk.gif" );

        double_buffer = createImage( width , height );
        dg = double_buffer.getGraphics();

        floorimg = createImage( width , height );  
        floorimg_g = floorimg.getGraphics();
        
        fxsiz = width / xtilesize;
        fysiz = height / ytilesize;

        field =  new int[fxsiz][fysiz];
        tmpfield = new int [fxsiz][fysiz];
        initFloorImage();
    
        initField();
    }

    public void paint( Graphics g )
    {
        drawAll();
        g.drawImage( double_buffer , 0 , 0 , null );

    }
    public void update( Graphics g )
    {
        paint( g );
    }

    int fxsiz;    // フロアのサイズ
    int fysiz;
    int field[][];       // 地形の情報の記憶用配列
    int tmpfield[][];    // 全体を一度に動かすために、一時記憶が必要。

    // すべて地形の要素として管理
    final int BLANK = 0;    // 何もない所に入れる値
    final int ROBOT = 1;    // ロボットがいる所に入れる値
    final int JUNK = 2;     // ジャンクがある所に入れる値
    final int PLAYER = 3;   // プレイヤーがいる所に入れる値
    int plx , ply;          // プレイヤーの位置
    int stage=0;            // ステージ番号
    int wait_bonus=0;       // ウェイト（見切り待ち）ボーナス計算用変数

    Color bg1 = new Color( 0x001028 );    // 背景は2色に塗り分ける。
    Color bg2 = new Color( 0x113322 );
    Color fg = new Color( 0xffee44 );

    int xtilesize =20,ytilesize=20;    // タイルの縦横の大きさ

    int score=0;    // 点数
    String yourname = "input yourname";

    Random ran = new Random();

    // まず、塗りつぶし用のフロアのイメージを作る（高速化のため）
    void initFloorImage()
    {
        int c=0;
        for(int i=0;i<fxsiz;i++){
            c = i;
            for(int j=0;j<fysiz;j++){
                c++;
                if( (c%2)==0){
                    floorimg_g.setColor( bg1 );
                } else {
                    floorimg_g.setColor( bg2 );
                }
                floorimg_g.fillRect( i*xtilesize , j*ytilesize , 
                                    xtilesize , ytilesize );
            }
        }
    }
    // 地形を初期化する
    void initField()
    {
        int num;

        // 敵の数を決める
        if( stage < 5 ) num = stage * 5 ; else num = 25 + stage;

        // フロアを初期化
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                field[i][j] = BLANK;
            }
        }
        // 敵を配置
        for( int i = 0 ; i < num ; i++ ){
            int x = ( ran.nextInt() & 0xffff ) % fxsiz;
            int y = ( ran.nextInt() & 0xffff ) % fysiz;
            field[x][y] = ROBOT;
        } 
        // プレイヤーを配置
        for(;;){
            int x = ( ran.nextInt() & 0xffff ) % fxsiz;
            int y = ( ran.nextInt() & 0xffff ) % fysiz;
            if( field[x][y] != BLANK ) continue;
            field[x][y] = PLAYER;
            plx = x;
            ply = y;
            break;
        }
    }

    // この返り値がtrueの場合は、歩けなかった。

    boolean walk( int rx , int ry , boolean teleport )
    {
        // 調べたり、数えたり。
        int robotno=0;

        // フィールドのコピーを一時的に作る。全体が一斉に新しい状態に
        // 移行するようなゲームでは、このようにテンポラリのバッファを
        // 作る必要がある。（ライフゲームなど）
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                if( field[i][j] == ROBOT ) robotno++;
                if( field[i][j] == PLAYER ){ plx = i; ply = j; }
                if( field[i][j] == JUNK) tmpfield[i][j] = JUNK; 
                else tmpfield[i][j] =BLANK;
            }
        }

        // 外枠よりも外へは、歩けない。
        if( (plx+rx) < 0 || (plx+rx) >=fxsiz ){
            return true;
        }
        if( (ply+ry) < 0 || (ply+ry) >=fysiz ){
            return true;
        }
        if( field[plx + rx][ply+ry] == JUNK ){
            return true;
        }

        tmpfield[plx+rx][ply+ry] = PLAYER;
        plx += rx; ply += ry;

        int score_store = score;

        // ロボットを一歩移動させる。その際には、ロボット同士の当たり判定
        // も実行する。ジャンクが新しくできる場合は点数も加算。
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                if( field[i][j] == ROBOT ){
                    int relx = 0 , rely = 0;
                    if( i > plx ) relx = -1;
                    if( i < plx ) relx = 1;
                    if( j > ply ) rely = -1;
                    if( j < ply ) rely = 1;
                    switch( tmpfield[i+relx][j+rely] ){
                        case ROBOT:
                        tmpfield[i+relx][j+rely] = JUNK;
                        score += 20;
                        break;
                        case JUNK:
                        score += 10;
                        break;
                        case PLAYER:
                        if( teleport == true ){
                            die();
                        }
                        score = score_store;
                        plx -= rx;
                        ply -= ry;
                        
                        return true;

                        case BLANK:
                        tmpfield[i+relx][j+rely] = ROBOT;
                        break;
                    }
                }
            }
        }

        // できあがったバッファを元のバッファにコピーする。
        
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                field[i][j] = tmpfield[i][j];
            }
        }

        // 敵を数えて誰もいなかったらクリア
        if( countEnemy(field) ==0 ){
            stage++;
            initField();
        }
        
        drawAll();
        repaint();
        
        return false;
    }
    // Windows95用のJDK1.1では、過去との互換性を保つために
    // 用意されているkeyDownメソッドばかりではなく、1.1で加わった
    // keyListenerを使ったやりかたでもキーイベントが飛んでこないので、
    // しかたなくマウスによる操作にしている。
    int gridx,gridy;
    public boolean mouseMove( Event e , int x , int y )
    {
        gridx = x / xtilesize;
        gridy = y / ytilesize;
        repaint();
        return true;
    }
    // キーボードでも操作できるようにしておく。
    public boolean keyDown( Event e , int c )
    {
        switch( c ){
            case 'h': walk( -1 , 0 , false ); break;
            case 'j': walk( 0 , 1 , false );break;
            case 'k': walk( 0 ,-1 , false );break;
            case 'l': walk( 1 , 0 , false );break;
            case 'y': walk( -1,-1, false );break;
            case 'u': walk( 1,-1,false );break;
            case 'b': walk( -1,1,false );break;
            case 'n': walk( 1,1,false );break;
            case 't':     
                  // nextInt()から正の値だけを取り出す方法に注目。
                  int destx = ( ran.nextInt() &0xff)%fxsiz ;
                  int desty = ( ran.nextInt() &0xff)%fysiz ;
                  walk( destx-plx , desty-ply , true );
                  break;
            case 'w':
                  waitRobots();
                  break;
            case ' ': walk(0,0,false );break;
            
        }
        return true;
    }
    public boolean mouseDown( Event e , int x , int y )
    {
        // マウスの座標が、どのマスに対応しているかは、割り算すれば
        // 直接に求めることができる（重要）。
        gridx = x / xtilesize;
        gridy = y / ytilesize;
        int dx =808, dy=808;
        boolean ret;

        if( gridx == ( plx + 1) ) dx = 1;
        else if( gridx == (plx -1 )) dx = -1;
        else if( gridx == plx ) dx = 0;
        if( gridy == ( ply + 1) ) dy = 1;
        else if( gridy == (ply -1 )) dy = -1; 
        else if( gridy == ply ) dy = 0;

        // 主人公の近くをクリックすると、普通に歩く。
        // 遠くをクリックすると、テレポートする。 
        // シフトキーを押しながらクリックすると、「待つ」。
        if( ( e.modifiers & Event.SHIFT_MASK ) != 0){
            waitRobots();
        } else
        if( dx >= -1 && dx <= 1 && dy >= -1 && dy <= 1 ){
            walk( dx,dy,false );
            
        } else {
            int destx = ( ran.nextInt() &0xff)%fxsiz ;
            int desty = ( ran.nextInt() &0xff)%fysiz ;
            walk( destx-plx , desty-ply , true );
        }
    
        return true;
    }
    void waitRobots()
    {

        int c;
        int total_robots = countEnemy(field);
        for(;;){
            // イベント処理のメソッドの中で描画するにはこうする。これを使う
            // と、好きなタイミングで描画させることができる。少々裏技的。
            paint( getGraphics() );    
            c = countEnemy(tmpfield);
            if( c == 0 ){
                wait_bonus = total_robots * stage;
                score += wait_bonus;
                initField();
                break;
            }

            if( walk( 0 , 0 , false ) == true ){
                // waitしてるのに歩けない場合、それは死だ。
                die();
                break;
            }
        }
    }

    // プレイヤーが死んだら、名前登録用のウィンドウを出す。
    void die()
    {
        win = new YourNameWindow( this , score , yourname);

        stage = score = 0;
        initField();
    }
    // 単に敵の数を数える。
    int countEnemy(int [][]f)
    {
        int counter=0;
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                if( f[i][j] == ROBOT ) counter++;
            }
        }
        return counter;
    }

    void drawAll()
    {
        // まず画面全体を消す。その際には作っておいた床の画像を
        // 1枚描画するだけ。
        dg.drawImage( floorimg , 0 , 0 , null );

        // フィールドを描く。
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                if( field[i][j] != BLANK ) 
                dg.drawImage( imgs[field[i][j]] , i*xtilesize ,j*ytilesize,this);
            }
        }
        
        // グリッドを描く。
        dg.drawRect( gridx*xtilesize , gridy*ytilesize , 
                    xtilesize-1,ytilesize-1 );
        // 点数表示
        dg.setColor( fg );
        dg.drawString( "SCORE " + score + "/STAGE " +
                      stage + "/Wait Bonus " + wait_bonus,10,30);
    }
}

class YourNameWindow extends Frame
{
    Button b;
    TextField t;
    Label l;
    Panel p;
    TextArea tarea;

    int score;
    robots parent;
    // your.site.or.jpには、あなたがCGIを置くサイトのホスト名を書く。
    // HTMLからgetParamするようにしてもよいだろうし、getDocumentBase()
    // するのもスマート。それは宿題ということにしておこう。

    // 以下のアドレスは、CGIプログラムを置く場所に応じて変える。
    String cgi_url = "http://your.site.or.jp/cgi-bin/ringo/java/aho.cgi";

    YourNameWindow(robots parent , int score , String name)
    {
        super("Highscore table");
        this.score = score;
        this.parent = parent;
        
        // コンポーネント類を置く。
        setLayout( new BorderLayout() );
        p = new Panel();        // Panelクラスを作り、
        p.setLayout( new BorderLayout() );
        b = new Button("Send");                   // 送信ボタン
        t = new TextField( name );                // 名前入力フィールド
        l = new Label( "your score: " + score );  // 点数表示用ラベル
        
        p.add( "West" , l );    // パネルに、ボタン、名前入力フィールド、ラベルを貼り付ける。
        p.add( "Center" , t );
        p.add( "East" , b );
        tarea = new TextArea( "now loading score list" ,10,20 ); 
                                  // サーバからのメッセージ表示エリア
        add( "North" ,p );        // パネルにアプレットを貼り付ける。
        add( "Center" , tarea );  // メッセージ表示エリアを貼り付ける。
        setForeground( new Color( 0xffee44 ));  // 色を設定。
        setBackground( new Color( 0x001028 ));
        resize(320,250);  // サイズを変更する。（JDK 1.0.2）        
        show();           // 名前入力ウィンドウ全体を表示する。（JDK 1.0.2）
        // setSize(320,250);  // JDK 1.1
        // setVisible(true);  // JDK 1.1

        // ハイスコアの表を取ってくる。
        String args[] = { "filename=robots_score" , "method=read" };
        
        // HTTPAccessクラスを初期化。POSTメソッドを使う。
        HTTPAccess ha=null;
        String listcontents=null;
        try{
            ha = new HTTPAccess( cgi_url , "POST" , args );
            // listcontents  = new String( ha.getDocument() ); // JDK 1.1
            byte buf[] = ha.getDocument();  // JDK 1.0.2
            listcontents = new String( buf , 0 , 0 , buf.length ); //JDK 1.0.2
        }catch( Exception e ){
            System.out.println( e ); 
            listcontents = "Network Error.\nPlease reconfigure\n" + 
            "appletviewer network properties.\n";
        }
        tarea.setText( listcontents );
    }

    // ボタンが押されたときの処理。JDK 1.0.2用コード
    public boolean action(Event e, Object o )
    {
        if( e.target == b ){
            if( t.getText().length() == 0 ){
                return true;
            }
            // スコアをCGIに送る。ここでもHTTPAcecssを使う。
            String args[] = 
            { "filename=robots_score" , 
              "method=append" ,"value="+  t.getText()+":"+score};
            try{
                HTTPAccess ha = new HTTPAccess( cgi_url , "POST" , args );
                byte [] ret = ha.getDocument();
                System.out.println( new String(ret ,0,0,ret.length) ); // 1.0.2
            }catch( Exception ex ){
                System.out.println( ex );
            }
            parent.yourname = t.getText();
            hide();   // JDK 1.0.2
            // setVisible( false );  // JDK 1.1
        }
        return true;
    }

    public String getName()
    {
        return t.getText();
    }
}
// 使いかたは、まず、URLの文字列またはURLを指定して、初期化し、その後
// getDocumentするだけ。そうすると結果のbyte配列が得られる。
class HTTPAccess
{
    URL url;
    URLConnection uc;
    String request_string;
    String method;

    int read_unit = 1000;


    HTTPAccess( String location , String method , String[] values ) 
    throws MalformedURLException
    {
        this( new URL( location ) , method , values );
    }
    HTTPAccess( String location ) throws MalformedURLException
    {
        this( new URL( location ) );
    }
    // 普通のコネクション
    HTTPAccess( URL u )
    {
        method = "GET";
        url = u;
    }
    // CGIを使うほう
    HTTPAccess( URL u , String method , String[] values )
    throws MalformedURLException
    {
        request_string = "";
        this.method = method;

        for(int i=0;i< values.length;i++){
            request_string += URLEncoder.encode( values[i] );
            if( i == (values.length-1) )break;
            request_string += "&";
        }
        // URLを得る。GETメソッドの場合は、URLの後ろに?を付けて、それに
        // 続けてメッセージを書くことになっているので（HTTPの仕様）、
        // そのようにする。
        if( method.equals("GET") ){
            url = new URL( u.toExternalForm() + "?" +  request_string );

        } else if( method.equals("POST") ){
            url = u;
        }

    }

    byte[] getDocument() throws Exception
    {
        
        ByteArrayOutputStream baout=null;
        try{
            // URLConnectionを得て、様々の設定をする。
            uc = url.openConnection();
            uc.setUseCaches(false); // ブラウザがキャッシュを使わないように
            uc.setDoInput(true);    // これをやらないとダメ。

            if( method.equals("POST") ){
                    uc.setDoOutput(true);  // これも重要。
                    OutputStream out = uc.getOutputStream();
                    //byte b[] = request_string.getBytes();  // JDK 1.1

                    byte b[] = new byte[10000]; // JDK 1.0.2
                    request_string.getBytes(    // JDK 1.0.2
                            0, request_string.length(),b,0 ); // JDK 1.0.2
                    
                    out.write( b , 0 , b.length );
                    out.write( '\n' );
                    out.close();
            }
            DataInputStream in =
            new DataInputStream( uc.getInputStream() );
            baout = new ByteArrayOutputStream();

            byte  b[] = new byte[read_unit];
            // ネットワークプログラムでは、ネットワークからの入力がいつ
            // 終わるかわからないため、readは最後まで読み込まずに終了す
            // ることがある。それに対処するために、readはループの中で
            // -1を返すまで続ける必要がある。
            while(true)
            {
                int nread = in.read( b, 0 , read_unit );
                if( nread == -1 ) break;
                baout.write( b , 0 , nread );
            }
            in.close();
            baout.close();
        
        }catch( Exception e ){    throw e; }
        return baout.toByteArray();
    }
}
