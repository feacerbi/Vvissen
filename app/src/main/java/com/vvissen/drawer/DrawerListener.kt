package com.vvissen.drawer

interface DrawerListener {
    fun onFilterSelected(pair: Pair<DrawerItem, Int>)
    fun clearFilters()
}