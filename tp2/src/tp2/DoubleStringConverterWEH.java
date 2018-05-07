/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package tp2;

import javafx.util.StringConverter;

/**
 * <p>{@link StringConverter} implementation for {@link Double}
 * (and double primitive) values.</p>
 * @since JavaFX 2.1
 */
public class DoubleStringConverterWEH extends StringConverter<Double> {
    /** {@inheritDoc} */
    @Override public Double fromString(String value) {
        double nombre = 0.0;
        // If the specified value is null or zero-length, return null
        if (value == null) {
            return null;
        }

        value = value.trim();

        if (value.length() < 1) {
            return null;
        }

        try {
            nombre = Double.valueOf(value);
        } catch (NumberFormatException e) {
            System.out.println("RETARD ALERT!!!");
        }
        return nombre;
    }

    /** {@inheritDoc} */
    @Override public String toString(Double value) {
        // If the specified value is null, return a zero-length String
        if (value == null) {
            return "";
        }

        return Double.toString(value.doubleValue());
    }
}
