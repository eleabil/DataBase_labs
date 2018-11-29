package com.bilianska.controller;

import com.bilianska.DTO.impl.AlbumDTO;
import com.bilianska.domain.Album;
import com.bilianska.exceptions.*;
import com.bilianska.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class AlbumController {
    @Autowired
    AlbumService albumService;
// get Album by class id
    @GetMapping(value = "/api/album/release_year/{year_id}")
    public ResponseEntity<List<AlbumDTO>> getAlbumsByYearId(@PathVariable Long year_id) throws NoSuchReleaseYearException, NoSuchAlbumException, NoSuchSingerException {
        Set<Album> AlbumSet = albumService.getAlbumByReleaseYearId(year_id);

        Link link = linkTo(methodOn(AlbumController.class).getAllAlbums()).withSelfRel();

        List<AlbumDTO> AlbumsDTO = new ArrayList<>();
        for (Album entity : AlbumSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            AlbumDTO dto = new AlbumDTO(entity, selfLink);
            AlbumsDTO.add(dto);
        }

        return new ResponseEntity<>(AlbumsDTO, HttpStatus.OK);
    }
// get Album
    @GetMapping(value = "/api/album/{id_album}")
    public ResponseEntity<AlbumDTO> getAlbums(@PathVariable Long id_album) throws NoSuchAlbumException, NoSuchSingerException, NoSuchReleaseYearException {
        Album album = albumService.getAlbums(id_album);
        Link link = linkTo(methodOn(AlbumController.class).getAlbums(id_album)).withSelfRel();

        AlbumDTO albumDTO = new AlbumDTO(album, link);

        return new ResponseEntity<>(albumDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Album")
    public ResponseEntity<Set<AlbumDTO>> getAllAlbums() throws NoSuchAlbumException, NoSuchSingerException, NoSuchReleaseYearException {
        List<Album> albumSet = albumService.getAllAlbums();
        Link link = linkTo(methodOn(AlbumController.class).getAllAlbums()).withSelfRel();

        Set<AlbumDTO> albumDTOS = new HashSet<>();
        for (Album entity : albumSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            AlbumDTO dto = new AlbumDTO(entity, selfLink);
            albumDTOS.add(dto);
        }

        return new ResponseEntity<>(albumDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Album/Singer/{id_singer}")
    public ResponseEntity<Set<AlbumDTO>> getAlbumsBySingerID(@PathVariable Long id_singer) throws NoSuchSingerException, NoSuchAlbumException, NoSuchReleaseYearException {
        Set<Album> albumSet = albumService.getAlbumsBySingerId(id_singer);
        Link link = linkTo(methodOn(AlbumController.class).getAllAlbums()).withSelfRel();

        Set<AlbumDTO> albumDTOS = new HashSet<>();
        for (Album entity : albumSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            AlbumDTO dto = new AlbumDTO(entity, selfLink);
            albumDTOS.add(dto);
        }

        return new ResponseEntity<>(albumDTOS, HttpStatus.OK);
    }
// add Album
    @PostMapping(value = "/api/Album/Year_of_release/{year_id}")
    public  ResponseEntity<AlbumDTO> addAlbum(@RequestBody Album album, @PathVariable Long year_id)
            throws NoSuchReleaseYearException, NoSuchAlbumException, NoSuchSingerException {
        albumService.createAlbum(album, year_id);
        Link link = linkTo(methodOn(AlbumController.class).getAlbums(album.getId())).withSelfRel();

        AlbumDTO albumDTO = new AlbumDTO(album, link);

        return new ResponseEntity<>(albumDTO, HttpStatus.CREATED);
    }
//update Album
    @PutMapping(value = "/api/Album/{id_album}/Year_of_release/{year_id}")
    public  ResponseEntity<AlbumDTO> updateAlbum(@RequestBody Album album,
                                                 @PathVariable Long id_album, @PathVariable Long year_id)
            throws NoSuchReleaseYearException, NoSuchAlbumException, NoSuchSingerException {
        albumService.updateAlbum(album, id_album, year_id);
        Album albums = albumService.getAlbums(id_album);
        Link link = linkTo(methodOn(AlbumController.class).getAlbums(id_album)).withSelfRel();

        AlbumDTO albumDTO = new AlbumDTO(albums, link);

        return new ResponseEntity<>(albumDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/Album/{id_album}")
    public  ResponseEntity deleteAlbum(@PathVariable Long id_album) throws NoSuchAlbumException, ExistsSingerForAlbumException, ExistsSingerForAlbumException {
        albumService.deleteAlbum(id_album);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/api/Album/{id_album}/Singer/{id_singer}")
    public  ResponseEntity<AlbumDTO> addSingerForAlbum(@PathVariable Long id_album, @PathVariable Long id_singer)
            throws NoSuchAlbumException, NoSuchSingerException, NoSuchReleaseYearException, AlreadyExistsSingerInAlbumException, SingerAbsentException {
        albumService.addSingerForAlbum(id_album, id_singer);
        Album albums = albumService.getAlbums(id_album);
        Link link = linkTo(methodOn(AlbumController.class).getAlbums(id_album)).withSelfRel();

        AlbumDTO albumDTO = new AlbumDTO(albums, link);

        return new ResponseEntity<>(albumDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/Album/{id_album}/{id_singer}")
    public  ResponseEntity<AlbumDTO> removeSingerForAlbum(@PathVariable Long id_album, @PathVariable Long id_singer)
            throws NoSuchAlbumException, NoSuchSingerException, NoSuchReleaseYearException, AlbumHasNotSingerException {
        albumService.removeSingerForAlbum(id_album, id_singer);
        Album albums = albumService.getAlbums(id_album);
        Link link = linkTo(methodOn(AlbumController.class).getAlbums(id_singer)).withSelfRel();

        AlbumDTO albumDTO = new AlbumDTO(albums, link);

        return new ResponseEntity<>(albumDTO, HttpStatus.OK);
    }

}

