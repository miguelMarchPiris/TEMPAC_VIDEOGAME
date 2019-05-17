package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Context
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import edu.ub.pis2019.pis_16.tempac.Model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.DocumentReference




object FirestoreHandler: DatabaseHandler {
    private val db:FirebaseFirestore
    // Create a reference to the users collection
    private val usersCollection :CollectionReference
    init {
        db = FirebaseFirestore.getInstance()
        usersCollection = db.collection("users")
    }
    override fun updateUser(user: User) {
        //If user doesn't exist or the new highscore is better we add

        //Get user from db
        val docRef = usersCollection.document(user.uid)
        docRef.get()
            .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val userDB = it.result!!.toObject(User::class.java!!)
                        //If the highscore on the database is higher, we conserve the db highscore
                        if (userDB != null && userDB!!.getHighscore() > user.getHighscore()) {
                            user.setHighscore(userDB!!.getHighscore())
                        }
                        usersCollection.document(user.uid).set(user)
                    }
            }
    }
    override fun getHighscore(username: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}