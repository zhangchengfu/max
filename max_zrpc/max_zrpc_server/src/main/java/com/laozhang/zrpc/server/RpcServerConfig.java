package com.laozhang.zrpc.server;

import com.laozhang.zrpc.HTTPTransportServer;
import com.laozhang.zrpc.TransportServer;
import com.laozhang.zrpc.codec.Decoder;
import com.laozhang.zrpc.codec.Encoder;
import com.laozhang.zrpc.codec.JSONDecoder;
import com.laozhang.zrpc.codec.JSONEncoder;
import lombok.Data;

/**
 * server配置
 */
@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private int port = 8800;

}
