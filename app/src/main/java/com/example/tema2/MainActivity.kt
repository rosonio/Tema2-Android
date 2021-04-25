package com.example.tema2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.homework2.R
import com.example.tema2.fragments.Fragment1
import com.example.tema2.fragments.Fragment2
import com.example.tema2.fragments.Fragment3
import com.example.tema2.interfaces.ActivityFragmentCommunication
import com.example.tema2.models.Album
import com.example.tema2.models.User

class MainActivity : AppCompatActivity(), ActivityFragmentCommunication{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment1()
    }

    private fun addFragment1() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = Fragment1::class.java.name
        val addTransaction = transaction.add(
            R.id.frame_layout, Fragment1.newInstance("",""), tag
        )
        addTransaction.commit()
    }

    override fun addFragment2(user: User) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = Fragment2::class.java.name
        val replaceTransaction = transaction.replace(
            R.id.frame_layout, Fragment2.newInstance("","", user), tag
        )
        replaceTransaction.addToBackStack(tag)
        replaceTransaction.commit()
    }

    override fun addFragment3(album: Album) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = Fragment3::class.java.name
        val replaceTransaction = transaction.replace(
            R.id.frame_layout, Fragment3.newInstance("","", album), tag
        )
        replaceTransaction.addToBackStack(tag)
        replaceTransaction.commit()
    }

    /*fun addFragment2(view: View) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = Fragment2::class.java.name
        val addTransaction = transaction.add(
            R.id.frame_layout, Fragment2.newInstance("",""), tag
        )
        addTransaction.commit()
    }*/
}