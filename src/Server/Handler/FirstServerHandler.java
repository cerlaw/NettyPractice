package Server.Handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author zhanghongjie
 * @date 2018/11/2
 * @descrition
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 服务器收到数据 - >" + byteBuf.toString(Charset.forName("utf-8")));

        //回复数据到客户端
        System.out.println(new Date() + ": 服务端写出数据");

        ByteBuf out = getByteBuf(ctx);

        ctx.channel().writeAndFlush(out);
    }

    /**跟客户端一致*/
    private ByteBuf getByteBuf(ChannelHandlerContext context) {
        byte[] bytes = "佩奇你好，我是乔治!".getBytes(Charset.forName("utf-8"));

        ByteBuf byteBuf = context.alloc().buffer();

        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
