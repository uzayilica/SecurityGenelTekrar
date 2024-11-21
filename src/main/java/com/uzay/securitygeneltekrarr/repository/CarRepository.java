package com.uzay.securitygeneltekrarr.repository;

import com.uzay.securitygeneltekrarr.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepository  extends JpaRepository<Car, Integer> {
}
