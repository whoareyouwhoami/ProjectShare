package com.project.share.service;

import java.util.Set;

public interface RedisService {
    Set<Object> getRecentMessages(String key);
}
