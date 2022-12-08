package ru.tinkoff.tlearn.presentation

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.tinkoff.tlearn.AppDispatchers
import ru.tinkoff.tlearn.databinding.FragmentStudyBinding
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class StudyFragment : Fragment() {

    companion object {
        private const val NO_MORE_CARDS_ANIMATION_DURATION = 300L
    }

    @Inject lateinit var dispatchers: AppDispatchers
    @Inject lateinit var cardStackAdapter: CardStackAdapter
    @Inject lateinit var initialCardLoadStateAdapter: CardLoadStateAdapter
    @Inject lateinit var cardLoadStateAdapter: CardLoadStateAdapter
    @Inject lateinit var cardStackLayoutManager: CardStackLayoutManager

    private val viewModel: StudyViewModel by viewModels()

    @Named("PositiveSwipeSetting")
    @Inject lateinit var positiveAnimationSetting: SwipeAnimationSetting
    @Named("NegativeSwipeSetting")
    @Inject lateinit var negativeAnimationSetting: SwipeAnimationSetting

    private var _binding: FragmentStudyBinding? = null
    private val binding: FragmentStudyBinding
        get() = _binding ?: throw RuntimeException("FragmentStudyBinding is null")

    private val lastCardOnTop: Boolean
        get() = cardStackAdapter.itemCount == cardStackLayoutManager.topPosition

    private val cardSwipeRestricted: Boolean
        get() = viewModel.lastCardLocked && lastCardOnTop

    private val noMoreCards: Boolean
        get() = viewModel.noMoreCards.value

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

    fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.refresh
            footer.loadState = loadStates.append
        }

        return ConcatAdapter(header, this, footer)
    }

    private fun setupCardStack() {
        setupCardStackListener()

        val retryClickListener = { cardStackAdapter.retry() }

        cardStackAdapter.onExpandCardClickListener = { it.expandCard() }
        initialCardLoadStateAdapter.onRetryClickListener = retryClickListener
        cardLoadStateAdapter.onRetryClickListener = retryClickListener

        binding.cardStackView.layoutManager = cardStackLayoutManager
        binding.cardStackView.adapter = cardStackAdapter.withLoadStateAdapters(
            header = initialCardLoadStateAdapter,
            footer = cardLoadStateAdapter
        )

        viewLifecycleOwner.lifecycleScope.launch(dispatchers.default) {
            cardStackAdapter.loadStateFlow.collectLatest {
                if (it.source.append.endOfPaginationReached) {
                    Log.d("PAGING", "End of pagination")
                    viewModel.endOfPaginationReached()
                }


                when (it.append) {
                    is LoadState.NotLoading -> viewModel.unlockLastCard()
                    is LoadState.Loading -> viewModel.lockLastCard()
                    is LoadState.Error -> viewModel.lockLastCard()
                }
            }
        }

    }

    private fun setupCardStackListener() {
        cardStackLayoutManager.cardStackListener = object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction) {
                when(direction) {
                    Direction.Left  -> viewModel.positiveCardAction()
                    Direction.Right -> viewModel.negativeCardAction()
                    else -> { /* do nothing */ }
                }

                if (lastCardOnTop && noMoreCards)
                    toggleNoMoreLayout(true)

            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
                updateManualSwipeLock()
            }

            override fun onCardDisappeared(view: View?, position: Int) {
            }

        }
    }

    private fun setupActionButtons() = with(binding) {
        btnPositive.setOnClickListener {
            if (!cardSwipeRestricted) {
                cardStackLayoutManager.setSwipeAnimationSetting(positiveAnimationSetting)
                cardStackView.swipe()
            }
        }

        btnNegative.setOnClickListener {
            if (!cardSwipeRestricted) {
                cardStackLayoutManager.setSwipeAnimationSetting(negativeAnimationSetting)
                cardStackView.swipe()
            }
        }
    }

    private fun updateManualSwipeLock() {
        if (cardSwipeRestricted) {
            cardStackLayoutManager.setCanScrollHorizontal(false)
        } else {
            cardStackLayoutManager.setCanScrollHorizontal(true)
        }
    }

    private fun toggleNoMoreLayout(visible: Boolean) {
        val root = binding.layoutNoMoreCards.root

        val transition = Fade().apply {
            duration = NO_MORE_CARDS_ANIMATION_DURATION
            addTarget(root)
        }

        TransitionManager.beginDelayedTransition(binding.root, transition)
        binding.layoutNoMoreCards.root.isVisible = visible
    }

    private fun observeViewModel() = with(viewLifecycleOwner.lifecycleScope) {
        launch(dispatchers.io) {
            viewModel.getCards().collectLatest {
                cardStackAdapter.submitData(lifecycle, it)
            }
        }

        launch(dispatchers.main) {
            viewModel.lastCardLock.collectLatest {
                updateManualSwipeLock()
            }
        }

        launch(dispatchers.main) {
            viewModel.noMoreCards.collectLatest {
                if (cardStackAdapter.itemCount < 1)
                    toggleNoMoreLayout(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}