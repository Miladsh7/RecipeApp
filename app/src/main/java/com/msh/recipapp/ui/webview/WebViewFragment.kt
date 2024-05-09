package com.msh.recipapp.ui.webview

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

class WebViewFragment : BaseFragment<FragmentWebViewBinding>(
    R.layout.fragment_web_view,
    FragmentWebViewBinding::class
) {

    private val args: WebViewFragmentArgs by navArgs()
    private lateinit var link: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            backImg.setOnClickListener { findNavController().popBackStack() }

            args.let {
                link = it.url
            }

            link.let { link ->
                webView.apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.builtInZoomControls = false
                    settings.domStorageEnabled = true
                    settings.databaseEnabled = true
                    webView.webViewClient = WebViewClient()
                    webView.isVerticalScrollBarEnabled = true
                    webView.isHorizontalScrollBarEnabled = true
                    webView.webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            if (newProgress < 100 && webViewLoading.visibility == View.GONE) {
                                webViewLoading.isVisible = true
                            }
                            webViewLoading.progress = newProgress
                            if (newProgress == 100) {
                                webViewLoading.isVisible = false
                            }
                        }
                    }
                    webView.loadUrl(link)
                }
            }
        }

    }
}