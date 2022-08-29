package com.jisoo.identityvalarmapp.main.charac_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jisoo.identityvalarmapp.databinding.FragmentBirthViewoneBinding
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.model.AlarmRunFunction
import com.jisoo.identityvalarmapp.model.CharacInfo

class BirthOneFragment: Fragment() {

    private lateinit var binding: FragmentBirthViewoneBinding

    private lateinit var runFunc: AlarmRunFunction

    private lateinit var janAdapter: ListAdapter
    private lateinit var febAdapter: ListAdapter
    private lateinit var marchAdapter: ListAdapter
    private lateinit var aprilAdapter: ListAdapter
    private lateinit var mayAdapter: ListAdapter
    private lateinit var juneAdapter: ListAdapter
    private lateinit var julyAdapter: ListAdapter
    private lateinit var augAdapter: ListAdapter
    private lateinit var sepAdapter: ListAdapter
    private lateinit var octAdapter: ListAdapter
    private lateinit var novAdapter: ListAdapter
    private lateinit var decAdapter: ListAdapter

    /**
     * 액티비티와 뷰모델을 공유
     * */
    private val model: MainViewModel by activityViewModels()

    fun newInstance(): BirthOneFragment {
        return BirthOneFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBirthViewoneBinding.inflate(inflater, container, false)

        setUpBinding()
        setUpView()
        setUpObserver()

        return binding.root
    }

    private fun setUpBinding() {
        binding.viewmodel = model
        binding.lifecycleOwner = viewLifecycleOwner
        runFunc = AlarmRunFunction(requireActivity())
    }

    private fun setUpView() {
        initAdapter()
        initManager()
    }

    private fun setUpObserver() {
        model.characList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                sortedRvByMonth(runFunc.returnBySortingTheList(it))
            }
        }

    }

    override fun onResume() {
        super.onResume()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyDataSetChanged() {
        janAdapter.notifyDataSetChanged()
        febAdapter.notifyDataSetChanged()
        marchAdapter.notifyDataSetChanged()
        aprilAdapter.notifyDataSetChanged()
        mayAdapter.notifyDataSetChanged()
        juneAdapter.notifyDataSetChanged()
        julyAdapter.notifyDataSetChanged()
        augAdapter.notifyDataSetChanged()
        sepAdapter.notifyDataSetChanged()
        octAdapter.notifyDataSetChanged()
        novAdapter.notifyDataSetChanged()
        decAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        janAdapter = ListAdapter(requireActivity())
        febAdapter = ListAdapter(requireActivity())
        marchAdapter = ListAdapter(requireActivity())
        aprilAdapter = ListAdapter(requireActivity())
        mayAdapter = ListAdapter(requireActivity())
        juneAdapter = ListAdapter(requireActivity())
        julyAdapter = ListAdapter(requireActivity())
        augAdapter = ListAdapter(requireActivity())
        sepAdapter = ListAdapter(requireActivity())
        octAdapter = ListAdapter(requireActivity())
        novAdapter = ListAdapter(requireActivity())
        decAdapter = ListAdapter(requireActivity())
    }

    private fun initManager() {
        val janManager = GridLayoutManager(requireActivity(), 4)
        binding.janView.bind.birthRv.layoutManager = janManager
        binding.janView.bind.birthRv.adapter = janAdapter

        val febManager = GridLayoutManager(requireActivity(), 4)
        binding.febView.bind.birthRv.layoutManager = febManager
        binding.febView.bind.birthRv.adapter = febAdapter

        val marchManager = GridLayoutManager(requireActivity(), 4)
        binding.marchView.bind.birthRv.layoutManager = marchManager
        binding.marchView.bind.birthRv.adapter = marchAdapter

        val aprilManager = GridLayoutManager(requireActivity(), 4)
        binding.aprilView.bind.birthRv.layoutManager = aprilManager
        binding.aprilView.bind.birthRv.adapter = aprilAdapter

        val mayManager = GridLayoutManager(requireActivity(), 4)
        binding.mayView.bind.birthRv.layoutManager = mayManager
        binding.mayView.bind.birthRv.adapter = mayAdapter

        val juneManager = GridLayoutManager(requireActivity(), 4)
        binding.juneView.bind.birthRv.layoutManager = juneManager
        binding.juneView.bind.birthRv.adapter = juneAdapter

        val julyManager = GridLayoutManager(requireActivity(), 4)
        binding.julyView.bind.birthRv.layoutManager = julyManager
        binding.julyView.bind.birthRv.adapter = julyAdapter

        val augManager = GridLayoutManager(requireActivity(), 4)
        binding.augView.bind.birthRv.layoutManager = augManager
        binding.augView.bind.birthRv.adapter = augAdapter

        val sepManager = GridLayoutManager(requireActivity(), 4)
        binding.sepView.bind.birthRv.layoutManager = sepManager
        binding.sepView.bind.birthRv.adapter = sepAdapter

        val octManager = GridLayoutManager(requireActivity(), 4)
        binding.octView.bind.birthRv.layoutManager = octManager
        binding.octView.bind.birthRv.adapter = octAdapter

        val novManager = GridLayoutManager(requireActivity(), 4)
        binding.novView.bind.birthRv.layoutManager = novManager
        binding.novView.bind.birthRv.adapter = novAdapter

        val decManager = GridLayoutManager(requireActivity(), 4)
        binding.decView.bind.birthRv.layoutManager = decManager
        binding.decView.bind.birthRv.adapter = decAdapter
    }

    private fun sortedRvByMonth(list: List<CharacInfo>) {
        val janSurList: ArrayList<CharacInfo> = ArrayList()
        val febSurList: ArrayList<CharacInfo> = ArrayList()
        val marchSurList: ArrayList<CharacInfo> = ArrayList()
        val aprilSurList: ArrayList<CharacInfo> = ArrayList()
        val maySurList: ArrayList<CharacInfo> = ArrayList()
        val juneSurList: ArrayList<CharacInfo> = ArrayList()
        val julySurList: ArrayList<CharacInfo> = ArrayList()
        val augSurList: ArrayList<CharacInfo> = ArrayList()
        val sepSurList: ArrayList<CharacInfo> = ArrayList()
        val octSurList: ArrayList<CharacInfo> = ArrayList()
        val novSurList: ArrayList<CharacInfo> = ArrayList()
        val decSurList: ArrayList<CharacInfo> = ArrayList()

        for (c_list in list) {
            when (c_list.birth.substring(0 until 2).toInt()) {
                1 -> janSurList.add(c_list)
                2 -> febSurList.add(c_list)
                3 -> marchSurList.add(c_list)
                4 -> aprilSurList.add(c_list)
                5 -> maySurList.add(c_list)
                6 -> juneSurList.add(c_list)
                7 -> julySurList.add(c_list)
                8 -> augSurList.add(c_list)
                9 -> sepSurList.add(c_list)
                10 -> octSurList.add(c_list)
                11 -> novSurList.add(c_list)
                12 -> decSurList.add(c_list)
            }

            janAdapter.setData(janSurList)
            febAdapter.setData(febSurList)
            marchAdapter.setData(marchSurList)
            aprilAdapter.setData(aprilSurList)
            mayAdapter.setData(maySurList)
            juneAdapter.setData(juneSurList)
            julyAdapter.setData(julySurList)
            augAdapter.setData(augSurList)
            sepAdapter.setData(sepSurList)
            octAdapter.setData(octSurList)
            novAdapter.setData(novSurList)
            decAdapter.setData(decSurList)
        }

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