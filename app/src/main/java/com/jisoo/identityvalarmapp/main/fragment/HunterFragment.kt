package com.jisoo.identityvalarmapp.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jisoo.identityvalarmapp.databinding.FragmentHunterBinding
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.main.OnClickCallback
import com.jisoo.identityvalarmapp.model.CharacInfo

class HunterFragment : Fragment(), OnClickCallback {

    private lateinit var binding : FragmentHunterBinding
    private lateinit var adapter: ListAdapter
    private val model: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentHunterBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListAdapter(context = requireContext())
        adapter.changeOnOffMode(this)
        binding.hunterRv.adapter  = adapter

        model.hunList.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

    }

    override fun onViewClick(info: CharacInfo) {
        model.update(info)
        if (info.onoff) {
            info.executionAlarm(requireContext())
        } else {
            info.cancelAlarm(requireContext())
        }
    }

}