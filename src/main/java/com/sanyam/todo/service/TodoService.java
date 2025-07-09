package com.sanyam.todo.service;

import com.sanyam.todo.model.Todo;
import com.sanyam.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TodoService.class);
    private final TodoRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TodoService(TodoRepository repository, RedisTemplate<String, Object> redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings("unchecked")
    public List<Todo> getAllTodos() {
    List<Todo> todos;

    // Try to fetch from Redis
    Object cached = redisTemplate.opsForValue().get("todos");

    if (cached != null) {
        todos = (List<Todo>) cached;
        log.info("🔥 Fetched todos from Redis");
    } else {
        // Fetch from MongoDB if not in Redis
        todos = repository.findByDeletedFalse();
        log.info("📡 Fetched todos from MongoDB");

        // ✅ Store in Redis with TTL
        redisTemplate.opsForValue().set("todos", todos, Duration.ofMinutes(5));
        log.info("📦 Stored in Redis with TTL");
    }

    return todos;
}

    public Todo createTodo(Todo todo) {
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());
        Todo saved = repository.save(todo);

        // Invalidate cache
        redisTemplate.delete("todos");
        System.out.println("❌ Cache invalidated (create)");

        return saved;
    }

    public Optional<Todo> getTodoById(String id) {
        return repository.findById(id).filter(todo -> !todo.isDeleted());
    }

    public Optional<Todo> updateTodo(String id, Todo updated) {
        return repository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setCompleted(updated.isCompleted());
            existing.setUpdatedAt(LocalDateTime.now());
            Todo saved = repository.save(existing);

            // Invalidate cache
            redisTemplate.delete("todos");
            System.out.println("❌ Cache invalidated (update)");

            return saved;
        });
    }

    public boolean deleteTodo(String id) {
        return repository.findById(id).map(todo -> {
            todo.setDeleted(true);
            todo.setUpdatedAt(LocalDateTime.now());
            repository.save(todo);

            // Invalidate cache
            redisTemplate.delete("todos");
            System.out.println("❌ Cache invalidated (delete)");

            return true;
        }).orElse(false);
    }
}
