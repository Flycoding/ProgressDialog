package com.flyingh.progressdialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String[] BOOKS = { "Java", "Android", "C", "C++" };
	private static final int RED = 0;
	private static final int GREEN = 1;
	private static final int BLUE = 2;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.text_view);
		textView.setText("Background color could be changed!!!");
		registerForContextMenu(textView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderIcon(R.drawable.ic_launcher);
		menu.setHeaderTitle("Change background color:");
		menu.add(0, RED, 0, "RED");
		menu.add(0, GREEN, 0, "GREEN");
		menu.add(0, BLUE, 0, "BLUE");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case RED:
			textView.setBackgroundColor(Color.RED);
			break;
		case GREEN:
			textView.setBackgroundColor(Color.GREEN);
			break;
		case BLUE:
			textView.setBackgroundColor(Color.BLUE);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	public void show(View view) {
		ProgressDialog.show(this, "this is the title!", "this is the message!", false, true);
	}

	public void show2(View view) {
		ProgressDialog pd = new ProgressDialog(this);
		pd.setTitle("Hello world!!!");
		pd.setMessage("Hello world!!!This is the message!!!!!");
		pd.setCancelable(true);
		pd.setIndeterminate(true);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.show();
	}

	public void show3(View view) {
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setTitle("Progress");
		pd.setMessage("Current progress:");
		pd.setCancelable(false);
		pd.setIndeterminate(false);
		pd.setMax(100);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (pd.getProgress() < pd.getMax()) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							pd.setProgress(pd.getProgress() + 1);
						}
					});
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pd.dismiss();
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		SubMenu booksSubMenu = menu.addSubMenu("Books");
		booksSubMenu.setIcon(R.drawable.ic_launcher);
		booksSubMenu.setHeaderIcon(R.drawable.ic_launcher);
		booksSubMenu.setHeaderTitle("Please choose a book:");
		for (int i = 0; i < BOOKS.length; i++) {
			booksSubMenu.add(0, i, 0, BOOKS[i]);
		}
		MenuItem menuItem = menu.add("Open Baidu");
		menuItem.setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com")));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "You choose the " + BOOKS[item.getItemId()] + " book!", Toast.LENGTH_SHORT).show();
		return super.onOptionsItemSelected(item);
	}

}
