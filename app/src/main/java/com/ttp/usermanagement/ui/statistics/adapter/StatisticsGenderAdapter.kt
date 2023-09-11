package com.ttp.usermanagement.ui.statistics.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ttp.usermanagement.data.model.StatisticItem
import com.ttp.usermanagement.databinding.ItemStatisticsGenderBinding

class StatisticsGenderAdapter(private val statisticItems: List<StatisticItem>) :
    RecyclerView.Adapter<StatisticsGenderAdapter.StatisticsGenderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsGenderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatisticsGenderBinding.inflate(inflater, parent, false)
        return StatisticsGenderViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatisticsGenderViewHolder, position: Int) {
        val item = statisticItems[position]
        holder.bind(item)
        holder.noUser.text = (position + 1).toString()
    }

    override fun getItemCount(): Int = statisticItems.size

    inner class StatisticsGenderViewHolder(private val binding: ItemStatisticsGenderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var noUser = binding.tvNo

        fun bind(statisticItem: StatisticItem) {
            binding.statisticItem = statisticItem
            binding.executePendingBindings()
        }
    }
}