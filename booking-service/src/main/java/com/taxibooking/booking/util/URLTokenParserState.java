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
public abstract class URLTokenParserState {

    private URLTokenParser context;

    public URLTokenParserState(URLTokenParser context) {
        this.context = context;
    }

    public abstract void addLeftBracket();

    public abstract void addRightBracket();

    public void addChar(char c) {
        this.getContext().addToTemp(c);
    }

    public URLTokenParser getContext() {
        return this.context;
    }

}
