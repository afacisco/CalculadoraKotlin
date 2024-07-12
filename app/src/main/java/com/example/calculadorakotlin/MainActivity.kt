package com.example.calculadorakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.calculadorakotlin.databinding.ActivityMainBinding
import java.text.DecimalFormat

/*
Autor: Juan Francisco Sánchez González
Fecha: 29/06/2024
Clase: Actividad principal que utiliza View-Binding y la interface View.OnClickListener para el evento
OnClick de los botones. Se trata de una calculadora sencilla de 1 operador, se implementan los 2 themes
(claro y oscuro) y las 2 vistas para la rotación del dispositivo.
*/

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // En base al nombre del archivo layout .xml, en este caso: activity_main.xml = ActivityMainBinding
    private lateinit var binding: ActivityMainBinding

    private var primerNum = 0.0
    private var segundoNum = 0.0
    private var operacion: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Preaprar el uso de View-Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        operacion = null

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btnComa.setOnClickListener(this)
        binding.btnMas.setOnClickListener(this)
        binding.btnMenos.setOnClickListener(this)
        binding.btnMult.setOnClickListener(this)
        binding.btnDiv.setOnClickListener(this)
        binding.btnLimpiar.setOnClickListener(this)
        binding.btnIgual.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btn0 -> pulsarNumero("0")
            binding.btn1 -> pulsarNumero("1")
            binding.btn2 -> pulsarNumero("2")
            binding.btn3 -> pulsarNumero("3")
            binding.btn4 -> pulsarNumero("4")
            binding.btn5 -> pulsarNumero("5")
            binding.btn6 -> pulsarNumero("6")
            binding.btn7 -> pulsarNumero("7")
            binding.btn8 -> pulsarNumero("8")
            binding.btn9 -> pulsarNumero("9")
            binding.btnComa -> pulsarNumero(".")
            binding.btnDiv -> pulsarOperacion("/")
            binding.btnIgual -> pulsarIgual()
            binding.btnMult -> pulsarOperacion("*")
            binding.btnMenos -> pulsarOperacion("-")
            binding.btnLimpiar -> pulsarBorrar()
            binding.btnMas -> pulsarOperacion("+")
        }
    }

    private fun pulsarNumero(num: String) {
        renderPantalla(num)
        checkoperacion()
    }

    private fun pulsarBorrar() {
        binding.pantalla.text ="0"
        primerNum = 0.0
        segundoNum = 0.0
    }

    private fun pulsarOperacion(op: String) {
        operacion = op
        primerNum = binding.pantalla.text.toString().toDouble()

        binding.pantalla.text = "0"
    }

    private fun pulsarIgual() {
        val result = when(operacion) {
            "+" -> primerNum + segundoNum
            "-" -> primerNum - segundoNum
            "*" -> primerNum * segundoNum
            "/" -> primerNum / segundoNum
            else -> 0
        }
        operacion = null
        primerNum = result.toDouble()
        binding.pantalla.text = if (result.toString().endsWith(".0")) {
            result.toString().replace(".0", "")
        } else {
            try {
               //"%2.f".format(result)
                val df: DecimalFormat = DecimalFormat("#.##")
                df.maximumFractionDigits = 2
                df.format(result)
            } catch (e:Exception) {
                Log.d("::ERROR FORMAT RESULT", e.printStackTrace().toString())
                null
            }
        }
    }

    private fun renderPantalla(num: String) {
        val result:String = if (binding.pantalla.text == "0" && num != ".") {
            num
        } else {
            "${binding.pantalla.text}$num"
        }
        binding.pantalla.text = result
    }

    private fun checkoperacion() {
        if (operacion == null) {
            primerNum = binding.pantalla.text.toString().toDouble()
        } else {
            segundoNum = binding.pantalla.text.toString().toDouble()
        }
    }
}