package com.fabestonia.streamtool.streamtool.controller;

import com.fabestonia.streamtool.streamtool.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/text/{key}")
    public String getText(@PathVariable String key) {
        return gameService.getText(key);
    }

    @PostMapping("/text/{key}")
    public void setText(@PathVariable String key, @RequestBody String newValue) {
        System.out.println(newValue);
        gameService.setText(key, newValue);
    }
}
