package com.example.demo
import android.app.Service
import android.content.Intent
import android.os.IBinder

import kotlin.math.*
import com.example.demo.ICalculatorService

class CalculatorService : Service() {
    private val binder = object : ICalculatorService.Stub() {
        override fun calculate(expression: String): Int {
            try {
                // 简化处理，仅支持个位数加减乘除
                val tokens = Regex("([0-9])([+\\-*/])([0-9])").find(expression)
                tokens?.let {
                    val (a, op, b) = it.destructured
                    val x = a.toInt()
                    val y = b.toInt()
                    return when (op) {
                        "+" -> x + y
                        "-" -> x - y
                        "*" -> x * y
                        "/" -> if (y != 0) x / y else 0
                        else -> 0
                    }
                }
                return 0
            } catch (e: Exception) {
                e.printStackTrace()
                return 0
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = binder
}
