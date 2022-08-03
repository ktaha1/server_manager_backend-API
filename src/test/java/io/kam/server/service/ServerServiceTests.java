package io.kam.server.service;

import io.kam.server.enumeration.Status;
import io.kam.server.model.Server;
import io.kam.server.repo.ServerRepo;
import io.kam.server.service.implementation.ServerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServerServiceTests {

    @Mock
    private ServerRepo serverRepo;

    @InjectMocks
    private ServerServiceImpl serverService;

    private Server server;

    @BeforeEach
    public void setup(){
        this.server = Server.builder()
                .id(10L)
                .name("Testing Server")
                .ipAddress("192.168.1.10")
                .status(Status.SERVER_UP)
                .build();
    }

}
