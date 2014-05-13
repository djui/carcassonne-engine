package carcassonne.server;

import com.google.common.base.Preconditions;

import java.util.logging.Level;
import java.util.logging.Logger;

import carcassonne.Step;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class MessageHandler extends ChannelInboundHandlerAdapter {

  private static final Logger logger = Logger.getLogger(MessageHandler.class.getName());

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    Preconditions.checkArgument(msg instanceof Step, "Wrong object instance {}", msg);
    ctx.write(msg);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    logger.log(Level.WARNING, "Unexpected exception from downstream.", cause);
    ctx.close();
  }

}
