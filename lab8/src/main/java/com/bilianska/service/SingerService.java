package com.bilianska.service;

import com.bilianska.Repository.AlbumRepository;
import com.bilianska.Repository.SingerRepository;
import com.bilianska.domain.Album;
import com.bilianska.domain.Singer;
import com.bilianska.exceptions.ExistsAlbumsForSingerException;
import com.bilianska.exceptions.NoSuchAlbumException;
import com.bilianska.exceptions.NoSuchSingerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class SingerService {
    @Autowired
    SingerRepository singerRepository;

    @Autowired
    AlbumRepository albumRepository;

    public Set<Singer> getSingersByAlbumId(Long id_album) throws NoSuchAlbumException {
        Album album = albumRepository.findById(id_album).get();//2.0.0.M7
        if (album == null) throw new NoSuchAlbumException();
        return album.getSingers();
    }

    public Singer getSinger(Long id_singer) throws NoSuchSingerException {
        Singer singer = singerRepository.findById(id_singer).get();//2.0.0.M7
        if (singer == null) throw new NoSuchSingerException();
        return singer;
    }

    public List<Singer> getAllSingers() {
        return singerRepository.findAll();
    }

    @Transactional
    public void createSinger(Singer singer) {
        singerRepository.save(singer);
    }

    @Transactional
    public void updateSinger(Singer uSinger, Long id_singer) throws NoSuchSingerException {
        Singer singer= singerRepository.findById(id_singer).get();//2.0.0.M7
        if (singer == null) throw new NoSuchSingerException();
        //update
        singer.setFirstName(uSinger.getFirstName());
    }

    @Transactional
    public void deleteSinger(Long id_singer) throws NoSuchSingerException, ExistsAlbumsForSingerException {
        Singer singer = singerRepository.findById(id_singer).get();//2.0.0.M7

        if (singer == null) throw new NoSuchSingerException();
        if (singer.getAlbumSet().size() != 0) throw new ExistsAlbumsForSingerException();
        singerRepository.delete(singer);
    }
}
