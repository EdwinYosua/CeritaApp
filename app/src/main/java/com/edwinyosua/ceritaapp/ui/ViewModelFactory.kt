package com.edwinyosua.ceritaapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edwinyosua.ceritaapp.di.Injection
import com.edwinyosua.ceritaapp.repository.AppsRepository
import com.edwinyosua.ceritaapp.ui.home.HomeViewModel
import com.edwinyosua.ceritaapp.ui.login.LoginViewModel
import com.edwinyosua.ceritaapp.ui.main.MainViewModel
import com.edwinyosua.ceritaapp.ui.register.RegisterViewModel

class ViewModelFactory private constructor(
    private val appRepo: AppsRepository,
//    private val pref: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(appRepo) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(appRepo) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(appRepo) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(appRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideRepo(context),
//                pref = SettingPreferences.getInstance(context.dataStore)
            )
        }.also { instance = it }
    }
}