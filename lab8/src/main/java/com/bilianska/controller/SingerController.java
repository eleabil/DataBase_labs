package com.bilianska.controller;

import com.bilianska.DTO.impl.SingerDTO;
import com.bilianska.domain.Singer;
import com.bilianska.exceptions.ExistsAlbumsForSingerException;
import com.bilianska.exceptions.NoSuchAlbumException;
import com.bilianska.exceptions.NoSuchSingerException;
import com.bilianska.service.SingerService;
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
public class SingerController {
    @Autowired
    SingerService singerService;

    @GetMapping(value = "/api/Singer/Album/{id_album}")
    public ResponseEntity<Set<SingerDTO>> getSingerByAlbumID(@PathVariable Long id_album) throws NoSuchAlbumException, NoSuchSingerException {
        Set<Singer> SingerSet = singerService.getSingersByAlbumId(id_album);
        Link link = linkTo(methodOn(SingerController.class).getAllSingers()).withSelfRel();

        Set<SingerDTO> singersDTO = new HashSet<>();
        for (Singer entity : SingerSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            SingerDTO dto = new SingerDTO(entity, selfLink);
            singersDTO.add(dto);
        }

        return new ResponseEntity<>(singersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Singer/{id_singer}")
    public ResponseEntity<SingerDTO> getSinger(@PathVariable Long id_singer) throws NoSuchSingerException, NoSuchAlbumException {
        Singer singer = singerService.getSinger(id_singer);
        Link link = linkTo(methodOn(SingerController.class).getSinger(id_singer)).withSelfRel();

        SingerDTO singerDTO = new SingerDTO(singer, link);

        return new ResponseEntity<>(singerDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Singer")
    public ResponseEntity<Set<SingerDTO>> getAllSingers() throws NoSuchSingerException, NoSuchAlbumException {
        List<Singer> SingerSet = singerService.getAllSingers();
        Link link = linkTo(methodOn(SingerController.class).getAllSingers()).withSelfRel();

        Set<SingerDTO> singerDTOS = new HashSet<>();
        for (Singer entity : SingerSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            SingerDTO dto = new SingerDTO(entity, selfLink);
            singerDTOS.add(dto);
        }

        return new ResponseEntity<>(singerDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/api/Singer")
    public ResponseEntity<SingerDTO> addSinger(@RequestBody Singer newSinger) throws NoSuchSingerException, NoSuchAlbumException {
        singerService.createSinger(newSinger);
        Link link = linkTo(methodOn(SingerController.class).getSinger(newSinger.getId())).withSelfRel();

        SingerDTO singerDTO = new SingerDTO(newSinger, link);

        return new ResponseEntity<>(singerDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/Singer/{id_singer}")
    public ResponseEntity<SingerDTO> updateSinger(@RequestBody Singer uSinger, @PathVariable Long id_singer) throws NoSuchSingerException, NoSuchAlbumException {
        singerService.updateSinger(uSinger, id_singer);
        Singer singer = singerService.getSinger(id_singer);
        Link link = linkTo(methodOn(SingerController.class).getSinger(id_singer)).withSelfRel();

        SingerDTO singerDTO = new SingerDTO(singer, link);

        return new ResponseEntity<>(singerDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/Singer/{id_singer}")
    public  ResponseEntity deleteSinger(@PathVariable Long id_singer) throws ExistsAlbumsForSingerException, NoSuchSingerException {
        singerService.deleteSinger(id_singer);
        return new ResponseEntity(HttpStatus.OK);
    }
}
