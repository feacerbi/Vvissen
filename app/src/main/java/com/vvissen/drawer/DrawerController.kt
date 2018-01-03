package com.vvissen.drawer

interface DrawerController {
    fun openDrawer()
    fun closeDrawer()
    fun toggleDrawer()
    fun isDrawerOpened(): Boolean
}