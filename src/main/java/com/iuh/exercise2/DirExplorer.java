package com.iuh.exercise2;

import java.io.File;

/*
 * Author: Nguyen Hong Son
 * Dated: 27/01/2024
*/
public class DirExplorer {
	// interface for file handler
	public interface FileHandler {
		void handle(int level, String path, File file);
	}

	// interface for file filter
	public interface Filter {
		boolean interested(int level, String path, File file);
	}

	private final FileHandler fileHandler;
	private final Filter filter;

	public DirExplorer(Filter filter, FileHandler fileHandler) {
		this.filter = filter;
		this.fileHandler = fileHandler;
	}

	public void explore(File root) {
		explore(0, "", root);
	}

	private void explore(int level, String path, File file) {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				if (child != null) {
					explore(level + 1, path + File.separator + child.getName(), child);
				}
			}
		} else {
			if (filter.interested(level, path, file)) {
				fileHandler.handle(level, path, file);
			}
		}
	}

	public static void main(String[] args) {
		File projectDir = new File("data" + File.separator + "demo.json");
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> System.out.println(path))
				.explore(projectDir);
	}
}