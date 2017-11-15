package com.rushlimit.geoquiz

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

val KEY_INDEX = "index"

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        savedInstanceState?.let {
            currentIndex = it.getInt(KEY_INDEX)
            toggleButtons(it.getBoolean(KEY_INDEX))
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
        outState?.putInt(KEY_INDEX, currentIndex)
        outState?.putBoolean(KEY_INDEX, trueButton.isEnabled)
    }

    private fun updateQuestion() {
        if (currentIndex == questionBank.size) {
            currentIndex = 0
        }

        questionTextView.setText(questionBank[currentIndex].textResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        if (userAnswer == questionBank[currentIndex].triviaAnswer) {
            makeText(R.string.correct_toast)
        } else {
            makeText(R.string.incorrect_toast)
        }
    }

    private fun toggleButtons(enabled: Boolean) {
        trueButton.isEnabled = enabled
        falseButton.isEnabled = enabled
    }

    private fun Context.makeText(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, resId, duration).show()
    }
}
