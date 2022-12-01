package ru.tinkoff.tlearn.di

import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.fragment.app.Fragment
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Named

@InstallIn(FragmentComponent::class)
@Module
class StudyModule {

    @Provides
    @Named("SwipeInterpolator")
    fun provideSwipeInterpolator(): Interpolator {
        return AccelerateInterpolator()
    }

    @Provides
    @Named("RatioInterpolator")
    fun provideRatioInterpolator(): Interpolator {
        return DecelerateInterpolator()
    }

    @Provides
    @Named("PositiveSwipeSetting")
    fun providePositiveSwipeSetting(
        @Named("SwipeInterpolator")
        interpolator: Interpolator
    ): SwipeAnimationSetting {
        return SwipeAnimationSetting.Builder()
            .setDuration(Duration.Slow.duration)
            .setInterpolator(interpolator)
            .setDirection(Direction.Left)
            .build()
    }

    @Provides
    @Named("NegativeSwipeSetting")
    fun provideNegativeSwipeSetting(
        @Named("SwipeInterpolator")
        interpolator: Interpolator
    ): SwipeAnimationSetting {
        return SwipeAnimationSetting.Builder()
            .setDuration(Duration.Slow.duration)
            .setInterpolator(interpolator)
            .setDirection(Direction.Right)
            .build()
    }

    @Provides
    fun provideCardStackLayoutManager(
        fragment: Fragment,
        @Named("PositiveSwipeSetting") setting: SwipeAnimationSetting,
        @Named("RatioInterpolator") ratioInterpolator: Interpolator
    ): CardStackLayoutManager {
        return CardStackLayoutManager(fragment.context).apply {
            setVisibleCount(2)
            setMaxDegree(15.0f)
            setDirections(listOf(
                Direction.Right,
                Direction.Left
            ))
            setCanScrollVertical(false)
            setScaleInterval(0.8f)
            setSwipeThreshold(0.3f)
            setFadeLastCard(true)
            setRatioInterpolator(ratioInterpolator)
            setImitateNaturalSwipe(true)
            setDragRatioBound(1f)
            setDynamicThreshold(true)
            setSwipeAnimationSetting(setting)
        }
    }
}