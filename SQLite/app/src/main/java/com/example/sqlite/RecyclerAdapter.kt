package com.example.sqlite

import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper: SqliteHelper? = null
    var listData = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)

        holder.setMemo(memo)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mMemo: Memo? = null

        init {
            itemView.delete.setOnClickListener {
                helper?.deleteMemo(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
        }

        fun setMemo(memo: Memo) {
            itemView.textNo.text = "${memo.no}"
            itemView.textContent.text = memo.content

            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
            itemView.textDatetime.text = "${sdf.format((memo.datetime))}"

            this.mMemo = memo
        }
    }
}