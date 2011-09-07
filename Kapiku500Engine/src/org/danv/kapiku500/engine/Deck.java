package org.danv.kapiku500.engine;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	ArrayList<Bone> _bones;
	int _playerIndex=0;
	public Deck()
	{
		_bones = new ArrayList<Bone>();
	}
	
	public void makeDeck()
	{
		for(int pip1=CONSTANTS.MIN_PIP; pip1<= CONSTANTS.MAX_PIP; pip1++)
			for(int pip2=pip1; pip2<= CONSTANTS.MAX_PIP; pip2++)
				_bones.add(new Bone(pip1, pip2));
	}
	
	public void shuffle()
	{
		java.util.Collections.shuffle(_bones);
		 _playerIndex = 0;
	}
	
	public List<Bone> deal(boolean all)
	{
		int boneIndex = _playerIndex * CONSTANTS.BONES_PER_HAND;
		_playerIndex++;
		
		if(!all)
			return _bones.subList(boneIndex, boneIndex + CONSTANTS.BONES_PER_HAND);
		else
			return _bones.subList(boneIndex, _bones.size());
	}
	
	public Bone[] getBones()
	{	
		Bone[] bones = new Bone[_bones.size()];
		return _bones.toArray(bones);
	}
}
