package gr.novidea.museumapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import gr.novidea.museumapp.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.userFragment -> {
                    try {
                        navController.navigate(R.id.action_staffHomeFragment_to_homeFragment)
                    } catch (e: Exception) { }
                }
                R.id.staffFragment -> {
                    try {
                        navController.navigate(R.id.action_homeFragment_to_staffHomeFragment)
                    } catch (e: Exception) { }
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}