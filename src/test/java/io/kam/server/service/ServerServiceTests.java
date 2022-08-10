package io.kam.server.service;

import io.kam.server.enumeration.Status;
import io.kam.server.model.Server;
import io.kam.server.repo.ServerRepo;
import io.kam.server.service.implementation.ServerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import org.mockito.ArgumentCaptor;
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
    public void setup() {
        this.server = Server.builder()
                .id(10L)
                .name("Testing Server")
                .ipAddress("192.168.1.10")
                .status(Status.SERVER_UP)
                .build();
    }

    @Test
    @DisplayName("Test for Save() method")
    public void givenServerObject_whenSaveServer_thenReturnServerObject() {

        // When
        serverService.create(this.server);

        // Then
        ArgumentCaptor<Server> serverArgumentCaptor = ArgumentCaptor.forClass(Server.class);
        verify(serverRepo).save(serverArgumentCaptor.capture());
        Server capturedServer = serverArgumentCaptor.getValue();
        log.info("Saved Server : {}", capturedServer);
        assertThat(capturedServer).isNotNull();
        assertThat(capturedServer.getImageUrl()).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit test for Get() method")
    public void givenServerId_whenGetServerById_thenReturnServerObject() {
        given(serverRepo.findById(server.getId()))
                .willReturn(Optional.of(server));

        Server foundedServer = serverService.get(server.getId());

        log.info("Founded Server : {}", foundedServer);
        assertThat(foundedServer).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for Update() method")
    public void givenServerObject_whenUpdate_thenReturnUpdatedServer() {
        given(serverRepo.save(server))
                .willReturn(server);
        server.setStatus(Status.SERVER_DOWN);

        Server updatedServer = serverService.update(server);

        log.info("Updated Server : {}", updatedServer);
        assertThat(updatedServer.getStatus()).isEqualTo(Status.SERVER_DOWN);
    }

    @Test
    @DisplayName("JUnit test for Delete() method")
    public void givenServerId_whenDeleteServer_thenReturnTrue() {
        willDoNothing().given(serverRepo)
                .deleteById(server.getId());

        boolean isDeleted = serverService.delete(server.getId());

        assertThat(isDeleted).isTrue();

    }

}
