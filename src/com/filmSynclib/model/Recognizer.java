package com.filmSynclib.model;

import java.util.ArrayList;
/**
 * Class to store detected character and recognize the key
 * @author fingent
 *
 */
public class Recognizer 
{
	ArrayList<Character> history; //history collection
	char acctualVaue;
	
	/**
	 * Constructor
	 */
	public Recognizer() 
	{
		history = new ArrayList<Character>();
		acctualVaue = ' ';
	}
	/**
	 * Function returns the recognized character if character occurs more than 3 times
	 * @param recognizedKey
	 * @return recognized character
	 */
	public char getRecognizedKey(char recognizedKey) 
	{
		history.add(recognizedKey);
		
		if(history.size() <= 4)
			return ' ';
		
		history.remove(0);
		
		int count = 0;
		
		for (Character c: history) 
		{
			if(c.equals(recognizedKey))
				count++;
		}
		
		if(count >= 3)
			acctualVaue = recognizedKey;
		
		return acctualVaue;
	}
}
