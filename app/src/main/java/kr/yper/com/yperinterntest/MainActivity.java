package kr.yper.com.yperinterntest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView tvNone;
    private ItemAdapter adapter;
    private SimpleDateFormat dateFormatter;

    private int recyclerViewMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        tvNone = findViewById(R.id.tvNone);
        dateFormatter = new SimpleDateFormat(Constants.dateFormat, Locale.getDefault());
        recyclerViewMargin = getResources().getDimensionPixelSize(R.dimen.recyclerview_margin);

        setSupportActionBar(toolbar);

        adapter = new ItemAdapter(new ItemAdapter.ACallback() {
            @Override
            public void onClickItem(int pos, Item item) {
                final int position = pos;
                new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setTitle(String.format(getString(R.string.msg_delete_item), item.key))
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.deleteItem(position);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }

            @Override
            public void needItemsCheck(int count) {
                checkVisible();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                outRect.left = recyclerViewMargin;
                outRect.right = recyclerViewMargin;
                if (pos == 0) {
                    outRect.top = recyclerViewMargin;
                    outRect.bottom = recyclerViewMargin;
                } else {
                    outRect.top = 0;
                    outRect.bottom = recyclerViewMargin;
                }
            }
        });

        checkVisible();
    }

    private void checkVisible() {
        if (adapter.getItemCount() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                openNewItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_ADD_ITEM && resultCode == Activity.RESULT_OK) {
            int key = data.getIntExtra(Constants.KEY_DRIVE_PRIMARY_KEY, -1);
            String address = data.getStringExtra(Constants.KEY_DRIVE_ADDRESS);
            if (key != -1) {
                Calendar cal = Calendar.getInstance();
                String date = dateFormatter.format(cal.getTime());
                Item item = new Item(date, key, address);
                adapter.addItem(item);
            }
        }
    }

    private void openNewItem() {
        Intent i = new Intent(this, AddActivity.class);
        startActivityForResult(i, Constants.REQUEST_ADD_ITEM);
    }
}
