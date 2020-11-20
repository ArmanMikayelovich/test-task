package com.mikayelovich.service;

import com.mikayelovich.model.UserEntity;
import com.mikayelovich.model.dto.UserDto;

public interface Mapper {
   UserDto map(UserEntity entity, UserDto dto);

}
