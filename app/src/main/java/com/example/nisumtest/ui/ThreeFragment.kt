package com.example.nisumtest.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nisumtest.R
import com.example.nisumtest.core.UIController
import com.example.nisumtest.data.local.entity.VarEntity
import com.example.nisumtest.databinding.FragmentThreeBinding
import com.example.nisumtest.ui.adapter.AdapterDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThreeFragment : Fragment() {
    private var _binding: FragmentThreeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var uiController: UIController
    private lateinit var detailAdapter: AdapterDetail

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
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { viewState ->
            if (viewState != null) {
                viewState.detailFragment.abbDetail?.let {
                    binding.abb = it.abb.sf
                    val listVars = mutableListOf<VarEntity>()
                    it.lfs.forEach { lf ->
                        binding.setLf(lf.lf.lf)
                        binding.setFreq(lf.lf.frequency.toString())
                        binding.setSince(lf.lf.since.toString())
                        lf.vars.forEach { vars ->
                            listVars.add(vars)
                        }
                    }
                    detailAdapter = AdapterDetail(listVars)
                    initRecycler()
                }
            }
        }
    }

    private fun initRecycler() {
        binding.relatedVarsList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = detailAdapter
        }
    }

    private fun initToolbar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(binding.detailToolbar)
            (activity as AppCompatActivity).title = getString(R.string.text_title_detail)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}