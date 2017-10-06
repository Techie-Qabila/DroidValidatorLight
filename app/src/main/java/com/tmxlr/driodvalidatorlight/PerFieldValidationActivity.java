package com.tmxlr.driodvalidatorlight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tmxlr.lib.driodvalidatorlight.Form;
import com.tmxlr.lib.driodvalidatorlight.helper.Range;
import com.tmxlr.lib.driodvalidatorlight.helper.RegexTemplate;

public class PerFieldValidationActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener {

    EditText editText;
    EditText editText2;
    Button btnNext;
    Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text);
        editText2 = (EditText) findViewById(R.id.edit_text2);
        btnNext = (Button) findViewById(R.id.btn_validate);
        btnNext.setText(R.string.button_next);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerFieldValidationActivity.this, "Success", Toast.LENGTH_LONG).show();
            }
        });

        form = new Form(this);
        form.enablePerFieldErrorMessageDisplay();
        form.check(editText, RegexTemplate.NOT_EMPTY_PATTERN, "Must not be empty");
        form.check(editText, RegexTemplate.EMAIL_PATTERN, "Must be an Email");
        form.checkLength(editText, Range.equalOrLess(12), "Text length must be less or equal to 12");

        //form.checkValue(editText2, Range.equalOrMore(23), "Entered value must be greater equal than 23");
        form.checkValue(editText2, Range.equalsOrMoreAndEqualsOrLess(-12, 12), "Entered value must between -12 and 12");

        editText.addTextChangedListener(this);
        editText2.addTextChangedListener(this);
        editText.setOnFocusChangeListener(this);
        editText2.setOnFocusChangeListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //na
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //na
    }

    @Override
    public void afterTextChanged(Editable s) {
        validate();
    }

    private void validate() {
        if(form.validate()) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            form.setShowError((EditText) v);
            validate();
        }
    }
}
