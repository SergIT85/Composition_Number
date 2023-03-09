package ru.androideducation.compositionnumber.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.androideducation.compositionnumber.R
import ru.androideducation.compositionnumber.databinding.FragmentChooseLevelBinding
import ru.androideducation.compositionnumber.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException(
            "Fragment ChooseLevelFragment/StartFragment" +
                    " = null (MY EXCEPTION!!!!!!!!!!)"
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            buttonLevelTest.setOnClickListener {
                lunchGameFragment(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                lunchGameFragment(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                lunchGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                lunchGameFragment(Level.HARD)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun lunchGameFragment(level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    companion object {

        const val NAME = "ChooseLevelFragment"

        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }

}