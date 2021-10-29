package com.example.nisumtest.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nisumtest.R
import com.example.nisumtest.core.*
import com.example.nisumtest.core.util.DataState
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.relation.AbbreviationWithLf
import com.example.nisumtest.databinding.FragmentFirstBinding
import com.example.nisumtest.ui.adapter.AdapterRecent
import com.example.nisumtest.ui.state.FirstStateEvent
import com.example.nisumtest.ui.state.MainEventState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment(), AdapterRecent.Interaction {
    companion object {
        private const val TAG = "FirstFragment"
    }

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var uiController: UIController
    private val recentAdapter = AdapterRecent(this)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        subscribeObservers()
        viewModel.setStateEvent(FirstStateEvent.RecentAbbreviations)
    }

    private fun initToolbar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
            (activity as AppCompatActivity).title = getString(R.string.first_fragment_label)
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { viewState ->
            if (viewState != null) {
                viewState.listFragment.listRecent?.let {
                    initRecycler()
                    recentAdapter.submitList(it)
                    if (it.isEmpty())
                        makeEmpty()
                }
            }
        }

        viewModel.errorState.observe(viewLifecycleOwner) {
            makeEmpty()
            uiController.onError(
                errorState = it,
                errorStateCallback = object : ErrorStateCallback {
                    override fun removeErrorFromStack() {
                    }
                }
            )
        }

        viewModel.loadingState.observe(viewLifecycleOwner) {
            if (it)
                binding.loading.show()
            else
                binding.loading.hide()
        }
    }

    private fun initRecycler() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = recentAdapter
        }
    }

    private fun makeEmpty() {
        binding.recyclerView.hide()
        binding.empty.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_find -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetailAbbreviationInteraction(id: Long) {

    }

}