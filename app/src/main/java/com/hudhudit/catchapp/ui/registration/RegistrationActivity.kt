package com.hudhudit.catchapp.ui.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hudhudit.catchapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }
}