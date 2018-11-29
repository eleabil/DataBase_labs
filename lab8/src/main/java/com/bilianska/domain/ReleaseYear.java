package com.bilianska.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.bilianska.DTO.EntityInterface;

import javax.persistence.*;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "release_year", schema = "Music8")
public class ReleaseYear implements EntityInterface {
    private Long id;
    private int yearOfRelease;

    public ReleaseYear(Long id, int volume) {
        this.id = id;
        this.yearOfRelease = volume;
    }

    private Set<Album> albumByReleaseYear;

    @Id
    @Column(name = "id_of_year", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReleaseYear(Long id) {
        this.id = id;
    }

    public ReleaseYear() {
    }

    @Basic
    @Column(name = "year_of_release", nullable = true)
    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }


    @OneToMany(
            mappedBy = "releaseYearByReleaseYear",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public Set<Album> getAlbum() {
        return albumByReleaseYear;
    }
    public void setAlbum(Set<Album> albumByReleaseYear)
    {
        this.albumByReleaseYear = albumByReleaseYear;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReleaseYear that = (ReleaseYear) o;
        return id == that.id &&
                Objects.equals(yearOfRelease, that.yearOfRelease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, yearOfRelease);
    }
    @Override
    public String toString() {
        return "ReleaseYear{" +
                "id=" + id +
                ", yearOfRelease='" + yearOfRelease + '\'' +
                '}';
    }

}
