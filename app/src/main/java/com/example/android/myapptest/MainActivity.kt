package com.example.android.myapptest

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.View

import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.android.myapptest.Contract.GameView
import kotlinx.android.synthetic.main.activity_main.*

private val engine:  GameEngine = GameEngine()

private var gameLayout: FrameLayout? = null
private var toobar: Toolbar? = null
var playButton: View? = null
var introText: View? = null
var gameOverText: View? = null
var scoreView: TextView? = null
var sContext: Context? = null
class MainActivity : AppCompatActivity(), GameView  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sContext = applicationContext
        toobar = toolbar
        gameLayout = game_layout as FrameLayout
        playButton = play_button
        introText = intro_text
        gameOverText = game_over

        //toobar = findViewById(R.id.toolbar) as Toolbar?
        //gameLayout = findViewById(R.id.game_layout) as FrameLayout
        //playButton = findViewById(R.id.play_button)
        //introText = findViewById(R.id.intro_text)
       //gameOverText = findViewById(R.id.game_over)
       //scoreView = findViewById(R.id.scoreView) as TextView
       //var scoreView = scoreView as TextView


        playButton?.setOnClickListener{engine.onPlayButtonClicked() }


        //engine.onGameViewReady()
       val d = Drawable.createFromStream(getAssets().open("bg.jpeg"), null);
        gameLayout?.background = d

        //
       engine.onGameViewReady(view = this)
    }


    override fun ShowAnt(ant: Ant) {
        val antView = ImageView(this)
        antView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_ant))
        antView.scaleType = ImageView.ScaleType.FIT_CENTER
        antView.tag = ant
        antView.setOnClickListener{view: View? ->
            view.let {
                engine.onAntClicked(view?.tag as Ant)
            }
        }
        val antSize =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56f, resources.displayMetrics)
        val layoutParams = FrameLayout.LayoutParams(antSize.toInt(), antSize.toInt())
        val screenWidth = gameLayout!!.width - antSize
        val screenHeight = gameLayout!!.height - antSize
        layoutParams.leftMargin = (ant.x * screenWidth).toInt()
        layoutParams.topMargin = (ant.y * screenHeight).toInt()

        gameLayout?.addView(antView, layoutParams)
    }

    override fun HideAnt(antToHide: Ant) {
        gameLayout?.let {

            for (i: Int in 0 .. gameLayout!!.childCount  ){
                val view = gameLayout!!.getChildAt(i)
                val ant = view.tag
                if(ant == antToHide){
                    gameLayout!!.removeView(view)
                    break
                }
            }
        }
    }

    override fun ShowScore(score: Int) {
        scoreView?.text = "Points:  + $score"
    }

    override fun SetIntroTextVisibility(visibility: Boolean) {
        introText?.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    override fun SetGameOverTextVisibility(visibility: Boolean)  {
        gameOverText?.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    override fun SetPlayButtonVisibility(visibility: Boolean)  {
        playButton?.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    override fun ClearView() {
        gameLayout?.removeAllViews()
    }
}

