package com.rushlimit.geoquiz

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

val KEY_INDEX = "index"
val KEY_INDEX_TOGGLES = "toggles"
val KEY_INDEX_SCORE = "score"

class QuizActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = arrayOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var quizScore = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        savedInstanceState?.let {
            currentIndex = it.getInt(KEY_INDEX)
            quizScore = it.getDouble(KEY_INDEX_SCORE)
            toggleButtons(it.getBoolean(KEY_INDEX_TOGGLES))
        }


        trueButton.setOnClickListener {
            toggleButtons(false)
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            toggleButtons(false)
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex++
            updateQuestion()
            toggleButtons(true)
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            it.putInt(KEY_INDEX, currentIndex)
            it.putDouble(KEY_INDEX_SCORE, quizScore)
            it.putBoolean(KEY_INDEX_TOGGLES, trueButton.isEnabled)
        }
    }

    private fun updateQuestion() {
        if (currentIndex == questionBank.size) {
            currentIndex = 0
            quizScore = 0.0
        }

        questionTextView.setText(questionBank[currentIndex].textResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        if (userAnswer == questionBank[currentIndex].triviaAnswer) {
            makeText(R.string.correct_toast)
            quizScore++
        } else {
            makeText(R.string.incorrect_toast)
        }

        if (currentIndex == questionBank.size - 1) {
            makeText(getString(R.string.percent_correct, (quizScore / questionBank.size) * 100))
        }
    }

    private fun toggleButtons(enabled: Boolean) {
        trueButton.isEnabled = enabled
        falseButton.isEnabled = enabled
    }

    private fun makeText(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        makeText(getString(resId), duration)
    }

    private fun Context.makeText(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }
}
