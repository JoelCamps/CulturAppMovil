package com.example.culturapp.fragments

import Users
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.adapters.BookingAdapter
import com.example.culturapp.api.calls.BookingsCall
import com.example.culturapp.clases.Bookings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservasFragment : Fragment() {

    private lateinit var user: Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable("userlogin") as Users
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
                             ): View? {
        return inflater.inflate(R.layout.fragment_reservas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvReserva = view.findViewById<RecyclerView>(R.id.RVReserva)

        CoroutineScope(Dispatchers.IO). launch {
            try {
                val bookings: List<Bookings> = BookingsCall().getBookingUser(user.id)

                withContext(Dispatchers.Main) {
                    rvReserva.layoutManager = LinearLayoutManager(requireContext())
                    val adapter = BookingAdapter(bookings, object : BookingAdapter.OnItemClickListener {
                        override fun onItemClick(booking: Bookings) {
                            val cancelarReservaFragment = CancelarReservaFragment()

                            val bundle = Bundle()
                            bundle.putSerializable("booking", booking)

                            cancelarReservaFragment.arguments = bundle

                            val transaction = requireActivity().supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, cancelarReservaFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                    })

                    rvReserva.adapter = adapter
                }
            }
            catch (e: Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error al mostrar las reservas", Toast.LENGTH_SHORT).show() //guardar texto
                }
            }
        }
    }

    companion object {
        fun newInstance(users: Users): ReservasFragment {
            val fragment = ReservasFragment()
            val args = Bundle()
            args.putSerializable("userlogin", users)
            fragment.arguments = args
            return fragment
        }
    }
}