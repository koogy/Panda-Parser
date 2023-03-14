package fr.uparis.pandaparser.core.serve.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.java.Log;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * The type Http panda parser server.
 *
 * @param port   the port
 * @param output The output
 */
@Log
public record HttpPandaParserServer(int port, String output) {

    static final boolean SSL = System.getProperty("ssl") != null;

    /**
     * The start server.
     *
     * @throws CertificateException certificate exception
     * @throws SSLException         ssl exception
     */
    public void start() throws CertificateException, SSLException {

        final SslContext sslCtx = getSslContext();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            Channel ch = getChannel(sslCtx, bossGroup, workerGroup, b);
            log.info("Open your web browser and navigate to " + (SSL ? "https" : "http") + "://localhost:" + this.port + '/');
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * The Channel.
     *
     * @param sslCtx          the ssl context
     * @param bossGroup       the boss group
     * @param workerGroup     the worker group
     * @param serverBootstrap the server bootstrap
     * @return the channel
     * @throws InterruptedException the interrupted exception
     */
    private Channel getChannel(SslContext sslCtx, EventLoopGroup bossGroup, EventLoopGroup workerGroup, ServerBootstrap serverBootstrap) throws InterruptedException {
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HttpPandaParserServerInitializer(sslCtx, output));

        return serverBootstrap.bind(this.port).sync().channel();
    }

    /**
     * The Ssl context.
     *
     * @return the ssl context
     * @throws CertificateException certificate exception
     * @throws SSLException         ssl exception
     */
    private SslContext getSslContext() throws CertificateException, SSLException {
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
                    .sslProvider(SslProvider.JDK).build();
        } else
            sslCtx = null;
        return sslCtx;
    }
}
