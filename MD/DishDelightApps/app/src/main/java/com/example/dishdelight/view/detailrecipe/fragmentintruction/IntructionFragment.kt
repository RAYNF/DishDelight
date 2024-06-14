package com.example.dishdelight.view.detailrecipe.fragmentintruction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentIntructionBinding
import com.example.dishdelight.data.dataclass.DataClassRecipeIntructionFragmentDashboard
import com.example.dishdelight.Adapter.AdapterDetaiIIntructionRecipeFragmentIntruction

class IntructionFragment : Fragment() {

    private var _binding:FragmentIntructionBinding? = null

    private val listIntructionFood = ArrayList<DataClassRecipeIntructionFragmentDashboard>()

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntructionBinding.inflate(inflater,container,false)
        val root: View = binding.root

        //rvInstruction
        binding.rvInstruction.setHasFixedSize(true)
        listIntructionFood.addAll(getInstruction())
        showRecylerIntructionFood()

        //ratingBintang
        binding.submitRating.setOnClickListener {
            val rating = binding.ratingBar.rating
            Toast.makeText(requireActivity(), "Rating yang dipilih: $rating", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private fun getInstruction(): ArrayList<DataClassRecipeIntructionFragmentDashboard> {
        val dataName = resources.getStringArray(R.array.foodInstruction)
        val dataPrice = resources.getStringArray(R.array.foodInstructionStep)

        val listFood = ArrayList<DataClassRecipeIntructionFragmentDashboard>()
        for (i in dataName.indices) {
            val food = DataClassRecipeIntructionFragmentDashboard( dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("Instruction", "instruction list size: ${listFood.size}")
        return listFood
    }

    private fun showRecylerIntructionFood() {
        binding.rvInstruction.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val foodAdapter = AdapterDetaiIIntructionRecipeFragmentIntruction(listIntructionFood)
        Log.d("instruction", "intruction listIntructionFood list size: ${listIntructionFood.size}")
        binding.rvInstruction.adapter = foodAdapter
    }

}