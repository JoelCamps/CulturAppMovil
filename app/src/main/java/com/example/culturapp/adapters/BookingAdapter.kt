package com.example.culturapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.clases.Bookings
import com.example.culturapp.clases.Events
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class BookingAdapter(
    private val context: Context,
    private var bookings: List<Bookings>,
    private val listener: OnItemClickListener,
                    ) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(booking: Bookings)
    }

    class BookingViewHolder(view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreEvento)
        val txtFecha: TextView = view.findViewById(R.id.txtFechaEvento)
        val txtTipo: TextView = view.findViewById(R.id.txtTipoEvento)

        init {
            view.setOnClickListener {
                listener.onItemClick(itemView.tag as Bookings)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
        return BookingViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val inputFormat = SimpleDateFormat("MMM dd yyyy h:mma", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val formattedDate = try {
            val date = inputFormat.parse(bookings[position].events?.start_datetime)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            Toast.makeText(context, context.getString(R.string.errorFecha), Toast.LENGTH_SHORT).show()
        }

        val booking = bookings[position]
        holder.txtNombre.text = booking.events?.title
        holder.txtFecha.text = formattedDate.toString()
        holder.txtTipo.text = booking.events?.type_event?.name

        holder.itemView.tag = booking
    }

    override fun getItemCount() = bookings.size

    fun updateList(newList: List<Bookings>) {
        bookings = newList.toMutableList()
        notifyDataSetChanged()
    }
}

