package fr.uparis.pandaparser.core.serve.http;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * http panda parser server initializer.
 */
public class HttpPandaParserServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    private final String output;

    /**
     * Instantiates a new http panda parser server initializer.
     *
     * @param sslCtx the ssl ctx
     * @param output the output
     */
    public HttpPandaParserServerInitializer(SslContext sslCtx, String output) {
        this.sslCtx = sslCtx;
        this.output = output;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpPandaParserServerHandler(this.output));
    }
}