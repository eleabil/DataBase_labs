package com.bilianska.DTO.impl;

import com.bilianska.DTO.DTO;
import com.bilianska.domain.Album;
import com.bilianska.domain.ReleaseYear;
import com.bilianska.exceptions.NoSuchAlbumException;
import com.bilianska.exceptions.NoSuchReleaseYearException;
import com.bilianska.exceptions.NoSuchSingerException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class AlbumDTO extends DTO<Album> {
    public AlbumDTO(Album mobile, Link link) throws NoSuchSingerException, NoSuchReleaseYearException, NoSuchAlbumException {
        super(mobile, link);
    }

    public Long getStudentId() {
        return getEntity().getId();
    }

    public String getFirstName() {
        return getEntity().getAlbumName();
    }

    public String getLastName() {
        return getEntity().getGenre();
    }

    public ReleaseYear getGroup() {
        return getEntity().getReleaseYearByReleaseYear();
    }


}
