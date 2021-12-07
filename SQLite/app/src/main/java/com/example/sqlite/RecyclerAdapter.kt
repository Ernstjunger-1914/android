package com.example.sqlite

import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper: SqliteHelper? = null
    val listData = mutableListOf<Memo>()

    override fun onBindViewHolder(holder: RecyclerAdapter.Holder, position: Int) {
        val memo = listData.get(position)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var mMemo: Memo? = null

        init {
            binding.delete.setOnClickListener {
                helper?.deleteMemo(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
        }

        fun setMemo(memo: Memo) {
        }
    }
}