package com.iuh.exercise2;

/*
 * Author: Nguyen Hong Son
 * Dated: 27/01/2024
*/
import java.io.File;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CommonOperations {
	public static void listMethodCalls(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
//			System.out.println(Strings.repeat("=", path.length()));
			try {
				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(PackageDeclaration n, Object arg) {
						super.visit(n, arg);
						System.out.println(n.getNameAsString());
					}

					@Override
					public void visit(FieldDeclaration n, Object arg) {
						super.visit(n, arg);
						System.out.println(" [L " + n.getBegin() + "] " + n);
					}

					@Override
					public void visit(MethodDeclaration n, Object arg) {
						super.visit(n, arg);
						System.out.println(" [L " + n.getBegin() + "] " + n.getDeclarationAsString());
					}
				}.visit(StaticJavaParser.parse(file), null);
			} catch (Exception e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);
	}

	public static void main(String[] args) {
		File projectDir = new File("T:\\ThucHanh\\json-demo\\src\\main\\java");
		listMethodCalls(projectDir);
	}
}
