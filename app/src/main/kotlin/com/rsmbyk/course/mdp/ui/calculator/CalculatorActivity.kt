package com.rsmbyk.course.mdp.ui.calculator

import android.os.Bundle
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.domain.model.Operator
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_calculator.*
import javax.inject.Inject

class CalculatorActivity: DaggerAppCompatActivity () {

    @Inject
    lateinit var viewModel: CalculatorViewModel

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_calculator)
        setupButtons ()
    }

    private fun setupButtons () {
        btn_add.setOnClickListener { evaluate (Operator.ADD) }
        btn_subtract.setOnClickListener { evaluate (Operator.SUBTRACT) }
        btn_multiply.setOnClickListener { evaluate (Operator.MULTIPLY) }
        btn_divide.setOnClickListener { evaluate (Operator.DIVIDE) }
    }

    private fun evaluate (operator: Operator) {
        try {
            result.setText (viewModel
                .evaluate (
                    operator,
                    number1.text.toString ().toInt (),
                    number2.text.toString ().toInt ())
                .toString ())
        } catch (e: Exception) {
            result.setText (getString (R.string.placeholder_calculator_error))
        }
    }
}
