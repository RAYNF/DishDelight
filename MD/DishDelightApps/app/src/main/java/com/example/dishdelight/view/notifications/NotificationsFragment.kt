package com.example.dishdelight.view.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.FragmentNotificationsBinding
import com.example.dishdelight.Adapter.AdapterFavoriteRecipeFragmentNotification
import com.example.dishdelight.data.dataclass.DataClassFavoriteRecipeFragmentNotification
import com.example.dishdelight.data.remote.entity.FavoriteMenusItem
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.data.viewmodel.MainViewModelMain
import com.example.dishdelight.data.viewmodel.NotificationsViewModelFragmentNotification
import com.example.dishdelight.factory.ViewModelFactory

class NotificationsFragment : Fragment() {


    private var _binding: FragmentNotificationsBinding? = null

    private val listDataClassFavoriteRecipeFragmentNotification =
        ArrayList<DataClassFavoriteRecipeFragmentNotification>()

    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModelMain> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val notificationsViewModelFragmentNotification =
//            ViewModelProvider(this).get(NotificationsViewModelFragmentNotification::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            Log.d("saved token", "ini token yang kesimpen: ${user.token}")
            if (user.token != null) {
                mainViewModel.ListFavorite(user.token)
                Log.d("ambil data", "berhasil ambil data")
            }
        }

        binding.rvFavorit.setHasFixedSize(true)
//        listDataClassFavoriteRecipeFragmentNotification.addAll(getFood())
//        showRecyclerViewFood()
        binding.rvFavorit.layoutManager = GridLayoutManager(requireActivity(), 2)

        mainViewModel.listFavorite.observe(viewLifecycleOwner) {
            Log.d("ambil data", "berhasil mengambil kumbulan data rekomendasi")
            if (it != null) {
                showRecyclerViewFood(it)
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRecyclerViewFood(favorit: List<FavoriteMenusItem>) {
        Log.d("list Favorite food", "Category list size: ${favorit.size}")

        val foodAdapter = AdapterFavoriteRecipeFragmentNotification(favorit)

        binding.rvFavorit.adapter = foodAdapter
    }
}
