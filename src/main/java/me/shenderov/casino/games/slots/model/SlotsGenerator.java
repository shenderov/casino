package me.shenderov.casino.games.slots.model;

import me.shenderov.casino.games.slots.constants.SlotsConstants;
import me.shenderov.casino.games.slots.entities.Reels;

import java.util.Random;

public class SlotsGenerator {

	public Reels spinReels() {
		Integer [] reelPosition = getReelPosition();
		Reels reels = new Reels();
		reels.setReel(SlotsConstants.REEL);
		reels.setReelPosition(reelPosition);
		reels.setKey(generateKey(reelPosition));
		return reels;
	}

	private Integer [] getReelPosition() {
		Integer [] combination = new Integer [SlotsConstants.REELS_NUMBER];
		for(int i = 0; i < combination.length; i++){
			int reelPosition = getRandomValue(SlotsConstants.REEL.size());
			combination[i] = SlotsConstants.REEL.get(reelPosition).ordinal()+1;
		}
		return combination;
	}

	private String generateKey(Integer [] reelPosition) {
		StringBuilder result = new StringBuilder();
		for (Integer position : reelPosition) {
			result.append(SlotsConstants.REEL.get(position).ordinal());
		}
		return result.toString();
	}

	private Integer getRandomValue(int symbols) {
		Random rand = new Random();
		return rand.nextInt(symbols);
	}
}
