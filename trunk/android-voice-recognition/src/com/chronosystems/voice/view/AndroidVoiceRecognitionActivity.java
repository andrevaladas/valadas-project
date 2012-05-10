package com.chronosystems.voice.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * A very simple application to handle Voice Recognition intents
 * and display the results
 */
public class AndroidVoiceRecognitionActivity extends Activity implements TextToSpeech.OnInitListener {
	private static final int REQUEST_CODE = 1234;
	private ListView wordsList;

	private TextToSpeech tts;

	/**
	 * Called with the activity is first created.
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_recog);

		final Button speakButton = (Button) findViewById(R.id.speakButton);

		tts = new TextToSpeech(this, this);

		wordsList = (ListView) findViewById(R.id.list);
		wordsList.setClickable(true);
		wordsList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				final Object o = wordsList.getItemAtPosition(position);
				speakOut(o.toString());
			}

		});

		// Disable button if no recognition service is present
		final PackageManager pm = getPackageManager();
		final List<ResolveInfo> activities = pm.queryIntentActivities(
				new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0)
		{
			speakButton.setEnabled(false);
			speakButton.setText("Recognizer not present");
		}
	}

	/**
	 * Handle the action of the button being clicked
	 */
	public void speakButtonClicked(final View v)
	{
		startVoiceRecognitionActivity();
	}

	/**
	 * Fire an intent to start the voice recognition activity.
	 */
	private void startVoiceRecognitionActivity() {
		final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
		startActivityForResult(intent, REQUEST_CODE);
	}

	/**
	 * Handle the results from the voice recognition activity.
	 */
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data)	{
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
		{
			// Populate the wordsList with the String values the recognition engine thought it heard
			final ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,matches));
		}
		super.onActivityResult(requestCode, resultCode, data);
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
		if (status == TextToSpeech.SUCCESS) {
			final int result = tts.setLanguage(Locale.getDefault());
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				final Intent installIntent = new Intent();
				installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
				Log.e("TTS", "Language is not supported");
			}
		} else {
			Log.e("TTS", "Initilization Failed");
		}
	}

	private void speakOut(final String text) {
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

}