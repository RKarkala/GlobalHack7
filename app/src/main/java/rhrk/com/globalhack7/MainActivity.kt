package rhrk.com.globalhack7


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.Locale
import java.util.jar.Manifest

class MainActivity : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    private lateinit var chat: Button;
    private lateinit var record: Button
    private lateinit var t1: TextToSpeech
    private lateinit var mSpeechRecognizer: SpeechRecognizer
    private lateinit var mSpeechRecognizerIntent: Intent
    private lateinit var targetLanguage : Spinner
    private lateinit var select : TextView;
    private lateinit var sourceLanguageCode : String;
    private var targetLanguageCode: String = "zh"

    private var mIslistening: Boolean = false

    private var codes = HashMap<String, String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.activity_main, null)
        codes.put("中文", "zh")
        codes.put("English", "en")
        codes.put("हिंदी", "hi")
        codes.put("Español", "es")
        codes.put("Melayu", "ms")
        codes.put("русский", "ru")
        codes.put("বাঙালি", "bn")
        codes.put("Português", "pt")
        codes.put("Français", "fr")
        select = view.findViewById(R.id.aa);
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        sourceLanguageCode = sharedPrefs.getString("source_language", "zh")
        targetLanguage = view.findViewById(R.id.targetLanguage)
        ArrayAdapter.createFromResource(
                context,
                R.array.language_array,
                R.layout.spinnertext
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinnertext)
            targetLanguage.adapter = adapter
        }
        targetLanguage.onItemSelectedListener = this
        select.text = Translate().execute(arrayOf(select.text as String, sourceLanguageCode)).get().toString()
        chat = view.findViewById(R.id.chat)
        chat.text = Translate().execute(arrayOf(chat.text as String, sourceLanguageCode)).get().toString()
        record = view.findViewById(R.id.record)
        record.setOnClickListener(this)
        chat.setOnClickListener(this)
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                sourceLanguageCode)

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                context?.packageName)


        val listener = SpeechRecognitionListener()
        mSpeechRecognizer.setRecognitionListener(listener)
        return view
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when(p0!!.id){
            R.id.targetLanguage -> {
                targetLanguageCode = codes.get(p0.getItemAtPosition(p2)).toString()
            }
        }
    }

    override fun onClick(view: View) {
        if (view === record) {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    sourceLanguageCode)
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                    context?.packageName)
            val listener = SpeechRecognitionListener()
            mSpeechRecognizer.setRecognitionListener(listener)
            mIslistening = !mIslistening
            if (mIslistening) {
                record.setBackgroundResource(R.drawable.stop)
            } else {
                record.setBackgroundResource(R.drawable.record)
            }

            if (mIslistening) {
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent)
            } else {
                System.out.println("Destroyed when button says ${record.text}");
                mSpeechRecognizer.stopListening()
                mSpeechRecognizer.cancel()
                mSpeechRecognizer.destroy()

            }
        }
        if(view == chat){
            val temp = sourceLanguageCode
            sourceLanguageCode = targetLanguageCode
            targetLanguageCode = temp
            Toast.makeText(context, "${sourceLanguageCode}->${targetLanguageCode}", Toast.LENGTH_LONG).show()
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
        @Suppress("DEPRECATION")
        override fun onResults(results: Bundle) {
            //Log.d(TAG, "onResults"); //$NON-NLS-1$
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            System.out.println(matches)
            for (match in matches!!) {
                Log.i("Transcript", match)
            }
            record.setBackgroundResource(R.drawable.record)
            mIslistening = false
            System.out.println(matches[0]);
            val a = Translate().execute(arrayOf(matches[0], targetLanguageCode)).get().toString()
            System.out.println(a);
            t1 = TextToSpeech(context, TextToSpeech.OnInitListener { i ->
                if (i == TextToSpeech.SUCCESS) {
                    t1.language = Locale(targetLanguageCode)
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        t1.speak(a, TextToSpeech.QUEUE_FLUSH, null, null)
                    }else {
                        t1.speak(a, TextToSpeech.QUEUE_FLUSH, null)
                    }
                }
            }, "com.google.android.tts")

            // matches are the return values of speech recognition engine
            // Use these values for whatever you wish to do
        }

        override fun onRmsChanged(rmsdB: Float) {}
    }

}
