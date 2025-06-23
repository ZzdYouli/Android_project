package com.example.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.demo.adapter.StaggeredImageAdapter
import com.example.demo.model.ImageItem
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.UUID

class StaggeredFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StaggeredImageAdapter
    private lateinit var refreshLayout: com.scwang.smart.refresh.layout.SmartRefreshLayout
    private var imageList = mutableListOf<ImageItem>()
    private var page = 1

    private val uiScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_staggered, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        refreshLayout = rootView.findViewById(R.id.refresh_layout)

        initRecyclerView()
        initRefreshLayout()

        // 默认自动加载第一页
        loadImages(page, isRefresh = true)

        return rootView
    }

    private fun initRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        adapter = StaggeredImageAdapter(requireContext(), imageList)
        recyclerView.adapter = adapter
    }

    private fun initRefreshLayout() {
        refreshLayout.setOnRefreshListener(OnRefreshListener {
            page = 1
            loadImages(page, isRefresh = true)
        })

        refreshLayout.setOnLoadMoreListener(OnLoadMoreListener {
            page++
            loadImages(page, isRefresh = false)
        })
    }

    private fun loadImages(page: Int, isRefresh: Boolean) {
        uiScope.launch {
            val newImages = fetchImageData()
            if (isRefresh) {
                adapter.setNewData(newImages)
                refreshLayout.finishRefresh()
            } else {
                adapter.addData(newImages)
                refreshLayout.finishLoadMore()
            }
        }
    }

    private suspend fun fetchImageData(): List<ImageItem> = withContext(Dispatchers.IO) {
        val resultList = mutableListOf<ImageItem>()

        repeat(20) {  // 模拟一次请求20张图片
            try {
                val randomLength = (200..800).random()
                val response = RetrofitClient.apiService.getImage(randomLength)

                if (response.isSuccessful) {
                    // 因为 picsum.photos 不返回JSON，我们只拼URL用
                    val imageUrl = "https://picsum.photos/400/$randomLength"
                    resultList.add(
                        ImageItem(
                            id = UUID.randomUUID().toString(),
                            imageUrl = imageUrl
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        resultList
    }

}
