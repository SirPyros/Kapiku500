package org.danv.kapiku500.engine;

public class Game {
	Hand _hand;
	Deck _deck;
	IPlayer[] _players;
	int _currentPlayerIndex = -1;
	IGameSetCallBack _gameCallBack;
	boolean _setComplete = false;
	boolean _firstSet = true;
	Bone _boardEnds;
	public Game()
	{
		_deck = new Deck();
		_deck.makeDeck();
	}
	
	public void newSet()
	{	
		_setComplete = false;
		_deck.shuffle();
		_boardEnds = null;
		int playerIndex = 0;
		for(IPlayer player : _players)
		{
			player.getHand().addBones(_deck.deal(false));
			if(_currentPlayerIndex == -1)
				if(player.getHand().getHasDoubleSix())
					_currentPlayerIndex = playerIndex;
				
			playerIndex++;
		}
		
		if(_players.length < CONSTANTS.MAX_PLAYERS)
		{
			_hand = new Hand();
			_hand.addBones(_deck.deal(true));
		}
	}
	
	public IPlayer[] getPlayers()
	{
		return _players;
	}
	
	public IPlayer getCurrentPlayer()
	{
		return _players[_currentPlayerIndex];
	}
	
	public int getCurrentPlayerIndex()
	{
		return _currentPlayerIndex;
	}
	
	public void setCurrentPlayerIndex(int index)
	{
		_currentPlayerIndex = index;
	}
	
	public boolean getSetComplete()
	{
		return _setComplete;
	}
	
	public void currentPlayerMove(int boneIndex)
	{
		getCurrentPlayer().playBone(boneIndex);
		if(getCurrentPlayer().getHasPlayerWon())
		{
			_setComplete = true;
			_gameCallBack.setComplete();
		}
	}
	
	public void nextTurn()
	{
		_currentPlayerIndex++;
		if(_currentPlayerIndex >= _players.length)
			_currentPlayerIndex = 0;
	}
	
	
	public void newGame(IPlayer[] players)
	{	
		_players = players;
		newSet();
	}
	
	public void playerMove(int boneIndex)
	{
		
	}
	
	public Deck getDeck()
	{
		return _deck;
	}
}
