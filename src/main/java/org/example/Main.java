package org.example;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;

import org.snf4j.core.SelectorLoop;
import org.snf4j.core.factory.AbstractSessionFactory;
import org.snf4j.core.handler.IStreamHandler;

public class Main {
    static final String PREFIX = "localhost";
    static final int PORT = Integer.getInteger(PREFIX+"Port", 8833);

    public static void main(String[] args) throws Exception {
        Database db = new Database();
        db.getDbConnection();
        SelectorLoop loop = new SelectorLoop();
        try {
            loop.start();
            System.out.println("server started");
            // Initialize the listener
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(PORT));

            // Register the listener
            loop.register(channel, new AbstractSessionFactory() {
                @Override
                protected IStreamHandler createHandler(SocketChannel channel) {
                    ServerHandler haha = null;
                    try {
                        haha = new ServerHandler();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    return haha;
                }
            }).sync();

            // Wait till the loop ends
            loop.join();
        }
        finally {
            // Gently stop the loop
            loop.stop();
        }
    }
}