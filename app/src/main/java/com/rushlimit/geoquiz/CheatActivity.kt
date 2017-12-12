package com.rushlimit.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_cheat.*
import android.animation.Animator
import android.animation.AnimatorListenerAdapter

val EXTRA_ANSWER = "answer"
val EXTRA_ANSWER_SHOWN = "answer_shown"

class CheatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val triviaAnswer = intent.getBooleanExtra(EXTRA_ANSWER, false)

        api_level_text_view.text = resources.getString(R.string.api_level, Build.VERSION.SDK_INT)

        show_answer_button.setOnClickListener {
            setAnswerShown()

            if (triviaAnswer) {
                answer_text_view.setText(R.string.true_button)
            } else {
                answer_text_view.setText(R.string.false_button)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val cx = show_answer_button.width / 2
                val cy = show_answer_button.height / 2
                val radius = show_answer_button.width.toFloat()
                val anim = ViewAnimationUtils.createCircularReveal(show_answer_button, cx, cy, radius, 0.toFloat())

                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        show_answer_button.visibility = View.INVISIBLE
                    }
                })
                anim.start()
            } else {
                show_answer_button.visibility = View.INVISIBLE
            }
        }
    }

    private fun setAnswerShown() {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, true)
        setResult(Activity.RESULT_OK, data)
    }
}
