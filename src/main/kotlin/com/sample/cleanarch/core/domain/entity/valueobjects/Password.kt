package com.sample.cleanarch.core.domain.entity.valueobjects

class Password(
    val value: String
) {

    companion object {
        private const val ERR_LEN = "Password must have at least eight characters!"
        private const val ERR_WHITESPACE = "Password must not contain whitespace!"
        private const val ERR_DIGIT = "Password must contain at least one digit!"
        private const val ERR_UPPER = "Password must have at least one uppercase letter!"
        private const val ERR_SPECIAL = "Password must have at least one special character, such as: _%-=+#@"
    }

    fun validate() {
        require(value.length >= 8) { ERR_LEN }
        require(value.none { it.isWhitespace() }) { ERR_WHITESPACE }
        require(value.any { it.isDigit() }) { ERR_DIGIT }
        require(value.any { it.isUpperCase() }) { ERR_UPPER }
        require(value.any { !it.isLetterOrDigit() }) { ERR_SPECIAL }
    }
}