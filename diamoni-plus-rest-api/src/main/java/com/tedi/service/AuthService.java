package com.tedi.service;

import com.tedi.auth.AuthUtils;
import com.tedi.auth.UserService;
import com.tedi.dao.DiamoniPlusUserDao;
import com.tedi.dto.*;
import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.mapper.DiamoniPlusUserMapper;
import com.tedi.mapper.ImageFileMapper;
import com.tedi.model.DiamoniPlusUser;
import com.tedi.validator.AuthValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

@ApplicationScoped
public class AuthService {

    @Inject
    UserService userService;

    @Inject
    AuthValidator authValidator;

    @Inject
    DiamoniPlusUserDao diamoniPlusUserDao;

    @Inject
    DiamoniPlusUserMapper diamoniPlusUserMapper;

    @Inject
    ImageFileMapper imageFileMapper;

    public RegisterRespMsgType register(RegisterReqMsgType param) throws ValidationFault {

        // Validate user credentials
        authValidator.validateUsername(param.getUsername());
        authValidator.validatePassword(param.getPassword(), param.getPasswordConfirmation());
        authValidator.validateEmail(param.getEmail());
        authValidator.validateNoAdmin(param.getDesiredRole());

        DiamoniPlusUser diamoniPlusUser = diamoniPlusUserDao.findByUsername(param.getUsername()).orElse(null);

        if (Objects.nonNull(diamoniPlusUser)) {
            throw new ValidationFault(ErrorMessageType.DATA_01_AuthService);
        }

        diamoniPlusUser = diamoniPlusUserDao.findByEmail(param.getEmail()).orElse(null);

        if (Objects.nonNull(diamoniPlusUser)) {
            throw new ValidationFault(ErrorMessageType.DATA_02_AuthService);
        }

        // Save user in database
        diamoniPlusUser = diamoniPlusUserMapper.toDiamoniPlusUser(param);
        diamoniPlusUserDao.saveDiamoniPlusUser(diamoniPlusUser);

        // Generate JWT
        String jwt = AuthUtils.generateJWT(diamoniPlusUser.getUsername(), Collections.singletonList(diamoniPlusUser.getRoleType().getValue()));

        RegisterRespMsgType response = new RegisterRespMsgType();
        response.setJwt(jwt);

        return response;
    }

    public LoginRespMsgType login(LoginReqMsgType param) throws ValidationFault {

        // Invalid username
        DiamoniPlusUser diamoniPlusUser = diamoniPlusUserDao.findByUsername(param.getUsername()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_03_AuthService)
        );

        // Invalid password
        if (!AuthUtils.passwordsMatch(param.getPassword(), diamoniPlusUser.getPassword())) {
            throw new ValidationFault(ErrorMessageType.DATA_03_AuthService);
        }

        // Generate JWT
        String jwt = AuthUtils.generateJWT(diamoniPlusUser.getUsername(), Collections.singletonList(diamoniPlusUser.getRoleType().getValue()));

        LoginRespMsgType response = new LoginRespMsgType();
        response.setJwt(jwt);

        return response;
    }

    public void changePassword(ChangePasswordReqMsgType param) throws ValidationFault {

        // Find user by JWT
        DiamoniPlusUser diamoniPlusUser = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_05_AuthService)
        );

        // Invalid password
        if (!AuthUtils.passwordsMatch(param.getCurrentPassword(), diamoniPlusUser.getPassword())) {
            throw new ValidationFault(ErrorMessageType.DATA_04_AuthService);
        }

        authValidator.validatePassword(param.getNewPassword(), param.getPasswordConfirmation());

        diamoniPlusUser.setPassword(AuthUtils.hashPassword(param.getNewPassword()));
    }

    public RetrieveUserInfoRespMsgType retrieveUserInfo() throws ValidationFault {

        DiamoniPlusUser diamoniPlusUser = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_05_AuthService)
        );

        RetrieveUserInfoRespMsgType response = new RetrieveUserInfoRespMsgType();

        response.setUsername(diamoniPlusUser.getUsername());
        response.setRole(userService.getRole());
        response.setFirstName(diamoniPlusUser.getFirstName());
        response.setLastName(diamoniPlusUser.getLastName());
        if (Objects.nonNull(diamoniPlusUser.getAvatar())) {
            response.setAvatar(imageFileMapper.toImageFileType(diamoniPlusUser.getAvatar()));
        }

        return response;
    }
}
