package com.bilianska.domain;

import com.bilianska.DTO.EntityInterface;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "singer", schema = "Music8")
public class Singer implements EntityInterface {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Album> albums = new HashSet<>();
    @Id
    @Column(name = "id_singer", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "firstname", nullable = false, length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastname", nullable = true, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @ManyToMany(targetEntity = Album.class, mappedBy="singers")
    public Set<Album> getAlbumSet() {
        return albums;
    }

    public void setAlbumSet(Set<Album> mobiles) {
        this.albums = mobiles;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Singer that = (Singer) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
    @Override
    public String toString(){
        return "Id= " + id + ", firstName= " + firstName
                + ", lastName= " + lastName;
    }
}
