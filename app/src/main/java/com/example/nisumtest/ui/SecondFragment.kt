package com.example.nisumtest.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nisumtest.R
import com.example.nisumtest.core.*
import com.example.nisumtest.core.util.DataState
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.relation.AbbreviationWithLf
import com.example.nisumtest.databinding.FragmentSecondBinding
import com.example.nisumtest.ui.adapter.AdapterAbbreviation
import com.example.nisumtest.ui.state.FirstStateEvent
import com.example.nisumtest.ui.state.MainEventState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable

@AndroidEntryPoint
class SecondFragment : Fragment(), AdapterAbbreviation.Interaction {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "SecondFragment"
    }

    private val viewModel: MainViewModel by activityViewModels()
    private val abbAdapter = AdapterAbbreviation(this)
    private lateinit var uiController: UIController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let {
            if (activity is MainActivity) {
                try {
                    uiController = context as UIController
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private val handler: Handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        query?.let {
            if (it.isNotBlank()) {
                binding.layoutEmptyList.hide()
                viewModel.setStateEvent(FirstStateEvent.FindAbbreviationsEvent(it))
                clearFocusAndHideKeyboard()
                binding.recyclerViewSearch.smoothScrollToPosition(0)
            }
        }
    }

    private val listAbb = mutableListOf<LfEntity>()

    private var query: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        disableSwipe()
        initRecyclerView()
        subscribeObservers()
        checkList()
        showEmptyResults()
        restoreQuery()

        binding.searchInput.editText?.setOnEditorActionListener { _, actionId, _ ->
            query = binding.searchInput.editText?.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    private fun clearFocusAndHideKeyboard() {
        binding.searchInput.editText?.clearFocus()
        binding.searchInput.editText?.hideKeyboard()
    }

    private fun initToolbar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(binding.searchToolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    private fun disableSwipe() {
        binding.swipeRefresh.isEnabled = false
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { viewState ->
            if (viewState != null) {
                viewState.searchFragment.listAbbreviations?.let {
                    if (it.isEmpty()) {
                        emptyState()
                    } else {
                        initRecyclerView()
                        listAbb.clear()
                        listAbb.addAll(it)
                        abbAdapter.submitList(it)
                        checkList()
                    }
                }
            }
        }

        viewModel.loadingState.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
            checkList()
        }

        viewModel.errorState.observe(viewLifecycleOwner) {
            cleanAndEmptyResult()
            uiController.onError(
                errorState = it,
                errorStateCallback = object : ErrorStateCallback {
                    override fun removeErrorFromStack() {
                    }
                }
            )
            binding.tvBoldInfo.text = getString(R.string.text_no_network)
            binding.img.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_no_network
                )
            )
            binding.tvRegularInfo.hide()
        }
    }

    private fun emptyState() {
        binding.layoutEmptyList.show()
        binding.tvRegularInfo.show()
        binding.tvBoldInfo.text = getString(R.string.text_find)
        binding.img.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_ghost
            )
        )
        cleanAndEmptyResult()
    }

    private fun cleanAndEmptyResult() {
        binding.swipeRefresh.isRefreshing = false
        listAbb.clear()
        abbAdapter.submitList(listAbb)
        showEmptyResults()
    }

    private fun checkList() {
        if (listAbb.isEmpty())
            binding.layoutEmptyList.show()
        else
            binding.layoutEmptyList.hide()
    }

    private fun showEmptyResults() {
        binding.layoutEmptyList.show()
        binding.tvBoldInfo.text = getString(R.string.text_empty_results)
        binding.tvRegularInfo.text = getString(R.string.text_find_other)
    }

    private fun initRecyclerView() {
        binding.recyclerViewSearch.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(TopSpacingItemDecoration(20))
            adapter = abbAdapter
        }
    }

    private fun restoreQuery() {
        query?.let {
            binding.searchValue = query
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
            R.id.action_main -> {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        restoreQuery()
    }

    override fun onStop() {
        super.onStop()
        clearFocusAndHideKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(id: Long) {
        viewModel.setStateEvent(FirstStateEvent.DetailAbbreviations(id))
        findNavController().navigate(R.id.action_abbreviationSearchFragment_to_abbreviationDetailFragment)
    }

}