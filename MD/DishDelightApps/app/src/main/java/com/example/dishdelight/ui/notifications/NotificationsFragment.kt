package com.example.dishdelight.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentNotificationsBinding
import com.example.dishdelight.Adapter.AdapterFavoriteRecipeFragmentNotification
import com.example.dishdelight.data.dataclass.DataClassFavoriteRecipeFragmentNotification
import com.example.dishdelight.data.viewmodel.NotificationsViewModelFragmentNotification

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val listDataClassFavoriteRecipeFragmentNotification = ArrayList<DataClassFavoriteRecipeFragmentNotification>()


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModelFragmentNotification =
            ViewModelProvider(this).get(NotificationsViewModelFragmentNotification::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvFavorit.setHasFixedSize(true)
        listDataClassFavoriteRecipeFragmentNotification.addAll(getFood())
        showRecyclerViewFood()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFood(): ArrayList<DataClassFavoriteRecipeFragmentNotification> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)
        val dataPrice = resources.getStringArray(R.array.foodPrice)

        val listFood = ArrayList<DataClassFavoriteRecipeFragmentNotification>()
        for (i in dataName.indices) {
            val food = DataClassFavoriteRecipeFragmentNotification(dataImg.getResourceId(i, -1), dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("notification Fragment", "Category list size: ${listFood.size}")
        return listFood

    }

    private fun showRecyclerViewFood() {
        Log.d("list Favorite food", "Category list size: ${listDataClassFavoriteRecipeFragmentNotification.size}")
        binding.rvFavorit.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        val foodAdapter = AdapterFavoriteRecipeFragmentNotification(listDataClassFavoriteRecipeFragmentNotification)
        Log.d("notification kirim", "Category list size: ${listDataClassFavoriteRecipeFragmentNotification.size}")
        binding.rvFavorit.adapter = foodAdapter
    }
}