package com.example.dishdelight.view.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.ActivityResultBinding
import com.example.dishdelight.view.detailrecipe.DetailRecipeActivity
import com.example.dishdelight.Adapter.AdapterGrafikNutrisiActivityResult
import com.example.dishdelight.data.dataclass.DataClassGrafikNutrisiActivityResult
import com.example.dishdelight.Adapter.AdapterCategorizeNutrionValueActivityResult
import com.example.dishdelight.data.dataclass.DataClassCategorizeNutrionValueActivityResult
import com.example.dishdelight.Adapter.AdapterRelatedRecipeActivityResult
import com.example.dishdelight.Adapter.AdapterSearchResultRecipeFragmentSearch
import com.example.dishdelight.data.dataclass.DataClassRelatedRecipeActivityResult
import com.example.dishdelight.data.remote.entity.RecommendationsItem
import com.example.dishdelight.data.remote.entity.SearchResultsItem
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.data.viewmodel.MainViewModelMain
import com.example.dishdelight.factory.ViewModelFactory
import com.example.dishdelight.view.scan.ScanActivity
import kotlin.random.Random

//blm bisa menampilkan related recipe, baru bisa menampilkan semua resep
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private val categoryFoodList = ArrayList<DataClassCategorizeNutrionValueActivityResult>()

    private val dataClassRelatedRecipeActivityResultList = ArrayList<DataClassRelatedRecipeActivityResult>()

    private val viewModel by viewModels<MainViewModelMain> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var mainViewModel: MainViewModel

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO = "extra_photo"
    }
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


        val name = intent.getStringExtra(EXTRA_NAME)
        val photoUri = intent.getParcelableExtra<Uri>(EXTRA_PHOTO)


        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.tvProgram.text = name
        binding.imgHighlightRecipe.setImageURI(photoUri)

        //blm bisa menampilkan menu akurat karena tidak ada id makanan
        binding.btnDetailRecipe.setOnClickListener {
            val intent = Intent(this, DetailRecipeActivity::class.java)
            intent.putExtra("ID_MENU", 1)
            startActivity(intent)
            finish()
        }

        binding.btnClose.setOnClickListener {
            val intent =Intent(this@ResultActivity,ScanActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnFavorit.setOnClickListener {

        }


        //nutrisi
        binding.rvNutrion.setHasFixedSize(true)
        binding.rvNutrion.layoutManager = LinearLayoutManager(this)

        val dataClassGrafikNutrisiActivityResultLists = listOf(
            DataClassGrafikNutrisiActivityResult("Protein", Random.nextInt(1,31)),
            DataClassGrafikNutrisiActivityResult("Fats", Random.nextInt(1,31)),
            DataClassGrafikNutrisiActivityResult("Fibers", Random.nextInt(1,31)),
            DataClassGrafikNutrisiActivityResult("Carbohidrat", Random.nextInt(1,31))
        )

        val adapter = AdapterGrafikNutrisiActivityResult(dataClassGrafikNutrisiActivityResultLists)
        binding.rvNutrion.adapter = adapter

        val sortedNutritionList = dataClassGrafikNutrisiActivityResultLists.sortedByDescending { it.amount }
        val topCategories = sortedNutritionList.take(2)

        topCategories.forEach {
            when(it.name){
                "Protein" -> categoryFoodList.add(DataClassCategorizeNutrionValueActivityResult("Rich in Protein"))
                "Fats" -> categoryFoodList.add(DataClassCategorizeNutrionValueActivityResult("Rich in Fat"))
                "Fibers" -> categoryFoodList.add(DataClassCategorizeNutrionValueActivityResult("Rich in Fiber"))
                "Carbohydrates" -> categoryFoodList.add(DataClassCategorizeNutrionValueActivityResult("Rich in Carbohidrat"))
            }
        }

        binding.rvCategory.setHasFixedSize(true)
        showRecyclerCategory()


        //related recipe
//        binding.rvRelatedRecipe.setHasFixedSize(true)
//        dataClassRelatedRecipeActivityResultList.addAll(getFoodRelatedData())
//        showRecyclerRelated()

        viewModel.getSession().observe(this) { user ->
            Log.d("saved token", "ini token yang kesimpen: ${user.token}")
            if (user.token != null) {
                search(user.token, name!!)
                Log.d("nama pencarian",name)
            }
        }

        mainViewModel.listRecomendation.observe(this) { listrecipeSearch ->
            if (listrecipeSearch != null) {
                Log.d("hasil pencarian",listrecipeSearch.toString())
                setUserData(listrecipeSearch)
            }
        }


    }

    private fun setUserData(listrecipe: List<RecommendationsItem>) {

        binding.rvRelatedRecipe.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterRelatedRecipeActivityResult(listrecipe)
        binding.rvRelatedRecipe.adapter = listFoodAdapter
        listFoodAdapter.notifyDataSetChanged()
    }

    private fun search( token: String,query: String,) {
//        mainViewModel.searchRecipe( token,query)
        mainViewModel.AllRecomendation(token)
    }

    private fun showRecyclerCategory() {
        binding.rvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterCategorizeNutrionValueActivityResult(categoryFoodList)
        binding.rvCategory.adapter = listFoodAdapter
    }

//    private fun getFoodRelatedData(): ArrayList<DataClassRelatedRecipeActivityResult> {
//        val catName = resources.getStringArray(R.array.foodName)
//        val catImg = resources.obtainTypedArray(R.array.foodImages)
//        val description = resources.getStringArray(R.array.foodPrice)
//
//        val listProgram = ArrayList<DataClassRelatedRecipeActivityResult>()
//        for (i in catName.indices) {
//            val food = DataClassRelatedRecipeActivityResult(catName[i], catImg.getResourceId(i, -1),description[i])
//            listProgram.add(food)
//        }
//        return listProgram
//    }

//    private fun showRecyclerRelated() {
//        binding.rvRelatedRecipe.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        val listFoodAdapter = AdapterRelatedRecipeActivityResult(dataClassRelatedRecipeActivityResultList)
//        binding.rvRelatedRecipe.adapter = listFoodAdapter
//    }

}