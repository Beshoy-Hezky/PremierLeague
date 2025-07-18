package com.premierleague.premier_league.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    void deleteByName(String playerName);
    Optional<Player> findByName(String name);

    List<Player> findByTeamIgnoreCaseContaining(String team);
    List<Player> findByNameIgnoreCaseContaining(String name);
    List<Player> findByPosIgnoreCaseContaining(String pos);
    List<Player> findByNationIgnoreCaseContaining(String nation);
    List<Player> findByTeamIgnoreCaseContainingAndPosIgnoreCaseContaining(String team, String pos);
}
