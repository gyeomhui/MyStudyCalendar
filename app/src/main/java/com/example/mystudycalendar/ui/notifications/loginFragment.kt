package com.example.mystudycalendar.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mystudycalendar.MainActivity
import com.example.mystudycalendar.R
import com.example.mystudycalendar.RegisterActivity
import com.example.mystudycalendar.calendarmemo.loginHelper

class loginFragment : Fragment() {
    lateinit var btnLogin: Button
    lateinit var editTextId: EditText
    lateinit var editTextPassword: EditText
    lateinit var btnRegister: Button
    var DB: loginHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        DB = loginHelper(requireContext())

        btnLogin = view.findViewById(R.id.btnLogin)
        editTextId = view.findViewById(R.id.editTextId)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        btnRegister = view.findViewById(R.id.btnRegister)

        // 로그인 버튼 클릭
        btnLogin.setOnClickListener {
            val user = editTextId.text.toString()
            val pass = editTextPassword.text.toString()

            // 빈칸 제출시 Toast
            if (user == "" || pass == "") {
                Toast.makeText(requireContext(), "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val checkUserpass = DB?.checkUserpass(user, pass)
                // id 와 password 일치시
                if (checkUserpass == true) {
                    Toast.makeText(requireContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "아이디와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 회원가입 버튼 클릭시
        btnRegister.setOnClickListener {
            val loginIntent = Intent(requireContext(), RegisterActivity::class.java)
            startActivity(loginIntent)
        }

        return view
    }
}
