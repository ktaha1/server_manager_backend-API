package io.kam.server.resource;

import static io.kam.server.enumeration.Status.SERVER_UP;
import io.kam.server.model.Response;
import io.kam.server.model.Server;
import io.kam.server.service.implementation.ServerServiceImpl;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.time.LocalDateTime.now;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
public class ServerResource {
    private final ServerServiceImpl service;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Collections.singletonMap("servers", service.list(30)))
                        .message("Servers retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable String ipAddress) throws IOException {
        Server server = service.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Collections.singletonMap("servers", server))
                        .message(server.getStatus() == SERVER_UP ? "Ping success":"Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Collections.singletonMap("server", service.create(server)))
                        .message("Server created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Collections.singletonMap("server", service.get(id)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Collections.singletonMap("deleted", service.delete(id)))
                        .message("Server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/img/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Download/images"+fileName));
    }
}
