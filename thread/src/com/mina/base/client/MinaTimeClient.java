package com.mina.base.client;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaTimeClient {

    public static void main(String[] args) {

        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        connector.setConnectTimeout(30);
        TimeClientHandler resultHandler = new TimeClientHandler();
        connector.setHandler(resultHandler);
        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", 8088));

        cf.awaitUninterruptibly();
        cf.getSession().write("hello");
        cf.getSession().write("quit");
        cf.getSession().getCloseFuture().awaitUninterruptibly();//
        connector.dispose();
        resultHandler.getMessage();

    }

}
