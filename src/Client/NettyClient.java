package Client;

import Client.Handler.HeartBeatResponseHandler;
import Client.Handler.HeartBeatTimerHandler;
import Client.Handler.LoginResponseHandler;
import Client.Handler.MessagResponseHandler;
import Codec.PacketDecoder;
import Codec.PacketEncoder;
import Protocal.request.MessageRequestPacket;
import Protocal.PacketCodec;
import Handler.IMIdleStateHandler;
import Utils.LoginUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanghongjie
 * @date 2018/11/1
 * @descrition 客户端NIO实现
 */
public class NettyClient {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
//                        channel.pipeline().addLast(new StringEncoder());
//                        channel.pipeline().addLast(new FirstClientHandler());
//                        channel.pipeline().addLast(new ClientHandler());
                        channel.pipeline().addLast(new IMIdleStateHandler())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessagResponseHandler())
                                .addLast(new PacketEncoder())
                                .addLast(new HeartBeatTimerHandler());
                    }
                });

        connect(bootstrap, "127.0.0.1", 6666, MAX_RETRY);

    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                Channel channel = ((ChannelFuture) future).channel();
                System.out.println("连接成功！");
                startConsoleThread(channel);
            }else if (retry == 0) {
                System.out.println("重连次数已用完，放弃连接！");
            }else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.out.println(new Date() + ": 连接失败，开始第" + order + "次重连....");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtils.hasLogin(channel)) {
                    System.out.println("输入信息发送至客户端");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    ByteBuf byteBuf = PacketCodec.INSTANCE.encode(packet, channel.alloc());
                    channel.writeAndFlush(byteBuf);
                }
            }
        });
    }
}
