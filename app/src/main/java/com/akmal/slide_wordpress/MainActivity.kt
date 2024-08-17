package com.akmal.slide_wordpress

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.akmal.slide_wordpress.adapter.ArticleAdapter
import com.akmal.slide_wordpress.databinding.ActivityMainBinding
import com.akmal.slide_wordpress.entities.ArticleEntity
import com.akmal.slide_wordpress.model.Article
import com.akmal.slide_wordpress.model.Title
import com.akmal.slide_wordpress.utils.HorizontalSpaceItemDecoration
import com.akmal.slide_wordpress.viewmodel.MainViewModel

/**
 * MainActivity adalah kelas utama dalam aplikasi
 * Bertanggung jawab menyiapkan ui
 * menyambungkan antara view model dan view
 */

class MainActivity : AppCompatActivity() {
    //  mengikat ui dari activity_main.xml
    private lateinit var binding: ActivityMainBinding
    //  menginisialisasi view model
    private val viewModel: MainViewModel by viewModels()
    // dipanggil saat aplikasi pertama kali dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inisialisasi objek binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
    }
    /**
     * Menyiapkan RecyclerView dengan mengonfigurasi layout manager dan menambahkan dekorasi item.
     */
    private fun setupRecyclerView() {
        binding.rvItemArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvItemArticle.addItemDecoration(HorizontalSpaceItemDecoration(10))
    }
    /**
     * Mengamati LiveData dari ViewModel untuk memperbarui RecyclerView ketika data berubah.
     */
    private fun observeViewModel() {
        viewModel.articles.observe(this, Observer { articles ->
            updateRecyclerView(articles)
        })
        viewModel.categoriesMap.observe(this, Observer {
            updateRecyclerView(viewModel.articles.value ?: emptyList())
        })
        viewModel.mediaMap.observe(this, Observer {
            updateRecyclerView(viewModel.articles.value ?: emptyList())
        })
    }
    /**
     * Memperbarui RecyclerView dengan data terbaru.
     *
     * @param articles Daftar objek ArticleEntity yang mewakili artikel.
     */
    private fun updateRecyclerView(articles: List<ArticleEntity>) {
        val categoriesMap = viewModel.categoriesMap.value ?: emptyMap()
        val mediaMap = viewModel.mediaMap.value ?: emptyMap()
        // Mengubah objek ArticleEntity menjadi model Article yang diperlukan oleh adapter
        val articleList = articles.map { article ->
            Article(
                id = article.id,
                title = Title(article.title),
                categories = article.categories,
                featured_media = article.featured_media
            )
        }
        // Inisialisasi ArticleAdapter baru dengan data yang diperbarui dan mengaturnya ke RecyclerView
        binding.rvItemArticle.adapter = ArticleAdapter(articleList, categoriesMap, mediaMap)
    }
}
