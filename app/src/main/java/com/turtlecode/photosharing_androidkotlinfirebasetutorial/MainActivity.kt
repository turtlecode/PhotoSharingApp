package com.turtlecode.photosharing_androidkotlinfirebasetutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseFirestore

    private lateinit var recyclerViewAdapter: PhotoRecyclerAdapter
    var postList = ArrayList<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        getInfo()

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = PhotoRecyclerAdapter(postList)
        recyclerView.adapter = recyclerViewAdapter
    }


    fun getInfo() {
        database.collection("Post").orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener {snapshot , exception ->
                if (exception != null) {
                    Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
                else {
                    if (snapshot != null) {
                        if (!snapshot.isEmpty) {
                            val documents = snapshot.documents
                            postList.clear()

                            for (document in documents) {

                                val userComment = document.get("userComment") as String
                                val imageURL = document.get("imageURL") as String

                                val indirilenPost = Post(userComment,imageURL)
                                postList.add(indirilenPost)
                            }
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.option,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.SavePhoto) {
            val intent = Intent(this,Photo_Sharing::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}