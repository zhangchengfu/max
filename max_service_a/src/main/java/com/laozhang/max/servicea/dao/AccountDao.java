package com.laozhang.max.servicea.dao;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface AccountDao {

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 金额
     */
    void decrease(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
