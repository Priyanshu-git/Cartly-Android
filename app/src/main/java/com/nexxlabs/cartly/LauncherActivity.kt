package com.nexxlabs.cartly

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nexxlabs.cartly.data.preferences.SessionManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class LauncherActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val token = sessionManager.authToken.firstOrNull()
            val target = if (!token.isNullOrEmpty()) {
                MainActivity::class.java
            } else {
                AuthActivity::class.java
            }

            startActivity(Intent(this@LauncherActivity, target))
            finish()
        }
    }
}