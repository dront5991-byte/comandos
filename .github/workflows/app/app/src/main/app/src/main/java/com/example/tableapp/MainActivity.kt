package com.example.tableapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var rowsContainer: LinearLayout
    private lateinit var btnAddRow: Button
    private val inflater by lazy { LayoutInflater.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rowsContainer = findViewById(R.id.rowsContainer)
        btnAddRow = findViewById(R.id.btnAddRow)

        btnAddRow.setOnClickListener { addRow() }

        // Стартовая строка
        addRow()
    }

    private fun addRow() {
        val row = inflater.inflate(R.layout.row_item, rowsContainer, false)

        val etCol1 = row.findViewById<EditText>(R.id.etCol1)
        val etCol2 = row.findViewById<EditText>(R.id.etCol2)
        val etCol3 = row.findViewById<EditText>(R.id.etCol3)

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val v1 = etCol1.text.toString().replace(',', '.').toDoubleOrNull()
                val v3 = etCol3.text.toString().replace(',', '.').toDoubleOrNull()

                if (v1 != null && v3 != null) {
                    // Столбец 2 = Столбец 3 - Столбец 1
                    val result = v3 - v1
                    etCol2.setText(formatNumber(result))
                } else {
                    etCol2.setText("")
                }
            }
        }

        etCol1.addTextChangedListener(watcher)
        etCol3.addTextChangedListener(watcher)

        rowsContainer.addView(row)
    }

    /** Убирает .0 у целых чисел для красоты вывода. */
    private fun formatNumber(value: Double): String {
        return if (value == value.toLong().toDouble()) value.toLong().toString()
        else value.toString()
    }
}
