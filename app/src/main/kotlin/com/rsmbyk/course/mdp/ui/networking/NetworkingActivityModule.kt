package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.common.CameraUtil
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.repository.ImageDataRepository
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named

@Module
class NetworkingActivityModule {

    @Provides
    fun provideImageRepository (context: Context, volleyRequestQueue: VolleyRequestQueue)
            : ImageRepository =
        ImageDataRepository (context, volleyRequestQueue)

    @Provides
    fun provideGetCapturedImageUseCase (imageRepository: ImageRepository): GetCapturedImageUseCase =
        GetCapturedImageUseCase (imageRepository)

    @Provides
    fun provideUploadImageUseCase (imageRepository: ImageRepository): UploadImageUseCase =
        UploadImageUseCase (imageRepository)

    @Provides
    fun provideNetworkingViewModelFactory (
        @Named ("upload_image_directory") uploadImageDirectory: File,
        getCapturedImageUseCase: GetCapturedImageUseCase,
        uploadImageUseCase: UploadImageUseCase)
            : NetworkingViewModelFactory =
        NetworkingViewModelFactory (uploadImageDirectory, getCapturedImageUseCase, uploadImageUseCase)

    @Provides
    fun provideNetworkingViewModel (activity: NetworkingActivity, factory: NetworkingViewModelFactory)
            : NetworkingViewModel =
        ViewModelProviders.of (activity, factory).get (NetworkingViewModel::class.java)

    @Provides
    fun provideCameraActivityModule (activity: NetworkingActivity): CameraUtil =
        CameraUtil (activity)
}
