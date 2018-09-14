package com.gmail.evanloafakahaitao.pcstore.service.converter.impl.dto;

import com.gmail.evanloafakahaitao.pcstore.dao.model.User;
import com.gmail.evanloafakahaitao.pcstore.service.converter.DTOConverter;
import com.gmail.evanloafakahaitao.pcstore.service.dto.ProfileDTO;
import com.gmail.evanloafakahaitao.pcstore.service.dto.RoleDTO;
import com.gmail.evanloafakahaitao.pcstore.service.dto.UserDTO;

public class UserDTOConverter implements DTOConverter<UserDTO, User> {

    private DTOConverter profileDTOConverter = new ProfileDTOConverter();
    private DTOConverter roleDTOConverter = new RoleDTOConverter();

    @SuppressWarnings("unchecked")
    @Override
    public UserDTO toDto(User entity) {
        return UserDTO.newBuilder()
                .withId(entity.getId())
                .withLastName(entity.getLastName())
                .withFirstName(entity.getFirstName())
                .withPassword(entity.getPassword())
                .withEmail(entity.getEmail())
                .withProfile((ProfileDTO) profileDTOConverter.toDto(entity.getProfile()))
                .withRole((RoleDTO) roleDTOConverter.toDto(entity.getRole()))
                .build();
    }
}
