package com.example.graphiccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView equationTv;
    MaterialButton buttonC,buttonX,buttonY,buttonX2,buttonY2,buttonXY;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonAC,buttonDot,buttonRun,buttonEqual;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        equationTv=findViewById(R.id.equation_tv);
        assignID(buttonC,R.id.button_c);
        assignID(buttonX,R.id.button_x);
        assignID(buttonY,R.id.button_y);
        assignID(buttonX2,R.id.button_x2);
        assignID(buttonY2,R.id.button_y2);
        assignID(buttonXY,R.id.button_xy);
        assignID(button0,R.id.button_0);
        assignID(button1,R.id.button_1);
        assignID(button2,R.id.button_2);
        assignID(button3,R.id.button_3);
        assignID(button4,R.id.button_4);
        assignID(button5,R.id.button_5);
        assignID(button6,R.id.button_6);
        assignID(button7,R.id.button_7);
        assignID(button8,R.id.button_8);
        assignID(button9,R.id.button_9);
        assignID(buttonAC,R.id.button_minus);
        assignID(buttonDot,R.id.button_add);
        assignID(buttonRun,R.id.button_run);
        assignID(buttonEqual,R.id.button_equal);

    }

    void assignID(MaterialButton btn,int id)
    {
        btn=findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button=(MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = equationTv.getText().toString();

        if(buttonText.equals("ac"))
        {
            equationTv.setText("");
            return;
        }
        if(buttonText.equals("C"))
        {
          dataToCalculate=dataToCalculate.substring(0,dataToCalculate.length()-1);
        }
        if(buttonText.equals("x")||buttonText.equals("y") ||buttonText.equals("x^2") ||buttonText.equals("y^2") ||buttonText.equals("xy") ||buttonText.equals("+") ||buttonText.equals("=") ||buttonText.equals("-"))
        {
          dataToCalculate=dataToCalculate+buttonText;
        }
        if(buttonText.equals("1") ||buttonText.equals("2")||buttonText.equals("3") ||buttonText.equals("4") ||buttonText.equals("5") ||buttonText.equals("6") ||buttonText.equals("7") ||buttonText.equals("8") ||buttonText.equals("9") ||buttonText.equals("0"))
        { dataToCalculate=dataToCalculate+buttonText;
        }

        equationTv.setText(dataToCalculate);

        if(buttonText.equals("go")){
            // Assume that the equation string is stored in a variable called equationString

            Equation equation = new Equation(); // create an instance of the Equation class
            double[] coefficients = equation.giveCoefficients(dataToCalculate); // call the giveCoefficients() method on the instance and pass in the equation string
            String equationString = printEquation(coefficients); // call the printEquation() method to get the equation in the required form
            equationTv.setText(equationString);
        }

    }
    String printEquation(double[] coefficients) {
        String equationString = "";
        double A= coefficients[0];
        double B= coefficients[2];
        double b= coefficients[1];
        double D= coefficients[3];
        double E= coefficients[4];
        double c= coefficients[5];
        double h = B / 2;
        double g = D / 2;
        double f = E / 2;


       /* // check if x^2 coefficient is non-zero
        if(coefficients[0] != 0) {
            equationString += coefficients[0] + "x^2 "+"+";
        }

        // check if y^2 coefficient is non-zero
        if(coefficients[1] != 0) {
            equationString += coefficients[1] + "y^2 "+"+";
        }

        // check if xy coefficient is non-zero
        if(coefficients[2] != 0) {
            equationString += coefficients[2] + "xy "+"+";
        }

        // check if x coefficient is non-zero
        if(coefficients[3] != 0) {
            equationString += coefficients[3] + "x "+"+";
        }

        // check if y coefficient is non-zero
        if(coefficients[4] != 0) {
            equationString += coefficients[4] + "y "+"+";
        }

        // check if constant term is non-zero
        if(coefficients[5] != 0) {
            equationString += coefficients[5] + " ";
        }

        equationString += "= 0"; // add the equal sign and zero at the end*/



        double del = A * b * c + 2 * f * g + A * f * f + b * g * g - c * h * h;
        if(A==0 && b==0 && h==0){
            equationString="Straight line.";
            //StraightLine.plot(D,E,c);
        }

        else if (del == 0 && A != 0 && b != 0 && h != 0) {
            equationString="Pair of straight lines.";
            //PairOfStraightLines.plot(A,h,b,g,f,c);
        }
        else if (del != 0 && A == b && h == 0) {
            equationString="Circle.";

            CirclePlotActivity circlePlotActivity = new CirclePlotActivity();
            circlePlotActivity.showCirclePlot(A, h, b, g, f, c);
            //Circle.calculateCirclePoints(A, h, b, g, f, c);
        }
        else if (del != 0 && A * b - h * h == 0) {
            equationString="Parabola.";

        }
        else if (del != 0 && A * b - h * h < 0) {
            equationString="Hyperbola.";
        }
        else if (del != 0 && A * b - h * h > 0) {
          equationString="Ellipse.";
            //Ellipse.calculateEllipsePoints(A, h, b, g, f, c);
        }
        else {
            equationString="Invalid input.";
        }

        return equationString;
    }
    }


