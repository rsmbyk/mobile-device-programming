package com.rsmbyk.course.mdp.ui.calculator

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.model.OperatorModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_calculator.*
import javax.inject.Inject

class CalculatorFragment: DaggerFragment () {

    @Inject
    lateinit var viewModel: CalculatorViewModel

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.fragment_calculator, container, false)

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        setupButtons ()
        viewModel.result.observe (this, Observer (::showResult))
        viewModel.error.observe (this, Observer (::showError))
    }

    private fun setupButtons () {
        btn_add.setOnClickListener (::evaluate)
        btn_subtract.setOnClickListener (::evaluate)
        btn_multiply.setOnClickListener (::evaluate)
        btn_divide.setOnClickListener (::evaluate)
    }

    private fun evaluate (view: View) {
        val number1Valid = number1.validate ()
        val number2Valid = number2.validate ()
        if (number1Valid && number2Valid)
            viewModel.evaluate (
                OperatorModel.values ().first { it.id == view.id },
                number1.text.toString ().toInt (),
                number2.text.toString ().toInt ())
    }

    private fun EditText.validate (): Boolean {
        return text.run {
            if (isEmpty ()) {
                error = getString (R.string.calculator_error_empty)
                return false
            } else {
                try {
                    toString ().toInt ()
                } catch (e: NumberFormatException) {
                    error = getString (R.string.calculator_error_invalid_int)
                    return false
                }
            }
            true
        }
    }

    private fun showResult (textResult: Int?) =
        result.setText (textResult?.toString ())

    private fun showError (t: Throwable?) =
        Toast.makeText (context!!, t?.message, Toast.LENGTH_LONG).show ()

    override fun onDestroy () {
        super.onDestroy ()
        viewModel.result.removeObservers (this)
        viewModel.error.removeObservers (this)
    }
}
