package com.tedi.mapper;

import com.tedi.auth.AuthUtils;
import com.tedi.dto.RegisterReqMsgType;
import com.tedi.model.DiamoniPlusUser;
import com.tedi.model.RoleType;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

@ApplicationScoped
public class DiamoniPlusUserMapper {

    public DiamoniPlusUser toDiamoniPlusUser(RegisterReqMsgType param) {

        DiamoniPlusUser diamoniPlusUser = new DiamoniPlusUser();
        diamoniPlusUser.setUsername(param.getUsername());
        diamoniPlusUser.setPassword(AuthUtils.hashPassword(param.getPassword()));
        diamoniPlusUser.setFirstName(param.getFirstName());
        diamoniPlusUser.setLastName(param.getLastName());
        diamoniPlusUser.setEmail(param.getEmail());
        diamoniPlusUser.setPhone(param.getPhone());
        diamoniPlusUser.setRoleType(RoleType.fromValue(param.getDesiredRole()));
        diamoniPlusUser.setAvatar(param.getAvatar());
        diamoniPlusUser.setCreatedAt(LocalDateTime.now());
        diamoniPlusUser.setUpdatedAt(LocalDateTime.now());

        return diamoniPlusUser;
    }
}
