package edu.ub.pis2019.pis_16.tempac.Presenter

import com.google.firebase.firestore.*
import edu.ub.pis2019.pis_16.tempac.Model.User



object FirestoreHandler: DatabaseHandler {
    private val db:FirebaseFirestore
    // Create a reference to the users collection
    private val usersCollection :CollectionReference
    init {
        db = FirebaseFirestore.getInstance()
        usersCollection = db.collection("users")
    }
    override fun updateUser(user: User) {
        //If user doesn't exist or the new highscore is better we update the user

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
    override fun getHighscoreTable(databaseCallback: DatabaseCallback){
        var highscoreArray = ArrayList<Pair<String,Int>>()
        val query = usersCollection.orderBy("highscore",Query.Direction.DESCENDING).get()
            .addOnSuccessListener {
                for(document in it.documents){
                    highscoreArray.add(Pair(document.get("username") as String,(document.get("highscore") as Long).toInt()))
                }
                databaseCallback.handleHighscoreTable(highscoreArray)
            }
    }

    override fun getOverallHighsscore(databaseCallback: DatabaseCallback){
        var pair = Pair("username",0)
        val query = usersCollection.orderBy("highscore",Query.Direction.DESCENDING).limit(1).get()
            .addOnSuccessListener {
                for(document in it.documents){
                    pair = Pair(document.get("username") as String,(document.get("highscore") as Long).toInt())
                }
                databaseCallback.handleHighscore(pair)
            }
    }
}