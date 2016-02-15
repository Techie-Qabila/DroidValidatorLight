package com.tmxlr.lib.driodvalidatorlight.validator;


import com.tmxlr.lib.driodvalidatorlight.base.Validator;
import com.tmxlr.lib.driodvalidatorlight.base.ValidatorException;
import com.tmxlr.lib.driodvalidatorlight.helper.Range;

public class LengthValidator extends Validator {

    final Range range;

    public LengthValidator(Range range, String errorMessage) {
        super(errorMessage);
        this.range = range;
    }


    @Override
    public boolean isValid(String value) throws ValidatorException {
        int length = value.length();
        if(range.includes(length)) {
            return true;
        }else{
            return false;
        }
    }
}
