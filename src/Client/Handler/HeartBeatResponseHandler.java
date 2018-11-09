package Client.Handler;

import Protocal.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhanghongjie
 * @date 2018/11/5
 * @descrition
 */
public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket msg) throws Exception {
        System.out.println("收到服务端返回的心跳信息");
    }
}
