package com.premierleague.premier_league.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByTeam(String teamName){
        return playerRepository.findAll().stream()
                .filter(player -> player.getTeam() != null && player.getTeam().toLowerCase().contains(teamName.toLowerCase()))
                .collect(Collectors.toList());
    }

    // if you want to search by last name of first name or something
    public List<Player> getPlayersByName(String name){
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPosition(String position){
        return playerRepository.findAll().stream()
                .filter(player -> player.getPos()!= null && player.getPos().toLowerCase().contains(position.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String nation){
        return playerRepository.findAll().stream()
                .filter(player -> player.getNation() != null && player.getNation().toLowerCase().contains(nation.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPostion(String team, String positon) {
        return playerRepository.findAll().stream()
                .filter(player ->
                        player.getTeam() != null &&
                                player.getPos() != null &&
                                player.getTeam().toLowerCase().contains(team.toLowerCase()) &&
                                player.getPos().toLowerCase().contains(positon.toLowerCase())
                )
                .collect(Collectors.toList());
    }


    public Player addPlayer(Player player){
        playerRepository.save(player);
        return player;
    }

    // if players switch teams and stuff
    public Player updatePlayer(Player updatedPlayer){
        Optional<Player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());
        if(existingPlayer.isPresent()){
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

    // to maintain the data integrity during this delete operation
    @Transactional
    public String deletePlayer(String playerName){
        Optional<Player> existingPlayer = playerRepository.findByName(playerName);
        if(existingPlayer.isPresent()){
            playerRepository.deleteByName(existingPlayer.get().getName());
            return "Player deleted successfully";
        }
        return "Player not found";
    }
}
