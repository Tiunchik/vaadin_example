package org.myvaadin.server.overallservices;

import org.myvaadin.server.game.ChessGame;
import org.myvaadin.server.game.ChessGameDTO;
import org.myvaadin.server.player.ChessPlayer;
import org.myvaadin.server.player.ChessPlayerDTO;
import org.myvaadin.server.school.ChessSchool;
import org.myvaadin.server.school.ChessSchoolDTO;
import org.myvaadin.server.security.Roles;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class Mapper {

    public ChessPlayerDTO convertToPlayerDTO(ChessPlayer plr) {
        ChessPlayerDTO dto = new ChessPlayerDTO();
        dto.setId(plr.getId());
        dto.setName(plr.getName());
        dto.setElo(plr.getElo());
        dto.setLogin(plr.getLogin());
        dto.setSchool(converToSchoolDTO(plr.getSchool()));
        dto.setRoles(plr.getRoles().stream()
                .map(Enum::toString)
                .collect(Collectors.toList()));
        return dto;
    }

    public ChessSchoolDTO converToSchoolDTO(ChessSchool schl) {
        ChessSchoolDTO dto = new ChessSchoolDTO();
        dto.setId(schl.getId());
        dto.setCalled(schl.getCalled());
        return dto;
    }

    public ChessGameDTO convertToGameDTO(ChessGame game) {
        ChessGameDTO dto = new ChessGameDTO();
        dto.setId(game.getId());
        dto.setFirstplayer(convertToPlayerDTO(game.getFirstPlayer()));
        dto.setSecondplayer(convertToPlayerDTO(game.getSecondPlayer()));
        dto.setGameFinished(game.getGameFinished().getTime());
        dto.setFirstPlayerEloChanged(game.getFirstPlayerEloChanged());
        dto.setSecondPlayerEloChanged(game.getSecondPlayerEloChanged());
        return dto;
    }

    public ChessPlayer convertToPlayer(ChessPlayerDTO dto) {
        ChessPlayer plr = new ChessPlayer(dto.getId());
        plr.setName(dto.getName());
        plr.setElo(dto.getElo());
        plr.setSchool(convertToSchool(dto.getSchool()));
        plr.setLogin(dto.getLogin());
        plr.setRoles(dto.getRoles()
                .stream()
                .map(Roles::valueOf)
                .collect(Collectors.toList()));
        return plr;
    }

    public ChessSchool convertToSchool(ChessSchoolDTO dto) {
        ChessSchool schl = new ChessSchool(dto.getId());
        schl.setCalled(dto.getCalled());
        return schl;
    }

    public ChessGame convertToGame(ChessGameDTO dto) {
        ChessGame game = new ChessGame(dto.getId());
        game.setFirstPlayer(convertToPlayer(dto.getFirstplayer()));
        game.setSecondPlayer(convertToPlayer(dto.getSecondplayer()));
        game.setGameFinished(new Timestamp(dto.getGameFinished()));
        game.setFirstPlayerEloChanged(dto.getFirstPlayerEloChanged());
        game.setSecondPlayerEloChanged(dto.getSecondPlayerEloChanged());
        return game;
    }
}
