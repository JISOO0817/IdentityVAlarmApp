package com.jisoo.identityvalarmapp.main.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.databinding.DialogBirthinfoBinding
import com.jisoo.identityvalarmapp.databinding.FragmentSurvivorBinding
import com.jisoo.identityvalarmapp.main.EmptyDataObserver
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.main.OnClickCallback
import com.jisoo.identityvalarmapp.model.CharacInfo
import com.jisoo.identityvalarmapp.util.dialog.BirthInfoDialog
import com.jisoo.identityvalarmapp.util.dialog.DialogSize
import com.jisoo.identityvalarmapp.util.dialog.Margin
import kotlin.math.roundToInt

class SurvivorFragment : Fragment(), OnClickCallback {

    private lateinit var binding: FragmentSurvivorBinding
    private lateinit var todayAdapter: ListAdapter
    private lateinit var adapter: ListAdapter
    private lateinit var afterAdapter: ListAdapter
    private val model: MainViewModel by viewModels()

    private lateinit var info: CharacInfo

    private lateinit var birthinfoBinding: DialogBirthinfoBinding
    private lateinit var birthInfoDialog: BirthInfoDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurvivorBinding.inflate(inflater, container, false)
        birthinfoBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_birthinfo, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emptyObserver = EmptyDataObserver(binding.todayBirthRv, binding.todayNoItem)
        val emptyObserver2 = EmptyDataObserver(binding.soonBirthRv, binding.beforeNoItem)
        val emptyObserver3 = EmptyDataObserver(binding.passedBirthRv, binding.passedNoItem)

        birthInfoDialog = BirthInfoDialog(requireContext(),birthinfoBinding) {
            if(birthInfoDialog.isShowing) {
                birthInfoDialog.dismiss()
            }
        }

        todayAdapter = ListAdapter(requireContext())
        todayAdapter.registerAdapterDataObserver(emptyObserver)
        todayAdapter.onClickItem(this)
        adapter = ListAdapter(requireContext())
        adapter.registerAdapterDataObserver(emptyObserver2)
        adapter.onClickItem(this)
        afterAdapter = ListAdapter(requireContext())
        afterAdapter.registerAdapterDataObserver(emptyObserver3)
        afterAdapter.onClickItem(this)

        val todayManager = GridLayoutManager(requireContext(), 1)
        binding.todayBirthRv.layoutManager = todayManager
        binding.todayBirthRv.adapter = todayAdapter

        val gridManager = GridLayoutManager(requireContext(), 4)
        binding.soonBirthRv.layoutManager = gridManager
        binding.soonBirthRv.adapter = adapter

        val afterGridManager = GridLayoutManager(requireContext(), 4)
        binding.passedBirthRv.layoutManager = afterGridManager
        binding.passedBirthRv.adapter = afterAdapter

        model.surList.observe(viewLifecycleOwner, {
            model.checkBirthDayPassed()
        })

        model.todaySurList.observe(viewLifecycleOwner, {
            todayAdapter.setData(it)
        })

        model.beforeSurList.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        model.passedSurList.observe(viewLifecycleOwner, {
            afterAdapter.setData(it)
        })

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewClick(info: CharacInfo) {
        showBirthInfoDialog(info)

//        model.update(info)
//        if (info.onoff) {
//            Log.d("jjs", "on")
//            info.executionAlarm(requireContext())
//        } else {
//            Log.d("jjs", "off")
//            info.cancelAlarm(requireContext())
//        }

    }

    private fun showBirthInfoDialog(info: CharacInfo) {
        birthInfoDialog.show()
        DialogSize.initDialogLayout(birthInfoDialog,requireContext())
        birthInfoDialog.setFontSize(16f,14f)
        birthInfoDialog.setMargin(
            setTypedValue(5f,0f,0f,5f),
            setTypedValue(5f,0f,0f,0f),
            setTypedValue(5f,30f,0f,0f)
        )

        Glide.with(requireContext())
            .load(R.drawable.ic_launcher_background)
            .into(birthinfoBinding.closeIv)

        birthinfoBinding.jobTv.text = info.job
        birthinfoBinding.birthTv.text = info.birth
    }

    private fun setTypedValue(topMargin: Float, bottomMargin: Float, leftMargin: Float, rightMargin: Float) : Margin {
        return Margin(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(topMargin).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(bottomMargin).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(leftMargin).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(rightMargin).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt()
        )
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


}