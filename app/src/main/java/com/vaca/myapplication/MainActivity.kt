package com.vaca.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android_serialport_api.SerialPort
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this@MainActivity, UARTService::class.java))

        handle.postDelayed(send, 2000)
    }

    val handle=Handler()
    val send= object:Runnable{
        override fun run() {
            UARTService.CarInfoPort?.let { writeCom(it, "lala".toByteArray()) }
//            writeCom(UARTService.GPSPort, "vaca22".toByteArray())
            handle.postDelayed(this, 2000)
        }

    }




//        writeCom(UARTService.CarInfoPort, "lala".toByteArray())
//        writeCom(UARTService.GPSPort, "vaca22".toByteArray())
//        handle.postDelayed(this, 2000)
//    }

    private fun writeCom(serialPort: SerialPort,
                         content: ByteArray) {
        val mOutputStream = serialPort.outputStream
        try {
            mOutputStream.write(content)
            mOutputStream.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}