package com.digi.redis.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "experience")
public class ExperienceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    private int experienceId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "title")
    private String title;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "location")
    private String location;
    @Column(name = "description")
    private String description;
}
