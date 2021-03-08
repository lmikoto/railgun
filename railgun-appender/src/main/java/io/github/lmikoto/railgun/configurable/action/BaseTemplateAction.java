package io.github.lmikoto.railgun.configurable.action;

import com.intellij.ui.treeStructure.Tree;
import io.github.lmikoto.railgun.configurable.CodeGroup;
import io.github.lmikoto.railgun.configurable.TemplateConfigurable;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

/**
 * @author liuyang
 * 2021/3/7 7:46 下午
 */
public abstract class BaseTemplateAction {


    private final Tree templateTree;

    public BaseTemplateAction(TemplateConfigurable configurable){
        templateTree = configurable.getTemplateTree();
    }

    protected DefaultMutableTreeNode getSelectedNode() {
        return (DefaultMutableTreeNode) this.templateTree.getLastSelectedPathComponent();
    }

    protected void showDialog(JDialog dialog, int width, int height) {
        dialog.setSize(width, height);
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(null);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    private void addNode(DefaultMutableTreeNode pNode, MutableTreeNode newNode){
        pNode.add(newNode);
        DefaultTreeModel model = (DefaultTreeModel) this.templateTree.getModel();
        TreeNode[] nodes = model.getPathToRoot(newNode);
        TreePath path = new TreePath(nodes);
        this.templateTree.scrollPathToVisible(path);
        this.templateTree.updateUI();
    }

    protected void addGroup(CodeGroup group) {
        DefaultMutableTreeNode treeRoot = (DefaultMutableTreeNode) this.templateTree.getModel().getRoot();
        addNode(treeRoot, new DefaultMutableTreeNode(group));
    }

}
