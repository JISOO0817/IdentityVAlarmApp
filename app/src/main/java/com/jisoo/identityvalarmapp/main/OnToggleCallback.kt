package com.jisoo.identityvalarmapp.main

import com.jisoo.identityvalarmapp.model.CharacInfo

interface OnToggleCallback {

    fun onToggle(info: CharacInfo)
}