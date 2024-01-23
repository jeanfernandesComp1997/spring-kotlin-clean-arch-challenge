package com.sample.cleanarch.core.validators

import com.sample.cleanarch.core.annotation.ValidUUID
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.*

class UUIDConstraintValidator : ConstraintValidator<ValidUUID, String> {
    override fun isValid(id: String, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return try {
            UUID.fromString(id)
            true
        } catch (ex: Exception) {
            false
        }
    }
}