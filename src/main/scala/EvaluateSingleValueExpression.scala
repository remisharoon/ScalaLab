//package analytics.utils
//
//import java.util.Stack
//
//import org.apache.commons.lang3.ArrayUtils
//
////remove if not needed
//import scala.collection.JavaConversions._
//
//object EvaluateSingleValueExpression {
//
//  var token_delimiter: Char = '~'
//
//  var ops_tokens: Array[Char] = Array('+', '-', '*', '/', '(', ')')
//
//  var ops_tokens2: Array[Char] = Array('+', '-', '*', '/', '(', ')', token_delimiter)
//
//  def evaluate(expression: String): Any = {
//
//    val tokens: Array[Char] = expression.toCharArray()
//    // Stack for elements: 'elems'
//    val elems: Stack[Any] = new Stack[Any]()
//    // Stack for Operators: 'ops'
//    val ops: Stack[Character] = new Stack[Character]()
//
//    for (i <- 0 until tokens.length) {
//
//      // Current token is a whitespace, skip it
//      if (tokens(i) == token_delimiter){
//
//        //continue
//      }else{
//
//        // Current token is alphanumeric, push it to stack for elems
//        if (!ArrayUtils.contains(ops_tokens2, tokens(i))) {
//          val sbuf: StringBuffer = new StringBuffer()
//          // There may be more than one digits in number
//          var i =0
//          while (i < tokens.length && (!ArrayUtils.contains(ops_tokens2,tokens(i)))) {
//            sbuf.append(tokens(i))
//            i = i+1;
//          }
//          elems.push(sbuf.toString)
//        }
//        // Current token is an opening brace, push it to 'ops'
//        else if (tokens(i) == '('){
//          ops.push(tokens(i))
//        }
//
//        // Closing brace encountered, solve entire brace
//        else if (tokens(i) == ')') {
//          while (ops.peek() != '('){
//            elems.push(applyOp(ops.pop(), elems.pop(), elems.pop()))
//          }
//          ops.pop()
//        }
//
//        // Current token is an operator.
//        else if (tokens(i) == '+' || tokens(i) == '-' || tokens(i) == '*' || tokens(i) == '/') {
//
//          // While top of 'ops' has same or greater precedence to current
//          // token, which is an operator. Apply operator on top of 'ops'
//          // to top two elements in elems stack
//          while (!ops.empty() && hasPrecedence(tokens(i), ops.peek())) {
//            elems.push(applyOp(ops.pop(), elems.pop(), elems.pop()))
//          }
//
//          // Push current token to 'ops'.
//          ops.push(tokens(i))
//        }
//      }
//    }
//
//    // Entire expression has been parsed at this point, apply remaining
//    // ops to remaining elems
//    while (!ops.empty()){
//      elems.push(applyOp(ops.pop(), elems.pop(), elems.pop()))
//    }
//
//    // Top of 'elems' contains result, return it
//    return  elems.pop()
//  }
//  // Entire expression has been parsed at this point, apply remaining
//
//  // otherwise returns false.
//  def hasPrecedence(op1: Char, op2: Char): Boolean = {
//    if (op2 == '(' || op2 == ')'){
//      false
//    }
//    if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')){
//      false
//    }else{
//      true
//    }
//  }
//
//  // and 'b'. Return the result.
//  def applyOp(op: Char, b: Any, a: Any): Any = {
//    op match {
//      case '+' =>
//        return a + "+" + b
//      case '-' => //return a - b;
//        return a + "-" + b
//      case '*' => //return a * b;
//        return a + "*" + b
//      case '/' =>
//        if (b == "0")
//          throw new UnsupportedOperationException("Cannot divide by zero")
//        //return a / b;
//        return a + "/" + b
//
//    }
//    return "0"
//  }
//
//  // Driver method to test above methods
//  def main(args: Array[String]): Unit = {
//    //        System.out.println(EvaluateExpression.evaluate("100*(2+12)/14"));
//
//    var expression1: String =  "((ENOC_ECM1_HVAC_Baseline_Report.LegacyEnergy+ENOC_ECM2_Lighting_Baseline_Report.LegacyEnergy+ENOC_ECM3_Signage_Baseline_Report.LegacyEnergy-ECM1_HVAC_Energy_Report.ModuleConsumedWatt-ECM2_Lighting_Energy_Report.ModuleConsumedWatt-ECM3_Signage_Energy_Report.ModuleConsumedWatt )/ENOC_1079_Site Total Baseline_Report.LegacyEnergy)*100"
//    var expression2: String =  "100*(2+12)/14"
//
//    for (tok <- ops_tokens) {
//      val tokenStr: String = java.lang.Character.toString(tok)
//
//      try {
//        expression1 = expression1.replace(tokenStr, token_delimiter + tokenStr + token_delimiter)
//      } catch {
//        case t: Throwable => t.printStackTrace() // TODO: handle error
//      }
//
//      try {
//        expression2 = expression2.replace(tokenStr, token_delimiter + tokenStr + token_delimiter)
//      } catch {
//        case t: Throwable => t.printStackTrace() // TODO: handle error
//      }
//
//    }
//    println(expression1)
//    //println(EvaluateSingleValueExpression.evaluate(expression1))
//    println(evaluate(expression1))
//    println(evaluate(expression2))
//  }
//
//
//  //        System.out.println(EvaluateExpression.evaluate("10 + 2 * 6"));
//  //        System.out.println(EvaluateExpression.evaluate("100 * 2 + 12"));
//  //        System.out.println(EvaluateExpression.evaluate("100 * ( 2 + 12 )"));
//  //        System.out.println(EvaluateExpression.evaluate("100 * ( 2 + 12 ) / 14"));
//  //
//  //        System.out.println(EvaluateExpression.evaluate("10+2*6"));
//  //        System.out.println(EvaluateExpression.evaluate("100*2+12"));
//  //        System.out.println(EvaluateExpression.evaluate("100*(2+12)"));
//  //        System.out.println(EvaluateExpression.evaluate("10 + 2 * 6"));
//  //        System.out.println(EvaluateExpression.evaluate("100 * 2 + 12"));
//  //        System.out.println(EvaluateExpression.evaluate("100 * ( 2 + 12 )"));
//  //        System.out.println(EvaluateExpression.evaluate("100 * ( 2 + 12 ) / 14"));
//  //
//  //        System.out.println(EvaluateExpression.evaluate("10+2*6"));
//  //        System.out.println(EvaluateExpression.evaluate("100*2+12"));
//  //        System.out.println(EvaluateExpression.evaluate("100*(2+12)"));
//
//}
