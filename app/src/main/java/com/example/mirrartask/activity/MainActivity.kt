package com.example.mirrartask.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mirrartask.R
import com.example.mirrartask.adapter.AdapterDailyImage
import com.example.mirrartask.databinding.ActivityMainBinding
import com.example.mirrartask.model.ModelDailyImage
import com.example.mirrartask.model.ModelImageOTD
import com.example.mirrartask.retrofit.ApiClient
import com.example.mirrartask.roomDatabase.MyViewModel
import com.example.mirrartask.roomDatabase.Table
import com.example.mirrartask.utils.myToast
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),AdapterDailyImage.Download{
    private lateinit var binding: ActivityMainBinding  //Bind XML Layout
    private lateinit var myViewModel: MyViewModel
    var shimmerFrameLayout: ShimmerFrameLayout? = null
    private var description = false
    private var mainData: Any? = null
    var imageURL = ""
    var title = ""
    var date = ""
    private lateinit var drawerLayout: DrawerLayout

    companion object {
        const val ApiKey =
            "xbn25URNmNIhkMhZv5a2c8Cdku4I09wwbAALpBQM"  //API key Generated by Nasa portal
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shimmerFrameLayout = findViewById(R.id.shimmer)
        shimmerFrameLayout = findViewById(R.id.shimmer_daily_image)

        //start Placeholder
        shimmerFrameLayout!!.startShimmer()
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        apiCallImageOFTheDay() //API Calling
        apiCallGetDailyImage()


        binding.viewDescription.setOnClickListener {
            if (description) {
                description = false
                binding.layoutDescription.visibility = View.GONE
            } else {
                description = true
                binding.layoutDescription.visibility = View.VISIBLE

            }
        }
        //click to Download Data
        binding.btnDownload.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE
            binding.btnDownloading.visibility = View.VISIBLE
            binding.btnDownload.visibility = View.GONE
            if (imageURL.isNotEmpty()) {
                lifecycleScope.launch {
                    val data = Table(title, date, getBitmap(imageURL))
                    myViewModel.insertPerson(data)
                    myToast(this@MainActivity, "Data Locally Stored")
                    binding.progressCircular.visibility = View.GONE
                    binding.btnDownloading.visibility = View.GONE
                    binding.btnDownload.visibility = View.VISIBLE
                }
            } else {
                myToast(this@MainActivity, "Data Not found")

            }
        }

        //Click to Side Drawer
        binding.drawerClick.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            binding.includeDrawer.layoutDownloaded.setOnClickListener {
                startActivity(Intent(this, DownloadedData::class.java))
                drawerLayout.closeDrawer(GravityCompat.START)
            }

            binding.includeDrawer.layoutVideoPlayer.setOnClickListener {
                startActivity(Intent(this, VideoPlayer::class.java))
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        drawerLayout = binding.drawerLayout

    }

    private fun apiCallImageOFTheDay() {
        ApiClient.apiService.getImageOfTheDay(ApiKey)
            .enqueue(object : Callback<ModelImageOTD> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelImageOTD>, response: Response<ModelImageOTD>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(this@MainActivity, "Server error")
                            binding.imageOfTheDay.visibility = View.VISIBLE
                            binding.shimmer.visibility = View.GONE

                        } else if (response.code() == 400) {
                            myToast(this@MainActivity, "Not found")
                            binding.imageOfTheDay.visibility = View.VISIBLE
                            binding.shimmer.visibility = View.GONE
                        } else if (response.body()!!.url.isEmpty()) {
                            binding.shimmer.visibility = View.GONE
                            myToast(this@MainActivity, "No Image Found")
                        } else {
                            binding.imageOfTheDay.visibility = View.VISIBLE
                            binding.shimmer.visibility = View.GONE
                            binding.tvTitle.text = response.body()!!.title
                            binding.tvDate.text = response.body()!!.date
                            binding.tvDescription.setText(response.body()!!.explanation)

                            Log.e("iisnd", "${response.body()!!.url}")
                            imageURL = response.body()!!.url
                            title = response.body()!!.title
                            date = response.body()!!.date

                            Picasso.get().load("${response.body()!!.url}")
                                .placeholder(R.drawable.placeholder_n)
                                .error(R.drawable.error_placeholder)
                                .into(binding.imageOfTheDay)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelImageOTD>, t: Throwable) {
                    binding.shimmer.visibility = View.GONE
                    myToast(this@MainActivity, "Something went wrong")

                }

            })
    }


    private fun apiCallGetDailyImage() {
        ApiClient.apiService.getDailyImage("1", "1000", ApiKey)
            .enqueue(object : Callback<ModelDailyImage> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelDailyImage>, response: Response<ModelDailyImage>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(this@MainActivity, "Server error")
                            binding.shimmerDailyImage.visibility = View.GONE

                        } else if (response.code() == 400) {
                            myToast(this@MainActivity, "Not found")
                            binding.shimmerDailyImage.visibility = View.GONE
                        } else {
                            binding.recyclerView.apply {
                                binding.recyclerView.visibility = View.VISIBLE
                                binding.shimmerDailyImage.visibility = View.GONE
                                adapter = AdapterDailyImage(this@MainActivity, response.body()!!,this@MainActivity)
                                binding.recyclerView.layoutManager = GridLayoutManager(context, 3)

                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFailure(call: Call<ModelDailyImage>, t: Throwable) {
                    binding.shimmerDailyImage.visibility = View.GONE
                    myToast(this@MainActivity, "Something went wrong")

                }

            })
    }

    //Covert Image to Bitmap for storing data
    private suspend fun getBitmap(imageURL: String): Bitmap {
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(imageURL)
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap

    }
// Download Image and saved in Room database
    override fun download(imageUrl: String, title: String, date: String) {
        if (imageURL.isNotEmpty()) {
            lifecycleScope.launch {
                val data = Table(title, date, getBitmap(imageUrl))
                myViewModel.insertPerson(data)
                myToast(this@MainActivity, "Data Locally Stored")
                binding.progressCircular.visibility = View.GONE
                binding.btnDownloading.visibility = View.GONE
                binding.btnDownload.visibility = View.VISIBLE
            }
        } else {
            myToast(this@MainActivity, "Data Not found")

        }
     }

}