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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentHomeBinding
import com.example.dishdelight.ui.home.listcategory.AdapterCategory
import com.example.dishdelight.ui.home.listcategory.FoodCategory
import com.example.dishdelight.ui.home.listpopular.AdapterPopularFood
import com.example.dishdelight.ui.home.listpopular.PopularFood
import com.example.dishdelight.ui.scan.ScanActivity
import com.example.dishdelight.ui.setting.SettingActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

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


           binding.searchBar.setOnClickListener {
              findNavController().navigate(R.id.action_navigation_home_to_searchFragment)
            }


        binding.btnSetting.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            startActivity(intent)
        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
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