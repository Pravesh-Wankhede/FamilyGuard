package com.example.familyprotector.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.familyprotector.R

class MemberAdapter(private val listMember: List<MemberModel>) : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberAdapter.ViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_member,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: MemberAdapter.ViewHolder, position: Int) {
        val item = listMember[position]
        holder.nameUser.text = item.name
        holder.address.text = item.address
        holder.battery.text = item.battery
        holder.distance.text = item.distance

    }

    override fun getItemCount(): Int {
        return listMember.size
    }

    class ViewHolder( private val item: View) : RecyclerView.ViewHolder(item){

        val imageUser = item.findViewById<ImageView>(R.id.guard_img)
        val nameUser = item.findViewById<TextView>(R.id.text_userprofile)
        val address = itemView.findViewById<TextView>(R.id.user_address)
        val battery = itemView.findViewById<TextView>(R.id.txt_battery)
        val distance = itemView.findViewById<TextView>(R.id.txt_distance)
    }
}