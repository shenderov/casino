package me.shenderov.casino.games.slots.constants;

import me.shenderov.casino.games.slots.enums.BetType;
import me.shenderov.casino.games.slots.enums.ReelSymbols;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public interface SlotsConstants {
	int BASE_BET = 1;
	int REELS_NUMBER = 3;
	double JACKPOT = 20000;
	String JACKPOT_KEY = "777";
	BetType JACKPOT_BET = BetType.X3;

	Map<Integer, ReelSymbols> REEL = Collections.unmodifiableMap(new LinkedHashMap<>() {
		{
			put(0, ReelSymbols.BLANK);
			put(1, ReelSymbols.RED7);
			put(2, ReelSymbols.BLANK);
			put(3, ReelSymbols.WHITE7);
			put(4, ReelSymbols.BLANK);
			put(5, ReelSymbols.BLUE7);
			put(6, ReelSymbols.BLANK);
			put(7, ReelSymbols.X1_BAR_RED);
			put(8, ReelSymbols.BLANK);
			put(9, ReelSymbols.X2_BAR_WHITE);
			put(10, ReelSymbols.BLANK);
			put(11, ReelSymbols.X3_BAR_BLUE);
			put(12, ReelSymbols.BLANK);
			put(13, ReelSymbols.WILD);
		}
	});

	Map<String, Integer> PAY_TABLE = Collections.unmodifiableMap(new LinkedHashMap<>() {
		{
			put("000", 1);
			put("366", 2);
			put("336", 2);
			put("363", 2);
			put("633", 2);
			put("663", 2);
			put("636", 2);
			put("144", 2);
			put("114", 2);
			put("141", 2);
			put("411", 2);
			put("441", 2);
			put("414", 2);
			put("255", 2);
			put("225", 2);
			put("252", 2);
			put("522", 2);
			put("552", 2);
			put("525", 2);
			put("227", 5);
			put("237", 5);
			put("247", 5);
			put("257", 5);
			put("267", 5);
			put("327", 5);
			put("337", 5);
			put("347", 5);
			put("357", 5);
			put("367", 5);
			put("427", 5);
			put("437", 5);
			put("447", 5);
			put("457", 5);
			put("467", 5);
			put("527", 5);
			put("537", 5);
			put("547", 5);
			put("557", 5);
			put("567", 5);
			put("627", 5);
			put("637", 5);
			put("647", 5);
			put("657", 5);
			put("667", 5);
			put("445", 5);
			put("446", 5);
			put("454", 5);
			put("455", 5);
			put("464", 5);
			put("465", 5);
			put("466", 5);
			put("544", 5);
			put("545", 5);
			put("546", 5);
			put("554", 5);
			put("556", 5);
			put("564", 5);
			put("565", 5);
			put("566", 5);
			put("644", 5);
			put("645", 5);
			put("646", 5);
			put("654", 5);
			put("655", 5);
			put("656", 5);
			put("664", 5);
			put("665", 5);
			put("444", 10);
			put("126", 20);
			put("153", 20);
			put("156", 20);
			put("423", 20);
			put("426", 20);
			put("453", 20);
			put("456", 20);
			put("555", 20);
			put("666", 30);
			put("112", 40);
			put("113", 40);
			put("121", 40);
			put("122", 40);
			put("131", 40);
			put("132", 40);
			put("133", 40);
			put("211", 40);
			put("212", 40);
			put("213", 40);
			put("221", 40);
			put("223", 40);
			put("231", 40);
			put("232", 40);
			put("233", 40);
			put("311", 40);
			put("312", 40);
			put("313", 40);
			put("321", 40);
			put("322", 40);
			put("323", 40);
			put("331", 40);
			put("332", 40);
			put("333", 80);
			put("222", 100);
			put("111", 120);
			put("123", 300);
			put("777", 1000);
		}
	});
	
}
