/*
 * This is a modified version of a class from the Android Open Source Project. 
 * The original copyright and license information follows.
 * 
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.hp.epromos;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

public class DragLayer extends FrameLayout implements DragController.DragListener {
	DragController mDragController;
	TableLayout tableView;
	LinearLayout shoppingCarts;

	public DragLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setDragController(DragController controller) {
		mDragController = controller;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return mDragController.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mDragController.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return mDragController.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {
		return mDragController.dispatchUnhandledMove(focused, direction);
	}


	public TableLayout getTableView() {
		return tableView;
	}


	public void setTableView(TableLayout newValue) {
		tableView = newValue;
	} 

	public void onDragStart(DragSource source, Object info, int dragAction) {

		if (shoppingCarts != null) {
			int numVisibleChildren = shoppingCarts.getChildCount();
			for (int i = 0; i < numVisibleChildren; i++) {
				View viewBeingInspected = shoppingCarts.getChildAt(i);
				Log.e("Debug", "Found Instance:" + viewBeingInspected.getClass().getName());
				if (viewBeingInspected instanceof DropTarget) {
					Log.e("Drag Start", "Shopping Basket Found");
					DropTarget view = (DropTarget) viewBeingInspected;
					mDragController.addDropTarget(view);
				} else {
					if (viewBeingInspected instanceof ViewGroup) {
						ViewGroup innerLayout = (ViewGroup) viewBeingInspected;
						int grandChildren = innerLayout.getChildCount();
						for (int loopVar = 0; loopVar < grandChildren; loopVar++) {
							View grandChildView = innerLayout.getChildAt(loopVar);
							if (grandChildView instanceof DropTarget) {
								Log.e("Drag Start", "Shopping Basket Found");
								DropTarget view = (DropTarget) grandChildView;
								mDragController.addDropTarget(view);
							}
						}
					}
				}
			}
		}

	}

	/**
	 * A drag-drop operation has eneded.
	 */

	public void onDragEnd() {
		mDragController.removeAllDropTargets();
	}

	/**
 */
	// Other Methods

	/**
	 * Show a string on the screen via Toast.
	 * 
	 * @param msg
	 *            String
	 * @return void
	 */

	public void toast(String msg) {
		// if (!DragActivity.Debugging) return;
		Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
	} // end toast

} // end class
