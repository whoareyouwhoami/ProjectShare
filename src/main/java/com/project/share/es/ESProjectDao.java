package com.project.share.es;

import com.project.share.model.ProjectES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ESProjectDao extends ElasticsearchRepository<ProjectES, Integer> {
    List<ProjectES> findByTitleOrDescription(String title, String description);
}
