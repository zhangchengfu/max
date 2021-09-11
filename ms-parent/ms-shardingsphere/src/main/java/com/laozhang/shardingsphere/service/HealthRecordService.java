package com.laozhang.shardingsphere.service;

import java.sql.SQLException;

public interface HealthRecordService {
	
	public void processHealthRecords() throws SQLException;

}
