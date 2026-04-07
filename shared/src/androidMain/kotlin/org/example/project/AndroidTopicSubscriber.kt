package org.example.project

import com.google.firebase.messaging.FirebaseMessaging
import org.example.project.domain.TopicSubscriber

class AndroidTopicSubscriber : TopicSubscriber {

    override fun subscribe(topics: List<String>) {
        topics.forEach {
            FirebaseMessaging.getInstance().subscribeToTopic(it)
        }
    }

    override fun unsubscribe(topics: List<String>) {
        topics.forEach {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(it)
        }
    }
}