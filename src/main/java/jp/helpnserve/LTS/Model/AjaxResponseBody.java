package jp.helpnserve.LTS.Model;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AjaxResponseBody {

	String msg;

	private int id;

	private String originSentence;

	private String translateSentence;

	private String explanation;

	private String soundUrl;

	Timestamp lastTime;

}
