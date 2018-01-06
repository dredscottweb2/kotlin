package com.example.android.myapptest


import android.os.Handler
import com.example.android.myapptest.Contract.*
import java.util.*

class GameEngine: Contract.GameEngine {
    private var random: Random = Random()
    private val ants: MutableList<Ant> = ArrayList<Ant>()
    private var score = 0
    private var currentTime = Date().time


    private  val handler: Handler = Handler()
    private  val showNewAntRunnable = object: Runnable{
        override fun run() {


                val ant = Ant((ants.size + 1).toLong(), random.nextFloat(), random.nextFloat())
                ants.add(ant)
                gameView?.ShowAnt(ant)
                handler.postDelayed(this, 700)


        }
    }



    private  var gameView: GameView? = null
    override fun onGameViewReady(view: GameView) {
       gameView = view

    }



    override fun onViewDestroyed() {
        gameView = null

    }


    override fun onPlayButtonClicked() {
        with(gameView!!) {
            SetPlayButtonVisibility(false)
            SetIntroTextVisibility(false)
            SetGameOverTextVisibility(false)
            ClearView()
        }
        ants.clear()
        currentTime = Date().time
        showNewAntRunnable.run()
    }

    override fun onAntClicked(ant: Ant)  {
        ants.remove(ant)
        gameView?.HideAnt(ant)
        score++
        gameView?.ShowScore(score)
        if((Date().time - currentTime > 40000) and (score < 60)){

            //Toast.makeText(sContext, (Date().time - currentTime).toString(), Toast.LENGTH_LONG).show()

                //Toast.makeText(sContext, "GameOver Adrien", Toast.LENGTH_LONG).show()
            with(ants) {
                clear()
                remove(ant)
            }

            with(gameView!!) {
                ClearView()
                SetPlayButtonVisibility(true)
                SetIntroTextVisibility(true)
                SetGameOverTextVisibility(true)
            }
            score = 0
            handler.removeCallbacks(showNewAntRunnable)

        }
    }

  }

