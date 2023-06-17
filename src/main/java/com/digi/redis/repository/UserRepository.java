package com.digi.redis.repository;

import com.digi.redis.model.entity.UserModel;
import com.digi.redis.model.projection.UserProfileView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query(value = "SELECT u.user_id as userId," +
            "u.user_name as userName," +
            "u.about, " +
            "e.experience_id as experienceId, " +
            "e.title, e.company_name as companyName, " +
            "e.location, e.description " +
            "FROM users u " +
            "INNER JOIN experience e ON u.user_id  = e.user_id " +
            "WHERE u.user_id = :userId ", nativeQuery = true)
    List<UserProfileView> getUserProfile(int userId);
}
