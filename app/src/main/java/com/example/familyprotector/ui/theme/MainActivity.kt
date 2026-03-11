package com.example.familyprotector.ui.theme

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.familyprotector.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_screen)

        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_bar)

        bottomBar.setOnItemSelectedListener {

            if (it.itemId == R.id.nav_sheild) {
                inflateFragment(GuardFragment.newInstance())
            }else if (it.itemId == R.id.nav_home) {
                inflateFragment(HomeFragment.newInstance())
            }

            true

        }


    }
}

private fun HomeScreen.inflateFragment(newInstance : Fragment) {

    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.frag_container, newInstance)
    transaction.commit()

}

