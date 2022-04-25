package me.shenderov.casino.games.slots.entities;

import me.shenderov.casino.games.slots.enums.BetType;

public class Responce {
	private Reels reels;
	private BetType betType;
	private boolean isWin;
	private boolean isJackpot;

	public Responce() {
	}

	public Responce(Reels reels, BetType betType, boolean isWin, boolean isJackpot) {
		this.reels = reels;
		this.betType = betType;
		this.isWin = isWin;
		this.isJackpot = isJackpot;
	}

	public Reels getReels() {
		return reels;
	}

	public void setReels(Reels reels) {
		this.reels = reels;
	}

	public BetType getBetType() {
		return betType;
	}

	public void setBetType(BetType betType) {
		this.betType = betType;
	}

	public boolean isWin() {
		return isWin;
	}

	public void setWin(boolean win) {
		isWin = win;
	}

	public boolean isJackpot() {
		return isJackpot;
	}

	public void setJackpot(boolean jackpot) {
		isJackpot = jackpot;
	}

	@Override
	public String toString() {
		return "Responce{" +
				"reels=" + reels +
				", betType=" + betType +
				", isWin=" + isWin +
				", isJackpot=" + isJackpot +
				'}';
	}
}
