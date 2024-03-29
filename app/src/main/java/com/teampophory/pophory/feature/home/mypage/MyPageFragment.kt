package com.teampophory.pophory.feature.home.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.hideLoading
import com.teampophory.pophory.common.fragment.showLoading
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentMypageBinding
import com.teampophory.pophory.feature.setting.SettingActivity
import com.teampophory.pophory.feature.share.ShareActivity
import com.teampophory.pophory.feature.story.StoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment() {
    private val binding by viewBinding(FragmentMypageBinding::bind)

    private val viewModel by viewModels<MyPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        setOnClickListener()
    }

    private fun initObserver() {
        viewModel.myPageInfo.observe(viewLifecycleOwner) { myPageInfoState ->
            when (myPageInfoState) {
                is MyPageInfoState.Uninitialized -> {
                    viewModel.getMyPageInfo()
                }

                is MyPageInfoState.Loading -> {
                    showLoading()
                }

                is MyPageInfoState.SuccessMyPageInfo -> {
                    hideLoading()
                    with(binding) {
                        val profile = myPageInfoState.data
                        tvMypageToolbarNickname.text = profile.nickname
                        tvMypageName.text = profile.realName
                        tvMypagePictureCount.text = setSpannableString(profile.photoCount)
                    }
                }

                is MyPageInfoState.Error -> {
                    hideLoading()
                }

                else -> {}
            }
        }
    }

    private fun setOnClickListener() {
        with(binding) {
            ivToolbarSetting.setOnClickListener {
                startActivity(Intent(requireContext(), SettingActivity::class.java))
            }
            layoutMypageShare.setOnClickListener {
                startActivity(Intent(requireContext(), ShareActivity::class.java))
            }
            layoutMypageStory.setOnClickListener {
                startActivity(Intent(requireContext(), StoryActivity::class.java))
            }
            tvMypageAdmob.setOnClickListener {
                runCatching {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AD_URL)))
                }
            }
        }
    }

    private fun setSpannableString(myPageInfoDataPhotoCount: Int): SpannableStringBuilder {
        val fullText =
            requireContext().getString(R.string.mypage_picture_count, myPageInfoDataPhotoCount)
        val coloredText = myPageInfoDataPhotoCount.toString()

        val spannableStringBuilder = SpannableStringBuilder(fullText)
        val start = fullText.indexOf(coloredText)
        val end = start + coloredText.length

        if (start != -1) {
            spannableStringBuilder.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        com.teampophory.pophory.designsystem.R.color.pophory_purple,
                    ),
                ),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }
        return spannableStringBuilder
    }

    companion object {
        private const val AD_URL = "https://walla.my/pophory_event3?utm_source=android"

        fun newInstance() = MyPageFragment()
    }
}
