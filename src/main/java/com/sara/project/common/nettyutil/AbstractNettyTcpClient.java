package com.sara.project.common.nettyutil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;

public abstract class AbstractNettyTcpClient  extends AbstractNettyClient{

    @Override
    public Bootstrap createBootstrap() {
        return new Bootstrap().group(getWorkerEventLoopGroup(getWorkerThreadSize()))
              //  .channel(NettyNetworkUtil.getClientSocketChannelClass(isUseNativeIO(), false))
                .option(ChannelOption.SO_LINGER, 0)
                .handler(getServiceHandler());
    }

}
