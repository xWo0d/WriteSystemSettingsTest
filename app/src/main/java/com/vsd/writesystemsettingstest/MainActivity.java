package com.vsd.writesystemsettingstest;

import android.content.Intent;
import android.content.pm.PermissionInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnTest;

    private static String URI = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        setListeners();

    }

    private void setListeners() {

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWriteSettingsPermission();
            }
        });

    }

    private void initUi() {

        btnTest = findViewById(R.id.btn_test);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private void checkWriteSettingsPermission(){

        if (Build.VERSION.SDK_INT > 22) {
            if(!Settings.System.canWrite(this)){
                startActivity(new Intent().setAction(Settings.ACTION_MANAGE_WRITE_SETTINGS));
            }
        } else {

            Settings.System.putString(this.getContentResolver(), URI, "test");

            Toast.makeText(this, Settings.System.getString(this.getContentResolver(),
                    URI), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT > 22) {
            if (Settings.System.canWrite(this)){
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();

                // this code does not work as expected even if permission granted and throw
                // "java.lang.RuntimeException: Unable to resume activity
                // {com.vsd.writesystemsettingstest/com.vsd.writesystemsettingstest.MainActivity}:
                // java.lang.IllegalArgumentException: You cannot keep your settings in the secure
                // settings.
                Settings.System.putString(this.getContentResolver(), URI, "test");

                Toast.makeText(this, Settings.System.getString(this.getContentResolver(),
                        URI), Toast.LENGTH_SHORT).show();
            }
        } else {

            Settings.System.putString(this.getContentResolver(), URI, "test");

            Toast.makeText(this, Settings.System.getString(this.getContentResolver(),
                    URI), Toast.LENGTH_SHORT).show();


        }

    }
}
