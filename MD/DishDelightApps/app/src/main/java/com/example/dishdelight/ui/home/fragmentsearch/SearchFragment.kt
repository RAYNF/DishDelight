package com.example.dishdelight.ui.home.fragmentsearch

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dishdelight.Adapter.AdapterSearchResultRecipeFragmentSearch
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentSearchBinding
import com.example.dishdelight.data.dataclass.DataClassRecipePopularFragmentHome


class SearchFragment : Fragment() {

    private var _binding:FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private val searchFood = ArrayList<DataClassRecipePopularFragmentHome>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        val root: View = binding.root

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    Toast.makeText(requireActivity(),searchView.text,Toast.LENGTH_SHORT).show()
//                    updateSearchResults(binding.searchView.text.toString())
                    false
                }
        }

        //rvSearch
        binding.rvSearchResult.setHasFixedSize(true)
        searchFood.addAll(getFood())
        showRecyclerViewFood()


        return root
    }

    //Food
    @SuppressLint("Recycle")
    private fun getFood(): ArrayList<DataClassRecipePopularFragmentHome> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)
        val dataPrice = resources.getStringArray(R.array.foodPrice)

        val listFood = ArrayList<DataClassRecipePopularFragmentHome>()
        for (i in dataName.indices) {
            val food = DataClassRecipePopularFragmentHome(dataImg.getResourceId(i, -1), dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listFood.size}")
        return listFood
    }

    private fun showRecyclerViewFood() {
        binding.rvSearchResult.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        val foodAdapter = AdapterSearchResultRecipeFragmentSearch(searchFood)
        Log.d("HomeFragment", "Category list size: ${searchFood.size}")
        binding.rvSearchResult.adapter = foodAdapter
    }

}