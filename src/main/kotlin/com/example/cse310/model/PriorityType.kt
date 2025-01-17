package com.example.cse310.model

enum class PriorityType(val status: Int) {
    HIGH(0),
    MEDIUM(1),
    LOW(2);

    companion object {
        private val CACHED_STATUSES: Map<Int, PriorityType> = entries.associateBy { it.status }

        fun getByCode(code: Int): PriorityType {
            return CACHED_STATUSES[code]
                ?: throw UnsupportedOperationException("Priority status with value = $code isn't supported yet")
        }
    }
}