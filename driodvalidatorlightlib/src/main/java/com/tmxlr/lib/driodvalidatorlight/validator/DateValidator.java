package com.tmxlr.lib.driodvalidatorlight.validator;


import com.tmxlr.lib.driodvalidatorlight.base.Validator;
import com.tmxlr.lib.driodvalidatorlight.base.ValidatorException;
import com.tmxlr.lib.driodvalidatorlight.helper.Range;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


public class DateValidator extends Validator {

    final Range range;
    final DateFormat dateFormat;

    public DateValidator(Range range, DateFormat dateFormat, String errorMessage) {
        super(errorMessage);
        this.range = range;
        this.dateFormat = dateFormat;
    }

    @Override
    public boolean isValid(String value) throws ValidatorException {
        try {
            Date date = dateFormat.parse(value);
            long time = date.getTime();
            if(range.includes(time)) {
                return true;
            }else{
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }
    

}
