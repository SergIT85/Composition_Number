package ru.androideducation.compositionnumber.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import ru.androideducation.compositionnumber.R
import ru.androideducation.compositionnumber.databinding.FragmentGameFinishedBinding
import ru.androideducation.compositionnumber.domain.entity.GameResult
import ru.androideducation.compositionnumber.domain.entity.Level


class GameFinishedFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameFinishedViewModel::class.java]
    }

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding
            ?: throw RuntimeException("FragmentGameFinishedBinding = null (MY EXCEPTION!!!!!!!!!!)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgsGameResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }

        viewModel.getGameResult(this.gameResult)
        viewModel.getAllGameResult()
        observeViewModel()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callback
        )
        binding.buttonRetry.setOnClickListener() {
            retryGame()
        }
    }

    private fun observeViewModel() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            setSmile(it.winner)
        }
        viewModel.needCountRightAnswers.observe(viewLifecycleOwner) {
            binding.tvRequiredAnswers.text = it
        }
        viewModel.yourScore.observe(viewLifecycleOwner) {
            binding.tvScoreAnswers.text = it
        }
        viewModel.needPercentRightAnswers.observe(viewLifecycleOwner) {
            binding.tvRequiredPercentage.text = it
        }
        viewModel.percentRightAnswers.observe(viewLifecycleOwner) {
            binding.tvScorePercentage.text = it
        }
    }

    private fun setSmile(resultGame:Boolean) {
        if (resultGame) {
            binding.emojiResult.setImageResource(R.drawable.ic_smile)
        } else {
            binding.emojiResult.setImageResource(R.drawable.ic_sad)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun parseArgsGameResult() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}