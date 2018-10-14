package rhrk.com.globalhack7;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FirstHomeScreen extends AppCompatActivity {

    TextView textView;
    TextView botView;
    String topValues[] = {"欢迎", "Welcome", "स्वागत हे", "Bienvenido",
    "Selamat datang", "желанный", "স্বাগত", "Bem vinda", "Bienvenue"};

    String bottomValues[] = {"点击任意位置开始", "Tap Anywhere to Start",
    "शुरू करने के लिए कहीं भी टैप करें", "Toque en cualquier lugar para comenzar",
    "Ketik Anywhere to Start", "Коснитесь Anywhere, чтобы начать",
    "শুরু করতে যেকোন জায়গায় আলতো চাপুন", "Toque em qualquer lugar para começar",
    "Appuyez n'importe où pour commencer"};
    int index = 0;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_time_home);
        runTopAnimation();

    }

    public void screenTapped(View view){
        Intent i = new Intent(this, ChooseLanguage.class);
        startActivity(i);
    }
    private void runTopAnimation(){

        final AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(1000);
        animation1.setStartOffset(500);
        final AlphaAnimation animation2 = new AlphaAnimation(1.0f, 0.0f);
        animation2.setDuration(1000);
        animation2.setStartOffset(500);
        textView = findViewById(R.id.welcome);
        botView = findViewById(R.id.tap);
        textView.startAnimation(animation1);


        //animation1 AnimationListener
        animation1.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation2 when animation1 ends (continue)
                textView.startAnimation(animation2);
                botView.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });


        //animation2 AnimationListener
        animation2.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation1 when animation2 ends
                textView.setText(topValues[(++index)%topValues.length]);
                botView.setText(bottomValues[(index)%bottomValues.length]);
                textView.startAnimation(animation1);
                botView.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });

    }

}
