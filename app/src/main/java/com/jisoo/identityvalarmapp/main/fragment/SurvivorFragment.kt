package com.jisoo.identityvalarmapp.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jisoo.identityvalarmapp.databinding.FragmentSurvivorBinding
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.main.OnClickCallback
import com.jisoo.identityvalarmapp.model.CharacInfo

class SurvivorFragment : Fragment(), OnClickCallback{

    private lateinit var binding: FragmentSurvivorBinding
    private lateinit var adapter: ListAdapter
    private lateinit var afterAdapter : ListAdapter
    private val model: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurvivorBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListAdapter(requireContext())
        afterAdapter = ListAdapter(requireContext())
        adapter.changeOnOffMode(this)
        val gridManager = GridLayoutManager(requireContext(),3)
        binding.soonBirthRv.layoutManager = gridManager
        binding.soonBirthRv.adapter = adapter
        val gridManager2 = GridLayoutManager(requireContext(),3)
        binding.afterBirthRv.layoutManager = gridManager2
        binding.afterBirthRv.adapter = afterAdapter


        model.surList.observe(viewLifecycleOwner, {
//            adapter.setData(it)

//            model.init()
            model.checkBirthDayPassed()

        })

        model.beforeSurList.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        model.afterSurList.observe(viewLifecycleOwner, {
            afterAdapter.setData(it)
        })


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewClick(info: CharacInfo) {
        model.update(info)
        if (info.onoff) {
            Log.d("jjs","on")
            info.executionAlarm(requireContext())
        } else {
            Log.d("jjs","off")
            info.cancelAlarm(requireContext())
        }
    }
}