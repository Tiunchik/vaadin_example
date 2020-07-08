package org.myvaadin.server.game;

import org.myvaadin.server.player.ChessPlayerDTO;

public class ChessGameDTO {

    private long id;

    private ChessPlayerDTO firstplayer;

    private ChessPlayerDTO secondplayer;

    private long gameFinished;

    private int firstPlayerEloChanged;

    private int secondPlayerEloChanged;

    public ChessGameDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChessPlayerDTO getFirstplayer() {
        return firstplayer;
    }

    public void setFirstplayer(ChessPlayerDTO firstplayer) {
        this.firstplayer = firstplayer;
    }

    public ChessPlayerDTO getSecondplayer() {
        return secondplayer;
    }

    public void setSecondplayer(ChessPlayerDTO secondplayer) {
        this.secondplayer = secondplayer;
    }

    public long getGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(long gameFinished) {
        this.gameFinished = gameFinished;
    }

    public int getFirstPlayerEloChanged() {
        return firstPlayerEloChanged;
    }

    public void setFirstPlayerEloChanged(int firstPlayerEloChanged) {
        this.firstPlayerEloChanged = firstPlayerEloChanged;
    }

    public int getSecondPlayerEloChanged() {
        return secondPlayerEloChanged;
    }

    public void setSecondPlayerEloChanged(int secondPlayerEloChanged) {
        this.secondPlayerEloChanged = secondPlayerEloChanged;
    }
}
