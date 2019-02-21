//package com.automate.test;
//
//import com.sun.source.tree.CompilationUnitTree;
//import com.sun.source.tree.VariableTree;
//import com.sun.source.util.JavacTask;
//import com.sun.source.util.TreeScanner;
//import com.sun.tools.javac.api.JavacTool;
//import com.sun.tools.javac.file.JavacFileManager;
//import com.sun.tools.javac.util.Context;
//
//import javax.tools.JavaCompiler;
//import javax.tools.JavaFileObject;
//import java.io.IOException;
//import java.nio.charset.Charset;
//
//public class JavaParser {
//
//    private static final String path = "D:\\idea-workspace\\Automate2\\automate-web\\src\\test\\java\\com\\automate\\test/DiskSpaceDetail.java";
//
//    private JavacFileManager fileManager;
//    private JavacTool javacTool;
//
//    public JavaParser() {
//        Context context = new Context();
//        fileManager = new JavacFileManager(context, true, Charset.defaultCharset());
//        javacTool = new JavacTool();
//    }
//
//    public void parseJavaFiles() {
//        Iterable<  ? extends JavaFileObject> files = fileManager.getJavaFileObjects(path);
//        JavaCompiler.CompilationTask compilationTask = javacTool.getTask(null, fileManager, null, null, null, files);
//        JavacTask javacTask = (JavacTask) compilationTask;
//        try {
//            Iterable<? extends CompilationUnitTree> result = javacTask.parse();
//            for (CompilationUnitTree tree : result) {
//                tree.accept(new SourceVisitor(), null);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    static class SourceVisitor extends TreeScanner<Void, Void>
//
//    {
//
//        private String currentPackageName = null;
//
//        @Override
//        public Void visitCompilationUnit (CompilationUnitTree node, Void aVoid){
//        return super.visitCompilationUnit(node, aVoid);
//    }
//
//        @Override
//        public Void visitVariable (VariableTree node, Void aVoid){
//        formatPtrln("variable name: %s, type: %s, kind: %s, package: %s",
//                node.getName(), node.getType(), node.getKind(), currentPackageName);
//        return null;
//    }
//    }
//
//    public static void formatPtrln(String format, Object... args) {
//        System.out.println(String.format(format, args));
//    }
//
//    public static void main(String[] args) {
//
//        new JavaParser().parseJavaFiles();
//    }
//}