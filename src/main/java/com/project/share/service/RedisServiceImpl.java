package com.project.share.service;

import com.project.share.controller.ProjectController;
import com.project.share.model.ChatMessage;
import com.project.share.model.MessageDetail;
import com.project.share.model.Project;
import com.project.share.redis.RedisDao;
import com.redislabs.lettusearch.Document;
import com.redislabs.lettusearch.RediSearchCommands;
import com.redislabs.lettusearch.SearchResults;
import com.redislabs.lettusearch.StatefulRediSearchConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {
    static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    @Resource(name="redisMessageTemplate")
    private ZSetOperations<String, Object> zSetOperations;

    @Resource(name="redisTemplateString")
    private HashOperations<String, Object, Object> hashOperations;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private StatefulRediSearchConnection<String, String> searchConnection;

    @Override
    public Set<Object> getRecentMessages(String key) {
        // return redisTemplate.boundZSetOps(key).range(0, -1);
        return zSetOperations.range(key, 0, -1);
    }

    @Override
    public void saveProject(Project project) {
        redisDao.save(project);
    }

    @Override
    public List<Map<Object, Object>> searchProject(String q) {
        RediSearchCommands<String, String> commands = searchConnection.sync();
        SearchResults<String, String> searchResults = commands.search("project-idx", q);

        log.info("Retrieved " + searchResults.getCount() + " results");
        List<Map<Object, Object>> list = new ArrayList<>();
        for(Document<String, String> result: searchResults) {
            Map<Object,Object> map = hashOperations.entries(result.getId());
            list.add(map);
        }
        return list;
    }
}
