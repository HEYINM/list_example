package kr.yper.com.yperinterntest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

public class AddActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etKey;
    private EditText etAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = findViewById(R.id.toolbar);
        etKey = findViewById(R.id.etKey);
        etAddress = findViewById(R.id.etAddress);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_complete:
                complete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void complete() {
        int key;
        try {
            key = Integer.parseInt(etKey.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.msg_drive_primary_key, Toast.LENGTH_SHORT).show();
            return;
        }
        String address = etAddress.getText().toString();
        if (address.isEmpty()) {
            Toast.makeText(this, R.string.msg_need_address, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent result = new Intent();
        result.putExtra(Constants.KEY_DRIVE_PRIMARY_KEY, key);
        result.putExtra(Constants.KEY_DRIVE_ADDRESS, address);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
