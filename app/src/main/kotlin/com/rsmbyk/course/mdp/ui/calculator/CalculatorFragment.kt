package com.rsmbyk.course.mdp.ui.calculator

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.setErrorText
import com.rsmbyk.course.mdp.common.setIntText
import com.rsmbyk.course.mdp.model.OperatorModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_calculator.*
import javax.inject.Inject

class CalculatorFragment: DaggerFragment () {

    @Inject
    lateinit var viewModel: CalculatorViewModel

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.activity_calculator, container, false)

    override fun onActivityCreated (savedInstanceState: Bundle?) {
        super.onActivityCreated (savedInstanceState)
        viewModel.result.observe (this, Observer (result::setIntText))
        viewModel.error.observe (this, Observer (result::setErrorText))
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated (view, savedInstanceState)
        setupButtons ()
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
        return text.isNotEmpty ().apply {
            if (!this)
                error = "Can't be empty"
        }
    }

    override fun onDestroy () {
        super.onDestroy ()
        viewModel.result.removeObservers (this)
        viewModel.error.removeObservers (this)
    }
}
