package me.shenderov.casino.games.slots.entities;

import me.shenderov.casino.games.slots.enums.ReelSymbols;

import java.util.Arrays;
import java.util.Map;

public class Reels {
	private Map<Integer, ReelSymbols> reel;
	private Integer [] reelPosition;
	private String key;
	
	public Reels() {
		super();
	}

	public Reels(Map<Integer, ReelSymbols> reel, Integer[] reelPosition, String key) {
		this.reel = reel;
		this.reelPosition = reelPosition;
		this.key = key;
	}

	public Map<Integer, ReelSymbols> getReel() {
		return reel;
	}

	public void setReel(Map<Integer, ReelSymbols> reel) {
		this.reel = reel;
	}

	public Integer[] getReelPosition() {
		return reelPosition;
	}

	public void setReelPosition(Integer[] reelPosition) {
		this.reelPosition = reelPosition;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Reels{" +
				"reel=" + reel +
				", reelPosition=" + Arrays.toString(reelPosition) +
				", key=" + key +
				'}';
	}
}
