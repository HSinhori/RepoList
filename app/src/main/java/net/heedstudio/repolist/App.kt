package net.heedstudio.repolist

import android.app.Application
import net.heedstudio.repolist.data.di.DataModule
import net.heedstudio.repolist.domain.di.DomainModule
import net.heedstudio.repolist.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }
}