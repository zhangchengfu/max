package com.laozhang.zrpc.client;

import com.laozhang.zrpc.HTTPTransportClient;
import com.laozhang.zrpc.Peer;
import com.laozhang.zrpc.TransportClient;
import com.laozhang.zrpc.codec.Decoder;
import com.laozhang.zrpc.codec.Encoder;
import com.laozhang.zrpc.codec.JSONDecoder;
import com.laozhang.zrpc.codec.JSONEncoder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RpcClientConfig {

    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private Class<? extends  TransportSelector> selectorClass = RandomTransportSelector.class;

    private int connectCount = 1;

    private List<Peer> servers = Arrays.asList(
            new Peer("127.0.0.1", 8800)
    );
}
