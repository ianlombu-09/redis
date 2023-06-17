package com.digi.redis.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserExperienceDTO {
    private int experienceId;
    private String title;
    private String companyName;
    private String location;
    private String description;
}
