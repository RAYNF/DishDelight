package com.example.dishdelight.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentHomeBinding
import com.example.dishdelight.ui.home.listcategory.AdapterCategory
import com.example.dishdelight.ui.home.listcategory.FoodCategory
import com.example.dishdelight.ui.home.listprogram.AdapterProgram
import com.example.dishdelight.ui.home.listprogram.FoodProgram

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // data sementara
    private val ProgramFoodList = ArrayList<FoodProgram>()

    private val categoryFoodList = ArrayList<FoodCategory>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Show RV Category
        binding.rvProgram.setHasFixedSize(true)
        ProgramFoodList.addAll(getFoodProgram())
        showRecylerProgram()

        binding.rvCategory.setHasFixedSize(true)
        categoryFoodList.addAll(getFoodCategory())
        showRecylerCategory()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    // Category
    @SuppressLint("Recycle")
    private fun getFoodProgram(): ArrayList<FoodProgram> {
        val catName = resources.getStringArray(R.array.programName)
        val catImg = resources.obtainTypedArray(R.array.programImages)

        val listProgram = ArrayList<FoodProgram>()
        for (i in catName.indices) {
            val food = FoodProgram(catName[i], catImg.getResourceId(i, -1))
            listProgram.add(food)
        }
        return listProgram
    }

    private fun showRecylerProgram() {
        binding.rvProgram.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterProgram(ProgramFoodList)
        binding.rvProgram.adapter = listFoodAdapter
    }

    private fun getFoodCategory(): ArrayList<FoodCategory> {
        val catName = resources.getStringArray(R.array.categoryName)

        val listCategory = ArrayList<FoodCategory>()
        for (i in catName.indices) {
            val food = FoodCategory(catName[i])
            listCategory.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listCategory.size}")
        return listCategory
    }

    private fun showRecylerCategory() {
        binding.rvCategory.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterCategory(categoryFoodList)
        Log.d("jumlah list","nilai ${categoryFoodList}")
        binding.rvCategory.adapter = listFoodAdapter
    }


}