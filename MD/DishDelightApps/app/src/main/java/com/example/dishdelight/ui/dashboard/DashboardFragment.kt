package com.example.dishdelight.ui.dashboard

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.databinding.DialogAddIngredientBinding
import com.example.dishdelight.databinding.DialogAddIntructionBinding
import com.example.dishdelight.databinding.FragmentDashboardBinding
import com.example.dishdelight.Adapter.AdapterListIngredientsRecipeFragmentDashboard
import com.example.dishdelight.data.dataclass.DataClassRecipeIngredientsFragmentDashboard
import com.example.dishdelight.Adapter.AdapterInstructionRecipeFragmentDashboard
import com.example.dishdelight.data.dataclass.DataClassRecipeIntructionFragmentDashboard
import com.example.dishdelight.data.viewmodel.DashboardViewModelFragmentDashboard

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val listIngredientsFood = ArrayList<DataClassRecipeIngredientsFragmentDashboard>()

    //blm selesai
    private val listIntructionFood = ArrayList<DataClassRecipeIntructionFragmentDashboard>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModelFragmentDashboard =
            ViewModelProvider(this).get(DashboardViewModelFragmentDashboard::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvIngredient.setHasFixedSize(true)
        listIngredientsFood.addAll(getFood())
        showRecyclerViewFood()

        binding.rvInstruction.setHasFixedSize(true)
        listIntructionFood.addAll(getInstruction())
        showRecyclerIntructionFood()

        binding.btnAddIngredient.setOnClickListener {
            showAddIngredientDialog()
        }

        binding.btnAddIntruction.setOnClickListener {
            showAddIntructionDialog()
        }

        return root
    }

    private fun showAddIntructionDialog() {
        val dialogInstructionBinding = DialogAddIntructionBinding.inflate(layoutInflater)
        val dialogIntruction = AlertDialog.Builder(requireActivity())
            .setView(dialogInstructionBinding.root)
            .create()

        dialogInstructionBinding.btnAdd.setOnClickListener {

        }

        dialogIntruction.show()
    }

    private fun showAddIngredientDialog() {
        val dialogBinding = DialogAddIngredientBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(dialogBinding.root)
            .create()

        var selectedImageUri: Uri? = null

        dialogBinding.btnGallery.setOnClickListener {

        }

        dialogBinding.btnCamera.setOnClickListener {

        }

        dialogBinding.btnAdd.setOnClickListener {

        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFood(): ArrayList<DataClassRecipeIngredientsFragmentDashboard> {
        val dataImg = resources.obtainTypedArray(R.array.foodImages)
        val dataName = resources.getStringArray(R.array.foodName)
        val dataPrice = resources.getStringArray(R.array.foodPrice)

        val listFood = ArrayList<DataClassRecipeIngredientsFragmentDashboard>()
        for (i in dataName.indices) {
            val food = DataClassRecipeIngredientsFragmentDashboard(dataImg.getResourceId(i, -1), dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("HomeFragment", "Category list size: ${listFood.size}")
        return listFood
    }

    private fun showRecyclerViewFood() {
        binding.rvIngredient.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val foodAdapter = AdapterListIngredientsRecipeFragmentDashboard(listIngredientsFood)
        Log.d("HomeFragment", "Category list size: ${listIngredientsFood.size}")
        binding.rvIngredient.adapter = foodAdapter
    }

    private fun getInstruction(): ArrayList<DataClassRecipeIntructionFragmentDashboard> {
        val dataName = resources.getStringArray(R.array.foodInstruction)
        val dataPrice = resources.getStringArray(R.array.foodInstructionStep)

        val listFood = ArrayList<DataClassRecipeIntructionFragmentDashboard>()
        for (i in dataName.indices) {
            val food = DataClassRecipeIntructionFragmentDashboard(dataName[i], dataPrice[i])
            listFood.add(food)
        }
        Log.d("Instruction", "instruction list size: ${listFood.size}")
        return listFood
    }

    private fun showRecyclerIntructionFood() {
        binding.rvInstruction.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val foodAdapter = AdapterInstructionRecipeFragmentDashboard(listIntructionFood)
        Log.d("instruction", "intruction listIntructionFood list size: ${listIntructionFood.size}")
        binding.rvInstruction.adapter = foodAdapter
    }
}