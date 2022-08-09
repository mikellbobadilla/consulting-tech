package com.mikellbobadilla.consultingtech.repository;

import com.mikellbobadilla.consultingtech.entity.Valoration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorationRepository extends JpaRepository<Valoration,Long> {
}
