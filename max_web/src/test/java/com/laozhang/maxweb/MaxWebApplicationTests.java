package com.laozhang.maxweb;

import com.laozhang.maxweb.base.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaxWebApplicationTests {

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Test
    public void contextLoads() {
        System.out.println("test");
    }

    /**
     * Spring RestTemplate 做  http请求
     */
    @Test
    public void testRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "1");
        User user = restTemplate.getForObject("http://localhost:8080/user/getById?id={id}", User.class, map);
        ResponseEntity entity = restTemplate.getForEntity("http://localhost:8080/user/getById?id={id}", User.class, map);
    }

    /**
     * Spring AsyncRestTemplate 做  http异步请求
     */
    @Test
    public void testAsyncRestTemplate() {
        String url = "http://localhost:8080/user/getById?id={id}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "1");
        ListenableFuture<ResponseEntity<User>> entity =  asyncRestTemplate.getForEntity(url, User.class, map);
        entity.addCallback(new SuccessCallback<ResponseEntity<User>>() {
            public void onSuccess(ResponseEntity<User> result) {
                System.out.println("a");
                User user = result.getBody();
            }
        }, new FailureCallback() {
            public void onFailure(Throwable ex) {
                System.out.println("b");
            }
        });

    }
}
