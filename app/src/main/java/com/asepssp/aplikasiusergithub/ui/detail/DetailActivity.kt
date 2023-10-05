package com.asepssp.aplikasiusergithub.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.asepssp.aplikasiusergithub.R
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseDetail
import com.asepssp.aplikasiusergithub.data.database.entity.FavoriteEntity
import com.asepssp.aplikasiusergithub.databinding.ActivityDetailBinding
import com.asepssp.aplikasiusergithub.ui.detail.fragment.FollowPager
import com.asepssp.aplikasiusergithub.ui.favorite.FavoriteViewModel
import com.asepssp.aplikasiusergithub.ui.favorite.ViewModelFavoriteFactory
import com.asepssp.aplikasiusergithub.utilities.loadImage
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var getUsername: String? = null
    private var getAvatarUrl: String? = null
    private var isFavorited: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User Github"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]


        setActionBar()
        setDataIntent()
        observeViewModel()
        setViewPager()
        getFavorites()


    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun setActionBar() {
        supportActionBar?.elevation = 0f
    }


    private fun setDataIntent() {
        getUsername = intent.getStringExtra(DetailViewModel.USERNAME)
        getAvatarUrl = intent.getStringExtra(DetailViewModel.AVATAR_URL)
        getUsername?.let { username ->
            detailViewModel.usersDetail(username)
        }
    }


    private fun observeViewModel() {
        detailViewModel.isLoading.observe(this) { isLoading -> showLoading(isLoading) }
        detailViewModel.userDetail.observe(this) { detailData ->
            usersDetail(
                detailData
            )
        }
    }


    @SuppressLint("SetTextI18n")
    private fun usersDetail(detailData: UserResponseDetail) {
        binding.imgDetailPhoto.loadImage(detailData.avatarUrl)
        binding.tvID.text = "ID : ${detailData.id}"
        binding.tvDetailName.text = detailData.name
        binding.tvDetailUsername.text = detailData.login
        binding.tvDetailFollowers.text = "${detailData.followers} Followers"
        binding.tvDetailFollowing.text = "${detailData.following} Following"
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.shareButton -> {
                val intent = Intent(Intent.ACTION_SEND)
                val shareUser = "Follow $getUsername on github!"
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, shareUser)
                startActivity(Intent.createChooser(intent, "Share with..."))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setViewPager() {
        val sectionsPagerAdapter = FollowPager(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabsLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    private fun getFavorites() {
        val favoriteViewModel by viewModels<FavoriteViewModel> {
            ViewModelFavoriteFactory.getInstance(application)
        }

        binding.favFab.setOnClickListener {
            val getUsername = intent.getStringExtra(DetailViewModel.USERNAME)
            val getAvatarUrl = intent.getStringExtra(DetailViewModel.AVATAR_URL)
            val favorite = FavoriteEntity(getUsername!!, getAvatarUrl!!)
            if (isFavorited) {
                favoriteViewModel.delete(favorite)
                Toast.makeText(this, "user $getUsername is not your favorite", Toast.LENGTH_SHORT)
                    .show()
            } else {
                favoriteViewModel.insert(favorite)
                Toast.makeText(this, "user $getUsername is your favorite", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        favoriteViewModel.getFavorite(getUsername.toString()).observe(this) {
            isFavorited = if (it?.username != null) {
                binding.favFab.setImageResource(R.drawable.favorite_icon)
                true
            } else {
                binding.favFab.setImageResource(R.drawable.favorite_border_icon)
                false
            }
        }
    }
}