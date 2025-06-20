package com.fabestonia.streamtool.streamtool.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

@Service
public class GameService {
    private final Map<String, String> texts = new ConcurrentHashMap<>();
    private final List<BiConsumer<String, String>> listeners = new ArrayList<>();

    public String getText(String chave) {
        return texts.getOrDefault(chave, "0");
    }

    public void setText(String key, String newValue) {
        texts.put(key, newValue);
        notify(key, newValue);
    }

    public void addListeners(BiConsumer<String, String> listener) {
        listeners.add(listener);
    }

    private void notify(String key, String value) {
        for (BiConsumer<String, String> listener : listeners) {
            listener.accept(key, value);
        }
    }
}
