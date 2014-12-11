package com.filmSynclib.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class to compute the spectrum data and recognize the key
 * @author fingent
 *
 */
public class StatelessRecognizer {

	private Spectrum spectrum; //Spectrum object
	private Collection<Tone> tones; //Tone collection object
	private static final int SPECTRUM_END_VALUE=420; //Spectrum end index
	private static final int SPECTRUM_START_VALUE=370; //Spectrum start index
	private static final int SPECTRUM_START_ZERO=0;
	
	/**
	 * Constructor
	 * @param spectrum spectrum object
	 */
	public StatelessRecognizer(Spectrum spectrum) 
	{
		this.spectrum = spectrum;
		
		tones = new ArrayList<Tone>();
		
		fillTones(); //initialize the tones
	}
	
	/**
	 * Function initialize the tones we are interested
	 */
	private void fillTones() {
		
		//48000
		tones.add(new Tone( 384, '0')); //18000
		tones.add(new Tone( 386, '1')); //18100
		tones.add(new Tone( 388, '2')); //18200
		tones.add(new Tone( 390, '3')); //18300
		tones.add(new Tone( 393, '4')); //18400
		tones.add(new Tone( 395, '5')); //18500
		tones.add(new Tone( 397, '6')); //18600
		tones.add(new Tone( 399, '7')); //18700
		tones.add(new Tone( 401, '8')); //18800
		tones.add(new Tone( 403, '9')); //18900
		tones.add(new Tone( 405, 'h')); //19000
		tones.add(new Tone( 407, 's')); //19100
		tones.add(new Tone( 410, 't')); //19200
		
		//44100
		/*
		tones.add(new Tone( 418, '0')); //18000
		tones.add(new Tone( 420, '1')); //18100
		tones.add(new Tone( 423, '2')); //18200
		tones.add(new Tone( 425, '3')); //18300
		tones.add(new Tone( 427, '4')); //18400
		tones.add(new Tone( 430, '5')); //18500
		tones.add(new Tone( 432, '6')); //18600
		tones.add(new Tone( 435, '7')); //18700
		tones.add(new Tone( 437, '8')); //18800
		tones.add(new Tone( 439, '9')); //18900
		tones.add(new Tone( 441, 'h')); //19000
		tones.add(new Tone( 444, 's')); //19100
		tones.add(new Tone( 446, 't')); //19200
		*/
		
	}

	/**
	 * Function to get the recognized key.
	 * @return
	 */
	public char getRecognizedKey()
	{
		
		Spectrum hSpectrum=new Spectrum(spectrum, SPECTRUM_START_VALUE, SPECTRUM_END_VALUE);
		int highMax = hSpectrum.getMax();
	
		Spectrum s=new Spectrum(spectrum, SPECTRUM_START_ZERO, SPECTRUM_END_VALUE);
		
		int max =s.getMax();
		
		if( max != highMax)
			return ' ';
		
		for (Tone t : tones) {
			if(t.match(highMax))
				return t.getKey();
		}
		
		return ' ';
	}
}
