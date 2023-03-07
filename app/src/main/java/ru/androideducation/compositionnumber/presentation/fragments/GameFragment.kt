package ru.androideducation.compositionnumber.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.androideducation.compositionnumber.R
import ru.androideducation.compositionnumber.databinding.FragmentGameBinding
import ru.androideducation.compositionnumber.domain.entity.GameResult
import ru.androideducation.compositionnumber.domain.entity.GameSettings
import ru.androideducation.compositionnumber.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException(
            "FragmentGameBinding = null (MY EXCEPTION!!!!!!!!!!)"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameSettings = GameSettings(1,1,1,1)
        val gameResult = GameResult(true,50, 10, gameSettings)
        binding.tvOption1.setOnClickListener {
            lunchResultFragment(gameResult)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parsArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    private fun lunchResultFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }
    companion object {
        const val NAME = "GameFragment"

        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {

            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}