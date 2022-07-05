package com.app.skillasessment

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.app.skillasessment.constant.Config.BASE_URL
import com.app.skillasessment.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

     var _binding: FragmentFirstBinding? = null

     val PAGE_URL = "pageUrl"
     val MAX_PROGRESS = 100
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
        setWebClient()
        loadUrl(BASE_URL)
       // callPrivacy()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun callPrivacy() {

        binding.progress.visibility = View.VISIBLE
        binding.webview.webViewClient = MyWebViewClient()
        binding.webview.setWebChromeClient(WebChromeClient())
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.domStorageEnabled = true
        binding.webview.loadUrl(BASE_URL)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.loadWithOverviewMode = true
        binding.webview.settings.useWideViewPort = true
        binding.webview.settings.domStorageEnabled = true

        binding.webview.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }

    }

    private fun setWebClient() {
        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                binding.progress.progress = newProgress
                if (newProgress < MAX_PROGRESS &&   binding.progress.visibility == ProgressBar.GONE) {
                    binding.progress.visibility =   View.VISIBLE
                }
                if (newProgress == MAX_PROGRESS) {
                    binding.progress.visibility = ProgressBar.GONE
                }
            }
        }
    }

    private fun loadUrl(pageUrl: String) {
        binding.webview.loadUrl(pageUrl)
    }


    inner class MyWebViewClient internal constructor() : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

            val url: String = request?.url.toString();
            view?.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progress.visibility=View.GONE
        }


        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
           // Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
            binding.progress.visibility=View.GONE
        }
    }



}