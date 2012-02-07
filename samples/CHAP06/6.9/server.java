import java.net.*;
import java.io.*;


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

    public static void main( String args[] )
    {
        if( args.length==0){
            System.out.println("Usage: java server portno");
            System.exit(0);
        }
        server serv;
        serv = new server( Integer.parseInt(args[0]) );
    }

    int playerno = 5;
    Thread thread;
    ServerSocket servsock;
    Socket sock[] = new Socket[playerno];       // �l���������p�ӂ���B
    boolean using[] = new boolean[playerno];    // �\�P�b�g���g�p�����ǂ����B
    InputStream in[] = new InputStream[playerno];
    OutputStream out[] = new OutputStream[playerno];
    int timeout[] = new int[playerno]; // ��莞�ԑ��삪�Ȃ��Ƃ���
                                       // �^�C���A�E�g�B
    int timeout_max = 300;

    acceptor ac;

    // �v���C���[�̏��
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
            x[i] = y[i] = 50;  // �����ʒu�͓K���B
            timeout[i] = 0;
        }
        thread = new Thread(this);
        thread.start();
    }

    // acceptor����Ăяo�����B�\�P�b�g�̋󂫂�T���ēo�^����B
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
    void deletePlayer(int index)
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

    // ���͂ɉ����Ĉړ�������B
    void playerMove( int index , int key )
    {
        int dx , dy;

        switch( key)
        {
            case 'h': dx = -5; dy = 0; break;
                      case 'j': dx = 0; dy = 5; break;
                      case 'k': dx = 0; dy = -5; break;
                      case 'l': dx = 5; dy = 0; break;
                      case 'q': dx=dy=0;deletePlayer( index ); return;
                      default: dx = dy = 0; break;
                  }
        x[index] += dx;
        if( x[index] < 0 ) x[index] = 0;
        if( x[index] > 200) x[index] = 200;
        y[index] += dy;
        if( y[index] < 0 ) y[index] = 0;
        if( y[index] > 200) y[index] = 200;
        timeout[index] = 0;         // �^�C���A�E�g�܂ł̎��Ԃ����ɖ߂��B
    }
    // �S���ɑ΂��Ĉʒu�̏��𑗐M����B
    void sendForAll()
    {
        // �܂��A����������B�o�b�t�@�̃T�C�Y�̓v���g�R�����Q�ƁB

        short sendbuf[] = new short[(playerno+1)*2*2];
        int counter = 0;
        for(int i = 0 ;i < playerno ; i++){
            if( using[i] == true){
                sendbuf[counter++] = (short)x[i];
                sendbuf[counter++] = (short)y[i];
            }
        }
        sendbuf[counter++] = -1; // �f�[�^�̍Ō�Ƃ����Ӗ��B
        sendbuf[counter++] = -1;


        for(int i = 0 ; i < playerno ; i++){
            if( using[i] == true )
            {
                try{
                    DataOutputStream dout;
                    dout = new DataOutputStream( out[i] );
                    for(int j=0; j < counter ; j++){
                        dout.writeShort( sendbuf[j] );
                    }
                    dout.flush();
                    // dout.close();
                }catch( IOException e ){
                    // �����������܂������Ȃ�������A���̃v���C���[�𖕏�����B
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
            }catch( InterruptedException e ){}

            // �T�[�o�̏�Ԃ�\������B
            for(int i=0;i < playerno ;i++){

                if(using[i])System.out.print("ON");
                else System.out.print("OFF");

                System.out.print(":"+x[i]);
                System.out.print(":"+y[i]);
                System.out.print(":"+timeout[i]);
                System.out.print(" ");
            }
            System.out.println("");

            // ���ꂼ��̐ڑ��̏����B
            for(int i = 0 ; i < playerno ; i++){
                if( using[i] == true ){
                    if( (++timeout[i]) > timeout_max ){
                        deletePlayer( i );
                        continue;
                    }
                    try{
                        if( in[i].available() > 0 ){
                            // �������𑗂��Ă������B
                            int r;
                            r = in[i].read();
                            playerMove( i, r );
                            sendForAll();
                        }
                    }catch( IOException e)
                    {
                        // �\�P�b�g�������g���Ȃ��B
                        deletePlayer( i );
                    }
                }
            }
        }
    }
}