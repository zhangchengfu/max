package com.laozhang.max_oauth2_server.service;


import com.laozhang.max_oauth2_server.entity.Client;

import java.util.List;

public interface ClientService {

    Client createClient(Client client);

    Client updateClient(Client client);

    void deleteClient(Long clientId);

    Client findOne(Long clientId);

    List<Client> findAll();

    Client findByClientId(String clientId);

    Client findByClientSecret(String clientSecret);
}
