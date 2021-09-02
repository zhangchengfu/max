package com.laozhang.max_interview;

import com.google.gson.Gson;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用：Map<String, Object> resultMap = WSExecutor.execute4Map(wsURL, method, params);
 */
public class WSExecutor {
	private static final Logger logger = LoggerFactory.getLogger(WSExecutor.class);
	
	private static Map<String, ClientThread> threadMap = new HashMap<>();
	
	private static JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
	
	private static Gson gson = new Gson();
	
	public static Object[] execute(String url,String method,Map<String, Object> params) {
		return callWS(url,method,params);
	}
	
	public static Map<String, Object> execute4Map(String url,String method,Map<String, Object> params) {
		return deNormalize(callWS(url,method,params));
	}
	
	private static Object[] callWS(String url,String method,Map<String, Object> params) {
		Client client = createClient(url);
		try {
			logger.info("Starting call WS url={},method={},params={}",url,method,gson.toJson(params));
			return client.invoke(method, gson.toJson(params));
		} catch (Exception e) {
			logger.error("Failed to call WS with url={},method={},params={}",url,method,gson.toJson(params),e);
			throw new RuntimeException("Failed to call WS",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, Object> deNormalize(Object[] result){
		if(result!=null && result[0]!=null) {
			return gson.fromJson(result[0].toString(), Map.class);
		}
		return Collections.EMPTY_MAP;
	}
	
	
	private static Client createClient(String url) {
		ClientThread t = null;
		if(threadMap.containsKey(url)) {
			t = threadMap.get(url);
		}else {
			t = new ClientThread(url);
			threadMap.put(url, t);
			t.start();
		}
		return t.getClient();
		
	}
	
	private static class ClientThread extends Thread{
		private Client client;
		private String url;
		private boolean ready;
		
		private ClientThread(String url) {
			this.url = url;
		}
		@Override
		public void run() {
			try {
				client = factory.createClient(url);
			} catch (Exception e) {
				throw new RuntimeException("无法创建WS client url="+url,e);
			}finally {
				ready = true;
			}
			
		}
		public Client getClient() {
			while(!ready) {
				try {
					sleep(500L);
				} catch (InterruptedException e) {
				}
			}
			return client;
		}
	}
}
