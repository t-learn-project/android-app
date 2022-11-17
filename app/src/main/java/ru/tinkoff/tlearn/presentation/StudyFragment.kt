package ru.tinkoff.tlearn.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.tinkoff.tlearn.databinding.FragmentStudyBinding
import ru.tinkoff.tlearn.di.ContextModule
import ru.tinkoff.tlearn.di.DaggerAppComponent
import javax.inject.Inject


class StudyFragment : Fragment() {

    private val component by lazy {
        val application = requireActivity().application

        DaggerAppComponent.builder()
            .contextModule(ContextModule(application))
            .build()
    }

    private var _binding: FragmentStudyBinding? = null
    private val binding: FragmentStudyBinding
        get() = _binding ?: throw RuntimeException("FragmentStudyBinding is null")

    @Inject
    lateinit var cardStackAdapter: CardStackAdapter

    @Inject
    lateinit var viewModelFactory: StudyViewModelFactory

    private lateinit var cardStackLayoutManager: CardStackLayoutManager

    private val viewModel: StudyViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[StudyViewModel::class.java]
    }

    private val swipeAnimationSetting = SwipeAnimationSetting.Builder()
        .setDuration(Duration.Slow.duration)
        .setInterpolator(LinearInterpolator())

    private val positiveAnimationSetting = swipeAnimationSetting
        .setDirection(Direction.Left)
        .build()

    private val negativeAnimationSetting = swipeAnimationSetting
        .setDirection(Direction.Right)
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }


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
        cardStackLayoutManager = CardStackLayoutManager(context, object : CardStackListener {
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

        })

        cardStackLayoutManager.apply {
            setVisibleCount(2)
            setMaxDegree(15.0f)
            setDirections(listOf(
                Direction.Right,
                Direction.Left
            ))
            setCanScrollVertical(false)
            setScaleInterval(0.9f)
            setSwipeThreshold(0.3f)
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
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getCards().collectLatest {
                cardStackAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}