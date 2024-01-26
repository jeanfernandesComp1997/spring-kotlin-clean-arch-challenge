package com.sample.cleanarch.core.gateway

interface DocumentCheckerGateway {

    suspend fun retrieveDocumentRestrictions(document: String): List<String>
}