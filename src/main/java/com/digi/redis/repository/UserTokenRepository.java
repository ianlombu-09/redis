package com.digi.redis.repository;

import com.digi.redis.model.entity.UserTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenModel, Integer> {
}
