package com.example.dishdelight.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentNotificationsBinding
import com.example.dishdelight.ui.home.listpopular.AdapterPopularFood
import com.example.dishdelight.ui.home.listpopular.PopularFood
import com.example.dishdelight.ui.notifications.listfavorit.AdapterFavoriteFood
import com.example.dishdelight.ui.notifications.listfavorit.FavorieFood

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val listFavorieFood = ArrayList<FavorieFood>()


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvFavorit.setHasFixedSize(true)
        listFavorieFood.addAll(getFood())
        showRecyclerViewFood()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFood(): ArrayList<FavorieFood> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)
        val dataPrice = resources.getStringArray(R.array.foodPrice)

        val listFood = ArrayList<FavorieFood>()
        for (i in dataName.indices) {
            val food = FavorieFood(dataImg.getResourceId(i, -1), dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("notification Fragment", "Category list size: ${listFood.size}")
        return listFood

    }

    private fun showRecyclerViewFood() {
        Log.d("list Favorite food", "Category list size: ${listFavorieFood.size}")
        binding.rvFavorit.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        val foodAdapter = AdapterFavoriteFood(listFavorieFood)
        Log.d("notification kirim", "Category list size: ${listFavorieFood.size}")
        binding.rvFavorit.adapter = foodAdapter
    }
}