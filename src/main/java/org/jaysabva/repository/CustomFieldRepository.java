package org.jaysabva.repository;

import org.jaysabva.entity.CustomField;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomFieldRepository extends MongoRepository<CustomField, Long> {
}
