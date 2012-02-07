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

    Image imgs[] = new Image[4];    // �摜���Ǘ����邽�߂�Image�^�̔z��
    int width, height;
    int interval = 50;    // ���̃A�v���b�g�ł́A�X���b�h�͎g��Ȃ��B
    Image floorimg;
    Graphics floorimg_g;

    YourNameWindow win;

    public void init()
    {
        width = size().width;      // JDK 1.0.2
        height = size().height;    // JDK 1.0.2
        // width = getSize().width;    // JDK 1.1
        // height = getSize().height;  // JDK 1.1

        // �K�v�ȉ摜�����[�h����B
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

    int fxsiz;    // �t���A�̃T�C�Y
    int fysiz;
    int field[][];       // �n�`�̏��̋L���p�z��
    int tmpfield[][];    // �S�̂���x�ɓ��������߂ɁA�ꎞ�L�����K�v�B

    // ���ׂĒn�`�̗v�f�Ƃ��ĊǗ�
    final int BLANK = 0;    // �����Ȃ����ɓ����l
    final int ROBOT = 1;    // ���{�b�g�����鏊�ɓ����l
    final int JUNK = 2;     // �W�����N�����鏊�ɓ����l
    final int PLAYER = 3;   // �v���C���[�����鏊�ɓ����l
    int plx , ply;          // �v���C���[�̈ʒu
    int stage=0;            // �X�e�[�W�ԍ�
    int wait_bonus=0;       // �E�F�C�g�i���؂�҂��j�{�[�i�X�v�Z�p�ϐ�

    Color bg1 = new Color( 0x001028 );    // �w�i��2�F�ɓh�蕪����B
    Color bg2 = new Color( 0x113322 );
    Color fg = new Color( 0xffee44 );

    int xtilesize =20,ytilesize=20;    // �^�C���̏c���̑傫��

    int score=0;    // �_��
    String yourname = "input yourname";

    Random ran = new Random();

    // �܂��A�h��Ԃ��p�̃t���A�̃C���[�W�����i�������̂��߁j
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
    // �n�`������������
    void initField()
    {
        int num;

        // �G�̐������߂�
        if( stage < 5 ) num = stage * 5 ; else num = 25 + stage;

        // �t���A��������
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                field[i][j] = BLANK;
            }
        }
        // �G��z�u
        for( int i = 0 ; i < num ; i++ ){
            int x = ( ran.nextInt() & 0xffff ) % fxsiz;
            int y = ( ran.nextInt() & 0xffff ) % fysiz;
            field[x][y] = ROBOT;
        } 
        // �v���C���[��z�u
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

    // ���̕Ԃ�l��true�̏ꍇ�́A�����Ȃ������B

    boolean walk( int rx , int ry , boolean teleport )
    {
        // ���ׂ���A��������B
        int robotno=0;

        // �t�B�[���h�̃R�s�[���ꎞ�I�ɍ��B�S�̂���ĂɐV������Ԃ�
        // �ڍs����悤�ȃQ�[���ł́A���̂悤�Ƀe���|�����̃o�b�t�@��
        // ���K�v������B�i���C�t�Q�[���Ȃǁj
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                if( field[i][j] == ROBOT ) robotno++;
                if( field[i][j] == PLAYER ){ plx = i; ply = j; }
                if( field[i][j] == JUNK) tmpfield[i][j] = JUNK; 
                else tmpfield[i][j] =BLANK;
            }
        }

        // �O�g�����O�ւ́A�����Ȃ��B
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

        // ���{�b�g������ړ�������B���̍ۂɂ́A���{�b�g���m�̓����蔻��
        // �����s����B�W�����N���V�����ł���ꍇ�͓_�������Z�B
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

        // �ł����������o�b�t�@�����̃o�b�t�@�ɃR�s�[����B
        
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                field[i][j] = tmpfield[i][j];
            }
        }

        // �G�𐔂��ĒN�����Ȃ�������N���A
        if( countEnemy(field) ==0 ){
            stage++;
            initField();
        }
        
        drawAll();
        repaint();
        
        return false;
    }
    // Windows95�p��JDK1.1�ł́A�ߋ��Ƃ̌݊�����ۂ��߂�
    // �p�ӂ���Ă���keyDown���\�b�h�΂���ł͂Ȃ��A1.1�ŉ������
    // keyListener���g������肩���ł��L�[�C�x���g�����ł��Ȃ��̂ŁA
    // �������Ȃ��}�E�X�ɂ�鑀��ɂ��Ă���B
    int gridx,gridy;
    public boolean mouseMove( Event e , int x , int y )
    {
        gridx = x / xtilesize;
        gridy = y / ytilesize;
        repaint();
        return true;
    }
    // �L�[�{�[�h�ł�����ł���悤�ɂ��Ă����B
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
                  // nextInt()���琳�̒l���������o�����@�ɒ��ځB
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
        // �}�E�X�̍��W���A�ǂ̃}�X�ɑΉ����Ă��邩�́A����Z�����
        // ���ڂɋ��߂邱�Ƃ��ł���i�d�v�j�B
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

        // ��l���̋߂����N���b�N����ƁA���ʂɕ����B
        // �������N���b�N����ƁA�e���|�[�g����B 
        // �V�t�g�L�[�������Ȃ���N���b�N����ƁA�u�҂v�B
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
            // �C�x���g�����̃��\�b�h�̒��ŕ`�悷��ɂ͂�������B������g��
            // �ƁA�D���ȃ^�C�~���O�ŕ`�悳���邱�Ƃ��ł���B���X���Z�I�B
            paint( getGraphics() );    
            c = countEnemy(tmpfield);
            if( c == 0 ){
                wait_bonus = total_robots * stage;
                score += wait_bonus;
                initField();
                break;
            }

            if( walk( 0 , 0 , false ) == true ){
                // wait���Ă�̂ɕ����Ȃ��ꍇ�A����͎����B
                die();
                break;
            }
        }
    }

    // �v���C���[�����񂾂�A���O�o�^�p�̃E�B���h�E���o���B
    void die()
    {
        win = new YourNameWindow( this , score , yourname);

        stage = score = 0;
        initField();
    }
    // �P�ɓG�̐��𐔂���B
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
        // �܂���ʑS�̂������B���̍ۂɂ͍���Ă��������̉摜��
        // 1���`�悷�邾���B
        dg.drawImage( floorimg , 0 , 0 , null );

        // �t�B�[���h��`���B
        for(int i=0;i<fxsiz;i++){
            for(int j=0;j<fysiz;j++){
                if( field[i][j] != BLANK ) 
                dg.drawImage( imgs[field[i][j]] , i*xtilesize ,j*ytilesize,this);
            }
        }
        
        // �O���b�h��`���B
        dg.drawRect( gridx*xtilesize , gridy*ytilesize , 
                    xtilesize-1,ytilesize-1 );
        // �_���\��
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
    // your.site.or.jp�ɂ́A���Ȃ���CGI��u���T�C�g�̃z�X�g���������B
    // HTML����getParam����悤�ɂ��Ă��悢���낤���AgetDocumentBase()
    // ����̂��X�}�[�g�B����͏h��Ƃ������Ƃɂ��Ă������B

    // �ȉ��̃A�h���X�́ACGI�v���O������u���ꏊ�ɉ����ĕς���B
    String cgi_url = "http://your.site.or.jp/cgi-bin/ringo/java/aho.cgi";

    YourNameWindow(robots parent , int score , String name)
    {
        super("Highscore table");
        this.score = score;
        this.parent = parent;
        
        // �R���|�[�l���g�ނ�u���B
        setLayout( new BorderLayout() );
        p = new Panel();        // Panel�N���X�����A
        p.setLayout( new BorderLayout() );
        b = new Button("Send");                   // ���M�{�^��
        t = new TextField( name );                // ���O���̓t�B�[���h
        l = new Label( "your score: " + score );  // �_���\���p���x��
        
        p.add( "West" , l );    // �p�l���ɁA�{�^���A���O���̓t�B�[���h�A���x����\��t����B
        p.add( "Center" , t );
        p.add( "East" , b );
        tarea = new TextArea( "now loading score list" ,10,20 ); 
                                  // �T�[�o����̃��b�Z�[�W�\���G���A
        add( "North" ,p );        // �p�l���ɃA�v���b�g��\��t����B
        add( "Center" , tarea );  // ���b�Z�[�W�\���G���A��\��t����B
        setForeground( new Color( 0xffee44 ));  // �F��ݒ�B
        setBackground( new Color( 0x001028 ));
        resize(320,250);  // �T�C�Y��ύX����B�iJDK 1.0.2�j        
        show();           // ���O���̓E�B���h�E�S�̂�\������B�iJDK 1.0.2�j
        // setSize(320,250);  // JDK 1.1
        // setVisible(true);  // JDK 1.1

        // �n�C�X�R�A�̕\������Ă���B
        String args[] = { "filename=robots_score" , "method=read" };
        
        // HTTPAccess�N���X���������BPOST���\�b�h���g���B
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

    // �{�^���������ꂽ�Ƃ��̏����BJDK 1.0.2�p�R�[�h
    public boolean action(Event e, Object o )
    {
        if( e.target == b ){
            if( t.getText().length() == 0 ){
                return true;
            }
            // �X�R�A��CGI�ɑ���B�����ł�HTTPAcecss���g���B
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
// �g�������́A�܂��AURL�̕�����܂���URL���w�肵�āA���������A���̌�
// getDocument���邾���B��������ƌ��ʂ�byte�z�񂪓�����B
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
    // ���ʂ̃R�l�N�V����
    HTTPAccess( URL u )
    {
        method = "GET";
        url = u;
    }
    // CGI���g���ق�
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
        // URL�𓾂�BGET���\�b�h�̏ꍇ�́AURL�̌���?��t���āA�����
        // �����ă��b�Z�[�W���������ƂɂȂ��Ă���̂ŁiHTTP�̎d�l�j�A
        // ���̂悤�ɂ���B
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
            // URLConnection�𓾂āA�l�X�̐ݒ������B
            uc = url.openConnection();
            uc.setUseCaches(false); // �u���E�U���L���b�V�����g��Ȃ��悤��
            uc.setDoInput(true);    // ��������Ȃ��ƃ_���B

            if( method.equals("POST") ){
                    uc.setDoOutput(true);  // ������d�v�B
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
            // �l�b�g���[�N�v���O�����ł́A�l�b�g���[�N����̓��͂�����
            // �I��邩�킩��Ȃ����߁Aread�͍Ō�܂œǂݍ��܂��ɏI����
            // �邱�Ƃ�����B����ɑΏ����邽�߂ɁAread�̓��[�v�̒���
            // -1��Ԃ��܂ő�����K�v������B
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
