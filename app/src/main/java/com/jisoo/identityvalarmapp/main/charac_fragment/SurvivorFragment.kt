package com.jisoo.identityvalarmapp.main.charac_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jisoo.identityvalarmapp.databinding.FragmentSurvivorBinding
import com.jisoo.identityvalarmapp.main.MainViewModel
import androidx.fragment.app.FragmentTransaction
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.util.Const.Companion.VIEW_TYPE_SP
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class SurvivorFragment : Fragment(){

    private lateinit var binding: FragmentSurvivorBinding
    private var birthOneFragment: BirthOneFragment = BirthOneFragment()
    private var birthTwoFragment: BirthTwoFragment = BirthTwoFragment()
    private lateinit var fg: Fragment
    /**
     * 액티비티와 뷰모델을 공유
     * */
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurvivorBinding.inflate(inflater, container, false)

        setUpBinding()
        setUpView()
        setUpObserver()

        return binding.root
    }


    private fun setUpBinding() {
        binding.viewmodel = model
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setUpView() {
        // 저장된 뷰타입 가져와서 초기에 보여주기....

        binding.viewChangeBtn1.textSize = getConvertDpByRes(13f)
        binding.viewChangeBtn2.textSize = getConvertDpByRes(13f)

        when(App.prefs.getViewType(VIEW_TYPE_SP,"")) {
            "0" -> {
                binding.viewChangeBtn1.isChecked = true
                fg = birthOneFragment.newInstance()
                setChildFragment(fg)
            }
            "1" -> {
                binding.viewChangeBtn2.isChecked = true
                fg = birthTwoFragment.newInstance()
                setChildFragment(fg)
            }
            else -> {
                binding.viewChangeBtn1.isChecked = true
                fg = birthOneFragment.newInstance()
                setChildFragment(fg)
            }
        }
    }

    private fun setUpObserver() {
        model.onUIMode0.observe(viewLifecycleOwner) {
            if (it == true) {
                birthOneFragment = BirthOneFragment()
                fg = birthOneFragment.newInstance();
                setChildFragment(fg)
                App.prefs.setViewType(VIEW_TYPE_SP, "0")
            }
        }

        model.onUIMode1.observe(viewLifecycleOwner) {
            if (it == true) {
                birthTwoFragment = BirthTwoFragment()
                fg = birthTwoFragment.newInstance()
                setChildFragment(fg)
                App.prefs.setViewType(VIEW_TYPE_SP, "1")
            }
        }

    }

    private fun setChildFragment(child: Fragment) {
        val childFt: FragmentTransaction = childFragmentManager.beginTransaction()

        if (!child.isAdded) {
            Log.d("fragTest","!child.isAdded")
            childFt.replace(R.id.child_fragment_container, child)
            childFt.addToBackStack(null)
            childFt.commit()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun getConvertDpByRes(dpSize: Float): Float {
        val weight: Float
        val dm = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val wi = width.toDouble() / dm.xdpi.toDouble()
        weight = (wi / 2.86817851).toFloat()
        return dpSize * weight
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }




}