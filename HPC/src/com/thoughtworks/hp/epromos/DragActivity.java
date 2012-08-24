package com.thoughtworks.hp.epromos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.activities.AddProductActivity;
import com.thoughtworks.hp.activities.ShoppingListListingActivity;
import com.thoughtworks.hp.datastore.ShoppingListTable;
import com.thoughtworks.hp.models.ShoppingList;

import java.util.List;

@SuppressLint({ "ParserError", "ParserError" })
public class DragActivity extends Activity implements View.OnLongClickListener, View.OnClickListener, View.OnTouchListener {

	private DragController mDragController;
	private DragLayer mDragLayer;

	private boolean mLongClickStartsDrag = true;

	public static final boolean Debugging = false;

	public void onClick(View v) {
		if (mLongClickStartsDrag) {
			toast("Press and hold to drag an image.");
		}
	}

	private void realizeShoppingLists(LinearLayout layoutToAddComponents) {
		ShoppingListTable databaseInstance = new ShoppingListTable();
		List<ShoppingList> shoppingLists = databaseInstance.findAll();
		int loopVar = 1;
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 300);
		layoutParams.setMargins(20, 10, 20, 10);
		for (final ShoppingList list : shoppingLists) {
			LinearLayout tempInstance = new LinearLayout(this);
			tempInstance.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			tempInstance.setOrientation(LinearLayout.VERTICAL);
			ShoppingBasket shoppingCart = new ShoppingBasket(this, list.getId());
			shoppingCart.setImageDrawable(getResources().getDrawable(R.drawable.list));
			shoppingCart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent addItemsToShoppingList = new Intent(DragActivity.this, AddProductActivity.class);
					addItemsToShoppingList.putExtra(ShoppingList.SHOPPING_LIST_ID, list.getId());
					startActivityForResult(addItemsToShoppingList, 3);

				}
			});
			TextView textView = new TextView(this);
			textView.setText(list.getName());
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			tempInstance.addView(shoppingCart, 0);
			tempInstance.addView(textView, 1);
			layoutToAddComponents.addView(tempInstance, loopVar++, layoutParams);
		}

	}

	@Override
	protected void onActivityResult(int req, int res, Intent data) {
		finish();
		startActivity(this.getIntent());
	}

	public void createNewList(View view) {
		Intent navigation = new Intent(DragActivity.this, ShoppingListListingActivity.class);
		startActivityForResult(navigation, 0);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.demo);

		TableLayout tableView = (TableLayout) findViewById(R.id.image_grid_view);
		LinearLayout shoppingListsGrid = (LinearLayout) findViewById(R.id.bottom_part);
		if (shoppingListsGrid == null)
			toast("Unable to find Shopping lists");
		else {
			realizeShoppingLists(shoppingListsGrid);
		}

		if (tableView == null)
			toast("Unable to find GridView");
		else {
			new PromoRealizer(this, tableView).realizePromotions();
			tableView.getLayoutParams().width = getWindowManager().getDefaultDisplay().getWidth();
			ScrollView mainScroll = (ScrollView) findViewById(R.id.list_scroll);
			mainScroll.getLayoutParams().height = getWindowManager().getDefaultDisplay().getHeight() - 225;
		}

		mDragController = new DragController(this);
		mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
		mDragLayer.setDragController(mDragController);
		mDragLayer.setTableView(tableView);
		mDragLayer.shoppingCarts = shoppingListsGrid;

		mDragController.setDragListener(mDragLayer);

		Toast.makeText(getApplicationContext(), getResources().getString(R.string.instructions), Toast.LENGTH_LONG).show();
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		ImageCell i = (ImageCell) v;
		trace("onItemClick in view: " + i.mCellNumber);
	}

	public boolean onLongClick(View v) {
		if (mLongClickStartsDrag) {

			if (!v.isInTouchMode()) {
				toast("isInTouchMode returned false. Try touching the view again.");
				return false;
			}
			return startDrag(v);
		}

		return false;
	}

	public boolean onTouch(View v, MotionEvent ev) {
		if (mLongClickStartsDrag)
			return false;

		boolean handledHere = false;

		final int action = ev.getAction();

		if (action == MotionEvent.ACTION_DOWN) {
			handledHere = startDrag(v);
		}

		return handledHere;
	}

	public boolean startDrag(View v) {
		DragSource dragSource = (DragSource) v;

		mDragController.startDrag(v, dragSource, dragSource, DragController.DRAG_ACTION_MOVE);

		return true;
	}

	public void toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public void trace(String msg) {
		Log.d("DragActivity", msg);
		if (!Debugging)
			return;
	}

}
