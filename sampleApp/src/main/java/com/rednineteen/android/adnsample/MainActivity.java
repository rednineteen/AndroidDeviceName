package com.rednineteen.android.adnsample;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rednineteen.android.adn.ADNDatabase;
import com.rednineteen.android.adn.ADNDevice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ADNDatabase.init(this);

        mTextMessage                = (TextView) findViewById(R.id.message);
        Button CurrentBtn           = (Button) findViewById(R.id.currentDeviceNameBtn);
        Button CurrentBrandBtn      = (Button) findViewById(R.id.currentDeviceBrandBtn);
        Button CurrentNameBrandBtn  = (Button) findViewById(R.id.currentDeviceNameWithBrandBtn);
        CurrentBtn.setOnClickListener(this);
        CurrentBrandBtn.setOnClickListener(this);
        CurrentNameBrandBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int action = view.getId();
        if (action == R.id.currentDeviceNameBtn) {
            mTextMessage.setText( ADNDatabase.getDeviceName() );
        } else if (action == R.id.currentDeviceBrandBtn) {
            ADNDevice d = ADNDatabase.getDevice(Build.MODEL);
            mTextMessage.setText( d.getBrand() );
        } else if (action == R.id.currentDeviceNameWithBrandBtn) {
            mTextMessage.setText( ADNDatabase.getDeviceName(Build.MODEL, "", true) );
        }
    }
}
