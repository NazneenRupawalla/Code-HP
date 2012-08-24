package com.thoughtworks.hp.epromos;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.hp.R;
import com.thoughtworks.hp.epromos.EPromoFactory.EPromo;

public class PromotionService {
	public List<EPromo> getCurrentPromotions() {
		List<EPromo> returnList = new ArrayList<EPromoFactory.EPromo>();
		returnList.add(EPromoFactory.createPromo(R.drawable._2, 20, "9789380658674",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._2, 20, "9789380658674",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._3, 20, "1236",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._4, 20, "9788184041804",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._5, 20, "1234",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._3, 20, "1236",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._4, 20, "9788184041804",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._5, 20, "1234",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._4, 20, "9788184041804",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._3, 20, "1236",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._2, 20, "9789380658674",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._3, 20, "1236",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._4, 20, "9788184041804",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._2, 20, "9789380658674",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._3, 20, "1236",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._4, 20, "9788184041804",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._5, 20, "1234",
				"Sample Description for the sake of it"));
		returnList.add(EPromoFactory.createPromo(R.drawable._4, 20, "9788184041804",
				"Sample Description for the sake of it"));
		return returnList;
	}
}
