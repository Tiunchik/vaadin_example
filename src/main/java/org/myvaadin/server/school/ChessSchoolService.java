package org.myvaadin.server.school;


import org.myvaadin.server.overallservices.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChessSchoolService {

    private SchoolRepository schlRep;

    private Mapper map;

    public ChessSchoolService(@Autowired SchoolRepository schlRep,
                              @Autowired Mapper map) {
        this.schlRep = schlRep;
        this.map = map;
    }

    public List<ChessSchoolDTO> getAllSchools() {
        List<ChessSchool> answer = schlRep.findAll();
        return answer.stream()
                .map(ex -> map.converToSchoolDTO(ex))
                .collect(Collectors.toList());
    }

    public ChessSchoolDTO getSchool(int id) {
        Optional<ChessSchool> plr = schlRep.findById(id);
        return plr.map(chsPlr -> map.converToSchoolDTO(chsPlr)).orElse(null);
    }

    public ChessSchoolDTO saveUpdateSchool(ChessSchoolDTO school) {
        return map.converToSchoolDTO(schlRep.save(map.convertToSchool(school)));
    }

    public boolean deleteSchool(int id) {
        schlRep.deleteById(id);
        return !schlRep.existsById(id);
    }
}
