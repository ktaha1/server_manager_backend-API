package io.kam.server;

import io.kam.server.model.Response;
import io.kam.server.model.Server;
import io.kam.server.resource.ServerResource;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerResourceTest {
    @Autowired
    private ServerResource serverResource;

    @LocalServerPort
    private int port;

    private final String resourceBaseUrl = "http://localhost:";

    private RestTemplate restTemplate;

    @Test
    @DisplayName("Context must be loaded ")
    public void contextLoad(){
        assertThat(serverResource).isNotNull();
    }

    @Test
    @DisplayName("List of servers is not empty")
    public void listOfServersExist(){
        restTemplate = new RestTemplate();
        String uri = resourceBaseUrl+port+"/server/list";
        ResponseEntity<Response> restResponse = restTemplate.getForEntity(uri, Response.class);
        Response response = restResponse.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getData().isEmpty()).isFalse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Pinging is Working")
    public void pingingFeatureIsWorking(){
        restTemplate = new RestTemplate();
        String uri = resourceBaseUrl+port+"/server/ping/192.168.1.1";
        ResponseEntity<Response> restResponse = restTemplate.getForEntity(uri, Response.class);
        Response response = restResponse.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Save Feature in Working")
    public void saveFeatureIsWorking(){
        restTemplate = new RestTemplate();
        String uri = resourceBaseUrl+port+"/server/save";
        Server server = Server.builder()
                .ipAddress("192.168.1.4")
                .name("Testing Server")
                .build();
        HttpEntity<Server> request = new HttpEntity<>(server);
        ResponseEntity<Response> restResponse = restTemplate.postForEntity(uri, request, Response.class);
        Response response = restResponse.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Delete Feature is Working")
    public void DeleteFeatureIsWorking(){
        restTemplate = new RestTemplate();
        String uri = resourceBaseUrl+port+"/server/delete/4";
        //Using Exchange API
        ResponseEntity<Response> restResponse = restTemplate.exchange(uri, HttpMethod.DELETE,null, Response.class);
        Response response = restResponse.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    }
}
