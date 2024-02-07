package com.sample.cleanarch.core.domain.exception

class DocumentRestrictionsException : RuntimeException(
    "Document has restrictions, please resolve it first."
)