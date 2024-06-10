package com.example.dishdelight.ui.result

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.ActivityResultBinding
import com.example.dishdelight.ui.detailrecipe.DetailRecipeActivity
import com.example.dishdelight.ui.home.listprogram.AdapterProgram
import com.example.dishdelight.ui.home.listprogram.FoodProgram
import com.example.dishdelight.ui.register.RegisterActivity
import com.example.dishdelight.ui.result.grafik.AdapterNutrisi
import com.example.dishdelight.ui.result.grafik.Nutrisi
import com.example.dishdelight.ui.result.listnutrion.AdapterNutrion
import com.example.dishdelight.ui.result.listnutrion.FoodNutrion
import com.example.dishdelight.ui.result.listrelated.AdapterRelatedFood
import com.example.dishdelight.ui.result.listrelated.RelatedFood


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private val categoryFoodList = ArrayList<FoodNutrion>()

    private val relatedFoodList = ArrayList<RelatedFood>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvCategory.setHasFixedSize(true)
        categoryFoodList.addAll(getFoodCategoryData())
        showRecyclerCategory()

        //nutrisi
        binding.rvNutrion.setHasFixedSize(true)
        binding.rvNutrion.layoutManager = LinearLayoutManager(this)
        val nutrisiList = listOf(
            Nutrisi("Protein", 23),
            Nutrisi("Fats", 23),
            Nutrisi("Fibers", 23),
            Nutrisi("Carbohidrat", 23)
        )
        val adapter = AdapterNutrisi(nutrisiList)
        binding.rvNutrion.adapter = adapter

        //related recipe
        binding.rvRelatedRecipe.setHasFixedSize(true)
        relatedFoodList.addAll(getFoodRelatedData())
        showRecyclerRelated()

        binding.btnDetailRecipe.setOnClickListener {
            val intent = Intent(this, DetailRecipeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getFoodCategoryData(): ArrayList<FoodNutrion> {
        val catName = resources.getStringArray(R.array.foodNutrion)

        val listCategory = ArrayList<FoodNutrion>()
        for (i in catName.indices) {
            val food = FoodNutrion(catName[i])
            listCategory.add(food)
        }
        return listCategory
    }

    private fun showRecyclerCategory() {
        binding.rvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterNutrion(categoryFoodList)
        binding.rvCategory.adapter = listFoodAdapter
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
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterRelatedFood(relatedFoodList)
        binding.rvRelatedRecipe.adapter = listFoodAdapter
    }

}