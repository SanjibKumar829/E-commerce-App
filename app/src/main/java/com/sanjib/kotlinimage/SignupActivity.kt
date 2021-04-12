package com.sanjib.kotlinimage

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {

    private lateinit var username:EditText
    private lateinit var password:EditText
    private lateinit var register:Button
    private lateinit var movetologin:TextView
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        username=findViewById(R.id.username)
        password=findViewById(R.id.password)
        register=findViewById(R.id.register)
        movetologin=findViewById(R.id.movelogin)
        auth= FirebaseAuth.getInstance()

        register.setOnClickListener {

            val user = username.text.toString().trim()
            val pass = password.text.toString().trim()

            if (user.isEmpty()){
                username.error="enter your email"
                username.requestFocus()
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()){

                username.error="enter valid email"
                username.requestFocus()
            }

            registerUser(user,pass)

        }

        movetologin.setOnClickListener {

            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }


    }

    private fun registerUser(user: String, pass: String) {

        auth.createUserWithEmailAndPassword(user,pass)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){

                    val intent=Intent(this,MainActivity::class.java).apply {
                        flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                    startActivity(intent)

                }
                else
                    {


                    task.exception?.message?.let {
                        toast(it)
                    }


                     }
            }

    }
    override fun onStart() {
        super.onStart()

        auth.currentUser?.let {
            login()
        }
    }


}
