package com.devtides.androidarchitectures.mvvm

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.devtides.androidarchitectures.R

class MVVMActivity : AppCompatActivity() {
    private lateinit var viewModel: CountriesViewModel
    private val listValues: MutableList<String> = ArrayList()
    private val adapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)
    }
    private val list: ListView by lazy {
        findViewById(R.id.list) as ListView
    }
    private val retryButton: Button by lazy {
        findViewById(R.id.retryButton) as Button
    }
    private val progress: ProgressBar by lazy {
        findViewById(R.id.progress) as ProgressBar
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvc)

        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)

        list.adapter = adapter
        list.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "You clicked " + listValues.get(position), Toast.LENGTH_SHORT).show()}
        observeViewModel()
    }

    fun observeViewModel() {

        viewModel.countries.observe(this, Observer<List<String>>{
            countries ->
            if (countries != null) {
                listValues.clear()
                listValues.addAll(countries)
                retryButton.visibility = View.GONE
                progress.visibility = View.GONE
                list.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            } else {
                list.visibility = View.GONE
            }
        })

        viewModel.errors.observe(this, Observer<Boolean> {hasError ->
            if (hasError == null) return@Observer
            if (hasError) {
                Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE
                list.visibility = View.GONE
                retryButton.visibility = View.VISIBLE
            } else
                Log.e("TAG", "observeViewModel:" )
        }

        )
    }


    fun onRetry(view: View?) {
        viewModel.onRefresh()
        list.visibility = View.GONE
        retryButton.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }


    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, MVVMActivity::class.java)
        }
    }
}