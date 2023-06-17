package com.digi.redis.service;

import com.alibaba.fastjson.JSON;
import com.digi.redis.model.entity.RegisterStateModel;
import com.digi.redis.model.request.UserRegisterRequest;
import com.digi.redis.model.response.UserRegisterResponse;
import com.digi.redis.repository.UserStateRepository;
import com.digi.redis.util.CacheUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

    @Autowired
    private final UserStateRepository userStateRepository;
    @Autowired
    private final CacheUtility cacheUtility;

    private final String USER_STATE = "User_State:";

    private final String finishRegisterState = "LAST STATE";


    public UserRegisterResponse execute(UserRegisterRequest request) {

        Optional<RegisterStateModel> registerState = userStateRepository.findById(request.getStateId());

        if (registerState.isPresent()) {
            RegisterStateModel state = registerState.get();
            state.setState(request.getState());

            userStateRepository.save(state);

            deleteUserStateOnRedis(request);
            updateUserStateOnRedis(request, state.getUserId());

            return UserRegisterResponse.builder()
                    .stateId(state.getStateId())
                    .userId(state.getUserId())
                    .state(request.getState())
                    .build();
        }

        return null;
    }

    public void deleteUserStateOnRedis(UserRegisterRequest request) {

        if (request.getState().equalsIgnoreCase(finishRegisterState)) {
            cacheUtility.delete(USER_STATE + request.getStateId());
        }
    }

    public void updateUserStateOnRedis(UserRegisterRequest request, int userId) {
        String jsonString = JSON.toJSONString(UserRegisterResponse.builder()
                .stateId(request.getStateId())
                .userId(userId)
                .state(request.getState())
                .build());

        if (!request.getState().equalsIgnoreCase(finishRegisterState)) {
            cacheUtility.set(USER_STATE + request.getStateId(), jsonString);
        }
    }
}
