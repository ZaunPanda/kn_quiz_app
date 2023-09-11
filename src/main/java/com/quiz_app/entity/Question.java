    package com.quiz_app.entity;

    import java.util.ArrayList;
    import java.util.Comparator;
    import java.util.List;

    public class Question {
        private int question_id;
        private String topic;
        private int difficulty;
        private String question;
        private List<Answer> answers;


        public Question() {
            this.answers = new ArrayList<>();
        }

        public Question(int questionId, String topic, int difficulty, String question) {
            this.question_id = questionId;
            this.topic = topic;
            this.difficulty = difficulty;
            this.question = question;
            this.answers = new ArrayList<>();
        }

        public int getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public List<Answer> getAnswers() {
            return answers;
        }

        public void setAnswers(Answer answer) {

            answers.add(answer);
        }

        public void sortAnswers() {
            answers.sort(Comparator.comparing(Answer::getText));
        }

        @Override
        public String toString() {
            return "Entity.Question{" +
                    "question_id=" + question_id +
                    ", topic='" + topic + '\'' +
                    ", difficulty=" + difficulty +
                    ", question='" + question + '\'' +
                    ", answers=" + answers +
                    '}';
        }
    }
