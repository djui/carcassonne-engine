package carcassonne.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {

  private final int port;

  public Server(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    EventLoopGroup acceptors = new NioEventLoopGroup(1);
    EventLoopGroup workers = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(acceptors, workers)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 100)
          .handler(new LoggingHandler(LogLevel.DEBUG))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline()
                  .addLast("Logger", new LoggingHandler(LogLevel.DEBUG))
                  .addLast("frameDecoder", new LineBasedFrameDecoder(80))
                  .addLast("messageDecoder", new JsonDecoder())
                  .addLast("messageHandler", new MessageHandler())
                  .addLast("messageEncoder", new JsonEncoder());
            }
          });

      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();
    } finally {
      acceptors.shutdownGracefully();
      workers.shutdownGracefully();
    }
  }
}
