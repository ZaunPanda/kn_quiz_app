package com.quiz_app.dao;

import com.quiz_app.entity.Answer;
import com.quiz_app.entity.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoQuestion {
    public static Connection con;

    public DaoQuestion() throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/kn_quizdb"; // table details
        String username = "bestuser"; // MySQL credentials
        String password = "bestuser";
        Class.forName("com.mysql.cj.jdbc.Driver"); // Driver name
        con = DriverManager.getConnection(url, username, password);
        System.out.println("Connection Established successfully");

    }

    public Question getQuestion(Integer question_id) throws Exception {
        Question question = new Question();

        String QuestionQuery = "select * FROM questions WHERE question_id = " + question_id;
        String AnswerQuery = "select * FROM responses WHERE question_id = " + question_id;

        Statement qst = con.prepareStatement(QuestionQuery);
        ResultSet qrs = qst.executeQuery(QuestionQuery);

        if (qrs.next()) {
            int questionId = qrs.getInt("question_id");
            String questionText = qrs.getString("question");
            int questionDifficulty = qrs.getInt("difficulty");
            String questionTopic = qrs.getString("topic");

            question.setQuestion_id(questionId);
            question.setQuestion(questionText);
            question.setDifficulty(questionDifficulty);
            question.setTopic(questionTopic);


            ResultSet ars = qst.executeQuery(AnswerQuery);
            while (ars.next()){
                String answerText = ars.getString("response_text");
                boolean answerIsCorrect = ars.getBoolean("is_correct");

                Answer answer = new Answer(questionId,answerText,answerIsCorrect);
                question.setAnswers(answer);
            }

        }

        qst.close();
        System.out.println("Connection Closed....");

        return question;
    }

    public static List<Question> getQuestionsByTopic(String topic)throws Exception{
        List<Question> questions = new ArrayList<>();

        String QuestionQuery = "select * FROM questions WHERE topic = '" + topic + "'";
        System.out.println(QuestionQuery);

        Statement qst = con.prepareStatement(QuestionQuery);
        ResultSet qrs = qst.executeQuery(QuestionQuery);
        while (qrs.next()) {
            int questionId = qrs.getInt("question_id");
            String questionText = qrs.getString("question");
            int questionDifficulty = qrs.getInt("difficulty");
            String questionTopic = qrs.getString("topic");
            Question question = new Question();

            question.setQuestion_id(questionId);
            question.setQuestion(questionText);
            question.setDifficulty(questionDifficulty);
            question.setTopic(questionTopic);
            questions.add(question);

            String AnswerQuery = "select * FROM responses WHERE question_id = " + questionId;

            Statement ast = con.prepareStatement(AnswerQuery);
            ResultSet ars = ast.executeQuery(AnswerQuery);
            while (ars.next()) {
                String answerText = ars.getString("response_text");
                boolean answerIsCorrect = ars.getBoolean("is_correct");
                Answer answer = new Answer(questionId,answerText,answerIsCorrect);
                question.setAnswers(answer);
            }
        }
        qst.close(); // close statement
        return questions;


    }
    public  void saveQuestion(Question question) throws Exception {
        int question_id = question.getQuestion_id();
        String question_text = question.getQuestion();
        String topic = question.getTopic();
        int difficulty = question.getDifficulty();
        String requestForQuestion = "INSERT INTO questions(question_id,topic, difficulty, question) VALUES ('" + question_id + "','" + topic + "', " + difficulty + ", '" + question_text + "')";
        Statement st = con.prepareStatement(requestForQuestion);
        st.executeUpdate(requestForQuestion);
        st.close(); // close statement


        for(Answer answer:question.getAnswers()){
            int is_correct = answer.isRightAnswer() ? 1 : 0;
            String requestForAnswers = "INSERT INTO responses(question_id,response_text,is_correct) VALUES ('" + question_id + "','" + answer.getText() + "', '" + is_correct + "')";
            Statement ansWst = con.prepareStatement(requestForAnswers);
            ansWst.executeUpdate(requestForAnswers);
            ansWst.close();
        }


    }
    public  void updateQuestion(int question_id, String question, String topic, Integer difficulty) throws Exception {
        StringBuilder query = new StringBuilder("UPDATE questions SET ");

        if (topic != null) {
            query.append("topic = '").append(topic).append("', ");
        }

        if (difficulty != null) {
            query.append("difficulty = ").append(difficulty).append(", ");
        }

        if (question != null) {
            query.append("question = '").append(question).append("', ");
        }

        query.deleteCharAt(query.length() - 2); // Удаляем лишнюю запятую и пробел перед закрывающей скобкой

        query.append("WHERE question_id = ").append(question_id);

        System.out.println(query);
        Statement st = con.createStatement();
        st.executeUpdate(query.toString());
        // Execute query
        st.close(); // close statement

    }
    public  void deleteQuestion(int question_id) throws Exception {
        String query = "DELETE FROM questions WHERE question_id = " + question_id;
        Statement st = con.createStatement();
        st.executeUpdate(query);
        // Execute query
        st.close(); // close statement
    }

    public void CloseDBConnection () throws Exception {
        con.close(); // close connection
    }


}
