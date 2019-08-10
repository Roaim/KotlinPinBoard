package com.roaim.pindownloader.mock

import android.graphics.Bitmap
import com.roaim.pindownloader.data.RemoteApi
import com.roaim.pindownloader.data.RemoteDataSource
import okhttp3.ResponseBody
import org.mockito.Mockito

class MockBitmapRemoteDataSource(remoteApi: RemoteApi = Mockito.spy(RemoteApi::class.java)) :
    RemoteDataSource<Bitmap>(remoteApi) {
    override fun convert(response: ResponseBody): Bitmap {
        return Mockito.spy(Bitmap::class.java)
    }

}