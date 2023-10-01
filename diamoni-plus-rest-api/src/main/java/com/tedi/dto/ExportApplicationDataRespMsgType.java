package com.tedi.dto;

import java.util.ArrayList;
import java.util.List;

public class ExportApplicationDataRespMsgType {

    private List<DiamoniPlusUserForExportType> users = new ArrayList<>();

    public List<DiamoniPlusUserForExportType> getUsers() {
        return users;
    }

    public void setUsers(List<DiamoniPlusUserForExportType> users) {
        this.users = users;
    }
}
