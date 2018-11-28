package com.bilianska;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "singer", schema = "Music7", catalog = "")
public class SingerEntity {
    private int id_singer;
    private String firstName;
    private String lastName;
    private String country;
    private List<AlbumEntity> albumEntities;

    public SingerEntity() {
    }

    public SingerEntity(int id_singer, String firstName, String lastName, String country, List<AlbumEntity> albumEntities) {
        this.id_singer = id_singer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.albumEntities = albumEntities;
    }

    @Id
    @Column(name = "id_singer", nullable = false)
    public int getId_singer() {
        return id_singer;
    }

    public void setId_singer(int id_singer) {
        this.id_singer = id_singer;
    }

    @Basic
    @Column(name = "firstname", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastname", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "country", nullable = false, length = 45)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingerEntity that = (SingerEntity) o;

        if (id_singer != that.id_singer) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id_singer;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @ManyToMany
    @JoinTable(name = "singer_album", catalog = "", schema = "Music7",
            joinColumns = @JoinColumn(name = "id_singer", referencedColumnName = "id_singer", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_album", referencedColumnName = "id_album", nullable = false))
    public List<AlbumEntity> getAlbumEntities() {
        return albumEntities;
    }

    public void setAlbumEntities(List<AlbumEntity> albumEntities) {
        this.albumEntities = albumEntities;
    }

    @Override
    public String toString() {
        return "SingerEntity{" +
                "id_singer=" + id_singer +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", albumEntities=" + albumEntities +
                '}';
    }
}
