import java.io.*;

class cgimain
{
    // ���C�����[�`���ł́A�����̃C���X�^���X����邾���B
    public static void main( String args[] )
    {
        cgimain c = new cgimain( args );
    }

    public final int MAXBYTE = 2000;
    byte b[] = new byte[MAXBYTE];

    String topdir;    // �A�N�Z�X����t�@�C��������f�B���N�g���B�����Ŏw��B

    cgimain( String args[] )
    {
        int querylen = 0;

        // �܂�GET���ǂ������ׁA�����łȂ����POST���炾�Ƃ��ē��́B
        String getquery = System.getProperty("QUERY");
        if( getquery != null && !getquery.equals("")){
            // b = getquery.getBytes();  // JDK 1.1
            getquery.getBytes( 0 , getquery.length() , b , 0 ); // JDK 1.0.2
            querylen = getquery.length();
        } else {
            try{
                querylen = System.in.read( b , 0 , MAXBYTE );
            }catch( IOException e ){System.out.println("ERROR: bad input");}
        }
        URLDecoder ud = new URLDecoder( b );


        // �u���E�U�ɑ���Ԃ����e�������B
        // �ŏ���HTTP�w�b�_�𑗐M����B��s����邽�߂̍Ō��'\n'����؁B
        // �����ł�println���g���̂ŁA'\n' ��1�ł悢�B
        System.out.println("Content-type: text/html\n");     
        System.out.println("<html>");
        System.out.println("<title>QUERY RESULT</title>");
        System.out.println("<body>");


        // �ǂ̃t�@�C���𑀍삷��̂��H
        if( args.length == 0 ){
            System.out.println("ERROR: top directory was not set.");
            return;
        } else {
            topdir = args[0];
        }

        String file_fullpath = topdir + "/" + ud.getValue("filename");



        // ���ۂ̃t�@�C���̏���������B�������������e�ɂ���āA������
        // �ύX����K�v������B
        RandomAccessFile f = null;

        if( ud.getValue("method" ).equals("append") ){
            try{
                f = new RandomAccessFile( file_fullpath , "rw" );
                String val = ud.getValue("value");
                // byte[]  valbyte = val.getBytes(); // JDK 1.1
                byte[] valbyte=new byte[val.length()]; // JDK 1.0.2
                val.getBytes( 0 , val.length() , valbyte , 0 ); // JDK 1.0.2

                f.seek( f.length() );
                f.write( valbyte ,0,valbyte.length ) ;
                f.write( '\n' ); // ���s������B
                System.out.println("OK: append successful.");
                f.close();
            }catch( IOException e ){
                System.out.println("ERROR: Error while writing." + e);
            }
        } else if( ud.getValue("method").equals("read") ){
            try{
                f = new RandomAccessFile( file_fullpath , "r" );
                byte fileb[] = new byte[ (int)f.length() ];
                System.out.println(f.read( fileb , 0 , (int)f.length() ) );
                f.close();
                System.out.println("OK: read successful.");
                System.out.write( fileb , 0 , fileb.length );
                System.out.println("");   // �Ō�ɋ�s������
                
            }catch( IOException e ){
                System.out.println( "ERROR: Error while reading." + e );
            }
        } else {
            System.out.println( "ERROR: method not specified." );
        }
    
    }
}

// URLDecoder��Java�R�A�ɂ���΂����̂����B
// �w�肳�ꂽ���O�̍��ڂ��Ȃ��ꍇ�́A��̕������Ԃ��B
// ascii�����ȊO�ɂ͑Ή����Ă��Ȃ��B
class URLDecoder
{
    int valuenum=0;
    byte newbuf[];
    int length=0;
    
    URLDecoder( byte linebuf[] )
    {
        length = linebuf.length;
        // byteToCharConverter�͓����ŗ^����ꂽ�o�b�t�@���g��
        // �̂ŁA�o�b�t�@�͒��߂ɂƂ��Ă����i2�{�j�B
        newbuf= new byte[ length*2 ]; 
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
                // String is = new String( ib );   // JDK 1.1
                String is = new String( ib , 0 );  // JDK 1.0.2

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
                // sub = new String( newbuf , i , len );   // JDK 1.1
                sub = new String( newbuf , 0 , i, len );   // JDK 1.0.2 
            }catch( StringIndexOutOfBoundsException e ){
                continue;
            }
            if( sub.equals( name ) ){
                for(int j=i;j<length;j++){
                    if( newbuf[j]==0 ){
                        
                        partlen=j-i;
                        break;
                    }
                    if( j == (length-1) ){
                        partlen = j-i+1;
                        break;
                    }
                }
                // return new String(newbuf,i+len+1,partlen-len -1);
                                                            // JDK 1.1
                return new String(newbuf,0,i+len+1,partlen-len-1);
                                                            // JDK 1.0.2
            }
        }
        return "";
    }
}
