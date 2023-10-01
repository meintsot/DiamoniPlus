package com.tedi.service;

import com.tedi.auth.UserService;
import com.tedi.dao.DiamoniPlusUserDao;
import com.tedi.dao.DiscussionsDao;
import com.tedi.dto.*;
import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.mapper.DiscussionsMapper;
import com.tedi.model.DiamoniPlusUser;
import com.tedi.model.Discussion;
import com.tedi.model.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class DiscussionsService {

    @Inject
    UserService userService;

    @Inject
    DiamoniPlusUserDao diamoniPlusUserDao;

    @Inject
    DiscussionsDao discussionsDao;

    @Inject
    DiscussionsMapper discussionsMapper;

    public CreateAndRetrieveDiscussionRespMsgType createAndRetrieveDiscussion(CreateAndRetrieveDiscussionReqMsgType param) throws ValidationFault {

        CreateAndRetrieveDiscussionRespMsgType response = new CreateAndRetrieveDiscussionRespMsgType();

        Discussion discussion = discussionsDao.findByHostUsernameAndTenantUsername(param, userService.getUser()).orElse(null);

        if (Objects.nonNull(discussion)) {
            response.setDiscussionReference(discussion.getDiscussionReference());
            return response;
        }

        DiamoniPlusUser host = diamoniPlusUserDao.findByUsername(param.getHostUsername()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_DiscussionsService)
        );

        DiamoniPlusUser tenant = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_02_DiscussionsService)
        );

        discussion = new Discussion();
        discussion.setDiscussionReference(UUID.randomUUID().toString());
        discussion.setHost(host);
        discussion.setTenant(tenant);
        discussion.setCreatedAt(LocalDateTime.now());
        discussion.setUpdatedAt(LocalDateTime.now());

        discussionsDao.saveDiscussion(discussion);

        response.setDiscussionReference(discussion.getDiscussionReference());
        return response;
    }

    public RetrieveDiscussionsRespMsgType retrieveDiscussions() throws ValidationFault {

        List<DiscussionType> discussions = discussionsDao.retrieveDiscussions(userService.getUser(), userService.getRole());

        RetrieveDiscussionsRespMsgType response = new RetrieveDiscussionsRespMsgType();
        response.getDiscussions().addAll(discussions);

        return response;
    }

    public RetrieveMessagesRespMsgType retrieveMessages(RetrieveMessagesReqMsgType param) {

        List<Message> messages = discussionsDao.retrieveMessages(param);

        List<MessageType> messageTypes = discussionsMapper.toMessageType(messages);

        RetrieveMessagesRespMsgType response = new RetrieveMessagesRespMsgType();
        response.getMessages().addAll(messageTypes);

        return response;
    }

    public void saveMessage(SaveMessageReqMsgType param) throws ValidationFault {

        Discussion discussion = discussionsDao.findByDiscussionReference(param.getDiscussionReference()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_03_DiscussionsService)
        );

        DiamoniPlusUser sender = diamoniPlusUserDao.findByUsername(param.getMessage().getSender()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_04_DiscussionsService)
        );

        DiamoniPlusUser receiver = diamoniPlusUserDao.findByUsername(param.getMessage().getReceiver()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_05_DiscussionsService)
        );

        Message message = new Message();

        message.setMessageText(param.getMessage().getMessageText());
        message.setDeleted(param.getMessage().getDeleted());
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setDiscussion(discussion);
        message.setMessageId(UUID.randomUUID().toString());
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        discussionsDao.saveMessage(message);

        discussion.getMessages().add(message);
        discussion.setUpdatedAt(LocalDateTime.now());
        sender.getSentMessages().add(message);
        receiver.getReceivedMessages().add(message);
    }

    public void deleteMessage(String messageId) {
        Message message = discussionsDao.retrieveMessage(messageId).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_06_DiscussionsService)
        );
        message.setDeleted(true);
    }
}
