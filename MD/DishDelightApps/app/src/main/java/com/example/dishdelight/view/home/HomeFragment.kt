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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentHomeBinding
import com.example.dishdelight.Adapter.AdapterCategoryRecipeFragmentHome
import com.example.dishdelight.data.dataclass.DataClassRecipeCategoryFragmentHome
import com.example.dishdelight.Adapter.AdapterPopularRecipeFragmentHome
import com.example.dishdelight.data.dataclass.DataClassRecipePopularFragmentHome
import com.example.dishdelight.data.remote.entity.RecommendationsItem
import com.example.dishdelight.data.viewmodel.HomeViewModelFragmentHome
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.factory.ViewModelFactory
import com.example.dishdelight.view.detailrecipe.DetailRecipeActivity
import com.example.dishdelight.data.viewmodel.MainViewModelMain
import com.example.dishdelight.view.login.LoginActivity
import com.example.dishdelight.view.scan.ScanActivity
import com.example.dishdelight.view.setting.SettingActivity
import kotlin.random.Random


//homefragment kurang category karena api tidak ada
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val categoryFoodList = ArrayList<DataClassRecipeCategoryFragmentHome>()

    private val viewModel by viewModels<MainViewModelMain> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getSession().observe(requireActivity()) { user ->
            Log.d("saved token", "ini token yang kesimpen: ${user.token}")
            if (user.token != null) {
                mainViewModel.AllRecomendation(user.token)
                Log.d("ambil data", "berhasil ambil data")
                binding.name.text = getFirstPartOfEmail(user.email)
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

        val requestOptions = RequestOptions()
            .error(R.drawable.image_user)
        //foto profil
        Glide.with(requireActivity())
            .load("https://w7.pngwing.com/pngs/178/595/png-transparent-user-profile-computer-icons-login-user-avatars-thumbnail.png")
            .apply(requestOptions)
            .into(binding.imgProfile)


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

                displayRandomFoodHighlight(it)
            }
        }

        return root
    }

    //kumpulan resep homepage
    private fun setData(rekomendasi: List<RecommendationsItem>) {
        viewModel.getSession().observe(requireActivity()) { user ->
            Log.d("saved token", "ini token yang kesimpen: ${user.token}")
            if (user.token != null) {
                val foodAdapter = AdapterPopularRecipeFragmentHome(
                    rekomendasi,
                    mainViewModel,
                    user.token,
//                    viewLifecycleOwner
                )
                Log.d("HomeFragment", "Category list size: ${rekomendasi.size}")
                binding.rvPopularFood.adapter = foodAdapter
            }
        }
//        val foodAdapter = AdapterPopularRecipeFragmentHome(rekomendasi,mainViewModel,)
//        Log.d("HomeFragment", "Category list size: ${rekomendasi.size}")
//        binding.rvPopularFood.adapter = foodAdapter

//        foodAdapter.setOnItemClickCallback(object : AdapterPopularRecipeFragmentHome.OnItemClickCallback {
//            override fun onItemClicked(menuId: Int) {
//                Log.d("HomeFragment", "onItemClicked id: $menuId")
//                val intent = Intent(requireActivity(), DetailRecipeActivity::class.java)
//                intent.putExtra(DetailRecipeActivity.MENU_ID, menuId)
//                startActivity(intent)
//            }
//
//        })
    }

    private fun displayRandomFoodHighlight(rekomendasi: List<RecommendationsItem>) {
        if (rekomendasi.isNotEmpty()) {
            val randomIndex = Random.nextInt(rekomendasi.size)
            val randomFood = rekomendasi[randomIndex]
            val reversePosition = rekomendasi.size - randomIndex

            binding.tvNameHiglight.text = randomFood.menuName
            val requestOptions = RequestOptions()
                .error(R.drawable.image_siomay)

            Glide.with(requireActivity())
                .load(randomFood.imageUrl)
                .apply(requestOptions)
                .into(binding.imgHighlightRecipe)

            binding.imgHighlightRecipe.setOnClickListener {
                Log.d("id menu", randomIndex.toString())
                val intent = Intent(requireActivity(), DetailRecipeActivity::class.java).apply {
                    putExtra("ID_MENU", reversePosition)
                }
                startActivity(intent)
            }
        } else {
            binding.tvNameHiglight.text = "Tidak ada rekomendasi"
        }
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

    private fun getFirstPartOfEmail(email: String, length: Int = 4): String {
        return if (email.length > length) {
            email.substring(0, length)
        } else {
            email
        }
    }
}


