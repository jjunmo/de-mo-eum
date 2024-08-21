package com.example.modelmapper.module.user.request;

import com.example.modelmapper.module.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private Long id;
    private String name;
    private String phone;
}
