package me.iotnoob.iotnoobandroidclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final String BACKEND_URL = "http://192.168.100.8";

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Puños Plata", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.configureListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**********************************************************************************************/
    /**********************************************************************************************/

    /**
     * Configure necessary listeners for items in view
     */
    private void configureListeners() {

        // Change direction button
        Button changeDirectionBtn = (Button) findViewById(R.id.changeDirectionBtn);
        changeDirectionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        String servoDirection = "1";
                        if (flag) {
                            servoDirection = "0";
                        }
                        flag = !flag;

                        RequestBody formBody = new FormEncodingBuilder()
                                .add("servoDirection", servoDirection)
                                .build();
                        Request request = new Request.Builder()
                                .url(BACKEND_URL + "/api/setServoDirection")
                                .post(formBody)
                                .build();

                        try {
                            Response response = client.newCall(request).execute();
                            Log.w("TAG", response.body().toString());
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
