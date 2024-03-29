package io.kam.server.service.implementation;

import io.kam.server.enumeration.Status;
import io.kam.server.model.Server;
import io.kam.server.repo.ServerRepo;
import io.kam.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerRepo repo;


    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP : {}", ipAddress);
        Server server = repo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        repo.save(server);
        return server;
    }

    @Override
    public Server create(Server server) {
        log.info("Saving new Server : {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return repo.save(server);
    }

    @Override
    public Collection<Server> list() {
        log.info("Fetching all servers");
        return repo.findAll();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id : {}", id);
        return repo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating Server : {}", server.getName());
        return repo.save(server);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting Server by ID: {}", id);
        repo.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png","server4.png"};
        return imageNames[new Random().nextInt(4)];
    }
}
