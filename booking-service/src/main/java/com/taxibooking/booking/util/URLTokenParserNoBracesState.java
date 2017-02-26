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
public class URLTokenParserNoBracesState extends URLTokenParserState {

    public URLTokenParserNoBracesState(URLTokenParser context) {
        super(context);
    }

    @Override
    public void addLeftBracket() {
        this.getContext().changeState(
                new URLTokenParserPotentialToken(
                        this.getContext()));
        this.getContext().addToURL();
    }

    @Override
    public void addRightBracket() {}
}
