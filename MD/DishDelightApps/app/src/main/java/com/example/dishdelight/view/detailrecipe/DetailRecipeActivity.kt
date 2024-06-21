package com.example.dishdelight.view.detailrecipe

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dishdelight.Adapter.AdapterDetailNutritionRecipeActivityDetailRecipe
import com.example.dishdelight.Adapter.SectionPagerAdapterActivityDetailRecipe
import com.example.dishdelight.R
import com.example.dishdelight.data.api.ApiConfig
import com.example.dishdelight.data.dataclass.DataClassDetailNutrionRecipeActivityDetailRecipe
import com.example.dishdelight.data.entity.MenuDetail
import com.example.dishdelight.data.viewmodel.MainViewModelMain
import com.example.dishdelight.databinding.ActivityDetailRecipeBinding
import com.example.dishdelight.factory.ViewModelFactory
import com.example.dishdelight.view.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailRecipeActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val MENU_ID = "MENU_ID"
    }

    private val viewModel by viewModels<MainViewModelMain> {
        ViewModelFactory.getInstance(this)
    }

    //blm selai rv nutrion
    private val listDataClassDetailNutrionRecipeActivityDetailRecipe =
        ArrayList<DataClassDetailNutrionRecipeActivityDetailRecipe>()

    private lateinit var binding: ActivityDetailRecipeBinding

    private val detailMenu = MutableStateFlow(MenuDetail())

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

        binding.btnClose.setOnClickListener {
            val intent = Intent(this@DetailRecipeActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.rvNutrion.setHasFixedSize(true)
        listDataClassDetailNutrionRecipeActivityDetailRecipe.addAll(getFood())
        showRecyclerViewFood()

        //section page adapter
        val sectionPagerAdapterActivityDetailRecipe = SectionPagerAdapterActivityDetailRecipe(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapterActivityDetailRecipe
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        val intentId = intent.getIntExtra(MENU_ID, 1)
        Log.d("Detail", "intent id: $intentId")
        viewModel.getSession().observe(this) {
            getDetailFood(intentId, it.token)
        }
        lifecycleScope.launch {
            detailMenu.collect {
                CoroutineScope(Dispatchers.Main).launch {
                    setUiData(it)
                }
            }
        }
    }

    private fun getDetailFood(menuId: Int, token: String) {
        lifecycleScope.launch {
            val response = ApiConfig.getApiService().getDetail(menuId, token)
            if (response.menuDetail != null) detailMenu.value = response.menuDetail
        }
    }

    private fun setUiData(menuDetail: MenuDetail) {
        Glide.with(binding.imgRecipe).load(menuDetail.imageUrl)
            .error(R.drawable.baseline_error_24)
            .into(binding.imgRecipe)
        binding.tvNameFood.text = menuDetail.menuName
        binding.tvDescriptionFood.text = menuDetail.description

        if (menuDetail.isFavorite == true)
            binding.btnSave.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        else
            binding.btnSave.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
    }

    private fun getFood(): ArrayList<DataClassDetailNutrionRecipeActivityDetailRecipe> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)

        val listFood = ArrayList<DataClassDetailNutrionRecipeActivityDetailRecipe>()
        for (i in dataName.indices) {
            val food = DataClassDetailNutrionRecipeActivityDetailRecipe(
                dataImg.getResourceId(i, -1),
                dataName[i]
            )
            listFood.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listFood.size}")
        return listFood
    }

    private fun showRecyclerViewFood() {
        binding.rvNutrion.layoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false)
        val foodAdapter = AdapterDetailNutritionRecipeActivityDetailRecipe(
            listDataClassDetailNutrionRecipeActivityDetailRecipe
        )
        Log.d(
            "HomeFragment",
            "Category list size: ${listDataClassDetailNutrionRecipeActivityDetailRecipe.size}"
        )
        binding.rvNutrion.adapter = foodAdapter
    }
}