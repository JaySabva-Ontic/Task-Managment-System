package org.jaysabva.repository;

import org.jaysabva.entity.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Sequence, String> {
}
