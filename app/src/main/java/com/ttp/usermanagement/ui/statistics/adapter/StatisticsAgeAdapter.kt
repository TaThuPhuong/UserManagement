package com.ttp.usermanagement.ui.statistics.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ttp.usermanagement.data.model.StatisticItem
import com.ttp.usermanagement.databinding.ItemStatisticsAgeBinding

class StatisticsAgeAdapter(private val statisticItems: List<StatisticItem>) :
    RecyclerView.Adapter<StatisticsAgeAdapter.StatisticsAgeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsAgeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatisticsAgeBinding.inflate(inflater, parent, false)
        return StatisticsAgeViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatisticsAgeViewHolder, position: Int) {
        val item = statisticItems[position]
        holder.bind(item)
        holder.noUser.text = (position + 1).toString()
    }

    override fun getItemCount(): Int = statisticItems.size

    inner class StatisticsAgeViewHolder(private val binding: ItemStatisticsAgeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var noUser = binding.tvNo

        fun bind(statisticItem: StatisticItem) {
            binding.statisticItem = statisticItem
            binding.executePendingBindings()
        }
    }
}