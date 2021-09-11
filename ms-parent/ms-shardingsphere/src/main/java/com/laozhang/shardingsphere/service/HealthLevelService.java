package com.laozhang.shardingsphere.service;

import java.sql.SQLException;

public interface HealthLevelService {

	public void processLevels() throws SQLException;
}
