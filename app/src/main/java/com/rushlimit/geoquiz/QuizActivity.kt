package com.rushlimit.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*

val KEY_INDEX = "index"
val REQUEST_CODE_CHEAT = 0
val KEY_CHEATS_AVAILABLE = "cheats_available"

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
    private var cheater = false
    private var availableCheats = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        availableCheats = savedInstanceState?.getInt(KEY_CHEATS_AVAILABLE) ?: 3

        updateCheatButtonState()
        true_button.setOnClickListener { checkAnswer(true) }
        false_button.setOnClickListener { checkAnswer(false) }

        cheat_button.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER, questionBank[currentIndex].triviaAnswer)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        next_button.setOnClickListener {
            currentIndex++
            cheater = false
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_INDEX, currentIndex)
        outState?.putInt(KEY_CHEATS_AVAILABLE, availableCheats)
    }

    private fun updateCheatButtonState() {
        if (availableCheats <= 0) {
            cheat_button.isEnabled = false
        }

        cheats_available_text_view.text = resources.getString(R.string.cheats_available, availableCheats)
    }

    private fun updateQuestion() {
        if (currentIndex == questionBank.size) {
            currentIndex = 0
        }

        question_text_view.setText(questionBank[currentIndex].textResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if (cheater) {
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
                cheater = data.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
                availableCheats--
                updateCheatButtonState()
            }
        }
    }
}
