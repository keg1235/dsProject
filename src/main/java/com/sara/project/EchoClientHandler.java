package com.sara.project;

import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EchoClientHandler extends ChannelHandlerAdapter {

        private final ByteBuf message;

        public static ByteBuf byteBufResult;

        public static ByteBuf bytefiveResult;

        public static ByteBuf totalResult;

        public static ByteBuf doBufferResult;

        public static Integer ddcnum;


        public static ByteBuf byteWriteBufResult;

        public static ChannelHandlerContext channelHandlerContext;


        public static String type;
        public static ByteBuf dcpSetBufferResult;
        // 초기화

        @Autowired
        private DdcRepository ddcRepository;

        public EchoClientHandler(){
            message = Unpooled.buffer(EchoClient.MESSAGE_SIZE);
            setDcpType("");
            //message.writeBytes(str);

        }

        public EchoClientHandler(ByteBuf byteBuf){
            message = Unpooled.buffer(EchoClient.MESSAGE_SIZE);
            setDcpType("");
            message.writeBytes(byteBuf);

        }

        public EchoClientHandler(ByteBuf byteBuf, Integer ddc){
        message = Unpooled.buffer(EchoClient.MESSAGE_SIZE);
        setDdc(ddc);
        setDcpType("");
        message.writeBytes(byteBuf);

    }

        public EchoClientHandler(ByteBuf byteBuf, String Type){
            message = Unpooled.buffer(EchoClient.MESSAGE_SIZE);
            setDcpType(Type);
            message.writeBytes(byteBuf);

        }


        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // 메시지를 쓴 후 플러쉬합니다.
            ctx.writeAndFlush(message);
            System.out.println("writeAndFlush"+ctx);
        }

        public Integer getDdc() {
        return ddcnum;
    }
        public void setDdc(Integer ddcnum) {this.ddcnum = ddcnum;}

        public String getDcpType() {
            return type;
        }
        public void setDcpType(String type) {this.type = type;}

        public ByteBuf getDcpSetBufferResult() {
        return dcpSetBufferResult;
    }

        public void setDcpSetBufferResult(ByteBuf byteBufResult) {this.dcpSetBufferResult = byteBufResult;}


        public ByteBuf getResultStatus() {
            return byteBufResult;
        }
        public ByteBuf getResultfive() {
            return bytefiveResult;
        }
        public ByteBuf getResulttotalResult() {
        return totalResult;
    }
        public void setResultStatus(ByteBuf byteBufResult) {this.byteBufResult = byteBufResult;}

        public ByteBuf getWriteResultStatus() {
        return byteWriteBufResult;
    }
        public void setWriteResultStatus(ByteBuf byteWriteBufResult) {
        this.byteWriteBufResult = byteWriteBufResult;
    }

        public ByteBuf getResultSlaveStatus() {
        return byteBufResult;
    }
        public void setResultfive(ByteBuf bytefiveResult) {
       this.bytefiveResult = bytefiveResult;
    }
        public void setResulttotalResult(ByteBuf totalResult) {
        this.totalResult = totalResult;
    }
        public void setResultSlaveStatus(ByteBuf byteBufResult) {this.byteBufResult = byteBufResult;}

        public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
            channelHandlerContext =ctx;
            // 받은 메시지를 ByteBuf형으로 캐스팅합니다.
            ByteBuf byteBufMessage = (ByteBuf) msg;
            // 읽을 수 있는 바이트의 길이를 가져옵니다.
            int size = byteBufMessage.readableBytes();

            // 읽을 수 있는 바이트의 길이만큼 바이트 배열을 초기화합니다.
           // byte [] byteMessage = new byte[size];
            // for문을 돌며 가져온 바이트 값을 연결합니다.
            /*
            for(int i = 0 ; i < size; i++){
                byteMessage[i] = byteBufMessage.getByte(i);
                System.out.println(byteBufMessage.getByte(i));
            }
            */
            System.out.println(size);

            if (type.equals("DCP")){
                System.out.println("DcpSet:");
                setDcpSetBufferResult(byteBufMessage);
                System.out.println(getDcpSetBufferResult());
            }else{
                if(size == 10) {
                    if (byteBufMessage.getByte(7) == 2){ // write retrun;
                        System.out.println("----------------------------");
                        //00 02 00 00 00 04 03 02 01 a3
                        //00 01 00 00 00 04 03 01 01 a3
                        setResultStatus(byteBufMessage);
                        System.out.println(getResultStatus());
                    }else if(byteBufMessage.getByte(7) == 1){
                        System.out.println("++++++++++++++++++++++++");
                        setWriteResultStatus(byteBufMessage);
                        System.out.println(getWriteResultStatus());
                    }
                }else if(size == 11) {

                    if(byteBufMessage.getByte(7) == 3){
                        System.out.println("five");
                        setResultfive(byteBufMessage);
                    }

                }
                else if(size == 17) {
                    System.out.println("total:");
                    setResulttotalResult(byteBufMessage);
                } else if(size > 20){
                    System.out.println("++++++++++++++++++++++++slave");
                    setResultSlaveStatus(byteBufMessage);
                    System.out.println(getResultSlaveStatus());
                }
            }


            //+-------------------------------------------------+
            //         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
            //+--------+-------------------------------------------------+----------------+
            //|00000000| 00 03 00 00 00 06 d1 03 02 bc 00 0c             |............    |
            //+--------+-------------------------------------------------+----------------+
            //+-------------------------------------------------+
            //        |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
            //+--------+-------------------------------------------------+----------------+
            //|00000000| 00 03 00 00 00 1b d1 03 18 00 02 00 00 00 00 00 |................|
            //|00000010| 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 |................|
            //|00000020| 00                                              |.               |
            //+--------+-------------------------------------------------+----------------+



            // 그후 컨텍스트를 종료합니다.
            ctx.close();
        }

        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.close();
        }

        public void channelReadComplete() throws Exception {
            channelHandlerContext.flush();
            channelHandlerContext.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {

            System.out.println("*******eeeeeeeeeeee******");
            List<Ddc> dcpList = ddcRepository.findByDdc(getDdc());

            for (Ddc ddc : dcpList){
                ddc.setDiStatus(null);
                ddc.setDoStatus(null);
                ddcRepository.save(ddc);
            }
            cause.printStackTrace();
            ctx.close();
        }
    public ByteBuf getResultDoStatus() {
        return doBufferResult;
    }

    public void setResultDoStatus(ByteBuf doBufferResult) {
        this.doBufferResult = doBufferResult;
    }

}
