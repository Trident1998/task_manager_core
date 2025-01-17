package com.example.cse310

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskManagerCoreApplication

fun main(args: Array<String>) {
	runApplication<TaskManagerCoreApplication>(*args)
}
