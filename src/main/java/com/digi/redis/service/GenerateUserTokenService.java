package com.digi.redis.service;

import com.alibaba.fastjson.JSON;
import com.digi.redis.model.entity.UserModel;
import com.digi.redis.model.entity.UserTokenModel;
import com.digi.redis.model.response.UserTokenResponse;
import com.digi.redis.repository.UserRepository;
import com.digi.redis.repository.UserTokenRepository;
import com.digi.redis.util.CacheUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenerateUserTokenService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserTokenRepository userTokenRepository;
    @Autowired
    private final CacheUtility cacheUtility;

    private final String USER_TOKEN = "User_Token:";

    private final String TOKEN = "6hrFDATxrG9w14QY9wwnmVhLE0Wg6LIvwOwUaxz761m1JfRp4rs8";
    private final Integer expireInSeconds = 60;

    private final UserTokenResponse response = UserTokenResponse.builder().build();

    public UserTokenResponse execute(int userId) {
        Optional<UserModel> user = userRepository.findById(userId);

        if (user.isPresent()) {
            UserTokenModel userToken = new UserTokenModel();
            userToken.setUserId(userId);
            userToken.setToken(TOKEN);
            userTokenRepository.save(userToken);

            // set userTokenResponse
            response.setTokenId(userToken.getTokenId());
            response.setUserId(userId);
            response.setToken(TOKEN);

            String jsonString = JSON.toJSONString(response);
            saveUserTokenOnRedis(userId, jsonString);

            return response;
        }

        return response;
    }

    public void saveUserTokenOnRedis(int userId, String value) {
        cacheUtility.setAndDeleteWithExpire(USER_TOKEN + userId, value, expireInSeconds);
    }
}
