package com.example.sigproj

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sigproj.ui.theme.SigprojTheme
import android.content.Intent
import android.nfc.Tag
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.sigproj.R.string
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signout: Button
    val textView = R.id.name
    private lateinit var signinbutton: Button
    private var RC_SIGN_IN = 123
    private lateinit var textView1: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginactivity)

        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)
        signinbutton = findViewById<Button>(R.id.signInButton)


        loginButton.setOnClickListener {
            // Replace the condition with your actual login logic
            if (isValidLogin()) {
                navigateToHome()
            } else {
                showToast("Invalid credentials")
            }
        }

        signinbutton.setOnClickListener {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)


        }
    }

     fun handleSignInResult(completedTask : Task<GoogleSignInAccount>) {
            try {

                val account = completedTask.getResult(ApiException::class.java)
                val idToken = account?.idToken
                val email = account?.email
                val name = account?.displayName

                if(email != null) {

                    //textView1.text = "Email: $email\nName: $name"
                    navigateToHome()
                }
                else {

                    //textView1.text = "No result"
                }

            } catch (e: ApiException) {

                Log.d("MainActivity", "signInResult: failed code = " + e.statusCode)
            }

        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == RC_SIGN_IN) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }


    private fun isValidLogin(): Boolean {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Replace this with your actual login validation logic
        return username == "shraddha" && password == "kulkarni"
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
        finish() // Optional: Close the login activity so the user can't go back
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
