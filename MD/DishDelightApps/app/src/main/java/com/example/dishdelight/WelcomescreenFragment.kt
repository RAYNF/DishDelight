package com.example.dishdelight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dishdelight.databinding.FragmentWelcomescreenBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomescreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomescreenFragment : Fragment() {
    private var _binding :FragmentWelcomescreenBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      _binding = FragmentWelcomescreenBinding.inflate(inflater,container,false)
        val view = binding.root
        return inflater.inflate(R.layout.fragment_welcomescreen, container, false)
    }

}