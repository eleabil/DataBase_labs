package com.bilianska.Repository;

import com.bilianska.domain.ReleaseYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseYearRepository extends JpaRepository<ReleaseYear, Long> {

}
