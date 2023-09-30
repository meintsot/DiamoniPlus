package com.tedi.service;

import com.tedi.dao.AdminDao;
import com.tedi.dao.DiamoniPlusUserDao;
import com.tedi.dto.*;
import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.mapper.AdminMapper;
import com.tedi.model.DiamoniPlusUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class AdminService {

    @Inject
    DiamoniPlusUserDao diamoniPlusUserDao;

    @Inject
    AdminDao adminDao;

    @Inject
    AdminMapper adminMapper;

    public void approveHostRole(String username) throws ValidationFault {

        DiamoniPlusUser host = diamoniPlusUserDao.findByUsername(username).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_AdminService)
        );

        host.setHostApproved(true);
    }

    public RetrieveHostApprovalRespMsgType retrieveHostApproval(String username) throws ValidationFault {
        DiamoniPlusUser host = diamoniPlusUserDao.findByUsername(username).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_AdminService)
        );

        RetrieveHostApprovalRespMsgType response = new RetrieveHostApprovalRespMsgType();
        response.setApproved(host.getHostApproved());
        return response;
    }

    public SearchUserProfilesRespMsgType searchUserProfiles(SearchUserProfilesReqMsgType param) {

        List<UserProfileResult> userProfileResults = adminDao.searchUserProfiles(param);
        Long totalResults = adminDao.countUserProfiles(param);

        SearchUserProfilesRespMsgType response = new SearchUserProfilesRespMsgType();
        response.getUserProfileResults().addAll(userProfileResults);
        response.setTotalResults(totalResults);

        return response;
    }

    public ExportApplicationDataRespMsgType exportApplicationData() {

        List<DiamoniPlusUser> diamoniPlusUsers = adminDao.findAll();

        return adminMapper.toExportApplicationDataRespMsgType(diamoniPlusUsers);
    }
}
