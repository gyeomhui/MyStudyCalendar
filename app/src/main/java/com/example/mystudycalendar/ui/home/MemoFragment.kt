package com.example.mystudycalendar.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mystudycalendar.MemoActivity
import com.example.mystudycalendar.R
import com.example.mystudycalendar.adapter.MemoAdapter
import com.example.mystudycalendar.calendarmemo.DBLoader
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MemoFragment : Fragment() {

    private lateinit var adapter: MemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_memo, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd: FloatingActionButton = view.findViewById(R.id.fab)
        btnAdd.setOnClickListener {
            startActivity(Intent(requireContext(), MemoActivity::class.java))
        }

        // Use requireContext() here to pass the correct context to DBLoader
        adapter = MemoAdapter(requireContext())
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        // Use requireContext() here as well
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        // Pass requireContext() to DBLoader constructor
        adapter.setList(DBLoader(requireContext()).memoList())
    }
}
