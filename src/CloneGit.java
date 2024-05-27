


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Represents a file in the version control system
class File {
    private String name;
    private String content;
    private Date created;
    private Date lastModified;

    public File(String name, String content) {
        this.name = name;
        this.content = content;
        this.created = new Date();
        this.lastModified = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }


}

// Represents a folder in the version control system
class Folder {
    private String name;
    private List<File> files;
    private List<Folder> folders;

    public Folder(String name) {
        this.name = name;
        this.files = new ArrayList<>();
        this.folders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }


    public void addFile(File file) {
        files.add(file);
    }

    public void addFolder(Folder folder) {
        folders.add(folder);
    }
}

class Version {
    private final int id;
    private final List<File> files;
    private final List<Folder> folders;

    public Version(int id, List<File> files, List<Folder> folders) {
        this.id = id;
        this.files = new ArrayList<>(files); // Copy the list to prevent modification of original list
        this.folders = new ArrayList<>(folders); // Copy the list to prevent modification of original list
    }

    public int getId() {
        return id;
    }

    public List<File> getFiles() {
        return files;
    }

    public List<Folder> getFolders() {
        return folders;
    }

}


class VersionControlSystem {
    private final List<Version> versions;
    private int nextVersionId;

    public VersionControlSystem() {
        this.versions = new ArrayList<>();
        this.nextVersionId = 1; // Start version IDs from 1
    }

    // Commit changes to files and folders
    public void commitChanges(List<File> changedFiles, List<Folder> changedFolders) {
        // Create a new version with the changed files and folders
        Version version = new Version(nextVersionId++, changedFiles, changedFolders);
        // Add the new version to the list of versions
        versions.add(version);
    }

    // Checkout a specific version of files and folders
    public void checkoutVersion(int versionId, List<File> files, List<Folder> folders) {
        // Find the version with the given version ID
        Version version = findVersionById(versionId);
        if (version != null) {
            // Copy the files and folders from the version to the provided lists
            files.addAll(version.getFiles());
            folders.addAll(version.getFolders());
        } else {
            System.out.println("Version with ID " + versionId + " not found.");
        }
    }

    // Helper method to find a version by ID
    private Version findVersionById(int versionId) {
        for (Version version : versions) {
            if (version.getId() == versionId) {
                return version;
            }
        }
        return null; // Version not found
    }
}

public class CloneGit {

    public static void main(String[] args) {

        File file1 = new File("file1.txt", "Content of file1");
        File file2 = new File("file2.txt", "Content of file2");
        File file3 = new File("samiul.txt", "My name is samiul haque,I am a java Developer");
        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");
        folder1.addFile(file1);
        folder1.addFile(file2);
        folder2.addFile(file3);
        // Create the version control system
        VersionControlSystem vcs = new VersionControlSystem();

        // Commit changes to files and folders
        List<File> changedFiles = new ArrayList<>();
        changedFiles.add(file1);
        changedFiles.add(file2);
        changedFiles.add(file3); // Include file3 in changed files
        List<Folder> changedFolders = new ArrayList<>();
        changedFolders.add(folder1);
        changedFolders.add(folder2);
        vcs.commitChanges(changedFiles, changedFolders);


        // Create a new file
        File newFile = new File("file4.txt", "Content of file4");
        // Add the new file to the list of changed files
        changedFiles.add(newFile);
        folder2.addFile(newFile);


        // Commit changes with the new file
        vcs.commitChanges(changedFiles, changedFolders);


        List<File> files = new ArrayList<>();
        List<Folder> folders = new ArrayList<>();
        vcs.checkoutVersion(2, files, folders);

        // Display the checked out files and folders after the update
        System.out.println("Files and folders in version 2:");
        displayFilesAndFolders(files, folders);
    }

    // Helper method to display files and folders
    private static void displayFilesAndFolders(List<File> files, List<Folder> folders) {
        for (File file : files) {
            System.out.println("- File: " + file.getName());

        }
        System.out.println("------------------------");
        for (Folder folder : folders) {
            System.out.println("- Folder: " + folder.getName());
            // Recursively display files and folders inside this folder
            displayFilesAndFolders(folder.getFiles(), folder.getFolders());
        }
    }
}
