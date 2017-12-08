package com.vvissen

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import java.io.Serializable
import java.util.*
import kotlin.reflect.KClass

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ByteArray.toHexString(): String {
    var i: Int
    var j = 0
    var `in`: Int
    val hex = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
    var out = ""

    while (j < this.size) {
        `in` = this[j].toInt() and 0xff
        i = `in` shr 4 and 0x0f
        out += hex[i]
        i = `in` and 0x0f
        out += hex[i]
        j++
    }
    return out
}

fun Long.toFormatedDate() = this.toString().toFormatedDate()

fun String.toFormatedDate(): String {
    val currentTime = Calendar.getInstance()
    val reqTime = Calendar.getInstance()
    reqTime.timeInMillis = this.toLong()

    if(reqTime[Calendar.YEAR] != currentTime[Calendar.YEAR]) {
        return DateFormat.format("MMM dd, yyyy 'at' h:mm a", reqTime).toString()
    }

    if(reqTime[Calendar.DAY_OF_MONTH] != currentTime[Calendar.DAY_OF_MONTH]) {
        return DateFormat.format("MMM dd 'at' h:mm a", reqTime).toString()
    }

    if(reqTime[Calendar.HOUR_OF_DAY] != currentTime[Calendar.HOUR_OF_DAY]) {
        val hoursPassed = currentTime[Calendar.HOUR_OF_DAY] - reqTime[Calendar.HOUR_OF_DAY]
        return hoursPassed.toString() + if(hoursPassed == 1) " h" else " hrs"
    }

    if(reqTime[Calendar.MINUTE] != currentTime[Calendar.MINUTE]) {
        val minsPassed = currentTime[Calendar.MINUTE] - reqTime[Calendar.MINUTE]
        return minsPassed.toString()  + if(minsPassed == 1) " min" else " mins"
    }

    return "Just now"
}

fun Long.toPeriodDate(): String = DateFormat.format("MMM dd", this).toString()

fun String.toFormatedWebsite(): String {
    if(this.contains("www") || !this.contains("//")) return this

    return this.replace("//", "//www.", true)
}

fun String.noDecimals() = this.substring(0, this.lastIndexOf('.'))

fun Long.toDistanceUnits(): String {
    var unit = "m"
    var value = this.toFloat()
    var precision = "%.0f"

    if(value / 1000 > 1) {
        unit = "km"
        value /= 1000
        precision = "%.1f"
    }

    return "$precision %s".format(value, unit)
}

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

fun Calendar.rollDays(amount: Int): Calendar {
    set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH) + amount)
    return this
}

fun AlertDialog.Builder.showOneChoiceCancelableDialog(
        title: String,
        message: String,
        buttonTitle: String,
        func: (DialogInterface, Int) -> Unit) {
    setTitle(title)
    setMessage(message)
    setPositiveButton(buttonTitle, func)
    setNeutralButton("Cancel") { _, _ -> }
    show()
}

fun AlertDialog.Builder.showTextDialog(
        title: String,
        message: String) {
    setTitle(title)
    setMessage(message)
    setPositiveButton("OK") { _, _ -> }
    show()
}

fun AlertDialog.Builder.showTwoChoiceCancelableDialog(
        title: String,
        message: String,
        buttonOneTitle: String,
        buttonTwoTitle: String,
        funcOne: (DialogInterface, Int) -> Unit,
        funcTwo: (DialogInterface, Int) -> Unit) {
    setTitle(title)
    setMessage(message)
    setPositiveButton(buttonOneTitle, funcOne)
    setNegativeButton(buttonTwoTitle, funcTwo)
    setNeutralButton("Cancel") { _, _ ->  }
    show()
}

fun AlertDialog.Builder.showInputDialog(
        title: String,
        buttonTitle: String,
        inputView: View,
        func: (DialogInterface, Int) -> Unit) {
    setView(inputView)
    setTitle(title)
    setPositiveButton(buttonTitle, func)
    setNegativeButton("Cancel") { _, _ -> }
    show()
}

fun AlertDialog.Builder.showListDialog(
        title: String,
        items: Array<String>,
        selected: Int = 0,
        func: (DialogInterface, Int) -> Unit) {
    setTitle(title)
    setSingleChoiceItems(items, selected, func)
    show()
}

fun AlertDialog.Builder.showAdapterDialog(
        title: String,
        adapter: ListAdapter,
        func: (DialogInterface, Int) -> Unit) {
    setTitle(title)
    setAdapter(adapter, func)
    show()
}

