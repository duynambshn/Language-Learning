package jp.helpnserve.LTS.Model;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Play {
	public static void main(String[] args) throws InterruptedException {
		File path = new File("sample.wav");

		// 指定されたURLのオーディオ入力ストリームを取得
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)) {

			// ファイルの形式取得
			AudioFormat af = ais.getFormat();

			// 単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(SourceDataLine.class, af);

			// 指定された Line.Info オブジェクトの記述に一致するラインを取得
			SourceDataLine s = (SourceDataLine) AudioSystem.getLine(dataLine);

			// 再生準備完了
			s.open();

			// ラインの処理を開始
			s.start();

			// 読み込みサイズ
			byte[] data = new byte[s.getBufferSize()];

			// 読み込んだサイズ
			int size = -1;

			// 再生処理のループ
			while (true) {
				// オーディオデータの読み込み
				size = ais.read(data);
				if (size == -1) {
					// すべて読み込んだら終了
					break;
				}
				// ラインにオーディオデータの書き込み
				s.write(data, 0, size);
			}

			// 残ったバッファをすべて再生するまで待つ
			s.drain();

			// ライン停止
			s.stop();

			// リソース解放
			s.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}