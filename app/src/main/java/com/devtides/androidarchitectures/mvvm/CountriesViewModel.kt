package com.devtides.androidarchitectures.mvvm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.devtides.androidarchitectures.model.CountriesService
import com.devtides.androidarchitectures.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class CountriesViewModel : ViewModel() {
    val countries: MutableLiveData<List<String>> = MutableLiveData()
    val errors: MutableLiveData<Boolean> = MutableLiveData()

    private val service: CountriesService = CountriesService()

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        service.countries
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country?>?>() {
                    override fun onError(e: Throwable) {
                        errors.value = true
                    }

                    override fun onSuccess(value: List<Country?>?) {
                        if (value == null) return
                        val countryNames: MutableList<String> = ArrayList()
                        for (country in value) {
                            if (country != null) {
                                countryNames.add(country.countryName)
                            }
                        }
                        errors.value = false
                        countries.value = countryNames
                    }
                })
    }

    fun onRefresh() {
        fetchCountries()
    }
}
