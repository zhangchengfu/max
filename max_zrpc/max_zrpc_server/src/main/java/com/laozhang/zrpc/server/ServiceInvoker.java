package com.laozhang.zrpc.server;

import com.laozhang.zrpc.Request;
import com.laozhang.zrpc.common.utils.ReflectionUtils;

/**
 * 调用具体服务
 */
public class ServiceInvoker {

    public Object invoke(ServiceInstance sis, Request request) {

        return ReflectionUtils.invoke(sis.getTarget(), sis.getMethod(), request.getParameters());
    }
}
