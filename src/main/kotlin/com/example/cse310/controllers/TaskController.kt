package com.example.cse310.controllers

import com.example.cse310.model.Statistic
import com.example.cse310.model.Task
import com.example.cse310.model.TaskDto
import com.example.cse310.service.TaskSrvice
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskSrvice
) {

    @GetMapping("/initialize")
    fun initialize(): ResponseEntity<Void> {
        taskService.initialize()
        return ResponseEntity.ok().build()
    }

    @PostMapping
    fun createTask(@RequestBody taskDto: TaskDto): ResponseEntity<Task> {
        val createdTask = taskService.createTask(taskDto)
        return ResponseEntity.ok(createdTask)
    }

    @GetMapping
    fun getAllTasks(): ResponseEntity<List<Task>> {
        val tasks = taskService.getAllTasks()
        return ResponseEntity.ok(tasks)
    }

    @GetMapping("/{task_id}")
    fun getTask(@PathVariable("task_id") taskId: Int): ResponseEntity<Task> {
        return ResponseEntity.ok(taskService.getTask(taskId))
    }

    @GetMapping("/statistic")
    fun getStatistic(): ResponseEntity<Statistic> {
        return ResponseEntity.ok(taskService.getStatistic())
    }

    @DeleteMapping("/{task_id}")
    fun deleteTask(@PathVariable("task_id") taskId: Int): ResponseEntity<Void> {
        taskService.deleteTask(taskId)
        return ResponseEntity.noContent().build()
    }
}