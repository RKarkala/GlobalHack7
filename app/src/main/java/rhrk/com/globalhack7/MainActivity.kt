package rhrk.com.globalhack7


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var record: Button
    private lateinit var transcript: TextView
    private lateinit var t1: TextToSpeech
    private lateinit var mSpeechRecognizer: SpeechRecognizer
    private lateinit var mSpeechRecognizerIntent: Intent
    private var mIslistening: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        record = findViewById(R.id.record)
        transcript = findViewById(R.id.transcript)
        record.setOnClickListener(this)
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                "zh")

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.packageName)


        val listener = SpeechRecognitionListener()
        mSpeechRecognizer.setRecognitionListener(listener)
    }

    override fun onClick(view: View) {
        if (view === record) {
            if (record.text == getString(R.string.record)) {
                record.text = getString(R.string.stop)
            } else {
                record.text = getString(R.string.record)
            }
            mIslistening = !mIslistening
            if (mIslistening) {
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent)
            } else {
                mSpeechRecognizer.stopListening()
            }
        }
    }


    private inner class SpeechRecognitionListener : RecognitionListener {

        override fun onBeginningOfSpeech() {
            //Log.d(TAG, "onBeginingOfSpeech");
        }

        override fun onBufferReceived(buffer: ByteArray) {

        }

        override fun onEndOfSpeech() {
            //Log.d(TAG, "onEndOfSpeech");
        }

        override fun onError(error: Int) {
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent)

            //Log.d(TAG, "error = " + error);
        }

        override fun onEvent(eventType: Int, params: Bundle) {

        }

        override fun onPartialResults(partialResults: Bundle) {

        }

        override fun onReadyForSpeech(params: Bundle) {
            Log.d("Speech", "onReadyForSpeech") //$NON-NLS-1$
        }

        override fun onResults(results: Bundle) {
            //Log.d(TAG, "onResults"); //$NON-NLS-1$
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            for (match in matches!!) {
                Log.i("Transcript", match)
            }
            record.text = getString(R.string.record)
            t1 = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { i ->
                if (i == TextToSpeech.SUCCESS) {
                    t1.language = Locale.SIMPLIFIED_CHINESE
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        t1.speak(matches[0], TextToSpeech.QUEUE_FLUSH, null, null)
                    }else {
                        t1.speak(matches[0], TextToSpeech.QUEUE_FLUSH, null)
                    }
                }
            }, "com.google.android.tts")

            transcript.text = matches[0]
            // matches are the return values of speech recognition engine
            // Use these values for whatever you wish to do
        }

        override fun onRmsChanged(rmsdB: Float) {}
    }

}
