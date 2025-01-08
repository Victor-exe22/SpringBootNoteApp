package it.network.note.dto.mapper;

import it.network.note.data.entities.UserEntity;
import it.network.note.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserDTO userDTO);

    UserDTO toDTO(UserEntity userEntity);

    void updateUserDTO(UserDTO source, @MappingTarget UserDTO target);

    void updateUserEntity(UserDTO source,@MappingTarget UserEntity target);


}
