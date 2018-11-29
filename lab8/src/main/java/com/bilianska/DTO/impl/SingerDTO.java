package com.bilianska.DTO.impl;

import com.bilianska.DTO.DTO;
import com.bilianska.domain.Singer;
import com.bilianska.exceptions.NoSuchSingerException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class SingerDTO extends DTO<Singer> {
    public SingerDTO(Singer customer, Link link) throws NoSuchSingerException {
        super(customer, link);
    }

    public Long getLecturerId() {
        return getEntity().getId();
    }

    public String getFirstName() {
        return getEntity().getFirstName();
    }

    public String getLastName() {
        return getEntity().getLastName();
    }
}
