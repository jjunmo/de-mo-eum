package com.example.modelmapper.module.user.response;

import com.example.modelmapper.module.user.request.UserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String phone;

    public static UserResponseDTO save(UserRequestDTO userRequestDTO){
        return UserResponseDTO.builder()
                .id(userRequestDTO.getId())
                .name(userRequestDTO.getName())
                .phone(userRequestDTO.getPhone())
                .build();
    }

}
