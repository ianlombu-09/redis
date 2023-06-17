package com.digi.redis.repository;

import com.digi.redis.model.entity.RegisterStateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStateRepository extends JpaRepository<RegisterStateModel, Integer> {
}
