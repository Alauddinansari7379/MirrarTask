package com.example.mirrartask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SimpleAdapter
import androidx.lifecycle.lifecycleScope
import com.example.mirrartask.R
import com.example.mirrartask.adapter.AdapterVideoList
import com.example.mirrartask.databinding.ActivityVideoPlayerBinding
import com.example.mirrartask.model.Camera
import com.example.mirrartask.model.ModelVideoList
import com.example.mirrartask.roomDatabase.Table
import com.example.mirrartask.utils.AppProgressBar
import com.example.mirrartask.utils.myToast
import fr.cmonapp.android_facebook_player.FacebookPlayerParameters
import fr.cmonapp.android_facebook_player.FacebookPlayerView
import kotlinx.coroutines.launch

class VideoPlayer : AppCompatActivity(), AdapterVideoList.Play {
    private lateinit var binding: ActivityVideoPlayerBinding
    private val videoLinks: ArrayList<ModelVideoList> = ArrayList()
    private val facebookPlayerView: FacebookPlayerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val videoUrl = "https://www.facebook.com/redfmindia/videos/867370527738584/"
        AppProgressBar.showLoaderDialog(this@VideoPlayer)


        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
        lifecycleScope.launch {
            addVideoLinks()
            val params = FacebookPlayerParameters(videoUrl)
            if (params.checkVideoUrl()) {
                binding.player.load(params) { facebookPlayer ->
                    // Player is loaded
                    //Examples usage
                    facebookPlayer.play()

                    facebookPlayer.pause()

                    facebookPlayer.seek(30)

                    facebookPlayer.mute()

                    facebookPlayer.unMute()

                    facebookPlayer.isMuted()

                    facebookPlayer.setVolume(0.5F)

                    facebookPlayer.getVolume()

                    facebookPlayer.getCurrentPosition { seconds ->

                    }

                    facebookPlayer.getDuration()

                }
            }

        }

    }

    private fun addVideoLinks() {
        videoLinks.add(
            ModelVideoList(
                "Video 1",
                "Description 1",
                R.drawable.play,
                "https://www.facebook.com/61550243400866/videos/145631931930256/"
            )
        )
        videoLinks.add(
            ModelVideoList(
                "Video 2",
                "Description 2",
                R.drawable.play,
                "https://www.facebook.com/100095506184024/videos/252629847694201/"
            )
        )
        videoLinks.add(
            ModelVideoList(
                "Video 3",
                "Description 3 ",
                R.drawable.play,
                "https://www.facebook.com/100095245864733/videos/1013170533256263/"
            )
        )
        videoLinks.add(
            ModelVideoList(
                "Video 4",
                "Description 4",
                R.drawable.play,
                "https://www.facebook.com/GreatIdeasGreatLife/videos/1197200391195643/"
            )
        )
        videoLinks.add(
            ModelVideoList(
                "Video 5",
                "Description 5",
                R.drawable.play,
                "https://www.facebook.com/SonySportsNetwork/videos/200396162940582/"
            )
        )
        videoLinks.add(
            ModelVideoList(
                "Video 6",
                "Description 6",
                R.drawable.play,
                "https://www.facebook.com/VGraphs/videos/1182903075681275/"
            )
        )
        videoLinks.add(
            ModelVideoList(
                "Video 7",
                "Description 7",
                R.drawable.play,
                "https://www.facebook.com/redfmindia/videos/867370527738584/"
            )
        )
        binding.recyclerView.apply {
            adapter = AdapterVideoList(this@VideoPlayer, videoLinks, this@VideoPlayer)
            AppProgressBar.hideLoaderDialog()

        }
    }

    override fun play(link: String) {
        val params = FacebookPlayerParameters(link)
        if (params.checkVideoUrl()) {
            binding.player.load(params) { facebookPlayer ->
                // Player is loaded
                //Examples usage
                facebookPlayer.play()

                facebookPlayer.pause()

                facebookPlayer.seek(30)

                facebookPlayer.mute()

                facebookPlayer.unMute()

                facebookPlayer.isMuted()

                facebookPlayer.setVolume(0.5F)

                facebookPlayer.getVolume()

                facebookPlayer.getCurrentPosition { seconds ->

                }

                facebookPlayer.getDuration()

            }
        }
    }
}