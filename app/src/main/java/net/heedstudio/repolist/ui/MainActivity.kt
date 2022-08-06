package net.heedstudio.repolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import net.heedstudio.repolist.R
import net.heedstudio.repolist.databinding.ActivityMainBinding
import net.heedstudio.repolist.ui.fragments.MyRepoFragment
import net.heedstudio.repolist.ui.fragments.SearchRepoFragment

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener{

    private lateinit var binding: ActivityMainBinding
    private val myOwnFragment = MyRepoFragment()
    private val searchFragment = SearchRepoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(myOwnFragment)
        binding.bottomNavigationMain.selectedItemId = R.id.my_own_repo_view
        binding.bottomNavigationMain.setOnNavigationItemSelectedListener(this)
    }

    private fun replaceFragment(fragment: Fragment? = null){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_main, fragment)
            transaction.commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.my_own_repo_view -> replaceFragment(myOwnFragment)

            R.id.search_view -> replaceFragment(searchFragment)

        }
        return true
    }
}