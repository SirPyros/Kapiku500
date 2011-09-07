package org.danv.kapiku500.engine;

public class HumanPlayer extends PlayerBase {

	public HumanPlayer(String name) 
	{
		super(name);
	}
	@Override
	public PlayerType getPlayerType() {
		// TODO Auto-generated method stub
		return PlayerType.HUMAN;
	}

	@Override
	public void playBone(int boneIndex) {
		// TODO Auto-generated method stub

	}

}
