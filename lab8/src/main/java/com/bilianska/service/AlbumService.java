package com.bilianska.service;

import com.bilianska.Repository.ReleaseYearRepository;
import com.bilianska.Repository.AlbumRepository;
import com.bilianska.Repository.SingerRepository;
import com.bilianska.domain.Album;
import com.bilianska.domain.ReleaseYear;
import com.bilianska.domain.Singer;
import com.bilianska.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class AlbumService {
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ReleaseYearRepository releaseYearRepository;

    @Autowired
    SingerRepository singerRepository;

    public Set<Album> getAlbumByReleaseYearId(Long id_album) throws NoSuchReleaseYearException {
        ReleaseYear releaseYear = releaseYearRepository.findById(id_album).get();//2.0.0.M7
        if (releaseYear == null) throw new NoSuchReleaseYearException();
        return releaseYear.getAlbum();
    }

    public Album getAlbums(Long id_album) throws NoSuchAlbumException {
        Album album = albumRepository.findById(id_album).get();//2.0.0.M7
        if (album == null) throw new NoSuchAlbumException();
        return album;
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Set<Album> getAlbumsBySingerId(Long id_singer) throws NoSuchSingerException {
        Singer singer = singerRepository.findById(id_singer).get();//2.0.0.M7
        if (singer == null) throw new NoSuchSingerException();
        return singer.getAlbumSet();
    }

    @Transactional
    public void createAlbum(Album Mobile, Long Cpu_id) throws NoSuchReleaseYearException {
        if (Cpu_id > 0) {
            ReleaseYear releaseYear = releaseYearRepository.findById(Cpu_id).get();//2.0.0.M7
            if (releaseYear == null) throw new NoSuchReleaseYearException();
            Mobile.setReleaseYearByReleaseYear(releaseYear);
        }
        albumRepository.save(Mobile);
    }

    @Transactional
    public void updateAlbum(Album uAlbum, Long Id_album, Long id_album) throws NoSuchReleaseYearException, NoSuchAlbumException {
        ReleaseYear releaseYear = releaseYearRepository.findById(id_album).get();//2.0.0.M7
        if (id_album > 0) {
            if (releaseYear == null) throw new NoSuchReleaseYearException();
        }
        Album album = albumRepository.findById(Id_album).get();//2.0.0.M7
        if (album == null) throw new NoSuchAlbumException();
        //update
        album.setAlbumName(uAlbum.getAlbumName());
        album.setGenre(uAlbum.getGenre());
        if (id_album > 0) album.setReleaseYearByReleaseYear(releaseYear);
        else album.setReleaseYearByReleaseYear(null);
        albumRepository.save(album);
    }

    @Transactional
    public void deleteAlbum(Long id_album) throws NoSuchAlbumException, ExistsSingerForAlbumException {
        Album album = albumRepository.findById(id_album).get();//2.0.0.M7
        if (album == null) throw new NoSuchAlbumException();
        if (album.getSingers().size() != 0) throw new ExistsSingerForAlbumException();
        albumRepository.delete(album);
    }

    @Transactional
    public void addSingerForAlbum(Long id_album, Long id_singer)
            throws NoSuchAlbumException, NoSuchSingerException, AlreadyExistsSingerInAlbumException, SingerAbsentException {
        Album album = albumRepository.findById(id_album).get();//2.0.0.M7
        if (album == null) throw new NoSuchAlbumException();
        Singer singer = singerRepository.findById(id_singer).get();//2.0.0.M7
        if (singer == null) throw new NoSuchSingerException();
        if (album.getSingers().contains(singer) == true) throw new AlreadyExistsSingerInAlbumException();
        album.getSingers().add(singer);
        albumRepository.save(album);
    }

    @Transactional
    public void removeSingerForAlbum(Long id_album, Long id_singer)
            throws NoSuchAlbumException, NoSuchSingerException, AlbumHasNotSingerException {

        Album album = albumRepository.findById(id_album).get();//2.0.0.M7
        if (album == null) throw new NoSuchAlbumException();

        Singer singer= singerRepository.findById(id_singer).get();//2.0.0.M7
        if (singer == null) throw new NoSuchSingerException();
        if (album.getSingers().contains(singer) == false) throw new AlbumHasNotSingerException();
        album.getSingers().remove(singer);
        albumRepository.save(album);
    }
}
