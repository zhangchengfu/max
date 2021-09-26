package com.laozhang.security.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@PostMapping
	public OrderInfo create(@RequestBody OrderInfo info, @AuthenticationPrincipal String username) {
//	public OrderInfo create(@RequestBody OrderInfo info, @RequestHeader String username) {
		log.info("user is " + username);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		PriceInfo price = restTemplate.getForObject("http://localhost:9060/prices/"+info.getProductId(), PriceInfo.class);
//		log.info("price is "+price.getPrice());
		return info;
	}
	
	@GetMapping("/{id}")
	public OrderInfo getInfo(@PathVariable Long id, @RequestHeader String username) {
		log.info("user is " + username);
		log.info("orderId is " + id);
		OrderInfo info = new OrderInfo();
		info.setId(id);
		info.setProductId(id * 5);
		return info;
	}

}
