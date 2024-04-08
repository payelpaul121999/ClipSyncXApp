package com.pal.clipsyncxapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.pal.clipsyncxapp.MainActivity
import com.pal.clipsyncxapp.R
import com.pal.clipsyncxapp.allFiles
import com.pal.clipsyncxapp.databinding.ActivitySignUpBinding
import com.pal.clipsyncxapp.model.UserModel

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToLoginBtn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }
        binding.submitBtn.setOnClickListener {
            signUp()
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
    private fun signUp(){
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.setError("Email is not valid")
            return
        }
        if (password.length < 6){
            binding.passwordInput.setError("Minimum 6 character")
            return
        }
        if(password!=confirmPassword){
            binding.confirmPasswordInput.setError("Password not matched")
            return
        }
        signUpWithFireBase(email,password)
    }

    private fun signUpWithFireBase(email: String, password: String) {
        setProgress(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnSuccessListener {
        it.user?.let {user->
            val userModel = UserModel(user.uid,email,email.substringBefore("@"))
            Firebase.firestore.collection("users").document(user.uid).set(userModel).addOnSuccessListener {
                allFiles.showToast(applicationContext,"Account created successfully")
                setProgress(false)
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        }.addOnFailureListener {
            allFiles.showToast(applicationContext,it.localizedMessage?: "Something went wrong")
            setProgress(false)
        }
    }

}