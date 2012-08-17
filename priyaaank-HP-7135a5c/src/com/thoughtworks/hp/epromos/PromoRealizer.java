package com.thoughtworks.hp.epromos;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.thoughtworks.hp.epromos.EPromoFactory.EPromo;

@SuppressLint("ParserError")
public class PromoRealizer {
	private Context mContext;
	private TableLayout mainLayout;
	private final static int numberOfPromotionOnEvenRows = 3;
	private final static int numberOfPromotionsOnOddRows = 2;

	public PromoRealizer(Context c, TableLayout mainLayout) {
		mContext = c;
		this.mainLayout = mainLayout;
	}

	public void realizePromotions() {

		List<EPromo> listOfPromotions = new PromotionService().getCurrentPromotions();

		mainLayout.setStretchAllColumns(true);
		mainLayout.setShrinkAllColumns(true);
		TableRow iteratingRow = new TableRow(mContext);
		ArrayList<TableRow> createdRows = new ArrayList<TableRow>();

		int rowCount = 0;
		int loopVar = 0;
		for (EPromo promo : listOfPromotions) {
			if (iteratingRow.getChildCount() == numberOfPromotionOnEvenRows && rowCount % 2 == 0) {
				createdRows.add(iteratingRow);
				rowCount++;
				iteratingRow = new TableRow(mContext);
				
			} else if (iteratingRow.getChildCount() == numberOfPromotionsOnOddRows
					&& rowCount % 2 != 0) {
				createdRows.add(iteratingRow);
				rowCount++;
				iteratingRow = new TableRow(mContext);
			}

			ImageCell promotionCell = new ImageCell(mContext);
			promotionCell.promotionBeingDisplayed = promo;
			promotionCell.mEmpty = false;
			promotionCell.tableView = mainLayout;
			promotionCell.setScaleType(ScaleType.FIT_XY);

			promotionCell.setBackgroundDrawable(mContext.getResources().getDrawable(
					promo.getPromotionImage()));
			promotionCell.setOnTouchListener((View.OnTouchListener) mContext);
			promotionCell.setOnClickListener((View.OnClickListener) mContext);
			promotionCell.setOnLongClickListener((View.OnLongClickListener) mContext);
			promotionCell.mCellNumber = loopVar;
			TableRow.LayoutParams trParams = new TableRow.LayoutParams();
			trParams.setMargins(2, 2, 2, 2);
			if (rowCount % 2 != 0) {
				if(iteratingRow.getChildCount() == (numberOfPromotionsOnOddRows -1)){
					trParams.span = 2;
				}
			} 
			iteratingRow.addView(promotionCell, trParams);
			loopVar++;
			
		}

		for (TableRow row : createdRows) {
			mainLayout.addView(row);
		}

	}
}
