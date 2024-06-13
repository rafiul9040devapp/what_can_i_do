package com.rafiul.whatcanido.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rafiul.whatcanido.EventObserver
import com.rafiul.whatcanido.R
import com.rafiul.whatcanido.adapter.TaskAdapter
import com.rafiul.whatcanido.databinding.FragmentTaskBinding
import com.rafiul.whatcanido.utils.showAlertDialog
import com.rafiul.whatcanido.utils.showToast


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private val taskViewModel by viewModels<TaskViewModel>()
    private lateinit var adapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentTaskBinding>(
            layoutInflater,
            R.layout.fragment_task,
            container,
            false
        ).apply {
            viewmodel = taskViewModel
        }
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTaskAdapter()
        setUpNavigation()
        setupDeleteDialog()

    }

    private fun setUpTaskAdapter() {
        binding.viewmodel?.let {
            adapter = TaskAdapter(viewModel = it)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun setUpNavigation() {
        taskViewModel.openTask.observe(viewLifecycleOwner, EventObserver {
            openTaskDetails(it)
        })

        taskViewModel.editTask.observe(viewLifecycleOwner, EventObserver {
            editTask(it)
        })
    }

    private fun setupDeleteDialog() {
        taskViewModel.showDeleteDialogEvent.observe(viewLifecycleOwner, EventObserver {
            requireActivity().showAlertDialog(
                title = getString(R.string.delete_task),
                message = getString(R.string.do_you_want_to_delete_this_task),
                positiveButtonText = getString(R.string.delete),
                positiveAction = {
                    taskViewModel.deleteTask(it)
                    requireActivity().showToast(getString(R.string.task_is_deleted))
                },
                negativeButtonText = getString(R.string.cancel)
            )
        })
    }

    private fun openTaskDetails(taskId: Int) {
        val action = TaskFragmentDirections.actionTaskFragmentToTaskDetailsFragment(taskId)
        findNavController().navigate(action)
    }

    private fun editTask(taskId: Int) {
        val action = TaskFragmentDirections.actionTaskFragmentToAddEditTaskFragment(taskId)
        findNavController().navigate(action)
    }

    fun navigateToAddTaskScreen() {
        findNavController().navigate(R.id.action_taskFragment_to_addEditTaskFragment)
    }

}