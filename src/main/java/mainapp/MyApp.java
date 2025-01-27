package mainapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class MyApp {

    // Method to check if the .git directory exists
    public static String directoryExists() {
        String gitDirectoryExists;
        try {
            System.out.println("Checking if .git directory exists...");
            gitDirectoryExists = executeCommand("git rev-parse --is-inside-work-tree").trim();
            System.out.println("Git Directory Exists: " + gitDirectoryExists);
            if (gitDirectoryExists.equals("true")) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            System.out.println("Error in directoryExists method: " + e.getMessage());
            return "";
        }
    }

    // Method to check if there's at least one commit in the repository
    public static String atleastOneCommit() {
        String atleastOneCommit;
        try {
            System.out.println("Checking if at least one commit exists...");
            atleastOneCommit = executeCommand("git log --oneline").trim();
            System.out.println("At least one commit: " + atleastOneCommit);
            if (!atleastOneCommit.isEmpty()) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            System.out.println("Error in atleastOneCommit method: " + e.getMessage());
            return "";
        }
    }

    // Method to check if a branch called feature_branch exists
    public static String featureBranchExists() {
        String featureBranchExists;
        try {
            System.out.println("Checking if feature_branch exists...");
            featureBranchExists = executeCommand("git branch --list feature_branch");
            System.out.println("Feature Branch Exists: " + featureBranchExists);
            if (featureBranchExists.contains("feature_branch")) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            System.out.println("Error in featureBranchExists method: " + e.getMessage());
            return "";
        }
    }

    // Method to check if a commit has been successfully rebased
    public static String rebaseSuccess() {
        String rebaseSuccess;
        try {
            System.out.println("Checking if the last commit has 'Rebased' in the message...");
            rebaseSuccess = executeCommand("git log --oneline | head -n 1").trim();
            System.out.println("Latest commit: " + rebaseSuccess);
            // Checking if the latest commit message contains "Rebased"
            if (rebaseSuccess.contains("Rebased")) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            System.out.println("Error in rebaseSuccess method: " + e.getMessage());
            return "";
        }
    }

    public static void main(String[] args) {
        try {
            // Check if .git directory exists
            String gitDirectoryExists = directoryExists();
            if (gitDirectoryExists.equals("true")) {
                System.out.println("Git repository initialized successfully.");
            } else {
                System.out.println("Git repository not initialized.");
                return;
            }

            // Check for at least one commit
            String atleastOneCommit = atleastOneCommit();
            if (atleastOneCommit.equals("true")) {
                System.out.println("Changes have been committed.");
            } else {
                System.out.println("No changes committed.");
                return;
            }

            // Check if feature_branch exists
            String featureBranchExists = featureBranchExists();
            if (featureBranchExists.equals("true")) {
                System.out.println("feature_branch exists.");
            } else {
                System.out.println("feature_branch does not exist.");
            }

            // Check if rebase was successful
            String rebaseSuccess = rebaseSuccess();
            if (rebaseSuccess.equals("true")) {
                System.out.println("Rebase operation was successful.");
            } else {
                System.out.println("Rebase operation failed.");
            }

        } catch (Exception e) {
            System.out.println("Error in main method: " + e.getMessage());
        }
    }

    private static String executeCommand(String command) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File("utils")); // Ensure this is correct
        processBuilder.command("bash", "-c", command);
        System.out.println("Executing command: " + command);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitVal = process.waitFor();
        System.out.println("Command executed with exit code: " + exitVal);
        if (exitVal == 0) {
            return output.toString();
        } else {
            System.out.println("Command failed with exit code: " + exitVal);
            throw new RuntimeException("Failed to execute command: " + command);
        }
    }
}

// package mainapp;

// import java.io.BufferedReader;
// import java.io.File;
// import java.io.InputStreamReader;

// public class MyApp {

// 	public static String directoryExists() {
// 		String gitDirectoryExists;
// 		try {
// 			gitDirectoryExists = executeCommand("git rev-parse --is-inside-work-tree").trim();
// 			if (gitDirectoryExists.equals("true")) {
// 				return "true";
// 			} else {
// 				return "false";
// 			}
// 		} catch (Exception e) {
// 			return "";
// 		}
// 	}

// 	public static String atleastOneCommit() {
// 		String atleastOneCommit;
// 		try {
// 			atleastOneCommit = executeCommand("git log --oneline").trim();
// 			if (!atleastOneCommit.isEmpty()) {
// 				return "true";
// 			} else {
// 				return "false";
// 			}
// 		} catch (Exception e) {
// 			return "";
// 		}
// 	}

// 	public static String tempBranch() {
// 		String tempBranchExists;
// 		try {
// 			tempBranchExists = executeCommand("git branch --list tmpbranch");
// 			if (tempBranchExists.contains("tmpbranch")) {
// 				return "true";
// 			} else {
// 				return "false";
// 			}
// 		} catch (Exception e) {
// 			return "";
// 		}
// 	}

// 	public static String mergeBase() {
// 		String mergeBase;
// 		try {
// 			mergeBase = executeCommand("git merge-base main tmpbranch").trim();
// 			String tmpBranchLastCommit = executeCommand("git rev-parse tmpbranch").trim();
// 			if (mergeBase.equals(tmpBranchLastCommit)) {
// 				return "true";
// 			} else {
// 				return "false";
// 			}
// 		} catch (Exception e) {
// 			return "";
// 		}
// 	}

// 	public static void main(String[] args) {
// 		try {
// 			// Check if .git directory exists
// 			String gitDirectoryExists = directoryExists();
// 			if (gitDirectoryExists.equals("true")) {
// 				System.out.println("Git repository initialized successfully.");
// 			} else {
// 				System.out.println("Git repository not initialized.");
// 				return;
// 			}

// //			// Check for commit (This checks if there's at least one commit)
// 			String atleastOneCommit = atleastOneCommit();
// 			if (atleastOneCommit.equals("true")) {
// 				System.out.println("Changes have been committed.");
// 			} else {
// 				System.out.println("No changes committed.");
// 				return;
// 			}

// //			// Check if tmpbranch exists
// 			String tempBranchExists = tempBranch();
// 			if (tempBranchExists.equals("true")) {
// 				System.out.println("tmpbranch exists.");
// 			} else {
// 				System.out.println("tmpbranch does not exist.");
// 			}

// //			// Check if tmpbranch has been merged into main
// //			// Note: This is a simplistic check. It assumes if tmpbranch is not ahead of
// //			// main, it's merged.
// 			String mergeBase = mergeBase();
// 			if (mergeBase.equals("true")) {
// 				System.out.println("tmpbranch has been merged into main.");
// 			} else {
// 				System.out.println("tmpbranch has not been merged into main.");
// 			}
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
// 	}

// 	private static String executeCommand(String command) throws Exception {
// 		ProcessBuilder processBuilder = new ProcessBuilder();
// 		processBuilder.directory(new File("utils"));
// 		processBuilder.command("bash", "-c", command);
// 		Process process = processBuilder.start();

// 		StringBuilder output = new StringBuilder();
// 		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

// 		String line;
// 		while ((line = reader.readLine()) != null) {
// 			output.append(line).append("\n");
// 		}

// 		int exitVal = process.waitFor();
// 		if (exitVal == 0) {
// 			return output.toString();
// 		} else {
// 			throw new RuntimeException("Failed to execute command: " + command);
// 		}
// 	}
// }
