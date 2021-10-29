package com.example.nisumtest.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.nisumtest.R
import com.example.nisumtest.core.ErrorState
import com.example.nisumtest.core.ErrorStateCallback
import com.example.nisumtest.core.UIController
import com.example.nisumtest.core.showSnackBar
import com.example.nisumtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UIController {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)
    }

    override fun onError(errorState: ErrorState, errorStateCallback: ErrorStateCallback) {
        binding.root.showSnackBar(errorState.message)
        errorStateCallback.removeErrorFromStack()
    }

    override fun onMessage() {

    }

}