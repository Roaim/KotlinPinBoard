package com.roaim.kotlinpinboard

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.databinding.ActivityTestBinding
import com.roaim.pindownloader.BitmapPinDownloader
import com.roaim.pindownloader.JsonPinDownloader
import com.roaim.pindownloader.toPoJo

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        val bitmapViewModel = ViewModelProviders.of(this).get(BitmapViewModel::class.java)
        binding.vm = bitmapViewModel
        binding.lifecycleOwner = this

        Transformations.switchMap(JsonPinDownloader().download(BuildConfig.SAMPLE_JSON_URL)) {
            Log.d(javaClass.name, "${it?.toString()}")
            it?.toPoJo(Array<LoremPicksum>::class.java)?.run {
                MutableLiveData<List<LoremPicksum>>(asList())
            }
        }.observe(this, Observer {
            Log.d(javaClass.name, "${it?.toString()}")
        })
    }
}

class BitmapViewModel : ViewModel() {
    companion object {
        private val bmpUrlList = listOf(
            "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=9fba74be19d78b1aa2495c0200b9fbce",
            "https://images.unsplash.com/photo-1464550838636-1a3496df938b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=9947985b2095f1c8fbbbe09a93b9b1d9",
            // returns 404
            "https://images.unsplash.com/photo-1464550580740-b3f73fd373cb?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=32eedaa5d930578ff89cff9195472650",
            "https://images.unsplash.com/photo-1464547323744-4edd0cd0c746?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=94a7331fc80787d57ba3b4b0c757131f"
        )
    }

    private val bitmapDownloader = BitmapPinDownloader()

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progress

    init {
        _progress.value = View.GONE
    }

    private val _bmpPreview = MutableLiveData<Bitmap>()
    val bmpPreview: LiveData<Bitmap>
        get() = _bmpPreview

    val bmpThumbs = mutableListOf<MutableLiveData<Bitmap>>().apply {
        repeat(4) {
            add(MutableLiveData())
        }
    }
    private val map = bmpUrlList.zip(bmpThumbs).toMap()

    private val _etUrl = MutableLiveData<String>()
    val etUrl: LiveData<String>
        get() = _etUrl

    private val _urlText = MutableLiveData<String>()
    val urlText: LiveData<String>
        get() = _urlText

    // Needs for stupid data binding
    fun download() = download(bmpUrlList.random())

    fun download(url: String = bmpUrlList.random()) {
        _progress.value = View.VISIBLE
        _etUrl.value = ""
        _urlText.value = url
        bitmapDownloader.download(url).apply {
            observeForever {
                _progress.value = View.GONE
                _bmpPreview.value = it
                map[url]?.value = it
                removeObserver {}
            }
        }
    }
}
