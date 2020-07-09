package org.myvaadin.server.player;

import org.myvaadin.server.overallservices.Mapper;
import org.myvaadin.server.school.ChessSchoolDTO;
import org.myvaadin.server.school.ChessSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChessPlayerService {

    private PlayerRepository plrRep;

    private ChessSchoolService schoolService;

    private Mapper map;

    public ChessPlayerService(@Autowired PlayerRepository plrRep,
                              @Autowired ChessSchoolService schoolService,
                              @Autowired Mapper map) {
        this.plrRep = plrRep;
        this.schoolService = schoolService;
        this.map = map;
    }

    public List<ChessPlayerDTO> getAllBestPlayersInLast5Minutes() {
        List<ChessPlayer> answer = plrRep.bestInLastFiveMinutes();
        return answer.stream()
                .map(ex -> map.convertToPlayerDTO(ex))
                .collect(Collectors.toList());
    }

    public List<ChessPlayerDTO> getAllPlayers() {
        List<ChessPlayer> answer = plrRep.findAll();
        return answer.stream()
                .map(ex -> map.convertToPlayerDTO(ex))
                .collect(Collectors.toList());
    }

    public ChessPlayerDTO getPlayer(int id) {
        Optional<ChessPlayer> plr = plrRep.findById(id);
        return plr.map(chessPlayer -> map.convertToPlayerDTO(chessPlayer)).orElse(null);
    }

    public ChessPlayerDTO saveUpdatePlayer(ChessPlayerDTO plr) {
        if (plr.getElo() == 0) {
            plr.setElo(400);
        }
        return map.convertToPlayerDTO(plrRep.save(map.convertToPlayer(plr)));
    }

    public boolean deletePlayer(int id) {
        plrRep.deleteById(id);
        return !plrRep.existsById(id);
    }

    public ChessPlayerDTO getFirstPlayer() {
        return map.convertToPlayerDTO(plrRep.getFirstPlayer());
    }

    public ChessPlayerDTO getSecondPlayer(int id) {
        return map.convertToPlayerDTO(plrRep.getSecondPlayer(id));
    }

    public void changeElo(int id, int elo){
        plrRep.changeElo(id, elo);
    }

    public int count(int id){
        return plrRep.count(id);
    }

    public Map<String,Integer> getHistogrammInfo(){
        HashMap<String, Integer> answer = new HashMap<>();
        schoolService.getAllSchools().forEach(school -> {
            answer.put(school.getCalled(), count(school.getId()));
        });
        return answer;
    }

}
