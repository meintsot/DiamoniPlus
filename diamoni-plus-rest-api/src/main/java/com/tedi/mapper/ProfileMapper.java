package com.tedi.mapper;

import com.tedi.dto.GetUserProfileRespMsgType;
import com.tedi.model.DiamoniPlusUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProfileMapper {

    @Inject
    ImageFileMapper imageFileMapper;

    public GetUserProfileRespMsgType toGetUserProfileRespMsgType(DiamoniPlusUser diamoniPlusUser) {

        GetUserProfileRespMsgType getUserProfileRespMsgType = new GetUserProfileRespMsgType();
        getUserProfileRespMsgType.setUsername(diamoniPlusUser.getUsername());
        getUserProfileRespMsgType.setFirstName(diamoniPlusUser.getFirstName());
        getUserProfileRespMsgType.setLastName(diamoniPlusUser.getLastName());
        getUserProfileRespMsgType.setEmail(diamoniPlusUser.getEmail());
        getUserProfileRespMsgType.setPhone(diamoniPlusUser.getPhone());
        getUserProfileRespMsgType.setDesiredRole(diamoniPlusUser.getRoleType().getValue());
        getUserProfileRespMsgType.setAvatar(imageFileMapper.toImageFileType(diamoniPlusUser.getAvatar()));

        return getUserProfileRespMsgType;
    }
}
