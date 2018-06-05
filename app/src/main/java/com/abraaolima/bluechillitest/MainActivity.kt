package com.abraaolima.bluechillitest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root_view.setOnTouchListener(object : OnGesture(this@MainActivity) {
            override fun onSingleTouch(event: MotionEvent) {
                launch(UI) {
                    val shape = presenter.generateNewShape()
                    val newView = CustomView(this@MainActivity, shape)

                    val params = RelativeLayout.LayoutParams(shape.size, shape.size)
                    params.leftMargin = event.x.toInt() - shape.size / 2
                    params.topMargin = event.y.toInt() - shape.size / 2
                    newView.layoutParams = params

                    addViewTouchHandler(newView)
                    root_view.addView(newView)
                }
            }

            override fun onSwipeLeft() {
                root_view.removeAllViews()
            }

            override fun onDoubleTouch() {}
        })
    }

    private fun addViewTouchHandler(view: CustomView) {
        view.setOnTouchListener(object : OnGesture(this@MainActivity) {
            override fun onSingleTouch(event: MotionEvent) {
                Toast.makeText(this@MainActivity, "${Random().nextInt()}", Toast.LENGTH_SHORT).show()
            }

            override fun onDoubleTouch() {
                launch(UI) {
                    view.updateShape(presenter.updateColor(view.shape))
                }
            }

            override fun onSwipeLeft() {}
        })
    }
}
