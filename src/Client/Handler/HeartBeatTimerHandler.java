package Client.Handler;

import Protocal.Packet;
import Protocal.PacketCodec;
import Protocal.request.HeartBeatRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanghongjie
 * @date 2018/11/5
 * @descrition 客户端定时发送心跳包到服务端
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    private ScheduledFuture future;

    /**
     * ctx.executor()返回的是当前channel绑定的NIO线程，然后，NIO线程有一个方法，scheduleAtFixedRate(),
     * 类似于jdk的定时任务机制，可以每隔一段时间执行一个任务，而我们这边实现的是每隔5秒，向服务器
     * 发送一个心跳检测包，这个时间端通常要比服务端的空闲检测时间的一半要短一些，我们这里直接定义为空闲
     * 检测时间的三分之一，主要是排除公网的偶发秒级抖动。实际中可以将心跳间隔和空闲检测时间设的长一些，具体应用
     *  具体实现
     *
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        future = ctx.executor().scheduleAtFixedRate(() -> {
                    System.out.println("客户端发送心跳包");
                    ctx.writeAndFlush(new HeartBeatRequestPacket());
                },
                HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (future != null) {
            future.cancel(true);
        }
        super.channelInactive(ctx);
    }
}
