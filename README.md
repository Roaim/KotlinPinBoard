# KotlinPinBoard
An async remote resource downloading library with sample pin board application using Android Jetpack written in Kotlin. 
** Read the [Wiki](https://github.com/Roaim/KotlinPinBoard/wiki) to know more about the project.**

## Technologies
* Android
* Kotlin
* Jetpack
* MVVM
* Repository Pattern
* LiveData
* Coroutine
* Retrofit
* Navigation Component
* Paging Library
* Android Data Binding
* Androidx Testing
* Mockito

## Modules:
### 1. lib_pin_downloader (Async Downloader library)
#### Features:
* Download Bitmap
* Download Json and serialize to kotlin data object
* Abstraction for supporting future data types
* Cache Data in LruCache
* If data is present in the cache then return from cache otherwise fetch from remote service and add to cache
* Uses 1/8 of the total allocated memory for caching and evicts less frequent item when cache reaches its limit
* Local Unit Test with 100% test coverage
#### Usage:
The root ```build.gradle``` should look like

```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
and in the app module ```build.gradle``` add following

```
dependencies {
    implementation 'com.github.Roaim:KotlinPinBoard:v2.5'
}
```
To download bitmap image :
``` Kotlin
fun downloadAndDisplayBitmap(lifecycleOwner: LifecycleOwner, imageView: ImageView?, url : String) {
        val bitmapPinDownloader = BitmapPinDownloader()
        bitmapPinDownloader.download(url).observe(lifecycleOwner, Observer {
            imageView?.setImageBitmap(it)
        })
}
```
To download json :
``` Kotlin
fun downloadJson(lifecycleOwner: LifecycleOwner, url: String) {
        JsonPinDownloader().download(url)
            .observe(lifecycleOwner, Observer { jsonElement ->
                // process jsonElement
                    ...
                // or convert it to pojo
                val poJo = jsonElement?.toPoJo<Unit>(PojoModel::class.java)
                    ...
         })
}
```

### 2. sample_pinboard_app (Sample Gallery App)
#### Features:
* Uses PinDownloader library module to load json and bitmap files
* Display images in Gallery by RecyclerView and PagedListAdapter
* Load more data when scrolling to the end of the list
* Upon clicking on an item navigate to full image screen with the help of Navigation Component

## Yet to come
* [ ] Efficiently decoding Bitmap by following [Official documentation](https://developer.android.com/topic/performance/graphics/load-bitmap)
* [ ] Make `PinDownloader<T>` return a sealed class object `Pin<T>` containing `Loading`, `Success`, `Failure` data classes instead of returning just `T`
* [x] Publish the library to [Jitpack.io](https://jitpack.io/)/[Bintray](https://bintray.com/)
* [ ] LocalUnitTests, IntegrationTests and UITests
