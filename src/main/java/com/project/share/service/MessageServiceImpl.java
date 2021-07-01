package com.project.share.service;

import com.project.share.dao.MessageDetailDao;
import com.project.share.model.ChatMessageDetail;
import com.project.share.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDetailDao messageDetailDao;

    private long createMessageId() {
        return (long) (Math.random() * Math.pow(10, 10));
    }

    @Override
    public List<ChatMessageDetail> getAllMessages(int userId) {
        return messageDetailDao.findAllByUserOneOrUserTwo(userId, userId);
    }

    @Override
    public String getMessageId(Project project, int userId) throws Exception {
        int projectOwner = project.getOwner().getId();
        if(userId == projectOwner)
            throw new Exception();

        int projectId = project.getId();
        long messageId;
        try {
            ChatMessageDetail messageDetail = messageDetailDao.findByUserOneAndUserTwoOrUserTwoAndUserOneAndProjectId(userId, projectOwner, projectOwner, userId, projectId);
            messageId = messageDetail.getRoomNumber();
        } catch (NullPointerException e) {
            messageId = createMessageId();

            ChatMessageDetail detail = new ChatMessageDetail();
            detail.setProjectId(projectId);
            detail.setRoomNumber(messageId);
            detail.setUserOne(userId);
            detail.setUserTwo(projectOwner);

            messageDetailDao.save(detail);
        }
        return Long.toString(messageId);
    }

    @Override
    public ChatMessageDetail getMessageDetail(long roomNumber) {
        return messageDetailDao.findByRoomNumber(roomNumber);
    }

    @Override
    public int getChatId(long roomNumber, int projectId) {
        ChatMessageDetail detail = messageDetailDao.findByRoomNumber(roomNumber);
        if(detail.getProjectId() != projectId) {
            return -1;
        }
        return detail.getId();
    }
}
