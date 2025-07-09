package com.sanyam.todo.repository;

import com.sanyam.todo.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TodoRepository extends MongoRepository<Todo, String> {
    List<Todo> findByDeletedFalse();
}
