package com.roaim.kotlinpinboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_pin_board.*

class PinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_board)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            findNavController(R.id.fragmentNavHost).apply {
                navigate(
                    when (currentDestination?.id) {
                        R.id.pinBoardFragment2 -> R.id.action_pinBoardFragment2_to_testActivity2
                        R.id.pinDetailsFragment -> R.id.action_pinDetailsFragment2_to_testActivity2
                        else -> {
                            R.id.action_pinBoardFragment2_to_testActivity2
                        }
                    }
                )
            }
        }
    }

}
