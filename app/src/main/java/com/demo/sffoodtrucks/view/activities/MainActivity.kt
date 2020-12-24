package com.demo.sffoodtrucks.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.demo.sffoodtrucks.R
import com.demo.sffoodtrucks.databinding.ActivityMainBinding
import com.demo.sffoodtrucks.view.listeners.ToolbarButtonClickListener
import com.demo.sffoodtrucks.viewmodel.SharedViewModel
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var navController : NavController
    lateinit var sharedViewModel : SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        navController = Navigation.findNavController(this,R.id.navhost_fragment)
        AndroidThreeTen.init(this);

        setupCurrentFragmentName(binding)
        setupToolbarTextListener(binding)
    }

    private fun setupCurrentFragmentName(binding: ActivityMainBinding) {
        binding.apply {
            curFragment = sharedViewModel.currentFragment
        }
    }

    private fun setupToolbarTextListener(binding: ActivityMainBinding) {
        binding.apply {

            listener = object : ToolbarButtonClickListener {
                override fun onButtonClicked(destination: String) {
                    when(destination) {
                        "Map" -> {
                            Toast.makeText(applicationContext, "GO TO MAP", Toast.LENGTH_LONG).show()

                            curFragment = "List"
                            navController.navigate(R.id.action_listFragment_to_mapsFragment)
                        }
                        "List" -> {
                            Toast.makeText(applicationContext, "GO TO LIST", Toast.LENGTH_LONG).show()

                            curFragment = "Map"
                            navController.navigate(R.id.action_mapsFragment_to_listFragment)
                        }
                    }
                }
            }
        }
    }

}