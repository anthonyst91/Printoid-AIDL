package fr.printoid.phones.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import fr.printoid.phones.ServerModel
import kotlinx.android.synthetic.main.row_profile.view.*
import java.text.SimpleDateFormat
import java.util.*

class ServerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(server: ServerModel) {
        itemView.tv_profile_name.text = server.serverName
        itemView.tv_profile_id.text = "id: ${server.serverId}"

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = server.lastConnectionTimestamp
        val date = SimpleDateFormat("dd/MM/yyyy hh:mma", Locale.getDefault()).format(calendar.time)

        itemView.tv_last_connection.text = "Last connection: $date"

    }

}