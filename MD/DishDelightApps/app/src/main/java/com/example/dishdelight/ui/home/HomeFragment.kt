package com.example.dishdelight.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentHomeBinding
import com.example.dishdelight.ui.home.listcategory.AdapterCategory
import com.example.dishdelight.ui.home.listcategory.FoodCategory
import com.example.dishdelight.ui.home.listpopular.AdapterPopularFood
import com.example.dishdelight.ui.home.listpopular.PopularFood
import com.example.dishdelight.ui.home.listprogram.AdapterProgram
import com.example.dishdelight.ui.home.listprogram.FoodProgram
import com.example.dishdelight.ui.scan.ScanActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // data sementara
    private val ProgramFoodList = ArrayList<FoodProgram>()

    private val categoryFoodList = ArrayList<FoodCategory>()

    private val popularFood = ArrayList<PopularFood>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Show RV program
        binding.rvProgram.setHasFixedSize(true)
        ProgramFoodList.addAll(getFoodProgram())
        showRecylerProgram()

        //show rv category
        binding.rvCategory.setHasFixedSize(true)
        categoryFoodList.addAll(getFoodCategory())
        showRecylerCategory()

        //show rv popular food
        binding.rvPopularFood.setHasFixedSize(true)
        popularFood.addAll(getFood())
        showRecyclerViewFood()

        binding.btnScan.setOnClickListener {
            val intent = Intent(requireActivity(), ScanActivity::class.java)
            startActivity(intent)
        }


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

    //Food
    @SuppressLint("Recycle")
    private fun getFood(): ArrayList<PopularFood> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)
        val dataPrice = resources.getStringArray(R.array.foodPrice)

        val listFood = ArrayList<PopularFood>()
        for (i in dataName.indices) {
            val food = PopularFood(dataImg.getResourceId(i, -1), dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listFood.size}")
        return listFood
    }

    private fun showRecyclerViewFood() {
        binding.rvPopularFood.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        val foodAdapter = AdapterPopularFood(popularFood)
        Log.d("HomeFragment", "Category list size: ${popularFood.size}")
        binding.rvPopularFood.adapter = foodAdapter
    }


}