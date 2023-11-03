package com.Bhalerao.ScrumPlay.controller;

import com.Bhalerao.ScrumPlay.Dto.PlayerDto;
import lombok.Builder;

import com.Bhalerao.ScrumPlay.Dto.SprintDto;
import com.Bhalerao.ScrumPlay.service.PlayerService;
import com.Bhalerao.ScrumPlay.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PlayerController {
    private static final Logger log = LoggerFactory.getLogger(PlayerController.class);

    private PlayerService playerService;
    private SprintService sprintService;

    @Autowired
    public PlayerController(PlayerService playerService, SprintService sprintService) {
        this.playerService = playerService;
        this.sprintService = sprintService;
    }

    @PostMapping("/add-gameConfig")
    public ResponseEntity<String> addPlayer(@RequestBody Map<String, Object> requestData) {
        try {
            List<Map<String, String>> playerDataList = (List<Map<String, String>>) requestData.get("players");

            List<PlayerDto> playerDtos = new ArrayList<>();

            // Iterate over the player data and create PlayerDto objects
            PlayerDto playerDto = null;
            for (Map<String, String> playerData : playerDataList) {
                String playerName = playerData.get("playerName");
                String playerRole = playerData.get("playerRole");
                playerDto = PlayerDto.builder()
                        .playerName(playerName)
                        .playerRole(playerRole)
                        .build();
                playerDtos.add(playerDto);
            }
            playerService.savePlayers(playerDtos);

            int teamSize = (int) requestData.get("teamSize");
            int sprintLength = (int) requestData.get("sprintLength");
            float scrumCallLength = ((Double) requestData.get("scrumCallLength")).floatValue();

            SprintDto sprintDto = SprintDto.builder()
                    .teamSize(teamSize)
                    .sprintLength(sprintLength)
                    .scrumCallLength(scrumCallLength)
                    .build();
            sprintService.saveSprint(sprintDto);

            return ResponseEntity.ok("Player and Sprint added successfully");
        } catch (Exception e) {
            log.error("Error adding player and sprint", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding player and sprint");
        }
    }
}
