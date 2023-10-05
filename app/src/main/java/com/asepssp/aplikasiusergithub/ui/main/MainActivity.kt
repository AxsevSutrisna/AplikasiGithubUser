package com.asepssp.aplikasiusergithub.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.asepssp.aplikasiusergithub.R
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseItems
import com.asepssp.aplikasiusergithub.databinding.ActivityMainBinding
import com.asepssp.aplikasiusergithub.ui.detail.DetailActivity
import com.asepssp.aplikasiusergithub.ui.detail.DetailViewModel
import com.asepssp.aplikasiusergithub.ui.favorite.FavoriteActivity
import com.asepssp.aplikasiusergithub.ui.mode.ModeActivity
import com.asepssp.aplikasiusergithub.ui.mode.ModeViewModel
import com.asepssp.aplikasiusergithub.ui.mode.SettingPreferences
import com.asepssp.aplikasiusergithub.ui.mode.ViewModelFactory
import com.asepssp.aplikasiusergithub.ui.mode.dataStore

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainVieModel: MainViewModel
    private lateinit var adapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()

        mainVieModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        setMenu()
        setRV()
        setSearchBar()
        observeViewModel()
        settingMode()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun setMenu() {
        binding.searchBar.inflateMenu(R.menu.option_menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuFavorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menuSetMode -> {
                    val intent = Intent(this, ModeActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }


    private fun setRV() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        adapter = MainAdapter()
        binding.rvUser.adapter = adapter
    }


    private fun setSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    mainVieModel.usersSearch(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })

            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val textSearchBar = searchView.text
                    mainVieModel.usersSearch(textSearchBar.toString())


                    searchView.hide()

                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    private fun settingMode(){
        val preference = SettingPreferences.getInstance(application.dataStore)
        val mainViemModel = ViewModelProvider(this, ViewModelFactory(preference))[ModeViewModel::class.java]

        mainViemModel.getModeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setUser(githubUser: List<UserResponseItems>) {

        adapter.submitFavoriteList(githubUser)

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponseItems) {
                putDetail(data)
            }
        })
        if (githubUser.isEmpty()) {
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun putDetail(data: UserResponseItems) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailViewModel.USERNAME, data.login)
        intent.putExtra(DetailViewModel.AVATAR_URL, data.avatarUrl)
        startActivity(intent)
    }


    private fun observeViewModel() {
        mainVieModel.usersList()
        mainVieModel.userList.observe(this) { setUser(it) }
        mainVieModel.userSearch.observe(this) { dataUser -> setUser(dataUser) }
        mainVieModel.isLoading.observe(this) { showLoading(it) }

    }

}