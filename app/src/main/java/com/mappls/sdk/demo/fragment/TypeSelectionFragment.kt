package com.mappls.sdk.demo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.mappls.sdk.demo.MainActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.adapter.CategoryAdapter
import com.mappls.sdk.demo.databinding.FragmentTypeSelectionBinding
import com.mappls.sdk.demo.model.CategoryModel
import com.mappls.sdk.demo.utils.Utils

class TypeSelectionFragment : Fragment() {

    private lateinit var mBinding: FragmentTypeSelectionBinding
    private val categoryModels = Utils.getCategoryList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTypeSelectionBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.categoryTypeRv.layoutManager = GridLayoutManager(requireContext(), 3)
        mBinding.categoryTypeRv.adapter = CategoryAdapter(categoryModels) {
            (requireActivity() as MainActivity).replaceFragment(SubCategoryFragment.newInstance(it.type))
        }
    }

}