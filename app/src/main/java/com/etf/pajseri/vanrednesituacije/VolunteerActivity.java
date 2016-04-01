package com.etf.pajseri.vanrednesituacije;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Djordje on 4/1/2016.
 */
public class VolunteerActivity extends AppCompatActivity {
    VolunteerObject mVolunteer = null;
    TextView mDescription, mNumber, mName;
    Button mCallButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_card_view);

        mVolunteer = (VolunteerObject) getIntent().getExtras().getSerializable("volunteer");
        System.out.println(mVolunteer);

        mDescription = (TextView) findViewById(R.id.volunteer_skills);
        mNumber = (TextView) findViewById(R.id.volunteer_number);
        mName = (TextView) findViewById(R.id.volunteer_name);

        mDescription.setText(mVolunteer.getDescription());
        mNumber.setText(mVolunteer.getPhoneNum());
        mName.setText(mVolunteer.getTitle());

        mCallButton = (Button) findViewById(R.id.volunteer_call);
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(VolunteerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(VolunteerActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);

                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mNumber.getText()));
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try{
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mNumber.getText()));
                        startActivity(intent);
                    }catch(SecurityException sec){
                        System.out.println(sec);
                    }


                } else {


                }
                return;
            }
        }
    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 5;
}
