package com.example.cse310.service

import com.example.cse310.model.*
import com.example.cse310.repository.TaskRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TaskSrvice(
    private val taskRepository: TaskRepository
) {

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

    fun getAllTasks(): List<Task> {
        return taskRepository.findAll()
    }

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

    fun getTask(taskId: Int): Task {
        return taskRepository.findById(taskId).get()
    }

    fun deleteTask(taskId: Int) {
        taskRepository.deleteById(taskId)
    }

    fun getStatistic(): Statistic {
        val tasks = taskRepository.findAll()
        val taskAmount = tasks.size

        return Statistic(taskAmount, getStatisticData(tasks))

    }

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

    private fun getPriorityStatistic(tasks: List<Task>): Map<String, Int> {
        return tasks.groupingBy { it.priority }.eachCount()
    }

    private fun getTaskStatusStatistic(tasks: List<Task>): Map<String, Int> {
        return tasks.groupingBy { it.tasksStatus }.eachCount()
    }

    private fun getDeadlineStatistic(tasks: List<Task>): Map<String, Int> {
        val currentDate = LocalDate.now();
        var afterDeadline = 0

        for (task in tasks) {
            afterDeadline += if (task.deadline.isAfter(currentDate)) 1 else 0
        }

        return mapOf("Until the deadline" to tasks.size - afterDeadline, "After the deadline" to afterDeadline)
    }
}
