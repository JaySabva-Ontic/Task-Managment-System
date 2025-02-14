package org.jaysabva.repository.elastic;

import org.jaysabva.entity.Task;
import org.jaysabva.entity.elastic.TaskES;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskESRepository extends ElasticsearchRepository<Task, Long> {

    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"description\": \"?0\"}}]}}")
    List<Task> findByTitleContainingOrDescriptionContaining(String title, String description);


    @Query("""
    {
    "multi_match" : {
        "query": "?0",
        "fields": ["*"],
        "lenient": true
    }
}
""")
    List<Task> universalSearch(String query);

    @Query("""
    {
        "range": {
            "createdAt": {
                "gte": "?0",
                "lte": "?1"
            }
        }
    }
    """)
    List<Task> findTaskByDateRange(String startDate, String endDate);
}