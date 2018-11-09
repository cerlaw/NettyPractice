package Server.Handler;

import Protocal.request.HeartBeatRequestPacket;
import Protocal.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhanghongjie
 * @date 2018/11/5
 * @descrition
 */
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        System.out.println("服务器收到心跳包，返回心跳包");
        ctx.channel().writeAndFlush(new HeartBeatResponsePacket());
    }
}
