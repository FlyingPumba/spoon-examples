// default package (CtPackage.TOP_LEVEL_PACKAGE_NAME in Spoon= unnamed package)



public class BuggyClass {
    public int m1(java.lang.String s) {
        if (s != null) {
            java.lang.System.out.println(s);
            return s.length();
        } else {
            return 0;
        }
    }

    public int m2(java.lang.String s) {
        if (s != null) {
            java.lang.System.out.println(s);
            return s.length();
        } else {
            return 0;
        }
    }

    public int m3(java.lang.String s) {
        java.lang.System.out.println(s);
        return s.length();
    }
}

