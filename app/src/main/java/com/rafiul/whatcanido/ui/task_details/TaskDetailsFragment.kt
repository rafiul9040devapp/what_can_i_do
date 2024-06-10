package com.rafiul.whatcanido.ui.task_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.rafiul.whatcanido.R
import com.rafiul.whatcanido.databinding.FragmentTaskDetailsBinding


class TaskDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailsBinding

    private val args: TaskDetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<TaskDetailsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_task_details, container, false)
        binding = FragmentTaskDetailsBinding.bind(view).apply {
            viewmodel = viewModel
        }
        binding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.getId(args.taskId)

        return view
    }
}