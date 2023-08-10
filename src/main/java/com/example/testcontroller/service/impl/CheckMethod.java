package com.example.testcontroller.service.impl;

import com.example.testcontroller.service.ICheckMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class CheckMethod implements ICheckMethod {
    private final ConcurrentHashMap<String, CounterReq> limiters = new ConcurrentHashMap<>();

    @Value("${ip.count}")
    private int count;
    @Value("${ip.minutes}")
    private int minutes;

    public ResponseEntity checkController(String ip) {
        final CounterReq counter = limiters.get(ip);
        if (Objects.isNull(counter)) {
            limiters.put(ip, new CounterReq());
        } else {
            counter.visitTimes.addLast(System.currentTimeMillis() / 1000);
            if (counter.getCounter() > count) {
                return new ResponseEntity(HttpStatus.BAD_GATEWAY);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    class CounterReq {
        public LinkedList<Long> visitTimes = new LinkedList() {{
            add(System.currentTimeMillis() / 1000);
        }};

        public int getCounter() {
            for (Iterator<Long> iterator = visitTimes.iterator(); iterator.hasNext(); ) {
                if (iterator.next() < (System.currentTimeMillis() / 1000) - (minutes * 60)) {
                    iterator.remove();
                } else {
                    break;
                }
            }
            return visitTimes.size();
        }
    }
}
