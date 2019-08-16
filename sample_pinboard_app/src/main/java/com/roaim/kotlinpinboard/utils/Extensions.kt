package com.roaim.kotlinpinboard.utils

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T?>.observeOnce(observer: Observer<T?>) {
    observeForever(observer.also { removeObserver(it) })
}

fun <T> LiveData<T?>.observeOnceInMain(observer: Observer<T?>) {
    Handler(Looper.getMainLooper()).post {
        observeOnce(observer)
    }
}

fun ImageView.setLiveBitmap(lifecycleOwner: LifecycleOwner, liveData: LiveData<Bitmap?>?) {
    liveData?.observe(lifecycleOwner, Observer {
        setImageBitmap(it)
    })
}