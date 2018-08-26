package com.example.my_pc.training;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OpenView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private Boolean permission = false;
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_view);

        recyclerView = (RecyclerView) findViewById(R.id.rV);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = checkSmsPermission();
            if (permission) {
                listItems = new ArrayList<>();
                ListItem listItem;

                Uri uriSms = Uri.parse("content://sms/inbox");
                final Cursor cursor = getContentResolver().query(uriSms,
                        new String[]{"_id", "address", "date", "body"}, null, null, null);

                while (cursor.moveToNext())
                {
                    String address = cursor.getString(1);
                    String msg = cursor.getString(3);
                    listItem = new ListItem(address, msg);
                    listItem.setNumber(address);
                    listItem.setMessage(msg);
                    listItems.add(listItem);
                }

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                adapter = new MyAdapter(listItems, this);
                recyclerView.setAdapter(adapter);
            }

            /**

             for(int i=0; i<10; i++)
             {
             ListItem listItem = new ListItem("SMS " + (i+1), "this is the SMS preview");
             listItems.add(listItem);
             }

             **/


        }
    }
    public boolean checkSmsPermission()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_SMS)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.READ_SMS)
                            == PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}