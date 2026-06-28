package com.ticketti.discovery_server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.cloud.config.enabled=false", // Evita timeouts conectando al config server
        "eureka.client.register-with-eureka=false",
        "eureka.client.fetch-registry=false"
    }
)
class DiscoveryServerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Environment environment;

    @Test
    void contextoCarga() {
        assertThat(environment).isNotNull();
        assertThat(port).isGreaterThan(0);
    }

    @Test
    void eurekaDashboardLoads() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:" + port + "/"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body())
            .as("El dashboard de Eureka debe contener el estado del sistema")
            .contains("System Status");
    }

    @Test
    void eurekaAppsEndpointReturnsOk() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:" + port + "/eureka/apps"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isIn(200, 204);
    }
}
