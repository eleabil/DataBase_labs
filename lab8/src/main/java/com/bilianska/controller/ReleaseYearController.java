package com.bilianska.controller;

import com.bilianska.DTO.impl.ReleaseYearDTO;
import com.bilianska.domain.ReleaseYear;

import com.bilianska.exceptions.*;
import com.bilianska.service.ReleaseYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ReleaseYearController {
    @Autowired
    ReleaseYearService releaseYearService;

    @GetMapping(value = "/api/Release_year")
    public ResponseEntity<Set<ReleaseYearDTO>> getAllReleaseYears() throws NoSuchAlbumException, NoSuchSingerException, NoSuchReleaseYearException {
        List<ReleaseYear> ReleaseYearSet = releaseYearService.getAllReleaseYear();
        Link link = linkTo(methodOn(ReleaseYearController.class).getAllReleaseYears()).withSelfRel();

        Set<ReleaseYearDTO> releaseYearDTOS = new HashSet<>();
        for (ReleaseYear entity : ReleaseYearSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            ReleaseYearDTO dto = new ReleaseYearDTO(entity, selfLink);
            releaseYearDTOS.add(dto);
        }

        return new ResponseEntity<>(releaseYearDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Release_year/{year_id}")
    public ResponseEntity<ReleaseYearDTO> getReleaseYear(@PathVariable Long year_id) throws NoSuchReleaseYearException, NoSuchAlbumException, NoSuchSingerException {
        ReleaseYear releaseYear = releaseYearService.getReleaseYear(year_id);
        Link link = linkTo(methodOn(ReleaseYearController.class).getReleaseYear(year_id)).withSelfRel();
        System.out.println(releaseYear);
        ReleaseYearDTO cpuDTO = new ReleaseYearDTO(releaseYear, link);

        return new ResponseEntity<>(cpuDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/Release_year/{year_id}")
    public  ResponseEntity<ReleaseYearDTO> addReleaseYear(@RequestBody ReleaseYear releaseYear) throws NoSuchReleaseYearException, NoSuchAlbumException, NoSuchSingerException {
        releaseYearService.createReleaseYear(releaseYear);
        Link link = linkTo(methodOn(ReleaseYearController.class).getReleaseYear(releaseYear.getId())).withSelfRel();

        ReleaseYearDTO releaseYearDTO = new ReleaseYearDTO(releaseYear, link);

        return new ResponseEntity<>(releaseYearDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/Year_id/{year_id}")
    public  ResponseEntity<ReleaseYearDTO> updateReleaseYear(@RequestBody ReleaseYear uReleaseYear, @PathVariable Long year_id) throws NoSuchReleaseYearException, NoSuchAlbumException, NoSuchSingerException {
        releaseYearService.updateReleaseYear(uReleaseYear, year_id);
        ReleaseYear releaseYear = releaseYearService.getReleaseYear(year_id);
        Link link = linkTo(methodOn(ReleaseYearController.class).getReleaseYear(year_id)).withSelfRel();

        ReleaseYearDTO releaseYearDTO = new ReleaseYearDTO(releaseYear, link);

        return new ResponseEntity<>(releaseYearDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/Release_year/{year_id}")
    public  ResponseEntity deleteReleaseYear(@PathVariable Long year_id) throws NoSuchReleaseYearException, ExistsAlbumsForSingerException, ExistsAlbumsForReleaseYearException {
        releaseYearService.updateReleaseYear(year_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
