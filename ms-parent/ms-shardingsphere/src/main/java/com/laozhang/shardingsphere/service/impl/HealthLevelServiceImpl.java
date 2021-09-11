package com.laozhang.shardingsphere.service.impl;

import com.laozhang.shardingsphere.entity.HealthLevel;
import com.laozhang.shardingsphere.repository.HealthLevelRepository;
import com.laozhang.shardingsphere.service.HealthLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


@Service
public class HealthLevelServiceImpl implements HealthLevelService {

	@Autowired
	private HealthLevelRepository healthLevelRepository;
	
	@Override
	public void processLevels() throws SQLException {
		healthLevelRepository.addEntity(new HealthLevel(1L, "level1"));
		healthLevelRepository.addEntity(new HealthLevel(2L, "level2"));
		healthLevelRepository.addEntity(new HealthLevel(3L, "level3"));
		healthLevelRepository.addEntity(new HealthLevel(4L, "level4"));
		healthLevelRepository.addEntity(new HealthLevel(5L, "level5"));
	}
}
