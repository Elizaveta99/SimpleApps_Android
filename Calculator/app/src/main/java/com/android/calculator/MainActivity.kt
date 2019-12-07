package com.android.calculator

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    var listOfItemsFrom = arrayListOf("", "")
    var listOfItemsTo = arrayListOf("", "")

    override fun onCreate(savedInstanceState: Bundle?) {

        listOfItemsFrom = resources.getStringArray(R.array.listOfItemsFrom).toCollection(ArrayList<String>())
        listOfItemsTo = resources.getStringArray(R.array.listOfItemsTo).toCollection(ArrayList<String>())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfItemsFrom)
        // Set layout to use when the list of choices appear
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner_from!!.adapter = aa1

        val aa2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfItemsTo)
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_to!!.adapter = aa2
    }



    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if(parent.id == R.id.spinner_from)
        {
            from.text = listOfItemsFrom[position]
            editText_from.isEnabled = true
        }
        else if(parent.id == R.id.spinner_to)
        {
            to.text = listOfItemsTo[position]
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        if(parent.id == R.id.spinner_from)
        {
            from.setText(R.string.from)
            editText_from.isEnabled = false
        }
        else if(parent.id == R.id.spinner_to)
        {
            to.setText(R.string.to)
        }
    }


    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
    }

    private fun basicAlert(view: View){

        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle(R.string.error)
            setMessage(R.string.wrong_input)
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
            show()
        }


    }

    fun calcButton(view: View) {
        try {
            var fromVal:Double = editText_from.text.toString().toDouble()
            if (fromVal >= 0) {
                if (spinner_from.selectedItem.equals(listOfItemsFrom[1]))
                    fromVal /= 1000000.0

                if (spinner_to.selectedItem.equals(listOfItemsTo[0])) {
                    editText_to.text = (fromVal * 10763910.0).toString()
                } else {
                    editText_to.text = (fromVal * 247.1).toString()
                }
            }
            else
            {
                basicAlert(view)
            }
        } catch (e: NumberFormatException) {
            basicAlert(view)
        }

    }


}
