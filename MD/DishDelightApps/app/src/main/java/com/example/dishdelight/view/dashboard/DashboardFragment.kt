package com.example.dishdelight.view.dashboard

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import com.example.dishdelight.utils.getImageUri

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val listIngredientsFood = ArrayList<DataClassRecipeIngredientsFragmentDashboard>()
    private val listIntructionFood = ArrayList<DataClassRecipeIntructionFragmentDashboard>()

    // Variabel global untuk URI gambar
    private var currentImageUri: Uri? = null
    private var currentImageUriIngredients: Uri? = null

    // Variabel global untuk dialogBinding
    private var dialogBindingIngredient: DialogAddIngredientBinding? = null
    private var dialogBindingIntruction: DialogAddIntructionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModelFragmentDashboard =
            ViewModelProvider(this).get(DashboardViewModelFragmentDashboard::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnAddIngredient.setOnClickListener {
            showAddIngredientDialog()
        }

        binding.btnAddIntruction.setOnClickListener {
            showAddIntructionDialog()
        }

        binding.btnSend.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_home)
        }

        binding.btnGaleri.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.rvIngredient.setHasFixedSize(true)
        listIngredientsFood.addAll(getFood())
        showRecyclerViewFood()

        binding.rvInstruction.setHasFixedSize(true)
        listIntructionFood.addAll(getInstruction())
        showRecyclerIntructionFood()

        return root
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireActivity())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private val launcherGalleryIngredient = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUriIngredients = uri
            showImageIngredient()

        } else {
            Log.d("Photo Picker", "No media Ingredient selected")
        }
    }

    private fun showImageIngredient() {
        currentImageUriIngredients?.let {
            dialogBindingIngredient?.imagePreview?.setImageURI(it)
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("photo picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun showAddIntructionDialog() {
         dialogBindingIntruction = DialogAddIntructionBinding.inflate(layoutInflater)
        val dialogIntruction = AlertDialog.Builder(requireActivity())
            .setView(dialogBindingIntruction!!.root)
            .create()

        dialogBindingIntruction!!.btnAdd.setOnClickListener {

        }



        dialogIntruction.show()
    }

    private fun showAddIngredientDialog() {
        dialogBindingIngredient = DialogAddIngredientBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(dialogBindingIngredient!!.root)
            .create()

        dialogBindingIngredient!!.btnGallery.setOnClickListener {
            launcherGalleryIngredient.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        dialogBindingIngredient!!.btnCamera.setOnClickListener {
          startCameraIngredient()
        }

        dialogBindingIngredient!!.btnAdd.setOnClickListener {
            // Handle the addition of ingredients
        }

        dialog.show()
    }

    private fun startCameraIngredient() {
        currentImageUriIngredients = getImageUri(requireActivity())
        launcherIntentCameraIngredient.launch(currentImageUriIngredients)

    }

    private val launcherIntentCameraIngredient = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){isSucces->
        if (isSucces){
            showImageIngredient()
        }

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
            val food = DataClassRecipeIngredientsFragmentDashboard(
                dataImg.getResourceId(i, -1), dataName[i], dataPrice[i]
            )
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