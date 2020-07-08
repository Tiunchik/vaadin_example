package org.myvaadin.server.game;

import org.myvaadin.server.overallservices.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChessGameService {

    private GameRepository gameRep;

    private Mapper map;

    public ChessGameService(@Autowired GameRepository gameRep,
                            @Autowired Mapper map) {
        this.gameRep = gameRep;
        this.map = map;
    }

    public List<ChessGameDTO> getAllGames() {
        List<ChessGame> answer = gameRep.findAll();
        return answer.stream()
                .map(ex -> map.convertToGameDTO(ex))
                .collect(Collectors.toList());
    }

    public ChessGameDTO getGame(long id) {
        Optional<ChessGame> plr = gameRep.findById(id);
        return plr.map(chsPlr -> map.convertToGameDTO(chsPlr)).orElse(null);
    }

    public ChessGameDTO saveUpdateGame(ChessGameDTO plr) {
        return map.convertToGameDTO(gameRep.save(map.convertToGame(plr)));

    }

    public boolean deleteGame(long id) {
        gameRep.deleteById(id);
        return !gameRep.existsById(id);
    }

    public int howManyGames(int id) {
        return gameRep.howManyGames(id);
    }

}
