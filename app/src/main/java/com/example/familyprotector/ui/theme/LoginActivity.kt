package com.example.familyprotector.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.example.familyprotector.R
import com.example.familyprotector.ui.theme.MainActivity
import com.example.familyprotector.ui.theme.SharedPreference
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var credentialManager: CredentialManager
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        credentialManager = CredentialManager.create(this)
        firebaseAuth = FirebaseAuth.getInstance()

        if (SharedPreference.isLoggedIn() && firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        val btn = findViewById<android.view.View>(R.id.btn_signing)
        btn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        credentialManager.getCredentialAsync(
            this,
            request,
            null,
            ContextCompat.getMainExecutor(this),
            object : androidx.credentials.CredentialManagerCallback<GetCredentialResponse, GetCredentialException> {

                override fun onResult(result: GetCredentialResponse) {
                    handleSignIn(result)
                }

                override fun onError(e: GetCredentialException) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Failed: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential


        when {
            credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken
                    val name = googleIdTokenCredential.displayName ?: "User"

                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

                    firebaseAuth.signInWithCredential(firebaseCredential)
                        .addOnSuccessListener {
                            SharedPreference.setLogin(true)
                            Toast.makeText(
                                this@LoginActivity,
                                "Welcome $name",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this@LoginActivity,
                                "Firebase Auth Failed: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                } catch (e: GoogleIdTokenParsingException) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Token Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            else -> {
                Toast.makeText(
                    this@LoginActivity,
                    "Unexpected credential type: ${credential.type}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}