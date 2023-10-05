package com.asepssp.aplikasiusergithub.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseItems
import com.asepssp.aplikasiusergithub.databinding.ActivityFavoriteBinding
import com.asepssp.aplikasiusergithub.ui.detail.DetailActivity
import com.asepssp.aplikasiusergithub.ui.detail.DetailViewModel
import com.asepssp.aplikasiusergithub.ui.main.MainAdapter

class FavoriteActivity : AppCompatActivity() {

    private var binding: ActivityFavoriteBinding? = null
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setRV()
        setUserData()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setUserData() {
        val favoriteViewModel by viewModels<FavoriteViewModel> {
            ViewModelFavoriteFactory.getInstance(application)
        }

        favoriteViewModel.getAllFavorite().observe(this) { favoriteUser ->
            val items = arrayListOf<UserResponseItems>()
            favoriteUser.map {
                val item = UserResponseItems(login = it.username, avatarUrl = it.avatarUrl!!)
                items.add(item)

            }
            mainAdapter.submitFavoriteList(items)
        }

        mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponseItems) {
                putDetailUser(data)
            }
        })
    }


    private fun putDetailUser(data: UserResponseItems) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailViewModel.USERNAME, data.login)
        intent.putExtra(DetailViewModel.AVATAR_URL, data.avatarUrl)
        startActivity(intent)
    }


    private fun setRV() {
        val layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvUser?.addItemDecoration(itemDecoration)

        mainAdapter = MainAdapter()
        binding?.rvUser?.adapter = mainAdapter

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}