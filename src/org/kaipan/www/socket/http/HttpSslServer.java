package org.kaipan.www.socket.http;

import java.util.Properties;

import org.kaipan.www.socket.core.MessageBuffer;
import org.kaipan.www.socket.core.IConfig;
import org.kaipan.www.socket.core.IServer;
import org.kaipan.www.socket.core.SocketProcessor;
import org.kaipan.www.socket.util.Utils;

public class HttpSslServer extends IServer
{
    public HttpSslServer(HttpSslConfig config)
    {
        super(config);

        createSocketProcessor(config);
    }
    
    public HttpSslConfig getConfig() 
    {
        return (HttpSslConfig)iconfig;
    }

    @Override
    protected void createSocketProcessor(IConfig config)
    {
        this.socketProcessor = new SocketProcessor(config);
        this.socketProcessor.init(new HttpMessageReaderFactory(), new MessageBuffer(), new MessageBuffer(), new HttpMessageProcessor(getConfig()));
    }
    
    public static void main(String[] args) 
    {
        HttpConfig config = new HttpConfig();
        
        String path = null;
        if ( args.length > 0 ) path = args[0];
        
        Properties property = null;
        if ( path == null ) {
            String jarHome = Utils.getJarHome(config);
            
            property = Utils.loadConfigFile(jarHome + "/https-server.properties");
        }
        else {
            property = Utils.loadConfigFile(path);
            if ( property == null ) {
                System.out.println("Usage: java -jar java-nio-http-server-{version}.jar "
                        + "\"path to file http-server.properties\"");
                return;
            }
        }
        config.load(property);
        
        HttpServer server = new HttpServer(config);
        server.start();
    }
}