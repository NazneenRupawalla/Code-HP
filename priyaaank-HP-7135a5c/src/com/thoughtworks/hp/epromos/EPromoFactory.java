package com.thoughtworks.hp.epromos;

public class EPromoFactory {
	public static EPromo createPromo(int promotionImage, int productDiscount, String productID,
			String descr) {
		return new EPromo(promotionImage, productDiscount, productID, descr);
	}

	public static class EPromo {
		private EPromo(int promotionImage, int productDiscount, String productID, String description) {
			this.promotionImage = promotionImage;
			this.productID = productID;
			this.discount = productDiscount;
			this.description = description;
		}

		private int promotionImage;
		private String productID;
		private int discount;
		private String description;

		public int getPromotionImage() {
			return promotionImage;
		}

		public String getDescription() {
			return description;
		}

		public String getProductID() {
			return productID;
		}

		public int getDiscount() {
			return discount;
		}
	}
}
