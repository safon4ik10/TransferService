package ru.netology.transferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.netology.transferservice.data.Amount;
import ru.netology.transferservice.data.Operation;
import ru.netology.transferservice.data.Transfer;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferServiceApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    public static GenericContainer<?> container = new GenericContainer<>("appserver")
            .withExposedPorts(8080);

    @BeforeAll
    public static void setUp() {
        container.start();
    }

    @Test
    void testTransfer() throws URISyntaxException {
        Integer port = container.getMappedPort(8080);
        String url = "http://localhost:" + port + "/transfer";
        URI uri = new URI(url);
        Transfer transfer = new Transfer(
                "1111111111111111",
                "11/23",
                "111",
                "2222222222222222",
                new Amount(500, "RUB")
        );

        HttpEntity<Transfer> request = new HttpEntity<>(transfer);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertNotNull(result);
    }

    @Test
    void testConfirmOperation() throws URISyntaxException {
        Integer port = container.getMappedPort(8080);
        String url = "http://localhost:" + port + "/confirmOperation";
        URI uri = new URI(url);

        HttpEntity<Operation> request = new HttpEntity<>(new Operation("123", "456"));

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertEquals("{\"operationId\":\"Success\"}", result.getBody());
    }

}
