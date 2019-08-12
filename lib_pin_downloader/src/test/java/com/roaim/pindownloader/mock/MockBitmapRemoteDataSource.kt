package com.roaim.pindownloader.mock

import android.graphics.Bitmap
import com.roaim.pindownloader.core.RemoteApi
import com.roaim.pindownloader.core.RemoteDataSource
import okhttp3.ResponseBody
import org.mockito.Mockito

class MockBitmapRemoteDataSource(remoteApi: RemoteApi = Mockito.spy(RemoteApi::class.java)) :
    RemoteDataSource<Bitmap>(remoteApi) {
    override suspend fun convert(response: ResponseBody?): Bitmap? {
        return response?.let { Mockito.spy(Bitmap::class.java) }
    }

}