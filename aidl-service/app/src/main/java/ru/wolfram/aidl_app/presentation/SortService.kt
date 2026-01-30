package ru.wolfram.aidl_app.presentation

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import ru.wolfram.aidl_app.ISortService

class SortService : Service() {
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private val binder = object : ISortService.Stub() {
        override fun sort(ints: IntArray): IntArray {
            return ints.sorted().toIntArray()
        }

        override fun getPid(): Int {
            return Process.myPid()
        }
    }

}