package org.jaysabva.service;

import org.jaysabva.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.jaysabva.entity.Sequence;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.mongodb.client.model.Filters.where;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    private final MongoTemplate mongoTemplate;
    @Autowired
    public SequenceGeneratorService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public long generateSequence(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));

        Update update = new Update().inc("seq", 1);

        Sequence counter = mongoTemplate.findAndModify(query, update, options().upsert(true).returnNew(true), Sequence.class);

        return counter.getSeq();
    }
}
