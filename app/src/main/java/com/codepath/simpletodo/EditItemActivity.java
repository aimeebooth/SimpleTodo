package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import org.w3c.dom.Text;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.codepath.simpletodo.R.id.btnSaveItem;
import static com.codepath.simpletodo.R.id.etEditItem;
import static com.codepath.simpletodo.R.id.etNewItem;
import static com.codepath.simpletodo.R.id.lvItems;

public class EditItemActivity extends AppCompatActivity {
  private int position;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });

    EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
    String value = getIntent().getStringExtra(MainActivity.KEY_ITEM);
    etEditItem.setText(value);
    etEditItem.setSelection(etEditItem.getText().length());
  }

  public void onSaveItem(View v) {
    EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
    // Prepare data intent
    Intent data = new Intent();
    // Pass relevant data back as a result
    position = getIntent().getIntExtra(MainActivity.KEY_POSITION, 0);
    data.putExtra(MainActivity.KEY_ITEM, etEditItem.getText().toString());
    data.putExtra(MainActivity.KEY_POSITION, position);
    data.putExtra(MainActivity.REQUEST_CODE, MainActivity.REQUEST_VALUE); // ints work too
    // Activity finished ok, return the data
    setResult(RESULT_OK, data); // set result code and bundle data for response
    finish(); // closes the activity, pass data to parent
  }
}
