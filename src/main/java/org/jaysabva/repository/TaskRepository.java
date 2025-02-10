package org.jaysabva.repository;

import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    Optional<Task> findTaskById(Long id);

    List<Task> findByStoryPoints(Long storyPoints);

    List<Task> findAndGroupByStoryPoints();

    List<Task> findByStoryPointsBetween(Long start, Long end);

    List<Task> findAllAndGroupByStoryPoints();

    boolean existsById(Long id);

    void deleteById(Long id);

    Optional<Task> findById(Long id);

    List<Task> findByTaskType(String taskType);

}