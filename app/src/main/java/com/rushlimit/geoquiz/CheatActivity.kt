package com.rushlimit.geoquiz

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cheat.*

val EXTRA_ANSWER = "answer"
val EXTRA_ANSWER_SHOWN = "answer_shown"

class CheatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val triviaAnswer = intent.getBooleanExtra(EXTRA_ANSWER, false)

        show_answer_button.setOnClickListener {
            setAnswerShown()

            if (triviaAnswer) {
                answer_text_view.setText(R.string.true_button)
            } else {
                answer_text_view.setText(R.string.false_button)
            }
        }
    }

    private fun setAnswerShown() {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, true)
        setResult(Activity.RESULT_OK, data)
    }
}
