package com.example.dishdelight.view.detailrecipe.fragmentintruction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentIntructionBinding
import com.example.dishdelight.data.dataclass.DataClassRecipeIntructionFragmentDashboard
import com.example.dishdelight.Adapter.AdapterDetaiIIntructionRecipeFragmentIntruction
import com.example.dishdelight.Adapter.AdapterPopularRecipeFragmentHome
import com.example.dishdelight.data.remote.entity.MenuDetail
import com.example.dishdelight.data.remote.entity.RecommendationsItem
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.data.viewmodel.MainViewModelMain
import com.example.dishdelight.data.viewmodel.SharedViewModel
import com.example.dishdelight.factory.ViewModelFactory

class IntructionFragment : Fragment() {

    //kurang rating button

    private var _binding: FragmentIntructionBinding? = null


    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var mainViewModel: MainViewModel

    private val viewModel by viewModels<MainViewModelMain> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntructionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        viewModel.getSession().observe(requireActivity()) {
            if (it.token != null) {
//                getDetailFood(intentId, it.token)
                sharedViewModel.selectedMenuId.observe(viewLifecycleOwner, Observer { menuId ->
                    Log.d("detail menu id", menuId.toString())
                    mainViewModel.DetailRecipe(menuId, it.token)
                })
            }
        }

        mainViewModel.detailRecipe.observe(requireActivity()){
            val intructionFragment = IntructionFragment()
            Log.d("ambil data detial", "berhasil mengambil kumbulan detail recipe")
            if (it != null){

                setData(it.instructions)

            }
        }


//        listIntructionFood.addAll(getInstruction())
//        showRecylerIntructionFood()

        //ratingBintang
        binding.submitRating.setOnClickListener {
            val rating = binding.ratingBar.rating
            Toast.makeText(requireActivity(), "Rating yang dipilih: $rating", Toast.LENGTH_SHORT)
                .show()
        }

        return root
    }

    private fun setData(rekomendasi: List<String>) {
        binding.rvInstruction.setHasFixedSize(true)
        binding.rvInstruction.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val foodAdapter = AdapterDetaiIIntructionRecipeFragmentIntruction(rekomendasi)
        binding.rvInstruction.adapter = foodAdapter

    }

    private fun getInstruction(): ArrayList<DataClassRecipeIntructionFragmentDashboard> {
        val dataName = resources.getStringArray(R.array.foodInstruction)
        val dataPrice = resources.getStringArray(R.array.foodInstructionStep)

        val listFood = ArrayList<DataClassRecipeIntructionFragmentDashboard>()
        for (i in dataName.indices) {
            val food = DataClassRecipeIntructionFragmentDashboard(dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("Instruction", "instruction list size: ${listFood.size}")
        return listFood
    }

    private fun showRecylerIntructionFood() {

    }

}