fun AlertDialog.Builder.showConfirmAdapterDialog(
        title: String,
        adapter: ListAdapter,
        buttonTitle: String,
        adapterFunc: (DialogInterface, Int) -> Unit,
        buttonFunc: (DialogInterface, Int) -> Unit) {
    setTitle(title)
    setAdapter(adapter, adapterFunc)
    setPositiveButton(buttonTitle, buttonFunc)
    setNegativeButton("Cancel") { _, _ -> }
    show()
}

fun AlertDialog.Builder.showCustomDialog(
        inputView: View) {
    setView(inputView)
    setTitle("")
    show()
}

fun FloatingActionButton.setUp(context: Context, show: Boolean, resource: Int, action: () -> Unit) {
    if(show) {
        show()
        setImageDrawable(resources.getDrawable(resource, context.theme))
        setOnClickListener { action() }
    } else {
        hide()
    }
}

//fun RecyclerView.getFirebaseAdapter(): FirebaseRecyclerAdapter<*, *>? {
//    if(adapter != null) {
//        return adapter as FirebaseRecyclerAdapter<*, *>
//    }
//    return null
//}
//
//fun Bundle.makeQueryBundle(query: Query): Bundle {
//    putString(PetsListFragment.DATABASE_REFERENCE, query.toString().removePrefix("https://buddies-5d07f.firebaseio.com/"))
//    return this
//}

fun Fragment.transact(activity: AppCompatActivity, id: Int, bundle: Bundle? = null) {
    val transaction = activity.supportFragmentManager.beginTransaction()

    if(bundle != null) arguments = bundle

    transaction.replace(id, this)
    transaction.commit()
}

fun Fragment.create(bundle: Bundle? = null): Fragment {
    if(bundle != null) arguments = bundle
    return this
}

fun android.app.Fragment.transact(activity: AppCompatActivity, id: Int, bundle: Bundle? = null) {
    val transaction = activity.fragmentManager.beginTransaction()

    if(bundle != null) arguments = bundle

    transaction.replace(id, this)
    transaction.commit()
}

fun <T : Any> Fragment.launchActivityWithExtras(
        clazz: KClass<T>,
        identifiers: Array<String>?,
        extras: Array<Any>?,
        forResult: Boolean = false,
        resultIdentifier: Int = -1) {
    val intent = Intent(activity, clazz.java)

    if(identifiers != null && extras != null) {
        for (extra in extras) {
            if (extra is String) {
                intent.putExtra(identifiers[extras.indexOf(extra)], extra)
            } else if (extra is Int) {
                intent.putExtra(identifiers[extras.indexOf(extra)], extra)
            } else if (extra is Boolean) {
                intent.putExtra(identifiers[extras.indexOf(extra)], extra)
            } else if(extra is Serializable) {
                intent.putExtra(identifiers[extras.indexOf(extra)], extra)
            }
        }
    }

    if(forResult) {
        startActivityForResult(intent, resultIdentifier)
    } else {
        startActivity(intent)
    }
}

fun <T : Any> Activity.launchActivity(clazz: KClass<T>) {
    val intent = Intent(this, clazz.java)
    startActivity(intent)
}

fun <T : Any> Activity.launchActivityWithExtras(
        clazz: KClass<T>,
        identifiers: Array<String>?,
        extras: Array<Any>?,
        forResult: Boolean = false,
        resultIdentifier: Int = -1) {
    val intent = Intent(this, clazz.java)

    if(identifiers != null && extras != null) {
        for (extra in extras) {
            if (extra is String) {
                intent.putExtra(identifiers[extras.indexOf(extra)], extra)
            } else if (extra is Int) {
                intent.putExtra(identifiers[extras.indexOf(extra)], extra)
            } else if (extra is Boolean) {
                intent.putExtra(identifiers[extras.indexOf(extra)], extra)
            } else if(extra is Parcelable) {
                intent.putExtra(identifiers[extras.indexOf(extra)], extra)
            }
        }
    }

    if(forResult) {
        startActivityForResult(intent, resultIdentifier)
    } else {
        startActivity(intent)
    }
}

fun <T : Any> Activity.launchActivityForResult(clazz: KClass<T>, identifier: Int) {
    val intent = Intent(this, clazz.java)
    startActivityForResult(intent, identifier)
}

fun <T : Any> Activity.launchActivityAndFinish(clazz: KClass<T>) {
    launchActivity(clazz)
    finish()
}

