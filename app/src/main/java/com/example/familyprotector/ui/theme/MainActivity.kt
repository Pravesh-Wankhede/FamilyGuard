package com.example.familyprotector.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.familyprotector.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_screen)

        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_bar)

        // ✅ Bottom Navigation
        bottomBar.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.nav_sheild -> loadFragment(GuardFragment.newInstance())
                R.id.nav_home -> loadFragment(HomeFragment.newInstance())
                R.id.nav_dashboard -> loadFragment(MapsFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment.newInstance())
            }

            true
        }

        // ✅ Default screen
        bottomBar.selectedItemId = R.id.nav_home

        // ✅ Save user to Firebase (safe)
        saveUserToFirebase()
    }

    // ✅ Fragment loader
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment)
            .commit()
    }

    // ✅ Firebase user save (safe)
    private fun saveUserToFirebase() {
        val user = FirebaseAuth.getInstance().currentUser ?: return

        val data = hashMapOf(
            "name" to user.displayName,
            "mail" to user.email,
            "photoUrl" to user.photoUrl.toString(),
            "uid" to user.uid
        )

        Firebase.firestore
            .collection("users")
            .document(user.email ?: user.uid)
            .set(data)
    }
}