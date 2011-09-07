package org.danv.kapiku500.engine;

public interface IPlayer {
	public PlayerType getPlayerType();
	public Hand getHand();
	public void playBone(int boneIndex);
	public boolean getHasPlayerWon();
	public String getName();
}
