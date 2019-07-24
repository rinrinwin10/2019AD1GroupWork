import java.util.HashMap;
import java.util.Scanner;

class Ex8_11 {
    static class Instruction {
        int cmd;
        String optkey;
        String optdata;
        String optid;
        String optname;
        int optpassengers;
    }
 
    static final int NOP = 0;
    static final int HALT = 1;
    static final int SHOW = 2;
    static final int SHOW90 = 90;
    static final int INS = 3;
    static final int DEL = 4;
    static final int FIND = 5;
    
    static HashMap<String, Integer> commandMap = new HashMap<String, Integer>();

    static {
        commandMap.put("nop", NOP);
        commandMap.put("halt", HALT);
        commandMap.put("show", SHOW);
        commandMap.put("show90", SHOW90);
        commandMap.put("ins", INS);    commandMap.put("insert", INS);
        commandMap.put("del", DEL);    commandMap.put("delete", DEL);
        commandMap.put("find", FIND);  commandMap.put("search", FIND);
    }

    public static void main(String args[]) {
        run_interpreter();
    }
    
    public static void printTxt(String s) {
        System.out.print(s);
    }
    
    static void run_interpreter() {
        MyBinaryTree mbt = new MyBinaryTree();
        mbt.rootnode = null;

        MyNode nd;
        Instruction inst;

        Scanner sc = new Scanner(System.in);

        do{
            inst = read_instruction(sc);
            switch(inst.cmd) {
            case NOP:
                break;
            case SHOW:
                if(mbt.rootnode!=null) {
                    mbt.rootnode.printTree(); 
                } 
                printTxt("\n");
                break;
            case SHOW90:
                if(mbt.rootnode!=null) {
                    mbt.rootnode.printTree90(); 
                } 
                printTxt("\n");
                break;
            case INS:
                MyNode newNode = new MyNode(inst.optid,inst.optname,inst.optpassengers);
                mbt.insertNode(newNode);
                break;
            case DEL:
                nd = mbt.deleteNodeByName(inst.optkey);
                if( nd != null ) {
                    printTxt(" |Node:");
                    nd.printThisNode(); printTxt(" deleted.\n");
                } else {
                    printTxt(" |Key not found.\n");
                }
                break;
            case FIND:
                // nd = mbt.findNodeByKey(inst.optkey);
                if( nd != null ) {
                    printTxt(" |found:");
                    nd.printThisNode(); printTxt(" \n");
                } else {
                    printTxt(" |Key not found.\n");
                }
                break;
            }
        } while( inst.cmd != HALT );
        
        return;
    }

    static String promptAndReadLine(Scanner sc, String prompt) {
        System.err.print(prompt+":");
        return sc.nextLine();
    }

    static void inputInstructionKey(Instruction inst, Scanner sc) {
        inst.optkey  = promptAndReadLine(sc, " key");
    }

    static void inputInstructionData(Instruction inst, Scanner sc) {
        inst.optdata  = promptAndReadLine(sc, "data");
    }

    static void inputInstructionKeyAndDataEachLine(Instruction inst, Scanner sc) {
        inputInstructionKey(inst, sc);
        inputInstructionData(inst, sc);
    }

    static void setInstructionCommand(Instruction inst, String cmd) {
        if (commandMap.containsKey(cmd)) {
            inst.cmd = commandMap.get(cmd);
        } else {
            inst.cmd = NOP;
        }
    }

    static void setInstructionKeyOneLine(Instruction inst, String[] param) {
        System.err.print(" key:"+param[1]+"\n");
        inst.optkey  = param[1];
    }

    static void setInstructionDataOneLine(Instruction inst, String[] param) {
        System.err.print("data:"+param[2]+"\n");
        inst.optdata  = param[2];
    }

    static void setInstructionKeyAndDataOneLine(Instruction inst, String[] param) {
        setInstructionKeyOneLine(inst, param);
        setInstructionDataOneLine(inst, param);
    }
    
    static Instruction read_instruction(Scanner sc) {
        Instruction inst = new Instruction();
        try{
            String buf;
            String[] param;
            buf = promptAndReadLine(sc, "cmd");
            param = buf.split(" ",0);
            setInstructionCommand(inst, param[0]);
            if( inst.cmd == INS) {
                if (param.length > 2){
                    setInstructionKeyAndDataOneLine(inst, param);
                } else {
                    inputInstructionKeyAndDataEachLine(inst, sc);
                }
            } else if( inst.cmd == DEL || inst.cmd == FIND) {
                if (param.length > 1){
                    setInstructionKeyOneLine(inst, param);
                }else{
                    inputInstructionKey(inst, sc);
                }
            }
        } catch( Exception e) {
            System.err.println(e);
            inst.cmd = NOP;
        }
        return inst;
    }
}
