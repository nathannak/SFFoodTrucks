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
                            navController.navigate(R.id.action_listFragment_to_mapsFragment)

                            /*
                            One change for DataBinding, another for retaining fragment name in SharedViewModel.
                            It can't be done via a SharedViewModel databinding variable in xml layout alone.
                            */
                            curFragment = "List"
                            sharedViewModel.currentFragment = "List"
                        }
                        "List" -> {
                            navController.navigate(R.id.action_mapsFragment_to_listFragment)
                            curFragment = "Map"
                            sharedViewModel.currentFragment = "Map"
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        /*
        since there is a button in toolbar to navigate
        back button does not need to do the same
        */
        finish()
    }
}