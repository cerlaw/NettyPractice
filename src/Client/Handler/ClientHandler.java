package Client.Handler;

import Protocal.Packet;
import Protocal.PacketCodec;
import Protocal.request.LoginRequestPacket;
import Protocal.response.LoginResponsePacket;
import Protocal.response.MessageResponsePacket;
import Utils.LoginUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * @author zhanghongjie
 * @date 2018/11/2
 * @descrition
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + " :客户端开始登陆");

        //创建登录对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUserName("boss");
        packet.setPassWord("MyPassWord");

        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(packet, ctx.alloc());

        //写数据
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + " :客户端登录成功");
                LoginUtils.markAsLogin(ctx.channel());
            }else {
                System.out.println(new Date() + " :客户端登录失败，原因: " + loginResponsePacket.getReason());
            }
        }else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + "客户端收到服务端回复信息：" + messageResponsePacket.getMessage());
        }
    }
}
