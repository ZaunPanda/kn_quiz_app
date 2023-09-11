package com.quiz_app.dao;

import com.quiz_app.entity.Answer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DaoAnswers {
    public static Connection con;

    public DaoAnswers() throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/kn_quizdb"; // table details
        String username = "bestuser"; // MySQL credentials
        String password = "bestuser";
        Class.forName("com.mysql.cj.jdbc.Driver"); // Driver name
        con = DriverManager.getConnection(url, username, password);
        System.out.println("Connection Established successfully");

    }
    public  void addAnswer(Answer answer) throws Exception {
        int question_id = answer.getQuestion_id();
        String response_text = answer.getText();
        int is_correct = answer.isRightAnswer() ? 1 : 0;

        String query = "INSERT INTO responses(question_id, response_text, is_correct) VALUES ('" + question_id + "', '" + response_text + "', '" + is_correct + "')";

        Statement st = con.createStatement();
        st.executeUpdate(query);
        // Execute query
        st.close(); // close statement
    }
    public  void updateAnswer(int question_id, String response_text,String new_text, Integer is_correct) throws Exception {
        StringBuilder query = new StringBuilder("UPDATE responses SET ");
        if (new_text != null) {
            query.append("response_text = '").append(new_text).append("', ");
        }
        if (is_correct != null) {
            query.append("is_correct = ").append(is_correct).append(", ");
        }

        query.deleteCharAt(query.length() - 2); // Удаляем лишнюю запятую и пробел перед закрывающей скобкой

        query.append("WHERE question_id = ").append(question_id);
        query.append(" AND response_text = '").append(response_text).append("'");

        Statement st = con.createStatement();
        st.executeUpdate(query.toString());

        // Execute query
        st.close(); // close statement
    }
    public  void deleteAnswer(int question_id,String answer_text ) throws Exception {
        String query = "DELETE FROM responses WHERE question_id = '" + question_id + "' AND response_text = '" + answer_text + "'";
        Statement st = con.createStatement();
        st.executeUpdate(query);
        // Execute query
        st.close(); // close statement
    }

    public void CloseDBConnection () throws Exception {
        con.close(); // close connection
    }
}