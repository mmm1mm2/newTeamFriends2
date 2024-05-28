package com.example.demo.quiz;

import java.util.ArrayList;
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

	//以下ChatGPT様のコード
	@RequestMapping("/quiz1")
	public String quiz1(Model model) {
	    // データベースからランダムに10問の問題を取得
	    List<EntQuiz> questions = getRandomQuestions(quizdao.searchDb(), 10);
	    model.addAttribute("questions", questions);

	    // 各問題に対する選択肢を生成
	    for (EntQuiz question : questions) {
	        List<EntQuiz> choices = generateChoices(question, quizdao.searchDb());
	        model.addAttribute("choices_" + question.getId(), choices);
	    }

	    return "quiz/quiz1";
	}

	//問題に対する選択肢を生成するメソッド
	private List<EntQuiz> generateChoices(EntQuiz question, List<EntQuiz> allQuestions) {
		List<EntQuiz> choices = new ArrayList<>();
		choices.add(question); // 正解を最初に追加
		// 他の3つの不正解をランダムに追加
		List<EntQuiz> wrongChoices = getRandomQuestions(allQuestions, 3);
		for (EntQuiz wrongChoice : wrongChoices) {
			if (!wrongChoice.equals(question)) {
				choices.add(wrongChoice);
			}
		}
		Collections.shuffle(choices); // 選択肢をシャッフルしてランダム化
		return choices;
	}

	// リストからランダムに指定された数の要素を取得するメソッド
	private List<EntQuiz> getRandomQuestions(List<EntQuiz> questions, int numQuestions) {
	    Collections.shuffle(questions); // リストをシャッフルしてランダム化
	    int endIndex = Math.min(numQuestions, questions.size()); // インデックスの上限をリストのサイズに合わせる
	    return questions.subList(0, endIndex); // ランダムに選択された問題を返す
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
