package com.sample.cleanarch.core.exception

class DocumentRestrictionsException : RuntimeException(
    "Document has restrictions, please resolve it first."
)