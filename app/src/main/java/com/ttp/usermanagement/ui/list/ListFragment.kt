package com.ttp.usermanagement.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.ttp.usermanagement.R
import com.ttp.usermanagement.base.view.BaseFragment
import com.ttp.usermanagement.common.custom.view.SpinnerCustom
import com.ttp.usermanagement.common.custom.view.showAutoDismissDialog
import com.ttp.usermanagement.common.exstension.showSnackBar
import com.ttp.usermanagement.common.util.Constants.SUCCESS
import com.ttp.usermanagement.data.model.Role
import com.ttp.usermanagement.data.model.UserInfo
import com.ttp.usermanagement.databinding.FragmentListBinding
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.ui.list.adapter.UserListAdapter
import com.ttp.usermanagement.ui.list.dialog.DialogDelete
import com.ttp.usermanagement.ui.list.dialog.DialogDetail
import com.ttp.usermanagement.ui.list.viewModel.ListViewModel
import com.ttp.usermanagement.ui.update.UpdateActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class ListFragment :
    BaseFragment<FragmentListBinding>(FragmentListBinding::inflate), View.OnClickListener {

    private val viewModel: ListViewModel by viewModels()

    private var selectedRoleId = -1
    private var roleList: List<Role> = emptyList()
    private var userList: List<UserInfo?> = emptyList()

    private lateinit var userAdapter: UserListAdapter

    private lateinit var edFamilyName: EditText
    private lateinit var edFirstName: EditText

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("roleList", ArrayList(roleList))

        outState.putString("familyName", edFamilyName.text.toString())
        outState.putString("firstName", edFirstName.text.toString())
        outState.putInt("selectedRoleId", selectedRoleId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingFactory.invoke(layoutInflater)
        if (savedInstanceState != null) {
            val savedFamilyName = savedInstanceState.getString("familyName", "")
            val savedFirstName = savedInstanceState.getString("firstName", "")
            selectedRoleId = savedInstanceState.getInt("selectedRoleId", -1)

            binding.edFamilyName.editText!!.setText(savedFamilyName)
            binding.edFirstName.editText!!.setText(savedFirstName)
            // Khôi phục trạng thái của Spinner
            roleList = savedInstanceState.getParcelableArrayList("roleList") ?: emptyList()
            setupRoleSpinner(roleList)
        }

        return binding.root
    }

    override fun setupData() {
        initView()
        initData()
        setupSwipeToRefresh()
    }

    override fun setupObserver() {
        viewModel.roleState.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    roleList = resource.result
                    setupRoleSpinner(roleList)
                }

                else -> {}
            }
        }
        viewModel.userState.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    userList = resource.result
                    userAdapter.updateList(userList)
                    binding.userList = userList
                }

                else -> {}
            }
        }

        viewModel.deleteState.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    when (resource.result) {
                        1 -> { //data = 1 là thành công SUCCESS
                            binding.root.showSnackBar(SUCCESS)
                            viewModel.getUser(
                                edFamilyName.text.toString(),
                                edFirstName.text.toString(),
                                selectedRoleId
                            )
                        }

                        0 -> { //data = 0 là lỗi DELETE_ERR
                            mContext.showAutoDismissDialog(
                                "Đã xảy ra lỗi trong quá trình xóa. \n Vui lòng thử lại sau",
                                3000
                            )
                            viewModel.deleteUser(null)
                            viewModel.getUser(
                                edFamilyName.text.toString(),
                                edFirstName.text.toString(),
                                selectedRoleId
                            )
                        }

                        -1 -> { //data = -1 là trùng User INVALID_USER
                            mContext.showAutoDismissDialog(
                                "Tài khoản đang đăng nhập, hiện không được xóa \n Vui lòng thử lại tài khoản khác",
                                3000
                            )
                            viewModel.deleteUser(null)
                        }
                    }

                }

                else -> {}
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.imvSearch -> {
                viewModel.getUser(
                    edFamilyName.text.toString(),
                    edFirstName.text.toString(),
                    selectedRoleId
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser(
            edFamilyName.text.toString(),
            edFirstName.text.toString(),
            selectedRoleId
        )
    }

    private fun initView() {
        edFamilyName = binding.edFamilyName.editText!!
        edFirstName = binding.edFirstName.editText!!
    }

    private fun initData() {
        if (roleList.isEmpty()) {
            viewModel.getRoles()
        }

        viewModel.getUser(
            edFamilyName.text.toString(),
            edFirstName.text.toString(),
            selectedRoleId
        )

        userAdapter = UserListAdapter(userList) { userInfo ->
            DialogDetail(mContext, userInfo) { result ->
                detailDialogCallBack(result, userInfo)
            }.show()
        }

        binding.rcvList.adapter = userAdapter

        binding.imvSearch.setOnClickListener(this)
    }

    private fun setupRoleSpinner(roles: List<Role>) {
        val placeholder = getString(R.string.select_role)
        val roleNames = listOf(placeholder) + roles.map { it.authorityName }
        val roleAdapter = SpinnerCustom(mContext, roleNames)
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spnRole.adapter = roleAdapter

        binding.spnRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedRoleId = if (position == 0) {
                    -1
                } else {
                    roles[position - 1].authorityId
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        val rolePosition = roles.indexOfFirst { role -> role.authorityId == selectedRoleId }
        if (rolePosition != -1) {
            binding.spnRole.setSelection(rolePosition + 1)
        }
    }

    private fun setupSwipeToRefresh() {
        val swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refreshData() {
        viewModel.getRoles()
        viewModel.getUser(edFamilyName.text.toString(), edFirstName.text.toString(), selectedRoleId)
    }

    private fun detailDialogCallBack(result: Int, userInfo: UserInfo) {
        when (result) {
            0 -> {
                DialogDelete(mContext, userInfo) {
                    if (it == 0) {
                        viewModel.deleteUser(userInfo.userId)
                    }
                }.show()
            }

            1 -> {
                val userInfoJson = Json.encodeToString(UserInfo.serializer(), userInfo)
                val intent = Intent(mContext, UpdateActivity::class.java)
                intent.putExtra("userInfoJson", userInfoJson)
                mContext.startActivity(intent)
            }
        }

    }
}