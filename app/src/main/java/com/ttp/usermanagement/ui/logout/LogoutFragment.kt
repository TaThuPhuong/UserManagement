package com.ttp.usermanagement.ui.logout

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ttp.usermanagement.R
import com.ttp.usermanagement.base.view.BaseFragment
import com.ttp.usermanagement.common.custom.view.DialogConfirm
import com.ttp.usermanagement.databinding.FragmentLogoutBinding
import com.ttp.usermanagement.ui.login.LoginActivity
import com.ttp.usermanagement.ui.main.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : BaseFragment<FragmentLogoutBinding>(FragmentLogoutBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    override fun setupData() {
        DialogConfirm(mContext, getString(R.string.log_out_confirm)) { result ->
            when (result) {
                1 -> {
                    viewModel.logOut(mContext)
                    val intent = Intent(mContext, LoginActivity::class.java)
                    mContext.startActivity(intent)
                    requireActivity().finish()
                }

                0 -> {
                    findNavController().popBackStack()
                }
            }
        }.show()
    }

    override fun setupObserver() {

    }

}