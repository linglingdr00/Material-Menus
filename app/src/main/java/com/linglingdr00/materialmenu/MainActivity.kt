package com.linglingdr00.materialmenu

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ListPopupWindow
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.linglingdr00.materialmenu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var buttonOne: MaterialButton
    private lateinit var buttonTwo: MaterialButton
    private lateinit var chipOne: Chip
    private lateinit var chipTwo: Chip
    private lateinit var textFieldOne: TextInputLayout
    private lateinit var autoTextViewOne: AutoCompleteTextView
    private lateinit var items: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        items = listOf("Item 1", "Item 2", "Item 3")

        /* 1.button + popup menu */
        buttonOne = binding.buttonOne

        buttonOne.setOnClickListener { v: View ->
            showMenu(v, R.menu.my_menu, 0)
            // 設 button icon 為 arrow down
            buttonOne.icon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_up)
        }

        /* 2.button + List popup window menu */
        buttonTwo = binding.buttonTwo
        val buttonPopupWindow = setListPopup(buttonTwo, 0)

        // Show list popup window on button click.
        buttonTwo.setOnClickListener {
            buttonPopupWindow.show()
            // 設 button icon 為 arrow down
            buttonOne.icon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_up)
        }

        /* 3.chip + popup menu */
        chipOne = binding.chipOne

        chipOne.setOnCloseIconClickListener { v ->
            showMenu(v, R.menu.my_menu, 1)
        }

        /* 4.chip + List popup window menu */
        chipTwo = binding.chipTwo
        val chipPopupWindow = setListPopup(chipTwo, 1)

        chipTwo.setOnCloseIconClickListener {
            chipPopupWindow.show()
        }

        /* 5.exposed dropdown menu */
        textFieldOne = binding.textFieldOne
        autoTextViewOne = binding.autoTextViewOne

        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (textFieldOne.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        /*val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.option_array,
            R.layout.list_item
        )
        autoTextViewOne.setAdapter(adapter)*/

        autoTextViewOne.isFocusable = false
        autoTextViewOne.setText(adapter.getItem(0).toString(), false)

        autoTextViewOne.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    Log.d(TAG, "Item 1")
                }
                1 -> {
                    Log.d(TAG, "Item 2")
                }
                2 -> {
                    Log.d(TAG, "Item 3")
                }
                else -> {}
            }
        }


    }

    // popup menu
    private fun showMenu(v: View, @MenuRes myMenu: Int, type: Int) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(myMenu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (type) {
                0 -> {
                    // 設 button 文字為 item title
                    buttonOne.text = menuItem.title
                }
                1 -> {
                    // 設 chip 文字為 item title
                    chipOne.text = menuItem.title
                }
                else -> {}
            }

            return@setOnMenuItemClickListener when (menuItem.itemId) {
                R.id.option_1 -> {
                    Log.d(TAG, "Option 1")
                    true
                }
                R.id.option_2 -> {
                    Log.d(TAG, "Option 2")
                    true
                }
                R.id.option_3 -> {
                    Log.d(TAG, "Option 3")
                    true
                }
                else -> super.onContextItemSelected(menuItem)
            }
        }
        popup.setOnDismissListener {
            if (type == 0) {
                // 設 button icon 為 arrow down
                buttonOne.icon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_down)
            }
        }
        // Show the popup menu
        popup.show()
    }

    private fun setListPopup(component: View, type: Int): ListPopupWindow {
        val listPopupWindow = ListPopupWindow(this, null)
        // Set button as the list popup's anchor
        listPopupWindow.anchorView = component

        // Set list popup's content
        val buttonTwoAdapter = ArrayAdapter(this, R.layout.list_item, items)
        listPopupWindow.setAdapter(buttonTwoAdapter)

        // Set list popup's item click listener
        listPopupWindow.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            // Respond to list popup window item click.
            if (type == 0) {
                buttonTwo.text = items[position]
            } else {
                chipTwo.text = items[position]
            }

            when (position) {
                0 -> {
                    Log.d(TAG, "Item 1")
                }
                1 -> {
                    Log.d(TAG, "Item 2")
                }
                2 -> {
                    Log.d(TAG, "Item 3")
                }
                else -> {}
            }
            // Dismiss popup.
            listPopupWindow.dismiss()
        }

        listPopupWindow.setOnDismissListener {
            // 設 button icon 為 arrow down
            buttonOne.icon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_down)
        }
        return listPopupWindow
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}