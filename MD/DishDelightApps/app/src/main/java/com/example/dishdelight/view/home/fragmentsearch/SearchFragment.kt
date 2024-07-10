package com.example.dishdelight.view.home.fragmentsearch

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.Adapter.AdapterSearchResultRecipeFragmentSearch
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentSearchBinding
import com.example.dishdelight.data.dataclass.DataClassRecipePopularFragmentHome
import com.example.dishdelight.data.remote.entity.RecommendationsItem
import com.example.dishdelight.data.remote.entity.SearchResultsItem
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.data.viewmodel.MainViewModelMain
import com.example.dishdelight.factory.ViewModelFactory


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!


    private val viewModel by viewModels<MainViewModelMain> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    viewModel.getSession().observe(requireActivity()) { user ->
                        Log.d("saved token", "ini token yang kesimpen: ${user.token}")
                        if (user.token != null) {
                            search(user.token, searchView.editText.text.toString())
                        }
                    }
                    false
                }

        }



        mainViewModel.listRecomendationSearch.observe(requireActivity()) { listrecipeSearch ->
            if (listrecipeSearch != null) {
                Log.d("hasil pencarian",listrecipeSearch.toString())
                setUserData(listrecipeSearch)
            }
        }

        //search view


        //button back
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_navigation_home)
        }



        return root
    }

    private fun setUserData(listrecipe: List<SearchResultsItem>) {
//        binding.rvSearchResult.setHasFixedSize(true)
        binding.rvSearchResult.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val foodAdapter = AdapterSearchResultRecipeFragmentSearch(listrecipe)
        binding.rvSearchResult.adapter = foodAdapter
    }

    private fun search( token: String,query: String,) {
        mainViewModel.searchRecipe( token,query)
    }

    //Food
    @SuppressLint("Recycle")
    private fun getFood(): ArrayList<DataClassRecipePopularFragmentHome> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)
        val dataPrice = resources.getStringArray(R.array.foodPrice)

        val listFood = ArrayList<DataClassRecipePopularFragmentHome>()
        for (i in dataName.indices) {
            val food = DataClassRecipePopularFragmentHome(
                dataImg.getResourceId(i, -1),
                dataName[i],
                dataPrice[i]
            )
            listFood.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listFood.size}")
        return listFood
    }

    private fun showRecyclerViewFood() {

    }

}