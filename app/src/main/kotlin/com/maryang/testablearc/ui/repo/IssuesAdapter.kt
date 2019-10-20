package com.maryang.testablearc.ui.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maryang.testablearc.R
import com.maryang.testablearc.entity.Issue
import kotlinx.android.synthetic.main.item_issue.view.*
import org.jetbrains.anko.sdk21.listeners.onClick

class IssuesAdapter : RecyclerView.Adapter<IssueViewHolder>() {
    var items: MutableList<Issue> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItem(item: Issue) {
        items.add(0, item)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder =
        IssueViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_issue, parent, false
            )
        )

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}


class IssueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(issue: Issue) {
        with(itemView) {
            val number = "#${issue.number}"
            issueNumber.text = number
            issueTitle.text = issue.title
            onClick { }
        }
    }
}
