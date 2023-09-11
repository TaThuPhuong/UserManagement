package com.ttp.usermanagement.ui.statistics

import android.os.Bundle
import com.ttp.usermanagement.base.view.BaseFragment
import com.ttp.usermanagement.data.model.StatisticItem
import com.ttp.usermanagement.databinding.FragmentStatisticsGenderBinding
import com.ttp.usermanagement.ui.statistics.adapter.StatisticsGenderAdapter


class StatisticsGenderFragment :
    BaseFragment<FragmentStatisticsGenderBinding>(FragmentStatisticsGenderBinding::inflate) {

    private lateinit var statisticList: List<StatisticItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statisticList = arguments?.getParcelableArrayList(ARG_STATISTIC_LIST)
            ?: emptyList()
    }

    override fun setupData() {
        val adapter = StatisticsGenderAdapter(statisticList)
        binding.rcvSttGender.adapter = adapter
    }

    override fun setupObserver() {

    }

    companion object {
        private const val ARG_STATISTIC_LIST = "arg_statistic_list"

        fun newInstance(statisticList: List<StatisticItem>): StatisticsGenderFragment {
            val fragment = StatisticsGenderFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_STATISTIC_LIST, ArrayList(statisticList))
            fragment.arguments = args
            return fragment
        }
    }


}