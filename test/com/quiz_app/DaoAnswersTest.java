package com.quiz_app;

import com.quiz_app.entity.Question;
import com.quiz_app.entity.Answer;
import com.quiz_app.dao.DaoAnswers;
import com.quiz_app.dao.DaoQuestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaoAnswersTest {

    private List<Question> testQuestions;
    DaoQuestion daoQuestion;

    DaoAnswers daoAnswers;

    @BeforeEach
    public void ConnectionAndDataSetup() throws Exception {
        daoQuestion = new DaoQuestion();
        daoAnswers = new DaoAnswers();
        testQuestions = new ArrayList<>();

        Question q2 = new Question(11, "math", 0, "2+2*2 =?");
        Answer q2a1 = new Answer(q2.getQuestion_id(), "12", false);
        Answer q2a2 = new Answer(q2.getQuestion_id(), "4", false);
        Answer q2a3 = new Answer(q2.getQuestion_id(), "6", true);
        q2.setAnswers(q2a1);
        q2.setAnswers(q2a2);
        q2.setAnswers(q2a3);
        testQuestions.add(q2);

        Question q3 = new Question(12, "math", 0, "1+1+1*(1+1) =?");
        Answer q3a1 = new Answer(q3.getQuestion_id(), "11", false);
        Answer q3a2 = new Answer(q3.getQuestion_id(), "6", false);
        Answer q3a3 = new Answer(q3.getQuestion_id(), "4", true);
        q3.setAnswers(q3a1);
        q3.setAnswers(q3a2);
        q3.setAnswers(q3a3);
        testQuestions.add(q3);

        Question q1 = new Question(1, "math", 2, "What is the square root of 16?");
        Answer q1a1 = new Answer(q1.getQuestion_id(), "4", true);
        Answer q1a2 = new Answer(q1.getQuestion_id(), "8", false);
        Answer q1a3 = new Answer(q1.getQuestion_id(), "2", false);
        q1.setAnswers(q1a1);
        q1.setAnswers(q1a2);
        q1.setAnswers(q1a3);
        testQuestions.add(q1);
    }
    @AfterEach
    public void CloseDBconn()throws Exception {
        daoQuestion.CloseDBConnection();
    }

    @Test
    void addAnswer() throws Exception {
        Question QuestionToUpdate = testQuestions.get(1);
        Answer newAnswer = new Answer(QuestionToUpdate.getQuestion_id(),"12",false);
        QuestionToUpdate.setAnswers(newAnswer);
        daoAnswers.addAnswer(newAnswer);
        Question DbReturtedQuestion = daoQuestion.getQuestion(QuestionToUpdate.getQuestion_id());
        daoAnswers.deleteAnswer(newAnswer.getQuestion_id(), newAnswer.getText());
        assertEquals(QuestionToUpdate.toString(),DbReturtedQuestion.toString());
    }

    @Test
    void updateAnswer() throws Exception {
        Question QuestionToUpdate = testQuestions.get(1);
        Answer answerToUpdate = QuestionToUpdate.getAnswers().get(1);
        daoAnswers.updateAnswer(QuestionToUpdate.getQuestion_id(),"4","44",0);
        Answer updatedAnswerFromDb = daoQuestion.getQuestion(QuestionToUpdate.getQuestion_id()).getAnswers().get(1);
        daoAnswers.updateAnswer(QuestionToUpdate.getQuestion_id(),"44","4",1);
        assertEquals(answerToUpdate.toString(),updatedAnswerFromDb.toString());
    }

    @Test
    void deleteAnswer() throws Exception {
        Question QuestionToUpdate = testQuestions.get(0);
        System.out.println(QuestionToUpdate);
        int answersAmount = QuestionToUpdate.getAnswers().size();
        Answer answerToDelete = QuestionToUpdate.getAnswers().get(0);
        daoAnswers.deleteAnswer(QuestionToUpdate.getQuestion_id(),answerToDelete.getText());
        Question DbQuestion = daoQuestion.getQuestion(QuestionToUpdate.getQuestion_id());
        System.out.println(DbQuestion);
        int newAnswerAmount = DbQuestion.getAnswers().size();
        daoAnswers.addAnswer(answerToDelete);
        assertEquals(answersAmount-1,newAnswerAmount);
    }
}
