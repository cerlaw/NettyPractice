package Server;

import Codec.PacketDecoder;
import Codec.PacketEncoder;
import Handler.IMIdleStateHandler;
import Server.Handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author zhanghongjie
 * @date 2018/11/1
 * @descrition Netty NIO服务端实现
 * 该类实现的功能有服务端启动、接受新连接、打印客户端传来的数据
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //相当于接受新连接线程，主要负责创建新连接
        NioEventLoopGroup boos = new NioEventLoopGroup();
        //相当于负责读取数据的线程，主要用于读取数据以及业务逻辑连接
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.group(boos, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        nioSocketChannel.pipeline().addLast(new StringDecoder());
//                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());
//                        nioSocketChannel.pipeline().addLast(new ServerHandler());
                        nioSocketChannel.pipeline().addLast(new IMIdleStateHandler())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginRequestHandler())
                                .addLast(HeartBeatRequestHandler.INSTANCE)
                                .addLast(new AuthHandler())
                                .addLast(new MessageRequestHandler())
                                .addLast(new PacketEncoder());
                    }
                });

        bind(serverBootstrap, 6666);
    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功！");
                }else {
                    System.out.println("端口[" + port + "]绑定失败！");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
