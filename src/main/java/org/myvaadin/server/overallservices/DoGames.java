package org.myvaadin.server.overallservices;

import org.myvaadin.server.game.ChessGameDTO;
import org.myvaadin.server.game.ChessGameService;
import org.myvaadin.server.player.ChessPlayerDTO;
import org.myvaadin.server.player.ChessPlayerService;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

@Service
@Scope(value = "singleton")
public class DoGames {

    private final ChessGameService gameService;

    private final ChessPlayerService plrService;

    private final Random rand = new Random();

    private final Mapper map;

    public DoGames(ChessGameService gameService, ChessPlayerService plrService, Mapper map) {
        this.gameService = gameService;
        this.plrService = plrService;
        this.map = map;
    }


    @Scheduled(fixedRate = 30000L)
    public void calculate() {
        ChessPlayerDTO first = plrService.getFirstPlayer();
        ChessPlayerDTO second = null;
        if (first != null) {
            second = plrService.getSecondPlayer(first.getId());
        }
        if (second != null) {
            double sa, sb;
            double ra = first.getElo(), rb = second.getElo();
            double ea = 1 / (1 + Math.pow(10, (rb - ra) / 400));
            double eb = 1 / (1 + Math.pow(10, (ra - rb) / 400));
            int result = rand.nextInt(2);
            sa = result == 0 ? 1 : (result == 2 ? 0.5 : 0);
            sb = result == 1 ? 1 : (result == 2 ? 0.5 : 0);
            int eloFirst = (int) (calculateKRatingForPlayer(first) * (sa - ea));
            int eloSecond = (int) (calculateKRatingForPlayer(second) * (sb - eb));
            if (saveGame(first, second, sa, eloFirst, eloSecond).getId() != 0) {
                plrService.changeElo(first.getId(), eloFirst);
                plrService.changeElo(second.getId(), eloSecond);
            }
        }
    }

    private double calculateKRatingForPlayer(ChessPlayerDTO player) {
        int gamesQuantity = gameService.howManyGames(player.getId());
        return gamesQuantity <= 30 ? 30.0 : (player.getElo() < 1600 ? 15.0 : 10);
    }

    private ChessGameDTO saveGame(ChessPlayerDTO first, ChessPlayerDTO second, double sa, int eloFirst, int eloSecond) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        ChessGameDTO game = new ChessGameDTO();
        game.setId(0);
        game.setGameFinished(cal.getTimeInMillis());
        game.setFirstplayer(sa == 1 ? first : sa == 0 ? second : first);
        game.setSecondplayer(sa == 1 ? second : sa == 0 ? first : second);
        game.setFirstPlayerEloChanged(sa == 1 ? eloFirst : sa == 0 ? eloSecond : eloFirst);
        game.setSecondPlayerEloChanged(sa == 1 ? eloSecond : sa == 0 ? eloFirst : eloSecond);
        game = gameService.saveUpdateGame(game);
        return game;
    }
}
