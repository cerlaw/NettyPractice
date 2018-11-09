package Client.Handler;

import Protocal.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author zhanghongjie
 * @date 2018/11/3
 * @descrition
 */
public class MessagResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        System.out.println(new Date() + "客户端收到服务端回复信息：" + messageResponsePacket.getMessage());
    }
}
