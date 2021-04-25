package com.project.share.redis;

import com.project.share.model.Author;
import com.project.share.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisDao extends CrudRepository<Project, Integer> {
}
