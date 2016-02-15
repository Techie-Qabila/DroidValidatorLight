package com.tmxlr.driodvalidatorlight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tmxlr.lib.driodvalidatorlight.Form;
import com.tmxlr.lib.driodvalidatorlight.helper.Range;
import com.tmxlr.lib.driodvalidatorlight.helper.RegexTemplate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;
    EditText editText2;
    Button btnValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text);
        editText2 = (EditText) findViewById(R.id.edit_text2);
        btnValidate = (Button) findViewById(R.id.btn_validate);

        btnValidate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        Form form = new Form(this);
        form.check(editText, RegexTemplate.NOT_EMPTY_PATTERN, "Must not be empty");
        form.check(editText, RegexTemplate.EMAIL_PATTERN, "Must be an Email");
        form.checkLength(editText, Range.equalOrLess(12), "Text length must be less or equal to 12");

        form.checkValue(editText2, Range.equalOrMore(23), "Entered value must be greater equal than 23");

        if(form.validate()) {
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
        }
    }
}
