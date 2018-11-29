package com.bilianska.service;

import com.bilianska.Repository.ReleaseYearRepository;
import com.bilianska.Repository.AlbumRepository;
import com.bilianska.domain.ReleaseYear;
import com.bilianska.exceptions.ExistsAlbumsForReleaseYearException;
import com.bilianska.exceptions.NoSuchReleaseYearException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReleaseYearService {
    @Autowired
    ReleaseYearRepository releaseYearRepository;
    private boolean ascending;

    @Autowired
    AlbumRepository albumRepository;

    public List<ReleaseYear> getAllReleaseYear() {
        return releaseYearRepository.findAll();
    }

    public ReleaseYear getReleaseYear(Long id_album) throws NoSuchReleaseYearException {

        ReleaseYear releaseYear = releaseYearRepository.findById(id_album).get();//2.0.0.M7
        System.out.println(releaseYear);
        if (releaseYear == null) throw new NoSuchReleaseYearException();
        return releaseYear;
    }

    @Transactional
    public void createReleaseYear(ReleaseYear cpu) {
        releaseYearRepository.save(cpu);
    }

    @Transactional
    public void updateReleaseYear(ReleaseYear releaseYear, Long id_album) throws NoSuchReleaseYearException {
        ReleaseYear releaseYear1 = releaseYearRepository.findById(id_album).get();//2.0.0.M7

        if (releaseYear1 == null) throw new NoSuchReleaseYearException();
        releaseYear1.setAlbum(releaseYear.getAlbum());
        releaseYearRepository.save(releaseYear1);
    }

    @Transactional
    public void updateReleaseYear(Long id_album) throws NoSuchReleaseYearException, ExistsAlbumsForReleaseYearException {
        ReleaseYear releaseYear = releaseYearRepository.findById(id_album).get();//2.0.0.M7
        if (releaseYear == null) throw new NoSuchReleaseYearException();
        if (releaseYear.getAlbum().size() != 0) throw new ExistsAlbumsForReleaseYearException();
        releaseYearRepository.delete(releaseYear);
    }


}
