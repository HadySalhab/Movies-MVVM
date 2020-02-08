package com.android.myapplication.movies.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.myapplication.movies.ui.detail.fragments.ReviewsFragment
import com.android.myapplication.movies.ui.detail.fragments.TrailersFragment
import java.lang.IndexOutOfBoundsException


const val REVIEW_LIST_PAGE_INDEX = 0
const val TRAILER_LIST_PAGE_INDEX = 1
class ViewPagerAdapter (fragment: Fragment):FragmentStateAdapter(fragment){
    private val tabFragmentsCreator: Map<Int, () -> Fragment> = mapOf(
        REVIEW_LIST_PAGE_INDEX to { ReviewsFragment() },
        TRAILER_LIST_PAGE_INDEX to { TrailersFragment() }
    )

    override fun getItemCount(): Int = tabFragmentsCreator.size

    override fun createFragment(position: Int): Fragment =
        tabFragmentsCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()

}