package com.bilianska;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "album", schema = "Music7", catalog = "")
public class AlbumEntity {
    private int id_album;
    private String name_album;
    private String genre;
    private String label;
    private List<SingerEntity> singerEntities;
    private ReleaseYearEntity releaseYearByReleaseYear;

    public AlbumEntity() {
    }

    public AlbumEntity(String name_album, String genre, String label, ReleaseYearEntity releaseYearByReleaseYear) {
       // this.studentId = studentId;
        this.name_album = name_album;
        this.genre = genre;
        this.label = label;
        this.releaseYearByReleaseYear = releaseYearByReleaseYear;
    }

    @Id
    @Column(name = "id_album", nullable = false)
    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    @Basic
    @Column(name = "name_album", nullable = false, length = 25)
    public String getName_album() {
        return name_album;
    }

    public void setName_album(String name_album) {
        this.name_album = name_album;
    }

    @Basic
    @Column(name = "genre", nullable = false, length = 25)
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Basic
    @Column(name = "label", nullable = true, length = 45)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumEntity that = (AlbumEntity) o;

        if (id_album != that.id_album) return false;
        if (name_album != null ? !name_album.equals(that.name_album) : that.name_album != null) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id_album;
        result = 31 * result + (name_album != null ? name_album.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "release_year", referencedColumnName = "release_year", nullable = false)
    public ReleaseYearEntity getReleaseYearByReleaseYear() {
        return releaseYearByReleaseYear;
    }

    public void setReleaseYearByReleaseYear(ReleaseYearEntity releaseYearByReleaseYear) {
        this.releaseYearByReleaseYear = releaseYearByReleaseYear;
    }

    @ManyToMany(mappedBy = "albumEntities")
    public List<SingerEntity> getSingerEntities() {
        return singerEntities;
    }

    public void addSingerEntity(SingerEntity SingerEntity){
        if(!getSingerEntities().contains(SingerEntity)){
            getSingerEntities().add(SingerEntity);
        }
        if(!SingerEntity.getAlbumEntities().contains(this)){
            SingerEntity.getAlbumEntities().add(this);
        }
    }

    public void deleteSingerEntity(SingerEntity singerEntity){
        if(getSingerEntities().contains(singerEntity)){
            getSingerEntities().remove(singerEntity);
        }
        if(singerEntity.getAlbumEntities().contains(this)){
            singerEntity.getAlbumEntities().remove(this);
        }
    }

    public void setSingerEntities(List<SingerEntity> singerEntities) {
        this.singerEntities = singerEntities;
    }

    @Override
    public String toString() {
        return "AlbumEntity{" +
                "album_id=" + id_album +
                ", singer_id=" + singerEntities.get(0).getId_singer() +
                '}';
    }
}
