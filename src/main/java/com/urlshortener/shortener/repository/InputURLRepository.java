package com.urlshortener.shortener.repository;

import com.urlshortener.shortener.model.InputURLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InputURLRepository extends JpaRepository<InputURLEntity, Long> {

    @Query("from InputURLEntity input where input.inputURL =  :inputUsRL")
    List<InputURLEntity> findByInputURL(@Param("inputUsRL") String inputUsRL);

    @Query("from InputURLEntity input where input.shortKey =  :shortKey")
    InputURLEntity findByShortURL(@Param("shortKey") String shortKey);

}