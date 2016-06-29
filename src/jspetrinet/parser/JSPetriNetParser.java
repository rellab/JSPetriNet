/* JSPetriNetParser.java */
/* Generated By:JavaCC: Do not edit this line. JSPetriNetParser.java */
package jspetrinet.parser;
import jspetrinet.ast.*;
import jspetrinet.exception.*;
import jspetrinet.petri.*;
public class JSPetriNetParser implements JSPetriNetParserConstants {
        private Net current;
        private Net global;

        public void setNet(Net global) {
                this.global = global;
                this.current = global;
        }

  final public void makeNet() throws ParseException, ASTException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLACE:
      case EXPTRANS:
      case IMMTRANS:
      case GENTRANS:
      case ARC:
      case IARC:
      case OARC:
      case HARC:
      case IFELSE:
      case NET:
      case BEGIN:
      case COMMENT:
      case REAL:
      case INTEGER:
      case STRING:
      case PLUS:
      case MINUS:
      case NOT:
      case IDENTIFIER:
      case GLOBAL_NTOKEN:
      case NTOKEN:
      case OPEN:
      case NL:
      case SEND:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLACE:
      case EXPTRANS:
      case IMMTRANS:
      case GENTRANS:
      case ARC:
      case IARC:
      case OARC:
      case HARC:
      case IFELSE:
      case NET:
      case BEGIN:
      case REAL:
      case INTEGER:
      case STRING:
      case PLUS:
      case MINUS:
      case NOT:
      case IDENTIFIER:
      case GLOBAL_NTOKEN:
      case NTOKEN:
      case OPEN:{
        Statement();
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMENT:{
        jj_consume_token(COMMENT);
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case SEND:{
        jj_consume_token(SEND);
        break;
        }
      case NL:{
        jj_consume_token(NL);
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void Statement() throws ParseException, ASTException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BEGIN:{
      block();
      break;
      }
    case PLACE:
    case EXPTRANS:
    case IMMTRANS:
    case GENTRANS:
    case ARC:
    case IARC:
    case OARC:
    case HARC:
    case NET:{
      Declaration();
      break;
      }
    case IFELSE:
    case REAL:
    case INTEGER:
    case STRING:
    case PLUS:
    case MINUS:
    case NOT:
    case IDENTIFIER:
    case GLOBAL_NTOKEN:
    case NTOKEN:
    case OPEN:{
      Expression();
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void block() throws ParseException, ASTException {
    bbegin();
    makeNet();
    bend();
  }

  final public void bbegin() throws ParseException, ASTException {
    jj_consume_token(BEGIN);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PLACE:
    case EXPTRANS:
    case IMMTRANS:
    case GENTRANS:
    case ARC:
    case IARC:
    case OARC:
    case HARC:
    case IFELSE:
    case NET:
    case BEGIN:
    case REAL:
    case INTEGER:
    case STRING:
    case PLUS:
    case MINUS:
    case NOT:
    case IDENTIFIER:
    case GLOBAL_NTOKEN:
    case NTOKEN:
    case OPEN:{
      Statement();
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMENT:{
      jj_consume_token(COMMENT);
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case SEND:{
      jj_consume_token(SEND);
      break;
      }
    case NL:{
      jj_consume_token(NL);
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void bend() throws ParseException, ASTException {
    jj_consume_token(END);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case SEND:{
      jj_consume_token(SEND);
      break;
      }
    case NL:{
      jj_consume_token(NL);
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void Declaration() throws ParseException, ASTException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NET:{
      NetDeclaration();
      break;
      }
    case PLACE:{
      PlaceDeclaration();
      break;
      }
    case EXPTRANS:{
      ExpTransDeclaration();
      break;
      }
    case IMMTRANS:{
      ImmTransDeclaration();
      break;
      }
    case GENTRANS:{
      GenTransDeclaration();
      break;
      }
    case ARC:{
      ArcDeclaration();
      break;
      }
    case IARC:{
      IArcDeclaration();
      break;
      }
    case OARC:{
      OArcDeclaration();
      break;
      }
    case HARC:{
      HArcDeclaration();
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void NetDeclaration() throws ParseException, ASTException {Token token;
    jj_consume_token(NET);
    token = jj_consume_token(IDENTIFIER);
if (current.contains(token.image)) {
                        current = current.getChild(token.image);
                } else {
                        Net tmp = new Net(current, token.image);
                        current = tmp;
                }
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NL:{
      jj_consume_token(NL);
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      ;
    }
    block();
current = current.getOuter();
  }

  final public void PlaceDeclaration() throws ParseException, ASTException {Token token;
        PairValueList optlist;
        Place p;
        int pmax;
    jj_consume_token(PLACE);
    token = jj_consume_token(IDENTIFIER);
pmax = Place.DefaultMax;
//		p = current.createPlace(token.image, Place.DefaultMax);

    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OPEN:{
      jj_consume_token(OPEN);
      optlist = OptionList();
for (PairValue pval : optlist.getList()) {
                        String label = pval.getLabel();
                        Object value = pval.getValue().eval(current);
                        if (label.equals("max")) {
                                if (value instanceof Integer) {
                                        pmax = (Integer) value;
                                } else {
                                        {if (true) throw new ASTException();}
                                }
                        } else {
                                {if (true) throw new UnknownOption();}
                        }
                }
      jj_consume_token(CLOSE);
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      ;
    }
p = current.createPlace(token.image, pmax);
  }

  final public void ExpTransDeclaration() throws ParseException, ASTException {Token token;
        PairValueList optlist;
        ExpTrans tr;
    jj_consume_token(EXPTRANS);
    token = jj_consume_token(IDENTIFIER);
tr = current.createExpTrans(token.image,
                        new ASTVariable(token.image + ".rate"));
                tr.setGuard(new ASTVariable(token.image + ".guard"));
                current.put(token.image + ".rate", new ASTValue(token.image + ".rate"));
                current.put(token.image + ".guard", new ASTValue(true));
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OPEN:{
      jj_consume_token(OPEN);
      optlist = OptionList();
for (PairValue pval : optlist.getList()) {
                        String label = pval.getLabel();
                        ASTree value = pval.getValue();
                        if (label.equals("rate")) {
                                current.put(token.image + ".rate", value);
                        } else if (label.equals("guard")) {
                                current.put(token.image + ".guard", value);
                        } else {
                                {if (true) throw new UnknownOption();}
                        }
                }
      jj_consume_token(CLOSE);
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      ;
    }
  }

  final public void ImmTransDeclaration() throws ParseException, ASTException {Token token;
        PairValueList optlist;
        ImmTrans tr;
    jj_consume_token(IMMTRANS);
    token = jj_consume_token(IDENTIFIER);
tr = current.createImmTrans(token.image,
                        new ASTVariable(token.image + ".weight"));
                tr.setGuard(new ASTVariable(token.image + ".guard"));
                current.put(token.image + ".weight", new ASTValue(token.image + ".weight"));
                current.put(token.image + ".guard", new ASTValue(true));
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OPEN:{
      jj_consume_token(OPEN);
      optlist = OptionList();
for (PairValue pval : optlist.getList()) {
                        String label = pval.getLabel();
                        ASTree value = pval.getValue();
                        if (label.equals("weight")) {
                                current.put(token.image + ".weight", value);
                        } else if (label.equals("guard")) {
                                current.put(token.image + ".guard", value);
                        } else {
                                {if (true) throw new UnknownOption();}
                        }
                }
      jj_consume_token(CLOSE);
      break;
      }
    default:
      jj_la1[13] = jj_gen;
      ;
    }
  }

  final public void GenTransDeclaration() throws ParseException, ASTException {Token token;
        PairValueList optlist;
    jj_consume_token(GENTRANS);
    token = jj_consume_token(IDENTIFIER);
current.createGenTrans(token.image,
                new ASTValue(token.image + ".dist"), GenTrans.DefaultPolicy);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OPEN:{
      jj_consume_token(OPEN);
      optlist = OptionList();
for (PairValue pval : optlist.getList()) {
                        String label = pval.getLabel();
                        ASTree value = pval.getValue();
                        GenTrans tr = (GenTrans) current.getTrans(token.image);
                        if (label.equals("dist")) {
                                tr.setDist(value);
                        } else if (label.equals("policy")) {
                                Object v = value.eval(current);
                                if (v instanceof Integer) {
                                        int pol = ((Integer) v).intValue();
                                        if (pol == 0) {
                                                tr.setPolicy(GenTransPolicy.PRD);
                                        } else if (pol == 1) {
                                                tr.setPolicy(GenTransPolicy.PRS);
                                        } else {
                                                {if (true) throw new UnknownOption();}
                                        }
                                } else {
                                        {if (true) throw new UnknownOption();}
                                }
                        } else if (label.equals("guard")) {
                                tr.setGuard(value);
                        } else {
                                {if (true) throw new UnknownOption();}
                        }
                }
      jj_consume_token(CLOSE);
      break;
      }
    default:
      jj_la1[14] = jj_gen;
      ;
    }
  }

  final public void ArcDeclaration() throws ParseException, ASTException {Token src;
        Token dest;
        PairValueList optlist;
        ArcBase a;
    jj_consume_token(ARC);
    src = jj_consume_token(IDENTIFIER);

    jj_consume_token(TO);
    dest = jj_consume_token(IDENTIFIER);
try {
                        Place p =current.getPlace(src.image);
                        Trans tr = current.getTrans(dest.image);
                        a = current.createNormalInArc(p, tr, new ASTValue(1));
                } catch (NotFindObjectException e1) {
                        try {
                                Trans tr = current.getTrans(src.image);
                                Place p =current.getPlace(dest.image);
                                a = current.createNormalOutArc(tr, p, new ASTValue(1));
                        } catch (NotFindObjectException e2) {
                                System.out.println("Did not find " + src.image + " -> " + dest.image);
                                {if (true) throw new NotFindObjectException();}
                        } catch (AlreadyExistException e3) {
                                System.out.println("Already exist " + src.image + " -> " + dest.image);
                                {if (true) throw new NotFindObjectException();}
                        }
                } catch (AlreadyExistException e4) {
                                System.out.println("Already exist " + src.image + " -> " + dest.image);
                                {if (true) throw new NotFindObjectException();}
                }
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OPEN:{
      jj_consume_token(OPEN);
      optlist = OptionList();
for (PairValue pval : optlist.getList()) {
                        String label = pval.getLabel();
                        ASTree value = pval.getValue();
                        if (label.equals("multi")) {
                                a.setMulti(value);
                        } else if (label.equals("firing")) {
                                a.setFiring(value);
                        } else {
                                {if (true) throw new UnknownOption();}
                        }
                }
      jj_consume_token(CLOSE);
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      ;
    }
  }

  final public void IArcDeclaration() throws ParseException, ASTException {Token src;
        Token dest;
        PairValueList optlist;
        ArcBase a;
    jj_consume_token(IARC);
    src = jj_consume_token(IDENTIFIER);

    jj_consume_token(TO);
    dest = jj_consume_token(IDENTIFIER);
Place p =current.getPlace(src.image);
                Trans tr = current.getTrans(dest.image);
                a = current.createNormalInArc(p, tr, new ASTValue(1));
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OPEN:{
      jj_consume_token(OPEN);
      optlist = OptionList();
for (PairValue pval : optlist.getList()) {
                        String label = pval.getLabel();
                        ASTree value = pval.getValue();
                        if (label.equals("multi")) {
                                a.setMulti(value);
                        } else if (label.equals("firing")) {
                                a.setFiring(value);
                        } else {
                                {if (true) throw new UnknownOption();}
                        }
                }
      jj_consume_token(CLOSE);
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      ;
    }
  }

  final public void OArcDeclaration() throws ParseException, ASTException {Token src;
        Token dest;
        PairValueList optlist;
        ArcBase a;
    jj_consume_token(OARC);
    src = jj_consume_token(IDENTIFIER);

    jj_consume_token(TO);
    dest = jj_consume_token(IDENTIFIER);
Place p =current.getPlace(dest.image);
                Trans tr = current.getTrans(src.image);
                a = current.createNormalOutArc(tr, p, new ASTValue(1));
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OPEN:{
      jj_consume_token(OPEN);
      optlist = OptionList();
for (PairValue pval : optlist.getList()) {
                        String label = pval.getLabel();
                        ASTree value = pval.getValue();
                        if (label.equals("multi")) {
                                a.setMulti(value);
                        } else if (label.equals("firing")) {
                                a.setFiring(value);
                        } else {
                                {if (true) throw new UnknownOption();}
                        }
                }
      jj_consume_token(CLOSE);
      break;
      }
    default:
      jj_la1[17] = jj_gen;
      ;
    }
  }

  final public void HArcDeclaration() throws ParseException, ASTException {Token src;
        Token dest;
        PairValueList optlist;
        ArcBase a;
    jj_consume_token(HARC);
    src = jj_consume_token(IDENTIFIER);

    jj_consume_token(TO);
    dest = jj_consume_token(IDENTIFIER);
Place p =current.getPlace(src.image);
                Trans tr = current.getTrans(dest.image);
                a = current.createInhibitArc(p, tr, new ASTValue(1));
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OPEN:{
      jj_consume_token(OPEN);
      optlist = OptionList();
for (PairValue pval : optlist.getList()) {
                        String label = pval.getLabel();
                        ASTree value = pval.getValue();
                        if (label.equals("multi")) {
                                a.setMulti(value);
                        } else {
                                {if (true) throw new UnknownOption();}
                        }
                }
      jj_consume_token(CLOSE);
      break;
      }
    default:
      jj_la1[18] = jj_gen;
      ;
    }
  }

  final public PairValueList OptionList() throws ParseException, ASTException {PairValueList optlist;
        PairValue val;
    val = OptionValue();
optlist = new PairValueList();
                optlist.add(val);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 45:{
        ;
        break;
        }
      default:
        jj_la1[19] = jj_gen;
        break label_2;
      }
      jj_consume_token(45);
      val = OptionValue();
optlist.add(val);
    }
{if ("" != null) return optlist;}
    throw new Error("Missing return statement in function");
  }

  final public PairValue OptionValue() throws ParseException, ASTException {Token token;
        ASTree val;
    token = jj_consume_token(IDENTIFIER);
    jj_consume_token(46);
    val = Expression();
{if ("" != null) return new PairValue(token.image, val);}
    throw new Error("Missing return statement in function");
  }

  final public ASTree getASTree() throws ParseException, ASTException {ASTree val;
    val = Expression();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case SEND:{
      jj_consume_token(SEND);
      break;
      }
    case 0:{
      jj_consume_token(0);
      break;
      }
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return val;}
    throw new Error("Missing return statement in function");
  }

  final public void getASTTest() throws ParseException, ASTException {ASTree val;
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IFELSE:
      case REAL:
      case INTEGER:
      case STRING:
      case PLUS:
      case MINUS:
      case NOT:
      case IDENTIFIER:
      case GLOBAL_NTOKEN:
      case NTOKEN:
      case OPEN:{
        ;
        break;
        }
      default:
        jj_la1[21] = jj_gen;
        break label_3;
      }
      val = Expression();
System.out.println(val.eval(current));
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case SEND:{
        jj_consume_token(SEND);
        break;
        }
      case NL:{
        jj_consume_token(NL);
        break;
        }
      case 0:{
        jj_consume_token(0);
        break;
        }
      default:
        jj_la1[22] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }

  }

/////
  final public ASTree Expression() throws ParseException, ASTException {ASTree val;
    if (jj_2_1(2)) {
      val = AssignExpression();
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IFELSE:
      case REAL:
      case INTEGER:
      case STRING:
      case PLUS:
      case MINUS:
      case NOT:
      case IDENTIFIER:
      case GLOBAL_NTOKEN:
      case NTOKEN:
      case OPEN:{
        val = OrExpression();
        break;
        }
      default:
        jj_la1[23] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return val;}
    throw new Error("Missing return statement in function");
  }

  final public ASTree AssignExpression() throws ParseException, ASTException {Token token;
        ASTree val;
    token = jj_consume_token(IDENTIFIER);

    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case 47:{
      jj_consume_token(47);
      val = Expression();

      break;
      }
    case 46:{
      jj_consume_token(46);
      val = Expression();

      break;
      }
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
current.put(token.image, val);
                {if ("" != null) return val;}
    throw new Error("Missing return statement in function");
  }

  final public ASTree OrExpression() throws ParseException, ASTException {ASTree val1;
        ASTree val2;
    val1 = AndExpression();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case OR:{
        ;
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        break label_4;
      }
      jj_consume_token(OR);
      val2 = AndExpression();
val1 = new ASTOr(val1, val2);
    }
{if ("" != null) return val1;}
    throw new Error("Missing return statement in function");
  }

  final public ASTree AndExpression() throws ParseException, ASTException {ASTree val1;
        ASTree val2;
    val1 = EqExpression();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case AND:{
        ;
        break;
        }
      default:
        jj_la1[26] = jj_gen;
        break label_5;
      }
      jj_consume_token(AND);
      val2 = EqExpression();
val1 = new ASTAnd(val1, val2);
    }
{if ("" != null) return val1;}
    throw new Error("Missing return statement in function");
  }

  final public ASTree EqExpression() throws ParseException, ASTException {ASTree val1;
        ASTree val2;
    val1 = CompareExpression();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EQ:
      case NEQ:{
        ;
        break;
        }
      default:
        jj_la1[27] = jj_gen;
        break label_6;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EQ:{
        jj_consume_token(EQ);
        val2 = CompareExpression();
val1 = new ASTEq(val1, val2);
        break;
        }
      case NEQ:{
        jj_consume_token(NEQ);
        val2 = CompareExpression();
val1 = new ASTNeq(val1, val2);
        break;
        }
      default:
        jj_la1[28] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return val1;}
    throw new Error("Missing return statement in function");
  }

  final public ASTree CompareExpression() throws ParseException, ASTException {ASTree val1;
        ASTree val2;
    val1 = TermExpression();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case GT:
      case LT:
      case GTE:
      case LTE:{
        ;
        break;
        }
      default:
        jj_la1[29] = jj_gen;
        break label_7;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case GT:{
        jj_consume_token(GT);
        val2 = TermExpression();
val1 = new ASTGt(val1, val2);
        break;
        }
      case GTE:{
        jj_consume_token(GTE);
        val2 = TermExpression();
val1 = new ASTGte(val1, val2);
        break;
        }
      case LT:{
        jj_consume_token(LT);
        val2 = TermExpression();
val1 = new ASTLt(val1, val2);
        break;
        }
      case LTE:{
        jj_consume_token(LTE);
        val2 = TermExpression();
val1 = new ASTLte(val1, val2);
        break;
        }
      default:
        jj_la1[30] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return val1;}
    throw new Error("Missing return statement in function");
  }

  final public ASTree TermExpression() throws ParseException, ASTException {ASTree val1;
        ASTree val2;
    val1 = MultiplyExpression();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:
      case MINUS:{
        ;
        break;
        }
      default:
        jj_la1[31] = jj_gen;
        break label_8;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:{
        jj_consume_token(PLUS);
        val2 = MultiplyExpression();
val1 = new ASTPlus(val1, val2);
        break;
        }
      case MINUS:{
        jj_consume_token(MINUS);
        val2 = MultiplyExpression();
val1 = new ASTMinus(val1, val2);
        break;
        }
      default:
        jj_la1[32] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return val1;}
    throw new Error("Missing return statement in function");
  }

  final public ASTree MultiplyExpression() throws ParseException, ASTException {ASTree val1;
        ASTree val2;
    val1 = Factor();
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MULTIPLY:
      case DIVIDE:
      case MOD:{
        ;
        break;
        }
      default:
        jj_la1[33] = jj_gen;
        break label_9;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MULTIPLY:{
        jj_consume_token(MULTIPLY);
        val2 = Factor();
val1 = new ASTMulti(val1, val2);
        break;
        }
      case DIVIDE:{
        jj_consume_token(DIVIDE);
        val2 = Factor();
val1 = new ASTDivide(val1, val2);
        break;
        }
      case MOD:{
        jj_consume_token(MOD);
        val2 = Factor();
val1 = new ASTMod(val1, val2);
        break;
        }
      default:
        jj_la1[34] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return val1;}
    throw new Error("Missing return statement in function");
  }

  final public ASTree Factor() throws ParseException, ASTException {ASTree val1;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IFELSE:
    case REAL:
    case INTEGER:
    case STRING:
    case IDENTIFIER:
    case GLOBAL_NTOKEN:
    case NTOKEN:
    case OPEN:{
      val1 = PrimaryExpression();
{if ("" != null) return val1;}
      break;
      }
    case NOT:{
      jj_consume_token(NOT);
      val1 = PrimaryExpression();
{if ("" != null) return new ASTNot(val1);}
      break;
      }
    case PLUS:{
      jj_consume_token(PLUS);
      val1 = PrimaryExpression();
{if ("" != null) return val1;}
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
      val1 = PrimaryExpression();
{if ("" != null) return new ASTUnaryMinus(val1);}
      break;
      }
    default:
      jj_la1[35] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public ASTree PrimaryExpression() throws ParseException, ASTException {ASTree val1, val2, val3;
        Token token;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INTEGER:{
      token = jj_consume_token(INTEGER);
{if ("" != null) return new ASTValue(Integer.parseInt(token.image));}
      break;
      }
    case REAL:{
      token = jj_consume_token(REAL);
{if ("" != null) return new ASTValue(Double.parseDouble(token.image));}
      break;
      }
    case STRING:{
      token = jj_consume_token(STRING);
{if ("" != null) return new ASTValue(token.image);}
      break;
      }
    case NTOKEN:{
      jj_consume_token(NTOKEN);
      token = jj_consume_token(IDENTIFIER);
{if ("" != null) return new ASTNumOfToken(current.getPlace(token.image));}
      break;
      }
    case GLOBAL_NTOKEN:{
      jj_consume_token(GLOBAL_NTOKEN);
      token = jj_consume_token(IDENTIFIER);
{if ("" != null) return new ASTNumOfToken(current.getPlace(token.image, global));}
      break;
      }
    case IDENTIFIER:{
      token = jj_consume_token(IDENTIFIER);
{if ("" != null) return new ASTVariable(token.image);}
      break;
      }
    case IFELSE:{
      jj_consume_token(IFELSE);
      jj_consume_token(OPEN);
      val1 = Expression();
      jj_consume_token(45);
      val2 = Expression();
      jj_consume_token(45);
      val3 = Expression();
      jj_consume_token(CLOSE);
{if ("" != null) return new ASTIfThenElse(val1, val2, val3);}
      break;
      }
    case OPEN:{
      jj_consume_token(OPEN);
      val1 = Expression();
      jj_consume_token(CLOSE);
{if ("" != null) return val1;}
      break;
      }
    default:
      jj_la1[36] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_3R_12()
 {
    if (jj_scan_token(46)) return true;
    return false;
  }

  private boolean jj_3R_11()
 {
    if (jj_scan_token(47)) return true;
    return false;
  }

  private boolean jj_3_1()
 {
    if (jj_3R_10()) return true;
    return false;
  }

  private boolean jj_3R_10()
 {
    if (jj_scan_token(IDENTIFIER)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_11()) {
    jj_scanpos = xsp;
    if (jj_3R_12()) return true;
    }
    return false;
  }

  /** Generated Token Manager. */
  public JSPetriNetParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[37];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x83f57fc0,0x83e57fc0,0x100000,0x0,0x83e57fc0,0x83e57fc0,0x100000,0x0,0x0,0x13fc0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x83e04000,0x1,0x83e04000,0x0,0x20000000,0x40000000,0x0,0x0,0x0,0x0,0x3000000,0x3000000,0x1c000000,0x1c000000,0x83e04000,0xe04000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x1bc0,0x3c0,0x0,0x1800,0x3c0,0x3c0,0x0,0x1800,0x1800,0x0,0x800,0x200,0x200,0x200,0x200,0x200,0x200,0x200,0x200,0x2000,0x1000,0x3c0,0x1800,0x3c0,0xc000,0x0,0x0,0x3,0x3,0x3c,0x3c,0x0,0x0,0x0,0x0,0x3c0,0x3c0,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public JSPetriNetParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public JSPetriNetParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new JSPetriNetParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public JSPetriNetParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new JSPetriNetParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public JSPetriNetParser(JSPetriNetParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(JSPetriNetParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[48];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 37; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 48; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
