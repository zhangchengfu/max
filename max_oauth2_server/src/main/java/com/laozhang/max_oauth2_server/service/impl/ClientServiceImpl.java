package com.laozhang.max_oauth2_server.service.impl;

import com.laozhang.max_oauth2_server.entity.Client;
import com.laozhang.max_oauth2_server.mapper.ClientMapper;
import com.laozhang.max_oauth2_server.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户端服务类
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public Client createClient(Client client) {
        // 暂时没有用到
        return null;
    }

    @Override
    public Client updateClient(Client client) {
        // 暂时没有用到
        return null;
    }

    @Override
    public void deleteClient(Long clientId) {

    }

    @Override
    public Client findOne(Long clientId) {
        return null;
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Client findByClientId(String clientId) {
        return clientMapper.selectByClientId(clientId);
    }

    @Override
    public Client findByClientSecret(String clientSecret) {
        return clientMapper.selectBySecret(clientSecret);
    }
}
