package net.heedstudio.repolist.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import net.heedstudio.repolist.R
import net.heedstudio.repolist.core.createDialog
import net.heedstudio.repolist.core.createProgressDialog
import net.heedstudio.repolist.core.hideSoftKeyboard
import net.heedstudio.repolist.databinding.ActivityMainBinding
import net.heedstudio.repolist.databinding.FragmentFindRepoBinding
import net.heedstudio.repolist.databinding.FragmentMyRepoBinding
import net.heedstudio.repolist.presentation.MainViewModel
import net.heedstudio.repolist.ui.RepoListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchRepoFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { RepoListAdapter() }
    private lateinit var binding: FragmentFindRepoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFindRepoBinding.inflate(layoutInflater, container, false)

        val dialog = binding.root.context.createProgressDialog()

        binding.rvSearchRepos.adapter = adapter
        binding.svSearch.setOnQueryTextListener(this)

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
        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getRepoList(it) }
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG, "onQueryTextChange: $newText")
        return false
    }

    companion object {
        private const val TAG = "TAG"
    }

}