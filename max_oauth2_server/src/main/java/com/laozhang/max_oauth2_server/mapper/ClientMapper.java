package com.laozhang.max_oauth2_server.mapper;

import com.laozhang.max_oauth2_server.entity.Client;
import org.apache.ibatis.annotations.Param;

public interface ClientMapper {

    Client selectByClientId(@Param("clientId") String clientId);

    Client selectBySecret(@Param("clientSecret") String clientSecret);
}
