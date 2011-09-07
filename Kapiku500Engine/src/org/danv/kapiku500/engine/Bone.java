package org.danv.kapiku500.engine;

public class Bone {
	int[] _pips;
	
	public Bone(int pip1, int pip2)
	{
		_pips = new int[2];
		_pips[0] = pip1;
		_pips[1] = pip2;
	}
	
	public int getPip1()
	{
		return _pips[0];
	}
	
	public int getPip2()
	{
		return _pips[1];
	}
	
	public int[] getPips()
	{
		return _pips;
	}
	
	public int getScore()
	{
		return _pips[0] + _pips[1];
	}	
	
	public boolean canPlay(Bone bone)
	{
		int[] bonePips = bone.getPips();
		
		for(int pip : bonePips)
			for(int pip2 : _pips)
				if(pip == pip2)
					return true;
		return false;
	}
	
	public boolean isDouble()
	{
		return _pips[0] == _pips[1];
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(_pips[0]) + String.valueOf(_pips[1]);
	}
}
