package com.example.cse310.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class TaskDto(
    @JsonProperty("title")
    val title: String,

    @JsonProperty("description")
    val description: String,

    @JsonProperty("priority")
    val priority: String,

    @JsonProperty("tasks_status")
    val tasksStatus: String,

    @JsonProperty("deadline")
    val deadline: LocalDate
)
