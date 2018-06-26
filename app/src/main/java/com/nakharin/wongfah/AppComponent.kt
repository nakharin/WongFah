package com.nakharin.wongfah

import com.nakharin.wongfah.controller.fragment.CategoryFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(fragment: CategoryFragment)
}