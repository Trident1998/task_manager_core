package com.example.cse310.model

enum class TaskStatus(val status: Int) {
    NOT_STARTED(0),
    IN_PROGRES(1),
    COMPLETED(2),
    BLOCKED(3);

    companion object {
        private val CACHED_STATUSES: Map<Int, TaskStatus> = entries.associateBy { it.status }

        fun getByCode(code: Int): TaskStatus {
            return CACHED_STATUSES[code]
                ?: throw UnsupportedOperationException("Task status with value = $code isn't supported yet")
        }
    }
}
