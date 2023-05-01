package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private final String ApiURL = "http://94.198.50.185:7081/api/users";

    public ApiService(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> showUsers() {
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(ApiURL, HttpMethod.GET, null
                        , new ParameterizedTypeReference<>() {});
        return responseEntity;
    }

    public String saveUser(User user, String cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Cookie", cookies);
        HttpEntity<String> requestEntity;
        try {
            System.out.println(objectMapper.writeValueAsString(user));
             requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(ApiURL, HttpMethod.POST, requestEntity
                        , new ParameterizedTypeReference<>() {});
        System.out.println(requestEntity.getHeaders());
        return responseEntity.getBody();
    }

    public String deleteUser(Long id, String cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Cookie", cookies);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(ApiURL + "/" + id, HttpMethod.DELETE, requestEntity
                        , new ParameterizedTypeReference<>() {});
        System.out.println(responseEntity.getHeaders());
        return responseEntity.getBody();
    }
}
