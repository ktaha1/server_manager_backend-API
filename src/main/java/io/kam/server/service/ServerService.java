package io.kam.server.service;

import io.kam.server.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server ping(String ipAddress) throws IOException;
    Server create(Server server);
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update(Server server);
    boolean delete(Long id);
}
