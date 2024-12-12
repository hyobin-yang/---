package store.model.policy;

import store.exception.ErrorMessages;

public enum Answer {
    POSITIVE("Y"), NEGATIVE("N");

    private final String answer;

    Answer(String answer){
        this.answer = answer;
    }

    public static Answer getAnswer(String answer){
        if (answer.trim().toUpperCase().equals(POSITIVE.answer)){
            return POSITIVE;
        }
        if (answer.trim().toUpperCase().equals(NEGATIVE.answer)){
            return NEGATIVE;
        }
        throw new IllegalArgumentException(ErrorMessages.INVALID_ANSWER_INPUT.getMessage());
    }
}
