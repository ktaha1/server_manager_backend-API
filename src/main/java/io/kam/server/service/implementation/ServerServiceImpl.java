package io.kam.server.service.implementation;

import io.kam.server.model.Server;
import io.kam.server.repo.ServerRepo;
import io.kam.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerRepo repo;


    @Override
    public Server create(Server server) {
        return repo.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        return repo.findAll().subList(0, limit);
    }

    @Override
    public Server get(Long id) {
        return repo.getById(id);
    }

    @Override
    public Server update(Server server) {
        return repo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        repo.deleteById(id);
        return true;
    }
}
