package Server.Handler;

import Protocal.request.LoginRequestPacket;
import Protocal.response.LoginResponsePacket;
import Utils.LoginUtils;
import Utils.Session;
import Utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * @author zhanghongjie
 * @date 2018/11/3
 * @descrition
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {

        System.out.println(new Date() + "客户端开始登陆....");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        if (vaild(loginRequestPacket)) {
            //校验成功
            System.out.println(new Date() + "客户端登录成功");
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            LoginUtils.markAsLogin(ctx.channel());
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), ctx.channel());
        }else {
            //校验失败
            System.out.println(new Date() + "客服端登录失败");
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败");
        }

        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unbindSession(ctx.channel());
        super.channelInactive(ctx);
    }

    private boolean vaild(LoginRequestPacket packet) {
        if ("boss".equals(packet.getUserName()) && "MyPassWord".equals(packet.getPassWord())) {
            return true;
        }else {
            return false;
        }
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
