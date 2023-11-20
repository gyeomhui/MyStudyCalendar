package com.example.mystudycalendar

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mystudycalendar.calendarmemo.DBLoader
import com.example.studycalendar.model.Memo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MemoActivity : AppCompatActivity() {

    private lateinit var date: Date
    private lateinit var editText: EditText
    private var item : Memo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("메모")

        val textdate = findViewById<TextView>(R.id.text_date)
        editText = findViewById(R.id.edit_text)
        date = Date()

        val intent = intent.extras
        if (intent != null && intent.containsKey("item")) {
            item = intent.getSerializable("item") as Memo
            // 처리 로직 추가
            item?.let {
                editText.setText(it.memo)
                date.time = it.datetime
            }
        }
        val simpledate = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        textdate.text = simpledate.format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add, menu)
        val menu_delete = menu!!.findItem(R.id.action_delete)
        if (item == null){
            menu_delete.isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_save -> {
                if(this.item == null) {
                    val memo = editText.text.toString()
                    val datetime = date.time
                    if (!memo.equals("")) {
                        DBLoader(this).save(memo, datetime)
                        Toast.makeText(applicationContext, getString(R.string.msg_save), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }else{
                    val memo = editText.text.toString()
                    if (memo != "") {
                        DBLoader(this).update(this.item!!.id, memo)
                        Toast.makeText(applicationContext, getString(R.string.msg_update), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                //save
            }

            R.id.action_delete -> {
                if (this.item != null) {
                    showDeleteConfirmationDialog()
                }
                //delete
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.confirm_delete_message))
            setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                if (this@MemoActivity.item != null) {
                    // User clicked Yes, delete the item
                    DBLoader(this@MemoActivity).delete(this@MemoActivity.item!!.id)
                    Toast.makeText(applicationContext, getString(R.string.msg_delete), Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    // Handle the case where item is null (optional)
                    Toast.makeText(applicationContext, getString(R.string.error_delete), Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                // User clicked No, do nothing
                dialog.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
