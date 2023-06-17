package com.digi.redis.model.response;

import com.digi.redis.model.DTO.UserExperienceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private List<UserExperienceDTO> experiences;
}
