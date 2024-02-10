package com.example.graphiccalculator;


public class Equation {

    private static String inputEquation;
    private static double[] coefficients = {0,0,0,0,0,0,0}; // coeff are arranged as [ x^2 , y^2 , xy , x , y , constant , /0 ]

    public static void toLHS () {
        String[] sides = inputEquation.split("=");

        StringBuilder finalEquation = new StringBuilder();
        finalEquation.setLength(0);
        finalEquation.append(sides[0]);

        if (Character.isDigit(sides[1].charAt(0)) || Character.isAlphabetic(sides[1].charAt(0))) {
            finalEquation.append('-');
        }

        for (int i=0;i<sides[1].length();i++) {
            char currentChar = sides[1].charAt(i);

            if (currentChar == '+') {
                finalEquation.append('-');
            } else if (currentChar == '-') {
                finalEquation.append('+');
            } else {
                finalEquation.append(currentChar);
            }
        }

        inputEquation = finalEquation.toString();
        //System.out.println("LHS eqn : " + inputEquation);
    }

    public static void checkEquation() {
        StringBuilder checkedEquation = new StringBuilder();
        checkedEquation.setLength(0);

        char currentChar,nextChar;

        if (Character.isAlphabetic(inputEquation.charAt(0))) {
            checkedEquation.append('1');
        }

        for (int i=0;i<inputEquation.length()-1;i++){
            currentChar = inputEquation.charAt(i);
            nextChar = inputEquation.charAt(i+1);

            checkedEquation.append(currentChar);
            if ((currentChar == '+' || currentChar == '-') && Character.isAlphabetic(nextChar)) { checkedEquation.append('1'); }
        }

        checkedEquation.append(inputEquation.charAt(inputEquation.length()-1));
        inputEquation = checkedEquation.toString();
        //System.out.println("Checked eqn : " + inputEquation);
    }

    public static void pickTerms (String inputEquation) {// Will pick out a single term from the equation
        StringBuilder buildTerm = new StringBuilder();
        buildTerm.setLength(0);
        buildTerm.append(inputEquation.charAt(0));

        for (int i=1;i<inputEquation.length();i++) {
            char currentChar = inputEquation.charAt(i);

            if (Character.isDigit(currentChar) || Character.isAlphabetic(currentChar) || currentChar == '^') {
                buildTerm.append(currentChar);
            } else if (currentChar == '+' || currentChar == '-') {
                sortCoefficients(buildTerm.toString());
                buildTerm.setLength(0);
                buildTerm.append(currentChar);
            }
        }

        if (buildTerm.length() > 0) {
            sortCoefficients(buildTerm.toString());
        }
    }

    public static void sortCoefficients (String term) {// Will take coefficients out of term and put it into coefficients[] array
        StringBuilder buildCoeff = new StringBuilder();
        double coeff;

        for (int i=0;i<term.length();i++) {
            if (Character.isAlphabetic(term.charAt(i))) { break; }

            buildCoeff.append(term.charAt(i));
        }
        if (buildCoeff == null || buildCoeff.toString() == "+") {
            coeff = 1;
        } else if (buildCoeff.toString() == "-") {
            coeff = -1;
        } else {
            coeff = Double.parseDouble(buildCoeff.toString());
        }

        if (term.contains("x^2")) {
            for (int i=0;i<term.indexOf("x^2");i++) {
                buildCoeff.append(term.charAt(i));
            }
            coefficients[0] += coeff;
            return;
        }
        if (term.contains("y^2")) {
            for (int i=0;i<term.indexOf("y^2");i++) {
                buildCoeff.append(term.charAt(i));
            }
            coefficients[1] += coeff;
            return;
        }
        if (term.contains("xy")) {
            for (int i=0;i<term.indexOf("xy");i++) {
                buildCoeff.append(term.charAt(i));
            }
            coefficients[2] += coeff;
            return;
        }
        if (term.contains("x")) {
            for (int i=0;i<term.indexOf("x");i++) {
                buildCoeff.append(term.charAt(i));
            }
            coefficients[3] += coeff;
            return;
        }
        if (term.contains("y")) {
            for (int i=0;i<term.indexOf("y");i++) {
                buildCoeff.append(term.charAt(i));
            }
            coefficients[4] += coeff;
        } else {
            coefficients[5] += coeff;
        }
    }

    public static double[] giveCoefficients(String equation) {
        inputEquation=equation;
        if (inputEquation.indexOf('=') != -1) { toLHS(); } // check if we need toLHS. If '=' is present then move RHS to LHS.
        checkEquation(); // puts coefficient 1 for -xy , y , +x , etc. tei exception ko lagi bhaneko thiye ni. Aah aah tei.
        pickTerms(inputEquation);

       return coefficients;
        //ConicSection conicSection = new ConicSection(coefficients[0], coefficients[2], coefficients[1], coefficients[3], coefficients[4], coefficients[5]);
        //conicSection.calculateConicSection();

    }


}