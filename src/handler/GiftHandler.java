package handler;

import com.alibaba.fastjson.JSON;

import dao.Gift;
//import dao.User;
import dao.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder;
import org.apache.log4j.Logger;
//import pojo.BuyGift;


import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class GiftHandler extends ChannelInboundHandlerAdapter {
    Logger logger = Logger.getLogger(GiftHandler.class);
    public String json;

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fhr = (FullHttpRequest) msg;
        String uri = fhr.uri();
        FullHttpResponse response = null;
        List<InterfaceHttpData> parmList = null;
        HttpPostRequestDecoder decoder = null;
        if (uri.equals("/snc/buy/gift")) {
            System.out.println("接受到ajax请求");
//
//            ByteBuf byteBuf = fhr.content();
//            Charset charset = Charset.forName("utf-8");
//            String json = byteBuf.toString(charset);
            Gift gift = new Gift();
//            gift.getMessage(json);
//            byteBuf.release();
            User user = new User();
            Gift buyGift = new Gift();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dd = sdf.format(date);// new Date()为获取当前系统时间
            String newDate = sdf.format(new Date());
            String result = "";
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                result += random.nextInt(10);
            }
            String orderId = newDate + result;   //获取订单号

            if (gift.buyGift("1", "20")) {
                json = "{\"res\":\"true\",\"data\":\"" + dd + "\",\"orderid\":\"" + orderId + "\"}";
                System.out.println(json);
            } else {
                json = "{\"res\":\"false\",\"data\":\"" + dd + "\",\"orderid\":\"" + orderId + "\"}";

            }

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(json.getBytes("UTF-8")));
            response.headers().set("Access-Control-Allow_Origin", "*");
            response.headers().set(CONTENT_TYPE, "text/json");
//            response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN,"*");
            response.headers().setInt(CONTENT_LENGTH,
                    response.content().readableBytes());
            ctx.writeAndFlush(response);
        }
        }
    }
//}
