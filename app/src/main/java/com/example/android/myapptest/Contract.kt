package com.example.android.myapptest

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.example.android.myapptest.Contract.GameView

interface Contract {
    interface GameView{
        fun ShowAnt(ant: Ant)
        fun HideAnt(ant: Ant)
        fun ShowScore(score: Int)
        fun SetIntroTextVisibility(visibility: Boolean)
        fun SetGameOverTextVisibility(visibility: Boolean)
        fun SetPlayButtonVisibility(visibility: Boolean)
        fun ClearView()

    }
    interface GameEngine{

        fun onViewDestroyed()
        fun onGameViewReady(view: GameView)
        fun onPlayButtonClicked()
        fun onAntClicked(ant: Ant)

    }
}