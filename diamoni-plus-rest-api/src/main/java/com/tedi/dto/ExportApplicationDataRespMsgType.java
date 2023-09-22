package com.tedi.dto;

import java.util.ArrayList;
import java.util.List;

public class ExportApplicationDataRespMsgType {

    private List<DiamoniPlusUserForExportType> users = new ArrayList<>();

    private List<MessagesForExportType> chats = new ArrayList<>();

    public List<DiamoniPlusUserForExportType> getUsers() {
        return users;
    }

    public void setUsers(List<DiamoniPlusUserForExportType> users) {
        this.users = users;
    }

    public List<MessagesForExportType> getChats() {
        return chats;
    }

    public void setChats(List<MessagesForExportType> chats) {
        this.chats = chats;
    }
}
