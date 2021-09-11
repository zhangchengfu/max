
package com.laozhang.shardingsphere.repository;

import java.sql.SQLException;
import java.util.List;

public interface BaseRepository<T, P> {
    
    P addEntity(T entity) throws SQLException;
    
    void deleteEntity(P primaryKey) throws SQLException;
    
    List<T> findEntities() throws SQLException;
}
