package com.example.mirrartask.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mirrartask.adapter.AdapterDownloadedImg
import com.example.mirrartask.databinding.ActivityDownloadedDataBinding
import com.example.mirrartask.roomDatabase.MyViewModel
import com.example.mirrartask.utils.AppProgressBar
import com.example.mirrartask.utils.myToast

class DownloadedData : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadedDataBinding
    private val adapter by lazy { AdapterDownloadedImg() }
    private lateinit var myViewModel: MyViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadedDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppProgressBar.showLoaderDialog(this@DownloadedData)
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        binding.recyclerView.adapter = adapter

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnDelete.setOnClickListener {
            AppProgressBar.showLoaderDialog(this@DownloadedData)
            myViewModel.delete()
            myViewModel.readData.observe(this) {
                adapter.setData(it)
            }
            AppProgressBar.hideLoaderDialog()
            myToast(this@DownloadedData, "Data Deleted Successfully")


        }

        myViewModel.readData.observe(this) {
            adapter.setData(it)
            AppProgressBar.hideLoaderDialog()

        }

    }

}
