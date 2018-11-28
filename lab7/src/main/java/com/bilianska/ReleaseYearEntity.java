package com.bilianska;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "release_year", schema = "Music7", catalog = "")
public class ReleaseYearEntity {
    private String release_year;

    public ReleaseYearEntity() {
    }

    public ReleaseYearEntity(String release_year) {
        this.release_year = release_year;
    }

    @Id
    @Column(name = "release_year", nullable = false, length = 25)
    public String getRelease_year() {
        return release_year;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReleaseYearEntity that = (ReleaseYearEntity) o;

        if (release_year != null ? !release_year.equals(that.release_year) : that.release_year != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return release_year != null ? release_year.hashCode() : 0;
    }
}
