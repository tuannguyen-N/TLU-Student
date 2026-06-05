package org.example.project

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.example.project.domain.model.Presence
import org.example.project.domain.repository.PresenceRepository

class AndroidPresenceRepository : PresenceRepository {
    private val database = FirebaseDatabase.getInstance()

    override fun setupPresence(studentId: String) {
        val connectedRef = database.getReference(".info/connected")
        val presenceRef = database.getReference("presence/$studentId")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected =
                    snapshot.getValue(Boolean::class.java) ?: false
                if (!connected) return

                presenceRef.child("online").onDisconnect().setValue(false)

                presenceRef.child("lastSeen").onDisconnect().setValue(ServerValue.TIMESTAMP)

                presenceRef.updateChildren(
                    mapOf(
                        "online" to true,
                        "lastSeen" to ServerValue.TIMESTAMP
                    )
                )
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun goOnline(studentId: String) {
        database.getReference("presence/$studentId/online").setValue(true)
    }

    override fun goOffline(studentId: String) {
        val ref = database.getReference("presence/$studentId")
        ref.child("online").setValue(false)
        ref.child("lastSeen").setValue(ServerValue.TIMESTAMP)
    }

    override fun observeOnlineStatus(
        userId: String
    ): Flow<Boolean> = callbackFlow {
        val ref = database.getReference("presence/$userId/online")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val online = snapshot.getValue(Boolean::class.java) ?: false
                trySend(online)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    override fun observePresence(
        userId: String
    ): Flow<Presence> = callbackFlow {
        val ref = database.getReference("presence/$userId")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val online = snapshot.child("online").getValue(Boolean::class.java) ?: false
                val lastSeen = snapshot.child("lastSeen").getValue(Long::class.java) ?: 0L

                trySend(Presence(isOnline = online, lastSeen = lastSeen))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    override fun observeAllPresence(): Flow<Map<String, Presence>> =
        callbackFlow {
            val ref = database.getReference("presence")
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val map = mutableMapOf<String, Presence>()
                    snapshot.children.forEach { child ->
                        val userId = child.key ?: return@forEach
                        val online = child.child("online").getValue(Boolean::class.java) ?: false
                        val lastSeen = child.child("lastSeen").getValue(Long::class.java) ?: 0L

                        map[userId] = Presence(isOnline = online, lastSeen = lastSeen)
                    }

                    trySend(map)
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            ref.addValueEventListener(listener)
            awaitClose {
                ref.removeEventListener(listener)
            }
        }
}