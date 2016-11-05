package cz.jalasoft.calculator;

import cz.jalasoft.calculator.formula.Formula;
import cz.jalasoft.calculator.formula.postfix.PostfixFormula;
import org.testng.Assert;
import org.testng.annotations.Test;

import static cz.jalasoft.calculator.formula.Operand.*;
import static cz.jalasoft.calculator.formula.ArithmeticOperator.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-04.
 */
public class PostfixFormulaTest {

    @Test
    public void postfixFormulaEvaluatesCorrectlyWhenDefiningOperationsAndOperatorsInCorrectOrder() {

        //3.14 | 2.8 | + | 3.1 | * |   ->  (3.14 + 2.8) * 3.1 = 18.414

        Formula<Number> formula = PostfixFormula.<Number>newFormula()
                .end(operand(3.14))
                .end(operand(2.8))
                .end(add())
                .end(operand(3.1))
                .end(multiply())
                .formula();

        Number result = formula.evaluate();

        Assert.assertEquals(result.doubleValue(), 18,414);
    }

    @Test
    public void postfixFormulaEvaluatesCorrectlyWhenDefiningOperationsAndOperatorInOrderAndPushingOperand() {

        //6.0 | 2.0 | / | 4.5 | - |   ->  (6.0 / 2.0) - 4.5 = -1.5

        Formula<Number> formula = PostfixFormula.<Number>newFormula()
                .end(operand(2.0))
                .end(divide())
                .end(operand(4.5))
                .end(subtract())
                .beginning(operand(6.0))
                .formula();

        Number result = formula.evaluate();

        Assert.assertEquals(result.doubleValue(), -1.5);
    }
}
