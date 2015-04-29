package lotteryMain;

import lotteryMainView.LotteryMainView;
import dbUnit.DriverProvider;

public class LoMainView {

	public static void main(String[] args) {
		DriverProvider.getDriver();
		new LotteryMainView();
	}
}
