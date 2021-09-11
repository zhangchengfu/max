package com.laozhang.shardingsphere.service;

import com.laozhang.shardingsphere.entity.User;

import java.sql.SQLException;
import java.util.List;


public interface UserService {
	
	public void processUsers() throws SQLException;
	
	public List<User> getUsers() throws SQLException;

}
