package com.vvissen

interface DrawerController {
    fun openDrawer()
    fun closeDrawer()
    fun toggleDrawer()
    fun isDrawerOpened(): Boolean
}