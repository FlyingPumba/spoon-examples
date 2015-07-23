// default package (CtPackage.TOP_LEVEL_PACKAGE_NAME in Spoon= unnamed package)



public class Main {
    static spoon.Launcher launcher = new spoon.Launcher();

    static java.util.List<spoon.reflect.declaration.CtSimpleType<?>> classes = new java.util.ArrayList<spoon.reflect.declaration.CtSimpleType<?>>();

    static spoon.reflect.declaration.CtSimpleType<?> buggyClass;

    static java.util.List<spoon.reflect.code.CtStatement> statements = new java.util.ArrayList<spoon.reflect.code.CtStatement>();

    static java.util.List<spoon.reflect.code.CtStatement> buggyStatements = new java.util.ArrayList<spoon.reflect.code.CtStatement>();

    public static void main(java.lang.String[] args) {
        java.lang.String[] spoon_args = new java.lang.String[]{ "-i" , "src/main/java/" , "-o" , "src/main/java/" };
        Main.launcher.setArgs(spoon_args);
        try {
            Main.launcher.run();
        } catch (java.lang.Exception e) {
            return ;
        }
        Main.initClasses();
        Main.initStatements();
        while (true) {
            org.junit.runner.JUnitCore junit = new org.junit.runner.JUnitCore();
            org.junit.runner.Result result = junit.run(BuggyClassTest.class);
            if (!(result.wasSuccessful())) {
                Main.mutateBuggyClass();
                Main.writeBuggyClass();
            } 
        }
    }

    private static void initClasses() {
        for (spoon.reflect.declaration.CtSimpleType<?> c : Main.launcher.getFactory().Class().getAll()) {
            Main.classes.add(c);
            if ((c.getActualClass()) == (BuggyClass.class)) {
                Main.buggyClass = c;
            } 
        }
    }

    private static void initStatements() {
        for (spoon.reflect.declaration.CtSimpleType<?> c : Main.classes) {
            Main.statements.addAll(c.getElements(Main.statementFilter));
            if ((c.getActualClass()) == (BuggyClass.class)) {
                Main.buggyStatements.addAll(c.getElements(Main.statementFilter));
            } 
        }
    }

    private static void mutateBuggyClass() {
        java.util.Random rnd = new java.util.Random();
        spoon.reflect.code.CtStatement newStatement = Main.statements.get(rnd.nextInt(Main.statements.size()));
        spoon.reflect.code.CtStatement oldStatement = Main.buggyStatements.get(rnd.nextInt(Main.buggyStatements.size()));
        if ((rnd.nextGaussian()) < 0.5) {
            oldStatement.insertAfter(newStatement);
        } else {
            oldStatement.insertBefore(newStatement);
        }
    }

    private static void writeBuggyClass() {
        java.lang.String[] spoon_args = new java.lang.String[]{ "-o" , "src/main/java/" };
        Main.launcher.setArgs(spoon_args);
        Main.launcher.addInputResource(((spoon.compiler.SpoonResource)(Main.buggyClass)));
        try {
            Main.launcher.run();
        } catch (java.lang.Exception e) {
            return ;
        }
        org.eclipse.jdt.internal.compiler.batch.Main.compile(org.eclipse.jdt.internal.compiler.batch.Main.tokenize("-1.6 src/main/java/"), new java.io.PrintWriter(java.lang.System.out), new java.io.PrintWriter(java.lang.System.err), null);
    }

    private static spoon.reflect.visitor.Filter<spoon.reflect.code.CtStatement> statementFilter = new spoon.reflect.visitor.Filter<spoon.reflect.code.CtStatement>() {
        @java.lang.Override
        public boolean matches(spoon.reflect.code.CtStatement element) {
            if (element instanceof spoon.reflect.code.CtBlock<?>) {
                return false;
            } else if (element instanceof spoon.reflect.code.CtAssert<?>) {
                return false;
            } else if (element instanceof spoon.reflect.code.CtTry) {
                return false;
            } else if (element instanceof spoon.reflect.declaration.CtClass<?>) {
                return false;
            } else {
                return true;
            }
        }

        @java.lang.Override
        public java.lang.Class<?> getType() {
            return spoon.reflect.code.CtStatement.class;
        }
    };
}

