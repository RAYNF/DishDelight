package com.example.dishdelight.ui.detailrecipe

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dishdelight.ui.detailrecipe.fragmeningredient.IngredientFragment
import com.example.dishdelight.ui.detailrecipe.fragmentintruction.IntructionFragment

//mengatur view pager2
class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       var fragment: Fragment? = null
        when(position){
            0 -> fragment = IngredientFragment()
            1 -> fragment = IntructionFragment()
        }
        return fragment as Fragment
    }
}