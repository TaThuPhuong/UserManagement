package com.ttp.usermanagement.ui.login

import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ttp.usermanagement.R
import com.ttp.usermanagement.base.view.BaseActivity
import com.ttp.usermanagement.common.custom.view.showAutoDismissDialog
import com.ttp.usermanagement.common.exstension.gone
import com.ttp.usermanagement.common.exstension.setErrorIfInvalid
import com.ttp.usermanagement.common.exstension.setErrorIfLengthExceeded
import com.ttp.usermanagement.common.exstension.visible
import com.ttp.usermanagement.common.util.SharedPref
import com.ttp.usermanagement.databinding.ActivityLoginBinding
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.ui.login.viewModel.LoginViewModel
import com.ttp.usermanagement.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var edUserId: EditText
    private lateinit var edPass: EditText

    override fun setupData() {
        initData()
        initView()
        initListener()
    }

    override fun setupObserver() {
        lifecycleScope.launch {
            viewModel.loginState.collectLatest { state ->
                if (state != null) {
                    when (state) {
                        is Resource.Error -> {
                            binding.progressBar.gone()
                            showAutoDismissDialog(getString(R.string.login_err))
                        }

                        is Resource.Loading -> {
                            binding.progressBar.visible()
                        }

                        is Resource.Success -> {
                            binding.progressBar.gone()
                            SharedPref.setLogin(this@LoginActivity, true)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }

                        else -> {
                            binding.progressBar.visible()
                        }
                    }
                }
            }
        }

        viewModel.sessionState.observe(this) {
            if (it) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.btnLogin -> {
                val userID = edUserId.text.toString().trim()
                val password = edPass.text.toString().trim()

                if (validate(userID, password)) {
                    viewModel.userLogin(userID, password)
                }
            }
        }
    }

    private fun initView() {
        edUserId = binding.edUserId.editText!!
        edPass = binding.edPass.editText!!
    }

    private fun initData() {
        viewModel.checkSession(this)
    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener(this)
    }

    private fun validate(userID: String, password: String): Boolean {
        val userIdError = "${getString(R.string.user_id)} ${getString(R.string.emty_err)}"
        val passError = "${getString(R.string.pass)} ${getString(R.string.emty_err)}"
        val maxLengthUserId = 8
        val maxLengthPass = 8
        binding.edPass.isPasswordVisibilityToggleEnabled = true

        if (userID.isEmpty() || userID.length > maxLengthUserId) {
            setErrorIfInvalid(edUserId, userIdError)
            setErrorIfLengthExceeded(edUserId, maxLengthUserId, getString(R.string.user_id), this)
            return false
        }

        if (password.isEmpty() || password.length > maxLengthPass) {
            setErrorIfInvalid(edPass, passError)
            binding.edPass.isPasswordVisibilityToggleEnabled = false
            setErrorIfLengthExceeded(edPass, maxLengthPass, getString(R.string.pass), this)
            return false
        }

        return true
    }
}
