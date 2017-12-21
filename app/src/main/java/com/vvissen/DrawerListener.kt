package com.vvissen

interface DrawerListener {
    fun onFilterSelected(pair: Pair<DrawerItem, Int>)
    fun cleanFilters()
}