package com.mikayelovich.service.impl;

import com.mikayelovich.model.RoleEntity;
import com.mikayelovich.model.UserEntity;
import com.mikayelovich.model.dto.UserDto;
import com.mikayelovich.model.enums.UserRole;
import com.mikayelovich.service.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MapperImpl implements Mapper {

    @Override
    public UserDto map(UserEntity entity, UserDto dto) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        final Set<UserRole> roles = entity.getRoleEntitySet()
                .stream()
                .map(RoleEntity::getRole)
                .collect(Collectors.toSet());

        dto.setRoles(roles);
        return dto;
    }
}
