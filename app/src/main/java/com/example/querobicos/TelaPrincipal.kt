package com.example.querobicos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.querobicos.Form.FormLogin
import com.example.querobicos.Fragments.Bicos
import com.example.querobicos.Fragments.CadastrarBicos
import com.example.querobicos.Fragments.Profissionais
import com.example.querobicos.databinding.ActivityTelaPrincipalBinding
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipal : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTelaPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.appBarTelaPrincipal.toolbar

        setSupportActionBar(toolbar)

        loadFragment(Bicos())

        binding.appBarTelaPrincipal.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sair->
            {
                FirebaseAuth.getInstance().signOut()
                openLogin()
            }
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.tela_principal, menu)
        return true
    }

    private fun openLogin(){
        val screenLogin = Intent(this, FormLogin::class.java)
        startActivity(screenLogin)
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_bicos-> loadFragment(Bicos())
            R.id.nav_profissionais-> loadFragment(Profissionais())


            R.id.nav_addBico->{
                val screenLogin = Intent(this, CadastrarBicos::class.java)
                startActivity(screenLogin)

            }
            R.id.nav_perfil->{

            }
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun loadFragment(fragment:Fragment){
        val classFragment = fragment
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.frame_conteiner, classFragment)
        fragment.commit()
    }
}