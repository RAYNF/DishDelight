package com.example.dishdelight.view.detailrecipe.fragmentingredient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dishdelight.Adapter.AdapterDetaiIIntructionRecipeFragmentIntruction
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentIngredientBinding
import com.example.dishdelight.data.dataclass.DataClassRecipeIngredientsFragmentDashboard
import com.example.dishdelight.Adapter.AdapterDetailIngredientRecipeFragmentIngredient
import com.example.dishdelight.Adapter.AdapterDetailRelatedRecipeFragmentIngredient
import com.example.dishdelight.data.dataclass.DataClassRelatedRecipeActivityResult
import com.example.dishdelight.data.remote.entity.MenuDetail
import com.example.dishdelight.data.remote.entity.RecommendationsItem
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.data.viewmodel.MainViewModelMain
import com.example.dishdelight.data.viewmodel.SharedViewModel
import com.example.dishdelight.factory.ViewModelFactory
import com.example.dishdelight.view.detailrecipe.fragmentintruction.IntructionFragment
import kotlin.random.Random

//kurang munculin gambar sama jumlah yang dibutuhkan untuk memasak 1 porsi
class IngredientFragment : Fragment() {

    private var _binding:FragmentIngredientBinding? = null

    private val binding get() = _binding!!

    //array status user
    private val randomStatuses = listOf(
        "Resep ini sangat mudah dibuat dan lezat!",
        "Bahan-bahan ini sempurna untuk makanan sehat.",
        "Cobalah resep ini untuk hidangan istimewa!",
        "Resep ini cepat dan mudah untuk hari sibuk.",
        "Nikmati rasa autentik dari resep ini."
    )

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var mainViewModel: MainViewModel

    private val viewModel by viewModels<MainViewModelMain> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientBinding.inflate(inflater,container,false)
        val root: View = binding.root

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        viewModel.getSession().observe(requireActivity()) {
            if (it.token != null) {
                sharedViewModel.selectedMenuId.observe(viewLifecycleOwner, Observer { menuId ->
                    Log.d("detail menu id", menuId.toString())
                    mainViewModel.DetailRecipe(menuId, it.token)
                    mainViewModel.AllRecomendation(it.token)
                })
            }
        }

        mainViewModel.detailRecipe.observe(requireActivity()){
            Log.d("ambil data detial", "berhasil mengambil kumbulan detail recipe")
            if (it != null){
                setData(it)
            }
        }

        mainViewModel.listRecomendation.observe(requireActivity()) {
            Log.d("ambil data", "berhasil mengambil kumbulan data rekomendasi")
            if (it != null) {
                setDataRecipe(it)
            }
        }

        return root
    }

    //karena rekomendasi blm ada jadi ambil homepage
    private fun setDataRecipe(it: List<RecommendationsItem>) {
        binding.rvRelatedRecipe.setHasFixedSize(true)
        binding.rvRelatedRecipe.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = AdapterDetailRelatedRecipeFragmentIngredient(it)
        binding.rvRelatedRecipe.adapter = listFoodAdapter
    }

    private fun setData(rekomendasi: MenuDetail) {
        val requestOptions = RequestOptions()
            .error(R.drawable.image_user)

        binding.rvIngredient.setHasFixedSize(true)
        binding.rvIngredient.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val foodAdapter = AdapterDetailIngredientRecipeFragmentIngredient(rekomendasi)
        Log.d("HomeFragment", "Category list size: ${rekomendasi.ingredients.size}")
        binding.rvIngredient.adapter = foodAdapter

        binding.tvTotalIngredient.text = rekomendasi.ingredients.size.toString() + " item"
        binding.tvName.text = rekomendasi.authorName
        Glide.with(requireActivity())
            .load(rekomendasi.imageUrl)
            .apply(requestOptions)
            .into(binding.imgProfile)

        val randomStatus = randomStatuses[Random.nextInt(randomStatuses.size)]
        binding.tvDescription.text = randomStatus
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}