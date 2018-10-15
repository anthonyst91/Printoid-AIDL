package fr.printoid.phones.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.printoid.phones.R
import fr.printoid.phones.ServerModel
import fr.printoid.phones.adapter.holder.ServerViewHolder
import fr.printoid.phones.adapter.listener.OnServerClickedListener

class ServerAdapter constructor(private var listener: OnServerClickedListener,
                                var items: MutableList<ServerModel> = mutableListOf()):
        RecyclerView.Adapter<ServerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ServerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_profile, parent, false)
        return ServerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener { listener.onServerClicked(items[position]) }
    }

    override fun getItemCount(): Int = items.size

}