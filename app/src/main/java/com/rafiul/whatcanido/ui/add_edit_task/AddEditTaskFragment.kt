package com.rafiul.whatcanido.ui.add_edit_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.rafiul.whatcanido.R
import com.rafiul.whatcanido.databinding.FragmentAddEditTaskBinding
import com.rafiul.whatcanido.utils.showSnackBar


class AddEditTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddEditTaskBinding
    private val viewModel by viewModels<AddEditTaskViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAddEditTaskBinding?>(
            layoutInflater,
            R.layout.fragment_add_edit_task,
            container,
            false
        ).apply {
            viewmodel = viewModel
        }
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindSnackBar(view)

    }

    private fun bindSnackBar(view: View) {
        view.showSnackBar(
            lifecycleOwner = viewLifecycleOwner,
            viewModel.snackBarMessage,
            Snackbar.LENGTH_LONG
        )
    }
}