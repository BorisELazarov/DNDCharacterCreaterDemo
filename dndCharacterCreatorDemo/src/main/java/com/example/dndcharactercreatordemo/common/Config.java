package com.example.dndcharactercreatordemo.common;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public CriteriaBuilder getCriteriaBuilder(CriteriaBuilder criteriaBuilder){
        return criteriaBuilder;
    }
}
