package org.example.project.domain

interface TopicSubscriber {
    fun subscribe(topics: List<String>)
    fun unsubscribe(topics: List<String>)
}