package com.example.cse310.controllers

import com.example.cse310.model.Statistic
import com.example.cse310.model.Task
import com.example.cse310.model.TaskDto
import com.example.cse310.service.TaskSrvice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskSrvice
) {

    /**
     * Initializes the task-related setup or resources.
     * Endpoint: `GET /tasks/initialize`
     *
     * @return `200 OK` response with no content if successful.
     */
    @GetMapping("/initialize")
    fun initialize(): ResponseEntity<Void> {
        taskService.initialize()
        return ResponseEntity.ok().build()
    }

    /**
     * Creates a new task.
     * Endpoint: `POST /tasks`
     *
     * @param taskDto The data transfer object representing the task to be created.
     * @return `200 OK` response containing the created task.
     */
    @PostMapping
    fun createTask(@RequestBody taskDto: TaskDto): ResponseEntity<Task> {
        val createdTask = taskService.createTask(taskDto)
        return ResponseEntity.ok(createdTask)
    }

    /**
     * Retrieves a list of all tasks.
     * Endpoint: `GET /tasks`
     *
     * @return `200 OK` response containing the list of tasks.
     */
    @GetMapping
    fun getAllTasks(): ResponseEntity<List<Task>> {
        val tasks = taskService.getAllTasks()
        return ResponseEntity.ok(tasks)
    }

    /**
     * Retrieves a specific task by its ID.
     * Endpoint: `GET /tasks/{task_id}`
     *
     * @param taskId The ID of the task to retrieve.
     * @return `200 OK` response containing the requested task.
     */
    @GetMapping("/{task_id}")
    fun getTask(@PathVariable("task_id") taskId: Int): ResponseEntity<Task> {
        return ResponseEntity.ok(taskService.getTask(taskId))
    }

    /**
     * Retrieves statistics about tasks.
     * Endpoint: `GET /tasks/statistic`
     *
     * @return `200 OK` response containing the task statistics.
     */
    @GetMapping("/statistic")
    fun getStatistic(): ResponseEntity<Statistic> {
        return ResponseEntity.ok(taskService.getStatistic())
    }

    /**
     * Deletes a specific task by its ID.
     * Endpoint: `DELETE /tasks/{task_id}`
     *
     * @param taskId The ID of the task to delete.
     * @return `204 No Content` response if deletion is successful.
     */
    @DeleteMapping("/{task_id}")
    fun deleteTask(@PathVariable("task_id") taskId: Int): ResponseEntity<Void> {
        taskService.deleteTask(taskId)
        return ResponseEntity.noContent().build()
    }
}
