package fr.printoid.phones.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.printoid.phones.R
import fr.printoid.phones.ServerModel
import fr.printoid.phones.adapter.holder.ServerViewHolder

class ServerAdapter constructor(var items: MutableList<ServerModel> = mutableListOf()):
        RecyclerView.Adapter<ServerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ServerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_profile, parent, false)
        return ServerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

}