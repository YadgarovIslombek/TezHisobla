package com.example.tezhisobla.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tezhisobla.R
import com.example.tezhisobla.databinding.FragmentGameBinding
import com.example.tezhisobla.domain.entity.GameResult
import com.example.tezhisobla.domain.entity.GameSetting
import com.example.tezhisobla.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var level: Level
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("binding not init")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvOption1.setOnClickListener {
                launchGameEndFragment(GameResult(true, 10, 10, GameSetting(0, 0, 0, 0)))
            }
        }
    }

    private fun launchGameEndFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.container_view, GameEndFrament.newInstance(gameResult)
        ).addToBackStack(null).commit()
    }

    private fun parseArgs() {
        level = requireArguments().getSerializable(KEY_OBJ) as Level
    }

    companion object {
        const val KEY_OBJ = "key"
        const val GAME_NAME = "GameFragment"

        @JvmStatic
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_OBJ, level)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}