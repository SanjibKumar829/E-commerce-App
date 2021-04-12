package com.sanjib.kotlinimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

import android.util.Patterns
import android.view.View
import android.widget.*

import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {




    private lateinit var auth: FirebaseAuth
    lateinit var login:Button
    lateinit var username:EditText
    lateinit var signup:TextView
    lateinit var password:EditText
    lateinit var progressbar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        username=findViewById(R.id.username)
        password=findViewById(R.id.pass)
        progressbar=findViewById(R.id.progress)
         login=findViewById(R.id.login)
         signup=findViewById(R.id.sign_up)
         auth= FirebaseAuth.getInstance()

        signup.setOnClickListener {

            startActivity(Intent(this,SignupActivity::class.java))
        }


      login.setOnClickListener {
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
          
          loginuser(user,pass)
      }







    }

    private fun loginuser(user: String, pass: String) {

        progressbar.visibility= View.VISIBLE
           auth.signInWithEmailAndPassword(user,pass)
               .addOnCompleteListener(this){
                   task ->
                   if (task.isSuccessful){

                       progressbar.visibility= View.GONE
                            login()
                   }
                   else{
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
