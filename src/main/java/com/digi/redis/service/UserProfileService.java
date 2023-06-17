package com.digi.redis.service;

import com.alibaba.fastjson.JSON;
import com.digi.redis.model.DTO.UserExperienceDTO;
import com.digi.redis.model.projection.UserProfileView;
import com.digi.redis.model.request.UserProfileRequest;
import com.digi.redis.model.response.UserProfileResponse;
import com.digi.redis.repository.UserRepository;
import com.digi.redis.util.CacheUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CacheUtility cacheUtility;

    private final String USER_PROFILE = "User_Profile:";

    public UserProfileResponse execute(UserProfileRequest request) {

        String experienceFromCache = cacheUtility.get(USER_PROFILE + request.getUserId());

        List<UserExperienceDTO> experiences = new ArrayList<>();
        experiences = getListExperience(request, experiences, experienceFromCache);

        return UserProfileResponse.builder()
                .experiences(experiences)
                .build();
    }

    public List<UserExperienceDTO> getListExperience(UserProfileRequest request, List<UserExperienceDTO> experiences, String experienceFromCache) {
        if (experienceFromCache == null) {
            var userProfiles = userRepository.getUserProfile(request.getUserId());

            for (UserProfileView userProfile : userProfiles) {
                experiences.add(UserExperienceDTO.builder()
                        .experienceId(userProfile.getExperienceId())
                        .title(userProfile.getTitle())
                        .companyName(userProfile.getCompanyName())
                        .location(userProfile.getLocation())
                        .description(userProfile.getDescription())
                        .build());
            }

            String jsonString = JSON.toJSONString(UserProfileResponse.builder()
                    .experiences(experiences)
                    .build());

            cacheUtility.set(USER_PROFILE + request.getUserId(), jsonString);
        } else {
            var listUserExperience = JSON.parseObject(experienceFromCache, UserProfileResponse.class);
            experiences = listUserExperience.getExperiences();
        }

        return experiences;
    }
}
