package com.sample.cleanarch.core.entity.valueobjects

import java.time.LocalDate
import java.time.Period
import kotlin.math.abs

class BirthDate(
    val value: LocalDate
) {

    companion object {
        private const val MINIMUM_AGE = 16
        private const val MAXIMUM_AGE = 130
        private const val SHOULD_MINIMUM_16_YEARS = "Birth date need to be minimum 16"
        private const val TOO_OLD_INVALID_AGE = "This is age is too old to be real."
    }

    fun validate() {
        require(getAge() > MINIMUM_AGE) { SHOULD_MINIMUM_16_YEARS }
        require(getAge() < MAXIMUM_AGE) { TOO_OLD_INVALID_AGE }
    }

    private fun getAge(): Int {
        val period = Period.between(value, LocalDate.now())
        return abs(period.years)
    }
}