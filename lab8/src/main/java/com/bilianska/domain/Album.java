package com.bilianska.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.bilianska.DTO.EntityInterface;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "album", schema = "Music8")
public class Album implements EntityInterface {
    private Long id;
    private String albumName;
    private String genre;
    private ReleaseYear releaseYearByReleaseYear;
    Set<Singer> singers = new HashSet<>();


    public Album(String catery, String mark, ReleaseYear cpuByCpu) {
        this.albumName = catery;
        this.genre = mark;
        this.releaseYearByReleaseYear = cpuByCpu;
    }

    public Album(Long id, String catery, String mark) {
        this.id = id;
        this.albumName = catery;
        this.genre = mark;
    }

    public Album() {
    }


    @Id
    @Column(name = "id_album", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name_album", nullable = true, length = 50)
    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Basic
    @Column(name = "genre", nullable = true, length = 50)
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @ManyToOne
    @JoinColumn(name = "id_release_year", referencedColumnName = "id_of_year", nullable = false)
    @JsonIgnore
    public ReleaseYear getReleaseYearByReleaseYear() {
        return releaseYearByReleaseYear;
    }

    public void setReleaseYearByReleaseYear(ReleaseYear releaseYearByReleaseYear) {
        this.releaseYearByReleaseYear = releaseYearByReleaseYear;
    }


    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "singer_album",
            joinColumns = { @JoinColumn(name = "id_album", referencedColumnName = "id_album", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "id_singer", referencedColumnName = "id_singer", nullable = false), }
    )
    @JsonIgnore
    public Set<Singer> getSingers() {
        return singers;
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    public void addStudentLecturer(Singer customerEntity){
        if(!getSingers().contains(customerEntity)){
            getSingers().add(customerEntity);
        }
        if(!customerEntity.getAlbumSet().contains(this)){
            customerEntity.getAlbumSet().add(this);
        }
    }

    public void deleteCustomerEntity(Singer customer){
        if(getSingers().contains(customer)){
            getSingers().remove(customer);
        }
        if(customer.getAlbumSet().contains(this)){
            customer.getAlbumSet().remove(this);
        }
    }



    @Override
    public String toString() {
        return "MobileEntity{" +
                "id=" + id +
                ", category='" + albumName + '\'' +
                ", mark1='" + genre + '\'' +
                ", singers=" + singers +
                '}';
    }

    public String toStringJoinTable(){
        return "MobileEntity{" +
                "id=" + id +
                " singers=" + singers +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album that = (Album) o;
        return id == that.id &&
                Objects.equals(albumName, that.albumName) &&
                Objects.equals(genre, that.genre);}


    @Override
    public int hashCode() {
        return Objects.hash(id, albumName, genre);
    }



}
