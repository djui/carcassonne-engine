package carcassonne.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.Charset;
import java.util.List;

import carcassonne.Step;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
public class JsonDecoder extends MessageToMessageDecoder<ByteBuf> {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    Step step = mapper.readValue(msg.toString(Charset.defaultCharset()), Step.class);
    out.add(step);
  }

}
