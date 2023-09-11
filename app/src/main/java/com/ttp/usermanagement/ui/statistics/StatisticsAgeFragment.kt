package com.ttp.usermanagement.ui.statistics

import android.os.Bundle
import com.ttp.usermanagement.base.view.BaseFragment
import com.ttp.usermanagement.data.model.StatisticItem
import com.ttp.usermanagement.databinding.FragmentStatisticsAgeBinding
import com.ttp.usermanagement.ui.statistics.adapter.StatisticsAgeAdapter


class StatisticsAgeFragment :
    BaseFragment<FragmentStatisticsAgeBinding>(FragmentStatisticsAgeBinding::inflate) {

    private lateinit var statisticList: List<StatisticItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statisticList = arguments?.getParcelableArrayList(ARG_STATISTIC_LIST) ?: emptyList()
    }

    override fun setupData() {
        val adapter = StatisticsAgeAdapter(statisticList)
        binding.rcvSttAge.adapter = adapter
    }

    override fun setupObserver() {

    }

    companion object {
        private const val ARG_STATISTIC_LIST = "arg_statistic_list"

        fun newInstance(statisticList: List<StatisticItem>): StatisticsAgeFragment {
            val fragment = StatisticsAgeFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_STATISTIC_LIST, ArrayList(statisticList))
            fragment.arguments = args
            return fragment
        }
    }
}