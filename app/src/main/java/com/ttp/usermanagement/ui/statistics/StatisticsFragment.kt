package com.ttp.usermanagement.ui.statistics

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ttp.usermanagement.R
import com.ttp.usermanagement.base.view.BaseFragment
import com.ttp.usermanagement.data.model.StatisticItem
import com.ttp.usermanagement.databinding.FragmentStatisticsBinding
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.ui.statistics.adapter.ViewPagerAdapter
import com.ttp.usermanagement.ui.statistics.viewModel.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment :
    BaseFragment<FragmentStatisticsBinding>(FragmentStatisticsBinding::inflate) {

    private val viewModel: StatisticsViewModel by viewModels()
    private lateinit var statisticList: List<StatisticItem>
    private var currentFragmentPosition: Int = 0

    override fun setupData() {
        viewModel.getStatistics()
        setupSwipeRefresh()
    }

    override fun setupObserver() {
        viewModel.statisticsState.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val statisticsMap: Map<String, Int> = resource.result

                    statisticList =
                        statisticsMap.entries.fold(mutableMapOf<String, StatisticItem>()) { acc, entry ->
                            val (key, value) = entry
                            val parts = key.split("_") //"CEO_female"
                            val roleName = parts[0] //  "CEO"
                            val roleValue = parts.getOrNull(1) ?: "male" //  "female"

                            // Kiểm tra xem đã tồn tại StatisticItem cho roleName chưa
                            val existingItem = acc.getOrPut(roleName) {
                                StatisticItem(roleName, 0, 0, 0, 0, 0)
                            }

                            when (roleValue) {
                                "male" -> existingItem.maleCount = value
                                "female" -> existingItem.femaleCount = value
                                "age0to19" -> existingItem.age0to19Count = value
                                "age20plus" -> existingItem.age20PlusCount = value
                                "ageunknown" -> existingItem.ageUnknownCount = value
                            }
                            acc
                        }.values.toList()
                    setUpViewPager()
                }

                else -> {}
            }
        }
    }

    private fun setUpViewPager() {
        val ageFragment = StatisticsAgeFragment.newInstance(statisticList)
        val genderFragment = StatisticsGenderFragment.newInstance(statisticList)

        val viewPagerAdapter = ViewPagerAdapter(this, listOf(ageFragment, genderFragment))
        binding.viewPager.adapter = viewPagerAdapter

        binding.viewPager.setCurrentItem(currentFragmentPosition, false)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentFragmentPosition = position
            }
        })

        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> mContext.resources.getString(R.string.stt_age)
                    1 -> mContext.resources.getString(R.string.gender)
                    else -> throw IllegalArgumentException("Invalid position")
                }
            }
        tabLayoutMediator.attach()
    }

    private fun setupSwipeRefresh() {
        binding.layoutRefresh.setOnRefreshListener {
            viewModel.getStatistics()
            binding.layoutRefresh.isRefreshing = false
        }
    }

}