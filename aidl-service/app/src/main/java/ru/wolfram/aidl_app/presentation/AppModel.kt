package ru.wolfram.aidl_app.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.wolfram.aidl_app.ISortService
import kotlin.random.Random

class AppModel {
    private var service: ISortService? = null
    private val _ints = MutableStateFlow(intArrayOf())
    val ints = _ints.asStateFlow()

    private val _servicePid = MutableSharedFlow<Int>(replay = 1)
    val servicePid = _servicePid.asSharedFlow()

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName,
            service: IBinder
        ) {
            this@AppModel.service = ISortService.Stub.asInterface(service)
            this@AppModel.service?.let { ser ->
                _servicePid.tryEmit(ser.pid)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
        }

    }

    fun bindService(context: Context) {
        Intent(context, SortService::class.java).also { intent ->
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun generate() {
        _ints.update {
            (1..25)
                .map { Random.nextInt(1, 100) }
                .toIntArray()
        }
    }

    fun sort() {
        service?.let { ser ->
            _ints.update {
                ser.sort(_ints.value)
            }
        }
    }

}