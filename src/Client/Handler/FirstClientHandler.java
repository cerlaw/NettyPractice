package Client.Handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author zhanghongjie
 * @date 2018/11/2
 * @descrition
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    /**发送数据的方法*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端写出数据");

        //1、获取数据
        ByteBuf buffer = getByteBuf(ctx);

        //2、写数据
        ctx.channel().writeAndFlush(buffer);
    }

    /**接受数据的方法*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf  byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + " :客户端收到数据: " + byteBuf.toString(Charset.forName("utf-8")));
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        //1、获取二进制抽象 ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();

        //2、准备数据，指定字符串的字符集为utf-8
        byte[] bytes = "你好，乔治!".getBytes(Charset.forName("utf-8"));

        //3、填充数据到byteBuf
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
