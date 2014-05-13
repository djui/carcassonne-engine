package carcassonne.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class JsonEncoder extends MessageToMessageEncoder<Object> {

  @Override
  protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
    System.out.println("@@@@@");
    System.out.println(msg);
    ObjectMapper mapper = new ObjectMapper();
    String frame = mapper.writeValueAsString(msg) + "\n";
    out.add(frame);
  }

}
