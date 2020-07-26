package com.devtides.androidarchitectures.mvc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.devtides.androidarchitectures.R

class MVCActivity : AppCompatActivity() {
    private val listValues: MutableList<String> = ArrayList()
    private var adapter: ArrayAdapter<String>? = null
    private lateinit var list: ListView
    private var controller: CountriesController? = null
    private var retryButton: Button? = null
    private var progress: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvc)

        controller = CountriesController(this)

        list = findViewById(R.id.list)
        retryButton = findViewById(R.id.retryButton)
        progress = findViewById(R.id.progress)
        adapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)

        list.setAdapter(adapter)
        list.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "You clicked " + listValues.get(position), Toast.LENGTH_SHORT).show()}
    }

    fun setValues(values: List<String>) {
        listValues.clear()
        listValues.addAll(values)
        retryButton?.visibility = View.GONE
        progress?.visibility = View.GONE
        list.visibility = View.VISIBLE
        adapter?.notifyDataSetChanged()
    }

    fun onRetry(view: View?) {
        controller?.onRefresh()
        list.visibility = View.GONE
        retryButton?.visibility = View.GONE
        progress?.visibility = View.VISIBLE
    }

    fun onError() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        progress?.visibility = View.GONE
        list.visibility = View.GONE
        retryButton?.visibility = View.VISIBLE
    }

    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, MVCActivity::class.java)
        }
    }
}