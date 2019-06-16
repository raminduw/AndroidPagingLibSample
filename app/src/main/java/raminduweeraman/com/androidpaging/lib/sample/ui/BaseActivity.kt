package raminduweeraman.com.androidpaging.lib.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import raminduweeraman.com.androidpaging.lib.sample.api.CarService
import raminduweeraman.com.androidpaging.lib.sample.di.DaggerAppComponent

abstract class BaseActivity : AppCompatActivity() {

    lateinit var compositeDisposable : CompositeDisposable
    lateinit var carService : CarService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    private fun initDagger(){
        val appComponent = DaggerAppComponent.builder().build()
        compositeDisposable =  appComponent.getDisposable()
        carService = appComponent.getService()
    }
}
