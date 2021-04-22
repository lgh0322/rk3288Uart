package com.vaca.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android_serialport_api.IDataDeal
import android_serialport_api.ReceiveComUtils
import android_serialport_api.SerialPort
import java.io.File
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UARTService : Service() {
    private var executor: ExecutorService? = null
    private var CarInfoUtils: ReceiveComUtils? = null
    private val GPSUtils: ReceiveComUtils? = null
    private val carInfoReceive = CarInfoReceive()



    inner class CarInfoReceive : IDataDeal {
        @Synchronized
        override fun onDataReceived(buffer: ByteArray, size: Int) {
            val back = ByteArray(size)
            System.arraycopy(buffer, 0, back, 0, size)
            Log.e("dsf", String(back))
        }
    }

    override fun onCreate() {
        super.onCreate()
        executor = Executors.newCachedThreadPool()
        try {
            CarInfoPort = SerialPort(
                File("/dev/ttyS1"),
                115200, 0
            )
            CarInfoUtils = ReceiveComUtils(CarInfoPort, carInfoReceive)
        } catch (e: IOException) {
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("HUANG", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("HUANG", "onDestroy")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i("HUANG", "onBind")
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.i("HUANG", "onUnbind")
        return super.onUnbind(intent)
    }

    companion object {
        val mLock = Any()
        var CarInfoPort: SerialPort? = null
        var GPSPort: SerialPort? = null
    }
}