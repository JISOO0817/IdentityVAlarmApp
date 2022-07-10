//package com.jisoo.identityvalarmapp.main.charac_fragment
//
//import android.os.Bundle
//import android.util.DisplayMetrics
//import android.util.Log
//import android.util.TypedValue
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.GridLayoutManager
//import com.bumptech.glide.Glide
//import com.jisoo.identityvalarmapp.R
//import com.jisoo.identityvalarmapp.databinding.DialogBirthinfoBinding
//import com.jisoo.identityvalarmapp.databinding.FragmentHunterBinding
//import com.jisoo.identityvalarmapp.main.EmptyDataObserver
//import com.jisoo.identityvalarmapp.main.MainViewModel
//import com.jisoo.identityvalarmapp.main.OnClickCallback
//import com.jisoo.identityvalarmapp.model.CharacInfo
//import com.jisoo.identityvalarmapp.util.dialog.BirthInfoDialog
//import com.jisoo.identityvalarmapp.util.dialog.DialogSize
//import com.jisoo.identityvalarmapp.util.dialog.Margin
//import kotlin.math.roundToInt
//
//class HunterFragment : Fragment(), OnClickCallback {
//
//    private lateinit var binding : FragmentHunterBinding
//    private lateinit var todayHunAdapter: ListAdapter
//    private lateinit var hunadapter: ListAdapter
//    private lateinit var passedHunAdapter: ListAdapter
//    private val model: MainViewModel by viewModels()
//
//    private lateinit var birthInfoBinding: DialogBirthinfoBinding
//    private lateinit var birthInfoDialog: BirthInfoDialog
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//       binding = FragmentHunterBinding.inflate(inflater,container,false)
//        birthInfoBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_birthinfo, null, false)
//        return binding.root
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val emptyObserver = EmptyDataObserver(binding.todayBirthRv, binding.todayNoItem)
//        val emptyObserver2 = EmptyDataObserver(binding.soonBirthRv, binding.beforeNoItem)
//        val emptyObserver3 = EmptyDataObserver(binding.passedBirthRv, binding.passedNoItem)
//
//        birthInfoDialog = BirthInfoDialog(requireContext(),birthInfoBinding) {
//            if(birthInfoDialog.isShowing) {
//                birthInfoDialog.dismiss()
//            }
//        }
//
//        todayHunAdapter = ListAdapter(requireContext())
//        todayHunAdapter.registerAdapterDataObserver(emptyObserver)
//        todayHunAdapter.onClickItem(this)
//        hunadapter = ListAdapter(requireContext())
//        hunadapter.registerAdapterDataObserver(emptyObserver2)
//        hunadapter.onClickItem(this)
//        passedHunAdapter = ListAdapter(requireContext())
//        passedHunAdapter.registerAdapterDataObserver(emptyObserver3)
//        passedHunAdapter.onClickItem(this)
//
//        val todayManager = GridLayoutManager(requireContext(), 1)
//        binding.todayBirthRv.layoutManager = todayManager
//        binding.todayBirthRv.adapter = todayHunAdapter
//
//        val gridManager = GridLayoutManager(requireContext(), 4)
//        binding.soonBirthRv.layoutManager = gridManager
//        binding.soonBirthRv.adapter = hunadapter
//
//        val afterGridManager = GridLayoutManager(requireContext(), 4)
//        binding.passedBirthRv.layoutManager = afterGridManager
//        binding.passedBirthRv.adapter = passedHunAdapter
////
////        model.hunList.observe(viewLifecycleOwner, {
////            model.checkBirthDayPassed2()
////        })
//
//        model.todayHunList.observe(viewLifecycleOwner, {
//            todayHunAdapter.setData(it)
//            Log.d("jjs","it.size:${it.size}")
//        })
//
//        model.beforeHunList.observe(viewLifecycleOwner, {
//            hunadapter.setData(it)
//        })
//
//        model.passedHunList.observe(viewLifecycleOwner, {
//            passedHunAdapter.setData(it)
//        })
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//        model.checkBirthDayPassed()
//    }
//
//    override fun onViewClick(info: CharacInfo) {
//        showBirthInfoDialog(info)
//    }
//
//    private fun showBirthInfoDialog(info: CharacInfo) {
//        birthInfoDialog.show()
//        DialogSize.initDialogLayout(birthInfoDialog,requireContext())
//        birthInfoDialog.setFontSize(16f,14f)
//        birthInfoDialog.setMargin(
//            setTypedValue(5f,0f,0f,5f),
//            setTypedValue(5f,0f,0f,0f),
//            setTypedValue(5f,30f,0f,0f)
//        )
//
//        Glide.with(requireContext())
//            .load(R.drawable.ic_launcher_background)
//            .into(birthInfoBinding.closeIv)
//
//        birthInfoBinding.jobTv.text = info.job
//        birthInfoBinding.birthTv.text = info.birth
//    }
//
//    private fun setTypedValue(topMargin: Float, bottomMargin: Float, leftMargin: Float, rightMargin: Float) : Margin {
//        return Margin(
//            TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                getConvertDpByRes(topMargin).roundToInt().toFloat(),
//                resources.displayMetrics
//            ).toInt(),
//            TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                getConvertDpByRes(bottomMargin).roundToInt().toFloat(),
//                resources.displayMetrics
//            ).toInt(),
//            TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                getConvertDpByRes(leftMargin).roundToInt().toFloat(),
//                resources.displayMetrics
//            ).toInt(),
//            TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                getConvertDpByRes(rightMargin).roundToInt().toFloat(),
//                resources.displayMetrics
//            ).toInt()
//        )
//    }
//
//    private fun getConvertDpByRes(dpSize: Float): Float {
//        val weight: Float
//        val dm = DisplayMetrics()
//        requireActivity().windowManager.defaultDisplay.getMetrics(dm)
//        val width = dm.widthPixels
//        val wi = width.toDouble() / dm.xdpi.toDouble()
//        weight = (wi / 2.86817851).toFloat()
//        return dpSize * weight
//    }
//
//
//}