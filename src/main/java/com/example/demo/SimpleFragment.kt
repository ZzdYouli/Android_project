package com.example.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SimpleFragment : Fragment() {

    companion object {
        private const val ARG_TEXT = "text"
        private const val ARG_INDEX = "index"

        fun newInstance(text: String, index: Int): SimpleFragment {
            val fragment = SimpleFragment()
            val bundle = Bundle()
            bundle.putString(ARG_TEXT, text)
            bundle.putInt(ARG_INDEX, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_simple, container, false)
        val textView = view.findViewById<TextView>(R.id.textView)
//        val button = view.findViewById<Button>(R.id.jumpButton)

        val text = arguments?.getString(ARG_TEXT)
        val index = arguments?.getInt(ARG_INDEX) ?: 0

        textView.text = text

//        button.setOnClickListener {
//            // 使用 ViewPager2 切换页面
//            (activity?.findViewById<ViewPager2>(R.id.viewPager))?.currentItem = (index + 1) % 3
//        }

        return view
    }
}

