package sg.howard.twitterclient;

import androidx.appcompat.app.AppCompatActivity;
import sg.howard.twitterclient.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;

public class Activity_Wellcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__wellcome);
        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (Exception e){

                }finally {
                    startActivity(new Intent(Activity_Wellcome.this,LoginActivity.class));
                }
            }
        });
        thread.start();
    }
}
