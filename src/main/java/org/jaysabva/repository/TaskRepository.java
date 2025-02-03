package org.jaysabva.repository;

import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    Optional<Task> findTaskById(String id);

    List<Task> findByStoryPoints(Long storyPoints);

    List<Task> findAndGroupByStoryPoints();

    List<Task> findByStoryPointsBetween(Long start, Long end);

    List<Task> findAllAndGroupByStoryPoints();
}