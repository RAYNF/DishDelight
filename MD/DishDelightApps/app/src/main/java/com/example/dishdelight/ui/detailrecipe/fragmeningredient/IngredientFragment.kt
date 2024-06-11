package com.example.dishdelight.ui.detailrecipe.fragmeningredient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentIngredientBinding
import com.example.dishdelight.ui.dashboard.listingredient.FoodIngredients
import com.example.dishdelight.ui.detailrecipe.fragmeningredient.listingredient.AdapterDetailIngredient
import com.example.dishdelight.ui.detailrecipe.fragmeningredient.listrelatedrecipe.AdapterDetailRecipeRelated
import com.example.dishdelight.ui.result.listrelated.RelatedFood


class IngredientFragment : Fragment() {

    private var _binding:FragmentIngredientBinding? = null

    private val binding get() = _binding!!


    //list ingredients ambil data class dashboard -> list intruction
    private val listIngredientsFood = ArrayList<FoodIngredients>()

    private val lisRelatedFood = ArrayList<RelatedFood>()

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
        lisRelatedFood.addAll(getFoodRelatedData())
        showRecyclerRelated()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFood(): ArrayList<FoodIngredients> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)
        val dataPrice = resources.getStringArray(R.array.foodPrice)

        val listFood = ArrayList<FoodIngredients>()
        for (i in dataName.indices) {
            val food = FoodIngredients(dataImg.getResourceId(i, -1), dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listFood.size}")
        return listFood
    }

    private fun showRecyclerViewFood() {
        binding.rvIngredient.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val foodAdapter = AdapterDetailIngredient(listIngredientsFood)
        Log.d("HomeFragment", "Category list size: ${listIngredientsFood.size}")
        binding.rvIngredient.adapter = foodAdapter
    }

    private fun getFoodRelatedData(): ArrayList<RelatedFood> {
        val catName = resources.getStringArray(R.array.foodName)
        val catImg = resources.obtainTypedArray(R.array.foodImages)
        val description = resources.getStringArray(R.array.foodPrice)

        val listProgram = ArrayList<RelatedFood>()
        for (i in catName.indices) {
            val food = RelatedFood(catName[i], catImg.getResourceId(i, -1),description[i])
            listProgram.add(food)
        }
        return listProgram
    }

    private fun showRecyclerRelated() {
        binding.rvRelatedRecipe.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterDetailRecipeRelated(lisRelatedFood)
        binding.rvRelatedRecipe.adapter = listFoodAdapter
    }

}