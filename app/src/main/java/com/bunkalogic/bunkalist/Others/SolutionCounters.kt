package com.bunkalogic.bunkalist.Others

import android.support.annotation.NonNull
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.*


class SolutionCounters {

    private val db: FirebaseFirestore? = null

    // [START counter_classes]
    // counters/${ID}
    inner class Counter(internal var numShards: Int)

    // counters/${ID}/shards/${NUM}
    inner class Shard(internal var count: Int)
    // [END counter_classes]

    // [START create_counter]
    fun createCounter(ref: DocumentReference, numShards: Int): Task<Void> {
        // Initialize the counter document, then initialize each shard.
        return ref.set(Counter(numShards))
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }

                val tasks: ArrayList<Task<Void>> = ArrayList()

                // Initialize each shard with count=0
                for (i in 0 until numShards) {
                    val makeShard = ref.collection("numPositive")
                        .document(i.toString())
                        .set(Shard(0))

                    tasks.add(makeShard)
                }

                Tasks.whenAll(tasks)
            }
    }
    // [END create_counter]

    // [START increment_counter]
    fun incrementCounter(ref: DocumentReference, numShards: Int): Task<Void> {
        val shardId = Math.floor(Math.random() * numShards).toInt()
        val shardRef = ref.collection("").document(shardId.toString())

        return db!!.runTransaction { transaction ->
            val shard = transaction.get(shardRef).toObject(Shard::class.java)
            shard!!.count += 1

            transaction.set(shardRef, shard!!)
            null
        }
    }
    // [END increment_counter]

    // [START get_count]
    fun getCount(ref: DocumentReference): Task<Int> {
        // Sum the count of each shard in the subcollection
        return ref.collection("shards").get()
            .continueWith(object : Continuation<QuerySnapshot, Int> {
                @Throws(Exception::class)
                override fun then(@NonNull task: Task<QuerySnapshot>): Int? {
                    var count = 0
                    for (snap in task.result!!) {
                        val shard = snap.toObject(Shard::class.java)
                        count += shard.count
                    }
                    return count
                }
            })
    }
    // [END get_count]

}