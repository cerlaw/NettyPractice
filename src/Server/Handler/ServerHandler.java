package Server.Handler;

import Protocal.Packet;
import Protocal.PacketCodec;
import Protocal.request.LoginRequestPacket;
import Protocal.request.MessageRequestPacket;
import Protocal.response.LoginResponsePacket;
import Protocal.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author zhanghongjie
 * @date 2018/11/2
 * @descrition
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        //解码
        Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

        //判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            System.out.println(new Date() + "客户端开始登陆....");

            if (vaild(loginRequestPacket)) {
                //校验成功
                System.out.println(new Date() + "客户端登录成功");
                loginResponsePacket.setSuccess(true);
            }else {
                //校验失败
                System.out.println(new Date() + "客服端登录失败");
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码校验失败");
            }

            replyToClient(ctx, loginResponsePacket);
        }else if (packet instanceof MessageRequestPacket) {
            //处理信息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + "：服务端收到客户端信息：" + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

            replyToClient(ctx, messageResponsePacket);

        }
    }

    private void replyToClient(ChannelHandlerContext ctx, Packet packet) {
        ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(packet, ctx.alloc());
        ctx.channel().writeAndFlush(responseByteBuf);
    }

    private boolean vaild(LoginRequestPacket packet) {
        if ("boss".equals(packet.getUserName()) && "MyPassWord".equals(packet.getPassWord())) {
            return true;
        }else {
            return false;
        }
    }
}
