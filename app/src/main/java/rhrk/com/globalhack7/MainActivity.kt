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
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import java.util.Locale
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    private lateinit var chat: Button;
    private lateinit var record: Button
    private lateinit var t1: TextToSpeech
    private lateinit var mSpeechRecognizer: SpeechRecognizer
    private lateinit var mSpeechRecognizerIntent: Intent
    private lateinit var targetLanguage : Spinner

    private lateinit var sourceLanguageCode : String;
    private var targetLanguageCode: String = "zh"

    private var mIslistening: Boolean = false
    private val ALL_CODE = 11;

    private var codes = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
        if(!hasPermissions(this, permissions)){
            ActivityCompat.requestPermissions(this, permissions, ALL_CODE);
        }

        codes.put("中文", "zh")
        codes.put("English", "en")
        codes.put("हिंदी", "hi")
        codes.put("Español", "es")
        codes.put("Melayu", "ms")
        codes.put("русский", "ru")
        codes.put("বাঙালি", "bn")
        codes.put("Português", "pt")
        codes.put("Français", "fr")
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        sourceLanguageCode = sharedPrefs.getString("source_language", "zh")
        targetLanguage = findViewById(R.id.targetLanguage)
        ArrayAdapter.createFromResource(
                this,
                R.array.language_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            targetLanguage.adapter = adapter
        }
        targetLanguage.onItemSelectedListener = this

        chat = findViewById(R.id.chat)
        record = findViewById(R.id.record)
        record.setOnClickListener(this)
        chat.setOnClickListener(this)
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                sourceLanguageCode)

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.packageName)


        val listener = SpeechRecognitionListener()
        mSpeechRecognizer.setRecognitionListener(listener)
    }

    fun hasPermissions(context: Context?, permissions: Array<String>?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
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
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    sourceLanguageCode)
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                    this.packageName)
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
        } else if (view == chat) {
                //start second activity
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ALL_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(this, "Permissions required", Toast.LENGTH_LONG).show();
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
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
            t1 = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { i ->
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
