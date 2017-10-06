package com.tmxlr.lib.driodvalidatorlight;


import android.content.Context;
import android.widget.EditText;

import com.tmxlr.lib.driodvalidatorlight.base.Validator;
import com.tmxlr.lib.driodvalidatorlight.base.ValidatorException;
import com.tmxlr.lib.driodvalidatorlight.helper.Range;
import com.tmxlr.lib.driodvalidatorlight.validator.DateValidator;
import com.tmxlr.lib.driodvalidatorlight.validator.LengthValidator;
import com.tmxlr.lib.driodvalidatorlight.validator.RegExpValidator;
import com.tmxlr.lib.driodvalidatorlight.validator.ValueValidator;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Form {

    public static final String TAG = Form.class.getSimpleName();
    private static final int SHOW_ERRORS_TAG = Form.class.hashCode();

    final Context context;
    final Map<EditText, List<Validator>> fieldValidators;
    private boolean perFieldErrorMessageDisplay = false;

    public Form(Context context) {
        this.context = context;
        this.fieldValidators = new HashMap<>();
    }

    public void check(EditText editText, String regex, String errMsg) {
        RegExpValidator validator = new RegExpValidator(Pattern.compile(regex), errMsg);
        addValidator(editText, validator);
    }

    public void check(EditText editText, Pattern pattern, String errMsg) {
        RegExpValidator validator = new RegExpValidator(pattern, errMsg);
        addValidator(editText, validator);
    }

    public void check(EditText editText, Validator validator, String errMsg) {
        addValidator(editText, validator);
    }

    public void checkValue(EditText editText, Range range, String errMsg) {
        ValueValidator validator = new ValueValidator(range, errMsg);
        addValidator(editText, validator);
    }

    public void checkLength(EditText editText, Range range, String errMsg) {
        LengthValidator validator = new LengthValidator(range, errMsg);
        addValidator(editText, validator);
    }

    public void checkDate(EditText editText, Range range, DateFormat dateFormat, String errMsg) {
        DateValidator validator = new DateValidator(range, dateFormat, errMsg);
        addValidator(editText, validator);
    }

    public void enablePerFieldErrorMessageDisplay() {
        perFieldErrorMessageDisplay = true;
    }

    public void setShowError(EditText editText) {
        editText.setTag(SHOW_ERRORS_TAG, new Object());
    }

    public boolean validate() {
        boolean formValid = true;
        for (Map.Entry<EditText, List<Validator>> entry : fieldValidators.entrySet()) {
            EditText editText = entry.getKey();
            List<Validator> validators = entry.getValue();
            formValid = formValid & this.validateField(editText, validators);
        }

        return formValid;
    }

    public void clearErrors() {
        for (Map.Entry<EditText, List<Validator>> entry : fieldValidators.entrySet()) {
            EditText editText = entry.getKey();
            editText.setError(null);
        }
    }

    public void clear() {
        fieldValidators.clear();
    }

    private boolean validateField(EditText editText, List<Validator> validators) {
        for (Validator validator : validators) {
            try {
                if (!validator.isValid(editText.getText().toString())) {
                    if (!perFieldErrorMessageDisplay ||
                            ((perFieldErrorMessageDisplay && editText.getTag(SHOW_ERRORS_TAG) != null))) {
                        editText.setError(validator.getMessage());
                    }
                    return false;
                }
            } catch (ValidatorException e) {
                e.printStackTrace();
                editText.setError(e.getMessage());
                return false;
            }
        }
        editText.setError(null);
        return true;
    }

    private void addValidator(EditText editText, Validator validator) {
        List<Validator> v;
        if (fieldValidators.containsKey(editText)) {
            v = fieldValidators.get(editText);
        } else {
            v = new ArrayList<>();
            fieldValidators.put(editText, v);
        }
        v.add(validator);
    }

}
