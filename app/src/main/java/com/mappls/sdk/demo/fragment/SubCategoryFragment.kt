package com.mappls.sdk.demo.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mappls.sdk.demo.MainActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.adapter.SubCategoryAdapter
import com.mappls.sdk.demo.databinding.FragmentSubCategoryBinding
import com.mappls.sdk.demo.utils.Utils

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_SCREEN_TYPE = "screen_type"

/**
 * A simple [Fragment] subclass.
 * Use the [SubCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubCategoryFragment : Fragment() {
    private var screenType: Int = 1
    private lateinit var mBinding: FragmentSubCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            screenType = it.getInt(ARG_SCREEN_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.subCategoryHeader.headerTitle.text = Utils.getSubCategoryTitle(screenType)
        mBinding.subCategoryHeader.headerBack.setOnClickListener {
            (requireActivity() as MainActivity).popBackStack(this)
        }

        mBinding.categoryTypeRv.layoutManager = LinearLayoutManager(requireContext())
        mBinding.categoryTypeRv.adapter = SubCategoryAdapter(Utils.getSubCategoryList(screenType)) {
            startActivity(Intent(requireContext(), it.activity))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param screenType Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(screenType: Int) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SCREEN_TYPE, screenType)
                }
            }
    }
}