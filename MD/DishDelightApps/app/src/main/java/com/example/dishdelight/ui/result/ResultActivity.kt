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
import com.example.dishdelight.Adapter.AdapterGrafikNutrisiActivityResult
import com.example.dishdelight.data.dataclass.DataClassGrafikNutrisiActivityResult
import com.example.dishdelight.Adapter.AdapterCategorizeNutrionValueActivityResult
import com.example.dishdelight.data.dataclass.DataClassCategorizeNutrionValueActivityResult
import com.example.dishdelight.Adapter.AdapterRelatedRecipeActivityResult
import com.example.dishdelight.data.dataclass.DataClassRelatedRecipeActivityResult


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private val categoryFoodList = ArrayList<DataClassCategorizeNutrionValueActivityResult>()

    private val dataClassRelatedRecipeActivityResultList = ArrayList<DataClassRelatedRecipeActivityResult>()
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
        val dataClassGrafikNutrisiActivityResultLists = listOf(
            DataClassGrafikNutrisiActivityResult("Protein", 23),
            DataClassGrafikNutrisiActivityResult("Fats", 23),
            DataClassGrafikNutrisiActivityResult("Fibers", 23),
            DataClassGrafikNutrisiActivityResult("Carbohidrat", 23)
        )
        val adapter = AdapterGrafikNutrisiActivityResult(dataClassGrafikNutrisiActivityResultLists)
        binding.rvNutrion.adapter = adapter

        //related recipe
        binding.rvRelatedRecipe.setHasFixedSize(true)
        dataClassRelatedRecipeActivityResultList.addAll(getFoodRelatedData())
        showRecyclerRelated()

        binding.btnDetailRecipe.setOnClickListener {
            val intent = Intent(this, DetailRecipeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getFoodCategoryData(): ArrayList<DataClassCategorizeNutrionValueActivityResult> {
        val catName = resources.getStringArray(R.array.foodNutrion)

        val listCategory = ArrayList<DataClassCategorizeNutrionValueActivityResult>()
        for (i in catName.indices) {
            val food = DataClassCategorizeNutrionValueActivityResult(catName[i])
            listCategory.add(food)
        }
        return listCategory
    }

    private fun showRecyclerCategory() {
        binding.rvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterCategorizeNutrionValueActivityResult(categoryFoodList)
        binding.rvCategory.adapter = listFoodAdapter
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
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterRelatedRecipeActivityResult(dataClassRelatedRecipeActivityResultList)
        binding.rvRelatedRecipe.adapter = listFoodAdapter
    }

}