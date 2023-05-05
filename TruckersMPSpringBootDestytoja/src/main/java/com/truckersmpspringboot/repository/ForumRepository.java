package com.truckersmpspringboot.repository;

import com.truckersmpspringboot.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum,Integer> {
    Forum findForumByForumTitle(String forumTitle);
}
