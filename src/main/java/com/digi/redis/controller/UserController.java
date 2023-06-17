package com.digi.redis.controller;

import com.digi.redis.model.request.UserProfileRequest;
import com.digi.redis.model.request.UserRegisterRequest;
import com.digi.redis.model.response.UserProfileResponse;
import com.digi.redis.model.response.UserRegisterResponse;
import com.digi.redis.model.response.UserTokenResponse;
import com.digi.redis.service.GenerateUserTokenService;
import com.digi.redis.service.UserProfileService;
import com.digi.redis.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user/v1")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserProfileService userProfileService;
    @Autowired
    private final UserRegisterService userRegisterService;
    @Autowired
    private final GenerateUserTokenService generateUserTokenService;

    @GetMapping("/{userId}")
    public UserProfileResponse getUserProfile(@PathVariable int userId) {
        return userProfileService.execute(UserProfileRequest.builder()
                .userId(userId)
                .build());
    }

    @PostMapping("/register/state")
    public UserRegisterResponse registerState(@RequestBody UserRegisterRequest request) {
        return userRegisterService.execute(request);
    }

    @PostMapping("/token/{userId}")
    public UserTokenResponse generateUserToken(@PathVariable int userId) {
        return generateUserTokenService.execute(userId);
    }
}
