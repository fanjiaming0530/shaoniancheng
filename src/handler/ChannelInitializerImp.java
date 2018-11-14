package org.handler;

import handler.GiftHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;


public class ChannelInitializerImp extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel sc)  {

        sc.pipeline().addLast(new HttpRequestDecoder());//inbound
        sc.pipeline().addLast(new HttpObjectAggregator(65536));//inbound
        sc.pipeline().addLast(new HttpResponseEncoder());//outbound
        sc.pipeline().addLast(new GiftHandler());
    }
}
