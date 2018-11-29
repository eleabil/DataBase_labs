package com.bilianska.Repository;

import com.bilianska.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

}
