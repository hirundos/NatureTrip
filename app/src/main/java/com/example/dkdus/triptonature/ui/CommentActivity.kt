package com.example.dkdus.triptonature.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.adapter.CommentAdapter
import com.example.dkdus.triptonature.model.CommentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.HashMap

class CommentActivity : AppCompatActivity() {
    private var dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    private var dateFormatKey = DateTimeFormatter.ofPattern("MMddHHmmss")
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    var commentData : String? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mUser: FirebaseUser
    private var placeId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!
        commentRecyclerview.layoutManager = LinearLayoutManager(this)

        if(intent is Intent)
            placeId = intent.getStringExtra("PlaceId")
        showComment()
        commentAdd()

    }

    private fun showComment() {
        val dbReference = firebaseDatabase.reference.child("/$placeId")
        dbReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataSet : MutableList<CommentDTO> = ArrayList()
                for(snapshot in snapshot.children) {
                    var isEmail = false
                    val id = snapshot.child("id").value.toString()
                    if(id == mUser.email)
                        isEmail = true

                    val time = snapshot.child("time").value.toString()
                    val contents = snapshot.child("comment").value.toString()
                    val key = snapshot.child("key").value.toString()
                    val myData = CommentDTO(id,time,contents,isEmail,key)
                    dataSet.add(myData)
                }
                val myAdapter = CommentAdapter(this@CommentActivity, dataSet){
                    val keys = it.key
                    val rmReference = firebaseDatabase.reference.child("/$placeId/$keys")
                    rmReference.removeValue().addOnSuccessListener {
                }.addOnFailureListener {
                        Log.d(TAG, "실패하였습니다.") }
                    false

                }

                commentCnt.text = myAdapter.itemCount.toString()
                commentRecyclerview.adapter = myAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Failed to read value."+ error.toException())
            }
        })
    }

    private fun commentAdd(){
        val databaseReference = firebaseDatabase.reference
        commentBtn.setOnClickListener {
            var todayDate : String = LocalDate.now().format(dateFormatter)
            var keyDate : String = LocalDateTime.now().format(dateFormatKey)
            commentData = comment_tv.text.toString()
            var key :String = mUser.uid.substring(0,5) + keyDate;
            val commentList = CommentDTO(mUser.email, todayDate, commentData,true, key)
            val UpdateCmt: MutableMap<String, Any> = HashMap()
            UpdateCmt["/$placeId/$key"] = commentList
            databaseReference.updateChildren(UpdateCmt)
            comment_tv.text = null
        }
    }

    companion object {
        val TAG = CommentActivity::class.java.simpleName
    }
}