package com.premierleague.premier_league.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByTeam(String teamName) {
        return playerRepository.findByTeamIgnoreCaseContaining(teamName);
    }

    public List<Player> getPlayersByName(String name) {
        return playerRepository.findByNameIgnoreCaseContaining(name);
    }

    public List<Player> getPlayersByPosition(String position) {
        return playerRepository.findByPosIgnoreCaseContaining(position);
    }

    public List<Player> getPlayersByNation(String nation) {
        return playerRepository.findByNationIgnoreCaseContaining(nation);
    }

    public List<Player> getPlayersByTeamAndPostion(String team, String positon) {
        return playerRepository.findByTeamIgnoreCaseContainingAndPosIgnoreCaseContaining(team, positon);
    }

    public Player addPlayer(Player player) {
        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer(Player updatedPlayer) {
        Optional<Player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());
        if (existingPlayer.isPresent()) {
            Player playerToUpdate = existingPlayer.get();
            playerToUpdate.setName(updatedPlayer.getName());
            playerToUpdate.setTeam(updatedPlayer.getTeam());
            playerToUpdate.setPos(updatedPlayer.getPos());
            playerToUpdate.setNation(updatedPlayer.getNation());
            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }
        return null;
    }

    @Transactional
    public String deletePlayer(String playerName) {
        Optional<Player> existingPlayer = playerRepository.findByName(playerName);
        if (existingPlayer.isPresent()) {
            playerRepository.deleteByName(existingPlayer.get().getName());
            return "Player deleted successfully";
        }
        return "Player not found";
    }
}
