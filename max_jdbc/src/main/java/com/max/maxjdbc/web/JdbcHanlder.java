package com.max.maxjdbc.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class JdbcHanlder {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ArrayBlockingQueue queue;

    public void batch(final Collection collection) {
        queue.drainTo(collection);
        String sql="insert into book(name,pbYear) values(?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                /*String name=collection.get(i).getName();
                int pbYear=collection.get(i).getPbYear();
                ps.setString(1, name);
                ps.setInt(2, pbYear);*/
            }

            @Override
            public int getBatchSize() {
                return collection.size();
            }
        });
    }
}
