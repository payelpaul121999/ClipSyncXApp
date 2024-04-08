package com.pal.clipsyncxapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.pal.clipsyncxapp.MainActivity
import com.pal.clipsyncxapp.allFiles
import com.pal.clipsyncxapp.databinding.ActivitySignInBinding





class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseAuth.getInstance().currentUser.let {
            Log.e("#JAPAN","${it}")
            if (it != null){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        binding.goToSignupBtn.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        binding.submitBtn.setOnClickListener {
            login()
        }
    }
   private fun setProgress(isProgress:Boolean){
        if (isProgress){
            binding.progressBar.visibility = View.VISIBLE
            binding.submitBtn.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.GONE
            binding.submitBtn.visibility = View.VISIBLE
        }
    }
    private fun login(){
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.setError("Email is not valid")
            return
        }
        if (password.length < 6){
            binding.passwordInput.setError("Minimum 6 character")
            return
        }
        loginWithFireBase(email,password)
    }

    private fun loginWithFireBase(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnSuccessListener {
            allFiles.showToast(this,"Login successfully")
            setProgress(false)
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            allFiles.showToast(applicationContext,it.localizedMessage?: "Something went wrong")
            setProgress(false)
        }
    }
}