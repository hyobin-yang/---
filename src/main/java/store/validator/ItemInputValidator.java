package store.validator;

import store.exception.ErrorMessages;

import java.util.regex.Pattern;

public class ItemInputValidator {

    private static final String START_REGEX = "^";
    private static final String END_REGEX = "$";
    private static final String BLANK = "\\s*";
    private static final String OPEN_SQUARE_BRACKETS = "\\[";
    private static final String CLOSE_SQUARE_BRACKETS = "\\]";
    private static final String HYPHEN = "-";
    private static final String ALLOWED_REGEX = "[^\\]-]+";
    private static final String INTEGER_REGEX = "[1-9]\\d*";

    private static final String FINAL_REGEX = START_REGEX + OPEN_SQUARE_BRACKETS + BLANK +
                    ALLOWED_REGEX + BLANK + HYPHEN + BLANK + INTEGER_REGEX + BLANK + CLOSE_SQUARE_BRACKETS + END_REGEX;

    private static final Pattern pattern = Pattern.compile(FINAL_REGEX);

    public static void validateItemInput(String input){
        if (!pattern.matcher(input).matches()){
            throw new IllegalArgumentException(ErrorMessages.INVALID_ITEM_INPUT.getMessage());
        }
    }

}
