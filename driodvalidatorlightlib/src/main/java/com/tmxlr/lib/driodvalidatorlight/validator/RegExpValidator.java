package com.tmxlr.lib.driodvalidatorlight.validator;

import com.tmxlr.lib.driodvalidatorlight.base.Validator;
import com.tmxlr.lib.driodvalidatorlight.base.ValidatorException;

import java.util.regex.Pattern;

public class RegExpValidator extends Validator {

	private final Pattern pattern;

	public RegExpValidator(Pattern pattern, String errorMessage) {
		super(errorMessage);
		this.pattern = pattern;
	}

	@Override
	public boolean isValid(String value) throws ValidatorException {
		if (pattern != null) {
			return pattern.matcher(value).matches();
		}
		throw new ValidatorException("You can set Regexp Pattern first");
	}
}
