package com.nzshitdevelop.calcnooop


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.media.SoundPool
class Main : AppCompatActivity() {
    // ------Обьявление переменных----------
    private lateinit var sp: SoundPool
    private var click: Int = 0
    private lateinit var Display: TextView
    private var num1: Float = 0f
    private var oper: String = ""
    private var isnewoper: Boolean = true
    // -----------Тут я обьявляю саунд пул-------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = SoundPool.Builder().setMaxStreams(5).build()
        click = sp.load(this, R.raw.clock, 1)
        Display = findViewById(R.id.Display)
        val numbers = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        numbers.forEach { num ->
            findViewById<Button>(get("btn$num")).setOnClickListener {
                sp.play(click, 1f, 1f, 1, 0, 1f)
                ooop (num.toString())
            }
        }

        // ---------Кнопки с саундпулом------------
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            sp.play(click, 1f, 1f, 1, 0, 1f)
            onclickoper("+")}
        findViewById<Button>(R.id.btnSub).setOnClickListener {
            sp.play(click, 1f, 1f, 1, 0, 1f)
            onclickoper("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener {
            sp.play(click, 1f, 1f, 1, 0, 1f)
            onclickoper("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener {
            sp.play(click, 1f, 1f, 1, 0, 1f)
            onclickoper("/") }
        findViewById<Button>(R.id.btnPerc).setOnClickListener {
            sp.play(click, 1f, 1f, 1, 0, 1f)
            onclickoper ("%") }
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            sp.play(click, 1f, 1f, 1, 0, 1f)
            calculateResult() }
        findViewById<Button>(R.id.btnC).setOnClickListener {
            sp.play(click, 1f, 1f, 1, 0, 1f)
            clear() }
        findViewById<Button>(R.id.btnDot).setOnClickListener {
            sp.play(click, 1f, 1f, 1, 0, 1f)
            odc() }
    }
    private fun get(name: String): Int {
        return resources.getIdentifier(name, "id", packageName)
    }
    private fun ooop(number: String) {
        if (isnewoper) {
            Display.text = number
            isnewoper = false
        } else {

            if (Display.text == "0" && number != ".") { // <-- защита от ввода
                Display.text = number
            } else {
                Display.text = Display.text.toString() + number
            }
        }
    }
    fun odc() {
        if (isnewoper) {
            Display.text = "0."
            isnewoper = false
        } else if (!Display.text.toString().contains(".")) {
            Display.text = Display.text.toString() + "."
        }
    }
    //
    fun onclickoper(op: String) {
        try {
            num1 = Display.text.toString().toFloat()
            oper = op
            isnewoper = true
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show() // <- Проверка
        }
    }
    // Результат
    fun calculateResult() {
        try {
            val num2 = Display.text.toString().toFloat()
            var result: Float

            when (oper) {
                "+" -> result = num1 + num2
                "-" -> result = num1 - num2
                "*" -> result = num1 * num2
                "%" -> result = num1 % num2
                "/" -> {
                    if (num2 != 0.0f) {
                        result = num1 / num2
                    } else {
                        Toast.makeText(this, "0 делить нельзя!", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                else -> return
            }

            // Что бы не было "колапса" при делении чисел которые не делятся, к примеру 10/3
            Display.text = String.format("%.10f", result).trimEnd('0').trimEnd('.').trimEnd(',') // <- trimEnd убирает все знаки после целого числа, что бы не было после 10+5 = 15. или 15,
            isnewoper = true
            oper = ""

        } catch (e: Exception) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun clear(){
        Display.text = "0"
        num1 = 0f
        oper = ""
        isnewoper = true
    }
}