package com.project.share.dao;

import com.project.share.model.ChatMessageDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDetailDao extends JpaRepository<ChatMessageDetail, Integer> {
    List<ChatMessageDetail> findAllByUserOneOrUserTwo(int userIdA, int userIdB);

    // @Query("SELECT x.roomNumber FROM ChatMessageDetail x WHERE ((x.userOne = :userOne AND x.userTwo = :userTwo) OR (x.userOne = :userTwo AND x.userTwo = :userOne)) AND x.projectId = :pid")
    ChatMessageDetail findByUserOneAndUserTwoOrUserTwoAndUserOneAndProjectId(int userOneA, int userTwoA, int userTwoB, int userOneB, int pid);

    ChatMessageDetail findByRoomNumber(long roomNumber);
}
