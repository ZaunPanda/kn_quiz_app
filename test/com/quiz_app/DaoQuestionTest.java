package java.com.quiz_app;

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

class DaoQuestionTest {

    private List<Question> testQuestions;
    DaoQuestion daoQuestion;

    @BeforeEach
    public void ConnectionAndDataSetup() throws Exception {
        daoQuestion = new DaoQuestion();
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
    void getQuestion() throws Exception {
        for (Question question : testQuestions) {
            Question DbReturtedQuestion = daoQuestion.getQuestion(question.getQuestion_id());
            assertEquals(question.toString(), DbReturtedQuestion.toString());


        }
    }

    @Test
    void getQuestionsByTopic() throws Exception {
        List<Question> dbQestionList = DaoQuestion.getQuestionsByTopic("math");
        assertEquals(dbQestionList.size(), testQuestions.size());

    }

    @Test
    void saveQuestion() throws Exception{
        Question SaveQuestionTest = new Question(667755, "test", 0, "Test data");
        Answer sQa1 = new Answer(SaveQuestionTest.getQuestion_id(), "0", true);
        Answer sQa2 = new Answer(SaveQuestionTest.getQuestion_id(), "1", false);
        Answer sQa3 = new Answer(SaveQuestionTest.getQuestion_id(), "2", false);
        SaveQuestionTest.setAnswers(sQa1);
        SaveQuestionTest.setAnswers(sQa2);
        SaveQuestionTest.setAnswers(sQa3);

        daoQuestion.saveQuestion(SaveQuestionTest);

        Question DbReturtedQuestion = daoQuestion.getQuestion(SaveQuestionTest.getQuestion_id());

        assertEquals(SaveQuestionTest.toString(), DbReturtedQuestion.toString());
        daoQuestion.deleteQuestion(SaveQuestionTest.getQuestion_id());

    }

    @Test
    void updateQuestion() throws Exception{
        Question UpdatedQ2 = testQuestions.get(0);
        daoQuestion.updateQuestion(UpdatedQ2.getQuestion_id(),UpdatedQ2.getQuestion(),UpdatedQ2.getTopic(),UpdatedQ2.getDifficulty());
        Question DbReturtedQuestion = daoQuestion.getQuestion(UpdatedQ2.getQuestion_id());
        daoQuestion.updateQuestion(UpdatedQ2.getQuestion_id(),"2+2*2 =?",UpdatedQ2.getTopic(),UpdatedQ2.getDifficulty());
        UpdatedQ2.sortAnswers();
        DbReturtedQuestion.sortAnswers();
        assertEquals(UpdatedQ2.toString(),DbReturtedQuestion.toString());


    }

    @Test
    void deleteQuestion() throws Exception{
        Question QuestionToDelete = testQuestions.get(0);
        daoQuestion.deleteQuestion(QuestionToDelete.getQuestion_id());
        Question DbReturtedQuestion = daoQuestion.getQuestion(QuestionToDelete.getQuestion_id());
        String questionText = DbReturtedQuestion.getQuestion();
        daoQuestion.saveQuestion(QuestionToDelete);
        assertEquals(null,questionText);


    }
}