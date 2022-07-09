package com.example.olxapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.olxapplication.BaseActivity
import com.example.olxapplication.MainActivity
import com.example.olxapplication.R
import com.example.olxapplication.utilities.Constants
import com.example.olxapplication.utilities.SharedPref
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*;

class LoginActivity: BaseActivity(), View.OnClickListener {


    private lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN: Int=100
    private var googleSignInOptions: GoogleSignInOptions? = null
    private var googleSignInClient: GoogleSignInClient? = null

    private val TAG = LoginActivity::class.java.simpleName

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        clickListeners()
        configureGoogleSignin()
    }

    private fun googleSignIn() {
          val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    private fun configureGoogleSignin() {
       googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
           .requestIdToken(getString(R.string.default_web_client_id))
           .requestEmail()
           .build()

       googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions!!)

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult((ApiException::class.java))
                firebaseAuthWithGoogle(account!!)
            }catch (e: ApiException){
                Log.w(TAG, "Google sign in failed",e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
          val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
          auth.signInWithCredential(credentials).addOnCompleteListener {
              if (it.isSuccessful){
                  if(account.email != null)
                      SharedPref(this).setString(Constants.USER_EMAIL,account.email!!)
                  if(account.id != null)
                      SharedPref(this).setString(Constants.USER_ID,account.id!!)
                  if(account.displayName != null)
                      SharedPref(this).setString(Constants.USER_NAME,account.displayName!!)
                  if(account.photoUrl != null)
                      SharedPref(this).setString(Constants.USER_PHOTO,account.photoUrl.toString()!!)
                  startActivity(Intent(this,MainActivity::class.java))
                  finish()
              }
              else{
                  Toast.makeText(this,"Google Signin Failed",Toast.LENGTH_LONG).show()
              }
          }

    }

    private fun clickListeners() {
          ll_google.setOnClickListener(this)
           ll_fb.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ll_google->{
                googleSignIn()
            }
            R.id.ll_fb->{}
        }
    }


}