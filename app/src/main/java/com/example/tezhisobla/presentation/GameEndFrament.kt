package com.example.tezhisobla.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.tezhisobla.R
import com.example.tezhisobla.domain.entity.GameResult
import com.example.tezhisobla.presentation.ChooseLevelFragment.Companion.LEVEL_NAME
import com.example.tezhisobla.presentation.GameFragment.Companion.GAME_NAME


class GameEndFrament : Fragment() {
    private lateinit var gameResult: GameResult


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_end_frament, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retry()
    }
    private fun retry(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack(GAME_NAME,FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }

        })
    }

    private fun parseArgs(){
        gameResult = requireArguments().getSerializable(KEY_RESULT) as GameResult
    }
    companion object {
        private const val KEY_RESULT = "result"
        @JvmStatic
        fun newInstance(gameResult: GameResult) =
            GameEndFrament().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_RESULT,gameResult)
                }
            }
    }
}