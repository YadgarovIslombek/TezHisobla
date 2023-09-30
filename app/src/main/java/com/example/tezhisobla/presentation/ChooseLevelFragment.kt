package com.example.tezhisobla.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tezhisobla.R
import com.example.tezhisobla.databinding.FragmentChooseLevelBinding
import com.example.tezhisobla.databinding.FragmentWelcomeBinding
import com.example.tezhisobla.domain.entity.Level


class ChooseLevelFragment : Fragment() {
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("binding not init $binding")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            btnLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            btnLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            btnLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }

        }
    }

    private fun launchGameFragment(level: Level){
        findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level))
    }


    companion object {
        const val LEVEL_NAME = "ChooseLevelFragment"
        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}