package com.devtides.androidarchitectures

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.devtides.androidarchitectures.mvc.MVCActivity
import com.devtides.androidarchitectures.mvp.MVPActivity
import com.devtides.androidarchitectures.mvvm.MVVMActivity

class ArchitecturesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_architectures)
    }

    fun onMVC(view: View) {
        startActivity(MVCActivity.getIntent(this))
    }

    fun onMVP(view: View) {
        startActivity(MVPActivity.getIntent(this))
    }

    fun onMVVM(view: View) {
        startActivity(MVVMActivity.getIntent(this))
    }
}