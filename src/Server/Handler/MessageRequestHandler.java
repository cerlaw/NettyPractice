package Server.Handler;

import Protocal.request.MessageRequestPacket;
import Protocal.response.MessageResponsePacket;
import Utils.Session;
import Utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author zhanghongjie
 * @date 2018/11/3
 * @descrition
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    /**互聊实现，相当于消息转发的作用*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        //拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        //通过消息发送方的会话信息构造要发送的的信息
        MessageResponsePacket packet = new MessageResponsePacket();
        packet.setFromUserId(session.getUserId());
        packet.setFromUserName(session.getUserName());
        packet.setMessage(msg.getMessage());

        //拿到消息的发送方的channel
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        //将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(packet);
        }else {
            System.out.println("【" + msg.getToUserId() + "】不在线，发送失败");
        }

    }


//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
//        //处理信息
//        System.out.println(new Date() + "：服务端收到客户端信息：" + messageRequestPacket.getMessage());
//
//        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
//
//        ctx.channel().writeAndFlush(messageResponsePacket);
//    }


}
