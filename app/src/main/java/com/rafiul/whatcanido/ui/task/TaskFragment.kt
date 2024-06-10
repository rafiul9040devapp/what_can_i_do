package com.rafiul.whatcanido.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        setUpTaskAdapter()
    }

    private fun setUpTaskAdapter() {
        binding.viewmodel?.let {
            adapter = TaskAdapter(viewModel = it)
            binding.recyclerView.adapter = adapter
        }
    }

     fun navigateToAddTaskScreen(){
        findNavController().navigate(R.id.action_taskFragment_to_addEditTaskFragment)
    }

}