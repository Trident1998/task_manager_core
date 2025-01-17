package com.example.cse310.model

import jakarta.persistence.*
import lombok.Builder
import java.time.LocalDate

@Entity
@Table(name = "tasks")
@Builder
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val priority: String,

    @Column(nullable = false)
    val tasksStatus: String,

    @Column(nullable = false)
    val deadline: LocalDate
)
