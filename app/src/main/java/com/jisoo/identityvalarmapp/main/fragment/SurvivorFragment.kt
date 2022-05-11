package com.jisoo.identityvalarmapp.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jisoo.identityvalarmapp.databinding.FragmentSurvivorBinding
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.main.OnToggleCallback
import com.jisoo.identityvalarmapp.model.CharacInfo

class SurvivorFragment : Fragment(), OnToggleCallback {

    private lateinit var binding: FragmentSurvivorBinding
    private lateinit var adapter: ListAdapter
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

        adapter = ListAdapter()
        adapter.addOnToggle(this)
        binding.survivorRv.adapter = adapter

        model.surList.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
    }

    override fun onToggle(info: CharacInfo) {
        Log.d("jjs","ontoggle")
        model.update(info)
        if(info.onoff) {
            Log.d("jjs","on")
            info.executionAlarm(requireContext())
        } else {
            Log.d("jjs","off")
            info.cancelAlarm(requireContext())
        }
    }
}