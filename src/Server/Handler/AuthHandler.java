package Server.Handler;

import Utils.LoginUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zhanghongjie
 * @date 2018/11/5
 * @descrition
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!LoginUtils.hasLogin(ctx.channel())) {
            //未登录就直接关闭连接，简单粗暴
            ctx.channel().close();
        }else {
            //防止重复校验登录状态，一行代码实现逻辑的删除
            ctx.pipeline().remove(this);
            //登录成功则继续传递给下一个Handler
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (LoginUtils.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证，AuthHandler被移除");
        }else {
            System.out.println("无登录验证，强制关闭连接！");
        }
    }
}
