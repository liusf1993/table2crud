package com.hqjl.table2crud.component;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.annotations.NotNull;

/**
 * PsiClassManager
 */
public class PsiFileManager implements ProjectComponent {

  private static final Logger logger = Logger.getInstance(PsiFileManager.class);
  private Project project;
  private PsiManager psiManager;
  private CodeStyleManager codeStyleManager;
  private LocalFileSystem localFileSystem;

  public PsiFileManager(Project project,
      PsiManager psiManager,
      CodeStyleManager codeStyleManager,
      LocalFileSystem localFileSystem) {
    this.project = project;
    this.psiManager = psiManager;
    this.localFileSystem = localFileSystem;
    this.codeStyleManager = codeStyleManager;
  }

  public static PsiFileManager getInstance(Project project) {
    return project.getComponent(PsiFileManager.class);
  }

  public static String getPrimaryClassNameFromJavaFileName(String name) {
    return name.substring(0, name.length() - ".java".length());
  }

  public PsiClass findPrimaryClass(String filePath) {
    debug("findPrimaryClass filePath=" + filePath);
    if (filePath == null) {
      return null;
    }
    VirtualFile vFile = localFileSystem.findFileByPath(filePath);
    if (vFile == null) {
      return null;
    }
    PsiFile psiFile = psiManager.findFile(vFile);
    return findPrimaryClass(psiFile);
  }

  public PsiFile findPsiFile(String filePath) {
    debug("findPsiFile filePath=" + filePath);
    if (filePath == null) {
      return null;
    }
    VirtualFile vFile = localFileSystem.findFileByPath(filePath);
    if (vFile == null) {
      return null;
    }
    PsiFile psiFile = psiManager.findFile(vFile);
    return psiFile;
  }

  public PsiClass findPrimaryClass(PsiFile psiFile) {
    if (!(psiFile instanceof PsiJavaFile)) {
      return null;
    }
    PsiJavaFile javaFile = (PsiJavaFile) psiFile;
    PsiClass[] classes = javaFile.getClasses();
    String filePrimaryClassName = getPrimaryClassNameFromJavaFileName(javaFile.getName());
    for (PsiClass aClass : classes) {
      if (filePrimaryClassName.equals(aClass.getName())) {
        return aClass;
      }
    }
    return null;
  }

  private void debug(String message) {
    if (logger.isDebugEnabled()) {
      logger.debug(message);
    }
  }

  public PsiManager getPsiManager() {
    return psiManager;
  }

  @Override
  public void projectOpened() {

  }

  @Override
  public void projectClosed() {

  }

  @Override
  public void initComponent() {

  }

  @Override
  public void disposeComponent() {

  }

}
