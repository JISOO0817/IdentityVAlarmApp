package com.jisoo.identityvalarmapp.main.charac_fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jisoo.identityvalarmapp.databinding.FragmentBirthViewtwoBinding
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.model.AlarmRunFunction
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class BirthTwoFragment: Fragment() {

    private lateinit var binding: FragmentBirthViewtwoBinding
    private lateinit var runFunc: AlarmRunFunction
    private lateinit var alarmRegAdapter: ViewTypeTwoAdapter
    private lateinit var birthdayPastAdapter: ViewTypeTwoAdapter
    private lateinit var notYetPassedAdapter: ViewTypeTwoAdapter

    /**
     * 액티비티와 뷰모델을 공유
     * */
    private val model: MainViewModel by activityViewModels()

    fun newInstance(): BirthTwoFragment {
        return BirthTwoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBirthViewtwoBinding.inflate(inflater, container, false)

        setUpBinding()
        setUpView()
        setUpObserver()

        return binding.root
    }

    private fun setUpBinding() {
        binding.viewModel = model
        binding.lifecycleOwner = viewLifecycleOwner
        runFunc = AlarmRunFunction(requireActivity())
    }

    private fun setUpView() {
        initAdapter()
        initManager()

//        if(birthdayPastAdapter.itemCount == 0) {
//            Log.d("adapter","unvisible")
//            binding.alreadyBirthTv.visibility = View.GONE
//        } else {
//            Log.d("adapter","visible")
//            binding.alreadyBirthTv.visibility = View.VISIBLE
//        }
    }

    private fun setUpObserver() {
        model.characList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                alarmRegAdapter.setData(runFunc.getClossetCharacList(runFunc.lunaList2SolarList(it)))
                birthdayPastAdapter.setData(runFunc.getbirthdayPastCharacter(it))
                notYetPassedAdapter.setData(runFunc.getNotYetPassedCharacList(it))
            }
        }

    }

    override fun onResume() {
        super.onResume()

    }

    private fun initAdapter() {
        alarmRegAdapter = ViewTypeTwoAdapter(requireActivity())
        birthdayPastAdapter = ViewTypeTwoAdapter(requireActivity())
        notYetPassedAdapter = ViewTypeTwoAdapter(requireActivity())
    }

    private fun initManager() {
        val alarmRegManager = GridLayoutManager(requireActivity(),4)
        binding.alarmCharacterRv.layoutManager = alarmRegManager
        binding.alarmCharacterRv.adapter = alarmRegAdapter

        val notYetPassedManager = GridLayoutManager(requireActivity(), 4)
        binding.notBirthRv.layoutManager = notYetPassedManager
        binding.notBirthRv.adapter = notYetPassedAdapter

        val birthdayPastManager = GridLayoutManager(requireActivity(),4 )
        binding.alreadyBirthRv.layoutManager = birthdayPastManager
        binding.alreadyBirthRv.adapter = birthdayPastAdapter

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