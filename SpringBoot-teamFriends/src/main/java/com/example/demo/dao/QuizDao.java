package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EntQuiz;

@Repository
public class QuizDao {

	public List<EntQuiz> searchDb() {
		//データベースからランダムで１つ取り出し、resultDB1に入れる（重複あり）
		String sql = "	SELECT * FROM sample ORDER BY RAND() LIMIT 1;";
		//画面に表示しやすい形のList(resultDB2)を用意
		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		//1件ずつピックアップ
		List<EntQuiz> resultDb2 = new ArrayList<EntQuiz>();

		for (Map<String, Object> result1 : resultDb1) {
			//データ1件分を1つのまとまりとしたEntForm型の「entformdb」を生成
			EntQuiz entformdb = new EntQuiz();
			//id、nameのデータをentformdbに移す
			entformdb.setName((String) result1.get("name"));
			entformdb.setQuestion((String) result1.get("question"));
			//移し替えたデータを持ったentformdbを、resultDB2に入れる
			resultDb2.add(entformdb);
		}
		//Controllerに渡す
		return resultDb2;
	}

	private final JdbcTemplate db;

	public QuizDao(JdbcTemplate db) {
		this.db = db;
	}

	
	// 重複を避けるための問題リスト
	private List<Integer> usedQuestions = new ArrayList<>();

	// 重複のないランダムな問題を取得
	public EntQuiz getUniqueRandomQuestion() {
		// データベースからランダムに1つの問題を取得
		String sql = "SELECT * FROM sample ORDER BY RAND() LIMIT 1";
		Map<String, Object> result = db.queryForMap(sql);

		// 取得したデータをEntQuizオブジェクトにマッピング
		EntQuiz question = new EntQuiz();
		question.setId((Integer) result.get("id"));
		question.setName((String) result.get("name"));
		question.setQuestion((String) result.get("question"));

		// 使用した問題のIDをリストに追加して重複を避ける
		usedQuestions.add(question.getId());

		return question;
	}
}
