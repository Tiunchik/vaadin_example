package org.myvaadin.server.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<ChessGame, Long> {

    @Query(value = "select count(*) from game where firstplayer_id = ?1 or secondplayer_id =?1",
    nativeQuery = true)
    int howManyGames(int id);



}
