package com.thoughtworks.hp.datastore;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class ProductAvailabilityService {
	public static boolean isProductAvailable(long productID) {
		return new Random().nextInt() % 2 == 0;
	}

	public static GregorianCalendar getProductRestockDate(long productID) {
		GregorianCalendar calendar = new GregorianCalendar();
		int randomInt = new Random().nextInt() % 20;
		calendar.add(Calendar.DATE, randomInt == 0 ? 5 : randomInt);
		return calendar;
	}
}
