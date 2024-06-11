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

    }

    private fun setUpNavigation() {
        taskViewModel.openTask.observe(viewLifecycleOwner, EventObserver {
            openTaskDetails(it)
        })

        taskViewModel.editTask.observe(viewLifecycleOwner, EventObserver {
            editTask(it)
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

    private fun setUpTaskAdapter() {
        binding.viewmodel?.let {
            adapter = TaskAdapter(viewModel = it)
            binding.recyclerView.adapter = adapter
        }
    }

    fun navigateToAddTaskScreen() {
        findNavController().navigate(R.id.action_taskFragment_to_addEditTaskFragment)
    }

}