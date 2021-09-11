package com.laozhang.shardingsphere.service.impl;

import com.laozhang.shardingsphere.entity.User;
import com.laozhang.shardingsphere.repository.UserRepository;
import com.laozhang.shardingsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void processUsers() throws SQLException {
		insertUsers();
	}

	private List<Long> insertUsers() throws SQLException {
		List<Long> result = new ArrayList<>(10);
		for (Long i = 1L; i <= 10; i++) {
			User user = new User();
			user.setUserId(i);
			user.setUserName("user_" + i);
			userRepository.addEntity(user);
			result.add(user.getUserId());
			System.out.println("Insert User:" + user.getUserId());
	        
		}
		return result;
	}

	@Override
	public List<User> getUsers() throws SQLException {
		return userRepository.findEntities();
	}
}
