package com.demo.sffoodtrucks.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.demo.sffoodtrucks.R
import com.demo.sffoodtrucks.databinding.ActivityMainBinding
import com.demo.sffoodtrucks.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var navController : NavController
    lateinit var mainSharedViewModel : SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainSharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        navController = Navigation.findNavController(this,R.id.navhost_fragment)

        setupCurrentFragmentName(binding)
        setupToolbarTextListener(binding)
    }

    private fun setupCurrentFragmentName(binding: ActivityMainBinding) {
        binding.apply {
            curFragment = mainSharedViewModel.currentFragment
        }
    }

    private fun setupToolbarTextListener(binding: ActivityMainBinding) {
        binding.apply {

            listener = object : ToolbarButtonClickListener {
                override fun onButtonClicked(destination: String) {
                    when(destination) {
                        "List" -> {
                            Toast.makeText(applicationContext, "GO TO MAP", Toast.LENGTH_LONG).show()

                            curFragment = "Map"
                            navController.navigate(R.id.action_listFragment_to_mapsFragment)
                        }
                        "Map" -> {
                            Toast.makeText(applicationContext, "GO TO LIST", Toast.LENGTH_LONG).show()

                            curFragment = "List"
                            navController.navigate(R.id.action_mapsFragment_to_listFragment)
                        }
                    }
                }
            }
        }
    }

}