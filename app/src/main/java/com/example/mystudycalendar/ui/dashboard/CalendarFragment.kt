package com.example.mystudycalendar.ui.dashboard

import android.content.Context
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mystudycalendar.R
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Calendar

class CalendarFragment : Fragment() {

    lateinit var userID: String
    lateinit var fname: String
    lateinit var str: String
    lateinit var calendarView: CalendarView
    lateinit var contextEditText: EditText
    lateinit var diaryTextView: TextView
    lateinit var saveBtn: Button
    lateinit var deleteBtn: Button
    lateinit var updateBtn: Button
    lateinit var diaryContent: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)

        // 여기에서 필요한 UI 요소 초기화
        calendarView = root.findViewById(R.id.calendar_view)
        contextEditText = root.findViewById(R.id.contextEditText)
        diaryTextView = root.findViewById(R.id.diaryTextView)
        saveBtn = root.findViewById(R.id.saveBtn)
        deleteBtn = root.findViewById(R.id.deleteBtn)
        updateBtn = root.findViewById(R.id.updateBtn)
        diaryContent = root.findViewById(R.id.diaryContent)

        // 여기에서 다른 초기화 작업 수행 가능

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userID = "userID"  // 사용자 ID 초기화

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            diaryTextView.visibility = View.VISIBLE
            saveBtn.visibility = View.VISIBLE
            contextEditText.visibility = View.VISIBLE
            diaryContent.visibility = View.INVISIBLE
            updateBtn.visibility = View.INVISIBLE
            deleteBtn.visibility = View.INVISIBLE

            diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            contextEditText.setText("")
            checkDay(year, month, dayOfMonth)
        }

        saveBtn.setOnClickListener {
            saveDiary(fname)
            contextEditText.visibility = View.INVISIBLE
            saveBtn.visibility = View.INVISIBLE
            updateBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE
            str = contextEditText.text.toString()
            diaryContent.text = str
            diaryContent.visibility = View.VISIBLE
        }
    }

    // 달력 내용 조회, 수정
    private fun checkDay(cYear: Int, cMonth: Int, cDay: Int) {
        fname = "${userID}${cYear}-${cMonth + 1}${cDay}.txt"

        var fileInputStream: FileInputStream
        try {
            fileInputStream = requireActivity().openFileInput(fname)
            val fileData = ByteArray(fileInputStream.available())
            fileInputStream.read(fileData)
            fileInputStream.close()
            str = String(fileData)
            contextEditText.visibility = View.INVISIBLE
            diaryContent.visibility = View.VISIBLE
            diaryContent.text = str
            saveBtn.visibility = View.INVISIBLE
            updateBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE

            updateBtn.setOnClickListener {
                contextEditText.visibility = View.VISIBLE
                diaryContent.visibility = View.INVISIBLE
                contextEditText.setText(str)
                saveBtn.visibility = View.VISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                diaryContent.text = contextEditText.text
            }

            deleteBtn.setOnClickListener {
                diaryContent.visibility = View.INVISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                contextEditText.setText("")
                contextEditText.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
                removeDiary(fname)
            }

            if (diaryContent.text == null) {
                diaryContent.visibility = View.INVISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                diaryTextView.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
                contextEditText.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 달력 내용 제거
    private fun removeDiary(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = requireActivity().openFileOutput(readDay, Context.MODE_PRIVATE)
            val content = ""
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 달력 내용 추가
    private fun saveDiary(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = requireActivity().openFileOutput(readDay, Context.MODE_PRIVATE)
            val content = contextEditText.text.toString()
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
