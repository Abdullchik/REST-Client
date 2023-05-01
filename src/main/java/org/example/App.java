package org.example;

import org.example.config.SpringConfig;
import org.example.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        ApiService apiService = applicationContext.getBean("apiService", ApiService.class);
        String result;
        ResponseEntity<String> responseEntity = apiService.showUsers();
        String cookie = responseEntity.getHeaders().getFirst("Set-Cookie");
        result = apiService.saveUser(new User(3L, "James", "Brown", (byte) 27), cookie);
        result += apiService.saveUser(new User(3L, "Thomas", "Shelby", (byte) 27), cookie);
        result += apiService.deleteUser(3L, cookie);
        System.out.println(result);
        System.out.println(result.length());
    }
}
