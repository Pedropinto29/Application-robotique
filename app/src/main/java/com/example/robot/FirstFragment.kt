package com.example.robot

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.robot.databinding.FragmentFirstBinding
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val easy = 8
            Log.d("LEVEL:", "Easy")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            (activity as MainActivity).sendData(easy)
        }

        binding.buttonFirst2.setOnClickListener {
            val hard = 9

            Log.d("LEVEL:", "Hard")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            (activity as MainActivity).sendData(hard)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}