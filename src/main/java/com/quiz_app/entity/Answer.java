package com.quiz_app.entity;
public class Answer {
    private int question_id;
    private String text;
    private boolean IsRightAnswer;

    public Answer(int question_id, String text, boolean isRightAnswer) {
        this.question_id = question_id;
        this.text = text;
        IsRightAnswer = isRightAnswer;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRightAnswer() {
        return IsRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        IsRightAnswer = rightAnswer;
    }

    @Override
    public String toString() {
        return "Entity.Answer{" +
                "question_id=" + question_id +
                ", text='" + text + '\'' +
                ", IsRightAnswer=" + IsRightAnswer +
                '}';
    }
}