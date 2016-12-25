package ru.sbt.accountservice.utils;


import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class Locker {
    private final ConcurrentMap<Long, Item> storage = new ConcurrentHashMap<>();

    public Object getOrRecreate(Long key) {
        return storage.compute(key, (k, v) -> {
            if(v == null) {
                return new Item(new Object(), 1);
            }
            v.counter++;
            return v;
        }).value;
    }

    public void release(Long key) {
        storage.compute(key, (k, v) -> {
            if(--v.counter == 0) {
                return null;
            }
            return v;
        });
    }

    private static class Item {
        Object value;
        int counter;

        Item(Object value, int counter) {
            this.value = value;
            this.counter = counter;
        }
    }
}
