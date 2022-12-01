package ru.tinkoff.tlearn.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.tinkoff.tlearn.databinding.FragmentStudyBinding
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class StudyFragment : Fragment() {

    @Inject lateinit var cardStackAdapter: CardStackAdapter
    @Inject lateinit var cardStackLayoutManager: CardStackLayoutManager
    private val viewModel: StudyViewModel by viewModels()

    @Inject @Named("PositiveSwipeSetting")
    lateinit var positiveAnimationSetting: SwipeAnimationSetting

    @Inject @Named("NegativeSwipeSetting")
    lateinit var negativeAnimationSetting: SwipeAnimationSetting

    private var _binding: FragmentStudyBinding? = null
    private val binding: FragmentStudyBinding
        get() = _binding ?: throw RuntimeException("FragmentStudyBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCardStack()
        setupActionButtons()
        observeViewModel()
    }

    private fun setupCardStack() {
        cardStackLayoutManager.cardStackListener = object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction) {
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
            }

        }

        binding.cardStackView.layoutManager = cardStackLayoutManager
        binding.cardStackView.adapter = cardStackAdapter
        binding.cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun setupActionButtons() = with(binding) {
        btnPositive.setOnClickListener {
            cardStackLayoutManager.setSwipeAnimationSetting(positiveAnimationSetting)
            cardStackView.swipe()
        }

        btnNegative.setOnClickListener {
            cardStackLayoutManager.setSwipeAnimationSetting(negativeAnimationSetting)
            cardStackView.swipe()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getCards().collectLatest {
                cardStackAdapter.submitData(lifecycle, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}