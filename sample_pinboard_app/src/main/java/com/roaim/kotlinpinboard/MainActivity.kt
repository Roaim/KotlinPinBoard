package com.roaim.kotlinpinboard

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.roaim.kotlinpinboard.databinding.ActivityMainBinding
import com.roaim.pindownloader.BitmapPinDownloader

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val bitmapViewModel = ViewModelProviders.of(this).get(BitmapViewModel::class.java)
        binding.vm = bitmapViewModel
        binding.lifecycleOwner = this
    }
}

class BitmapViewModel : ViewModel() {
    private val bitmapDownloader = BitmapPinDownloader()
    val thumbBitmap =
        bitmapDownloader.download(url = "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=200\\u0026fit=max\\u0026s=9fba74be19d78b1aa2495c0200b9fbce")
    val thumbBitmap2 =
        bitmapDownloader.download(url = "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=200\\u0026fit=max\\u0026s=9fba74be19d78b1aa2495c0200b9fbce")
    //    val thumbBitmapAnother =
//        bitmapDownloader.download(url = "https://images.unsplash.com/photo-1464550838636-1a3496df938b?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=200\\u0026fit=max\\u0026s=9947985b2095f1c8fbbbe09a93b9b1d9")
    private val _buttonBitmap = MediatorLiveData<Bitmap>()
    val buttonBitmap: LiveData<Bitmap>
        get() = _buttonBitmap
    private val _buttonBitmap2 = MediatorLiveData<Bitmap>()
    val buttonBitmap2: LiveData<Bitmap>
        get() = _buttonBitmap2

    fun download() {
        val url =
            "https://images.unsplash.com/photo-1464550838636-1a3496df938b?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=200\\u0026fit=max\\u0026s=9947985b2095f1c8fbbbe09a93b9b1d9"
        _buttonBitmap.addSource(bitmapDownloader.download(url = url)) {
            _buttonBitmap.value = it
        }
//        if (_buttonBitmap2.hasObservers()) _buttonBitmap2.removeSource(thumbBitmap2)
        _buttonBitmap2.hasActiveObservers().let { _buttonBitmap2.removeSource(thumbBitmap2) }
        _buttonBitmap2.addSource(thumbBitmap2) { _buttonBitmap2.value = it }
    }
}

object MainBindingAdapter {
    @BindingAdapter("imageBitmap")
    fun setImageBitmap(imageView: ImageView, liveData: LiveData<Bitmap>) {
        liveData.value?.let { imageView.setImageBitmap(it) }
    }
}
