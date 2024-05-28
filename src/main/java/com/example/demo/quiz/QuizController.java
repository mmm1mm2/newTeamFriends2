package com.example.demo.quiz;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.QuizDao;
import com.example.demo.entity.EntQuiz;

@Controller
public class QuizController {

	//QuizDaoの用意
	private final QuizDao quizdao;

	@Autowired
	public QuizController(QuizDao quizdao) {
		this.quizdao = quizdao;
	}

	@RequestMapping("/index")
	public String start(Model model) {
		model.addAttribute("title", "この人誰だろな？");
		return "index";
	}

/*	@RequestMapping("/quiz1")
	public String quiz1(Model model) {

		List<EntQuiz> list = quizdao.searchDb();
		model.addAttribute("dbList", list);
		model.addAttribute("message", "問題");
		return "quiz/quiz1";
	}*/
	
	//以下ChatGPT様のコード
	@RequestMapping("/quiz1")
    public String quiz1(Model model) {
        // データベースからランダムに10問の問題を取得
        List<EntQuiz> questions = getRandomQuestions(quizdao.searchDb(), 10);
        model.addAttribute("questions", questions);

        return "quiz/quiz1";
	}
	
	// リストからランダムに指定された数の要素を取得するメソッド
    private List<EntQuiz> getRandomQuestions(List<EntQuiz> questions, int numQuestions) {
        Collections.shuffle(questions); // リストをシャッフルしてランダム化
        return questions.subList(0, numQuestions); // ランダムに選択された10問を返す
    }
	//ここまでChatGPT

	
	@RequestMapping("/right")
	public String right(Model model) {
		return "quiz/right";
	}

	@RequestMapping("/wrong")
	public String wrong(Model model) {
		return "quiz/wrong";
	}
}
