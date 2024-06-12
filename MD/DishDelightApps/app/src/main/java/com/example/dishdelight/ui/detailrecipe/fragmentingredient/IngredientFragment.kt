package com.example.dishdelight.ui.detailrecipe.fragmentingredient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentIngredientBinding
import com.example.dishdelight.data.dataclass.DataClassRecipeIngredientsFragmentDashboard
import com.example.dishdelight.Adapter.AdapterDetailIngredientRecipeFragmentIngredient
import com.example.dishdelight.Adapter.AdapterDetailRelatedRecipeFragmentIngredient
import com.example.dishdelight.data.dataclass.DataClassRelatedRecipeActivityResult


class IngredientFragment : Fragment() {

    private var _binding:FragmentIngredientBinding? = null

    private val binding get() = _binding!!


    //list ingredients ambil data class dashboard -> list intruction
    private val listIngredientsFood = ArrayList<DataClassRecipeIngredientsFragmentDashboard>()

    private val lisDataClassRelatedRecipeActivityResult = ArrayList<DataClassRelatedRecipeActivityResult>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientBinding.inflate(inflater,container,false)
        val root: View = binding.root

        binding.rvIngredient.setHasFixedSize(true)
        listIngredientsFood.addAll(getFood())
        showRecyclerViewFood()

        //related recipe
        binding.rvRelatedRecipe.setHasFixedSize(true)
        lisDataClassRelatedRecipeActivityResult.addAll(getFoodRelatedData())
        showRecyclerRelated()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFood(): ArrayList<DataClassRecipeIngredientsFragmentDashboard> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)
        val dataPrice = resources.getStringArray(R.array.foodPrice)

        val listFood = ArrayList<DataClassRecipeIngredientsFragmentDashboard>()
        for (i in dataName.indices) {
            val food = DataClassRecipeIngredientsFragmentDashboard(dataImg.getResourceId(i, -1), dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listFood.size}")
        return listFood
    }

    private fun showRecyclerViewFood() {
        binding.rvIngredient.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val foodAdapter = AdapterDetailIngredientRecipeFragmentIngredient(listIngredientsFood)
        Log.d("HomeFragment", "Category list size: ${listIngredientsFood.size}")
        binding.rvIngredient.adapter = foodAdapter
    }

    private fun getFoodRelatedData(): ArrayList<DataClassRelatedRecipeActivityResult> {
        val catName = resources.getStringArray(R.array.foodName)
        val catImg = resources.obtainTypedArray(R.array.foodImages)
        val description = resources.getStringArray(R.array.foodPrice)

        val listProgram = ArrayList<DataClassRelatedRecipeActivityResult>()
        for (i in catName.indices) {
            val food = DataClassRelatedRecipeActivityResult(catName[i], catImg.getResourceId(i, -1),description[i])
            listProgram.add(food)
        }
        return listProgram
    }

    private fun showRecyclerRelated() {
        binding.rvRelatedRecipe.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterDetailRelatedRecipeFragmentIngredient(lisDataClassRelatedRecipeActivityResult)
        binding.rvRelatedRecipe.adapter = listFoodAdapter
    }

}