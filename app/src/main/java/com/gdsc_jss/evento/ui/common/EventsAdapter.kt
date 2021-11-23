package com.gdsc_jss.evento.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdsc_jss.evento.databinding.ItemEventCardBinding
import com.gdsc_jss.evento.network.models.EventResponse
import com.gdsc_jss.evento.util.formatTo
import com.gdsc_jss.evento.util.toDate
import com.gdsc_jss.evento.util.toDp

class EventsAdapter(val list: ArrayList<EventResponse>) : RecyclerView.Adapter<EventsAdapter.VH>() {
    class VH(val binding: ItemEventCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemEventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val context = holder.binding.root.context
        if (position == this.itemCount - 1) {
            val lp = holder.binding.root.layoutParams as ViewGroup.MarginLayoutParams
            lp.setMargins(24.toDp(context), 4.toDp(context), 24.toDp(context), 86.toDp(context))
            holder.binding.root.layoutParams = lp
        }

        holder.binding.apply {
            name.text = list[position].name
            description.text = list[position].description
            societyName.text = list[position].society.name
            Glide.with(root).load(list[position].image).into(banner)
            Glide.with(root).load(list[position].society.image).into(societyDp)
            date.text = list[position].dateAndTime.toDate()?.formatTo("dd MMMM', 'YYYY")
            time.text = list[position].dateAndTime.toDate()?.formatTo("hh:mm a")
        }

    }

    override fun getItemCount(): Int = list.size
}