package com.burakcanaksoy.atomiccountryaggregator.mapper;

import com.burakcanaksoy.atomiccountryaggregator.model.User;
import com.burakcanaksoy.atomiccountryaggregator.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public static UserResponse toResponse(User user){
        if (user == null){
            return null;
        }
        UserResponse response = new UserResponse();
        response.setName(user.getName());
        response.setSurname(user.getSurname());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }

    public static List<UserResponse> toListResponse(List<User> userList){
        List<UserResponse> responseList = new ArrayList<>();

        if (userList.isEmpty()){
            return responseList;
        }
        for (User user : userList){
            responseList.add(toResponse(user));
        }
        return responseList;
    }


}
