package com.courage.platform.schedule.server.rpc.processor;

import com.alibaba.fastjson.JSON;
import com.courage.platform.rpc.remoting.netty.codec.PlatformNettyRequestProcessor;
import com.courage.platform.rpc.remoting.netty.protocol.PlatformRemotingCommand;
import com.courage.platform.schedule.rpc.protocol.OtherTriggerCommand;
import com.courage.platform.schedule.server.service.ScheduleTriggerService;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 其他方调用server 来实现自动执行
 * Created by zhangyong on 2019/11/11.
 */
public class OtherTriggerTaskProcessor implements PlatformNettyRequestProcessor {

    private final static Logger logger = LoggerFactory.getLogger(OtherTriggerTaskProcessor.class);

    @Autowired
    private ScheduleTriggerService scheduleTriggerService;

    @Override
    public PlatformRemotingCommand processRequest(ChannelHandlerContext ctx, PlatformRemotingCommand request) throws Exception {
        byte[] bytes = request.getBody();
        OtherTriggerCommand otherTriggerCommand = JSON.parseObject(bytes, OtherTriggerCommand.class);
        scheduleTriggerService.doRpcTrigger(otherTriggerCommand.getJobId());
        PlatformRemotingCommand response = new PlatformRemotingCommand();
        return response;
    }

    @Override
    public boolean rejectRequest() {
        return false;
    }

}
