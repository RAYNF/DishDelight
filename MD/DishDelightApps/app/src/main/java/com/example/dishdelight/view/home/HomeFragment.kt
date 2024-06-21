package com.example.dishdelight.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentHomeBinding
import com.example.dishdelight.Adapter.AdapterCategoryRecipeFragmentHome
import com.example.dishdelight.data.dataclass.DataClassRecipeCategoryFragmentHome
import com.example.dishdelight.Adapter.AdapterPopularRecipeFragmentHome
import com.example.dishdelight.data.dataclass.DataClassRecipePopularFragmentHome
import com.example.dishdelight.data.entity.RecommendationsItem
import com.example.dishdelight.data.viewmodel.HomeViewModelFragmentHome
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.factory.ViewModelFactory
import com.example.dishdelight.view.detailrecipe.DetailRecipeActivity
import com.example.dishdelight.data.viewmodel.MainViewModelMain
import com.example.dishdelight.view.login.LoginActivity
import com.example.dishdelight.view.scan.ScanActivity
import com.example.dishdelight.view.setting.SettingActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val categoryFoodList = ArrayList<DataClassRecipeCategoryFragmentHome>()

//    private val dataClassRecipePopularFragmentHome = ArrayList<DataClassRecipePopularFragmentHome>()

    private val viewModel by viewModels<MainViewModelMain> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModelFragmentHome =
            ViewModelProvider(this).get(HomeViewModelFragmentHome::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mainViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                MainViewModel::class.java
            )

        viewModel.getSession().observe(requireActivity()) { user ->
            Log.d("saved token", "ini token yang kesimpen: ${user.token}")
            if (user.token != null) {
                mainViewModel.AllRecomendation(user.token)
                Log.d("ambil data", "berhasil ambil data")
            }
        }


        //btn scan
        binding.btnScan.setOnClickListener {
            val intent = Intent(requireActivity(), ScanActivity::class.java)
            startActivity(intent)
        }

        //search bar
        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_searchFragment)
        }

        //btn setting
        binding.btnSetting.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            startActivity(intent)
        }

        //button log out
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }

        //gambar higliht
        binding.imgHighlightRecipe.setOnClickListener {
            val intent = Intent(requireActivity(), DetailRecipeActivity::class.java)
            startActivity(intent)
        }


        //show rv category
        binding.rvCategory.setHasFixedSize(true)
        categoryFoodList.addAll(getFoodCategory())
        showRecylerCategory()

        //show rv popular food
        binding.rvPopularFood.setHasFixedSize(true)
        binding.rvPopularFood.layoutManager = GridLayoutManager(requireActivity(), 2)

        mainViewModel.listRecomendation.observe(requireActivity()) {
            Log.d("ambil data", "berhasil mengambil kumbulan data rekomendasi")
            if (it != null) {
                setData(it)
            }
        }


//        dataClassRecipePopularFragmentHome.addAll(getFood())
//        showRecyclerViewFood()


        return root
    }

    private fun setData(rekomendasi: List<RecommendationsItem>) {
        val foodAdapter = AdapterPopularRecipeFragmentHome(rekomendasi)
        Log.d("HomeFragment", "Category list size: ${rekomendasi.size}")
        binding.rvPopularFood.adapter = foodAdapter
        foodAdapter.setOnItemClickCallback(object : AdapterPopularRecipeFragmentHome.OnItemClickCallback {
            override fun onItemClicked(menuId: Int) {
                Log.d("HomeFragment", "onItemClicked id: $menuId")
                val intent = Intent(requireActivity(), DetailRecipeActivity::class.java)
                intent.putExtra(DetailRecipeActivity.MENU_ID, menuId)
                startActivity(intent)
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


    private fun getFoodCategory(): ArrayList<DataClassRecipeCategoryFragmentHome> {
        val catName = resources.getStringArray(R.array.categoryName)

        val listCategory = ArrayList<DataClassRecipeCategoryFragmentHome>()
        for (i in catName.indices) {
            val food = DataClassRecipeCategoryFragmentHome(catName[i])
            listCategory.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listCategory.size}")
        return listCategory
    }

    private fun showRecylerCategory() {
        binding.rvCategory.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterCategoryRecipeFragmentHome(categoryFoodList)
        Log.d("jumlah list", "nilai ${categoryFoodList}")
        binding.rvCategory.adapter = listFoodAdapter
    }
}

    //Food
//    @SuppressLint("Recycle")
//    private fun getFood(): ArrayList<DataClassRecipePopularFragmentHome> {
//        val dataImg = resources.obtainTypedArray(R.array.foodImages)
//        val dataName = resources.getStringArray(R.array.foodName)
//        val dataPrice = resources.getStringArray(R.array.foodPrice)
//
//        val listFood = ArrayList<DataClassRecipePopularFragmentHome>()
//        for (i in dataName.indices) {
//            val food = DataClassRecipePopularFragmentHome(
//                dataImg.getResourceId(i, -1),
//                dataName[i],
//                dataPrice[i]
//            )
//            listFood.add(food)
//        }
//        Log.d("HomeFragment", "Category list size: ${listFood.size}")
//        return listFood
//    }


