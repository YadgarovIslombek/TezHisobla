package com.example.tezhisobla.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tezhisobla.R
import com.example.tezhisobla.databinding.FragmentGameBinding
import com.example.tezhisobla.domain.entity.GameResult
import com.example.tezhisobla.domain.entity.Level

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()
    private val gameViewModel:GameViewModel by lazy {
        ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("binding not init")

    private val tvVariants by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
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
        observeViewModel()
        chooseAnswer()
        gameViewModel.startGame(args.level)

    }

    private fun chooseAnswer() {
        for (tt in tvVariants) {
            tt.setOnClickListener {
                gameViewModel.chooseAnswer(tt.text.toString().toInt())

            }
        }
    }


    private fun observeViewModel() {
        gameViewModel.question.observe(viewLifecycleOwner) {
            binding.txtYigindi.text = it.yigindi.toString()
            binding.txtLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvVariants.size){
                tvVariants[i].text = it.opitions[i].toString()
            }

        }
        gameViewModel.percentRightOfAnswer.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it,true)
        }
        gameViewModel.enoughCountRightOfAnswer.observe(viewLifecycleOwner){
            val colorResId = if (it){
                android.R.color.holo_green_light
            }else{
                android.R.color.holo_red_light
            }
            val color  = ContextCompat.getColor(requireActivity(),colorResId)
            binding.tvAnswersProgress.setTextColor(color)
        }
        gameViewModel.progressAnswer.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }
        gameViewModel.enoughPercentOfAnswer.observe(viewLifecycleOwner){
            val colorResId = if (it){
                android.R.color.holo_green_light
            }else{
                android.R.color.holo_red_light
            }
            val color  = ContextCompat.getColor(requireContext(),colorResId)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        gameViewModel.formattedTime.observe(viewLifecycleOwner){
            binding.txtTimer.text = it
        }
        gameViewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
        gameViewModel.gameResult.observe(viewLifecycleOwner){
            launchGameEndFragment(it)
        }

    }

    private fun launchGameEndFragment(gameResult: GameResult) {
        val args = Bundle().apply {
            putSerializable(GameEndFrament.KEY_RESULT,gameResult)
        }
       findNavController().navigate(R.id.action_gameFragment_to_gameEndFrament,args)
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