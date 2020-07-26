package com.devtides.androidarchitectures.mvp

import com.devtides.androidarchitectures.model.CountriesService
import com.devtides.androidarchitectures.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class CountriesPresenter(private val view: View) {
    interface View {
        fun setValues(values: List<String>)
        fun onError()
    }

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
                        view.onError()
                    }

                    override fun onSuccess(value: List<Country?>?) {
                        if (value == null) return
                        val countryNames: MutableList<String> = ArrayList()
                        for (country in value) {
                            if (country != null) {
                                countryNames.add(country.countryName)
                            }
                        }
                        view.setValues(countryNames)
                    }
                })
    }

    fun onRefresh() {
        fetchCountries()
    }
}