package com.example.mystudycalendar.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mystudycalendar.MemoActivity
import com.example.mystudycalendar.R
import com.example.studycalendar.model.Memo
import java.text.SimpleDateFormat
import java.util.Date

class MemoAdapter (content: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val array = ArrayList<Memo>()
    private val content = content

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(content).inflate(R.layout.item_memo, parent, false)
        return HolderView(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as HolderView
        val item = array.get(position)
        holder.text_memo.setText(item.memo)

        val date = Date()
        val datetime = SimpleDateFormat("yyyy/MM/dd")
        date.time = item.datetime
        holder.text_date.setText(datetime.format(date))


        holder.itemView.setOnClickListener{
            val intent = Intent(content, MemoActivity::class.java)
            intent.putExtra("item", item)
            content.startActivity(intent)
        }
    }

    fun setList(array:ArrayList<Memo>){
        this.array.clear()
        this.array.addAll(array)
        notifyDataSetChanged()
    }

    class HolderView(view: View) : RecyclerView.ViewHolder(view){
        val text_memo = view.findViewById<TextView>(R.id.text_memo)
        val text_date = view.findViewById<TextView>(R.id.text_date)
    }
}