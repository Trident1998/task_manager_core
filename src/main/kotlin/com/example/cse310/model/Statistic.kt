package com.example.cse310.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Statistic(
    @JsonProperty("task_amount")
    val taskAmount: Int,

    @JsonProperty("statistic")
    val statistic: List<StatisticData>
)

data class StatisticData(
    @JsonProperty("property_name")
    val propertyName: String,

    @JsonProperty("data")
    val data: Map<String, Int>
)