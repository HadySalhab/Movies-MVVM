package com.android.myapplication.movies

import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

abstract class  BaseActivity : AppCompatActivity() {
    private lateinit var progressBar:ProgressBar
    override fun setContentView(layoutResID: Int) {
        val rootView = layoutInflater.inflate(R.layout.activity_base,null)
        val frameLayout = rootView.findViewById<FrameLayout>(R.id.activity_content)
        progressBar = rootView.findViewById(R.id.progress_bar)

        //will inflate the layout passed from child Activities
        //and attach it to the parent layout
        layoutInflater.inflate(layoutResID,frameLayout,true)
        super.setContentView(layoutResID)
    }

    fun showProgressBar(isVisible:Boolean){
        if(isVisible) {
            progressBar.visibility = View.GONE
        }else{
            progressBar.visibility = View.VISIBLE
        }
    }
}