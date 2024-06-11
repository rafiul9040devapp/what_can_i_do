package com.rafiul.whatcanido.ui.add_edit_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.rafiul.whatcanido.EventObserver
import com.rafiul.whatcanido.R
import com.rafiul.whatcanido.databinding.FragmentAddEditTaskBinding
import com.rafiul.whatcanido.utils.showNumberOfCharacters
import com.rafiul.whatcanido.utils.showSnackBar


class AddEditTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddEditTaskBinding
    private val args by navArgs<AddEditTaskFragmentArgs>()
    private val viewModel by viewModels<AddEditTaskViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentAddEditTaskBinding?>(
            layoutInflater,
            R.layout.fragment_add_edit_task,
            container,
            false
        ).apply {
            viewmodel = viewModel
        }
        binding.lifecycleOwner = this.viewLifecycleOwner

        settingUpObserverForEditTask()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindSnackBar(view)
        bindWithUI()
        setUpNavigation()
    }

    private fun settingUpObserverForEditTask() {
        viewModel.getTaskById(args.taskId)?.let { taskObserver ->
            taskObserver.observe(viewLifecycleOwner) { task ->
                task?.let {
                    viewModel.title.postValue(it.title)
                    viewModel.description.postValue(it.description)
                }
            }
        }
    }

    private fun setUpNavigation() {
        viewModel.operation.observe(viewLifecycleOwner, EventObserver {
            navigateToTaskScreen()
        })
    }

    private fun navigateToTaskScreen() {
        findNavController().navigate(R.id.action_addEditTaskFragment_to_taskFragment)
    }

    private fun bindWithUI() {
        binding.titleCharacterTv.showNumberOfCharacters(
            lifecycleOwner = viewLifecycleOwner,
            viewModel.title
        )

        binding.descriptionCharacterTv.showNumberOfCharacters(
            lifecycleOwner = viewLifecycleOwner,
            viewModel.description
        )
    }

    private fun bindSnackBar(view: View) {
        view.showSnackBar(
            lifecycleOwner = viewLifecycleOwner,
            viewModel.snackBarMessage,
            Snackbar.LENGTH_LONG
        )
    }
}