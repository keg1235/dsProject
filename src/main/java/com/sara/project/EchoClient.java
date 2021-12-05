package com.sara.project;

import com.sara.project.dcpSet.DcpSet;
import com.sara.project.dcpSet.DcpSetRepository;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;
import com.sara.project.ddc.DdcService;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


public class EchoClient {

        // 호스트를 정의합니다. 로컬 루프백 주소를 지정합니다.
       // private static final String HOST = "192.168.0.2";
        // 접속할 포트를 정의합니다.
        //private static final int PORT = 502;
        // 메시지 사이즈를 결정합니다.
        static final int MESSAGE_SIZE = 256;
        private String host;
        private int port = 502;
        public EchoClient(){
            this.host = "192.168.0.62";
            this.port = 502;
        }

        public EchoClient(String host, int port) {
            this.host = host;
            this.port = port;
        }


        public void getDataReq(ByteBuf buffer,int ddcId ,DdcRepository ddcRepository){
            EventLoopGroup group = new NioEventLoopGroup();

            try{
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel sc) throws Exception {
                                ChannelPipeline cp = sc.pipeline();

                                cp.addLast(new LoggingHandler(LogLevel.INFO));
                                cp.addLast(new ReadTimeoutHandler(1),new EchoClientHandler(buffer,ddcId));
                            }
                        });

                ChannelFuture cf = b.connect(host, port).sync();

                cf.channel().closeFuture().sync();
            }
            catch(Exception e){
                System.out.println("*******No Connect******");
                List<Ddc> dcpList = ddcRepository.findByDdc(ddcId);
                System.out.println(dcpList.size());
                for (Ddc ddc : dcpList){
                    ddc.setDiStatus(null);
                    ddc.setDoStatus(null);
                    ddcRepository.save(ddc);
                }
                group.shutdownGracefully();
                //e.printStackTrace();
            }
            finally{
                group.shutdownGracefully();
            }
        }

    public void getDataReq(ByteBuf buffer,DcpSet dcpSet ,DcpSetRepository dcpSetRepository){
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline cp = sc.pipeline();

                            cp.addLast(new LoggingHandler(LogLevel.INFO));
                            cp.addLast(new ReadTimeoutHandler(1),new EchoClientHandler(buffer,dcpSet.getReadWriteType()));
                        }
                    });

            ChannelFuture cf = b.connect(host, port).sync();

            cf.channel().closeFuture().sync();
        }
        catch(Exception e){
            System.out.println("*******No Connect******");
            dcpSet.setValue("연결 안됨");
            dcpSetRepository.save(dcpSet);
            group.shutdownGracefully();
            e.printStackTrace();
        }
        finally{
            group.shutdownGracefully();
        }
    }


    public void getDataReq(ByteBuf buffer){
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline cp = sc.pipeline();

                            cp.addLast(new LoggingHandler(LogLevel.INFO));
                            cp.addLast(new ReadTimeoutHandler(1),new EchoClientHandler(buffer));
                        }
                    });

            ChannelFuture cf = b.connect(host, port).sync();

            cf.channel().closeFuture().sync();
        }
        catch(Exception e){
            System.out.println("*******No Connect******");
            group.shutdownGracefully();
            //e.printStackTrace();
        }
        finally{
            group.shutdownGracefully();
        }
    }
}
