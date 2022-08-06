package net.heedstudio.repolist.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import net.heedstudio.repolist.R
import net.heedstudio.repolist.core.createDialog
import net.heedstudio.repolist.core.createProgressDialog
import net.heedstudio.repolist.databinding.FragmentMyRepoBinding
import net.heedstudio.repolist.presentation.MainViewModel
import net.heedstudio.repolist.ui.RepoListAdapter
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyRepoFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { RepoListAdapter() }
    private lateinit var binding: FragmentMyRepoBinding
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var username: String
    private val prefs: String = "prefUserName"
    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMyRepoBinding.inflate(layoutInflater, container, false)

        dialog = binding.root.context.createProgressDialog()

        binding.rvOwnRepos.adapter = adapter

        viewModel.getRepoList(getString(R.string.my_own_repository))
        setRepoList()

        return binding.root
    }

    private fun setRepoList() {
        viewModel.repos.observe(requireActivity()) {
            when (it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    binding.root.context.createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
    }
}