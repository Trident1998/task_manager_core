package com.example.cse310.service

import com.example.cse310.model.*
import com.example.cse310.repository.TaskRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TaskSrvice(
    private val taskRepository: TaskRepository
) {

    /**
     * Initializes the task database with a predefined list of 10 tasks.
     * Each task is given a title, description, priority, status, and deadline.
     */
    fun initialize() {
        val list = List(10) { i ->
            Task(
                title = "Task ${i + 1}",
                description = "Description for task ${i + 1}",
                priority = PriorityType.getByCode(i % 3).name,
                tasksStatus = TaskStatus.getByCode(i % 4).name,
                deadline = LocalDate.now().minusDays(3).plusDays(i.toLong())
            )
        }

        taskRepository.saveAll(list)
    }

    /**
     * Retrieves all tasks from the repository.
     *
     * @return A list of all tasks.
     */
    fun getAllTasks(): List<Task> {
        return taskRepository.findAll()
    }

    /**
     * Creates a new task based on the provided task DTO.
     *
     * @param taskDto The data transfer object containing task details.
     * @return The created task.
     */
    fun createTask(taskDto: TaskDto): Task {
        val task = Task(
            title = taskDto.title,
            description = taskDto.description,
            tasksStatus = taskDto.tasksStatus,
            priority = taskDto.priority,
            deadline = taskDto.deadline
        )

        return taskRepository.save(task)
    }

    /**
     * Retrieves a specific task by its ID.
     *
     * @param taskId The ID of the task to retrieve.
     * @return The task with the specified ID.
     * @throws NoSuchElementException if the task is not found.
     */
    fun getTask(taskId: Int): Task {
        return taskRepository.findById(taskId).get()
    }

    /**
     * Deletes a specific task by its ID.
     *
     * @param taskId The ID of the task to delete.
     */
    fun deleteTask(taskId: Int) {
        taskRepository.deleteById(taskId)
    }

    /**
     * Generates statistics about the tasks.
     *
     * @return A [Statistic] object containing the total number of tasks and detailed statistics.
     */
    fun getStatistic(): Statistic {
        val tasks = taskRepository.findAll()
        val taskAmount = tasks.size

        return Statistic(taskAmount, getStatisticData(tasks))
    }

    /**
     * Generates detailed statistics about tasks, including priority distribution,
     * task status distribution, and deadline-related statistics.
     *
     * @param tasks The list of tasks to analyze.
     * @return A list of [StatisticData] objects representing the statistics.
     */
    private fun getStatisticData(tasks: List<Task>): List<StatisticData> {
        val priorityStatistic = getPriorityStatistic(tasks)
        val taskStatusStatistic = getTaskStatusStatistic(tasks)
        val deadlineStatistic = getDeadlineStatistic(tasks)

        return listOf(
            StatisticData("Priority", priorityStatistic),
            StatisticData("Task Status", taskStatusStatistic),
            StatisticData("DeadLine", deadlineStatistic),
        )
    }

    /**
     * Computes the distribution of tasks by priority.
     *
     * @param tasks The list of tasks to analyze.
     * @return A map where the keys are priority levels and the values are their respective counts.
     */
    private fun getPriorityStatistic(tasks: List<Task>): Map<String, Int> {
        return tasks.groupingBy { it.priority }.eachCount()
    }

    /**
     * Computes the distribution of tasks by status.
     *
     * @param tasks The list of tasks to analyze.
     * @return A map where the keys are task statuses and the values are their respective counts.
     */
    private fun getTaskStatusStatistic(tasks: List<Task>): Map<String, Int> {
        return tasks.groupingBy { it.tasksStatus }.eachCount()
    }

    /**
     * Computes the number of tasks that are before or after their deadlines.
     *
     * @param tasks The list of tasks to analyze.
     * @return A map with two keys: "Until the deadline" and "After the deadline", representing task counts.
     */
    private fun getDeadlineStatistic(tasks: List<Task>): Map<String, Int> {
        val currentDate = LocalDate.now()
        var afterDeadline = 0

        for (task in tasks) {
            afterDeadline += if (task.deadline.isAfter(currentDate)) 1 else 0
        }

        return mapOf("Until the deadline" to tasks.size - afterDeadline, "After the deadline" to afterDeadline)
    }
}

