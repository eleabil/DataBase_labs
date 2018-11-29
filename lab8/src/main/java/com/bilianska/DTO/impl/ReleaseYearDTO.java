package com.bilianska.DTO.impl;

import com.bilianska.DTO.DTO;
import com.bilianska.controller.AlbumController;
import com.bilianska.domain.Album;
import com.bilianska.domain.ReleaseYear;
import com.bilianska.exceptions.NoSuchAlbumException;
import com.bilianska.exceptions.NoSuchReleaseYearException;
import com.bilianska.exceptions.NoSuchSingerException;
import org.springframework.hateoas.Link;

import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


public class ReleaseYearDTO extends DTO<ReleaseYear> {
    public ReleaseYearDTO(ReleaseYear cpu, Link link) throws NoSuchReleaseYearException, NoSuchSingerException, NoSuchAlbumException {
        super(cpu, link);
        add(linkTo(methodOn(AlbumController.class).getAlbumsByYearId(getEntity().getId())).withRel("mobile"));
    }

    public Long getStudentId() {
        return getEntity().getId();
    }

    public Integer getNumberOfGroup() {
        return getEntity().getYearOfRelease();
    }
  

    public Set<Album> getStudents() {
        return getEntity().getAlbum();
    }
}
