package com.teampophory.pophory

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.bottomnavigation.SpaceItem
import com.teampophory.pophory.databinding.ActivitySampleBinding

class SampleActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivitySampleBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bnv.apply {
            initWithSaveInstanceState(savedInstanceState)
            addSpaceItem(SpaceItem("HOME", R.drawable.ic_home))
            addSpaceItem(SpaceItem("HEXAGON", R.drawable.ic_hexagon))
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        binding.bnv.onSaveInstanceState(outState)
    }
}