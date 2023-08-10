package com.example.testcontroller;

import com.example.testcontroller.service.impl.CheckMethod;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class TestcontrollerApplicationTests extends CheckMethod {

    @Value("${ip.count}")
    private int count;

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    public void testCase(String ip) throws ExecutionException, InterruptedException {

        CompletableFuture future1 = CompletableFuture.runAsync(() -> {
            for (int i = 1; i <= count; i++) {
                ResponseEntity responseEntity = checkController(ip);
                System.out.println(responseEntity);
            }
        });
        CompletableFuture future2 = CompletableFuture.runAsync(() -> {
            for (int i = 1; i <= count; i++) {
                ResponseEntity responseEntity = checkController(ip);
                System.out.println(responseEntity);
            }
        });
        CompletableFuture.allOf(future1, future2).thenAccept(v -> {
            System.out.println(v);
        }).get();
        // For error (count >)
        ResponseEntity responseEntity = checkController(ip);
        System.out.println(responseEntity);
    }

}
