package com.khor.smartpay.feature_home.domain.model

data class MonthlyAnalytics(
    val january: Map<String, List<Double>> = emptyMap()
)
