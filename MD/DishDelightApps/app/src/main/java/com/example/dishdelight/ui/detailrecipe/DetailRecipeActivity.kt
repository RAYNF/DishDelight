package com.example.dishdelight.ui.detailrecipe

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.dishdelight.R
import com.example.dishdelight.databinding.ActivityDetailRecipeBinding
import com.example.dishdelight.ui.detailrecipe.listdetailnutrion.AdapterDetailNutrition
import com.example.dishdelight.ui.detailrecipe.listdetailnutrion.DetailNutrition
import com.example.dishdelight.ui.home.listpopular.AdapterPopularFood
import com.example.dishdelight.ui.home.listpopular.PopularFood
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailRecipeActivity : AppCompatActivity() {

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    //blm selai rv nutrion
    private val listDetailNutrition = ArrayList<DetailNutrition>()

    private lateinit var binding: ActivityDetailRecipeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvNutrion.setHasFixedSize(true)
        listDetailNutrition.addAll(getFood())
        showRecyclerViewFood()

        //section page adapter
        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager:ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs,viewPager){tab,position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
        
    }

    private fun getFood(): ArrayList<DetailNutrition> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)

        val listFood = ArrayList<DetailNutrition>()
        for (i in dataName.indices) {
            val food = DetailNutrition(dataImg.getResourceId(i, -1), dataName[i])
            listFood.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listFood.size}")
        return listFood
    }

    private fun showRecyclerViewFood() {
        binding.rvNutrion.layoutManager =
            GridLayoutManager(this, 2,LinearLayoutManager.HORIZONTAL,false)
        val foodAdapter = AdapterDetailNutrition(listDetailNutrition)
        Log.d("HomeFragment", "Category list size: ${listDetailNutrition.size}")
        binding.rvNutrion.adapter = foodAdapter
    }
}