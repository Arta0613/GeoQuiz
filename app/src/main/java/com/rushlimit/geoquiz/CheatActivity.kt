package com.rushlimit.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cheat.*

val EXTRA_ANSWER = "answer"
val EXTRA_ANSWER_SHOWN = "answer_shown"

class CheatActivity : AppCompatActivity() {

    private var userHasCheated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        userHasCheated = savedInstanceState?.getBoolean(KEY_CHEAT_INDEX) ?: false

        if (userHasCheated) {
            setAnswerShown()
        }

        val triviaAnswer = intent.getBooleanExtra(EXTRA_ANSWER, false)

        show_answer_button.setOnClickListener {
            userHasCheated = true
            setAnswerShown()

            if (triviaAnswer) {
                answer_text_view.setText(R.string.true_button)
            } else {
                answer_text_view.setText(R.string.false_button)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(KEY_CHEAT_INDEX, userHasCheated)
    }

    private fun setAnswerShown() {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, userHasCheated)
        setResult(Activity.RESULT_OK, data)
    }
}
