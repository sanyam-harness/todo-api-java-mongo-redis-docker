package com.sanyam.todo.controller;

import com.sanyam.todo.model.Todo;
import com.sanyam.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping
    public List<Todo> getAll() {
        return service.getAllTodos();
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return service.createTodo(todo);
    }

    @GetMapping("/{id}")
    public Optional<Todo> getById(@PathVariable String id) {
        return service.getTodoById(id);
    }

    @PutMapping("/{id}")
    public Optional<Todo> update(@PathVariable String id, @RequestBody Todo updated) {
        return service.updateTodo(id, updated);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        boolean success = service.deleteTodo(id);
        return success ? "Deleted" : "Todo not found";
    }
}

