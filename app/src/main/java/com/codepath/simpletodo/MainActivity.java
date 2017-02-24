package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.codepath.simpletodo.R.id.lvItems;

public class MainActivity extends AppCompatActivity {
  ArrayList<String> items;
  ArrayAdapter<String> itemsAdapter;
  ListView lvItems;

  public static final String REQUEST_CODE = "request_code";
  public static final int REQUEST_VALUE = 20;
  public static final String KEY_POSITION = "item_position";
  public static final String KEY_ITEM = "item_text";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    lvItems = (ListView)findViewById(R.id.lvItems);
    readItems();
    itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
    lvItems.setAdapter(itemsAdapter);
    setupListViewListener();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == REQUEST_VALUE) {
      int position = data.getIntExtra(KEY_POSITION, 0);
      String newText = data.getStringExtra(KEY_ITEM);
      items.set(position, newText);
      itemsAdapter.notifyDataSetChanged();
      writeItems();
      Toast.makeText(this, "TODO Updated!", Toast.LENGTH_SHORT).show();
    }
  }

  private void setupListViewListener() {
    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String value = lvItems.getItemAtPosition(position).toString();
        Intent data = new Intent(MainActivity.this, EditItemActivity.class);
        data.putExtra(KEY_POSITION, position);
        data.putExtra(KEY_ITEM, value);
        startActivityForResult(data, REQUEST_VALUE);
      }
    });
  }

  private void readItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      items = new ArrayList<String>(FileUtils.readLines(todoFile));
    } catch (IOException e) {
      items = new ArrayList<String>();
    }
  }

  private void writeItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      FileUtils.writeLines(todoFile, items);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void onAddItem(View v) {
    EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    String itemText = etNewItem.getText().toString();
    itemsAdapter.add(itemText);
    etNewItem.setText("");
    writeItems();
  }
}
