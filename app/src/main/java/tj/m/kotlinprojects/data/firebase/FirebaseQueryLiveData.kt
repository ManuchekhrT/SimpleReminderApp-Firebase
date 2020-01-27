package tj.m.kotlinprojects.data.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*


class FirebaseQueryLiveData : LiveData<DataSnapshot> {

    private val query: Query
    private val listener = MyValueEventListener()

    constructor(query: Query) {
        this.query = query
    }

    constructor(ref: DatabaseReference) {
        this.query = ref
    }

    override fun onActive() {
        Log.d(TAG, "onActive")
        query.addValueEventListener(listener)
    }

    override fun onInactive() {
        Log.d(TAG, "onInactive")
        query.removeEventListener(listener)
    }

    private inner class MyValueEventListener : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e(TAG, "Can't listen to query $query", databaseError.toException())
        }
    }

    companion object {
        private val TAG = "FirebaseQueryLiveData"
    }
}