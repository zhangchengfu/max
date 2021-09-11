package com.laozhang.shardingsphere.service.impl;

import com.laozhang.shardingsphere.entity.EncryptUser;
import com.laozhang.shardingsphere.repository.EncryptUserRepository;
import com.laozhang.shardingsphere.service.EncryptUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class EncryptUserServiceImpl implements EncryptUserService {

	@Autowired
	private EncryptUserRepository encryptUserRepository;
	
	@Override
	public void processEncryptUsers() throws SQLException {
		insertEncryptUsers();
	}
	
	private List<Long> insertEncryptUsers() throws SQLException {
		List<Long> result = new ArrayList<>(10);
        for (Long i = 1L; i <= 10; i++) {
        	EncryptUser encryptUser = new EncryptUser();
        	encryptUser.setUserId(i);
        	encryptUser.setUserName("test_" + i);
        	encryptUser.setPwd("pwd" + i);
            encryptUserRepository.addEntity(encryptUser);
            result.add(encryptUser.getUserId());
        }
        
        return result;		
	}

	@Override
	public List<EncryptUser> getEncryptUsers() throws SQLException {
		return encryptUserRepository.findEntities();
	}
}
