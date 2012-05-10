package com.chronosystems.speech.view;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AndroidTextToSpeechActivity extends Activity implements
TextToSpeech.OnInitListener {
	/** Called when the activity is first created. */

	private TextToSpeech tts;
	private Button btnSpeak;
	private EditText txtText;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tts = new TextToSpeech(this, this);

		btnSpeak = (Button) findViewById(R.id.btnSpeak);

		txtText = (EditText) findViewById(R.id.txtText);

		// button on click event
		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View arg0) {
				speakOut();
			}

		});
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(final int status) {
		// TODO Auto-generated method stub

		if (status == TextToSpeech.SUCCESS) {

			final int result = tts.setLanguage(Locale.getDefault());
			// tts.setPitch(5); // set pitch level
			// tts.setSpeechRate(2); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				final Intent installIntent = new Intent();
				installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
				Log.e("TTS", "Language is not supported");
			} else {
				btnSpeak.setEnabled(true);
				speakOut();
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}

	private void speakOut() {

		final String text = txtText.getText().toString();

		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
}