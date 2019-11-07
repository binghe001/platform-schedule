package com.courage.platform.schedule.server.rpc;

import com.courage.platform.rpc.remoting.PlatformChannelEventListener;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * rpc channel 变化监听器
 * Created by zhangyong on 2019/11/7.
 */
public class RpcChannelListener implements PlatformChannelEventListener {

    private static final Logger logger = LoggerFactory.getLogger(RpcChannelListener.class);

    private final static String CHANNEL_ID_KEY = "channelId";

    @Autowired
    private RpcChannelManager rpcChannelManager;

    @Override
    public void onChannelConnect(String remoteAddr, Channel channel) {
        logger.info("connect远程链接:" + remoteAddr);
        Long channelId = ChannelUtils.getChannelId();
        ChannelUtils.putAttr(CHANNEL_ID_KEY, String.valueOf(channelId), channel);
    }

    @Override
    public void onChannelClose(String remoteAddr, Channel channel) {
        logger.info("close远程链接:" + remoteAddr);
    }

    @Override
    public void onChannelException(String remoteAddr, Channel channel) {

    }

    @Override
    public void onChannelIdle(String remoteAddr, Channel channel) {

    }

}
