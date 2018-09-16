package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.common.CameraUtil
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.repository.ImageDataRepository
import com.rsmbyk.course.mdp.data.repository.UploadImageDataRepository
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named

@Module
class NetworkingFragmentModule {

    @Provides
    fun provideImageRepository (): ImageRepository =
        ImageDataRepository ()

    @Provides
    fun provideGetCapturedImageUseCase (imageRepository: ImageRepository): GetCapturedImageUseCase =
        GetCapturedImageUseCase (imageRepository)

    @Provides
    fun provideUploadImageRepository (context: Context, volleyRequestQueue: VolleyRequestQueue)
            : UploadImageRepository =
        UploadImageDataRepository (context, volleyRequestQueue)

    @Provides
    fun provideUploadImageUseCase (uploadImageRepository: UploadImageRepository): UploadImageUseCase =
        UploadImageUseCase (uploadImageRepository)

    @Provides
    fun provideNetworkingViewModelFactory (
        @Named ("nrp") nrp: String,
        @Named ("upload_image_directory") uploadImageDirectory: File,
        getCapturedImageUseCase: GetCapturedImageUseCase,
        uploadImageUseCase: UploadImageUseCase)
            : NetworkingViewModelFactory =
        NetworkingViewModelFactory (nrp, uploadImageDirectory, getCapturedImageUseCase, uploadImageUseCase)

    @Provides
    fun provideNetworkingViewModel (fragment: NetworkingFragment, factory: NetworkingViewModelFactory)
            : NetworkingViewModel =
        ViewModelProviders.of (fragment, factory).get (NetworkingViewModel::class.java)

    @Provides
    fun provideCameraActivityModule (fragment: NetworkingFragment): CameraUtil =
        CameraUtil (fragment)
}
