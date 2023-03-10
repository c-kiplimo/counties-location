package com.ke.location.repository;

import com.ke.location.entity.County;

import com.ke.location.entity.SubCounty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountyRepository extends JpaRepository<County,Integer> {

    List<County> findAll();
    Optional<County> getCountyByName(String county_name);
    Page<County> findByNameContaining(String name, Pageable pageable);

}
