package com.example.factorize.repos;

import com.example.factorize.domain.Numbers;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NumberRepo extends CrudRepository<Numbers, Long> {
    List<Numbers> findBybigInteger(String bigInteger);
}
