import java.applet.AudioClip;  // AudioClip���g�����߂�import
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.applet.Applet;


public class game extends Applet implements Runnable
{


    Thread thread;
    Image double_buffer;
    Graphics dg;

    int cron=0;

    // �ύX�\�ӏ��A���l�̂Ƃ��낾���F��ς���B
    int width , height;

    final int holesize = 64;  // ���̃T�C�Y(�����`)

    // ���O���@���𒲐����邽�߂̕ύX���� 
    final int yokosize = 5; // ���̐�
    final int tatesize = 4; 
    public final int interval = 100;    // �Q�[���̐i�s���x
    final int mogtime = 40;   // ���O���̎���
    final int misstime = 4;   // �~�X�\���̎���
    final int hittime = 4;    // �q�b�g�����\���̎���
    final int mogblank = 8;   // �������ɑ����ďo��Ƃ��̊�
    final int rate = 10;      // ���O�����o������p�x
    final int life_max = 10;  // 10��~�X������I���
    final int levelunit = 10; // 10�C������邲�ƂɃ��x���A�b�v


    int mog[][] = new int[yokosize][tatesize ]; // ���O�����A�K�v�Ȑ�����
                                                // �p�ӂ���B
    int mogstate[][] = new int[yokosize][tatesize];  // ���O���̏��

    final int ALIVE = 1;  // ���O���̏�Ԃ́A�����̒l�����Ă����B
    final int MISS = 2;
    final int HIT = 3;
    final int OUT = 0;


    int score, level, life, hitno;

    MediaTracker mt; // �摜���[�h�̂��߂Ɏg��MediaTracker
    Image aliveimg;  // ���O���̉摜�i�o�����Ă���Ƃ��́j
    Image hitimg;    // ���O���̉摜�i�@�����u�Ԃ́j
    AudioClip hitsound,hellosound; // �@�������Əo�Ă��鉹
    

    int mode;  
    final int TITLE = 1;       // mode�ɓ����l
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

        /* ��������A���O���@���̂��߂̕ύX���� */
        dg.setColor( Color.black );
        dg.fillRect( 0 , 0 , width , height );


        // �g���摜�����[�h����B���[�h���I���܂�MediaTracker���g���đ҂B
        aliveimg = getImage( getDocumentBase() , "alive.gif" );
        hitimg = getImage( getDocumentBase() , "hit.gif" );
        
        mt = new MediaTracker( this ); // ����ID�ő҂B
        mt.addImage( aliveimg, 1 );
        mt.addImage( hitimg , 1 );
        try{
            mt.waitForID( 1 ); // ���[�h�J�n���A�w�肵��ID�̉摜��
                               // �S�����[�h�����܂ő҂B
        }catch( InterruptedException ie ){}

        hitsound = getAudioClip( getDocumentBase() , "yahoo.au" );
        hellosound = getAudioClip( getDocumentBase() , "hi.au" );
        // �^�C�g����ʂ�\���B
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
            dg.drawString( "press mouse button" , 30 , height/2+50 );
        } else if( mode == GAME ){

        /* �ύX�\�ӏ� */
        
            // ��莞�Ԃ��ƂɁA���O�����o��������B
            // ���x���ɉ����āA�o�Ă��郂�O���������Ă����B

            if( ( cron % rate ) == 0 ){
                int kazu;
                kazu = new Random().nextInt() & 255;
                kazu = kazu % level;
                for(int i = 0 ; i <= kazu ; i++){
                    putMogura();
                }
            }
            // ���O���̎��������炵�Ă����B
            moveMogura();

            // �����Ȃ��Ȃ�����A�Q�[���I�[�o�[�B
            if( life < 0 ){
                mode = GAMEOVER;
            }
            
            // �_���Ȃǂ�\���A�����Ă��珑�����Ƃɒ��ӁB
            dg.setColor( Color.black );
            dg.fillRect( yokosize * holesize+10,0, 
                         width - yokosize*holesize , 100 );

            dg.setColor( Color.white );
            dg.drawString("Score " + score , yokosize * holesize +10 , 30);
            dg.drawString("Life " + life , yokosize * holesize+10,60);
            dg.drawString("Level " + level , yokosize * holesize+10,90);

        } else if( mode == GAMEOVER ){
            dg.setColor( Color.black );
            dg.fillRect( 0 , 0 , width , height );
            dg.setColor( Color.red );
            dg.drawString( "Game Over",30, height/2 );
        }
    }
    void moveMogura()
    {
        // ���ʂ܂ł̎��Ԃ�1���炷�B���񂾂��̂́A�����Ă��܂��B
        for(int i = 0 ; i < tatesize ; i++){
            for(int j = 0 ; j < yokosize ; j++){
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


    // ���O����1�C�o��������B
    void putMogura(){
        
        int r = new Random().nextInt();
        r = r & 255; // ���̒l�ɖ߂��B���̌��ʁAr�ɂ́A0����255�̒l������B
        r = r % ( yokosize * tatesize );
        int yoko = r % yokosize;
        int tate = r / yokosize;

        // ���O���������Ă����莞�Ԃ����Ȃ��Ɠ������ɂ͓o��ł��Ȃ��B
        if( mog[yoko][tate] > -mogblank ) return;
        hellosound.play(); // �o�����̉����o���B
        mogstate[ yoko ][ tate ] = ALIVE;
        mog[ yoko ][ tate ] = mogtime;

        dg.setColor( Color.white );
        dg.fillRect( yoko * holesize , tate*holesize , holesize , holesize );
        dg.drawImage( aliveimg , yoko * holesize , tate*holesize ,null );
    }
    // ���O���������B
    void outMogura( int x , int y ){
        
        dg.setColor( Color.black );
        dg.fillRect( x * holesize , y * holesize , holesize , holesize );
        dg.setColor( Color.white );
        dg.drawRect( x * holesize , y * holesize , holesize , holesize );
    }
    // ������Ԃ̃��O����\������B
    void missMogura( int x , int y ){

        dg.setColor( Color.black );
        dg.fillRect( x * holesize+1 , y*holesize+1 , holesize-1 , 
                     holesize-1 );
        dg.setColor( Color.yellow );
        dg.drawString( "MISS" ,x*holesize , y*holesize+(holesize/2) );


    }
    // �����Ԃ̃��O����\������B
    void yarareMogura( int x , int y ){
        dg.setColor( Color.red );
        dg.drawLine( x * holesize , y* holesize , x*holesize + holesize , 
                     y*holesize + holesize );
        dg.drawLine( x * holesize + holesize , y * holesize , x * holesize ,                     y * holesize + holesize );
        dg.drawImage( hitimg , x*holesize, y*holesize , null );
    }
    // �}�E�X�������ꂽ�Ƃ��̏���
    public boolean mouseDown( Event e , int x , int y )
    {
        if( mode == TITLE ){
        
            // ���O��������������B
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
            // �ǂ̃��O����@�����̂��𔻒肷��B
            int yoko , tate; // ���O���̌��̈ʒu
            yoko = x / holesize;
            tate = y / holesize;
            
            // ���O���������Ă���Ƃ������q�b�g
        
            if( yoko>=0 && yoko < yokosize &&
                tate>=0 && tate < tatesize &&
               mogstate[yoko][tate] == ALIVE ){
                mog[yoko][tate] = hittime;
                mogstate[yoko][tate] = HIT;
                yarareMogura( yoko , tate );
                score = score + level;
                hitsound.play();  // �����o���B
                hitno++;
                // ���x��
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