package org.danv.kapiku500.engine;


public abstract class PlayerBase implements IPlayer {
	private Hand _hand;
	private String _name;
	protected PlayerBase(String name)
	{
		_hand = new Hand();
		_name = name;
	}

	public Hand getHand() {
		return _hand;
	}
	
	public boolean getHasPlayerWon()
	{
		return _hand.getBoneCount() == 0;
	}
	
	public String getName()
	{
		return _name;
	}
}
