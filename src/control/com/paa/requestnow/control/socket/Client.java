package com.paa.requestnow.control.socket;

import com.paa.requestnow.control.util.Serializer;
import com.paa.requestnow.model.ApplicationUtilities;
import com.paa.requestnow.model.ConfigurationManager;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author artur
 */
public abstract class Client
    implements 
        Runnable
{
    private static final String HOST = ConfigurationManager.getInstance().getProperty( "server.host", "127.0.0.1" );
    private static final Integer PORT = Integer.parseInt( ConfigurationManager.getInstance().getProperty( "server.port", "12345" ) );
    
    public Client(){}
    
    
    public abstract void onRecive( Object  data ) throws Exception;
    
    @Override
    public void run() 
    {
        MulticastSocket socket = null;
        
        try 
        {
            // determina endereco e porta
            InetAddress grupo = InetAddress.getByName( HOST );

            // cria multicast socket e se une ao grupo
            socket = new MulticastSocket( PORT );
            
            socket.joinGroup( grupo );
        
            try
            {
                while ( true ) 
                {
                    // prepara buffer (vazio)
                    byte[] buf = new byte[256];

                    // prepara pacote para resposta
                    DatagramPacket pacote = new DatagramPacket( buf, buf.length );

                    // recebe pacote
                    socket.receive( pacote );

                    onRecive( Serializer.deserialize( pacote.getData() ) );
                }
            }
            
            catch ( Exception e )
            {
                ApplicationUtilities.logException( e );
            }
            
            // fecha socket
            socket.close();
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
}
