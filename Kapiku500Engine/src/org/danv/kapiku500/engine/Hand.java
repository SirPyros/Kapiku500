package org.danv.kapiku500.engine;

import java.util.ArrayList;
import java.util.List;
public class Hand {
	ArrayList<Bone> _bones;
	
	public Hand()
	{
		_bones = new ArrayList<Bone>();
	}
	
	public void addBones(List<Bone> bones)
	{
		_bones.addAll(bones);
	}
	
	public int getScore()
	{
		int score = 0;
		for(Bone bone : _bones)
			score += bone.getScore();
			
		return score;
	}
	
	public void removeBone(int boneIndex)
	{
		_bones.remove(boneIndex);
	}
	
	public int getBoneCount()
	{
		return _bones.size();
	}
	
	public boolean getHasDoubleSix()
	{
		for(Bone bone : _bones)
			if(bone.getScore()==12)
				return true;
		
		return false;
	}
	
	public Bone[] getBones()
	{	
		Bone[] bones = new Bone[_bones.size()];
		return _bones.toArray(bones);
	}

}
