package com.DuckyDoc.QA.Controller;

import com.DuckyDoc.QA.Model.Answer;
import com.DuckyDoc.QA.Model.Query;
import com.DuckyDoc.QA.Repo.AnswerRepository;
import com.DuckyDoc.QA.Repo.QueryRepository;
import com.DuckyDoc.QA.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QueryRepository queryRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/answers")
    public List<Answer> getAllAnswers() {
        System.out.println("Get all Answers...");

        List<Answer> answers = new ArrayList<>();
        answerRepository.findAll().forEach(answers::add);

        return answers;
    }

    @GetMapping("queries/{queryId}/answers")
    public List<Answer> getAnswers(@PathVariable (value = "queryId") Long queryId) {
        List<Answer> answers = answerRepository.findByQueryId(queryId);

        return answers;
    }

    @GetMapping(value = "answers/{id}")
    public Answer findById(@PathVariable long id) {
        Optional<Answer> answer = answerRepository.findById(id);

        if (answer.isPresent()) {
            Answer _answer = answer.get();
            return _answer;
        } else {
            return null;
        }
    }

    @GetMapping(value = "answers/user/{id_user}")
    public List<Answer> findByUser(@PathVariable long id_user) {
        List<Answer> answers = answerRepository.findByUserId(id_user);

        for(int i = 0; i < answers.size(); i++){
            answers.get(i).getQuery().setAnswers(null);
        }

        return answers;
    }

    @PostMapping(value = "answers/create")
    public Answer postAnswer(@RequestBody Answer answer) {
        System.out.println("Insert new answer...");
        Answer _answer = answerRepository.save(answer);
        return answer;
    }

    //UTILE??
    @DeleteMapping("/answers/delete/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable("id") long id) {
        System.out.println("Delete Answer with ID = " + id + "...");

        answerRepository.deleteById(id);
        return new ResponseEntity<>("Answer has been deleted!", HttpStatus.OK);
    }

    //UTILE??
    @DeleteMapping("/answers/delete")
    public ResponseEntity<String> deleteAllAnswers() {
        System.out.println("Delete All Answers...");

        answerRepository.deleteAll();
        return new ResponseEntity<>("All Answers have been deleted!", HttpStatus.OK);
    }

    //UTILE??
    @PutMapping("/answers/setText/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable("id") long id, @RequestBody String newText) {
        System.out.println("Update Answer with ID = " + id + "...");

        Optional<Answer> answer = answerRepository.findById(id);

        if (answer.isPresent()) {
            Answer _answer = answer.get();
            _answer.setText(newText);
            return new ResponseEntity<>(answerRepository.save(_answer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/answers/correct/{id}")
    public ResponseEntity<Answer> correctAnswer(@PathVariable("id") long id) {
        System.out.println("Update Answer with ID = " + id + "...");

        Optional<Answer> answer = answerRepository.findById(id);

        if (answer.isPresent()) {
            Answer _answer = answer.get();
            _answer.setCorrect(true);
            return new ResponseEntity<>(answerRepository.save(_answer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
