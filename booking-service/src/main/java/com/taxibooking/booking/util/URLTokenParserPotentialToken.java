/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxibooking.booking.util;

/**
 *
 * @author vinodkandula
 */
public class URLTokenParserPotentialToken extends URLTokenParserState {

	public URLTokenParserPotentialToken(URLTokenParser context) {
		super(context);
	}

	@Override
	public void addLeftBracket() {

	}

	@Override
	public void addRightBracket() {

		String value = this.getContext().getKey(this.getContext().getTemp());

		// look in default application properties file.
		if (value == null) {
			throw new IllegalStateException("Property not found : "+this.getContext().getTemp());

		}

		this.getContext().setToken(value);
		this.getContext().addToURL();
		this.getContext().changeState(new URLTokenParserNoBracesState(this.getContext()));
	}
}
