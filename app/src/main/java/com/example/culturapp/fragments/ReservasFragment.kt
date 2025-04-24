package com.example.culturapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.culturapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [ReservasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReservasFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
                             ): View? {
        return inflater.inflate(R.layout.fragment_reservas, container, false)
    }
}