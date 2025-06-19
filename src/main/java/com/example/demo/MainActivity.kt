package com.example.demo

import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.ICalculatorService

class MainActivity : AppCompatActivity() {

    private var calculatorService: ICalculatorService? = null
    private var isBound = false
    private lateinit var display: TextView
    private var expression = ""

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            calculatorService = ICalculatorService.Stub.asInterface(binder)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            calculatorService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.textDisplay)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerButtons)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        val buttons = listOf("7","8","9","+","4","5","6","-","1","2","3","*","0","C","=","/")
        recyclerView.adapter = ButtonAdapter(buttons) { button ->
            when (button) {
                "=" -> {
                    if (isBound) {
                        val result = calculatorService?.calculate(expression) ?: 0
                        display.text = result.toString()
                        expression = ""
                    }
                }
                "C" -> {
                    expression = ""
                    display.text = ""
                }
                else -> {
                    expression += button
                    display.text = expression
                }
            }
        }

        val intent = Intent(this, CalculatorService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        if (isBound) unbindService(serviceConnection)
        super.onDestroy()
    }
}
