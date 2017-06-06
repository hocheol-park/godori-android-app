package univ.ajou.godoriapp;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import univ.ajou.godoriapp.util.PermissionListener;
import univ.ajou.godoriapp.util.TedPermission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);


        Button btnRemote = (Button) findViewById(R.id.btn_remote);
        Button btnGallery = (Button) findViewById(R.id.btn_gallery);
        btnRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RemoteActivity.class);
                startActivity(i);
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(i);
            }
        });

        checkPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public PermissionListener permissionlistener;
    public void checkPermission() {

        permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // WHAT
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "You must agree the permission" , Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        // 마시멜로우 이상 버전의 경우 체크.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new TedPermission(this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("You must agree the permission")
                    .setPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    )
                    .check();
        }
        else {
            // WHAT
        }

    }


}
