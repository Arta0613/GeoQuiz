package com.rushlimit.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*

val KEY_INDEX = "index"
val KEY_CHEAT_INDEX = "cheat_index"
val REQUEST_CODE_CHEAT = 0

class QuizActivity : AppCompatActivity() {

    private val questionBank = arrayOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var cheatMap: HashMap<Int, Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        cheatMap = savedInstanceState?.getSerializable(KEY_CHEAT_INDEX) as HashMap<Int, Boolean>? ?: HashMap()

        true_button.setOnClickListener { checkAnswer(true) }
        false_button.setOnClickListener { checkAnswer(false) }

        cheat_button.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER, questionBank[currentIndex].triviaAnswer)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        next_button.setOnClickListener {
            currentIndex++
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_INDEX, currentIndex)
        outState?.putSerializable(KEY_CHEAT_INDEX, cheatMap)
    }

    private fun updateQuestion() {
        if (currentIndex == questionBank.size) {
            currentIndex = 0
        }

        question_text_view.setText(questionBank[currentIndex].textResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if (cheatMap?.get(currentIndex) == true) {
            makeText(R.string.judgement_toast)
            return
        }

        if (userAnswer == questionBank[currentIndex].triviaAnswer) {
            makeText(R.string.correct_toast)
        } else {
            makeText(R.string.incorrect_toast)
        }
    }

    private fun Context.makeText(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, resId, duration).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        data?.let {
            if (requestCode == REQUEST_CODE_CHEAT) {
                cheatMap?.put(currentIndex, data.getBooleanExtra(EXTRA_ANSWER_SHOWN, false))
            }
        }
    }
}
