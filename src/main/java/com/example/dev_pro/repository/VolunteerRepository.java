package com.example.dev_pro.repository;

import com.example.dev_pro.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {


}
