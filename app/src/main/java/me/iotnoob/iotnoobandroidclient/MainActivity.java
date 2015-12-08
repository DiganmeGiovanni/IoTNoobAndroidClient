package me.iotnoob.iotnoobandroidclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final String BACKEND_URL = "http://192.241.215.123";

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
                Snackbar.make(view, getString(R.string.info_about), Snackbar.LENGTH_LONG)
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

        // Turn servo clockwise
        Button turnClockwiseBtn = (Button) this.findViewById(R.id.turnClockwiseBtn);
        turnClockwiseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setStatus(1);
                new Thread(new Runnable() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(BACKEND_URL + "/api/servo/turnClockwise")
                                .build();

                        try {
                            Response response = client.newCall(request).execute();
                            Log.w("TAG", response.body().string());

                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        // Turn servo counterclockwise
        Button turnCounterClockwise = (Button) this.findViewById(R.id.turnCounterClockwiseBtn);
        turnCounterClockwise.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setStatus(0);
                new Thread(new Runnable() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(BACKEND_URL + "/api/servo/turnNoClockwise")
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

        // Stops the servo
        Button stopServoBtn = (Button) this.findViewById(R.id.turnStopBtn);
        stopServoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setStatus(2);
                new Thread(new Runnable() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(BACKEND_URL + "/api/servo/stop")
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

    private void setStatus(int status) {
        TextView statusTV = (TextView) this.findViewById(R.id.statusTV);

        switch (status) {
            case 0:
                statusTV.setText(getString(R.string.status_counterclockwise));
                break;
            case 1:
                statusTV.setText(getString(R.string.status_clockwise));
                break;
            case 2:
                statusTV.setText(getString(R.string.status_stop));
                break;
        }
    }
 }
