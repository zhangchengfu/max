package com.laozhang.max_shiro.service;

public interface IService<T> {
    int save(T entity);

    int delete(Object id);

    int update(T entity);

    T selectById(Object id);
}
