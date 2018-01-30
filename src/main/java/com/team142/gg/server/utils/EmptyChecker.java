/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

/**
 *
 * @author just1689
 */
public class EmptyChecker {

    public static boolean checkIfEmpty(String s) {
        if (s == null) {
            return true;
        }
        if (s.trim().isEmpty()) {
            return true;
        }
        return false;
    }

}
