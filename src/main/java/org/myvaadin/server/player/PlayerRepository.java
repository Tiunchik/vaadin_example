package org.myvaadin.server.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<ChessPlayer, Integer> {

    @Query(value = "select * from player order by random() limit 1",
            nativeQuery = true)
    ChessPlayer getFirstPlayer();

    @Query(value="select * from player where school_id != ?1 order by random() limit 1",
            nativeQuery = true)
    ChessPlayer getSecondPlayer(int school_id);

    @Modifying
    @Transactional
    @Query(value = "update player set elo = elo + ?2 where id = ?1",
    nativeQuery = true)
    int changeElo(int player_id, int elochange);

    @Query(value = "select * from\n" +
            "    (select p.id, p.elo, p.name, p.login, p.password, p.school_id, sum(g.elo) cnt from\n" +
            "    (select g1.firstplayer_id id, g1.first_player_elo_changed elo, g1.game_finished fin from game g1\n" +
            "    union\n" +
            "    select g2.secondplayer_id id, g2.second_player_elo_changed elo, g2.game_finished fin from game g2) g\n" +
            "       left outer join player p on g.id = p.id\n" +
            "    where (g.fin >= DATEADD(MINUTE, -5 ,CURRENT_TIMESTAMP))\n" +
            "    group by p.id\n" +
            "    order by cnt DESC) last where cnt > 0 limit 5",
            nativeQuery = true)
    List<ChessPlayer> bestInLastFiveMinutes();

    ChessPlayer findByLoginAndPassword(String login, String pass);

    @Query(value = "select count(*) from player where player.SCHOOL_ID = ?1",
            nativeQuery = true)
    int count(int school_id);
}
