package io.kam.server.service;

import io.kam.server.enumeration.Status;
import io.kam.server.model.Server;
import io.kam.server.repo.ServerRepo;
import io.kam.server.service.implementation.ServerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Slf4j
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

    @Test
    @DisplayName("JUnit Test for Save() method")
    public void givenServerObject_whenSaveServer_thenReturnServerObject(){
        //Given - precondition or setup
        /*given(serverRepo.findById(this.server.getId()))
                .willReturn(Optional.empty());*/
        given(serverRepo.save(server))
                .willReturn(server);

        // When - Action or behavior that we re going to test
        Server savedServer = serverService.create(this.server);

        //Then - Verify the output
        log.info("Saved Server : {}", savedServer);
        Assertions.assertThat(savedServer).isNotNull();
    }

}
