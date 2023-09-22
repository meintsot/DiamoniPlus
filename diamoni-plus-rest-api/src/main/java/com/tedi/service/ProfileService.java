package com.tedi.service;

import com.tedi.auth.UserService;
import com.tedi.dao.DiamoniPlusUserDao;
import com.tedi.dto.GetUserProfileRespMsgType;
import com.tedi.dto.ImageFileType;
import com.tedi.dto.RetrieveUserProfileImageRespMsgType;
import com.tedi.dto.UpdateUserProfileReqMsgType;
import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.mapper.ImageFileMapper;
import com.tedi.mapper.ProfileMapper;
import com.tedi.model.DiamoniPlusUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@ApplicationScoped
public class ProfileService {

    @Inject
    UserService userService;

    @Inject
    DiamoniPlusUserDao diamoniPlusUserDao;

    @Inject
    ProfileMapper profileMapper;

    @Inject
    ImageFileMapper imageFileMapper;


    public GetUserProfileRespMsgType getUserProfile(String username) throws ValidationFault {
        DiamoniPlusUser diamoniPlusUser = diamoniPlusUserDao.findByUsername(username).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_ProfileService)
        );

        return profileMapper.toGetUserProfileRespMsgType(diamoniPlusUser);
    }

    public RetrieveUserProfileImageRespMsgType retrieveUserProfileImage(String username) throws ValidationFault {
        DiamoniPlusUser diamoniPlusUser = diamoniPlusUserDao.findByUsername(username).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_ProfileService)
        );

        ImageFileType avatar = Objects.nonNull(diamoniPlusUser.getAvatar()) ?
                imageFileMapper.toImageFileType(diamoniPlusUser.getAvatar()) : null;

        RetrieveUserProfileImageRespMsgType response = new RetrieveUserProfileImageRespMsgType();
        response.setAvatar(avatar);

        return response;
    }

    public void updateUserProfile(UpdateUserProfileReqMsgType param) throws ValidationFault {

        DiamoniPlusUser diamoniPlusUser = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_ProfileService)
        );

        diamoniPlusUser.setFirstName(param.getFirstName());
        diamoniPlusUser.setLastName(param.getLastName());
        diamoniPlusUser.setPhone(param.getPhone());
        if (Objects.nonNull(param.getAvatar())) {
            if (Objects.isNull(param.getAvatar().getBinaryIdentification()) || !param.getAvatar().getBinaryIdentification().equals(diamoniPlusUser.getAvatar().getBinaryIdentification())) {
                diamoniPlusUser.setAvatar(imageFileMapper.toImageFile(param.getAvatar()));
            }
        } else {
            diamoniPlusUser.setAvatar(null);
        }
    }
}
