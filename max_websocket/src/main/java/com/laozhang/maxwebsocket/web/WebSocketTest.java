package com.laozhang.maxwebsocket.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@ServerEndpoint(value = "/websocket")
public class WebSocketTest {
    private static Logger logger = LoggerFactory.getLogger(WebSocketTest.class);

    //线程安全的静态变量，表示在线连接数
    private static volatile int onlineCount = 0;

    //用来存放每个客户端对应的websocketTest对象，适用与同时与每个客户端通信
    public static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet();

    //若要实现服务端与指定客户端通信的话，可以使用Map来完成，其中key可以为用户标识
    public static ConcurrentHashMap<Session, Object> websocketMap = new ConcurrentHashMap();

    // 与某个客户端的连接会话，通过它实现定向推送（只推送给某个用户）
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数，session为与某个客户端的连接会话，需要通过它给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        //加入到set中
        webSocketSet.add(this);
        //加入到map中
        websocketMap.put(session, this);
        //在线数加1
        addOnlineCount();
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     *
     * @param closeSession
     */
    @OnClose
    public void onClose(Session closeSession) {
        //从set中删除
        webSocketSet.remove(this);
        //从map中删除
        websocketMap.remove(closeSession);
        subOnlineCount();
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session mySession) throws Exception {
        logger.info("来自客户端的消息：" + message);
        // 群发消息
        for (WebSocketTest item : webSocketSet) {
            try {
                item.sendAllMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //推送给单个客户端
        for (Session session : websocketMap.keySet()) {
            if (session.equals(mySession)) {
                WebSocketTest item = (WebSocketTest) websocketMap.get(mySession);
                try {
                    String msg = "hi,这是返回的信息";
                    item.sendMessage(mySession, msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("发生错误");
    }

    //给所有客户发送信息
    public void sendAllMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    //定向发送信息
    public void sendMessage(Session mySession, String message) throws IOException {
        synchronized (this) {
            try {
                //该session如果已被删除，则不执行发送请求
                if (mySession.isOpen()) {
                    mySession.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        onlineCount--;
    }
}
